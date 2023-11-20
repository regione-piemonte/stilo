/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SimpleAttributeTableGenerator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.io.RASInputStream;
import com.itextpdf.text.io.RandomAccessSource;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.codec.Base64;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.auriga.ui.module.layout.server.task.bean.InfoFirmaGraficaBean;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.AbstractResponseOperationType.ErrorsMessage;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.InformazioniFirmaGraficaType;
import it.eng.fileOperation.clientws.InputCompletaFirmaPadesType;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputPreparaFirmaPadesType;
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponsePreparaFirmaPadesType;
import it.eng.fileOperation.clientws.VerificationStatusType;
import it.eng.hsm.HsmSignOptionFactory;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.utils.CMSProcessableInputStream;
import it.eng.hsm.utils.CustomContentSigner;
import it.eng.hsm.utils.CustomJcaSignerInfoGeneratorBuilder;
import it.eng.services.fileop.FileOpUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.storageutil.exception.StorageException;

public class FirmaPadesApposizioneFileOpUtil {

	private static Logger log = Logger.getLogger(FirmaPadesApposizioneFileOpUtil.class);
	
	public static byte[] firmaFileHash(FileDaFirmare fileDaFirmareBean, File fileDaFirmare, X509Certificate cert, String firmatario, Hsm hsmClient) throws Exception, StorageException {
		String reason = "";
		String location = "";
		List<InfoFirmaGraficaBean> listaInformazioniFirmaGraficaBean = fileDaFirmareBean.getListaInformazioniFirmaGrafica();
		byte[] bytesFileFirmato = null;
		
		for (int i = 0; i < fileDaFirmareBean.getListaInformazioniFirmaGrafica().size(); i++) {
			InfoFirmaGraficaBean lInformazioniFirmaGraficaBean = listaInformazioniFirmaGraficaBean.get(i);
			String nomeCampoFirma = "Firma " + UUID.randomUUID().toString();
			Integer nroPaginePdf = fileDaFirmareBean.getInfoFile() != null && fileDaFirmareBean.getInfoFile().getNumPaginePdf() != null && fileDaFirmareBean.getInfoFile().getNumPaginePdf() > 0 ? fileDaFirmareBean.getInfoFile().getNumPaginePdf() : PdfUtil.recuperaNumeroPagine(fileDaFirmare);
			
			lInformazioniFirmaGraficaBean.setFirmaInEsecuzione(true);
			
			byte[] byteFileConApposizioneGraficaFirma = FirmaPadesApposizioneFileOpUtil.getByteFileDaFirmare(fileDaFirmare.toURI().toString(), fileDaFirmareBean.getNomeFile(), cert, firmatario, nomeCampoFirma, reason, location, nroPaginePdf, lInformazioniFirmaGraficaBean);
			
			String uriFileConApposizioneGraficaFirma = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(byteFileConApposizioneGraficaFirma));
			File fileConApposizioneGraficaFirma = StorageImplementation.getStorage().getRealFile(uriFileConApposizioneGraficaFirma);
			
			HashRequestBean hashFirmata = FirmaPadesApposizioneFileOpUtil.getHashFirmata(fileConApposizioneGraficaFirma, nomeCampoFirma, cert, hsmClient);
							
			bytesFileFirmato = FirmaPadesApposizioneFileOpUtil.completaFirma(fileConApposizioneGraficaFirma.toURI().toString(), fileDaFirmareBean.getNomeFile(), nomeCampoFirma, hashFirmata.getHash());
			
			lInformazioniFirmaGraficaBean.setFirmaEseguita(true);

			if (i < fileDaFirmareBean.getListaInformazioniFirmaGrafica().size() - 1) {
				String urifileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(bytesFileFirmato));
				fileDaFirmare = StorageImplementation.getStorage().getRealFile(urifileFirmato);
			}
		}
		return bytesFileFirmato;
	}

	public static byte[] getByteFileDaFirmare(String fileDaFirmareUrl, String nomeFile, X509Certificate userCert, String firmatario, String nomeCampoFirma, String reason, String location, Integer nroPaginePdf, InfoFirmaGraficaBean lInfoFirmaGraficaBean) throws Exception {
		InputFileOperationType lInputFileOperationType = FileOpUtility.buildInputFileOperationType(fileDaFirmareUrl, nomeFile);

		Operations operations = new Operations();

		InputPreparaFirmaPadesType lInputPreparaFirmaPadesType = new InputPreparaFirmaPadesType();
		lInputPreparaFirmaPadesType.setLocation(location);
		lInputPreparaFirmaPadesType.setNomeCampoFirma(nomeCampoFirma);
		lInputPreparaFirmaPadesType.setReason(reason);
		if (userCert != null) {
			// Recupero il DataHandler del certificato
			File fileCert = File.createTempFile("cert", ".crt");
			System.out.println(fileCert);
			FileOutputStream os = new FileOutputStream(fileCert);
			os.write(userCert.getEncoded());
			os.close();
			DataHandler fileStreamCert = new DataHandler(new FileDataSource(fileCert));
			lInputPreparaFirmaPadesType.setUserCertificate(fileStreamCert);
		}
		if (StringUtils.isNotBlank(firmatario)) {
			lInputPreparaFirmaPadesType.setFirmatario(firmatario);
		}

		// infoGrafica.setImmagine(imageFile);
		InformazioniFirmaGraficaType lInformazioniFirmaGraficaType = new InformazioniFirmaGraficaType();
		lInformazioniFirmaGraficaType.setTesto(lInfoFirmaGraficaBean.getTesto());
		lInformazioniFirmaGraficaType.setImmagine(lInfoFirmaGraficaBean.getImmagine());
		lInformazioniFirmaGraficaType.setCoordinataXRiquadroFirma(lInfoFirmaGraficaBean.getCoordinataXRiquadroFirma());
		lInformazioniFirmaGraficaType.setCoordinataYRiquadroFirma(lInfoFirmaGraficaBean.getCoordinataYRiquadroFirma());
		lInformazioniFirmaGraficaType.setAreaOrizzontale(lInfoFirmaGraficaBean.getAreaOrizzontale());
		lInformazioniFirmaGraficaType.setAreaVerticale(lInfoFirmaGraficaBean.getAreaVerticale());
		lInformazioniFirmaGraficaType.setAmpiezzaRiquadroFirma(lInfoFirmaGraficaBean.getAmpiezzaRiquadroFirma());
		lInformazioniFirmaGraficaType.setAltezzaRiquadroFirma(lInfoFirmaGraficaBean.getAltezzaRiquadroFirma());
		
		if (nroPaginePdf != null && nroPaginePdf > 0 && lInfoFirmaGraficaBean.getNroPagineFirmeGrafiche() != null && lInfoFirmaGraficaBean.getNroPagineFirmeGrafiche() > 0) {
			// In questo caso ho delle pagine dedicate alla firma, posizionate in testa al file unione
			Integer numeroPaginaAssoluta = lInfoFirmaGraficaBean.getNroPagina();
			if (numeroPaginaAssoluta != null) {
				numeroPaginaAssoluta = numeroPaginaAssoluta >= 0 ? numeroPaginaAssoluta : lInfoFirmaGraficaBean.getNroPagineFirmeGrafiche();
			} else {
				numeroPaginaAssoluta = 0;
			}
			lInformazioniFirmaGraficaType.setPagina(numeroPaginaAssoluta);
		} else if (lInfoFirmaGraficaBean.getNroPagina() != null) {
			// Non ho delle pagine dedicate alla firma. La posiziono nella pagina del file indicata
			lInformazioniFirmaGraficaType.setPagina(lInfoFirmaGraficaBean.getNroPagina() >= 0 ? lInfoFirmaGraficaBean.getNroPagina() : nroPaginePdf);
		} else {
			// Se il numero della pagina è nullo posiziono la firma nella prima pagina del file
			lInformazioniFirmaGraficaType.setPagina(1);
		}
				
		lInputPreparaFirmaPadesType.setInformazioniFirmaGrafica(lInformazioniFirmaGraficaType);

		operations.getOperation().add(lInputPreparaFirmaPadesType);

		FileOperationResponse lFileOperationResponse = FileOpUtility.callFileOperationNoOut(lInputFileOperationType, operations);

		List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults().getFileOperationResult();
		
		for (AbstractResponseOperationType opResult : opResults) {
			if (opResult instanceof ResponsePreparaFirmaPadesType) {
				ResponsePreparaFirmaPadesType responsefirmaType = (ResponsePreparaFirmaPadesType) opResult;
				if (responsefirmaType.getVerificationStatus() != null && (responsefirmaType.getVerificationStatus().equals(VerificationStatusType.KO)
						|| responsefirmaType.getVerificationStatus().equals(VerificationStatusType.ERROR))) {
					ErrorsMessage errorList = responsefirmaType.getErrorsMessage();
					if (errorList != null && errorList.getErrMessage() != null) {
						for (MessageType error : errorList.getErrMessage()) {
							System.out.println(error.getCode() + " " + error.getDescription());
						}
					}
				}
			}
		}

		DataHandler fileResultDataHandler = lFileOperationResponse.getFileoperationResponse().getFileResult();
		if (fileResultDataHandler != null) {
			InputStream in = fileResultDataHandler.getInputStream();
			byte[] byteArray = org.apache.commons.io.IOUtils.toByteArray(in);
			return byteArray;
		} else {
			log.error("Errore in getByteDaFirmare, verificare i log di fileop");
			throw new Exception("Errore nella creazione del file da firmare");
		}
	}

	private static String getEcodedSignedHash(Hsm clientHsm, HashRequestBean hashDaFirmare) throws UnsupportedOperationException, HsmClientConfigException, HsmClientSignatureException {
		SignResponseBean response = null;

		List<HashRequestBean> listaHashDaFirmare = new ArrayList<HashRequestBean>();
		listaHashDaFirmare.add(hashDaFirmare);

		if (clientHsm.getHsmConfig().getClientConfig().isRequireSignatureInSession()) {
			// Eseguo la firma multipla hash in sessione. La sessione è già stata aperta al momento della richiesta dei certificati
			String idSession = clientHsm.getHsmConfig().getClientConfig().getIdSession();
			response = clientHsm.firmaMultiplaHashInSession(listaHashDaFirmare, idSession);
		} else {
			// Eseguo la firma multipla hash normale
			response = clientHsm.firmaMultiplaHash(listaHashDaFirmare);
		}

		MessageBean message = response.getMessage();
		if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
			log.error("Errore: - " + message.getCode() + " " + message.getDescription());
			throw new HsmClientSignatureException("Errore nella firma: " + message.getDescription());
		}
		List<HashResponseBean> listHashResponseBean = response.getHashResponseBean();
		if ((listHashResponseBean != null) && (listHashResponseBean.size() >= 1) && (listHashResponseBean.get(0).getHashFirmata() != null)) {
			return Base64.encodeBytes(listHashResponseBean.get(0).getHashFirmata());
		} else {
			String errorMessage = "Errore nella firma remota";
			if ((message != null) && (message.getDescription() != null)) {
				errorMessage += ". " + message.getDescription();
			}
			if ((listHashResponseBean != null) && (listHashResponseBean.size() >= 1)) {
				for (HashResponseBean bean : response.getHashResponseBean()) {
					if (bean.getMessage() != null) {
						MessageBean messageElemento = bean.getMessage();
						errorMessage += "\n" + "Codice errore: " + messageElemento.getCode() + " Descrizione errore: " + messageElemento.getDescription();
					}
				}
			}
			throw new HsmClientSignatureException(errorMessage);
		}
	}

	public static byte[] completaFirma(String fileDaFirmareUrl, String nomeFile, String nomeCampoFirma, String encodedSignedHash) throws Exception {
		InputFileOperationType lInputFileOperationType = FileOpUtility.buildInputFileOperationType(fileDaFirmareUrl, nomeFile);

		Operations operations = new Operations();

		InputCompletaFirmaPadesType lInputCompletaFirmaPadesType = new InputCompletaFirmaPadesType();
		lInputCompletaFirmaPadesType.setEncodedSignedHash(encodedSignedHash);
		lInputCompletaFirmaPadesType.setNomeCampoFirma(nomeCampoFirma);

		operations.getOperation().add(lInputCompletaFirmaPadesType);

		FileOperationResponse lFileOperationResponse = FileOpUtility.callFileOperationNoOut(lInputFileOperationType, operations);

		List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults().getFileOperationResult();

		for (AbstractResponseOperationType opResult : opResults) {

			if (opResult instanceof ResponsePreparaFirmaPadesType) {
				ResponsePreparaFirmaPadesType responsefirmaType = (ResponsePreparaFirmaPadesType) opResult;
				if (responsefirmaType.getVerificationStatus() != null && (responsefirmaType.getVerificationStatus().equals(VerificationStatusType.KO)
						|| responsefirmaType.getVerificationStatus().equals(VerificationStatusType.ERROR))) {
					ErrorsMessage errorList = responsefirmaType.getErrorsMessage();
					if (errorList != null && errorList.getErrMessage() != null) {
						for (MessageType error : errorList.getErrMessage()) {
							System.out.println(error.getCode() + " " + error.getDescription());
						}
					}
				}
			}
		}

		DataHandler fileResultDataHandler = lFileOperationResponse.getFileoperationResponse().getFileResult();
		if (fileResultDataHandler != null) {
			InputStream in = fileResultDataHandler.getInputStream();
			byte[] byteArray = org.apache.commons.io.IOUtils.toByteArray(in);
			return byteArray;
		} else {
			log.error("Errore in completaFirmaNoOut, verificare i log di fileop");
			throw new Exception("Errore nella crezione del file firmato");
		}
	}

	private static HashRequestBean getHashFirmata(File fileFirmatoEmpty, String nomeCampoFirma, X509Certificate userCert, Hsm clientHsm) throws Exception {
		PdfReader reader = new PdfReader(fileFirmatoEmpty.getAbsolutePath());
		AcroFields af = reader.getAcroFields();
		PdfDictionary v = af.getSignatureDictionary(nomeCampoFirma);
		if (v == null) {
			throw new DocumentException("No field");
		}
		if (!af.signatureCoversWholeDocument(nomeCampoFirma)) {
			throw new DocumentException("Not the last signature");
		}
		
		PdfArray b = v.getAsArray(PdfName.BYTERANGE);
		long[] gaps = b.asLongArray();
		if (b.size() != 4 || gaps[0] != 0) {
			throw new DocumentException("Single exclusion space supported");
		}
		RandomAccessSource readerSource = reader.getSafeFile().createSourceView();
		InputStream rg = new RASInputStream(new RandomAccessSourceFactory().createRanged(readerSource, gaps));
		byte[] hashSigned = signHash(rg, userCert, clientHsm);

		HashRequestBean hashRequestBean = new HashRequestBean();
		hashRequestBean.setHash(Base64.encodeBytes(hashSigned));
		hashRequestBean.setSignOption(HsmSignOptionFactory.getSignOption(clientHsm.getHsmConfig().getHsmType()));
		return hashRequestBean;
	}
	
	
	private static byte[] signHash(InputStream content, X509Certificate userCert, Hsm clientHsm) throws Exception {
		List<Certificate> certList = new ArrayList<>();
		certList.add(userCert);
		Store certs = new JcaCertStore(certList);
		CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
		org.bouncycastle.asn1.x509.Certificate cert = org.bouncycastle.asn1.x509.Certificate.getInstance(ASN1Primitive.fromByteArray(userCert.getEncoded()));

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		int nRead = 0;
		int written = 0;
		byte[] buf = new byte[65664];
		while ((nRead = content.read(buf)) > 0) {
			md.update(buf, 0, nRead);
			written += nRead;
		}
		content.close();
		
		byte[] contentDigest = md.digest();
		byte[] certDigest = md.digest(userCert.getEncoded());
		
		AttributeTable attributeTable = FirmaPadesUtil.generateSignedAttributes(contentDigest, certDigest);

		ContentSigner sha1Signer = new CustomContentSigner(userCert.getEncoded(), "SHA256withRSA", clientHsm);

		SignerInfoGenerator sigInfoGen = new CustomJcaSignerInfoGeneratorBuilder(
				new JcaDigestCalculatorProviderBuilder().build())
				.build(sha1Signer, new X509CertificateHolder(cert));

		SignerInfoGenerator sigInfoGenWithSignedAttr = new SignerInfoGenerator(sigInfoGen, new SimpleAttributeTableGenerator(attributeTable), null);

		gen.addSignerInfoGenerator(sigInfoGenWithSignedAttr/*sigInfoGen*/);
		gen.addCertificates(certs);
		
		CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
		
		CMSSignedData signedData = gen.generate(msg, false);
		byte[] signed = signedData.getEncoded();

		return signed;
	}	
	
}
