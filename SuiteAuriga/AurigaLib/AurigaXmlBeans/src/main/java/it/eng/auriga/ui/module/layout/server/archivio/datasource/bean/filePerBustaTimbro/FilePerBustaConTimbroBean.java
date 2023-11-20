/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FilePerBustaConTimbroBean {
	
	private boolean flgVersionePubblicabile;
	private List<InfoFilePerBustaTimbro> listaFilePerBustaTimbro;
	
	public boolean isFlgVersionePubblicabile() {
		return flgVersionePubblicabile;
	}
	public List<InfoFilePerBustaTimbro> getListaFilePerBustaTimbro() {
		return listaFilePerBustaTimbro;
	}
	public void setFlgVersionePubblicabile(boolean flgVersionePubblicabile) {
		this.flgVersionePubblicabile = flgVersionePubblicabile;
	}
	public void setListaFilePerBustaTimbro(List<InfoFilePerBustaTimbro> listaFilePerBustaTimbro) {
		this.listaFilePerBustaTimbro = listaFilePerBustaTimbro;
	}
	
	
}
