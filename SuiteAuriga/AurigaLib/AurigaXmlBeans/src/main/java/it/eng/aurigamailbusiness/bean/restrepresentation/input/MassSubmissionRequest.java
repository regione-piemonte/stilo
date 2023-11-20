/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.ColMapping;

@XmlRootElement(name = "richiestaInvioMassivoEmail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Richiesta per l'invio massivo di email")
public class MassSubmissionRequest {

	@XmlElement(name = "userId", required = true)
	@ApiModelProperty(example = "applicazione", required = true, value = "Identificativo dell'utente")
	private String userId;

	@XmlElement(name = "password", required = true)
	@ApiModelProperty(example = "Pdasf$78FF", required = true, value = "Password dell'utente")
	private String password;

	@XmlElement(name = "indirizzoCasellaInvio", required = true) /* mittente */
	@ApiModelProperty(example = "nomeutente@dominio.it", required = true, value = "Indirizzo di posta elettronica del mittente")
	private String addressFrom;

	@XmlElement(name = "oggettoMail", required = true)
	@ApiModelProperty(example = "breve descrizione del contenuto del messaggio", required = true, value = "Dovrebbe aiutare il destinatario a capire il contenuto del messaggio")
	private String subject;

	@XmlElement(name = "corpoMail", required = true)
	@ApiModelProperty(example = "contenuto del messaggio", required = true, value = "Contenuto informativo che il mittente vuol comunicare ai destinatari")
	private String body;

	@XmlElementWrapper(name="colonneXls")
	@XmlElement(name = "colonna", required = true)
	@ApiModelProperty(value = "Mappatura delle colonne", required = true)
	private List<ColMapping> columns = new ArrayList<ColMapping>(0);

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddressFrom() {
		return addressFrom;
	}

	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<ColMapping> getColumns() {
		if (columns == null) {
			columns = new ArrayList<ColMapping>();
		}
		return columns;
	}

	public void setColumns(List<ColMapping> columns) {
		this.columns = columns;
	}

}
