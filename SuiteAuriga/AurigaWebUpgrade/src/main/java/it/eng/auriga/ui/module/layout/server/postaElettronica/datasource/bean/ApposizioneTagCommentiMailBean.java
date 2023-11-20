/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author DANCRIST
 *
 */

public class ApposizioneTagCommentiMailBean {
	
	private List<PostaElettronicaBean> listaEmail;
	private List<ItemTagCommentiMailBean> itemTagCommentiMail;
	private HashMap<String, String> errorMessages;
	
	public List<PostaElettronicaBean> getListaEmail() {
		return listaEmail;
	}

	public void setListaEmail(List<PostaElettronicaBean> listaEmail) {
		this.listaEmail = listaEmail;
	}

	public List<ItemTagCommentiMailBean> getItemTagCommentiMail() {
		return itemTagCommentiMail;
	}

	public void setItemTagCommentiMailBean(List<ItemTagCommentiMailBean> itemTagCommentiMail) {
		this.itemTagCommentiMail = itemTagCommentiMail;
	}

	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
