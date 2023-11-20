/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttributoXRicercaBean {
	
	// 1: Nome identificativo dell'attributo
	@NumeroColonna(numero = "1")
	private String nome;
	
	// 2: Label dell'attributo
	@NumeroColonna(numero = "2")
	private String label;
	
	// 3: Tipo dell'attributo
	@NumeroColonna(numero = "3")
	private String tipo;
	
	// 4: Flg 1/0 che indica se sull'attributo testo serve prevedere la ricerca full-text (1) o meno (0)
	@NumeroColonna(numero = "4")
	private String flgFullText;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFlgFullText() {
		return flgFullText;
	}
	
	public void setFlgFullText(String flgFullText) {
		this.flgFullText = flgFullText;
	}	
	
}


