/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PtvRichInvioPoste generated by hbm2java
 */
@Entity
@Table(name = "PTV_RICH_INVIO_POSTE")
public class PtvRichInvioPoste implements java.io.Serializable {

	private PtvRichInvioPosteId id;

	public PtvRichInvioPoste() {
	}

	public PtvRichInvioPoste(PtvRichInvioPosteId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "idRichiesta", column = @Column(name = "ID_RICHIESTA", nullable = false, length = 64)),
			@AttributeOverride(name = "tipoServizio", column = @Column(name = "TIPO_SERVIZIO", nullable = false, length = 1)),
			@AttributeOverride(name = "tsIns", column = @Column(name = "TS_INS", nullable = false, length = 7)),
			@AttributeOverride(name = "uteIns", column = @Column(name = "UTE_INS", precision = 22, scale = 0)),
			@AttributeOverride(name = "etichetta", column = @Column(name = "ETICHETTA", nullable = false, length = 250)),
			@AttributeOverride(name = "stato", column = @Column(name = "STATO", nullable = false, length = 50)),
			@AttributeOverride(name = "tsAggStato", column = @Column(name = "TS_AGG_STATO", nullable = false, length = 7)),
			@AttributeOverride(name = "nextOperVsPoste", column = @Column(name = "NEXT_OPER_VS_POSTE", length = 10)),
			@AttributeOverride(name = "tsInizioAggAuto", column = @Column(name = "TS_INIZIO_AGG_AUTO")),
			@AttributeOverride(name = "flgAccettazioneAuto", column = @Column(name = "FLG_ACCETTAZIONE_AUTO", nullable = false, precision = 1, scale = 0)),
			@AttributeOverride(name = "idMittente", column = @Column(name = "ID_MITTENTE", length = 64)),
			@AttributeOverride(name = "flgInvioDatiMitt", column = @Column(name = "FLG_INVIO_DATI_MITT", precision = 1, scale = 0)),
			@AttributeOverride(name = "idRichiestaPoste", column = @Column(name = "ID_RICHIESTA_POSTE", length = 250)),
			@AttributeOverride(name = "idOrdinePoste", column = @Column(name = "ID_ORDINE_POSTE", length = 250)),
			@AttributeOverride(name = "nroParti", column = @Column(name = "NRO_PARTI", nullable = false, precision = 2, scale = 0)),
			@AttributeOverride(name = "nroDestinatari", column = @Column(name = "NRO_DESTINATARI", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "tsSottomissionePoste", column = @Column(name = "TS_SOTTOMISSIONE_POSTE", length = 7)),
			@AttributeOverride(name = "tsConfermaPoste", column = @Column(name = "TS_CONFERMA_POSTE", length = 7)),
			@AttributeOverride(name = "codStatoLavPoste", column = @Column(name = "COD_STATO_LAV_POSTE", length = 1)),
			@AttributeOverride(name = "tsStatoLavPoste", column = @Column(name = "TS_STATO_LAV_POSTE", length = 7)),
			@AttributeOverride(name = "flgNazionale", column = @Column(name = "FLG_NAZIONALE", nullable = false, precision = 1, scale = 0)),
			@AttributeOverride(name = "flgOpzCta", column = @Column(name = "FLG_OPZ_CTA", nullable = false, precision = 1, scale = 0)),
			@AttributeOverride(name = "firma", column = @Column(name = "FIRMA", length = 100)),
			@AttributeOverride(name = "prezzoTot", column = @Column(name = "PREZZO_TOT", precision = 10)),
			@AttributeOverride(name = "nroPagineTrasmesse", column = @Column(name = "NRO_PAGINE_TRASMESSE", precision = 2, scale = 0)),
			@AttributeOverride(name = "xmlDettagli", column = @Column(name = "XML_DETTAGLI")) })
	public PtvRichInvioPosteId getId() {
		return this.id;
	}

	public void setId(PtvRichInvioPosteId id) {
		this.id = id;
	}

}