package it.eng.core.service.serialization.impl;

import javax.xml.bind.annotation.XmlTransient;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
 public class XmlSerializeExlusionStrategy implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes f) {
        	boolean ret=false;
        	 
        	if(f.getAnnotation(XmlTransient.class)!=null){
        		ret=true;
        	}
            return ret;
        }

    }