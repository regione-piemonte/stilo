/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Bean che identifica il body del messaggio
 * 
 * @author michele
 *
 */
public class BodyMessageBean {

	public BodyMessageBean() {
	}

	public BodyMessageBean(String type, String body) {
		super();
		this.type = type;
		this.body = body;
	}

	private String type;
	private String body;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
