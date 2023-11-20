/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.FileOperationRequest;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.FileOperationWS;
import it.eng.fileOperation.clientws.InputFile;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputFormatRecognitionType;
import it.eng.fileOperation.clientws.InputSigVerifyType;
import it.eng.fileOperation.clientws.InputSigVerifyType.SignatureVerify;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponseFormatRecognitionType;
import it.eng.fileOperation.clientws.ResponseSigVerify;
import it.eng.fileOperation.clientws.VerificationStatusType;

/**
 * classe di utilità per il riconoscimento del mimetype di un file e per l'acquisizione del nuovo nome
 * 
 * @author jravagnan
 * 
 */
public class FileUtilities {

	private static Logger log = LogManager.getLogger(FileUtilities.class);

	private String wsEndpoint;

	/**
	 * ricava le informazioni(mimetype, newName, firma o meno) di un file
	 * 
	 * @param file
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	public EnhancedFile retrieveFileInfos(File file) throws AurigaMailBusinessException {
		return retrieveFileInfos(file, file.getName());
	}

	public EnhancedFile retrieveFileInfos(File file, String originalName) throws AurigaMailBusinessException {
		EnhancedFile ef = new EnhancedFile();
		String mimeType = null;
		String newName = null;
		boolean fileFirmato = false;
		try {
			ef.setFile(file);
			ef.setOriginalFileName(originalName);

			URL url = new URL(wsEndpoint);
			QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fooService = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fooService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			InputSigVerifyType signver = new InputSigVerifyType();
			InputFormatRecognitionType format = new InputFormatRecognitionType();
			signver.setRecursive(false);
			signver.setChildValidation(false);
			SignatureVerify verify = new SignatureVerify();
			verify.setCAReliability(false);
			verify.setCRLCheck(false);
			verify.setDetectCode(false);
			signver.setSignatureVerify(verify);
			Operations ops = new Operations();
			ops.getOperation().add(format);
			ops.getOperation().add(signver);

			request.setOperations(ops);

			InputFile inputFile = new InputFile();
			inputFile.setFileUrl(file.toURI().toString());
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			fileOp.setOriginalName(originalName);
			request.setFileOperationInput(fileOp);
			FileOperationResponse resp = fooService.execute(request);
			if (resp != null && resp.getFileoperationResponse() != null && resp.getFileoperationResponse().getFileOperationResults() != null
					&& resp.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
				List<AbstractResponseOperationType> listaRes = resp.getFileoperationResponse().getFileOperationResults().getFileOperationResult();

				for (AbstractResponseOperationType type : listaRes) {
					if (type instanceof ResponseFormatRecognitionType) {
						if (type.getVerificationStatus() == VerificationStatusType.OK) {
							mimeType = ((ResponseFormatRecognitionType) type).getMimeType();
							newName = ((ResponseFormatRecognitionType) type).getNewFileName();
						}
					}
					if (type instanceof ResponseSigVerify) {
						if (((ResponseSigVerify) type).getSigVerifyResult().getMessage() != null
								&& ((ResponseSigVerify) type).getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED")) {
							fileFirmato = false;
						} else {
							fileFirmato = true;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Impossibile ottenere informazioni ulteriori sul file :" + file.getName(), e);
			throw new AurigaMailBusinessException("Impossibile ottenere informazioni ulteriori sul file :" + file.getName(), e);
		}
		ef.setMimeType(mimeType);
		ef.setNewName(newName);
		ef.setIsFirmato(fileFirmato);
		return ef;
	}

	public EnhancedFile retrieveFileInfosFromRealFile(File file, String originalName) throws AurigaMailBusinessException {
		EnhancedFile ef = new EnhancedFile();
		String mimeType = null;
		String newName = null;
		boolean fileFirmato = false;
		try {
			ef.setFile(file);
			ef.setOriginalFileName(originalName);
			String path = file.toURI().toString();
			URL url = new URL(wsEndpoint);
			QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fooService = service.getPort(FileOperationWS.class);

			// enable mtom on client
			((BindingProvider) fooService).getRequestContext();
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fooService).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest request = new FileOperationRequest();

			InputSigVerifyType signver = new InputSigVerifyType();
			InputFormatRecognitionType format = new InputFormatRecognitionType();
			signver.setRecursive(false);
			signver.setChildValidation(false);
			SignatureVerify verify = new SignatureVerify();
			verify.setCAReliability(false);
			verify.setCRLCheck(false);
			verify.setDetectCode(false);
			signver.setSignatureVerify(verify);
			Operations ops = new Operations();
			ops.getOperation().add(format);
			ops.getOperation().add(signver);

			request.setOperations(ops);

			// DataHandler fileStream = new DataHandler( new FileDataSource( file ) );
			InputFile inputFile = new InputFile();
			// URI lUri = new URI(path);
			inputFile.setFileUrl(path);
			// inputFile.setFileStream( fileStream );
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			fileOp.setOriginalName(originalName);
			request.setFileOperationInput(fileOp);
			FileOperationResponse resp = fooService.execute(request);
			if (resp != null && resp.getFileoperationResponse() != null && resp.getFileoperationResponse().getFileOperationResults() != null
					&& resp.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
				List<AbstractResponseOperationType> listaRes = resp.getFileoperationResponse().getFileOperationResults().getFileOperationResult();

				for (AbstractResponseOperationType type : listaRes) {
					if (type instanceof ResponseFormatRecognitionType) {
						if (type.getVerificationStatus() == VerificationStatusType.OK) {
							mimeType = ((ResponseFormatRecognitionType) type).getMimeType();
							newName = ((ResponseFormatRecognitionType) type).getNewFileName();
						}
					}
					if (type instanceof ResponseSigVerify) {
						if (((ResponseSigVerify) type).getSigVerifyResult().getMessage() != null
								&& ((ResponseSigVerify) type).getSigVerifyResult().getMessage().getCode().equals("SIGN_OP.FILENOTSIGNED")) {
							fileFirmato = false;
						} else {
							fileFirmato = true;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Impossibile ottenere informazioni ulteriori sul file :" + file.getName(), e);
			throw new AurigaMailBusinessException("Impossibile ottenere informazioni ulteriori sul file :" + file.getName(), e);
		}
		ef.setMimeType(mimeType);
		ef.setNewName(newName);
		ef.setIsFirmato(fileFirmato);
		return ef;
	}

	public static String calcolaImprontaWithoutFileOp(File file, String algoritmo, String encoding) throws Exception {
		FileInputStream fileStream = null;
		try {
			byte[] hash = null;
			if (StringUtils.isBlank(algoritmo)) {
				throw new Exception("Algoritmo per il calcolo dell'impronta non valorizzato");
			} else if (algoritmo.equalsIgnoreCase("SHA-256")) {
				fileStream = new FileInputStream(file);
				hash = DigestUtils.sha256(fileStream);
			} else if (algoritmo.equalsIgnoreCase("SHA-1")) {
				fileStream = new FileInputStream(file);
				hash = DigestUtils.sha(fileStream);
			} else {
				throw new Exception("L'algoritmo " + algoritmo + " non è uno di quelli previsti");
			}
			String encodedHash = null;
			if (StringUtils.isBlank(encoding)) {
				throw new Exception("Encoding per il calcolo dell'impronta non valorizzato");
			} else if (encoding.equalsIgnoreCase("base64")) {
				encodedHash = Base64.encodeBase64String(hash);
			} else if (encoding.equalsIgnoreCase("hex")) {
				encodedHash = Hex.encodeHexString(hash);
			} else {
				throw new Exception("L'encoding " + encoding + " non è uno di quelli previsti");
			}
			return encodedHash;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore durante il calcolo dell'impronta", e);
		} finally {
			if (fileStream != null) {
				try {
					fileStream.close();
				} catch (Exception e) {
					log.warn("Errore nella chiusura dello stream", e);
				}
			}
		}
	}

	public String getWsEndpoint() {
		return wsEndpoint;
	}

	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}
}
