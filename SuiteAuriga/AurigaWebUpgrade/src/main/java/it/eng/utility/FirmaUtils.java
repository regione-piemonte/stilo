/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import it.eng.auriga.ui.module.layout.server.common.CalcolaImpronteService;
import it.eng.fileOperation.clientws.DnType;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FirmaUtils {
	
	private static final Logger log = Logger.getLogger(FirmaUtils.class);

	public static MimeTypeFirmaBean aggiungiInfoFirmaInInfoFile(String uriFileFirmato, MimeTypeFirmaBean infoFileDaAggiornare, CertBean certificatoDiFirmaBean, String tipoFirma) throws StorageException, Exception {
		X509Certificate certificatoDiFirma = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(certificatoDiFirmaBean.getCertValue()));
		Map<String, DnType> mappaDnType = createDnTypeFromX509Certificate(certificatoDiFirma);
		DnType subjectDn = mappaDnType.get("subjectDn");
		DnType issuerDn = mappaDnType.get("issuerDn");
		Map<String, String> mappaAttributiCertificato = createMappaAttributiCertificatoFromX509Certificate(certificatoDiFirma);
		List<String> keyUsageList = getKeyUsageFromX509Certificate(certificatoDiFirma);
		return aggiungiInfoFirmaInInfoFile(uriFileFirmato, infoFileDaAggiornare, tipoFirma, subjectDn, issuerDn, mappaAttributiCertificato, keyUsageList);
	}
	
	public static MimeTypeFirmaBean aggiungiInfoFirmaInInfoFile(String uriFileFirmato, MimeTypeFirmaBean infoFileDaAggiornare, Map<String, String> mappaAttributiCertificatoDiFirma, String tipoFirma) throws StorageException, Exception {
		Map<String, DnType> mappaDnType = createDnTypeFromMappaAttributi(mappaAttributiCertificatoDiFirma);
		DnType subjectDn = mappaDnType.get("subjectDn");
		DnType issuerDn = mappaDnType.get("issuerDn");
		Map<String, String> mappaAttributiCertificato = createMappaAttributiCertificatoFromFromMappaAttributi(mappaAttributiCertificatoDiFirma);
		List<String> keyUsageList = getKeyUsageFromMappaAttributi(mappaAttributiCertificatoDiFirma);
		return aggiungiInfoFirmaInInfoFile(uriFileFirmato, infoFileDaAggiornare, tipoFirma, subjectDn, issuerDn, mappaAttributiCertificato, keyUsageList);
	}

	private static MimeTypeFirmaBean aggiungiInfoFirmaInInfoFile(String uriFileFirmato, MimeTypeFirmaBean infoFileDaAggiornare, String tipoFirma, DnType subjectDn, DnType issuerDn, Map<String, String> mappaAttributiCertificato, List<String> keyUsageList) throws StorageException, Exception {
		File fileFirmato = StorageImplementation.getStorage().getRealFile(uriFileFirmato);
		// Creo la stringa del firmatario
		String strFirmatario = (subjectDn.getC() == null ? "" : "C=" + subjectDn.getC()) + (subjectDn.getCn() == null ? "" : " CN=" + subjectDn.getCn())
				/**+ (subject.getName() == null ? "" : " NAME=" + subject.getName())*/ + (subjectDn.getO() == null ? "" : " O=" + subjectDn.getO())
				+ (subjectDn.getOu() == null ? "" : " OU=" + subjectDn.getOu());
		
		// Creo il nuovo firmatario e setto i valori
		Firmatari nuovoFirmatario = new Firmatari();
		nuovoFirmatario.setFiglioDi("1");
		String tipoBustaCrittografica = "";
		// In base al tipo di firma vedo se aggiungere la busta nel livello esterno o se creare un nuovo livello
		if (tipoFirma != null && tipoFirma.toLowerCase().indexOf("pades") != -1) {
			// Se la firma è PAdES i livelli di firma restano invariati e aggiungerò la busta al livello più esterno, ovvero 1
			nuovoFirmatario.setTipoFirma("PAdES");
			tipoBustaCrittografica = "PAdES";
		} else {
			if (tipoFirma != null && tipoFirma.toLowerCase().indexOf("congiunta") != -1) {
				// Se la firma è CAdES congiunta i livelli di firma restano invariati e aggiungerò la busta al livello più esterno, ovvero 1
				nuovoFirmatario.setTipoFirma("CAdES_BES");
				tipoBustaCrittografica = "CAdES_BES";
			} else {
				// Se la firma è CAdES verirticale aggiungerò un nuovo livello di firma 1, e dovrò traslare quelli esistenti
				nuovoFirmatario.setTipoFirma("CAdES_BES");
				tipoBustaCrittografica = "CAdES_BES";
				// Aggiungo un nuovo livello, quindi devo traslare tutte le firme esistenti
				Firmatari[] firmatari = infoFileDaAggiornare.getBuste();
				for (int i = 0; i < firmatari.length; i++) {
					Firmatari firmatario = firmatari[i];
					String livello = firmatario.getFiglioDi();
					if (livello != null) {
						int livelloInt = Integer.parseInt(livello);
						firmatario.setFiglioDi((livelloInt + 1) + "");
					}
				}
			}
		}
		nuovoFirmatario.setSubject(strFirmatario);
		nuovoFirmatario.setEnteCertificatore(issuerDn.getO());
		nuovoFirmatario.setNomeFirmatario(subjectDn.getCn());
		boolean certificatoScaduto = false;
		Date dataFirma = new Date();
		if (StringUtils.isNotBlank(mappaAttributiCertificato.get("notBefore"))) {
			Date notBefore = new Date(Long.parseLong(mappaAttributiCertificato.get("notBefore")));
			nuovoFirmatario.setDataEmissione(notBefore);			
			if (dataFirma.before(notBefore)) {
				certificatoScaduto = true;
			}
		} else {
			log.error("Non è possibile determinare il valore notAfter del certificato di firma");
			throw new Exception("Non è possibile determinare il valore notAfter del certificato di firma");
		}
		if (StringUtils.isNotBlank(mappaAttributiCertificato.get("notAfter"))) {
			Date notAfter = new Date(Long.parseLong(mappaAttributiCertificato.get("notAfter")));
			nuovoFirmatario.setDataScadenza(new Date(Long.parseLong(mappaAttributiCertificato.get("notAfter"))));
			if (dataFirma.after(notAfter)) {
				certificatoScaduto = true;
			}
		} else {
			log.error("Non è possibile determinare il valore notBefore del certificato di firma");
			throw new Exception("Non è possibile determinare il valore notBefore del certificato di firma");
		}
		if (StringUtils.isNotBlank(mappaAttributiCertificato.get("serialNumber"))) {
			nuovoFirmatario.setSerialNumber(mappaAttributiCertificato.get("serialNumber"));
		}
		if (keyUsageList != null) {
			nuovoFirmatario.setKeyUsages(keyUsageList.toArray(new String[keyUsageList.size()]));
		}
		nuovoFirmatario.setDataFirma(dataFirma);	
		nuovoFirmatario.setStatoCertificato("Certificato credibile");
		nuovoFirmatario.setCertExpiration(certificatoScaduto ? "Il certificato non è in corso di validità" : "Il certificato è in corso di validità");
		nuovoFirmatario.setFirmaValida(!certificatoScaduto);
		nuovoFirmatario.setVerificationStatus("La firma risulta valida");
		nuovoFirmatario.setDataVerificaFirma(new Date());
		nuovoFirmatario.setCrlResult("Il certificato non è revocato ne sospeso alla data");
		nuovoFirmatario.setCaReliability("Il certificato è emesso da una CA accreditata");
		nuovoFirmatario.setTipoFirmaQA("Q");
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		SignatureVerifyOptionBean lSignatureVerifyOptionBean = (SignatureVerifyOptionBean) SpringAppContext.getContext().getBean("signatureVerifyOptionBean");
		nuovoFirmatario.setInfoFirma(InfoFileUtility.getInfoFirma(lDocumentConfiguration, lSignatureVerifyOptionBean));
		// Non setto le informazioni della marca temporale, perchè se marco il file passo per getInfoFile di fileop
		// Aggiorno le buste creando il nuovo array, il nuovo firmatario avrà posizione 0
		Firmatari[] firmatatiDaAggiornare = infoFileDaAggiornare.getBuste();
		Firmatari[] firmatatiAggiornati;
		if (firmatatiDaAggiornare != null) {
			firmatatiAggiornati = new Firmatari[firmatatiDaAggiornare.length + 1];
			for (int i = 0; i < firmatatiDaAggiornare.length; i++) {
				firmatatiAggiornati[i + 1] = firmatatiDaAggiornare[i];
			}
			firmatatiAggiornati[0] = nuovoFirmatario;
		} else {
			firmatatiAggiornati = new Firmatari[1];
			firmatatiAggiornati[0] = nuovoFirmatario;
		}
		infoFileDaAggiornare.setBuste(firmatatiAggiornati);
		// Aggiorno i firmatari
		List<String> firmatari = new ArrayList<String>();
		if (firmatatiAggiornati != null){
			for (Firmatari firmatario : firmatatiAggiornati) {
				firmatari.add(firmatario.getNomeFirmatario());
			}						
		}
		
		// Aggiorno altri parametri dll'infoFile
		infoFileDaAggiornare.setFirmatari(firmatari.toArray(new String[] {}));
		infoFileDaAggiornare.setAlgoritmo("SHA-256");
		infoFileDaAggiornare.setEncoding("base64");
		infoFileDaAggiornare.setFirmato(true);
		infoFileDaAggiornare.setFirmaValida(!certificatoScaduto);
		infoFileDaAggiornare.setTipoFirma(tipoBustaCrittografica);
		// Salvo l'impronta del file prefirma, la devo passare al DB per capire se sto rifirmando un file già salvato oppure no. 
		// In base a questo i firmatari in DB verranno aggiunti a quelli già esistenti oppure andranno a sostituire quelli già presenti
		// Se improntaFilePreFirma valorizzata vuol dire che ho fatto più firme consecutive sul file prima del salvataggio, quandi non la devo riaggiornare
		if (StringUtils.isBlank(infoFileDaAggiornare.getImprontaFilePreFirma())) {
			infoFileDaAggiornare.setImprontaFilePreFirma(infoFileDaAggiornare.getImpronta());
		}
		infoFileDaAggiornare.setImpronta((new CalcolaImpronteService()).calcolaImprontaWithoutFileOp(fileFirmato, "SHA-256", "base64"));
		infoFileDaAggiornare.setBytes(fileFirmato.length());
		// Inserisco e informazioni di firma e marca della busta più esterna
		InfoFirmaMarca infoFirmaMarca = new InfoFirmaMarca();
		infoFirmaMarca.setDataOraVerificaFirma(new Date());
		infoFirmaMarca.setFirmeNonValideBustaCrittografica(false);
		// Se provengo da una firma poi in CalcolaFirmaDatasource aggiorno il parametro firmeExtraAuriga e settto i parametri delle firme interne
		infoFirmaMarca.setFirmeExtraAuriga(true);
		infoFirmaMarca.setTipoBustaCrittografica(tipoBustaCrittografica);
		infoFirmaMarca.setInfoBustaCrittografica(InfoFileUtility.getInfoFirma(lDocumentConfiguration, lSignatureVerifyOptionBean));
		infoFileDaAggiornare.setInfoFirmaMarca(infoFirmaMarca);

		return infoFileDaAggiornare;
	}
	
	private static Map<String, DnType> createDnTypeFromX509Certificate(X509Certificate certificatoDiFirma) throws CertificateException, IOException {
		X500Name x500name = new JcaX509CertificateHolder(certificatoDiFirma).getSubject();
		Map<String, String> x509NameSubject = getX509Name(certificatoDiFirma, "Subject");
		// Creo subjectDn
		DnType subjectDn = new DnType();
		// Setto Cn
		RDN[] cnsSubject = x500name.getRDNs(BCStyle.CN);
		if (cnsSubject != null && cnsSubject.length > 0) {
			RDN cnSubject = cnsSubject[0];
			if (cnSubject != null && cnSubject.getFirst() != null) {
				subjectDn.setCn(IETFUtils.valueToString(cnSubject.getFirst().getValue()));
			} else {
				subjectDn.setCn(x509NameSubject.get("CN"));
			}
		}
		// Setto serial number
		RDN[] serialNumbersSubject = x500name.getRDNs(BCStyle.SERIALNUMBER);
		if (serialNumbersSubject != null && serialNumbersSubject.length > 0) {
			RDN serialNumberSubject = serialNumbersSubject[0];
			if (serialNumberSubject != null && serialNumberSubject.getFirst() != null) {
				subjectDn.setSerialNumber(IETFUtils.valueToString(serialNumberSubject.getFirst().getValue()));
			} else {
				subjectDn.setSerialNumber(x509NameSubject.get("SERIALNUMBER"));
			}
		}
		// Setto givenName
		RDN[] givenNamesSubject = x500name.getRDNs(BCStyle.GIVENNAME);
		if (givenNamesSubject != null && givenNamesSubject.length > 0) {
			RDN givenNameSubject = givenNamesSubject[0];
			if (givenNameSubject != null && givenNameSubject.getFirst() != null) {
				subjectDn.setGivenName(IETFUtils.valueToString(givenNameSubject.getFirst().getValue()));
			} else {
				subjectDn.setGivenName(x509NameSubject.get("GIVENNAME"));
			}
		}
		// Setto Dn
		RDN[] dnsSubject = x500name.getRDNs(BCStyle.DN_QUALIFIER);
		if (dnsSubject != null && dnsSubject.length > 0) {
			RDN dnSubject = dnsSubject[0];
			if (dnSubject != null && dnSubject.getFirst() != null) {
				subjectDn.setDn(IETFUtils.valueToString(dnSubject.getFirst().getValue()));
			} else {
				subjectDn.setDn(x509NameSubject.get("DN"));
			}
		}
		// Setto surname
		RDN[] surnamesSubject = x500name.getRDNs(BCStyle.SURNAME);
		if (surnamesSubject != null && surnamesSubject.length > 0) {
			RDN surnameSubject = surnamesSubject[0];
			if (surnameSubject != null && surnameSubject.getFirst() != null) {
				subjectDn.setSurname(IETFUtils.valueToString(surnameSubject.getFirst().getValue()));
			} else {
				subjectDn.setSurname(x509NameSubject.get("SURNAME"));
			}
		}
		// Setto ou
		RDN[] ousSubject = x500name.getRDNs(BCStyle.OU);
		if (ousSubject != null && ousSubject.length > 0) {
			RDN ouSubject = ousSubject[0];
			if (ouSubject != null && ouSubject.getFirst() != null) {
				subjectDn.setOu(IETFUtils.valueToString(ouSubject.getFirst().getValue()));
			}
		}
		// Setto o
		RDN[] osSubject = x500name.getRDNs(BCStyle.O);
		if (osSubject != null && osSubject.length > 0) {
			RDN oSubject = osSubject[0];
			if (oSubject != null && oSubject.getFirst() != null) {
				subjectDn.setO(IETFUtils.valueToString(oSubject.getFirst().getValue()));
			}
		}
		// Setto c
		RDN[] csSubject = x500name.getRDNs(BCStyle.C);
		if (csSubject != null && csSubject.length > 0) {
			RDN cSubject = csSubject[0];
			if (cSubject != null && cSubject.getFirst() != null) {
				subjectDn.setC(IETFUtils.valueToString(cSubject.getFirst().getValue()));
			}
		}
		// Setto issuerDn
		DnType issuerDn = new DnType();
		X500Name x500nameIssuer = new JcaX509CertificateHolder(certificatoDiFirma).getIssuer();
		RDN[] osIssuer = x500nameIssuer.getRDNs(BCStyle.O);
		if (osIssuer != null && osIssuer.length > 0) {
			RDN oIssuer = osIssuer[0];
			if (oIssuer != null && oIssuer.getFirst() != null) {
				issuerDn.setO(IETFUtils.valueToString(oIssuer.getFirst().getValue()));
			}
		}
		// Creo la mapa risultante
		Map<String, DnType> mappaDnType = new HashMap<>();
		mappaDnType.put("issuerDn", issuerDn);
		mappaDnType.put("subjectDn", subjectDn);
		return mappaDnType;
	}
	
	private static Map<String, DnType> createDnTypeFromMappaAttributi(Map<String, String> mappaAttributiCertificato) throws CertificateException {
		// Creo subjectDn
		DnType subjectDn = new DnType();
		// Setto Cn
		String cnSubject = mappaAttributiCertificato.get("cnSubject");
		if (StringUtils.isNotBlank(cnSubject)) {
			subjectDn.setCn(cnSubject);
		}
		// Setto serial number
		String serialNumberSubject = mappaAttributiCertificato.get("serialNumberSubject");
		if (StringUtils.isNotBlank(serialNumberSubject)) {
			subjectDn.setSerialNumber(serialNumberSubject);
		}
		// Setto givenName
		String givenNameSubject = mappaAttributiCertificato.get("givenNameSubject");
		if (StringUtils.isNotBlank(givenNameSubject)) {
			subjectDn.setGivenName(givenNameSubject);
		}
		// Setto Dn
		String dnSubject = mappaAttributiCertificato.get("dnSubject");
		if (StringUtils.isNotBlank(dnSubject)) {
			subjectDn.setDn(dnSubject);
		} 
		// Setto surname
		String surnameSubject = mappaAttributiCertificato.get("surnameSubject");
		if (StringUtils.isNotBlank(surnameSubject)) {
			subjectDn.setSurname(surnameSubject);
		} 
		// Setto ou
		String ouSubject = mappaAttributiCertificato.get("ouSubject");
		if (StringUtils.isNotBlank(ouSubject)) {
			subjectDn.setOu(ouSubject);
		}
		// Setto o
		String oSubject = mappaAttributiCertificato.get("oSubject");
		if (StringUtils.isNotBlank(oSubject)) {
			subjectDn.setO(oSubject);
		}
		// Setto c
		String cSubject = mappaAttributiCertificato.get("cSubject");
		if (StringUtils.isNotBlank(cSubject)) {
			subjectDn.setC(cSubject);
		}
		// Creo issureDn
		DnType issuerDn = new DnType();
		// Setto o
		String oIssuer = mappaAttributiCertificato.get("oIssuer");
		if (StringUtils.isNotBlank(oIssuer)) {
			issuerDn.setO(oIssuer);
		}
		// Creo la mapa risultante
		Map<String, DnType> mappaDnType = new HashMap<>();
		mappaDnType.put("issuerDn", issuerDn);
		mappaDnType.put("subjectDn", subjectDn);
		return mappaDnType;
	}
	
	private static Map<String, String> createMappaAttributiCertificatoFromX509Certificate(X509Certificate certificatoDiFirma) throws CertificateException {
		Map<String, String> mappaAttributiCertificato = new HashMap<>();
		if (certificatoDiFirma.getNotBefore() != null) {
			mappaAttributiCertificato.put("notBefore", certificatoDiFirma.getNotBefore().getTime() + "");
		}
		if (certificatoDiFirma.getNotAfter() != null) {
			mappaAttributiCertificato.put("notAfter", certificatoDiFirma.getNotAfter().getTime() + "");
		}
		if (certificatoDiFirma.getSerialNumber() != null) {
			mappaAttributiCertificato.put("serialNumber", certificatoDiFirma.getSerialNumber().toString());
		}
		return mappaAttributiCertificato;
	}
	
	private static Map<String, String> createMappaAttributiCertificatoFromFromMappaAttributi(Map<String, String> mappaAttributi) throws CertificateException {
		Map<String, String> mappaAttributiCertificato = new HashMap<>();
		if (mappaAttributi != null) {
			mappaAttributiCertificato.put("notBefore", mappaAttributi.get("notBefore"));
			mappaAttributiCertificato.put("notAfter", mappaAttributi.get("notAfter"));
			mappaAttributiCertificato.put("serialNumber", mappaAttributi.get("serialNumber"));
		}
		return mappaAttributiCertificato;
	}
	
	public static List<String> getKeyUsageFromX509Certificate(X509Certificate certificato) {
		List<String> keyUsages = new ArrayList<String>();
		Extensions xc509Ext = getX509Extensions(certificato);
		if (xc509Ext != null) {
			KeyUsage ku = KeyUsage.fromExtensions(xc509Ext);
			if (ku != null) {
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.digitalSignature) != 0)
					keyUsages.add("digitalSignature");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.nonRepudiation) != 0)
					keyUsages.add("nonRepudiation");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyEncipherment) != 0)
					keyUsages.add("keyEncipherment");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.dataEncipherment) != 0)
					keyUsages.add("dataEncipherment");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyAgreement) != 0)
					keyUsages.add("keyAgreement");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyCertSign) != 0)
					keyUsages.add("keyCertSign");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.cRLSign) != 0)
					keyUsages.add("cRLSign");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.encipherOnly) != 0)
					keyUsages.add("encipherOnly");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.decipherOnly) != 0)
					keyUsages.add("decipherOnly");
			}
		}
		return keyUsages;
	}
	
	public static List<String> getKeyUsageFromMappaAttributi(Map<String, String> mappaAttributiCertificato) {
		List<String> keyUsages = new ArrayList<String>();
		String elencoKeyUsage = mappaAttributiCertificato.get("keyUsage");
		if (StringUtils.isNotBlank(elencoKeyUsage)) {
			elencoKeyUsage = elencoKeyUsage.replace("[", "").replace("]", "");
			keyUsages = new ArrayList<String>(Arrays.asList(elencoKeyUsage.split(",")));
		}
		return keyUsages;
	}
	
	public static Map<String, String> getX509Name(X509Certificate certificato, String tipo) throws CertificateEncodingException, IOException {
		Map<String, String> X500Name = new HashMap<String, String>();
		Certificate xc509;
		xc509 = getX509CertificateStructure(certificato);
		org.bouncycastle.asn1.x500.X500Name _aName = null;
		if (tipo != null && tipo.equalsIgnoreCase("Subject")) {
			_aName = xc509.getSubject();
		}
		if (tipo != null && tipo.equalsIgnoreCase("Issuer")) {
			_aName = xc509.getIssuer();
		}
		if (_aName != null) {
			ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();
			RDN[] values = _aName.getRDNs();
			for (int i = 0; i < oidv.length; i++) {
				RDN cn = values[i];
				AttributeTypeAndValue[] cns = cn.getTypesAndValues();
				for(AttributeTypeAndValue x : cns){
					String attributeId=BCStyle.CN.getId();
					if (BCStyle.C.equals(x.getType())) {
			            attributeId = "C";
			        } else if (BCStyle.O.equals(x.getType())) {
			            attributeId = "O";
			        } else if (BCStyle.OU.equals(x.getType())) {
			            attributeId = "OU";
			        } else if (BCStyle.DN_QUALIFIER.equals(x.getType())) {
			            attributeId = "DN_QUALIFIER";
			        } else if (BCStyle.ST.equals(x)) {
			            attributeId = "ST";
			        } else if (BCStyle.L.equals(x.getType())) {
			            attributeId = "L";
			        } else if (BCStyle.CN.equals(x.getType())) {
			            attributeId = "CN";
			        } else if (BCStyle.SN.equals(x.getType())) {
			            attributeId = "SN";
			        } else if (BCStyle.DC.equals(x.getType())) {
			            attributeId = "DC";
			        }
					X500Name.put(attributeId, x.getValue().toString());
				}
			}
		}
		return X500Name;
	}
	
