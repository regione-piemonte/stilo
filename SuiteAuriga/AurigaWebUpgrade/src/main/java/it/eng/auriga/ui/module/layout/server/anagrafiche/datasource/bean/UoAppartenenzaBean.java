/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class UoAppartenenzaBean implements Comparable<UoAppartenenzaBean>{

	@NumeroColonna(numero = "1")
	private String idUo;
	@NumeroColonna(numero = "2")
	private String descrizione;
	

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int compareTo(UoAppartenenzaBean o) {
		return idUo.compareTo(o.getIdUo());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof UoAppartenenzaBean))
			return false;
		UoAppartenenzaBean objConv = (UoAppartenenzaBean) obj;
		return idUo.equals(objConv.getIdUo());
	}

}
