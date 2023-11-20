/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class Testata implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Proposta proposta;
	private Definitivo definitivo;
	private TipoEU eu;
	
	public Proposta getProposta() {
		return proposta;
	}
	
	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}
	
	public Definitivo getDefinitivo() {
		return definitivo;
	}
	
	public void setDefinitivo(Definitivo definitivo) {
		this.definitivo = definitivo;
	}
	
	public TipoEU getEu() {
		return eu;
	}
	
	public void setEu(TipoEU eu) {
		this.eu = eu;
	}

	@Override
	public String toString() {
		return "Testata [proposta=" + proposta + ", definitivo=" + definitivo + ", eu=" + eu + "]";
	}
	
}
