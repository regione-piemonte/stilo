/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
	name = "CountResults", 
	entities = { 
		@EntityResult(
			entityClass = Conteggio.class, 
			fields = {
				@FieldResult(name = "nomeEntita", column = "NOME_ENTITA"), 
				@FieldResult(name = "conteggio", column = "CONTEGGIO") 
			}
		)
	}
)
public class Conteggio implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	@Id
	private String nomeEntita;
	private String conteggio;

	public Conteggio() {
	}

	public Conteggio(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	public Conteggio(String nomeEntita, String conteggio) {
		this.nomeEntita = nomeEntita;
		this.conteggio = conteggio;
	}

	public String getNomeEntita() {
		return this.nomeEntita;
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	public String getConteggio() {
		return this.conteggio;
	}

	public void setConteggio(String conteggio) {
		this.conteggio = conteggio;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Conteggio))
			return false;
		Conteggio castOther = (Conteggio) other;

		return ((this.getNomeEntita() == castOther.getNomeEntita()) 
				|| (this.getNomeEntita() != null && castOther.getNomeEntita() != null 
					&& this.getNomeEntita().equals(castOther.getNomeEntita())))
				&& ((this.getConteggio() == castOther.getConteggio()) 
						|| (this.getConteggio() != null && castOther.getConteggio() != null 
							&& this.getConteggio().equals(castOther.getConteggio())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNomeEntita() == null ? 0 : this.getNomeEntita().hashCode());
		result = 37
				* result
				+ (getConteggio() == null ? 0 : this.getConteggio().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("Conteggio [nomeEntita=%s, conteggio=%s]", nomeEntita, conteggio);
	}
}
