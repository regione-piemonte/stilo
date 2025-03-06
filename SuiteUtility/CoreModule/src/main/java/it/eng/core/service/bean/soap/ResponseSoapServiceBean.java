package it.eng.core.service.bean.soap;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * bean per la risposta alla chiamata soap
 * @author Russo
 *
 */
public class ResponseSoapServiceBean {
	 
	private String xml;
	 
	 private Map<String,File> attach;
	 
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public Map<String, File> getAttach() {
		return attach;
	}
	public void setAttach(Map<String, File> attach) {
		this.attach = attach;
	}
 
}
