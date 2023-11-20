/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.agibilita.RichiesteAgibilitaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafeProcedimenti.AnagrafeProcedimentiLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.GruppiSoggettiLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.RubricaEmailLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.RuoliAmministrativiLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SoggettiDetail;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SoggettiLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.TopograficoLayout;
import it.eng.auriga.ui.module.layout.client.applicazioniEsterne.ApplicazioniEsterneLayout;
import it.eng.auriga.ui.module.layout.client.archiviaContratti.ContrattiLayout;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioLayout;
import it.eng.auriga.ui.module.layout.client.archivio.GenericWindow;
import it.eng.auriga.ui.module.layout.client.archivio.PraticaPregressaDetail;
import it.eng.auriga.ui.module.layout.client.attiAutorizzazione.AttiAutorizzazioneAnnRegWindow;
import it.eng.auriga.ui.module.layout.client.attributiCustom.AttributiCustomLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.campionamentoAtti.RegoleCampionamentoLayout;
import it.eng.auriga.ui.module.layout.client.campionamentoAtti.RichiestaCampionePopup;
import it.eng.auriga.ui.module.layout.client.campionamentoAtti.VerificaRaffinamentoCampioniLayout;
import it.eng.auriga.ui.module.layout.client.caricamentorubriche.CaricamentoRubricaClientiWindow;
import it.eng.auriga.ui.module.layout.client.caselleEmail.CaselleEmailLayout;
import it.eng.auriga.ui.module.layout.client.chiusuraMassivaEmail.ChiusuraMassivaEmailWindow;
import it.eng.auriga.ui.module.layout.client.configurazioneFlussi.ConfigurazioneFlussiLayout;
import it.eng.auriga.ui.module.layout.client.defattivitaprocedimenti.DefAttivitaProcedimentiLayout;
import it.eng.auriga.ui.module.layout.client.deleghe.AttivaDisattivaSessioneInDelegaWindow;
import it.eng.auriga.ui.module.layout.client.deleghe.DefinizioneDelegheWindow;
import it.eng.auriga.ui.module.layout.client.diagrammi.DiagrammiLayout;
import it.eng.auriga.ui.module.layout.client.documentidarifirmare.DocumentiDaRifirmareWindow;
import it.eng.auriga.ui.module.layout.client.docviapec.RichiesteDocViaPecLayout;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorWindow;
import it.eng.auriga.ui.module.layout.client.elenchiAlbi.SelezionaTipoElencoAlboWindow;
import it.eng.auriga.ui.module.layout.client.firmamultiplahash.TestFirmaMultiplaHashLayout;
import it.eng.auriga.ui.module.layout.client.foglioimportato.ContenutoFoglioImportatoLayout;
import it.eng.auriga.ui.module.layout.client.foglioimportato.FoglioImportatoLayout;
import it.eng.auriga.ui.module.layout.client.gestioneAutotuteleCed.RichiesteAutotuteleCedInIterLayout;
import it.eng.auriga.ui.module.layout.client.gestioneAutotuteleCed.RichiesteAutotuteleCedPersonaliLayout;
import it.eng.auriga.ui.module.layout.client.gestioneContenutiAmministrazioneTrasparente.ContenutiAmmTraspLayout;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.avvioProcedimento.AvvioProcedimentoWindow;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.procedimentiInIter.ProcedimentiInIterLayout;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.procedimentiPersonali.ProcedimentiPersonaliLayout;
import it.eng.auriga.ui.module.layout.client.gestioneTSO.TSOInIterLayout;
import it.eng.auriga.ui.module.layout.client.gestioneTSO.TSOPersonaliLayout;
import it.eng.auriga.ui.module.layout.client.gestioneUtenti.GestioneUtentiLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.attiinlavorazione.AttiCompletiInLavorazioneLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.attiinlavorazione.AttiInLavorazioneLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.delibere.ConvocazioneSedutaDetail;
import it.eng.auriga.ui.module.layout.client.gestioneatti.delibere.DiscussioneSedutaDetail;
import it.eng.auriga.ui.module.layout.client.gestioneatti.delibere.StoricoConvocazioneSedutaDetail;
import it.eng.auriga.ui.module.layout.client.gestioneatti.delibere.StoricoDiscussioneSedutaDetail;
import it.eng.auriga.ui.module.layout.client.gestioneatti.personali.AttiCompletiPersonaliLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.personali.AttiPersonaliLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.info.InformazioniWindow;
import it.eng.auriga.ui.module.layout.client.invioRaccomandate.InvioRaccomandateLayout;
import it.eng.auriga.ui.module.layout.client.istanzePortaleRiscossioneDaIstruire.IstanzePortaleRiscossioneDaIstruireLayout;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaTipoAttoWindow;
import it.eng.auriga.ui.module.layout.client.librofirmamassiva.LibroFirmaMassivaWindow;
import it.eng.auriga.ui.module.layout.client.logOperazioni.LogOperazioniLayout;
import it.eng.auriga.ui.module.layout.client.monitoraggioOperazioniBatch.MonitoraggioOperazioniBatchLayout;
import it.eng.auriga.ui.module.layout.client.monitoraggioPdV.MonitoraggioPdVLayout;
import it.eng.auriga.ui.module.layout.client.oggettario.OggettarioLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.OrganigrammaLayout;
import it.eng.auriga.ui.module.layout.client.passwordScaduta.CambioPasswordWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.FirmaEmailHtmlPopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaLayout;
import it.eng.auriga.ui.module.layout.client.profili.ProfiliLayout;
import it.eng.auriga.ui.module.layout.client.proposteOrganigramma.AvvioPropostaOrganigrammaWindow;
import it.eng.auriga.ui.module.layout.client.proposteOrganigramma.proposteOrgInIter.ProposteOrganigrammaInIterLayout;
import it.eng.auriga.ui.module.layout.client.proposteOrganigramma.proposteOrgPersonali.ProposteOrganigrammaPersonaliLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneContrattiBarcodeWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailBozze;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailEntrata;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailInterna;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailModelli;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailUscita;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocolloPregressoDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.RegistrazioneMultiplaUscitaDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.RegistroFattureDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.RepertorioDetailEntrata;
import it.eng.auriga.ui.module.layout.client.protocollazione.RepertorioDetailInterno;
import it.eng.auriga.ui.module.layout.client.protocollazione.RepertorioDetailUscita;
import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaTipoDocPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.ShowItemFunction;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaFaldoneWindow;
import it.eng.auriga.ui.module.layout.client.pubblicazioneAlbo.NuovaRichiestaPubblicazioneWindow;
import it.eng.auriga.ui.module.layout.client.pubblicazioneAlbo.PubblicazioneAlboConsultazioneRichiesteLayout;
import it.eng.auriga.ui.module.layout.client.pubblicazioneAlbo.PubblicazioneAlboRicercaPubblicazioniLayout;
import it.eng.auriga.ui.module.layout.client.registriNumerazione.RegistriNumerazioneLayout;
import it.eng.auriga.ui.module.layout.client.registroDocumenti.RegistroDocumentiLayout;
import it.eng.auriga.ui.module.layout.client.regoleProtocollazioneAutomaticaCaselle.RegoleProtocollazioneAutomaticaCaselleLayout;
import it.eng.auriga.ui.module.layout.client.report.ListaAccompagnatoriaProtocollazioniWindow;
import it.eng.auriga.ui.module.layout.client.report.StatisticheCogitoWindow;
import it.eng.auriga.ui.module.layout.client.report.StatisticheDocumentiWindow;
import it.eng.auriga.ui.module.layout.client.report.StatisticheMailStoricizzateWindow;
import it.eng.auriga.ui.module.layout.client.report.StatisticheMailWindow;
import it.eng.auriga.ui.module.layout.client.report.StatisticheProtocolloWindow;
import it.eng.auriga.ui.module.layout.client.report.StatisticheTrasparenzaAmministrativaWindow;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiDetail;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.VisureInIterLayout;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.VisurePersonaliLayout;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoDocumento.RichiestaAccessoDocumentoPopUp;
import it.eng.auriga.ui.module.layout.client.richiesteAutotutelaCed.RichiesteAutotutelaCedLayout;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.auriga.ui.module.layout.client.stampaRegProt.StampaRegProtWindow;
import it.eng.auriga.ui.module.layout.client.sub_profili.SubProfiliLayout;
import it.eng.auriga.ui.module.layout.client.tipoFascicoliAggr.TipoFascicoliAggregatiLayout;
import it.eng.auriga.ui.module.layout.client.tipologieDocumentali.TipologieDocumentaliLayout;
import it.eng.auriga.ui.module.layout.client.titolario.TitolarioLayout;
import it.eng.auriga.ui.module.layout.client.toponomastica.LookupViarioPopup;
import it.eng.auriga.ui.module.layout.client.vocabolario.VocabolarioLayout;
import it.eng.utility.ui.module.core.client.UserInterfaceConfig;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.util.ClientFactory;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.SavePreferenceAction;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterLookupType;
import it.eng.utility.ui.module.layout.shared.bean.GetListaDefPrefsBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

public class AurigaUserInterfaceConfig implements UserInterfaceConfig {
	
	private Boolean isAttivaAccessibilita=null;

