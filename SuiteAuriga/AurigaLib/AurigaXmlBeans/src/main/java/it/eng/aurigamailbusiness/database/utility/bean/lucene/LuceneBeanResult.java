/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class LuceneBeanResult {

	private String idEmail;

	private Float score;

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Float getScore() {
		return score;
	}
}
