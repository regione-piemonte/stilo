/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;
import java.io.Serializable;

/**
 * Bean che contiene informazioni sulla casella
 * 
 * @author Federico Cacco
 *
 */

public class InfoCasellaBean extends VisualBean implements Serializable {

	private static final long serialVersionUID = -4028847205561266615L;

	@NumeroColonna(numero = "1")
	private String idAccount;
	@NumeroColonna(numero = "2")
	private String account;
	@NumeroColonna(numero = "3")
	private String tipoMail;

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTipoMail() {
		return tipoMail;
	}

	public void setTipoMail(String tipoMail) {
		this.tipoMail = tipoMail;
	}

}