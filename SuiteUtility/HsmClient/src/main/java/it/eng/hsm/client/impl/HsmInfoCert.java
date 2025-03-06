package it.eng.hsm.client.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.InfoCertConfig;
import it.eng.hsm.client.config.PadesConfig;
import it.eng.hsm.client.config.RestConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;
import it.eng.hsm.client.util.ConnectionFactory;

public class HsmInfoCert extends HsmImpl {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public SignResponseBean firmaCades(List<byte[]> listaBytesFileDaFirmare, SignOption infoCertOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();

		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}

		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}

		boolean sigillo = (infoCertOption != null) && infoCertOption.isSigillo();
		String urlEndpoint = restConfig.getUrlEndpoint();

		String alias = infoCertConfig.getAlias();
		if (urlEndpoint == null || alias == null) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}

		DefaultClientConfig cc = new DefaultClientConfig();

		Client client = null;
		if (infoCertConfig.getProxyConfig() != null && infoCertConfig.getProxyConfig().isUseProxy()) {
			URLConnectionClientHandler ch = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}

		String path = "";
		if (sigillo) {
			path = "/seal/sign/cades/" + alias;
		} else {
			if (infoCertConfig.isAuto()) {
				path = "/auto/sign/cades/" + alias;
			} else {
				path = "/remote/sign/cades/" + alias;
			}
		}
		logger.debug("Endpoint " + urlEndpoint + path);

		WebResource webResource = client.resource(urlEndpoint).path(path);

		FormDataMultiPart multi = new FormDataMultiPart();
		multi = multi.field("pin", infoCertConfig.getPin());
		if (!infoCertConfig.isAuto()) {
			logger.debug("Si tratta di una firma remota ");
			multi = multi.field("otp", infoCertConfig.getOtp());
		}

		int index = 1;
		for (byte[] bytesFileDaFirmare : listaBytesFileDaFirmare) {
			FormDataBodyPart bodyPart = new FormDataBodyPart("contentToSign-" + index, new ByteArrayInputStream(bytesFileDaFirmare), MediaType.APPLICATION_OCTET_STREAM_TYPE);
			// FileDataBodyPart bodyPart = new FileDataBodyPart("contentToSign-0",
			// file,
			// MediaType.APPLICATION_OCTET_STREAM_TYPE);
			logger.info("Aggiungo alla request lo stream del file con indice " + index );
			bodyPart.setContentDisposition(FormDataContentDisposition.name("contentToSign-"+index).fileName(""+index + "__").build());
			if (bodyPart != null) {
				multi.bodyPart(bodyPart);
				index++;
			}
		}

		Builder restBuilder = webResource.acceptLanguage(Locale.ITALIAN).type(MediaType.MULTIPART_FORM_DATA_TYPE);
		logger.info("Chiamo il servizio di firma " );
		ClientResponse response = restBuilder.post(ClientResponse.class, multi);
		logger.info("response.getStatus() " + response.getStatus());

		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma cades - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			signResponseBean.setMessage(messageBean);
			for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
		} else {
			InputStream output = response.getEntity(InputStream.class);
			try {
				if (listaBytesFileDaFirmare.size() > 1) {
					logger.info("Firma multipla - ricevo in output uno zip" );
					File targetFile = File.createTempFile( "_zip", ".zip");
					OutputStream outStream = new FileOutputStream(targetFile);
					logger.info("Salvo lo zip " + targetFile);
					outStream.write(IOUtils.toByteArray(output));
					outStream.close();
					
					// firma multipla - output zip da spacchettare
					Map<Integer, File> mappaFiles = extractZip(/*output*/new FileInputStream( targetFile ), false, false, sigillo);
					if (mappaFiles != null && !mappaFiles.isEmpty()) {
						List<File> fileEstratti = new ArrayList<File>();
				    	for(int indexFile=1; indexFile<=listaBytesFileDaFirmare.size();indexFile++){
				    		logger.info("Aggiungo il file " + indexFile + " " + mappaFiles.get(indexFile) );
				    		fileEstratti.add(mappaFiles.get(indexFile));
				    	}
						
						for (File fileEstratto : fileEstratti) {
							FileResponseBean fileResponseBean = new FileResponseBean();
							MessageBean messageBean = new MessageBean();
							messageBean.setStatus(ResponseStatus.OK);
							fileResponseBean.setMessage(messageBean);
							logger.info("Restituisco il file " + fileEstratto );
							FileInputStream fsEstratto = new FileInputStream(fileEstratto);
							fileResponseBean.setFileFirmato(IOUtils.toByteArray(fsEstratto));
							signResponseBean.getFileResponseBean().add(fileResponseBean);
							fsEstratto.close();
							//fileEstratto.delete();
						}
					} else {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("Errore nel recupero dei file firmati");
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma cades - Descrizione errore: " + "Errore nel recupero dei file firmati" + " " + getHsmUserDescription());
						for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
							FileResponseBean fileResponseBean = new FileResponseBean();
							fileResponseBean.setMessage(messageBean);
							signResponseBean.getFileResponseBean().add(fileResponseBean);
						}
					}
				} else {
					logger.info("Firma singola - ricevo in output un file firmato" );
					// firma singola - output direttamente il firmato
					FileResponseBean fileResponseBean = new FileResponseBean();
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					fileResponseBean.setMessage(messageBean);
					fileResponseBean.setFileFirmato(IOUtils.toByteArray(output));
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}

			} catch (IOException e) {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
		}
		setSignResponseMessage(signResponseBean);
		return signResponseBean;
		// throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaPades(List<byte[]> listaBytesFileDaFirmare, SignOption infoCertOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file pades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();

		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}

		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}

		boolean sigillo = (infoCertOption != null) && infoCertOption.isSigillo();
		String urlEndpoint = restConfig.getUrlEndpoint();

		String alias = infoCertConfig.getAlias();
		logger.debug("urlEndpoint: " + urlEndpoint + " alias: " + alias);
		if (urlEndpoint == null || alias == null) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}

		PadesConfig padesConfig = infoCertConfig.getPadesConfig();
