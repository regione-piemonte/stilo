package it.eng.dm.sira.service.ost;

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
