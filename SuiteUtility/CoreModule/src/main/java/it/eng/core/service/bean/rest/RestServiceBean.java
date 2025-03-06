package it.eng.core.service.bean.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che identifica la chiamata al servizio rest
 * @author michele
 */

@XmlRootElement
public class RestServiceBean {
	
	private String serializationType;
	private String servicename;
	private String operationname;
	private String tokenid;
	private String uuidtransaction;
	private String idDominio;
	private List<String> inputs;
	
	public String getSerializationType() {
		return serializationType;
	}

	public void setSerializationType(String serializationType) {
		this.serializationType = serializationType;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getOperationname() {
		return operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public String getUuidtransaction() {
		return uuidtransaction;
	}

	public void setUuidtransaction(String uuidtransaction) {
		this.uuidtransaction = uuidtransaction;
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	@Override
	public String toString() {
		return "RestServiceBean [serializationType=" + serializationType
				+ ", servicename=" + servicename + ", operationname="
				+ operationname + "]";
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	
	
}