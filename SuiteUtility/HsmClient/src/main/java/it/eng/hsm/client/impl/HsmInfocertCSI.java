package it.eng.hsm.client.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.sun.istack.ByteArrayDataSource;

import it.doqui.dosign.dosign.business.session.dosign.remotev2.DocumentDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidAuthenticationException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidDataException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidModeException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidOtpException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidPinException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignRemoteService;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.Format;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.Mechanism;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteCertsDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteDigestDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteEndTransactionDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteOtpDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteSignatureDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteStartTransactionDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteTimestampDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.TsFormat;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.Type;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.marca.FileMarcatoResponseBean;
import it.eng.hsm.client.bean.marca.MarcaRequestBean;
import it.eng.hsm.client.bean.marca.MarcaResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.InfocertCSIConfig;
import it.eng.hsm.client.config.ProxyConfig;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;

public class HsmInfocertCSI extends HsmImpl {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public SignResponseBean firmaCades(List<byte[]> listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}
		
		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();
			binding.setMTOMEnabled(true);
			
			RemoteSignatureDto request = new RemoteSignatureDto();
			request.setUsername(infocertCSIConfig.getUser());
			request.setPassword(infocertCSIConfig.getPassword());
			logger.debug("infocertCSIConfig.getOtpPwd() " + infocertCSIConfig.getOtpPwd());
			request.setOtp(infocertCSIConfig.getOtpPwd());
			
			request.setTimestamped(true);
			request.setFormat(Format.CADES);
			request.setProvider(infocertCSIConfig.getLabelProvider());
			
			int index = 1;
			for( byte[] bytes : listaBytesFileDaFirmare) {
				String contentType = "application/octet-stream";
				DataHandler fileStream =  new DataHandler( new ByteArrayDataSource(bytes, contentType) );
				DocumentDto documentDto = new DocumentDto();
				documentDto.setDocumentContent(fileStream);
				documentDto.setDocumentName(""+index + "__");
				index++;
				request.getDocuments().add(documentDto );
				request.setTimestamped(false);
			}
			
