/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class Firmatari {

	private String subject;
	private String id;
	private String figlioDi;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFiglioDi() {
		return figlioDi;
	}
	public void setFiglioDi(String figlioDi) {
		this.figlioDi = figlioDi;
	}
	
	@Override
	public String toString() {
		return String.format("Firmatari [subject=%s, id=%s, figlioDi=%s]", subject, id, figlioDi);
	}
}