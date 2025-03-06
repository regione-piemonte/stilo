package it.eng.core.service.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.eng.core.annotation.Attachment;
/**
 * bean utilizzato per serializzare un bean con proprietà che devono essere inviate come allegati
 * ogni paramentro del bean deve essere di tipo File e deve avere l'annotation {@link Attachment} e 
 * l'annotaion {@link XmlTransient}
 * @author Russo
 *
 */
@XmlRootElement
public class BeanParamsAttachDescription  extends AttachDescription implements Serializable{
	//parametro utilizzato per verificare se la stringa serializzata è un BeanParamsAttachDescription
	public static final String beanParamMarker="@@BeanParamsAttachDescription@@";
	//
	private String marker;
	//prop del bean serializzate come attach
	private ArrayList<BeanParamDescription> params= new ArrayList<BeanParamsAttachDescription.BeanParamDescription>();
	//parte del bean serializzata senza attach 
	private String serializedData;
	
	
	public String getMarker() {
		return marker;
	}


	public void setMarker(String marker) {
		this.marker = marker;
	}


	public static String getBeanparammarker() {
		return beanParamMarker;
	}


	public String getSerializedData() {
		return serializedData;
	}


	public void setSerializedData(String serializedData) {
		this.serializedData = serializedData;
	}


	public ArrayList<BeanParamDescription> getParams() {
		return params;
	}


	public void setParams(ArrayList<BeanParamDescription> params) {
		this.params = params;
	}

	public void addParam(BeanParamDescription par){
		params.add(par);
	}
	
	@XmlRootElement
	public static class BeanParamDescription{
		
		private String contentDisposition;//not used
		//prop del bean che è inviata come attach
		private String propName;
		//tipo della prop da inviare come attach
		private String propType;
		//tipo di collection
		private String collectionClassName;
		//id degli attach associati alla prop
		private List<String> idAttach= new ArrayList<String>();
		//uri degli attach associati alla prop
		private List<String> uriAttach= new ArrayList<String>();
				
		public void addIdAttach(String id){
			idAttach.add(id);
		}
		
		public List<String> getIdAttach() {
			return idAttach;
		}

		public void setIdAttach(List<String> idAttach) {
			this.idAttach = idAttach;
		}
		
		public void addUriAttach(String uri){
			uriAttach.add(uri);
		}

		public List<String> getUriAttach() {
			return uriAttach;
		}

		public void setUriAttach(List<String> uriUriAttach) {
			this.uriAttach = uriAttach;
		}

		public String getContentDisposition() {
			return contentDisposition;
		}

		public void setContentDisposition(String contentDisposition) {
			this.contentDisposition = contentDisposition;
		}

		public String getPropName() {
			return propName;
		}

		public void setPropName(String propName) {
			this.propName = propName;
		}

		public String getPropType() {
			return propType;
		}

		public void setPropType(String propType) {
			this.propType = propType;
		}

		public String getCollectionClassName() {
			return collectionClassName;
		}

		public void setCollectionClassName(String collectionClassName) {
			this.collectionClassName = collectionClassName;
		}
		
	}
}