			RemoteSignatureDto response;
			try {
				response = infocertService.sign(request );
				if( response.getData()!=null ){
					InputStream in = null;
					try {
						in = response.getData().getInputStream();
						byte[] byteArray=IOUtils.toByteArray(in);
						
						if (listaBytesFileDaFirmare.size() > 1) {
							logger.info("Firma multipla - ricevo in output uno zip" );
							File targetFile = File.createTempFile( "_zip", ".zip");
							OutputStream outStream = new FileOutputStream(targetFile);
							outStream.write(byteArray);
							outStream.close();
							
							InputStream zipStream = new FileInputStream( targetFile );
							Map<Integer, File> mappaFiles = extractZip(zipStream, false, false, false);
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
									fileEstratto.delete();
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
							fileResponseBean.setFileFirmato(byteArray);
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
					} finally {
						try {
							if( in!=null )
							 in.close();
						} catch (IOException e) { }
					}
				} else {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("");
					messageBean.setCode("");
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
					signResponseBean.setMessage(messageBean);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} catch (DosignException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidDataException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidModeException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidOtpException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidPinException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} 
		
		setSignResponseMessage(signResponseBean);
		return signResponseBean;
	}
	
	public SignResponseBean firmaPades(List<byte[]> listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file pades - INIZIO");
		
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}
		
		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();
			binding.setMTOMEnabled(true);
			
			RemoteSignatureDto request = new RemoteSignatureDto();
			request.setUsername(infocertCSIConfig.getUser());
			request.setPassword(infocertCSIConfig.getPassword());
			logger.debug("infocertCSIConfig.getOtpPwd() " + infocertCSIConfig.getOtpPwd());
			request.setOtp(infocertCSIConfig.getOtpPwd());
			
			request.setTimestamped(true);
			request.setFormat(Format.PADES);
			request.setProvider(infocertCSIConfig.getLabelProvider());
			
			int index = 1;
			for( byte[] bytes : listaBytesFileDaFirmare) {
				String contentType = "application/octet-stream";
				DataHandler fileStream =  new DataHandler( new ByteArrayDataSource(bytes, contentType) );
				DocumentDto documentDto = new DocumentDto();
				documentDto.setDocumentContent(fileStream);
				documentDto.setDocumentName(""+index + "__");
				index++;
				request.getDocuments().add(documentDto);
				request.setTimestamped(false);
			}
			
			RemoteSignatureDto response;
			try {
				response = infocertService.sign(request );
				if( response.getData()!=null ){
					InputStream in = null;
					try {
						in = response.getData().getInputStream();
						byte[] byteArray=IOUtils.toByteArray(in);
						
						if (listaBytesFileDaFirmare.size() > 1) {
							logger.info("Firma multipla - ricevo in output uno zip" );
							File targetFile = File.createTempFile( "_zip", ".zip");
							OutputStream outStream = new FileOutputStream(targetFile);
							outStream.write(byteArray);
							outStream.close();
						
							InputStream zipStream = new FileInputStream( targetFile );
							Map<Integer, File> mappaFiles = extractZip(zipStream, true, false, false);
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
									fileEstratto.delete();
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
						}  else {
							// firma singola - output direttamente il firmato
							FileResponseBean fileResponseBean = new FileResponseBean();
							MessageBean messageBean = new MessageBean();
							messageBean.setStatus(ResponseStatus.OK);
							fileResponseBean.setMessage(messageBean);
							fileResponseBean.setFileFirmato(byteArray);
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
					} finally {
						try {
							if( in!=null )
							 in.close();
						} catch (IOException e) { }
					}
				} else {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("");
					messageBean.setCode("");
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
					signResponseBean.setMessage(messageBean);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} catch (DosignException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidDataException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidModeException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidOtpException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidPinException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} 
		
		setSignResponseMessage(signResponseBean);
		return signResponseBean;
	}
	
	
	/*@Override
	public SignResponseBean firmaPades(List<byte[]>  listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.info("Metodo di firma file pades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}

		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		PadesConfig padesConfig = infocertCSIConfig.getPadesConfig();
		if (padesConfig == null) {
			logger.error("Non e' stata specificata la configurazione per la firma pades" + " " + getHsmUserDescription());
			//throw new HsmClientConfigException("Configurazione Pades non specificata");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();

			RemotePdfSignatureDto request = new RemotePdfSignatureDto();
			request.setUsername(infocertCSIConfig.getUser());
			request.setPassword(infocertCSIConfig.getPassword());
			logger.debug("infocertCSIConfig.getOtpPwd() " + infocertCSIConfig.getOtpPwd());
			request.setOtp(infocertCSIConfig.getOtpPwd());
			
			if (padesConfig != null) {
				RemoteGraphicDto graphic = new RemoteGraphicDto();
				graphic.setTesto(padesConfig.getTesto());
				graphic.setPage(Integer.parseInt(padesConfig.getNumPagina()));
				if (padesConfig.getLeftX() != null)
					graphic.setLeftx(Integer.parseInt(padesConfig.getLeftX()));
				if (padesConfig.getLeftY() != null)
					graphic.setLefty(Integer.parseInt(padesConfig.getLeftY()));
				if (padesConfig.getRightX() != null)
					graphic.setRightx(Integer.parseInt(padesConfig.getRightX()));
				if (padesConfig.getRightY() != null)
					graphic.setRighty(Integer.parseInt(padesConfig.getRightY()));
				//graphic.setBShowDateTime(true);
				request.setGraphicDto( graphic );
			}
			
			request.setProvider( infocertCSIConfig.getLabelProvider() );
			
			for( byte[] bytes : listaBytesFileDaFirmare) {
				String contentType = "application/octet-stream";
				DataHandler fileStream =  new DataHandler( new ByteArrayDataSource(bytes, contentType) );
				request.getDocuments().add(fileStream);
				//logger.debug("set data " );
				//request.setData(fileStream);
				request.setTimestamped(false);
			}
			
			RemoteSignatureDto response;
			try {
				response = infocertService.pdfsign(request );
				if (response != null) {
					if (response.getData()==null ) {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("");
						messageBean.setCode("");
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma pades - Descrizione errore: "+getHsmUserDescription());
						signResponseBean.setMessage(messageBean);
						FileResponseBean fileResponseBean = new FileResponseBean();
						fileResponseBean.setMessage(messageBean);
						signResponseBean.getFileResponseBean().add(fileResponseBean);
					} else {
						FileResponseBean fileResponseBean = new FileResponseBean();
						InputStream in = null;
						try {
							in = response.getData().getInputStream();
							
							jj
							byte[] byteArray=IOUtils.toByteArray(in);
							logger.debug("setto il file in response " );
							fileResponseBean.setFileFirmato( byteArray );
							MessageBean messageBean = new MessageBean();
							messageBean.setDescription("");
							messageBean.setCode("");
							messageBean.setStatus(ResponseStatus.OK);
							signResponseBean.setMessage(messageBean);
							fileResponseBean.setMessage(messageBean);
						} catch (IOException e) {
							MessageBean messageBean = new MessageBean();
							messageBean.setDescription("");
							messageBean.setCode("");
							messageBean.setStatus(ResponseStatus.KO);
							logger.error("Errore nella risposta di firma pades -  " + getHsmUserDescription());
							fileResponseBean.setMessage(messageBean);
						} finally {
							try {
								if( in!=null )
								 in.close();
							} catch (IOException e) { }
						}
						signResponseBean.getFileResponseBean().add(fileResponseBean );
					}
				} else {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("Errore nella risposta di firma pades ");
					logger.error("Errore nella risposta di firma pades" + " " + getHsmUserDescription());
					messageBean.setStatus(ResponseStatus.KO);
					signResponseBean.setMessage(messageBean);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} catch (DosignException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma pades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidDataException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma pades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidOtpException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma pades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (DosignInvalidPinException_Exception e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma pades -  " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
				
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} 
		return signResponseBean;
	}*/

	@Override
	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di apertura sessione di firma - INIZIO");
		SessionResponseBean sessionResponseBean = new SessionResponseBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}

		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}
		
		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();

