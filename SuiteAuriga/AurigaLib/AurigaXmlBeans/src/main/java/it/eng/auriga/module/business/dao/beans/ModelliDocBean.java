/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import it.eng.core.business.beans.AbstractBean;

public class ModelliDocBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 8149931418143744545L;
	
	private BigDecimal idModello;
	private BigDecimal idSpAoo;
	private String codApplOwner;
	private String codIstApplOwner;
	private String nomeModello;
	private String desModello;
	private BigDecimal idDoc;
	private BigDecimal idDocXml;
	private BigDecimal idDocPdf;
	private BigDecimal idDocHtml;
	private BigDecimal nroOrdine;
	private Serializable bookmark;
	private String note;
	private Boolean flgAnn;
	private String motiviAnn;
	private Serializable altriAttributi;
	private Serializable infoStorico;
	private String provCiModello;
	private Boolean flgHidden;
	private Boolean flgLocked;
	private Boolean flgProfComp;
	private Date tsIns;
	private BigDecimal idUserIns;
	private Date tsLastUpd;
	private BigDecimal idUserLastUpd;
	private String tipoModello;
	
	private Map<String, Map<String, String>> mappaAttributiDimanici;

	public BigDecimal getIdModello() {
		return this.idModello;
	}

	public void setIdModello(BigDecimal idModello) {
		this.idModello = idModello;
		this.getUpdatedProperties().add("idModello");
	}

	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
		this.getUpdatedProperties().add("idSpAoo");
	}

	public String getCodApplOwner() {
		return this.codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
		this.getUpdatedProperties().add("codApplOwner");
	}

	public String getCodIstApplOwner() {
		return this.codIstApplOwner;
	}

	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
		this.getUpdatedProperties().add("codIstApplOwner");
	}

	public String getNomeModello() {
		return this.nomeModello;
	}

	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
		this.getUpdatedProperties().add("nomeModello");
	}

	public String getDesModello() {
		return this.desModello;
	}

	public void setDesModello(String desModello) {
		this.desModello = desModello;
		this.getUpdatedProperties().add("desModello");
	}

	public BigDecimal getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
		this.getUpdatedProperties().add("idDoc");
	}

	public BigDecimal getIdDocXml() {
		return this.idDocXml;
	}

	public void setIdDocXml(BigDecimal idDocXml) {
		this.idDocXml = idDocXml;
		this.getUpdatedProperties().add("idDocXml");
	}

	public BigDecimal getIdDocPdf() {
		return this.idDocPdf;
	}

	public void setIdDocPdf(BigDecimal idDocPdf) {
		this.idDocPdf = idDocPdf;
		this.getUpdatedProperties().add("idDocPdf");
	}

	public BigDecimal getIdDocHtml() {
		return this.idDocHtml;
	}

	public void setIdDocHtml(BigDecimal idDocHtml) {
		this.idDocHtml = idDocHtml;
		this.getUpdatedProperties().add("idDocHtml");
	}

	public BigDecimal getNroOrdine() {
		return this.nroOrdine;
	}

	public void setNroOrdine(BigDecimal nroOrdine) {
		this.nroOrdine = nroOrdine;
		this.getUpdatedProperties().add("nroOrdine");
	}

	public Serializable getBookmark() {
		return this.bookmark;
	}

	public void setBookmark(Serializable bookmark) {
		this.bookmark = bookmark;
		this.getUpdatedProperties().add("bookmark");
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
		this.getUpdatedProperties().add("note");
	}

	public Boolean getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(Boolean flgAnn) {
		this.flgAnn = flgAnn;
		this.getUpdatedProperties().add("flgAnn");
	}

	public String getMotiviAnn() {
		return this.motiviAnn;
	}

	public void setMotiviAnn(String motiviAnn) {
		this.motiviAnn = motiviAnn;
		this.getUpdatedProperties().add("motiviAnn");
	}

	public Serializable getAltriAttributi() {
		return this.altriAttributi;
	}

	public void setAltriAttributi(Serializable altriAttributi) {
		this.altriAttributi = altriAttributi;
		this.getUpdatedProperties().add("altriAttributi");
	}

	public Serializable getInfoStorico() {
		return this.infoStorico;
	}

	public void setInfoStorico(Serializable infoStorico) {
		this.infoStorico = infoStorico;
		this.getUpdatedProperties().add("infoStorico");
	}

	public String getProvCiModello() {
		return this.provCiModello;
	}

	public void setProvCiModello(String provCiModello) {
		this.provCiModello = provCiModello;
		this.getUpdatedProperties().add("provCiModello");
	}

	public Boolean getFlgHidden() {
		return this.flgHidden;
	}

	public void setFlgHidden(Boolean flgHidden) {
		this.flgHidden = flgHidden;
		this.getUpdatedProperties().add("flgHidden");
	}

	public Boolean getFlgLocked() {
		return this.flgLocked;
	}

	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
		this.getUpdatedProperties().add("flgLocked");
	}

	public Boolean getFlgProfComp() {
		return this.flgProfComp;
	}

	public void setFlgProfComp(Boolean flgProfComp) {
		this.flgProfComp = flgProfComp;
		this.getUpdatedProperties().add("flgProfComp");
	}

	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
		this.getUpdatedProperties().add("tsIns");
	}

	public BigDecimal getIdUserIns() {
		return this.idUserIns;
	}

	public void setIdUserIns(BigDecimal idUserIns) {
		this.idUserIns = idUserIns;
		this.getUpdatedProperties().add("idUserIns");
	}

	public Date getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
		this.getUpdatedProperties().add("tsLastUpd");
	}

	public BigDecimal getIdUserLastUpd() {
		return this.idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
		this.getUpdatedProperties().add("idUserLastUpd");
	}

	public String getTipoModello() {
		return this.tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
		this.getUpdatedProperties().add("tipoModello");
	}
	
	public Map<String, Map<String, String>> getMappaAttributiDimanici() {
		return mappaAttributiDimanici;
	}

	public void setMappaAttributiDimanici(Map<String, Map<String, String>> mappaAttributiDimanici) {
		this.mappaAttributiDimanici = mappaAttributiDimanici;
		this.getUpdatedProperties().add("mappaAttributiDimanici");
	}

}
