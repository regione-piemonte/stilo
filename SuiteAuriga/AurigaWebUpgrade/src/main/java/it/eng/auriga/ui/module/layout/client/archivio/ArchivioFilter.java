/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.form.FilterClause;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.datasource.FieldFetchDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabile;
import it.eng.utility.ui.module.layout.client.common.filter.AttributiCustomDelTipo;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

public class ArchivioFilter extends ConfigurableFilter {

	private boolean fromScrivania;
	private String tipoNodo;
	private String idNode;

	private String showFilterFullText;	
	private String showFilterProtocollo;
	private String showFilterDataApertura;
	private String showFilterDataChiusura;
	private String showFilterBozze;
	private String showFilterStampe;
	private String showFilterSoloDaLeggere;
	private String showFilterInvio;
	private String showFilterArchiviazione;
	private String showFilterEliminazione;
	private String showFilterRicevutiInviatiViaEmail;
	private String showFilterStatoPresaInCarico;
	private String showFilterStatoRichAnnullamento;
	private String showFilterStatoAutorizzazione;
	private String showFilterInCompetenzaA;
	private String showFilterRicevutiPerCompetenza;
	private String showFilterRicevutiPerConoscenzaCC;
	private String showFilterRicevutiPerConoscenzaNA;
	private String showFilterMittente;
	private String showFilterDestinatario;
	private String showFilterOggetto;
	private String showFilterNomeFascicolo;
	private String showFilterRifFascicolo;	
	private String showFilterRifClassificazione;	
	private String showFilterRegEffettuataDa;
	private String showFilterFascicoli;
	private String showFilterDocumenti;
	private String showFilterDdIA;
	private String showFilterFdIA;
	private String showFilterAltraNumerazione;
	private String showFilterStatoLavAperta;
	private String showFilterStatoTrasmissioneEmail;
	private String showFilterCapoFilaFasc;
	private String showFilterRegistroAltriRif;
	private String showFilterEstremiAttoAutAnn;
	private String showFilterDataPresaInCarico;
	private String showFilterEsibente;
	private String showFilterPerizia;
	private String showFilterCentroDiCosto;
	private String showFilterFlgSottopostoControlloRegAmm;
	private String showFilterDataScadenza;
	private String showFilterInConoscenzaA;
	private String showFilterAttoCircoscrizione;
	private String showFilterOrganiCollegiali;
	private String showFilterUOCompetente;
	private String showFilterPresenzaOpere;
	private String showFilterSottoTipologiaAtto;
	private String showFilterStatiTrasfBloomfleet;
	private String showRegoleRegistrazioneAutomaticaEmail;
	
	
	// URBANISTICA
	private String showFilterEstremiProtUrbanisticaAltreNum;
	private String showFilterDataApprovazioneUrbanistica;
	private String showFilterUnitaDiConservazioneUrbanistica;
	private String showFilterDataEsitoCittadellaUrbanistica;
	private String showFilterRichiesteDaApprovareUrbanistica;
	private String showFilterRichiesteDaVerificareUrbanistica;
	private String showFilterRichiesteAppuntamentoUrbanistica;
	private String showFilterAppuntamentiDaFissare;
	
	private String showFilterProtNoScan;
	private String showFilterImmaginiNonAssociateAiProtocolli;
	
	private String showFilterRdAeAttiCollegati;
	
	
	
	public ArchivioFilter(String lista, Map<String, String> extraparam) {
	
		super(lista, extraparam);

		updateShowFilter(extraparam);

		addFilterChangedHandler(new FilterChangedHandler() {

			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				
				int posSearchFulltext = getClausePosition("searchFulltext");
				if (posSearchFulltext != -1) {
					if (showFilterFullText.equalsIgnoreCase("N")) {
						removeClause(getClause(posSearchFulltext));
					}
				}
				
				// Filtri relativi a registrazioni annullate
				int posSoloRegAnnullate = getClausePosition("soloRegAnnullate");
				if (posSoloRegAnnullate != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posSoloRegAnnullate));
					}
				}

