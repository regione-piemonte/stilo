package it.eng.core.service.serialization.impl.base;

import it.eng.core.annotation.XmlWrapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
@XmlWrapper(forClass="java.lang.Byte")
@XmlRootElement
public class ByteWrapper implements Serializable,XmlWrappable<Byte>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3478889231952408694L;
	//@XmlValue
	Byte value;
	//default costructor
	public ByteWrapper() {

	}

	public ByteWrapper(Byte value) {
		super();
		this.value = value;
	}

	public Byte getData(){
		return value;
	}

	@Override
	public void setData(Byte data) {
		this.value=data;
		
	}
}


