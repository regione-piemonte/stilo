package it.eng.core.service.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import it.eng.core.business.exception.CoreExceptionBean;
import it.eng.core.config.SpringCoreContextProvider;
import it.eng.core.performance.PerformanceLogger;
import it.eng.core.service.bean.AttachDescription;
import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.AttachHelper;
import it.eng.core.service.bean.BeanParamsAttachDescription;
import it.eng.core.service.bean.EmptyListHelper;
import it.eng.core.service.bean.EmptyListParamDescription;
import it.eng.core.service.bean.IAuthenticationBean;
import it.eng.core.service.bean.NullBean;
import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.bean.soap.ResponseSoapServiceBean;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.client.config.RestBusinessConfig;
import it.eng.core.service.client.config.RestServiceInvocationBefore;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.core.service.serialization.SerializerRepository;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.RestCallSupport;

/**
 * Implementazione tramite REST service
 */
public class RestBusiness implements IBusiness {

    private static Logger log = Logger.getLogger(RestBusiness.class);

    private static final String MESSAGE_TRUNCATED = "corpo del testo eliminato per la presenza di caratteri non processabili";

    private String baseURL;

    private RestServiceInvocationBefore before;
    
    private static Map<String, RestServiceInvocationBefore> lMap = null;
    
    private static RestBusinessConfig config = null;

