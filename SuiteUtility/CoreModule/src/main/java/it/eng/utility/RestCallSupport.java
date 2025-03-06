package it.eng.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import it.eng.core.business.exception.CoreExceptionBean;
import it.eng.core.service.bean.AttachDescription;
import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.AttachHelper;
import it.eng.core.service.bean.BeanParamsAttachDescription;
import it.eng.core.service.bean.EmptyListHelper;
import it.eng.core.service.bean.EmptyListParamDescription;
import it.eng.core.service.bean.soap.ResponseSoapServiceBean;
import it.eng.core.service.client.CoreException;
import it.eng.core.service.serialization.IServiceSerialize;

public class RestCallSupport {
	
	private static Logger log = Logger.getLogger(RestCallSupport.class);
	
	public static final void fill(String servicename, String operationame, IServiceSerialize serializeUtil,
			List<FileIdAssociation> attachsend, ArrayList<String> inputstrings, Serializable... inputs)
			throws Exception {
		int i = 0;// parameter pos
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
	
    // parse della response jersey
    /**
     * parserizza la risposta jersey che � multipart, la parte denomianta
     * response contiene la risposta effettiva gli altri sono attach
     */
	public static final ResponseSoapServiceBean getResponse(Map<String, List<FormDataBodyPart>> parts) throws Exception {
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
                    Object obj = parte.getEntity();
                    String xml = null;
                    if (obj instanceof BodyPartEntity) {
                    	xml = parte.getEntityAs(String.class);
                    } else {
                    	xml = String.valueOf(obj);
                    }
                    rssb.setXml(xml);
                }
            } else {
                // recupera gli allegati
                List<FormDataBodyPart> files = parts.get(key);
                // ammetto solo un file per chiave
                if (files != null && files.size() > 0) {
                    FormDataBodyPart parte = files.get(0);
                    File file = getFileFromPart(parte);
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

	private static final File getFileFromPart(FormDataBodyPart parte) throws Exception {
		Object obj = parte.getEntity();
		File f = null;
		if (obj instanceof File) {
			f = (File) obj;
		} else {
			BodyPartEntity bpe = null;
			InputStream is = null;
			if (obj instanceof BodyPartEntity) {
				bpe = (BodyPartEntity) obj;
				is = bpe.getInputStream();
			} if (obj instanceof InputStream) {
				is = (InputStream) obj;
			} 
			f = File.createTempFile("rep", "tmp");
	        OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
	        try {
	            ReaderWriter.writeTo(is, out);
	        } finally {
	            if (bpe != null) bpe.cleanup();
	            if (out != null) out.close();
	        }
		}//else
        return f;
    }
	
    /**
     * Verifica se è stato riscontrato un errore nel core se si recupera i
     * dati dell'errore e costruisci una CoreException, altrimenti costruisce
     * una coreexception unmanaged
     * @param cr
     * @return
     */
	public static final CoreException buildCoreException(UniformInterfaceException ufe) {
        CoreException ce = null;
        try {
            ClientResponse cr = ufe.getResponse();
            // if(cr.hasEntity())
            FormDataMultiPart fom = cr.getEntity(FormDataMultiPart.class);
            // cerco la part errore
            FormDataBodyPart errorpart = fom.getField("error");
            if (errorpart != null) {
                log.debug("trovato errore nella response");
                CoreExceptionBean ceb = errorpart.getEntityAs(CoreExceptionBean.class);
                ce = new CoreException(ceb);
            } else {
                log.fatal("impossibile leggere coreexceptionbean dalla response");
                throw new Exception("trapped");
            }

            // CoreExceptionBean ceb = cr.getEntity(CoreExceptionBean.class);

        } catch (Exception e) {
            // se non è una eccezione sul server rilancio l'eccezione
            // originaria
            // throw new InvocationException(ufe);
            ce = new CoreException(new CoreExceptionBean(ufe));
        }
        return ce;
    }
	

	public static final CoreException buildCoreException(Response response) {
        CoreException ce = null;
        try {
            final FormDataMultiPart fdmp = (FormDataMultiPart) response.getEntity();
            final FormDataBodyPart errorpart = fdmp.getField("error");
            if (errorpart != null) {
                log.debug("trovato errore nella response");
                final CoreExceptionBean ceb = errorpart.getEntityAs(CoreExceptionBean.class);
                ce = new CoreException(ceb);
            } 
        } catch (Exception e) {
            log.error("Errore in buildCoreException: "+String.valueOf(e.getLocalizedMessage()));
        }
        return ce;
    }

	
}
