package it.eng.core.service.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmptyListParamDescription extends AttachDescription implements Serializable{

	private static final long serialVersionUID = 5874801530774354043L;
	
	private List<EmptyListPropertyDescription> epmtyList;
	
	private BeanParamsAttachDescription bean;
	
	//parte del bean serializzata senza attach 
	private String serializedData;
	
	@XmlRootElement
	public static class EmptyListPropertyDescription{
		
		//prop del bean che è inviata come attach
		private String propName;
		//tipo di collection
		private String collectionClassName;
		public String getPropName() {
			return propName;
		}

		public void setPropName(String propName) {
			this.propName = propName;
		}

		public String getCollectionClassName() {
			return collectionClassName;
		}

		public void setCollectionClassName(String collectionClassName) {
			this.collectionClassName = collectionClassName;
		}
		
	}

	public List<EmptyListPropertyDescription> getEpmtyList() {
		return epmtyList;
	}

	public void setEpmtyList(List<EmptyListPropertyDescription> epmtyList) {
		this.epmtyList = epmtyList;
	}

	public void setBean(BeanParamsAttachDescription bean) {
		this.bean = bean;
	}

	public BeanParamsAttachDescription getBean() {
		return bean;
	}

	public void setSerializedData(String serializedData) {
		this.serializedData = serializedData;
	}

	public String getSerializedData() {
		return serializedData;
	}
}
