package it.eng.core.service.client;

import it.eng.core.business.exception.CoreExceptionBean;
import it.eng.core.service.bean.AttachDescription;
import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.AttachHelper;
import it.eng.core.service.bean.IAuthenticationBean;
import it.eng.core.service.bean.soap.RequestSoapServiceBean;
import it.eng.core.service.bean.soap.ResponseSoapServiceBean;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.client.soap.GenericSoapService;
import it.eng.core.service.client.soap.SoapServiceService;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.core.service.serialization.SerializerRepository;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

/**
 * Implementazione tramite SOAP Service
 * @author michele
 *
 */
public class SoapBusiness implements IBusiness {
	private static final Logger log=Logger.getLogger(SoapBusiness.class);

	private static final String SUFFIX_SOAP_SERVICE_URL = "/business/soap/ServiceSoap?wsdl";

	private SoapServiceService service = null;
	private String baseURL;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Serializable callInternal(IAuthenticationBean authBean, Locale locale,Class<?> ouptutclass,Type outputType,String servicename, String operationame, Serializable... inputs) throws Exception {
		try{
			if(service==null){
				 
				URL url = null;
				if (baseURL == null)
				 url = new URL(Configuration.getInstance().getUrl()+SUFFIX_SOAP_SERVICE_URL);
				else url = new URL(baseURL+SUFFIX_SOAP_SERVICE_URL);
				//service = new SoapServiceService(url, new QName("http://soap.service.iris.eng.it/", "SoapServiceService"));
				service = new SoapServiceService(url, new QName("http://server.service.core.eng.it/", "SoapServiceService"));
				
			}


			//Serializzo l'oggetto
			IServiceSerialize serializeUtil = SerializerRepository.getSerializationUtil(Configuration.getInstance().getSerializationtype());

			//Serializzo gli oggetti passati in ingresso
			List<String> inputstrings = new ArrayList<String>();
			//		for(Serializable input:inputs){
			//			inputstrings.add(serializeUtil.serialize(input));
			//		}


			//Serializzo gli oggetti passati in ingresso
			List<FileIdAssociation> attachsend= new ArrayList<FileIdAssociation>();
			int i=0;//parameter pos
			if(inputs!=null){
				for(Serializable input:inputs){
					//verifico se devo serializzare come attach
					AttachDescription ad=AttachHelper.serializeAsAttach(i, input,serializeUtil);
					if(ad!=null){
						log.debug("servicename:"+servicename+" parametro "+i+" serializzato come  attach class:"+ad.getClass());
						List<FileIdAssociation> assocs= ad.getContents();
						attachsend.addAll(assocs);
						inputstrings.add(serializeUtil.serialize(ad));
					}
					else{
						log.debug("servicename:"+servicename+" parametro "+i+" serializzato senza attach");
						inputstrings.add(serializeUtil.serialize(input));
					}
					i++;
				}	
			}
			//Chiamata all'operazione
			//String returnobjectserialize = service.getSoapServicePort().serviceoperationinvoke(Configuration.getInstance().getSerializationtype().toString(), "", "", servicename, operationame, inputstrings);
			RequestSoapServiceBean soapBean = new RequestSoapServiceBean();
			soapBean.setSerializationtype(Configuration.getInstance().getSerializationtype().toString());
			soapBean.setUuidtransaction("");
			soapBean.setTokenid("");
			soapBean.setServicename(servicename);
			soapBean.setOperationame(operationame);
			soapBean.setObjectsserialize(inputstrings);
			//gestione file da rivedere
			soapBean.setAttachments(attachsend);
			if (baseURL == null)
				soapBean.setUrl(new URL(Configuration.getInstance().getUrl()+SUFFIX_SOAP_SERVICE_URL));
				else soapBean.setUrl(new URL(baseURL+SUFFIX_SOAP_SERVICE_URL));
			
			
			Serializable ser=null;
			//istanzio il client
			GenericSoapService client = new GenericSoapService();


			ResponseSoapServiceBean ret=null;
			//gestione della autenticazione IRIS-like...
			//vengono aggiunti header con i dati di autenticazione 
			if (authBean!=null) {
				ret=client.invoke(soapBean, authBean.describe());
			}		
			else {
				ret=client.invoke(soapBean);
			}
			//			
			
			if(ouptutclass != null) {
//				if(ouptutclass.equals(File.class) && ret.getAttachments()!=null && ret.getAttachments().size()>0){
//					log.debug("trovato attach in response: ritorno il primo attach");
//					ser=ret.getAttachments().get(0);
//				}else{
//					//deserializzo 
//					ser=(Serializable)serializeUtil.deserialize(ret.getXml(),ouptutclass,outputType);
//				}
				 
					//verifica se ci sono file e ricostruisci gli input da inviare al servizio
					String objectserialize=ret.getXml();
					Serializable obj=serializeUtil.deserialize(objectserialize,ouptutclass,outputType);
					if(obj instanceof AttachDescription){
						log.debug("il parametro di ritorno contiene attach class:"+obj.getClass());
						//ricostruzione dell'oggetto da passsare
						ser=AttachHelper.deserialize((AttachDescription)obj, ret.getAttach(), serializeUtil, ouptutclass,outputType);
						log.debug("parametro ricostruito "+ser.getClass());
					}else{
						ser= serializeUtil.deserialize(objectserialize,ouptutclass,outputType);
						log.debug("parametro deserializzato"+ser);
					}
			}
			return ser;
		}catch(Exception ex){
			//throw new InvocationException(ex);
			log.fatal("launching CoreException from this ex",ex);
			throw new CoreException(new CoreExceptionBean(ex));
		}
	}

	@Override
	public void init(Configuration config) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Serializable call(String url, Locale locale, Class<?> outputclass,
			Type outputType, String module, String method,
			Serializable... input) throws Exception {
		baseURL = url;
		return callInternal(null, locale, outputclass, outputType, module, method, input);
	}

	@Override
	public Serializable call(IAuthenticationBean authBean, String url, Locale locale,
			Class<?> outputclass, Type outputType, String module,
			String method, Serializable... input) throws Exception {
		baseURL = url;
		return callInternal(authBean, locale, outputclass, outputType, module, method, input);
	}

	@Override
	public Serializable call(Locale locale, Class<?> outputclass,
			Type outputType, String module, String method,
			Serializable... input) throws Exception {
		return callInternal(null, locale, outputclass, outputType, module, method, input);
	}
	
	
}