    private Serializable callInternal(IAuthenticationBean authBean, Locale locale, Class<?> outputclass, Type outputType, String servicename,
            String operationame, Serializable... inputs) throws Exception {

    	Client client = Client.create(new DefaultClientConfig());
    	
    	if ((config == null) && (SpringAppContext.getContext().containsBeanDefinition("RestBusinessConfig"))) {
    		config = (RestBusinessConfig) SpringAppContext.getContext().getBean("RestBusinessConfig");
    	}
		if (config != null) {
			client.setConnectTimeout(config.getConnectionTimeout());
			client.setReadTimeout(config.getReadTimeout());
		} 

    	try {
        	if (lMap == null) {
        		lMap = SpringCoreContextProvider.getContext().getBeansOfType(RestServiceInvocationBefore.class);
        	}
            if (lMap != null && lMap.size() > 0) {
                for (RestServiceInvocationBefore lRestServiceInvocationBefore : lMap.values()) {
                    if (baseURL.equals(lRestServiceInvocationBefore.getUrl())) {
                        before = lRestServiceInvocationBefore;
                    }
                }
            }
        } catch (Exception e) {
            before = null;
        }
        Serializable ser = null; // ritorno
        try {

            WebResource service = client.resource(getBaseURI());
            // in ogni chiamata andiamo a impostare
            // Serializzo l'oggetto
            IServiceSerialize serializeUtil = SerializerRepository.getSerializationUtil(Configuration.getInstance().getSerializationtype());

            // Contenitore dei dati da spedire
            FormDataMultiPart multi = new FormDataMultiPart();

            // Serializzo gli oggetti passati in ingresso
            List<FileIdAssociation> attachsend = new ArrayList<FileIdAssociation>();

            ArrayList<String> inputstrings = new ArrayList<String>();
            int i = 0;// parameter pos
            if (inputs != null) {
                for (Serializable input : inputs) {
                    // verifico se devo serializzare come attach
                    AttachDescription ad = AttachHelper.serializeAsAttach(i, input, serializeUtil);
                    EmptyListParamDescription lAttachDescription = EmptyListHelper.serializeAsAttach(i, input, serializeUtil);
                    if (ad != null) {
                        // Solo se e' un bean
                        if (ad instanceof BeanParamsAttachDescription && lAttachDescription != null) {
                            lAttachDescription.setBean((BeanParamsAttachDescription) ad);
                            List<FileIdAssociation> assocs = ad.getContents();
                            attachsend.addAll(assocs);
                            inputstrings.add(serializeUtil.serialize(lAttachDescription));
                        } else {
                            log.debug("servicename:" + servicename + "op:" + operationame + " parametro " + i + " serializzato come  attach class:"
                                    + ad.getClass());
                            List<FileIdAssociation> assocs = ad.getContents();
                            attachsend.addAll(assocs);
                            inputstrings.add(serializeUtil.serialize(ad));
                        }
                    } else {
                        if (lAttachDescription != null) {
                            lAttachDescription.setSerializedData(serializeUtil.serialize(input));
                            inputstrings.add(serializeUtil.serialize(lAttachDescription));

                        } else {
                            log.debug("servicename:" + servicename + "op:" + operationame + " parametro " + i + " serializzato senza attach");
                            inputstrings.add(serializeUtil.serialize(input));
                        }
                    }
                    i++;
                }
            }
            // aggiungo gli attach
            for (FileIdAssociation fileIdAssociation : attachsend) {
                // per compatibilit� con soap metto l'id all'interno delle
                // parentesi <id>
                FileDataBodyPart fdbp = new FileDataBodyPart(fileIdAssociation.getId(), fileIdAssociation.getContent());
                multi.bodyPart(fdbp);
                log.debug("aggiungo multipart " + fileIdAssociation.getId() + " content:" + fileIdAssociation.getContent());
            }

            RestServiceBean bean = new RestServiceBean();
            bean.setOperationname(operationame);
            bean.setServicename(servicename);
            bean.setTokenid("");            
            bean.setUuidtransaction("");
            bean.setInputs(inputstrings);
            bean.setSerializationType(Configuration.getInstance().getSerializationtype().toString());
            bean.setIdDominio("test");
            int size = 0;
            List<String> inputToCheck = bean.getInputs();
            for (String input : inputToCheck) {
                size += input != null ? input.length() : 0;
            }
            log.debug("Size del messaggio da inviare in REST: " + size);
            bean.setInputs(inputToCheck);
            if (before != null) {
                before.before(bean);
            }

            multi = multi.field("restservicebean", bean, MediaType.APPLICATION_XML_TYPE);
            // Chiamo il servizio
            client.setChunkedEncodingSize(1000000);
            if (Configuration.getInstance().getRestChunkedEncodingSize() != null) {
                client.setChunkedEncodingSize(Configuration.getInstance().getRestChunkedEncodingSize().intValue());
            }

            Builder restBuilder = service.path("invoke").acceptLanguage(locale).type(MediaType.MULTIPART_FORM_DATA_TYPE);

            // gestione della autenticazione IRIS-like...
            // vengono aggiunti header con i dati di autenticazione
            if (authBean != null) {

                Map<String, String> beanDescription = authBean.describe();
                for (String key : beanDescription.keySet()) {
                    restBuilder = restBuilder.header(key, beanDescription.get(key));
                }

            }
            log.debug("Primo tentativo di invocazione dei servizi");
            
            PerformanceLogger lPerformanceLogger1 = new PerformanceLogger(getUserInfoForPerformanceLogger(authBean), "First call " + servicename + "." + operationame + getTagContent(inputstrings.toString(), "tipocomboin") + getTagContent(inputstrings.toString(), "altriparametriin"));
            
            lPerformanceLogger1.start();
    		        	
            ClientResponse cr1 = restBuilder.post(ClientResponse.class, multi);
            
            lPerformanceLogger1.end();    		 
            
            try {
                if (Response.Status.OK.getStatusCode() == cr1.getStatus()) {
                    if (outputclass != null) {
                        //
                        // si dovrebbe usare multipart ma per semplificare uso
                        // FileDatamultiPart
                    	log.debug("prima: ");
                        FormDataMultiPart mp = cr1.getEntity(FormDataMultiPart.class);
                        log.debug("mp: " +mp);
                        ResponseSoapServiceBean ret = getResponse(mp.getFields());
                        log.debug("ret: " +ret.getXml());
                        // verifica se ci sono file e ricostruisci gli input
                        String objectserialize = ret.getXml();
                        log.debug("objectserialize: " +objectserialize);
						for (int j = 0; j < mp.getBodyParts().size(); j++) {
							BodyPartEntity en = (BodyPartEntity) mp.getBodyParts().get(j).getEntity();
							log.debug("en: " + en);
							en.close();
						}
						log.debug("en.close()");
                        // deserializzo il ritorno se � un attachDescription
                        // allora ci sono attach e il ritorno effettivo va'
                        // ricostruito
                        Serializable obj = serializeUtil.deserialize(objectserialize, outputclass, outputType);
                        log.debug("Serializable:" + obj.getClass());
                        if (obj instanceof NullBean)
                            return null;
                        if (obj instanceof AttachDescription) {
                            log.debug("il parametro di ritorno contiene attach class:" + obj.getClass());
                            // ricostruzione dell'oggetto da passsare
                            ser = AttachHelper.deserialize((AttachDescription) obj, ret.getAttach(), serializeUtil, outputclass, outputType);
                            log.debug("parametro ricostruito " + ser.getClass());
                        } else {
                            ser = serializeUtil.deserialize(objectserialize, outputclass, outputType);
                            log.debug("parametro deserializzato" + ser);
                        }
                    }
                } else {
                    if (Response.Status.BAD_REQUEST.getStatusCode() == cr1.getStatus() && inputToCheck.get(0).contains("<corpo>")
                            && inputToCheck.get(0).contains("</corpo>")) {
                        for (int j = 0; j < inputToCheck.size(); j++) {
                            String input = inputToCheck.get(j);
                            // elimino il body solamente se la prima
                            // trasmissione � andata in errore e tento di
                            // nuovo
                            if (input != null && input.contains("<corpo>") && input.contains("</corpo>")
                                    && input.substring(input.indexOf("<corpo>"), input.indexOf("</corpo>")).length() > 1) {
                                String primaParte = input.substring(0, input.indexOf("<corpo>"));
                                String secondaParte = input.substring(input.indexOf("</corpo>"), input.length());
                                input = primaParte + MESSAGE_TRUNCATED + secondaParte;
                                inputToCheck.set(j, input);
                            }
                        }
                        bean.setInputs(inputToCheck);
                        multi = multi.field("restservicebean", bean, MediaType.APPLICATION_XML_TYPE);

                        // Chiamo il servizio
                        // String returnobjectserialize =
                        // service.path("invoke").acceptLanguage(locale).type(MediaType.MULTIPART_FORM_DATA_TYPE).post(String.class,multi);

                        client.setChunkedEncodingSize(1000000);
                        if (Configuration.getInstance().getRestChunkedEncodingSize() != null) {
                            client.setChunkedEncodingSize(Configuration.getInstance().getRestChunkedEncodingSize().intValue());
                        }

                        restBuilder = service.path("invoke").acceptLanguage(locale).type(MediaType.MULTIPART_FORM_DATA_TYPE);

                        // gestione della autenticazione IRIS-like...
                        // vengono aggiunti header con i dati di autenticazione
                        if (authBean != null) {

                            Map<String, String> beanDescription = authBean.describe();
                            for (String key : beanDescription.keySet()) {
                                restBuilder = restBuilder.header(key, beanDescription.get(key));
                            }

                        }
                        log.debug("Secondo tentativo di invocazione dei servizi");
                        
                        PerformanceLogger lPerformanceLogger2 = new PerformanceLogger(getUserInfoForPerformanceLogger(authBean), "Second call " + servicename + "." + operationame + getTagContent(inputstrings.toString(), "tipocomboin") + getTagContent(inputstrings.toString(), "altriparametriin"));
                        
                        lPerformanceLogger2.start();
                		        	                       
                        ClientResponse cr2 = restBuilder.post(ClientResponse.class, multi);
                        
                        lPerformanceLogger2.end();
                        
                        try {
                            if (Response.Status.OK.getStatusCode() == cr2.getStatus()) {
                                if (outputclass != null) {
                                    //
                                    // si dovrebbe usare multipart ma per
                                    // semplificare uso FileDatamultiPart
                                    FormDataMultiPart mp = cr2.getEntity(FormDataMultiPart.class);
                                    ResponseSoapServiceBean ret = getResponse(mp.getFields());
                                    // verifica se ci sono file e ricostruisci
                                    // gli input
                                    String objectserialize = ret.getXml();
                                    // deserializzo il ritorno se � un
                                    // attachDescription allora ci sono attach e
                                    // il ritorno effettivo va'
                                    // ricostruito
                                    log.debug("objectserialize: " +objectserialize);
                                    for (int j = 0; j < mp.getBodyParts().size(); j++) {
            							BodyPartEntity en = (BodyPartEntity) mp.getBodyParts().get(j).getEntity();
            							log.debug("en: " + en);
            							en.close();
            						}
            						log.debug("en.close()");
                                    Serializable obj = serializeUtil.deserialize(objectserialize, outputclass, outputType);
                                    if (obj instanceof NullBean)
                                        return null;
                                    if (obj instanceof AttachDescription) {
                                        log.debug("il parametro di ritorno contiene attach class:" + obj.getClass());
                                        // ricostruzione dell'oggetto da
                                        // passsare
                                        ser = AttachHelper.deserialize((AttachDescription) obj, ret.getAttach(), serializeUtil, outputclass,
                                                outputType);
                                        log.debug("parametro ricostruito " + ser.getClass());
                                    } else {
                                        ser = serializeUtil.deserialize(objectserialize, outputclass, outputType);
                                        log.debug("parametro deserializzato" + ser);
                                    }
                                }
                            } else {
                                log.fatal("risposta NOT OK dal server:" + cr2);
                                throw new UniformInterfaceException(cr2);
                            }
                        } finally {
                            cr2.close();
                        }
                    } else {
                        log.fatal("risposta NOT OK dal server:" + cr1);
                        throw new UniformInterfaceException(cr1);
                    }
                }
            } finally {
                cr1.close();
            }
        } catch (ClientHandlerException che) {
            // errore di comunicazione con il client
            log.debug("launching CoreException from this ClientHandlerException", che);
            
            if (che.getCause() instanceof SocketTimeoutException) {
            	throw new SocketTimeoutException(config != null ? config.getErrorMessage() : "Impossibile evadere la richiesta nel tempo stabilito. Riprovare pi� tardi");
            }
            
            throw new CoreException(new CoreExceptionBean(che));
        } catch (UniformInterfaceException ufe) {
            // errore sul server
            throw RestCallSupport.buildCoreException(ufe);
        } catch (it.eng.core.business.exception.ServiceException pServiceException) {
            throw pServiceException;
        } catch (Exception ex) {
            log.debug("launching CoreException from this ex", ex);
            throw new CoreException(new CoreExceptionBean(ex));
        } finally {
            client.destroy();
        }
		
        return ser;
    }

