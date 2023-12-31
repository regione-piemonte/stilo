/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PttTmpStatprocedimenti generated by hbm2java
 */
@Entity
@Table(name = "PTT_TMP_STATPROCEDIMENTI")
public class PttTmpStatprocedimenti implements java.io.Serializable {

	private PttTmpStatprocedimentiId id;

	public PttTmpStatprocedimenti() {
	}

	public PttTmpStatprocedimenti(PttTmpStatprocedimentiId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "magicnumber", column = @Column(name = "MAGICNUMBER", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "appoggioIdProc", column = @Column(name = "APPOGGIO_ID_PROC", length = 12)),
			@AttributeOverride(name = "appoggioDtAvvio", column = @Column(name = "APPOGGIO_DT_AVVIO", length = 12)),
			@AttributeOverride(name = "appoggioTpPr", column = @Column(name = "APPOGGIO_TP_PR", length = 5)),
			@AttributeOverride(name = "appoggioAnnoPr", column = @Column(name = "APPOGGIO_ANNO_PR", length = 4)),
			@AttributeOverride(name = "appoggioNumPr", column = @Column(name = "APPOGGIO_NUM_PR", length = 7)),
			@AttributeOverride(name = "appoggioDNumCopia", column = @Column(name = "APPOGGIO_D_NUM_COPIA", length = 2)),
			@AttributeOverride(name = "anno", column = @Column(name = "ANNO", length = 4)),
			@AttributeOverride(name = "titolo", column = @Column(name = "TITOLO", length = 20)),
			@AttributeOverride(name = "classe", column = @Column(name = "CLASSE", length = 20)),
			@AttributeOverride(name = "sottoc", column = @Column(name = "SOTTOC", length = 20)),
			@AttributeOverride(name = "progr", column = @Column(name = "PROGR", length = 20)),
			@AttributeOverride(name = "sotto", column = @Column(name = "SOTTO", length = 20)),
			@AttributeOverride(name = "appoggioIdTpProc", column = @Column(name = "APPOGGIO_ID_TP_PROC", length = 8)),
			@AttributeOverride(name = "appoggioDesTpProc", column = @Column(name = "APPOGGIO_DES_TP_PROC", length = 200)),
			@AttributeOverride(name = "appoggioFlgStato", column = @Column(name = "APPOGGIO_FLG_STATO", length = 1)),
			@AttributeOverride(name = "appoggioDtChiusura", column = @Column(name = "APPOGGIO_DT_CHIUSURA", length = 12)),
			@AttributeOverride(name = "appoggioFlgChiusura", column = @Column(name = "APPOGGIO_FLG_CHIUSURA", length = 1)),
			@AttributeOverride(name = "appoggioFlgManuale", column = @Column(name = "APPOGGIO_FLG_MANUALE", length = 1)),
			@AttributeOverride(name = "sett", column = @Column(name = "SETT", length = 2)),
			@AttributeOverride(name = "serv", column = @Column(name = "SERV", length = 2)),
			@AttributeOverride(name = "uoc", column = @Column(name = "UOC", length = 2)),
			@AttributeOverride(name = "uos", column = @Column(name = "UOS", length = 2)),
			@AttributeOverride(name = "post", column = @Column(name = "POST", length = 4)),
			@AttributeOverride(name = "appoggioDesRsp", column = @Column(name = "APPOGGIO_DES_RSP", length = 140)),
			@AttributeOverride(name = "setti", column = @Column(name = "SETTI", length = 2)),
			@AttributeOverride(name = "servi", column = @Column(name = "SERVI", length = 2)),
			@AttributeOverride(name = "uoci", column = @Column(name = "UOCI", length = 2)),
			@AttributeOverride(name = "uosi", column = @Column(name = "UOSI", length = 2)),
			@AttributeOverride(name = "posti", column = @Column(name = "POSTI", length = 4)),
			@AttributeOverride(name = "appoggioDesRspOper", column = @Column(name = "APPOGGIO_DES_RSP_OPER", length = 140)),
			@AttributeOverride(name = "appoggioDtSosp", column = @Column(name = "APPOGGIO_DT_SOSP", length = 12)),
			@AttributeOverride(name = "appoggioNroSosp", column = @Column(name = "APPOGGIO_NRO_SOSP", length = 2)),
			@AttributeOverride(name = "appoggioDtInterr", column = @Column(name = "APPOGGIO_DT_INTERR", length = 12)),
			@AttributeOverride(name = "appoggioDtRipresa", column = @Column(name = "APPOGGIO_DT_RIPRESA", length = 12)),
			@AttributeOverride(name = "appoggioGgprev", column = @Column(name = "APPOGGIO_GGPREV", length = 40)),
			@AttributeOverride(name = "appoggioGgnetti", column = @Column(name = "APPOGGIO_GGNETTI", length = 40)),
			@AttributeOverride(name = "appoggioGglordi", column = @Column(name = "APPOGGIO_GGLORDI", length = 40)),
			@AttributeOverride(name = "appoggioGgsforo", column = @Column(name = "APPOGGIO_GGSFORO", length = 40)),
			@AttributeOverride(name = "appoggioGgxscad", column = @Column(name = "APPOGGIO_GGXSCAD", length = 40)),
			@AttributeOverride(name = "appoggioGgsosp", column = @Column(name = "APPOGGIO_GGSOSP", length = 40)),
			@AttributeOverride(name = "appoggioGginterr", column = @Column(name = "APPOGGIO_GGINTERR", length = 40)),
			@AttributeOverride(name = "appoggioGgest", column = @Column(name = "APPOGGIO_GGEST", length = 40)),
			@AttributeOverride(name = "livello4Classif", column = @Column(name = "LIVELLO4_CLASSIF", length = 20)),
			@AttributeOverride(name = "livello5Classif", column = @Column(name = "LIVELLO5_CLASSIF", length = 20)),
			@AttributeOverride(name = "appoggioDtScadenza", column = @Column(name = "APPOGGIO_DT_SCADENZA", length = 20)) })
	public PttTmpStatprocedimentiId getId() {
		return this.id;
	}

	public void setId(PttTmpStatprocedimentiId id) {
		this.id = id;
	}

}
