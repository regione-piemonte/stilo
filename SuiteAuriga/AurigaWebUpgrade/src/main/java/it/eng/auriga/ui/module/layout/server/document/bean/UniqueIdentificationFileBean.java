/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class UniqueIdentificationFileBean {

	private String descrizione;
	private String nome;
	private String hash;
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public boolean realEquals(UniqueIdentificationFileBean o) {
		if (((o.getDescrizione()==null && descrizione==null) || (o.getDescrizione()!=null && o.descrizione.equals(descrizione))) && 
		o.getHash().equals(hash) && o.getNome().equals(nome)) return true;
		else return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UniqueIdentificationFileBean)) return false;
		return realEquals((UniqueIdentificationFileBean)obj);
	}
	
	
}
