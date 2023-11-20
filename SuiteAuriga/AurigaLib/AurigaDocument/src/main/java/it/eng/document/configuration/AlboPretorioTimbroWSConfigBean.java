/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;

public class AlboPretorioTimbroWSConfigBean {

	private String tipoBarcodeModello;

	private OpzioniCopertinaTimbroBean opzioniTimbro;

	public String getTipoBarcodeModello() {
		return tipoBarcodeModello;
	}

	public void setTipoBarcodeModello(String tipoBarcodeModello) {
		this.tipoBarcodeModello = tipoBarcodeModello;
	}

	public OpzioniCopertinaTimbroBean getOpzioniTimbro() {
		return opzioniTimbro;
	}

	public void setOpzioniTimbro(OpzioniCopertinaTimbroBean opzioniTimbro) {
		this.opzioniTimbro = opzioniTimbro;
	}

}