    // parse della response jersey
    /**
     * parserizza la risposta jersey che � multipart, la parte denomianta
     * response contiene la risposta effettiva gli altri sono attach
     */
    private ResponseSoapServiceBean getResponse(Map<String, List<FormDataBodyPart>> parts) throws Exception {
        log.debug("LATO client: parsing response");
        ResponseSoapServiceBean rssb = new ResponseSoapServiceBean();
        Map<String, File> ret = new HashMap<String, File>();
        Set<String> keys = parts.keySet();
        for (String key : keys) {
            log.debug("key in part:" + key);
            // System.out.println("part:"+ parts.get(key));
            if (key.equals("response")) {
                // recupera la risposta
                List<FormDataBodyPart> files = parts.get(key);
                // qui dovrebbe essercene solo uno
                if (files != null && files.size() > 0) {
                    FormDataBodyPart parte = files.get(0);
                    log.debug("files.get(0): "+files.get(0));
                    rssb.setXml(parte.getEntityAs(String.class));
                }
            } else {
                // recupera gli allegati
                List<FormDataBodyPart> files = parts.get(key);
                // ammetto solo un file per chiave
                if (files != null && files.size() > 0) {
                    FormDataBodyPart parte = files.get(0);
                    File file = getFileFromPart(parte);
                    log.debug("file: "+file);
                    if (key.startsWith("<")) {
                        key = key.substring(1, key.length() - 1);
                        log.debug("removed angular braket:" + key);
                    }
                    ret.put(key, file);
                }
            }
        }
        rssb.setAttach(ret);
        return rssb;
    }

