package it.eng.core.service.client.config;

import org.apache.log4j.Logger;

import it.eng.core.config.ConfigUtil;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import it.eng.core.service.serialization.SerializationType;
import it.eng.core.service.serialization.SerializerRepository;

/**
 * Classe che configura la chiamata
 * @author michele
 *
 */
public class Configuration {
	
	private static Logger log = Logger.getLogger(Configuration.class);
	private Configuration() {
		// TODO Auto-generated constructor stub
	}
	
	private static Configuration instance;
	
	public static synchronized Configuration getInstance() {
		if(instance==null){
			instance = new Configuration();
			
		}
		return instance;
	}
	
	private String url;
	
	private SerializationType serializationtype = SerializationType.XML;
	
	private BusinessType businesstype = BusinessType.SOAP;
	
	private Boolean sendAttachmentsByUri;
	
	private Integer restChunkedEncodingSize;
	
	private String packageref="it.eng";
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SerializationType getSerializationtype() {
		return serializationtype;
	}

	public void setSerializationtype(SerializationType serializationtype) {
		this.serializationtype = serializationtype;
	}

	public BusinessType getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(BusinessType businesstype) {
		this.businesstype = businesstype;
	}

	public String getPackageref() {
		return packageref;
	}

	public void setPackageref(String packageref) {
		this.packageref = packageref;
	}
	
	
	
	//inizializza il contesto jaxb per serializzare in xml i bean di ingresso/uscita
//	private synchronized void initJAXBContext(){
//		Reflections reflections = new Reflections( getPackageref());
//		// 
//		//Set<Class<?>> classesXML = reflection.getTypesAnnotatedWith(XmlRootElement.class);		
//		try {
//			Set<Class<?>> classesXML = reflections.getTypesAnnotatedWith(XmlRootElement.class);	
//			classesXML.add(String.class);
//			XmlServiceSerialize.settingContext(classesXML);
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public Boolean getSendAttachmentsByUri() {
		return sendAttachmentsByUri;
	}
	
	public void setSendAttachmentsByUri(Boolean sendAttachmentsByUri) {
		this.sendAttachmentsByUri = sendAttachmentsByUri;
	}
	
	public Integer getRestChunkedEncodingSize() {
		return restChunkedEncodingSize;
	}

	public void setRestChunkedEncodingSize(Integer restChunkedEncodingSize) {
		this.restChunkedEncodingSize = restChunkedEncodingSize;
	}

	public synchronized void initClient() throws Exception{
		log.debug("<<<<<<<<<<<<<  client config >>>>>>>>>>>>");
		log.debug("businessType: "+getBusinesstype());
		log.debug("serialization:"+getSerializationtype());
		log.debug("service package:"+getPackageref());
		log.debug("url:"+getUrl());
		validateConfig();
		//perform some initializtiation based on config
//		switch (getSerializationtype()) {
//		case XML:
//			log.debug("setting jaxb contex");
//			  initJAXBContext();
//			break;
//		case JSON:
//			break;
//		default:
//			break;
//		}
		
//		switch (getBusinesstype()){
//		case API:
//			log.debug("Selezionata modalità API avvio inizializzazione dei servizi");
//			ConfigUtil.initConfig();
//			break;
//		case REST:
//			break;
//		case SOAP:
//			break;
//		default:
//			break;
//		}
		if(getBusinesstype()==BusinessType.API){
			log.debug("Selezionata modalità API avvio inizializzazione dei servizi");
			ConfigUtil.initConfig();
		}else {
			//modalità REST o SOAP inizializza i serializer impostati e le business
			SerializerRepository.initSerializerClient(this);
			//TODO init business
		}
	}
	
	
	private void validateConfig() throws Exception{
		if(getBusinesstype()==null){
			throw new ConfigException("occorre impostare la businessType da usare");
		}
		
		if(getBusinesstype()== BusinessType.REST || getBusinesstype()==BusinessType.SOAP){
			if(getUrl()==null){
				throw new ConfigException("occorre impostare la url per la business selezionata");
			}
			if(getPackageref()==null){
				throw new ConfigException("occorre impostare il package Di riferimento in cui cercare i servizi");
			}
			if(getSerializationtype()==null){
				throw new ConfigException("occorre impostare i ltipo di serializzazione da usare");
			}
		}
	}
}
