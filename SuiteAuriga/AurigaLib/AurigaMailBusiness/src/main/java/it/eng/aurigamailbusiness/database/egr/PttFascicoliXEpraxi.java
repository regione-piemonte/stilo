/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PttFascicoliXEpraxi generated by hbm2java
 */
@Entity
@Table(name = "PTT_FASCICOLI_X_EPRAXI")
public class PttFascicoliXEpraxi implements java.io.Serializable {

	private PttFascicoliXEpraxiId id;

	public PttFascicoliXEpraxi() {
	}

	public PttFascicoliXEpraxi(PttFascicoliXEpraxiId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idFascicolo", column = @Column(name = "ID_FASCICOLO", precision = 8, scale = 0)),
			@AttributeOverride(name = "numSottofasc", column = @Column(name = "NUM_SOTTOFASC", precision = 5, scale = 0)),
			@AttributeOverride(name = "codice", column = @Column(name = "CODICE", length = 50)),
			@AttributeOverride(name = "dtApertura", column = @Column(name = "DT_APERTURA", length = 7)),
			@AttributeOverride(name = "livUoApertura", column = @Column(name = "LIV_UO_APERTURA", length = 30)),
			@AttributeOverride(name = "desUoApertura", column = @Column(name = "DES_UO_APERTURA", length = 300)),
			@AttributeOverride(name = "livUoPresso", column = @Column(name = "LIV_UO_PRESSO", length = 30)),
			@AttributeOverride(name = "desUoPresso", column = @Column(name = "DES_UO_PRESSO", length = 300)),
			@AttributeOverride(name = "flgAcc", column = @Column(name = "FLG_ACC", length = 1)),
			@AttributeOverride(name = "txtOgg", column = @Column(name = "TXT_OGG", length = 250)),
			@AttributeOverride(name = "note", column = @Column(name = "NOTE", length = 75)),
			@AttributeOverride(name = "noteStoria", column = @Column(name = "NOTE_STORIA", length = 250)),
			@AttributeOverride(name = "flgProc", column = @Column(name = "FLG_PROC", length = 1)),
			@AttributeOverride(name = "idProc", column = @Column(name = "ID_PROC", precision = 8, scale = 0)),
			@AttributeOverride(name = "dtAvvioProc", column = @Column(name = "DT_AVVIO_PROC", length = 7)),
			@AttributeOverride(name = "idTpProc", column = @Column(name = "ID_TP_PROC", precision = 8, scale = 0)),
			@AttributeOverride(name = "desTpProc", column = @Column(name = "DES_TP_PROC", length = 250)),
			@AttributeOverride(name = "desRespProc", column = @Column(name = "DES_RESP_PROC", length = 500)),
			@AttributeOverride(name = "desIstrProc", column = @Column(name = "DES_ISTR_PROC", length = 500)),
			@AttributeOverride(name = "flgModificabile", column = @Column(name = "FLG_MODIFICABILE", length = 1)),
			@AttributeOverride(name = "flgRsv", column = @Column(name = "FLG_RSV", length = 1)),
			@AttributeOverride(name = "flgAbilModAcl", column = @Column(name = "FLG_ABIL_MOD_ACL", length = 1)),
			@AttributeOverride(name = "idFascicoloRif", column = @Column(name = "ID_FASCICOLO_RIF", precision = 8, scale = 0)),
			@AttributeOverride(name = "numSottofascRif", column = @Column(name = "NUM_SOTTOFASC_RIF", precision = 5, scale = 0)),
			@AttributeOverride(name = "decFascRif", column = @Column(name = "DEC_FASC_RIF", length = 100)),
			@AttributeOverride(name = "motiviRif", column = @Column(name = "MOTIVI_RIF", length = 250)),
			@AttributeOverride(name = "paroleChiave", column = @Column(name = "PAROLE_CHIAVE", length = 1000)),
			@AttributeOverride(name = "annoFasc", column = @Column(name = "ANNO_FASC", precision = 4, scale = 0)),
			@AttributeOverride(name = "numFasc", column = @Column(name = "NUM_FASC", precision = 8, scale = 0)),
			@AttributeOverride(name = "titoloClass", column = @Column(name = "TITOLO_CLASS", length = 10)),
			@AttributeOverride(name = "classeClass", column = @Column(name = "CLASSE_CLASS", length = 3)),
			@AttributeOverride(name = "sottoclasseClass", column = @Column(name = "SOTTOCLASSE_CLASS", length = 3)),
			@AttributeOverride(name = "livello4Class", column = @Column(name = "LIVELLO4_CLASS", length = 3)),
			@AttributeOverride(name = "livello5Class", column = @Column(name = "LIVELLO5_CLASS", length = 3)),
			@AttributeOverride(name = "annoFascRif", column = @Column(name = "ANNO_FASC_RIF", precision = 4, scale = 0)),
			@AttributeOverride(name = "titoloFascRif", column = @Column(name = "TITOLO_FASC_RIF", length = 10)),
			@AttributeOverride(name = "classeFascRif", column = @Column(name = "CLASSE_FASC_RIF", length = 3)),
			@AttributeOverride(name = "sottoclasseFascRif", column = @Column(name = "SOTTOCLASSE_FASC_RIF", length = 3)),
			@AttributeOverride(name = "livello4FascRif", column = @Column(name = "LIVELLO4_FASC_RIF", length = 3)),
			@AttributeOverride(name = "livello5FascRif", column = @Column(name = "LIVELLO5_FASC_RIF", length = 3)),
			@AttributeOverride(name = "numFascRif", column = @Column(name = "NUM_FASC_RIF", precision = 5, scale = 0)),
			@AttributeOverride(name = "numSfascRif", column = @Column(name = "NUM_SFASC_RIF", precision = 8, scale = 0)),
			@AttributeOverride(name = "docereditaacl", column = @Column(name = "DOCEREDITAACL", precision = 1, scale = 0)),
			@AttributeOverride(name = "idSessione", column = @Column(name = "ID_SESSIONE", nullable = false, length = 100)) })
	public PttFascicoliXEpraxiId getId() {
		return this.id;
	}

	public void setId(PttFascicoliXEpraxiId id) {
		this.id = id;
	}

}
