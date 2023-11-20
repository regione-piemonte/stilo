/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class BeanUtil {
	
	//merge two bean by discovering differences private 
	public static void compareObjects(AbstractBean oldBean, AbstractBean newBean) throws Exception {
		BeanMap map = new BeanMap(oldBean);
        PropertyUtilsBean propUtils = new PropertyUtilsBean();
        for (Object propNameObject : map.keySet()) {
            String propertyName = (String) propNameObject;
            Object oldProperty = propUtils.getProperty(oldBean, propertyName);
            Object newProperty = propUtils.getProperty(newBean, propertyName);
            if(oldProperty == null) {    
            	if(newProperty == null) {
            		newBean.getUpdatedProperties().remove(propertyName);
            	} else {            		
            		newBean.getUpdatedProperties().add(propertyName); 
            	}
            } else {            	
            	if(!oldProperty.equals(newProperty)) {               
            		newBean.getUpdatedProperties().add(propertyName);                
            	} else {
            		newBean.getUpdatedProperties().remove(propertyName);
            	}
            }
        }
    }

}
