package it.eng.core.service.bean.soap;

import it.eng.core.service.bean.AttachDescription.FileIdAssociation;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * payload da inviare al ws in ascolto 
 * @author Russo
 *
 */
@XmlRootElement(name="serviceoperationinvoke",namespace="http://server.service.core.eng.it/")
public class RequestSoapServiceBean {
	@XmlTransient
	private URL url;//url di chiamata
	private List<FileIdAssociation> attachments= new ArrayList<FileIdAssociation>();
	private String serializationtype;
	private String uuidtransaction;
	private String tokenid;
	private String servicename;
	private String operationame;
	private List<String> objectsserialize;

	public String getSerializationtype() {
		return serializationtype;
	}
	public void setSerializationtype(String serializationtype) {
		this.serializationtype = serializationtype;
	}
	public String getUuidtransaction() {
		return uuidtransaction;
	}
	public void setUuidtransaction(String uuidtransaction) {
		this.uuidtransaction = uuidtransaction;
	}
	public String getTokenid() {
		return tokenid;
	}
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getOperationame() {
		return operationame;
	}
	public void setOperationame(String operationame) {
		this.operationame = operationame;
	}
	public List<String> getObjectsserialize() {
		return objectsserialize;
	}
	public void setObjectsserialize(List<String> objectsserialize) {
		this.objectsserialize = objectsserialize;
	}

	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	public List<FileIdAssociation> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<FileIdAssociation> attachments) {
		this.attachments = attachments;
	}
	 
	
}
