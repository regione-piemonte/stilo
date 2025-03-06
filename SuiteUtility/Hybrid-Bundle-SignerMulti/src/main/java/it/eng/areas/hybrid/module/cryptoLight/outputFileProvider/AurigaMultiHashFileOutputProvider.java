package it.eng.areas.hybrid.module.cryptoLight.outputFileProvider;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.fileOperation.clientws.DnType;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

public class AurigaMultiHashFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(AurigaMultiHashFileOutputProvider.class);

	private String outputUrl;
	private String idSelected;
	private String callBack;
	private String callBackStart;

	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;

	private String callBackResult = null;

	@Override
	public FileOutputProviderOperationResultEnum saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, X509Certificate certificatoDiFirma, String... params) throws Exception {
		logger.info("Metodo saveOutputFile");
		callBackResult = null;
		FileOutputProviderOperationResultEnum operationResult = FileOutputProviderOperationResultEnum.OK;
		if (getOutputUrl() != null) {
			String jSessionId = PreferenceManager.getString(PreferenceKeys.PROPERTY_JSESSIONID);
			URI uri;
			String domain = "";
			try {
				String uploadUrl = PreferenceManager.getString(PreferenceKeys.PROPERTY_OUTPUTURL);
				uri = new URI(uploadUrl);
				domain = uri.getHost();
				domain =  domain.startsWith("www.") ? domain.substring(4) : domain;
			} catch (URISyntaxException e1) {
				logger.error("Errore nel recupero del dominio", e1);
			}
			if (getCallBackStart() != null) {
			}
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse(jSessionId, domain, "/");
			CloseableHttpResponse response = null;

			try {
				logger.info("idDoc " + params[0]);
				List<NameValuePair> lListParames = new ArrayList<NameValuePair>();
				if (params[0] != null) {
					logger.info("idDoc " + params[0]);
					lListParames.add(new BasicNameValuePair("idDoc", params[0]));
				}
				if (params[1] != null) {
					lListParames.add(new BasicNameValuePair("signedBean", params[1]));
				}
				if (params[2] != null) {
					logger.info("firmatario " + params[2]);
					lListParames.add(new BasicNameValuePair("firmatario", params[2]));
				}
				if (params[3] != null) {
					logger.info("pathFileTemp " + params[3]);
					lListParames.add(new BasicNameValuePair("pathFileTemp", params[3]));
				}
				if (params[4] != null) {
					logger.info("versioneDoc " + params[4]);
					lListParames.add(new BasicNameValuePair("versioneDoc", params[4]));
				}
				if (params[5] != null) {
					logger.info("firmaPresente " + params[5]);
					lListParames.add(new BasicNameValuePair("firmaPresente", params[5]));
				}
				if (params[6] != null) {
					logger.info("firmaValida " + params[6]);
					lListParames.add(new BasicNameValuePair("firmaValida", params[6]));
				}
				if (params[7] != null) {
					logger.info("modalitaFirma " + params[7]);
					lListParames.add(new BasicNameValuePair("modalitaFirma", params[7]));
				}
				if (params[8] != null) {
					logger.info("subjectX500Principal " + params[8]);
					lListParames.add(new BasicNameValuePair("subjectX500Principal", params[8]));
				}

				HttpUriRequest upload = RequestBuilder.post().setUri(new URI(getOutputUrl())).addParameters(lListParames.toArray(new NameValuePair[] {})).build();

				String result = null;
				BufferedReader br = null;
				response = lDefaultHttpClient.execute(upload);
				logger.info(response.getStatusLine());
				StringBuilder sb = new StringBuilder();
				String line;
				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				result = sb.toString();
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Il server ha risposto: " + response.getStatusLine() + ": " + result);
					if ("VERIFICA_CONFORMITA_CERTIFICATO_FIRMA_ERROR".equalsIgnoreCase(result)) {
						operationResult = FileOutputProviderOperationResultEnum.VERIFICA_COERENZA_CERTIFICATO_ERROR;
					} else {
						operationResult = FileOutputProviderOperationResultEnum.FILE_UPLOAD_ERROR;
					}
				} else { 
					// Aggiungo le informazioni del certificato nel result
					Map<String, String> mappaAttributiCertificatoFirma = getMappaAttributiCertificatoDiFirma(certificatoDiFirma);
					JSONObject attributiCertificatoFirmaJso = new JSONObject(mappaAttributiCertificatoFirma);
					String attributiCertificatoFirmaStr = attributiCertificatoFirmaJso.toString();
					result = result + "#" + attributiCertificatoFirmaStr; 
					operationResult = FileOutputProviderOperationResultEnum.OK;
					if (callBack != null && !callBack.equals("")) {
						callBackResult = result;
					}
				}
			} catch (Exception e) {
				operationResult = FileOutputProviderOperationResultEnum.FILE_UPLOAD_ERROR;
				logger.error("Errore", e);
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} else {
			operationResult = FileOutputProviderOperationResultEnum.FILE_UPLOAD_ERROR;
			logger.error("OutputUrl null");
		}
		return operationResult;
	}

	@Override
	public void saveOutputParameter() throws Exception {
		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString(PreferenceKeys.PROPERTY_OUTPUTURL);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl(urlOutput);

			idSelected = PreferenceManager.getString(PreferenceKeys.PROPERTY_IDSELECTED);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_IDSELECTED + ": " + idSelected);
			setIdSelected(idSelected);

			callBack = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACK);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack(callBack);

			callBackStart = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACK_START);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACK_START + ": " + callBackStart);
			setCallBackStart(callBackStart);

			String autoClosePostSignString = PreferenceManager.getString(PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if (autoClosePostSignString != null)
				autoClosePostSign = Boolean.valueOf(autoClosePostSignString);

			callBackAskForClose = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);

		} catch (Exception e1) {
			logger.error(e1);
		}
	}

	public String getOutputUrl() {
		return outputUrl;
	}

	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}

	public String getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(String idSelected) {
		this.idSelected = idSelected;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public void setCallBackStart(String callBackStart) {
		this.callBackStart = callBackStart;
	}

	public String getCallBackStart() {
		return callBackStart;
	}

	@Override
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}

	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}

	@Override
	public String getCallbackResult() {
		return callBackResult;
	}

	@Override
	public String getCallback() {
		return this.callBack;
	}
	
	private Map<String, String> getMappaAttributiCertificatoDiFirma(X509Certificate certificatoDiFirma) throws CertificateEncodingException {
		Map<String, String> mappaAttributiCertificato = new HashMap<>();
		X500Name x500name = new JcaX509CertificateHolder(certificatoDiFirma).getSubject();
		Map<String, String> x509NameSubject = getX509Name(certificatoDiFirma, "Subject");
		// Creo subjectDn
		DnType subjectDn = new DnType();
		// Setto Cn
		RDN[] cnsSubject = x500name.getRDNs(BCStyle.CN);
		if (cnsSubject != null && cnsSubject.length > 0) {
			RDN cnSubject = cnsSubject[0];
			if (cnSubject != null && cnSubject.getFirst() != null) {
				mappaAttributiCertificato.put("cnSubject", IETFUtils.valueToString(cnSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("cnSubject", x509NameSubject.get("CN"));
			}
		}
		// Setto serial number
		RDN[] serialNumbersSubject = x500name.getRDNs(BCStyle.SERIALNUMBER);
		if (serialNumbersSubject != null && serialNumbersSubject.length > 0) {
			RDN serialNumberSubject = serialNumbersSubject[0];
			if (serialNumberSubject != null && serialNumberSubject.getFirst() != null) {
				mappaAttributiCertificato.put("serialNumberSubject",IETFUtils.valueToString(serialNumberSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("serialNumberSubject", x509NameSubject.get("SERIALNUMBER"));
			}
		}
		// Setto givenName
		RDN[] givenNamesSubject = x500name.getRDNs(BCStyle.GIVENNAME);
		if (givenNamesSubject != null && givenNamesSubject.length > 0) {
			RDN givenNameSubject = givenNamesSubject[0];
			if (givenNameSubject != null && givenNameSubject.getFirst() != null) {
				subjectDn.setGivenName(IETFUtils.valueToString(givenNameSubject.getFirst().getValue()));
				mappaAttributiCertificato.put("givenNameSubject", IETFUtils.valueToString(givenNameSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("givenNameSubject", x509NameSubject.get("GIVENNAME"));
			}
		}
		// Setto Dn
		RDN[] dnsSubject = x500name.getRDNs(BCStyle.DN_QUALIFIER);
		if (dnsSubject != null && dnsSubject.length > 0) {
			RDN dnSubject = dnsSubject[0];
			if (dnSubject != null && dnSubject.getFirst() != null) {
				mappaAttributiCertificato.put("dnSubject", IETFUtils.valueToString(dnSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("dnSubject", x509NameSubject.get("DN"));
			}
		}
		// Setto surname
		RDN[] surnamesSubject = x500name.getRDNs(BCStyle.SURNAME);
		if (surnamesSubject != null && surnamesSubject.length > 0) {
			RDN surnameSubject = surnamesSubject[0];
			if (surnameSubject != null && surnameSubject.getFirst() != null) {
				mappaAttributiCertificato.put("surnameSubject", IETFUtils.valueToString(surnameSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("surnameSubject", x509NameSubject.get("SURNAME"));
			}
		}
		// Setto ou
		RDN[] ousSubject = x500name.getRDNs(BCStyle.OU);
		if (ousSubject != null && ousSubject.length > 0) {
			RDN ouSubject = ousSubject[0];
			if (ouSubject != null && ouSubject.getFirst() != null) {
				mappaAttributiCertificato.put("ouSubject", IETFUtils.valueToString(ouSubject.getFirst().getValue()));
			}
		}
		// Setto o
		RDN[] osSubject = x500name.getRDNs(BCStyle.O);
		if (osSubject != null && osSubject.length > 0) {
			RDN oSubject = osSubject[0];
			if (oSubject != null && oSubject.getFirst() != null) {
				subjectDn.setO(IETFUtils.valueToString(oSubject.getFirst().getValue()));
				mappaAttributiCertificato.put("oSubject", IETFUtils.valueToString(oSubject.getFirst().getValue()));
			}
		}
		// Setto c
		RDN[] csSubject = x500name.getRDNs(BCStyle.C);
		if (csSubject != null && csSubject.length > 0) {
			RDN cSubject = csSubject[0];
			if (cSubject != null && cSubject.getFirst() != null) {
				mappaAttributiCertificato.put("cSubject", IETFUtils.valueToString(cSubject.getFirst().getValue()));
			}
		}
		// Setto issuerDn
		DnType issuerDn = new DnType();
		X500Name x500nameIssuer = new JcaX509CertificateHolder(certificatoDiFirma).getIssuer();
		RDN[] osIssuer = x500nameIssuer.getRDNs(BCStyle.O);
		if (osIssuer != null && osIssuer.length > 0) {
			RDN oIssuer = osIssuer[0];
			if (oIssuer != null && oIssuer.getFirst() != null) {
				mappaAttributiCertificato.put("oIssuer", IETFUtils.valueToString(oIssuer.getFirst().getValue()));
			}
		}
		if (certificatoDiFirma.getNotBefore() != null) {
			mappaAttributiCertificato.put("notBefore", certificatoDiFirma.getNotBefore().getTime() + "");
		}
		if (certificatoDiFirma.getNotAfter() != null) {
			mappaAttributiCertificato.put("notAfter", certificatoDiFirma.getNotAfter().getTime() + "");
			
		}
		if (certificatoDiFirma.getSerialNumber() != null) {
			mappaAttributiCertificato.put("serialNumber", certificatoDiFirma.getSerialNumber().toString());
		}
		List<String> keyUsageList = getKeyUsage(certificatoDiFirma);
		mappaAttributiCertificato.put("keyUsage", keyUsageList.toString());
		return mappaAttributiCertificato;
	}

	private static Map<String, String> getX509Name(X509Certificate certificato, String tipo) {
		Map<String, String> X500Name = new HashMap<String, String>();
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			X500Name _aName = null;
			if (tipo != null && tipo.equalsIgnoreCase("Subject"))
				_aName = xc509.getSubject();
			if (tipo != null && tipo.equalsIgnoreCase("Issuer"))
				_aName = xc509.getIssuer();
			if (_aName != null) {
				ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();
				RDN[] values = _aName.getRDNs();
				String sAname = "";
				for (int i = 0; i < oidv.length; i++) {
					if (i < values.length)
						sAname = values[i].toString();
					if (BCStyle.INSTANCE.oidToAttrNames(oidv[i]) != null)
						X500Name.put(BCStyle.INSTANCE.oidToAttrNames(oidv[i]).toString(), sAname);
				}
			}
		} catch (CertificateEncodingException e) {
			logger.error("Eccezione getX509Name", e);
		} catch (IOException e) {
			logger.error("Eccezione getX509Name", e);
		}
		return X500Name;
	}
	
	private static Certificate getX509CertificateStructure(X509Certificate certificato) throws CertificateEncodingException, IOException {
		byte[] derdata = certificato.getEncoded();
		ByteArrayInputStream as = new ByteArrayInputStream(derdata);
		ASN1InputStream aderin = new ASN1InputStream(as);
		ASN1Primitive ado = aderin.readObject();
		Certificate xc509 = Certificate.getInstance(ado);
		return xc509;
	}
	
	public static List<String> getKeyUsage(X509Certificate certificato) {

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
	
	private static Extensions getX509Extensions(X509Certificate certificato) {
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			return xc509.getTBSCertificate().getExtensions();
		} catch (CertificateEncodingException e) {
			logger.error("Eccezione getX509Extensions", e);
		} catch (IOException e) {
			logger.error("Eccezione getX509Extensions", e);
		}
		return null;
	}

}
