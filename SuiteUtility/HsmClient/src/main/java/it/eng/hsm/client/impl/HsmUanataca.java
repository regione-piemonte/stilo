package it.eng.hsm.client.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.sun.istack.ByteArrayDataSource;

import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidDataException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidModeException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidOtpException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignInvalidPinException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.DosignRemoteService;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.Env;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.Format;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.Mechanism;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteCertsDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteDigestDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteEndTransactionDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemoteGraphicDto;
import it.doqui.dosign.dosign.business.session.dosign.remotev2.RemotePdfSignatureDto;
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
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.PadesConfig;
import it.eng.hsm.client.config.ProxyConfig;
import it.eng.hsm.client.config.UanatacaConfig;
import it.eng.hsm.client.config.UanatacaEnvironment;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;

public class HsmUanataca extends HsmImpl {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public SignResponseBean firmaCades(List<byte[]> listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		System.out.println("qui");
		
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}
		
		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = uanatacaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = uanatacaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		System.out.println("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();
			
			RemoteSignatureDto request = new RemoteSignatureDto();
			request.setUsername(uanatacaConfig.getUser());
			request.setPassword(uanatacaConfig.getPassword());
			request.setOtp(uanatacaConfig.getOtpPwd());
			request.setPin(uanatacaConfig.getPin());
			
			request.setTimestamped(true);
			request.setFormat(Format.CADES);
			request.setProvider("UANATACA");
			System.out.println("getEnvironment " + uanatacaConfig.getEnvironment());
			UanatacaEnvironment uanatacaEnvironment = UanatacaEnvironment.valueOf( uanatacaConfig.getEnvironment() );
			System.out.println("uanatacaEnvironment " + uanatacaEnvironment);
			
			if( uanatacaEnvironment!=null && uanatacaEnvironment.equals( UanatacaEnvironment.BOX ))
				request.setEnv( Env.BOX );
			else
				request.setEnv( Env.CLOUD );
			System.out.println("request " + request.getEnv());
			
			for( byte[] bytes : listaBytesFileDaFirmare) {
				String contentType = "application/octet-stream";
				DataHandler fileStream =  new DataHandler( new ByteArrayDataSource(bytes, contentType) );
				request.setData(fileStream);
				
				System.out.println("setta marca true");
				request.setTimestamped(true);
			
				RemoteSignatureDto response;
				try {
					response = uanatacaService.sign(request );
					if( response.getData()!=null ){
						FileResponseBean fileResponseBean = new FileResponseBean();
						InputStream in = null;
						try {
							in = response.getData().getInputStream();
							byte[] byteArray=IOUtils.toByteArray(in);
							fileResponseBean.setFileFirmato( byteArray );
						} catch (IOException e) {
							MessageBean messageBean = new MessageBean();
							messageBean.setDescription("");
							messageBean.setCode("");
							messageBean.setStatus(ResponseStatus.KO);
							logger.error("Errore nella risposta di firma cades -  " + getHsmUserDescription());
							fileResponseBean.setMessage(messageBean);
						} finally {
							try {
								if( in!=null )
								 in.close();
							} catch (IOException e) { }
						}
						signResponseBean.getFileResponseBean().add(fileResponseBean );
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
			}
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} 
		return signResponseBean;
	}
	
	//@Override
	public SignResponseBean firmaPades1(List<byte[]> listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file pades - INIZIO");
		System.out.println("qui");
		
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}
		
		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = uanatacaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = uanatacaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		System.out.println("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();
			
			RemoteSignatureDto request = new RemoteSignatureDto();
			request.setUsername(uanatacaConfig.getUser());
			request.setPassword(uanatacaConfig.getPassword());
			request.setOtp(uanatacaConfig.getOtpPwd());
			request.setPin(uanatacaConfig.getPin());
			
			request.setTimestamped(true);
			request.setFormat(Format.PADES);
			request.setProvider("UANATACA");
			System.out.println("getEnvironment " + uanatacaConfig.getEnvironment());
			UanatacaEnvironment uanatacaEnvironment = UanatacaEnvironment.valueOf( uanatacaConfig.getEnvironment() );
			System.out.println("uanatacaEnvironment " + uanatacaEnvironment);
			
			if( uanatacaEnvironment!=null && uanatacaEnvironment.equals( UanatacaEnvironment.BOX ))
				request.setEnv( Env.BOX );
			else
				request.setEnv( Env.CLOUD );
			System.out.println("request " + request.getEnv());
			
			for( byte[] bytes : listaBytesFileDaFirmare) {
				String contentType = "application/octet-stream";
				DataHandler fileStream =  new DataHandler( new ByteArrayDataSource(bytes, contentType) );
				request.setData(fileStream);
			
				RemoteSignatureDto response;
				try {
					response = uanatacaService.sign(request );
					if( response.getData()!=null ){
						FileResponseBean fileResponseBean = new FileResponseBean();
						InputStream in = null;
						try {
							in = response.getData().getInputStream();
							byte[] byteArray=IOUtils.toByteArray(in);
							fileResponseBean.setFileFirmato( byteArray );
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
					} else {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("");
						messageBean.setCode("");
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma pades -  " + getHsmUserDescription());
						signResponseBean.setMessage(messageBean);
						FileResponseBean fileResponseBean = new FileResponseBean();
						fileResponseBean.setMessage(messageBean);
						signResponseBean.getFileResponseBean().add(fileResponseBean);
					}
				} catch (DosignException_Exception e) {
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(e.getMessage());
					messageBean.setCode("");
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma pades -  " + getHsmUserDescription());
					signResponseBean.setMessage(messageBean);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				} catch (DosignInvalidModeException_Exception e) {
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
			}
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} 
		return signResponseBean;
	}
	
	@Override
	public SignResponseBean firmaPades(List<byte[]>  listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		System.out.println("Metodo di firma file pades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}

		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = uanatacaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = uanatacaConfig.getProxyConfig();
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

		PadesConfig padesConfig = uanatacaConfig.getPadesConfig();
		if (padesConfig == null) {
			logger.error("Non e' stata specificata la configurazione per la firma pades" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Pades non specificata");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();

			RemotePdfSignatureDto request = new RemotePdfSignatureDto();
			request.setUsername(uanatacaConfig.getUser());
			request.setPassword(uanatacaConfig.getPassword());
			request.setOtp(uanatacaConfig.getOtpPwd());
			request.setPin(uanatacaConfig.getPin());
			
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
			//request.setGraphicDto( graphic );
			
			request.setProvider("UANATACA");
			System.out.println("getEnvironment " + uanatacaConfig.getEnvironment());
			UanatacaEnvironment uanatacaEnvironment = UanatacaEnvironment.valueOf( uanatacaConfig.getEnvironment() );
			System.out.println("uanatacaEnvironment " + uanatacaEnvironment);
			
			if( uanatacaEnvironment!=null && uanatacaEnvironment.equals( UanatacaEnvironment.BOX ))
				request.setEnv( Env.BOX );
			else
				request.setEnv( Env.CLOUD );
			System.out.println("request " + request.getEnv());
			
			for( byte[] bytes : listaBytesFileDaFirmare) {
				String contentType = "application/octet-stream";
				DataHandler fileStream =  new DataHandler( new ByteArrayDataSource(bytes, contentType) );
				request.setData(fileStream);
				RemoteSignatureDto response;
				try {
					response = uanatacaService.pdfsign(request );
					if (response != null) {
						//logger.debug("Response Status: " + response.getStatus());
						//logger.debug("Response Return Code: " + response.getReturnCode());
						if (response.getData()==null ) {
							MessageBean messageBean = new MessageBean();
							messageBean.setDescription(/*response.getDescription()*/"");
							messageBean.setCode(/*response.getReturnCode()*/"");
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
								byte[] byteArray=IOUtils.toByteArray(in);
								fileResponseBean.setFileFirmato( byteArray );
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
					//e.printStackTrace();
					logger.error(getHsmUserDescription(), e);
					//throw new HsmClientSignatureException("Errore in fase di firma");
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
				
			}
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} 
		return signResponseBean;
	}

	@Override
	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di apertura sessione di firma - INIZIO");
		System.out.println("Metodo di apertura sessione di firma - INIZIO");
		SessionResponseBean sessionResponseBean = new SessionResponseBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}

		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();


		WSConfig wsSignConfig = uanatacaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		System.out.println("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();

			RemoteStartTransactionDto request = new RemoteStartTransactionDto();
			request.setEnv(Env.CLOUD);
			System.out.println(uanatacaConfig.getUser());
			request.setUsername(uanatacaConfig.getUser());
			System.out.println(uanatacaConfig.getPassword());
			request.setPassword(uanatacaConfig.getPassword());
			System.out.println(uanatacaConfig.getOtpPwd());
			request.setOtp(uanatacaConfig.getOtpPwd());
			request.setPin( uanatacaConfig.getPin());
			request.setProvider("UANATACA");
			
			request.setUsername("1468634");
			request.setPassword("Chdf4w2h");
			request.setPin("belorado74");
	        request.setOtp("000000");
	        request.setProvider("UANATACA");
			
			String response;
			try {
				System.out.println("start");
				response = uanatacaService.startTransaction(request);
			
				System.out.println("Response " + response );
				String sessionId = null;
				if (response == null ) {
					String messageString = "Errore in fase di apertura sessione firma";
					logger.error("Errore in fase di apertura sessione firma " + getHsmUserDescription());
					System.out.println("Errore in fase di apertura sessione firma " + getHsmUserDescription());
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(messageString);
					messageBean.setStatus(ResponseStatus.KO);
					sessionResponseBean.setMessage(messageBean);
				} else {
					sessionId = response;
					logger.debug("Sessione di firma iniziata - SessionId: " + sessionId);
					System.out.println("Sessione di firma iniziata - SessionId: " + sessionId);
					sessionResponseBean.setSessionId(sessionId);
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					sessionResponseBean.setMessage(messageBean);
				}
			} catch (DosignException_Exception e) {
				e.printStackTrace();
				String messageString = e.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				System.out.println("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			} catch (DosignInvalidOtpException_Exception e) {
				e.printStackTrace();
				String messageString = e.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				System.out.println("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			} catch (DosignInvalidPinException_Exception e) {
				e.printStackTrace();
				String messageString = e.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				System.out.println("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
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
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}

		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = uanatacaConfig.getWsSignConfig();
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
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();
			
			RemoteEndTransactionDto request = new RemoteEndTransactionDto();
			request.setUsername(uanatacaConfig.getUser());
			request.setPassword(uanatacaConfig.getPassword());
			request.setSessionId(sessionId);
			request.setEnv(Env.CLOUD);
			
			try {
				uanatacaService.endTransaction(request);
			} catch (DosignException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				System.out.println("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
			} catch (DosignInvalidOtpException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				System.out.println("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
			} catch (DosignInvalidPinException_Exception e) {
				String messageString = e.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				System.out.println("Errore in fase di chiusura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
			}
			System.out.println("Sessione di firma terminata");
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
		System.out.println("Metodo di firma multipla hash con sessione - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}

		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = uanatacaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = uanatacaConfig.getProxyConfig();
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
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();

			RemoteDigestDto  request = new RemoteDigestDto ();
			request.setUsername( /*uanatacaConfig.getUser()*/ "1467596");
			request.setPassword( /*uanatacaConfig.getPassword()*/"f43Nrr;Y");
			request.setPin( uanatacaConfig.getPin());
			request.setEnv(Env.CLOUD);
			request.setIdentifier( uanatacaConfig.getIdentifier() );
			//request.setOtp("000000");
			request.setMechanism(Mechanism.RSASHA_256);
			if( sessionId!=null){
			System.out.println("setto " + sessionId);
			request.setSessionId( sessionId );
			} else {
				request.setOtp("000000");
			}
			request.setProvider("UANATACA");
			request.setBillingUser( uanatacaConfig.getBillingUsername()/*"apifirmapiemonte@bit4id.com"*/);
			request.setBillingPassword( uanatacaConfig.getBillingPassword()/*"Un5nNnBXImp5KQ=="*/);
			
			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				//byte[] hashDecoded = Base64.decodeBase64(hashDaFirmare.getHash());
				request.setPlaintext(hashDaFirmare.getHash());
				String response = uanatacaService.digest(request);

				HashResponseBean hashResponseBean = new HashResponseBean();
				if (response != null) {
					//logger.debug("Response Status: " + response.getStatus());
					//logger.debug("Response Return Code: " + response.getReturnCode());
					/*if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("KO")) {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription(response.getDescription());
						messageBean.setCode(response.getReturnCode());
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma multipla hash - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
						hashResponseBean.setMessage(messageBean);
					} else {*/
						logger.debug("Ricevuto hash firmata");
						// byte[] signature = response.getBinaryoutput();
						byte[] signature = Base64.decodeBase64(response)/*.getSignature()*/;
						MessageBean messageBean = new MessageBean();
						messageBean.setStatus(ResponseStatus.OK);
						signResponseBean.setMessage(messageBean);
						hashResponseBean.setHashFirmata(signature);
					//}
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
		System.out.println("Metodo di richiesta certificati utente - INIZIO");
		CertResponseBean certResponseBean = new CertResponseBean();
		List<CertBean> certBeanList = new ArrayList<CertBean>();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}

		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();

		WSConfig wsCertConfig = uanatacaConfig.getWsCertConfig();
		if (wsCertConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta certificati" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta certificati non specificata");
		}

		ProxyConfig proxyConfig = uanatacaConfig.getProxyConfig();
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
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();

			RemoteCertsDto request = new RemoteCertsDto();
			request.setUsername( uanatacaConfig.getUser());
			request.setPassword( uanatacaConfig.getPassword());
			request.setPin( uanatacaConfig.getPin());
			request.setProvider("UANATACA");
			System.out.println("getEnvironment " + uanatacaConfig.getEnvironment());
			UanatacaEnvironment uanatacaEnvironment = UanatacaEnvironment.valueOf( uanatacaConfig.getEnvironment() );
			System.out.println("uanatacaEnvironment " + uanatacaEnvironment);
			
			if( uanatacaEnvironment!=null && uanatacaEnvironment.equals( UanatacaEnvironment.BOX ))
				request.setEnv( Env.BOX );
			else
				request.setEnv( Env.CLOUD );
			System.out.println("request " + request.getEnv());
			System.out.println("request " + uanatacaConfig.getIdentifier());
			request.setIdentifier( uanatacaConfig.getIdentifier() );
			System.out.println("request " + request.getIdentifier());
			request.setType(Type.CERTIFICATE);

			System.out.println("chiedo certs");
			String response = uanatacaService.certs(request);
			System.out.println(response);
			
			if (response != null) {
				System.out.println(response);
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
				System.out.println("Errore nella risposta di certificati utente" + " " + getHsmUserDescription());
				logger.error("Errore nella risposta di certificati utente" + " " + getHsmUserDescription());
				certResponseBean.setMessageBean(messageBean);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error(getHsmUserDescription(), e);
		} catch (DosignException_Exception e) {
			e.printStackTrace();
			
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription("Errore nella risposta di certificati utente " + e.getMessage());
			System.out.println("Errore nella risposta di certificati utente" + " " + e.getMessage());
			logger.error("Errore nella risposta di certificati utente" + " " + e.getMessage());
			certResponseBean.setMessageBean(messageBean);
		}
		return certResponseBean;
	}
	
	@Override
	public MarcaResponseBean aggiungiMarca(File fileDaMarcare, MarcaRequestBean marcaRequestBean) throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		System.out.println("qui");
		
		MarcaResponseBean marcaResponseBean = new MarcaResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof UanatacaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Uanataca" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Uanataca non specificata");
		}
		
		UanatacaConfig uanatacaConfig = (UanatacaConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = uanatacaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = uanatacaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		System.out.println("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			DosignRemoteService uanatacaService = service.getPort(DosignRemoteService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) uanatacaService).getBinding();
			
			RemoteTimestampDto request = new RemoteTimestampDto();
			if( marcaRequestBean!=null && marcaRequestBean.getTipoFirma()!=null && marcaRequestBean.getTipoFirma().equalsIgnoreCase("PDF"))
				request.setFormat(TsFormat.PDF);
			if( marcaRequestBean!=null && marcaRequestBean.getTipoFirma()!=null && marcaRequestBean.getTipoFirma().equalsIgnoreCase("CADES"))
				request.setFormat(TsFormat.P_7_M);
			if( marcaRequestBean!=null && marcaRequestBean.getTipoFirma()!=null && marcaRequestBean.getTipoFirma().equalsIgnoreCase("TSD"))
				request.setFormat(TsFormat.TSD);
			request.setProvider("UANATACA");
			System.out.println("getEnvironment " + uanatacaConfig.getEnvironment());
			UanatacaEnvironment uanatacaEnvironment = UanatacaEnvironment.valueOf( uanatacaConfig.getEnvironment() );
			System.out.println("uanatacaEnvironment " + uanatacaEnvironment);
			
			if( uanatacaEnvironment!=null && uanatacaEnvironment.equals( UanatacaEnvironment.BOX ))
				request.setEnv( Env.BOX );
			else
				request.setEnv( Env.CLOUD );
			System.out.println("request " + request.getEnv());
			
			System.out.println("passo il file");
			
			/*DataHandler fileStream =  new DataHandler( new FileDataSource(fileDaMarcare) );
			request.setData(fileStream);*/
			byte[] bytes;
			try {
				System.out.println(fileDaMarcare);
				bytes = IOUtils.toByteArray(new FileInputStream(fileDaMarcare));
				String contentType = "application/octet-stream";
				DataHandler fileStream =  new DataHandler( new ByteArrayDataSource(bytes, contentType) );
				request.setData(fileStream);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
			RemoteTimestampDto response;
			try {
				response = uanatacaService.createTimeStampedData(request );
				System.out.println(response);
				System.out.println(response.getProvider() + " " + response.getFormat());
				System.out.println(response.getData());
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
						e.printStackTrace();
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("");
						messageBean.setCode("");
						messageBean.setStatus(ResponseStatus.KO);
						System.out.println("Errore nella risposta di marca-  " );
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
					System.out.println("Errore nella risposta di marca - ggg "  );
					marcaResponseBean.setMessage(messageBean);
				}
			} catch (DosignException_Exception e) {
				e.printStackTrace();
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
	
}
