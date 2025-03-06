package it.eng.core.service.serialization.impl.base;

import it.eng.core.annotation.XmlWrapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlWrapper(forClass="java.lang.String")
@XmlRootElement
public class StringWrapper  implements Serializable,XmlWrappable<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3478889231952408694L;
	//@XmlValue
	String value;
	//default costructor
	public StringWrapper() {

	}

	public StringWrapper(String value) {
		super();
		this.value = value;
	}
	@Override
	public String getData(){
		return value;
	}

	@Override
	public void setData(String data) {
		this.value=data;
		
	}
}


