package it.eng.core.service.serialization.impl;

import it.eng.core.service.bean.AttachDeSerializationException;
import it.eng.core.service.bean.AttachDescription;
import it.eng.core.service.bean.BeanParamsAttachDescription;
import it.eng.core.service.bean.CollectionFileAttachDescription;
import it.eng.core.service.bean.EmptyListParamDescription;
import it.eng.core.service.bean.FileAttachDescription;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.IServiceSerialize;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonServiceSerialize implements IServiceSerialize {
    private static final Logger log = Logger.getLogger(JsonServiceSerialize.class);

    @Override
    public String serialize(Object obj) throws Exception {
        // FIXME manage error
        // Gson gson = new Gson();
        Gson gson = new GsonBuilder().setExclusionStrategies(new XmlSerializeExlusionStrategy())
        // .serializeNulls() <-- uncomment to serialize NULL fields as well
                .create();
        String json = gson.toJson(obj);
        return json;
    }

    @Override
    public Serializable deserialize(String str, Class classe, Type type) throws Exception {
        Gson gson = new Gson();
        Serializable obj = null;
        //
        log.debug("deserialization param str:\n" + str + "\n" + "classe:" + classe + " type=" + type);
        // verifico se posso deserializzarlo come attachDescription se non ci
        // riesco
        // deserializzo come tutti gli altri oggetti
        obj = deserializaAttach(gson, str, classe, type);
        if (obj == null) {
            if (type != null) {
                obj = (Serializable) gson.fromJson(str, type);
            } else {
                obj = (Serializable) gson.fromJson(str, classe);
            }
            log.debug("stringa deserializzata senza attach ");
        } else {
            log.debug("stringa deserializzata come attach " + obj);
        }
        return obj;

    }

    /**
     * verifica se la stringa ricevuta va deserializzata come
     * {@link AttachDescription} se ritorna null vuol dire che non è un
     * attachDescription
     * @param gson
     * @param str
     * @param clazz
     * @param type
     * @return
     * @throws Exception
     */
    public Serializable deserializaAttach(Gson gson, String str, Class clazz, Type type) throws Exception {
        Serializable obj = null;
        try {
            // se la classe originaria è un file deserializza come
            // fileattachdescription
            if (clazz.equals(File.class)) {
                log.debug("tento deserializing come " + FileAttachDescription.class);
                obj = gson.fromJson(str, FileAttachDescription.class);

            }
            // per capire se è una collection di file verifico se
            // il bean deserializzato contiene il marker giusto
            else if (isCollAttachDescription(str)
            // Collection.class.isAssignableFrom(clazz)
            // && (type instanceof ParameterizedType) &&
            // ((ParameterizedType)type).getActualTypeArguments()[0].equals(File.class)
            ) {
                CollectionFileAttachDescription tmp = gson.fromJson(str, CollectionFileAttachDescription.class);
                if (tmp != null && tmp.getMarker() != null && tmp.getMarker().equals(CollectionFileAttachDescription.collMarker)) {
                    obj = tmp;
                }
                // obj=fileCollectionDeser(gson, str);

            }// per capire se è un bean con attach verifico se
             // c'è il marker
            else if (isBeanAttachDescription(str)) {
                BeanParamsAttachDescription tmp = gson.fromJson(str, BeanParamsAttachDescription.class);
                if (tmp != null && tmp.getMarker() != null && tmp.getMarker().equals(BeanParamsAttachDescription.beanParamMarker)) {
                    obj = tmp;
                }
            } else {
                try {
                    EmptyListParamDescription lEmptyListParamDescription = gson.fromJson(str, EmptyListParamDescription.class);
                    if (StringUtils.isNotEmpty(lEmptyListParamDescription.getSerializedData())) {
                        return lEmptyListParamDescription;
                    }
                } catch (Exception ex) {
                    // non riesce a deserializzare
                    return null;
                }
            }
        } catch (Exception ex) {
            log.fatal("deserializazione fallita per la stringa:\n" + str, ex);
            throw new AttachDeSerializationException("deserializazione fallita", ex);
        }
        return obj;
    }

    private boolean isCollAttachDescription(String str) {
        return str != null && str.contains(CollectionFileAttachDescription.collMarker);
    }

    private boolean isBeanAttachDescription(String str) {
        return str != null && str.contains(BeanParamsAttachDescription.beanParamMarker);
    }

    // private Serializable fileCollectionDeser(Gson gson,String json){
    // Serializable obj=null;
    // log.debug("tento deserializing come "+CollectionFileAttachDescription.class);
    // try{
    // CollectionFileAttachDescription temp =
    // (CollectionFileAttachDescription)gson.fromJson(json,
    // CollectionFileAttachDescription.class);
    // if(temp.getCollectionClassName()!=null){
    // //considero la deserializzazione corretta se riesco a costruire un
    // //CollectionFileAttachDescription con collectionclassname valorizzato
    // obj=temp;
    // }
    // }catch(Exception ex){
    // log.debug("eccezione prevista ",ex);
    // }
    // return obj;
    // }

    @Override
    public void initByClientConfig(Configuration config) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void initByServerConfig() throws Exception {
        // TODO Auto-generated method stub

    }

}
