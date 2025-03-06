package it.eng.core.service.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.core.service.bean.EmptyListParamDescription.EmptyListPropertyDescription;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

public class EmptyListHelper {
	private static final Logger log=Logger.getLogger(EmptyListHelper.class);
	
	public static EmptyListParamDescription serializeAsAttach(int parpos,Serializable obj,IServiceSerialize serializerUtil) throws Exception{
		if(obj==null) 
			return null;
		if (obj.getClass().getAnnotation(XmlRootElement.class) == null) return null;
		List<EmptyListPropertyDescription> lList = retrieveEmptyList(obj);
		if (lList.size()>0){
			EmptyListParamDescription lEmptyListParamDescription = new EmptyListParamDescription();
			lEmptyListParamDescription.setParPosition(parpos);
			lEmptyListParamDescription.setEpmtyList(lList);
			return lEmptyListParamDescription;
		} else return null;
		
	}
	
	private static List<EmptyListPropertyDescription> retrieveEmptyList(Object obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWrapperImpl wrapperObj = BeanPropertyUtility.getBeanWrapper(obj);
		List<EmptyListPropertyDescription> lList = new ArrayList<EmptyListPropertyDescription>();
		Field[] lFields = obj.getClass().getDeclaredFields();
		for (Field lField : lFields){
			Object lObjectField = null;
			try {
				lObjectField = BeanPropertyUtility.getPropertyValue(obj, wrapperObj, lField.getName());
				// lObjectField = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(obj, lField.getName());
			} catch (NoSuchMethodException e) {
				lObjectField = null;
			}
			
			if (lObjectField == null) {} 
			else if (lObjectField instanceof Collection<?>){
				
				if (((Collection<?>)lObjectField).size()==0){
					EmptyListPropertyDescription lEmptyListPropertyDescription = new EmptyListPropertyDescription();
					ParameterizedType genericListType = (ParameterizedType) lField.getGenericType();
					Class<?> genericListClass = (Class<?>) genericListType.getActualTypeArguments()[0];
					lEmptyListPropertyDescription.setCollectionClassName(genericListClass.getName());
					lEmptyListPropertyDescription.setPropName(lField.getName());
					lList.add(lEmptyListPropertyDescription);
				}
			}
		}
		return lList;
	}
}

