package it.eng.core.service.serialization.impl.base;

import it.eng.core.annotation.XmlWrapper;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlWrapper(forClass="java.util.Date")
@XmlRootElement
public class DateWrapper  implements Serializable,XmlWrappable<Date>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3478889231952408694L;
	//@XmlValue
	Date value;
	//default costructor
	public DateWrapper() {

	}

	public DateWrapper(Date value) {
		super();
		this.value = value;
	}
	@Override
	public Date getData(){
		return value;
	}

	@Override
	public void setData(Date data) {
		this.value=data;
		
	}
}


