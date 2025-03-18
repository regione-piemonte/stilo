/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FasiBeanOut {
	
	private List<String> fasi;

	private String faseAttiva;

	public List<String> getFasi() {
		return fasi;
	}

	public void setFasi(List<String> fasi) {
		this.fasi = fasi;
	}

	public String getFaseAttiva() {
		return faseAttiva;
	}

	public void setFaseAttiva(String faseAttiva) {
		this.faseAttiva = faseAttiva;
	}

}
