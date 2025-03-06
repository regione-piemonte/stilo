package it.eng.hsm.client.bean;

public class MessageBean {

	private String code;
	private String description;
	private ResponseStatus status;
	
	public MessageBean() {
		super();
	}

	public MessageBean(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	
}