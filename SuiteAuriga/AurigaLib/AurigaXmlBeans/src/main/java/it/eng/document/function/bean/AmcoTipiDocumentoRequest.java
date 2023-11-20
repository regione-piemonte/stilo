/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoTipiDocumentoRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String descrizione;
    private String finanziaria;
    
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getFinanziaria() {
		return finanziaria;
	}
	
	public void setFinanziaria(String finanziaria) {
		this.finanziaria = finanziaria;
	}
	
	@Override
	public String toString() {
		return "AmcoTipiDocumentoRequest [nome=" + nome + ", descrizione=" + descrizione + ", finanziaria="
				+ finanziaria + "]";
	}
	
}