//	private static Map<String, String> getX509Name(X509Certificate certificato, String tipo) {
//		Map<String, String> X500Name = new HashMap<String, String>();
//		Certificate xc509;
//		try {
//			xc509 = getX509CertificateStructure(certificato);
//			X500Name _aName = null;
//			if (tipo != null && tipo.equalsIgnoreCase("Subject"))
//				_aName = xc509.getSubject();
//			if (tipo != null && tipo.equalsIgnoreCase("Issuer"))
//				_aName = xc509.getIssuer();
//			if (_aName != null) {
//				ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();
//				RDN[] values = _aName.getRDNs();
//				String sAname = "";
//				for (int i = 0; i < oidv.length; i++) {
//					if (i < values.length)
//						sAname = values[i].toString();
//					if (BCStyle.INSTANCE.oidToAttrNames(oidv[i]) != null)
//						X500Name.put(BCStyle.INSTANCE.oidToAttrNames(oidv[i]).toString(), sAname);
//				}
//			}
//		} catch (CertificateEncodingException e) {
//			log.error("Eccezione getX509Name", e);
//		} catch (IOException e) {
//			log.error("Eccezione getX509Name", e);
//		}
//		return X500Name;
//	}
	
	private static Certificate getX509CertificateStructure(X509Certificate certificato) throws CertificateEncodingException, IOException {
		byte[] derdata = certificato.getEncoded();
		ByteArrayInputStream as = new ByteArrayInputStream(derdata);
		ASN1InputStream aderin = new ASN1InputStream(as);
		ASN1Primitive ado = aderin.readObject();
		Certificate xc509 = Certificate.getInstance(ado);
		return xc509;
	}
	
	private static Extensions getX509Extensions(X509Certificate certificato) {
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			return xc509.getTBSCertificate().getExtensions();
		} catch (CertificateEncodingException e) {
			log.error("Eccezione getX509Extensions", e);
		} catch (IOException e) {
			log.error("Eccezione getX509Extensions", e);
		}
		return null;
	}
}
