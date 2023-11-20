/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

/**
 * 
 * @author dbe4235
 *
 */

public class SmistamentoAttiRagioneriaTreeNodeBean extends TreeNodeBean {
	
	/**
	 * conteggio contenuti nodo (solo ultimo livello)
	 */
	private String conteggiNodo;

	/**
	 * IdProcessType
	 */
	private String idProcessType;

	/**
	 * Assegnatario (SV o UO + ID_UO o ID_SCRIVNIA)
	 */
	private String assegnatario;

	/**
	 *  Data inoltro a ragioneria (in formato DD/MM/RRRR)
	 */
	private Date dtInoltroRagioneria;

	public String getConteggiNodo() {
		return conteggiNodo;
	}

	public void setConteggiNodo(String conteggiNodo) {
		this.conteggiNodo = conteggiNodo;
	}

	public String getIdProcessType() {
		return idProcessType;
	}

	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public Date getDtInoltroRagioneria() {
		return dtInoltroRagioneria;
	}

	public void setDtInoltroRagioneria(Date dtInoltroRagioneria) {
		this.dtInoltroRagioneria = dtInoltroRagioneria;
	}
	
}