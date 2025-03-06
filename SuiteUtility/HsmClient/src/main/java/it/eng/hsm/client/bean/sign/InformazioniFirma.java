package it.eng.hsm.client.bean.sign;

import java.util.List;

/**
 * 
 * @author DANCRIST
 *
 */

public class InformazioniFirma {
	
	private String name;	 
	private Boolean signed;
	private Integer page;
	private List<Integer> position;
	private Integer pageH;
	private Boolean visible;	   
	private String signer;	   	    
	private String location;  
	private String reason;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getSigned() {
		return signed;
	}
	public void setSigned(Boolean signed) {
		this.signed = signed;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public List<Integer> getPosition() {
		return position;
	}
	public void setPosition(List<Integer> position) {
		this.position = position;
	}
	public Integer getPageH() {
		return pageH;
	}
	public void setPageH(Integer pageH) {
		this.pageH = pageH;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}