    private File getFileFromPart(FormDataBodyPart parte) throws Exception {
        BodyPartEntity bpe = (BodyPartEntity) parte.getEntity();
        File f = File.createTempFile("rep", "tmp");
        OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
        try {
            ReaderWriter.writeTo(bpe.getInputStream(), out);
        } finally {
            bpe.cleanup();
            out.close();
        }
        return f;
    }

    /**
     * Recupera il base uri
     * @return
     */
    private URI getBaseURI() {
        if (baseURL != null)
            return UriBuilder.fromUri(baseURL + "/rest/ServiceRestStore").build();
        return UriBuilder.fromUri(Configuration.getInstance().getUrl() + "/rest/ServiceRestStore").build();
    }

    @Override
    public void init(Configuration config) {
        log.info("init RestBusiness..");

    }

    @Override
    public Serializable call(String url, Locale locale, Class<?> outputclass, Type outputType, String module, String method, Serializable... input)
            throws Exception {
        baseURL = url;
        return callInternal(null, locale, outputclass, outputType, module, method, input);
    }

    @Override
    public Serializable call(IAuthenticationBean authBean, String url, Locale locale, Class<?> outputclass, Type outputType, String module,
            String method, Serializable... input) throws Exception {
        baseURL = url;
        return callInternal(authBean, locale, outputclass, outputType, module, method, input);
    }

    @Override
    public Serializable call(Locale locale, Class<?> outputclass, Type outputType, String module, String method, Serializable... input)
            throws Exception {
        return callInternal(null, locale, outputclass, outputType, module, method, input);

    }

    public RestServiceInvocationBefore getBefore() {
        return before;
    }

    public void setBefore(RestServiceInvocationBefore before) {
        this.before = before;
    }
    
    private static String getTagContent(String str, String tagName) {
    	try {
	    	int start = str.indexOf("<" + tagName + ">") + tagName.length() + 2;
	    	int end = str.indexOf("</" + tagName + ">");
	    	return " " + tagName + ":" + str.substring(start, end);
	    } catch (Exception e) {}
    	return "";
    }
    
    /**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(IAuthenticationBean lIAuthenticationBean) {
		if (lIAuthenticationBean != null) {
			String nomeApplicazione = lIAuthenticationBean.getApplicationName();
			return "[" + nomeApplicazione + "]";
		}
		return "";
	}

}