			RemoteStartTransactionDto request = new RemoteStartTransactionDto();
			request.setUsername( infocertCSIConfig.getUser() );
			request.setPassword( infocertCSIConfig.getPassword() );
			request.setOtp( infocertCSIConfig.getOtpPwd() );
			request.setProvider(infocertCSIConfig.getLabelProvider() );
			
			String response;
			try {
				response = infocertService.startTransaction(request);
			
				String sessionId = null;
				if (response == null ) {
					String messageString = "Errore in fase di apertura sessione firma";
					logger.error("Errore in fase di apertura sessione firma " + getHsmUserDescription());
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(messageString);
					messageBean.setStatus(ResponseStatus.KO);
					sessionResponseBean.setMessage(messageBean);
				} else {
					sessionId = response;
					logger.debug("Sessione di firma iniziata - SessionId: " + sessionId);
					sessionResponseBean.setSessionId(sessionId);
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					sessionResponseBean.setMessage(messageBean);
				}
			} catch (DosignException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			} catch (DosignInvalidOtpException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			} catch (DosignInvalidPinException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di apertura sessione di firma ");
		}

		return sessionResponseBean;
	}
	
	@Override
	public MessageBean chiudiSessioneFirmaMultipla(String sessionId) throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di chiusura sessione di firma - INIZIO");
		MessageBean messageBean = new MessageBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI  non specificata");
		}

		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();
			
			RemoteEndTransactionDto request = new RemoteEndTransactionDto();
			request.setUsername(infocertCSIConfig.getUser());
			request.setPassword(infocertCSIConfig.getPassword());
			request.setSessionId(sessionId);
			
			try {
				infocertService.endTransaction(request);
			} catch (DosignException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
			} catch (DosignInvalidOtpException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
			} catch (DosignInvalidPinException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
			}
			logger.info("Sessione di firma terminata");
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di chiusura sessione di firma ");
		}
		return messageBean;

	}
	
	@Override
	public SignResponseBean firmaMultiplaHashInSession(List<HashRequestBean> listaHashDaFirmare, String sessionId)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma multipla hash con sessione - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}

		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();

			RemoteDigestDto  request = new RemoteDigestDto ();
			request.setUsername( infocertCSIConfig.getUser() );
			request.setPassword( infocertCSIConfig.getPassword() );
			request.setMechanism(Mechanism.RSASHA_256);
			if( sessionId!=null){
				request.setSessionId( sessionId );
			} else {
				request.setOtp( infocertCSIConfig.getOtpPwd() );
			}
			request.setProvider( infocertCSIConfig.getLabelProvider() );
			
			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				//byte[] hashDecoded = Base64.decodeBase64(hashDaFirmare.getHash());
				request.setPlaintext(hashDaFirmare.getHash());
				String response = infocertService.digest(request);

				HashResponseBean hashResponseBean = new HashResponseBean();
				if (response != null) {
					logger.debug("Ricevuto hash firmata");
					byte[] signature = Base64.decodeBase64(response)/*.getSignature()*/;
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					signResponseBean.setMessage(messageBean);
					hashResponseBean.setHashFirmata(signature);
				} else {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("Errore nella risposta di firma pades ");
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma pades" + " " + getHsmUserDescription());
					hashResponseBean.setMessage(messageBean);
				}
				signResponseBean.getHashResponseBean().add(hashResponseBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (DosignException_Exception e) {
			e.printStackTrace();
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;
	}
	
	@Override
	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di richiesta certificati utente - INIZIO");
		CertResponseBean certResponseBean = new CertResponseBean();
		List<CertBean> certBeanList = new ArrayList<CertBean>();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}

		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		WSConfig wsCertConfig = infocertCSIConfig.getWsCertConfig();
		if (wsCertConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta certificati" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta certificati non specificata");
		}

		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}


		String wsdlEndpoint = wsCertConfig.getWsdlEndpoint();
		String serviceNS = wsCertConfig.getServiceNS();
		String serviceName = wsCertConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();

			RemoteCertsDto request = new RemoteCertsDto();
			request.setUsername( infocertCSIConfig.getUser() );
			request.setPassword( infocertCSIConfig.getPassword() );
			request.setProvider( infocertCSIConfig.getLabelProvider() );
			request.setType(Type.CERTIFICATE);

			String response = infocertService.certs(request);
			
			if (response != null) {
				CertBean certBean = new CertBean();
				certBean.setCertId("CERT");
				certBean.setCertValue(Base64.decodeBase64(response));
				certBeanList.add(certBean);
				
				certResponseBean.setCertList(certBeanList);
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				certResponseBean.setMessageBean(messageBean);
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di certificati utente");
				logger.error("Errore nella risposta di certificati utente" + " " + getHsmUserDescription());
				certResponseBean.setMessageBean(messageBean);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error(getHsmUserDescription(), e);
		} catch (DosignException_Exception e) {
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription("Errore nella risposta di certificati utente " + e.getMessage());
			logger.error("Errore nella risposta di certificati utente" + " " + e.getMessage());
			certResponseBean.setMessageBean(messageBean);
		}
		return certResponseBean;
	}
	
	@Override
	public MarcaResponseBean aggiungiMarca(File fileDaMarcare, MarcaRequestBean marcaRequestBean) throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		
		MarcaResponseBean marcaResponseBean = new MarcaResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}
		
		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();
			
			RemoteTimestampDto request = new RemoteTimestampDto();
			if( marcaRequestBean!=null && marcaRequestBean.getTipoFirma()!=null && marcaRequestBean.getTipoFirma().equalsIgnoreCase("PDF"))
				request.setFormat(TsFormat.PDF);
			if( marcaRequestBean!=null && marcaRequestBean.getTipoFirma()!=null && marcaRequestBean.getTipoFirma().equalsIgnoreCase("CADES"))
				request.setFormat(TsFormat.P_7_M);
			if( marcaRequestBean!=null && marcaRequestBean.getTipoFirma()!=null && marcaRequestBean.getTipoFirma().equalsIgnoreCase("TSD"))
				request.setFormat(TsFormat.TSD);
			request.setProvider( infocertCSIConfig.getLabelProvider() );
			
			byte[] bytes;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(fileDaMarcare);
				bytes = IOUtils.toByteArray(fis);
				String contentType = "application/octet-stream";
				ByteArrayDataSource bads = new ByteArrayDataSource(bytes, contentType);
				DataHandler fileStream =  new DataHandler( bads );
				request.setData(fileStream);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			} finally {
				if( fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						//e.printStackTrace();
					}
				}
			}
			
			RemoteTimestampDto response;
			try {
				if( request.getData()!=null){
					response = infocertService.createTimeStampedData(request );
					if( response.getData()!=null ){
						InputStream in = null;
						try {
							in = response.getData().getInputStream();
							byte[] byteArray=IOUtils.toByteArray(in);
							MessageBean messageBean = new MessageBean();
							messageBean.setStatus(ResponseStatus.OK);
							FileMarcatoResponseBean fileMarcatoResponseBean = new FileMarcatoResponseBean();
							fileMarcatoResponseBean.setMessageBean(messageBean);
							fileMarcatoResponseBean.setFileMarcato(byteArray);
							marcaResponseBean.setFileMarcatoResponseBean(fileMarcatoResponseBean );
							marcaResponseBean.setMessage(messageBean);
						} catch (IOException e) {
							//e.printStackTrace();
							MessageBean messageBean = new MessageBean();
							messageBean.setDescription("");
							messageBean.setCode("");
							messageBean.setStatus(ResponseStatus.KO);
							marcaResponseBean.setMessage(messageBean);
						} finally {
							try {
								if( in!=null )
								 in.close();
							} catch (IOException e) { }
						}
					} else {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("");
						messageBean.setCode("");
						messageBean.setStatus(ResponseStatus.KO);
						marcaResponseBean.setMessage(messageBean);
					}
				} else {
					logger.error("request non valida");
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("request non valida");
					messageBean.setCode("");
					messageBean.setStatus(ResponseStatus.KO);
					marcaResponseBean.setMessage(messageBean);
				}
			} catch (DosignException_Exception e) {
				//e.printStackTrace();
				logger.error(getHsmUserDescription(), e);
				//throw new HsmClientSignatureException("Errore in fase di firma");
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma marca -  " + e.getMessage());
				marcaResponseBean.setMessage(messageBean);
			} 
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			//throw new HsmClientSignatureException("Errore in fase di firma");
		} 
		return marcaResponseBean;
	}
	
	private Map<Integer,File> extractZip(InputStream fileZip, boolean pades, boolean xades, boolean sigillo) {
		logger.info("Metodo extract pades " + pades + " xades " + xades + " sigillo " + sigillo);
		Map<Integer,File>  filesEstratti = new HashMap<Integer,File> ();
		byte[] buffer = new byte[1024];
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(fileZip);
			ZipEntry zipEntry = zis.getNextEntry();
			int index = 1;
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
								logger.debug(fileName);
								indexFile=""+index;//fileName.substring(fileName.indexOf("dosign_")+7, fileName.indexOf(".p7m"));
								index++;
								//indexFile=fileName.substring(0, fileName.indexOf("__"));
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
							logger.info(fileName);
							indexFile=""+index;//fileName.substring(fileName.indexOf("dosign_")+7, fileName.indexOf(".p7m"));
							index++;
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
	
	@Override
	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo richiediOTP - INIZIO");
		OtpResponseBean otpResponseBean = new OtpResponseBean();

		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfocertCSIConfig)) {
			logger.error("Non e' stata specificata la configurazione per Infocert CSI " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Infocert CSI non specificata");
		}
		
		InfocertCSIConfig infocertCSIConfig = (InfocertCSIConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = infocertCSIConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = infocertCSIConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		URL url;
		try {
			url = new URL(wsdlEndpoint);
			
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService infocertService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) infocertService).getBinding();
			
			RemoteOtpDto request = new RemoteOtpDto();
			request.setUsername(infocertCSIConfig.getUser());
			request.setPassword(infocertCSIConfig.getPassword());
			request.setProvider(infocertCSIConfig.getLabelProvider());
			infocertService.pushOtp(request );
			logger.debug("Otp innviato");
			MessageBean messageBean = new MessageBean();
			messageBean.setStatus(ResponseStatus.OK);
			otpResponseBean.setMessage(messageBean);
		} catch (MalformedURLException e) {
			String messageString = "Errore nella richiesta otp " + e.getMessage();
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di richiesta otp - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			otpResponseBean.setMessage(messageBean);
		} catch (DosignException_Exception e) {
			String messageString = "Errore nella richiesta otp " + e.getMessage();
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di richiesta otp - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			otpResponseBean.setMessage(messageBean);
		} catch (DosignInvalidAuthenticationException_Exception e) {
			String messageString = "Errore nella richiesta otp " + e.getMessage();
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di richiesta otp - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			otpResponseBean.setMessage(messageBean);
		} catch (DosignInvalidDataException_Exception e) {
			String messageString = "Errore nella richiesta otp " + e.getMessage();
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di richiesta otp - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			otpResponseBean.setMessage(messageBean);
		}

		return otpResponseBean;
	}
	
}
