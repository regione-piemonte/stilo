/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
	name = "DocumentiProvider", 
	entities = { 
		@EntityResult(
			entityClass = DocumentiProvider.class, 
			fields = {
				@FieldResult(name = "idDoc",			column = "ID_DOC"),
				@FieldResult(name = "displayFilename",	column = "DISPLAY_FILENAME"),
			    @FieldResult(name = "rifInRepository",	column = "RIF_IN_REPOSITORY"),
			}
		)
	}
)
public class DocumentiProvider implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	@Id
	private BigDecimal idDoc;
	private String displayFilename;
	private String rifInRepository;

	public DocumentiProvider() {
	}
	
	
   
	

	public DocumentiProvider(BigDecimal idDoc, String displayFilename, String rifInRepository) {
		super();
		this.idDoc = idDoc;
		this.displayFilename = displayFilename;
		this.rifInRepository = rifInRepository;
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentiProvider other = (DocumentiProvider) obj;
		if (displayFilename == null) {
			if (other.displayFilename != null)
				return false;
		} else if (!displayFilename.equals(other.displayFilename))
			return false;
		if (idDoc == null) {
			if (other.idDoc != null)
				return false;
		} else if (!idDoc.equals(other.idDoc))
			return false;
		if (rifInRepository == null) {
			if (other.rifInRepository != null)
				return false;
		} else if (!rifInRepository.equals(other.rifInRepository))
			return false;
		return true;
	}





	@Override
	public String toString() {
		return "DocumentiProvider [idDoc=" + idDoc + ", displayFilename=" + displayFilename + ", rifInRepository="
				+ rifInRepository + "]";
	}

     

}
