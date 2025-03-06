package it.eng.core.service.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.Providers;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.serializers.XmlSerializer;

import com.sun.jersey.core.header.OutBoundHeaders;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.exception.ServiceException;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.config.ConfigUtil;
import it.eng.core.config.CoreConfig;
import it.eng.core.config.SpringCoreContextProvider;
import it.eng.core.performance.PerformanceLogger;
import it.eng.core.service.bean.AttachDescription;
import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.AttachHelper;
import it.eng.core.service.bean.IAuthenticationBean;
import it.eng.core.service.bean.IServiceInvokerInfo;
import it.eng.core.service.bean.IrisCall;
import it.eng.core.service.bean.NullBean;
import it.eng.core.service.bean.ServiceBean;
import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.client.ExceptionType;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.core.service.serialization.SerializationType;
import it.eng.core.service.serialization.SerializerRepository;

/**
 * servizio rest esposto per invocare le {@link Operation} via rest.
 * @author Russo
 */
// FIXME Per semplificare alcune operazioni ugli allegati si impone come ritorno
// il mediatype MULTIPART_FORM_DATA
// anche se vobrebbe essere multipart/mixed
@Path("/ServiceRestStore")
public class ServiceRestStore {
    private static Logger log = Logger.getLogger(ServiceRestStore.class);

    private RestBusinessAfter after;
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse respose;
    @Context
    Providers providers;

