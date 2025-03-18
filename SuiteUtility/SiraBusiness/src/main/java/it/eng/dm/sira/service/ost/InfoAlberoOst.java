/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class InfoAlberoOst {

	private List<InfoBeanOut> ostFigli;

	private InfoBeanOut ostPadre;

	public List<InfoBeanOut> getOstFigli() {
		return ostFigli;
	}

	public void setOstFigli(List<InfoBeanOut> ostFigli) {
		this.ostFigli = ostFigli;
	}

	public InfoBeanOut getOstPadre() {
		return ostPadre;
	}

	public void setOstPadre(InfoBeanOut ostPadre) {
		this.ostPadre = ostPadre;
	}
}
