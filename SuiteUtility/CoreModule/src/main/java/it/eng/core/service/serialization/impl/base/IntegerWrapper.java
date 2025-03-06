package it.eng.core.service.serialization.impl.base;

import it.eng.core.annotation.XmlWrapper;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlWrapper(forClass="java.lang.Integer")
@XmlRootElement
public class IntegerWrapper  implements Serializable,XmlWrappable<Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3478889231952408694L;
	//@XmlValue
	Integer value;
	//default costructor
	public IntegerWrapper() {

	}

	public IntegerWrapper(Integer value) {
		super();
		this.value = value;
	}
	@Override
	public Integer getData(){
		return value;
	}

	@Override
	public void setData(Integer data) {
		this.value=data;
		
	}
}