	@Override
	public Canvas getPortletLayout(final String nomeEntita, HashMap<String, String> params) {

		final boolean isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");
		if ("archivio".equals(nomeEntita)) {
			if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
				GenericWindow window = new GenericWindow(new ArchivioLayout(), nomeEntita, I18NUtil.getMessages().menu_archivio_title(), "menu/archivio.png");
				window.show();
				return null;
			} else {
				return new ArchivioLayout();
			}

		} else if ("titolario".equals(nomeEntita)) {
			return new TitolarioLayout();
		} else if ("organigramma".equals(nomeEntita)) {
			return new OrganigrammaLayout();
		} else if ("vocabolario".equals(nomeEntita)) {
			return new VocabolarioLayout();
		} else if ("tipologiedocumentali".equals(nomeEntita)) {
			return new TipologieDocumentaliLayout();
		} else if ("tipofascicoliaggr".equals(nomeEntita)) {
			return new TipoFascicoliAggregatiLayout();
		} else if ("scrivania".equals(nomeEntita)) {
			if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
				GenericWindow window = new GenericWindow(new ScrivaniaLayout(), nomeEntita, I18NUtil.getMessages().menu_scrivania_title(),
						"menu/scrivania.png");
				window.show();
				return null;
			} else {
				return new ScrivaniaLayout();
			}
		} else if ("atti_autorizzazione_ann_reg".equals(nomeEntita)) {
			AttiAutorizzazioneAnnRegWindow lAttiAutorizzazioneAnnRegWindow = new AttiAutorizzazioneAnnRegWindow();
			lAttiAutorizzazioneAnnRegWindow.show();
			return null;
		} else if ("richieste_agib".equals(nomeEntita)) {
			return new RichiesteAgibilitaLayout();
		} else if ("stampa_reg_prot".equals(nomeEntita)) {
			StampaRegProtWindow lStampaRegProtWindow = new StampaRegProtWindow(nomeEntita);
			lStampaRegProtWindow.show();
			return null;
		} else if ("stampa_reg_repertorio".equals(nomeEntita)) {
			StampaRegProtWindow lStampaRegProtWindow = new StampaRegProtWindow(nomeEntita);
			lStampaRegProtWindow.show();
			return null;
		} else if ("stampa_reg_proposte_atti".equals(nomeEntita)) {
			StampaRegProtWindow lStampaRegProtWindow = new StampaRegProtWindow(nomeEntita);
			lStampaRegProtWindow.show();
			return null;
		} else if ("stampa_reg_pubblicazioni".equals(nomeEntita)) {
			StampaRegProtWindow lStampaRegProtWindow = new StampaRegProtWindow(nomeEntita);
			lStampaRegProtWindow.show();
			return null;
		} else if ("protocollazione_entrata".equals(nomeEntita)) {
			// PROTOCOLLAZIONE IN ENTRATA
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaEntrata")) {
				String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtEntrata");
				String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtEntrata");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
					initialValues.put("codCategoriaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("codCategoriaAltraNumerazioneEntrata"));
					initialValues.put("siglaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("siglaAltraNumerazioneEntrata"));
				}
				ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(null);
				protocollazioneDetailEntrata.nuovoDettaglio(null, initialValues);
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					protocollazioneDetailEntrata.setTabIndex(-1);
					protocollazioneDetailEntrata.setCanFocus(false);
					ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailEntrata, "menu/protocollazione_entrata.png", nomeEntita,
							I18NUtil.getMessages().menu_protocollazione_entrata_title());
					window.show();
					return null;
				} else {
					return protocollazioneDetailEntrata;
				}
			} else {
				String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtEntrata");
//				String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtEntrata");				
				AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "PG", null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
						String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;						
						Map<String, Object> initialValues = new HashMap<String, Object>();
						if (idTipoDoc != null && !"".equals(idTipoDoc)) {
							initialValues.put("tipoDocumento", idTipoDoc);
							initialValues.put("nomeTipoDocumento", nomeTipoDoc);
							initialValues.put("codCategoriaAltraNumerazione", lRecordTipoDoc.getAttribute("codCategoriaAltraNumerazione"));
							initialValues.put("siglaAltraNumerazione", lRecordTipoDoc.getAttribute("siglaAltraNumerazione"));
						}
						ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(null);
						protocollazioneDetailEntrata.nuovoDettaglio(null, initialValues);
						if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
							ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailEntrata, "menu/protocollazione_entrata.png",
									nomeEntita, I18NUtil.getMessages().menu_protocollazione_entrata_title());
							window.show();
						} else {
							Layout.addPortlet(nomeEntita, protocollazioneDetailEntrata);
						}
					}
				});
				return null;
			}
		} else if ("protocollazione_uscita".equals(nomeEntita)) {
			// PROTOCOLLAZIONE IN USCITA
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaProtUscita")) {
				String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtUscita");
				String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtUscita");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
					initialValues.put("codCategoriaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("codCategoriaAltraNumerazioneUscita"));
					initialValues.put("siglaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("siglaAltraNumerazioneUscita"));
				}
				ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(null);
				protocollazioneDetailUscita.nuovoDettaglio(null, initialValues);
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailUscita, "menu/protocollazione_uscita.png", nomeEntita,
							I18NUtil.getMessages().menu_protocollazione_uscita_title());
					window.show();
					return null;
				} else {
					return protocollazioneDetailUscita;
				}
			} else {
				String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtUscita");
//				String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtUscita");
				AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "PG", null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
						String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;						
						Map<String, Object> initialValues = new HashMap<String, Object>();
						if (idTipoDoc != null && !"".equals(idTipoDoc)) {
							initialValues.put("tipoDocumento", idTipoDoc);
							initialValues.put("nomeTipoDocumento", nomeTipoDoc);
							initialValues.put("codCategoriaAltraNumerazione", lRecordTipoDoc.getAttribute("codCategoriaAltraNumerazione"));
							initialValues.put("siglaAltraNumerazione", lRecordTipoDoc.getAttribute("siglaAltraNumerazione"));
						}
						ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(null);
						protocollazioneDetailUscita.nuovoDettaglio(null, initialValues);
						if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
							ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailUscita, "menu/protocollazione_uscita.png", nomeEntita,
									I18NUtil.getMessages().menu_protocollazione_uscita_title());
							window.show();
						} else {
							Layout.addPortlet(nomeEntita, protocollazioneDetailUscita);
						}
					}
				});
				return null;
			}
		} else if ("protocollazione_interna".equals(nomeEntita)) {
			// PROTOCOLLAZIONE TRA UFFICI
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaProtInterna")) {
				String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtInterna");
				String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtInterna");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
					initialValues.put("codCategoriaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("codCategoriaAltraNumerazioneInterna"));
					initialValues.put("siglaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("siglaAltraNumerazioneInterna"));
				}
				ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(null);
				protocollazioneDetailInterna.nuovoDettaglio(null, initialValues);
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailInterna, "menu/protocollazione_interna.png", nomeEntita,
							I18NUtil.getMessages().menu_protocollazione_interna_title());
					window.show();
				} else {
					return protocollazioneDetailInterna;
				}
				return null;
			} else {
				String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtInterna");
//				String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtInterna");
				String categoriaReg = AurigaLayout.getParametroDBAsBoolean("ATTIVA_REGISTRO_PG_X_PROT_INTERNA") ? "PG" : "I";
				String siglaReg = AurigaLayout.getParametroDBAsBoolean("ATTIVA_REGISTRO_PG_X_PROT_INTERNA") ? null : "P.I.";
				AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, categoriaReg, siglaReg, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
						String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;						
						Map<String, Object> initialValues = new HashMap<String, Object>();
						if (idTipoDoc != null && !"".equals(idTipoDoc)) {
							initialValues.put("tipoDocumento", idTipoDoc);
							initialValues.put("nomeTipoDocumento", nomeTipoDoc);
							initialValues.put("codCategoriaAltraNumerazione", lRecordTipoDoc.getAttribute("codCategoriaAltraNumerazione"));
							initialValues.put("siglaAltraNumerazione", lRecordTipoDoc.getAttribute("siglaAltraNumerazione"));
						}
						ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(null);
						protocollazioneDetailInterna.nuovoDettaglio(null, initialValues);
						if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
							ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailInterna, "menu/protocollazione_interna.png",
									nomeEntita, I18NUtil.getMessages().menu_protocollazione_interna_title());
							window.show();
						} else {
							Layout.addPortlet(nomeEntita, protocollazioneDetailInterna);
						}
					}
				});
				return null;
			}
		} else if("protocollazione_contratti_barcode".equals(nomeEntita)) {
			ProtocollazioneContrattiBarcodeWindow protocollazioneContrattiBarcodeWindow = new ProtocollazioneContrattiBarcodeWindow();
			protocollazioneContrattiBarcodeWindow.show();
		} else if ("compilazione_modello".equals(nomeEntita)) {
			// COMPILAZIONE MODULO	
			final boolean required = true;
			final String idTipoDocDefault = null;
			final String categoriaReg = "PG";
			final String siglaReg = null;
			final ServiceCallback<Record> callback = new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordTipoDoc) {

					String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
					String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;						
					Map<String, Object> initialValues = new HashMap<String, Object>();
					if (idTipoDoc != null && !"".equals(idTipoDoc)) {
						initialValues.put("tipoDocumento", idTipoDoc);
						initialValues.put("nomeTipoDocumento", nomeTipoDoc);
						initialValues.put("codCategoriaAltraNumerazione", lRecordTipoDoc.getAttribute("codCategoriaAltraNumerazione"));
						initialValues.put("siglaAltraNumerazione", lRecordTipoDoc.getAttribute("siglaAltraNumerazione"));
						initialValues.put("flgTipoProtModulo", lRecordTipoDoc.getAttribute("flgTipoProtModulo"));
					}
					ProtocollazioneDetailModelli protocollazioneDetailModelli = new ProtocollazioneDetailModelli(nomeEntita + "." + idTipoDoc);
					protocollazioneDetailModelli.nuovoDettaglio(null, initialValues);
					if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
						ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailModelli, "menu/compilazione_modello.png", nomeEntita,
								"Compilazione modulo " + nomeTipoDoc);
						window.show();
					} else {
						Layout.addPortlet(nomeEntita + "." + idTipoDoc, "Compilazione modulo " + nomeTipoDoc, "menu/compilazione_modello.png",
								protocollazioneDetailModelli);
					}
				}
			};
			GWTRestDataSource idTipoDocumentoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
			idTipoDocumentoDS.addParam("showErrorMsg", "true");
			idTipoDocumentoDS.addParam("categoriaReg", categoriaReg);
			idTipoDocumentoDS.addParam("siglaReg", siglaReg);
			idTipoDocumentoDS.addParam("isCompilazioneModulo", "true");
			idTipoDocumentoDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					if (response.getData().length == 0) {
						AurigaLayout.addMessage(new MessageBean( "Nessun modulo disponibile", "", MessageType.ERROR));
					} else if (response.getData().length == 1) {
						Record lRecord = new Record();
						if (required && response.getData().length == 1) {
							lRecord.setAttribute("idTipoDocumento", response.getData()[0].getAttribute("idTipoDocumento"));
							lRecord.setAttribute("descTipoDocumento", response.getData()[0].getAttribute("descTipoDocumento"));
							lRecord.setAttribute("codCategoriaAltraNumerazione", response.getData()[0].getAttribute("codCategoriaAltraNumerazione"));
							lRecord.setAttribute("siglaAltraNumerazione", response.getData()[0].getAttribute("siglaAltraNumerazione"));
							lRecord.setAttribute("flgTipoDocConVie", response.getData()[0].getAttributeAsBoolean("flgTipoDocConVie"));
							lRecord.setAttribute("flgOggettoNonObblig", response.getData()[0].getAttributeAsBoolean("flgOggettoNonObblig"));
							lRecord.setAttribute("flgTipoProtModulo", response.getData()[0].getAttribute("flgTipoProtModulo"));
						}
						if (callback != null) {
							callback.execute(lRecord);
						}
					} else {
						new SceltaTipoDocPopup(required, idTipoDocDefault, null, categoriaReg, siglaReg, null, callback) {
							
							@Override
							public boolean isCompilazioneModulo() {
								return true;
							}														
						};
					}
				}
			});
			return null;
		} else if ("stampa_etichetta_faldone".equals(nomeEntita)) {
			StampaEtichettaFaldoneWindow lStampaEtichettaFaldoneWindow = new StampaEtichettaFaldoneWindow();
			lStampaEtichettaFaldoneWindow.show();
			return null;
		} else if ("oggettario".equals(nomeEntita)) {
			return new OggettarioLayout();
		} else if ("gestioneutenti".equals(nomeEntita)) {
			return new GestioneUtentiLayout();
		} else if ("attributi_custom".equals(nomeEntita)) {
			return new AttributiCustomLayout();
		} else if ("anagrafiche_soggetti".equals(nomeEntita)) {
			return new SoggettiLayout();
		} else if ("definizione_attivita_procedimenti".equals(nomeEntita)) {
			return new DefAttivitaProcedimentiLayout();
		} else if ("anagrafe_procedimenti".equals(nomeEntita)) {
			return new AnagrafeProcedimentiLayout();
		} else if ("gruppi_soggetti".equals(nomeEntita)) {
			return new GruppiSoggettiLayout();
		} else if ("anagrafiche_rubricaemail".equals(nomeEntita)) {
			return new RubricaEmailLayout();
		} else if ("anagrafiche_topografico".equals(nomeEntita)) {
			return new TopograficoLayout();
		} else if ("invio_documentazione_via_pec".equals(nomeEntita)) {
			return new RichiesteDocViaPecLayout();
		} else if ("repertorio_entrata".equals(nomeEntita)) {
			buildRepertorio(nomeEntita, "E");
		} else if ("repertorio_interno".equals(nomeEntita)) {
			buildRepertorio(nomeEntita, "I");
		} else if ("repertorio_uscita".equals(nomeEntita)) {
			buildRepertorio(nomeEntita, "U");
		} else if("repertorio_accesso_civico".equals(nomeEntita)) {
			buildRepertorioAccessoCivico(nomeEntita);
		} else if("repertorio_contratti".equals(nomeEntita)) {
			buildRepertorioContratti(nomeEntita);
		} else if("repertorio_trasparenza".equals(nomeEntita)) {
			buildRepertorioTrasparenza(nomeEntita);
		} else if ("registro_fatture".equals(nomeEntita)) {
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRegistroFatture")) {
				String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRegistroFatture");
				String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRegistroFatture");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
					initialValues.put("protocolloGenerale", true);
				}
				RegistroFattureDetail registroFattureDetail = new RegistroFattureDetail(nomeEntita);
				registroFattureDetail.nuovoDettaglio(null, initialValues);
				return registroFattureDetail;
			} else {
				String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRegistroFatture");
				// String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRegistroFatture");
				AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, null, null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
						String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
						Map<String, Object> initialValues = new HashMap<String, Object>();
						if (idTipoDoc != null && !"".equals(idTipoDoc)) {
							initialValues.put("tipoDocumento", idTipoDoc);
							initialValues.put("nomeTipoDocumento", nomeTipoDoc);
							initialValues.put("protocolloGenerale", true);
						}
						RegistroFattureDetail registroFattureDetail = new RegistroFattureDetail(nomeEntita);
						registroFattureDetail.nuovoDettaglio(null, initialValues);
						Layout.addPortlet(nomeEntita, registroFattureDetail);
					}
				});
				return null;
			}
		} else if ("numerazione_provvisoria".equals(nomeEntita)) {
			// IN BOZZA
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaBozza")) {
				String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoBozza");
				String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoBozza");
				Boolean flgTipoDocConVie = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgTipoDocConVieBozza");
				Boolean flgOggettoNonObblig = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgOggettoNonObbligBozza");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
					initialValues.put("flgTipoDocConVie", flgTipoDocConVie);
					initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
				}
				ProtocollazioneDetailBozze protocollazioneDetailBozze = ProtocollazioneUtil.buildProtocollazioneDetailBozze(null, initialValues, null);
				protocollazioneDetailBozze.nuovoDettaglio(null, initialValues);
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailBozze, "menu/numerazione_provvisoria.png", nomeEntita,
							I18NUtil.getMessages().menu_numerazione_provvisoria_title());
					window.show();
					return null;
				} else {
				return protocollazioneDetailBozze;
				}
			} else {
				String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoBozza");
				// String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoBozza");
				AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, null, null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
						String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
						Boolean flgTipoDocConVie = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgTipoDocConVie") : null;
						Boolean flgOggettoNonObblig = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgOggettoNonObblig") : null;
						Map<String, Object> initialValues = new HashMap<String, Object>();
						if (idTipoDoc != null && !"".equals(idTipoDoc)) {
							initialValues.put("tipoDocumento", idTipoDoc);
							initialValues.put("nomeTipoDocumento", nomeTipoDoc);
							initialValues.put("flgTipoDocConVie", flgTipoDocConVie);
							initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
						}
						ProtocollazioneDetailBozze protocollazioneDetailBozze = ProtocollazioneUtil.buildProtocollazioneDetailBozze(null, initialValues, null);
						protocollazioneDetailBozze.nuovoDettaglio(null, initialValues);
						if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
							ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailBozze, "menu/numerazione_provvisoria.png", nomeEntita,
									I18NUtil.getMessages().menu_numerazione_provvisoria_title());
							window.show();
						} else {
						Layout.addPortlet(nomeEntita, protocollazioneDetailBozze);
					}
					}
				});
				return null;
			}
		} else if ("registrazione_multipla_uscita".equals(nomeEntita)) {			
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRegMultiplaUscita")) {
				String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRegMultiplaUscita");
				String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRegMultiplaUscita");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
					initialValues.put("codCategoriaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("codCategoriaAltraNumerazioneRegMultiplaUscita"));
					initialValues.put("siglaAltraNumerazione", AurigaLayout.getImpostazioniDocumento("siglaAltraNumerazioneRegMultiplaUscita"));
				}
				RegistrazioneMultiplaUscitaDetail registrazioneMultiplaUscitaDetail = ProtocollazioneUtil.buildRegistrazioneMultiplaUscitaDetail(null);
				registrazioneMultiplaUscitaDetail.nuovoDettaglio(null, initialValues);
				if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
					ProtocollazioneWindow window = new ProtocollazioneWindow(registrazioneMultiplaUscitaDetail, "menu/protocollazione_uscita.png", nomeEntita, 
							I18NUtil.getMessages().menu_registrazione_multipla_uscita_title());
					window.show();
					return null;
				} else {
					return registrazioneMultiplaUscitaDetail;
				}
			} else {
				String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRegMultiplaUscita");
//				String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRegMultiplaUscita");
				AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "PG", null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
						String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;						
						Map<String, Object> initialValues = new HashMap<String, Object>();
						if (idTipoDoc != null && !"".equals(idTipoDoc)) {
							initialValues.put("tipoDocumento", idTipoDoc);
							initialValues.put("nomeTipoDocumento", nomeTipoDoc);
							initialValues.put("codCategoriaAltraNumerazione", lRecordTipoDoc.getAttribute("codCategoriaAltraNumerazione"));
							initialValues.put("siglaAltraNumerazione", lRecordTipoDoc.getAttribute("siglaAltraNumerazione"));
						}
						RegistrazioneMultiplaUscitaDetail registrazioneMultiplaUscitaDetail = ProtocollazioneUtil.buildRegistrazioneMultiplaUscitaDetail(null);
						registrazioneMultiplaUscitaDetail.nuovoDettaglio(null, initialValues);
						if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
							ProtocollazioneWindow window = new ProtocollazioneWindow(registrazioneMultiplaUscitaDetail, "menu/protocollazione_uscita.png", nomeEntita, 
									I18NUtil.getMessages().menu_registrazione_multipla_uscita_title());
							window.show();
						} else {
							Layout.addPortlet(nomeEntita, registrazioneMultiplaUscitaDetail);
						}
					}
				});
				return null;
			}										
		} else if ("carica_protocollo_pregresso".equals(nomeEntita)) {
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaProtPregresso")) {
				String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtPregresso");
				String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtPregresso");
				String codCategoriaAltraNumerazione = AurigaLayout.getImpostazioniDocumento("codCategoriaAltraNumerazionePregresso");
				String siglaAltraNumerazione = AurigaLayout.getImpostazioniDocumento("siglaAltraNumerazionePregresso");
				final Boolean flgTipoDocConVie = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgTipoDocConVieProtPregresso");
				final Boolean flgOggettoNonObblig = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgOggettoNonObbligProtPregresso");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
					initialValues.put("codCategoriaAltraNumerazione", codCategoriaAltraNumerazione);
					initialValues.put("siglaAltraNumerazione", siglaAltraNumerazione);
					// initialValues.put("flgTipoDocConVie", flgTipoDocConVie);
					// initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
				}
				ProtocolloPregressoDetail protocolloPregressoDetail = new ProtocolloPregressoDetail(
						"carica_protocollo_pregresso") {

					public Boolean getFlgTipoDocConVie() {
						return flgTipoDocConVie;
					}

					public Boolean getFlgOggettoNonObblig() {
						return flgOggettoNonObblig;
					}
				};
				protocolloPregressoDetail.editNewRecord(initialValues);
				protocolloPregressoDetail.setInitialValues();
				return protocolloPregressoDetail;
			} else {
				String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoProtPregresso");
				// String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoProtPregresso");
				AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "PG", null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoDoc) {

						String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
						String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
						String codCategoriaAltraNumerazione = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("codCategoriaAltraNumerazione") : null;
						String siglaAltraNumerazione = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("siglaAltraNumerazione") : null;
						final Boolean flgTipoDocConVie = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgTipoDocConVie") : null;
						final Boolean flgOggettoNonObblig = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgOggettoNonObblig") : null;
						Map<String, Object> initialValues = new HashMap<String, Object>();
						if (idTipoDoc != null && !"".equals(idTipoDoc)) {
							initialValues.put("tipoDocumento", idTipoDoc);
							initialValues.put("nomeTipoDocumento", nomeTipoDoc);
							initialValues.put("codCategoriaAltraNumerazione", codCategoriaAltraNumerazione);
							initialValues.put("siglaAltraNumerazione", siglaAltraNumerazione);
							// initialValues.put("flgTipoDocConVie", flgTipoDocConVie);
							// initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
						}
						ProtocolloPregressoDetail protocolloPregressoDetail = new ProtocolloPregressoDetail(
								"carica_protocollo_pregresso") {

							public Boolean getFlgTipoDocConVie() {
								return flgTipoDocConVie;
							}

							public Boolean getFlgOggettoNonObblig() {
								return flgOggettoNonObblig;
							}
						};
						protocolloPregressoDetail.editNewRecord(initialValues);
						protocolloPregressoDetail.setInitialValues();
						Layout.addPortlet(nomeEntita, protocolloPregressoDetail);
					}
				});
				return null;
			}
		} else if ("carica_pratica_pregressa".equals(nomeEntita)) {
			if (AurigaLayout.getImpostazioniFascicoloAsBoolean("skipSceltaTipologiaPregresso")) {
				String idFolderType = AurigaLayout.getImpostazioniFascicolo("idFolderTypePregresso");
				String nomeFolderType = AurigaLayout.getImpostazioniFascicolo("descFolderTypePregresso");
				String templateNomeFolder = AurigaLayout.getImpostazioniFascicolo("templateNomeFolderPregresso");
				Boolean flgTipoFolderConVie = AurigaLayout
						.getImpostazioniFascicoloAsBoolean("flgTipoFolderConViePregresso");
				String dictionaryEntrySezione = AurigaLayout
						.getImpostazioniFascicolo("dictionaryEntrySezionePregresso");
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idFolderType != null && !"".equals(idFolderType)) {
					initialValues.put("idFolderType", idFolderType);
					initialValues.put("nomeFolderType", nomeFolderType);
					initialValues.put("templateNomeFolder", templateNomeFolder);
					initialValues.put("flgTipoFolderConVie", flgTipoFolderConVie);
					initialValues.put("dictionaryEntrySezione", dictionaryEntrySezione);
					initialValues.put("flgUdFolder", "F");
					initialValues.put("flgFascTitolario", false);
				}
				PraticaPregressaDetail praticaPGWebDetail = new PraticaPregressaDetail("carica_pratica_pregressa");
				praticaPGWebDetail.editNewRecord(initialValues);
				praticaPGWebDetail.newMode();
				Layout.addPortlet(nomeEntita, praticaPGWebDetail);
			} else {
				String idFolderTypeDefault = AurigaLayout.getImpostazioniFascicolo("idFolderTypePregresso");
				Record lRecord = new Record();
				lRecord.setAttribute("flgUdFolder", "F");
				lRecord.setAttribute("flgFascTitolario", false);
				AurigaLayout.apriSceltaTipoFolderPopup(false, idFolderTypeDefault, lRecord,
					new ServiceCallback<Record>() {

						@Override
						public void execute(Record lRecordTipoFolder) {
							String idFolderType = lRecordTipoFolder != null
									? lRecordTipoFolder.getAttribute("idFolderType")
									: null;
							String descFolderType = lRecordTipoFolder != null
									? lRecordTipoFolder.getAttribute("descFolderType")
									: null;
							String templateNomeFolder = lRecordTipoFolder != null
									? lRecordTipoFolder.getAttribute("templateNomeFolder")
									: null;
							Boolean flgTipoFolderConVie = lRecordTipoFolder != null
									? lRecordTipoFolder.getAttributeAsBoolean("flgTipoFolderConVie")
									: null;
							String dictionaryEntrySezione = lRecordTipoFolder != null
									? lRecordTipoFolder.getAttribute("dictionaryEntrySezione")
									: null;
							Map<String, Object> initialValues = new HashMap<String, Object>();
							if (idFolderType != null && !"".equals(idFolderType)) {
								initialValues.put("idFolderType", idFolderType);
								initialValues.put("nomeFolderType", descFolderType);
								initialValues.put("templateNomeFolder", templateNomeFolder);
								initialValues.put("flgTipoFolderConVie", flgTipoFolderConVie);
								initialValues.put("dictionaryEntrySezione", dictionaryEntrySezione);
								initialValues.put("flgUdFolder", "F");
								initialValues.put("flgFascTitolario", false);
							}
							PraticaPregressaDetail praticaPGWebDetail = new PraticaPregressaDetail(
									"carica_pratica_pregressa");
							praticaPGWebDetail.editNewRecord(initialValues);
							praticaPGWebDetail.newMode();
							Layout.addPortlet(nomeEntita, praticaPGWebDetail);
						}
					});
			}
			return null;
		} else if ("contratti".equals(nomeEntita)) {
			return new ContrattiLayout(nomeEntita);
		} else if ("registro_documenti".equals(nomeEntita)) {
			return new RegistroDocumentiLayout(nomeEntita);
		} else if ("deleghe_attivadisattiva".equals(nomeEntita)) {
			AttivaDisattivaSessioneInDelegaWindow lAttivaDisattivaSessioneInDelegaWindow = new AttivaDisattivaSessioneInDelegaWindow();
			lAttivaDisattivaSessioneInDelegaWindow.show();
			return null;
		} else if ("deleghe_definizione".equals(nomeEntita)) {
			DefinizioneDelegheWindow lDefinizioneDelegheWindow = new DefinizioneDelegheWindow();
			lDefinizioneDelegheWindow.show();
			return null;
		} else if ("cambiopwd".equals(nomeEntita)) {
			CambioPasswordWindow lCambioPasswordWindow = new CambioPasswordWindow(null, "menu");
			lCambioPasswordWindow.show();
			return null;
		} else if ("posta_elettronica".equals(nomeEntita)) {
			if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
				GenericWindow window = new GenericWindow(new PostaElettronicaLayout(), nomeEntita, I18NUtil.getMessages().menu_posta_elettronica_title(),
						"menu/postainarrivo.png");
				window.show();
				return null;
			} else {
				return new PostaElettronicaLayout();
			}
		} else if ("caselle_email".equals(nomeEntita)) {
			return new CaselleEmailLayout();
		} else if ("caricamento_rubrica".equals(nomeEntita)) {
			return new CaricamentoRubricaClientiWindow();
		} else if ("firma_email".equals(nomeEntita)) {
			final GWTRestDataSource firmaEmailHtmlDS = UserInterfaceFactory.getPreferenceDataSource();
			firmaEmailHtmlDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
			AdvancedCriteria firmaEmailHtmlCriteria = new AdvancedCriteria();
			firmaEmailHtmlCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
			firmaEmailHtmlDS.fetchData(firmaEmailHtmlCriteria, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					final Record recordPref = (response.getStatus() == DSResponse.STATUS_SUCCESS
							&& response.getData().length != 0) ? response.getData()[0] : null;
					FirmaEmailHtmlPopup firmaEmailHtmlPopup = new FirmaEmailHtmlPopup(
							recordPref != null ? recordPref.getAttributeAsString("value") : null,
							new SavePreferenceAction() {

								@Override
								public void execute(final String valueToSave) {
									if (recordPref != null) {
										recordPref.setAttribute("value", valueToSave);
										firmaEmailHtmlDS.updateData(recordPref);
									} else {
										Record record = new Record();
										record.setAttribute("prefName", "DEFAULT");
										record.setAttribute("value", valueToSave);
										firmaEmailHtmlDS.addData(record);
									}
									AurigaLayout.addMessage(new MessageBean( "Firma automatica in calce alle e-mail salvata con successo", "",
											MessageType.INFO));
								}
							});
					firmaEmailHtmlPopup.show();
				}
			});
		} else if ("attributi_dinamici".equals(nomeEntita)) {
			final VLayout layout = new VLayout();
			layout.setHeight100();
			layout.setWidth100();
			/*
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			Record record = new Record();
			record.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			record.setAttribute("tipoEntita", "1");
			 */
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TestAttributiDinamiciDatasource");
			Record record = new Record();
			lGwtRestService.call(record, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					AttributiDinamiciDetail attributiDinamiciLayout = new AttributiDinamiciDetail("attributiDinamici", object
							.getAttributeAsRecordList("attributiAdd"), object.getAttributeAsMap("mappaDettAttrLista"), object
							.getAttributeAsMap("mappaValoriAttrLista"), object.getAttributeAsMap("mappaVariazioniAttrLista"), object
							.getAttributeAsMap("mappaDocumenti"), null, null, null);
					attributiDinamiciLayout.setCanEdit(true);
					layout.addMember(attributiDinamiciLayout);
				}
			});
			return layout;
		} else if ("elenchi_albi".equals(nomeEntita)) {
			SelezionaTipoElencoAlboWindow lSelezionaTipoElencoWindow = new SelezionaTipoElencoAlboWindow();
			lSelezionaTipoElencoWindow.show();
			return null;
		} else if ("modellatore_processi".equals(nomeEntita)) {
			String url = AurigaLayout.getParametroDB("URL_MODELLATORE_PROCESSI");
			String username = AurigaLayout.getParametroDB("USERID_ACTIVITI_FLOW") != null ? AurigaLayout.getParametroDB("USERID_ACTIVITI_FLOW") : "mattia";
			String password = AurigaLayout.getParametroDB("PWD_ACTIVITI_FLOW") != null ? AurigaLayout.getParametroDB("PWD_ACTIVITI_FLOW") : "zanin";
			if (url != null && !"".equals(url) && username != null && !"".equals(username) && password != null && !"".equals(password)) {
				url += "?usernameExt=" + username + "&passwordExt=" + password;
				com.google.gwt.user.client.Window.open(url, "_newtab", "");
			} else {
				Layout.addMessage(new MessageBean("Parametro in DB non configurato", "", MessageType.ERROR));
			}
			return null;
		} else if ("statisticheProtocolli".equals(nomeEntita)) {
			StatisticheProtocolloWindow lStatisticheProtocolloWindow = new StatisticheProtocolloWindow();
			lStatisticheProtocolloWindow.show();
			return null;
		} else if ("statisticheDocumenti".equals(nomeEntita)) {
			StatisticheDocumentiWindow lStatisticheDocumentiWindow = new StatisticheDocumentiWindow();
			lStatisticheDocumentiWindow.show();
			return null;
		} else if ("statisticheCogito".equals(nomeEntita)) {
			StatisticheCogitoWindow lStatisticheCogitoWindow = new StatisticheCogitoWindow();
			lStatisticheCogitoWindow.show();
			return null;
		} else if ("statisticheMailStoricizzate".equals(nomeEntita)) {
			StatisticheMailStoricizzateWindow lStatisticheMailStoricizzateWindow = new StatisticheMailStoricizzateWindow();
			lStatisticheMailStoricizzateWindow.show();
			return null;
		} else if ("statisticheMail".equals(nomeEntita)) {
			StatisticheMailWindow lStatisticheMailWindow = new StatisticheMailWindow();
			lStatisticheMailWindow.show();
			return null;
		} else if ("statisticheTrasparenzaAmministrativa".equals(nomeEntita)) {
			StatisticheTrasparenzaAmministrativaWindow lStatisticheTrasparenzaAmministrativaWindow = new StatisticheTrasparenzaAmministrativaWindow();
			lStatisticheTrasparenzaAmministrativaWindow.show();
			return null;
		} else if ("regoleCampionamentoAtti".equals(nomeEntita)) {
			return new RegoleCampionamentoLayout();
		} else if ("richiesteCampioneAtti".equals(nomeEntita)) {
			RichiestaCampionePopup lRichiestaCampionePopup = new RichiestaCampionePopup(null, null);
			lRichiestaCampionePopup.show();
			return null;
		} else if ("verificaRaffinamentoCampioni".equals(nomeEntita)) {
			return new VerificaRaffinamentoCampioniLayout();		
		} else if ("listaAccompagnatoriaProtocollazioni".equals(nomeEntita)) {
			ListaAccompagnatoriaProtocollazioniWindow lListaAccompagnatoriaProtocollazioniWindow
				= new ListaAccompagnatoriaProtocollazioniWindow();
			lListaAccompagnatoriaProtocollazioniWindow.show();
			return null;
		} else if ("visure_in_iter".equals(nomeEntita)) {
			return new VisureInIterLayout(nomeEntita);
		} else if ("visure_personali".equals(nomeEntita)) {
			return new VisurePersonaliLayout(nomeEntita);
		} else if ("avvio_procedimento".equals(nomeEntita)) {
			AvvioProcedimentoWindow lAvvioProcedimentoWindow = new AvvioProcedimentoWindow();
			lAvvioProcedimentoWindow.show();
			return null;
		} else if ("procedimenti_personali".equals(nomeEntita)) {
			return new ProcedimentiPersonaliLayout("procedimenti_personali");
		} else if ("procedimenti_in_iter".equals(nomeEntita)) {
			return new ProcedimentiInIterLayout("procedimenti_in_iter");
		} else if ("richiesteAutotuteleCedPersonali".equals(nomeEntita)) {
			return new RichiesteAutotuteleCedPersonaliLayout("richiesteAutotuteleCedPersonali");
		} else if ("richiesteAutotuteleCedInIter".equals(nomeEntita)) {
			return new RichiesteAutotuteleCedInIterLayout("richiesteAutotuteleCedInIter");
		} else if ("tso_in_iter".equals(nomeEntita)) {
			return new TSOInIterLayout(nomeEntita);
		} else if ("tso_personali".equals(nomeEntita)) {
			return new TSOPersonaliLayout(nomeEntita);
		} else if ("avvio_proposta_organigramma".equals(nomeEntita)) {
			AvvioPropostaOrganigrammaWindow lAvvioPropostaOrganigrammaWindow = new AvvioPropostaOrganigrammaWindow();
			lAvvioPropostaOrganigrammaWindow.show();
			return null;
		} else if ("proposte_organigramma_personali".equals(nomeEntita)) {
			return new ProposteOrganigrammaPersonaliLayout("proposte_organigramma_personali");
		} else if ("proposte_organigramma_in_iter".equals(nomeEntita)) {
			return new ProposteOrganigrammaInIterLayout("proposte_organigramma_in_iter");
		} else if ("configurazione_flussi_modellati".equals(nomeEntita)) {
			return new ConfigurazioneFlussiLayout("configurazione_flussi_modellati");
		} else if ("avvio_iter_atti".equals(nomeEntita)) {
			GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("LoadComboAttoConFlussoWFDataSource");
			lGWTRestDataSource.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					Record[] data = response.getData();
					if (data.length > 0) {
						SelezionaTipoAttoWindow lSelezionaTipoAttoWindow = new SelezionaTipoAttoWindow();
						lSelezionaTipoAttoWindow.show();
					} /*else if (data.length == 1) {						
						final Record lFormRecord = data[0];
						AurigaLayout.avviaPratica(lFormRecord);
					} */else {
						Layout.addMessage(new MessageBean("Nessun tipo atto disponibile", "", MessageType.ERROR));
					}
				}
			});
			return null;	
			
		} else if ("atti_in_lavorazione".equals(nomeEntita)) {		
			return new AttiInLavorazioneLayout("atti_in_lavorazione");		
		} else if ("atti_personali".equals(nomeEntita)) {		
			return new AttiPersonaliLayout("atti_personali");
		} else if ("atti_completi_in_lavorazione".equals(nomeEntita)) {			
			return new AttiCompletiInLavorazioneLayout("atti_completi_in_lavorazione");
		} else if ("atti_completi_personali".equals(nomeEntita)) {
			return new AttiCompletiPersonaliLayout("atti_completi_personali");
		} else if ("libro_firma".equals(nomeEntita)) {
			LibroFirmaMassivaWindow lLibroFirmaMassivaWindow = new LibroFirmaMassivaWindow();
			lLibroFirmaMassivaWindow.show();
			return null;
		} else if ("documenti_da_rifirmare".equals(nomeEntita)) {
			DocumentiDaRifirmareWindow lDocumentiDaRifirmareWindow = new DocumentiDaRifirmareWindow();
			lDocumentiDaRifirmareWindow.show();
			return null;
		} else if ("test_firma".equals(nomeEntita)) {
			TestFirmaMultiplaHashLayout lTestFirmaMultiplaHashLayout = new TestFirmaMultiplaHashLayout();
			return lTestFirmaMultiplaHashLayout;
		} else if ("richiesteAutotutelaCed".equals(nomeEntita)) {
			/*
			ListGrid list = new ListGrid();
			list.setWidth100();
			list.setHeight100();
			list.setAutoFetchData(true);
			list.setDataSource(new TestRichiesteAutotutelaCedDataSource());
			return list;			 
			 */
			return new RichiesteAutotutelaCedLayout(nomeEntita);
		} else if ("diagrammi".equals(nomeEntita)) {
			Layout.addMaximizedModalWindow(nomeEntita, "Diagrammi di flusso", "menu/diagrammi.png", new DiagrammiLayout());
			return null;
			// return new DiagrammiLayout();
		} else if ("editor".equals(nomeEntita)) {
			CKEditorWindow lCKEditorWindow = new CKEditorWindow("Editor", "blank.png");
			lCKEditorWindow.show();
			return null;
		} else if ("editorOrganigramma".equals(nomeEntita)) {
			HtmlEditorOrganigrammaFlowWindow htmlFlowWindow = new HtmlEditorOrganigrammaFlowWindow("editorOrganigramma", "Editor Organigramma", "menu/organigramma.png", GWT.getHostPageBaseURL() + "editorOrganigramma.html");
			htmlFlowWindow.setRedrawOnResize(false);
			htmlFlowWindow.show();
		} else if ("info".equals(nomeEntita)) {
			InformazioniWindow informazioniWindow = new InformazioniWindow(nomeEntita);
			informazioniWindow.show();
			return null;
		} else if ("istanze_portale_riscossione_da_istruire".equals(nomeEntita)) {
			return new IstanzePortaleRiscossioneDaIstruireLayout(nomeEntita, null, null);
		} else if ("richiesta_accesso_atti".equals(nomeEntita)) {
			AurigaLayout.apriSceltaTipoRichiestaAccessoAttiPopup(null, new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordTipoDoc) {

					String idTipoDocumento = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
					String descTipoDocumento = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
					Boolean flgTipoDocConVie = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgTipoDocConVie") : null;
					String siglaPraticaSuSistUfficioRichiedente = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("siglaPraticaSuSistUfficioRichiedente") : null;
					String idNodoDefaultRicercaAtti = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idNodoDefaultRicercaAtti") : null;
					Map<String, Object> initialValues = new HashMap<String, Object>();
					if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
						initialValues.put("tipoDocumento", idTipoDocumento);
						initialValues.put("nomeTipoDocumento", descTipoDocumento);
						initialValues.put("flgTipoDocConVie", flgTipoDocConVie);
						initialValues.put("siglaPraticaSuSistUfficioRichiedente", siglaPraticaSuSistUfficioRichiedente);
						initialValues.put("idNodoDefaultRicercaAtti", idNodoDefaultRicercaAtti);
					}
					RichiestaAccessoAttiDetail lRichiestaAccessoAttiDetail = new RichiestaAccessoAttiDetail(nomeEntita);
					lRichiestaAccessoAttiDetail.editNewRecord(initialValues);
					lRichiestaAccessoAttiDetail.newMode();
					Layout.addPortlet(nomeEntita, lRichiestaAccessoAttiDetail);
				}
			});
			return null;
		} else if ("monitoraggioPdV".equals(nomeEntita)) {
			return new MonitoraggioPdVLayout();
		} else if ("monitoraggio_operazioni_batch".equals(nomeEntita)) {
			return new MonitoraggioOperazioniBatchLayout();
		} else if ("applicazioni_esterne".equals(nomeEntita)) {
			return new ApplicazioniEsterneLayout();
		} else if ("registri_numerazione".equals(nomeEntita)) {
			return new RegistriNumerazioneLayout();
		} else if ("log_operazioni".equals(nomeEntita)) {
			return new LogOperazioniLayout();
		} 
		// GIUNTA
		else if("gestione_delibere_giunta_convocazioneseduta".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("giunta","CONVOCAZIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					Record getRecord = new Record();
					getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
					getRecord.setAttribute("organoCollegiale", "giunta");
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
					lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							
							ConvocazioneSedutaDetail lConvocazioneSedutaDetail = new ConvocazioneSedutaDetail(nomeEntita, object, "GIUNTA");
							lConvocazioneSedutaDetail.caricaDettaglio(object);
							MenuBean menu = Layout.getMenu(nomeEntita);
							String title = menu != null ? menu.getTitle() : "Convocazione seduta Giunta"; 
							String icon = menu != null ? menu.getIcon() : "menu/convocazioneseduta.png";
							Layout.addPortlet(nomeEntita, title, icon, lConvocazioneSedutaDetail);
						}
					});
				}
			});
			return null;
			
		} else if("gestione_delibere_giunta_discussione".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("giunta","DISCUSSIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttribute("idSeduta") == null) {
						Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
					} else {
						Record getRecord = new Record();
						getRecord.setAttribute("organoCollegiale", "giunta");
						getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
						GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
						lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								DiscussioneSedutaDetail lDiscussioneSedutaDetail = new DiscussioneSedutaDetail(nomeEntita, object, "GIUNTA");
								lDiscussioneSedutaDetail.caricaDettaglio(object);
								MenuBean menu = Layout.getMenu(nomeEntita);
								String title = menu != null ? menu.getTitle() : "Discussione Giunta"; 
								String icon = menu != null ? menu.getIcon() : "menu/discussione.png";
								Layout.addPortlet(nomeEntita, title, icon, lDiscussioneSedutaDetail);
							}
						});
					}
				}
			});
			return null;
			
		} else if("gestione_delibere_giunta_storicosedute".equals(nomeEntita)) {
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("giunta","CONSULTAZIONE_STORICO", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {

					if(lRecord.getAttribute("storico") != null && "discussione".equalsIgnoreCase(lRecord.getAttributeAsString("storico"))) {							
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {					
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("organoCollegiale", "giunta");
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoDiscussioneSedutaDetail lStoricoDiscussioneSedutaDetail = new StoricoDiscussioneSedutaDetail(nomeEntita, object, "GIUNTA");
									lStoricoDiscussioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico sedute Giunta"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoDiscussioneSedutaDetail);
								}
							});	
						}
					} else {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							recordToLoad.setAttribute("organoCollegiale", "giunta");
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoConvocazioneSedutaDetail lStoricoConvocazioneSedutaDetail = new StoricoConvocazioneSedutaDetail(nomeEntita, object, "GIUNTA");
									lStoricoConvocazioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico sedute Giunta"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoConvocazioneSedutaDetail);
								}
							});
						}
					}
				}
			});
			return null;
		} 
		//CONSIGLIO
		else if("gestione_delibere_consiglio_convocazioneseduta".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("consiglio","CONVOCAZIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					Record getRecord = new Record();
					getRecord.setAttribute("organoCollegiale", "consiglio");
					getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
					lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							
							ConvocazioneSedutaDetail lConvocazioneSedutaDetail = new ConvocazioneSedutaDetail(nomeEntita, object, "CONSIGLIO");
							lConvocazioneSedutaDetail.caricaDettaglio(object);
							MenuBean menu = Layout.getMenu(nomeEntita);
							String title = menu != null ? menu.getTitle() : "Convocazione seduta Consiglio"; 
							String icon = menu != null ? menu.getIcon() : "menu/convocazioneseduta.png";
							Layout.addPortlet(nomeEntita, title, icon, lConvocazioneSedutaDetail);
						}
					});
				}
			});
			return null;
		} else if("gestione_delibere_consiglio_discussione".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("consiglio","DISCUSSIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttribute("idSeduta") == null) {
						Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
					} else {
						Record getRecord = new Record();
						getRecord.setAttribute("organoCollegiale", "consiglio");
						getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
						GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
						lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								DiscussioneSedutaDetail lDiscussioneSedutaDetail = new DiscussioneSedutaDetail(nomeEntita, object, "CONSIGLIO");
								lDiscussioneSedutaDetail.caricaDettaglio(object);
								MenuBean menu = Layout.getMenu(nomeEntita);
								String title = menu != null ? menu.getTitle() : "Discussione Consiglio"; 
								String icon = menu != null ? menu.getIcon() : "menu/discussione.png";
								Layout.addPortlet(nomeEntita, title, icon, lDiscussioneSedutaDetail);
							}
						});
					}
				}
			});
			return null;
		} else if("gestione_delibere_consiglio_storicosedute".equals(nomeEntita)) {
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("consiglio","CONSULTAZIONE_STORICO", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttributeAsString("storico") != null && "discussione".equalsIgnoreCase(lRecord.getAttributeAsString("storico"))) {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {					
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("organoCollegiale", "consiglio");
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoDiscussioneSedutaDetail lStoricoDiscussioneSedutaDetail = new StoricoDiscussioneSedutaDetail(nomeEntita, object, "CONSIGLIO");
									lStoricoDiscussioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico sedute Consiglio"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoDiscussioneSedutaDetail);
								}
							});	
						}
					} else {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							recordToLoad.setAttribute("organoCollegiale", "consiglio");
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoConvocazioneSedutaDetail lStoricoConvocazioneSedutaDetail = new StoricoConvocazioneSedutaDetail(nomeEntita, object, "CONSIGLIO");
									lStoricoConvocazioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico sedute Consiglio"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoConvocazioneSedutaDetail);
								}
							});
						}
					}
				}
			});
			return null;
		}
		//COMMISSIONE
		else if("gestione_delibere_commissioni_convocazioneseduta".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("commissione","CONVOCAZIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttribute("idSeduta") != null && "#NULL".equals(lRecord.getAttribute("idSeduta"))) {
						
						AurigaLayout.apriSceltaCommissione(new ServiceCallback<Record>() {

							@Override
							public void execute(Record lRecord) {
								
								lRecord.setAttribute("organoCollegiale", "commissione");
								GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
								lGWTRestDataSource.call(lRecord, new ServiceCallback<Record>() {
									
									@Override
									public void execute(Record object) {
										
										ConvocazioneSedutaDetail lConvocazioneSedutaDetail = new ConvocazioneSedutaDetail(nomeEntita, object, "COMMISSIONE");
										lConvocazioneSedutaDetail.caricaDettaglio(object);
										MenuBean menu = Layout.getMenu(nomeEntita);
										String title = menu != null ? menu.getTitle() : "Convocazione seduta Commissioni"; 
										String icon = menu != null ? menu.getIcon() : "menu/convocazioneseduta.png";
										Layout.addPortlet(nomeEntita, title, icon, lConvocazioneSedutaDetail);
									}
								});
							}
						});
						
					} else {
						
						Record getRecord = new Record();
						getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
						getRecord.setAttribute("organoCollegiale", "commissione");
						GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
						lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								ConvocazioneSedutaDetail lConvocazioneSedutaDetail = new ConvocazioneSedutaDetail(nomeEntita, object, "COMMISSIONE");
								lConvocazioneSedutaDetail.caricaDettaglio(object);
								MenuBean menu = Layout.getMenu(nomeEntita);
								String title = menu != null ? menu.getTitle() : "Convocazione seduta Commissioni"; 
								String icon = menu != null ? menu.getIcon() : "menu/convocazioneseduta.png";
								Layout.addPortlet(nomeEntita, title, icon, lConvocazioneSedutaDetail);
							}
						});
						
					}

				}
			});
			return null;
		} else if("gestione_delibere_commissioni_discussione".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegialiDiscussione("commissione","DISCUSSIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					if(lRecord.getAttribute("idSeduta") == null) {
						Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
					} else {
						Record getRecord = new Record();
						getRecord.setAttribute("organoCollegiale", "commissione");
						getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
						GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
						lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								DiscussioneSedutaDetail lDiscussioneSedutaDetail = new DiscussioneSedutaDetail(nomeEntita, object, "COMMISSIONE");
								lDiscussioneSedutaDetail.caricaDettaglio(object);	
								MenuBean menu = Layout.getMenu(nomeEntita);
								String title = menu != null ? menu.getTitle() : "Discussione Commissioni"; 
								String icon = menu != null ? menu.getIcon() : "menu/discussione.png";
								Layout.addPortlet(nomeEntita, title, icon, lDiscussioneSedutaDetail);
							}
						});
					}
				}
			});
			return null;
		} else if("gestione_delibere_commissioni_storicosedute".equals(nomeEntita)) {
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("commissione","CONSULTAZIONE_STORICO", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttributeAsString("storico") != null && "discussione".equalsIgnoreCase(lRecord.getAttributeAsString("storico"))) {						
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("organoCollegiale", "commissione");
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoDiscussioneSedutaDetail lStoricoDiscussioneSedutaDetail = new StoricoDiscussioneSedutaDetail(nomeEntita, object, "COMMISSIONE");
									lStoricoDiscussioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico sedute Commissione"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoDiscussioneSedutaDetail);
								}
							});	
						}
					} else {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
							recordToLoad.setAttribute("organoCollegiale", "commissione");
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoConvocazioneSedutaDetail lStoricoConvocazioneSedutaDetail = new StoricoConvocazioneSedutaDetail(nomeEntita, object, "COMMISSIONE");
									lStoricoConvocazioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico sedute Commissione"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoConvocazioneSedutaDetail);
								}
							});
						}
					}
				}
			});
			return null;
			
		} 
		//COMITATO DI GESTIONE
		else if("gestione_delibere_comitatogestione_convocazioneseduta".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("comitato_gestione","CONVOCAZIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					Record getRecord = new Record();
					getRecord.setAttribute("organoCollegiale", "comitato_gestione");
					getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
					lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							
							ConvocazioneSedutaDetail lConvocazioneSedutaDetail = new ConvocazioneSedutaDetail(nomeEntita, object, "COMITATO_GESTIONE");
							lConvocazioneSedutaDetail.caricaDettaglio(object);
							MenuBean menu = Layout.getMenu(nomeEntita);
							String title = menu != null ? menu.getTitle() : "Convocazione seduta Comitato di Gestione"; 
							String icon = menu != null ? menu.getIcon() : "menu/convocazioneseduta.png";
							Layout.addPortlet(nomeEntita, title, icon, lConvocazioneSedutaDetail);
						}
					});
				}
			});
			return null;
		} else if("gestione_delibere_comitatogestione_discussione".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("comitato_gestione","DISCUSSIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttribute("idSeduta") == null) {
						Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
					} else {
						Record getRecord = new Record();
						getRecord.setAttribute("organoCollegiale", "comitato_gestione");
						getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
						GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
						lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								DiscussioneSedutaDetail lDiscussioneSedutaDetail = new DiscussioneSedutaDetail(nomeEntita, object, "COMITATO_GESTIONE");
								lDiscussioneSedutaDetail.caricaDettaglio(object);
								MenuBean menu = Layout.getMenu(nomeEntita);
								String title = menu != null ? menu.getTitle() : "Discussione Comitato di Gestione"; 
								String icon = menu != null ? menu.getIcon() : "menu/discussione.png";
								Layout.addPortlet(nomeEntita, title, icon, lDiscussioneSedutaDetail);
							}
						});
					}
				}
			});
			return null;
		} else if("gestione_delibere_comitatogestione_storicosedute".equals(nomeEntita)) {
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("comitato_gestione","CONSULTAZIONE_STORICO", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttributeAsString("storico") != null && "discussione".equalsIgnoreCase(lRecord.getAttributeAsString("storico"))) {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {					
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("organoCollegiale", "comitato_gestione");
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoDiscussioneSedutaDetail lStoricoDiscussioneSedutaDetail = new StoricoDiscussioneSedutaDetail(nomeEntita, object, "COMITATO_GESTIONE");
									lStoricoDiscussioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico sedute Comitato di Gestione"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoDiscussioneSedutaDetail);
								}
							});	
						}
					} else {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							recordToLoad.setAttribute("organoCollegiale", "comitato_gestione");
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoConvocazioneSedutaDetail lStoricoConvocazioneSedutaDetail = new StoricoConvocazioneSedutaDetail(nomeEntita, object, "COMITATO_GESTIONE");
									lStoricoConvocazioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico Comitato di Gestione"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoConvocazioneSedutaDetail);
								}
							});
						}
					}
				}
			});
			return null;
		}
		//ORGANISMO_PATERNARIATO
		else if("gestione_delibere_organismopaternariato_convocazioneseduta".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("organismo_paternariato","CONVOCAZIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					Record getRecord = new Record();
					getRecord.setAttribute("organoCollegiale", "organismo_paternariato");
					getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
					lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							
							ConvocazioneSedutaDetail lConvocazioneSedutaDetail = new ConvocazioneSedutaDetail(nomeEntita, object, "ORGANISMO_PATERNARIATO");
							lConvocazioneSedutaDetail.caricaDettaglio(object);
							MenuBean menu = Layout.getMenu(nomeEntita);
							String title = menu != null ? menu.getTitle() : "Convocazione seduta Organismo di Paternariato della Risorsa Mare"; 
							String icon = menu != null ? menu.getIcon() : "menu/convocazioneseduta.png";
							Layout.addPortlet(nomeEntita, title, icon, lConvocazioneSedutaDetail);
						}
					});
				}
			});
			return null;
		} else if("gestione_delibere_organismopaternariato_discussione".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("organismo_paternariato","DISCUSSIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttribute("idSeduta") == null) {
						Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
					} else {
						Record getRecord = new Record();
						getRecord.setAttribute("organoCollegiale", "organismo_paternariato");
						getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
						GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
						lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								DiscussioneSedutaDetail lDiscussioneSedutaDetail = new DiscussioneSedutaDetail(nomeEntita, object, "ORGANISMO_PATERNARIATO");
								lDiscussioneSedutaDetail.caricaDettaglio(object);
								MenuBean menu = Layout.getMenu(nomeEntita);
								String title = menu != null ? menu.getTitle() : "Discussione Organismo di Paternariato della Risorsa Mare"; 
								String icon = menu != null ? menu.getIcon() : "menu/discussione.png";
								Layout.addPortlet(nomeEntita, title, icon, lDiscussioneSedutaDetail);
							}
						});
					}
				}
			});
			return null;
		} else if("gestione_delibere_organismopaternariato_storicosedute".equals(nomeEntita)) {
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("organismo_paternariato","CONSULTAZIONE_STORICO", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttributeAsString("storico") != null && "discussione".equalsIgnoreCase(lRecord.getAttributeAsString("storico"))) {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {					
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("organoCollegiale", "organismo_paternariato");
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoDiscussioneSedutaDetail lStoricoDiscussioneSedutaDetail = new StoricoDiscussioneSedutaDetail(nomeEntita, object, "ORGANISMO_PATERNARIATO");
									lStoricoDiscussioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico Organismo di Paternariato della Risorsa Mare"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoDiscussioneSedutaDetail);
								}
							});	
						}
					} else {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							recordToLoad.setAttribute("organoCollegiale", "organismo_paternariato");
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoConvocazioneSedutaDetail lStoricoConvocazioneSedutaDetail = new StoricoConvocazioneSedutaDetail(nomeEntita, object, "ORGANISMO_PATERNARIATO");
									lStoricoConvocazioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico Organismo di Paternariato della Risorsa Mare"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoConvocazioneSedutaDetail);
								}
							});
						}
					}
				}
			});
			return null;
		}
		//CONFERENZA
		else if("gestione_delibere_conferenza_convocazioneseduta".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("conferenza","CONVOCAZIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					Record getRecord = new Record();
					getRecord.setAttribute("organoCollegiale", "conferenza");
					getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
					lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							
							ConvocazioneSedutaDetail lConvocazioneSedutaDetail = new ConvocazioneSedutaDetail(nomeEntita, object, "CONFERENZA");
							lConvocazioneSedutaDetail.caricaDettaglio(object);
							MenuBean menu = Layout.getMenu(nomeEntita);
							String title = menu != null ? menu.getTitle() : "Convocazione seduta Conferenza"; 
							String icon = menu != null ? menu.getIcon() : "menu/convocazione.png";
							Layout.addPortlet(nomeEntita, title, icon, lConvocazioneSedutaDetail);
						}
					});
				}
			});
			return null;
		} else if("gestione_delibere_conferenza_discussione".equals(nomeEntita)) {
			
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("conferenza","DISCUSSIONE", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttribute("idSeduta") == null) {
						Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
					} else {
						Record getRecord = new Record();
						getRecord.setAttribute("organoCollegiale", "conferenza");
						getRecord.setAttribute("idSeduta", lRecord.getAttribute("idSeduta"));
						GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
						lGWTRestDataSource.call(getRecord, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								
								DiscussioneSedutaDetail lDiscussioneSedutaDetail = new DiscussioneSedutaDetail(nomeEntita, object, "CONFERENZA");
								lDiscussioneSedutaDetail.caricaDettaglio(object);
								MenuBean menu = Layout.getMenu(nomeEntita);
								String title = menu != null ? menu.getTitle() : "Discussione Conferenza"; 
								String icon = menu != null ? menu.getIcon() : "menu/discussione.png";
								Layout.addPortlet(nomeEntita, title, icon, lDiscussioneSedutaDetail);
							}
						});
					}
				}
			});
			return null;
		} else if("gestione_delibere_conferenza_storicosedute".equals(nomeEntita)) {
			AurigaLayout.apriSceltaSedutaOrganiCollegiali("conferenza","CONSULTAZIONE_STORICO", new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecord) {
					
					if(lRecord.getAttributeAsString("storico") != null && "discussione".equalsIgnoreCase(lRecord.getAttributeAsString("storico"))) {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {					
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("organoCollegiale", "conferenza");
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoDiscussioneSedutaDetail lStoricoDiscussioneSedutaDetail = new StoricoDiscussioneSedutaDetail(nomeEntita, object, "CONFERENZA");
									lStoricoDiscussioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico Conferenza"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoDiscussioneSedutaDetail);
								}
							});	
						}
					} else {
						if(lRecord.getAttribute("idSeduta") == null) {
							Layout.addMessage(new MessageBean("Nessuna seduta presente","",MessageType.WARNING));
						} else {
							Record recordToLoad = new Record();
							recordToLoad.setAttribute("idSeduta", lRecord.getAttribute("idSeduta") != null ? lRecord.getAttribute("idSeduta") : null);
							recordToLoad.setAttribute("organoCollegiale", "conferenza");
							GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
							lGWTRestDataSource.call(recordToLoad, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									
									StoricoConvocazioneSedutaDetail lStoricoConvocazioneSedutaDetail = new StoricoConvocazioneSedutaDetail(nomeEntita, object, "CONFERENZA");
									lStoricoConvocazioneSedutaDetail.caricaDettaglio(object);
									MenuBean menu = Layout.getMenu(nomeEntita);
									String title = menu != null ? menu.getTitle() : "Storico Conferenza"; 
									String icon = menu != null ? menu.getIcon() : "menu/storicosedute.png";
									Layout.addPortlet(nomeEntita, title, icon, lStoricoConvocazioneSedutaDetail);
								}
							});
						}
					}
				}
			});
			return null;
		} else if ("sub_profili".equals(nomeEntita)) {
			return new SubProfiliLayout();
		} 
		else if ("profili".equals(nomeEntita)) {
			return new ProfiliLayout();
		} 
		else if ("richiesta_accesso_documento".equals(nomeEntita)) {
			RichiestaAccessoDocumentoPopUp richiestaAccessoDocumentoPopUp = new RichiestaAccessoDocumentoPopUp();
			richiestaAccessoDocumentoPopUp.show();
			return null;
		} 
		else if ("chiusura_massiva_email".equals(nomeEntita)) {
			ChiusuraMassivaEmailWindow lChiusuraMassivaEmailWindow = new ChiusuraMassivaEmailWindow(nomeEntita);
			lChiusuraMassivaEmailWindow.show();
			return null;
		} 
		else if ("pubblicazione_albo_consultazione_richieste".equals(nomeEntita)) {
			return new PubblicazioneAlboConsultazioneRichiesteLayout();
		} 
		else if ("pubblicazione_albo_nuova_richiesta".equals(nomeEntita)) {
				NuovaRichiestaPubblicazioneWindow lNuovaRichiestaPubblicazioneWindow = new NuovaRichiestaPubblicazioneWindow();
				lNuovaRichiestaPubblicazioneWindow.show();
				return null;
		}
		else if ("pubblicazione_albo_ricerca_pubblicazioni".equals(nomeEntita)) {
			return new PubblicazioneAlboRicercaPubblicazioniLayout();
		}
		else if("invio_raccomandate".equals(nomeEntita)) {
			return new InvioRaccomandateLayout();
		}		
		else if("contenuto_foglio_importato".equals(nomeEntita)) {
			return new ContenutoFoglioImportatoLayout();
		}
		else if("foglio_importato".equals(nomeEntita)) {
			return new FoglioImportatoLayout();
		}
		else if ("anagrafiche_ruoli_amministrativi".equals(nomeEntita)) {
			return new RuoliAmministrativiLayout();
		} 
		
		else if ("contenuti_amministrazione_trasparente".equals(nomeEntita)) {
			return new ContenutiAmmTraspLayout();
		}
		
		else if ("regole_protocollazione_automatica_caselle_pec_peo".equals(nomeEntita)) {
			return new RegoleProtocollazioneAutomaticaCaselleLayout();
		}

		/*
		 * else if ("istanze_ced".equals(nomeEntita)) { Record lRecordToLoad = new
		 * Record(); lRecordToLoad.setAttribute("descTipoDocumento", "CED");
		 * GWTRestDataSource lGwtRestDataSource = new
		 * GWTRestDataSource("ProtocolloDataSource");
		 * lGwtRestDataSource.executecustom("getIdDocType", lRecordToLoad, new
		 * DSCallback() {
		 * 
		 * @Override public void execute(DSResponse response, Object rawData, DSRequest
		 * request) { if (response.getStatus() == DSResponse.STATUS_SUCCESS) { Record
		 * data = response.getData()[0]; String idTipoDocumento =
		 * data.getAttribute("idTipoDocumento"); Map<String, Object> initialValues =
		 * null; if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
		 * initialValues = new HashMap<String, Object>();
		 * initialValues.put("tipoDocumento", idTipoDocumento); } IstanzeDetail
		 * istanzeDetail = new IstanzeDetail(nomeEntita, false);
		 * istanzeDetail.editNewRecord(initialValues); istanzeDetail.setInitialValues();
		 * Layout.addPortlet(nomeEntita, istanzeDetail); } } }); } else if
		 * ("istanze_autotutela".equals(nomeEntita)) { Record lRecordToLoad = new
		 * Record(); lRecordToLoad.setAttribute("descTipoDocumento", "AUTOTUTELA");
		 * GWTRestDataSource lGwtRestDataSource = new
		 * GWTRestDataSource("ProtocolloDataSource");
		 * lGwtRestDataSource.executecustom("getIdDocType", lRecordToLoad, new
		 * DSCallback() {
		 * 
		 * @Override public void execute(DSResponse response, Object rawData, DSRequest
		 * request) { if (response.getStatus() == DSResponse.STATUS_SUCCESS) { Record
		 * data = response.getData()[0]; String idTipoDocumento =
		 * data.getAttribute("idTipoDocumento"); Map<String, Object> initialValues =
		 * null; if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
		 * initialValues = new HashMap<String, Object>();
		 * initialValues.put("tipoDocumento", idTipoDocumento); } IstanzeDetail
		 * istanzeDetail = new IstanzeDetail(nomeEntita, false);
		 * istanzeDetail.editNewRecord(initialValues); istanzeDetail.setInitialValues();
		 * Layout.addPortlet(nomeEntita, istanzeDetail); } } }); }
		 */
		return null;
	}
	
	public void buildConvocazioneSedutaDelibera() {
		
	}

	public void buildRepertorio(final String nomeEntita, final String flgTipoProv) {

		if("E".equalsIgnoreCase(flgTipoProv)) {
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaRepertorioEntrata")) {
				final String repertorioEntrata = AurigaLayout.getImpostazioniDocumento("repertorioEntrata");
				if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioEntrata")) {
					String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
					String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioEntrata");
					apriRepertorioDetail(nomeEntita, repertorioEntrata, idTipoDoc, nomeTipoDoc);
				} else {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioEntrata, new ServiceCallback<Record>() {

						@Override
						public void execute(Record lRecordTipoDoc) {
							String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
							String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
							apriRepertorioDetail(nomeEntita, repertorioEntrata, idTipoDoc, nomeTipoDoc);
						}
					});
				}
			} else {
				final String repertorioEntrataDefault = AurigaLayout.getImpostazioniDocumento("repertorioEntrata");				
				AurigaLayout.apriSceltaRepertorioPopup("E", repertorioEntrataDefault, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordRepertorio) {

						final String repertorioEntrata = lRecordRepertorio != null ? lRecordRepertorio.getAttribute("repertorio") : null;
						if (repertorioEntrata != null && !"".equals(repertorioEntrata)) {
							if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioEntrata")) {
								String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
								String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioEntrata");
								apriRepertorioDetail(nomeEntita, repertorioEntrata, idTipoDoc, nomeTipoDoc);
							} else {
								String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioEntrata");
								AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioEntrata, new ServiceCallback<Record>() {

									@Override
									public void execute(Record lRecordTipoDoc) {
										String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
										String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
										apriRepertorioDetail(nomeEntita, repertorioEntrata, idTipoDoc, nomeTipoDoc);
									}
								});
							}
						}
					}
				});
			}
		} else if("I".equalsIgnoreCase(flgTipoProv)) {
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaRepertorioInterno")) {
				final String repertorioInterno = AurigaLayout.getImpostazioniDocumento("repertorioInterno");
				if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioInterno")) {
					String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioInterno");
					String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioInterno");
					apriRepertorioDetail(nomeEntita, repertorioInterno, idTipoDoc, nomeTipoDoc);
				} else {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioInterno");
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioInterno,
							new ServiceCallback<Record>() {

								@Override
								public void execute(Record lRecordTipoDoc) {
									String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
									String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
									apriRepertorioDetail(nomeEntita, repertorioInterno, idTipoDoc, nomeTipoDoc);
								}
							});
				}
			} else {
				final String repertorioInternoDefault = AurigaLayout.getImpostazioniDocumento("repertorioInterno");			
				AurigaLayout.apriSceltaRepertorioPopup("I", repertorioInternoDefault, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordRepertorio) {	
						
						final String repertorioInterno = lRecordRepertorio != null ? lRecordRepertorio.getAttribute("repertorio") : null;
						if (repertorioInterno != null && !"".equals(repertorioInterno)) {
							if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioInterno")) {
								String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioInterno");
								String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioInterno");
								apriRepertorioDetail(nomeEntita, repertorioInterno, idTipoDoc, nomeTipoDoc);
							} else {
								String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioInterno");
								AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioInterno, new ServiceCallback<Record>() {

									@Override
									public void execute(Record lRecordTipoDoc) {
										String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
										String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
										apriRepertorioDetail(nomeEntita, repertorioInterno, idTipoDoc, nomeTipoDoc);
									}
								});
							}
						}
					}
				});
			}
		} else if("U".equalsIgnoreCase(flgTipoProv)) {
			if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaRepertorioUscita")) {
				final String repertorioUscita = AurigaLayout.getImpostazioniDocumento("repertorioUscita");
				if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioUscita")) {
					String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioUscita");
					String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioUscita");
					apriRepertorioDetail(nomeEntita, repertorioUscita, idTipoDoc, nomeTipoDoc);
				} else {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioUscita");
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioUscita, new ServiceCallback<Record>() {

						@Override
						public void execute(Record lRecordTipoDoc) {
							String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
							String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
							apriRepertorioDetail(nomeEntita, repertorioUscita, idTipoDoc, nomeTipoDoc);
						}
					});
				}
			} else {
				final String repertorioUscitaDefault = AurigaLayout.getImpostazioniDocumento("repertorioUscita");		
				AurigaLayout.apriSceltaRepertorioPopup("U", repertorioUscitaDefault, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordRepertorio) {

						final String repertorioUscita = lRecordRepertorio != null ? lRecordRepertorio.getAttribute("repertorio") : null;
						if (repertorioUscita != null && !"".equals(repertorioUscita)) {
							if (AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaRepertorioUscita")) {
								String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioUscita");
								String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoRepertorioUscita");
								apriRepertorioDetail(nomeEntita, repertorioUscita, idTipoDoc, nomeTipoDoc);
							} else {
								String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoRepertorioUscita");
								AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, "R", repertorioUscita, new ServiceCallback<Record>() {

									@Override
									public void execute(Record lRecordTipoDoc) {
										String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
										String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
										apriRepertorioDetail(nomeEntita, repertorioUscita, idTipoDoc, nomeTipoDoc);
									}
								});
							}
						}
					}
				});
			}
		} 
	}
	
	public void buildRepertorioAccessoCivico(final String nomeEntita) {
		
		final String repertorioAccessoCivico = AurigaLayout.getParametroDB("SIGLA_REGISTRO_ACCESSO_CIVICO");		
		if (repertorioAccessoCivico != null && !"".equals(repertorioAccessoCivico)) {			
			AurigaLayout.apriSceltaTipoDocPopup(true, null, "R", repertorioAccessoCivico, new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordTipoDoc) {
					String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
					String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
					apriRepertorioDetail(nomeEntita, repertorioAccessoCivico, idTipoDoc, nomeTipoDoc);
				}
			});		
		}
	}
	
	public void buildRepertorioContratti(final String nomeEntita) {
		final String repertorioContratti = AurigaLayout.getParametroDB("SIGLA_REGISTRO_CONTRATTI");		
		if (repertorioContratti != null && !"".equals(repertorioContratti)) {			
			AurigaLayout.apriSceltaTipoDocPopup(true, null, "R", repertorioContratti, new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordTipoDoc) {
					String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
					String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
					apriRepertorioDetail(nomeEntita, repertorioContratti, idTipoDoc, nomeTipoDoc);
				}
			});		
		}
	}
	
	public void buildRepertorioTrasparenza(final String nomeEntita) {
		final String repertorioTrasparenza = AurigaLayout.getParametroDB("SIGLA_REPERTORIO_TRASP_AMM");		
		if (repertorioTrasparenza != null && !"".equals(repertorioTrasparenza)) {			
			AurigaLayout.apriSceltaTipoDocPopup(false, null, "R", repertorioTrasparenza, new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordTipoDoc) {
					String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
					String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
					apriRepertorioDetail(nomeEntita, repertorioTrasparenza, idTipoDoc, nomeTipoDoc);
				}
			});		
		}
	}

	public void apriRepertorioDetail(final String nomeEntita, final String repertorio, final String idTipoDoc, final String nomeTipoDoc) {
		GWTRestService<Record, Record> lControlloRepertoriazioneDataSource = new GWTRestService<Record, Record>("ControlloRepertoriazioneDataSource");
		lControlloRepertoriazioneDataSource.call(new Record(), new ServiceCallback<Record>() {

			@Override
			public void execute(final Record object) {
				Map<String, Object> initialValues = new HashMap<String, Object>();
				initialValues.put("repertorio", repertorio);
				if (idTipoDoc != null && !"".equals(idTipoDoc)) {
					initialValues.put("tipoDocumento", idTipoDoc);
					initialValues.put("nomeTipoDocumento", nomeTipoDoc);
				}
				if ("repertorio_entrata".equalsIgnoreCase(nomeEntita)) {
					RepertorioDetailEntrata repertorioDetailEntrata = ProtocollazioneUtil.buildRepertorioDetailEntrata(null, new ShowItemFunction() {

						@Override
						public boolean showItem() {
							return object.getAttributeAsBoolean("showCheckAnnoPassato");
						}
					});
					repertorioDetailEntrata.nuovoDettaglio(null, initialValues);
					if (AurigaLayout.getIsAttivaAccessibilita()) {
						ProtocollazioneWindow window = new ProtocollazioneWindow(repertorioDetailEntrata, "menu/repertorio_entrata.png", nomeEntita,
								I18NUtil.getMessages().menu_repertorio_entrata_title());
						window.show();
					} else {
					Layout.addPortlet(nomeEntita, repertorioDetailEntrata);
					}
				} else if("repertorio_accesso_civico".equalsIgnoreCase(nomeEntita)) {
					RepertorioDetailEntrata repertorioDetailEntrata = ProtocollazioneUtil.buildRepertorioDetailAccessoCivico(null, new ShowItemFunction() {

						@Override
						public boolean showItem() {
							return object.getAttributeAsBoolean("showCheckAnnoPassato");
						}
					});
					if(nomeTipoDoc.contains("semplice")) {
						initialValues.put("flgRichAccCivSemplice", true);
					} else if(nomeTipoDoc.contains("generalizzato")) {
						initialValues.put("flgRichAccCivGeneralizzato", true);
					}								
					repertorioDetailEntrata.nuovoDettaglio(null, initialValues);
					Layout.addPortlet(nomeEntita, repertorioDetailEntrata);
				} else if("repertorio_contratti".equalsIgnoreCase(nomeEntita)) {
					RepertorioDetailUscita repertorioDetailUscita = ProtocollazioneUtil.buildRepertorioDetailContratti(null, new ShowItemFunction() {

						@Override
						public boolean showItem() {
							return object.getAttributeAsBoolean("showCheckAnnoPassato");
						}
					});
					repertorioDetailUscita.nuovoDettaglio(null, initialValues);
					Layout.addPortlet(nomeEntita, repertorioDetailUscita);
				} else if("repertorio_trasparenza".equalsIgnoreCase(nomeEntita)) {
					RepertorioDetailInterno repertorioDetailInterno = ProtocollazioneUtil.buildRepertorioDetailInterno(null, new ShowItemFunction() {
						
						@Override
						public boolean showItem() {
							return object.getAttributeAsBoolean("showCheckAnnoPassato");
						}
					});		
					repertorioDetailInterno.nuovoDettaglio(null, initialValues);
					Layout.addPortlet(nomeEntita, repertorioDetailInterno);
				} else if ("repertorio_interno".equalsIgnoreCase(nomeEntita)) {
					RepertorioDetailInterno repertorioDetailInterno = ProtocollazioneUtil.buildRepertorioDetailInterno(null, new ShowItemFunction() {
						
						@Override
						public boolean showItem() {
							return object.getAttributeAsBoolean("showCheckAnnoPassato");
						}
					});		
					repertorioDetailInterno.nuovoDettaglio(null, initialValues);
					if (AurigaLayout.getIsAttivaAccessibilita()) {
						ProtocollazioneWindow window = new ProtocollazioneWindow(repertorioDetailInterno, "menu/repertorio_interno.png", nomeEntita,
								I18NUtil.getMessages().menu_repertorio_interno_title());
						window.show();
					} else {
					Layout.addPortlet(nomeEntita, repertorioDetailInterno);
					}
				} else if ("repertorio_uscita".equalsIgnoreCase(nomeEntita)) {
					RepertorioDetailUscita repertorioDetailUscita = ProtocollazioneUtil.buildRepertorioDetailUscita(null, new ShowItemFunction() {
						
						@Override
						public boolean showItem() {
							return object.getAttributeAsBoolean("showCheckAnnoPassato");
						}
					});					
					repertorioDetailUscita.nuovoDettaglio(null, initialValues);
					if (AurigaLayout.getIsAttivaAccessibilita()) {
						ProtocollazioneWindow window = new ProtocollazioneWindow(repertorioDetailUscita, "menu/repertorio_uscita.png", nomeEntita,
								I18NUtil.getMessages().menu_repertorio_uscita_title());
						window.show();
					} else {
					Layout.addPortlet(nomeEntita, repertorioDetailUscita);
				}
			}
			}
		});
	}

	@Override
	public GWTRestDataSource getPreferenceDataSource() {
		return new GWTRestDataSource("AurigaPreferenceDataSource", "key", FieldType.TEXT);
	}

	@Override
	public GWTRestService<GetListaDefPrefsBean, GetListaDefPrefsBean> getListaDefPrefsService() {
		return new GWTRestService<GetListaDefPrefsBean, GetListaDefPrefsBean>("AurigaGetListaDefPrefsDataSource");
	}

	@Override
	public GWTRestService<LoginBean, MenuBean> getServiceRestMenu() {
		return new GWTRestService<LoginBean, MenuBean>("AurigaMenuDataSource");
	}

	@Override
	public String getPrefKeyPrefix() {
		return null;
	}
	
	@Override
	public void onClickLookupButtonWithFilters(FilterLookupType type, Record filters, final Map<String, Object> extraparams, final ServiceCallback<Record> callback) {


		if (type != null) {
			if (type.equals(FilterLookupType.indirizzi_viario)) {
				LookupViarioPopup lookupViarioPopup = new LookupViarioPopup(null, null, true) {

					@Override
					public void manageMultiLookupUndo(Record record) {

					}

					@Override
					public void manageMultiLookupBack(Record record) {

					}

					@Override
					public void manageLookupBack(Record record) {

						Record lRecordLookup = new Record();
						lRecordLookup.setAttribute("id", record.getAttribute("codiceViarioToponimo"));
						lRecordLookup.setAttribute("descrizione", record.getAttribute("descrNomeToponimo"));
						if (callback != null) {
							callback.execute(lRecordLookup);
						}
					}
				};
				lookupViarioPopup.show();
			}

			if (type.equals(FilterLookupType.rubrica_soggetti)) {
				
				LookupSoggettiPopup lookupSoggettiPopup = new LookupSoggettiPopup(filters, null, true) {

					@Override
					public void manageMultiLookupUndo(Record record) {

					}

					@Override
					public void manageMultiLookupBack(Record record) {

					}

					@Override
					public void manageLookupBack(Record record) {

						Record lRecordLookup = new Record();
						lRecordLookup.setAttribute("id", record.getAttribute("idSoggetto"));
						String tipo = calcolaTipoSoggetto(record.getAttribute("tipo"));
						if (tipo != null) {
							if (isPersonaGiuridica(tipo)) {
								lRecordLookup.setAttribute("descrizione", record.getAttribute("denominazione"));
							} else if (isPersonaFisica(tipo)) {
								lRecordLookup.setAttribute("descrizione",
										record.getAttribute("cognome") + " " + record.getAttribute("nome"));
							}
						} else {
							Layout.addMessage(new MessageBean("Soggetto non presente in rubrica o di tipo non ammesso",
									"", MessageType.ERROR));
						}
						if (callback != null) {
							callback.execute(lRecordLookup);
						}
					}

					public String calcolaTipoSoggetto(String tipo) {
						String tipoSoggetto = null;
						if ("UO;UOI".equals(tipo)) {
							tipoSoggetto = "UOI";
						} else if ("UP".equals(tipo)) {
							tipoSoggetto = "UP";
						} else if ("#APA".equals(tipo)) {
							tipoSoggetto = "PA";
						} else if ("#IAMM".equals(tipo)) {
							tipoSoggetto = "AOOI";
						} else if ("#AF".equals(tipo)) {
							tipoSoggetto = "PF";
						} else if ("#AG".equals(tipo)) {
							tipoSoggetto = "PG";
						}
						return tipoSoggetto;
					}

					protected boolean isPersonaGiuridica(String tipoSoggetto) {
						if (tipoSoggetto != null) {
							if ("G".equals(tipoSoggetto) || "PA".equals(tipoSoggetto) || "PG".equals(tipoSoggetto)
									|| "UOI".equals(tipoSoggetto) || "AOOI".equals(tipoSoggetto)
									|| "AOOE".equals(tipoSoggetto))
								return true;
						}
						return false;
					}

					protected boolean isPersonaFisica(String tipoSoggetto) {
						if (tipoSoggetto != null) {
							if ("F".equals(tipoSoggetto) || "PF".equals(tipoSoggetto) || "UP".equals(tipoSoggetto))
								return true;
						}
						return false;
					}
					
					@Override
					public String[] getTipiAmmessi() {
						if (extraparams != null) {
							if (extraparams.get("tipiAmmessi") != null) {
								return (String[])extraparams.get("tipiAmmessi");
							}
						} 
						return new String[0];
					}
					
					@Override
					public String getFinalita() {
						if (extraparams != null) {
							if (extraparams.get("finalita") != null) {
								return (String)extraparams.get("finalita");
							}
						}
						return super.getFinalita();
					}
					
				};
				lookupSoggettiPopup.show();
			}
		}
	
		
	}
	@Override
	public void onClickLookupFilterLookupButton(FilterLookupType type, final ServiceCallback<Record> callback) {

		if (type != null) {
			if (type.equals(FilterLookupType.indirizzi_viario)) {
				LookupViarioPopup lookupViarioPopup = new LookupViarioPopup(null, null, true) {

					@Override
					public void manageMultiLookupUndo(Record record) {

					}

					@Override
					public void manageMultiLookupBack(Record record) {

					}

					@Override
					public void manageLookupBack(Record record) {

						Record lRecordLookup = new Record();
						lRecordLookup.setAttribute("id", record.getAttribute("codiceViarioToponimo"));
						lRecordLookup.setAttribute("descrizione", record.getAttribute("descrNomeToponimo"));
						if (callback != null) {
							callback.execute(lRecordLookup);
						}
					}
				};
				lookupViarioPopup.show();
			}

			if (type.equals(FilterLookupType.rubrica_soggetti)) {
				LookupSoggettiPopup lookupSoggettiPopup = new LookupSoggettiPopup(null, null, true) {

					@Override
					public void manageMultiLookupUndo(Record record) {

					}

					@Override
					public void manageMultiLookupBack(Record record) {

					}

					@Override
					public void manageLookupBack(Record record) {

						Record lRecordLookup = new Record();
						lRecordLookup.setAttribute("id", record.getAttribute("idSoggetto"));
						String tipo = calcolaTipoSoggetto(record.getAttribute("tipo"));
						if (tipo != null) {
							if (isPersonaGiuridica(tipo)) {
								lRecordLookup.setAttribute("descrizione", record.getAttribute("denominazione"));
							} else if (isPersonaFisica(tipo)) {
								lRecordLookup.setAttribute("descrizione",
										record.getAttribute("cognome") + " " + record.getAttribute("nome"));
							}
						} else {
							Layout.addMessage(new MessageBean("Soggetto non presente in rubrica o di tipo non ammesso",
									"", MessageType.ERROR));
						}
						if (callback != null) {
							callback.execute(lRecordLookup);
						}
					}

					public String calcolaTipoSoggetto(String tipo) {
						String tipoSoggetto = null;
						if ("UO;UOI".equals(tipo)) {
							tipoSoggetto = "UOI";
						} else if ("UP".equals(tipo)) {
							tipoSoggetto = "UP";
						} else if ("#APA".equals(tipo)) {
							tipoSoggetto = "PA";
						} else if ("#IAMM".equals(tipo)) {
							tipoSoggetto = "AOOI";
						} else if ("#AF".equals(tipo)) {
							tipoSoggetto = "PF";
						} else if ("#AG".equals(tipo)) {
							tipoSoggetto = "PG";
						}
						return tipoSoggetto;
					}

					protected boolean isPersonaGiuridica(String tipoSoggetto) {
						if (tipoSoggetto != null) {
							if ("G".equals(tipoSoggetto) || "PA".equals(tipoSoggetto) || "PG".equals(tipoSoggetto)
									|| "UOI".equals(tipoSoggetto) || "AOOI".equals(tipoSoggetto)
									|| "AOOE".equals(tipoSoggetto))
								return true;
						}
						return false;
					}

					protected boolean isPersonaFisica(String tipoSoggetto) {
						if (tipoSoggetto != null) {
							if ("F".equals(tipoSoggetto) || "PF".equals(tipoSoggetto) || "UP".equals(tipoSoggetto))
								return true;
						}
						return false;
					}
				};
				lookupSoggettiPopup.show();
			}
		}
	}

	@Override
	public void onClickLookupFilterDetailButton(FilterLookupType type, String id, final String descrizione) {
		if (type != null) {
			if (type.equals(FilterLookupType.rubrica_soggetti)) {
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idSoggetto", id);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnagraficaSoggettiDataSource",
						"idSoggetto", FieldType.TEXT);
				lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record detailRecord = response.getData()[0];
							SoggettiDetail detail = new SoggettiDetail("anagrafiche_soggetti.detail");
							detail.editRecord(detailRecord);
							detail.viewMode();
							Layout.addModalWindow("dettaglio_soggetto", "Dettaglio " + descrizione,
									"buttons/detail.png", detail);
						}
					}
				});
			}
		}
	}

	@Override
	public FormItem buildCustomFilterEditorType(FilterFieldBean filterFieldBean) {
		return null;
	}

	@Override
	public String getParametroDB(String nome) {
		return AurigaLayout.getParametroDB(nome);
	}

	@Override
	public boolean getParametroDBAsBoolean(String nome) {
		return AurigaLayout.getParametroDBAsBoolean(nome);
	}

	@Override
	public boolean isAbilToExportList() {
		return true;
	}
	
	@Override
	public boolean isAttivaAccessibilita() {
		return isAttivaAccessibilita == null ? false : isAttivaAccessibilita;
	}

	@Override
	public void initIsAttivaAccessibilita() {
		try {
			final GWTRestDataSource isAttivaAccessibilitaDS = UserInterfaceFactory.getPreferenceDataSource();
			isAttivaAccessibilitaDS.addParam("prefKey", "impostazioniSceltaAccessibilita");
			AdvancedCriteria preferenceAccessibilitaCriteria = new AdvancedCriteria();
			preferenceAccessibilitaCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
			isAttivaAccessibilitaDS.fetchData(preferenceAccessibilitaCriteria, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record[] data = response.getData();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS && data.length != 0) {
						
						String value = data[0].getAttributeAsString("value");
						Record isAttivaAccessibilitaRecord = new Record(JSON.decode(value));
						isAttivaAccessibilita = (isAttivaAccessibilitaRecord != null && isAttivaAccessibilitaRecord.getAttributeAsBoolean("abilitaAccessibilita") != null) ? isAttivaAccessibilitaRecord.getAttributeAsBoolean("abilitaAccessibilita")
									: false;
					}
							
				}
			});
		} catch (Exception e) {
			isAttivaAccessibilita = false;
		}
	}

	@Override
	public ClientFactory getClientFactory() {
		return new ClientFactoryImpl();
	}

	@Override
	public boolean isAttivoClienteRER() {
		return AurigaLayout.isAttivoClienteRER();
	}
	
}