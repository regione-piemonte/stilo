package it.eng.core.service.serialization.impl.base;

import it.eng.core.annotation.XmlWrapper;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlWrapper(forClass="java.math.BigDecimal")
@XmlRootElement
public class BigDecimalWrapper  implements Serializable,XmlWrappable<BigDecimal>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3478889231952408694L;
	//@XmlValue
	BigDecimal value;
	//default costructor
	public BigDecimalWrapper() {

	}

	public BigDecimalWrapper(BigDecimal value) {
		super();
		this.value = value;
	}
	@Override
	public BigDecimal getData(){
		return value;
	}

	@Override
	public void setData(BigDecimal data) {
		this.value=data;
		
	}
}


