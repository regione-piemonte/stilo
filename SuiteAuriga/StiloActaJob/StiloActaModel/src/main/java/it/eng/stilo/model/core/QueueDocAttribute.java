/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.model.common.AbstractEntity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "SA_QUEUE_DOC_ATTRIBUTES")
public class QueueDocAttribute implements Serializable {

	@Id
	@Column(name = "ID_ATTRIBUTE")
	private String idAttribute;
	
	@Column(name = "ID_DOCUMENT", nullable = false)
	private Long idDocument;

	@Column(name = "ATTRIBUTE_TAG", nullable = false)
	private String attributeTag;

	@Column(name = "STR_VALUE", nullable = true)
	private String strValue;

	public String getIdAttribute() {
		return idAttribute;
	}

	public void setIdAttribute(String idAttribute) {
		this.idAttribute = idAttribute;
	}

	public Long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(Long idDocument) {
		this.idDocument = idDocument;
	}

	public String getAttributeTag() {
		return attributeTag;
	}

	public void setAttributeTag(String attributeTag) {
		this.attributeTag = attributeTag;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	
	
}
