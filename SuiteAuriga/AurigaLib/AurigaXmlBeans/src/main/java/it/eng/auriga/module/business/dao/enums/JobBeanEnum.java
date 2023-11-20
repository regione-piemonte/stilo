/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Elenca i vari stati che può assumere un record in BMT_JOBS
 * 
 * @author massimo malvestio
 *
 */
public enum JobBeanEnum {

	INIZIALE("I"), IN_ESECUZIONE("E"), COMPLETATO_CON_SUCCESSO("R"), COMPLETATO_CON_ERRORE("X");

	private String code;

	private JobBeanEnum(String code) {
		setCode(code);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;

	}

}
