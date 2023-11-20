/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUTCTime;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSSignedDataStreamGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SimpleAttributeTableGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import it.eng.auriga.ui.module.layout.server.common.P7MDetector;
import it.eng.hsm.HsmSignOptionFactory;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientRuntimeOperatorException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.pkbox.generated.PKBoxException_Exception;
import it.eng.hsm.utils.CustomContentSigner;
import it.eng.utility.ui.sign.CMSUtils;
import it.eng.utility.ui.sign.SignerHashUtil;

public class FirmaCadesUtil {

	private static Logger logger = Logger.getLogger(FirmaCadesUtil.class);

	public static byte[] firmaFileP7mVerticaleHash(byte[] fileDaFirmare, X509Certificate userCert, Hsm clientHsm) throws HsmClientSignatureException {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			MessageDigest md = MessageDigest.getInstance("SHA256");
			byte[] buf = new byte[65664];

			InputStream fis = new ByteArrayInputStream(fileDaFirmare);
			int nRead = 0;
			while ((nRead = fis.read(buf)) > 0) {
				md.update(buf, 0, nRead);
			}
			fis.close();
			byte[] contentDigest = md.digest();

			List<X509Certificate> certList = new ArrayList<X509Certificate>();
			certList.add(userCert);
			Store y = new JcaCertStore(certList);

			CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
			ContentSigner contentSigner = new CustomContentSigner(userCert.getEncoded(), "SHA256withRSA", clientHsm);

			md = MessageDigest.getInstance("SHA256");
			byte[] certDigest = md.digest(userCert.getEncoded());
			AttributeTable attributeTable = generateSignedAttributes(contentDigest, certDigest);

			JcaDigestCalculatorProviderBuilder jcadigestbuilder = new JcaDigestCalculatorProviderBuilder();
			DigestCalculatorProvider jcadigestprov = jcadigestbuilder.setProvider("BC").build();
			JcaSignerInfoGeneratorBuilder jca_sigb = new JcaSignerInfoGeneratorBuilder(jcadigestprov);
			SignerInfoGenerator sigInfoGen = jca_sigb.build(contentSigner, userCert);

			SignerInfoGenerator sigInfoGenWithSignedAttr = new SignerInfoGenerator(sigInfoGen, new SimpleAttributeTableGenerator(attributeTable), null);
			gen.addSignerInfoGenerator(sigInfoGenWithSignedAttr);

			gen.addCertificates(y);

			fis = new ByteArrayInputStream(fileDaFirmare);

			OutputStream outs = gen.open(bout, true);

			while ((nRead = fis.read(buf)) > 0) {
				outs.write(buf, 0, nRead);
			}
			outs.close();
			outs.flush();
			bout.close();
			fis.close();

			byte[] bytesfirmati = bout.toByteArray();

			return bytesfirmati;
		} catch (HsmClientRuntimeOperatorException e) {
			// Questa eccezione viene sollavata dal CustomContentSigner in caso di errori
			logger.error("Errore nella firma remota", e);
			String errorMessage = "Errore nella firma remota";
			if (e.getMessage() != null) {
				errorMessage += ". " + e.getMessage();
			}
			throw new HsmClientSignatureException(errorMessage, e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (CMSException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (CertificateEncodingException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (OperatorCreationException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}
	}
	
	public static List<byte[]> firmaFileP7mVerticaleHashPkBox(List<byte[]> listaFileDaFirmare, X509Certificate userCert, Hsm clientHsm) throws HsmClientSignatureException {
		
		List<HashRequestBean> listaHashDaFirmare = new ArrayList<HashRequestBean>();
		
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		try {
			for (byte[] fileDaFirmare : listaFileDaFirmare) {
				HashRequestBean hashRequestBean = new HashRequestBean();
				
				MessageDigest md = MessageDigest.getInstance("SHA256");
			
				byte[] buf = new byte[65664];
	
				InputStream fis = new ByteArrayInputStream(fileDaFirmare);
				int nRead = 0;
				while ((nRead = fis.read(buf)) > 0) {
					md.update(buf, 0, nRead);
				}
				fis.close();
				
				byte[] contentDigestMD = md.digest();
				logger.debug("Digest MD: " + new String(contentDigestMD));
				logger.debug("Digest MD BASE64: " + Base64.encodeBase64String(contentDigestMD));
				byte[] contentDigest = digest(fileDaFirmare, "2");
				logger.debug("Digest PKBOX: " + new String(contentDigest));
				logger.debug("Digest PKBOX BASE64: " + Base64.encodeBase64String(contentDigest));
				
				hashRequestBean.setHash(Base64.encodeBase64String(contentDigest));
				hashRequestBean.setSignOption(HsmSignOptionFactory.getSignOption(clientHsm.getHsmConfig().getHsmType()));
				listaHashDaFirmare.add(hashRequestBean);
			}
				
			SignResponseBean response = clientHsm.firmaMultiplaHash(listaHashDaFirmare);
			
			MessageBean message = response.getMessage();
			if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
				logger.error("Errore: - " + message.getCode() + " " + message.getDescription());
				throw new HsmClientSignatureException("Errore nella firma: " + message.getDescription());
			}
			List<HashResponseBean> listHashResponseBean = response.getHashResponseBean();
			List<byte[]> listaBytesfirmati = new ArrayList<byte[]>();
			if ((listHashResponseBean != null) && (listHashResponseBean.size() >= 1)) {
				for (int i = 0; i < listHashResponseBean.size(); i++) {
					try {
						logger.debug("dimensione listHashResponseBean: " + listHashResponseBean.size());
						logger.debug("i: " + i);
						logger.debug("listHashResponseBean.get(i): " + listHashResponseBean.get(i));
						logger.debug("listHashResponseBean.get(i).getMessage(): " + listHashResponseBean.get(i).getMessage());
						logger.debug("listHashResponseBean.get(i).getMessage().getStatus(): " + listHashResponseBean.get(i).getMessage().getStatus());
						if (listHashResponseBean.get(i).getMessage().getStatus().equals(ResponseStatus.OK)) {
							byte[] hashFirmata = listHashResponseBean.get(i).getHashFirmata();
							logger.debug("hashFirmata " + new String(hashFirmata));
						
							byte[] dataBytes =Base64.decodeBase64(hashFirmata);
							logger.debug("hashFirmata decodificata " + new String(dataBytes));
						
							CMSSignedData s = new CMSSignedData(dataBytes);
							ByteArrayInputStream bis = new ByteArrayInputStream(s.getEncoded());
							//OutputStream outputStream = new FileOutputStream(File.createTempFile("signed", "p7m"));
							ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
							byte[] fileDaFirmare = listaFileDaFirmare.get(i);
							SignerHashUtil.attachP7M(fileDaFirmare, bis, outputStream);
						
							byte[] bytesfirmati = outputStream.toByteArray();
							logger.debug("bytesfirmati  " + new String(bytesfirmati));
							listaBytesfirmati.add(bytesfirmati);
						} else {
							logger.error("Errore nella firma per il file numero " + i + " Errore: " + listHashResponseBean.get(i).getMessage().getDescription());
							throw new HsmClientSignatureException("Errore nella firma: " + listHashResponseBean.get(i).getMessage().getDescription());
						}
					} catch (Exception e) {
						logger.error("Errore nella firma", e);
						throw new HsmClientSignatureException(e.getMessage(), e);
					}
				}
			}
			return listaBytesfirmati;
		} catch (NoSuchAlgorithmException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (UnsupportedOperationException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (HsmClientConfigException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} 
	}
		
	public static List<byte[]> firmaFileP7mVerticaleFile(List<byte[]> listaFileDaFirmare, Hsm clientHsm) throws HsmClientSignatureException {
		try {
			SignResponseBean signResponseBean = clientHsm.firmaCades(listaFileDaFirmare, null);
			if ((signResponseBean != null) && (signResponseBean.getMessage() != null)) {
				logger.debug("Response Status: " + signResponseBean.getMessage().getStatus());
				logger.debug("Response Return Code: " + signResponseBean.getMessage().getCode());
				if (signResponseBean.getMessage().getDescription() != null) {
					logger.debug("Response Description: " + signResponseBean.getMessage().getDescription());
				}
				if (signResponseBean.getMessage().getStatus().equals(ResponseStatus.OK)) {
					List<FileResponseBean> listaFileResponseBean = signResponseBean.getFileResponseBean();
					List<byte[]> listaByteFirmati = new ArrayList<>();
					for (FileResponseBean fileResponseBean : listaFileResponseBean) {
						listaByteFirmati.add(fileResponseBean.getFileFirmato());
					}
					return listaByteFirmati;
				} else {
					logger.error("Errore nella firma. Errore: " + signResponseBean.getMessage().getDescription());
					throw new HsmClientSignatureException("Errore nella firma: " + signResponseBean.getMessage().getDescription());
				}
			}
		} catch (HsmClientConfigException e) {
			logger.error("Errore in firma cades verticale", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (HsmClientSignatureException e) {
			logger.error("Errore in firma cades verticale", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (UnsupportedOperationException e) {
			logger.error("Errore in firma cades verticale", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}
		return null;
	}
	
	public static byte[] firmaFileP7mCongiuntaHash(byte[] fileDaFirmare, X509Certificate userCert, Hsm clientHsm) throws HsmClientSignatureException {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			InputStream fis = new ByteArrayInputStream(fileDaFirmare);

			CMSSignedDataParser cmsSignedDataParser = CMSUtils.getCMSSignedDataParser(fileDaFirmare);			
			
			InputStream contentStream = cmsSignedDataParser.getSignedContent().getContentStream();
			byte[] contentDigest = DigestUtils.sha256(contentStream);
			
			cmsSignedDataParser.close();
			
			// get data
			CMSSignedData cmsSignedData = CMSUtils.getCMSSignedData(fileDaFirmare);
			
			List<X509Certificate> certList = new ArrayList<X509Certificate>();
			certList.add(userCert);
			Store y = new JcaCertStore(certList);

			ContentSigner contentSigner = new CustomContentSigner(userCert.getEncoded(), "SHA256withRSA", clientHsm);

			MessageDigest md = MessageDigest.getInstance("SHA256");
			byte[] certDigest = md.digest(userCert.getEncoded());
			AttributeTable attributeTable = generateSignedAttributes(contentDigest, certDigest);
			JcaDigestCalculatorProviderBuilder jcadigestbuilder = new JcaDigestCalculatorProviderBuilder();
			DigestCalculatorProvider jcadigestprov = jcadigestbuilder.setProvider("BC").build();
			JcaSignerInfoGeneratorBuilder jca_sigb = new JcaSignerInfoGeneratorBuilder(jcadigestprov);
			SignerInfoGenerator sigInfoGen = jca_sigb.build(contentSigner, userCert);

			SignerInfoGenerator sigInfoGenWithSignedAttr = new SignerInfoGenerator(sigInfoGen, new SimpleAttributeTableGenerator(attributeTable), null);

			CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

			gen.addSignerInfoGenerator(sigInfoGenWithSignedAttr);
			gen.addCertificates(y);

			// CMSProcessable message = new CMSProcessableByteArray(contentDigest);

			CMSTypedData content = cmsSignedData.getSignedContent();
			cmsSignedData = gen.generate(content, false);

			SignerHashUtil.addSignerInfo(fileDaFirmare, cmsSignedData, bout);

			bout.flush();
			bout.close();
			fis.close();

			return bout.toByteArray();
		} catch (HsmClientRuntimeOperatorException e) {
			// Questa eccezione viene sollavata dal CustomContentSigner in caso di errori
			logger.error("Errore nella firma remota", e);
			String errorMessage = "Errore nella firma remota";
			if (e.getMessage() != null) {
				errorMessage += ". " + e.getMessage();
			}
			throw new HsmClientSignatureException(errorMessage, e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (CMSException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (CertificateEncodingException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (OperatorCreationException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}

	}
			
	public static List<byte[]> firmaFileP7mCongiuntaHashPkBox(List<byte[]> listaFileDaFirmare, X509Certificate userCert, Hsm clientHsm) throws Exception {

		List<HashRequestBean> listaHashDaFirmare = new ArrayList<HashRequestBean>();

		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		try {
			for (byte[] fileDaFirmare : listaFileDaFirmare) {
				HashRequestBean hashRequestBean = new HashRequestBean();

				MessageDigest md = MessageDigest.getInstance("SHA256");

				byte[] buf = new byte[65664];

				InputStream fis = new ByteArrayInputStream(fileDaFirmare);
				int nRead = 0;
				while ((nRead = fis.read(buf)) > 0) {
					md.update(buf, 0, nRead);
				}
				fis.close();

				byte[] contentDigestMD = md.digest();
				logger.debug("Digest MD: " + new String(contentDigestMD));
				logger.debug("Digest MD BASE64: " + Base64.encodeBase64String(contentDigestMD));

				P7MDetector lP7MDetector = new P7MDetector();
				InputStream streamNonFirmato = lP7MDetector.getContent(new ByteArrayInputStream(fileDaFirmare));
				byte[] byesDaFirmnare = IOUtils.toByteArray(streamNonFirmato);
				byte[] contentDigest = digest(byesDaFirmnare, "2");
				logger.debug("Digest PKBOX: " + new String(contentDigest));
				logger.debug("Digest PKBOX BASE64: " + Base64.encodeBase64String(contentDigest));

				hashRequestBean.setHash(Base64.encodeBase64String(contentDigest));
				hashRequestBean.setSignOption(HsmSignOptionFactory.getSignOption(clientHsm.getHsmConfig().getHsmType()));
				listaHashDaFirmare.add(hashRequestBean);
			}

			SignResponseBean response = clientHsm.firmaMultiplaHash(listaHashDaFirmare);

			MessageBean message = response.getMessage();
			if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
				logger.error("Errore: - " + message.getCode() + " " + message.getDescription());
				throw new HsmClientSignatureException("Errore nella firma: " + message.getDescription());
			}
			List<HashResponseBean> listHashResponseBean = response.getHashResponseBean();
			List<byte[]> listaBytesfirmati = new ArrayList<byte[]>();
			if ((listHashResponseBean != null) && (listHashResponseBean.size() >= 1)) {
				for (int i = 0; i < listHashResponseBean.size(); i++) {
					try {
						logger.debug("dimensione listHashResponseBean: " + listHashResponseBean.size());
						logger.debug("i: " + i);
						logger.debug("listHashResponseBean.get(i): " + listHashResponseBean.get(i));
						logger.debug("listHashResponseBean.get(i).getMessage(): " + listHashResponseBean.get(i).getMessage());
						logger.debug("listHashResponseBean.get(i).getMessage().getStatus(): " + listHashResponseBean.get(i).getMessage().getStatus());
						if (listHashResponseBean.get(i).getMessage().getStatus().equals(ResponseStatus.OK)) {
							byte[] hashFirmata = listHashResponseBean.get(i).getHashFirmata();
							logger.debug("hashFirmata " + new String(hashFirmata));

							byte[] dataBytes = Base64.decodeBase64(hashFirmata);
							logger.debug("hashFirmata decodificata " + new String(dataBytes));

							CMSSignedData s = new CMSSignedData(dataBytes);
							ByteArrayInputStream bis = new ByteArrayInputStream(s.getEncoded());
							// OutputStream outputStream = new FileOutputStream(File.createTempFile("signed", "p7m"));
							ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
							byte[] fileDaFirmare = listaFileDaFirmare.get(i);
							// SignerHashUtil.attachP7M(fileDaFirmare, bis, outputStream);
							SignerHashUtil.addSignerInfo(fileDaFirmare, s, outputStream);

							byte[] bytesfirmati = outputStream.toByteArray();
							logger.debug("bytesfirmati " + new String(bytesfirmati));
							listaBytesfirmati.add(bytesfirmati);
						} else {
							logger.error("Errore nella firma per il file numero " + i + " Errore: " + listHashResponseBean.get(i).getMessage().getDescription());
							throw new HsmClientSignatureException("Errore nella firma: " + listHashResponseBean.get(i).getMessage().getDescription());
						}
					} catch (Exception e) {
						logger.error("Errore nella firma", e);
						throw new HsmClientSignatureException(e.getMessage(), e);
					}
				}
			}
			return listaBytesfirmati;
		} catch (NoSuchAlgorithmException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (UnsupportedOperationException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (HsmClientConfigException e) {
			logger.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}
	}
	
	public static List<byte[]> firmaFileP7mCongiuntaFile(List<byte[]> listaFileDaFirmare, Hsm clientHsm) throws HsmClientSignatureException {
		try {
			SignResponseBean signResponseBean = clientHsm.firmaCades(listaFileDaFirmare, null);
			if ((signResponseBean != null) && (signResponseBean.getMessage() != null)) {
				logger.debug("Response Status: " + signResponseBean.getMessage().getStatus());
				logger.debug("Response Return Code: " + signResponseBean.getMessage().getCode());
				if (signResponseBean.getMessage().getDescription() != null) {
					logger.debug("Response Description: " + signResponseBean.getMessage().getDescription());
				}
				if (signResponseBean.getMessage().getStatus().equals(ResponseStatus.OK)) {
					List<FileResponseBean> listaFileResponseBean = signResponseBean.getFileResponseBean();
					List<byte[]> listaByteFirmati = new ArrayList<>();
					for (FileResponseBean fileResponseBean : listaFileResponseBean) {
						listaByteFirmati.add(fileResponseBean.getFileFirmato());
					}
					return listaByteFirmati;
				} else {
					logger.error("Errore nella firma. Errore: " + signResponseBean.getMessage().getDescription());
					throw new HsmClientSignatureException("Errore nella firma: " + signResponseBean.getMessage().getDescription());
				}
			}
		} catch (HsmClientConfigException e) {
			logger.error("Errore in firma cades congiunta", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (HsmClientSignatureException e) {
			logger.error("Errore in firma cades congiunta", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (UnsupportedOperationException e) {
			logger.error("Errore in firma cades congiunta", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}
		return null;
	}

	public static AttributeTable generateSignedAttributes(byte[] contentDigest, byte[] certDigest) {
		ASN1EncodableVector v = new ASN1EncodableVector();

		Attribute attr = null;

		attr = new Attribute(CMSAttributes.contentType, new DERSet(new DERObjectIdentifier("1.2.840.113549.1.7.1")));
		v.add(attr);

		attr = new Attribute(CMSAttributes.signingTime, new DERSet(new DERUTCTime(new Date())));
		v.add(attr);

		attr = new Attribute(CMSAttributes.messageDigest, new DERSet(new DEROctetString(contentDigest)));
		v.add(attr);

		ESSCertID essCertid = new ESSCertID(certDigest);
		attr = new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificateV2, new DERSet(new SigningCertificate(essCertid)));
		v.add(attr);

		return new AttributeTable(v);
	}
	
	public static byte[] digest( byte[] data, String encoding) {
		
		URL url;
		try {
			url = new URL("https://pkboxrmt.csi.it:8443/pkserver/service/default/Utils?wsdl");
			QName qname = new QName("http://soap.remote.pkserver.it", "Utils");
			Service service = Service.create(url, qname);
			
			it.eng.hsm.client.pkbox.generated.UtilsPortType pkboxService = service.getPort(it.eng.hsm.client.pkbox.generated.UtilsPortType.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) pkboxService).getBinding();
			BindingProvider bindingProvider = (BindingProvider) pkboxService;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://pkboxrmt.csi.it:8443/pkserver/services/Utils?wsdl");
			
			
			int algorithm = 5;
			String environment = "default";
			
			logger.debug("---- Invoco il servizio digest con parametri ");
			logger.debug("environment " + environment );
			logger.debug("data " + data);
			logger.debug("algorithm " + algorithm);
			logger.debug("encoding " + encoding);
			byte[] digest = null;
			boolean esito = false;
			int digestEncoding = Integer.parseInt(encoding);
			try {
				digest = pkboxService.digest(environment, data, algorithm, digestEncoding);
				esito = true;
				logger.debug("Servizio invocato - in test " + new String(digest));
			}catch (PKBoxException_Exception e) {
				logger.debug("ERRORE PKBoxException_Exception", e);
			} catch (Exception e) {
				logger.debug("ERRORE Exception", e);
			}
			
			return digest;
		} catch (MalformedURLException e) {
			logger.debug("ERRORE Exception", e);
		} 
		
		return null;
	}

}