    @POST
    @Path("invoke")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    // @Produces(MediaType.APPLICATION_XML)
    // @Produces("multipart/mixed")
    // @Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response invoke(@FormDataParam("restservicebean") RestServiceBean servicebeanrest,
    // @FormDataParam("files") List<FormDataBodyPart> files,
            FormDataMultiPart multiPart) throws ServiceException {

        Object output = "";
        providers.getExceptionMapper(ServiceException.class);
        IServiceSerialize serializeutil = null;

        try {
            after = SpringCoreContextProvider.getContext().getBean(RestBusinessAfter.class);
        } catch (Exception e) {
            after = null;
        }
        Serializable outputobject = null;
        try {

            Locale localeFromClient = request.getLocale();
            log.debug("Locale:" + request.getLocale());
            // MessageHelper.changeLocale(localeFromClient);
            log.debug("invocato servizio rest del core... servicebean:" + servicebeanrest);

            // Istanzio l'utilità di serializzazione
            serializeutil = SerializerRepository.getSerializationUtil(SerializationType.valueOf(servicebeanrest.getSerializationType()));

            String idDominio = "DEFAULT";
            String idApplicazione = null;

            IAuthenticationBean authenticationBean = null;
            // AUTENTICAZIONE IRIS
            // cerco se in configurazione e' prevista la autenticazione
            // se si, controllo che negli header http siano stati passati i
            // parametri
            // se si invoco il servizio con i parametri
            String loginService = null;//
            if (ConfigUtil.isAuthenticationEnabled()) {
                // se si, controllo che in configurazione sia configurato il
                // servizio di login
                loginService = ConfigUtil.getAuthenticationServiceName();
                String authenticationBeanName = ConfigUtil.getAuthenticationBeanName();

                authenticationBean = (IAuthenticationBean) Class.forName(authenticationBeanName).newInstance();

                Set<String> beanProps = authenticationBean.listProperties();

                Map<String, String> mappaValori = new HashMap<String, String>();
                for (String key : beanProps) {
                    String value = request.getHeader(key);
                    if (value != null)
                        mappaValori.put(key, value);
                }

                authenticationBean.populateFromDescription(mappaValori);
                idApplicazione = authenticationBean.getApplicationName();

                if (authenticationBean.getApplicationName() != null) {
                    org.apache.commons.configuration.Configuration configurazioneApplicazioniEnti = ConfigUtil.getMappaApplEnte();
                    String tmp = (String) configurazioneApplicazioniEnti.getProperty(authenticationBean.getApplicationName());
                    idDominio = tmp == null ? "DEFAULT" : tmp;
                }

            }
         
            // cerco se ci sono gli header realtivi alla autenticazione

            //

            // Setto i valori del token di connessione nella variabile
            // threadlocal
            // request.getH
            // TODO DA RIDEFINIRE
            SubjectBean subject = new SubjectBean();
            subject.setUuidtransaction(servicebeanrest.getUuidtransaction());
            subject.setIdDominio(idDominio);
            subject.setLocale(localeFromClient);
            SubjectUtil.subject.set(subject);

            if (after != null) {
                after.after(servicebeanrest);
            }

            // chiamo il servizio
            if (ConfigUtil.isAuthenticationEnabled() && !servicebeanrest.getServicename().equals(loginService)) {
                subject.setIdapplicazione(idApplicazione);
                
                PerformanceLogger lPerformanceLogger = new PerformanceLogger(getUserInfoForPerformanceLogger(subject), loginService + ".login");
                lPerformanceLogger.start();
                
                IServiceInvokerInfo invokerInfo = (IServiceInvokerInfo) IrisCall.call(loginService, "login",
                        new Serializable[] { authenticationBean });
                
                lPerformanceLogger.end();

                if (invokerInfo != null) {
                    subject.setIduser(invokerInfo.getIdServiceInvoker());
                    subject.setNomeapplicazione(invokerInfo.getNomeApplicazione());
                }

            }

            // TODO questo serve solo per verificare i parametri? spostare nella
            // IrisCall risparmio una chiamata a refletcion
            ServiceBean servicebean = IrisCall.getOperationServiceBean(servicebeanrest.getServicename(), servicebeanrest.getOperationname());

            // Recupero i parametri del servizio
            Class<?>[] parameters = servicebean.getOperation().getParameterTypes();
            Type[] types = servicebean.getOperation().getGenericParameterTypes();
            // se non ci sono paramentri confronta zero con zero
            if (servicebeanrest.getInputs() == null) {
                servicebeanrest.setInputs(new ArrayList<String>(0));
            }
            if (parameters.length != servicebeanrest.getInputs().size()) {
                throw new Exception("Il numero dei parametri di input è errato");
            }

            // Deserializzo i parametri di uscita
            List<Serializable> inputsObject = new ArrayList<Serializable>();
            int index = 0;
            // int currentFile=0;
            // VEcchio sistema
            // for (int count = 0; count<servicebeanrest.getInputs().size();
            // count++){
            // String objectserialize = servicebeanrest.getInputs().get(count);
            // Type lType ;
            // Class lClas;
            // HashSet<File> lHashFiles = new HashSet<File>();
            // if (types[index] == lHashFiles.getClass() ){
            // lType = FileListAttachDescription.class;
            // } else if (types[index] == File.class){
            // lType = FileAttachDescription.class;
            // } else lType = types[index];
            // if (parameters[index] == lHashFiles.getClass() ){
            // lClas = FileListAttachDescription.class;
            // lType = null;
            // } else if (parameters[index] == File.class){
            // lClas = FileAttachDescription.class;
            // lType = null;
            // } else lClas = parameters[index];
            // Serializable
            // param=serializeutil.deserialize(objectserialize,lClas,lType);
            // index++;
            // if(param instanceof FileAttachDescription){
            // inputsObject.add(((FileAttachDescription)
            // param).getParPosition(), getFileFromParts(files, currentFile));
            // currentFile++;//passo al file successivo
            // } else if(param instanceof FileListAttachDescription){
            // FileListAttachDescription lFileListAttachDescription =
            // (FileListAttachDescription) param;
            // HashSet<File> lFiles = new
            // HashSet<File>(lFileListAttachDescription.getListSize());
            // for (int i=0; i<lFileListAttachDescription.getListSize(); i++){
            // File lFile = getFileFromParts(files, currentFile);
            // lFiles.add(lFile);
            // currentFile++;//passo al file successivo
            // }
            // inputsObject.add(lFiles);
            // } else{
            // inputsObject.add(param);
            // }
            // }
            //
            // //Effettuo la chiamata all'api
            // Object outputobject =
            // IrisCall.call(servicebeanrest.getServicename(),servicebeanrest.getOperationname(),inputsObject.toArray(new
            // Serializable[0]));
            // if(outputobject!=null){ //per i metodi void!
            // //Serializzo l'oggetto di ritorno
            // if(outputobject instanceof File || outputobject instanceof
            // InputStream){
            // output=buildStreamingOutput(outputobject);
            // }else{
            // output = serializeutil.serialize(outputobject);
            // }
            // }
            // nuovo sistema
            log.debug("LATO SERVER:estraggo i files dalle parti");
            Map<String, File> mapAttach = getFiles(multiPart.getFields());
            log.debug(" START inizio deserializzazione parametri");
            for (int count = 0; count < servicebeanrest.getInputs().size(); count++) {
                String objectserialize = servicebeanrest.getInputs().get(count);
                if (objectserialize == null || objectserialize.equals("")) {
                    // parameter passed null
                    log.debug("parametro " + index + " vuoto");
                    inputsObject.add(null);
                } else {
                    // verifica se ci sono file e ricostruisci gli input da
                    // inviare al servizio
                    Serializable obj = serializeutil.deserialize(objectserialize, parameters[index], types[index]);
                    if (obj instanceof AttachDescription) {
                        log.debug("il parametro " + index + " contiene attach class:" + obj.getClass());
                        // ricostruzione dell'oggetto da passare
                        Serializable recostructedObj = AttachHelper.deserialize((AttachDescription) obj, mapAttach, serializeutil, parameters[index],
                                types[index]);
                        log.debug("ricostruito oggetto " + recostructedObj.getClass());
                        inputsObject.add(recostructedObj);
                    } else {
                        // Serializable
                        // objdes=serializeutil.deserialize(objectserialize,parameters[index],types[index]);
                        log.debug("parametro " + index + " deserializzato " + obj);
                        inputsObject.add(obj);
                    }
                }
                index++;
            }
     
            log.debug(" END fine deserializzazione parametri");
            // Effettuo la chiamata all'api
            
            PerformanceLogger lPerformanceLogger = new PerformanceLogger(getUserInfoForPerformanceLogger(servicebeanrest), servicebeanrest.getServicename() + "." + servicebeanrest.getOperationname());
            lPerformanceLogger.start();
            
            outputobject = IrisCall.call(servicebeanrest.getServicename(), servicebeanrest.getOperationname(),
                    inputsObject.toArray(new Serializable[0]));
            //
            // Serializzo l'oggetto di ritorno
            // output = serializeutil.serialize(outputobject);
            lPerformanceLogger.end();
            
            if (outputobject != null) { // per i metodi void!
                //
                List<FileIdAssociation> responseAttach = new ArrayList<FileIdAssociation>();
                AttachDescription ad = AttachHelper.serializeAsAttach(-1, outputobject, serializeutil);
                // dovresti usar MultiPart, ma per semplificare suo
                // FormDataMultipart
                // MultiPart multi= new MultiPart();
                FormDataMultiPart multi = new FormDataMultiPart();
                multi.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
                // FIXME impostare mediaype uguale a serialization per la parte
                // che rappresenta la response
                if (ad != null) {
                    log.debug("parametro di ritorno serializzato come  attach class:" + ad.getClass());
                    List<FileIdAssociation> assocs = ad.getContents();
                    responseAttach.addAll(assocs);
                    String beanResponse = serializeutil.serialize(ad);
                    multi.bodyPart(new FormDataBodyPart("response", beanResponse, MediaType.APPLICATION_XML_TYPE));
                    // aggiungo gli allegati
                    for (FileIdAssociation fida : responseAttach) {
                        FileDataBodyPart fdbp = new FileDataBodyPart(fida.getId(), fida.getContent());
                        multi.bodyPart(fdbp);
                    }
                    output = multi;
                } else {
                    log.debug("parametro di ritorno serializzato SENZA attach");
                    multi.bodyPart(new FormDataBodyPart("response", serializeutil.serialize(outputobject), MediaType.APPLICATION_XML_TYPE));
                    output = multi;
                    // output= serializeutil.serialize(outputobject);
                }
                // test multipartwriter

                // Serializzo l'oggetto di ritorno
                // if(outputobject instanceof File || outputobject instanceof
                // InputStream){
                // output=buildStreamingOutput(outputobject);
                // }else{
                // output = serializeutil.serialize(outputobject);
                // }

            } else {
                outputobject = new NullBean();
                FormDataMultiPart multi = new FormDataMultiPart();
                multi.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
                multi.bodyPart(new FormDataBodyPart("response", serializeutil.serialize(outputobject), MediaType.APPLICATION_XML_TYPE));
                output = multi;
            }
        } catch (ServiceException sex) {
            // log.error("fatal exception during call ", sex);
            // outputobject = sex;
            // FormDataMultiPart multi = new FormDataMultiPart();
            // multi.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
            // try {
            // ManagedExceptionBean lManagedExceptionBean = new
            // ManagedExceptionBean(sex);
            // multi.bodyPart(new FormDataBodyPart("response",
            // serializeutil.serialize(lManagedExceptionBean),
            // MediaType.APPLICATION_XML_TYPE));
            // } catch (Exception e) {
            // log.error("fatal exception during call ", e);
            // throw new ServiceException(e);
            // }
            // output = multi;
            log.error("fatal exception during call ", sex);
            throw sex;
        } catch (Exception ex) {
            log.error("fatal exception during call ", ex);
            throw new ServiceException(ex);
            // FormDataMultiPart multi = new FormDataMultiPart();
            // multi.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
            // try {
            // UnmanagedExceptionBean lUnmanagedExceptionBean = new
            // UnmanagedExceptionBean(ex);
            // multi.bodyPart(new FormDataBodyPart("response",
            // serializeutil.serialize(lUnmanagedExceptionBean),
            // MediaType.APPLICATION_XML_TYPE));
            // } catch (Exception e) {
            // log.error("fatal exception during call ", e);
            // throw new ServiceException(e);
            // }
            // output = multi;
        }
        log.debug("output ritornato dal servizio:" + output);
        return Response.ok(output).build();
    }

    private StreamingOutput buildStreamingOutput(Object outputobject) throws Exception {
        return new MyStreamingOutput(outputobject);
    }

    /**
     * recupera la mappa dei file dalle parti
     * @param parts
     * @return
     * @throws Exception
     */
    private Map<String, File> getFiles(Map<String, List<FormDataBodyPart>> parts) throws Exception {
        Map<String, File> ret = new HashMap<String, File>();
        Set<String> keys = parts.keySet();
        for (String key : keys) {
            log.debug("key in part:" + key);
            // FIXME da capire perchè in alcuni casi la chiave contiene le
            // parentesi angolari
            if (key.startsWith("<")) {
                key = key.substring(1, key.length() - 1);
                log.debug("removed angular braket:" + key);
            }
            // System.out.println("part:"+ parts.get(key));
            // salta il bean con i parametri xml per l'invocazione
            if (!key.equals("restservicebean")) {
                List<FormDataBodyPart> files = parts.get(key);
                // ammetto solo un file per chiave
                if (files != null && files.size() > 0) {
                    FormDataBodyPart parte = files.get(0);
                    File file = getFileFromPart(parte);
                    ret.put(key, file);
                }
            }
        }
        return ret;
    }

    private File getFileFromParts(List<FormDataBodyPart> files, int pos) throws Exception {
        if (pos < files.size()) {
            FormDataBodyPart fdbp = files.get(pos);
            if (fdbp != null) {
                return getFileFromPart(fdbp);
            }
        }
        // this is an error
        throw new ServiceException(ExceptionType.UNMANAGED, CoreConfig.modulename, "GENERIC");
    }

    private File getFileFromPart(FormDataBodyPart parte) throws Exception {
    	Object obj = parte.getEntity();
    	if (obj instanceof File) {
    		return (File) obj;
    	} 
    	BodyPartEntity bpe = null;
    	InputStream is = null;
    	if (obj instanceof BodyPartEntity) {
            bpe = (BodyPartEntity) obj;
            is = (InputStream) bpe.getInputStream();
    	} else if (obj instanceof InputStream) {
    		is = (InputStream) obj;
    	}
        File f = File.createTempFile("rep", "tmp");
        OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
        try {
            ReaderWriter.writeTo(is, out);
        } finally {
        	if (bpe != null) {
        		bpe.cleanup();
        	}
            if (out != null) {
            	out.close();
            }
        }

        return f;
    }

    @GET
    @Path("getService")
    @Produces("application/xml")
    public String getService() {
        StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\"?>");
        buffer.append("<services>");
        Reflections reflections = new Reflections(ConfigUtil.getServicePackage());
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);
        Iterator<?> iter = services.iterator();
        while (iter.hasNext()) {
            Class<?> classe = (Class<?>) iter.next();
            String servicename = classe.getAnnotation(Service.class).name();
            buffer.append("<service name=\"" + servicename + "\">");
            Method[] methods = classe.getDeclaredMethods();
            buffer.append("<operations>");
            classe.getDeclaredFields();
            for (Method method : methods) {
                Operation operation = method.getAnnotation(Operation.class);
                if (operation != null) {
                    buffer.append("<operation name=\"" + operation.name() + "\">");
                    buffer.append("<returntype><![CDATA[");
                    try {
                        // Reflections reflections2 = new Reflections(new
                        // Object[]{method.getReturnType()});
                        XmlSerializer serializer = new XmlSerializer();
                        buffer.append(serializer.toString(reflections));
                    } catch (Exception e) {
                        log.fatal(e);
                    }
                    buffer.append("]]></returntype>");
                    buffer.append("</operation>");
                }
            }
            buffer.append("</operations>");
            buffer.append("</service>");
        }
        buffer.append("</services>");
        return buffer.toString();
    }

    class MyStreamingOutput implements StreamingOutput {
        // source for streaming
        private Object source;

        public MyStreamingOutput() {

        }

        public MyStreamingOutput(Object source) {
            this.source = source;
        }

        @Override
        public void write(OutputStream output) throws IOException, WebApplicationException {
            InputStream is = null;
            if (source instanceof File) {
                is = new BufferedInputStream(new FileInputStream((File) source));
                ReaderWriter.writeTo(is, output);
                return;
            } else if (source instanceof InputStream) {
                is = (InputStream) source;
                ReaderWriter.writeTo(is, output);
                return;
            } else if (source instanceof MultiPart) {
                // test per multipart

                MultivaluedMap<String, Object> headers = new OutBoundHeaders();
                // headers.add("Content-Type:",
                // "Multipart/mixed;");//Content-Type: Multipart/mixed;
                // boundary="sample_boundary";
                File dest = new File("c:/multi.bin");
                // OutputStream out=FileUtils.openOutputStream(dest);
                CustomMultiPartWriter bw = new CustomMultiPartWriter(providers);
                bw.writeTo((MultiPart) source, MultiPart.class, MultiPart.class, new Annotation[0], MediaType.APPLICATION_OCTET_STREAM_TYPE, headers,
                        output);
                return;
            }

        }

    }

    public RestBusinessAfter getAfter() {
        return after;
    }

    public void setAfter(RestBusinessAfter after) {
        this.after = after;
    }
    
    /**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(SubjectBean subjectBean) {
		String result = "";
		if (subjectBean != null) {
			result = "[";
			String idUser = subjectBean.getIduser();
			String idDominio = subjectBean.getIdDominio();
			String idApplicazione = subjectBean.getIdapplicazione();
			String nomeApplicazione = subjectBean.getNomeapplicazione();
			result = "[";
			if (StringUtils.isNotBlank(idUser)) {
				result += " idUser: " + idUser;
			}
			if (StringUtils.isNotBlank(idDominio)) {
				result += " idDominio: " + idDominio;
			}
			if (StringUtils.isNotBlank(idApplicazione)) {
				result += " idApplicazione: " + idApplicazione;
			}
			if (StringUtils.isNotBlank(nomeApplicazione)) {
				result += " nomeApplicazione: " + nomeApplicazione;
			}
			result += "]";
		}
		return result;
	}
		
	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger(RestServiceBean servicebeanrest) {
		String result = "";
		if (servicebeanrest != null && servicebeanrest.getInputs() != null){
			result = "[";
			for (String input : servicebeanrest.getInputs()) {
				if (StringUtils.isNotBlank(input)) {
					String idUser = retrieveValueOfInputNode(input, "<idUser>");
					String idDominio = retrieveValueOfInputNode(input, "<idDominio>");
					String idApplicazione = retrieveValueOfInputNode(input, "<idApplicazione>");
					if (StringUtils.isNotBlank(idUser)) {
						result += " idUser: " + idUser;
					}
					if (StringUtils.isNotBlank(idDominio)) {
						result += " idDominio: " + idDominio;
					}
					if (StringUtils.isNotBlank(idApplicazione)) {
						result += " idApplicazione: " + idApplicazione;
					}
				}
			}
			result += "]";
		}
		return result;
	}
		
	private static String retrieveValueOfInputNode(String input, String node) {
		if (StringUtils.isNotBlank(input) && StringUtils.isNotBlank(node)) {
			int index = input.indexOf(node);
			if (index >= 0){
				int startIndex = input.indexOf(">", index + 1) + 1;
				int endIndex = input.indexOf("<", index + 1);
				if (startIndex != -1 && endIndex != -1 && endIndex > startIndex && endIndex <= input.length()) {
					return input.substring(startIndex, endIndex);
				}
			}
		}
		return "";
	}
}
