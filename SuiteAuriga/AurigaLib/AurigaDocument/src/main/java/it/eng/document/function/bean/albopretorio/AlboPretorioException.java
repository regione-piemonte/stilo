/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.GsonBuilder;

@XmlRootElement(name="alboPretorioException")
public class AlboPretorioException {
	
	private String status;
	private String errorMessage;
	
	public String getStatus() {
		return status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	public Response throwException(String status, String errorMessage) {
		this.status=status;
		this.errorMessage=errorMessage;	
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(this, AlboPretorioException.class);

		return Response.ok(res).build();
	}
	
	
}
