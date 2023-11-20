/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
	name = "DominioResults", 
	entities = { 
		@EntityResult(
			entityClass = Dominio.class, 
			fields = {
				@FieldResult(name = "codice", column = "CODICE"), 
				@FieldResult(name = "descrizione", column = "DESCRIZIONE") 
			}
		)
	}
)
public class Dominio implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	@Id
	private String codice;
	private String descrizione;

	public Dominio() {
	}

	public Dominio(String codice) {
		this.codice = codice;
	}

	public Dominio(String codice, String descrizione) {
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Dominio))
			return false;
		Dominio castOther = (Dominio) other;

		return ((this.getCodice() == castOther.getCodice()) || (this
				.getCodice() != null && castOther.getCodice() != null && this
				.getCodice().equals(castOther.getCodice())))
				&& ((this.getDescrizione() == castOther.getDescrizione()) || (this
						.getDescrizione() != null
						&& castOther.getDescrizione() != null && this
						.getDescrizione().equals(castOther.getDescrizione())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCodice() == null ? 0 : this.getCodice()
						.hashCode());
		result = 37
				* result
				+ (getDescrizione() == null ? 0 : this.getDescrizione()
						.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("Dominio [codice=%s, descrizione=%s]", codice, descrizione);
	}
}