//		if (padesConfig == null) {
//			logger.error("Non e' stata specificata la configurazione per la firma pades" + " " + getHsmUserDescription());
//			throw new HsmClientConfigException("Configurazione Pades non specificata");
//		}

		DefaultClientConfig cc = new DefaultClientConfig();
		// cc.getClasses().add(StringProvider.class);/////////
		// cc.getClasses().add(InputStreamProvider.class);////
		// cc.getClasses().add(MultiPartWriter.class);////////
		Client client = null;
		if (infoCertConfig.getProxyConfig() != null && infoCertConfig.getProxyConfig().isUseProxy()) {
			URLConnectionClientHandler ch = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}

		String path = "";
		if (sigillo) {
			path = "/seal/sign/pades/" + alias;
		} else {
			if (infoCertConfig.isAuto()) {
				path = "/auto/sign/pades/" + alias;
			} else {
				path = "/remote/sign/pades/" + alias;
			}
		}
		logger.debug("Endpoint " + urlEndpoint + path);

		WebResource webResource = client.resource(urlEndpoint).path(path);

		FormDataMultiPart multi = new FormDataMultiPart();
		multi = multi.field("pin", infoCertConfig.getPin());
		if (!infoCertConfig.isAuto()) {
			logger.debug("Si tratta di una firma remota ");
			multi = multi.field("otp", infoCertConfig.getOtp());
		}

		int index = 1;
		for (byte[] bytesFileDaFirmare : listaBytesFileDaFirmare) {
			FormDataBodyPart bodyPart = new FormDataBodyPart("contentToSign-" + index, new ByteArrayInputStream(bytesFileDaFirmare),
					MediaType.APPLICATION_OCTET_STREAM_TYPE);
			// FileDataBodyPart bodyPart = new FileDataBodyPart("contentToSign-0",
			// file,
			// MediaType.APPLICATION_OCTET_STREAM_TYPE);
			logger.info("Aggiungo alla request lo stream del file con indice " + index );
			bodyPart.setContentDisposition(
                FormDataContentDisposition.name("contentToSign-"+index)
                        .fileName(""+index + "__").build());
			if (bodyPart != null) {
				multi.bodyPart(bodyPart);
			}
			index++;
		}

		if (padesConfig != null) {
			multi = multi.field("box_signature_page", padesConfig.getNumPagina());
			multi = multi.field("box_signature_llx", padesConfig.getLeftX());
			multi = multi.field("box_signature_lly", padesConfig.getLeftY());
			multi = multi.field("box_signature_urx", padesConfig.getRightX());
			multi = multi.field("box_signature_ury", padesConfig.getRightY());
			multi = multi.field("box_signature_reason", padesConfig.getReason());
			multi = multi.field("box_signature_lbl_reason", padesConfig.getReason());
		}

		Builder restBuilder = webResource.acceptLanguage(Locale.ITALIAN).type(MediaType.MULTIPART_FORM_DATA);
		logger.info("Chiamo il servizio di firma " );
		ClientResponse response = restBuilder.post(ClientResponse.class, multi);

		logger.info("response.getStatus() " + response.getStatus());
		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma pades - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			signResponseBean.setMessage(messageBean);
			for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
		} else {
			InputStream output = response.getEntity(InputStream.class);
			try {
				if (listaBytesFileDaFirmare.size() > 1) {
					logger.info("Firma multipla - ricevo in output uno zip" );
					// firma multipla - output zip da spacchettare
					File targetFile = File.createTempFile( "_zip", ".zip");
					OutputStream outStream = new FileOutputStream(targetFile);
					logger.info("Salvo lo zip " + targetFile);
					outStream.write(IOUtils.toByteArray(output));
					outStream.close();
					
					Map<Integer, File> mappaFiles = extractZip(/*output*/new FileInputStream( targetFile ), true, false, sigillo);
					if (mappaFiles != null && !mappaFiles.isEmpty()) {
						List<File> fileEstratti = new ArrayList<File>();
				    	for(int indexFile=1; indexFile<=listaBytesFileDaFirmare.size();indexFile++){
				    		logger.info("Aggiungo il file " + indexFile + " " + mappaFiles.get(indexFile) );
				    		fileEstratti.add(mappaFiles.get(indexFile));
				    	}
						for (File fileEstratto : fileEstratti) {
							FileResponseBean fileResponseBean = new FileResponseBean();
							MessageBean messageBean = new MessageBean();
							messageBean.setStatus(ResponseStatus.OK);
							fileResponseBean.setMessage(messageBean);
							logger.info("Restituisco il file " + fileEstratto );
							FileInputStream fsEstratto = new FileInputStream(fileEstratto);
							fileResponseBean.setFileFirmato(IOUtils.toByteArray(fsEstratto));
							signResponseBean.getFileResponseBean().add(fileResponseBean);
							fsEstratto.close();
							//fileEstratto.delete();
						}
					} else {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("Errore nel recupero dei file firmati");
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma pades - Descrizione errore: " + "Errore nel recupero dei file firmati" + " " + getHsmUserDescription());
						for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
							FileResponseBean fileResponseBean = new FileResponseBean();
							fileResponseBean.setMessage(messageBean);
							signResponseBean.getFileResponseBean().add(fileResponseBean);
						}
					}
				} else {
					// firma singola - output direttamente il firmato
					FileResponseBean fileResponseBean = new FileResponseBean();
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					fileResponseBean.setMessage(messageBean);
					fileResponseBean.setFileFirmato(IOUtils.toByteArray(output));
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}

			} catch (IOException e) {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma pades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
		}
		setSignResponseMessage(signResponseBean);
		return signResponseBean;
	}

	@Override
	public SignResponseBean firmaCadesParallela(List<byte[]> bytesFileDaFirmare, SignOption infoCertOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaXades(List<byte[]> listaBytesFileDaFirmare, SignOption infoCertOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file xades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();

		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}

		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}

		boolean sigillo = (infoCertOption != null) && infoCertOption.isSigillo();
		
		logger.debug("sigillo: " + sigillo);
		boolean enveloping = infoCertOption.isEnveloping();
		logger.debug("enveloping: " + enveloping);
		boolean detached = infoCertOption.isDetached();
		logger.debug("detached: " + detached);

		String urlEndpoint = restConfig.getUrlEndpoint();

		String alias = infoCertConfig.getAlias();
		logger.debug("urlEndpoint: " + urlEndpoint + " alias: " + alias);
		if (urlEndpoint == null || alias == null) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}

		DefaultClientConfig cc = new DefaultClientConfig();
		// cc.getClasses().add(StringProvider.class);/////////
		// cc.getClasses().add(InputStreamProvider.class);////
		// cc.getClasses().add(MultiPartWriter.class);////////
		Client client = null;
		if (infoCertConfig.getProxyConfig() != null && infoCertConfig.getProxyConfig().isUseProxy()) {
			URLConnectionClientHandler ch = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}

		String path = "";
		if (sigillo) {
			if (enveloping) {
				path = "/seal/sign/xades-enveloping/" + alias;
			} else if (detached) {
				path = "/seal/sign/xades-detached/" + alias;
			} else {
				path = "/seal/sign/xades-enveloped/" + alias;
			}
		} else {
			if (infoCertConfig.isAuto()) {
				if (enveloping) {
					path = "/auto/sign/xades-enveloping/" + alias;
				} else if (detached) {
					path = "/auto/sign/xades-detached/" + alias;
				} else {
					path = "/auto/sign/xades-enveloped/" + alias;
				}
			} else {
				if (enveloping) {
					path = "/remote/sign/xades-enveloping/" + alias;
				} else if (detached) {
					path = "/remote/sign/xades-detached/" + alias;
				} else {
					path = "/remote/sign/xades-enveloped/" + alias;
				}
			}
		}
		logger.debug("Endpoint " + urlEndpoint + path);

		WebResource webResource = client.resource(urlEndpoint).path(path);

		FormDataMultiPart multi = new FormDataMultiPart();
		multi = multi.field("pin", infoCertConfig.getPin());
		if (!infoCertConfig.isAuto() && !sigillo) {
			multi = multi.field("otp", infoCertConfig.getOtp());
		}

		int index = 1;
		for (byte[] bytesFileDaFirmare : listaBytesFileDaFirmare) {
			FormDataBodyPart bodyPart = new FormDataBodyPart("contentToSign-" + index, new ByteArrayInputStream(bytesFileDaFirmare),
					MediaType.APPLICATION_OCTET_STREAM_TYPE);
			// FileDataBodyPart bodyPart = new FileDataBodyPart("contentToSign-0",
			// file,
			// MediaType.APPLICATION_OCTET_STREAM_TYPE);
			logger.debug("Aggiungo il file con indice " + index );
			bodyPart.setContentDisposition(
                FormDataContentDisposition.name("contentToSign-"+index)
                        .fileName(""+index + "__").build());
			
			if (bodyPart != null) {
				multi.bodyPart(bodyPart);
			}
			index++;
		}

		Builder restBuilder = webResource.acceptLanguage(Locale.ITALIAN).type(MediaType.MULTIPART_FORM_DATA);
		ClientResponse response = restBuilder.post(ClientResponse.class, multi);

		logger.info("response.getStatus() " + response.getStatus());

		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma xades - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			signResponseBean.setMessage(messageBean);
			for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
		} else {
			InputStream output = response.getEntity(InputStream.class);
			try {
				if (listaBytesFileDaFirmare.size() > 1) {
					// firma multipla - output zip da spacchettare
					Map<Integer, File> mappaFiles = extractZip(output, false, true, sigillo);
					if (mappaFiles != null && !mappaFiles.isEmpty()) {
						List<File> fileEstratti = new ArrayList<File>();
				    	for(int indexFile=1; indexFile<=listaBytesFileDaFirmare.size();indexFile++){
				    		fileEstratti.add(mappaFiles.get(indexFile));
				    	}
						for (File fileEstratto : fileEstratti) {
							FileResponseBean fileResponseBean = new FileResponseBean();
							MessageBean messageBean = new MessageBean();
							messageBean.setStatus(ResponseStatus.OK);
							fileResponseBean.setMessage(messageBean);
							FileInputStream fsEstratto = new FileInputStream(fileEstratto);
							fileResponseBean.setFileFirmato(IOUtils.toByteArray(fsEstratto));
							signResponseBean.getFileResponseBean().add(fileResponseBean);
							fsEstratto.close();
							fileEstratto.delete();
						}
					} else {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("Errore nel recupero dei file firmati");
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma xades - Descrizione errore: " + "Errore nel recupero dei file firmati" + " " + getHsmUserDescription());
						for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
							FileResponseBean fileResponseBean = new FileResponseBean();
							fileResponseBean.setMessage(messageBean);
							signResponseBean.getFileResponseBean().add(fileResponseBean);
						}
					}
				} else {
					// firma singola - output direttamente il firmato
					FileResponseBean fileResponseBean = new FileResponseBean();
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					fileResponseBean.setMessage(messageBean);
					fileResponseBean.setFileFirmato(IOUtils.toByteArray(output));
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} catch (IOException e) {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma xades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
		}
		setSignResponseMessage(signResponseBean);
		return signResponseBean;
		// throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaMultiplaHash(List<HashRequestBean> listaHashDaFirmare) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		OtpResponseBean otpResponseBean = new OtpResponseBean();

		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}

		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di richiesta otp" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di richiesta otp non specificata");
		}

		String urlEndpoint = restConfig.getUrlEndpoint();
		String alias = infoCertConfig.getAlias();
		logger.debug("urlEndpoint: " + urlEndpoint + " alias: " + alias);
		if (urlEndpoint == null || alias == null) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}

		DefaultClientConfig cc = new DefaultClientConfig();

		Client client = null;
		if (infoCertConfig.getProxyConfig() != null && infoCertConfig.getProxyConfig().isUseProxy()) {
			URLConnectionClientHandler ch = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}
		logger.debug("Endpoint " + urlEndpoint + "/request-otp/" + alias);

		WebResource webResource = client.resource(urlEndpoint).path("/remote/request-otp/" + alias);

		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di richiesta otp - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			otpResponseBean.setMessage(messageBean);
		} else {
			logger.debug("Otp innviato");
			MessageBean messageBean = new MessageBean();
			messageBean.setStatus(ResponseStatus.OK);
			otpResponseBean.setMessage(messageBean);
		}
		return otpResponseBean;
	}

	@Override
	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaMultiplaHashInSession(List<HashRequestBean> listaHashDaFirmare, String sessionId) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public MessageBean chiudiSessioneFirmaMultipla(String sessionId) throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	private Map<Integer,File> extractZip(InputStream fileZip, boolean pades, boolean xades, boolean sigillo) {
		logger.info("Metodo extract pades " + pades + " xades " + xades + " sigillo " + sigillo);
		Map<Integer,File>  filesEstratti = new HashMap<Integer,File> ();
		byte[] buffer = new byte[1024];
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(fileZip);
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				String fileName = zipEntry.getName();
				logger.info("fileName " + fileName );
				if (zipEntry.isDirectory()) {

				} else {
					File fileTmp = File.createTempFile(fileName + "_extract", ".tmp");
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(fileTmp);
						int len;
						while ((len = zis.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
						fos.close();
						String indexFile = "";
						if( pades ){
							if( sigillo ) {
								//indexFile=fileName.substring(0, fileName.indexOf("-signed.pdf"));
								indexFile=fileName.substring(0, fileName.indexOf("__"));
							} else { 
								//indexFile=fileName;
								indexFile=fileName.substring(0, fileName.indexOf("__"));
							}
						} else if( xades ){
							if( sigillo ) {
								//indexFile=fileName.substring(0, fileName.indexOf("-signed.xml"));
								indexFile=fileName.substring(0, fileName.indexOf("__"));
							} else { 
								//indexFile=fileName;
								indexFile=fileName.substring(0, fileName.indexOf("__"));
							}
						}else {
							//indexFile=fileName.substring(0, fileName.indexOf(".p7m"));
							indexFile=fileName.substring(0, fileName.indexOf("__"));
						}
						logger.info("Estraggo il file con indice " + indexFile + " = " + fileTmp );
						filesEstratti.put(Integer.parseInt(indexFile), fileTmp);
					} catch (Exception e) {

					}
				}
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();

		} catch (FileNotFoundException e) {
			filesEstratti = null;
		} catch (IOException e) {
			filesEstratti = null;
		} finally {
			try {
				if (zis != null)
					zis.close();
			} catch (IOException e) {

			}
		}
		logger.info("filesEstratti " + filesEstratti);
		return filesEstratti;
	}
}