				// Filtri relativi a numero protocollo ricevuto
				int posNroProtRicevuto = getClausePosition("nroProtRicevuto");
				if (posNroProtRicevuto != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroProtRicevuto));
					}
				}
				
				// Filtri relativi a numero e data raccomandata ricevute
				int posNroRaccomandata = getClausePosition("nroRaccomandata");
				if (posNroRaccomandata != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroRaccomandata));
					}
				}
				
				int posDtRaccomandata = getClausePosition("dtRaccomandata");
				if (posDtRaccomandata != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posDtRaccomandata));
					}
				}

				// Filtri relativi a data protocollo ricevuto
				int posDataProtRicevuto = getClausePosition("dataProtRicevuto");
				if (posDataProtRicevuto != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posDataProtRicevuto));
					}
				}
				
				// Filtri relativi a tipo documento
				int posTipoDoc = getClausePosition("tipoDoc");
				if (posTipoDoc != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posTipoDoc));
					}
				}
				
				// Filtri relativi a canale
				int supportoProt = getClausePosition("supportoProt");
				if (supportoProt != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(supportoProt));
					}
				}
				
				// Filtri relativi ad apposto timbro
				int flgAppostoTimbro = getClausePosition("flgAppostoTimbro");
				if (flgAppostoTimbro != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(flgAppostoTimbro));
					}
				}
				
				// Filtri relativi ad Prot. Sub. N
				int sub = getClausePosition("sub");
				if (sub != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(sub));
					}
				}
				
				// Filtri relativi ad Registro prot. settore:
				int sigla = getClausePosition("sigla");
				if (sigla != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(sigla));
					}
				}
				
				// Filtri relativi a U.O di registrazione
				int uoRegistrazione = getClausePosition("uoRegistrazione");
				if (uoRegistrazione != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(uoRegistrazione));
					}
				}
				// Filtri relativi alla riservatezza
				int flgRiservatezza = getClausePosition("flgRiservatezza");
				if (flgRiservatezza != -1) {
					// va mostrato se sono o in fascicoli o in documenti
					// quindi va nascosto quando non sono ne in fascicoli ne in documenti
					if (showFilterFascicoli.equalsIgnoreCase("N") && showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(flgRiservatezza));
					}
				}
				
				// Filtri relativi a UO Apertura
				int uoApertura = getClausePosition("uoApertura");
				if (uoApertura != -1) {
					if (showFilterFascicoli.equalsIgnoreCase("N")) {
						removeClause(getClause(uoApertura));
					}
				}
				
				// Filtri relativi a Restringi a (se sono in fascicolo rimuovo "documento")
				int posFlgUdFolderDocumento = getClausePosition("flgUdFolder");
				if (posFlgUdFolderDocumento != -1) {
					// quando sono in archivio showFilterFascicoli = S e showFilterDocumenti = S, se uno dei due è N o entrambi vuol dire che sono in scrivania
					// se sono in scrivania lo nascondo
					// praticamente va mostrato solo quando sono in archivio, e quindi ho sia fascicoli che documenti assieme
					if (showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posFlgUdFolderDocumento));
					}
				}

				// Filtri relativi a Restringi a (se sono in documento nascondo il filter di fascicolo)
				int posFlgUdFolderFascicolo = getClausePosition("flgUdFolderFascicolo");
				if (posFlgUdFolderFascicolo != -1) {
					// se sono in scrivania in un nodo che non è fascicoli, oppure non sono in scrivania (quindi fascicoli e documenti assieme) lo nascondo
					// praticamente va mostrato solo se sono in scrivania in un nodo con solo fascicoli
					if (showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("S")) {
						removeClause(getClause(posFlgUdFolderFascicolo));
					}
				}
				
				// Filtri relativi a tipo fasc./aggregato doc.
				int posTipoFolder = getClausePosition("tipoFolder");
				if (posTipoFolder != -1) {
					if (showFilterFascicoli.equalsIgnoreCase("N")) {
						removeClause(getClause(posTipoFolder));
					}
				}

				// Filtri relativi a fascicoli-> Da assegnatre/inviare
				int posAssegnatoAFdIA = getClausePosition("assegnatoA");
				if (posAssegnatoAFdIA != -1) {
					if (showFilterFdIA.equalsIgnoreCase("N")) {
						removeClause(getClause(posAssegnatoAFdIA));
					}
				}
				int posStatoPresaInCaricoFdIA = getClausePosition("statoPresaInCarico");
				if (posStatoPresaInCaricoFdIA != -1) {
					if (showFilterFdIA.equalsIgnoreCase("N")) {
						removeClause(getClause(posStatoPresaInCaricoFdIA));
					}
				}

				// Filtri relativi a Documenti da assegnare/inviare
				int posAssegnatoADdIA = getClausePosition("assegnatoA");
				if (posAssegnatoADdIA != -1) {
					if (showFilterDdIA.equalsIgnoreCase("N")) {
						removeClause(getClause(posAssegnatoADdIA));
					}
				}
				int posStatoPresaInCaricoDdIA = getClausePosition("statoPresaInCarico");
				if (posStatoPresaInCaricoDdIA != -1) {
					if (showFilterDdIA.equalsIgnoreCase("N")) {
						removeClause(getClause(posStatoPresaInCaricoDdIA));
					}
				}
				int posInviatiViaEmailDdIA = getClausePosition("inviatiViaEmail");
				if (posInviatiViaEmailDdIA != -1) {
					if (showFilterDdIA.equalsIgnoreCase("N")) {
						removeClause(getClause(posInviatiViaEmailDdIA));
					}
				}
				/*** Filtri di ricerca - Altre Numerazioni ***/
				int posAltraNumerazioneSigla = getClausePosition("altraNumerazioneSigla");
				if (posAltraNumerazioneSigla != -1) {
					if (showFilterAltraNumerazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posAltraNumerazioneSigla));
					}
				}
				int posAltraNumerazioneAnno = getClausePosition("altraNumerazioneAnno");
				if (posAltraNumerazioneAnno != -1) {
					if (showFilterAltraNumerazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posAltraNumerazioneAnno));
					}
				}
				int posAltraNumerazioneData = getClausePosition("altraNumerazioneData");
				if (posAltraNumerazioneData != -1) {
					if (showFilterAltraNumerazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posAltraNumerazioneData));
					}
				}
				int posAltraNumerazioneNr = getClausePosition("altraNumerazioneNr");
				if (posAltraNumerazioneNr != -1) {
					if (showFilterAltraNumerazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posAltraNumerazioneNr));
					}
				}
				/*** Antonio **/
				// Filtri relativi alle bozze
				int posAnnoBozza = getClausePosition("annoBozza");
				if (posAnnoBozza != -1) {
					if (showFilterBozze.equalsIgnoreCase("N")) {
						removeClause(getClause(posAnnoBozza));
					}
				}
				int posNroBozza = getClausePosition("nroBozza");
				if (posNroBozza != -1) {
					if (showFilterBozze.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroBozza));
					}
				}
				int posTsBozza = getClausePosition("tsBozza");
				if (posTsBozza != -1) {
					if (showFilterBozze.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsBozza));
					}
				}
				int posFileAssociati = getClausePosition("fileAssociati");
				if (posFileAssociati != -1) {
					if (showFilterBozze.equalsIgnoreCase("N")) {
						removeClause(getClause(posFileAssociati));
					}
					// Se e' stato selezionato il nodo "Documenti protocollati in attesa immagini", tolgo il filtro "File associati"
					if (showFilterProtNoScan.equalsIgnoreCase("S")) {
						removeClause(getClause(posFileAssociati));
					}
					
				}
				
				
				
				
				// Filtri relativi alle stampe
				int posAnnoStampa = getClausePosition("annoStampa");
				if (posAnnoStampa != -1) {
					if (showFilterStampe.equalsIgnoreCase("N")) {
						removeClause(getClause(posAnnoStampa));
					}
				}
				int posNroStampa = getClausePosition("nroStampa");
				if (posNroStampa != -1) {
					if (showFilterStampe.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroStampa));
					}
				}
				int posTsStampa = getClausePosition("tsStampa");
				if (posTsStampa != -1) {
					if (showFilterStampe.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsStampa));
					}
				}				
				int posTipoDocStampa = getClausePosition("tipoDocStampa");				
				if(posTipoDocStampa != -1) {
					if (showFilterStampe.equalsIgnoreCase("N")){						
						removeClause(getClause(posTipoDocStampa));	
					}
				}				
				int posNroRichiestaStampaExp = getClausePosition("nroRichiestaStampaExp");				
				if(posNroRichiestaStampaExp != -1) {
					if (showFilterStampe.equalsIgnoreCase("N")){						
						removeClause(getClause(posNroRichiestaStampaExp));	
					}
				}
				int posTsRichiestaStampaExp = getClausePosition("tsRichiestaStampaExp");				
				if(posTsRichiestaStampaExp != -1) {
					if (showFilterStampe.equalsIgnoreCase("N")){						
						removeClause(getClause(posTsRichiestaStampaExp));	
					}
				}
				
				// Filtri relativi al protocollo
				int posAnnoProt = getClausePosition("annoProt");
				if (posAnnoProt != -1) {
					if (showFilterProtocollo.equalsIgnoreCase("N")) {
						removeClause(getClause(posAnnoProt));
					}
				}
				int posNroProt = getClausePosition("nroProt");
				if (posNroProt != -1) {
					if (showFilterProtocollo.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroProt));
					}
				}
				int posTipoProt = getClausePosition("tipoProt");
				if (posTipoProt != -1) {
					if (showFilterProtocollo.equalsIgnoreCase("N")) {
						removeClause(getClause(posTipoProt));
					}
				}
				int posTsRegistrazione = getClausePosition("tsRegistrazione");
				if (posTsRegistrazione != -1) {
					if (showFilterProtocollo.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsRegistrazione));
					}
				}
				// Filtro "Data apertura fascicolo"
				int posTsAperturaFascicolo = getClausePosition("tsAperturaFascicolo");
				if (posTsAperturaFascicolo != -1) {
					if (showFilterDataApertura.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsAperturaFascicolo));
					}
				}				
				// Filtro "Data chiusurafascicolo"
				int posTsChiusuraFascicolo = getClausePosition("tsChiusuraFascicolo");
				if (posTsChiusuraFascicolo != -1) {
					if (showFilterDataChiusura.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsChiusuraFascicolo));
					}
				}
				// Filtro "Da leggere"
				int posFlgSoloDaLeggere = getClausePosition("flgSoloDaLeggere");
				if (posFlgSoloDaLeggere != -1) {
					if (showFilterSoloDaLeggere.equalsIgnoreCase("N")) {
						removeClause(getClause(posFlgSoloDaLeggere));
					}
				}
				// Filtri relativi agli inviati
				int posTsInvio = getClausePosition("tsInvio");
				if (posTsInvio != -1) {
					if (showFilterInvio.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsInvio));
					}
				}
				int posDestinatarioInvio = getClausePosition("destinatarioInvio");
				if (posDestinatarioInvio != -1) {
					if (showFilterInvio.equalsIgnoreCase("N")) {
						removeClause(getClause(posDestinatarioInvio));
					}
				}
				int posTsArchiviazione = getClausePosition("tsArchiviazione");
				if (posTsArchiviazione != -1) {
					if (showFilterArchiviazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsArchiviazione));
					}
				}
				// Filtri relativi agli eliminati
				int posTsEliminazione = getClausePosition("tsEliminazione");
				if (posTsEliminazione != -1) {
					if (showFilterEliminazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsEliminazione));
					}
				}
				int posSezioneEliminazioneDoc = getClausePosition("sezioneEliminazioneDoc");
				if (posSezioneEliminazioneDoc != -1) {
					if (showFilterEliminazione.equalsIgnoreCase("N") || !"D".equals(tipoNodo)) {
						removeClause(getClause(posSezioneEliminazioneDoc));
					}
				}
				int posSezioneEliminazioneFasc = getClausePosition("sezioneEliminazioneFasc");
				if (posSezioneEliminazioneFasc != -1) {
					if (showFilterEliminazione.equalsIgnoreCase("N") || !"F".equals(tipoNodo)) {
						removeClause(getClause(posSezioneEliminazioneFasc));
					}
				}
				// Filtro "Ricevuti tramite e-mail"
				int posSoloDocRicevutiViaEmail = getClausePosition("soloDocRicevutiViaEmail");
				if (posSoloDocRicevutiViaEmail != -1) {
					if (showFilterRicevutiInviatiViaEmail.equalsIgnoreCase("N")) {
						removeClause(getClause(posSoloDocRicevutiViaEmail));
					}
				}
				// Filtro "Inviati tramite e-mail"
				int posInviatiViaEmail = getClausePosition("inviatiViaEmail");
				if (posInviatiViaEmail != -1) {
					if (showFilterRicevutiInviatiViaEmail.equalsIgnoreCase("N")) {
						removeClause(getClause(posInviatiViaEmail));
					}
				}
				int posStatoPresaInCarico = getClausePosition("statoPresaInCarico");
				if (posStatoPresaInCarico != -1) {
					if (showFilterStatoPresaInCarico.equalsIgnoreCase("N")) {
						removeClause(getClause(posStatoPresaInCarico));
					}
				}
				int posStatoRichAnnullamento = getClausePosition("statoRichAnnullamento");
				if (posStatoRichAnnullamento != -1) {
					if (showFilterStatoRichAnnullamento.equalsIgnoreCase("N")) {
						removeClause(getClause(posStatoRichAnnullamento));
					}
				}
				int posStatoAutorizzazione = getClausePosition("statoAutorizzazione");
				if (posStatoAutorizzazione != -1) {
					if (showFilterStatoAutorizzazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posStatoAutorizzazione));
					}
				}
				int posTsAssegnazione = getClausePosition("tsAssegnazione");
				if (posTsAssegnazione != -1) {
					if (showFilterRicevutiPerCompetenza.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsAssegnazione));
					}
				}
				int posAssegnatoA = getClausePosition("assegnatoA");
				if (posAssegnatoA != -1) {
					if (showFilterInCompetenzaA.equalsIgnoreCase("N")) {
						removeClause(getClause(posAssegnatoA));
					}
				}
				int posTsNotificaCC = getClausePosition("tsNotificaCC");
				if (posTsNotificaCC != -1) {
					if (showFilterRicevutiPerConoscenzaCC.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsNotificaCC));
					}
				}
				int posInviatoA = getClausePosition("inviatoA");
				if (posInviatoA != -1) {
					if (showFilterRicevutiPerConoscenzaCC.equalsIgnoreCase("N") && showFilterInConoscenzaA.equalsIgnoreCase("N")) {
						removeClause(getClause(posInviatoA));
					}
				}
				int posTsNotificaNA = getClausePosition("tsNotificaNA");
				if (posTsNotificaNA != -1) {
					if (showFilterRicevutiPerConoscenzaNA.equalsIgnoreCase("N")) {
						removeClause(getClause(posTsNotificaNA));
					}
				}
				int posNotificatoA = getClausePosition("notificatoA");
				if (posNotificatoA != -1) {
					if (showFilterRicevutiPerConoscenzaNA.equalsIgnoreCase("N")) {
						removeClause(getClause(posNotificatoA));
					}
				}
				int posMittente = getClausePosition("mittente");
				if (posMittente != -1) {
					if (showFilterMittente.equalsIgnoreCase("N")) {
						removeClause(getClause(posMittente));
					}
				}
				int posMittenteInRubrica = getClausePosition("mittenteInRubrica");
				if (posMittenteInRubrica != -1) {
					if (showFilterMittente.equalsIgnoreCase("N")) {
						removeClause(getClause(posMittenteInRubrica));
					}
				}
				int posDestinatario = getClausePosition("destinatario");
				if (posDestinatario != -1) {
					if (showFilterDestinatario.equalsIgnoreCase("N")) {
						removeClause(getClause(posDestinatario));
					}
				}
				int posDestinatarioInRubrica = getClausePosition("destinatarioInRubrica");
				if (posDestinatarioInRubrica != -1) {
					if (showFilterDestinatario.equalsIgnoreCase("N")) {
						removeClause(getClause(posDestinatarioInRubrica));
					}
				}
				int posEsibente = getClausePosition("esibente");
				if (posEsibente != -1) {
					if (showFilterEsibente.equalsIgnoreCase("N")) {
						removeClause(getClause(posEsibente));
					}
				}
				int posEsibenteInRubrica = getClausePosition("esibenteInRubrica");
				if (posEsibenteInRubrica != -1) {
					if (showFilterEsibente.equalsIgnoreCase("N")) {
						removeClause(getClause(posEsibenteInRubrica));
					}
				}
				int posOggetto = getClausePosition("oggetto");
				if (posOggetto != -1) {
					if (showFilterOggetto.equalsIgnoreCase("N")) {
						removeClause(getClause(posOggetto));
					}
				}
				int posNomeFascicolo = getClausePosition("nomeFascicolo");
				if (posNomeFascicolo != -1) {
					if (showFilterNomeFascicolo.equalsIgnoreCase("N")) {
						removeClause(getClause(posNomeFascicolo));
					}
				}
				int posRegEffettuataDa = getClausePosition("regEffettuataDa");
				if (posRegEffettuataDa != -1) {
					if (showFilterRegEffettuataDa.equalsIgnoreCase("N")) {
						removeClause(getClause(posRegEffettuataDa));
					}
				}
				/** FILTRI MODULO ATTI **/
				int posUoProponente = getClausePosition("uoProponente");
				if (posUoProponente != -1) {
					if (hideFilterModuloAtti()) {
						removeClause(getClause(posUoProponente));
					}
				}
				
				int posUtentiAvvioAtto = getClausePosition("utentiAvvioAtto");
				if (posUtentiAvvioAtto != -1) {
					if (hideFilterModuloAtti()) {
						removeClause(getClause(posUtentiAvvioAtto));
					}
				}
				int posUtentiAdozioneAtto = getClausePosition("utentiAdozioneAtto");
				if (posUtentiAdozioneAtto != -1) {
					if (hideFilterModuloAtti()) {
						removeClause(getClause(posUtentiAdozioneAtto));
					}
				}
				int posStatiDoc = getClausePosition("statiDoc");
				if (posStatiDoc != -1) {
					if (hideFilterModuloAtti()) {
						removeClause(getClause(posStatiDoc));
					}
				}
				int posDataFirmaAtto = getClausePosition("dataFirmaAtto");
				if (posDataFirmaAtto != -1) {
					if (hideFilterModuloAtti()) {
						removeClause(getClause(posDataFirmaAtto));
					}
				}

				if (showFilterStampe.equalsIgnoreCase("S")) {
					List<FilterClause> lClausesToRemoveList = new ArrayList<FilterClause>();
					for (int i = 0; i < getClauseStack().getMembers().length - 1; i++) {
						FormItem lClauseFieldNameItem = getClauseFieldNameItem(i);
						if (lClauseFieldNameItem.getValue() != null  && 
                            !lClauseFieldNameItem.getValue().equals("searchFulltext") && 
                            !lClauseFieldNameItem.getValue().equals("annoStampa")     && 
                            !lClauseFieldNameItem.getValue().equals("nroStampa")      && 
                            !lClauseFieldNameItem.getValue().equals("tsStampa")       &&
                            !lClauseFieldNameItem.getValue().equals("tipoDocStampa")  &&
						    !lClauseFieldNameItem.getValue().equals("nroRichiestaStampaExp")       &&
						    !lClauseFieldNameItem.getValue().equals("tsRichiestaStampaExp")       
						   
                           ) 

                        {
							lClausesToRemoveList.add(getClause(i));
						}
					}
					for (FilterClause lClauseToRemove : lClausesToRemoveList) {
						removeClause(lClauseToRemove);
					}
				}

				int postatoLavorazioneAperto = getClausePosition("statoLavorazioneAperto");
				if (postatoLavorazioneAperto != -1) {
					if (showFilterStatoLavAperta.equalsIgnoreCase("N")) {
						removeClause(getClause(postatoLavorazioneAperto));
					}
				}

				int statoTrasmissioneEmail = getClausePosition("statoTrasmissioneEmail");
				if (statoTrasmissioneEmail != -1) {
					if (showFilterStatoTrasmissioneEmail.equalsIgnoreCase("N")) {
						removeClause(getClause(statoTrasmissioneEmail));
					}
				}
				
				
				// Filtro "Fascicolo - Capo fila"				
				int capoFilaFasc = getClausePosition("capoFilaFasc");
				if (capoFilaFasc != -1) {
					if ((showFilterCapoFilaFasc.equalsIgnoreCase("N"))) {								
						removeClause(getClause(capoFilaFasc));
					}
				}
				
				// Filtro "Prot. - Altri riferimenti - Tipo"			
				int registroAltriRifTipo = getClausePosition("registroAltriRifTipo");
				if (registroAltriRifTipo != -1) {
					if ((showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {								
						removeClause(getClause(registroAltriRifTipo));
					}
				}
								
				// Filtro "Prot. - Altri riferimenti - Nro"			
				int registroAltriRifNro = getClausePosition("registroAltriRifNro");
				if (registroAltriRifNro != -1) {
					if ((showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {								
						removeClause(getClause(registroAltriRifNro));
					}
				}
				
				// Filtro "Prot. - Altri riferimenti - Data"			
				int registroAltriRifData = getClausePosition("registroAltriRifData");
				if (registroAltriRifData != -1) {
					if ((showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {								
						removeClause(getClause(registroAltriRifData));
					}
				}

				// Filtro "Prot. - Altri riferimenti - Anno"			
				int registroAltriRifAnno = getClausePosition("registroAltriRifAnno");
				if (registroAltriRifAnno != -1) {
					if ((showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {								
						removeClause(getClause(registroAltriRifAnno));
					}
				}
				
				// Filtro Atto aut.annullamento per le sezioni "Anullamenti da autorizzare & Registrazioni da anullare "
				int attoAutAnnullamento = getClausePosition("attoAutAnnullamento");
				if(attoAutAnnullamento != -1) {
					if(("showFilterEstremiAttoAutAnn").equalsIgnoreCase("N")) {
						removeClause(getClause(attoAutAnnullamento));
					}
				}
				
				/**
				 * FILTRI URBANISTICA
				 */
				
				//------------------FILTRI COMUNI------------------//
				int estremiProtUrbanisticaAnno = getClausePosition("estremiProtUrbanisticaAnno");
				if (estremiProtUrbanisticaAnno != -1) {
					if ((showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {								
						removeClause(getClause(estremiProtUrbanisticaAnno));
					}
				}
				int estremiProtUrbanisticaNro = getClausePosition("estremiProtUrbanisticaNro");
				if (estremiProtUrbanisticaNro != -1) {
					if ((showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {								
						removeClause(getClause(estremiProtUrbanisticaNro));
					}
				}
				int estremiProtUrbanisticaData = getClausePosition("estremiProtUrbanisticaData");
				if (estremiProtUrbanisticaData != -1) {
					if ((showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {								
						removeClause(getClause(estremiProtUrbanisticaData));
					}
				}
				int estremiAltreNumUrbanisticaNumero = getClausePosition("estremiAltreNumUrbanisticaNumero");
				if (estremiAltreNumUrbanisticaNumero != -1) {
					if ((showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {								
						removeClause(getClause(estremiAltreNumUrbanisticaNumero));
					}
				}
				int estremiAltreNumUrbanisticaAnno = getClausePosition("estremiAltreNumUrbanisticaAnno");
				if (estremiAltreNumUrbanisticaAnno != -1) {
					if ((showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {								
						removeClause(getClause(estremiAltreNumUrbanisticaAnno));
					}
				}
				int indirizzoEstremiProtUrbanistica = getClausePosition("indirizzoEstremiProtUrbanistica");
				if (indirizzoEstremiProtUrbanistica != -1) {
					if ((showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {								
						removeClause(getClause(indirizzoEstremiProtUrbanistica));
					}
				}
				//------------------FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.EN }------------------//
				int dataApprovazioneUrbanistica = getClausePosition("dataApprovazioneUrbanistica");
				if (dataApprovazioneUrbanistica != -1) {
					if ((showFilterDataApprovazioneUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataApprovazioneUrbanistica));
					}
				}
				//------------------FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF }------------------//
				int dataEsitoCittadellaUrbanistica = getClausePosition("dataEsitoCittadellaUrbanistica");
				if (dataEsitoCittadellaUrbanistica != -1) {
					if ((showFilterDataEsitoCittadellaUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataEsitoCittadellaUrbanistica));
					}
				}
				//------------------FILTRI comuni ad IdNode { D.RAA.ADF - D.RAA.AF - D.RAA.EN }------------------//
				int unitaDiConservazioneUrbanistica = getClausePosition("unitaDiConservazioneUrbanistica");
				if (unitaDiConservazioneUrbanistica != -1) {
					if ((showFilterUnitaDiConservazioneUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(unitaDiConservazioneUrbanistica));
					}
				}
				//------------------FILTRI Richieste da approvare------------------//
				int invioApprovazioneUrbanistica = getClausePosition("invioApprovazioneUrbanistica");
				if (invioApprovazioneUrbanistica != -1) {
					if ((showFilterRichiesteDaApprovareUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(invioApprovazioneUrbanistica));
					}
				}
				//------------------FILTRI Richieste da verificare------------------//
				int verificaCompletataUrbanistica = getClausePosition("verificaCompletataUrbanistica");
				if (verificaCompletataUrbanistica != -1) {
					if ((showFilterRichiesteDaVerificareUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(verificaCompletataUrbanistica));
					}
				}
				//------------------FILTRI Richieste di appuntamento ------------------//
				int dataAppuntUrbanisticaDataAppuntamento = getClausePosition("dataAppuntUrbanisticaDataAppuntamento");
				if (dataAppuntUrbanisticaDataAppuntamento != -1) {
					if ((showFilterRichiesteAppuntamentoUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataAppuntUrbanisticaDataAppuntamento));
					}
				}
				int dataPrelievoUrbanisticaDataPrelievo = getClausePosition("dataPrelievoUrbanisticaDataPrelievo");
				if (dataPrelievoUrbanisticaDataPrelievo != -1) {
					if ((showFilterRichiesteAppuntamentoUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(dataPrelievoUrbanisticaDataPrelievo));
					}
				}
				int richAppuntUrbanisticaPrelievoEffettuato = getClausePosition("richAppuntUrbanisticaPrelievoEffettuato");
				if (richAppuntUrbanisticaPrelievoEffettuato != -1) {
					if ((showFilterRichiesteAppuntamentoUrbanistica.equalsIgnoreCase("N"))) {								
						removeClause(getClause(richAppuntUrbanisticaPrelievoEffettuato));
					}
				}
				//------------------FILTRI Appuntamenti da fissare ------------------//
				int flgAppuntamentiDaFissare = getClausePosition("flgAppuntamentiDaFissare");
				if (flgAppuntamentiDaFissare != -1) {
					if ((showFilterAppuntamentiDaFissare.equalsIgnoreCase("N"))) {								
						removeClause(getClause(flgAppuntamentiDaFissare));
					}
				}
				
				
				// Filtro CLASSIFICAZIONE 
				int posClassificazione = getClausePosition("classificazioneArchivio");
				if (posClassificazione != -1) {
					if (showFilterRifClassificazione.equalsIgnoreCase("N")) {
						removeClause(getClause(posClassificazione));
					}
				}
				
				// Filtro ANNO FASCICOLO (se sono in documento nascondo il filtro)
				int posAnnoFascicolo = getClausePosition("annoFascicolo");
				if (posAnnoFascicolo != -1) {
					if (showFilterRifFascicolo.equalsIgnoreCase("N")) {
						removeClause(getClause(posAnnoFascicolo));
					}
				}
				
				// Filtro NUMERO FASCICOLO (se sono in documento nascondo il filtro)
				int posNroFascicolo = getClausePosition("nroFascicolo");
				if (posNroFascicolo != -1) {
					if (showFilterRifFascicolo.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroFascicolo));
					}
				}
				
				// Filtro NUMERO SOTTO FASCICOLO (se sono in documento nascondo il filtro)
				int posNroSottoFascicolo = getClausePosition("nroSottoFascicolo");
				if (posNroSottoFascicolo != -1) {
					if (showFilterRifFascicolo.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroSottoFascicolo));
					}
				}
				
				// Filtro CODICE FASCICOLO (se sono in documento nascondo il filtro)
				int posCodiceFascicolo = getClausePosition("codiceFascicolo");
				if (posCodiceFascicolo != -1) {
					if (showFilterRifFascicolo.equalsIgnoreCase("N")) {
						removeClause(getClause(posCodiceFascicolo));
					}
				}
				
				// Filtro DATA STESURA 
				int posDtStesura = getClausePosition("dtStesura");
				if (posDtStesura != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posDtStesura));
					}
				}
				
				// Filtro DOC. COLLEGATI AL NOMINATIVO 
				int posDocCollegatiNominativo = getClausePosition("docCollegatiNominativo");
				if (posDocCollegatiNominativo != -1) {
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(posDocCollegatiNominativo));
					}
				}
				
				int posDtPresaInCarico = getClausePosition("tsPresaInCarico");
				if (posDtPresaInCarico != -1) {
					if (showFilterDataPresaInCarico.equalsIgnoreCase("N")) {
						removeClause(getClause(posDtPresaInCarico));
					}
				}
								
				// Filtro perizia (solo per ADSP)
				int posPerizia = getClausePosition("perizia");
				if (posPerizia != -1) {
					if (showFilterPerizia.equalsIgnoreCase("N")) {
						removeClause(getClause(posPerizia));
					}
				}
					
				// Filtro Presenza opere (solo per ADSP)
				int posPresenzaOpere = getClausePosition("presenzaOpere");
				if (posPresenzaOpere != -1) {
					if (showFilterPresenzaOpere.equalsIgnoreCase("N")) {
						removeClause(getClause(posPresenzaOpere));
					}
				}
				
				int posSottoTipologiaAtto = getClausePosition("sottoTipologiaAtto");
				if (posSottoTipologiaAtto != -1) {
					if (showFilterSottoTipologiaAtto.equalsIgnoreCase("N")) {
						removeClause(getClause(posSottoTipologiaAtto));
					}
				}
				
				// Filtro CdC atto (solo per COTO)
				int posCentroDiCosto = getClausePosition("centroDiCosto");
				if (posCentroDiCosto != -1) {
					if (showFilterCentroDiCosto.equalsIgnoreCase("N")) {
						removeClause(getClause(posCentroDiCosto));
					}
				}
				
				//Filtro Atto Circoscrizione
				int attoCircoscrizione = getClausePosition("attoCircoscrizione");
				if (attoCircoscrizione != -1) {
					if (showFilterAttoCircoscrizione.equalsIgnoreCase("N")) {
						removeClause(getClause(attoCircoscrizione));
					}
				}
				
				// Filtri Organi Collegiali
				int inseritoInOdGDiscussioneSeduta = getClausePosition("inseritoInOdGDiscussioneSeduta");
				if (inseritoInOdGDiscussioneSeduta != -1) {
					if (showFilterOrganiCollegiali.equalsIgnoreCase("N")) {
						removeClause(getClause(inseritoInOdGDiscussioneSeduta));
					}
				}
				
				// Filtro FlgSottopostoControlloRegAmm (solo per CMTO)
				int posFlgSottopostoControlloRegAmm = getClausePosition("flgSottopostoControlloRegAmm");
				if (posFlgSottopostoControlloRegAmm != -1) {
					if (showFilterFlgSottopostoControlloRegAmm.equalsIgnoreCase("N")) {
						removeClause(getClause(posFlgSottopostoControlloRegAmm));
					}
				}
				
				// Filtro Data scadenza liquidazione
				int posDataScadenza = getClausePosition("dataScadenza");
				if (posDataScadenza != -1) {
					if (showFilterDataScadenza.equalsIgnoreCase("N")) {
						removeClause(getClause(posDataScadenza));
					}
				}
				
				
				// Filtro 'Passato dallo smistamento'
				int flgPassaggioDaSmistamento = getClausePosition("flgPassaggioDaSmistamento");
				if (flgPassaggioDaSmistamento != -1) {
					// va mostrato se sono in documenti
					// quindi va nascosto quando non sono in documenti
					if (showFilterDocumenti.equalsIgnoreCase("N")) {
						removeClause(getClause(flgPassaggioDaSmistamento));
					}
				}
				
				
				// Filtro UO competente atto (solo per ADSP)
				int posUOCompetente = getClausePosition("uoCompetente");
				if (posUOCompetente != -1) {
					if (showFilterUOCompetente.equalsIgnoreCase("N")) {
						removeClause(getClause(posUOCompetente));
					}
				}	
				
				// Filtro RdA e atti collegati
				int posRdAeAttiCollegati = getClausePosition("rdAeAttiCollegati");
				if (posRdAeAttiCollegati != -1) {
					if (showFilterRdAeAttiCollegati.equalsIgnoreCase("N")) {
						removeClause(getClause(posRdAeAttiCollegati));
					}
				}	
				
				// Filtri relativi alle "immagini non associate ai protocolli" 
				int posDtScansioneMassiva = getClausePosition("dataScansioneMassiva");
				if (posDtScansioneMassiva != -1) {
					if (showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("N")) {
						removeClause(getClause(posDtScansioneMassiva));
					}
				}
				
				int posNroImmagineScansioneMassiva = getClausePosition("nroImmagineScansioneMassiva");
				if (posNroImmagineScansioneMassiva != -1) {
					if (showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("N")) {
						removeClause(getClause(posNroImmagineScansioneMassiva));
					}
				}
				
				int posSedeScansioneMassiva = getClausePosition("sedeScansioneMassiva");				
				if(posSedeScansioneMassiva != -1) {
					if (showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("N")){						
						removeClause(getClause(posSedeScansioneMassiva));	
					}
				}	
				
				if (showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("S")) {
					List<FilterClause> lClausesToRemoveList = new ArrayList<FilterClause>();
					for (int i = 0; i < getClauseStack().getMembers().length - 1; i++) {
						FormItem lClauseFieldNameItem = getClauseFieldNameItem(i);
						if (lClauseFieldNameItem.getValue() != null  && 
                            !lClauseFieldNameItem.getValue().equals("searchFulltext")              && 
                            !lClauseFieldNameItem.getValue().equals("dataScansioneMassiva")        && 
                            !lClauseFieldNameItem.getValue().equals("nroImmagineScansioneMassiva") && 
                            !lClauseFieldNameItem.getValue().equals("sedeScansioneMassiva")
                           ) 

                        {
							lClausesToRemoveList.add(getClause(i));
						}
					}
					for (FilterClause lClauseToRemove : lClausesToRemoveList) {
						removeClause(lClauseToRemove);
					}
				}
				
				// Filtro 'Stato trasferimento a Bloomfleet' (solo per A2A)
				int statiTrasfBloomfleet = getClausePosition("statiTrasfBloomfleet");
				if (statiTrasfBloomfleet != -1) {
					// va mostrato se sono in documenti
					// quindi va nascosto quando non sono in documenti
					if (showFilterDocumenti.equalsIgnoreCase("N") || showFilterStatiTrasfBloomfleet.equalsIgnoreCase("N")) {
						removeClause(getClause(statiTrasfBloomfleet));
					}
				}	
				
				
				// Filtro 'Regola reg. automatica'
				// quando sono in archivio showFilterFascicoli = S e showFilterDocumenti = S, se uno dei due è N o entrambi vuol dire che sono in scrivania
				// se sono in scrivania lo nascondo
				// praticamente va mostrato solo quando sono in archivio, e quindi ho sia fascicoli che documenti assieme
				int regoleRegistrazioneAutomaticaEmail = getClausePosition("regoleRegistrazioneAutomaticaEmail");
				if (regoleRegistrazioneAutomaticaEmail != -1) {
					if ( showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("N") || showRegoleRegistrazioneAutomaticaEmail.equalsIgnoreCase("N") ){	
						removeClause(getClause(regoleRegistrazioneAutomaticaEmail));
					}
				}
			}
		});
	}

	public void updateShowFilter(Map<String, String> extraparam) {
		setExtraParam(extraparam);
		fromScrivania = getExtraParam().get("fromScrivania") != null && !"".equals(getExtraParam().get("fromScrivania")) ? new Boolean(getExtraParam().get( "fromScrivania")) : false;
		tipoNodo = getExtraParam().get("tipoNodo") != null ? getExtraParam().get("tipoNodo") : "";
		idNode = getExtraParam().get("idNode") != null ? getExtraParam().get("idNode") : "";
		showFilterFdIA = getExtraParam().get("showFilterFdIA") != null ? getExtraParam().get("showFilterFdIA") : "S";
		showFilterDdIA = getExtraParam().get("showFilterDdIA") != null ? getExtraParam().get("showFilterDdIA") : "S";
		showFilterFascicoli = getExtraParam().get("showFilterFascicoli") != null ? getExtraParam().get("showFilterFascicoli") : "S";
		showFilterDocumenti = getExtraParam().get("showFilterDocumenti") != null ? getExtraParam().get("showFilterDocumenti") : "S";
		// showFilterRestringiRicercaA = getExtraParam().get("showFilterRestringiRicercaA") != null ? getExtraParam().get("showFilterRestringiRicercaA") : "S";		
		if (AurigaLayout.getParametroDBAsBoolean("HIDE_FILTER_FULLTEXT_REP_DOC")) {
			String showFlgRicorsivaStringaFullText = getExtraParam().get("showFlgRicorsiva");
			if (showFlgRicorsivaStringaFullText == null) {
				FilterBean filterBean = getFilterConfigBean();
				if(filterBean != null) {
					for (FilterFieldBean lFilterFieldBean : filterBean.getFields()) {
						if ("searchFulltext".equalsIgnoreCase(lFilterFieldBean.getName())) {
							showFlgRicorsivaStringaFullText = String.valueOf(lFilterFieldBean.isShowFlgRicorsiva());
							break;
						}
					}				
				}
			}
			showFilterFullText = showFlgRicorsivaStringaFullText != null && new Boolean(showFlgRicorsivaStringaFullText) ? "S" : "N";
		} else {
			showFilterFullText = "S";
		}
		showFilterProtocollo = getExtraParam().get("showFilterProtocollo") != null ? getExtraParam().get("showFilterProtocollo") : "S";
		showFilterDataApertura = getExtraParam().get("showFilterDataApertura") != null ? getExtraParam().get("showFilterDataApertura") : "S";		
		showFilterBozze = getExtraParam().get("showFilterBozze") != null ? getExtraParam().get("showFilterBozze") : "S";
		showFilterStampe = getExtraParam().get("showFilterStampe") != null ? getExtraParam().get("showFilterStampe") : "N";
		showFilterSoloDaLeggere = getExtraParam().get("showFilterSoloDaLeggere") != null ? getExtraParam().get("showFilterSoloDaLeggere") : "N";
		showFilterInvio = getExtraParam().get("showFilterInvio") != null ? getExtraParam().get("showFilterInvio") : "N";
		showFilterArchiviazione = getExtraParam().get("showFilterArchiviazione") != null ? getExtraParam().get("showFilterArchiviazione") : "N";
		showFilterEliminazione = getExtraParam().get("showFilterEliminazione") != null ? getExtraParam().get("showFilterEliminazione") : "N";
		showFilterRicevutiInviatiViaEmail = getExtraParam().get("showFilterRicevutiInviatiViaEmail") != null ? getExtraParam().get("showFilterRicevutiInviatiViaEmail") : "S";
		showFilterStatoPresaInCarico = getExtraParam().get("showFilterStatoPresaInCarico") != null ? getExtraParam().get("showFilterStatoPresaInCarico") : "S";
		showFilterStatoRichAnnullamento = getExtraParam().get("showFilterStatoRichAnnullamento") != null ? getExtraParam().get("showFilterStatoRichAnnullamento") : "N";
		showFilterStatoAutorizzazione = getExtraParam().get("showFilterStatoAutorizzazione") != null ? getExtraParam().get("showFilterStatoAutorizzazione") : "N";
		showFilterInCompetenzaA = getExtraParam().get("showFilterInCompetenzaA") != null ? getExtraParam().get("showFilterInCompetenzaA") : "S";
		showFilterRicevutiPerCompetenza = getExtraParam().get("showFilterRicevutiPerCompetenza") != null ? getExtraParam().get("showFilterRicevutiPerCompetenza") : "N";
		showFilterRicevutiPerConoscenzaCC = getExtraParam().get("showFilterRicevutiPerConoscenzaCC") != null ? getExtraParam().get("showFilterRicevutiPerConoscenzaCC") : "N";
		showFilterRicevutiPerConoscenzaNA = getExtraParam().get("showFilterRicevutiPerConoscenzaNA") != null ? getExtraParam().get("showFilterRicevutiPerConoscenzaNA") : "N";
		showFilterMittente = getExtraParam().get("showFilterMittente") != null ? getExtraParam().get("showFilterMittente") : "S";
		showFilterEsibente = getExtraParam().get("showFilterEsibente") != null ? getExtraParam().get("showFilterEsibente") : "S";		
		showFilterDestinatario = getExtraParam().get("showFilterDestinatario") != null ? getExtraParam().get("showFilterDestinatario") : "S";
		showFilterOggetto = getExtraParam().get("showFilterOggetto") != null ? getExtraParam().get("showFilterOggetto") : "S";
		showFilterNomeFascicolo = getExtraParam().get("showFilterNomeFascicolo") != null ? getExtraParam().get("showFilterNomeFascicolo") : "S";		
		showFilterRifFascicolo = getExtraParam().get("showFilterRifFascicolo") != null ? getExtraParam().get("showFilterRifFascicolo") : "S";
		showFilterRifClassificazione = getExtraParam().get("showFilterRifClassificazione") != null ? getExtraParam().get("showFilterRifClassificazione") : "S";
		
		showFilterRegEffettuataDa = getExtraParam().get("showFilterRegEffettuataDa") != null ? getExtraParam().get("showFilterRegEffettuataDa") : "S";
		if (AurigaLayout.getParametroDBAsBoolean("ALTRE_NUM_ATTIVE")) {
			showFilterAltraNumerazione = getExtraParam().get("showFilterAltraNumerazione") != null ? getExtraParam().get("showFilterAltraNumerazione") : "S";
		} else {
			showFilterAltraNumerazione = "N";
		}

		showFilterStatoLavAperta = getExtraParam().get("showFilterStatoLavAperta") != null ? getExtraParam().get("showFilterStatoLavAperta") : "N";
		showFilterStatoTrasmissioneEmail = getExtraParam().get("showFilterStatoTrasmissioneEmail") != null ? getExtraParam().get("showFilterStatoTrasmissioneEmail") : "N";		
		showFilterCapoFilaFasc = getExtraParam().get("showFilterCapoFilaFasc") != null ? getExtraParam().get("showFilterCapoFilaFasc") : (AurigaLayout.getParametroDBAsBoolean("ATTIVA_CAPOFILA") ? "S" : "N");
		showFilterRegistroAltriRif = getExtraParam().get("showFilterRegistroAltriRif") != null ? getExtraParam().get("showFilterRegistroAltriRif") : "S";
		showFilterEstremiAttoAutAnn = getExtraParam().get("showFilterEstremiAttoAutAnn") != null ? getExtraParam().get("showFilterEstremiAttoAutAnn") : "S";
		showFilterDataPresaInCarico = getExtraParam().get("showFilterDataPresaInCarico") != null ? getExtraParam().get("showFilterDataPresaInCarico") : "S";
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_ARCH_DEPOSITO")){
			showFilterDataChiusura = getExtraParam().get("showFilterDataChiusura") != null ? getExtraParam().get("showFilterDataChiusura") : "S";
		} else {
			showFilterDataChiusura = "N";
		}
		
		showFilterInConoscenzaA = getExtraParam().get("showFilterInConoscenzaA") != null ? getExtraParam().get("showFilterInConoscenzaA") : "S";
		
		//---------------------- FILTRI URBANISTICA ----------------------
		showFilterEstremiProtUrbanisticaAltreNum = getExtraParam().get("showFilterEstremiProtUrbanisticaAltreNum") != null ? getExtraParam().get("showFilterEstremiProtUrbanisticaAltreNum") : "N";
		showFilterDataApprovazioneUrbanistica = getExtraParam().get("showFilterDataApprovazioneUrbanistica") != null ? getExtraParam().get("showFilterDataApprovazioneUrbanistica") : "N";
		showFilterUnitaDiConservazioneUrbanistica= getExtraParam().get("showFilterUnitaDiConservazioneUrbanistica") != null ? getExtraParam().get("showFilterUnitaDiConservazioneUrbanistica") : "N";
		showFilterDataEsitoCittadellaUrbanistica = getExtraParam().get("showFilterDataEsitoCittadellaUrbanistica") != null ? getExtraParam().get("showFilterDataEsitoCittadellaUrbanistica") : "N";
		showFilterRichiesteDaApprovareUrbanistica = getExtraParam().get("showFilterRichiesteDaApprovareUrbanistica") != null ? getExtraParam().get("showFilterRichiesteDaApprovareUrbanistica") : "N";
		showFilterRichiesteDaVerificareUrbanistica = getExtraParam().get("showFilterRichiesteDaVerificareUrbanistica") != null ? getExtraParam().get("showFilterRichiesteDaVerificareUrbanistica") : "N";
		showFilterRichiesteAppuntamentoUrbanistica = getExtraParam().get("showFilterRichiesteAppuntamentoUrbanistica") != null ? getExtraParam().get("showFilterRichiesteAppuntamentoUrbanistica") : "N";
		showFilterAppuntamentiDaFissare = getExtraParam().get("showFilterAppuntamentiDaFissare") != null ? getExtraParam().get("showFilterAppuntamentiDaFissare") : "N";
		
		/**
		 * FILTRO ATTO CIRCOSCRIZIONE
		 */
		String nomeOrganoDecentrato = AurigaLayout.getParametroDB("NOME_ORGANO_DECENTRATO");		
		if (nomeOrganoDecentrato != null && !"".equals(nomeOrganoDecentrato)){
			showFilterAttoCircoscrizione = getExtraParam().get("showFilterAttoCircoscrizione") != null ? getExtraParam().get("showFilterAttoCircoscrizione") : "S";
		} else {
			showFilterAttoCircoscrizione = "N";
		}
		
		/**
		 * FILTRI ORGANI COLLEGIALI
		 */
		if (AurigaLayout.isAttivaNuovaPropostaAtto2Completa()){
			showFilterOrganiCollegiali = getExtraParam().get("showFilterOrganiCollegiali") != null ? getExtraParam().get("showFilterOrganiCollegiali") : "S";
		} else {
			showFilterOrganiCollegiali = "N";
		}
		
		/**
		 * FILTRI ADSP
		 */
		// Filtro PERIZIA (solo per ADSP)
		if (AurigaLayout.isAttivoClienteADSP()){
			showFilterPerizia = getExtraParam().get("showFilterPerizia") != null ? getExtraParam().get("showFilterPerizia") : "S";
		} else {
			showFilterPerizia = "N";
		}

		// Filtro PRESENZA OPERE (solo per ADSP)
		if (AurigaLayout.isAttivoClienteADSP()){
			showFilterPresenzaOpere = getExtraParam().get("showFilterPresenzaOpere") != null ? getExtraParam().get("showFilterPresenzaOpere") : "S";
		} else {
			showFilterPresenzaOpere = "N";
		}
		
		
		// Filtro SOTTO TIPOLOGIA (solo per ADSP)
		if (AurigaLayout.isAttivoClienteADSP()){
			showFilterSottoTipologiaAtto = getExtraParam().get("showFilterSottoTipologiaAtto") != null ? getExtraParam().get("showFilterSottoTipologiaAtto") : "S";
		} else {
			showFilterSottoTipologiaAtto = "N";
		}
		
		// Filtro UO competente atto (solo per ADSP)
		if (AurigaLayout.isAttivoClienteADSP()){
			showFilterUOCompetente = getExtraParam().get("showFilterUOCompetente") != null ? getExtraParam().get("showFilterUOCompetente") : "S";
		} else {
			showFilterUOCompetente = "N";
		}
		
		// Filtro RdA e atti collegati
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVATO_ITER_RDA")){
			showFilterRdAeAttiCollegati = getExtraParam().get("showFilterRdAeAttiCollegati") != null ? getExtraParam().get("showFilterRdAeAttiCollegati") : "S";
		} else {
			showFilterRdAeAttiCollegati = "N";
		}
		
		/**
		 * FILTRI COTO
		 */
		if (AurigaLayout.isAttivoClienteCOTO()){
			showFilterCentroDiCosto = getExtraParam().get("showFilterCentroDiCosto") != null ? getExtraParam().get("showFilterCentroDiCosto") : "S";
		} else {
			showFilterCentroDiCosto = "N";
		}

		if (AurigaLayout.isAttivoClienteCMTO()){
			showFilterFlgSottopostoControlloRegAmm = getExtraParam().get("showFilterFlgSottopostoControlloRegAmm") != null ? getExtraParam().get("showFilterFlgSottopostoControlloRegAmm") : "S";
		} else {
			showFilterFlgSottopostoControlloRegAmm = "N";
		}
				
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")){
			showFilterDataScadenza = getExtraParam().get("showFilterDataScadenza") != null ? getExtraParam().get("showFilterDataScadenza") : "S";
		} else {
			showFilterDataScadenza = "N";
		}

		/**
		 * FILTRI A2A
		 */
		if (AurigaLayout.isAttivoClienteA2A()){
			showFilterStatiTrasfBloomfleet = getExtraParam().get("showFilterStatiTrasfBloomfleet") != null ? getExtraParam().get("showFilterStatiTrasfBloomfleet") : "S";
		} else {
			showFilterStatiTrasfBloomfleet = "N";
		}
		
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_PROT_AUTO_MAIL")){
			showRegoleRegistrazioneAutomaticaEmail = getExtraParam().get("showRegoleRegistrazioneAutomaticaEmail") != null ? getExtraParam().get("showRegoleRegistrazioneAutomaticaEmail") : "S";
		} else {
			showRegoleRegistrazioneAutomaticaEmail = "N";
		}
		
		// Filtro "Documenti protocollati in attesa immagini"
		showFilterProtNoScan = getExtraParam().get("showFilterProtNoScan") != null ? getExtraParam().get("showFilterProtNoScan") : "N";
				
		// Filtro "Immagini non associate ai protocolli"
		showFilterImmaginiNonAssociateAiProtocolli = getExtraParam().get("showFilterImmaginiNonAssociateAiProtocolli") != null ? getExtraParam().get("showFilterImmaginiNonAssociateAiProtocolli") : "N";
		
	}
	
	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
		
		// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE.DAPROT o D.BOZZE o D.2A.DAPROT, mostro solo i filtri :
		// "Anno bozza", 
		// "Classificazione",
		// "Data bozza",
		// "Data stesura", 
		// "Destinatario",
		// "Destinatario (in rubrica)", 
		// "Doc.collegati al nominativo"
		// "File associati" , 
		// "In competenza a", 
		// "Mezzo di trasmissione", 
		// "Mittente", 
		// "Mittente (in rubrica)", 
		// "Note",
		// "N bozza"
		// "Oggetto"
		// "Riservatezza"
		// "Supporto"
		// "Tipo documento"
		// "U.O.di registrazione"
		if (idNode !=null && (idNode.equalsIgnoreCase("D.BOZZE.DAPROT") || idNode.equalsIgnoreCase("D.BOZZE") || idNode.equalsIgnoreCase("D.2A.DAPROT"))) {
				LinkedHashMap<String, String> lMapNew = new LinkedHashMap<String, String>();
				for (String key : lMap.keySet()) {
					if (
							 key.equals("annoBozza")              ||
							 key.equals("classificazioneArchivio")||
							 key.equals("tsBozza")                || 
							 key.equals("dtStesura")              ||
							 key.equals("destinatario")           ||
							 key.equals("destinatarioInRubrica")  ||
							 key.equals("docCollegatiNominativo") ||
							 key.equals("fileAssociati")          ||
							 key.equals("assegnatoA")             ||
							 key.equals("mezzoTrasmissione")      ||
							 key.equals("mittente")               ||
							 key.equals("mittenteInRubrica")      ||
							 key.equals("noteUd")                 ||
							 key.equals("nroBozza")               ||
							 key.equals("oggetto")                ||
							 key.equals("flgRiservatezza")        ||
							 key.equals("supportoProt")           ||
							 key.equals("tipoDoc")                ||
							 key.equals("uoRegistrazione")  
					    ) 
					{
						lMapNew.put(key, lMap.get(key));
					}
				}
				return lMapNew;
			}
		
		
		// Se e' stato selezionato il nodo "Immagini non associate ai protocolli", mostro solo i filtri "Data scansione", "N° immagine", "Sedi"
		if (showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("S")) {
			LinkedHashMap<String, String> lMapNew = new LinkedHashMap<String, String>();
			for (String key : lMap.keySet()) {
				if (
						 key.equals("searchFulltext")                 || 
						 key.equals("dataScansioneMassiva")           || 
						 key.equals("nroImmagineScansioneMassiva")    || 
						 key.equals("sedeScansioneMassiva")
				    ) 
				{
					lMapNew.put(key, lMap.get(key));
				}
			}
			return lMapNew;
		}
		
		if (showFilterStampe.equalsIgnoreCase("S")) {
			LinkedHashMap<String, String> lMapNew = new LinkedHashMap<String, String>();
			for (String key : lMap.keySet()) {
				if (
						 key.equals("searchFulltext") || 
						 key.equals("annoStampa")     || 
						 key.equals("nroStampa")      || 
						 key.equals("tsStampa")       ||
						 key.equals("tipoDocStampa")  ||
						 key.equals("nroRichiestaStampaExp")  ||
						 key.equals("tsRichiestaStampaExp") 
				    ) 
				{
					lMapNew.put(key, lMap.get(key));
				}
			}
			return lMapNew;
		}

		// if(lMap.containsKey("flgUdFolder") && showFilterRestringiRicercaA.equalsIgnoreCase("N")) {
		// lMap.remove("flgUdFolder");
		// }
		
		if (lMap.containsKey("searchFulltext") && showFilterFullText.equalsIgnoreCase("N")) {
			lMap.remove("searchFulltext");
		}
		
		

		/** FILTRI DIN RICERCA ALTRE NUMERAZIONI **/
		if (lMap.containsKey("statoLavorazioneAperto") && showFilterStatoLavAperta.equalsIgnoreCase("N")) {
			lMap.remove("statoLavorazioneAperto");
		}

		if (lMap.containsKey("altraNumerazioneSigla") && showFilterAltraNumerazione.equalsIgnoreCase("N")) {
			lMap.remove("altraNumerazioneSigla");
		}
		if (lMap.containsKey("altraNumerazioneAnno") && showFilterAltraNumerazione.equalsIgnoreCase("N")) {
			lMap.remove("altraNumerazioneAnno");
		}
		if (lMap.containsKey("altraNumerazioneData") && showFilterAltraNumerazione.equalsIgnoreCase("N")) {
			lMap.remove("altraNumerazioneData");
		}
		if (lMap.containsKey("altraNumerazioneNr") && showFilterAltraNumerazione.equalsIgnoreCase("N")) {
			lMap.remove("altraNumerazioneNr");
		}
		/** ANTONIO **/
		if (lMap.containsKey("soloRegAnnullate") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("soloRegAnnullate");
		}
		if (lMap.containsKey("nroProtRicevuto") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("nroProtRicevuto");
		}
		if (lMap.containsKey("dataProtRicevuto") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("dataProtRicevuto");
		}
		if (lMap.containsKey("tipoDoc") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("tipoDoc");
		}	
		if (lMap.containsKey("supportoProt") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("supportoProt");
		}
		if (lMap.containsKey("flgAppostoTimbro") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("flgAppostoTimbro");
		}
		if (lMap.containsKey("sub") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("sub");
		}
		if (lMap.containsKey("sigla") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("sigla");
		}
		if (lMap.containsKey("uoRegistrazione") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("uoRegistrazione");
		}
		if (lMap.containsKey("flgRiservatezza") && (showFilterFascicoli.equalsIgnoreCase("N") && showFilterDocumenti.equalsIgnoreCase("N"))) {
			lMap.remove("flgRiservatezza");
		}
		if (lMap.containsKey("uoApertura") && (showFilterFascicoli.equalsIgnoreCase("N"))) {
			lMap.remove("uoApertura");
		}
		if (lMap.containsKey("flgUdFolder") && (showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("N"))) {
			lMap.remove("flgUdFolder");
		}
		if (lMap.containsKey("flgUdFolderFascicolo") && (showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("S"))) {
			lMap.remove("flgUdFolderFascicolo");
		}
		if (lMap.containsKey("tipoFolder") && showFilterFascicoli.equalsIgnoreCase("N")) {
			lMap.remove("tipoFolder");
		}
		if (lMap.containsKey("assegnatoA") && showFilterDdIA.equalsIgnoreCase("N")) {
			lMap.remove("assegnatoA");
		}
		if (lMap.containsKey("statoPresaInCarico") && showFilterDdIA.equalsIgnoreCase("N")) {
			lMap.remove("statoPresaInCarico");
		}
		if (lMap.containsKey("inviatiViaEmail") && showFilterDdIA.equalsIgnoreCase("N")) {
			lMap.remove("inviatiViaEmail");
		}
		if (lMap.containsKey("assegnatoA") && showFilterFdIA.equalsIgnoreCase("N")) {
			lMap.remove("assegnatoA");
		}
		if (lMap.containsKey("statoPresaInCarico") && showFilterFdIA.equalsIgnoreCase("N")) {
			lMap.remove("statoPresaInCarico");
		}
		/** ANTONIO **/		
		if (lMap.containsKey("annoBozza") && showFilterBozze.equalsIgnoreCase("N")) {
			lMap.remove("annoBozza");
		}
		if (lMap.containsKey("nroBozza") && showFilterBozze.equalsIgnoreCase("N")) {
			lMap.remove("nroBozza");
		}
		if (lMap.containsKey("tsBozza") && showFilterBozze.equalsIgnoreCase("N")) {
			lMap.remove("tsBozza");
		}
		if (lMap.containsKey("fileAssociati") && showFilterBozze.equalsIgnoreCase("N")) {
			lMap.remove("fileAssociati");
		}
		if (lMap.containsKey("annoStampa") && showFilterStampe.equalsIgnoreCase("N")) {
			lMap.remove("annoStampa");
		}
		if (lMap.containsKey("nroStampa") && showFilterStampe.equalsIgnoreCase("N")) {
			lMap.remove("nroStampa");
		}
		if (lMap.containsKey("tsStampa") && showFilterStampe.equalsIgnoreCase("N")) {
			lMap.remove("tsStampa");
		}
		if(lMap.containsKey("tipoDocStampa") && showFilterStampe.equalsIgnoreCase("N")) {
			lMap.remove("tipoDocStampa");					
		}
		if(lMap.containsKey("nroRichiestaStampaExp") && showFilterStampe.equalsIgnoreCase("N")) {
			lMap.remove("nroRichiestaStampaExp");					
		}
		if(lMap.containsKey("tsRichiestaStampaExp") && showFilterStampe.equalsIgnoreCase("N")) {
			lMap.remove("tsRichiestaStampaExp");					
		}

		if (lMap.containsKey("annoProt") && showFilterProtocollo.equalsIgnoreCase("N")) {
			lMap.remove("annoProt");
		}
		if (lMap.containsKey("nroProt") && showFilterProtocollo.equalsIgnoreCase("N")) {
			lMap.remove("nroProt");
		}
		if (lMap.containsKey("tipoProt") && showFilterProtocollo.equalsIgnoreCase("N")) {
			lMap.remove("tipoProt");
		}
		if (lMap.containsKey("tsRegistrazione") && showFilterProtocollo.equalsIgnoreCase("N")) {
			lMap.remove("tsRegistrazione");
		}
		if (lMap.containsKey("tsAperturaFascicolo") && showFilterDataApertura.equalsIgnoreCase("N")) {
			lMap.remove("tsAperturaFascicolo");
		}
		if (lMap.containsKey("tsChiusuraFascicolo") && showFilterDataChiusura.equalsIgnoreCase("N")) {
			lMap.remove("tsChiusuraFascicolo");
		}
		if (lMap.containsKey("flgSoloDaLeggere") && showFilterSoloDaLeggere.equalsIgnoreCase("N")) {
			lMap.remove("flgSoloDaLeggere");
		}
		if (lMap.containsKey("tsInvio") && showFilterInvio.equalsIgnoreCase("N")) {
			lMap.remove("tsInvio");
		}
		if (lMap.containsKey("destinatarioInvio") && showFilterInvio.equalsIgnoreCase("N")) {
			lMap.remove("destinatarioInvio");
		}
		if (lMap.containsKey("tsArchiviazione") && showFilterArchiviazione.equalsIgnoreCase("N")) {
			lMap.remove("tsArchiviazione");
		}
		if (lMap.containsKey("tsEliminazione") && showFilterEliminazione.equalsIgnoreCase("N")) {
			lMap.remove("tsEliminazione");
		}
		if (lMap.containsKey("sezioneEliminazioneDoc") && (showFilterEliminazione.equalsIgnoreCase("N") || !"D".equals(tipoNodo))) {
			lMap.remove("sezioneEliminazioneDoc");
		}
		if (lMap.containsKey("sezioneEliminazioneFasc") && (showFilterEliminazione.equalsIgnoreCase("N") || !"F".equals(tipoNodo))) {
			lMap.remove("sezioneEliminazioneFasc");
		}
		if (lMap.containsKey("soloDocRicevutiViaEmail") && showFilterRicevutiInviatiViaEmail.equalsIgnoreCase("N")) {
			lMap.remove("soloDocRicevutiViaEmail");
		}
		if (lMap.containsKey("inviatiViaEmail") && showFilterRicevutiInviatiViaEmail.equalsIgnoreCase("N")) {
			lMap.remove("inviatiViaEmail");
		}
		if (lMap.containsKey("statoPresaInCarico") && showFilterStatoPresaInCarico.equalsIgnoreCase("N")) {
			lMap.remove("statoPresaInCarico");
		}
		if (lMap.containsKey("statoRichAnnullamento") && showFilterStatoRichAnnullamento.equalsIgnoreCase("N")) {
			lMap.remove("statoRichAnnullamento");
		}
		if (lMap.containsKey("statoAutorizzazione") && showFilterStatoAutorizzazione.equalsIgnoreCase("N")) {
			lMap.remove("statoAutorizzazione");
		}
		if (lMap.containsKey("tsAssegnazione") && showFilterRicevutiPerCompetenza.equalsIgnoreCase("N")) {
			lMap.remove("tsAssegnazione");
		}
		if (lMap.containsKey("assegnatoA") && showFilterInCompetenzaA.equalsIgnoreCase("N")) {
			lMap.remove("assegnatoA");
		}
		if (lMap.containsKey("tsNotificaCC") && showFilterRicevutiPerConoscenzaCC.equalsIgnoreCase("N")) {
			lMap.remove("tsNotificaCC");
		}
		if (lMap.containsKey("inviatoA") && showFilterRicevutiPerConoscenzaCC.equalsIgnoreCase("N") && showFilterInConoscenzaA.equalsIgnoreCase("N")) {
			lMap.remove("inviatoA");
		}
		if (lMap.containsKey("tsNotificaNA") && showFilterRicevutiPerConoscenzaNA.equalsIgnoreCase("N")) {
			lMap.remove("tsNotificaNA");
		}
		if (lMap.containsKey("notificatoA") && showFilterRicevutiPerConoscenzaNA.equalsIgnoreCase("N")) {
			lMap.remove("notificatoA");
		}
		if (lMap.containsKey("mittente") && showFilterMittente.equalsIgnoreCase("N")) {
			lMap.remove("mittente");
		}
		if (lMap.containsKey("mittenteInRubrica") && showFilterMittente.equalsIgnoreCase("N")) {
			lMap.remove("mittenteInRubrica");
		}
		if (lMap.containsKey("esibente") && showFilterEsibente.equalsIgnoreCase("N")) {
			lMap.remove("esibente");
		}
		if (lMap.containsKey("esibenteInRubrica") && showFilterEsibente.equalsIgnoreCase("N")) {
			lMap.remove("esibenteInRubrica");
		}
		if (lMap.containsKey("destinatario") && showFilterDestinatario.equalsIgnoreCase("N")) {
			lMap.remove("destinatario");
		}
		if (lMap.containsKey("destinatarioInRubrica") && showFilterDestinatario.equalsIgnoreCase("N")) {
			lMap.remove("destinatarioInRubrica");
		}
		if (lMap.containsKey("oggetto") && showFilterOggetto.equalsIgnoreCase("N")) {
			lMap.remove("oggetto");
		}
		if (lMap.containsKey("nomeFascicolo") && showFilterNomeFascicolo.equalsIgnoreCase("N")) {
			lMap.remove("nomeFascicolo");
		}
		if (lMap.containsKey("regEffettuataDa") && showFilterRegEffettuataDa.equalsIgnoreCase("N")) {
			lMap.remove("regEffettuataDa");
		}
		if (lMap.containsKey("nroRaccomandata") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("nroRaccomandata");
		}
		if (lMap.containsKey("dtRaccomandata") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("dtRaccomandata");
		}
		
		/** FILTRI MODULO ATTI **/
		if (lMap.containsKey("uoProponente") && hideFilterModuloAtti()) {
			lMap.remove("uoProponente");
		}
		if (lMap.containsKey("utentiAvvioAtto") && hideFilterModuloAtti()) {
			lMap.remove("utentiAvvioAtto");
		}
		if (lMap.containsKey("utentiAdozioneAtto") && hideFilterModuloAtti()) {
			lMap.remove("utentiAdozioneAtto");
		}
		if (lMap.containsKey("statiDoc") && hideFilterModuloAtti()) {
			lMap.remove("statiDoc");
		}
		if (lMap.containsKey("dataFirmaAtto") && hideFilterModuloAtti()) {
			lMap.remove("dataFirmaAtto");
		}
		if (lMap.containsKey("capoFilaFasc") && (showFilterCapoFilaFasc.equalsIgnoreCase("N"))) {		
			lMap.remove("capoFilaFasc");
		}
		if (lMap.containsKey("registroAltriRifTipo") && (showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {		
			lMap.remove("registroAltriRifTipo");
		}
		if (lMap.containsKey("showFilterEstremiAttoAutAnn") && (showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {		
			lMap.remove("showFilterEstremiAttoAutAnn");
		}
		if (lMap.containsKey("registroAltriRifNro") && (showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {		
			lMap.remove("registroAltriRifNro");
		}		
		if (lMap.containsKey("registroAltriRifData") && (showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {		
			lMap.remove("registroAltriRifData");
		}
		if (lMap.containsKey("registroAltriRifAnno") && (showFilterRegistroAltriRif.equalsIgnoreCase("N"))) {		
			lMap.remove("registroAltriRifAnno");
		}
		//---------------------FILTRI URBANISTICA---------------------//
		//Filtri Comuni
		if (lMap.containsKey("estremiProtUrbanisticaAnno") && (showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {		
			lMap.remove("estremiProtUrbanisticaAnno");
		}
		if (lMap.containsKey("estremiProtUrbanisticaNro") && (showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {		
			lMap.remove("estremiProtUrbanisticaNro");
		}
		if (lMap.containsKey("estremiProtUrbanisticaData") && (showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {		
			lMap.remove("estremiProtUrbanisticaData");
		}
		if (lMap.containsKey("estremiAltreNumUrbanisticaNumero") && (showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {		
			lMap.remove("estremiAltreNumUrbanisticaNumero");
		}
		if (lMap.containsKey("estremiAltreNumUrbanisticaAnno") && (showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {		
			lMap.remove("estremiAltreNumUrbanisticaAnno");
		}
		if (lMap.containsKey("indirizzoEstremiProtUrbanistica") && (showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N"))) {		
			lMap.remove("indirizzoEstremiProtUrbanistica");
		}
		//FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if (lMap.containsKey("dataApprovazioneUrbanistica") && (showFilterDataApprovazioneUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("dataApprovazioneUrbanistica");
		}
		//FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF }
		if (lMap.containsKey("unitaDiConservazioneUrbanistica") && (showFilterUnitaDiConservazioneUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("unitaDiConservazioneUrbanistica");
		}
		//FILTRI comuni ad IdNode { D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if (lMap.containsKey("dataEsitoCittadellaUrbanistica") && (showFilterDataEsitoCittadellaUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("dataEsitoCittadellaUrbanistica");
		}
		//Filtri Richieste da approvare
		if (lMap.containsKey("invioApprovazioneUrbanistica") && (showFilterRichiesteDaApprovareUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("invioApprovazioneUrbanistica");
		}
		//Filtri Richieste da verificare
		if (lMap.containsKey("verificaCompletataUrbanistica") && (showFilterRichiesteDaVerificareUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("verificaCompletataUrbanistica");
		}
		//Filtri Richieste di appuntamento
		if (lMap.containsKey("dataAppuntUrbanisticaDataAppuntamento") && (showFilterRichiesteAppuntamentoUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("dataAppuntUrbanisticaDataAppuntamento");
		}
		if (lMap.containsKey("dataPrelievoUrbanisticaDataPrelievo") && (showFilterRichiesteAppuntamentoUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("dataPrelievoUrbanisticaDataPrelievo");
		}
		if (lMap.containsKey("richAppuntUrbanisticaPrelievoEffettuato") && (showFilterRichiesteAppuntamentoUrbanistica.equalsIgnoreCase("N"))) {		
			lMap.remove("richAppuntUrbanisticaPrelievoEffettuato");
		}
		//Filtri Appuntamenti da fissare
		if (lMap.containsKey("flgAppuntamentiDaFissare") && (showFilterAppuntamentiDaFissare.equalsIgnoreCase("N"))) {		
			lMap.remove("flgAppuntamentiDaFissare");
		}
		
		// Filtro CLASSIFICAZIONE
		if (lMap.containsKey("classificazioneArchivio") && showFilterRifClassificazione.equalsIgnoreCase("N")) {
			lMap.remove("classificazioneArchivio");
		}
		
		// Filtro ANNO FASCICOLO		
		if (lMap.containsKey("annoFascicolo") && showFilterRifFascicolo.equalsIgnoreCase("N")) {
			lMap.remove("annoFascicolo");
		}

		// Filtro NUMERO FASCICOLO		
		if (lMap.containsKey("nroFascicolo") && showFilterRifFascicolo.equalsIgnoreCase("N")) {
			lMap.remove("nroFascicolo");
		}
		
		// Filtro NUMERO SOTTO FASCICOLO		
		if (lMap.containsKey("nroSottoFascicolo") && showFilterRifFascicolo.equalsIgnoreCase("N")) {
			lMap.remove("nroSottoFascicolo");
		}

		// Filtro CODICE FASCICOLO		
		if (lMap.containsKey("codiceFascicolo") && showFilterRifFascicolo.equalsIgnoreCase("N")) {
			lMap.remove("codiceFascicolo");
		}
		
		// Filtro DATA STESURA
		if (lMap.containsKey("dtStesura") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("dtStesura");
		}
		
		// Filtro DOC. COLLEGATI AL NOMINATIVO
		if (lMap.containsKey("docCollegatiNominativo") && showFilterDocumenti.equalsIgnoreCase("N")) {
			lMap.remove("docCollegatiNominativo");
		}
		
		// Filtro data presa in carico
		if (lMap.containsKey("tsPresaInCarico") && showFilterDataPresaInCarico.equalsIgnoreCase("N")) {
			lMap.remove("tsPresaInCarico");
		}

		// Filtro perizia (solo per ADSP)
		if (lMap.containsKey("perizia") && showFilterPerizia.equalsIgnoreCase("N")) {
			lMap.remove("perizia");
		}
		
		// Filtro Presenza opere (solo per ADSP)
		if (lMap.containsKey("presenzaOpere") && showFilterPresenzaOpere.equalsIgnoreCase("N")) {
			lMap.remove("presenzaOpere");
		}
		
		// Filtro Sotto tipologia (solo per ADSP)
		if (lMap.containsKey("sottoTipologiaAtto") && showFilterSottoTipologiaAtto.equalsIgnoreCase("N")) {
			lMap.remove("sottoTipologiaAtto");
		}
		
		// Filtro CdC atto (solo per COTO)
		if (lMap.containsKey("centroDiCosto") && showFilterCentroDiCosto.equalsIgnoreCase("N")) {
			lMap.remove("centroDiCosto");
		}
		if (lMap.containsKey("attoCircoscrizione") && showFilterAttoCircoscrizione.equalsIgnoreCase("N")) {
			lMap.remove("attoCircoscrizione");
		}
		if (lMap.containsKey("inseritoInOdGDiscussioneSeduta") && showFilterOrganiCollegiali.equalsIgnoreCase("N")) {
			lMap.remove("inseritoInOdGDiscussioneSeduta");
		}
		
		// Filtro FlgSottopostoControlloRegAmm (solo per CMTO)
		if (lMap.containsKey("flgSottopostoControlloRegAmm") && showFilterFlgSottopostoControlloRegAmm.equalsIgnoreCase("N")) {
			lMap.remove("flgSottopostoControlloRegAmm");
		}
		
		// Filtro Data scadenza liquidazione		
		if (lMap.containsKey("dataScadenza") && showFilterDataScadenza.equalsIgnoreCase("N")) {
			lMap.remove("dataScadenza");
		}
		
		// Filtro 'Passato dallo smistamento'
		if (lMap.containsKey("flgPassaggioDaSmistamento") && (showFilterDocumenti.equalsIgnoreCase("N"))) {
			lMap.remove("flgPassaggioDaSmistamento");
		}
		
		
		// Filtro UO competente atto (solo per ADSP)
		if (lMap.containsKey("uoCompetente") && showFilterUOCompetente.equalsIgnoreCase("N")) {
			lMap.remove("uoCompetente");
		}

		// Filtro RdA e atti collegati
		if (lMap.containsKey("rdAeAttiCollegati") && showFilterRdAeAttiCollegati.equalsIgnoreCase("N")) {
			lMap.remove("rdAeAttiCollegati");
		}
		
		// Se e' stato selezionato il nodo "Documenti protocollati in attesa immagini", tolgo il filtro "File associati"
		if (lMap.containsKey("fileAssociati") && showFilterProtNoScan.equalsIgnoreCase("S")) {
			lMap.remove("fileAssociati");
		}
		
		// Se NON e' stato selezionato il nodo "Immagini non associate ai protocolli", tolgo i filtri "Data scansione", "N° immagine", "Sedi"
		if (lMap.containsKey("dataScansioneMassiva") && showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("N")) {
			lMap.remove("dataScansioneMassiva");
		}
		if (lMap.containsKey("nroImmagineScansioneMassiva") && showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("N")) {
			lMap.remove("nroImmagineScansioneMassiva");
		}
		if (lMap.containsKey("sedeScansioneMassiva") && showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("N")) {
			lMap.remove("sedeScansioneMassiva");
		}
				
		// Filtro 'Stato trasferimento a Bloomfleet' (solo per A2A)
		if (lMap.containsKey("statiTrasfBloomfleet") && (showFilterDocumenti.equalsIgnoreCase("N") || showFilterStatiTrasfBloomfleet.equalsIgnoreCase("N"))) {
			lMap.remove("statiTrasfBloomfleet");
		}

		// Filtro 'Regola reg. automatica'
		// quando sono in archivio showFilterFascicoli = S e showFilterDocumenti = S, se uno dei due è N o entrambi vuol dire che sono in scrivania
		// se sono in scrivania lo nascondo
		// praticamente va mostrato solo quando sono in archivio, e quindi ho sia fascicoli che documenti assieme
		if (lMap.containsKey("regoleRegistrazioneAutomaticaEmail")) {
			 if ( showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("N") || showRegoleRegistrazioneAutomaticaEmail.equalsIgnoreCase("N") ){
				lMap.remove("regoleRegistrazioneAutomaticaEmail");
			}
		}
		
		return lMap;
	}

	@Override
	public void createFilteredSelectItem(FilterFieldBean pFilterFieldBean, DataSourceField pDataSourceField) {
		SelectItemFiltrabile lSelectItem = new SelectItemFiltrabile(filter, pFilterFieldBean, pDataSourceField);
		if ("flgUdFolder".equals(pDataSourceField.getName())) {
			if(lSelectItem.getOptionDataSource() != null && (lSelectItem.getOptionDataSource() instanceof GWTRestDataSource)) {				
				GWTRestDataSource datasource = (GWTRestDataSource) lSelectItem.getOptionDataSource();
				datasource.addParam("tipoNodo", tipoNodo);
				lSelectItem.setOptionDataSource(datasource);
			}
		}
		pDataSourceField.setEditorType(lSelectItem);
		pDataSourceField.setFilterEditorType(SelectItem.class);
		mappaSelects.put(pFilterFieldBean.getName(), lSelectItem);
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		// Filtro "Restringi ricerca a"
		// if (showFilterRestringiRicercaA.equalsIgnoreCase("N")){
		// selected = selected + "flgUdFolder,";
		// }		
		// Filtri ricerca full-text
		if (showFilterFullText.equalsIgnoreCase("N")) {
			selected = selected + "searchFulltext,";
		}		
		// Filtri relativi alle bozze
		if (showFilterBozze.equalsIgnoreCase("N")) {
			selected = selected + "annoBozza,nroBozza,tsBozza,fileAssociati,";
		}
		/**** ANTONIO ***/
		// Filtri registrazioni annullate
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "soloRegAnnullate,nroProtRicevuto,dataProtRicevuto,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "tipoDoc,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "supportoProt,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "flgAppostoTimbro,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "sub,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "sigla,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "uoRegistrazione,";
		}
		if (showFilterFascicoli.equalsIgnoreCase("N") && showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "flgRiservatezza,";
		}
		if (showFilterFascicoli.equalsIgnoreCase("N")) {
			selected = selected + "uoApertura,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "nroRaccomandata,";
		}
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "dtRaccomandata,";
		}
		if (showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "flgUdFolder,";
		}
		if (showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("S")) {
			selected = selected + "flgUdFolderFascicolo,";
		}
		if (showFilterFascicoli.equalsIgnoreCase("N")) {
			selected = selected + "tipoFolder,";
		}
		// Filtri Documenti da assegnare/inviare
		if (showFilterDdIA.equalsIgnoreCase("N")) {
			selected = selected + "assegnatoA,statoPresaInCarico,inviatiViaEmail,";
		}
		// Filtri Fascicolo da assegnare/inviare
		if (showFilterFdIA.equalsIgnoreCase("N")) {
			selected = selected + "assegnatoA,statoPresaInCarico,";
		}
		/**** ANTONIO ***/
		// Filtri relativi alle stampe
		if (showFilterStampe.equalsIgnoreCase("N")) {
			selected = selected + "annoStampa,nroStampa,tsStampa,tipoDocStampa,nroRichiestaStampaExp,tsRichiestaStampaExp,";
		}
		// Filtri relativi al protocollo
		if (showFilterProtocollo.equalsIgnoreCase("N")) {
			selected = selected + "annoProt,nroProt,tipoProt,tsRegistrazione,";
		}
		// Filtro "Data apertura fascicolo"
		if (showFilterDataApertura.equalsIgnoreCase("N")) {
			selected = selected + "tsAperturaFascicolo,";
		}
		// Filtro "Data chiusura fascicolo"
		if (showFilterDataChiusura.equalsIgnoreCase("N")) {
			selected = selected + "tsChiusuraFascicolo,";
		}
		// Filtro "Da leggere"
		if (showFilterSoloDaLeggere.equalsIgnoreCase("N")) {
			selected = selected + "flgSoloDaLeggere,";
		}
		// Filtri relativi agli inviati
		if (showFilterInvio.equalsIgnoreCase("N")) {
			selected = selected + "tsInvio,destinatarioInvio,";
		}
		// Filtri relativi agli archiviati
		if (showFilterArchiviazione.equalsIgnoreCase("N")) {
			selected = selected + "tsArchiviazione,";
		}
		// Filtri relativi agli eliminati
		if (showFilterEliminazione.equalsIgnoreCase("N")) {
			selected = selected + "tsEliminazione,sezioneEliminazioneDoc,sezioneEliminazioneFasc,";
		} else {
			if ("D".equals(tipoNodo)) {
				selected = selected + "sezioneEliminazioneFasc,";
			} else if ("F".equals(tipoNodo)) {
				selected = selected + "sezioneEliminazioneDoc,";
			}
		}
		// Filtro "Ricevuti tramite e-mail" e "Inviati tramite e-mail"
		if (showFilterRicevutiInviatiViaEmail.equalsIgnoreCase("N")) {
			selected = selected + "soloDocRicevutiViaEmail,inviatiViaEmail,";
		}
		if (showFilterStatoPresaInCarico.equalsIgnoreCase("N")) {
			selected = selected + "statoPresaInCarico,";
		}
		if (showFilterStatoRichAnnullamento.equalsIgnoreCase("N")) {
			selected = selected + "statoRichAnnullamento,";
		}
		if (showFilterStatoAutorizzazione.equalsIgnoreCase("N")) {
			selected = selected + "statoAutorizzazione,";
		}
		if (showFilterInCompetenzaA.equalsIgnoreCase("N")) {
			selected = selected + "assegnatoA,";
		}
		if (showFilterRicevutiPerCompetenza.equalsIgnoreCase("N")) {
			selected = selected + "tsAssegnazione,";
		}
		if (showFilterRicevutiPerConoscenzaCC.equalsIgnoreCase("N")) {
			selected = selected + "tsNotificaCC,";
		}
		if (showFilterInConoscenzaA.equalsIgnoreCase("N") && showFilterRicevutiPerConoscenzaCC.equalsIgnoreCase("N")) {
			selected = selected + "inviatoA,";
		}
		if (showFilterRicevutiPerConoscenzaNA.equalsIgnoreCase("N")) {
			selected = selected + "tsNotificaNA,notificatoA,";
		}
		if (showFilterMittente.equalsIgnoreCase("N")) {
			selected = selected + "mittente,mittenteInRubrica,";
		}
		if (showFilterEsibente.equalsIgnoreCase("N")) {
			selected = selected + "esibente,esibenteInRubrica,";
		}
		if (showFilterDestinatario.equalsIgnoreCase("N")) {
			selected = selected + "destinatario,destinatarioInRubrica,";
		}
		if (showFilterOggetto.equalsIgnoreCase("N")) {
			selected = selected + "oggetto,";
		}
		if (showFilterNomeFascicolo.equalsIgnoreCase("N")) {
			selected = selected + "nomeFascicolo,";
		}
		if (showFilterRegEffettuataDa.equalsIgnoreCase("N")) {
			selected = selected + "regEffettuataDa,";
		}
		/** FILTRI DI RICERCA ALTRE NUMERAZIONI **/
		if (showFilterAltraNumerazione.equalsIgnoreCase("N")) {
			selected = selected + "altraNumerazioneSigla,altraNumerazioneAnno,altraNumerazioneData,altraNumerazioneNr,";
		}
		if (showFilterStatoLavAperta.equalsIgnoreCase("N")) {
			selected = selected + "statoLavorazioneAperto,";
		}
		/** FILTRI MODULO ATTI **/
		if (hideFilterModuloAtti()) {
			selected = selected + "uoProponente,utentiAvvioAtto,utentiAdozioneAtto,statiDoc,dataFirmaAtto,";
		}
		if (showFilterStampe.equalsIgnoreCase("S")) {
			// Recupero la select sui campi relativa all'ultima clausola
			FormItem lLastClauseFieldNameItem = getClauseFieldNameItem(getClauseStack().getMembers().length - 2);
			// Recupero la mappa
			LinkedHashMap<String, String> lMap = (LinkedHashMap<String, String>) JSOHelper.getAttributeAsMap(lLastClauseFieldNameItem.getJsObj(), "valueMap");
			for (String key : lMap.keySet()) {
				if (
                     !key.equals("searchFulltext")              && 
                     !key.equals("annoStampa")                  && 
                     !key.equals("nroStampa")                   && 
                     !key.equals("tsStampa")                    &&
                     !key.equals("tipoDocStampa")               &&
				     !key.equals("nroRichiestaStampaExp")       &&
				     !key.equals("tsRichiestaStampaExp")       
                   ) 
                {
					selected += key + ",";
				}
			}
		}
		// Filtro "Fascicolo - Capo fila"
		if (showFilterCapoFilaFasc.equalsIgnoreCase("N")) {		
			selected = selected + "capoFilaFasc,";
		}
		// Filtro "Prot. - Altri riferimenti - Tipo"
		if (showFilterRegistroAltriRif.equalsIgnoreCase("N")) {		
			selected = selected + "registroAltriRifTipo,";
		}
		// Filtro Atto aut.annullamento
		if(showFilterEstremiAttoAutAnn.equalsIgnoreCase("N")) {
			selected = selected + "attoAutAnnullamento,";
		}
		// Filtro "Prot. - Altri riferimenti - Nro"
		if (showFilterRegistroAltriRif.equalsIgnoreCase("N")) {		
			selected = selected + "registroAltriRifNro,";
		}
		// Filtro "Prot. - Altri riferimenti - Data"
		if (showFilterRegistroAltriRif.equalsIgnoreCase("N")) {		
			selected = selected + "registroAltriRifData,";
		}
		// Filtro "Prot. - Altri riferimenti - Anno"
		if (showFilterRegistroAltriRif.equalsIgnoreCase("N")) {		
			selected = selected + "registroAltriRifAnno,";
		}
		// -------------- FILTRI URBANISTICA --------------
		// Filtri Comuni
		if(showFilterEstremiProtUrbanisticaAltreNum.equalsIgnoreCase("N")){
			selected = selected + "estremiProtUrbanisticaAnno,estremiProtUrbanisticaNro,estremiProtUrbanisticaData,estremiAltreNumUrbanisticaNumero,"
					+ "estremiAltreNumUrbanisticaAnno,indirizzoEstremiProtUrbanistica,";
		}
		//FILTRI comuni ad IdNode { D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if(showFilterDataApprovazioneUrbanistica.equalsIgnoreCase("N")){
			selected = selected + "dataApprovazioneUrbanistica,";
		}
		//FILTRI comuni ad IdNode {  D.RAA.DV - D.RAA.ADF - D.RAA.AF }
		if(showFilterUnitaDiConservazioneUrbanistica.equalsIgnoreCase("N")){
			selected = selected + "unitaDiConservazioneUrbanistica,";
		}
		//FILTRI comuni ad IdNode {   D.RAA.ADF - D.RAA.AF - D.RAA.EN }
		if(showFilterDataEsitoCittadellaUrbanistica.equalsIgnoreCase("N")){
			selected = selected + "dataEsitoCittadellaUrbanistica,";
		}
		//Filtri Richieste da approvare
		if(showFilterRichiesteDaApprovareUrbanistica.equalsIgnoreCase("N")){
			selected = selected + "invioApprovazioneUrbanistica,";
		}
		//Filtri Richieste da verificare
		if(showFilterRichiesteDaVerificareUrbanistica.equalsIgnoreCase("N")){
			selected = selected + "verificaCompletataUrbanistica,";
		}
		//Filtri Richieste di appuntamento
		if(showFilterRichiesteAppuntamentoUrbanistica.equalsIgnoreCase("N")){
			selected = selected + "dataAppuntUrbanisticaDataAppuntamento,dataPrelievoUrbanisticaDataPrelievo,richAppuntUrbanisticaPrelievoEffettuato,";
		}
		//Filtri Appuntamenti da fissare
		if(showFilterAppuntamentiDaFissare.equalsIgnoreCase("N")){
			selected = selected + "flgAppuntamentiDaFissare,";
		}
			
		// Filtri CLASSIFICAZIONE
		if (showFilterRifClassificazione.equalsIgnoreCase("N")) {
			selected = selected + "classificazioneArchivio,";
		}
				
		// Filtri FASCICOLO ( anno, numero, sotto fasc, codice )
		if (showFilterRifFascicolo.equalsIgnoreCase("N")) {
			selected = selected + "annoFascicolo,nroFascicolo,nroSottoFascicolo,codiceFascicolo,";
		}
		
		if (showFilterDataPresaInCarico.equalsIgnoreCase("N")) {
			selected = selected + "tsPresaInCarico,";
		}
		
		// Filtro perizia (solo per ADSP)
		if (showFilterPerizia.equalsIgnoreCase("N")) {
			selected = selected + "perizia,";
		}
		
		// Filtro Presenza opere (solo per ADSP)
		if (showFilterPresenzaOpere.equalsIgnoreCase("N")) {
			selected = selected + "presenzaOpere,";
		}
		
		// Filtro Sotto tipologia atto(solo per ADSP)
		if (showFilterSottoTipologiaAtto.equalsIgnoreCase("N")) {
			selected = selected + "sottoTipologiaAtto,";
		}
		
		// Filtro CdC atto (solo per COTO)
		if (showFilterCentroDiCosto.equalsIgnoreCase("N")) {
			selected = selected + "centroDiCosto,";
		}
		
		// Filtro Atto Circoscrizione
		if (showFilterAttoCircoscrizione.equalsIgnoreCase("N")) {
			selected = selected + "attoCircoscrizione,";
		}

		// Filtri Organi Collegiali
		if (showFilterOrganiCollegiali.equalsIgnoreCase("N")) {
			selected = selected + "inseritoInOdGDiscussioneSeduta,";
		}
				
		// Filtro FlgSottopostoControlloRegAmm (solo per CMTO)
		if (showFilterFlgSottopostoControlloRegAmm.equalsIgnoreCase("N")) {
			selected = selected + "flgSottopostoControlloRegAmm,";
		}
						
		// Filtro Data scadenza liquidazione
		if (showFilterDataScadenza.equalsIgnoreCase("N")) {
			selected = selected + "dataScadenza,";
		}
		
		// Filtro 'Passato dallo smistamento'
		if (showFilterDocumenti.equalsIgnoreCase("N")) {
			selected = selected + "flgPassaggioDaSmistamento,";
		}
				
		
		// Filtro UO competente atto (solo per ADSP)
		if (showFilterUOCompetente.equalsIgnoreCase("N")) {
			selected = selected + "uoCompetente,";
		}
			
		// Filtro RdA e atti collegati
		if (showFilterRdAeAttiCollegati.equalsIgnoreCase("N")) {
			selected = selected + "rdAeAttiCollegati,";
		}
		
		// Se e' stato selezionato il nodo "Documenti protocollati in attesa immagini", tolgo il filtro "File associati"
		if (showFilterProtNoScan.equalsIgnoreCase("S")) {
			selected = selected + "fileAssociati,";
		}
		
		// Se NON e' stato selezionato il nodo "Immagini non associate ai protocolli", tolgo i filtri "Data scansione", "N° immagine", "Sedi"
		if (showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("N")) {
			selected = selected + "dataScansioneMassiva,nroImmagineScansioneMassiva,sedeScansioneMassiva,";
		}
		
		// Se e' stato selezionato il nodo "Immagini non associate ai protocolli", tolgo tutte le altre voci dalla combo dei filtri  
		if (showFilterImmaginiNonAssociateAiProtocolli.equalsIgnoreCase("S")) {
			// Recupero la select sui campi relativa all'ultima clausola
			FormItem lLastClauseFieldNameItem = getClauseFieldNameItem(getClauseStack().getMembers().length - 2);
			// Recupero la mappa
			LinkedHashMap<String, String> lMap = (LinkedHashMap<String, String>) JSOHelper.getAttributeAsMap(lLastClauseFieldNameItem.getJsObj(), "valueMap");
			for (String key : lMap.keySet()) {
				if (
                     !key.equals("searchFulltext")               && 
                     !key.equals("dataScansioneMassiva")         && 
                     !key.equals("nroImmagineScansioneMassiva")  && 
                     !key.equals("sedeScansioneMassiva") 
                   ) 
                {
					selected += key + ",";
				}
			}
		}
		
		// ottavio - AURIGA-520 : Se e' stato selezionato il nodo D.BOZZE.DAPROT o D.BOZZE o D.2A.DAPROT, tolgo tutte le altre voci dalla combo dei filtri
		if (idNode !=null && (idNode.equalsIgnoreCase("D.BOZZE.DAPROT") || idNode.equalsIgnoreCase("D.BOZZE") || idNode.equalsIgnoreCase("D.2A.DAPROT"))) {
			// Recupero la select sui campi relativa all'ultima clausola
			FormItem lLastClauseFieldNameItem = getClauseFieldNameItem(getClauseStack().getMembers().length - 2);
			// Recupero la mappa
			LinkedHashMap<String, String> lMap = (LinkedHashMap<String, String>) JSOHelper.getAttributeAsMap(lLastClauseFieldNameItem.getJsObj(), "valueMap");
			for (String key : lMap.keySet()) {
				if (
                     !key.equals("annoBozza")                    && 
                     !key.equals("classificazioneArchivio")      &&
                     !key.equals("tsBozza")                      && 
                     !key.equals("dtStesura")                    &&
                     !key.equals("destinatario")                 &&
                     !key.equals("destinatarioInRubrica")        &&
                     !key.equals("docCollegatiNominativo")       &&
                     !key.equals("fileAssociati")                &&
                     !key.equals("assegnatoA")                   &&
                     !key.equals("mezzoTrasmissione")            &&
                     !key.equals("mittente")                     &&
                     !key.equals("mittenteInRubrica")            &&
                     !key.equals("noteUd")                       &&
                     !key.equals("nroBozza")                     &&
                     !key.equals("oggetto")                      &&
                     !key.equals("flgRiservatezza")              &&
                     !key.equals("supportoProt")                 &&
                     !key.equals("tipoDoc")                      &&
                     !key.equals("uoRegistrazione")                    
                   ) 
                {
					selected += key + ",";
				}
			}
		}
		 
		// Filtro 'Stato trasferimento a Bloomfleet' (solo per A2A)
		if (showFilterDocumenti.equalsIgnoreCase("N") || showFilterStatiTrasfBloomfleet.equalsIgnoreCase("N")) {
			selected = selected + "statiTrasfBloomfleet,";
		}
				
		// Filtro 'Regola reg. automatica'
		// quando sono in archivio showFilterFascicoli = S e showFilterDocumenti = S, se uno dei due è N o entrambi vuol dire che sono in scrivania
		// se sono in scrivania lo nascondo
		// praticamente va mostrato solo quando sono in archivio, e quindi ho sia fascicoli che documenti assieme
		if ( showFilterFascicoli.equalsIgnoreCase("N") || showFilterDocumenti.equalsIgnoreCase("N") || showRegoleRegistrazioneAutomaticaEmail.equalsIgnoreCase("N") ){
			selected = selected + "regoleRegistrazioneAutomaticaEmail,";
		}
		
		for (Criterion lCriterion : lCriterions) {
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())) {
				selected += lCriterion.getFieldName() + ",";
			}
		}
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}

	public boolean hideFilterModuloAtti() {
		return fromScrivania || !AurigaLayout.getParametroDBAsBoolean("ATTIVATO_MODULO_ATTI");
	}
	
	@Override
	protected DataSourceField buildField(FilterFieldBean lFilterFieldBean) {
		DataSourceField lDataSourceField = null;
		if("tipoDoc".equals(lFilterFieldBean.getName())) {
			Map<String, String> lMapPropertyAttributiCustomDelTipo = new HashMap<String, String>();
			lMapPropertyAttributiCustomDelTipo.put("nomeTabella", lFilterFieldBean.getNomeTabella());
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RICERCA_PREGRESSO")) {
				if(nomeEntita != null && "archivio_pregresso".equals(nomeEntita)) {
					lMapPropertyAttributiCustomDelTipo.put("isArchivioPregresso", "true");	
				} else {
					lMapPropertyAttributiCustomDelTipo.put("isArchivioPregresso", "false");
				}
			}
			lDataSourceField = new AttributiCustomDelTipo(lFilterFieldBean.getName(), lFilterFieldBean.getTitle(), lMapPropertyAttributiCustomDelTipo);			
		} else if("indirizzo".equals(lFilterFieldBean.getName())) {		
			lDataSourceField = super.buildField(lFilterFieldBean);
			TextItem editorType = new TextItem();
			editorType.setWidth(200);
			lDataSourceField.setEditorType(editorType);				
		} else {
			lDataSourceField = super.buildField(lFilterFieldBean);
		}	
		if("centroDiCosto".equals(lFilterFieldBean.getName())) {
			// per il filtro CdC atto forzo solo l'operatore uguale a
			lDataSourceField.setValidOperators(OperatorId.EQUALS);
		}
		return lDataSourceField;
	}

	@Override
	protected FieldFetchDataSource getFieldFetchDataSource() {
		String idNode = getExtraParam().get("idNode") != null ? getExtraParam().get("idNode") : "";
		FieldFetchDataSource  lFieldFetchDataSource  = super.getFieldFetchDataSource();
		lFieldFetchDataSource.addParam("idNode", idNode);
		return lFieldFetchDataSource;
	}
}
