/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Interfaccia che espone i le costanti delle label da inserire nelle gui
 * 
 * @author michele
 * 
 */
public interface Messages extends it.eng.utility.ui.module.core.client.i18n.defaults.Messages {

  //#############################################################
  //#                     ALLEGATO CANVAS                       #
  //#############################################################

  @Key("allegato_canvas_barcodeA4DatiTipologiaMenuItem")
  String allegato_canvas_barcodeA4DatiTipologiaMenuItem(); 

  @Key("allegato_canvas_barcodeA4MenuItem")
  String allegato_canvas_barcodeA4MenuItem(); 

  @Key("allegato_canvas_barcodeA4MultipliMenuItem")
  String allegato_canvas_barcodeA4MultipliMenuItem(); 

  @Key("allegato_canvas_barcodeEtichettaDatiTipologiaMenuItem")
  String allegato_canvas_barcodeEtichettaDatiTipologiaMenuItem(); 

  @Key("allegato_canvas_barcodeEtichettaMenuItem")
  String allegato_canvas_barcodeEtichettaMenuItem(); 

  @Key("allegato_canvas_barcodeEtichettaMultiploMenuItem")
  String allegato_canvas_barcodeEtichettaMultiploMenuItem(); 

  @Key("allegato_canvas_barcodeMultipliA4DatiTipologiaMenuItem")
  String allegato_canvas_barcodeMultipliA4DatiTipologiaMenuItem(); 

  @Key("allegato_canvas_barcodeMultipliEtichettaDatiTipologiaMenuItem")
  String allegato_canvas_barcodeMultipliEtichettaDatiTipologiaMenuItem(); 

  @Key("allegato_canvas_messageDocumentoNonTipizzato")
  String allegato_canvas_messageDocumentoNonTipizzato(); 

  @Key("allegato_canvas_timbraDatiSegnaturaMenuItem")
  String allegato_canvas_timbraDatiSegnaturaMenuItem(); 

  @Key("allegato_canvas_timbraDatiSegnaturaMenuItemTitolo")
  String allegato_canvas_timbraDatiSegnaturaMenuItemTitolo(); 

  @Key("allegato_canvas_timbraDatiTipologiaMenuItem")
  String allegato_canvas_timbraDatiTipologiaMenuItem(); 

  @Key("allegato_canvas_timbraMenuItem")
  String allegato_canvas_timbraMenuItem(); 



  //#############################################################
  //#                   ANAGRAFE DEI PROCEDIMENTI               #
  //#############################################################

  @Key("anagrafe_procedimenti_Fasi_esterne")
  String anagrafe_procedimenti_Fasi_esterne(); 

  @Key("anagrafe_procedimenti_Interruzione")
  String anagrafe_procedimenti_Interruzione(); 

  @Key("anagrafe_procedimenti_Interruzione_entro_gg")
  String anagrafe_procedimenti_Interruzione_entro_gg(); 

  @Key("anagrafe_procedimenti_N_max_sospensioni")
  String anagrafe_procedimenti_N_max_sospensioni(); 

  @Key("anagrafe_procedimenti_Rif_normativi")
  String anagrafe_procedimenti_Rif_normativi(); 

  @Key("anagrafe_procedimenti_Silenzio_assenso")
  String anagrafe_procedimenti_Silenzio_assenso(); 

  @Key("anagrafe_procedimenti_Sospensioni")
  String anagrafe_procedimenti_Sospensioni(); 

  @Key("anagrafe_procedimenti_UOCompetente")
  String anagrafe_procedimenti_UOCompetente(); 

  @Key("anagrafe_procedimenti_descrizione")
  String anagrafe_procedimenti_descrizione(); 

  @Key("anagrafe_procedimenti_duratamax")
  String anagrafe_procedimenti_duratamax(); 

  @Key("anagrafe_procedimenti_id")
  String anagrafe_procedimenti_id(); 

  @Key("anagrafe_procedimenti_iniziativa")
  String anagrafe_procedimenti_iniziativa(); 

  @Key("anagrafe_procedimenti_nome")
  String anagrafe_procedimenti_nome(); 

  @Key("anagrafe_procedimenti_responsabile")
  String anagrafe_procedimenti_responsabile(); 

  @Key("anagrafe_procedimenti_lookupProcedimentiPopup_title")
  String anagrafe_procedimenti_lookupProcedimentiPopup_title(); 



  //#############################################################
  //#    				APPLICAZIONI ESTERNE   		            #
  //#############################################################

  @Key("applicazioni_esterne_edit_title")
  String applicazioni_esterne_edit_title(String attribute0);

  @Key("applicazioni_esterne_new_title")
  String applicazioni_esterne_new_title(); 

  @Key("applicazioni_esterne_prompt")
  String applicazioni_esterne_prompt(); 

  @Key("applicazioni_esterne_title")
  String applicazioni_esterne_title(); 

  @Key("applicazioni_esterne_view_title")
  String applicazioni_esterne_view_title(String attribute0);


  //#ApplicazioniEsterneList
  @Key("applicazioni_esterne_codApplicazione_list")
  String applicazioni_esterne_codApplicazione_list(); 

  @Key("applicazioni_esterne_codIstanza_list")
  String applicazioni_esterne_codIstanza_list(); 

  @Key("applicazioni_esterne_dtCensimento_list")
  String applicazioni_esterne_dtCensimento_list(); 

  @Key("applicazioni_esterne_dtUltimoAggiornamento_list")
  String applicazioni_esterne_dtUltimoAggiornamento_list(); 

  @Key("applicazioni_esterne_flgUsaCredenzialiDiverse_list")
  String applicazioni_esterne_flgUsaCredenzialiDiverse_list(); 

  @Key("applicazioni_esterne_nome_list")
  String applicazioni_esterne_nome_list(); 

  @Key("applicazioni_esterne_utenteCensimento_list")
  String applicazioni_esterne_utenteCensimento_list(); 

  @Key("applicazioni_esterne_utenteUltimoAggiornamento_list")
  String applicazioni_esterne_utenteUltimoAggiornamento_list(); 

  @Key("applicazioni_esterne_valido_list")
  String applicazioni_esterne_valido_list(); 


  //#ApplicazioniEsterneDetail
  @Key("applicazioni_esterne_codApplicazione_detail")
  String applicazioni_esterne_codApplicazione_detail(); 

  @Key("applicazioni_esterne_codIstanza_detail")
  String applicazioni_esterne_codIstanza_detail(); 

  @Key("applicazioni_esterne_dtCensimento_detail")
  String applicazioni_esterne_dtCensimento_detail(); 

  @Key("applicazioni_esterne_dtUltimoAggiornamento_detail")
  String applicazioni_esterne_dtUltimoAggiornamento_detail(); 

  @Key("applicazioni_esterne_flgUsaCredenzialiDiverse_detail")
  String applicazioni_esterne_flgUsaCredenzialiDiverse_detail(); 

  @Key("applicazioni_esterne_nome_detail")
  String applicazioni_esterne_nome_detail(); 

  @Key("applicazioni_esterne_utenteCensimento_detail")
  String applicazioni_esterne_utenteCensimento_detail(); 

  @Key("applicazioni_esterne_utenteUltimoAggiornamento_detail")
  String applicazioni_esterne_utenteUltimoAggiornamento_detail(); 

  @Key("applicazioni_esterne_valido_detail")
  String applicazioni_esterne_valido_detail(); 



  //#######################################################
  //#### 				APPOSIZIONE 					###
  //#######################################################

  @Key("apposizioneFirma_button_title")
  String apposizioneFirma_button_title(); 
  
  @Key("apposizioneFirmaProtocollazione_button_title")
  String apposizioneFirmaProtocollazione_button_title(); 

  @Key("apposizioneVisto_button_title")
  String apposizioneVisto_button_title(); 

  @Key("apposizione_button_annulla")
  String apposizione_button_annulla(); 

  @Key("apposizione_button_salva")
  String apposizione_button_salva(); 

  @Key("apposizione_label_apposta_firma")
  String apposizione_label_apposta_firma(); 

  @Key("apposizione_label_apposto_visto")
  String apposizione_label_apposto_visto(); 

  @Key("apposizione_label_rifiutata_firma")
  String apposizione_label_rifiutata_firma(); 

  @Key("apposizione_label_rifiutato_visto")
  String apposizione_label_rifiutato_visto(); 

  @Key("rifiutoApposizioneFirma_button_title")
  String rifiutoApposizioneFirma_button_title(); 

  @Key("rifiutoApposizioneVisto_button_title")
  String rifiutoApposizioneVisto_button_title(); 



  //#############################################################
  //#                        ARCHIVIO                           #
  //#############################################################

  @Key("archivio_lookupArchivioPopup_title")
  String archivio_lookupArchivioPopup_title(); 

  @Key("associa_invio_email_data_invio")
  String associa_invio_email_data_invio(); 

  @Key("archivio_chiudiFascicoloButton_prompt")
  String archivio_chiudiFascicoloButton_prompt(); 
  
  @Key("archivio_riapriFascicoloButton_prompt")
  String archivio_riapriFascicoloButton_prompt();

  @Key("archivio_versaInArchivioStoricoFascicoloButton_prompt")
  String archivio_versaInArchivioStoricoFascicoloButton_prompt(); 
  
  //#ArchivioLayout
  @Key("archivio_layout_archiviaConludiLavorazione_title")
  String archivio_layout_archiviaConludiLavorazione_title();
  
  @Key("archivio_layout_archiviaConludiLavorazione_errorfolder")
  String archivio_layout_archiviaConludiLavorazione_errorfolder();

  @Key("archivio_layout_apposizioneCommentiPopupMassivo_title")
  String archivio_layout_apposizioneCommentiPopupMassivo_title();
  
  @Key("archivio_layout_apposizioneCommentiMultiButton_title")
  String archivio_layout_apposizioneCommentiMultiButton_title();
  
  //#ArchivioList
  @Key("archivio_list_assegnatariField_title")
  String archivio_list_assegnatariField_title(); 

  @Key("archivio_list_apposizioneCommentiPopupUd_title")
  String archivio_list_apposizioneCommentiPopupUd_title(String attribute0);
  
  @Key("archivio_list_apposizioneCommentiPopupFolder_title")
  String archivio_list_apposizioneCommentiPopupFolder_title(String attribute0);

  @Key("archivio_list_descContenutiField_title")
  String archivio_list_descContenutiField_title(); 

  @Key("archivio_list_destinatariField_title")
  String archivio_list_destinatariField_title(); 

  @Key("archivio_list_destinatariInvioField_title")
  String archivio_list_destinatariInvioField_title(); 

  @Key("archivio_list_eliminatoDaField_ADA_value")
  String archivio_list_eliminatoDaField_ADA_value(); 

  @Key("archivio_list_eliminatoDaField_B_value")
  String archivio_list_eliminatoDaField_B_value(); 

  @Key("archivio_list_eliminatoDaField_C_value")
  String archivio_list_eliminatoDaField_C_value(); 

  @Key("archivio_list_eliminatoDaField_DAV_value")
  String archivio_list_eliminatoDaField_DAV_value(); 

  @Key("archivio_list_eliminatoDaField_DA_value")
  String archivio_list_eliminatoDaField_DA_value(); 

  @Key("archivio_list_eliminatoDaField_DFA_value")
  String archivio_list_eliminatoDaField_DFA_value(); 

  @Key("archivio_list_eliminatoDaField_DFI_value")
  String archivio_list_eliminatoDaField_DFI_value(); 

  @Key("archivio_list_eliminatoDaField_DIA_value")
  String archivio_list_eliminatoDaField_DIA_value(); 

  @Key("archivio_list_eliminatoDaField_DPR_value")
  String archivio_list_eliminatoDaField_DPR_value(); 

  @Key("archivio_list_eliminatoDaField_IEML_value")
  String archivio_list_eliminatoDaField_IEML_value(); 

  @Key("archivio_list_eliminatoDaField_I_value")
  String archivio_list_eliminatoDaField_I_value(); 

  @Key("archivio_list_eliminatoDaField_NA_value")
  String archivio_list_eliminatoDaField_NA_value(); 

  @Key("archivio_list_eliminatoDaField_NCC_value")
  String archivio_list_eliminatoDaField_NCC_value(); 

  @Key("archivio_list_eliminatoDaField_NNA_value")
  String archivio_list_eliminatoDaField_NNA_value(); 

  @Key("archivio_list_eliminatoDaField_N_value")
  String archivio_list_eliminatoDaField_N_value(); 

  @Key("archivio_list_eliminatoDaField_P_value")
  String archivio_list_eliminatoDaField_P_value(); 

  @Key("archivio_list_eliminatoDaField_S_value")
  String archivio_list_eliminatoDaField_S_value(); 

  @Key("archivio_list_eliminatoDaField_W_value")
  String archivio_list_eliminatoDaField_W_value(); 

  @Key("archivio_list_eliminatoDaField_title")
  String archivio_list_eliminatoDaField_title(); 

  @Key("archivio_list_estremiInvioNotificheField_notifiche_title")
  String archivio_list_estremiInvioNotificheField_notifiche_title(); 

  @Key("archivio_list_estremiInvioNotificheField_title")
  String archivio_list_estremiInvioNotificheField_title(); 

  @Key("archivio_list_flgInviataViaEmailField_title")
  String archivio_list_flgInviataViaEmailField_title(); 

  @Key("archivio_list_flgPresaInCaricoField_0_value")
  String archivio_list_flgPresaInCaricoField_0_value(); 

  @Key("archivio_list_flgPresaInCaricoField_1_value")
  String archivio_list_flgPresaInCaricoField_1_value(); 

  @Key("archivio_list_flgPresaInCaricoField_title")
  String archivio_list_flgPresaInCaricoField_title(); 

  @Key("archivio_list_flgRicevutaViaEmailField_title")
  String archivio_list_flgRicevutaViaEmailField_title(); 

  @Key("archivio_list_folderUpButton_prompt")
  String archivio_list_folderUpButton_prompt(); 

  @Key("archivio_list_iconaFolderButton_prompt")
  String archivio_list_iconaFolderButton_prompt(); 

  @Key("archivio_list_livelloRiservatezzaField_title")
  String archivio_list_livelloRiservatezzaField_title(); 

  @Key("archivio_list_mittentiField_title")
  String archivio_list_mittentiField_title(); 

  @Key("archivio_list_newFolderButton_prompt")
  String archivio_list_newFolderButton_prompt(); 

  @Key("archivio_list_newFolderCustomButton_prompt")
  String archivio_list_newFolderCustomButton_prompt(); 

  @Key("archivio_list_newUdButton_prompt")
  String archivio_list_newUdButton_prompt(); 

  @Key("archivio_list_nomeField_title")
  String archivio_list_nomeField_title(); 

  @Key("archivio_list_oggettoField_title")
  String archivio_list_oggettoField_title(); 

  @Key("archivio_list_motivoAnnullamentoField_title")
  String archivio_list_motivoAnnullamentoField_title(); 
  
  @Key("archivio_list_prioritaField_title")
  String archivio_list_prioritaField_title(); 

  @Key("archivio_list_prioritaInvioNotificheField_title")
  String archivio_list_prioritaInvioNotificheField_title(); 

  @Key("archivio_list_responsabileFascicoloField_title")
  String archivio_list_responsabileFascicoloField_title(); 

  @Key("archivio_list_programmazioneAcquisti_title")
  String archivio_list_programmazioneAcquisti_title();
    
  @Key("archivio_list_codiceCIG_title")
  String archivio_list_codiceCIG_title();
  
  @Key("archivio_list_cui_title")
  String archivio_list_cui_title();
  
  @Key("archivio_list_inConoscenzaA_title")
  String archivio_list_inConoscenzaA_title();
  
  @Key("archivio_list_scoreField_title")
  String archivio_list_scoreField_title();
  
  @Key("archivio_list_dtEsecutivitaField_title")
  String archivio_list_dtEsecutivitaField_title();
  
  @Key("archivio_list_flgImmediatamenteEsegField")
  String archivio_list_flgImmediatamenteEsegField();
  
  @Key("archivio_list_segnaturaField_title")
  String archivio_list_segnaturaField_title(); 

  @Key("archivio_list_nroSecondarioField_title")
  String archivio_list_nroSecondarioField_title();
  
  @Key("archivio_list_numeroField_title")
  String archivio_list_numeroField_title(); 

  @Key("archivio_list_annoField_title")
  String archivio_list_annoField_title(); 

  @Key("archivio_list_statoField_title")
  String archivio_list_statoField_title(); 

  @Key("archivio_list_tipoField_title")
  String archivio_list_tipoField_title(); 

  @Key("archivio_list_file_timbra")
  String archivio_list_file_timbra(); 

  @Key("archivio_list_file_timbra_datiSegnatura")
  String archivio_list_file_timbra_datiSegnatura(); 

  @Key("archivio_list_file_timbra_datiTipologia")
  String archivio_list_file_timbra_datiTipologia(); 
  
  @Key("archivio_list_revoca_atto_button")
  String archivio_list_revoca_atto_button(); 

  @Key("archivio_list_protocollazione_entrata_button")
  String archivio_list_protocollazione_entrata_button(); 

  @Key("archivio_list_protocollazione_uscita_button")
  String archivio_list_protocollazione_uscita_button(); 

  @Key("archivio_list_protocollazione_interna_button")
  String archivio_list_protocollazione_interna_button(); 
  
  @Key("archivio_list_tipoProtocolloField_title")
  String archivio_list_tipoProtocolloField_title(); 

  @Key("archivio_list_tipoProtocolloInEntrataAlt_value")
  String archivio_list_tipoProtocolloInEntrataAlt_value(); 

  @Key("archivio_list_tipoProtocolloInUscitaAlt_value")
  String archivio_list_tipoProtocolloInUscitaAlt_value(); 

  @Key("archivio_list_tipoProtocolloInternoAlt_value")
  String archivio_list_tipoProtocolloInternoAlt_value();
  
  @Key("archivio_list_tipoVersoInEntrataAlt_value")
  String archivio_list_tipoVersoInEntrataAlt_value(); 

  @Key("archivio_list_tipoVersoInUscitaAlt_value")
  String archivio_list_tipoVersoInUscitaAlt_value(); 

  @Key("archivio_list_tipoVersoInternoAlt_value")
  String archivio_list_tipoVersoInternoAlt_value();

  @Key("archivio_list_tipoProtocolloIstanzaAlt_value")
  String archivio_list_tipoProtocolloIstanzaAlt_value(); 
  
  @Key("archivio_list_tipoDocumentoRegistratoAlt_value")
  String archivio_list_tipoDocumentoRegistratoAlt_value();   

  @Key("archivio_list_tipoProtocolloNULLAlt_value")
  String archivio_list_tipoProtocolloNULLAlt_value(); 

  @Key("archivio_list_tsAperturaField_title")
  String archivio_list_tsAperturaField_title(); 

  @Key("archivio_list_tsChiusuraField_title")
  String archivio_list_tsChiusuraField_title(); 

  @Key("archivio_list_tsDocumentoField_title")
  String archivio_list_tsDocumentoField_title(); 

  @Key("archivio_list_tsEliminazioneField_title")
  String archivio_list_tsEliminazioneField_title(); 

  @Key("archivio_list_tsInvioField_title")
  String archivio_list_tsInvioField_title(); 

  @Key("archivio_list_tsRegistrazioneField_title")
  String archivio_list_tsRegistrazioneField_title(); 

  @Key("archivio_list_tsRicezioneField_title")
  String archivio_list_tsRicezioneField_title(); 

  @Key("archivio_list_uoProtocollanteField_title")
  String archivio_list_uoProtocollanteField_title(); 

  @Key("archivio_list_utenteProtocollanteField_title")
  String archivio_list_utenteProtocollanteField_title(); 
  
  @Key("archivio_list_repertorio_title")
  String archivio_list_repertorio_title();
  
  @Key("archivio_list_attoAutAnnullamento_title")
  String archivio_list_attoAutAnnullamento_title();
  
  @Key("archivio_list_perizie_title")
  String archivio_list_perizie_title();
  
  @Key("archivio_list_centroDiCosto_title")
  String archivio_list_centroDiCosto_title();
  
  @Key("archivio_list_dataScadenza_title")
  String archivio_list_dataScadenza_title();
  
  @Key("archivio_list_annotazioniApposteField_title")
  String archivio_list_annotazioniApposteField_title();
  
  @Key("archivio_list_annotazioniAppostePopupFolder_title")
  String archivio_list_annotazioniAppostePopupFolder_title();

  @Key("archivio_list_flgSottopostoControlloRegAmmField_title")
  String archivio_list_flgSottopostoControlloRegAmmField_title(); 

  @Key("archivio_list_flgSottopostoControlloRegAmmField_1_value")
  String archivio_list_flgSottopostoControlloRegAmmField_1_value(); 
    
  @Key("archivio_list_nrLiquidazioneContestualeField_title")
  String archivio_list_nrLiquidazioneContestualeField_title(); 
  
  @Key("archivio_list_flgDeterminaContrarreTramiteGara_title")
  String archivio_list_flgDeterminaContrarreTramiteGara_title(); 
  
  @Key("archivio_list_flgDeterminaAggiudicaGara_title")
  String archivio_list_flgDeterminaAggiudicaGara_title(); 
  
  @Key("archivio_list_flgDeterminaRimodulazioneSpesaPostAggiudica_title")
  String archivio_list_flgDeterminaRimodulazioneSpesaPostAggiudica_title(); 
  
  @Key("archivio_list_flgLiquidazioneContestualeImpegni_title")
  String archivio_list_flgLiquidazioneContestualeImpegni_title(); 
  
  @Key("archivio_list_flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi_title")
  String archivio_list_flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi_title(); 
  
  @Key("archivio_list_tipoAffidamento_title")
  String archivio_list_tipoAffidamento_title(); 
  
  @Key("archivio_list_materiaTipoAtto_title")
  String archivio_list_materiaTipoAtto_title(); 
  
  @Key("archivio_list_progettoLLPP_title")
  String archivio_list_progettoLLPP_title(); 
  
  @Key("archivio_list_beniServizi_title")
  String archivio_list_beniServizi_title(); 
  
  @Key("archivio_list_ordinativi_title")
  String archivio_list_ordinativi_title(); 
  
  @Key("archivio_list_protocollazioneDocumenti_title")
  String archivio_list_protocollazioneDocumenti_title(); 
  
  @Key("archivio_list_recuperoFileDaTimbrareEFirmare_title")
  String archivio_list_recuperoFileDaTimbrareEFirmare_title();
		  
  @Key("archivio_list_apposizioneTimbri_title")
  String archivio_list_apposizioneTimbri_title(); 
  
  @Key("archivio_list_msgDiInvioField_title")
  String archivio_list_msgDiInvioField_title(); 
  
  @Key("archivio_list_listaOpereAttoVuota_error")
  String archivio_list_listaOpereAttoVuota_error(); 
  
  @Key("archivio_list_societaField_title")
  String archivio_list_societaField_title();
  
  
  @Key("archivio_list_statoTrasferimentoBloomfleetField_title")
  String archivio_list_statoTrasferimentoBloomfleetField_title();
  
  @Key("archivio_list_statoTrasferimentoBloomfleet_da_trasferire_Alt_title")
  String archivio_list_statoTrasferimentoBloomfleet_da_trasferire_Alt_title();
  
  @Key("archivio_list_statoTrasferimentoBloomfleet_trasferito_Alt_title")
  String archivio_list_statoTrasferimentoBloomfleet_trasferito_Alt_title();
  
  @Key("archivio_list_statoTrasferimentoBloomfleet_in_errore_Alt_title")
  String archivio_list_statoTrasferimentoBloomfleet_in_errore_Alt_title();
  
  @Key("archivio_list_flgPresenzaContrattiField")
  String archivio_list_flgPresenzaContrattiField();
  
  @Key("archivio_list_flgPresenzaContratti_1_Alt_title")
  String archivio_list_flgPresenzaContratti_1_Alt_title();
  
  //#ArchivioDetail
  @Key("archivio_detail_altridatiSection_title")
  String archivio_detail_altridatiSection_title(); 

  @Key("archivio_detail_assegnazioneSection_readonly_title")
  String archivio_detail_assegnazioneSection_readonly_title(); 

  @Key("archivio_detail_assegnazioneSection_title")
  String archivio_detail_assegnazioneSection_title(); 

  @Key("archivio_detail_collocazionefisicaSection_title")
  String archivio_detail_collocazionefisicaSection_title(); 

  @Key("archivio_detail_condivisioneSection_readonly_title")
  String archivio_detail_condivisioneSection_readonly_title(); 

  @Key("archivio_detail_condivisioneSection_title")
  String archivio_detail_condivisioneSection_title(); 

  @Key("archivio_detail_datiprincipaliSection_title")
  String archivio_detail_datiprincipaliSection_title(); 

  @Key("archivio_detail_descContenutiFascicoloItem_title")
  String archivio_detail_descContenutiFascicoloItem_title(); 

  @Key("archivio_detail_edit_title")
  String archivio_detail_edit_title(String attribute0);

  @Key("archivio_detail_emailInviataAlt_value")
  String archivio_detail_emailInviataAlt_value(); 

  @Key("archivio_detail_emailRicevutaAlt_value")
  String archivio_detail_emailRicevutaAlt_value(); 

  @Key("archivio_detail_estremiSection_title")
  String archivio_detail_estremiSection_title(); 

  @Key("archivio_detail_flgArchivioValueCAlt_value")
  String archivio_detail_flgArchivioValueCAlt_value(); 

  @Key("archivio_detail_flgArchivioValueDAlt_value")
  String archivio_detail_flgArchivioValueDAlt_value(); 

  @Key("archivio_detail_flgArchivioValueSAlt_value")
  String archivio_detail_flgArchivioValueSAlt_value(); 

  @Key("archivio_detail_new_title")
  String archivio_detail_new_title(); 

  @Key("archivio_detail_noteFascicoloItem_title")
  String archivio_detail_noteFascicoloItem_title(); 

  @Key("archivio_detail_nroSecondarioFascicoloItem_title")
  String archivio_detail_nroSecondarioFascicoloItem_title(); 

  @Key("archivio_detail_permessiSection_title")
  String archivio_detail_permessiSection_title(); 

  @Key("archivio_detail_responsabileSection_title")
  String archivio_detail_responsabileSection_title(); 

  @Key("archivio_detail_segnaturaSection_title")
  String archivio_detail_segnaturaSection_title(); 

  @Key("archivio_detail_view_title")
  String archivio_detail_view_title(String attribute0);

  @Key("archivio_detail_caricamento_dettaglio_documento")
  String archivio_detail_caricamento_dettaglio_documento();


  //#ArchivioContratto
  @Key("archivio_contratto_allegati_section_title")
  String archivio_contratto_allegati_section_title(); 

  @Key("archivio_contratto_anno_protocollo")
  String archivio_contratto_anno_protocollo(); 

  @Key("archivio_contratto_codice_fiscale")
  String archivio_contratto_codice_fiscale(); 

  @Key("archivio_contratto_conservazione_sostitutiva_section_title")
  String archivio_contratto_conservazione_sostitutiva_section_title(); 

  @Key("archivio_contratto_contenuti_section_title")
  String archivio_contratto_contenuti_section_title(); 

  @Key("archivio_contratto_contraenti_section_title")
  String archivio_contratto_contraenti_section_title(); 

  @Key("archivio_contratto_data_protocollo")
  String archivio_contratto_data_protocollo(); 

  @Key("archivio_contratto_data_stipula")
  String archivio_contratto_data_stipula(); 

  @Key("archivio_contratto_denominazione_cognome_nome")
  String archivio_contratto_denominazione_cognome_nome(); 

  @Key("archivio_contratto_errore_invio")
  String archivio_contratto_errore_invio(); 

  @Key("archivio_contratto_inviato_conservazione")
  String archivio_contratto_inviato_conservazione(); 

  @Key("archivio_contratto_nome_file_primario")
  String archivio_contratto_nome_file_primario(); 

  @Key("archivio_contratto_numero")
  String archivio_contratto_numero(); 

  @Key("archivio_contratto_numero_protocollo")
  String archivio_contratto_numero_protocollo(); 

  @Key("archivio_contratto_oggetto")
  String archivio_contratto_oggetto(); 

  @Key("archivio_contratto_partita_iva")
  String archivio_contratto_partita_iva(); 

  @Key("archivio_contratto_protocollo_section_title")
  String archivio_contratto_protocollo_section_title(); 

  @Key("archivio_contratto_salva_contratto_title")
  String archivio_contratto_salva_contratto_title(); 

  @Key("archivio_contratto_sigla_protocollo")
  String archivio_contratto_sigla_protocollo(); 

  @Key("archivio_contratto_stato_conservazione")
  String archivio_contratto_stato_conservazione(); 

  @Key("archivio_contratto_testata_section_title")
  String archivio_contratto_testata_section_title(); 



  //#############################################################
  //#                    ATTI IN LAVORAZIONE                    #
  //#############################################################

  @Key("atti_inlavorazione_iter_svolto_bottone_cerca")
  String atti_inlavorazione_iter_svolto_bottone_cerca(); 

  @Key("atti_inlavorazione_iter_svolto_filtro_messaggio")
  String atti_inlavorazione_iter_svolto_filtro_messaggio(); 

  @Key("atti_inlavorazione_iter_svolto_filtro_nome")
  String atti_inlavorazione_iter_svolto_filtro_nome(); 

  @Key("atti_inlavorazione_iter_svolto_filtro_svolta_da")
  String atti_inlavorazione_iter_svolto_filtro_svolta_da(); 

  @Key("atti_inlavorazione_iter_svolto_form_title")
  String atti_inlavorazione_iter_svolto_form_title(); 

  @Key("atti_inlavorazione_iter_svolto_image_prompt")
  String atti_inlavorazione_iter_svolto_image_prompt(); 

  @Key("atti_inlavorazione_iter_svolto_menu_title")
  String atti_inlavorazione_iter_svolto_menu_title(); 

  @Key("atti_inlavorazione_iter_svolto_title")
  String atti_inlavorazione_iter_svolto_title(); 

  @Key("atti_inlavorazione_list_annoProposta")
  String atti_inlavorazione_list_annoProposta(); 

  @Key("atti_inlavorazione_list_annoRepertorio")
  String atti_inlavorazione_list_annoRepertorio(); 

  @Key("atti_inlavorazione_list_dataProposta")
  String atti_inlavorazione_list_dataProposta(); 

  @Key("atti_inlavorazione_list_dataRepertorio")
  String atti_inlavorazione_list_dataRepertorio(); 

  @Key("atti_inlavorazione_list_idAttoField")
  String atti_inlavorazione_list_idAttoField(); 

  @Key("atti_inlavorazione_list_numeroProposta")
  String atti_inlavorazione_list_numeroProposta(); 

  @Key("atti_inlavorazione_list_numeroRepertorio")
  String atti_inlavorazione_list_numeroRepertorio(); 

  @Key("atti_inlavorazione_list_siglaProposta")
  String atti_inlavorazione_list_siglaProposta(); 

  @Key("atti_inlavorazione_list_siglaRepertorio")
  String atti_inlavorazione_list_siglaRepertorio(); 

  @Key("atti_inlavorazione_filter_dataInvioFaseApp")
  String atti_inlavorazione_filter_dataInvioFaseApp(); 

  @Key("atti_inlavorazione_filter_dataInvioVerCont")
  String atti_inlavorazione_filter_dataInvioVerCont(); 

  @Key("atti_inlavorazione_filter_dataRilAppCont")
  String atti_inlavorazione_filter_dataRilAppCont(); 

  @Key("atti_inlavorazione_list_assegnatario")
  String atti_inlavorazione_list_assegnatario(); 
  
  @Key("atti_inlavorazione_list_assegnatarioSG")
  String atti_inlavorazione_list_assegnatarioSG(); 

  @Key("atti_inlavorazione_list_avviatoDa")
  String atti_inlavorazione_list_avviatoDa(); 

  @Key("atti_inlavorazione_list_dataAtto")
  String atti_inlavorazione_list_dataAtto(); 

  @Key("atti_inlavorazione_list_dataAvvio")
  String atti_inlavorazione_list_dataAvvio(); 

  @Key("atti_inlavorazione_list_inFase")
  String atti_inlavorazione_list_inFase(); 

  @Key("atti_inlavorazione_list_oggetto")
  String atti_inlavorazione_list_oggetto(); 

  @Key("atti_inlavorazione_list_ordinamentoNumeroAtto")
  String atti_inlavorazione_list_ordinamentoNumeroAtto(); 

  @Key("atti_inlavorazione_list_ordinamentoNumeroProposta")
  String atti_inlavorazione_list_ordinamentoNumeroProposta(); 

  @Key("atti_inlavorazione_list_prossimeAttivita")
  String atti_inlavorazione_list_prossimeAttivita(); 

  @Key("atti_inlavorazione_list_tipoAtto")
  String atti_inlavorazione_list_tipoAtto(); 

  @Key("atti_inlavorazione_list_ultimaAttivita")
  String atti_inlavorazione_list_ultimaAttivita(); 
  
  @Key("atti_inlavorazione_list_ultimaAttivitaEsito")
  String atti_inlavorazione_list_ultimaAttivitaEsito(); 
  
  @Key("atti_inlavorazione_list_ultimaAttivitaMessaggio")
  String atti_inlavorazione_list_ultimaAttivitaMessaggio(); 

  @Key("atti_inlavorazione_list_unOrgProponente")
  String atti_inlavorazione_list_unOrgProponente(); 

  @Key("atti_inlavorazione_list_dataInvioVerificaContabile")
  String atti_inlavorazione_list_dataInvioVerificaContabile();
  
  @Key("atti_inlavorazione_list_dataInvioFaseAppDirettori")
  String atti_inlavorazione_list_dataInvioFaseAppDirettori();
    
  @Key("atti_inlavorazione_list_dirigenteAdottante")
  String atti_inlavorazione_list_dirigenteAdottante();
 
  @Key("atti_inlavorazione_list_respProcedimento")
  String atti_inlavorazione_list_respProcedimento();
  
  @Key("atti_inlavorazione_list_rup")
  String atti_inlavorazione_list_rup();

  @Key("atti_inlavorazione_list_altriRespInConcerto")
  String atti_inlavorazione_list_altriRespInConcerto();
  
  @Key("atti_inlavorazione_list_responsabilePEG")
  String atti_inlavorazione_list_responsabilePEG();
  
  @Key("atti_inlavorazione_list_uoCompDefSpesa")
  String atti_inlavorazione_list_uoCompDefSpesa();
  
  @Key("atti_inlavorazione_list_codiceCIG")
  String atti_inlavorazione_list_codiceCIG();
  
  @Key("atti_inlavorazione_list_estensori")
  String atti_inlavorazione_list_estensori();
  
  @Key("atti_inlavorazione_list_assegnatarioUffAcquisti")
  String atti_inlavorazione_list_assegnatarioUffAcquisti();
  
  
//#############################################################
//#            ATTI IN LAVORAZIONE COMPLETI                   #
//#############################################################
  
  @Key("atti_inlavorazione_completi_iter_svolto_bottone_cerca")
  String atti_inlavorazione_completi_iter_svolto_bottone_cerca();

  @Key("atti_inlavorazione_completi_iter_svolto_filtro_messaggio")
  String atti_inlavorazione_completi_iter_svolto_filtro_messaggio();

  @Key("atti_inlavorazione_completi_iter_svolto_filtro_nome")
  String atti_inlavorazione_completi_iter_svolto_filtro_nome();
	
  @Key("atti_inlavorazione_completi_iter_svolto_filtro_svolta_da")
  String atti_inlavorazione_completi_iter_svolto_filtro_svolta_da();
	
  @Key("atti_inlavorazione_completi_iter_svolto_form_title")
  String atti_inlavorazione_completi_iter_svolto_form_title();
	
  @Key("atti_inlavorazione_completi_iter_svolto_image_prompt")
  String atti_inlavorazione_completi_iter_svolto_image_prompt();
	
  @Key("atti_inlavorazione_completi_iter_svolto_menu_title")
  String atti_inlavorazione_completi_iter_svolto_menu_title();
	
  @Key("atti_inlavorazione_completi_iter_svolto_title")
  String atti_inlavorazione_completi_iter_svolto_title();
	
  @Key("atti_inlavorazione_completi_list_annoProposta")
  String atti_inlavorazione_completi_list_annoProposta();
	
  @Key("atti_inlavorazione_completi_list_annoRepertorio")
  String atti_inlavorazione_completi_list_annoRepertorio();
	
  @Key("atti_inlavorazione_completi_list_dataRepertorio")
  String atti_inlavorazione_completi_list_dataRepertorio();
	
  @Key("atti_inlavorazione_completi_list_idAttoField")
  String atti_inlavorazione_completi_list_idAttoField();
	
  @Key("atti_inlavorazione_completi_list_numeroProposta")
  String atti_inlavorazione_completi_list_numeroProposta();
	
  @Key("atti_inlavorazione_completi_list_numeroRepertorio")
  String atti_inlavorazione_completi_list_numeroRepertorio();
	
  @Key("atti_inlavorazione_completi_list_siglaProposta")
  String atti_inlavorazione_completi_list_siglaProposta();
	
  @Key("atti_inlavorazione_completi_list_siglaRepertorio")
  String atti_inlavorazione_completi_list_siglaRepertorio();
	
  @Key("atti_inlavorazione_completi_filter_dataInvioVerCont")
  String atti_inlavorazione_completi_filter_dataInvioVerCont();
	
  @Key("atti_inlavorazione_completi_filter_dataRilAppCont")
  String atti_inlavorazione_completi_filter_dataRilAppCont();
	
  @Key("atti_inlavorazione_completi_list_assegnatario")
  String atti_inlavorazione_completi_list_assegnatario();
  
  @Key("atti_inlavorazione_completi_list_assegnatarioSG")
  String atti_inlavorazione_completi_list_assegnatarioSG();
	
  @Key("atti_inlavorazione_completi_list_avviatoDa")
  String atti_inlavorazione_completi_list_avviatoDa();
	
  @Key("atti_inlavorazione_completi_list_dataAtto")
  String atti_inlavorazione_completi_list_dataAtto();
	
  @Key("atti_inlavorazione_completi_list_dataAvvio")
  String atti_inlavorazione_completi_list_dataAvvio();
	
  @Key("atti_inlavorazione_completi_list_inFase")
  String atti_inlavorazione_completi_list_inFase();
	
  @Key("atti_inlavorazione_completi_list_oggetto")
  String atti_inlavorazione_completi_list_oggetto();
	
  @Key("atti_inlavorazione_completi_list_ordinamentoNumeroAtto")
  String atti_inlavorazione_completi_list_ordinamentoNumeroAtto();
	
  @Key("atti_inlavorazione_completi_list_ordinamentoNumeroProposta")
  String atti_inlavorazione_completi_list_ordinamentoNumeroProposta();
	
  @Key("atti_inlavorazione_completi_list_prossimeAttivita")
  String atti_inlavorazione_completi_list_prossimeAttivita();
	
  @Key("atti_inlavorazione_completi_list_tipoAtto")
  String atti_inlavorazione_completi_list_tipoAtto();
	
  @Key("atti_inlavorazione_completi_list_ultimaAttivita")
  String atti_inlavorazione_completi_list_ultimaAttivita();
	
  @Key("atti_inlavorazione_completi_list_ultimaAttivitaEsito")
  String atti_inlavorazione_completi_list_ultimaAttivitaEsito(); 
  
  @Key("atti_inlavorazione_completi_list_ultimaAttivitaMessaggio")
  String atti_inlavorazione_completi_list_ultimaAttivitaMessaggio();
	
  @Key("atti_inlavorazione_completi_list_unOrgProponente")
  String atti_inlavorazione_completi_list_unOrgProponente();
	
  @Key("atti_inlavorazione_completi_list_uoCompetente")
  String atti_inlavorazione_completi_list_uoCompetente();
	
  @Key("atti_inlavorazione_completi_list_dataInvioVerificaContabile")
  String atti_inlavorazione_completi_list_dataInvioVerificaContabile();
	
  @Key("atti_inlavorazione_completi_list_codiceCIG")
  String atti_inlavorazione_completi_list_codiceCIG();
	
  @Key("atti_inlavorazione_completi_list_nominativi")
  String atti_inlavorazione_completi_list_nominativi();
  
  @Key("atti_inlavorazione_completi_list_parolaChiave")
  String atti_inlavorazione_completi_list_parolaChiave();
  
  @Key("atti_inlavorazione_completi_list_iniziativa")
  String atti_inlavorazione_completi_list_iniziativa();
  
  @Key("atti_inlavorazione_completi_list_flgRilevanzaContabile")
  String atti_inlavorazione_completi_list_flgRilevanzaContabile();
  
  @Key("atti_inlavorazione_completi_list_tipoDettaglio")
  String atti_inlavorazione_completi_list_tipoDettaglio();
  
  @Key("atti_inlavorazione_completi_list_codTipo")
  String atti_inlavorazione_completi_list_codTipo();
  
  @Key("atti_inlavorazione_completi_list_flgImmEseguibile")
  String atti_inlavorazione_completi_list_flgImmEseguibile();
  
  @Key("atti_inlavorazione_completi_list_stato")
  String atti_inlavorazione_completi_list_stato();
  
  @Key("atti_inlavorazione_completi_list_dataPrimoInoltroRagioneria")
  String atti_inlavorazione_completi_list_dataPrimoInoltroRagioneria();
  
  @Key("atti_inlavorazione_completi_list_nroInoltriRagioneria")
  String atti_inlavorazione_completi_list_nroInoltriRagioneria();
  
  @Key("atti_inlavorazione_completi_list_statoRagioneria")
  String atti_inlavorazione_completi_list_statoRagioneria();
  
  @Key("atti_inlavorazione_completi_list_centroDiCosto")
  String atti_inlavorazione_completi_list_centroDiCosto();
  
  @Key("atti_inlavorazione_completi_list_dataScadenza")
  String atti_inlavorazione_completi_list_dataScadenza();
  
  @Key("atti_inlavorazione_completi_list_ordinativi")
  String atti_inlavorazione_completi_list_ordinativi();
  
  @Key("atti_inlavorazione_completi_list_flgVisionato")
  String atti_inlavorazione_completi_list_flgVisionato();
  
  @Key("atti_inlavorazione_completi_list_tagApposti")
  String atti_inlavorazione_completi_list_tagApposti();
  
  @Key("atti_inlavorazione_completi_list_nrAttoStruttura")
  String atti_inlavorazione_completi_list_nrAttoStruttura();
  
  @Key("atti_inlavorazione_completi_list_nrLiqContestuale")
  String atti_inlavorazione_completi_list_nrLiqContestuale();
  
  @Key("atti_inlavorazione_completi_list_flgPresentiEmendamenti")
  String atti_inlavorazione_completi_list_flgPresentiEmendamenti();
  
  @Key("atti_inlavorazione_completi_list_dataInvioApprovazioneDG")
  String atti_inlavorazione_completi_list_dataInvioApprovazioneDG();
  
  @Key("atti_inlavorazione_completi_list_programmazioneAcquisti")
  String atti_inlavorazione_completi_list_programmazioneAcquisti();
  
  @Key("atti_inlavorazione_completi_list_cui")
  String atti_inlavorazione_completi_list_cui();
  
  @Key("atti_inlavorazione_completi_list_assegnatarioUffAcquisti")
  String atti_inlavorazione_completi_list_assegnatarioUffAcquisti();
  
  
  // AttiCompletiInLavorazioneFilter  
  @Key("atti_inlavorazione_completi_filter_determinaContrarreConGara")
  String atti_inlavorazione_completi_filter_determinaContrarreConGara();
  
  @Key("atti_inlavorazione_completi_filter_tipoAffidamento")
  String atti_inlavorazione_completi_filter_tipoAffidamento();
    
  @Key("atti_inlavorazione_completi_filter_determinaRimodulazioneSpesaPostAggiudica")
  String atti_inlavorazione_completi_filter_determinaRimodulazioneSpesaPostAggiudica();
  
  @Key("atti_inlavorazione_completi_filter_materieTipiAtto")
  String atti_inlavorazione_completi_filter_materieTipiAtto();
  
  @Key("atti_inlavorazione_completi_filter_liquidazioneContestualeImpegni")
  String atti_inlavorazione_completi_filter_liquidazioneContestualeImpegni();
  
  @Key("atti_inlavorazione_completi_filter_liquidazioneContestualeAltriAspettiCont")
  String atti_inlavorazione_completi_filter_liquidazioneContestualeAltriAspettiCont();
  
  @Key("atti_inlavorazione_completi_filter_determinaAggiudicaGara")
  String atti_inlavorazione_completi_filter_determinaAggiudicaGara();

  
  //##############################################################
  //# 		              ATTI PERSONALI 			             #
  //##############################################################

  @Key("atti_personali_list_assegnatario")
  String atti_personali_list_assegnatario(); 

  @Key("atti_personali_list_avviatoDa")
  String atti_personali_list_avviatoDa(); 

  @Key("atti_personali_list_dataAtto")
  String atti_personali_list_dataAtto(); 

  @Key("atti_personali_list_dataAvvio")
  String atti_personali_list_dataAvvio(); 

  @Key("atti_personali_list_dataInvioFaseAppDirettori")
  String atti_personali_list_dataInvioFaseAppDirettori(); 

  @Key("atti_personali_list_dataInvioVerificaContabile")
  String atti_personali_list_dataInvioVerificaContabile(); 

  @Key("atti_personali_list_dataProposta")
  String atti_personali_list_dataProposta(); 

  @Key("atti_personali_list_inFase")
  String atti_personali_list_inFase(); 

  @Key("atti_personali_list_oggetto")
  String atti_personali_list_oggetto(); 

  @Key("atti_personali_list_ordinamentoNumeroAtto")
  String atti_personali_list_ordinamentoNumeroAtto(); 

  @Key("atti_personali_list_ordinamentoNumeroProposta")
  String atti_personali_list_ordinamentoNumeroProposta(); 

  @Key("atti_personali_list_prossimeAttivita")
  String atti_personali_list_prossimeAttivita(); 

  @Key("atti_personali_list_tipoAtto")
  String atti_personali_list_tipoAtto(); 

  @Key("atti_personali_list_ultimaAttivita")
  String atti_personali_list_ultimaAttivita(); 

  @Key("atti_personali_list_ultimaAttivitaEsito")
  String atti_personali_list_ultimaAttivitaEsito(); 

  @Key("atti_personali_list_ultimaAttivitaMessaggio")
  String atti_personali_list_ultimaAttivitaMessaggio(); 

  @Key("atti_personali_list_unOrgProponente")
  String atti_personali_list_unOrgProponente(); 

  @Key("atti_personali_list_dirigenteAdottante")
  String atti_personali_list_dirigenteAdottante();
  
  @Key("atti_personali_list_respProcedimento")
  String atti_personali_list_respProcedimento();
  
  @Key("atti_personali_list_rup")
  String atti_personali_list_rup();

  @Key("atti_personali_list_altriRespInConcerto")
  String atti_personali_list_altriRespInConcerto();
  
  @Key("atti_personali_list_responsabilePEG")
  String atti_personali_list_responsabilePEG();
  
  @Key("atti_personali_list_uoCompDefSpesa")
  String atti_personali_list_uoCompDefSpesa();
  
  @Key("atti_personali_list_codiceCIG")
  String atti_personali_list_codiceCIG();
  
  @Key("atti_personali_list_estensori")
  String atti_personali_list_estensori();
  
  //##############################################################
  //# 		             ATTI PERSONALI COMPLETI 			     #
  //##############################################################

  @Key("atti_personali_completi_list_assegnatario")
  String atti_personali_completi_list_assegnatario(); 

  @Key("atti_personali_completi_list_avviatoDa")
  String atti_personali_completi_list_avviatoDa(); 
  
  @Key("atti_personali_completi_list_dataAtto")
  String atti_personali_completi_list_dataAtto(); 

  @Key("atti_personali_completi_list_dataAvvio")
  String atti_personali_completi_list_dataAvvio(); 

  @Key("atti_personali_completi_list_dataInvioVerificaContabile")
  String atti_personali_completi_list_dataInvioVerificaContabile(); 

  @Key("atti_personali_completi_list_inFase")
  String atti_personali_completi_list_inFase(); 

  @Key("atti_personali_completi_list_oggetto")
  String atti_personali_completi_list_oggetto(); 

  @Key("atti_personali_completi_list_ordinamentoNumeroAtto")
  String atti_personali_completi_list_ordinamentoNumeroAtto(); 

  @Key("atti_personali_completi_list_ordinamentoNumeroProposta")
  String atti_personali_completi_list_ordinamentoNumeroProposta(); 

  @Key("atti_personali_completi_list_prossimeAttivita")
  String atti_personali_completi_list_prossimeAttivita(); 

  @Key("atti_personali_completi_list_tipoAtto")
  String atti_personali_completi_list_tipoAtto(); 

  @Key("atti_personali_completi_list_ultimaAttivita")
  String atti_personali_completi_list_ultimaAttivita(); 
  
  @Key("atti_personali_completi_list_ultimaAttivitaEsito")
  String atti_personali_completi_list_ultimaAttivitaEsito();
  
  @Key("atti_personali_completi_list_ultimaAttivitaMessaggio")
  String atti_personali_completi_list_ultimaAttivitaMessaggio();

  @Key("atti_personali_completi_list_unOrgProponente")
  String atti_personali_completi_list_unOrgProponente(); 
    
  @Key("atti_personali_completi_list_uoCompetente")
  String atti_personali_completi_list_uoCompetente(); 
  
  @Key("atti_personali_completi_list_codiceCIG")
  String atti_personali_completi_list_codiceCIG();
  
  @Key("atti_personali_completi_list_nominativi")
  String atti_personali_completi_list_nominativi();
  
  @Key("atti_personali_completi_list_parolaChiave")
  String atti_personali_completi_list_parolaChiave();
  
  @Key("atti_personali_completi_list_iniziativa")
  String atti_personali_completi_list_iniziativa();
  
  @Key("atti_personali_completi_list_flgRilevanzaContabile")
  String atti_personali_completi_list_flgRilevanzaContabile();
  
  @Key("atti_personali_completi_list_tipoDettaglio")
  String atti_personali_completi_list_tipoDettaglio();
  
  @Key("atti_personali_completi_list_codTipo")
  String atti_personali_completi_list_codTipo();
  
  @Key("atti_personali_completi_list_flgImmEseguibile")
  String atti_personali_completi_list_flgImmEseguibile();
  
  @Key("atti_personali_completi_list_stato")
  String atti_personali_completi_list_stato();
  
  @Key("atti_personali_completi_list_dataPrimoInoltroRagioneria")
  String atti_personali_completi_list_dataPrimoInoltroRagioneria();
 
  @Key("atti_personali_completi_list_nroInoltriRagioneria")
  String atti_personali_completi_list_nroInoltriRagioneria();
  
  @Key("atti_personali_completi_list_statoRagioneria")
  String atti_personali_completi_list_statoRagioneria();
  
  @Key("atti_personali_completi_list_centroDiCosto")
  String atti_personali_completi_list_centroDiCosto();
  
  @Key("atti_personali_completi_list_dataScadenza")
  String atti_personali_completi_list_dataScadenza();
  
  @Key("atti_personali_completi_list_ordinativi")
  String atti_personali_completi_list_ordinativi();
  
  @Key("atti_personali_completi_list_flgVisionato")
  String atti_personali_completi_list_flgVisionato();
  
  @Key("atti_personali_completi_list_tagApposti")
  String atti_personali_completi_list_tagApposti();
  
  @Key("atti_personali_completi_list_flgNotifiche")
  String atti_personali_completi_list_flgNotifiche();
  
  @Key("atti_personali_completi_list_assegnatarioUffAcquisti")
  String atti_personali_completi_list_assegnatarioUffAcquisti();
  
  // AttiCompletiPersonaliFilter
  @Key("atti_personali_completi_filter_determinaContrarreConGara")
  String atti_personali_completi_filter_determinaContrarreConGara();
  
  @Key("atti_personali_completi_filter_tipoAffidamento")
  String atti_personali_completi_filter_tipoAffidamento();
    
  @Key("atti_personali_completi_filter_determinaRimodulazioneSpesaPostAggiudica")
  String atti_personali_completi_filter_determinaRimodulazioneSpesaPostAggiudica();
  
  @Key("atti_personali_completi_filter_materieTipiAtto")
  String atti_personali_completi_filter_materieTipiAtto();
  
  @Key("atti_personali_completi_filter_liquidazioneContestualeImpegni")
  String atti_personali_completi_filter_liquidazioneContestualeImpegni();
  
  @Key("atti_personali_completi_filter_liquidazioneContestualeAltriAspettiCont")
  String atti_personali_completi_filter_liquidazioneContestualeAltriAspettiCont();

  @Key("atti_personali_completi_filter_determinaAggiudicaGara")
  String atti_personali_completi_filter_determinaAggiudicaGara();

  
  @Key("atti_personali_list_nrAttoStruttura")
  String atti_personali_list_nrAttoStruttura();
  
  @Key("atti_personali_list_nrLiqContestuale")
  String atti_personali_list_nrLiqContestuale();
  
  @Key("atti_personali_list_flgPresentiEmendamenti")
  String atti_personali_list_flgPresentiEmendamenti();
  
  @Key("atti_personali_list_dataInvioApprovazioneDG")
  String atti_personali_list_dataInvioApprovazioneDG();
  
  @Key("atti_personali_list_programmazioneAcquisti")
  String atti_personali_list_programmazioneAcquisti();
  
  @Key("atti_personali_list_cui")
  String atti_personali_list_cui();
  
  
  @Key("atti_personali_list_assegnatarioUffAcquisti")
  String atti_personali_list_assegnatarioUffAcquisti();
  
  
  
  //#############################################################
  //#                      ATTRIBUTI CUSTOM                     #
  //#############################################################

  @Key("attributi_custom_altezza_video")
  String attributi_custom_altezza_video(); 

  @Key("attributi_custom_appartenente")
  String attributi_custom_appartenente(); 

  @Key("attributi_custom_case")
  String attributi_custom_case(); 

  @Key("attributi_custom_da_indicizzare")
  String attributi_custom_da_indicizzare(); 

  @Key("attributi_custom_dati_sensibili")
  String attributi_custom_dati_sensibili(); 

  @Key("attributi_custom_descrizione")
  String attributi_custom_descrizione(); 

  @Key("attributi_custom_edit_title")
  String attributi_custom_edit_title(String attribute0);

  @Key("attributi_custom_espressione_regolare")
  String attributi_custom_espressione_regolare(); 

  @Key("attributi_custom_etichetta")
  String attributi_custom_etichetta(); 

  @Key("attributi_custom_formato")
  String attributi_custom_formato(); 

  @Key("attributi_custom_largh_video")
  String attributi_custom_largh_video(); 

  @Key("attributi_custom_max_valore")
  String attributi_custom_max_valore(); 

  @Key("attributi_custom_min_valore")
  String attributi_custom_min_valore(); 

  @Key("attributi_custom_new_title")
  String attributi_custom_new_title(); 

  @Key("attributi_custom_nome")
  String attributi_custom_nome(); 

  @Key("attributi_custom_nro_cifre_caratteri")
  String attributi_custom_nro_cifre_caratteri(); 

  @Key("attributi_custom_nro_decimali")
  String attributi_custom_nro_decimali(); 

  @Key("attributi_custom_opzioni_possibili")
  String attributi_custom_opzioni_possibili(); 

  @Key("attributi_custom_ordine_num")
  String attributi_custom_ordine_num(); 

  @Key("attributi_custom_query_valori_possibili")
  String attributi_custom_query_valori_possibili(); 

  @Key("attributi_custom_riga_num")
  String attributi_custom_riga_num(); 

  @Key("attributi_custom_scelta_tipologia_lista")
  String attributi_custom_scelta_tipologia_lista(); 
  
  @Key("attributi_custom_scelta_tipologia_editorHtml")
  String attributi_custom_scelta_tipologia_editorHtml(); 
  
  @Key("attributi_custom_select_area_testo")
  String attributi_custom_select_area_testo(); 

  @Key("attributi_custom_select_casella_di_spunta")
  String attributi_custom_select_casella_di_spunta(); 

  @Key("attributi_custom_select_data")
  String attributi_custom_select_data(); 

  @Key("attributi_custom_select_data_ora")
  String attributi_custom_select_data_ora(); 

  @Key("attributi_custom_select_importo")
  String attributi_custom_select_importo(); 

  @Key("attributi_custom_select_lista_scelta_popolata")
  String attributi_custom_select_lista_scelta_popolata(); 

  @Key("attributi_custom_select_numerico")
  String attributi_custom_select_numerico(); 

  @Key("attributi_custom_select_radio")
  String attributi_custom_select_radio(); 

  @Key("attributi_custom_select_stringa")
  String attributi_custom_select_stringa();
  
  @Key("attributi_custom_select_editorHtml")
  String attributi_custom_select_editorHtml();

  @Key("attributi_custom_select_image")
  String attributi_custom_select_image();

  @Key("attributi_custom_select_strutturato")
  String attributi_custom_select_strutturato(); 

  @Key("attributi_custom_sotto_attributo_di")
  String attributi_custom_sotto_attributo_di(); 

  @Key("attributi_custom_tipo")
  String attributi_custom_tipo(); 

  @Key("attributi_custom_tutto_maiuscolo")
  String attributi_custom_tutto_maiuscolo(); 

  @Key("attributi_custom_tutto_minuscolo")
  String attributi_custom_tutto_minuscolo(); 

  @Key("attributi_custom_valido")
  String attributi_custom_valido(); 

  @Key("attributi_custom_valore_default")
  String attributi_custom_valore_default(); 

  @Key("attributi_custom_valore_obbligatorio")
  String attributi_custom_valore_obbligatorio(); 

  @Key("attributi_custom_valori_unici")
  String attributi_custom_valori_unici(); 

  @Key("attributi_custom_view_title")
  String attributi_custom_view_title(String attribute0);



  //#############################################################
  //#                        CAMBIO PASSWORD                    #
  //#############################################################

  @Key("cambioPasswordWindow_title")
  String cambioPasswordWindow_title(); 

  @Key("cambioPassword_annullaButton_title")
  String cambioPassword_annullaButton_title(); 

  @Key("cambioPassword_confermaButton_title")
  String cambioPassword_confermaButton_title(); 

  @Key("cambioPassword_confermaPasswordItem_title")
  String cambioPassword_confermaPasswordItem_title(); 

  @Key("cambioPassword_esitoValidazione_KO_value")
  String cambioPassword_esitoValidazione_KO_value(); 

  @Key("cambioPassword_esitoValidazione_OK_value")
  String cambioPassword_esitoValidazione_OK_value(); 

  @Key("cambioPassword_newPasswordItem_title")
  String cambioPassword_newPasswordItem_title(); 

  @Key("cambioPassword_oldPasswordItem_title")
  String cambioPassword_oldPasswordItem_title(); 



  //#############################################################
  //#                  CARICAMENTO RUBRICA                      #
  //#############################################################

  @Key("caricamentoRubricaClientiWindow_nomeFile")
  String caricamentoRubricaClientiWindow_nomeFile(); 

  @Key("caricamentoRubricaClientiWindow_section_attributi")
  String caricamentoRubricaClientiWindow_section_attributi(); 
  
  @Key("caricamentoRubricaClientiWindow_tipiDaImportare")
  String caricamentoRubricaClientiWindow_tipiDaImportare();

  @Key("caricamentoRubrica_aggiornaRubrica")
  String caricamentoRubrica_aggiornaRubrica(); 

  @Key("caricamentoRubrica_button_save")
  String caricamentoRubrica_button_save(); 

  @Key("caricamentoRubrica_caricamentoAvvenutoConSuccesso")
  String caricamentoRubrica_caricamentoAvvenutoConSuccesso(); 

  @Key("caricamentoRubrica_caricamentoInCorso")
  String caricamentoRubrica_caricamentoInCorso(); 

  @Key("caricamentoRubrica_companyIdSelect_title")
  String caricamentoRubrica_companyIdSelect_title(); 

  @Key("caricamentoRubrica_mappaturaCampiSection_title")
  String caricamentoRubrica_mappaturaCampiSection_title(); 



  //#############################################################
  //#                        CASELLE EMAIL                      #
  //#############################################################

  @Key("caselleEmail_detail_dominioItem_title")
  String caselleEmail_detail_dominioItem_title(); 

  @Key("caselleEmail_detail_flgAttivaScarico_title")
  String caselleEmail_detail_flgAttivaScarico_title(); 

  @Key("caselleEmail_detail_nuovoIndirizzoEmailItem_title")
  String caselleEmail_detail_nuovoIndirizzoEmailItem_title(); 

  @Key("caselleEmail_edit_title")
  String caselleEmail_edit_title(String attribute0);

  @Key("caselleEmail_list_creaComeCopiaContextMenuItem_title")
  String caselleEmail_list_creaComeCopiaContextMenuItem_title(); 

  @Key("caselleEmail_list_denominazioneField_title")
  String caselleEmail_list_denominazioneField_title(); 

  @Key("caselleEmail_list_denominazioniUoAssociateField_title")
  String caselleEmail_list_denominazioniUoAssociateField_title(); 

  @Key("caselleEmail_list_flgCancCopiaDiScaricoAttivaField_title")
  String caselleEmail_list_flgCancCopiaDiScaricoAttivaField_title(); 

  @Key("caselleEmail_list_flgInvioAttivoField_title")
  String caselleEmail_list_flgInvioAttivoField_title(); 

  @Key("caselleEmail_list_flgRicezioneAttivaField_title")
  String caselleEmail_list_flgRicezioneAttivaField_title(); 

  @Key("caselleEmail_list_idCasellaField_title")
  String caselleEmail_list_idCasellaField_title(); 

  @Key("caselleEmail_list_idNodoScaricoField_title")
  String caselleEmail_list_idNodoScaricoField_title(); 

  @Key("caselleEmail_list_idSpAooField_title")
  String caselleEmail_list_idSpAooField_title(); 

  @Key("caselleEmail_list_indirizzoEmailField_title")
  String caselleEmail_list_indirizzoEmailField_title(); 

  @Key("caselleEmail_list_intervalloScaricoField_title")
  String caselleEmail_list_intervalloScaricoField_title(); 

  @Key("caselleEmail_list_nroGiorniPasswordAttualeField_title")
  String caselleEmail_list_nroGiorniPasswordAttualeField_title(); 

  @Key("caselleEmail_list_nroMaxDestinatariField_title")
  String caselleEmail_list_nroMaxDestinatariField_title(); 

  @Key("caselleEmail_list_nroMaxEmailScaricateField_title")
  String caselleEmail_list_nroMaxEmailScaricateField_title(); 

  @Key("caselleEmail_list_nroMaxTentativiScaricoField_title")
  String caselleEmail_list_nroMaxTentativiScaricoField_title(); 

  @Key("caselleEmail_list_passwordField_title")
  String caselleEmail_list_passwordField_title(); 

  @Key("caselleEmail_list_soggettiAbilitatiContextMenuItem_title")
  String caselleEmail_list_soggettiAbilitatiContextMenuItem_title(); 

  @Key("caselleEmail_list_tipoAccountField_title")
  String caselleEmail_list_tipoAccountField_title(); 

  @Key("caselleEmail_list_tsUltimoAggPasswordField_title")
  String caselleEmail_list_tsUltimoAggPasswordField_title(); 

  @Key("caselleEmail_list_usernameField_title")
  String caselleEmail_list_usernameField_title(); 

  @Key("caselleEmail_list_uteUltimoAggPasswordField_title")
  String caselleEmail_list_uteUltimoAggPasswordField_title(); 

  @Key("caselleEmail_newComeCopia_title")
  String caselleEmail_newComeCopia_title(); 

  @Key("caselleEmail_new_title")
  String caselleEmail_new_title(); 

  @Key("caselleEmail_salvaComeCopia_message")
  String caselleEmail_salvaComeCopia_message(); 

  @Key("caselleEmail_soggettiAbilitatiCasella_window_title")
  String caselleEmail_soggettiAbilitatiCasella_window_title(String attribute0);

  @Key("caselleEmail_soggettiAbilitati_tipoFruitore_ENTE_value")
  String caselleEmail_soggettiAbilitati_tipoFruitore_ENTE_value(); 

  @Key("caselleEmail_soggettiAbilitati_tipoFruitore_UO_value")
  String caselleEmail_soggettiAbilitati_tipoFruitore_UO_value(); 

  @Key("caselleEmail_tipoAccount_C_value")
  String caselleEmail_tipoAccount_C_value(); 

  @Key("caselleEmail_tipoAccount_N_value")
  String caselleEmail_tipoAccount_N_value(); 

  @Key("caselleEmail_tipoAccount_O_value")
  String caselleEmail_tipoAccount_O_value(); 

  @Key("caselleEmail_view_title")
  String caselleEmail_view_title(String attribute0);

  
  @Key("caselleEmail_detail_testInvioButton_prompt")
  String caselleEmail_detail_testInvioButton_prompt();

  @Key("caselleEmail_detail_testScaricoButton_prompt")
  String caselleEmail_detail_testScaricoButton_prompt();

  


  //#############################################################
  //#                        CATEGORIA EMAIL                    #
  //#############################################################	

  @Key("email_categoria_ALTRO_value")
  String email_categoria_ALTRO_value(); 

  @Key("email_categoria_ANOMALIA_value")
  String email_categoria_ANOMALIA_value(); 

  @Key("email_categoria_DELIVERY_STATUS_NOT_value")
  String email_categoria_DELIVERY_STATUS_NOT_value(); 

  @Key("email_categoria_INTEROP_AGG_value")
  String email_categoria_INTEROP_AGG_value(); 

  @Key("email_categoria_INTEROP_ANN_value")
  String email_categoria_INTEROP_ANN_value(); 

  @Key("email_categoria_INTEROP_CONF_value")
  String email_categoria_INTEROP_CONF_value(); 

  @Key("email_categoria_INTEROP_ECC_value")
  String email_categoria_INTEROP_ECC_value(); 

  @Key("email_categoria_INTEROP_SEGN_value")
  String email_categoria_INTEROP_SEGN_value(); 

  @Key("email_categoria_PEC_RIC_ACC_value")
  String email_categoria_PEC_RIC_ACC_value(); 

  @Key("email_categoria_PEC_RIC_CONS_value")
  String email_categoria_PEC_RIC_CONS_value(); 

  @Key("email_categoria_PEC_RIC_NO_ACC_value")
  String email_categoria_PEC_RIC_NO_ACC_value(); 

  @Key("email_categoria_PEC_RIC_NO_CONS_value")
  String email_categoria_PEC_RIC_NO_CONS_value(); 

  @Key("email_categoria_PEC_RIC_PREAS_C_value")
  String email_categoria_PEC_RIC_PREAS_C_value(); 

  @Key("email_categoria_PEC_RIC_PREAVV_NO_CONS_value")
  String email_categoria_PEC_RIC_PREAVV_NO_CONS_value(); 

  @Key("email_categoria_PEC_value")
  String email_categoria_PEC_value(); 

  @Key("email_categoria_PEO_RIC_CONF_value")
  String email_categoria_PEO_RIC_CONF_value(); 


  //#############################################################
  //#              CRONOLOGIA OPERAZIONI EMAIL LIST             #
  //#############################################################

  @Key("cronologia_operazioni_effettuate_dettemail_del")
  String cronologia_operazioni_effettuate_dettemail_del(); 

  @Key("cronologia_operazioni_effettuate_dettemail_effettuatoda")
  String cronologia_operazioni_effettuate_dettemail_effettuatoda(); 

  @Key("cronologia_operazioni_effettuate_dettemail_anomedi")
  String cronologia_operazioni_effettuate_dettemail_anomedi(); 

  @Key("cronologia_operazioni_effettuate_dettemail_tipo")
  String cronologia_operazioni_effettuate_dettemail_tipo(); 

  @Key("cronologia_operazioni_effettuate_dettemail_dettagli")
  String cronologia_operazioni_effettuate_dettemail_dettagli(); 



  //#############################################################
  //#              EMAIL NOTIFICA RICHESTA CONSENSI             #
  //#############################################################

  @Key("richiesteConsensi_mittente_email_value")
  String richiesteConsensi_mittente_email_value(); 

  @Key("richiesteConsensi_oggetto_email_value")
  String richiesteConsensi_oggetto_email_value(String attribute0);

  @Key("richiesteConsensi_body_email_value")
  String richiesteConsensi_body_email_value(String attribute0, String attribute1, String attribute2);

  @Key("richiesteConsensi_oggetto_email_anag_check_ko_value")
  String richiesteConsensi_oggetto_email_anag_check_ko_value(String attribute0);

  @Key("richiesteConsensi_body_email_anag_check_ko_value")
  String richiesteConsensi_body_email_anag_check_ko_value(String attribute0, String attribute1, String attribute2, String attribute3, String attribute4, String attribute5);

  @Key("richiesteConsensi_oggetto_email_anag_dest_ko_value")
  String richiesteConsensi_oggetto_email_anag_dest_ko_value(String attribute0);

  @Key("richiesteConsensi_body_email_anag_dest_ko_value")
  String richiesteConsensi_body_email_anag_dest_ko_value(String attribute0, String attribute1, String attribute2, String attribute3, String attribute4, String attribute5);

  @Key("richiesteConsensi_oggetto_email_ok_value")
  String richiesteConsensi_oggetto_email_ok_value(String attribute0, String attribute1);

  @Key("richiesteConsensi_body_email_ok_value")
  String richiesteConsensi_body_email_ok_value(String attribute0, String attribute1, String attribute2, String attribute3, String attribute4);



  //#############################################################
  //#                         RUBRICA EMAIL                     #
  //#############################################################

  @Key("rubricaemail_annullamentoLogicoAsk_message")
  String rubricaemail_annullamentoLogicoAsk_message(); 

  @Key("rubricaemail_detail_codAmministrazioneItem_title")
  String rubricaemail_detail_codAmministrazioneItem_title(); 

  @Key("rubricaemail_detail_codAooItem_title")
  String rubricaemail_detail_codAooItem_title(); 

  @Key("rubricaemail_detail_edit_title")
  String rubricaemail_detail_edit_title(String attribute0);

  @Key("rubricaemail_detail_flgPECverificataItem_title")
  String rubricaemail_detail_flgPECverificataItem_title(); 

  @Key("rubricaemail_detail_flgPresenteInIPAItem_title")
  String rubricaemail_detail_flgPresenteInIPAItem_title(); 

  @Key("rubricaemail_detail_flgValidoItem_title")
  String rubricaemail_detail_flgValidoItem_title(); 

  @Key("rubricaemail_detail_idFruitoreCasellaItem_title")
  String rubricaemail_detail_idFruitoreCasellaItem_title(); 

  @Key("rubricaemail_detail_idProvSoggRifItem_title")
  String rubricaemail_detail_idProvSoggRifItem_title(); 

  @Key("rubricaemail_detail_indirizzoEmailItem_title")
  String rubricaemail_detail_indirizzoEmailItem_title(); 

  @Key("rubricaemail_detail_newContatto_title")
  String rubricaemail_detail_newContatto_title(); 

  @Key("rubricaemail_detail_newGruppo_title")
  String rubricaemail_detail_newGruppo_title(); 

  @Key("rubricaemail_detail_nomeItem_title")
  String rubricaemail_detail_nomeItem_title(); 

  @Key("rubricaemail_detail_tipoAccountItem_title")
  String rubricaemail_detail_tipoAccountItem_title(); 

  @Key("rubricaemail_detail_tipoSoggettoRifItem_title")
  String rubricaemail_detail_tipoSoggettoRifItem_title(); 

  @Key("rubricaemail_detail_view_title")
  String rubricaemail_detail_view_title(String attribute0);

  @Key("rubricaemail_eliminazioneFisicaAsk_message")
  String rubricaemail_eliminazioneFisicaAsk_message(); 

  @Key("rubricaemail_flgPECverificata_1_Alt_value")
  String rubricaemail_flgPECverificata_1_Alt_value(); 

  @Key("rubricaemail_flgPEOverificata_1_Alt_value")
  String rubricaemail_flgPEOverificata_1_Alt_value(); 

  @Key("rubricaemail_flgPresenteInIPA_1_Alt_value")
  String rubricaemail_flgPresenteInIPA_1_Alt_value(); 

  @Key("rubricaemail_flgValido_0_value")
  String rubricaemail_flgValido_0_value(); 

  @Key("rubricaemail_flgValido_1_value")
  String rubricaemail_flgValido_1_value(); 


  @Key("rubricaemail_list_codAmministrazioneField_title")
  String rubricaemail_list_codAmministrazioneField_title(); 

  @Key("rubricaemail_list_codAooField_title")
  String rubricaemail_list_codAooField_title(); 

  @Key("rubricaemail_list_codiceIPAField_title")
  String rubricaemail_list_codiceIPAField_title(); 

  @Key("rubricaemail_list_dataInsField_title")
  String rubricaemail_list_dataInsField_title(); 

  @Key("rubricaemail_list_dataUltModField_title")
  String rubricaemail_list_dataUltModField_title(); 

  @Key("rubricaemail_list_flgPECverificataField_title")
  String rubricaemail_list_flgPECverificataField_title(); 

  @Key("rubricaemail_list_flgPresenteInIPAField_title")
  String rubricaemail_list_flgPresenteInIPAField_title(); 

  @Key("rubricaemail_list_flgValidoField_title")
  String rubricaemail_list_flgValidoField_title(); 

  @Key("rubricaemail_list_idField_title")
  String rubricaemail_list_idField_title(); 

  @Key("rubricaemail_list_idFruitoreCasellaField_title")
  String rubricaemail_list_idFruitoreCasellaField_title(); 

  @Key("rubricaemail_list_idProvSoggRifField_title")
  String rubricaemail_list_idProvSoggRifField_title(); 

  @Key("rubricaemail_list_indirizzoEmailField_title")
  String rubricaemail_list_indirizzoEmailField_title(); 

  @Key("rubricaemail_list_nomeField_title")
  String rubricaemail_list_nomeField_title(); 

  @Key("rubricaemail_list_tipoAccountField_title")
  String rubricaemail_list_tipoAccountField_title(); 

  @Key("rubricaemail_list_tipoIndirizzoField_title")
  String rubricaemail_list_tipoIndirizzoField_title(); 

  @Key("rubricaemail_list_tipoSoggettoRifField_title")
  String rubricaemail_list_tipoSoggettoRifField_title(); 

  @Key("rubricaemail_list_utenteInsField_title")
  String rubricaemail_list_utenteInsField_title(); 

  @Key("rubricaemail_list_utenteUltModField_title")
  String rubricaemail_list_utenteUltModField_title(); 
  
  @Key("rubricaemail_list_sigilloField_title")
  String rubricaemail_list_sigilloField_title();

  @Key("rubricaemail_lookupRubricaEmailPopup_title")
  String rubricaemail_lookupRubricaEmailPopup_title(); 

  @Key("rubricaemail_multilookupRubricaButton_prompt")
  String rubricaemail_multilookupRubricaButton_prompt(); 

  @Key("rubricaemail_recProtetto_0_value")
  String rubricaemail_recProtetto_0_value(); 

  @Key("rubricaemail_recProtetto_1_value")
  String rubricaemail_recProtetto_1_value(); 

  @Key("rubricaemail_tipoAccount_C_Alt_value")
  String rubricaemail_tipoAccount_C_Alt_value(); 

  @Key("rubricaemail_tipoAccount_C_value")
  String rubricaemail_tipoAccount_C_value(); 

  @Key("rubricaemail_tipoAccount_O_Alt_value")
  String rubricaemail_tipoAccount_O_Alt_value(); 

  @Key("rubricaemail_tipoAccount_O_value")
  String rubricaemail_tipoAccount_O_value(); 

  @Key("rubricaemail_tipoIndirizzo_G_Alt_value")
  String rubricaemail_tipoIndirizzo_G_Alt_value(); 

  @Key("rubricaemail_tipoIndirizzo_S_Alt_value")
  String rubricaemail_tipoIndirizzo_S_Alt_value(); 

  @Key("rubricaemail_tipoSoggettoRif_AOOE_Alt_value")
  String rubricaemail_tipoSoggettoRif_AOOE_Alt_value(); 

  @Key("rubricaemail_tipoSoggettoRif_AOOI_Alt_value")
  String rubricaemail_tipoSoggettoRif_AOOI_Alt_value(); 

  @Key("rubricaemail_tipoSoggettoRif_A_Alt_value")
  String rubricaemail_tipoSoggettoRif_A_Alt_value(); 

  @Key("rubricaemail_tipoSoggettoRif_C_Alt_value")
  String rubricaemail_tipoSoggettoRif_C_Alt_value(); 

  @Key("rubricaemail_tipoSoggettoRif_UO_Alt_value")
  String rubricaemail_tipoSoggettoRif_UO_Alt_value(); 

  @Key("rubricaemail_tipoSoggettoRif_UT_Alt_value")
  String rubricaemail_tipoSoggettoRif_UT_Alt_value(); 



  //#############################################################
  //#                 STATI CONSOLIDAMENTO EMAIL                #
  //#############################################################	

  @Key("email_stato_ACCETTATA_value")
  String email_stato_ACCETTATA_value(); 

  @Key("email_stato_AVVERTIMENTI_IN_CONSEGNA_value")
  String email_stato_AVVERTIMENTI_IN_CONSEGNA_value(); 

  @Key("email_stato_CONSEGNATA_PARZIALMENTE_value")
  String email_stato_CONSEGNATA_PARZIALMENTE_value(); 

  @Key("email_stato_CONSEGNATA_value")
  String email_stato_CONSEGNATA_value(); 

  @Key("email_stato_ERRORI_IN_CONSEGNA_value")
  String email_stato_ERRORI_IN_CONSEGNA_value(); 

  @Key("email_stato_NON_ACCETTATA_value")
  String email_stato_NON_ACCETTATA_value(); 



  //#############################################################
  //#                        INVIO MAIL                         #
  //#############################################################	

  @Key("inviomailform_flgInvioSeparato_errormessages")
  String inviomailform_flgInvioSeparato_errormessages(); 

  @Key("inviomailform_flgInvioSeparato_title")
  String inviomailform_flgInvioSeparato_title(); 

  @Key("inviomailformnew_confermalettura_title")
  String inviomailformnew_confermalettura_title(); 


  //#invioUDmail
  @Key("invioudmail_detail_destCCItem_title")
  String invioudmail_detail_destCCItem_title(); 

  @Key("invioudmail_detail_destPrimarioItem_title")
  String invioudmail_detail_destPrimarioItem_title(); 

  @Key("invioudmail_detail_indirizzoMailItem_title")
  String invioudmail_detail_indirizzoMailItem_title(); 

  @Key("invioudmail_detail_intestazioneItem_title")
  String invioudmail_detail_intestazioneItem_title(); 

  @Key("invioudmail_detail_lCheckboxConfermaLettura_title")
  String invioudmail_detail_lCheckboxConfermaLettura_title(); 

  @Key("invioudmail_detail_lCheckboxItemSalvaInviati_title")
  String invioudmail_detail_lCheckboxItemSalvaInviati_title(); 

  @Key("invioudmail_detail_lCheckboxRichiestaConferma_title")
  String invioudmail_detail_lCheckboxRichiestaConferma_title(); 

  @Key("invioudmail_detail_lInvioUDDestinatariItem_title")
  String invioudmail_detail_lInvioUDDestinatariItem_title(); 

  @Key("invioudmail_detail_lSelectItemMittente_title")
  String invioudmail_detail_lSelectItemMittente_title(); 

  @Key("invioudmail_detail_lTextItemDestinatariCCN_title")
  String invioudmail_detail_lTextItemDestinatariCCN_title(); 

  @Key("invioudmail_detail_lTextItemDestinatariCC_title")
  String invioudmail_detail_lTextItemDestinatariCC_title(); 

  @Key("invioudmail_detail_lTextItemDestinatari_title")
  String invioudmail_detail_lTextItemDestinatari_title(); 

  @Key("invioudmail_detail_lTextItemOggetto_title")
  String invioudmail_detail_lTextItemOggetto_title(); 

  @Key("invioudmail_detail_lookupRubricaEmailItem_title")
  String invioudmail_detail_lookupRubricaEmailItem_title(); 

  @Key("invioudmail_detail_mailinterop_title")
  String invioudmail_detail_mailinterop_title(); 
  
  @Key("invioudmail_detail_mailricevuta_title")
  String invioudmail_detail_mailricevuta_title(); 
  
  @Key("invioudmail_detail_mailricevuta_Windowtitle")
  String invioudmail_detail_mailricevuta_Windowtitle(); 

  @Key("invioudmail_detail_mailordinaria_title")
  String invioudmail_detail_mailordinaria_title(); 

  @Key("invioudmail_detail_mailpec_title")
  String invioudmail_detail_mailpec_title(); 

  @Key("invioudmail_detail_mailpeo_title")
  String invioudmail_detail_mailpeo_title(); 

  @Key("invioudmail_detail_multilookupRubricaEmailItem_title")
  String invioudmail_detail_multilookupRubricaEmailItem_title(); 

  @Key("invioudmail_detail_salvaMittenteDefault_title")
  String invioudmail_detail_salvaMittenteDefault_title(); 

  @Key("invioudmail_detail_tipoDestinatarioGruppoItem_title")
  String invioudmail_detail_tipoDestinatarioGruppoItem_title(); 

  @Key("invioudmail_detail_tipoDestinatarioSingoloItem_title")
  String invioudmail_detail_tipoDestinatarioSingoloItem_title(); 

  @Key("invioudmail_pec_Alt_value")
  String invioudmail_pec_Alt_value(); 


  //#invioUDmailPopup
  @Key("invio_mail_popup_chiudiMailButton_title")
  String invio_mail_popup_chiudiMailButton_title(); 

  @Key("invio_mail_popup_chiudiMailEAnnullaAzioneButton_title")
  String invio_mail_popup_chiudiMailEAnnullaAzioneButton_title(); 

  @Key("invio_mail_popup_chiudiMailECompletaAzioneButton_title")
  String invio_mail_popup_chiudiMailECompletaAzioneButton_title(); 

  @Key("invio_mail_popup_form_title")
  String invio_mail_popup_form_title(); 

  @Key("invio_mail_popup_messaggio_inoltro_multiplo")
  String invio_mail_popup_messaggio_inoltro_multiplo(String attribute0);

  @Key("invio_mail_popup_messaggio_inoltro_multiplo_azione_multipla")
  String invio_mail_popup_messaggio_inoltro_multiplo_azione_multipla(String attribute0);

  @Key("invio_mail_popup_messaggio_inoltro_multiplo_azione_singola")
  String invio_mail_popup_messaggio_inoltro_multiplo_azione_singola(String attribute0, String attribute1);

  @Key("invio_mail_popup_messaggio_inoltro_singolo")
  String invio_mail_popup_messaggio_inoltro_singolo(String attribute0);

  @Key("invio_mail_popup_messaggio_inoltro_singolo_azione_singola")
  String invio_mail_popup_messaggio_inoltro_singolo_azione_singola(String attribute0, String attribute1);

  @Key("invio_mail_popup_messaggio_rispondi")
  String invio_mail_popup_messaggio_rispondi(String attribute0);

  @Key("invio_mail_popup_messaggio_rispondi_azione_singola")
  String invio_mail_popup_messaggio_rispondi_azione_singola(String attribute0, String attribute1);

  @Key("invio_mail_popup_nonChiudereMailButton_title")
  String invio_mail_popup_nonChiudereMailButton_title(); 



  //#############################################################
  //#            CRUSCOTTO GESTIONE EMAIL                       #
  //#############################################################

  //# OperazioniPerEmailPopup
  @Key("messa_incaricoemailpopup_title_presaInCarico")
  String messa_incaricoemailpopup_title_presaInCarico(); 

  @Key("messa_incaricoemailpopup_title_messaInCarico")
  String messa_incaricoemailpopup_title_messaInCarico(); 

  @Key("messa_incaricoemailpopup_title_mandaInApprovazione")
  String messa_incaricoemailpopup_title_mandaInApprovazione(); 

  @Key("messa_incaricoemailpopup_title_rilascio")
  String messa_incaricoemailpopup_title_rilascio(); 

  @Key("messa_incaricoemailpopup_utenti_title_mandaInApprovazione")
  String messa_incaricoemailpopup_utenti_title_mandaInApprovazione(); 

  @Key("messa_incaricoemailpopup_utenti_title_mettiInCarico")
  String messa_incaricoemailpopup_utenti_title_mettiInCarico(); 

  @Key("metti_in_carico_label_button")
  String metti_in_carico_label_button(); 



  //#############################################################
  //#                        CLIENTI                            #
  //#############################################################

  @Key("clienti_annullamentoLogicoAsk_message")
  String clienti_annullamentoLogicoAsk_message(); 

  @Key("clienti_codTipoSoggInt_AOOI_value")
  String clienti_codTipoSoggInt_AOOI_value(); 

  @Key("clienti_codTipoSoggInt_UOI_value")
  String clienti_codTipoSoggInt_UOI_value(); 

  @Key("clienti_codTipoSoggInt_UP_value")
  String clienti_codTipoSoggInt_UP_value(); 

  @Key("clienti_contatti_tipo_E_value")
  String clienti_contatti_tipo_E_value(); 

  @Key("clienti_contatti_tipo_F_value")
  String clienti_contatti_tipo_F_value(); 

  @Key("clienti_contatti_tipo_T_value")
  String clienti_contatti_tipo_T_value(); 

  @Key("clienti_descEsigibilitaIva_D_value")
  String clienti_descEsigibilitaIva_D_value(); 

  @Key("clienti_descEsigibilitaIva_I_value")
  String clienti_descEsigibilitaIva_I_value(); 

  @Key("clienti_descEsigibilitaIva_S_value")
  String clienti_descEsigibilitaIva_S_value(); 

  @Key("clienti_eliminazioneFisicaAsk_message")
  String clienti_eliminazioneFisicaAsk_message(); 

  @Key("clienti_esitoValidazione_KO_value")
  String clienti_esitoValidazione_KO_value(); 

  @Key("clienti_esitoValidazione_OK_value")
  String clienti_esitoValidazione_OK_value(); 

  @Key("clienti_flgAnn_1_value")
  String clienti_flgAnn_1_value(); 

  @Key("clienti_flgCertificato_1_value")
  String clienti_flgCertificato_1_value(); 

  @Key("clienti_flgDiSistema_1_value")
  String clienti_flgDiSistema_1_value(); 

  @Key("clienti_flgEmailPecPeo_PEC_value")
  String clienti_flgEmailPecPeo_PEC_value(); 

  @Key("clienti_flgEmailPecPeo_PEO_value")
  String clienti_flgEmailPecPeo_PEO_value(); 

  @Key("clienti_flgPersFisica_0_value")
  String clienti_flgPersFisica_0_value(); 

  @Key("clienti_flgPersFisica_1_value")
  String clienti_flgPersFisica_1_value(); 

  @Key("clienti_flgPersFisica_NULL_value")
  String clienti_flgPersFisica_NULL_value(); 

  @Key("clienti_flgSegnoImporti_1_value")
  String clienti_flgSegnoImporti_1_value(); 

  @Key("clienti_flgValido_1_value")
  String clienti_flgValido_1_value(); 

  @Key("clienti_lookupSoggettiPopup_title")
  String clienti_lookupSoggettiPopup_title(); 

  @Key("clienti_sesso_F_value")
  String clienti_sesso_F_value(); 

  @Key("clienti_sesso_M_value")
  String clienti_sesso_M_value(); 

  @Key("clienti_tipoIndirizzo_D_value")
  String clienti_tipoIndirizzo_D_value(); 

  @Key("clienti_tipoIndirizzo_RC_value")
  String clienti_tipoIndirizzo_RC_value(); 

  @Key("clienti_tipoIndirizzo_RS_value")
  String clienti_tipoIndirizzo_RS_value(); 

  @Key("clienti_tipoIndirizzo_SL_value")
  String clienti_tipoIndirizzo_SL_value(); 

  @Key("clienti_tipoIndirizzo_SO_value")
  String clienti_tipoIndirizzo_SO_value(); 

  @Key("clienti_tipo_AF_value")
  String clienti_tipo_AF_value(); 

  @Key("clienti_tipo_AG_value")
  String clienti_tipo_AG_value(); 

  @Key("clienti_tipo_APA_value")
  String clienti_tipo_APA_value(); 

  @Key("clienti_tipo_IAMM_value")
  String clienti_tipo_IAMM_value(); 

  @Key("clienti_tipo_PV_value")
  String clienti_tipo_PV_value(); 

  @Key("clienti_tipo_UOUOI_value")
  String clienti_tipo_UOUOI_value(); 

  @Key("clienti_tipo_UP_value")
  String clienti_tipo_UP_value(); 

  //#ClientiDetail
  @Key("clienti_detail_abiItem_title")
  String clienti_detail_abiItem_title(); 

  @Key("clienti_detail_altreDenominazioniSection_title")
  String clienti_detail_altreDenominazioniSection_title(); 

  @Key("clienti_detail_altreDenominazioni_tipoItem_title")
  String clienti_detail_altreDenominazioni_tipoItem_title(); 

  @Key("clienti_detail_annoPosizioneFinanziariaItem_title")
  String clienti_detail_annoPosizioneFinanziariaItem_title(); 

  @Key("clienti_detail_beneficiarioItem_title")
  String clienti_detail_beneficiarioItem_title(); 

  @Key("clienti_detail_billingAccountItem_title")
  String clienti_detail_billingAccountItem_title(); 

  @Key("clienti_detail_cabItem_title")
  String clienti_detail_cabItem_title(); 

  @Key("clienti_detail_causaleCessazioneItem_title")
  String clienti_detail_causaleCessazioneItem_title(); 

  @Key("clienti_detail_cessazioneSection_title")
  String clienti_detail_cessazioneSection_title(); 

  @Key("clienti_detail_cidItem_title")
  String clienti_detail_cidItem_title(); 

  @Key("clienti_detail_cittaNascitaIstituzioneItem_title")
  String clienti_detail_cittaNascitaIstituzioneItem_title(); 

  @Key("clienti_detail_cittadinanzaItem_title")
  String clienti_detail_cittadinanzaItem_title(); 

  @Key("clienti_detail_codIndPaItem_title")
  String clienti_detail_codIndPaItem_title(); 

  @Key("clienti_detail_codReItem_title")
  String clienti_detail_codReItem_title(); 

  @Key("clienti_detail_codiceAmmInIpaItem_title")
  String clienti_detail_codiceAmmInIpaItem_title(); 

  @Key("clienti_detail_codiceAooInIpaItem_title")
  String clienti_detail_codiceAooInIpaItem_title(); 

  @Key("clienti_detail_codiceFiscaleItem_title")
  String clienti_detail_codiceFiscaleItem_title(); 

  @Key("clienti_detail_codiceIpaItem_title")
  String clienti_detail_codiceIpaItem_title(); 

  @Key("clienti_detail_codiceRapidoItem_title")
  String clienti_detail_codiceRapidoItem_title(); 

  @Key("clienti_detail_codiceUoInIpaItem_title")
  String clienti_detail_codiceUoInIpaItem_title(); 

  @Key("clienti_detail_cognomeItem_title")
  String clienti_detail_cognomeItem_title(); 

  @Key("clienti_detail_comuneNascitaIstituzioneItem_title")
  String clienti_detail_comuneNascitaIstituzioneItem_title(); 

  @Key("clienti_detail_condizioneGiuridicaItem_title")
  String clienti_detail_condizioneGiuridicaItem_title(); 

  @Key("clienti_detail_contattiSection_title")
  String clienti_detail_contattiSection_title(); 

  @Key("clienti_detail_contatti_flgCasellaIstituzItem_title")
  String clienti_detail_contatti_flgCasellaIstituzItem_title(); 

  @Key("clienti_detail_contatti_flgDichIpaItem_title")
  String clienti_detail_contatti_flgDichIpaItem_title(); 

  @Key("clienti_detail_contatti_flgPecItem_title")
  String clienti_detail_contatti_flgPecItem_title(); 

  @Key("clienti_detail_contatti_tipoTelItem_title")
  String clienti_detail_contatti_tipoTelItem_title(); 

  @Key("clienti_detail_dataCessazioneItem_title")
  String clienti_detail_dataCessazioneItem_title(); 

  @Key("clienti_detail_dataNascitaIstituzioneItem_title")
  String clienti_detail_dataNascitaIstituzioneItem_title(); 

  @Key("clienti_detail_datiPagamentoSection_title")
  String clienti_detail_datiPagamentoSection_title(); 

  @Key("clienti_detail_denominazioneItem_title")
  String clienti_detail_denominazioneItem_title(); 

  @Key("clienti_detail_edit_title")
  String clienti_detail_edit_title(String attribute0);

  @Key("clienti_detail_esigibilitaIvaItem_title")
  String clienti_detail_esigibilitaIvaItem_title(); 

  @Key("clienti_detail_estremiSuIpaSection_title")
  String clienti_detail_estremiSuIpaSection_title(); 

  @Key("clienti_detail_flgPersFisicaItem_title")
  String clienti_detail_flgPersFisicaItem_title(); 

  @Key("clienti_detail_flgSegnoImportiItem_title")
  String clienti_detail_flgSegnoImportiItem_title(); 

  @Key("clienti_detail_flgTipoIdFiscaleItem_title")
  String clienti_detail_flgTipoIdFiscaleItem_title(); 

  @Key("clienti_detail_flgUnitaDiPersonaleItem_title")
  String clienti_detail_flgUnitaDiPersonaleItem_title(); 

  @Key("clienti_detail_gruppoDiRiferimentoItem_title")
  String clienti_detail_gruppoDiRiferimentoItem_title(); 

  @Key("clienti_detail_gruppoRiferimentoItem_title")
  String clienti_detail_gruppoRiferimentoItem_title(); 

  @Key("clienti_detail_ibanItem_title")
  String clienti_detail_ibanItem_title(); 

  @Key("clienti_detail_indirizziSection_title")
  String clienti_detail_indirizziSection_title(); 

  @Key("clienti_detail_indirizzi_capItem_title")
  String clienti_detail_indirizzi_capItem_title(); 

  @Key("clienti_detail_indirizzi_cittaItem_title")
  String clienti_detail_indirizzi_cittaItem_title(); 

  @Key("clienti_detail_indirizzi_civicoItem_title")
  String clienti_detail_indirizzi_civicoItem_title(); 

  @Key("clienti_detail_indirizzi_complementoIndirizzoItem_title")
  String clienti_detail_indirizzi_complementoIndirizzoItem_title(); 

  @Key("clienti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage")
  String clienti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage(); 

  @Key("clienti_detail_indirizzi_comuneItem_title")
  String clienti_detail_indirizzi_comuneItem_title(); 

  @Key("clienti_detail_indirizzi_dataValidoDalItem_title")
  String clienti_detail_indirizzi_dataValidoDalItem_title(); 

  @Key("clienti_detail_indirizzi_dataValidoFinoAlItem_title")
  String clienti_detail_indirizzi_dataValidoFinoAlItem_title(); 

  @Key("clienti_detail_indirizzi_frazioneItem_title")
  String clienti_detail_indirizzi_frazioneItem_title(); 

  @Key("clienti_detail_indirizzi_indirizzoItem_title")
  String clienti_detail_indirizzi_indirizzoItem_title(); 

  @Key("clienti_detail_indirizzi_provinciaItem_title")
  String clienti_detail_indirizzi_provinciaItem_title(); 

  @Key("clienti_detail_indirizzi_statoItem_title")
  String clienti_detail_indirizzi_statoItem_title(); 

  @Key("clienti_detail_indirizzi_zonaItem_title")
  String clienti_detail_indirizzi_zonaItem_title(); 

  @Key("clienti_detail_ipaItem_title")
  String clienti_detail_ipaItem_title(); 

  @Key("clienti_detail_istitutoFinanziarioItem_title")
  String clienti_detail_istitutoFinanziarioItem_title(); 

  @Key("clienti_detail_istituzioneSection_title")
  String clienti_detail_istituzioneSection_title(); 

  @Key("clienti_detail_nascitaSection_title")
  String clienti_detail_nascitaSection_title(); 

  @Key("clienti_detail_new_title")
  String clienti_detail_new_title(); 

  @Key("clienti_detail_nomeItem_title")
  String clienti_detail_nomeItem_title(); 

  @Key("clienti_detail_odaCigItem_title")
  String clienti_detail_odaCigItem_title(); 

  @Key("clienti_detail_odaCupItem_title")
  String clienti_detail_odaCupItem_title(); 

  @Key("clienti_detail_odaNrContrattoItem_title")
  String clienti_detail_odaNrContrattoItem_title(); 

  @Key("clienti_detail_odaRifAmmInpsItem_title")
  String clienti_detail_odaRifAmmInpsItem_title(); 

  @Key("clienti_detail_partitaIvaItem_title")
  String clienti_detail_partitaIvaItem_title(); 

  @Key("clienti_detail_posizioneFinanziariaItem_title")
  String clienti_detail_posizioneFinanziariaItem_title(); 

  @Key("clienti_detail_provNascitaIstituzioneItem_title")
  String clienti_detail_provNascitaIstituzioneItem_title(); 

  @Key("clienti_detail_rendReItem_title")
  String clienti_detail_rendReItem_title(); 

  @Key("clienti_detail_rifAmministrativoItem_title")
  String clienti_detail_rifAmministrativoItem_title(); 

  @Key("clienti_detail_sessoItem_title")
  String clienti_detail_sessoItem_title(); 

  @Key("clienti_detail_societaItem_title")
  String clienti_detail_societaItem_title(); 

  @Key("clienti_detail_soggettiSection_title")
  String clienti_detail_soggettiSection_title(); 

  @Key("clienti_detail_sottotipoItem_title")
  String clienti_detail_sottotipoItem_title(); 

  @Key("clienti_detail_statoNascitaIstituzioneItem_title")
  String clienti_detail_statoNascitaIstituzioneItem_title(); 

  @Key("clienti_detail_tipoItem_title")
  String clienti_detail_tipoItem_title(); 

  @Key("clienti_detail_tipologiaItem_title")
  String clienti_detail_tipologiaItem_title(); 

  @Key("clienti_detail_titoloItem_title")
  String clienti_detail_titoloItem_title(); 

  @Key("clienti_detail_emailPecItem_title")
  String clienti_detail_emailPecItem_title(); 
  
  @Key("clienti_detail_view_title")
  String clienti_detail_view_title(String attribute0);


  //# ClientiList
  @Key("clienti_list_abiField_title")
  String clienti_list_abiField_title(); 

  @Key("clienti_list_acronimoField_title")
  String clienti_list_acronimoField_title(); 

  @Key("clienti_list_altreDenominazioniField_title")
  String clienti_list_altreDenominazioniField_title(); 

  @Key("clienti_list_annoPosizioneFinanziariaField_title")
  String clienti_list_annoPosizioneFinanziariaField_title(); 

  @Key("clienti_list_beneficiarioField_title")
  String clienti_list_beneficiarioField_title(); 

  @Key("clienti_list_billingAccountField_title")
  String clienti_list_billingAccountField_title(); 

  @Key("clienti_list_cabField_title")
  String clienti_list_cabField_title(); 

  @Key("clienti_list_causaleCessazioneField_title")
  String clienti_list_causaleCessazioneField_title(); 

  @Key("clienti_list_cidField_title")
  String clienti_list_cidField_title(); 

  @Key("clienti_list_cittadinanzaField_title")
  String clienti_list_cittadinanzaField_title(); 

  @Key("clienti_list_codIndPaField_title")
  String clienti_list_codIndPaField_title(); 

  @Key("clienti_list_codReField_title")
  String clienti_list_codReField_title(); 

  @Key("clienti_list_codTipoSoggIntField_title")
  String clienti_list_codTipoSoggIntField_title(); 

  @Key("clienti_list_codiceAmmInIpaField_title")
  String clienti_list_codiceAmmInIpaField_title(); 

  @Key("clienti_list_codiceAooInIpaField_title")
  String clienti_list_codiceAooInIpaField_title(); 

  @Key("clienti_list_codiceFiscaleField_title")
  String clienti_list_codiceFiscaleField_title(); 

  @Key("clienti_list_codiceIpaField_title")
  String clienti_list_codiceIpaField_title(); 

  @Key("clienti_list_codiceOrigineField_title")
  String clienti_list_codiceOrigineField_title(); 

  @Key("clienti_list_codiceRapidoField_title")
  String clienti_list_codiceRapidoField_title(); 

  @Key("clienti_list_cognomeField_title")
  String clienti_list_cognomeField_title(); 

  @Key("clienti_list_comuneNascitaIstituzioneField_title")
  String clienti_list_comuneNascitaIstituzioneField_title(); 

  @Key("clienti_list_condizioneGiuridicaField_title")
  String clienti_list_condizioneGiuridicaField_title(); 

  @Key("clienti_list_dataCessazioneField_title")
  String clienti_list_dataCessazioneField_title(); 

  @Key("clienti_list_dataNascitaIstituzioneField_title")
  String clienti_list_dataNascitaIstituzioneField_title(); 

  @Key("clienti_list_denominazioneField_title")
  String clienti_list_denominazioneField_title(); 

  @Key("clienti_list_descFlgTipoIdFiscale_0_value")
  String clienti_list_descFlgTipoIdFiscale_0_value(); 

  @Key("clienti_list_descFlgTipoIdFiscale_1_value")
  String clienti_list_descFlgTipoIdFiscale_1_value(); 

  @Key("clienti_list_emailField_title")
  String clienti_list_emailField_title(); 

  @Key("clienti_list_emailPecField_title")
  String clienti_list_emailPecField_title(); 

  @Key("clienti_list_esigibilitaIvaField_title")
  String clienti_list_esigibilitaIvaField_title(); 

  @Key("clienti_list_estremiCertificazioneField_title")
  String clienti_list_estremiCertificazioneField_title(); 

  @Key("clienti_list_faxField_title")
  String clienti_list_faxField_title(); 

  @Key("clienti_list_flgAnnField_title")
  String clienti_list_flgAnnField_title(); 

  @Key("clienti_list_flgCertificatoField_title")
  String clienti_list_flgCertificatoField_title(); 

  @Key("clienti_list_flgDiSistemaField_title")
  String clienti_list_flgDiSistemaField_title(); 

  @Key("clienti_list_flgEmailPecPeoField_title")
  String clienti_list_flgEmailPecPeoField_title(); 

  @Key("clienti_list_flgInOrganigrammaField_title")
  String clienti_list_flgInOrganigrammaField_title(); 

  @Key("clienti_list_flgPersFisicaField_title")
  String clienti_list_flgPersFisicaField_title(); 

  @Key("clienti_list_flgSegnoImportiField_title")
  String clienti_list_flgSegnoImportiField_title(); 

  @Key("clienti_list_flgTipoIdFiscaleField_title")
  String clienti_list_flgTipoIdFiscaleField_title(); 

  @Key("clienti_list_flgValidoField_title")
  String clienti_list_flgValidoField_title(); 

  @Key("clienti_list_gruppoDiRiferimentoField_title")
  String clienti_list_gruppoDiRiferimentoField_title(); 

  @Key("clienti_list_ibanField_title")
  String clienti_list_ibanField_title(); 

  @Key("clienti_list_idClienteField_title")
  String clienti_list_idClienteField_title(); 

  @Key("clienti_list_idSoggettoField_title")
  String clienti_list_idSoggettoField_title(); 

  @Key("clienti_list_indirizzoField_title")
  String clienti_list_indirizzoField_title(); 

  @Key("clienti_list_ipaField_title")
  String clienti_list_ipaField_title(); 

  @Key("clienti_list_istitutoFinanziarioField_title")
  String clienti_list_istitutoFinanziarioField_title(); 

  @Key("clienti_list_nomeField_title")
  String clienti_list_nomeField_title(); 

  @Key("clienti_list_odaCigField_title")
  String clienti_list_odaCigField_title(); 

  @Key("clienti_list_odaCupField_title")
  String clienti_list_odaCupField_title(); 

  @Key("clienti_list_odaNrContrattoField_title")
  String clienti_list_odaNrContrattoField_title(); 

  @Key("clienti_list_odaRifAmmInpsField_title")
  String clienti_list_odaRifAmmInpsField_title(); 

  @Key("clienti_list_partitaIvaField_title")
  String clienti_list_partitaIvaField_title(); 

  @Key("clienti_list_posizioneFinanziariaField_title")
  String clienti_list_posizioneFinanziariaField_title(); 

  @Key("clienti_list_rendReField_title")
  String clienti_list_rendReField_title(); 

  @Key("clienti_list_scoreField_title")
  String clienti_list_scoreField_title(); 

  @Key("clienti_list_societaField_title")
  String clienti_list_societaField_title(); 

  @Key("clienti_list_sottotipoField_title")
  String clienti_list_sottotipoField_title(); 

  @Key("clienti_list_statoNascitaIstituzioneField_title")
  String clienti_list_statoNascitaIstituzioneField_title(); 

  @Key("clienti_list_telField_title")
  String clienti_list_telField_title(); 

  @Key("clienti_list_tipoField_title")
  String clienti_list_tipoField_title(); 

  @Key("clienti_list_titoloField_title")
  String clienti_list_titoloField_title(); 

  @Key("clienti_list_tsInsField_title")
  String clienti_list_tsInsField_title(); 

  @Key("clienti_list_tsLastUpdField_title")
  String clienti_list_tsLastUpdField_title(); 

  @Key("clienti_list_uteInsField_title")
  String clienti_list_uteInsField_title(); 

  @Key("clienti_list_uteLastUpdField_title")
  String clienti_list_uteLastUpdField_title(); 

  @Key("clienti_list_vecchieDenominazioniField_title")
  String clienti_list_vecchieDenominazioniField_title(); 



  //#############################################################
  //#                   CONDIVISIONE WINDOW                     #
  //#############################################################

  @Key("condivisioneWindow_livelloPrioritaItem_title")
  String condivisioneWindow_livelloPrioritaItem_title(); 

  @Key("condivisioneWindow_messaggioInvioItem_title")
  String condivisioneWindow_messaggioInvioItem_title(); 

  @Key("condivisioneWindow_motivoInvioItem_title")
  String condivisioneWindow_motivoInvioItem_title(); 

  @Key("condivisioneWindow_title")
  String condivisioneWindow_title(); 

  @Key("condivisioneWindow_tsDecorrenzaAssegnazItem_title")
  String condivisioneWindow_tsDecorrenzaAssegnazItem_title(); 

  //#############################################################
  //#      CONFIGURAZIONE FLUSSI MODELLATI                      #
  //#############################################################
  
  @Key("configurazione_flussi_deleteButtonAsk_message")
  String configurazione_flussi_deleteButtonAsk_message();
  
  @Key("configurazione_flussi_deleteButton_prompt")
  String configurazione_flussi_deleteButton_prompt();

  //#ConfigurazioneFlussiDetail
  @Key("configurazione_flussi_detail_codTipoFlussoItem")
  String configurazione_flussi_detail_codTipoFlussoItem(); 

  @Key("configurazione_flussi_detail_idTaskItem")
  String configurazione_flussi_detail_idTaskItem(); 

  @Key("configurazione_flussi_detail_nomeTaskItem")
  String configurazione_flussi_detail_nomeTaskItem(); 

  @Key("configurazione_flussi_detail_idFaseItem")
  String configurazione_flussi_detail_idFaseItem(); 

  @Key("configurazione_flussi_detail_numeroOrdineItem")
  String configurazione_flussi_detail_numeroOrdineItem(); 


  //#ConfigurazioneFlussiDetail - lista ACL	
  @Key("configurazione_flussi_detail_aclSection_title")
  String configurazione_flussi_detail_aclSection_title(); 
  
  //ConfigurazioneFlussiDetail - lista attributi add editabili
	
  @Key("configurazione_flussi_detail_attributiAddEditabiliSection_title")
  String configurazione_flussi_detail_attributiAddEditabiliSection_title();

  @Key("configurazione_flussi_detail_list_acl_descTipoEntitaAclField")
  String configurazione_flussi_detail_list_acl_descTipoEntitaAclField(); 

  @Key("configurazione_flussi_detail_list_acl_descUOAclField")
  String configurazione_flussi_detail_list_acl_descUOAclField(); 

  @Key("configurazione_flussi_detail_list_acl_descUtenteAclField")
  String configurazione_flussi_detail_list_acl_descUtenteAclField(); 

  @Key("configurazione_flussi_detail_list_acl_eliminazioneFisicaAsk_message")
  String configurazione_flussi_detail_list_acl_eliminazioneFisicaAsk_message(); 

  @Key("configurazione_flussi_detail_list_acl_flgEreditaAclField")
  String configurazione_flussi_detail_list_acl_flgEreditaAclField(); 

  @Key("configurazione_flussi_detail_list_acl_flgExecuteActivityAclField")
  String configurazione_flussi_detail_list_acl_flgExecuteActivityAclField(); 

  @Key("configurazione_flussi_detail_list_acl_flgReadOnlyActivityAclField")
  String configurazione_flussi_detail_list_acl_flgReadOnlyActivityAclField(); 



  //#############################################################
  //#                     CONTRIBUENTE COMBO                    #
  //#############################################################

  @Key("contribuente_combo_tipo_AF_value")
  String contribuente_combo_tipo_AF_value(); 

  @Key("contribuente_combo_tipo_AG_value")
  String contribuente_combo_tipo_AG_value(); 



  //#############################################################
  //#    			DATI STATISTICHE DOCUMENTI   		        #
  //#############################################################

  @Key("datiStatisticheDocumenti_list_categoriaRegistrazioneField_title")
  String datiStatisticheDocumenti_list_categoriaRegistrazioneField_title(); 

  @Key("datiStatisticheDocumenti_list_codiceApplicazioneField_title")
  String datiStatisticheDocumenti_list_codiceApplicazioneField_title(); 

  @Key("datiStatisticheDocumenti_list_codiceUOField_title")
  String datiStatisticheDocumenti_list_codiceUOField_title(); 

  @Key("datiStatisticheDocumenti_list_denominazioneUtenteField_title")
  String datiStatisticheDocumenti_list_denominazioneUtenteField_title(); 

  @Key("datiStatisticheDocumenti_list_idEnteAooField_title")
  String datiStatisticheDocumenti_list_idEnteAooField_title(); 

  @Key("datiStatisticheDocumenti_list_livelloRiservatezzaField_title")
  String datiStatisticheDocumenti_list_livelloRiservatezzaField_title(); 

  @Key("datiStatisticheDocumenti_list_mezzoTrasmissioneField_title")
  String datiStatisticheDocumenti_list_mezzoTrasmissioneField_title(); 

  @Key("datiStatisticheDocumenti_list_nomeApplicazioneField_title")
  String datiStatisticheDocumenti_list_nomeApplicazioneField_title(); 

  @Key("datiStatisticheDocumenti_list_nomeEnteAooField_title")
  String datiStatisticheDocumenti_list_nomeEnteAooField_title(); 

  @Key("datiStatisticheDocumenti_list_nomeUOField_title")
  String datiStatisticheDocumenti_list_nomeUOField_title(); 

  @Key("datiStatisticheDocumenti_list_nrDocumentiField_title")
  String datiStatisticheDocumenti_list_nrDocumentiField_title(); 

  @Key("datiStatisticheDocumenti_list_percArrotondataField_title")
  String datiStatisticheDocumenti_list_percArrotondataField_title(); 
  
  @Key("datiStatisticheDocumenti_list_regValideAnnullateField_title")
  String datiStatisticheDocumenti_list_regValideAnnullateField_title(); 

  @Key("datiStatisticheDocumenti_list_percField_title")
  String datiStatisticheDocumenti_list_percField_title(); 

  @Key("datiStatisticheDocumenti_list_periodoField_title")
  String datiStatisticheDocumenti_list_periodoField_title(); 

  @Key("datiStatisticheDocumenti_list_presenzaFileField_title")
  String datiStatisticheDocumenti_list_presenzaFileField_title(); 

  @Key("datiStatisticheDocumenti_list_siglaRegistroField_title")
  String datiStatisticheDocumenti_list_siglaRegistroField_title(); 

  @Key("datiStatisticheDocumenti_list_supportoField_title")
  String datiStatisticheDocumenti_list_supportoField_title(); 

  @Key("datiStatisticheDocumenti_list_tipoRegistrazioneField_title")
  String datiStatisticheDocumenti_list_tipoRegistrazioneField_title(); 

  @Key("datiStatisticheDocumenti_list_usernameUtenteField_title")
  String datiStatisticheDocumenti_list_usernameUtenteField_title(); 

  @Key("datiStatisticheDocumenti_salvaComeModelloButton_prompt")
  String datiStatisticheDocumenti_salvaComeModelloButton_prompt(); 



  //#############################################################
  //#          DEFINIZIONE ATTIVITA DEI PROCEDIMENTI		    #
  //#############################################################

  @Key("definizione_attivita_procedimenti_categoria")
  String definizione_attivita_procedimenti_categoria(); 

  @Key("definizione_attivita_procedimenti_descrizione")
  String definizione_attivita_procedimenti_descrizione(); 

  @Key("definizione_attivita_procedimenti_edit_title")
  String definizione_attivita_procedimenti_edit_title(String attribute0);

  @Key("definizione_attivita_procedimenti_in_tutti_procedimenti")
  String definizione_attivita_procedimenti_in_tutti_procedimenti(); 

  @Key("definizione_attivita_procedimenti_new_title")
  String definizione_attivita_procedimenti_new_title(); 

  @Key("definizione_attivita_procedimenti_note")
  String definizione_attivita_procedimenti_note(); 

  @Key("definizione_attivita_procedimenti_tipologia_doc_ass")
  String definizione_attivita_procedimenti_tipologia_doc_ass(); 

  @Key("definizione_attivita_procedimenti_validita")
  String definizione_attivita_procedimenti_validita(); 

  @Key("definizione_attivita_procedimenti_view_title")
  String definizione_attivita_procedimenti_view_title(String attribute0);

  @Key("def_attivita_procedimenti_canvas_inTabCodice")
  String def_attivita_procedimenti_canvas_inTabCodice(); 

  @Key("def_attivita_procedimenti_canvas_inTabLabel")
  String def_attivita_procedimenti_canvas_inTabLabel(); 
  
  @Key("def_attivita_procedimenti_canvas_tsVldDal")
  String def_attivita_procedimenti_canvas_tsVldDal();
  
  @Key("def_attivita_procedimenti_canvas_tsVldA")
  String def_attivita_procedimenti_canvas_tsVldA();

  @Key("def_attivita_procedimenti_canvas_inTabPrincipale")
  String def_attivita_procedimenti_canvas_inTabPrincipale(); 

  @Key("def_attivita_procedimenti_canvas_nrMaxVal")
  String def_attivita_procedimenti_canvas_nrMaxVal(); 

  @Key("def_attivita_procedimenti_canvas_obbligatorio")
  String def_attivita_procedimenti_canvas_obbligatorio(); 

  @Key("def_attivita_procedimenti_canvas_ripetibile")
  String def_attivita_procedimenti_canvas_ripetibile(); 
  
  @Key("def_attivita_procedimenti_canvas_associato_wf")
  String def_attivita_procedimenti_canvas_associato_wf(); 

  //#############################################################
  //#                          ERRROE                           #
  //#############################################################	

  @Key("errore_all_archiviazione_postaelettronica_layout_messages")
  String errore_all_archiviazione_postaelettronica_layout_messages(); 

  @Key("errore_single_archiviazione_postaelettronica_layout_messages")
  String errore_single_archiviazione_postaelettronica_layout_messages(); 

  @Key("errorearchiviazione_postaelettronica_list_messages")
  String errorearchiviazione_postaelettronica_list_messages(); 



  //#############################################################
  //#                       AURIGA FILTER                       #
  //#############################################################

  //#ArchivioFilter
  @Key("auriga_filter_archivio_nroProt_title")
  String auriga_filter_archivio_nroProt_title(); 

  @Key("auriga_filter_archivio_tipoProt_title")
  String auriga_filter_archivio_tipoProt_title(); 

  @Key("auriga_filter_archivio_tsRegistrazione_title")
  String auriga_filter_archivio_tsRegistrazione_title(); 

  @Key("auriga_filter_archivio_annoProt_title")
  String auriga_filter_archivio_annoProt_title(); 

  @Key("auriga_filter_archivio_regEffettuataDa_title")
  String auriga_filter_archivio_regEffettuataDa_title(); 

  @Key("auriga_filter_archivio_mittente_title")
  String auriga_filter_archivio_mittente_title(); 

  @Key("auriga_filter_archivio_mittenteInRubrica_title")
  String auriga_filter_archivio_mittenteInRubrica_title(); 

  @Key("auriga_filter_archivio_destinatario_title")
  String auriga_filter_archivio_destinatario_title(); 

  @Key("auriga_filter_archivio_destinatarioInRubrica_title")
  String auriga_filter_archivio_destinatarioInRubrica_title(); 

  @Key("auriga_filter_archivio_oggetto_title")
  String auriga_filter_archivio_oggetto_title(); 

  @Key("auriga_filter_archivio_nomeFascicolo_title")
  String auriga_filter_archivio_nomeFascicolo_title(); 

  @Key("auriga_filter_archivio_tsAperturaFascicolo_title")
  String auriga_filter_archivio_tsAperturaFascicolo_title(); 

  @Key("auriga_filter_archivio_tsChiusuraFascicolo_title")
  String auriga_filter_archivio_tsChiusuraFascicolo_title(); 

  @Key("auriga_filter_archivio_tsAssegnazione_title")
  String auriga_filter_archivio_tsAssegnazione_title(); 

  @Key("auriga_filter_archivio_assegnatoA_title")
  String auriga_filter_archivio_assegnatoA_title(); 

  @Key("auriga_filter_archivio_tsNotificaCC_title")
  String auriga_filter_archivio_tsNotificaCC_title(); 

  @Key("auriga_filter_archivio_inviatoA_title")
  String auriga_filter_archivio_inviatoA_title(); 

  @Key("auriga_filter_archivio_tsNotificaNA_title")
  String auriga_filter_archivio_tsNotificaNA_title(); 

  @Key("auriga_filter_archivio_notificatoA_title")
  String auriga_filter_archivio_notificatoA_title(); 

  @Key("auriga_filter_archivio_soloDocRicevutiViaEmail_title")
  String auriga_filter_archivio_soloDocRicevutiViaEmail_title(); 

  @Key("auriga_filter_archivio_inviatiViaEmail_title")
  String auriga_filter_archivio_inviatiViaEmail_title(); 

  @Key("auriga_filter_archivio_soloRegAnnullate_title")
  String auriga_filter_archivio_soloRegAnnullate_title(); 

  @Key("auriga_filter_archivio_nroBozza_title")
  String auriga_filter_archivio_nroBozza_title(); 

  @Key("auriga_filter_archivio_tsBozza_title")
  String auriga_filter_archivio_tsBozza_title(); 

  @Key("auriga_filter_archivio_annoBozza_title")
  String auriga_filter_archivio_annoBozza_title(); 

  @Key("auriga_filter_archivio_nroStampa_title")
  String auriga_filter_archivio_nroStampa_title(); 

  @Key("auriga_filter_archivio_tsStampa_title")
  String auriga_filter_archivio_tsStampa_title(); 

  @Key("auriga_filter_archivio_annoStampa_title")
  String auriga_filter_archivio_annoStampa_title(); 

  @Key("auriga_filter_archivio_fileAssociati_title")
  String auriga_filter_archivio_fileAssociati_title(); 

  @Key("auriga_filter_archivio_flgUdFolder_title")
  String auriga_filter_archivio_flgUdFolder_title(); 

  @Key("auriga_filter_archivio_flgUdFolderFascicolo_title")
  String auriga_filter_archivio_flgUdFolderFascicolo_title(); 

  @Key("auriga_filter_archivio_flgSoloDaLeggere_title")
  String auriga_filter_archivio_flgSoloDaLeggere_title(); 

  @Key("auriga_filter_archivio_tsInvio_title")
  String auriga_filter_archivio_tsInvio_title(); 

  @Key("auriga_filter_archivio_destinatarioInvio_title")
  String auriga_filter_archivio_destinatarioInvio_title(); 

  @Key("auriga_filter_archivio_tsArchiviazione_title")
  String auriga_filter_archivio_tsArchiviazione_title(); 

  @Key("auriga_filter_archivio_tsEliminazione_title")
  String auriga_filter_archivio_tsEliminazione_title(); 

  @Key("auriga_filter_archivio_sezioneEliminazioneDoc_title")
  String auriga_filter_archivio_sezioneEliminazioneDoc_title(); 

  @Key("auriga_filter_archivio_sezioneEliminazioneFasc_title")
  String auriga_filter_archivio_sezioneEliminazioneFasc_title(); 

  @Key("auriga_filter_archivio_statoPresaInCarico_title")
  String auriga_filter_archivio_statoPresaInCarico_title(); 

  @Key("auriga_filter_archivio_statoRichAnnullamento_title")
  String auriga_filter_archivio_statoRichAnnullamento_title(); 

  @Key("auriga_filter_archivio_statoAutorizzazione_title")
  String auriga_filter_archivio_statoAutorizzazione_title(); 

  @Key("auriga_filter_archivio_altraNumerazioneSigla_title")
  String auriga_filter_archivio_altraNumerazioneSigla_title(); 

  @Key("auriga_filter_archivio_altraNumerazioneAnno_title")
  String auriga_filter_archivio_altraNumerazioneAnno_title(); 

  @Key("auriga_filter_archivio_altraNumerazioneData_title")
  String auriga_filter_archivio_altraNumerazioneData_title(); 

  @Key("auriga_filter_archivio_altraNumerazioneNr_title")
  String auriga_filter_archivio_altraNumerazioneNr_title(); 
  
  @Key("auriga_filter_archivioPregresso_altraNumerazioneNr_title")
  String auriga_filter_archivioPregresso_altraNumerazioneNr_title(); 

  @Key("auriga_filter_archivio_mezzoTrasmissione_title")
  String auriga_filter_archivio_mezzoTrasmissione_title(); 

  @Key("auriga_filter_archivio_uoProponente_title")
  String auriga_filter_archivio_uoProponente_title(); 

  @Key("auriga_filter_archivio_uoCompetente_title")
  String auriga_filter_archivio_uoCompetente_title(); 

  
  @Key("auriga_filter_archivio_utentiAvvioAtto_title")
  String auriga_filter_archivio_utentiAvvioAtto_title(); 

  @Key("auriga_filter_archivio_utentiAdozioneAtto_title")
  String auriga_filter_archivio_utentiAdozioneAtto_title(); 

  @Key("auriga_filter_archivio_noteUd_title")
  String auriga_filter_archivio_noteUd_title(); 

  @Key("auriga_filter_archivio_statiDoc_title")
  String auriga_filter_archivio_statiDoc_title(); 
  
  @Key("auriga_filter_archivio_statiFolder_title")
  String auriga_filter_archivio_statiFolder_title();

  @Key("auriga_filter_archivio_dataFirmaAtto_title")
  String auriga_filter_archivio_dataFirmaAtto_title(); 
  
  @Key("auriga_filter_archivioPregresso_dataFirmaAtto_title")
  String auriga_filter_archivioPregresso_dataFirmaAtto_title(); 
  
  @Key("auriga_filter_archivio_tipoDoc_title")
  String auriga_filter_archivio_tipoDoc_title(); 

  @Key("auriga_filter_archivio_nroProtRicevuto_title")
  String auriga_filter_archivio_nroProtRicevuto_title(); 

  @Key("auriga_filter_archivio_dataProtRicevuto_title")
  String auriga_filter_archivio_dataProtRicevuto_title(); 

  @Key("auriga_filter_archivio_statoLavorazioneAperto_title")
  String auriga_filter_archivio_statoLavorazioneAperto_title(); 

  @Key("auriga_filter_archivio_statoTrasmissioneEmail_title")
  String auriga_filter_archivio_statoTrasmissioneEmail_title(); 

  @Key("auriga_filter_archivio_tipoDocStampa_title")
  String auriga_filter_archivio_tipoDocStampa_title(); 

  @Key("auriga_filter_archivio_nroRichiestaStampaExp_title")
  String auriga_filter_archivio_nroRichiestaStampaExp_title(); 

  @Key("auriga_filter_archivio_tsRichiestaStampaExp_title")
  String auriga_filter_archivio_tsRichiestaStampaExp_title(); 

  @Key("auriga_filter_archivio_capoFilaFasc_title")
  String auriga_filter_archivio_capoFilaFasc_title(); 

  @Key("auriga_filter_archivio_registroAltriRifTipo_title")
  String auriga_filter_archivio_registroAltriRifTipo_title(); 

  @Key("auriga_filter_archivio_registroAltriRifNro_title")
  String auriga_filter_archivio_registroAltriRifNro_title(); 

  @Key("auriga_filter_archivio_registroAltriRifData_title")
  String auriga_filter_archivio_registroAltriRifData_title(); 

  @Key("auriga_filter_archivio_registroAltriRifAnno_title")
  String auriga_filter_archivio_registroAltriRifAnno_title(); 

  @Key("auriga_filter_archivio_protocolloCapofila_title")
  String auriga_filter_archivio_protocolloCapofila_title(); 

  @Key("auriga_filter_archivio_docCollegatiProt_title")
  String auriga_filter_archivio_docCollegatiProt_title(); 

  @Key("auriga_filter_archivio_indirizzoInVario_title")
  String auriga_filter_archivio_indirizzoInVario_title(); 

  @Key("auriga_filter_archivio_indirizzo_title")
  String auriga_filter_archivio_indirizzo_title(); 
  
  @Key("auriga_filter_archivio_supporto_title")
  String auriga_filter_archivio_supporto_title();
  
  @Key("auriga_filter_archivio_uoRegistrazione_title")
  String auriga_filter_archivio_uoRegistrazione_title();
  
  @Key("auriga_filter_archivio_uoApertura_title")
  String auriga_filter_archivio_uoApertura_title();
  
  
  @Key("auriga_filter_archivio_flgAppostoTimbro_title")
  String auriga_filter_archivio_flgAppostoTimbro_title();
  
  @Key("auriga_filter_archivio_sub_title")
  String auriga_filter_archivio_sub_title();
  
  @Key("auriga_filter_archivio_sigla_title")
  String auriga_filter_archivio_sigla_title();

  @Key("auriga_filter_archivio_classificazione_title")
  String auriga_filter_archivio_classificazione_title();
  
  @Key("auriga_filter_archivio_annoFascicolo_title")
  String auriga_filter_archivio_annoFascicolo_title();

  @Key("auriga_filter_archivio_nroFascicolo_title")
  String auriga_filter_archivio_nroFascicolo_title();

  @Key("auriga_filter_archivio_nroSottoFascicolo_title")
  String auriga_filter_archivio_nroSottoFascicolo_title();

  @Key("auriga_filter_archivio_codiceFascicolo_title")
  String auriga_filter_archivio_codiceFascicolo_title();

  @Key("auriga_filter_archivio_attoAutAnnullamento_title")
  String auriga_filter_archivio_attoAutAnnullamento_title();
  
  @Key("auriga_filter_archivio_dtStesura_title")
  String auriga_filter_archivio_dtStesura_title();
 
  @Key("auriga_filter_archivio_docCollegatiNominativo_title")
  String auriga_filter_archivio_docCollegatiNominativo_title();
   
  @Key("auriga_filter_archivio_esibente_title")
  String auriga_filter_archivio_esibente_title();
  
  @Key("auriga_filter_archivio_esibenteInRubrica_title")
  String auriga_filter_archivio_esibenteInRubrica_title();
  
  @Key("auriga_filter_archivio_cig_title")
  String auriga_filter_archivio_cig_title();
  
  @Key("auriga_filter_archivio_centroDiCosto")
  String auriga_filter_archivio_centroDiCosto();
  
  @Key("auriga_filter_archivio_dataScadenza")
  String auriga_filter_archivio_dataScadenza();
  
  @Key("auriga_filter_archivio_cfNominativoCollegato")
  String auriga_filter_archivio_cfNominativoCollegato();
  
  @Key("auriga_filter_archivio_perizia_title")
  String auriga_filter_archivio_perizia_title();
  
  @Key("auriga_filter_archivio_riservatezza_title")
  String auriga_filter_archivio_riservatezza_title(); 
  
  
  @Key("auriga_filter_archivio_nroPubblicazione_title")
  String auriga_filter_archivio_nroPubblicazione_title(); 
  
  @Key("auriga_filter_archivio_annoPubblicazione_title")
  String auriga_filter_archivio_annoPubblicazione_title(); 
  
  @Key("auriga_filter_archivio_dtInizioPubblicazione_title")
  String auriga_filter_archivio_dtInizioPubblicazione_title();
  
  @Key("auriga_filter_archivio_dtTerminePubblicazione_title")
  String auriga_filter_archivio_dtTerminePubblicazione_title();
  
  @Key("auriga_filter_archivio_ggDurataPubblicazione_title")
  String auriga_filter_archivio_ggDurataPubblicazione_title(); 
  
  @Key("auriga_filter_archivio_uoPubblicazione_title")
  String auriga_filter_archivio_uoPubblicazione_title(); 
  
  @Key("auriga_filter_archivio_pubblicazioneEffettuataDa_title")
  String auriga_filter_archivio_pubblicazioneEffettuataDa_title(); 
  
  @Key("auriga_filter_archivio_flgPubblicazioneAnnullata_title")
  String auriga_filter_archivio_flgPubblicazioneAnnullata_title(); 
  
  @Key("auriga_filter_archivio_flgPubblicazioneRettificata_title")
  String auriga_filter_archivio_flgPubblicazioneRettificata_title(); 
  
  @Key("auriga_filter_archivio_statoPubblicazione_title")
  String auriga_filter_archivio_statoPubblicazione_title(); 
  
  @Key("auriga_filter_archivio_presoInCaricoDa_title")
  String auriga_filter_archivio_presoInCaricoDa_title(); 
  
  @Key("auriga_filter_archivio_presaVisioneEffettuataDa_title")
  String auriga_filter_archivio_presaVisioneEffettuataDa_title(); 
  
  @Key("auriga_filter_archivio_flgConFileFirmati_title")
  String auriga_filter_archivio_flgConFileFirmati_title(); 
    
  @Key("auriga_filter_archivio_formatiElettronici")
  String auriga_filter_archivio_formatiElettronici(); 
  
  @Key("auriga_filter_archivio_flgSottopostoControlloRegAmm_title")
  String auriga_filter_archivio_flgSottopostoControlloRegAmm_title(); 
    
  @Key("auriga_filter_archivio_flgPassaggioDaSmistamento_title")
  String auriga_filter_archivio_flgPassaggioDaSmistamento_title(); 
  
  @Key("auriga_filter_archivio_presenza_opere_title")
  String auriga_filter_archivio_presenza_opere_title(); 
  
  @Key("auriga_filter_archivio_statiTrasfBloomfleet_title")
  String auriga_filter_archivio_statiTrasfBloomfleet_title(); 
  
  @Key("auriga_filter_archivio_regoleRegistrazioneAutomaticaEmail_title")
  String auriga_filter_archivio_regoleRegistrazioneAutomaticaEmail_title(); 
  
  @Key("auriga_filter_archivio_rdAeAttiCollegati_title")
  String auriga_filter_archivio_rdAeAttiCollegati_title(); 
 
  @Key("auriga_filter_archivio_statoClassFascDocumenti_title")
  String auriga_filter_archivio_statoClassFascDocumenti_title(); 
  
  
  //#ScrivaniaFilter
  @Key("auriga_filter_scrivania_nroProt_title")
  String auriga_filter_scrivania_nroProt_title(); 

  @Key("auriga_filter_scrivania_tipoProt_title")
  String auriga_filter_scrivania_tipoProt_title(); 

  @Key("auriga_filter_scrivania_tsRegistrazione_title")
  String auriga_filter_scrivania_tsRegistrazione_title(); 

  @Key("auriga_filter_scrivania_annoProt_title")
  String auriga_filter_scrivania_annoProt_title(); 

  @Key("auriga_filter_scrivania_regEffettuataDa_title")
  String auriga_filter_scrivania_regEffettuataDa_title(); 

  @Key("auriga_filter_scrivania_mittente_title")
  String auriga_filter_scrivania_mittente_title(); 

  @Key("auriga_filter_scrivania_mittenteInRubrica_title")
  String auriga_filter_scrivania_mittenteInRubrica_title(); 

  @Key("auriga_filter_scrivania_destinatario_title")
  String auriga_filter_scrivania_destinatario_title(); 

  @Key("auriga_filter_scrivania_destinatarioInRubrica_title")
  String auriga_filter_scrivania_destinatarioInRubrica_title(); 

  @Key("auriga_filter_scrivania_oggetto_title")
  String auriga_filter_scrivania_oggetto_title(); 

  @Key("auriga_filter_scrivania_nomeFascicolo_title")
  String auriga_filter_scrivania_nomeFascicolo_title(); 

  @Key("auriga_filter_scrivania_tsAperturaFascicolo_title")
  String auriga_filter_scrivania_tsAperturaFascicolo_title(); 

  @Key("auriga_filter_scrivania_tsAssegnazione_title")
  String auriga_filter_scrivania_tsAssegnazione_title(); 

  @Key("auriga_filter_scrivania_assegnatoA_title")
  String auriga_filter_scrivania_assegnatoA_title(); 

  @Key("auriga_filter_scrivania_tsNotificaCC_title")
  String auriga_filter_scrivania_tsNotificaCC_title(); 

  @Key("auriga_filter_scrivania_inviatoA_title")
  String auriga_filter_scrivania_inviatoA_title(); 

  @Key("auriga_filter_scrivania_tsNotificaNA_title")
  String auriga_filter_scrivania_tsNotificaNA_title(); 

  @Key("auriga_filter_scrivania_notificatoA_title")
  String auriga_filter_scrivania_notificatoA_title(); 

  @Key("auriga_filter_scrivania_soloDocRicevutiViaEmail_title")
  String auriga_filter_scrivania_soloDocRicevutiViaEmail_title(); 

  @Key("auriga_filter_scrivania_inviatiViaEmail_title")
  String auriga_filter_scrivania_inviatiViaEmail_title(); 

  @Key("auriga_filter_scrivania_soloRegAnnullate_title")
  String auriga_filter_scrivania_soloRegAnnullate_title(); 

  @Key("auriga_filter_scrivania_nroBozza_title")
  String auriga_filter_scrivania_nroBozza_title(); 

  @Key("auriga_filter_scrivania_tsBozza_title")
  String auriga_filter_scrivania_tsBozza_title(); 

  @Key("auriga_filter_scrivania_annoBozza_title")
  String auriga_filter_scrivania_annoBozza_title(); 

  @Key("auriga_filter_scrivania_nroStampa_title")
  String auriga_filter_scrivania_nroStampa_title(); 

  @Key("auriga_filter_scrivania_tsStampa_title")
  String auriga_filter_scrivania_tsStampa_title(); 

  @Key("auriga_filter_scrivania_annoStampa_title")
  String auriga_filter_scrivania_annoStampa_title(); 

  @Key("auriga_filter_scrivania_fileAssociati_title")
  String auriga_filter_scrivania_fileAssociati_title(); 

  @Key("auriga_filter_scrivania_flgUdFolder_title")
  String auriga_filter_scrivania_flgUdFolder_title(); 

  @Key("auriga_filter_scrivania_flgUdFolderFascicolo_title")
  String auriga_filter_scrivania_flgUdFolderFascicolo_title(); 

  @Key("auriga_filter_scrivania_flgSoloDaLeggere_title")
  String auriga_filter_scrivania_flgSoloDaLeggere_title(); 

  @Key("auriga_filter_scrivania_tsInvio_title")
  String auriga_filter_scrivania_tsInvio_title(); 

  @Key("auriga_filter_scrivania_destinatarioInvio_title")
  String auriga_filter_scrivania_destinatarioInvio_title(); 

  @Key("auriga_filter_scrivania_tsArchiviazione_title")
  String auriga_filter_scrivania_tsArchiviazione_title(); 

  @Key("auriga_filter_scrivania_tsEliminazione_title")
  String auriga_filter_scrivania_tsEliminazione_title(); 

  @Key("auriga_filter_scrivania_sezioneEliminazioneDoc_title")
  String auriga_filter_scrivania_sezioneEliminazioneDoc_title(); 

  @Key("auriga_filter_scrivania_sezioneEliminazioneFasc_title")
  String auriga_filter_scrivania_sezioneEliminazioneFasc_title(); 

  @Key("auriga_filter_scrivania_statoPresaInCarico_title")
  String auriga_filter_scrivania_statoPresaInCarico_title(); 

  @Key("auriga_filter_scrivania_statoRichAnnullamento_title")
  String auriga_filter_scrivania_statoRichAnnullamento_title(); 

  @Key("auriga_filter_scrivania_statoAutorizzazione_title")
  String auriga_filter_scrivania_statoAutorizzazione_title(); 

  @Key("auriga_filter_scrivania_altraNumerazioneSigla_title")
  String auriga_filter_scrivania_altraNumerazioneSigla_title(); 

  @Key("auriga_filter_scrivania_altraNumerazioneAnno_title")
  String auriga_filter_scrivania_altraNumerazioneAnno_title(); 

  @Key("auriga_filter_scrivania_altraNumerazioneData_title")
  String auriga_filter_scrivania_altraNumerazioneData_title(); 

  @Key("auriga_filter_scrivania_altraNumerazioneNr_title")
  String auriga_filter_scrivania_altraNumerazioneNr_title(); 

  @Key("auriga_filter_scrivania_mezzoTrasmissione_title")
  String auriga_filter_scrivania_mezzoTrasmissione_title(); 

  @Key("auriga_filter_scrivania_uoProponente_title")
  String auriga_filter_scrivania_uoProponente_title(); 

  @Key("auriga_filter_scrivania_utentiAvvioAtto_title")
  String auriga_filter_scrivania_utentiAvvioAtto_title(); 

  @Key("auriga_filter_scrivania_utentiAdozioneAtto_title")
  String auriga_filter_scrivania_utentiAdozioneAtto_title(); 

  @Key("auriga_filter_scrivania_noteUd_title")
  String auriga_filter_scrivania_noteUd_title(); 

  @Key("auriga_filter_scrivania_statiDoc_title")
  String auriga_filter_scrivania_statiDoc_title(); 

  @Key("auriga_filter_scrivania_dataFirmaAtto_title")
  String auriga_filter_scrivania_dataFirmaAtto_title(); 

  @Key("auriga_filter_scrivania_tipoDoc_title")
  String auriga_filter_scrivania_tipoDoc_title(); 

  @Key("auriga_filter_scrivania_statoLavorazioneAperto_title")
  String auriga_filter_scrivania_statoLavorazioneAperto_title(); 

  @Key("auriga_filter_scrivania_statoTrasmissioneEmail_title")
  String auriga_filter_scrivania_statoTrasmissioneEmail_title(); 

  @Key("auriga_filter_scrivania_nroProtRicevuto_title")
  String auriga_filter_scrivania_nroProtRicevuto_title(); 

  @Key("auriga_filter_scrivania_dataProtRicevuto_title")
  String auriga_filter_scrivania_dataProtRicevuto_title(); 

  @Key("auriga_filter_scrivania_tipoDocStampa_title")
  String auriga_filter_scrivania_tipoDocStampa_title(); 

  @Key("auriga_filter_scrivania_nroRichiestaStampaExp_title")
  String auriga_filter_scrivania_nroRichiestaStampaExp_title(); 

  @Key("auriga_filter_scrivania_tsRichiestaStampaExp_title")
  String auriga_filter_scrivania_tsRichiestaStampaExp_title(); 

  @Key("auriga_filter_scrivania_capoFilaFasc_title")
  String auriga_filter_scrivania_capoFilaFasc_title(); 

  @Key("auriga_filter_scrivania_registroAltriRifTipo_title")
  String auriga_filter_scrivania_registroAltriRifTipo_title(); 

  @Key("auriga_filter_scrivania_registroAltriRifNro_title")
  String auriga_filter_scrivania_registroAltriRifNro_title(); 

  @Key("auriga_filter_scrivania_registroAltriRifData_title")
  String auriga_filter_scrivania_registroAltriRifData_title(); 

  @Key("auriga_filter_scrivania_registroAltriRifAnno_title")
  String auriga_filter_scrivania_registroAltriRifAnno_title(); 

  @Key("auriga_filter_scrivania_dtStesura_title")
  String auriga_filter_scrivania_dtStesura_title();

  @Key("auriga_filter_scrivania_dtPresaInCarico_title")
  String auriga_filter_scrivania_dtPresaInCarico_title();

  @Key("auriga_filter_scrivania_docCollegatiNominativo_title")
  String auriga_filter_scrivania_docCollegatiNominativo_title();
  
  @Key("auriga_filter_scrivania_cig_title")
  String auriga_filter_scrivania_cig_title();

  @Key("auriga_filter_scrivania_perizia_title")
  String auriga_filter_scrivania_perizia_title();
  
  @Key("auriga_filter_scrivania_riservatezza_title")
  String auriga_filter_scrivania_riservatezza_title(); 
  
  @Key("auriga_filter_scrivania_flgPassaggioDaSmistamento_title")
  String auriga_filter_scrivania_flgPassaggioDaSmistamento_title(); 
    
  @Key("auriga_filter_scrivania_presenza_opere_title")
  String auriga_filter_scrivania_presenza_opere_title(); 
  
  @Key("auriga_filter_scrivania_statiTrasfBloomfleet_title")
  String auriga_filter_scrivania_statiTrasfBloomfleet_title(); 
  
  
  //#SoggettiFilter
  @Key("auriga_soggetti_filter_codIstatComune")
  String auriga_soggetti_filter_codIstatComune(); 

  @Key("auriga_soggetti_filter_codRapidoIO")
  String auriga_soggetti_filter_codRapidoIO(); 

  @Key("auriga_soggetti_filter_descCitta")
  String auriga_soggetti_filter_descCitta(); 

  @Key("auriga_soggetti_filter_eMailIO")
  String auriga_soggetti_filter_eMailIO(); 

  @Key("auriga_soggetti_filter_flgIncludiAnnullati")
  String auriga_soggetti_filter_flgIncludiAnnullati(); 

  @Key("auriga_soggetti_filter_flgSoloVld")
  String auriga_soggetti_filter_flgSoloVld(); 

  @Key("auriga_soggetti_filter_porzioneRubrica")
  String auriga_soggetti_filter_porzioneRubrica(); 

  @Key("auriga_soggetti_filter_restringiRicercaIndirizzo")
  String auriga_soggetti_filter_restringiRicercaIndirizzo(); 

  @Key("auriga_soggetti_filter_restringiRicercaVia_value_d")
  String auriga_soggetti_filter_restringiRicercaVia_value_d(); 

  @Key("auriga_soggetti_filter_restringiRicercaVia_value_rc")
  String auriga_soggetti_filter_restringiRicercaVia_value_rc(); 

  @Key("auriga_soggetti_filter_restringiRicercaVia_value_rs")
  String auriga_soggetti_filter_restringiRicercaVia_value_rs(); 

  @Key("auriga_soggetti_filter_restringiRicercaVia_value_sl")
  String auriga_soggetti_filter_restringiRicercaVia_value_sl(); 

  @Key("auriga_soggetti_filter_restringiRicercaVia_value_so")
  String auriga_soggetti_filter_restringiRicercaVia_value_so(); 

  @Key("auriga_soggetti_filter_strInDenominazione")
  String auriga_soggetti_filter_strInDenominazione(); 

  @Key("auriga_soggetti_filter_tipo_select_value_apa")
  String auriga_soggetti_filter_tipo_select_value_apa(); 

  @Key("auriga_soggetti_filter_tipo_select_value_apf")
  String auriga_soggetti_filter_tipo_select_value_apf(); 

  @Key("auriga_soggetti_filter_tipo_select_value_apg")
  String auriga_soggetti_filter_tipo_select_value_apg(); 

  @Key("auriga_soggetti_filter_tipo_select_value_mdg")
  String auriga_soggetti_filter_tipo_select_value_mdg(); 

  @Key("auriga_soggetti_filter_tipo_select_value_unitadipersonale")
  String auriga_soggetti_filter_tipo_select_value_unitadipersonale(); 

  @Key("auriga_soggetti_filter_tipo_select_value_uo_ufficiointerno")
  String auriga_soggetti_filter_tipo_select_value_uo_ufficiointerno(); 

  @Key("auriga_soggetti_filter_tipologia")
  String auriga_soggetti_filter_tipologia(); 

  @Key("auriga_soggetti_filter_viaIndirizzo")
  String auriga_soggetti_filter_viaIndirizzo(); 

  @Key("auriga_soggetti_filter_viaIndirizzoViario")
  String auriga_soggetti_filter_viaIndirizzoViario(); 


  //#AnagraficaFilter
  @Key("auriga_filter_anagrafica_clienti_abi_title")
  String auriga_filter_anagrafica_clienti_abi_title(); 

  @Key("auriga_filter_anagrafica_clienti_beneficiario_title")
  String auriga_filter_anagrafica_clienti_beneficiario_title(); 

  @Key("auriga_filter_anagrafica_clienti_billingAccount_title")
  String auriga_filter_anagrafica_clienti_billingAccount_title(); 

  @Key("auriga_filter_anagrafica_clienti_cab_title")
  String auriga_filter_anagrafica_clienti_cab_title(); 

  @Key("auriga_filter_anagrafica_clienti_cid_title")
  String auriga_filter_anagrafica_clienti_cid_title(); 

  @Key("auriga_filter_anagrafica_clienti_codiceFiscale_title")
  String auriga_filter_anagrafica_clienti_codiceFiscale_title(); 

  @Key("auriga_filter_anagrafica_clienti_codiceIpa_title")
  String auriga_filter_anagrafica_clienti_codiceIpa_title(); 

  @Key("auriga_filter_anagrafica_clienti_codiceRapido_title")
  String auriga_filter_anagrafica_clienti_codiceRapido_title(); 

  @Key("auriga_filter_anagrafica_clienti_flgIncludiAnnullati_title")
  String auriga_filter_anagrafica_clienti_flgIncludiAnnullati_title(); 

  @Key("auriga_filter_anagrafica_clienti_flgSoloVld_title")
  String auriga_filter_anagrafica_clienti_flgSoloVld_title(); 

  @Key("auriga_filter_anagrafica_clienti_gruppoDiRiferimento_title")
  String auriga_filter_anagrafica_clienti_gruppoDiRiferimento_title(); 

  @Key("auriga_filter_anagrafica_clienti_iban_title")
  String auriga_filter_anagrafica_clienti_iban_title(); 

  @Key("auriga_filter_anagrafica_clienti_ipa_title")
  String auriga_filter_anagrafica_clienti_ipa_title(); 

  @Key("auriga_filter_anagrafica_clienti_istitutoFinanziario_title")
  String auriga_filter_anagrafica_clienti_istitutoFinanziario_title(); 

  @Key("auriga_filter_anagrafica_clienti_partitaIva_title")
  String auriga_filter_anagrafica_clienti_partitaIva_title(); 

  @Key("auriga_filter_anagrafica_clienti_societa_title")
  String auriga_filter_anagrafica_clienti_societa_title(); 

  @Key("auriga_filter_anagrafica_clienti_strInDenominazione_title")
  String auriga_filter_anagrafica_clienti_strInDenominazione_title(); 

  @Key("auriga_filter_anagrafica_clienti_tipologia_title")
  String auriga_filter_anagrafica_clienti_tipologia_title(); 

  @Key("auriga_filter_anagrafiche_societa_flgSoloVld_title")
  String auriga_filter_anagrafiche_societa_flgSoloVld_title(); 

  @Key("auriga_filter_anagrafiche_tipi_doc_codTipoDoc_title")
  String auriga_filter_anagrafiche_tipi_doc_codTipoDoc_title(); 

  @Key("auriga_filter_anagrafiche_tipi_doc_nomeTipoDoc_title")
  String auriga_filter_anagrafiche_tipi_doc_nomeTipoDoc_title(); 


  //#AttributiFilter
  @Key("auriga_filter_attributi_custom_appartenenza_title")
  String auriga_filter_attributi_custom_appartenenza_title(); 

  @Key("auriga_filter_attributi_custom_descrizione_title")
  String auriga_filter_attributi_custom_descrizione_title(); 

  @Key("auriga_filter_attributi_custom_etichetta_title")
  String auriga_filter_attributi_custom_etichetta_title(); 

  @Key("auriga_filter_attributi_custom_flgEscludiSottoAttr_title")
  String auriga_filter_attributi_custom_flgEscludiSottoAttr_title(); 

  @Key("auriga_filter_attributi_custom_flgInclAnnullati_title")
  String auriga_filter_attributi_custom_flgInclAnnullati_title(); 

  @Key("auriga_filter_attributi_custom_name_title")
  String auriga_filter_attributi_custom_name_title(); 

  @Key("auriga_filter_attributi_custom_tipo_title")
  String auriga_filter_attributi_custom_tipo_title(); 


  //#GetioneUtentiFilter
  @Key("auriga_filter_gestioneutenti_desUser_title")
  String auriga_filter_gestioneutenti_desUser_title(); 

  @Key("auriga_filter_gestioneutenti_email_title")
  String auriga_filter_gestioneutenti_email_title(); 

  @Key("auriga_filter_gestioneutenti_flgAccreditatiInDomIO_title")
  String auriga_filter_gestioneutenti_flgAccreditatiInDomIO_title(); 

  @Key("auriga_filter_gestioneutenti_flgSoloVld_title")
  String auriga_filter_gestioneutenti_flgSoloVld_title(); 

  @Key("auriga_filter_gestioneutenti_profiloUtente_title")
  String auriga_filter_gestioneutenti_profiloUtente_title(); 

  @Key("auriga_filter_gestioneutenti_qualificaUtente_title")
  String auriga_filter_gestioneutenti_qualificaUtente_title(); 

  @Key("auriga_filter_gestioneutenti_username_title")
  String auriga_filter_gestioneutenti_username_title(); 

  @Key("avvenutaarchiviazione_postaelettronica_list_messages")
  String avvenutaarchiviazione_postaelettronica_list_messages(); 


  @Key("auriga_filter_EsitoJobCaricamentoSelect_KO_title")
  String auriga_filter_EsitoJobCaricamentoSelect_KO_title(); 

  @Key("auriga_filter_EsitoJobCaricamentoSelect_OK_title")
  String auriga_filter_EsitoJobCaricamentoSelect_OK_title(); 

  @Key("auriga_filter_TipoFileJobCaricamentoSelect_CO_title")
  String auriga_filter_TipoFileJobCaricamentoSelect_CO_title(); 

  @Key("auriga_filter_TipoFileJobCaricamentoSelect_FT_title")
  String auriga_filter_TipoFileJobCaricamentoSelect_FT_title(); 

  @Key("auriga_filter_TipoFileJobCaricamentoSelect_SC_title")
  String auriga_filter_TipoFileJobCaricamentoSelect_SC_title(); 



  //#############################################################
  //#                        FILTERBEAN                         #
  //#############################################################

  //#filterbean_StatoFatturaSelect
  @Key("filterbean_StatoFatturaSelect_AVVNOCONS_value")
  String filterbean_StatoFatturaSelect_AVVNOCONS_value(); 

  @Key("filterbean_StatoFatturaSelect_CONS_value")
  String filterbean_StatoFatturaSelect_CONS_value(); 

  @Key("filterbean_StatoFatturaSelect_DEC_TERM_value")
  String filterbean_StatoFatturaSelect_DEC_TERM_value(); 

  @Key("filterbean_StatoFatturaSelect_DFIRM_value")
  String filterbean_StatoFatturaSelect_DFIRM_value(); 

  @Key("filterbean_StatoFatturaSelect_DTRASM_SdI_value")
  String filterbean_StatoFatturaSelect_DTRASM_SdI_value(); 

  @Key("filterbean_StatoFatturaSelect_ERRTR_SdI_value")
  String filterbean_StatoFatturaSelect_ERRTR_SdI_value(); 

  @Key("filterbean_StatoFatturaSelect_ERR_CONS_value")
  String filterbean_StatoFatturaSelect_ERR_CONS_value(); 

  @Key("filterbean_StatoFatturaSelect_ES_NEG_value")
  String filterbean_StatoFatturaSelect_ES_NEG_value(); 

  @Key("filterbean_StatoFatturaSelect_ES_POS_value")
  String filterbean_StatoFatturaSelect_ES_POS_value(); 

  @Key("filterbean_StatoFatturaSelect_RIF_SdI_value")
  String filterbean_StatoFatturaSelect_RIF_SdI_value(); 

  @Key("filterbean_StatoFatturaSelect_STORNO_DF_value")
  String filterbean_StatoFatturaSelect_STORNO_DF_value(); 

  @Key("filterbean_StatoFatturaSelect_STORNO_value")
  String filterbean_StatoFatturaSelect_STORNO_value(); 

  @Key("filterbean_StatoFatturaSelect_TRASM_SdI_value")
  String filterbean_StatoFatturaSelect_TRASM_SdI_value(); 

  @Key("filterbean_StatoFatturaSelect_FATTURAPA_ELAB_value")
  String filterbean_StatoFatturaSelect_FATTURAPA_ELAB_value(); 

  @Key("filterbean_StatoFatturaSelect_FATTURAPA_SENT_value")
  String filterbean_StatoFatturaSelect_FATTURAPA_SENT_value(); 

  @Key("filterbean_StatoFatturaSelect_PEC_ELAB_value")
  String filterbean_StatoFatturaSelect_PEC_ELAB_value(); 

  @Key("filterbean_StatoFatturaSelect_PEC_SENT_value")
  String filterbean_StatoFatturaSelect_PEC_SENT_value(); 

  @Key("filterbean_StatoFatturaSelect_PEC_NO_SENT_value")
  String filterbean_StatoFatturaSelect_PEC_NO_SENT_value(); 

  @Key("filterbean_StatoFatturaSelect_PEC_ACC_value")
  String filterbean_StatoFatturaSelect_PEC_ACC_value(); 

  @Key("filterbean_StatoFatturaSelect_PEC_CONS_value")
  String filterbean_StatoFatturaSelect_PEC_CONS_value(); 

  @Key("filterbean_StatoFatturaSelect_PEC_NO_CONS_value")
  String filterbean_StatoFatturaSelect_PEC_NO_CONS_value(); 


  //#filterbean_StatoDocumentiSelect
  @Key("filterbean_StatoDocumentiSelect_DFIRM_value")
  String filterbean_StatoDocumentiSelect_DFIRM_value(); 

  @Key("filterbean_StatoDocumentiSelect_DTRASM_SdI_value")
  String filterbean_StatoDocumentiSelect_DTRASM_SdI_value(); 

  @Key("filterbean_StatoDocumentiSelect_ERRTR_SdI_value")
  String filterbean_StatoDocumentiSelect_ERRTR_SdI_value(); 

  @Key("filterbean_StatoDocumentiSelect_ERR_CONS_value")
  String filterbean_StatoDocumentiSelect_ERR_CONS_value(); 

  @Key("filterbean_StatoDocumentiSelect_ES_NEG_value")
  String filterbean_StatoDocumentiSelect_ES_NEG_value(); 

  @Key("filterbean_StatoDocumentiSelect_ES_POS_value")
  String filterbean_StatoDocumentiSelect_ES_POS_value(); 

  @Key("filterbean_StatoDocumentiSelect_RIF_SdI_value")
  String filterbean_StatoDocumentiSelect_RIF_SdI_value(); 

  @Key("filterbean_StatoDocumentiSelect_STORNO_DF_value")
  String filterbean_StatoDocumentiSelect_STORNO_DF_value(); 

  @Key("filterbean_StatoDocumentiSelect_STORNO_value")
  String filterbean_StatoDocumentiSelect_STORNO_value(); 

  @Key("filterbean_StatoDocumentiSelect_TRASM_SdI_value")
  String filterbean_StatoDocumentiSelect_TRASM_SdI_value(); 

  @Key("filterbean_StatoDocumentiSelect2_ARCH_value")
  String filterbean_StatoDocumentiSelect2_ARCH_value(); 

  @Key("filterbean_StatoDocumentiSelect2_CS_value")
  String filterbean_StatoDocumentiSelect2_CS_value(); 

  @Key("filterbean_StatoDocumentiSelect2_ERR_value")
  String filterbean_StatoDocumentiSelect2_ERR_value(); 

  @Key("filterbean_StatoDocumentiSelect_AVVNOCONS_value")
  String filterbean_StatoDocumentiSelect_AVVNOCONS_value(); 

  @Key("filterbean_StatoDocumentiSelect_CONS_value")
  String filterbean_StatoDocumentiSelect_CONS_value(); 

  @Key("filterbean_StatoDocumentiSelect_DEC_TERM_value")
  String filterbean_StatoDocumentiSelect_DEC_TERM_value(); 


  //#filterbean_TipologiaClienteSelect
  @Key("filterbean_TipologiaClienteSelect_AF_value")
  String filterbean_TipologiaClienteSelect_AF_value(); 

  @Key("filterbean_TipologiaClienteSelect_AG_value")
  String filterbean_TipologiaClienteSelect_AG_value(); 

  @Key("filterbean_TipologiaClienteSelect_APA_value")
  String filterbean_TipologiaClienteSelect_APA_value(); 


  //#Filterbean MesiSelect
  @Key("filterbean_MesiSelect_0_value")
  String filterbean_MesiSelect_0_value(); 

  @Key("filterbean_MesiSelect_10_value")
  String filterbean_MesiSelect_10_value(); 

  @Key("filterbean_MesiSelect_11_value")
  String filterbean_MesiSelect_11_value(); 

  @Key("filterbean_MesiSelect_1_value")
  String filterbean_MesiSelect_1_value(); 

  @Key("filterbean_MesiSelect_2_value")
  String filterbean_MesiSelect_2_value(); 

  @Key("filterbean_MesiSelect_3_value")
  String filterbean_MesiSelect_3_value(); 

  @Key("filterbean_MesiSelect_4_value")
  String filterbean_MesiSelect_4_value(); 

  @Key("filterbean_MesiSelect_5_value")
  String filterbean_MesiSelect_5_value(); 

  @Key("filterbean_MesiSelect_6_value")
  String filterbean_MesiSelect_6_value(); 

  @Key("filterbean_MesiSelect_7_value")
  String filterbean_MesiSelect_7_value(); 

  @Key("filterbean_MesiSelect_8_value")
  String filterbean_MesiSelect_8_value(); 

  @Key("filterbean_MesiSelect_9_value")
  String filterbean_MesiSelect_9_value(); 


  //#Filtri
  @Key("filter_statoCaricamentoRubriche_caricatoDa_title")
  String filter_statoCaricamentoRubriche_caricatoDa_title(); 

  @Key("filter_statoCaricamentoRubriche_denominazioneSocieta_title")
  String filter_statoCaricamentoRubriche_denominazioneSocieta_title(); 

  @Key("filter_statoCaricamentoRubriche_tipiContenuto_title")
  String filter_statoCaricamentoRubriche_tipiContenuto_title(); 

  @Key("filter_statoCaricamentoRubriche_stati_title")
  String filter_statoCaricamentoRubriche_stati_title(); 

  @Key("filter_statoCaricamentoRubriche_uploadDate_title")
  String filter_statoCaricamentoRubriche_uploadDate_title(); 

  @Key("filter_statoCaricamentoRubriche_dataInizioElaborazione_title")
  String filter_statoCaricamentoRubriche_dataInizioElaborazione_title(); 

  @Key("filter_statoCaricamentoRubriche_dataFineElaborazione_title")
  String filter_statoCaricamentoRubriche_dataFineElaborazione_title(); 



  //#############################################################
  //#              FIRME IN CALCE				                #
  //#############################################################

  @Key("firme_in_calce_modelliSelectItem_title")
  String firme_in_calce_modelliSelectItem_title(); 

  @Key("firme_in_calce_firmaPredefinita_title")
  String firme_in_calce_firmaPredefinita_title(); 

  @Key("firme_in_calce_firmaPredefinitaNonPresente_warningMessage")
  String firme_in_calce_firmaPredefinitaNonPresente_warningMessage(); 

  @Key("firme_in_calce_modificaInTestoSemplice_warningMessage")
  String firme_in_calce_modificaInTestoSemplice_warningMessage(); 



  //#############################################################
  //#                      FirmaUtility                         #
  //#############################################################

  @Key("firmaUtility_richiestaCodiceOtp")
  String firmaUtility_richiestaCodiceOtp(); 

  @Key("firmaUtility_richiestaCodiceOtpOk")
  String firmaUtility_richiestaCodiceOtpOk(); 

  @Key("firmaUtility_fileFirmatoOk")
  String firmaUtility_fileFirmatoOk(); 



  //#############################################################
  //#               Credenziali per firma HSM                   #
  //#############################################################

  @Key("hsmCredenzialiWindow_title")
  String hsmCredenzialiWindow_title(); 

  @Key("hsmCredenzialiWindow_usernameTextItem")
  String hsmCredenzialiWindow_usernameTextItem();
  
  @Key("hsmCredenzialiWindow_usernameCFTextItem")
  String hsmCredenzialiWindow_usernameCFTextItem();
  
  @Key("hsmCredenzialiWindow_usernameDeleganteTextItem")
  String hsmCredenzialiWindow_usernameDeleganteTextItem(); 
  
  @Key("hsmCredenzialiWindow_usernameCFDeleganteTextItem")
  String hsmCredenzialiWindow_usernameCFDeleganteTextItem(); 

  @Key("hsmCredenzialiWindow_caricaCertificatiImgButton")
  String hsmCredenzialiWindow_caricaCertificatiImgButton();

  @Key("hsmCredenzialiWindow_listaCertificatiSelect")
  String hsmCredenzialiWindow_listaCertificatiSelect(); 

  @Key("hsmCredenzialiWindow_passwordTextItem")
  String hsmCredenzialiWindow_passwordTextItem(); 

  @Key("hsmCredenzialiWindow_generazioneRemotaOtpSmsImgButton")
  String hsmCredenzialiWindow_generazioneRemotaOtpSmsImgButton(); 

  @Key("hsmCredenzialiWindow_generazioneRemotaOtpCallImgButton")
  String hsmCredenzialiWindow_generazioneRemotaOtpCallImgButton(); 

  @Key("hsmCredenzialiWindow_otpCodeTextItem")
  String hsmCredenzialiWindow_otpCodeTextItem(); 

  @Key("hsmCredenzialiWindow_okButton")
  String hsmCredenzialiWindow_okButton(); 

  @Key("hsmCredenzialiWindow_annullaButton")
  String hsmCredenzialiWindow_annullaButton(); 
  
  //#############################################################
  //#               Credenziali OTP per firma HSM                   #
  //#############################################################

  @Key("hsmCredenzialiOTPWindow_title")
  String hsmCredenzialiOTPWindow_title(); 
  
  @Key("hsmCredenzialiOTPWindow_usernameTextItem")
  String hsmCredenzialiOTPWindow_usernameTextItem();
  
  @Key("hsmCredenzialiOTPWindow_passwordTextItem")
  String hsmCredenzialiOTPWindow_passwordTextItem(); 
  
  @Key("hsmCredenzialiOTPWindow_okButton")
  String hsmCredenzialiOTPWindow_okButton(); 
  
  @Key("hsmCredenzialiOTPWindow_annullaButton")
  String hsmCredenzialiOTPWindow_annullaButton(); 
  

  //#############################################################
  //#                     GESTIONE UTENTI                       #
  //#############################################################

  @Key("gestioneutenti_clientiSection_title")
  String gestioneutenti_clientiSection_title(); 

  @Key("gestioneutenti_afterAssociaDominio_message")
  String gestioneutenti_afterAssociaDominio_message(); 

  @Key("gestioneutenti_afterDeleteDominio_message")
  String gestioneutenti_afterDeleteDominio_message(); 

  @Key("gestioneutenti_associaDominioError_message")
  String gestioneutenti_associaDominioError_message(); 

  @Key("gestioneutenti_associaaldominio")
  String gestioneutenti_associaaldominio(); 

  @Key("gestioneutenti_attivoIn")
  String gestioneutenti_attivoIn(); 

  @Key("gestioneutenti_dtUltimoAccesso")
  String gestioneutenti_dtUltimoAccesso(); 

  @Key("gestioneutenti_codFiscale")
  String gestioneutenti_codFiscale(); 

  @Key("gestioneutenti_codiceRapido")
  String gestioneutenti_codiceRapido(); 

  @Key("gestioneutenti_cognome")
  String gestioneutenti_cognome(); 

  @Key("gestioneutenti_cognomeNome")
  String gestioneutenti_cognomeNome(); 

  @Key("gestioneutenti_confermiRimuovereDalDominio")
  String gestioneutenti_confermiRimuovereDalDominio(); 

  @Key("gestioneutenti_detail_edit_title")
  String gestioneutenti_detail_edit_title(String attribute0);

  @Key("gestioneutenti_detail_view_title")
  String gestioneutenti_detail_view_title(String attribute0);

  @Key("gestioneutenti_dtFineVld")
  String gestioneutenti_dtFineVld(); 

  @Key("gestioneutenti_dtFineVldDetail")
  String gestioneutenti_dtFineVldDetail(); 

  @Key("gestioneutenti_dtIniVld")
  String gestioneutenti_dtIniVld(); 

  @Key("gestioneutenti_email")
  String gestioneutenti_email(); 

  @Key("gestioneutenti_flgUtenteInternoEsterno")
  String gestioneutenti_flgUtenteInternoEsterno(); 

  @Key("gestioneutenti_gruppoClientiSection_title")
  String gestioneutenti_gruppoClientiSection_title(); 

  @Key("gestioneutenti_gruppoClienti_title")
  String gestioneutenti_gruppoClienti_title(); 

  @Key("gestioneutenti_idProfilo")
  String gestioneutenti_idProfilo(); 
  
  @Key("gestioneutenti_subprofili_title")
  String gestioneutenti_subprofili_title();

  @Key("gestioneutenti_idUser")
  String gestioneutenti_idUser(); 

  @Key("gestioneutenti_importaDaLDAP")
  String gestioneutenti_importaDaLDAP(); 

  @Key("gestioneutenti_importaDaLDAPError_message")
  String gestioneutenti_importaDaLDAPError_message(); 

  @Key("gestioneutenti_importaDaLDAPemailError_message")
  String gestioneutenti_importaDaLDAPemailError_message(); 

  @Key("gestioneutenti_importaDaLDAPusernameError_message")
  String gestioneutenti_importaDaLDAPusernameError_message(); 

  @Key("gestioneutenti_indirizziSection_title")
  String gestioneutenti_indirizziSection_title(); 

  @Key("gestioneutenti_indirizzo")
  String gestioneutenti_indirizzo(); 

  @Key("gestioneutenti_lookupGestioneUtentiPopup_title")
  String gestioneutenti_lookupGestioneUtentiPopup_title(); 


  @Key("gestioneutenti_nome")
  String gestioneutenti_nome(); 

  @Key("gestioneutenti_nroMatricola")
  String gestioneutenti_nroMatricola(); 

  @Key("gestioneutenti_ordFirmatariFFDG_title")
  String gestioneutenti_ordFirmatariFFDG_title(); 

  @Key("gestioneutenti_password")
  String gestioneutenti_password(); 

  @Key("gestioneutenti_profilazioneUtente")
  String gestioneutenti_profilazioneUtente(); 

  @Key("gestioneutenti_puntoVenditaSection_title")
  String gestioneutenti_puntoVenditaSection_title(); 

  @Key("gestioneutenti_qualifica")
  String gestioneutenti_qualifica(); 

  @Key("gestioneutenti_rimuoviDominioError_message")
  String gestioneutenti_rimuoviDominioError_message(); 

  @Key("gestioneutenti_rimuovidaldominio")
  String gestioneutenti_rimuovidaldominio(); 

  @Key("gestioneutenti_societaSection_title")
  String gestioneutenti_societaSection_title(); 

  @Key("gestioneutenti_societa_title")
  String gestioneutenti_societa_title(); 

  @Key("gestioneutenti_titolo")
  String gestioneutenti_titolo(); 

  @Key("gestioneutenti_username")
  String gestioneutenti_username(); 

  @Key("gestioneutenti_visibEmailTransCaselleAssociateUOSection_title")
  String gestioneutenti_visibEmailTransCaselleAssociateUOSection_title(); 

  @Key("gestionneutenti_associataAlDominio")
  String gestionneutenti_associataAlDominio(); 

  @Key("gestionneutenti_confermiAssociareAlDominio")
  String gestionneutenti_confermiAssociareAlDominio(); 
  
  @Key("gestioneutenti_codiceUO")
  String gestioneutenti_codiceUO();

  @Key("gestioneutenti_nomeAppUO")
  String gestioneutenti_nomeAppUO();
  
  @Key("gestioneutenti_duplicaButton_title")
  String gestioneutenti_duplicaButton_title();
  
  @Key("gestioneutenti_afterDuplicaUtente_message")
  String gestioneutenti_afterDuplicaUtente_message(); 
  
  @Key("gestioneutenti_duplicaUtenteError_message")
  String gestioneutenti_duplicaUtenteError_message(); 
  
  @Key("gestioneutenti_flgUtenzaApplicativa_title")
  String gestioneutenti_flgUtenzaApplicativa_title(); 
    
  @Key("gestioneutenti_flgDisattivaNotifDocDaPrendereInCarico_title")
  String gestioneutenti_flgDisattivaNotifDocDaPrendereInCarico_title(); 
  
  //#GestioneUtentiCombo
  @Key("gestioneutenti_combo_mansione_label")
  String gestioneutenti_combo_mansione_label(); 

  @Key("gestioneutenti_combo_mansione_descrizioneMansioneField")
  String gestioneutenti_combo_mansione_descrizioneMansioneField(); 

  @Key("gestioneutenti_combo_lingua_descrizioneLinguaField")
  String gestioneutenti_combo_lingua_descrizioneLinguaField(); 

  @Key("gestioneutenti_combo_lingua_idLinguaField")
  String gestioneutenti_combo_lingua_idLinguaField(); 

  @Key("gestioneutenti_combo_lingua_label")
  String gestioneutenti_combo_lingua_label(); 

  @Key("gestioneutenti_combo_lingua_nomeLinguaField")
  String gestioneutenti_combo_lingua_nomeLinguaField(); 

  @Key("gestioneutenti_combo_loghiDominio_descrizioneLogoField")
  String gestioneutenti_combo_loghiDominio_descrizioneLogoField(); 

  @Key("gestioneutenti_combo_loghiDominio_idLogoField")
  String gestioneutenti_combo_loghiDominio_idLogoField(); 

  @Key("gestioneutenti_combo_loghiDominio_label")
  String gestioneutenti_combo_loghiDominio_label(); 

  @Key("gestioneutenti_combo_loghiDominio_nomeLogoField")
  String gestioneutenti_combo_loghiDominio_nomeLogoField(); 

  @Key("gestioneutenti_combo_societa_denominazioneItem_title")
  String gestioneutenti_combo_societa_denominazioneItem_title(); 

  @Key("gestioneutenti_combo_gruppoclienti_denominazioneItem_title")
  String gestioneutenti_combo_gruppoclienti_denominazioneItem_title(); 

  @Key("gestioneutenti_combo_areecommerciali_denominazioneItem_title")
  String gestioneutenti_combo_areecommerciali_denominazioneItem_title(); 

  @Key("gestioneutenti_combo_societa_title")
  String gestioneutenti_combo_societa_title(); 

  @Key("gestioneutenti_combo_gruppoclienti_title")
  String gestioneutenti_combo_gruppoclienti_title(); 

  @Key("gestioneutenti_combo_areecommerciali_title")
  String gestioneutenti_combo_areecommerciali_title(); 

  @Key("gestioneutenti_confermaPassword")
  String gestioneutenti_confermaPassword(); 


  //#GestioneUtentiList
  @Key("gestioneutenti_clienti_list_denominazioneSocietaField")
  String gestioneutenti_clienti_list_denominazioneSocietaField(); 

  @Key("gestioneutenti_clienti_list_denominazioneClienteField")
  String gestioneutenti_clienti_list_denominazioneClienteField(); 

  @Key("gestioneutenti_clienti_list_codfiscaleSoggettoField")
  String gestioneutenti_clienti_list_codfiscaleSoggettoField(); 

  @Key("gestioneutenti_clienti_list_cidField")
  String gestioneutenti_clienti_list_cidField(); 

  @Key("gestioneutenti_clienti_list_billingAccountField")
  String gestioneutenti_clienti_list_billingAccountField(); 

  @Key("gestioneutenti_clienti_list_gruppoDiRiferimentoField")
  String gestioneutenti_clienti_list_gruppoDiRiferimentoField(); 
  //CR Visualizzazione di documenti e fascicoli assegnati/inviati in copia alla struttura 13/10/2019
  @Key("visualizzaDocumentiFascicoliStruttura_title")
  String visualizzaDocumentiFascicoliStruttura_title();
  @Key("modificaDocumentiFascicoliStruttura_title")
  String modificaDocumentiFascicoliStruttura_title();
  @Key("flgIncluseScrivanie_title")
  String flgIncluseScrivanie_title(); 
  
  
  //# UO associate all'utente
  @Key("gestioneutenti_uoAssociateUtente_flgAccessoDocLimSVItem_title")
  String gestioneutenti_uoAssociateUtente_flgAccessoDocLimSVItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_flgRegistrazioneEItem_title")
  String gestioneutenti_uoAssociateUtente_flgRegistrazioneEItem_title(); 

  
  @Key("gestioneutenti_uoAssociateUtente_flgRegistrazioneUIItem_title")
  String gestioneutenti_uoAssociateUtente_flgRegistrazioneUIItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_flgGestAttiItem_title")
  String gestioneutenti_uoAssociateUtente_flgGestAttiItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_flgVisPropAttiInIterItem_title")
  String gestioneutenti_uoAssociateUtente_flgVisPropAttiInIterItem_title(); 
  
  @Key("gestioneutenti_uoAssociateUtente_flgRiservatezzaRelUserUoItem_title")
  String gestioneutenti_uoAssociateUtente_flgRiservatezzaRelUserUoItem_title(); 
  
  @Key("gestioneutenti_uoAssociateUtente_uoPuntoProtocolloItem_title")
  String gestioneutenti_uoAssociateUtente_uoPuntoProtocolloItem_title(); 
  
  
  @Key("gestioneutenti_uoAssociateUtenteSection_title")
  String gestioneutenti_uoAssociateUtenteSection_title(); 

  @Key("gestioneutenti_uoAssociateUtente_codRapidoItem_title")
  String gestioneutenti_uoAssociateUtente_codRapidoItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_denominazioneUoItem_title")
  String gestioneutenti_uoAssociateUtente_denominazioneUoItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_descTipoRelazioneItem_title")
  String gestioneutenti_uoAssociateUtente_descTipoRelazioneItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_dtInizioVldItem_title")
  String gestioneutenti_uoAssociateUtente_dtInizioVldItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_dtFineVldItem_title")
  String gestioneutenti_uoAssociateUtente_dtFineVldItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_ruoloItem_title")
  String gestioneutenti_uoAssociateUtente_ruoloItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_flgIncluseSottoUoItem_title")
  String gestioneutenti_uoAssociateUtente_flgIncluseSottoUoItem_title(); 

  @Key("gestioneutenti_uoAssociateUtente_flgIncluseScrivanieItem_title")
  String gestioneutenti_uoAssociateUtente_flgIncluseScrivanieItem_title(); 

  
  @Key("gestioneutenti_uoAssociateUtente_uoPuntoProtocolloIcon_title")
  String gestioneutenti_uoAssociateUtente_uoPuntoProtocolloIcon_title(); 

  
  @Key("gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title")
  String gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title(); 

  
  @Key("gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_prompt")
  String gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_prompt(); 

  
  @Key("gestioneutenti_uoAssociateUtente_deleteButtonAsk_message")
  String gestioneutenti_uoAssociateUtente_deleteButtonAsk_message(); 

  
  
  
  //# Popup relazione UO-utente   
  @Key("agganciautenteuopopup_uoAssociateUtenteSection_title")
  String agganciautenteuopopup_uoAssociateUtenteSection_title(); 

  
  
  //#Lista delle applicazioni esterne o loro istanze in cui è accreditato
  @Key("gestioneutenti_applEstAccreditateSection_title")
  String gestioneutenti_applEstAccreditateSection_title(); 

  @Key("gestioneutenti_applEstAccred_codiceApplEstItem_title")
  String gestioneutenti_applEstAccred_codiceApplEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_codiceIstApplItem_title")
  String gestioneutenti_applEstAccred_codiceIstApplItem_title(); 

  @Key("gestioneutenti_applEstAccred_denominazioneApplEstItem_title")
  String gestioneutenti_applEstAccred_denominazioneApplEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_idUtenteApplEstItem_title")
  String gestioneutenti_applEstAccred_idUtenteApplEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_usernameUtenteApplEstItem_title")
  String gestioneutenti_applEstAccred_usernameUtenteApplEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_idUoApplEstItem_title")
  String gestioneutenti_applEstAccred_idUoApplEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_nriLivelliApplEstItem_title")
  String gestioneutenti_applEstAccred_nriLivelliApplEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_denominazioneUoApplEstItem_title")
  String gestioneutenti_applEstAccred_denominazioneUoApplEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_flgUsaCredenzialiDiverseAurigaItem_title")
  String gestioneutenti_applEstAccred_flgUsaCredenzialiDiverseAurigaItem_title(); 

  @Key("gestioneutenti_applEstAccred_uoPerRegDocItem_title")
  String gestioneutenti_applEstAccred_uoPerRegDocItem_title(); 

  @Key("gestioneutenti_applEstAccred_codiceApplIstEstItem_title")
  String gestioneutenti_applEstAccred_codiceApplIstEstItem_title(); 

  @Key("gestioneutenti_applEstAccred_deleteButtonAsk_message")
  String gestioneutenti_applEstAccred_deleteButtonAsk_message(); 



  //#############################################################
  //#                       GRUPPI SOGGETTI                     #
  //#############################################################

  @Key("gruppisoggetti_annullamentoLogicoAsk_message")
  String gruppisoggetti_annullamentoLogicoAsk_message(); 

  @Key("gruppisoggetti_eliminazioneFisicaAsk_message")
  String gruppisoggetti_eliminazioneFisicaAsk_message(); 

  @Key("gruppisoggetti_flgCreatodame_0_value")
  String gruppisoggetti_flgCreatodame_0_value(); 

  @Key("gruppisoggetti_flgCreatodame_1_value")
  String gruppisoggetti_flgCreatodame_1_value(); 

  @Key("gruppisoggetti_flgSoggettiInterni_1_value")
  String gruppisoggetti_flgSoggettiInterni_1_value(); 

  @Key("gruppisoggetti_flgValido_0_value")
  String gruppisoggetti_flgValido_0_value(); 

  @Key("gruppisoggetti_flgValido_1_value")
  String gruppisoggetti_flgValido_1_value(); 

  @Key("gruppisoggetti_lookupGruppiPopup_title")
  String gruppisoggetti_lookupGruppiPopup_title(); 

  @Key("gruppisoggetti_recProtetto_0_value")
  String gruppisoggetti_recProtetto_0_value(); 

  @Key("gruppisoggetti_recProtetto_1_value")
  String gruppisoggetti_recProtetto_1_value(); 


  //#GruppiSoggettiDetail
  @Key("gruppisoggetti_detail_codiceRapidoItem_title")
  String gruppisoggetti_detail_codiceRapidoItem_title(); 

  @Key("gruppisoggetti_detail_dataInsItem_title")
  String gruppisoggetti_detail_dataInsItem_title(); 

  @Key("gruppisoggetti_detail_dataUltModItem_title")
  String gruppisoggetti_detail_dataUltModItem_title(); 

  @Key("gruppisoggetti_detail_datiPrincipaliSection_title")
  String gruppisoggetti_detail_datiPrincipaliSection_title(); 

  @Key("gruppisoggetti_detail_descUtenteInsItem_title")
  String gruppisoggetti_detail_descUtenteInsItem_title(); 

  @Key("gruppisoggetti_detail_descUtenteUltModItem_title")
  String gruppisoggetti_detail_descUtenteUltModItem_title(); 

  @Key("gruppisoggetti_detail_dtValiditaAItem_title")
  String gruppisoggetti_detail_dtValiditaAItem_title(); 

  @Key("gruppisoggetti_detail_dtValiditaDaItem_title")
  String gruppisoggetti_detail_dtValiditaDaItem_title(); 

  @Key("gruppisoggetti_detail_edit_title")
  String gruppisoggetti_detail_edit_title(String attribute0);

  @Key("gruppisoggetti_detail_flgCreatodameItem_title")
  String gruppisoggetti_detail_flgCreatodameItem_title(); 

  @Key("gruppisoggetti_detail_flgSoggettiInterniItem_title")
  String gruppisoggetti_detail_flgSoggettiInterniItem_title(); 

  @Key("gruppisoggetti_detail_flgValidoItem_title")
  String gruppisoggetti_detail_flgValidoItem_title(); 

  @Key("gruppisoggetti_detail_idItem_title")
  String gruppisoggetti_detail_idItem_title(); 

  @Key("gruppisoggetti_detail_multilookupMembriButton_prompt")
  String gruppisoggetti_detail_multilookupMembriButton_prompt(); 

  @Key("gruppisoggetti_detail_multilookupRubricaButton_prompt")
  String gruppisoggetti_detail_multilookupRubricaButton_prompt(); 

  @Key("gruppisoggetti_detail_new_title")
  String gruppisoggetti_detail_new_title(); 

  @Key("gruppisoggetti_detail_nomeItem_title")
  String gruppisoggetti_detail_nomeItem_title(); 

  @Key("gruppisoggetti_detail_flgInibitaAssItem_title")
  String gruppisoggetti_detail_flgInibitaAssItem_title(); 

  @Key("gruppisoggetti_detail_recProtettoItem_title")
  String gruppisoggetti_detail_recProtettoItem_title(); 

  @Key("gruppisoggetti_detail_soggettiSection_title")
  String gruppisoggetti_detail_soggettiSection_title(); 

  @Key("gruppisoggetti_detail_tabGruppo_title")
  String gruppisoggetti_detail_tabGruppo_title(); 

  @Key("gruppisoggetti_detail_tabSoggetti_title")
  String gruppisoggetti_detail_tabSoggetti_title(); 

  @Key("gruppisoggetti_detail_tipoMembroGruppoAlt_value")
  String gruppisoggetti_detail_tipoMembroGruppoAlt_value(); 

  @Key("gruppisoggetti_detail_tipoMembroSoggettoAlt_value")
  String gruppisoggetti_detail_tipoMembroSoggettoAlt_value(); 

  @Key("gruppisoggetti_detail_validitaSection_title")
  String gruppisoggetti_detail_validitaSection_title(); 

  @Key("gruppisoggetti_detail_view_title")
  String gruppisoggetti_detail_view_title(String attribute0);


  //#GruppiSoggettiList
  @Key("gruppisoggetti_list_codiceRapidoField_title")
  String gruppisoggetti_list_codiceRapidoField_title(); 

  @Key("gruppisoggetti_list_dataInsField_title")
  String gruppisoggetti_list_dataInsField_title(); 

  @Key("gruppisoggetti_list_dataUltModField_title")
  String gruppisoggetti_list_dataUltModField_title(); 

  @Key("gruppisoggetti_list_descUtenteInsField_title")
  String gruppisoggetti_list_descUtenteInsField_title(); 

  @Key("gruppisoggetti_list_descUtenteUltModField_title")
  String gruppisoggetti_list_descUtenteUltModField_title(); 

  @Key("gruppisoggetti_list_dtValiditaAField_title")
  String gruppisoggetti_list_dtValiditaAField_title(); 

  @Key("gruppisoggetti_list_dtValiditaDaField_title")
  String gruppisoggetti_list_dtValiditaDaField_title(); 

  @Key("gruppisoggetti_list_flgCreatodameField_title")
  String gruppisoggetti_list_flgCreatodameField_title(); 

  @Key("gruppisoggetti_list_flgSoggettiInterniField_title")
  String gruppisoggetti_list_flgSoggettiInterniField_title(); 

  @Key("gruppisoggetti_list_flgValidoField_title")
  String gruppisoggetti_list_flgValidoField_title(); 

  @Key("gruppisoggetti_list_idField_title")
  String gruppisoggetti_list_idField_title(); 

  @Key("gruppisoggetti_list_nomeField_title")
  String gruppisoggetti_list_nomeField_title(); 

  @Key("gruppisoggetti_list_recProtettoField_title")
  String gruppisoggetti_list_recProtettoField_title(); 
  
  
  //#############################################################
  //#                     INVII EFFETTUATI                      #
  //#############################################################

  @Key("invii_effettuati_list_flgInvioNotifica")
  String invii_effettuati_list_flgInvioNotifica();
  
  @Key("invii_effettuati_list_desUteInvioNotifica")
  String invii_effettuati_list_desUteInvioNotifica();
  
  @Key("invii_effettuati_list_tsInvioNotifica")
  String invii_effettuati_list_tsInvioNotifica();
  
  @Key("invii_effettuati_list_desMittente")
  String invii_effettuati_list_desMittente();
  
  @Key("invii_effettuati_list_desDestinatario")
  String invii_effettuati_list_desDestinatario();
  
  @Key("invii_effettuati_list_desMotivo")
  String invii_effettuati_list_desMotivo();
  
  @Key("invii_effettuati_list_messaggio")
  String invii_effettuati_list_messaggio();
  
  @Key("invii_effettuati_list_livPriorita")
  String invii_effettuati_list_livPriorita();
  
  @Key("invii_effettuati_list_iconaFlgAnnullabile")
  String invii_effettuati_list_iconaFlgAnnullabile();



  //#############################################################
  //#             INVIO DOCUMENTAZIONE TRAMITE PEC              #
  //#############################################################

  @Key("invio_documentazione_via_pec_prompt")
  String invio_documentazione_via_pec_prompt(); 

  @Key("invio_documentazione_via_pec_title")
  String invio_documentazione_via_pec_title(); 

  @Key("invio_documentazione_via_pec_idRichiesta")
  String invio_documentazione_via_pec_idRichiesta(); 

  @Key("invio_documentazione_via_pec_applicazione_richiedente")
  String invio_documentazione_via_pec_applicazione_richiedente(); 

  @Key("invio_documentazione_via_pec_docType")
  String invio_documentazione_via_pec_docType(); 

  @Key("invio_documentazione_via_pec_xmlRequest")
  String invio_documentazione_via_pec_xmlRequest(); 

  @Key("invio_documentazione_via_pec_stato_richiesta")
  String invio_documentazione_via_pec_stato_richiesta(); 

  @Key("invio_documentazione_via_pec_errore")
  String invio_documentazione_via_pec_errore(); 

  @Key("invio_documentazione_via_pec_richiesta_del")
  String invio_documentazione_via_pec_richiesta_del(); 

  @Key("invio_documentazione_via_pec_email_inviata_il")
  String invio_documentazione_via_pec_email_inviata_il(); 

  @Key("invio_documentazione_via_pec_dettaglio_email")
  String invio_documentazione_via_pec_dettaglio_email(); 

  @Key("invio_documentazione_via_pec_list_idrichiesta")
  String invio_documentazione_via_pec_list_idrichiesta(); 

  @Key("invio_documentazione_via_pec_list_applrich")
  String invio_documentazione_via_pec_list_applrich(); 

  @Key("invio_documentazione_via_pec_list_descrizioneappl")
  String invio_documentazione_via_pec_list_descrizioneappl(); 

  @Key("invio_documentazione_via_pec_list_tipodocinv")
  String invio_documentazione_via_pec_list_tipodocinv(); 

  @Key("invio_documentazione_via_pec_list_decodificatipodoc")
  String invio_documentazione_via_pec_list_decodificatipodoc(); 

  @Key("invio_documentazione_via_pec_list_statorich")
  String invio_documentazione_via_pec_list_statorich(); 

  @Key("invio_documentazione_via_pec_list_errormessage")
  String invio_documentazione_via_pec_list_errormessage(); 

  @Key("invio_documentazione_via_pec_list_richdel")
  String invio_documentazione_via_pec_list_richdel(); 

  @Key("invio_documentazione_via_pec_list_emailinvil")
  String invio_documentazione_via_pec_list_emailinvil(); 

  @Key("invio_documentazione_via_pec_view_detail")
  String invio_documentazione_via_pec_view_detail(String attribute0);



  //#############################################################
  //#                         INVIO NOTIFICA                    #
  //#############################################################	

  @Key("invionotificainterop_esitoInvio_OK_value")
  String invionotificainterop_esitoInvio_OK_value(); 

  @Key("invionotificainterop_invioButton_title")
  String invionotificainterop_invioButton_title(); 

  @Key("invionotificainteropform_allegatiItem_title")
  String invionotificainteropform_allegatiItem_title(); 

  @Key("invionotificainteropform_destinatariCCItem_title")
  String invionotificainteropform_destinatariCCItem_title(); 

  @Key("invionotificainteropform_destinatariCCNItem_title")
  String invionotificainteropform_destinatariCCNItem_title(); 

  @Key("invionotificainteropform_destinatariItem_title")
  String invionotificainteropform_destinatariItem_title(); 

  @Key("invionotificainteropform_destinatariValidatorErrorMessage")
  String invionotificainteropform_destinatariValidatorErrorMessage(); 

  @Key("invionotificainteropform_mittenteItem_title")
  String invionotificainteropform_mittenteItem_title(); 

  @Key("invionotificainteropform_oggettoItem_title")
  String invionotificainteropform_oggettoItem_title(); 

  @Key("invionotificainteropform_salvaInviatiItem_title")
  String invionotificainteropform_salvaInviatiItem_title(); 

  @Key("invionotificainteropform_styleItem_title")
  String invionotificainteropform_styleItem_title(); 

  @Key("invionotificainteropform_styleMap_HTML_value")
  String invionotificainteropform_styleMap_HTML_value(); 

  @Key("invionotificainteropform_styleMap_TEXT_value")
  String invionotificainteropform_styleMap_TEXT_value(); 

  @Key("invionotificainteropwindow_title")
  String invionotificainteropwindow_title(String attribute0);



  //#############################################################
  //#                          ISTANZE PORTALE                  #
  //#############################################################

  //#InstanzedaIstruire
  @Key("istanze_portale_riscossione_da_istruire_nrIstanzaField_title")
  String istanze_portale_riscossione_da_istruire_nrIstanzaField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_tsIstanzaField_title")
  String istanze_portale_riscossione_da_istruire_tsIstanzaField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_tipoIstanzaField_title")
  String istanze_portale_riscossione_da_istruire_tipoIstanzaField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_oggettoField_title")
  String istanze_portale_riscossione_da_istruire_oggettoField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_nrAllegatiField_title")
  String istanze_portale_riscossione_da_istruire_nrAllegatiField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_tributoField_title")
  String istanze_portale_riscossione_da_istruire_tributoField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_annoRiferimentoField_title")
  String istanze_portale_riscossione_da_istruire_annoRiferimentoField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_numeroDocRiferimentoField_title")
  String istanze_portale_riscossione_da_istruire_numeroDocRiferimentoField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_classificazioneField_title")
  String istanze_portale_riscossione_da_istruire_classificazioneField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_nomeContribuenteField_title")
  String istanze_portale_riscossione_da_istruire_nomeContribuenteField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_cfContribuenteField_title")
  String istanze_portale_riscossione_da_istruire_cfContribuenteField_title(); 

  @Key("istanze_portale_riscossione_da_istruire_codACSContribuenteField_title")
  String istanze_portale_riscossione_da_istruire_codACSContribuenteField_title(); 
  
  @Key("istanze_portale_riscossione_da_istruire_mezzoTrasmissioneField_title")
  String istanze_portale_riscossione_da_istruire_mezzoTrasmissioneField_title();

  @Key("istanze_portale_riscossione_da_istruire_detail_new_title")
  String istanze_portale_riscossione_da_istruire_detail_new_title(); 

  @Key("istanze_portale_riscossione_da_istruire_detail_edit_title")
  String istanze_portale_riscossione_da_istruire_detail_edit_title(String attribute0);

  @Key("istanze_portale_riscossione_da_istruire_detail_view_title")
  String istanze_portale_riscossione_da_istruire_detail_view_title(String attribute0);


  //#InstanzeFilter
  @Key("istanze_portale_riscossione_filter_nrIstanza_title")
  String istanze_portale_riscossione_filter_nrIstanza_title(); 

  @Key("istanze_portale_riscossione_filter_tsIstanza_title")
  String istanze_portale_riscossione_filter_tsIstanza_title(); 

  @Key("istanze_portale_riscossione_filter_tipoIstanza_title")
  String istanze_portale_riscossione_filter_tipoIstanza_title(); 

  @Key("istanze_portale_riscossione_filter_annoRiferimento_title")
  String istanze_portale_riscossione_filter_annoRiferimento_title(); 

  @Key("istanze_portale_riscossione_filter_tributo_title")
  String istanze_portale_riscossione_filter_tributo_title(); 

  @Key("istanze_portale_riscossione_filter_classificazionePortale_title")
  String istanze_portale_riscossione_filter_classificazionePortale_title(); 

  @Key("istanze_portale_riscossione_filter_numeroDocRiferimento_title")
  String istanze_portale_riscossione_filter_numeroDocRiferimento_title(); 

  @Key("istanze_portale_riscossione_filter_oggetto_title")
  String istanze_portale_riscossione_filter_oggetto_title(); 

  @Key("istanze_portale_riscossione_filter_nomeContribuente_title")
  String istanze_portale_riscossione_filter_nomeContribuente_title(); 

  @Key("istanze_portale_riscossione_filter_cfContribuente_title")
  String istanze_portale_riscossione_filter_cfContribuente_title(); 

  @Key("istanze_portale_riscossione_filter_codACSContribuente_title")
  String istanze_portale_riscossione_filter_codACSContribuente_title(); 
  
  @Key("istanze_portale_riscossione_filter_mezzoTrasmissione_title")
  String istanze_portale_riscossione_filter_mezzoTrasmissione_title();

  @Key("istanze_portale_riscossione_da_istruire_avviaIstruttoriaMultiButton")
  String istanze_portale_riscossione_da_istruire_avviaIstruttoriaMultiButton();
  
  @Key("istanze_portale_riscossione_da_istruire_assegnazioneAltroUfficioMultiButton")
  String istanze_portale_riscossione_da_istruire_assegnazioneAltroUfficioMultiButton();
  
  @Key("istanze_portale_riscossione_da_istruire_restituzioneSmistatoreMultiButton")
  String istanze_portale_riscossione_da_istruire_restituzioneSmistatoreMultiButton();
  
  @Key("istanze_portale_riscossione_da_istruire_assegnaAltroUfficioMultiButton")
  String istanze_portale_riscossione_da_istruire_assegnaAltroUfficioMultiButton();

  @Key("istanze_portale_riscossione_da_istruire_visualizzaFilePrimarioMenuItem_title")
  String istanze_portale_riscossione_da_istruire_visualizzaFilePrimarioMenuItem_title(); 

  @Key("istanze_portale_riscossione_da_istruire_visualizzaAllegatiMenuItem_title")
  String istanze_portale_riscossione_da_istruire_visualizzaAllegatiMenuItem_title(); 


  //#IstanzeDetail
  @Key("istanze_detail_tabDatiPrincipali_title")
  String istanze_detail_tabDatiPrincipali_title(); 

  @Key("istanze_detail_tabDatiPrincipali_prompt")
  String istanze_detail_tabDatiPrincipali_prompt(); 



  //#############################################################
  //#                   ITEM IN LAVORAZIONE                     #
  //#############################################################

  @Key("item_inlavorazione_itemLavNumProgressivo")
  String item_inlavorazione_itemLavNumProgressivo(); 

  @Key("item_inlavorazione_itemLavCaricatoDa")
  String item_inlavorazione_itemLavCaricatoDa(); 

  @Key("item_inlavorazione_itemLavDataOraCaricamento")
  String item_inlavorazione_itemLavDataOraCaricamento(); 

  @Key("item_inlavorazione_itemLavModificatoDa")
  String item_inlavorazione_itemLavModificatoDa(); 

  @Key("item_inlavorazione_itemLavDataOraModifica")
  String item_inlavorazione_itemLavDataOraModifica(); 

  @Key("item_inlavorazione_itemLavTag")
  String item_inlavorazione_itemLavTag(); 

  @Key("item_inlavorazione_itemLavCommento")
  String item_inlavorazione_itemLavCommento(); 

  @Key("item_inlavorazione_itemLavNomeFile")
  String item_inlavorazione_itemLavNomeFile(); 



  //#############################################################
  //#    	   LISTA ACCOMPAGNATORIA PROTOCOLLAZIONI        	#
  //#############################################################

  @Key("listaAccompagnatoriaProtocollazioniwindow_title")
  String listaAccompagnatoriaProtocollazioniwindow_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_flgProtSoloMie_title")
  String listaAccompagnatoriaProtocollazioni_detail_flgProtSoloMie_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_flgProtUtenteSelezionato_title")
  String listaAccompagnatoriaProtocollazioni_detail_flgProtUtenteSelezionato_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_flgProtDiTutti_title")
  String listaAccompagnatoriaProtocollazioni_detail_flgProtDiTutti_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_dtInizio_title")
  String listaAccompagnatoriaProtocollazioni_detail_dtInizio_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_dtFine_title")
  String listaAccompagnatoriaProtocollazioni_detail_dtFine_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_flgDestinatariTutti_title")
  String listaAccompagnatoriaProtocollazioni_detail_flgDestinatariTutti_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_flgDestinatariSelezionati_title")
  String listaAccompagnatoriaProtocollazioni_detail_flgDestinatariSelezionati_title(); 

  @Key("listaAccompagnatoriaProtocollazioni_detail_detailSectionSceltaTitle")
  String listaAccompagnatoriaProtocollazioni_detail_detailSectionSceltaTitle();
  
  @Key("listaAccompagnatoriaProtocollazioni_detail_detailSectionPeriodoTitle")
  String listaAccompagnatoriaProtocollazioni_detail_detailSectionPeriodoTitle();
  
  @Key("listaAccompagnatoriaProtocollazioni_detail_detailSectionDestinatariTitle")
  String listaAccompagnatoriaProtocollazioni_detail_detailSectionDestinatariTitle();

  //#############################################################
  //#                LISTA FUNZIONALITA                         #
  //#############################################################

  @Key("lista_funzionalita_window_title")
  String lista_funzionalita_window_title(); 

  @Key("lista_funzionalita_codFunzP1")
  String lista_funzionalita_codFunzP1(); 

  @Key("lista_funzionalita_codFunzP2")
  String lista_funzionalita_codFunzP2(); 

  @Key("lista_funzionalita_codFunzP3")
  String lista_funzionalita_codFunzP3(); 

  @Key("lista_funzionalita_codice")
  String lista_funzionalita_codice(); 

  @Key("lista_funzionalita_descrizione")
  String lista_funzionalita_descrizione(); 

  @Key("lista_funzionalita_flgSoloDisp")
  String lista_funzionalita_flgSoloDisp(); 

  
  @Key("lista_funzionalita_lookupListaFunzionalitaPopup_title")
  String lista_funzionalita_lookupListaFunzionalitaPopup_title(); 

  

  //#############################################################
  //#                          MENU                             #
  //#############################################################

/*  @Key("menu_procedimenti_in_lavorazione_prompt")
  String menu_procedimenti_in_lavorazione_prompt();*/ 

/*  @Key("menu_procedimenti_in_lavorazione_title")
  String menu_procedimenti_in_lavorazione_title();*/ 

  @Key("menu_altre_registrazioni_numerazioni_prompt")
  String menu_altre_registrazioni_numerazioni_prompt(); 

  @Key("menu_altre_registrazioni_numerazioni_title")
  String menu_altre_registrazioni_numerazioni_title(); 

  @Key("menu_anagrafe_procedimenti_prompt")
  String menu_anagrafe_procedimenti_prompt(); 

  @Key("menu_anagrafe_procedimenti_title")
  String menu_anagrafe_procedimenti_title(); 

  @Key("menu_anagrafiche_prompt")
  String menu_anagrafiche_prompt(); 

  @Key("menu_anagrafiche_rubricaemail_prompt")
  String menu_anagrafiche_rubricaemail_prompt(); 

  @Key("menu_anagrafiche_rubricaemail_title")
  String menu_anagrafiche_rubricaemail_title(); 

  @Key("menu_anagrafiche_rubricamail_prompt")
  String menu_anagrafiche_rubricamail_prompt(); 

  @Key("menu_anagrafiche_rubricamail_title")
  String menu_anagrafiche_rubricamail_title(); 

  @Key("menu_anagrafiche_soggetti_prompt")
  String menu_anagrafiche_soggetti_prompt(); 

  @Key("menu_anagrafiche_soggetti_title")
  String menu_anagrafiche_soggetti_title(); 

  @Key("menu_anagrafiche_title")
  String menu_anagrafiche_title(); 

  @Key("menu_anagrafiche_topografico_prompt")
  String menu_anagrafiche_topografico_prompt(); 

  @Key("menu_anagrafiche_topografico_title")
  String menu_anagrafiche_topografico_title(); 

  @Key("menu_archivio_prompt")
  String menu_archivio_prompt(); 

  @Key("menu_archivio_title")
  String menu_archivio_title(); 
  
  @Key("libro_firma_prompt")
  String libro_firma_prompt(); 

  @Key("libro_firma_title")
  String libro_firma_title(); 

  @Key("menu_atti_autorizzazione_ann_reg_prompt")
  String menu_atti_autorizzazione_ann_reg_prompt(); 

  @Key("menu_atti_autorizzazione_ann_reg_title")
  String menu_atti_autorizzazione_ann_reg_title(); 

  @Key("menu_atti_in_lavorazione_prompt")
  String menu_atti_in_lavorazione_prompt(); 

  @Key("menu_atti_in_lavorazione_title")
  String menu_atti_in_lavorazione_title(); 

  @Key("menu_atti_personali_prompt")
  String menu_atti_personali_prompt(); 

  @Key("menu_atti_personali_title")
  String menu_atti_personali_title(); 
  
  @Key("menu_atti_completi_in_lavorazione_title")
  String menu_atti_completi_in_lavorazione_title();
  
  @Key("menu_atti_completi_in_lavorazione_prompt")
  String menu_atti_completi_in_lavorazione_prompt();
  
  @Key("menu_atti_completi_personali_title")
  String menu_atti_completi_personali_title();
  
  @Key("menu_atti_completi_personali_prompt")
  String menu_atti_completi_personali_prompt();

  @Key("menu_attributi_custom_prompt")
  String menu_attributi_custom_prompt(); 

  @Key("menu_attributi_custom_title")
  String menu_attributi_custom_title(); 

  @Key("menu_attributi_dinamici_prompt")
  String menu_attributi_dinamici_prompt(); 

  @Key("menu_attributi_dinamici_title")
  String menu_attributi_dinamici_title(); 

  @Key("menu_avvio_iter_atti_prompt")
  String menu_avvio_iter_atti_prompt(); 

  @Key("menu_avvio_iter_atti_title")
  String menu_avvio_iter_atti_title(); 

  @Key("menu_avvio_procedimento_prompt")
  String menu_avvio_procedimento_prompt(); 

  @Key("menu_avvio_procedimento_title")
  String menu_avvio_procedimento_title(); 

  @Key("menu_cambiopwd_prompt")
  String menu_cambiopwd_prompt(); 

  @Key("menu_cambiopwd_title")
  String menu_cambiopwd_title(); 

  @Key("menu_caricamento_rubrica_prompt")
  String menu_caricamento_rubrica_prompt(); 

  @Key("menu_caricamento_rubrica_title")
  String menu_caricamento_rubrica_title(); 

  @Key("menu_caselle_email_prompt")
  String menu_caselle_email_prompt(); 

  @Key("menu_caselle_email_title")
  String menu_caselle_email_title(); 

  @Key("menu_configurazione_contesto_procedurale_prompt")
  String menu_configurazione_contesto_procedurale_prompt(); 

  @Key("menu_configurazione_contesto_procedurale_title")
  String menu_configurazione_contesto_procedurale_title(); 

  @Key("menu_configurazione_flussi_modellati_prompt")
  String menu_configurazione_flussi_modellati_prompt(); 

  @Key("menu_configurazione_flussi_modellati_title")
  String menu_configurazione_flussi_modellati_title(); 

  @Key("menu_conservazione_prompt")
  String menu_conservazione_prompt(); 

  @Key("menu_conservazione_title")
  String menu_conservazione_title(); 

  @Key("menu_contratti_prompt")
  String menu_contratti_prompt(); 

  @Key("menu_contratti_title")
  String menu_contratti_title(); 

  @Key("menu_definizione_attivita_procedimenti_prompt")
  String menu_definizione_attivita_procedimenti_prompt(); 

  @Key("menu_definizione_attivita_procedimenti_title")
  String menu_definizione_attivita_procedimenti_title(); 

  @Key("menu_deleghe_attivadisattiva_prompt")
  String menu_deleghe_attivadisattiva_prompt(); 

  @Key("menu_deleghe_attivadisattiva_title")
  String menu_deleghe_attivadisattiva_title(); 

  @Key("menu_deleghe_definizione_prompt")
  String menu_deleghe_definizione_prompt(); 

  @Key("menu_deleghe_definizione_title")
  String menu_deleghe_definizione_title(); 

  @Key("menu_deleghe_prompt")
  String menu_deleghe_prompt(); 

  @Key("menu_deleghe_title")
  String menu_deleghe_title(); 

  @Key("menu_elenchi_albi_prompt")
  String menu_elenchi_albi_prompt(); 

  @Key("menu_elenchi_albi_title")
  String menu_elenchi_albi_title(); 

  @Key("menu_gestione_atti_prompt")
  String menu_gestione_atti_prompt(); 

  @Key("menu_gestione_atti_title")
  String menu_gestione_atti_title(); 
  
  @Key("menu_gestione_visure_title")
  String menu_gestione_visure_title();
  
  @Key("menu_gestione_visure_prompt")
  String menu_gestione_visure_prompt();

  @Key("menu_gestione_procedimenti_prompt")
  String menu_gestione_procedimenti_prompt(); 

  @Key("menu_gestione_procedimenti_title")
  String menu_gestione_procedimenti_title(); 
  
  @Key("menu_gestione_tso_title")
  String menu_gestione_tso_title();
  
  @Key("menu_gestione_tso_prompt")
  String menu_gestione_tso_prompt();

  @Key("menu_gestioneutenti_prompt")
  String menu_gestioneutenti_prompt(); 

  @Key("menu_gestioneutenti_title")
  String menu_gestioneutenti_title(); 

  @Key("menu_gruppi_soggetti_prompt")
  String menu_gruppi_soggetti_prompt(); 

  @Key("menu_gruppi_soggetti_title")
  String menu_gruppi_soggetti_title(); 

  @Key("menu_info_prompt")
  String menu_info_prompt(); 

  @Key("menu_info_title")
  String menu_info_title(); 

  @Key("menu_istanze_autotutela_prompt")
  String menu_istanze_autotutela_prompt(); 

  @Key("menu_istanze_autotutela_title")
  String menu_istanze_autotutela_title(); 

  @Key("menu_istanze_ced_prompt")
  String menu_istanze_ced_prompt(); 

  @Key("menu_istanze_ced_title")
  String menu_istanze_ced_title(); 

  @Key("menu_istanze_portale_riscossione_da_istruire_prompt")
  String menu_istanze_portale_riscossione_da_istruire_prompt(); 

  @Key("menu_istanze_portale_riscossione_da_istruire_title")
  String menu_istanze_portale_riscossione_da_istruire_title(); 

  @Key("menu_istanze_prompt")
  String menu_istanze_prompt(); 

  @Key("menu_istanze_title")
  String menu_istanze_title(); 

  @Key("menu_listaAccompagnatoriaProtocollazioni_prompt")
  String menu_listaAccompagnatoriaProtocollazioni_prompt(); 

  @Key("menu_listaAccompagnatoriaProtocollazioni_title")
  String menu_listaAccompagnatoriaProtocollazioni_title(); 

  @Key("menu_modellatore_processi_prompt")
  String menu_modellatore_processi_prompt(); 

  @Key("menu_modellatore_processi_title")
  String menu_modellatore_processi_title(); 

  @Key("menu_numerazione_provvisoria_prompt")
  String menu_numerazione_provvisoria_prompt(); 

  @Key("menu_numerazione_provvisoria_title")
  String menu_numerazione_provvisoria_title(); 
  
  @Key("menu_registrazione_multipla_uscita_prompt")
  String menu_registrazione_multipla_uscita_prompt(); 
  
  @Key("menu_registrazione_multipla_uscita_title")
  String menu_registrazione_multipla_uscita_title(); 

  @Key("menu_oggettario_prompt")
  String menu_oggettario_prompt(); 

  @Key("menu_oggettario_title")
  String menu_oggettario_title(); 

  @Key("menu_organigramma_prompt")
  String menu_organigramma_prompt(); 

  @Key("menu_organigramma_title")
  String menu_organigramma_title(); 

  @Key("menu_posta_elettronica_prompt")
  String menu_posta_elettronica_prompt(); 

  @Key("menu_posta_elettronica_title")
  String menu_posta_elettronica_title(); 
  
  @Key("menu_visure_in_iter_title")
  String menu_visure_in_iter_title();
  
  @Key("menu_visure_in_iter_prompt")
  String menu_visure_in_iter_prompt();
  
  @Key("menu_visure_personali_title")
  String menu_visure_personali_title();
  
  @Key("menu_visure_personali_prompt")
  String menu_visure_personali_prompt();

  @Key("menu_procedimenti_in_iter_prompt")
  String menu_procedimenti_in_iter_prompt(); 

  @Key("menu_procedimenti_in_iter_title")
  String menu_procedimenti_in_iter_title(); 

  @Key("menu_procedimenti_personali_prompt")
  String menu_procedimenti_personali_prompt(); 

  @Key("menu_procedimenti_personali_title")
  String menu_procedimenti_personali_title(); 
  
  @Key("menu_tso_in_iter_title")
  String menu_tso_in_iter_title();
  
  @Key("menu_tso_in_iter_prompt")
  String menu_tso_in_iter_prompt();
  
  @Key("menu_tso_personali_title")
  String menu_tso_personali_title();
  
  @Key("menu_tso_personali_prompt")
  String menu_tso_personali_prompt();

  @Key("menu_protocollazione_entrata_prompt")
  String menu_protocollazione_entrata_prompt(); 

  @Key("menu_protocollazione_entrata_title")
  String menu_protocollazione_entrata_title(); 

  @Key("menu_protocollazione_interna_prompt")
  String menu_protocollazione_interna_prompt(); 

  @Key("menu_protocollazione_interna_title")
  String menu_protocollazione_interna_title(); 

  @Key("menu_stampa_etichetta_faldone_prompt")
  String menu_stampa_etichetta_faldone_prompt(); 

  @Key("menu_stampa_etichetta_faldone_title")
  String menu_stampa_etichetta_faldone_title(); 
  
  @Key("menu_richieste_agib_title")
  String menu_richieste_agib_title(); 

  @Key("menu_richieste_agib_prompt")
  String menu_richieste_agib_prompt(); 

  @Key("menu_protocollazione_prompt")
  String menu_protocollazione_prompt(); 

  @Key("menu_protocollazione_title")
  String menu_protocollazione_title(); 

  @Key("menu_protocollazione_uscita_prompt")
  String menu_protocollazione_uscita_prompt(); 

  @Key("menu_protocollazione_uscita_title")
  String menu_protocollazione_uscita_title(); 
  
  @Key("menu_registri_numerazione_prompt")
  String menu_registri_numerazione_prompt();
  
  @Key("menu_registri_numerazione_title")
  String menu_registri_numerazione_title();

  @Key("menu_registro_documenti_prompt")
  String menu_registro_documenti_prompt(); 

  @Key("menu_registro_documenti_title")
  String menu_registro_documenti_title(); 

  @Key("menu_registro_fatture_prompt")
  String menu_registro_fatture_prompt(); 

  @Key("menu_registro_fatture_title")
  String menu_registro_fatture_title(); 

  @Key("menu_repertorio_entrata_title")
  String menu_repertorio_entrata_title();
  
  @Key("menu_repertorio_entrata_prompt")
  String menu_repertorio_entrata_prompt ();
  
  @Key("menu_repertorio_interno_title")
  String menu_repertorio_interno_title();
  
  @Key("menu_repertorio_interno_prompt")
  String menu_repertorio_interno_prompt();
		  
  @Key("menu_repertorio_uscita_title")
  String menu_repertorio_uscita_title();
  
  @Key("menu_repertorio_uscita_prompt")
  String menu_repertorio_uscita_prompt();
  
  @Key("menu_repertorio_accesso_civico_title")
  String menu_repertorio_accesso_civico_title();
  
  @Key("menu_repertorio_accesso_civico_prompt")
  String menu_repertorio_accesso_civico_prompt();
  
  @Key("menu_repertorio_contratti_title")
  String menu_repertorio_contratti_title();
  
  @Key("menu_repertorio_contratti_prompt")
  String menu_repertorio_contratti_prompt();
  
  @Key("menu_repertorio_trasparenza_title")
  String menu_repertorio_trasparenza_title();
  
  @Key("menu_repertorio_trasparenza_prompt")
  String menu_repertorio_trasparenza_prompt();
  
  @Key("menu_report_prompt")
  String menu_report_prompt(); 

  @Key("menu_report_title")
  String menu_report_title(); 

  @Key("menu_richiesta_accesso_atti_menu_prompt")
  String menu_richiesta_accesso_atti_menu_prompt(); 

  @Key("menu_richiesta_accesso_atti_menu_title")
  String menu_richiesta_accesso_atti_menu_title(); 

  @Key("menu_scrivania_prompt")
  String menu_scrivania_prompt(); 

  @Key("menu_scrivania_title")
  String menu_scrivania_title(); 

  @Key("menu_stampa_reg_proposte_atti_prompt")
  String menu_stampa_reg_proposte_atti_prompt(); 

  @Key("menu_stampa_reg_proposte_atti_title")
  String menu_stampa_reg_proposte_atti_title(); 

  @Key("menu_stampa_reg_prot_prompt")
  String menu_stampa_reg_prot_prompt(); 

  @Key("menu_stampa_reg_prot_title")
  String menu_stampa_reg_prot_title(); 

  @Key("menu_stampa_reg_repertorio_prompt")
  String menu_stampa_reg_repertorio_prompt(); 

  @Key("menu_stampa_reg_repertorio_title")
  String menu_stampa_reg_repertorio_title(); 
  
  @Key("stampa_reg_pubblicazioni_prompt")
  String stampa_reg_pubblicazioni_prompt(); 
  
  @Key("stampa_reg_pubblicazioni_title")
  String stampa_reg_pubblicazioni_title(); 

  @Key("menu_stampa_registro_prompt")
  String menu_stampa_registro_prompt(); 

  @Key("menu_stampa_registro_title")
  String menu_stampa_registro_title(); 

  @Key("menu_statisticheDocumenti_prompt")
  String menu_statisticheDocumenti_prompt(); 

  @Key("menu_statisticheDocumenti_title")
  String menu_statisticheDocumenti_title(); 
  
  @Key("menu_statisticheCogito_prompt")
  String menu_statisticheCogito_prompt(); 

  @Key("menu_statisticheCogito_title")
  String menu_statisticheCogito_title(); 

  
  @Key("menu_statisticheMailStoricizzate_title")
  String menu_statisticheMailStoricizzate_title(); 
  
  @Key("menu_statisticheMailStoricizzate_prompt")
  String menu_statisticheMailStoricizzate_prompt(); 
 
  @Key("menu_statisticheProtocolli_prompt")
  String menu_statisticheProtocolli_prompt(); 

  @Key("menu_statisticheProtocolli_title")
  String menu_statisticheProtocolli_title(); 

  @Key("menu_statisticheMail_prompt")
  String menu_statisticheMail_prompt(); 

  @Key("menu_statisticheMail_title")
  String menu_statisticheMail_title(); 

  
  @Key("menu_stato_caricamento_rubriche_menu_prompt")
  String menu_stato_caricamento_rubriche_menu_prompt(); 

  @Key("menu_stato_caricamento_rubriche_menu_title")
  String menu_stato_caricamento_rubriche_menu_title(); 

  @Key("menu_titolario_prompt")
  String menu_titolario_prompt(); 

  @Key("menu_titolario_title")
  String menu_titolario_title(); 

  @Key("menu_vocabolario_prompt")
  String menu_vocabolario_prompt(); 

  @Key("menu_vocabolario_title")
  String menu_vocabolario_title(); 

  @Key("menu_wizard_prompt")
  String menu_wizard_prompt(); 

  @Key("menu_wizard_title")
  String menu_wizard_title(); 

  @Key("newIstanzaButton_prompt")
  String newIstanzaButton_prompt(String attribute0);

  @Key("menu_caricamento_in_corso")
  String menu_caricamento_in_corso();


  @Key("menu_statisticheTrasparenzaAmministrativa_prompt")
  String menu_statisticheTrasparenzaAmministrativa_prompt(); 

  @Key("menu_statisticheTrasparenzaAmministrativa_title")
  String menu_statisticheTrasparenzaAmministrativa_title(); 

  
  //#############################################################
  //#                     	  MODELLI DOCUMENTO                 #        
  //#############################################################
  
  @Key("modellidoclist_idModello_title")
  String modellidoclist_idModello_title();
  
  @Key("modellidoclist_nomeModello_title")
  String modellidoclist_nomeModello_title();

  @Key("modellidoclist_desModello_title")
  String modellidoclist_desModello_title();
  
  @Key("modellidoclist_note_title")
  String modellidoclist_note_title();
  
  @Key("modellidoclist_tipoModello_title")
  String modellidoclist_tipoModello_title();
  
  @Key("modellidoclist_tsCreazione_title")
  String modellidoclist_tsCreazione_title();
  
  @Key("modellidoclist_creatoDa_title")
  String modellidoclist_creatoDa_title();
  
  @Key("modellidoclist_tsUltimoAgg_title")
  String modellidoclist_tsUltimoAgg_title();
  
  @Key("modellidoclist_ultimoAggEffDa_title")
  String modellidoclist_ultimoAggEffDa_title();
  
  @Key("modellidoclist_flgValido_title")
  String modellidoclist_flgValido_title();
  
  @Key("modellidoclist_nomeEntitaAssociata_title")
  String modellidoclist_nomeEntitaAssociata_title();

  //#############################################################
  //#                          OGGETTARIO                       #
  //#############################################################

  @Key("oggettario_annullamentoLogicoAsk_message")
  String oggettario_annullamentoLogicoAsk_message(); 


  //#Oggettario detail
  @Key("oggettario_detail_edit_title")
  String oggettario_detail_edit_title(String attribute0);

  @Key("oggettario_detail_flgPubblicatoItem_title")
  String oggettario_detail_flgPubblicatoItem_title(); 

  @Key("oggettario_detail_flgXRegInEntrataItem_title")
  String oggettario_detail_flgXRegInEntrataItem_title(); 

  @Key("oggettario_detail_flgXRegInUscitaItem_title")
  String oggettario_detail_flgXRegInUscitaItem_title(); 

  @Key("oggettario_detail_flgXRegInterneItem_title")
  String oggettario_detail_flgXRegInterneItem_title(); 

  @Key("oggettario_detail_flgXRegTitleItem_title")
  String oggettario_detail_flgXRegTitleItem_title(); 

  @Key("oggettario_detail_new_title")
  String oggettario_detail_new_title(); 

  @Key("oggettario_detail_nomeItem_title")
  String oggettario_detail_nomeItem_title(); 

  @Key("oggettario_detail_noteItem_title")
  String oggettario_detail_noteItem_title(); 

  @Key("oggettario_detail_oggettoItem_title")
  String oggettario_detail_oggettoItem_title(); 

  @Key("oggettario_detail_view_title")
  String oggettario_detail_view_title(String attribute0);


  @Key("oggettario_eliminazioneFisicaAsk_message")
  String oggettario_eliminazioneFisicaAsk_message(); 

  @Key("oggettario_flgCreatoDaMe_true_value")
  String oggettario_flgCreatoDaMe_true_value(); 

  @Key("oggettario_flgDiSistema_true_value")
  String oggettario_flgDiSistema_true_value(); 

  @Key("oggettario_flgPubblicato_true_value")
  String oggettario_flgPubblicato_true_value(); 

  @Key("oggettario_flgValido_true_value")
  String oggettario_flgValido_true_value(); 

  @Key("oggettario_flgXRegInEntrata_true_value")
  String oggettario_flgXRegInEntrata_true_value(); 

  @Key("oggettario_flgXRegInUscita_true_value")
  String oggettario_flgXRegInUscita_true_value(); 

  @Key("oggettario_flgXRegInterne_true_value")
  String oggettario_flgXRegInterne_true_value(); 

  @Key("oggettario_flgVisibileAlleSottoUo_true_value")
  String oggettario_flgVisibileAlleSottoUo_true_value(); 
  
  @Key("oggettario_flgModificabileDalleSottoUo_true_value")
  String oggettario_flgModificabileDalleSottoUo_true_value(); 

  //#Oggettario list
  @Key("oggettario_list_codOrigineField_title")
  String oggettario_list_codOrigineField_title(); 

  @Key("oggettario_list_flgCreatoDaField_title")
  String oggettario_list_flgCreatoDaField_title(); 
  
  @Key("oggettario_list_flgTipoModelloField_title")
  String oggettario_list_flgTipoModelloField_title();

  @Key("oggettario_list_flgDiSistemaField_title")
  String oggettario_list_flgDiSistemaField_title(); 

  @Key("oggettario_list_flgPubblicatoField_title")
  String oggettario_list_flgPubblicatoField_title(); 

  @Key("oggettario_list_flgValidoField_title")
  String oggettario_list_flgValidoField_title(); 

  @Key("oggettario_list_flgXRegInEntrataField_title")
  String oggettario_list_flgXRegInEntrataField_title(); 

  @Key("oggettario_list_flgXRegInUscitaField_title")
  String oggettario_list_flgXRegInUscitaField_title(); 

  @Key("oggettario_list_flgXRegInterneField_title")
  String oggettario_list_flgXRegInterneField_title(); 

  @Key("oggettario_list_idModelloField_title")
  String oggettario_list_idModelloField_title(); 

  @Key("oggettario_list_nomeField_title")
  String oggettario_list_nomeField_title(); 

  @Key("oggettario_list_noteField_title")
  String oggettario_list_noteField_title(); 

  @Key("oggettario_list_oggettoField_title")
  String oggettario_list_oggettoField_title(); 

  @Key("oggettario_list_tsInsField_title")
  String oggettario_list_tsInsField_title(); 

  @Key("oggettario_list_tsLastUpdField_title")
  String oggettario_list_tsLastUpdField_title(); 

  @Key("oggettario_list_uteInsField_title")
  String oggettario_list_uteInsField_title(); 

  @Key("oggettario_list_uteLastUpdField_title")
  String oggettario_list_uteLastUpdField_title(); 

  @Key("oggettario_lookupOggettarioPopup_title")
  String oggettario_lookupOggettarioPopup_title(); 

  @Key("oggettario_list_denominazioneUoModello_title")
  String oggettario_list_denominazioneUoModello_title();

  @Key("oggettario_list_numeroLivelliField_title")
  String oggettario_list_numeroLivelliField_title();

  @Key("oggettario_list_flgVisibileAlleSottoUoField_title")
  String oggettario_list_flgVisibileAlleSottoUoField_title();

  @Key("oggettario_list_flgModificabileDalleSottoUoField_title")
  String oggettario_list_flgModificabileDalleSottoUoField_title();
  
  //#############################################################################
  //#           LISTA OPERAZIONI EFFETTUATE DEL DOCUMENTO/FASCICOLO             #
  //#############################################################################	

  @Key("operazionieffettuateFolderCustom_window_title")
  String operazionieffettuateFolderCustom_window_title(String attribute0);

  @Key("operazionieffettuateDoc_window_title")
  String operazionieffettuateDoc_window_title(String attribute0);

  @Key("operazionieffettuateFasc_window_title")
  String operazionieffettuateFasc_window_title(String attribute0);

  @Key("operazionieffettuateSottofasc_window_title")
  String operazionieffettuateSottofasc_window_title(String attribute0);

  @Key("cronologiaoperazionieffettuatedettaglioEmail_window_title")
  String cronologiaoperazionieffettuatedettaglioEmail_window_title(String attribute0);



  //#############################################################
  //#                       ORGANIGRAMMA                        #
  //#############################################################

  @Key("organigramma_associa_errore")
  String organigramma_associa_errore(); 

  @Key("organigramma_associa_errore_parziale")
  String organigramma_associa_errore_parziale(); 

  @Key("organigramma_associa_errore_totale")
  String organigramma_associa_errore_totale(); 

  @Key("organigramma_associa_in_corso")
  String organigramma_associa_in_corso(); 

  @Key("organigramma_associa_successo")
  String organigramma_associa_successo(); 

  @Key("organigramma_gestionePuntiProtocolloWaitPooup_message")
  String organigramma_gestionePuntiProtocolloWaitPooup_message(); 

  @Key("organigramma_layout_flgSoloAttive")
  String organigramma_layout_flgSoloAttive();
  
  @Key("organigramma_layout_tipoRelUtentiConUOSelectItem_title")
  String organigramma_layout_tipoRelUtentiConUOSelectItem_title();
  
  @Key("organigramma_layout_tipoRelUtentiConUOButton_title")
  String organigramma_layout_tipoRelUtentiConUOButton_title();
  
  @Key("organigramma_layout_flgMostraSVItem")
  String organigramma_layout_flgMostraSVItem();
  
  @Key("organigramma_filter_flgPuntoRaccoltaEmail_title")
  String organigramma_filter_flgPuntoRaccoltaEmail_title();

  //#OrganigrammaList
  @Key("organigramma_list_InPrecedenza_title")
  String organigramma_list_InPrecedenza_title(); 
  
  @Key("organigramma_list_collegataAPuntoProt_title")
  String organigramma_list_collegataAPuntoProt_title();
  
  @Key("organigramma_list_flgPuntoProtocollo_title")
  String organigramma_list_flgPuntoProtocollo_title();

  @Key("organigramma_list_flgPuntoRaccoltaEmail_title")
  String organigramma_list_flgPuntoRaccoltaEmail_title();
  
  @Key("organigramma_list_flgPuntoRaccoltaEmail_hover")
  String organigramma_list_flgPuntoRaccoltaEmail_hover();
  
  @Key("organigramma_list_agganciaUtente")
  String organigramma_list_agganciaUtente(); 

  @Key("organigramma_list_associaPuntiProtocolloMultiButton_title")
  String organigramma_list_associaPuntiProtocolloMultiButton_title(); 

  @Key("organigramma_list_associaPuntiProtocolloPopup_title")
  String organigramma_list_associaPuntiProtocolloPopup_title(); 

  @Key("organigramma_list_associazione_error")
  String organigramma_list_associazione_error(); 

  @Key("organigramma_list_associazione_success")
  String organigramma_list_associazione_success(); 

  @Key("organigramma_list_codRapidoUoField_title")
  String organigramma_list_codRapidoUoField_title(); 

  @Key("organigramma_list_codUoField_title")
  String organigramma_list_codUoField_title(); 

  @Key("organigramma_list_codiceField_title")
  String organigramma_list_codiceField_title(); 

  @Key("organigramma_list_competenzefunzioniField_title")
  String organigramma_list_competenzefunzioniField_title(); 

  @Key("organigramma_list_denominazioneEstesaField_title")
  String organigramma_list_denominazioneEstesaField_title(); 

  @Key("organigramma_list_descrizioneEstesaField_title")
  String organigramma_list_descrizioneEstesaField_title(); 

  @Key("organigramma_list_descrizioneField_title")
  String organigramma_list_descrizioneField_title(); 

  @Key("organigramma_list_emailField_title")
  String organigramma_list_emailField_title(); 

  @Key("organigramma_list_faxField_title")
  String organigramma_list_faxField_title(); 

  @Key("organigramma_list_flgPubblicataSuIpaAlt_value")
  String organigramma_list_flgPubblicataSuIpaAlt_value(String attribute0);

  @Key("organigramma_list_folderUpButton_prompt")
  String organigramma_list_folderUpButton_prompt(); 

  @Key("organigramma_list_iconaFolderButton_prompt")
  String organigramma_list_iconaFolderButton_prompt();
  
  @Key("organigramma_list_iconaUT_prompt")
  String organigramma_list_iconaUT_prompt();
  
  @Key("organigramma_list_iconaSV_prompt")
  String organigramma_list_iconaSV_prompt();

  @Key("organigramma_list_indirizzoField_title")
  String organigramma_list_indirizzoField_title(); 

  @Key("organigramma_list_listaPuntiProtocolloVuota_error")
  String organigramma_list_listaPuntiProtocolloVuota_error(); 

  @Key("organigramma_list_listaUoVuota_error")
  String organigramma_list_listaUoVuota_error(); 

  @Key("organigramma_list_nomeField_title")
  String organigramma_list_nomeField_title(); 

  @Key("organigramma_list_profiloField_title")
  String organigramma_list_profiloField_title(); 
  
  @Key("organigramma_list_subProfiliField_title")
  String organigramma_list_subProfiliField_title(); 

  @Key("organigramma_list_pubblicataSuIpaField_title")
  String organigramma_list_pubblicataSuIpaField_title(); 

  @Key("organigramma_list_qualificaField_title")
  String organigramma_list_qualificaField_title(); 

  @Key("organigramma_list_ruoloField_title")
  String organigramma_list_ruoloField_title(); 

  @Key("organigramma_list_scollegaPuntiProtocolloMultiButton_title")
  String organigramma_list_scollegaPuntiProtocolloMultiButton_title(); 

  @Key("organigramma_list_scollegaPuntiProtocolloPopup_title")
  String organigramma_list_scollegaPuntiProtocolloPopup_title(); 

  @Key("organigramma_list_scollegamento_error")
  String organigramma_list_scollegamento_error(); 

  @Key("organigramma_list_scollegamento_success")
  String organigramma_list_scollegamento_success(); 

  @Key("organigramma_list_scoreField_title")
  String organigramma_list_scoreField_title(); 

  @Key("organigramma_list_telefonoField_title")
  String organigramma_list_telefonoField_title(); 

  @Key("organigramma_list_tipoRelUtenteUoField_A_prompt")
  String organigramma_list_tipoRelUtenteUoField_A_prompt();
  
  @Key("organigramma_list_tipoRelUtenteUoField_D_prompt")
  String organigramma_list_tipoRelUtenteUoField_D_prompt();
  
  @Key("organigramma_list_tipoRelUtenteUoField_L_prompt")
  String organigramma_list_tipoRelUtenteUoField_L_prompt();
  
  @Key("organigramma_list_tipoRelUtenteUoField_title")
  String organigramma_list_tipoRelUtenteUoField_title(); 

  @Key("organigramma_list_titoloField_title")
  String organigramma_list_titoloField_title(); 

  @Key("organigramma_list_tsFineField_title")
  String organigramma_list_tsFineField_title(); 

  @Key("organigramma_list_tsInizioField_title")
  String organigramma_list_tsInizioField_title(); 

  @Key("organigramma_list_usernameField_title")
  String organigramma_list_usernameField_title(); 
  
  @Key("organigramma_list_tsStrutturaDaCessareDalField_title")
  String organigramma_list_tsStrutturaDaCessareDalField_title(); 
  
  @Key("organigramma_uo_list_origineCreazione_title")
  String organigramma_uo_list_origineCreazione_title(); 
  

  @Key("organigramma_popup_section_puntiProtocollo")
  String organigramma_popup_section_puntiProtocollo(); 

  @Key("organigramma_postazione_detail_con_delega_alle_sotto_uo")
  String organigramma_postazione_detail_con_delega_alle_sotto_uo(); 

  @Key("organigramma_postazione_detail_data_al")
  String organigramma_postazione_detail_data_al(); 

  @Key("organigramma_postazione_detail_data_dal")
  String organigramma_postazione_detail_data_dal(); 

  @Key("organigramma_postazione_detail_flg_con_delega_postazioni_utente")
  String organigramma_postazione_detail_flg_con_delega_postazioni_utente(); 

  @Key("organigramma_postazione_detail_flg_estendi_delega_postazioni_utente")
  String organigramma_postazione_detail_flg_estendi_delega_postazioni_utente(); 

  @Key("organigramma_postazione_detail_flg_estendi_delega_sotto_uo")
  String organigramma_postazione_detail_flg_estendi_delega_sotto_uo(); 

  @Key("organigramma_postazione_detail_ruolo")
  String organigramma_postazione_detail_ruolo(); 

  @Key("organigramma_postazione_detail_section_utente_corrente")
  String organigramma_postazione_detail_section_utente_corrente(); 

  @Key("organigramma_postazione_detail_section_utente_nuovo")
  String organigramma_postazione_detail_section_utente_nuovo(); 

  @Key("organigramma_postazione_detail_tipo_assegnazione")
  String organigramma_postazione_detail_tipo_assegnazione(); 

  @Key("organigramma_postazione_detail_utenti")
  String organigramma_postazione_detail_utenti(); 


  
  @Key("organigramma_postazione_detail_descUoSpostamentoDocFasc_title")
  String organigramma_postazione_detail_descUoSpostamentoDocFasc_title(); 
 
  @Key("organigramma_postazione_detail_uoSpostaDocFascSection_title")
  String organigramma_postazione_detail_uoSpostaDocFascSection_title(); 

  
  @Key("organigramma_postazione_detail_resocontoSpostamentoDocFascSection_title")
  String organigramma_postazione_detail_resocontoSpostamentoDocFascSection_title();
  
  
  @Key("organigramma_promo_associate")
  String organigramma_promo_associate(); 

  @Key("organigramma_scollega_errore")
  String organigramma_scollega_errore(); 

  @Key("organigramma_scollega_errore_parziale")
  String organigramma_scollega_errore_parziale(); 

  @Key("organigramma_scollega_errore_totale")
  String organigramma_scollega_errore_totale(); 

  @Key("organigramma_scollega_in_corso")
  String organigramma_scollega_in_corso(); 

  @Key("organigramma_scollega_successo")
  String organigramma_scollega_successo(); 

  @Key("organigramma_tipologia_offerte")
  String organigramma_tipologia_offerte(); 

  @Key("organigramma_list_icona_OrigineCreazione_HR_Button_prompt")
  String organigramma_list_icona_OrigineCreazione_HR_Button_prompt(); 

  @Key("organigramma_list_icona_origineCreazione_M_Button_prompt")
  String organigramma_list_icona_origineCreazione_M_Button_prompt(); 

  
  //#OrganigrammaDetail
  
  
  @Key("organigramma_uo_detail_uoLiquidatoreDetailSection_title")
  String organigramma_uo_detail_uoLiquidatoreDetailSection_title(); 

  
  @Key("organigramma_uo_detail_associaPuntiProtocolloDetailSection_title")
  String organigramma_uo_detail_associaPuntiProtocolloDetailSection_title(); 

  @Key("organigramma_uo_detail_contattiOrganigrammaDetailSection_title")
  String organigramma_uo_detail_contattiOrganigrammaDetailSection_title(); 
  
  @Key("organigramma_uo_detail_centriCostoOrganigrammaDetailSection_title")
  String organigramma_uo_detail_centriCostoOrganigrammaDetailSection_title(); 

  @Key("organigramma_uo_detail_dataIstituita_title")
  String organigramma_uo_detail_dataIstituita_title(); 

  @Key("organigramma_uo_detail_dataSoppressa_title")
  String organigramma_uo_detail_dataSoppressa_title(); 

  @Key("organigramma_uo_detail_denominzazione_title")
  String organigramma_uo_detail_denominzazione_title(); 

  @Key("organigramma_uo_detail_denominzazione_title_breve")
  String organigramma_uo_detail_denominzazione_title_breve(); 

  @Key("organigramma_uo_detail_denominazioneCdCCdR_title")
  String organigramma_uo_detail_denominazioneCdCCdR_title(); 

  @Key("organigramma_uo_detail_assessoreRif_title")
  String organigramma_uo_detail_assessoreRif_title(); 
  
  @Key("organigramma_uo_detail_estremiVariazioneDaStoricizzareDetailSection_title")
  String organigramma_uo_detail_estremiVariazioneDaStoricizzareDetailSection_title(); 

  @Key("organigramma_uo_detail_flgInibitaAssegnazione_title")
  String organigramma_uo_detail_flgInibitaAssegnazione_title(); 

  @Key("organigramma_uo_detail_flgInibitaAssegnazioneEmail_title")
  String organigramma_uo_detail_flgInibitaAssegnazioneEmail_title(); 

  @Key("organigramma_uo_detail_flgPuntoRaccoltaEmail_title")
  String organigramma_uo_detail_flgPuntoRaccoltaEmail_title(); 
  
  @Key("organigramma_uo_detail_flgPuntoRaccoltaDocumenti_title")
  String organigramma_uo_detail_flgPuntoRaccoltaDocumenti_title(); 

  @Key("organigramma_uo_detail_flgPuntoRaccoltaEmailItem_hover")
  String organigramma_uo_detail_flgPuntoRaccoltaEmailItem_hover(); 

  @Key("organigramma_uo_detail_flgInibitoInvioCC_title")
  String organigramma_uo_detail_flgInibitoInvioCC_title(); 

  @Key("organigramma_uo_detail_flgPuntoProtocolloItem_title")
  String organigramma_uo_detail_flgPuntoProtocolloItem_title(); 

  @Key("organigramma_uo_detail_flgStoricizzaDatiVariati_title")
  String organigramma_uo_detail_flgStoricizzaDatiVariati_title(); 

  @Key("organigramma_uo_detail_flgPuntoRaccoltaEmailItem_title")
  String organigramma_uo_detail_flgPuntoRaccoltaEmailItem_title(); 

  @Key("organigramma_uo_detail_labelSocioCoopDatiClienteFormItem_title")
  String organigramma_uo_detail_labelSocioCoopDatiClienteFormItem_title(); 

  @Key("organigramma_uo_detail_categoriaSocioCoopDatiClienteFormItem_title")
  String organigramma_uo_detail_categoriaSocioCoopDatiClienteFormItem_title();

	
  @Key("organigramma_uo_detail_livello_title")
  String organigramma_uo_detail_livello_title(); 

  @Key("organigramma_uo_detail_motiviVariazioneIn_title")
  String organigramma_uo_detail_motiviVariazioneIn_title(); 

  @Key("organigramma_uo_detail_nroLivello_title")
  String organigramma_uo_detail_nroLivello_title(); 

  @Key("organigramma_uo_detail_presenteAttoDefOrganigramma_title")
  String organigramma_uo_detail_presenteAttoDefOrganigramma_title(); 

  @Key("organigramma_uo_detail_inibitaPreimpDestProtEntrata_title")
  String organigramma_uo_detail_inibitaPreimpDestProtEntrata_title(); 
  
  @Key("organigramma_uo_detail_tipo_title")
  String organigramma_uo_detail_tipo_title(); 

  @Key("organigramma_uo_detail_variazioniDatiIn_title")
  String organigramma_uo_detail_variazioniDatiIn_title();
  
  @Key("organigramma_uo_detail_uoDerivataDetailSection_title")
  String organigramma_uo_detail_uoDerivataDetailSection_title();
  
  @Key("organigramma_uo_detail_uoDerivataPerSelect_title")
  String organigramma_uo_detail_uoDerivataPerSelect_title();
		  
  @Key("organigramma_uo_detail_uoDerivataDaItem_titte")
  String organigramma_uo_detail_uoDerivataDaItem_title();
  
  @Key("organigramma_uo_detail_uoDatoLuogoADetailSection_title")
  String organigramma_uo_detail_uoDatoLuogoADetailSection_title();
  
  @Key("organigramma_uo_detail_uoDatoLuogoAPerSelect_title")
  String organigramma_uo_detail_uoDatoLuogoAPerSelect_title();
		  
  @Key("organigramma_uo_detail_uoDatoLuogoAItem_titte")
  String organigramma_uo_detail_uoDatoLuogoAItem_title();

  @Key("organigramma_uo_detail_uoSpostaDocFascSection_title")
  String organigramma_uo_detail_uoSpostaDocFascSection_title();

  @Key("organigramma_uo_detail_uoSpostaMailSection_title")
  String organigramma_uo_detail_uoSpostaMailSection_title();

  @Key("organigramma_uo_detail_resocontoSpostamentoDocFascMailSection_title")
  String organigramma_uo_detail_resocontoSpostamentoDocFascMailSection_title();
  
  @Key("organigramma_uo_detail_nrDocAssegnati_title")
  String organigramma_uo_detail_nrDocAssegnati_title();
  
  @Key("organigramma_uo_detail_dataConteggioDocAssegnati_title")
  String organigramma_uo_detail_dataConteggioDocAssegnati_title();
  
  @Key("organigramma_uo_detail_nrFascAssegnati_title")
  String organigramma_uo_detail_nrFascAssegnati_title();
  
  @Key("organigramma_uo_detail_dataConteggioFascAssegnati_title")
  String organigramma_uo_detail_dataConteggioFascAssegnati_title();
  
  @Key("organigramma_uo_detail_nrMailAssegnati_title")
  String organigramma_uo_detail_nrMailAssegnati_title();
  
  @Key("organigramma_uo_detail_dataConteggioMailAssegnati_title")
  String organigramma_uo_detail_dataConteggioMailAssegnati_title();
  
  @Key("organigramma_uo_detail_descUoSpostamentoDocFasc_title")
  String organigramma_uo_detail_descUoSpostamentoDocFasc_title();
  
  @Key("organigramma_uo_detail_statoSpostamentoDocFasc_title")
  String organigramma_uo_detail_statoSpostamentoDocFasc_title();
  
  @Key("organigramma_uo_detail_dataInizioSpostamentoDocFasc_title")
  String organigramma_uo_detail_dataInizioSpostamentoDocFasc_title();
  
  @Key("organigramma_uo_detail_dataFineSpostamentoDocFasc_title")
  String organigramma_uo_detail_dataFineSpostamentoDocFasc_title();

  
  @Key("organigramma_uo_detail_descUoSpostamentoMail_title")
  String organigramma_uo_detail_descUoSpostamentoMail_title();
  
  @Key("organigramma_uo_detail_statoSpostamentoMail_title")
  String organigramma_uo_detail_statoSpostamentoMail_title();
  
  @Key("organigramma_uo_detail_dataInizioSpostamentoMail_title")
  String organigramma_uo_detail_dataInizioSpostamentoMail_title();
  
  @Key("organigramma_uo_detail_dataFineSpostamentoMail_title")
  String organigramma_uo_detail_dataFineSpostamentoMail_title();

  @Key("organigramma_uo_detail_competenzeItem_title")
  String organigramma_uo_detail_competenzeItem_title();

  @Key("organigramma_uo_detail_ciProvUO_title")
  String organigramma_uo_detail_ciProvUO_title();

  @Key("organigramma_uo_detail_formDatiPrincipaliSection_title")
  String organigramma_uo_detail_formDatiPrincipaliSection_title();

  @Key("organigramma_uo_detail_formOpzioniSection_title")
  String organigramma_uo_detail_formOpzioniSection_title();

  @Key("organigramma_uo_detail_formAssegnazioniUnitaPersonaleStrutturaSection_title")
  String organigramma_uo_detail_formAssegnazioniUnitaPersonaleStrutturaSection_title();

  @Key("organigramma_uo_detail_flgPropagaAssInvioUP_title")
  String organigramma_uo_detail_flgPropagaAssInvioUP_title();

  
  @Key("organigramma_uo_detail_flgAssInvioUP_title")
  String organigramma_uo_detail_flgAssInvioUP_title();

  
  @Key("organigramma_uo_detail_competenzeSection_title")
  String organigramma_uo_detail_competenzeSection_title();

  
  @Key("organigramma_uo_detail_abilitaUoProtEntrata_title")
  String organigramma_uo_detail_abilitaUoProtEntrata_title();
  
  @Key("organigramma_uo_detail_abilitaUoProtUscita_title")
  String organigramma_uo_detail_abilitaUoProtUscita_title();
  
  @Key("organigramma_uo_detail_abilitaUoProtUscitaFull_title")
  String organigramma_uo_detail_abilitaUoProtUscitaFull_title();
  
  @Key("organigramma_uo_detail_abilitaUoAssRiservatezza_title")
  String organigramma_uo_detail_abilitaUoAssRiservatezza_title();
  
  @Key("organigramma_uo_detail_abilitaUoGestMulte_title")
  String organigramma_uo_detail_abilitaUoGestMulte_title();
  	  
  @Key("organigramma_uo_detail_abilitaUoGestContratti_title")
  String organigramma_uo_detail_abilitaUoGestContratti_title();
  	  
  @Key("organigramma_uo_detail_flgUfficioGareAppalti_title")
  String organigramma_uo_detail_flgUfficioGareAppalti_title();
  
  @Key("organigramma_uo_detail_abilitaUoScansioneMassiva_title")
  String organigramma_uo_detail_abilitaUoScansioneMassiva_title();
  
  
  
  //#############################################################
  //#                 OSSERVAZIONI E NOTIFICHE                  #
  //#############################################################

  @Key("osservazioniNotifiche_detail_destinatario_title")
  String osservazioniNotifiche_detail_destinatario_title(); 

  @Key("osservazioniNotifiche_detail_messaggio_title")
  String osservazioniNotifiche_detail_messaggio_title(); 

  @Key("osservazioniNotifiche_detail_priorita_title")
  String osservazioniNotifiche_detail_priorita_title(); 

  @Key("osservazioniNotifiche_layout_aggiungi_title")
  String osservazioniNotifiche_layout_aggiungi_title(); 

  @Key("osservazioniNotifiche_list_aNomeDi_title")
  String osservazioniNotifiche_list_aNomeDi_title(); 

  @Key("osservazioniNotifiche_list_dataNotifica_title")
  String osservazioniNotifiche_list_dataNotifica_title(); 

  @Key("osservazioniNotifiche_list_destinatario_title")
  String osservazioniNotifiche_list_destinatario_title(); 

  @Key("osservazioniNotifiche_list_esclusivo_title")
  String osservazioniNotifiche_list_esclusivo_title(); 

  @Key("osservazioniNotifiche_list_messaggio_title")
  String osservazioniNotifiche_list_messaggio_title(); 

  @Key("osservazioniNotifiche_list_mittente_title")
  String osservazioniNotifiche_list_mittente_title(); 

  @Key("osservazioniNotifiche_list_personale_title")
  String osservazioniNotifiche_list_personale_title(); 

  @Key("osservazioniNotifiche_list_priorita_title")
  String osservazioniNotifiche_list_priorita_title(); 

  @Key("osservazioniNotifiche_menu_apri_title")
  String osservazioniNotifiche_menu_apri_title(); 

  @Key("osservazioniNotifiche_window_title")
  String osservazioniNotifiche_window_title(); 

  @Key("apposizioneCommenti_menu_apri_title")
  String apposizioneCommenti_menu_apri_title(); 

  
  //#############################################################
  //#                 SEGNA COME VISIONATO                      #
  //#############################################################
  
  @Key("segnaComeVisionato_menu_apri_title")
  String segnaComeVisionato_menu_apri_title(); 

  @Key("segnaComeVisionato_Popup_Ud_title")
  String segnaComeVisionato_Popup_Ud_title(String attribute0);
  
  @Key("segnaComeVisionato_Popup_Folder_title")
  String segnaComeVisionato_Popup_Folder_title(String attribute0);

  @Key("segnaComeVisionato_PopupMassivo_title")
  String segnaComeVisionato_PopupMassivo_title();
  
  
  //#############################################################
  //#                 SEGNA COME ARCHIVIATO                      #
  //#############################################################
  
  @Key("segnaComeArchiviato_menu_apri_title")
  String segnaComeArchiviato_menu_apri_title(); 
  
  @Key("segnaComeArchiviato_Popup_Ud_title")
  String segnaComeArchiviato_Popup_Ud_title(String attribute0);
  
  @Key("segnaComeArchiviato_Popup_Folder_title")
  String segnaComeArchiviato_Popup_Folder_title(String attribute0);
  
  @Key("segnaComeArchiviato_PopupMassivo_title")
  String segnaComeArchiviato_PopupMassivo_title();
  
  //#############################################################
  //#                      POSTA ELETTRONICA                    #
  //#############################################################

  @Key("dettaglio_posta_elettronica_stampaFile")
  String dettaglio_posta_elettronica_stampaFile(); 

  @Key("dettaglio_posta_elettronica_stampaTuttiAllegati")
  String dettaglio_posta_elettronica_stampaTuttiAllegati(); 

  @Key("dettaglio_posta_elettronica_stampaTuttiFileNoteAppunti")
  String dettaglio_posta_elettronica_stampaTuttiFileNoteAppunti(); 

  @Key("dettaglio_posta_elettronica_stampaTuttiIFile")
  String dettaglio_posta_elettronica_stampaTuttiIFile(); 
  
  @Key("dettaglio_posta_elettronica_esportaDestinatari")
  String dettaglio_posta_elettronica_esportaDestinatari(); 


  //#PostElettronicaList
  @Key("postaElettronica__list_copiaMailGiaProtocollataField")
  String postaElettronica__list_copiaMailGiaProtocollataField(); 

  @Key("postaElettronica__list_askChiusuraMailField")
  String postaElettronica__list_askChiusuraMailField(); 
  
  @Key("postaElettronica__list_askChiusuraCopiaMail")
  String postaElettronica__list_askChiusuraCopiaMail(); 
  
  @Key("postaElettronica__list_statoConsegnaField")
  String postaElettronica__list_statoConsegnaField(); 
  
  @Key("postaElettronica_iconaMicroCategoriaMail")
  String postaElettronica_iconaMicroCategoriaMail(); 

  @Key("postaElettronica_list__uscita_iconaMicroCategoriaMail")
  String postaElettronica_list__uscita_iconaMicroCategoriaMail(); 

  @Key("postaElettronica_list_accountMittenteField_title")
  String postaElettronica_list_accountMittenteField_title(); 

  @Key("postaElettronica_list_allegatiField_prompt")
  String postaElettronica_list_allegatiField_prompt(String attribute0);

  @Key("postaElettronica_list_allegatiField_title")
  String postaElettronica_list_allegatiField_title(); 

  @Key("postaElettronica_list_allegatiFirmatiField_prompt")
  String postaElettronica_list_allegatiFirmatiField_prompt(String attribute0);

  @Key("postaElettronica_list_allegatiFirmatiField_title")
  String postaElettronica_list_allegatiFirmatiField_title(); 

  @Key("postaElettronica_list_assegnatarioField_title")
  String postaElettronica_list_assegnatarioField_title(); 

  @Key("postaElettronica_list_avvertimentiField_title")
  String postaElettronica_list_avvertimentiField_title(); 

  @Key("postaElettronica_list_azioneDaFare_title")
  String postaElettronica_list_azioneDaFare_title(); 

  @Key("postaElettronica_list_casellaRicezioneField_title")
  String postaElettronica_list_casellaRicezioneField_title(); 

  @Key("postaElettronica_list_categoriaField_title")
  String postaElettronica_list_categoriaField_title(); 

  @Key("postaElettronica_list_chiusuraEffettuataDaField_title")
  String postaElettronica_list_chiusuraEffettuataDaField_title(); 

  @Key("postaElettronica_list_cognomeNomeMittMail")
  String postaElettronica_list_cognomeNomeMittMail(); 

  @Key("postaElettronica_list_contrassegnoField_title")
  String postaElettronica_list_contrassegnoField_title(); 

  @Key("postaElettronica_list_corpoField_title")
  String postaElettronica_list_corpoField_title(); 

  @Key("postaElettronica_list_destinatariCCField_title")
  String postaElettronica_list_destinatariCCField_title(); 

  @Key("postaElettronica_list_destinatariCCNField_title")
  String postaElettronica_list_destinatariCCNField_title(); 

  @Key("postaElettronica_list_destinatariField_title")
  String postaElettronica_list_destinatariField_title(); 

  @Key("postaElettronica_list_destinatariPrimariField_title")
  String postaElettronica_list_destinatariPrimariField_title(); 

  @Key("postaElettronica_list_dimensioneField_title")
  String postaElettronica_list_dimensioneField_title(); 

  @Key("postaElettronica_list_emailCollegataField_title")
  String postaElettronica_list_emailCollegataField_title(); 

  @Key("postaElettronica_list_estremiDocInviatoField_title")
  String postaElettronica_list_estremiDocInviatoField_title(); 

  @Key("postaElettronica_list_estremiProtCollegatiField_title")
  String postaElettronica_list_estremiProtCollegatiField_title(); 

  @Key("postaElettronica_list_flgInteroperabileMail")
  String postaElettronica_list_flgInteroperabileMail(); 

  @Key("postaElettronica_list_flgPECMail")
  String postaElettronica_list_flgPECMail(); 

  @Key("postaElettronica_list_iconaFlgEmailFirmataField_title")
  String postaElettronica_list_iconaFlgEmailFirmataField_title(); 

  @Key("postaElettronica_list_iconaFlgInoltrataField_title")
  String postaElettronica_list_iconaFlgInoltrataField_title(); 

  @Key("postaElettronica_list_iconaFlgInviataRispostaField_title")
  String postaElettronica_list_iconaFlgInviataRispostaField_title(); 

  @Key("postaElettronica_list_iconaFlgNoAssocAutoField_title")
  String postaElettronica_list_iconaFlgNoAssocAutoField_title(); 

  @Key("postaElettronica_list_iconaFlgNotifInteropAggField_title")
  String postaElettronica_list_iconaFlgNotifInteropAggField_title(); 

  @Key("postaElettronica_list_iconaFlgNotifInteropAnnField_title")
  String postaElettronica_list_iconaFlgNotifInteropAnnField_title(); 

  @Key("postaElettronica_list_iconaFlgNotifInteropConfField_title")
  String postaElettronica_list_iconaFlgNotifInteropConfField_title(); 

  @Key("postaElettronica_list_iconaFlgNotifInteropEccField_title")
  String postaElettronica_list_iconaFlgNotifInteropEccField_title(); 

  @Key("postaElettronica_list_iconaFlgRichConfermaField_title")
  String postaElettronica_list_iconaFlgRichConfermaField_title(); 

  @Key("postaElettronica_list_iconaFlgSpamField_title")
  String postaElettronica_list_iconaFlgSpamField_title(); 

  @Key("postaElettronica_list_iconaFlgStatoProtField_title")
  String postaElettronica_list_iconaFlgStatoProtField_title(); 

  @Key("postaElettronica_list_idEmail")
  String postaElettronica_list_idEmail(); 

  @Key("postaElettronica_list_indirizzoMittMail")
  String postaElettronica_list_indirizzoMittMail(); 

  @Key("postaElettronica_list_livPrioritaField_title")
  String postaElettronica_list_livPrioritaField_title(); 

  @Key("postaElettronica_list_messageIdField_title")
  String postaElettronica_list_messageIdField_title(); 

  @Key("postaElettronica_list_messageProgressivoField_title")
  String postaElettronica_list_messageProgressivoField_title(); 

  @Key("postaElettronica_list_messaggioAssegnazioneField_title")
  String postaElettronica_list_messaggioAssegnazioneField_title(); 

  @Key("postaElettronica_list_oggettoField_title")
  String postaElettronica_list_oggettoField_title(); 

  @Key("postaElettronica_list_progressivo")
  String postaElettronica_list_progressivo(); 

  @Key("postaElettronica_list_protocolliDestinatariField_title")
  String postaElettronica_list_protocolliDestinatariField_title(); 

  @Key("postaElettronica_list_sottoTipoMail")
  String postaElettronica_list_sottoTipoMail(); 

  @Key("postaElettronica_list_statoAccettazioneField")
  String postaElettronica_list_statoAccettazioneField(); 

  @Key("postaElettronica_list_statoConsolidamentoField_title")
  String postaElettronica_list_statoConsolidamentoField_title(); 

  @Key("postaElettronica_list_statoInvioField")
  String postaElettronica_list_statoInvioField(); 

  @Key("postaElettronica_list_tipoMail")
  String postaElettronica_list_tipoMail(); 

  @Key("postaElettronica_list_tsAssegnField_title")
  String postaElettronica_list_tsAssegnField_title(); 

  @Key("postaElettronica_list_tsChiusuraField_title")
  String postaElettronica_list_tsChiusuraField_title(); 

  @Key("postaElettronica_list_tsInvioClientField_title")
  String postaElettronica_list_tsInvioClientField_title(); 

  @Key("postaElettronica_list_tsInvioField_title")
  String postaElettronica_list_tsInvioField_title(); 

  @Key("postaElettronica_list_tsInvioMail")
  String postaElettronica_list_tsInvioMail(); 

  @Key("postaElettronica_list_tsRicezioneField_title")
  String postaElettronica_list_tsRicezioneField_title(); 

  @Key("postaElettronica_list_tsSalvataggioEmailField_title")
  String postaElettronica_list_tsSalvataggioEmailField_title(); 

  @Key("postaElettronica_list_uscita_estremiProtMittMail")
  String postaElettronica_list_uscita_estremiProtMittMail(); 

  @Key("postaElettronica_list_uscita_flgInteroperabileMail")
  String postaElettronica_list_uscita_flgInteroperabileMail(); 

  @Key("postaElettronica_list_uscita_flgPECMail")
  String postaElettronica_list_uscita_flgPECMail(); 

  @Key("postaElettronica_list_uscita_idEmail")
  String postaElettronica_list_uscita_idEmail(); 

  @Key("postaElettronica_list_uscita_indirizzoMittMail")
  String postaElettronica_list_uscita_indirizzoMittMail(); 

  @Key("postaElettronica_list_uscita_progressivo")
  String postaElettronica_list_uscita_progressivo(); 

  @Key("postaElettronica_list_uscita_sottoTipoMail")
  String postaElettronica_list_uscita_sottoTipoMail(); 

  @Key("postaElettronica_list_uscita_tipoMail")
  String postaElettronica_list_uscita_tipoMail(); 

  @Key("postaElettronica_list_uscita_tsInvioMail")
  String postaElettronica_list_uscita_tsInvioMail(); 

  @Key("postaElettronica_mittente_default_warning")
  String postaElettronica_mittente_default_warning(); 

  @Key("postaElettronica_mittente_default_warning_digidoc")
  String postaElettronica_mittente_default_warning_digidoc(); 

  @Key("postaElettronica_nuovomessaggio_mittente_default_warning")
  String postaElettronica_nuovomessaggio_mittente_default_warning(); 

  @Key("posta_destinatari_copia_attori_item")
  String posta_destinatari_copia_attori_item(); 

  @Key("posta_destinatari_copia_ccn_attori_item")
  String posta_destinatari_copia_ccn_attori_item(); 

  @Key("posta_elettornica_detail_notificaEccezione")
  String posta_elettornica_detail_notificaEccezione(); 

  @Key("posta_elettronica_allega_mail_senza_busta_trasporto")
  String posta_elettronica_allega_mail_senza_busta_trasporto(); 

  @Key("posta_elettronica_annulla_archiviazione_errore")
  String posta_elettronica_annulla_archiviazione_errore(); 

  @Key("posta_elettronica_annulla_archiviazione_errore_parziale")
  String posta_elettronica_annulla_archiviazione_errore_parziale(); 

  @Key("posta_elettronica_annulla_archiviazione_errore_totale")
  String posta_elettronica_annulla_archiviazione_errore_totale(); 

  @Key("posta_elettronica_annulla_archiviazione_successo")
  String posta_elettronica_annulla_archiviazione_successo(); 

  @Key("posta_elettronica_archiviazione_errore")
  String posta_elettronica_archiviazione_errore(); 

  @Key("posta_elettronica_archiviazione_errore_parziale")
  String posta_elettronica_archiviazione_errore_parziale(); 

  @Key("posta_elettronica_archiviazione_errore_totale")
  String posta_elettronica_archiviazione_errore_totale(); 

  @Key("posta_elettronica_archiviazione_successo")
  String posta_elettronica_archiviazione_successo(); 

  @Key("posta_elettronica_assegna_tool_strip_button")
  String posta_elettronica_assegna_tool_strip_button(); 

  @Key("posta_elettronica_assegnazione_errore")
  String posta_elettronica_assegnazione_errore(); 

  @Key("posta_elettronica_assegnazione_errore_parziale")
  String posta_elettronica_assegnazione_errore_parziale(); 

  @Key("posta_elettronica_assegnazione_errore_totale")
  String posta_elettronica_assegnazione_errore_totale(); 

  @Key("posta_elettronica_assegnazione_in_corso")
  String posta_elettronica_assegnazione_in_corso(); 

  @Key("posta_elettronica_assegnazione_successo")
  String posta_elettronica_assegnazione_successo(); 

  @Key("posta_elettronica_associa_invio_detail_button")
  String posta_elettronica_associa_invio_detail_button(); 

  @Key("posta_elettronica_associa_protocollo_tool_trip_button")
  String posta_elettronica_associa_protocollo_tool_trip_button(); 

  @Key("posta_elettronica_azione_da_fare_tool_strip_button")
  String posta_elettronica_azione_da_fare_tool_strip_button(); 

  @Key("posta_elettronica_button_registra_istanza")
  String posta_elettronica_button_registra_istanza(); 

  @Key("posta_elettronica_button_registra_istanza_autotutela")
  String posta_elettronica_button_registra_istanza_autotutela();

  @Key("posta_elettronica_button_registra_istanza_ced")
  String posta_elettronica_button_registra_istanza_ced(); 
  
  @Key("posta_elettronica_button_registra_richiesta_accesso_atti")
  String posta_elettronica_button_registra_richiesta_accesso_atti();

  @Key("posta_elettronica_casella_account_item")
  String posta_elettronica_casella_account_item(); 

  @Key("posta_elettronica_conferma_menu_item")
  String posta_elettronica_conferma_menu_item(); 

  @Key("posta_elettronica_corpo_mail_contenuti_item")
  String posta_elettronica_corpo_mail_contenuti_item(); 

  @Key("posta_elettronica_data_reg_mitt_protocollo_item")
  String posta_elettronica_data_reg_mitt_protocollo_item(); 

  @Key("posta_elettronica_data_stato_lavorazione_apartiredal")
  String posta_elettronica_data_stato_lavorazione_apartiredal(); 

  @Key("posta_elettronica_des_uo_assegnataria_item")
  String posta_elettronica_des_uo_assegnataria_item(); 

  @Key("posta_elettronica_destinatari_principali_attori_item")
  String posta_elettronica_destinatari_principali_attori_item(); 
  
  @Key("posta_elettronica_totale_destinatari_principali_attori_item")
  String posta_elettronica_totale_destinatari_principali_attori_item(); 
  
  @Key("posta_destinatari_totale_copia_attori_item")
  String posta_destinatari_totale_copia_attori_item(); 
  
  @Key("posta_destinatari_totale_copia_ccn_attori_item")
  String posta_destinatari_totale_copia_ccn_attori_item(); 


  //#PostElettronicaDetail
  @Key("posta_elettronica_detail_accountMittente")
  String posta_elettronica_detail_accountMittente(); 

  @Key("posta_elettronica_detail_aggiornamento_MenuItem")
  String posta_elettronica_detail_aggiornamento_MenuItem(); 

  @Key("posta_elettronica_detail_allegaFileMenuItem")
  String posta_elettronica_detail_allegaFileMenuItem(); 

  @Key("posta_elettronica_detail_allegaMailOriginaleMenuItem")
  String posta_elettronica_detail_allegaMailOriginaleMenuItem(); 

  @Key("posta_elettronica_detail_allegati")
  String posta_elettronica_detail_allegati(); 

  @Key("posta_elettronica_detail_annullamentoMenuItem")
  String posta_elettronica_detail_annullamentoMenuItem(); 

  @Key("posta_elettronica_detail_annullamento_assegnazione_mail")
  String posta_elettronica_detail_annullamento_assegnazione_mail(); 

  @Key("posta_elettronica_detail_assegnaDetailButton")
  String posta_elettronica_detail_assegnaDetailButton(); 

  @Key("posta_elettronica_detail_assegnazione_mail")
  String posta_elettronica_detail_assegnazione_mail(); 

  @Key("posta_elettronica_detail_avvertimenti")
  String posta_elettronica_detail_avvertimenti(); 

  @Key("posta_elettronica_detail_azione_da_fare")
  String posta_elettronica_detail_azione_da_fare(); 

  @Key("posta_elettronica_detail_caricamento_dettaglio_mail")
  String posta_elettronica_detail_caricamento_dettaglio_mail(); 

  @Key("posta_elettronica_detail_caricamento_mail")
  String posta_elettronica_detail_caricamento_mail(); 

  @Key("posta_elettronica_detail_confermaMenuItem")
  String posta_elettronica_detail_confermaMenuItem(); 

  @Key("posta_elettronica_detail_corpo")
  String posta_elettronica_detail_corpo(); 

  @Key("posta_elettronica_detail_destinatariCC")
  String posta_elettronica_detail_destinatariCC(); 

  @Key("posta_elettronica_detail_destinatariCCn")
  String posta_elettronica_detail_destinatariCCn(); 

  @Key("posta_elettronica_detail_download")
  String posta_elettronica_detail_download(); 

  @Key("posta_elettronica_detail_eccezioneMenuItem")
  String posta_elettronica_detail_eccezioneMenuItem(); 

  @Key("posta_elettronica_detail_firmaInCalcePickListProperties_warningmessage")
  String posta_elettronica_detail_firmaInCalcePickListProperties_warningmessage(); 

  @Key("posta_elettronica_detail_inoltraDetailButton")
  String posta_elettronica_detail_inoltraDetailButton(); 

  @Key("posta_elettronica_detail_inoltrata")
  String posta_elettronica_detail_inoltrata(); 

  @Key("posta_elettronica_detail_inviaNotificaAggiornamentoDetailButton")
  String posta_elettronica_detail_inviaNotificaAggiornamentoDetailButton(); 

  @Key("posta_elettronica_detail_inviaNotificaAnnullamentoDetailButton")
  String posta_elettronica_detail_inviaNotificaAnnullamentoDetailButton(); 

  @Key("posta_elettronica_detail_inviaNotificaConfermaDetailButton")
  String posta_elettronica_detail_inviaNotificaConfermaDetailButton(); 

  @Key("posta_elettronica_detail_inviaNotificaEccezioneDetailButton")
  String posta_elettronica_detail_inviaNotificaEccezioneDetailButton(); 

  @Key("posta_elettronica_detail_invia_notifica_detail_button")
  String posta_elettronica_detail_invia_notifica_detail_button(); 

  @Key("posta_elettronica_detail_invio_mail")
  String posta_elettronica_detail_invio_mail(); 

  @Key("posta_elettronica_detail_messageIdField")
  String posta_elettronica_detail_messageIdField(); 

  @Key("posta_elettronica_detail_notificaAggiornamento")
  String posta_elettronica_detail_notificaAggiornamento(); 

  @Key("posta_elettronica_detail_notificaAnnullamento")
  String posta_elettronica_detail_notificaAnnullamento(); 

  @Key("posta_elettronica_detail_notificaConferma")
  String posta_elettronica_detail_notificaConferma(); 

  @Key("posta_elettronica_detail_oggetto")
  String posta_elettronica_detail_oggetto(); 

  @Key("posta_elettronica_detail_protocollaDetailButton")
  String posta_elettronica_detail_protocollaDetailButton(); 

  @Key("posta_elettronica_detail_ricevutePostaInUscitaDetailButton")
  String posta_elettronica_detail_ricevutePostaInUscitaDetailButton(); 

  @Key("posta_elettronica_detail_rispondiATuttiDetailButton")
  String posta_elettronica_detail_rispondiATuttiDetailButton(); 

  @Key("posta_elettronica_detail_rispondiDetailButton")
  String posta_elettronica_detail_rispondiDetailButton(); 

  @Key("posta_elettronica_detail_salvataggio_mail")
  String posta_elettronica_detail_salvataggio_mail(); 

  @Key("posta_elettronica_detail_section_contenuti")
  String posta_elettronica_detail_section_contenuti(); 

  @Key("posta_elettronica_detail_section_contenuti_bozza")
  String posta_elettronica_detail_section_contenuti_bozza(); 

  @Key("posta_elettronica_detail_section_estremi")
  String posta_elettronica_detail_section_estremi(); 

  @Key("posta_elettronica_detail_section_file_allegati")
  String posta_elettronica_detail_section_file_allegati(); 

  @Key("posta_elettronica_detail_section_protocollo_mitt")
  String posta_elettronica_detail_section_protocollo_mitt(); 

  @Key("posta_elettronica_detail_section_tab_datiPrincipali")
  String posta_elettronica_detail_section_tab_datiPrincipali(); 

  @Key("posta_elettronica_detail_section_tab_emailSucc")
  String posta_elettronica_detail_section_tab_emailSucc(); 

  @Key("posta_elettronica_detail_stato_consolidamento")
  String posta_elettronica_detail_stato_consolidamento(); 

  @Key("posta_elettronica_detail_tsInvio")
  String posta_elettronica_detail_tsInvio(); 


  @Key("posta_elettronica_diaggiornamento_menu_item")
  String posta_elettronica_diaggiornamento_menu_item(); 

  @Key("posta_elettronica_diannullamento_menu_item")
  String posta_elettronica_diannullamento_menu_item(); 

  @Key("posta_elettronica_diconferma_menu_item")
  String posta_elettronica_diconferma_menu_item(); 

  @Key("posta_elettronica_dieccezione_menu_item")
  String posta_elettronica_dieccezione_menu_item(); 

  @Key("posta_elettronica_eccezione_menu_item")
  String posta_elettronica_eccezione_menu_item(); 

  @Key("posta_elettronica_editor_view_title_new_message")
  String posta_elettronica_editor_view_title_new_message(); 

  @Key("posta_elettronica_eliminazione")
  String posta_elettronica_eliminazione(); 

  @Key("posta_elettronica_eliminazione_errore")
  String posta_elettronica_eliminazione_errore(); 

  @Key("posta_elettronica_eliminazione_errore_parziale")
  String posta_elettronica_eliminazione_errore_parziale(); 

  @Key("posta_elettronica_eliminazione_errore_totale")
  String posta_elettronica_eliminazione_errore_totale(); 

  @Key("posta_elettronica_eliminazione_in_corso")
  String posta_elettronica_eliminazione_in_corso(); 

  @Key("posta_elettronica_eliminazione_successo")
  String posta_elettronica_eliminazione_successo(); 

  @Key("posta_elettronica_email_predecessore_account_mittente_item")
  String posta_elettronica_email_predecessore_account_mittente_item(); 

  @Key("posta_elettronica_email_predecessore_casella_account_item")
  String posta_elettronica_email_predecessore_casella_account_item(); 

  @Key("posta_elettronica_email_predecessore_destinatari_cc_item")
  String posta_elettronica_email_predecessore_destinatari_cc_item(); 

  @Key("posta_elettronica_email_predecessore_destinatari_principali_item")
  String posta_elettronica_email_predecessore_destinatari_principali_item(); 

  @Key("posta_elettronica_email_predecessore_message_progressivo_item")
  String posta_elettronica_email_predecessore_message_progressivo_item(); 

  @Key("posta_elettronica_email_predecessore_subject_item")
  String posta_elettronica_email_predecessore_subject_item(); 

  @Key("posta_elettronica_email_predecessore_ts_invio_item")
  String posta_elettronica_email_predecessore_ts_invio_item(); 

  @Key("posta_elettronica_email_predecessore_ts_ricezione_item")
  String posta_elettronica_email_predecessore_ts_ricezione_item(); 

  @Key("posta_elettronica_ente_protocollo_item")
  String posta_elettronica_ente_protocollo_item(); 

  @Key("posta_elettronica_estremi_doc_trasmessi_item")
  String posta_elettronica_estremi_doc_trasmessi_item(); 

  @Key("posta_elettronica_flg_ricevuta_conferma_interop_item")
  String posta_elettronica_flg_ricevuta_conferma_interop_item(); 

  @Key("posta_elettronica_flg_ricevuta_eccezione_interop_item")
  String posta_elettronica_flg_ricevuta_eccezione_interop_item(); 

  @Key("posta_elettronica_flg_ricevuta_lettura")
  String posta_elettronica_flg_ricevuta_lettura(); 

  @Key("posta_elettronica_flg_ricevuto_aggiornamento_interop")
  String posta_elettronica_flg_ricevuto_aggiornamento_interop(); 

  @Key("posta_elettronica_flg_ricevuto_anno_reg_interop")
  String posta_elettronica_flg_ricevuto_anno_reg_interop(); 

  @Key("posta_elettronica_idEmail")
  String posta_elettronica_idEmail(); 

  @Key("posta_elettronica_id_email_list")
  String posta_elettronica_id_email_list(); 

  @Key("posta_elettronica_incaricoa_item")
  String posta_elettronica_incaricoa_item(); 

  @Key("posta_elettronica_indirizzo_dest")
  String posta_elettronica_indirizzo_dest(); 

  @Key("posta_elettronica_indirizzo_destinatari_ccn_item")
  String posta_elettronica_indirizzo_destinatari_ccn_item(); 

  @Key("posta_elettronica_indirizzo_destinatari_copia_attori_item")
  String posta_elettronica_indirizzo_destinatari_copia_attori_item(); 

  @Key("posta_elettronica_indirizzo_destinatari_coppia_attori_item")
  String posta_elettronica_indirizzo_destinatari_coppia_attori_item(); 

  @Key("posta_elettronica_indirizzo_destinatari_originali_attori_item")
  String posta_elettronica_indirizzo_destinatari_originali_attori_item(); 

  @Key("posta_elettronica_inoltra")
  String posta_elettronica_inoltra(); 

  @Key("posta_elettronica_inoltra_contenuti_menu_item")
  String posta_elettronica_inoltra_contenuti_menu_item(); 

  @Key("posta_elettronica_inoltra_menu_item")
  String posta_elettronica_inoltra_menu_item(); 

  @Key("posta_elettronica_inoltra_menu_item_sbustato")
  String posta_elettronica_inoltra_menu_item_sbustato(); 

  @Key("posta_elettronica_inoltra_tool_strip_button")
  String posta_elettronica_inoltra_tool_strip_button(); 

  @Key("posta_elettronica_invia_notifica_aggiornamento_detail_button")
  String posta_elettronica_invia_notifica_aggiornamento_detail_button(); 

  @Key("posta_elettronica_invia_notifica_annullamento_detail_button")
  String posta_elettronica_invia_notifica_annullamento_detail_button(); 

  @Key("posta_elettronica_invia_notifica_conferma_detail_button")
  String posta_elettronica_invia_notifica_conferma_detail_button(); 

  @Key("posta_elettronica_invia_notifica_detail_button")
  String posta_elettronica_invia_notifica_detail_button(); 

  @Key("posta_elettronica_invia_notifica_eccezione_detail_button")
  String posta_elettronica_invia_notifica_eccezione_detail_button(); 

  @Key("posta_elettronica_inviataRisposta")
  String posta_elettronica_inviataRisposta(); 

  @Key("posta_elettronica_item_estremi_doc_trasmessi")
  String posta_elettronica_item_estremi_doc_trasmessi(); 

  @Key("posta_elettronica_layout_apposizioneTagCommenti")
  String posta_elettronica_layout_apposizioneTagCommenti(); 

  @Key("posta_elettronica_layout_apposizioneTagCommenti_validazione_errormsg")
  String posta_elettronica_layout_apposizioneTagCommenti_validazione_errormsg(); 


  //#PostElettronicaList
  @Key("posta_elettronica_list_allegaFileEmailMenuItem")
  String posta_elettronica_list_allegaFileEmailMenuItem(); 

  @Key("posta_elettronica_list_appuntiNote")
  String posta_elettronica_list_appuntiNote(); 

  @Key("posta_elettronica_list_assegnatarioField")
  String posta_elettronica_list_assegnatarioField(); 

  @Key("posta_elettronica_list_azioneDaFareMenuItem")
  String posta_elettronica_list_azioneDaFareMenuItem(); 

  @Key("posta_elettronica_list_codAzioneDaFareField")
  String posta_elettronica_list_codAzioneDaFareField(); 

  @Key("posta_elettronica_list_dettaglioAzioneDaFareField")
  String posta_elettronica_list_dettaglioAzioneDaFareField(); 

  @Key("posta_elettronica_list_flgEmailCertificataField")
  String posta_elettronica_list_flgEmailCertificataField(); 

  @Key("posta_elettronica_list_flgInteroperabileField")
  String posta_elettronica_list_flgInteroperabileField(); 

  @Key("posta_elettronica_list_iconaFlgInoltrataField")
  String posta_elettronica_list_iconaFlgInoltrataField(); 

  @Key("posta_elettronica_list_iconaFlgInviataRispostaField")
  String posta_elettronica_list_iconaFlgInviataRispostaField(); 

  @Key("posta_elettronica_list_inCaricoAField")
  String posta_elettronica_list_inCaricoAField(); 

  @Key("posta_elettronica_list_inCaricoDalField")
  String posta_elettronica_list_inCaricoDalField(); 

  @Key("posta_elettronica_list_inoltraMenuItem")
  String posta_elettronica_list_inoltraMenuItem(); 

  @Key("posta_elettronica_list_inoltraMenuItemSbustato")
  String posta_elettronica_list_inoltraMenuItemSbustato(); 

  @Key("posta_elettronica_list_mandaInApprovazioneMenuItem")
  String posta_elettronica_list_mandaInApprovazioneMenuItem(); 

  @Key("posta_elettronica_list_messaInCaricoMenuItem")
  String posta_elettronica_list_messaInCaricoMenuItem(); 

  @Key("posta_elettronica_list_nroGiorniApertaField")
  String posta_elettronica_list_nroGiorniApertaField(); 

  @Key("posta_elettronica_list_nuovoInvioCopiaMenuItem")
  String posta_elettronica_list_nuovoInvioCopiaMenuItem(); 

  @Key("posta_elettronica_list_oggettoProtocolloMittenteField")
  String posta_elettronica_list_oggettoProtocolloMittenteField(); 

  @Key("posta_elettronica_list_prendiInCaricoMenuItem")
  String posta_elettronica_list_prendiInCaricoMenuItem(); 

  @Key("posta_elettronica_list_protocollaMenuItem")
  String posta_elettronica_list_protocollaMenuItem(); 

  @Key("posta_elettronica_list_registraMenuItem")
  String posta_elettronica_list_registraMenuItem(); 
  
  @Key("posta_elettronica_list_repertorioMenuItem")
  String posta_elettronica_list_repertorioMenuItem();
  
  @Key("posta_elettronica_list_protocollaAccessoAttiSUE")
  String posta_elettronica_list_protocollaAccessoAttiSUE();
  
  @Key("posta_elettronica_list_protocolloMittenteField")
  String posta_elettronica_list_protocolloMittenteField(); 

  @Key("posta_elettronica_list_reinviaMenu")
  String posta_elettronica_list_reinviaMenu(); 

  @Key("posta_elettronica_list_reinviaMenuItem")
  String posta_elettronica_list_reinviaMenuItem(); 

  @Key("posta_elettronica_list_ricevutePostaInUscitaMenuItem")
  String posta_elettronica_list_ricevutePostaInUscitaMenuItem(); 

  @Key("posta_elettronica_list_rilasciaMenuItem")
  String posta_elettronica_list_rilasciaMenuItem(); 

  @Key("posta_elettronica_list_rispondiATuttiConAllegatiMenuItem")
  String posta_elettronica_list_rispondiATuttiConAllegatiMenuItem(); 

  @Key("posta_elettronica_list_rispondiATuttiMenuItem")
  String posta_elettronica_list_rispondiATuttiMenuItem(); 

  @Key("posta_elettronica_list_rispondiATuttiStandardMenuItem")
  String posta_elettronica_list_rispondiATuttiStandardMenuItem(); 

  @Key("posta_elettronica_list_rispondiConAllegatiMenuItem")
  String posta_elettronica_list_rispondiConAllegatiMenuItem(); 

  @Key("posta_elettronica_list_rispondiMenuItem")
  String posta_elettronica_list_rispondiMenuItem(); 

  @Key("posta_elettronica_list_rispondiStandardMenuItem")
  String posta_elettronica_list_rispondiStandardMenuItem(); 

  @Key("posta_elettronica_list_scaricaEmailMenuItem")
  String posta_elettronica_list_scaricaEmailMenuItem(); 

  @Key("posta_elettronica_list_scaricaEmailSenzaBustaPECTrasportoMenuItem")
  String posta_elettronica_list_scaricaEmailSenzaBustaPECTrasportoMenuItem(); 

  @Key("posta_elettronica_list_scaricaMenuItem")
  String posta_elettronica_list_scaricaMenuItem(); 

  @Key("posta_elettronica_list_scaricaZipAllegati")
  String posta_elettronica_list_scaricaZipAllegati(); 

  @Key("posta_elettronica_list_sottotipoEmailField")
  String posta_elettronica_list_sottotipoEmailField(); 

  @Key("posta_elettronica_list_stampaHtmlMenuItem")
  String posta_elettronica_list_stampaHtmlMenuItem(); 

  @Key("posta_elettronica_list_stampaMenuItem")
  String posta_elettronica_list_stampaMenuItem(); 

  @Key("posta_elettronica_list_stampaPdfMenuItem")
  String posta_elettronica_list_stampaPdfMenuItem(); 

  @Key("posta_elettronica_list_stampaTestoHtmlMenuItem")
  String posta_elettronica_list_stampaTestoHtmlMenuItem(); 

  @Key("posta_elettronica_list_stampaTestoMenuItem")
  String posta_elettronica_list_stampaTestoMenuItem(); 

  @Key("posta_elettronica_list_statoAccettazioneField")
  String posta_elettronica_list_statoAccettazioneField(); 

  @Key("posta_elettronica_list_statoConsegnaField")
  String posta_elettronica_list_statoConsegnaField(); 

  @Key("posta_elettronica_list_statoInvioField")
  String posta_elettronica_list_statoInvioField(); 

  @Key("posta_elettronica_list_statoLavorazioneField")
  String posta_elettronica_list_statoLavorazioneField(); 

  @Key("posta_elettronica_list_tagAppostoField")
  String posta_elettronica_list_tagAppostoField(); 

  @Key("posta_elettronica_list_tipoEmailField")
  String posta_elettronica_list_tipoEmailField(); 

  @Key("posta_elettronica_list_visualizzaMenuItem")
  String posta_elettronica_list_visualizzaMenuItem(); 

  @Key("posta_elettronica_list_visualizzaProtAssociatiMenuItem")
  String posta_elettronica_list_visualizzaProtAssociatiMenuItem(); 


  @Key("posta_elettronica_manda_in_approvazione_errore")
  String posta_elettronica_manda_in_approvazione_errore(); 

  @Key("posta_elettronica_manda_in_approvazione_errore_parziale")
  String posta_elettronica_manda_in_approvazione_errore_parziale(); 

  @Key("posta_elettronica_manda_in_approvazione_errore_totale")
  String posta_elettronica_manda_in_approvazione_errore_totale(); 

  @Key("posta_elettronica_manda_in_approvazione_successo")
  String posta_elettronica_manda_in_approvazione_successo(); 

  @Key("posta_elettronica_menu_item_inoltra")
  String posta_elettronica_menu_item_inoltra(); 

  @Key("posta_elettronica_menu_item_inoltra_sbustato")
  String posta_elettronica_menu_item_inoltra_sbustato(); 


  @Key("posta_elettronica_messa_in_approvazione_in_corso")
  String posta_elettronica_messa_in_approvazione_in_corso(); 

  @Key("posta_elettronica_messa_in_carico_errore")
  String posta_elettronica_messa_in_carico_errore(); 

  @Key("posta_elettronica_messa_in_carico_errore_parziale")
  String posta_elettronica_messa_in_carico_errore_parziale(); 

  @Key("posta_elettronica_messa_in_carico_errore_totale")
  String posta_elettronica_messa_in_carico_errore_totale(); 

  @Key("posta_elettronica_messa_in_carico_in_corso")
  String posta_elettronica_messa_in_carico_in_corso(); 

  @Key("posta_elettronica_messa_in_carico_menu_item")
  String posta_elettronica_messa_in_carico_menu_item(); 

  @Key("posta_elettronica_messa_in_carico_successo")
  String posta_elettronica_messa_in_carico_successo(); 

  @Key("posta_elettronica_numero_protocollo_item")
  String posta_elettronica_numero_protocollo_item(); 

  @Key("posta_elettronica_nuovo_invio_copia_tool_strip_button")
  String posta_elettronica_nuovo_invio_copia_tool_strip_button(); 

  @Key("posta_elettronica_nuovo_mess_window")
  String posta_elettronica_nuovo_mess_window(); 

  @Key("posta_elettronica_oggetto_dati_protocollo_contenuti_item")
  String posta_elettronica_oggetto_dati_protocollo_contenuti_item(); 

  @Key("posta_elettronica_oggetto_mail_protocollo_item")
  String posta_elettronica_oggetto_mail_protocollo_item(); 

  @Key("posta_elettronica_orario_incaricodal")
  String posta_elettronica_orario_incaricodal(); 

  @Key("posta_elettronica_orario_uo_assegnazione")
  String posta_elettronica_orario_uo_assegnazione(); 

  @Key("posta_elettronica_presa_in_carico_errore")
  String posta_elettronica_presa_in_carico_errore(); 

  @Key("posta_elettronica_presa_in_carico_errore_parziale")
  String posta_elettronica_presa_in_carico_errore_parziale(); 

  @Key("posta_elettronica_presa_in_carico_errore_totale")
  String posta_elettronica_presa_in_carico_errore_totale(); 

  @Key("posta_elettronica_presa_in_carico_in_corso")
  String posta_elettronica_presa_in_carico_in_corso(); 

  @Key("posta_elettronica_replicable_canvas_errore_stampa_file")
  String posta_elettronica_replicable_canvas_errore_stampa_file(); 

  @Key("posta_elettronica_salvamail_item_warning_message")
  String posta_elettronica_salvamail_item_warning_message(); 

  @Key("preview_window_file_not_valid")
  String preview_window_file_not_valid(); 


  @Key("posta_elettronica_annulla_uo_competente")
  String posta_elettronica_annulla_uo_competente(); 

  @Key("posta_elettronica_annulla_uo_competente_error")
  String posta_elettronica_annulla_uo_competente_error(); 

  @Key("posta_elettronica_annulla_uo_competente_success")
  String posta_elettronica_annulla_uo_competente_success(); 

  @Key("posta_elettronica_annulla_uo_competente_waiting")
  String posta_elettronica_annulla_uo_competente_waiting(); 

  @Key("posta_elettronica_detail_section_note_appunti")
  String posta_elettronica_detail_section_note_appunti(); 

  @Key("posta_elettronica_detail_section_note_datiprincipali")
  String posta_elettronica_detail_section_note_datiprincipali(); 

  @Key("posta_elettronica_presa_in_carico_menu_item")
  String posta_elettronica_presa_in_carico_menu_item(); 

  @Key("posta_elettronica_presa_in_carico_successo")
  String posta_elettronica_presa_in_carico_successo(); 

  @Key("posta_elettronica_protocolla_tool_strip_button")
  String posta_elettronica_protocolla_tool_strip_button(); 

  @Key("posta_elettronica_protocollata_come_item")
  String posta_elettronica_protocollata_come_item(); 

  @Key("posta_elettronica_registro_protocollo_item")
  String posta_elettronica_registro_protocollo_item(); 

  @Key("posta_elettronica_reinvia_email_fault_tool_strip_button")
  String posta_elettronica_reinvia_email_fault_tool_strip_button(); 

  @Key("posta_elettronica_reinvia_tool_strip_button")
  String posta_elettronica_reinvia_tool_strip_button(); 

  @Key("posta_elettronica_reload_tool_strip_button")
  String posta_elettronica_reload_tool_strip_button(); 

  @Key("posta_elettronica_rilascia_tool_strip_button")
  String posta_elettronica_rilascia_tool_strip_button(); 

  @Key("posta_elettronica_rilascio_errore")
  String posta_elettronica_rilascio_errore(); 

  @Key("posta_elettronica_rilascio_errore_parziale")
  String posta_elettronica_rilascio_errore_parziale(); 

  @Key("posta_elettronica_rilascio_errore_totale")
  String posta_elettronica_rilascio_errore_totale(); 

  @Key("posta_elettronica_rilascio_in_corso")
  String posta_elettronica_rilascio_in_corso(); 

  @Key("posta_elettronica_rilascio_successo")
  String posta_elettronica_rilascio_successo(); 

  @Key("posta_elettronica_rispondi_a_tutti_allegati_submenu_item")
  String posta_elettronica_rispondi_a_tutti_allegati_submenu_item(); 

  @Key("posta_elettronica_rispondi_a_tutti_menu_item")
  String posta_elettronica_rispondi_a_tutti_menu_item(); 

  @Key("posta_elettronica_rispondi_a_tutti_standard_submenu_item")
  String posta_elettronica_rispondi_a_tutti_standard_submenu_item(); 

  @Key("posta_elettronica_rispondi_allegati_submenu_item")
  String posta_elettronica_rispondi_allegati_submenu_item(); 

  @Key("posta_elettronica_rispondi_menu_item")
  String posta_elettronica_rispondi_menu_item(); 

  @Key("posta_elettronica_rispondi_standard_submenu_item")
  String posta_elettronica_rispondi_standard_submenu_item(); 

  @Key("posta_elettronica_rispondi_tool_strip_button")
  String posta_elettronica_rispondi_tool_strip_button(); 

  @Key("posta_elettronica_scarica_mail_menu_item")
  String posta_elettronica_scarica_mail_menu_item(); 

  @Key("posta_elettronica_scarica_mail_protocollo_tool_strip_button")
  String posta_elettronica_scarica_mail_protocollo_tool_strip_button(); 

  @Key("posta_elettronica_scarica_mail_senzabusta_pec_trasporto_menu_item")
  String posta_elettronica_scarica_mail_senzabusta_pec_trasporto_menu_item(); 

  @Key("posta_elettronica_set_uo_competente")
  String posta_elettronica_set_uo_competente(); 

  @Key("posta_elettronica_setta_azione_da_fare_errore")
  String posta_elettronica_setta_azione_da_fare_errore(); 

  @Key("posta_elettronica_setta_azione_da_fare_errore_parziale")
  String posta_elettronica_setta_azione_da_fare_errore_parziale(); 

  @Key("posta_elettronica_setta_azione_da_fare_errore_totale")
  String posta_elettronica_setta_azione_da_fare_errore_totale(); 

  @Key("posta_elettronica_setta_azione_da_fare_in_corso")
  String posta_elettronica_setta_azione_da_fare_in_corso(); 

  @Key("posta_elettronica_setta_azione_da_fare_successo")
  String posta_elettronica_setta_azione_da_fare_successo(); 

  @Key("posta_elettronica_stampa_pdf_mail_tool_strip_button")
  String posta_elettronica_stampa_pdf_mail_tool_strip_button(); 

  @Key("posta_elettronica_stato_consolidamento_dest")
  String posta_elettronica_stato_consolidamento_dest(); 

  @Key("posta_elettronica_stato_lavorazione_item")
  String posta_elettronica_stato_lavorazione_item(); 

  @Key("posta_elettronica_tipo_dest")
  String posta_elettronica_tipo_dest(); 


  @Key("posta_elettronica_title_item_casella_account")
  String posta_elettronica_title_item_casella_account(); 

  @Key("posta_elettronica_title_item_des_uo_assegnataria")
  String posta_elettronica_title_item_des_uo_assegnataria(); 

  @Key("posta_elettronica_title_item_incaricoa")
  String posta_elettronica_title_item_incaricoa(); 

  @Key("posta_elettronica_title_item_progressivo")
  String posta_elettronica_title_item_progressivo(); 

  @Key("posta_elettronica_title_item_protocollata_come")
  String posta_elettronica_title_item_protocollata_come(); 

  @Key("posta_elettronica_title_item_stato_consolidamento")
  String posta_elettronica_title_item_stato_consolidamento(); 

  @Key("posta_elettronica_title_item_stato_lavorazione")
  String posta_elettronica_title_item_stato_lavorazione(); 

  @Key("posta_elettronica_title_item_ts_invio")
  String posta_elettronica_title_item_ts_invio(); 

  @Key("posta_elettronica_title_item_ts_ricezione")
  String posta_elettronica_title_item_ts_ricezione(); 

  @Key("posta_elettronica_title_messages_id")
  String posta_elettronica_title_messages_id(); 

  @Key("posta_elettronica_title_orario_assegnazione")
  String posta_elettronica_title_orario_assegnazione(); 

  @Key("posta_elettronica_title_orario_incaricodal")
  String posta_elettronica_title_orario_incaricodal(); 

  @Key("posta_elettronica_title_orario_stato_lavorazione")
  String posta_elettronica_title_orario_stato_lavorazione(); 

  @Key("posta_elettronica_title_ts_assegnazione_dal")
  String posta_elettronica_title_ts_assegnazione_dal(); 

  @Key("posta_elettronica_title_ts_incaricodal")
  String posta_elettronica_title_ts_incaricodal(); 

  @Key("posta_elettronica_title_ts_stato_lavorazione_apartiredal")
  String posta_elettronica_title_ts_stato_lavorazione_apartiredal(); 


  @Key("posta_elettronica_ts_incaricodal")
  String posta_elettronica_ts_incaricodal(); 

  @Key("posta_elettronica_ts_invio_item")
  String posta_elettronica_ts_invio_item(); 

  @Key("posta_elettronica_ts_ricezione_item")
  String posta_elettronica_ts_ricezione_item(); 

  @Key("posta_elettronica_ts_uo_assegnazione_dal")
  String posta_elettronica_ts_uo_assegnazione_dal(); 

  @Key("posta_elettronica_viewer_contenuti_item1")
  String posta_elettronica_viewer_contenuti_item1(); 

  @Key("posta_elettronica_viewer_contenuti_item")
  String posta_elettronica_viewer_contenuti_item(); 

  @Key("posta_elettronica_visualizza_cronologia_icon")
  String posta_elettronica_visualizza_cronologia_icon(); 

  @Key("posta_elettronica_visualizza_dett_prot_icon")
  String posta_elettronica_visualizza_dett_prot_icon(); 

  @Key("posta_elettronica_visualizza_dettaglio_email_predecessore_icon")
  String posta_elettronica_visualizza_dettaglio_email_predecessore_icon(); 

  @Key("posta_elettronica_visualizza_msg_assegnazione_icon")
  String posta_elettronica_visualizza_msg_assegnazione_icon(); 

  @Key("posta_mittente_email_inviata_attori_item")
  String posta_mittente_email_inviata_attori_item(); 
  
  @Key("posta_elettronica_messaggio_errore_ricevuta_pec_item")
  String posta_elettronica_messaggio_errore_ricevuta_pec_item();
  
  @Key("registra_repertorio_email_window_title")
  String registra_repertorio_email_window_title();
  
  @Key("edita_protocollo_window_from_mail_title")
  String edita_protocollo_window_from_mail_title();
  
  @Key("posta_elettronica_filter_ricercaMailArchiviate")
  String posta_elettronica_filter_ricercaMailArchiviate();
  
  @Key("posta_elettronica_filter_ricercamailarchiviateselect_solo_archiviate")
  String posta_elettronica_filter_ricercamailarchiviateselect_solo_archiviate();
  
  @Key("posta_elettronica_filter_ricercamailarchiviateselect_anche_archiviate")
  String posta_elettronica_filter_ricercamailarchiviateselect_anche_archiviate();

 //#############################################################
 //#  INVIO MESSAGGIO MULTI DESTINATARI DA XLS
 //#############################################################  
 
  @Key("invio_mail_form_multi_destinatari_xls_nomeFileXlsItem")
  String invio_mail_form_multi_destinatari_xls_nomeFileXlsItem();
  
  @Key("invio_mail_form_multi_destinatari_xls_rigaXlsDaItem")
  String invio_mail_form_multi_destinatari_xls_rigaXlsDaItem();
  
  @Key("invio_mail_form_multi_destinatari_xls_rigaXlsAItem")
  String invio_mail_form_multi_destinatari_xls_rigaXlsAItem();
  
  @Key("invio_mail_form_multi_destinatari_xls_noteAvvertimentoItem")
  String invio_mail_form_multi_destinatari_xls_noteAvvertimentoItem();
    
  @Key("invio_mail_form_multi_destinatari_xls_invioEmailAsk_message")
  String invio_mail_form_multi_destinatari_xls_invioEmailAsk_message();
 
  @Key("invio_mail_form_multi_destinatari_xls_rigaXlsDaItem_nonValido_errorMessage")
  String invio_mail_form_multi_destinatari_xls_rigaXlsDaItem_nonValido_errorMessage();
  
  @Key("invio_mail_form_multi_destinatari_xls_rigaXlsDaItem_obbligatorio_errorMessage")
  String invio_mail_form_multi_destinatari_xls_rigaXlsDaItem_obbligatorio_errorMessage();

  @Key("invio_mail_form_multi_destinatari_xls_dettagliXlsIndirizziEmailItem_obbligatorio_errorMessage")
  String invio_mail_form_multi_destinatari_xls_dettagliXlsIndirizziEmailItem_obbligatorio_errorMessage();
  
  @Key("invio_mail_form_multi_destinatari_xls_esitoInvio_OK_value")
  String invio_mail_form_multi_destinatari_xls_esitoInvio_OK_value(String attribute0);
  
  
  
  //#############################################################
  //#  INDIRIZZI EMAIL XLS CANVAS
  //#############################################################  
  @Key("dettagli_xls_indirizzi_email_canvas_campoXlsItem")
  String dettagli_xls_indirizzi_email_canvas_campoXlsItem();
  
  @Key("dettagli_xls_indirizzi_email_canvas_colonnaXlsItem")
  String dettagli_xls_indirizzi_email_canvas_colonnaXlsItem();
  
  @Key("dettagli_xls_indirizzi_email_item_title")
  String dettagli_xls_indirizzi_email_item_title();

  
  
  //#############################################################
  //#          POSTA IN ENTRATA/USCITA REGISTRAZIONE            #
  //#############################################################	

  @Key("postainarrivoregistrazione_window_title")
  String postainarrivoregistrazione_window_title(); 

  @Key("postainuscitaregistrazione_detail_dataInvioItem_title")
  String postainuscitaregistrazione_detail_dataInvioItem_title(); 

  @Key("postainuscitaregistrazione_detail_datiPrincipaliSection_title")
  String postainuscitaregistrazione_detail_datiPrincipaliSection_title(); 

  @Key("postainuscitaregistrazione_detail_destinatariItem_title")
  String postainuscitaregistrazione_detail_destinatariItem_title(); 

  @Key("postainuscitaregistrazione_detail_edit_title")
  String postainuscitaregistrazione_detail_edit_title(String attribute0);

  @Key("postainuscitaregistrazione_detail_idEmailItem_title")
  String postainuscitaregistrazione_detail_idEmailItem_title(); 

  @Key("postainuscitaregistrazione_detail_new_title")
  String postainuscitaregistrazione_detail_new_title(); 

  @Key("postainuscitaregistrazione_detail_statoConsolidamentoItem_title")
  String postainuscitaregistrazione_detail_statoConsolidamentoItem_title(); 

  @Key("postainuscitaregistrazione_detail_tipoCasellaMittenteItem_title")
  String postainuscitaregistrazione_detail_tipoCasellaMittenteItem_title(); 

  @Key("postainuscitaregistrazione_detail_view_title")
  String postainuscitaregistrazione_detail_view_title(String attribute0);

  @Key("postainuscitaregistrazione_list_dataInvioField_title")
  String postainuscitaregistrazione_list_dataInvioField_title(); 

  @Key("postainuscitaregistrazione_list_destinatariField_title")
  String postainuscitaregistrazione_list_destinatariField_title(); 

  @Key("postainuscitaregistrazione_list_idEmailField_title")
  String postainuscitaregistrazione_list_idEmailField_title(); 

  @Key("postainuscitaregistrazione_list_statoConsolidamentoField_title")
  String postainuscitaregistrazione_list_statoConsolidamentoField_title(); 

  @Key("postainuscitaregistrazione_list_tipoCasellaMittenteField_title")
  String postainuscitaregistrazione_list_tipoCasellaMittenteField_title(); 

  @Key("postainuscitaregistrazione_tipoCasellaMittente_C_Alt_value")
  String postainuscitaregistrazione_tipoCasellaMittente_C_Alt_value(); 

  @Key("postainuscitaregistrazione_tipoCasellaMittente_O_Alt_value")
  String postainuscitaregistrazione_tipoCasellaMittente_O_Alt_value(); 

  @Key("postainuscitaregistrazione_window_title")
  String postainuscitaregistrazione_window_title(); 

  //#############################################################
  //#                       RICEVUTA POSTA IN USCITA            #
  //#############################################################

  @Key("ricevutePostaInUscitaWindow_emptyForDest_message")
  String ricevutePostaInUscitaWindow_emptyForDest_message(); 

  @Key("ricevutePostaInUscitaWindow_empty_message")
  String ricevutePostaInUscitaWindow_empty_message(); 

  @Key("ricevutePostaInUscitaWindow_title")
  String ricevutePostaInUscitaWindow_title(); 

  @Key("ricevutepostainuscita_detail_categoriaItem_title")
  String ricevutepostainuscita_detail_categoriaItem_title(); 

  @Key("ricevutepostainuscita_detail_dataRicezioneItem_title")
  String ricevutepostainuscita_detail_dataRicezioneItem_title(); 

  @Key("ricevutepostainuscita_detail_datiPrincipaliSection_title")
  String ricevutepostainuscita_detail_datiPrincipaliSection_title(); 

  @Key("ricevutepostainuscita_detail_edit_title")
  String ricevutepostainuscita_detail_edit_title(String attribute0);

  @Key("ricevutepostainuscita_detail_mittenteItem_title")
  String ricevutepostainuscita_detail_mittenteItem_title(); 

  @Key("ricevutepostainuscita_detail_new_title")
  String ricevutepostainuscita_detail_new_title(); 

  @Key("ricevutepostainuscita_detail_view_title")
  String ricevutepostainuscita_detail_view_title(String attribute0);

  @Key("ricevutepostainuscita_list_categoriaField_title")
  String ricevutepostainuscita_list_categoriaField_title(); 

  @Key("ricevutepostainuscita_list_dataRicezioneField_title")
  String ricevutepostainuscita_list_dataRicezioneField_title(); 

  @Key("ricevutepostainuscita_list_mittenteField_title")
  String ricevutepostainuscita_list_mittenteField_title(); 

  @Key("ricevutepostainuscita_lookupRicevutePostaInUscitaPopup_title")
  String ricevutepostainuscita_lookupRicevutePostaInUscitaPopup_title(); 

  //#############################################################
  //#                     PRIORITA                              #
  //#############################################################	

  @Key("prioritaAlta_Alt_value")
  String prioritaAlta_Alt_value(); 

  @Key("prioritaAltissima_Alt_value")
  String prioritaAltissima_Alt_value(); 

  @Key("prioritaBassa_Alt_value")
  String prioritaBassa_Alt_value(); 

  @Key("prioritaMedia_Alt_value")
  String prioritaMedia_Alt_value(); 

  @Key("prioritaNormale_Alt_value")
  String prioritaNormale_Alt_value(); 

  @Key("prendi_in_carico_label_button")
  String prendi_in_carico_label_button(); 
  
  //#############################################################
  //#      VISURE PERSONALI	                                    #
  //#############################################################
  //#VisurePersonaliList
  @Key("visure_personali_list_idProcedimentoField")
  String visure_personali_list_idProcedimentoField(); 

  @Key("visure_personali_list_nomeProcedimentoField")
  String visure_personali_list_nomeProcedimentoField(); 

//  @Key("visure_personali_list_estremiProcedimentoField")
//  String visure_personali_list_estremiProcedimentoField(); 

//  @Key("visure_personali_list_tipoProcedimentoField")
//  String visure_personali_list_tipoProcedimentoField(); 

//  @Key("visure_personali_list_oggettoProcedimentoField")
//  String visure_personali_list_oggettoProcedimentoField(); 

  @Key("visure_personali_list_dataAvvioProcedimentoField")
  String visure_personali_list_dataAvvioProcedimentoField(); 

  @Key("visure_personali_list_decorrenzaProcedimentoField")
  String visure_personali_list_decorrenzaProcedimentoField(); 

  @Key("visure_personali_list_processoPadreProcedimentoField")
  String visure_personali_list_processoPadreProcedimentoField(); 

  @Key("visure_personali_list_statoProcedimentoField")
  String visure_personali_list_statoProcedimentoField(); 

  @Key("visure_personali_list_documentoInizialeProcedimentoField")
  String visure_personali_list_documentoInizialeProcedimentoField(); 

//  @Key("visure_personali_list_attoParereFinaleProcedimentoField")
//  String visure_personali_list_attoParereFinaleProcedimentoField(); 

  @Key("visure_personali_list_inFaseProcedimentoField")
  String visure_personali_list_inFaseProcedimentoField(); 

  @Key("visure_personali_list_scadenzaTermineProcedimentoField")
  String visure_personali_list_scadenzaTermineProcedimentoField(); 

  @Key("visure_personali_list_flgScadenzaTermineProcedimentoField")
  String visure_personali_list_flgScadenzaTermineProcedimentoField(); 

  @Key("visure_personali_list_ultimoTaskProcedimentoField")
  String visure_personali_list_ultimoTaskProcedimentoField(); 

  @Key("visure_personali_list_messaggioUltimoTaskProcedimentoField")
  String visure_personali_list_messaggioUltimoTaskProcedimentoField(); 

  @Key("visure_personali_list_noteProcedimentoField")
  String visure_personali_list_noteProcedimentoField(); 

//  @Key("visure_personali_list_nominativiProcedimentoField")
//  String visure_personali_list_nominativiProcedimentoField(); 

//  @Key("visure_personali_list_assegnatarioProcedimentoField")
//  String visure_personali_list_assegnatarioProcedimentoField(); 

  @Key("visure_personali_list_prossimeAttivitaField")
  String visure_personali_list_prossimeAttivitaField(); 

  @Key("visure_personali_list_avviatoDaField")
  String visure_personali_list_avviatoDaField(); 
  
  @Key("visure_personali_flgScadenzaTermineProcedimento_1_value")
  String visure_personali_flgScadenzaTermineProcedimento_1_value(); 
  
  @Key("visure_personali_list_richiedenteField")
  String visure_personali_list_richiedenteField();

  @Key("visure_personali_list_tipoRichiedenteField")
  String visure_personali_list_tipoRichiedenteField();
  
  @Key("visure_personali_flgTipoRichiedenteEsternovalue")
  String visure_personali_flgTipoRichiedenteEsternovalue();
  
  @Key("visure_personali_flgTipoRichiedenteInternovalue")
  String visure_personali_flgTipoRichiedenteInternovalue();
  
  @Key("visure_personali_richAttiDiFabbrica_value")
  String visure_personali_richAttiDiFabbrica_value();
  
  @Key("visure_personali_list_fabbricatoAttiCostrFino1996Esternovalue")
  String visure_personali_list_fabbricatoAttiCostrFino1996Esternovalue();
  
  @Key("visure_personali_list_fabbricatoAttiCostrFino1996Internovalue")
  String visure_personali_list_fabbricatoAttiCostrFino1996Internovalue();
  
  @Key("visure_personali_list_fabbricatoAttiCostrFino1996EsternoInternovalue")
  String visure_personali_list_fabbricatoAttiCostrFino1996EsternoInternovalue();
  
  @Key("visure_personali_list_fabbricatoAttiCostrDa1997Esternovalue")
  String visure_personali_list_fabbricatoAttiCostrDa1997Esternovalue();
  
  @Key("visure_personali_list_fabbricatoAttiCostrDa1997Internovalue")
  String visure_personali_list_fabbricatoAttiCostrDa1997Internovalue();
  
  @Key("visure_personali_list_fabbricatoAttiCostrDa1997EsternoInternovalue")
  String visure_personali_list_fabbricatoAttiCostrDa1997EsternoInternovalue();
  
  @Key("visure_personali_list_respIstruttoriaField")
  String  visure_personali_list_respIstruttoriaField();
  
  @Key("visure_personali_list_indirizzoField")
  String visure_personali_list_indirizzoField();
  
  @Key("visure_personali_list_attiRichiestiField")
  String visure_personali_list_attiRichiestiField();
  
  @Key("visure_personali_list_classificaAttiField")
  String visure_personali_list_classificaAttiField();
  
  @Key("visure_personali_list_archiviCoinvoltiField")
  String visure_personali_list_archiviCoinvoltiField();
  
  @Key("visure_personali_list_udcField")
  String visure_personali_list_udcField();
  
  @Key("visure_personali_list_appCittadellaField")
  String visure_personali_list_appCittadellaField();
  
  @Key("visure_personali_list_appBerninaField")
  String visure_personali_list_appBerninaField();
  
  @Key("visure_personali_list_codPraticaSUEField")
  String visure_personali_list_codPraticaSUEField();
  
  @Key("visure_personali_list_tipologiaRichiedenteSUEField")
  String visure_personali_list_tipologiaRichiedenteSUEField();
  
  @Key("visure_personali_list_motivazioneVisuraSUEField")
  String visure_personali_list_motivazioneVisuraSUEField();

  @Key("visure_personali_list_richAttiDiFabbricaField")
  String visure_personali_list_richAttiDiFabbricaField();

  @Key("visure_personali_list_fabbricatoAttiCostrFino1996Field")
  String visure_personali_list_fabbricatoAttiCostrFino1996Field();

  @Key("visure_personali_list_fabbricatoAttiCostrDa1997Field")
  String visure_personali_list_fabbricatoAttiCostrDa1997Field();
  
//  @Key("visure_personali_list_richiestoCartaceoField")
//  String visure_personali_list_richiestoCartaceoField();
  
  @Key("visure_personali_list_richiestoCartaceo_value")
  String visure_personali_list_richiestoCartaceo_value();

  @Key("visure_personali_eliminazioneFisicaAsk_message")
  String visure_personali_eliminazioneFisicaAsk_message(); 

  @Key("visure_personali_annullamentoLogicoAsk_message")
  String visure_personali_annullamentoLogicoAsk_message(); 

  @Key("visure_personali_iconaFolderButton_prompt")
  String visure_personali_iconaFolderButton_prompt(); 

  @Key("visure_personali_iconaFolderDetailButton_prompt")
  String visure_personali_iconaFolderDetailButton_prompt(); 
  
  @Key("visure_personali_fileRichiestaMenuItem_prompt")
  String visure_personali_fileRichiestaMenuItem_prompt();
  
  @Key("visure_personali_visualizzaIstanzaButton")
  String visure_personali_visualizzaIstanzaButton();
  
  @Key("visure_personali_impostaRespIstruttoriaButtonMassivo")
  String visure_personali_impostaRespIstruttoriaButtonMassivo();
  
  @Key("visure_personali_list_ultimaAttivitaEsito")
  String visure_personali_list_ultimaAttivitaEsito();
  
  @Key("visure_personali_list_canaleArrivoField")
  String visure_personali_list_canaleArrivoField();
  
  @Key("visure_personali_list_richModificheField")
  String visure_personali_list_richModificheField();
 
  //#############################################################
  //#      VISURE IN ITER                                 #
  //#############################################################
  
  //#ProcedimentiInIterList
  @Key("visure_in_iter_list_idProcedimentoField")
  String visure_in_iter_list_idProcedimentoField(); 

  @Key("visure_in_iter_list_nomeProcedimentoField")
  String visure_in_iter_list_nomeProcedimentoField(); 

//  @Key("visure_in_iter_list_estremiProcedimentoField")
//  String visure_in_iter_list_estremiProcedimentoField(); 

//  @Key("visure_in_iter_list_tipoProcedimentoField")
//  String visure_in_iter_list_tipoProcedimentoField(); 

//  @Key("visure_in_iter_list_oggettoProcedimentoField")
//  String visure_in_iter_list_oggettoProcedimentoField(); 

  @Key("visure_in_iter_list_dataAvvioProcedimentoField")
  String visure_in_iter_list_dataAvvioProcedimentoField(); 

  @Key("visure_in_iter_list_decorrenzaProcedimentoField")
  String visure_in_iter_list_decorrenzaProcedimentoField(); 

  @Key("visure_in_iter_list_processoPadreProcedimentoField")
  String visure_in_iter_list_processoPadreProcedimentoField(); 

  @Key("visure_in_iter_list_statoProcedimentoField")
  String visure_in_iter_list_statoProcedimentoField(); 

  @Key("visure_in_iter_list_documentoInizialeProcedimentoField")
  String visure_in_iter_list_documentoInizialeProcedimentoField(); 

//  @Key("visure_in_iter_list_attoParereFinaleProcedimentoField")
//  String visure_in_iter_list_attoParereFinaleProcedimentoField(); 

  @Key("visure_in_iter_list_inFaseProcedimentoField")
  String visure_in_iter_list_inFaseProcedimentoField(); 

  @Key("visure_in_iter_list_scadenzaTermineProcedimentoField")
  String visure_in_iter_list_scadenzaTermineProcedimentoField(); 

  @Key("visure_in_iter_list_flgScadenzaTermineProcedimentoField")
  String visure_in_iter_list_flgScadenzaTermineProcedimentoField(); 

  @Key("visure_in_iter_list_ultimoTaskProcedimentoField")
  String visure_in_iter_list_ultimoTaskProcedimentoField(); 

  @Key("visure_in_iter_list_messaggioUltimoTaskProcedimentoField")
  String visure_in_iter_list_messaggioUltimoTaskProcedimentoField(); 

  @Key("visure_in_iter_list_noteProcedimentoField")
  String visure_in_iter_list_noteProcedimentoField(); 

//  @Key("visure_in_iter_list_nominativiProcedimentoField")
//  String visure_in_iter_list_nominativiProcedimentoField(); 

//  @Key("visure_in_iter_list_assegnatarioProcedimentoField")
//  String visure_in_iter_list_assegnatarioProcedimentoField(); 

  @Key("visure_in_iter_list_prossimeAttivitaField")
  String visure_in_iter_list_prossimeAttivitaField(); 

  @Key("visure_in_iter_list_avviatoDaField")
  String visure_in_iter_list_avviatoDaField(); 
  
  @Key("visure_in_iter_list_richiedenteField")
  String visure_in_iter_list_richiedenteField();

  @Key("visure_in_iter_list_tipoRichiedenteField")
  String visure_in_iter_list_tipoRichiedenteField();
  
  @Key("visure_in_iter_flgTipoRichiedenteEsternovalue")
  String visure_in_iter_flgTipoRichiedenteEsternovalue();
  
  @Key("visure_in_iter_flgTipoRichiedenteInternovalue")
  String visure_in_iter_flgTipoRichiedenteInternovalue();
  
  @Key("visure_in_iter_flgTipoRichiedenteEsternoInternovalue")
  String visure_in_iter_flgTipoRichiedenteEsternoInternovalue();
  
  @Key("visure_in_iter_list_richAttiDiFabbrica_value")
  String visure_in_iter_list_richAttiDiFabbrica_value();
  
  @Key("visure_in_iter_fabbricatoAttiCostrFino1996Esterno_value")
  String visure_in_iter_fabbricatoAttiCostrFino1996Esterno_value();
  
  @Key("visure_in_iter_fabbricatoAttiCostrFino1996Interno_value")
  String visure_in_iter_fabbricatoAttiCostrFino1996Interno_value();
  
  @Key("visure_in_iter_fabbricatoAttiCostrFino1996EsternoInterno_value")
  String visure_in_iter_fabbricatoAttiCostrFino1996EsternoInterno_value();
  
  @Key("visure_in_iter_fabbricatoAttiCostrDa1997Esterno_value")
  String visure_in_iter_fabbricatoAttiCostrDa1997Esterno_value();
  
  @Key("visure_in_iter_fabbricatoAttiCostrDa1997Interno_value")
  String visure_in_iter_fabbricatoAttiCostrDa1997Interno_value();
  
  @Key("visure_in_iter_fabbricatoAttiCostrDa1997EsternoInterno_value")
  String visure_in_iter_fabbricatoAttiCostrDa1997EsternoInterno_value();
  
  @Key("visure_in_iter_richiestoCartaceo_value")
  String visure_in_iter_richiestoCartaceo_value();
  
  @Key("visure_in_iter_list_respIstruttoriaField")
  String  visure_in_iter_list_respIstruttoriaField();
  
  @Key("visure_in_iter_list_indirizzoField")
  String visure_in_iter_list_indirizzoField();
  
  @Key("visure_in_iter_list_attiRichiestiField")
  String visure_in_iter_list_attiRichiestiField();
  
  @Key("visure_in_iter_list_classificaAttiField")
  String visure_in_iter_list_classificaAttiField();
  
  @Key("visure_in_iter_list_archiviCoinvoltiField")
  String visure_in_iter_list_archiviCoinvoltiField();
  
  @Key("visure_in_iter_list_udcField")
  String visure_in_iter_list_udcField();
  
  @Key("visure_in_iter_list_appCittadellaField")
  String visure_in_iter_list_appCittadellaField();
  
  @Key("visure_in_iter_list_appBerninaField")
  String visure_in_iter_list_appBerninaField();
  
  @Key("visure_in_iter_list_codPraticaSUEField")
  String visure_in_iter_list_codPraticaSUEField();

  @Key("visure_in_iter_list_tipologiaRichiedenteSUEField")
  String visure_in_iter_list_tipologiaRichiedenteSUEField();
  
  @Key("visure_in_iter_list_motivazioneVisuraSUEField")
  String visure_in_iter_list_motivazioneVisuraSUEField();
  
  @Key("visure_in_iter_list_richAttiDiFabbricaField")
  String visure_in_iter_list_richAttiDiFabbricaField();
  
  @Key("visure_in_iter_list_fabbricatoAttiCostrFino1996Field")
  String visure_in_iter_list_fabbricatoAttiCostrFino1996Field();
  
  @Key("visure_in_iter_list_fabbricatoAttiCostrDa1997Field")
  String visure_in_iter_list_fabbricatoAttiCostrDa1997Field();
  
//  @Key("visure_in_iter_list_richiestoCartaceoField")
//  String visure_in_iter_list_richiestoCartaceoField();
  
  @Key("visure_in_iter_flgScadenzaTermineProcedimento_1_value")
  String visure_in_iter_flgScadenzaTermineProcedimento_1_value(); 

  @Key("visure_in_iter_annullamentoLogicoAsk_message")
  String visure_in_iter_annullamentoLogicoAsk_message(); 

  @Key("visure_in_iter_eliminazioneFisicaAsk_message")
  String visure_in_iter_eliminazioneFisicaAsk_message(); 

  @Key("visure_in_iter_iconaFolderButton_prompt")
  String visure_in_iter_iconaFolderButton_prompt(); 

  @Key("visure_in_iter_iconaFolderDetailButton_prompt")
  String visure_in_iter_iconaFolderDetailButton_prompt();
  
  @Key("visure_in_iter_visualizzaIstanzaButton")
  String visure_in_iter_visualizzaIstanzaButton();
  
  @Key("visure_in_iter_impostaRespIstruttoriaButtonMassivo")
  String visure_in_iter_impostaRespIstruttoriaButtonMassivo();
  
  @Key("visure_in_iter_fileRichiestaMenuItem_prompt")
  String visure_in_iter_fileRichiestaMenuItem_prompt();
  
  @Key("visure_in_iter_list_richModificheFieldField")
  String visure_in_iter_list_richModificheFieldField();
  
  @Key("visure_in_iter_list_ultimaAttivitaEsito")
  String visure_in_iter_list_ultimaAttivitaEsito();
  
  @Key("visure_in_iter_list_canaleArrivo")
  String visure_in_iter_list_canaleArrivo();

  //#############################################################
  //#      PROCEDIMENTI IN ITER                                 #
  //#############################################################

  //#ProcedimentiInIterList
  @Key("procedimenti_in_iter_list_idProcedimentoField")
  String procedimenti_in_iter_list_idProcedimentoField(); 

  @Key("procedimenti_in_iter_list_nomeProcedimentoField")
  String procedimenti_in_iter_list_nomeProcedimentoField(); 

  @Key("procedimenti_in_iter_list_estremiProcedimentoField")
  String procedimenti_in_iter_list_estremiProcedimentoField(); 

  @Key("procedimenti_in_iter_list_tipoProcedimentoField")
  String procedimenti_in_iter_list_tipoProcedimentoField(); 

  @Key("procedimenti_in_iter_list_oggettoProcedimentoField")
  String procedimenti_in_iter_list_oggettoProcedimentoField(); 

  @Key("procedimenti_in_iter_list_dataAvvioProcedimentoField")
  String procedimenti_in_iter_list_dataAvvioProcedimentoField(); 

  @Key("procedimenti_in_iter_list_decorrenzaProcedimentoField")
  String procedimenti_in_iter_list_decorrenzaProcedimentoField(); 

  @Key("procedimenti_in_iter_list_processoPadreProcedimentoField")
  String procedimenti_in_iter_list_processoPadreProcedimentoField(); 

  @Key("procedimenti_in_iter_list_statoProcedimentoField")
  String procedimenti_in_iter_list_statoProcedimentoField(); 

  @Key("procedimenti_in_iter_list_documentoInizialeProcedimentoField")
  String procedimenti_in_iter_list_documentoInizialeProcedimentoField(); 

  @Key("procedimenti_in_iter_list_attoParereFinaleProcedimentoField")
  String procedimenti_in_iter_list_attoParereFinaleProcedimentoField(); 

  @Key("procedimenti_in_iter_list_inFaseProcedimentoField")
  String procedimenti_in_iter_list_inFaseProcedimentoField(); 

  @Key("procedimenti_in_iter_list_scadenzaTermineProcedimentoField")
  String procedimenti_in_iter_list_scadenzaTermineProcedimentoField(); 

  @Key("procedimenti_in_iter_list_flgScadenzaTermineProcedimentoField")
  String procedimenti_in_iter_list_flgScadenzaTermineProcedimentoField(); 

  @Key("procedimenti_in_iter_list_ultimaAttivitaField")
  String procedimenti_in_iter_list_ultimaAttivitaField(); 

  @Key("procedimenti_in_iter_list_ultimaAttivitaMessaggioField")
  String procedimenti_in_iter_list_ultimaAttivitaMessaggioField(); 

  @Key("procedimenti_in_iter_list_noteProcedimentoField")
  String procedimenti_in_iter_list_noteProcedimentoField(); 

  @Key("procedimenti_in_iter_list_nominativiProcedimentoField")
  String procedimenti_in_iter_list_nominativiProcedimentoField(); 

  @Key("procedimenti_in_iter_list_assegnatarioProcedimentoField")
  String procedimenti_in_iter_list_assegnatarioProcedimentoField(); 

  @Key("procedimenti_in_iter_list_prossimeAttivitaField")
  String procedimenti_in_iter_list_prossimeAttivitaField(); 

  @Key("procedimenti_in_iter_list_avviatoDaField")
  String procedimenti_in_iter_list_avviatoDaField(); 
  
  @Key("procedimenti_in_iter_list_dataPresentazioneField")
  String procedimenti_in_iter_list_dataPresentazioneField();
  
  @Key("procedimenti_in_iter_list_tipiTributoField")
  String procedimenti_in_iter_list_tipiTributoField();
  
  @Key("procedimenti_in_iter_list_anniTributoField")
  String procedimenti_in_iter_list_anniTributoField();
  
  @Key("procedimenti_in_iter_list_attiRiferimentoField")
  String procedimenti_in_iter_list_attiRiferimentoField();
  
  @Key("procedimenti_in_iter_list_flgPresenzaProcedimentiField")
  String procedimenti_in_iter_list_flgPresenzaProcedimentiField();
  
  @Key("procedimenti_in_iter_flgPresenzaProcedimenti_1_value")
  String procedimenti_in_iter_flgPresenzaProcedimenti_1_value();
  
  @Key("procedimenti_in_iter_list_ultimaAttivitaEsitoField")
  String procedimenti_in_iter_list_ultimaAttivitaEsitoField();
  
  @Key("procedimenti_in_iter_list_assegnatarioIstruttoriaField")
  String procedimenti_in_iter_list_assegnatarioIstruttoriaField();
  
  @Key("procedimenti_in_iter_list_assegnatarioIstruttoriaPreIstruttoriaField")
  String procedimenti_in_iter_list_assegnatarioIstruttoriaPreIstruttoriaField();
  
  @Key("procedimenti_in_iter_list_denominazioneRichiedenteField")
  String procedimenti_in_iter_list_denominazioneRichiedenteField();
  
  @Key("procedimenti_in_iter_list_codFiscalePivaRichiedenteField")
  String procedimenti_in_iter_list_codFiscalePivaRichiedenteField();
  
  
  //#ProcedimentiInIterDetail	
  @Key("procedimenti_in_iter_detail_idProcedimentoItem")
  String procedimenti_in_iter_detail_idProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_nomeProcedimentoItem")
  String procedimenti_in_iter_detail_nomeProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_estremiProcedimentoItem")
  String procedimenti_in_iter_detail_estremiProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_tipoProcedimentoItem")
  String procedimenti_in_iter_detail_tipoProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_oggettoProcedimentoItem")
  String procedimenti_in_iter_detail_oggettoProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_dataAvvioProcedimentoItem")
  String procedimenti_in_iter_detail_dataAvvioProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_decorrenzaProcedimentoItem")
  String procedimenti_in_iter_detail_decorrenzaProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_processoPadreProcedimentoItem")
  String procedimenti_in_iter_detail_processoPadreProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_statoProcedimentoItem")
  String procedimenti_in_iter_detail_statoProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_documentoInizialeProcedimentoItem")
  String procedimenti_in_iter_detail_documentoInizialeProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_attoParereFinaleProcedimentoItem")
  String procedimenti_in_iter_detail_attoParereFinaleProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_inFaseProcedimentoItem")
  String procedimenti_in_iter_detail_inFaseProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_scadenzaTermineProcedimentoItem")
  String procedimenti_in_iter_detail_scadenzaTermineProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_flgScadenzaTermineProcedimentoItem")
  String procedimenti_in_iter_detail_flgScadenzaTermineProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_ultimaAttivitaItem")
  String procedimenti_in_iter_detail_ultimaAttivitaItem(); 

  @Key("procedimenti_in_iter_detail_ultimaAttivitaMessaggioItem")
  String procedimenti_in_iter_detail_ultimaAttivitaMessaggioItem(); 

  @Key("procedimenti_in_iter_detail_noteProcedimentoItem")
  String procedimenti_in_iter_detail_noteProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_nominativiProcedimentoItem")
  String procedimenti_in_iter_detail_nominativiProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_assegnatarioProcedimentoItem")
  String procedimenti_in_iter_detail_assegnatarioProcedimentoItem(); 

  @Key("procedimenti_in_iter_detail_prossimeAttivitaItem")
  String procedimenti_in_iter_detail_prossimeAttivitaItem(); 

  @Key("procedimenti_in_iter_detail_avviatoDaItem")
  String procedimenti_in_iter_detail_avviatoDaItem(); 

  @Key("procedimenti_in_iter_flgScadenzaTermineProcedimento_1_value")
  String procedimenti_in_iter_flgScadenzaTermineProcedimento_1_value(); 

  @Key("procedimenti_in_iter_annullamentoLogicoAsk_message")
  String procedimenti_in_iter_annullamentoLogicoAsk_message(); 

  @Key("procedimenti_in_iter_eliminazioneFisicaAsk_message")
  String procedimenti_in_iter_eliminazioneFisicaAsk_message(); 

  @Key("procedimenti_in_iter_iconaFolderButton_prompt")
  String procedimenti_in_iter_iconaFolderButton_prompt(); 

  @Key("procedimenti_in_iter_iconaFolderDetailButton_prompt")
  String procedimenti_in_iter_iconaFolderDetailButton_prompt(); 
  
  @Key("procedimenti_in_iter_iconaVisualizzaRispostaCedAutotuteleButton_prompt")
  String procedimenti_in_iter_iconaVisualizzaRispostaCedAutotuteleButton_prompt(); 

  //#############################################################
  //#                       LISTA ATTI                          #
  //#############################################################
  
  @Key("atti_list_avviso_Warning_NumerazioneConRegistrazione")
  String atti_list_avviso_Warning_NumerazioneConRegistrazione(); 

  @Key("atti_list_avviso_Bloccante_NumerazioneConRegistrazione")
  String atti_list_avviso_Bloccante_NumerazioneConRegistrazione();   

  //#############################################################
  //#                 PROCEDIMENTI PERSONALI                    #
  //#############################################################

  //#ProcedimentiPersonaliList
  @Key("procedimenti_personali_list_idProcedimentoField")
  String procedimenti_personali_list_idProcedimentoField(); 

  @Key("procedimenti_personali_list_nomeProcedimentoField")
  String procedimenti_personali_list_nomeProcedimentoField(); 

  @Key("procedimenti_personali_list_estremiProcedimentoField")
  String procedimenti_personali_list_estremiProcedimentoField(); 

  @Key("procedimenti_personali_list_tipoProcedimentoField")
  String procedimenti_personali_list_tipoProcedimentoField(); 

  @Key("procedimenti_personali_list_oggettoProcedimentoField")
  String procedimenti_personali_list_oggettoProcedimentoField(); 

  @Key("procedimenti_personali_list_dataAvvioProcedimentoField")
  String procedimenti_personali_list_dataAvvioProcedimentoField(); 

  @Key("procedimenti_personali_list_decorrenzaProcedimentoField")
  String procedimenti_personali_list_decorrenzaProcedimentoField(); 

  @Key("procedimenti_personali_list_processoPadreProcedimentoField")
  String procedimenti_personali_list_processoPadreProcedimentoField(); 

  @Key("procedimenti_personali_list_statoProcedimentoField")
  String procedimenti_personali_list_statoProcedimentoField(); 

  @Key("procedimenti_personali_list_documentoInizialeProcedimentoField")
  String procedimenti_personali_list_documentoInizialeProcedimentoField(); 

  @Key("procedimenti_personali_list_attoParereFinaleProcedimentoField")
  String procedimenti_personali_list_attoParereFinaleProcedimentoField(); 

  @Key("procedimenti_personali_list_inFaseProcedimentoField")
  String procedimenti_personali_list_inFaseProcedimentoField(); 

  @Key("procedimenti_personali_list_scadenzaTermineProcedimentoField")
  String procedimenti_personali_list_scadenzaTermineProcedimentoField(); 

  @Key("procedimenti_personali_list_flgScadenzaTermineProcedimentoField")
  String procedimenti_personali_list_flgScadenzaTermineProcedimentoField(); 

  @Key("procedimenti_personali_list_ultimaAttivitaField")
  String procedimenti_personali_list_ultimaAttivitaField(); 

  @Key("procedimenti_personali_list_ultimaAttivitaMessaggioField")
  String procedimenti_personali_list_ultimaAttivitaMessaggioField(); 

  @Key("procedimenti_personali_list_noteProcedimentoField")
  String procedimenti_personali_list_noteProcedimentoField(); 

  @Key("procedimenti_personali_list_nominativiProcedimentoField")
  String procedimenti_personali_list_nominativiProcedimentoField(); 

  @Key("procedimenti_personali_list_assegnatarioProcedimentoField")
  String procedimenti_personali_list_assegnatarioProcedimentoField(); 

  @Key("procedimenti_personali_list_prossimeAttivitaField")
  String procedimenti_personali_list_prossimeAttivitaField(); 

  @Key("procedimenti_personali_list_avviatoDaField")
  String procedimenti_personali_list_avviatoDaField(); 
  
  @Key("procedimenti_personali_list_dataPresentazioneField")
  String procedimenti_personali_list_dataPresentazioneField();
  
  @Key("procedimenti_personali_list_tipiTributoField")
  String procedimenti_personali_list_tipiTributoField();
  
  @Key("procedimenti_personali_list_anniTributoField")
  String procedimenti_personali_list_anniTributoField();
  
  @Key("procedimenti_personali_list_attiRiferimentoField")
  String procedimenti_personali_list_attiRiferimentoField();
  
  @Key("procedimenti_personali_list_flgPresenzaProcedimentiField")
  String procedimenti_personali_list_flgPresenzaProcedimentiField();
  
  @Key("procedimenti_personali_flgPresenzaProcedimenti_1_value")
  String procedimenti_personali_flgPresenzaProcedimenti_1_value();
  
  @Key("procedimenti_personali_list_ultimaAttivitaEsitoField")
  String procedimenti_personali_list_ultimaAttivitaEsitoField();
  
  @Key("procedimenti_personali_list_assegnatarioIstruttoriaField")
  String procedimenti_personali_list_assegnatarioIstruttoriaField();
  
  @Key("procedimenti_personali_list_assegnatarioIstruttoriaPreIstruttoriaField")
  String procedimenti_personali_list_assegnatarioIstruttoriaPreIstruttoriaField();
  
  @Key("procedimenti_personali_list_denominazioneRichiedenteField")
  String procedimenti_personali_list_denominazioneRichiedenteField();
  
  @Key("procedimenti_personali_list_codFiscalePivaRichiedenteField")
  String procedimenti_personali_list_codFiscalePivaRichiedenteField();
  
  //#ProcedimentiPersonaliDetail
  @Key("procedimenti_personali_detail_idProcedimentoItem")
  String procedimenti_personali_detail_idProcedimentoItem(); 

  @Key("procedimenti_personali_detail_nomeProcedimentoItem")
  String procedimenti_personali_detail_nomeProcedimentoItem(); 

  @Key("procedimenti_personali_detail_estremiProcedimentoItem")
  String procedimenti_personali_detail_estremiProcedimentoItem(); 

  @Key("procedimenti_personali_detail_tipoProcedimentoItem")
  String procedimenti_personali_detail_tipoProcedimentoItem(); 

  @Key("procedimenti_personali_detail_oggettoProcedimentoItem")
  String procedimenti_personali_detail_oggettoProcedimentoItem(); 

  @Key("procedimenti_personali_detail_dataAvvioProcedimentoItem")
  String procedimenti_personali_detail_dataAvvioProcedimentoItem(); 

  @Key("procedimenti_personali_detail_decorrenzaProcedimentoItem")
  String procedimenti_personali_detail_decorrenzaProcedimentoItem(); 

  @Key("procedimenti_personali_detail_processoPadreProcedimentoItem")
  String procedimenti_personali_detail_processoPadreProcedimentoItem(); 

  @Key("procedimenti_personali_detail_statoProcedimentoItem")
  String procedimenti_personali_detail_statoProcedimentoItem(); 

  @Key("procedimenti_personali_detail_documentoInizialeProcedimentoItem")
  String procedimenti_personali_detail_documentoInizialeProcedimentoItem(); 

  @Key("procedimenti_personali_detail_attoParereFinaleProcedimentoItem")
  String procedimenti_personali_detail_attoParereFinaleProcedimentoItem(); 

  @Key("procedimenti_personali_detail_inFaseProcedimentoItem")
  String procedimenti_personali_detail_inFaseProcedimentoItem(); 

  @Key("procedimenti_personali_detail_scadenzaTermineProcedimentoItem")
  String procedimenti_personali_detail_scadenzaTermineProcedimentoItem(); 

  @Key("procedimenti_personali_detail_flgScadenzaTermineProcedimentoItem")
  String procedimenti_personali_detail_flgScadenzaTermineProcedimentoItem(); 

  @Key("procedimenti_personali_detail_ultimaAttivitaItem")
  String procedimenti_personali_detail_ultimaAttivitaItem(); 

  @Key("procedimenti_personali_detail_ultimaAttivitaMessaggioItem")
  String procedimenti_personali_detail_ultimaAttivitaMessaggioItem(); 

  @Key("procedimenti_personali_detail_noteProcedimentoItem")
  String procedimenti_personali_detail_noteProcedimentoItem(); 

  @Key("procedimenti_personali_detail_nominativiProcedimentoItem")
  String procedimenti_personali_detail_nominativiProcedimentoItem(); 

  @Key("procedimenti_personali_detail_assegnatarioProcedimentoItem")
  String procedimenti_personali_detail_assegnatarioProcedimentoItem(); 

  @Key("procedimenti_personali_detail_prossimeAttivitaItem")
  String procedimenti_personali_detail_prossimeAttivitaItem(); 

  @Key("procedimenti_personali_detail_avviatoDaItem")
  String procedimenti_personali_detail_avviatoDaItem(); 

  @Key("procedimenti_personali_detail_new_title")
  String procedimenti_personali_detail_new_title(); 

  @Key("procedimenti_personali_detail_edit_title")
  String procedimenti_personali_detail_edit_title(String attribute0);

  @Key("procedimenti_personali_detail_view_title")
  String procedimenti_personali_detail_view_title(String attribute0);

  @Key("procedimenti_personali_flgScadenzaTermineProcedimento_1_value")
  String procedimenti_personali_flgScadenzaTermineProcedimento_1_value(); 

  @Key("procedimenti_personali_eliminazioneFisicaAsk_message")
  String procedimenti_personali_eliminazioneFisicaAsk_message(); 

  @Key("procedimenti_personali_annullamentoLogicoAsk_message")
  String procedimenti_personali_annullamentoLogicoAsk_message(); 

  @Key("procedimenti_personali_iconaFolderButton_prompt")
  String procedimenti_personali_iconaFolderButton_prompt(); 

  @Key("procedimenti_personali_iconaFolderDetailButton_prompt")
  String procedimenti_personali_iconaFolderDetailButton_prompt(); 
  
  @Key("procedimenti_personali_iconaVisualizzaRispostaCedAutotuteleButton_prompt")
  String procedimenti_personali_iconaVisualizzaRispostaCedAutotuteleButton_prompt(); 
  
  //#############################################################
  //#                       TSO PERSONALI                       #
  //#############################################################
  @Key("tso_personali_list_documentoInizialeProcedimento")
  String tso_personali_list_documentoInizialeProcedimento();
  
  @Key("tso_personali_list_dataPresentazione")
  String tso_personali_list_dataPresentazione();
  
  @Key("tso_personali_list_inFaseProcedimento")
  String tso_personali_list_inFaseProcedimento();
  
  @Key("tso_personali_list_ultimoTaskProcedimento")
  String tso_personali_list_ultimoTaskProcedimento();
  
  @Key("tso_personali_list_messaggioUltimoTaskProcedimento")
  String tso_personali_list_messaggioUltimoTaskProcedimento();
  
  @Key("tso_personali_list_prossimeAttivita")
  String tso_personali_list_prossimeAttivita();
  
  @Key("tso_personali_list_assegnatarioProcedimento")
  String tso_personali_list_assegnatarioProcedimento();
  
  @Key("tso_personali_list_esitoUltimaAttivita")
  String tso_personali_list_esitoUltimaAttivita();
  
  @Key("tso_personali_list_dataCertificato")
  String tso_personali_list_dataCertificato();
  
  @Key("tso_personali_list_cognomeMedico")
  String tso_personali_list_cognomeMedico();
  
  @Key("tso_personali_list_nomeMedico")
  String tso_personali_list_nomeMedico();
  
  @Key("tso_personali_list_contattiMedico")
  String tso_personali_list_contattiMedico();
  
  @Key("tso_personali_list_cognomePaziente")
  String tso_personali_list_cognomePaziente();
  
  @Key("tso_personali_list_nomePaziente")
  String tso_personali_list_nomePaziente();
  
  @Key("tso_personali_list_dataNascitaPaziente")
  String tso_personali_list_dataNascitaPaziente();
  
  @Key("tso_personali_list_minorePaziente")
  String tso_personali_list_minorePaziente();
  
  @Key("tso_personali_list_luogoNascitaPaziente")
  String tso_personali_list_luogoNascitaPaziente();
  
  //#############################################################
  //#                       TSO IN ITER                         #
  //#############################################################
  @Key("tso_in_iter_list_documentoInizialeProcedimento")
  String tso_in_iter_list_documentoInizialeProcedimento();
  
  @Key("tso_in_iter_list_dataPresentazione")
  String tso_in_iter_list_dataPresentazione();
  
  @Key("tso_in_iter_list_inFaseProcedimento")
  String tso_in_iter_list_inFaseProcedimento();
  
  @Key("tso_in_iter_list_ultimoTaskProcedimento")
  String tso_in_iter_list_ultimoTaskProcedimento();
  
  @Key("tso_in_iter_list_messaggioUltimoTaskProcedimento")
  String tso_in_iter_list_messaggioUltimoTaskProcedimento();
  
  @Key("tso_in_iter_list_prossimeAttivita")
  String tso_in_iter_list_prossimeAttivita();
  
  @Key("tso_in_iter_list_assegnatarioProcedimento")
  String tso_in_iter_list_assegnatarioProcedimento();
  
  @Key("tso_in_iter_list_esitoUltimaAttivita")
  String tso_in_iter_list_esitoUltimaAttivita();
  
  @Key("tso_in_iter_list_dataCertificato")
  String tso_in_iter_list_dataCertificato();
  
  @Key("tso_in_iter_list_cognomeMedico")
  String tso_in_iter_list_cognomeMedico();
  
  @Key("tso_in_iter_list_nomeMedico")
  String tso_in_iter_list_nomeMedico();
  
  @Key("tso_in_iter_list_contattiMedico")
  String tso_in_iter_list_contattiMedico();
  
  @Key("tso_in_iter_list_cognomePaziente")
  String tso_in_iter_list_cognomePaziente();
  
  @Key("tso_in_iter_list_nomePaziente")
  String tso_in_iter_list_nomePaziente();
  
  @Key("tso_in_iter_list_dataNascitaPaziente")
  String tso_in_iter_list_dataNascitaPaziente();
  
  @Key("tso_in_iter_list_minorePaziente")
  String tso_in_iter_list_minorePaziente();
  
  @Key("tso_in_iter_list_luogoNascitaPaziente")
  String tso_in_iter_list_luogoNascitaPaziente();
  
  //#############################################################
  //#                       ORGANI COLLEGIALI                   #
  //#############################################################
  
  @Key("convocazione_seduta_detail_downloadSchedaSintesiMultiButton_title")
  String convocazione_seduta_detail_downloadSchedaSintesiMultiButton_title();
  
  @Key("convocazione_seduta_detail_downloadSchedaSintesiMultiButton_richiesta_download")
  String convocazione_seduta_detail_downloadSchedaSintesiMultiButton_richiesta_download();
  
  @Key("convocazione_seduta_detail_downloadSchedaSintesiMultiButton_errore_download")
  String convocazione_seduta_detail_downloadSchedaSintesiMultiButton_errore_download();
  
  @Key("convocazione_seduta_detail_downloadFileAttiMultiButton_title")
  String convocazione_seduta_detail_downloadFileAttiMultiButton_title();
  
  @Key("convocazione_seduta_detail_trasmettiileAttiMultiButton_title")
  String convocazione_seduta_detail_trasmettiileAttiMultiButton_title();
  
  @Key("convocazione_seduta_detail_downloadFileAttiMultiButton_richiesta_download")
  String convocazione_seduta_detail_downloadFileAttiMultiButton_richiesta_download();
  
  @Key("convocazione_seduta_detail_downloadFileAttiMultiButton_errore_download")
  String convocazione_seduta_detail_downloadFileAttiMultiButton_errore_download();

  //#############################################################
  //#                       PROTOCOLLAZIONE                     #
  //#############################################################

  @Key("protocollazione_copertinaTimbroNrAllegato")
  String protocollazione_copertinaTimbroNrAllegato(); 


  //#Protocollazione_detail
  @Key("protocollazione_detail_acquisisciDaScannerMenuItem_prompt")
  String protocollazione_detail_acquisisciDaScannerMenuItem_prompt(); 

  @Key("protocollazione_detail_altraNumerazioneDataRegistrazioneItem_title")
  String protocollazione_detail_altraNumerazioneDataRegistrazioneItem_title(); 

  @Key("protocollazione_detail_altraNumerazioneDestinatarioItem_title")
  String protocollazione_detail_altraNumerazioneDestinatarioItem_title(); 

  @Key("protocollazione_detail_altraNumerazioneNumeroItem_title")
  String protocollazione_detail_altraNumerazioneNumeroItem_title(); 

  @Key("protocollazione_detail_altraNumerazioneRedattoreItem_title")
  String protocollazione_detail_altraNumerazioneRedattoreItem_title(); 

  @Key("protocollazione_detail_altraNumerazioneSiglaItem_title")
  String protocollazione_detail_altraNumerazioneSiglaItem_title(); 

  @Key("protocollazione_detail_altreVieForm_title")
  String protocollazione_detail_altreVieForm_title(); 
  
  @Key("protocollazione_detail_pubblicazioneForm_title")
  String protocollazione_detail_pubblicazioneForm_title(); 
  
  @Key("protocollazione_detail_ripubblicazioneForm_title")
  String protocollazione_detail_ripubblicazioneForm_title(); 
  
  @Key("protocollazione_detail_altriDatiForm_title")
  String protocollazione_detail_altriDatiForm_title(); 

  @Key("protocollazione_detail_altriDatiRicezioneButton_prompt1")
  String protocollazione_detail_altriDatiRicezioneButton_prompt1(String attribute0);

  @Key("protocollazione_detail_altriDatiRicezioneButton_prompt2")
  String protocollazione_detail_altriDatiRicezioneButton_prompt2(String attribute0, String attribute1, String attribute2);

  @Key("protocollazione_detail_altriDatiRicezioneButton_prompt")
  String protocollazione_detail_altriDatiRicezioneButton_prompt(); 

  @Key("protocollazione_detail_annoDocRiferimentoItem_title")
  String protocollazione_detail_annoDocRiferimentoItem_title(); 

  @Key("protocollazione_detail_annoFascicoloItem_title")
  String protocollazione_detail_annoFascicoloItem_title(); 

  @Key("protocollazione_detail_annoPassatoItem_title")
  String protocollazione_detail_annoPassatoItem_title(); 

  @Key("protocollazione_detail_annoProtRicevutoItem_title")
  String protocollazione_detail_annoProtRicevutoItem_title(); 

  @Key("protocollazione_detail_annotazioniForm_title")
  String protocollazione_detail_annotazioniForm_title(); 

  @Key("protocollazione_detail_aoomdgMittDestItem_noSearchOrEmptyMessage")
  String protocollazione_detail_aoomdgMittDestItem_noSearchOrEmptyMessage(); 

  @Key("protocollazione_detail_apponiSegnatureRegistrazioneMenuItem_prompt")
  String protocollazione_detail_apponiSegnatureRegistrazioneMenuItem_prompt(); 

  @Key("protocollazione_detail_assegnazioneForm_title")
  String protocollazione_detail_assegnazioneForm_title(); 

  @Key("protocollazione_detail_assegnazioneForm_title_readonly")
  String protocollazione_detail_assegnazioneForm_title_readonly(); 

  @Key("protocollazione_detail_attestatoConformitaMenuItem")
  String protocollazione_detail_attestatoConformitaMenuItem(); 

  @Key("protocollazione_detail_barcodeA4DatiTipologiaMenuItem")
  String protocollazione_detail_barcodeA4DatiTipologiaMenuItem(); 

  @Key("protocollazione_detail_barcodeA4MenuItem")
  String protocollazione_detail_barcodeA4MenuItem(); 

  @Key("protocollazione_detail_barcodeA4MultipliMenuItem")
  String protocollazione_detail_barcodeA4MultipliMenuItem(); 

  @Key("protocollazione_detail_barcodeA4NrDocumentoMenuItem")
  String protocollazione_detail_barcodeA4NrDocumentoMenuItem(); 

  @Key("protocollazione_detail_barcodeA4NrDocumentoPoisizioneMenuItem")
  String protocollazione_detail_barcodeA4NrDocumentoPoisizioneMenuItem(); 

  @Key("protocollazione_detail_barcodeEtichettaDatiTipologiaMenuItem")
  String protocollazione_detail_barcodeEtichettaDatiTipologiaMenuItem(); 

  @Key("protocollazione_detail_barcodeEtichettaMenuItem")
  String protocollazione_detail_barcodeEtichettaMenuItem(); 

  @Key("protocollazione_detail_barcodeEtichettaMultiploMenuItem")
  String protocollazione_detail_barcodeEtichettaMultiploMenuItem(); 

  @Key("protocollazione_detail_barcodeEtichettaNrDocMenuItem")
  String protocollazione_detail_barcodeEtichettaNrDocMenuItem(); 

  @Key("protocollazione_detail_barcodeEtichettaNrDocPosMenuItem")
  String protocollazione_detail_barcodeEtichettaNrDocPosMenuItem(); 

  @Key("protocollazione_detail_barcodeMultipliA4DatiTipologiaMenuItem")
  String protocollazione_detail_barcodeMultipliA4DatiTipologiaMenuItem(); 

  @Key("protocollazione_detail_barcodeMultipliA4NrDocPosMenuItem")
  String protocollazione_detail_barcodeMultipliA4NrDocPosMenuItem(); 

  @Key("protocollazione_detail_barcodeMultipliA4NrDocumentoMenuItem")
  String protocollazione_detail_barcodeMultipliA4NrDocumentoMenuItem(); 

  @Key("protocollazione_detail_barcodeMultipliEtichettaDatiTipologiaMenuItem")
  String protocollazione_detail_barcodeMultipliEtichettaDatiTipologiaMenuItem(); 

  @Key("protocollazione_detail_barcodeMultipliEtichettaNrDocMenuItem")
  String protocollazione_detail_barcodeMultipliEtichettaNrDocMenuItem(); 

  @Key("protocollazione_detail_barcodeMultipliEtichettaNrDocPosMenuItem")
  String protocollazione_detail_barcodeMultipliEtichettaNrDocPosMenuItem(); 

  @Key("protocollazione_detail_capofilaItem_title")
  String protocollazione_detail_capofilaItem_title(); 

  @Key("protocollazione_detail_cercaInOrganigrammaButton_prompt")
  String protocollazione_detail_cercaInOrganigrammaButton_prompt(); 

  @Key("protocollazione_detail_cercaInRubricaButton_prompt")
  String protocollazione_detail_cercaInRubricaButton_prompt(); 

  @Key("protocollazione_detail_classificazioneFascicolazioneForm_title")
  String protocollazione_detail_classificazioneFascicolazioneForm_title(); 

  @Key("protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage")
  String protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage(); 

  @Key("protocollazione_detail_classificazioneItem_title")
  String protocollazione_detail_classificazioneItem_title(); 

  @Key("protocollazione_detail_codFiscaleItem_title")
  String protocollazione_detail_codFiscaleItem_title(); 
  
  @Key("protocollazione_detail_codFiscalePIVAItem_title")
  String protocollazione_detail_codFiscalePIVAItem_title();

  @Key("protocollazione_detail_codRapidoItem_title")
  String protocollazione_detail_codRapidoItem_title(); 

  @Key("protocollazione_detail_codiceACSItem_title")
  String protocollazione_detail_codiceACSItem_title(); 

  @Key("protocollazione_detail_codiceItem_title")
  String protocollazione_detail_codiceItem_title(); 

  @Key("protocollazione_detail_codrapidosoggetto_esitoValidazione_KO")
  String protocollazione_detail_codrapidosoggetto_esitoValidazione_KO(String attribute0, String attribute1);

  @Key("protocollazione_detail_cognomeItem_title")
  String protocollazione_detail_cognomeItem_title(); 

  @Key("protocollazione_detail_collegaDocumentiFileImportati_title")
  String protocollazione_detail_collegaDocumentiFileImportati_title(); 

  @Key("protocollazione_detail_collocazioneFisicaForm_title")
  String protocollazione_detail_collocazioneFisicaForm_title(); 

  @Key("protocollazione_detail_collocazioneFisicaItem_title")
  String protocollazione_detail_collocazioneFisicaItem_title(); 

  @Key("protocollazione_detail_conDatiAnnullatiButton_prompt")
  String protocollazione_detail_conDatiAnnullatiButton_prompt(); 

  @Key("protocollazione_detail_condivisioneForm_title")
  String protocollazione_detail_condivisioneForm_title(); 

  @Key("protocollazione_detail_condivisioneForm_title_readonly")
  String protocollazione_detail_condivisioneForm_title_readonly(); 

  @Key("protocollazione_detail_contenutiForm_title")
  String protocollazione_detail_contenutiForm_title(); 

  @Key("protocollazione_detail_contribuenteForm_title")
  String protocollazione_detail_contribuenteForm_title(); 

  @Key("protocollazione_detail_copertinaConEtichetta")
  String protocollazione_detail_copertinaConEtichetta(); 

  @Key("protocollazione_detail_copertinaConEtichettaMultipla")
  String protocollazione_detail_copertinaConEtichettaMultipla(); 

  @Key("protocollazione_detail_copertinaConSegnatura")
  String protocollazione_detail_copertinaConSegnatura(); 

  @Key("protocollazione_detail_copertinaConTimbro")
  String protocollazione_detail_copertinaConTimbro(); 

  @Key("protocollazione_detail_copertinaConTimbroMultipla")
  String protocollazione_detail_copertinaConTimbroMultipla(); 

  @Key("protocollazione_detail_dataAperturaFascicoloItem_title")
  String protocollazione_detail_dataAperturaFascicoloItem_title(); 

  @Key("protocollazione_detail_dataAttoItem_title")
  String protocollazione_detail_dataAttoItem_title(); 

  @Key("protocollazione_detail_dataAvvio_title")
  String protocollazione_detail_dataAvvio_title(); 

  @Key("protocollazione_detail_dataChiusuraFascicoloItem_title")
  String protocollazione_detail_dataChiusuraFascicoloItem_title(); 

  @Key("protocollazione_detail_dataDocumentoItem_title")
  String protocollazione_detail_dataDocumentoItem_title(); 

  @Key("protocollazione_detail_dataEOraArrivoItem_title")
  String protocollazione_detail_dataEOraArrivoItem_title(); 

  @Key("protocollazione_detail_dataEOraRicezioneItem_title")
  String protocollazione_detail_dataEOraRicezioneItem_title(); 

  @Key("protocollazione_detail_dataNotificaDestinatarioItem_title")
  String protocollazione_detail_dataNotificaDestinatarioItem_title(); 

  @Key("protocollazione_detail_dataProtRicevutoItem_title")
  String protocollazione_detail_dataProtRicevutoItem_title(); 

  @Key("protocollazione_detail_dataProtocolloItem_title")
  String protocollazione_detail_dataProtocolloItem_title(); 

  @Key("protocollazione_detail_dataRaccomandataDestinatarioItem_title")
  String protocollazione_detail_dataRaccomandataDestinatarioItem_title(); 

  @Key("protocollazione_detail_dataRaccomandataCanaleItem_title")
  String protocollazione_detail_dataRaccomandataCanaleItem_title(); 

  @Key("protocollazione_detail_dataRaccomandataItem_title")
  String protocollazione_detail_dataRaccomandataItem_title(); 

  @Key("protocollazione_detail_dataRegEmergenzaItem_title")
  String protocollazione_detail_dataRegEmergenzaItem_title(); 

  @Key("protocollazione_detail_dataRiservatezzaItem_title")
  String protocollazione_detail_dataRiservatezzaItem_title(); 

  @Key("protocollazione_detail_datiRicezioneForm_title")
  String protocollazione_detail_datiRicezioneForm_title(); 

  @Key("protocollazione_detail_denominazioneEstesaItem_title")
  String protocollazione_detail_denominazioneEstesaItem_title(); 

  @Key("protocollazione_detail_denominazioneItem_title")
  String protocollazione_detail_denominazioneItem_title(); 

  @Key("protocollazione_detail_desUOAttoItem_title")
  String protocollazione_detail_desUOAttoItem_title(); 

  @Key("protocollazione_detail_desUOProtocolloItem_title")
  String protocollazione_detail_desUOProtocolloItem_title(); 

  @Key("protocollazione_detail_desUserAperturaFascicoloItem_title")
  String protocollazione_detail_desUserAperturaFascicoloItem_title(); 

  @Key("protocollazione_detail_desUserAttoItem_title")
  String protocollazione_detail_desUserAttoItem_title(); 

  @Key("protocollazione_detail_desUserChiusuraFascicoloItem_title")
  String protocollazione_detail_desUserChiusuraFascicoloItem_title(); 
  
  @Key("protocollazione_detail_societaItem_title")
  String protocollazione_detail_societaItem_title();

  @Key("protocollazione_detail_desUserProtocolloItem_title")
  String protocollazione_detail_desUserProtocolloItem_title(); 

  @Key("protocollazione_detail_descrizioneEstesaItem_title")
  String protocollazione_detail_descrizioneEstesaItem_title(); 

  @Key("protocollazione_detail_descrizioneItem_title")
  String protocollazione_detail_descrizioneItem_title(); 

  @Key("protocollazione_detail_destinatariForm_title")
  String protocollazione_detail_destinatariForm_title(); 

  @Key("protocollazione_detail_dettaglioCertificazione_prompt")
  String protocollazione_detail_dettaglioCertificazione_prompt(); 

  @Key("protocollazione_detail_docCollegatoForm_title")
  String protocollazione_detail_docCollegatoForm_title(); 

  @Key("protocollazione_detail_docRiferimentoItem_title")
  String protocollazione_detail_docRiferimentoItem_title(); 

  @Key("protocollazione_detail_downloadFirmatoMenuItem_prompt")
  String protocollazione_detail_downloadFirmatoMenuItem_prompt(); 

  @Key("protocollazione_detail_downloadMenuItem_prompt")
  String protocollazione_detail_downloadMenuItem_prompt(); 

  @Key("protocollazione_detail_downloadSbustatoMenuItem_prompt")
  String protocollazione_detail_downloadSbustatoMenuItem_prompt(); 

  @Key("protocollazione_detail_elencoSegnatureInError_error")
  String protocollazione_detail_elencoSegnatureInError_error(); 

  @Key("protocollazione_detail_eliminaMenuItem_prompt")
  String protocollazione_detail_eliminaMenuItem_prompt(); 

  @Key("protocollazione_detail_emailContribuenteItem_title")
  String protocollazione_detail_emailContribuenteItem_title(); 
  
  @Key("protocollazione_detail_telContribuenteItem_title")
  String protocollazione_detail_telContribuenteItem_title();   

  @Key("protocollazione_detail_esibentiForm_title")
  String protocollazione_detail_esibentiForm_title(); 

  @Key("protocollazione_detail_esitoValidazione_KO_value")
  String protocollazione_detail_esitoValidazione_KO_value(); 

  @Key("protocollazione_detail_fascicolazioneForm_title")
  String protocollazione_detail_fascicolazioneForm_title(); 

  @Key("protocollazione_detail_fascicolazioneItem_title")
  String protocollazione_detail_fascicolazioneItem_title(); 

  @Key("protocollazione_detail_opzioniInvioAssegnazioneButton_prompt")
  String protocollazione_detail_opzioniInvioAssegnazioneButton_prompt(); 

  @Key("protocollazione_detail_feedbackSuPropostaButton_prompt")
  String protocollazione_detail_feedbackSuPropostaButton_prompt(); 

  @Key("protocollazione_detail_fileAllegatiForm_title")
  String protocollazione_detail_fileAllegatiForm_title(); 

  @Key("protocollazione_detail_fileFirmatoDigButton_prompt")
  String protocollazione_detail_fileFirmatoDigButton_prompt(); 

  @Key("protocollazione_detail_filePrimarioForm_title")
  String protocollazione_detail_filePrimarioForm_title(); 

  @Key("protocollazione_detail_firmaMenuItem_prompt")
  String protocollazione_detail_firmaMenuItem_prompt(); 

  @Key("protocollazione_detail_firmaNonValidaButton_prompt")
  String protocollazione_detail_firmaNonValidaButton_prompt(); 
  
  @Key("protocollazione_detail_firmaNonValidaConDataButton_prompt")
  String protocollazione_detail_firmaNonValidaConDataButton_prompt(String attribute0);
  
  @Key("protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt")
  String protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(String attribute0);
  
  @Key("protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt")
  String protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt();

  @Key("protocollazione_detail_flagSoggettiGruppo_E_value")
  String protocollazione_detail_flagSoggettiGruppo_E_value(); 

  @Key("protocollazione_detail_flagSoggettiGruppo_I_value")
  String protocollazione_detail_flagSoggettiGruppo_I_value(); 

  @Key("protocollazione_detail_flagSoggettiGruppo_M_value")
  String protocollazione_detail_flagSoggettiGruppo_M_value(); 

  @Key("protocollazione_detail_flagSoggettiGruppo_O_value")
  String protocollazione_detail_flagSoggettiGruppo_O_value(); 

  @Key("protocollazione_detail_flgAssegnaItem_title")
  String protocollazione_detail_flgAssegnaItem_title(); 
  
  @Key("protocollazione_detail_flgAssegnataItem_title")
  String protocollazione_detail_flgAssegnataItem_title();

  @Key("protocollazione_detail_flgFileDaFirmare_title")
  String protocollazione_detail_flgFileDaFirmare_title(); 

  @Key("protocollazione_detail_flgPCItem_title")
  String protocollazione_detail_flgPCItem_title(); 

  @Key("protocollazione_detail_flgCCItem_title")
  String protocollazione_detail_flgCCItem_title(); 

  @Key("protocollazione_detail_folderCustomForm_title")
  String protocollazione_detail_folderCustomForm_title(); 

  @Key("protocollazione_detail_generaDaModelloButton_prompt")
  String protocollazione_detail_generaDaModelloButton_prompt(); 

  @Key("protocollazione_detail_generazioneMenuItem_prompt")
  String protocollazione_detail_generazioneMenuItem_prompt(); 

  @Key("protocollazione_detail_importaFileDaAltriDocumenti_title")
  String protocollazione_detail_importaFileDaAltriDocumenti_title();
  
  @Key("protocollazione_detail_importaDocumentiDaAltriDocumenti_title")
  String protocollazione_detail_importaDocumentiDaAltriDocumenti_title(); 

  @Key("protocollazione_detail_importazioneFileWaitPopup_title")
  String protocollazione_detail_importazioneFileWaitPopup_title(); 

  @Key("protocollazione_detail_importazioneFile_error")
  String protocollazione_detail_importazioneFile_error(); 

  @Key("protocollazione_detail_indiceItem_title")
  String protocollazione_detail_indiceItem_title(); 

  @Key("protocollazione_detail_indirizzoDestinatarioItem_title")
  String protocollazione_detail_indirizzoDestinatarioItem_title(); 

  @Key("protocollazione_detail_interessatiForm_title")
  String protocollazione_detail_interessatiForm_title(); 
  
  @Key("protocollazione_detail_delegatoForm_title")
  String protocollazione_detail_delegatoForm_title(); 
 
  @Key("protocollazione_detail_firmatariForm_title")
  String protocollazione_detail_firmatariForm_title(); 
 
  @Key("protocollazione_detail_livelloRiservatezzaItem_title")
  String protocollazione_detail_livelloRiservatezzaItem_title(); 

  @Key("protocollazione_detail_livelloUO1ListGridField_title")
  String protocollazione_detail_livelloUO1ListGridField_title(); 

  @Key("protocollazione_detail_livelloUO2ListGridField_title")
  String protocollazione_detail_livelloUO2ListGridField_title(); 

  @Key("protocollazione_detail_livelloUO3ListGridField_title")
  String protocollazione_detail_livelloUO3ListGridField_title(); 

  @Key("protocollazione_detail_livelloUO4ListGridField_title")
  String protocollazione_detail_livelloUO4ListGridField_title(); 

  @Key("protocollazione_detail_livelloUO5ListGridField_title")
  String protocollazione_detail_livelloUO5ListGridField_title(); 

  @Key("protocollazione_detail_lookupArchivioButton_prompt")
  String protocollazione_detail_lookupArchivioButton_prompt(); 

  @Key("protocollazione_detail_lookupOrganigrammaButton_prompt")
  String protocollazione_detail_lookupOrganigrammaButton_prompt(); 

  @Key("protocollazione_detail_lookupRubricaButton_prompt")
  String protocollazione_detail_lookupRubricaButton_prompt(); 

  @Key("protocollazione_detail_lookupTemplateOggettoButton_prompt")
  String protocollazione_detail_lookupTemplateOggettoButton_prompt(); 

  @Key("protocollazione_detail_lookupTitolarioButton_prompt")
  String protocollazione_detail_lookupTitolarioButton_prompt(); 

  @Key("protocollazione_detail_lookupTitolarioCogitoButton_prompt")
  String protocollazione_detail_lookupTitolarioCogitoButton_prompt(); 
  
  @Key("protocollazione_detail_stato_detail")
  String protocollazione_detail_stato_detail();

  
  @Key("protocollazione_detail_lookupTopograficoButton_prompt")
  String protocollazione_detail_lookupTopograficoButton_prompt(); 

  @Key("protocollazione_detail_messageDocumentoNonTipizzato")
  String protocollazione_detail_messageDocumentoNonTipizzato(); 

  @Key("protocollazione_detail_mezzoTrasmissioneItem_title")
  String protocollazione_detail_mezzoTrasmissioneItem_title(); 

  @Key("protocollazione_detail_mittentiForm_title")
  String protocollazione_detail_mittentiForm_title(); 

  @Key("protocollazione_detail_modelliSelectItem_title")
  String protocollazione_detail_modelliSelectItem_title(); 

  @Key("protocollazione_detail_multilookupArchivioButton_prompt")
  String protocollazione_detail_multilookupArchivioButton_prompt(); 

  @Key("protocollazione_detail_multilookupOrganigrammaButton_prompt")
  String protocollazione_detail_multilookupOrganigrammaButton_prompt(); 

  @Key("protocollazione_detail_multilookupRubricaButton_prompt")
  String protocollazione_detail_multilookupRubricaButton_prompt(); 

  @Key("protocollazione_detail_multilookupTitolarioButton_prompt")
  String protocollazione_detail_multilookupTitolarioButton_prompt(); 

  @Key("protocollazione_detail_nomeFascicoloItem_title")
  String protocollazione_detail_nomeFascicoloItem_title(); 

  @Key("protocollazione_detail_nomeFileAllegatoItem_title")
  String protocollazione_detail_nomeFileAllegatoItem_title(); 
  
  @Key("protocollazione_detail_nomeFileOmissisAllegatoItem_title")
  String protocollazione_detail_nomeFileOmissisAllegatoItem_title(); 
  
  @Key("protocollazione_detail_nomeFilePrimarioItem_title")
  String protocollazione_detail_nomeFilePrimarioItem_title(); 

  @Key("protocollazione_detail_nomeItem_title")
  String protocollazione_detail_nomeItem_title(); 

  @Key("protocollazione_detail_noteItem_title")
  String protocollazione_detail_noteItem_title(); 

  @Key("protocollazione_detail_nroAttoItem_title")
  String protocollazione_detail_nroAttoItem_title(); 

  @Key("protocollazione_detail_nroBozzaItem_title")
  String protocollazione_detail_nroBozzaItem_title(); 

  @Key("protocollazione_detail_nroDocRiferimentoItem_title")
  String protocollazione_detail_nroDocRiferimentoItem_title(); 

  @Key("protocollazione_detail_nroFascicoloItem_title")
  String protocollazione_detail_nroFascicoloItem_title(); 

  @Key("protocollazione_detail_nroInsertoItem_title")
  String protocollazione_detail_nroInsertoItem_title(); 

  @Key("protocollazione_detail_nroIstanzaItem_title")
  String protocollazione_detail_nroIstanzaItem_title(); 

  @Key("protocollazione_detail_nroNotificaDestinatarioItem_title")
  String protocollazione_detail_nroNotificaDestinatarioItem_title(); 

  @Key("protocollazione_detail_nroProtRicevutoItem_title")
  String protocollazione_detail_nroProtRicevutoItem_title(); 

  @Key("protocollazione_detail_nroProtocolloItem_title")
  String protocollazione_detail_nroProtocolloItem_title(); 

  @Key("protocollazione_detail_nroRaccomandataDestinatarioItem_title")
  String protocollazione_detail_nroRaccomandataDestinatarioItem_title(); 

  @Key("protocollazione_detail_nroRaccomandataCanaleItem_title")
  String protocollazione_detail_nroRaccomandataCanaleItem_title(); 

  @Key("protocollazione_detail_nroRaccomandataItem_title")
  String protocollazione_detail_nroRaccomandataItem_title(); 

  @Key("protocollazione_detail_nroRegEmergenzaItem_title")
  String protocollazione_detail_nroRegEmergenzaItem_title(); 

  @Key("protocollazione_detail_nroSottoFascicoloItem_title")
  String protocollazione_detail_nroSottoFascicoloItem_title(); 

  @Key("protocollazione_detail_nuovaProtButton_prompt")
  String protocollazione_detail_nuovaProtButton_prompt(); 

  @Key("protocollazione_detail_nuovaProtComeCopiaButton_prompt")
  String protocollazione_detail_nuovaProtComeCopiaButton_prompt(); 

  @Key("protocollazione_detail_nuovoFascicoloButton_prompt")
  String protocollazione_detail_nuovoFascicoloButton_prompt(); 

  @Key("protocollazione_detail_nuovoInsertoButton_prompt")
  String protocollazione_detail_nuovoInsertoButton_prompt(); 

  @Key("protocollazione_detail_nuovoSottoFascicoloButton_prompt")
  String protocollazione_detail_nuovoSottoFascicoloButton_prompt(); 

  @Key("protocollazione_detail_oggettoItem_title")
  String protocollazione_detail_oggettoItem_title(); 

  @Key("protocollazione_detail_okButton_title")
  String protocollazione_detail_okButton_title(); 

  @Key("protocollazione_detail_operazioniEffettuateButton_prompt")
  String protocollazione_detail_operazioniEffettuateButton_prompt(); 

  @Key("protocollazione_detail_previewButton_prompt")
  String protocollazione_detail_previewButton_prompt(); 

  @Key("protocollazione_detail_previewEditButton_prompt")
  String protocollazione_detail_previewEditButton_prompt(); 

  @Key("protocollazione_detail_prioritaRiservatezzaItem_title")
  String protocollazione_detail_prioritaRiservatezzaItem_title(); 

  @Key("protocollazione_detail_revoca_atto_button")
  String protocollazione_detail_revoca_atto_button(); 

  @Key("protocollazione_detail_protocollazione_entrata_button")
  String protocollazione_detail_protocollazione_entrata_button(); 

  @Key("protocollazione_detail_protocollazione_uscita_button")
  String protocollazione_detail_protocollazione_uscita_button(); 

  @Key("protocollazione_detail_protocollazione_interna_button")
  String protocollazione_detail_protocollazione_interna_button(); 

  @Key("protocollazione_detail_protocolloItem_title")
  String protocollazione_detail_protocolloItem_title(); 

  @Key("protocollazione_detail_protocolloRicevutoItem_title")
  String protocollazione_detail_protocolloRicevutoItem_title(); 

  @Key("protocollazione_detail_raccomandataItem_title")
  String protocollazione_detail_raccomandataItem_title(); 

  @Key("protocollazione_detail_regEmergenzaForm_title")
  String protocollazione_detail_regEmergenzaForm_title(); 

  @Key("protocollazione_detail_regEmergenzaItem_title")
  String protocollazione_detail_regEmergenzaItem_title(); 

  @Key("protocollazione_detail_registraButton_prompt")
  String protocollazione_detail_registraButton_prompt(); 

  @Key("protocollazione_detail_registrazioneEmergenzaButton_prompt2")
  String protocollazione_detail_registrazioneEmergenzaButton_prompt2(String attribute0, String attribute1, String attribute2);

  @Key("protocollazione_detail_registrazioneEmergenzaButton_prompt")
  String protocollazione_detail_registrazioneEmergenzaButton_prompt(); 

  @Key("protocollazione_detail_registrazioneForm_title")
  String protocollazione_detail_registrazioneForm_title(); 

  @Key("protocollazione_detail_registrofatture_title")
  String protocollazione_detail_registrofatture_title(); 

  @Key("protocollazione_detail_repertorioItem_title")
  String protocollazione_detail_repertorioItem_title(); 

  @Key("protocollazione_detail_repertorio_title")
  String protocollazione_detail_repertorio_title(); 

  @Key("protocollazione_detail_ricezioneForm_title")
  String protocollazione_detail_ricezioneForm_title(); 
  
  @Key("protocollazione_detail_periziaForm_title")
  String protocollazione_detail_periziaForm_title(); 
  
  @Key("protocollazione_detail_variazioneDatiRegistrazione_title")
  String protocollazione_detail_variazioneDatiRegistrazione_title(); 

  @Key("protocollazione_detail_rifOrigineProtRicevutoItem_title")
  String protocollazione_detail_rifOrigineProtRicevutoItem_title(); 

  @Key("protocollazione_detail_rifRegEmergenzaItem_title")
  String protocollazione_detail_rifRegEmergenzaItem_title(); 

  @Key("protocollazione_detail_riservatezzaItem_title")
  String protocollazione_detail_riservatezzaItem_title(); 

  @Key("protocollazione_detail_salvaComeModelloButton_prompt")
  String protocollazione_detail_salvaComeModelloButton_prompt(); 

  @Key("protocollazione_detail_salvaComeTemplateOggettoButton_prompt")
  String protocollazione_detail_salvaComeTemplateOggettoButton_prompt(); 

  @Key("protocollazione_detail_salvaInArchivioButton_prompt")
  String protocollazione_detail_salvaInArchivioButton_prompt(); 

  @Key("protocollazione_detail_salvaInRubricaButton_prompt")
  String protocollazione_detail_salvaInRubricaButton_prompt(); 

  @Key("protocollazione_detail_visualizzaInRubricaButton_prompt")
  String protocollazione_detail_visualizzaInRubricaButton_prompt();

  @Key("protocollazione_detail_salvaInTopograficoButton_prompt")
  String protocollazione_detail_salvaInTopograficoButton_prompt(); 

  @Key("protocollazione_detail_salvaInTopografico_esitoValidazione_KO")
  String protocollazione_detail_salvaInTopografico_esitoValidazione_KO(); 

  @Key("protocollazione_detail_siglaDocRiferimentoItem_title")
  String protocollazione_detail_siglaDocRiferimentoItem_title(); 

  @Key("protocollazione_detail_stampaCopertinaButton_prompt")
  String protocollazione_detail_stampaCopertinaButton_prompt(); 
  
  @Key("protocollazione_detail_stampaRicevutaButton_prompt")
  String protocollazione_detail_stampaRicevutaButton_prompt(); 

  @Key("protocollazione_detail_stampaEtichettaButton_prompt")
  String protocollazione_detail_stampaEtichettaButton_prompt(); 

  @Key("protocollazione_detail_stampaFileCertificazione_prompt")
  String protocollazione_detail_stampaFileCertificazione_prompt(); 

  @Key("protocollazione_detail_tabAltreVie_prompt")
  String protocollazione_detail_tabAltreVie_prompt(); 

  @Key("protocollazione_detail_tabAltreVie_title")
  String protocollazione_detail_tabAltreVie_title();
  
  @Key("protocollazione_detail_tabPubblicazione_prompt")
  String protocollazione_detail_tabPubblicazione_prompt();
  
  @Key("protocollazione_detail_tabPubblicazione_title")
  String protocollazione_detail_tabPubblicazione_title(); 

  @Key("protocollazione_detail_stampe")
  String protocollazione_detail_stampe(); 
  
  @Key("protocollazione_detail_richAccCivicoTitle")
  String protocollazione_detail_richAccCivicoTitle();
  
  @Key("protocollazione_detail_flgRichAccCivSemplice_title")
  String protocollazione_detail_flgRichAccCivSemplice_title();
  
  @Key("protocollazione_detail_flgRichAccCivGeneralizzato_title")
  String protocollazione_detail_flgRichAccCivGeneralizzato_title();
  
  @Key("protocollazione_detail_regAccessoCivicoButton_title")
  String protocollazione_detail_regAccessoCivicoButton_title();
  
  @Key("protocollazione_detail_presaIncarico_title")
  String protocollazione_detail_presaIncarico_title();
  
  @Key("protocollazione_detail_restituisci_title")
  String protocollazione_detail_restituisci_title();
  
  @Key("protocollazione_detail_segnaComeVisionato_title")
  String protocollazione_detail_segnaComeVisionato_title();
  
  @Key("protocollazione_detail_segnaComeArchiviato_title")
  String protocollazione_detail_segnaComeArchiviato_title();
  
  @Key("protocollazione_detail_fascicolazione_title")
  String protocollazione_detail_fascicolazione_title();
  
  @Key("protocollazione_detail_tabAssegnazioneEClassificazione_prompt")
  String protocollazione_detail_tabAssegnazioneEClassificazione_prompt(); 

  @Key("protocollazione_detail_tabAssegnazioneEClassificazione_title")
  String protocollazione_detail_tabAssegnazioneEClassificazione_title(); 

  @Key("protocollazione_detail_tabAssegnazioneEFascicolazione_prompt")
  String protocollazione_detail_tabAssegnazioneEFascicolazione_prompt(); 

  @Key("protocollazione_detail_tabAssegnazioneEFascicolazione_title")
  String protocollazione_detail_tabAssegnazioneEFascicolazione_title(); 

  @Key("protocollazione_detail_tabDatiDocumento_prompt")
  String protocollazione_detail_tabDatiDocumento_prompt(); 

  @Key("protocollazione_detail_tabDatiDocumento_title")
  String protocollazione_detail_tabDatiDocumento_title(); 

  @Key("protocollazione_detail_tabEsibentiEInteressati_prompt")
  String protocollazione_detail_tabEsibentiEInteressati_prompt(); 

  @Key("protocollazione_detail_tabEsibentiEInteressati_title")
  String protocollazione_detail_tabEsibentiEInteressati_title(); 

  @Key("protocollazione_detail_tabInteressati_prompt")
  String protocollazione_detail_tabInteressati_prompt(); 

  @Key("protocollazione_detail_tabInteressati_title")
  String protocollazione_detail_tabInteressati_title(); 
  
  @Key("protocollazione_detail_tabDelegatoEFirmatari_prompt")
  String protocollazione_detail_tabDelegatoEFirmatari_prompt(); 
  	  
  @Key("protocollazione_detail_tabDelegatoEFirmatari_title")
  String protocollazione_detail_tabDelegatoEFirmatari_title();  

  @Key("protocollazione_detail_timbroConformitaOriginaleMenuItem")
  String protocollazione_detail_timbroConformitaOriginaleMenuItem();
  
  @Key("protocollazione_detail_timbroCopiaConformeMenuItem")
  String protocollazione_detail_timbroCopiaConformeMenuItem();
  
  @Key("protocollazione_detail_timbroCopiaConformeMenuItem_stampa")
  String protocollazione_detail_timbroCopiaConformeMenuItem_stampa();
  
  @Key("protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale")
  String protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale();
  
  @Key("protocollazione_detail_timbraDatiSegnaturaMenuItem")
  String protocollazione_detail_timbraDatiSegnaturaMenuItem(); 

  @Key("protocollazione_detail_timbraDatiTipologiaMenuItem")
  String protocollazione_detail_timbraDatiTipologiaMenuItem(); 

  @Key("protocollazione_detail_timbraMenuItem")
  String protocollazione_detail_timbraMenuItem(); 

  @Key("protocollazione_detail_timbraMenuItem_prompt")
  String protocollazione_detail_timbraMenuItem_prompt(); 

  @Key("protocollazione_detail_tipoDocumentoItem_title")
  String protocollazione_detail_tipoDocumentoItem_title(); 

  @Key("protocollazione_detail_tipoItem_title")
  String protocollazione_detail_tipoItem_title(); 

  @Key("protocollazione_detail_uoItem_title")
  String protocollazione_detail_uoItem_title(); 

  @Key("protocollazione_detail_verificaRegistrazioneButton_prompt")
  String protocollazione_detail_verificaRegistrazioneButton_prompt(); 

  @Key("protocollazione_detail_visualizzaDatiFirmeMenuItem_prompt")
  String protocollazione_detail_visualizzaDatiFirmeMenuItem_prompt(); 

  @Key("protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage")
  String protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage();

  @Key("protocollazione_detail_dataRicezioneItem_title")
  String protocollazione_detail_dataRicezioneItem_title();

  @Key("protocollazione_editWindow_title")
  String protocollazione_editWindow_title(); 

  @Key("protocollazione_opzioniInvioAssegnazionePopup_flgMancataPresaInCaricoItem_title")
  String protocollazione_opzioniInvioAssegnazionePopup_flgMancataPresaInCaricoItem_title(); 

  @Key("protocollazione_opzioniInvioAssegnazionePopup_flgPresaInCaricoItem_title")
  String protocollazione_opzioniInvioAssegnazionePopup_flgPresaInCaricoItem_title(); 

  @Key("protocollazione_opzioniInvioAssegnazionePopup_giorniTrascorsiItem_title")
  String protocollazione_opzioniInvioAssegnazionePopup_giorniTrascorsiItem_title(); 

  @Key("protocollazione_opzioniInvioAssegnazionePopup_title")
  String protocollazione_opzioniInvioAssegnazionePopup_title(); 
  
  @Key("protocollazione_opzioniInvioAssegnazionePopup_verifycheckLD")
  String protocollazione_opzioniInvioAssegnazionePopup_verifycheckLD();

  @Key("protocollazione_feedbackSuPropostaAssegnazionePopup_flgConfermaItem_title")
  String protocollazione_feedbackSuPropostaAssegnazionePopup_flgConfermaItem_title(); 

  @Key("protocollazione_feedbackSuPropostaAssegnazionePopup_flgMancataAssegnazioneDefItem_title")
  String protocollazione_feedbackSuPropostaAssegnazionePopup_flgMancataAssegnazioneDefItem_title(); 

  @Key("protocollazione_feedbackSuPropostaAssegnazionePopup_flgVariazioneItem_title")
  String protocollazione_feedbackSuPropostaAssegnazionePopup_flgVariazioneItem_title(); 

  @Key("protocollazione_feedbackSuPropostaAssegnazionePopup_giorniTrascorsiItem_title")
  String protocollazione_feedbackSuPropostaAssegnazionePopup_giorniTrascorsiItem_title(); 

  @Key("protocollazione_feedbackSuPropostaAssegnazionePopup_title")
  String protocollazione_feedbackSuPropostaAssegnazionePopup_title(); 

  @Key("protocollazione_firmaWindow_title")
  String protocollazione_firmaWindow_title(); 

  @Key("protocollazione_flg_no_pubbl")
  String protocollazione_flg_no_pubbl(); 
  
  @Key("protocollazione_flg_pubblica_separato")
  String protocollazione_flg_pubblica_separato();
  
  @Key("protocollazione_flg_dati_sensibili")
  String protocollazione_flg_dati_sensibili(); 
  
  @Key("protocollazione_flgTimbraFilePostReg")
  String protocollazione_flgTimbraFilePostReg();

  @Key("protocollazione_flg_parte_dispositivo")
  String protocollazione_flg_parte_dispositivo(); 

  @Key("protocollazione_message_modifica_registrazione_esito_OK_value")
  String protocollazione_message_modifica_registrazione_esito_OK_value(String attribute0, String attribute1);

  @Key("protocollazione_message_registrazione_generale_esito_OK_value")
  String protocollazione_message_registrazione_generale_esito_OK_value(String attribute0, String attribute1);

  @Key("protocollazione_message_registrazione_interna_esito_OK_value")
  String protocollazione_message_registrazione_interna_esito_OK_value(String attribute0, String attribute1);
  
  @Key("protocollazione_message_repertoriazione_esito_OK_value")
  String protocollazione_message_repertoriazione_esito_OK_value(String attribute0, String attribute1, String attribute2);

  @Key("protocollazione_previewDocWindow_title")
  String protocollazione_previewDocWindow_title(); 

  @Key("protocollazione_scansioneWindow_title")
  String protocollazione_scansioneWindow_title(); 


  @Key("protocollazione_select_listmap_AOOI_value")
  String protocollazione_select_listmap_AOOI_value(); 

  @Key("protocollazione_select_listmap_LD_value")
  String protocollazione_select_listmap_LD_value(); 

  @Key("protocollazione_select_listmap_PA_value")
  String protocollazione_select_listmap_PA_value(); 
  
  @Key("protocollazione_select_listmap_XLS_value")
  String protocollazione_select_listmap_XLS_value(); 

  @Key("protocollazione_select_listmap_PF_value")
  String protocollazione_select_listmap_PF_value(); 

  @Key("protocollazione_select_listmap_PG_value")
  String protocollazione_select_listmap_PG_value(); 

  @Key("protocollazione_select_listmap_PREF_value")
  String protocollazione_select_listmap_PREF_value(); 

  @Key("protocollazione_select_listmap_UP_UOI_value")
  String protocollazione_select_listmap_UP_UOI_value(); 

  @Key("protocollazione_select_listmap_UOI_value")
  String protocollazione_select_listmap_UOI_value(); 

  @Key("protocollazione_select_listmap_UP_value")
  String protocollazione_select_listmap_UP_value(); 

  @Key("protocollazione_select_listmap_generic_value")
  String protocollazione_select_listmap_generic_value(); 

  @Key("protocollazione_stampaEtichettaWindowImpostazioni_title")
  String protocollazione_stampaEtichettaWindowImpostazioni_title(); 

  @Key("protocollazione_stampaEtichettaWindow_title")
  String protocollazione_stampaEtichettaWindow_title(); 

  @Key("protocollazione_stampaEtichetteNrAllegato")
  String protocollazione_stampaEtichetteNrAllegato(); 


  // TAB ALBO
  
  @Key("protocollazione_detail_tabAlbo_prompt")
  String protocollazione_detail_tabAlbo_prompt(); 
  
  @Key("protocollazione_detail_tabAlbo_title")
  String protocollazione_detail_tabAlbo_title(); 
  
  @Key("protocollazione_detail_datiPubblicazioneAlboSection_title")
  String protocollazione_detail_datiPubblicazioneAlboSection_title(); 
  
  @Key("protocollazione_detail_datiAnnullamentoPubblicazioneAlboSection_title")
  String protocollazione_detail_datiAnnullamentoPubblicazioneAlboSection_title(); 

  @Key("protocollazione_detail_datiProroghePubblicazioneAlboSection_title")
  String protocollazione_detail_datiProroghePubblicazioneAlboSection_title(); 
  
  
  @Key("protocollazione_detail_datiFilePrimarioPubblicazioneAlboSection_title")
  String protocollazione_detail_datiFilePrimarioPubblicazioneAlboSection_title(); 
  
  
   @Key("protocollazione_detail_dataInizioPubblicazioneItem_title")
   String protocollazione_detail_dataInizioPubblicazioneItem_title(); 

   @Key("protocollazione_detail_giorniPubblicazioneItem_title")
   String protocollazione_detail_giorniPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_dataFinePubblicazioneItem_title")
   String protocollazione_detail_dataFinePubblicazioneItem_title(); 

   @Key("protocollazione_detail_nominativoOperatoreRichiestaPubblicazioneItem_title")
   String protocollazione_detail_nominativoOperatoreRichiestaPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_responsabileProcedimentoPubblicazioneItem_title")
   String protocollazione_detail_responsabileProcedimentoPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_flgModalitaPubblicazioneItem_title")
   String protocollazione_detail_flgModalitaPubblicazioneItem_title(); 

   @Key("protocollazione_detail_numeroPubblicazioneItem_title")
   String protocollazione_detail_numeroPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_dataRegNelRegistroPubblicazioniItem_title")
   String protocollazione_detail_dataRegNelRegistroPubblicazioniItem_title(); 

   @Key("protocollazione_detail_dataAnnullamentoPubblicazioneItem_title")
   String protocollazione_detail_dataAnnullamentoPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_nominativoOperatoreRichiestaAnnullamentoPubblicazioneItemItem_title")
   String protocollazione_detail_nominativoOperatoreRichiestaAnnullamentoPubblicazioneItemItem_title(); 
   
   @Key("protocollazione_detail_motivazioneAnnullamentoPubblicazioneItem_title")
   String protocollazione_detail_motivazioneAnnullamentoPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_flgPubblicazioneRimossaItem_title")
   String protocollazione_detail_flgPubblicazioneRimossaItem_title(); 
   
   
   @Key("protocollazione_detail_giorniProrogaPubblicazioneItem_title")
   String protocollazione_detail_giorniProrogaPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_nominativoOperatoreRichiestaProrogaPubblicazioneItem_title")
   String protocollazione_detail_nominativoOperatoreRichiestaProrogaPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_dataRichiestaProrogaPubblicazioneItem_title")
   String protocollazione_detail_dataRichiestaProrogaPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_motivazioneRichiestaProrogaPubblicazioneItem_title")
   String protocollazione_detail_motivazioneRichiestaProrogaPubblicazioneItem_title(); 
   
   @Key("protocollazione_detail_storicoProroghePubblicazioneButton_prompt")
   String protocollazione_detail_storicoProroghePubblicazioneButton_prompt(); 
   
   @Key("protocollazione_detail_pubblicazionePrecButton_prompt")
   String protocollazione_detail_pubblicazionePrecButton_prompt(); 
   
   @Key("protocollazione_detail_pubblicazioneSuccButton_prompt")
   String protocollazione_detail_pubblicazioneSuccButton_prompt(); 
   
   @Key("protocollazione_detail_nomeFilePrimarioAlboVerTxtItem_title")
   String protocollazione_detail_nomeFilePrimarioAlboVerTxtItem_title(); 
   
   @Key("protocollazione_detail_nomeFilePrimarioAlboVerPdfItem_title")
   String protocollazione_detail_nomeFilePrimarioAlboVerPdfItem_title(); 
   
  //   # POPUP SELEZIONA TIPOLOGIA
   @Key("protocollazione_select_tipologia_empty_value")
   String protocollazione_select_tipologia_empty_value(); 
   
   @Key("protocollazione_select_repertorio_tipologia_empty_value")
   String protocollazione_select_repertorio_tipologia_empty_value(); 
   
  //###################################################################
  //#              Atti Autorizzazione Annullamento Registrazioni     #
  //###################################################################
   
   // #AttiAutorizzazioneAnnRegDetail
   @Key("atti_autorizzazione_annreg_nroBozza")
   String atti_autorizzazione_annreg_nroBozza();
   
   @Key("atti_autorizzazione_annreg_tsRegBozza")
   String atti_autorizzazione_annreg_tsRegBozza();
   
   @Key("atti_autorizzazione_annreg_desUteBozza")
   String atti_autorizzazione_annreg_desUteBozza();
   
   @Key("atti_autorizzazione_annreg_nroAtto")
   String atti_autorizzazione_annreg_nroAtto(); 
   
   @Key("atti_autorizzazione_annreg_tsRegAtto")
   String atti_autorizzazione_annreg_tsRegAtto();
   
   @Key("atti_autorizzazione_annreg_desUteAtto")
   String atti_autorizzazione_annreg_desUteAtto();
    
   @Key("atti_autorizzazione_annreg_oggetto")
   String atti_autorizzazione_annreg_oggetto();
   
   @Key("atti_autorizzazione_annreg_regDaAnnullareSection")
   String atti_autorizzazione_annreg_regDaAnnullareSection();
   
   @Key("atti_autorizzazione_listaRegDaAnnullare_nroReg")
   String atti_autorizzazione_listaRegDaAnnullare_nroReg();
   
   @Key("atti_autorizzazione_listaRegDaAnnullare_dettaglioUdRegButton")
   String atti_autorizzazione_listaRegDaAnnullare_dettaglioUdRegButton ();
   
   @Key("atti_autorizzazione_listaRegDaAnnullare_tsRichAnn")
   String atti_autorizzazione_listaRegDaAnnullare_tsRichAnn();
   
   @Key("atti_autorizzazione_listaRegDaAnnullare_uteRichAnn")
   String atti_autorizzazione_listaRegDaAnnullare_uteRichAnn();
   
   @Key("atti_autorizzazione_listaRegDaAnnullare_motiviAnn")
   String atti_autorizzazione_listaRegDaAnnullare_motiviAnn();
   
   
   
  // #Filter  
  @Key("auriga_filter_atti_autorizzazione_annreg_dtCreazione_title")
  String auriga_filter_atti_autorizzazione_annreg_dtCreazione_title();

  @Key("auriga_filter_atti_autorizzazione_annreg_nroBozza_title")
  String auriga_filter_atti_autorizzazione_annreg_nroBozza_title();
  
  @Key("auriga_filter_atti_autorizzazione_annreg_annoCreazione_title")
  String auriga_filter_atti_autorizzazione_annreg_annoCreazione_title();
  
  
   
  //###################################################################
  //#                     NUOVA PROPOSTA ATTO 2                       #
  //###################################################################
  
  @Key("nuovaPropostaAtto2_detail_tabDatiScheda_prompt")
  String nuovaPropostaAtto2_detail_tabDatiScheda_prompt(); 

  @Key("nuovaPropostaAtto2_detail_tabDatiDispositivo_prompt")
  String nuovaPropostaAtto2_detail_tabDatiDispositivo_prompt();
  
  @Key("nuovaPropostaAtto2_detail_tabDatiDispositivo2_prompt")
  String nuovaPropostaAtto2_detail_tabDatiDispositivo2_prompt();
  
  @Key("nuovaPropostaAtto2_detail_tabAllegati_prompt")
  String nuovaPropostaAtto2_detail_tabAllegati_prompt(); 
 
  @Key("nuovaPropostaAtto2_detail_tabAllegatiPareri_prompt")
  String nuovaPropostaAtto2_detail_tabAllegatiPareri_prompt(); 
 
  @Key("nuovaPropostaAtto2_detail_tabDatiContabili_prompt")
  String nuovaPropostaAtto2_detail_tabDatiContabili_prompt(); 

  @Key("nuovaPropostaAtto2_detail_tabDatiSpesa_prompt")
  String nuovaPropostaAtto2_detail_tabDatiSpesa_prompt(); 

  @Key("nuovaPropostaAtto2_detail_tabDatiSpesaCorrente_prompt")
  String nuovaPropostaAtto2_detail_tabDatiSpesaCorrente_prompt(); 

  @Key("nuovaPropostaAtto2_detail_tabDatiSpesaContoCapitale_prompt")
  String nuovaPropostaAtto2_detail_tabDatiSpesaContoCapitale_prompt(); 
  
  @Key("nuovaPropostaAtto2_detail_tabDatiSpesaPersonale_prompt")
  String nuovaPropostaAtto2_detail_tabDatiSpesaPersonale_prompt(); 
   
  @Key("nuovaPropostaAtto2_detail_detailSectionRegistrazione_title")
  String nuovaPropostaAtto2_detail_detailSectionRegistrazione_title(); 
   
  @Key("nuovaPropostaAtto2_detail_detailSectionPubblicazione_title")
  String nuovaPropostaAtto2_detail_detailSectionPubblicazione_title(); 

  @Key("nuovaPropostaAtto2_detail_detailSectionRuoli_title")
  String nuovaPropostaAtto2_detail_detailSectionRuoli_title(); 
  
  @Key("nuovaPropostaAtto2_detail_detailSectionPropostaConcertoCon_title")
  String nuovaPropostaAtto2_detail_detailSectionPropostaConcertoCon_title(); 
  
  @Key("nuovaPropostaAtto2_detail_detailSectionProponenti_title")
  String nuovaPropostaAtto2_detail_detailSectionProponenti_title();   
  
  @Key("nuovaPropostaAtto2_detail_detailSectionEstensori_title")
  String nuovaPropostaAtto2_detail_detailSectionEstensori_title();  
  
  @Key("nuovaPropostaAtto2_detail_detailSectionCIG_title")
  String nuovaPropostaAtto2_detail_detailSectionCIG_title();

  @Key("nuovaPropostaAtto2_detail_detailSectionOggetto_title")
  String nuovaPropostaAtto2_detail_detailSectionOggetto_title(); 

  @Key("nuovaPropostaAtto2_detail_detailSectionCaratteristicheProvvedimento_title")
  String nuovaPropostaAtto2_detail_detailSectionCaratteristicheProvvedimento_title(); 
  
  @Key("nuovaPropostaAtto2_detail_detailSectionAttiPresupposti_title")
  String nuovaPropostaAtto2_detail_detailSectionAttiPresupposti_title(); 

  @Key("nuovaPropostaAtto2_detail_detailSectionRiferimentiNormativi_title")
  String nuovaPropostaAtto2_detail_detailSectionRiferimentiNormativi_title(); 

  @Key("nuovaPropostaAtto2_detail_detailSectionMotivazioni_title")
  String nuovaPropostaAtto2_detail_detailSectionMotivazioni_title(); 

  @Key("nuovaPropostaAtto2_detail_detailSectionPremessa_title")
  String nuovaPropostaAtto2_detail_detailSectionPremessa_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionLoghiAggiuntiviDispositivo_title")
  String nuovaPropostaAtto2_detail_detailSectionLoghiAggiuntiviDispositivo_title();
 
  @Key("nuovaPropostaAtto2_detail_detailSectionDispositivo_title")
  String nuovaPropostaAtto2_detail_detailSectionDispositivo_title(); 

  @Key("nuovaPropostaAtto2_detail_detailSectionAllegati_title")
  String nuovaPropostaAtto2_detail_detailSectionAllegati_title(); 

  @Key("nuovaPropostaAtto2_detail_detailSectionRuoliSpesa_title")
  String nuovaPropostaAtto2_detail_detailSectionRuoliSpesa_title(); 
  
  @Key("nuovaPropostaAtto2_detail_detailSectionOpzioniSpesa_title")
  String nuovaPropostaAtto2_detail_detailSectionOpzioniSpesa_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaCorrente_title")
  String nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaCorrente_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBCorrente_title")
  String nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBCorrente_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaCorrente_title")
  String nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaCorrente_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionFileXlsCorrente_title")
  String nuovaPropostaAtto2_detail_detailSectionFileXlsCorrente_title();
		  
  @Key("nuovaPropostaAtto2_detail_detailSectionAllegatiCorrente_title")
  String nuovaPropostaAtto2_detail_detailSectionAllegatiCorrente_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionNoteCorrente_title")
  String nuovaPropostaAtto2_detail_detailSectionNoteCorrente_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaContoCapitale_title")
  String nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaContoCapitale_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBContoCapitale_title")
  String nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBContoCapitale_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaContoCapitale_title")
  String nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaContoCapitale_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionFileXlsContoCapitale_title")
  String nuovaPropostaAtto2_detail_detailSectionFileXlsContoCapitale_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionAllegatiContoCapitale_title")
  String nuovaPropostaAtto2_detail_detailSectionAllegatiContoCapitale_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionNoteContoCapitale_title")
  String nuovaPropostaAtto2_detail_detailSectionNoteContoCapitale_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionSpesaAnnoCorrenteESuccessivi_title")
  String nuovaPropostaAtto2_detail_detailSectionSpesaAnnoCorrenteESuccessivi_title();
  
  @Key("nuovaPropostaAtto2_detail_detailSectionSpesaAnnua_title")
  String nuovaPropostaAtto2_detail_detailSectionSpesaAnnua_title();
 
  @Key("nuovaPropostaAtto2_detail_iconaTipoRegistrazione_prompt")
  String nuovaPropostaAtto2_detail_iconaTipoRegistrazione_prompt();

  @Key("nuovaPropostaAtto2_detail_siglaRegistrazione_title")
  String nuovaPropostaAtto2_detail_siglaRegistrazione_title();

  @Key("nuovaPropostaAtto2_detail_numeroRegistrazione_title")
  String nuovaPropostaAtto2_detail_numeroRegistrazione_title();

  @Key("nuovaPropostaAtto2_detail_dataRegistrazione_title")
  String nuovaPropostaAtto2_detail_dataRegistrazione_title();

  @Key("nuovaPropostaAtto2_detail_desUserRegistrazione_title")
  String nuovaPropostaAtto2_detail_desUserRegistrazione_title();

  @Key("nuovaPropostaAtto2_detail_desUORegistrazione_title")
  String nuovaPropostaAtto2_detail_desUORegistrazione_title();

  @Key("nuovaPropostaAtto2_detail_iconaTipoRegProvvisoria_prompt")
  String nuovaPropostaAtto2_detail_iconaTipoRegProvvisoria_prompt();

  @Key("nuovaPropostaAtto2_detail_siglaRegProvvisoria_title")
  String nuovaPropostaAtto2_detail_siglaRegProvvisoria_title();
 
  @Key("nuovaPropostaAtto2_detail_numeroRegProvvisoria_title")
  String nuovaPropostaAtto2_detail_numeroRegProvvisoria_title();

  @Key("nuovaPropostaAtto2_detail_dataRegProvvisoria_title")
  String nuovaPropostaAtto2_detail_dataRegProvvisoria_title();

  @Key("nuovaPropostaAtto2_detail_desUserRegProvvisoria_title")
  String nuovaPropostaAtto2_detail_desUserRegProvvisoria_title();

  @Key("nuovaPropostaAtto2_detail_desUORegProvvisoria_title")
  String nuovaPropostaAtto2_detail_desUORegProvvisoria_title();

  @Key("nuovaPropostaAtto2_detail_dataInizioPubblicazione_title")
  String nuovaPropostaAtto2_detail_dataInizioPubblicazione_title();
  
  @Key("nuovaPropostaAtto2_detail_giorniPubblicazione_title")
  String nuovaPropostaAtto2_detail_giorniPubblicazione_title();
 
  @Key("nuovaPropostaAtto2_detail_ufficioProponente_title")
  String nuovaPropostaAtto2_detail_ufficioProponente_title(); 

  @Key("nuovaPropostaAtto2_detail_dirigenteAdottante_title")
  String nuovaPropostaAtto2_detail_dirigenteAdottante_title(); 
 
  @Key("nuovaPropostaAtto2_detail_responsabileProcedimento_title")
  String nuovaPropostaAtto2_detail_responsabileProcedimento_title();   
 
  @Key("nuovaPropostaAtto2_detail_responsabileUnicoProvvedimento_title")
  String nuovaPropostaAtto2_detail_responsabileUnicoProvvedimento_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgAncheRdP_title")
  String nuovaPropostaAtto2_detail_flgAncheRdP_title(); 
   
  @Key("nuovaPropostaAtto2_detail_flgAncheRUP_title")
  String nuovaPropostaAtto2_detail_flgAncheRUP_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgAncheAdottante_title")
  String nuovaPropostaAtto2_detail_flgAncheAdottante_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgRichiediVistoDirettore_title")
  String nuovaPropostaAtto2_detail_flgRichiediVistoDirettore_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgDeterminaAContrarreTramiteProceduraGara_title")
  String nuovaPropostaAtto2_detail_flgDeterminaAContrarreTramiteProceduraGara_title();  
  
  @Key("nuovaPropostaAtto2_detail_flgDeterminaAggiudicaProceduraGara_title")
  String nuovaPropostaAtto2_detail_flgDeterminaAggiudicaProceduraGara_title();  
  
  @Key("nuovaPropostaAtto2_detail_flgDeterminaRimodulazioneSpesaGaraAggiudicata_title")
  String nuovaPropostaAtto2_detail_flgDeterminaRimodulazioneSpesaGaraAggiudicata_title();
  
  @Key("nuovaPropostaAtto2_detail_flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro_title")
  String nuovaPropostaAtto2_detail_flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro_title();
  
  @Key("nuovaPropostaAtto2_detail_attoDeterminaAContrarre_title")
  String nuovaPropostaAtto2_detail_attoDeterminaAContrarre_title();  
  
  @Key("nuovaPropostaAtto2_detail_attoRiferimento_title")
  String nuovaPropostaAtto2_detail_attoRiferimento_title();  
  
  @Key("nuovaPropostaAtto2_detail_siglaAttoDeterminaAContrarre_title")
  String nuovaPropostaAtto2_detail_siglaAttoDeterminaAContrarre_title();  
 
  @Key("nuovaPropostaAtto2_detail_numeroAttoDeterminaAContrarre_title")
  String nuovaPropostaAtto2_detail_numeroAttoDeterminaAContrarre_title();  
  
  @Key("nuovaPropostaAtto2_detail_annoAttoDeterminaAContrarre_title")
  String nuovaPropostaAtto2_detail_annoAttoDeterminaAContrarre_title();  
  
  @Key("nuovaPropostaAtto2_detail_flgDeterminaConcerto_title")
  String nuovaPropostaAtto2_detail_flgDeterminaConcerto_title();  
  
  @Key("nuovaPropostaAtto2_detail_flgSpesa_title")
  String nuovaPropostaAtto2_detail_flgSpesa_title();  
  
  @Key("nuovaPropostaAtto2_detail_richVerificaDiValidator_errorMessage")
  String nuovaPropostaAtto2_detail_richVerificaDiValidator_errorMessage();  
  
  @Key("nuovaPropostaAtto2_detail_richVerificaDi_title")
  String nuovaPropostaAtto2_detail_richVerificaDi_title();  
   
  @Key("nuovaPropostaAtto2_detail_flgRichVerificaDiBilancioCorrente_title")
  String nuovaPropostaAtto2_detail_flgRichVerificaDiBilancioCorrente_title();  
  
  @Key("nuovaPropostaAtto2_detail_flgRichVerificaDiBilancioContoCapitale_title")
  String nuovaPropostaAtto2_detail_flgRichVerificaDiBilancioContoCapitale_title();  
  
  @Key("nuovaPropostaAtto2_detail_flgRichVerificaDiContabilita_title")
  String nuovaPropostaAtto2_detail_flgRichVerificaDiContabilita_title();  
  
  @Key("nuovaPropostaAtto2_detail_flgPresaVisioneContabilita_title")
  String nuovaPropostaAtto2_detail_flgPresaVisioneContabilita_title();
  
  @Key("nuovaPropostaAtto2_detail_loghiAggiuntiviDispositivo_title")
  String nuovaPropostaAtto2_detail_loghiAggiuntiviDispositivo_title();
     
  @Key("nuovaPropostaAtto2_detail_flgFirmatario_title")
  String nuovaPropostaAtto2_detail_flgFirmatario_title();
  
  @Key("nuovaPropostaAtto2_detail_dirigentiProponenti_title")
  String nuovaPropostaAtto2_detail_dirigentiProponenti_title();
  
  @Key("nuovaPropostaAtto2_detail_assessori_title")
  String nuovaPropostaAtto2_detail_assessori_title();
  
  @Key("nuovaPropostaAtto2_detail_consiglieri_title")
  String nuovaPropostaAtto2_detail_consiglieri_title();
  
  @Key("nuovaPropostaAtto2_detail_allegati_flgParteIntegrante_title")
  String nuovaPropostaAtto2_detail_allegati_flgParteIntegrante_title();
  
  @Key("nuovaPropostaAtto2_detail_allegati_flgDatiSensibili_title")
  String nuovaPropostaAtto2_detail_allegati_flgDatiSensibili_title();
  
  @Key("nuovaPropostaAtto2_detail_flgAdottanteUnicoRespPEG_title")
  String nuovaPropostaAtto2_detail_flgAdottanteUnicoRespPEG_title(); 
  
  @Key("nuovaPropostaAtto2_detail_responsabiliPEG_title")
  String nuovaPropostaAtto2_detail_responsabiliPEG_title(); 
  
  @Key("nuovaPropostaAtto2_detail_ufficioDefinizioneSpesa_title")
  String nuovaPropostaAtto2_detail_ufficioDefinizioneSpesa_title(); 
  
  @Key("nuovaPropostaAtto2_detail_tipoSpesaValidator_errorMessage")
  String nuovaPropostaAtto2_detail_tipoSpesaValidator_errorMessage(); 
  
  @Key("nuovaPropostaAtto2_detail_tipoSpesa_title")
  String nuovaPropostaAtto2_detail_tipoSpesa_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgSpesaCorrente_title")
  String nuovaPropostaAtto2_detail_flgSpesaCorrente_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgImpegniCorrenteGiaValidati_title")
  String nuovaPropostaAtto2_detail_flgImpegniCorrenteGiaValidati_title(); 
 
  @Key("nuovaPropostaAtto2_detail_flgSpesaContoCapitale_title")
  String nuovaPropostaAtto2_detail_flgSpesaContoCapitale_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgImpegniContoCapitaleGiaRilasciati_title")
  String nuovaPropostaAtto2_detail_flgImpegniContoCapitaleGiaRilasciati_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgSoloSubImpSubCrono_title")
  String nuovaPropostaAtto2_detail_flgSoloSubImpSubCrono_title(); 
 
  @Key("nuovaPropostaAtto2_detail_flgConVerificaContabilita_title")
  String nuovaPropostaAtto2_detail_flgConVerificaContabilita_title(); 
  
  @Key("nuovaPropostaAtto2_detail_flgRichiediParereRevisoriContabili_title")
  String nuovaPropostaAtto2_detail_flgRichiediParereRevisoriContabili_title(); 
  
  @Key("nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_title")
  String nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_title(); 
  
  @Key("nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneA")
  String nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneA();

  @Key("nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneB")
  String nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneB();
	
  @Key("nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_title")
  String nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_title();
  
  @Key("nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB1")
  String nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB1();
		  
  @Key("nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB2")
  String nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB2();
  
  @Key("nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB3")
  String nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB3();
  
  @Key("nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB4")
  String nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB4(); 	
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_IPG_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_IPG_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_ACC_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_ACC_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VIP_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VIP_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VAC_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VAC_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SIP_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SIP_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SAC_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SAC_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VSI_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VSI_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VSA_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VSA_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_COP_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_COP_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SCP_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SCP_value();

  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_numeroDettaglio_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_numeroDettaglio_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_subNumero_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_subNumero_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_annoCrono_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_annoCrono_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_numeroCrono_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_numeroCrono_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_subCrono_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_subCrono_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_annoEsercizio_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_annoEsercizio_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_flgCorrelata_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_flgCorrelata_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_annoCompetenza_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_annoCompetenza_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_E_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_E_value();
		 
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_U_value")
  String nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_U_value();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_importo_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_importo_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_oggetto_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_oggetto_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_codiceCIG_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_codiceCIG_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_codiceCUP_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_codiceCUP_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_codiceGAMIPBM_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_codiceGAMIPBM_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_unitaOrgCdR_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_unitaOrgCdR_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_descrizioneCapitolo_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_descrizioneCapitolo_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_liv5PdC_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_liv5PdC_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_annoEsigibilitaDebito_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_annoEsigibilitaDebito_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaDa_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaDa_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaA_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaA_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_dataScadenzaEntrata_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_dataScadenzaEntrata_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_dichiarazioneDL78_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_dichiarazioneDL78_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_tipoFinanziamento_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_tipoFinanziamento_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_denominazioneSogg_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_denominazioneSogg_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_codFiscaleSogg_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_codFiscaleSogg_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_codPIVASogg_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_codPIVASogg_title(); 
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_indirizzoSogg_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_indirizzoSogg_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_cap_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_cap_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_localita_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_localita_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_provincia_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_provincia_title();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_flgValidato_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_flgValidato_title(); 
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_settore_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_settore_title();  
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_specifiche_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_specifiche_title();
 
  @Key("nuovaPropostaAtto2_detail_listaDatiContabili_note_title")
  String nuovaPropostaAtto2_detail_listaDatiContabili_note_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_IPG_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_IPG_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_ACC_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_ACC_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VIP_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VIP_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VAC_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VAC_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SIP_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SIP_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SAC_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SAC_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSI_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSI_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSA_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSA_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_COP_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_COP_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SCP_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SCP_value();

  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroDettaglio_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroDettaglio_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subNumero_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subNumero_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCrono_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCrono_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroCrono_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroCrono_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subCrono_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subCrono_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoEsercizio_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoEsercizio_title();
   
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_E_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_E_value();
		 
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_U_value")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_U_value();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_capitolo_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_capitolo_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_articolo_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_articolo_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numero_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numero_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_descrizioneCapitolo_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_descrizioneCapitolo_title();
 
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_liv5PdC_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_liv5PdC_title();
 
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCompetenza_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCompetenza_title();
 
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importoDisponibile_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importoDisponibile_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importo_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importo_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgCorrelata_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgCorrelata_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgValidato_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgValidato_title();  
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_oggetto_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_oggetto_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_unitaOrgCdRFilterXCap_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_unitaOrgCdRFilterXCap_title();
    
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCIG_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCIG_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCUP_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCUP_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceGAMIPBM_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceGAMIPBM_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_unitaOrgCdR_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_unitaOrgCdR_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoEsigibilitaDebito_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoEsigibilitaDebito_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaDa_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaDa_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaA_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaA_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataScadenzaEntrata_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataScadenzaEntrata_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dichiarazioneDL78_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dichiarazioneDL78_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoFinanziamento_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoFinanziamento_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_denominazioneSogg_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_denominazioneSogg_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codFiscaleSogg_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codFiscaleSogg_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codPIVASogg_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codPIVASogg_title();  
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_indirizzoSogg_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_indirizzoSogg_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_cap_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_cap_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_localita_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_localita_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_provincia_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_provincia_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_settore_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_settore_title();  
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_listaSpecifiche_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_listaSpecifiche_title();
 
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_note_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_note_title();
 
  @Key("nuovaPropostaAtto2_detail_datiStoriciWindow_title")
  String nuovaPropostaAtto2_detail_datiStoriciWindow_title();
  
  @Key("nuovaPropostaAtto2_detail_invioDatiSpesaWindow_title")
  String nuovaPropostaAtto2_detail_invioDatiSpesaWindow_title();
  
  @Key("nuovaPropostaAtto2_detail_selectDependsFromUOProponentePickList_emptyMessage")
  String nuovaPropostaAtto2_detail_selectDependsFromUOProponentePickList_emptyMessage();
  
  @Key("nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage")
  String nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage();
  
  @Key("nuovaPropostaAtto2_detail_avvisoNumerazioneConRegistrazione")
  String nuovaPropostaAtto2_detail_avvisoNumerazioneConRegistrazione();
  
  @Key("nuovaPropostaAtto2_detail_avvisoNumerazioneSenzaRegistrazione")
  String nuovaPropostaAtto2_detail_avvisoNumerazioneSenzaRegistrazione();
  
  @Key("nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneConRegistrazione")
  String nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneConRegistrazione();
  
  @Key("nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneSenzaRegistrazione")
  String nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneSenzaRegistrazione();
  
  @Key("nuovaPropostaAtto2_detail_avvisoPostErroreRollbackNumerazioneDefinitiva")
  String nuovaPropostaAtto2_detail_avvisoPostErroreRollbackNumerazioneDefinitiva();
  
  @Key("nuovaPropostaAtto2_detail_avviso_Warning_PostErroreRollbackNumerazioneDefinitiva")
  String nuovaPropostaAtto2_detail_avviso_Warning_PostErroreRollbackNumerazioneDefinitiva();
    
  @Key("protocollazione_detail_fileExistIcon_prompt")
  String protocollazione_detail_fileExistIcon_prompt(); 

  @Key("protocollazione_detail_fileNotExistIcon_prompt")
  String protocollazione_detail_fileNotExistIcon_prompt(); 


  //#############################################################
  //#                        PUNTI VENDITA                      #
  //#############################################################	 

  @Key("puntivendita_combo_label")
  String puntivendita_combo_label(); 

  @Key("puntivendita_combo_denominazioneField")
  String puntivendita_combo_denominazioneField(); 

  @Key("puntivendita_combo_cittaField")
  String puntivendita_combo_cittaField(); 

  @Key("puntivendita_combo_targaProvinciaField")
  String puntivendita_combo_targaProvinciaField(); 

  @Key("puntivendita_combo_capField")
  String puntivendita_combo_capField(); 

  @Key("puntivendita_combo_indirizzoField")
  String puntivendita_combo_indirizzoField(); 

  @Key("puntivendita_combo_civicoIndirizzoField")
  String puntivendita_combo_civicoIndirizzoField(); 



  //#############################################################
  //#                       REGISTRO DOCUMENTI                  #
  //#############################################################

  @Key("registro_documenti_cod_fisc_PIVA")
  String registro_documenti_cod_fisc_PIVA(); 

  @Key("registro_documenti_codice_cliente")
  String registro_documenti_codice_cliente(); 

  @Key("registro_documenti_codice_contratto")
  String registro_documenti_codice_contratto(); 

  @Key("registro_documenti_codice_documento")
  String registro_documenti_codice_documento(); 

  @Key("registro_documenti_codice_fornitura")
  String registro_documenti_codice_fornitura(); 

  @Key("registro_documenti_codice_prd_pod")
  String registro_documenti_codice_prd_pod(); 

  @Key("registro_documenti_codice_processo")
  String registro_documenti_codice_processo(); 

  @Key("registro_documenti_codice_societa")
  String registro_documenti_codice_societa(); 

  @Key("registro_documenti_codice_tipo_documento")
  String registro_documenti_codice_tipo_documento(); 

  @Key("registro_documenti_data_documento")
  String registro_documenti_data_documento(); 

  @Key("registro_documenti_descrizione_cliente")
  String registro_documenti_descrizione_cliente(); 

  @Key("registro_documenti_idud")
  String registro_documenti_idud(); 

  @Key("registro_documenti_nome")
  String registro_documenti_nome(); 

  @Key("registro_documenti_numero_documento")
  String registro_documenti_numero_documento(); 

  @Key("registro_documenti_oggetto")
  String registro_documenti_oggetto(); 

  @Key("registro_documenti_scarica_documento_title")
  String registro_documenti_scarica_documento_title(); 

  @Key("registro_documenti_sezionale")
  String registro_documenti_sezionale(); 

  @Key("registro_documenti_stato")
  String registro_documenti_stato(); 

  @Key("registro_documenti_stato_conservazione")
  String registro_documenti_stato_conservazione(); 

  @Key("registro_documenti_tipo_fattura")
  String registro_documenti_tipo_fattura(); 

  @Key("registro_documenti_tipo_servizio")
  String registro_documenti_tipo_servizio(); 

  @Key("registro_documenti_visualizza_documento_title")
  String registro_documenti_visualizza_documento_title(); 
  
  
 //#############################################################
 //#                   REGISTRI NUMERAZIONE                    #
 //#############################################################
  

  //#RegistriNumerazioneDetail
  @Key("registri_numerazione_detail_codCategoriaItem")
  String registri_numerazione_detail_codCategoriaItem();
  
  @Key("registri_numerazione_detail_siglaTipoRegNumItem")
  String registri_numerazione_detail_siglaTipoRegNumItem();
  
  @Key("registri_numerazione_detail_descrizioneItem")
  String registri_numerazione_detail_descrizioneItem();
  
  @Key("registri_numerazione_detail_dtInizioVldItem")
  String registri_numerazione_detail_dtInizioVldItem();
  
  @Key("registri_numerazione_detail_dtFineVldItem")
  String registri_numerazione_detail_dtFineVldItem();
  
  @Key("registri_numerazione_detail_flgStatoRegistroItem")
  String registri_numerazione_detail_flgStatoRegistroItem();
  
  @Key("registri_numerazione_detail_flgStatoRegistroItem_aperto")
  String registri_numerazione_detail_flgStatoRegistroItem_aperto();
  
  @Key("registri_numerazione_detail_flgStatoRegistroItem_chiuso")
  String registri_numerazione_detail_flgStatoRegistroItem_chiuso();
  
  @Key("registri_numerazione_detail_nrAnniRinnovaNumerazioneItem")
  String registri_numerazione_detail_nrAnniRinnovaNumerazioneItem();
  
  @Key("registri_numerazione_detail_annoInizioNumerazioneItem")
  String registri_numerazione_detail_annoInizioNumerazioneItem();
  
  @Key("registri_numerazione_detail_nroInizialeItem")
  String registri_numerazione_detail_nroInizialeItem();
  
  @Key("registri_numerazione_detail_nrUltimaRegItem")
  String registri_numerazione_detail_nrUltimaRegItem();
  
  @Key("registri_numerazione_detail_dtUltimaRegItem")
  String registri_numerazione_detail_dtUltimaRegItem();
  
  @Key("registri_numerazione_detail_flgRestrizioniVersoRegEItem")
  String registri_numerazione_detail_flgRestrizioniVersoRegEItem();
  
  @Key("registri_numerazione_detail_flgRestrizioniVersoRegUItem")
  String registri_numerazione_detail_flgRestrizioniVersoRegUItem();
  
  @Key("registri_numerazione_detail_flgRestrizioniVersoRegIItem")
  String registri_numerazione_detail_flgRestrizioniVersoRegIItem();
  
  @Key("registri_numerazione_detail_flgCtrAbilUOMittItem")
  String registri_numerazione_detail_flgCtrAbilUOMittItem();
  
  @Key("registri_numerazione_detail_gruppoRegistriAppItem")
  String registri_numerazione_detail_gruppoRegistriAppItem();

  @Key("registri_numerazione_detail_dtUltimoCambioStatoItem")
  String registri_numerazione_detail_dtUltimoCambioStatoItem();
  
  @Key("registri_numerazione_detail_Abilitazioni")
  String registri_numerazione_detail_Abilitazioni();
  
  @Key("registri_numerazione_detail_flgRichAbilXVisualizzItem")
  String registri_numerazione_detail_flgRichAbilXVisualizzItem();
  
  @Key("registri_numerazione_detail_flgRichAbilXNumerareItem")
  String registri_numerazione_detail_flgRichAbilXNumerareItem();
  
  @Key("registri_numerazione_detail_flgInternaItem")
  String registri_numerazione_detail_flgInternaItem();
  
  @Key("registri_numerazione_detail_flgNumerazioneSenzaContinuitaItem")
  String registri_numerazione_detail_flgNumerazioneSenzaContinuitaItem();
  
  @Key("registri_numerazione_detail_tipiDocAmmEscSection_title")
  String registri_numerazione_detail_tipiDocAmmEscSection_title();

  @Key("registri_numerazione_view_title")
  String registri_numerazione_view_title(String attribute0);
  
  @Key("registri_numerazione_edit_title")
  String registri_numerazione_edit_title(String attribute0);
  
  @Key("registri_numerazione_new_title")
  String registri_numerazione_new_title();

  //#RegistriNumerazioneList
  @Key("registri_numerazione_list_idTipoRegNum")
  String registri_numerazione_list_idTipoRegNum();

  @Key("registri_numerazione_list_descrizione")
  String registri_numerazione_list_descrizione();

  @Key("registri_numerazione_list_desCategoria")
  String registri_numerazione_list_desCategoria();

  @Key("registri_numerazione_list_siglaTipoRegNum")
  String registri_numerazione_list_siglaTipoRegNum();
  
  @Key("registri_numerazione_list_gruppiRegistriAppartenenza")
  String registri_numerazione_list_gruppiRegistriAppartenenza();
  
  @Key("registri_numerazione_list_nrAnniRinnovaNumerazione")
  String registri_numerazione_list_nrAnniRinnovaNumerazione();
  
  @Key("registri_numerazione_list_nrUltimaReg")
  String registri_numerazione_list_nrUltimaReg();
  
  @Key("registri_numerazione_list_descStatoRegistro")
  String registri_numerazione_list_descStatoRegistro();

  @Key("registri_numerazione_list_valido")
  String registri_numerazione_list_valido();
  
  @Key("registri_numerazione_list_dtUltimaReg")
  String registri_numerazione_list_dtUltimaReg();
  
  @Key("registri_numerazione_list_stringListaDocRegistrabili")
  String registri_numerazione_list_stringListaDocRegistrabili();
  
  @Key("registri_numerazione_list_dtCreazione")
  String registri_numerazione_list_dtCreazione();
  
  @Key("registri_numerazione_list_desUteCreazione")
  String registri_numerazione_list_desUteCreazione();
  
  @Key("registri_numerazione_list_dtUltimaModifica")
  String registri_numerazione_list_dtUltimaModifica();
  
  @Key("registri_numerazione_list_desUteUltimaMod")
  String registri_numerazione_list_desUteUltimaMod();
  
  @Key("registri_numerazione_list_flgAbilFirma")
  String registri_numerazione_list_flgAbilFirma();
  
  @Key("registri_numerazione_list_flgRichAbilVis")
  String registri_numerazione_list_flgRichAbilVis();
  
  @Key("registri_numerazione_list_flgRichAbilXGestIn")
  String registri_numerazione_list_flgRichAbilXGestIn();
  
  @Key("registri_numerazione_list_flgRichAbilXAssegn")
  String registri_numerazione_list_flgRichAbilXAssegn();
  
  @Key("registri_numerazione_list_flgInterna")
  String registri_numerazione_list_flgInterna();
  
  @Key("registri_numerazione_list_flgValido")
  String registri_numerazione_list_flgValido();
  
  @Key("registri_numerazione_lookupRegistriNumerazionePopup_title")
  String registri_numerazione_lookupRegistriNumerazionePopup_title();
  
  //#Filtri
  @Key("registri_numerazione_filter_categoria")
  String registri_numerazione_filter_categoria();
  
  @Key("registri_numerazione_filter_sigla")
  String registri_numerazione_filter_sigla();
  
  @Key("registri_numerazione_filter_descrizione")
  String registri_numerazione_filter_descrizione();
  
  @Key("registri_numerazione_filter_statoRegistro")
  String registri_numerazione_filter_statoRegistro();
  
  @Key("registri_numerazione_filter_flgSoloValidi")
  String registri_numerazione_filter_flgSoloValidi();
  
  
  //#############################################################
  //#                  RICHIESTA ACCESSO ATTI                   #
  //#############################################################

  @Key("richiestaAccessoAtti_list_protocollo_title")
  String richiestaAccessoAtti_list_protocollo_title(); 

  @Key("richiestaAccessoAtti_list_dataProtocollo_title")
  String richiestaAccessoAtti_list_dataProtocollo_title(); 

  @Key("richiestaAccessoAtti_list_numUffRichiedente_title")
  String richiestaAccessoAtti_list_numUffRichiedente_title(); 

  @Key("richiestaAccessoAtti_list_richEsterno_title")
  String richiestaAccessoAtti_list_richEsterno_title(); 

  @Key("richiestaAccessoAtti_list_indirizzo_title")
  String richiestaAccessoAtti_list_indirizzo_title(); 

  @Key("richiestaAccessoAtti_list_richRegistrataDa_title")
  String richiestaAccessoAtti_list_richRegistrataDa_title(); 

  @Key("richiestaAccessoAtti_list_dataInvioApprovazione_title")
  String richiestaAccessoAtti_list_dataInvioApprovazione_title(); 

  @Key("richiestaAccessoAtti_list_dataApprovazione_title")
  String richiestaAccessoAtti_list_dataApprovazione_title(); 

  @Key("richiestaAccessoAtti_list_richApprovataDa_title")
  String richiestaAccessoAtti_list_richApprovataDa_title(); 

  @Key("richiestaAccessoAtti_list_dataEsitoCittadella_title")
  String richiestaAccessoAtti_list_dataEsitoCittadella_title(); 

  @Key("richiestaAccessoAtti_list_dataAppuntamento_title")
  String richiestaAccessoAtti_list_dataAppuntamento_title(); 

  @Key("richiestaAccessoAtti_list_dataPrelievo_title")
  String richiestaAccessoAtti_list_dataPrelievo_title(); 

  @Key("richiestaAccessoAtti_list_statoPrelievo_title")
  String richiestaAccessoAtti_list_statoPrelievo_title(); 

  @Key("richiestaAccessoAtti_list_udc_title")
  String richiestaAccessoAtti_list_udc_title(); 

  @Key("richiestaAccessoAtti_list_elencoProtocolliAttiRich_title")
  String richiestaAccessoAtti_list_elencoProtocolliAttiRich_title(); 

  @Key("richiestaAccessoAtti_list_attiNonPresentiInCittadella_title")
  String richiestaAccessoAtti_list_attiNonPresentiInCittadella_title(); 

  @Key("richiestaAccessoAtti_list_datiNotifica_title")
  String richiestaAccessoAtti_list_datiNotifica_title(); 

  @Key("richiestaAccessoAtti_list_attiNonPresentiInCittadella_warning")
  String richiestaAccessoAtti_list_attiNonPresentiInCittadella_warning(); 



  //#############################################################
  //#                        RICHIESTA AUTOTUTELA               #
  //#############################################################	

  //#RichiestaAutotutelaList
  @Key("richiesta_autotutela_list_descrizioneTipoRichiesta")
  String richiesta_autotutela_list_descrizioneTipoRichiesta(); 

  @Key("richiesta_autotutela_list_codiceTipoRichiesta")
  String richiesta_autotutela_list_codiceTipoRichiesta(); 

  @Key("richiesta_autotutela_list_descrizioneStatoRichiesta")
  String richiesta_autotutela_list_descrizioneStatoRichiesta(); 

  @Key("richiesta_autotutela_list_codiceStatoRichiesta")
  String richiesta_autotutela_list_codiceStatoRichiesta(); 

  @Key("richiesta_autotutela_list_codicefiscaleUtente")
  String richiesta_autotutela_list_codicefiscaleUtente(); 

  @Key("richiesta_autotutela_list_numeroDocumento")
  String richiesta_autotutela_list_numeroDocumento(); 

  @Key("richiesta_autotutela_list_numeroProtocollo")
  String richiesta_autotutela_list_numeroProtocollo(); 

  @Key("richiesta_autotutela_list_dataRichiesta")
  String richiesta_autotutela_list_dataRichiesta(); 

  @Key("richiesta_autotutela_list_dataCambioStato")
  String richiesta_autotutela_list_dataCambioStato(); 

  @Key("richiesta_autotutela_list_flagLetto")
  String richiesta_autotutela_list_flagLetto(); 

  @Key("richiesta_autotutela_list_idEnte")
  String richiesta_autotutela_list_idEnte(); 

  @Key("richiesta_autotutela_list_Esito")
  String richiesta_autotutela_list_Esito(); 

  @Key("richiesta_autotutela_list_Motivazione")
  String richiesta_autotutela_list_Motivazione(); 

  @Key("richiesta_autotutela_list_Codice_Acs")
  String richiesta_autotutela_list_Codice_Acs(); 

  @Key("richiesta_autotutela_list_Denominazione_Intestatario")
  String richiesta_autotutela_list_Denominazione_Intestatario(); 

  @Key("richiesta_autotutela_list_Indirizzo_Intestatario")
  String richiesta_autotutela_list_Indirizzo_Intestatario(); 

  @Key("richiesta_autotutela_list_Comune_Intestatario")
  String richiesta_autotutela_list_Comune_Intestatario(); 

  @Key("richiesta_autotutela_list_Provincia_Intestatario")
  String richiesta_autotutela_list_Provincia_Intestatario(); 

  @Key("richiesta_autotutela_list_Cap_Intestatario")
  String richiesta_autotutela_list_Cap_Intestatario(); 

  @Key("richiesta_autotutela_list_Anno_Documento")
  String richiesta_autotutela_list_Anno_Documento(); 

  @Key("richiesta_autotutela_list_Data_Documento")
  String richiesta_autotutela_list_Data_Documento(); 

  @Key("richiesta_autotutela_list_Identrata")
  String richiesta_autotutela_list_Identrata(); 

  @Key("richiesta_autotutela_list_Entrata")
  String richiesta_autotutela_list_Entrata(); 

  @Key("richiesta_autotutela_list_Aliasentrata")
  String richiesta_autotutela_list_Aliasentrata(); 

  @Key("richiesta_autotutela_list_Note")
  String richiesta_autotutela_list_Note(); 


  @Key("richiesta_autotutela_tab_riepilogo")
  String richiesta_autotutela_tab_riepilogo(); 

  @Key("richiesta_autotutela_titolo_sezione_dettaglio")
  String richiesta_autotutela_titolo_sezione_dettaglio(); 

  @Key("richiesta_autotutela_titolo_sezione_stato_lavorazione")
  String richiesta_autotutela_titolo_sezione_stato_lavorazione(); 

  @Key("richiesta_autotutela_titolo_sezione_allegati")
  String richiesta_autotutela_titolo_sezione_allegati(); 

  @Key("richiesta_autotutela_titolo_sezione_altro")
  String richiesta_autotutela_titolo_sezione_altro(); 

  @Key("richiesta_autotutela_label_tipo_richiesta")
  String richiesta_autotutela_label_tipo_richiesta(); 

  @Key("richiesta_autotutela_label_codice_fiscale")
  String richiesta_autotutela_label_codice_fiscale(); 

  @Key("richiesta_autotutela_label_num_documento")
  String richiesta_autotutela_label_num_documento(); 

  @Key("richiesta_autotutela_label_num_prot")
  String richiesta_autotutela_label_num_prot(); 

  @Key("richiesta_autotutela_label_ente")
  String richiesta_autotutela_label_ente(); 

  @Key("richiesta_autotutela_label_motivazione")
  String richiesta_autotutela_label_motivazione(); 

  @Key("richiesta_autotutela_label_ricevuta")
  String richiesta_autotutela_label_ricevuta(); 

  @Key("richiesta_autotutela_label_esito")
  String richiesta_autotutela_label_esito(); 

  @Key("richiesta_autotutela_label_data_richiesta")
  String richiesta_autotutela_label_data_richiesta(); 

  @Key("richiesta_autotutela_label_data_cambio_stato")
  String richiesta_autotutela_label_data_cambio_stato(); 

  @Key("richiesta_autotutela_label_stato")
  String richiesta_autotutela_label_stato(); 

  @Key("richiesta_autotutela_label_letto")
  String richiesta_autotutela_label_letto(); 

  @Key("richiesta_autotutela_label_codice_acs")
  String richiesta_autotutela_label_codice_acs(); 

  @Key("richiesta_autotutela_label_intestatario_denominazione")
  String richiesta_autotutela_label_intestatario_denominazione(); 

  @Key("richiesta_autotutela_label_intestatario_indirizzo")
  String richiesta_autotutela_label_intestatario_indirizzo(); 

  @Key("richiesta_autotutela_label_intestatario_cap")
  String richiesta_autotutela_label_intestatario_cap(); 

  @Key("richiesta_autotutela_label_intestatario_comune")
  String richiesta_autotutela_label_intestatario_comune(); 

  @Key("richiesta_autotutela_label_intestatario_provincia")
  String richiesta_autotutela_label_intestatario_provincia(); 

  @Key("richiesta_autotutela_label_anno_documento")
  String richiesta_autotutela_label_anno_documento(); 

  @Key("richiesta_autotutela_label_data_documento")
  String richiesta_autotutela_label_data_documento(); 

  @Key("richiesta_autotutela_label_entrata")
  String richiesta_autotutela_label_entrata(); 

  @Key("richiesta_autotutela_label_entrata_alias")
  String richiesta_autotutela_label_entrata_alias(); 

  @Key("richiesta_autotutela_label_note")
  String richiesta_autotutela_label_note(); 



  //#############################################################
  //#                  SELEZIONALI ANAGRAFICA                   #
  //#############################################################

  @Key("selezionaStampante_title")
  String selezionaStampante_title(); 

  @Key("sezionali_annullamentoLogicoAsk_message")
  String sezionali_annullamentoLogicoAsk_message(); 

  @Key("sezionali_detail_annoSezionaleItem_title")
  String sezionali_detail_annoSezionaleItem_title(); 

  @Key("sezionali_detail_codSezionaleItem_title")
  String sezionali_detail_codSezionaleItem_title(); 

  @Key("sezionali_detail_descrizioneSezionaleItem_title")
  String sezionali_detail_descrizioneSezionaleItem_title(); 

  @Key("sezionali_detail_edit_title")
  String sezionali_detail_edit_title(String attribute0);

  @Key("sezionali_detail_flgAnnItem_title")
  String sezionali_detail_flgAnnItem_title(); 

  @Key("sezionali_detail_idSezionaleItem_title")
  String sezionali_detail_idSezionaleItem_title(); 

  @Key("sezionali_detail_new_title")
  String sezionali_detail_new_title(); 

  @Key("sezionali_detail_rangeSezionaleAItem_title")
  String sezionali_detail_rangeSezionaleAItem_title(); 

  @Key("sezionali_detail_rangeSezionaleDaItem_title")
  String sezionali_detail_rangeSezionaleDaItem_title(); 

  @Key("sezionali_detail_sezionaleSection_title")
  String sezionali_detail_sezionaleSection_title(); 

  @Key("sezionali_detail_view_title")
  String sezionali_detail_view_title(String attribute0);

  @Key("sezionali_eliminazioneFisicaAsk_message")
  String sezionali_eliminazioneFisicaAsk_message(); 

  @Key("sezionali_flgAnn_1_value")
  String sezionali_flgAnn_1_value(); 

  @Key("sezionali_flgDiSistema_1_value")
  String sezionali_flgDiSistema_1_value(); 

  @Key("sezionali_flgValido_1_value")
  String sezionali_flgValido_1_value(); 

  @Key("sezionali_list_annoSezionaleField_title")
  String sezionali_list_annoSezionaleField_title(); 

  @Key("sezionali_list_codSezionaleField_title")
  String sezionali_list_codSezionaleField_title(); 

  @Key("sezionali_list_dataInsField_title")
  String sezionali_list_dataInsField_title(); 

  @Key("sezionali_list_dataUltModField_title")
  String sezionali_list_dataUltModField_title(); 

  @Key("sezionali_list_descUtenteInsField_title")
  String sezionali_list_descUtenteInsField_title(); 

  @Key("sezionali_list_descUtenteUltModField_title")
  String sezionali_list_descUtenteUltModField_title(); 

  @Key("sezionali_list_descrizioneSezionaleField_title")
  String sezionali_list_descrizioneSezionaleField_title(); 

  @Key("sezionali_list_flgAnnField_title")
  String sezionali_list_flgAnnField_title(); 

  @Key("sezionali_list_flgDiSistemaField_title")
  String sezionali_list_flgDiSistemaField_title(); 

  @Key("sezionali_list_flgValidoField_title")
  String sezionali_list_flgValidoField_title(); 

  @Key("sezionali_list_idSezionaleField_title")
  String sezionali_list_idSezionaleField_title(); 

  @Key("sezionali_list_idUtenteInsField_title")
  String sezionali_list_idUtenteInsField_title(); 

  @Key("sezionali_list_idUtenteUltModField_title")
  String sezionali_list_idUtenteUltModField_title(); 

  @Key("sezionali_list_rangeSezionaleAField_title")
  String sezionali_list_rangeSezionaleAField_title(); 

  @Key("sezionali_list_rangeSezionaleDaField_title")
  String sezionali_list_rangeSezionaleDaField_title(); 

  @Key("sezionali_list_scoreField_title")
  String sezionali_list_scoreField_title(); 



 



  //#############################################################
  //#                          SOGGETTI                         #
  //#############################################################

  @Key("soggetti_annullamentoLogicoAsk_message")
  String soggetti_annullamentoLogicoAsk_message(); 

  @Key("soggetti_codTipoSoggInt_AOOI_value")
  String soggetti_codTipoSoggInt_AOOI_value(); 

  @Key("soggetti_codTipoSoggInt_UOI_value")
  String soggetti_codTipoSoggInt_UOI_value(); 

  @Key("soggetti_codTipoSoggInt_UP_value")
  String soggetti_codTipoSoggInt_UP_value(); 

  @Key("soggetti_contatti_tipo_E_value")
  String soggetti_contatti_tipo_E_value(); 

  @Key("soggetti_contatti_tipo_F_value")
  String soggetti_contatti_tipo_F_value(); 

  @Key("soggetti_contatti_tipo_T_value")
  String soggetti_contatti_tipo_T_value(); 


  //#SoggetiDetail
  @Key("soggetti_detail_altreDenominazioniSection_title")
  String soggetti_detail_altreDenominazioniSection_title(); 

  @Key("soggetti_detail_altreDenominazioni_tipoItem_title")
  String soggetti_detail_altreDenominazioni_tipoItem_title(); 

  @Key("soggetti_detail_assegnaAdUoSection_title")
  String soggetti_detail_assegnaAdUoSection_title(); 

  @Key("soggetti_detail_assegnazioneUoItem_title")
  String soggetti_detail_assegnazioneUoItem_title(); 

  @Key("soggetti_detail_attributiCustomSection_title")
  String soggetti_detail_attributiCustomSection_title(); 

  @Key("soggetti_detail_causaleCessazioneItem_title")
  String soggetti_detail_causaleCessazioneItem_title(); 

  @Key("soggetti_detail_cessazioneSection_title")
  String soggetti_detail_cessazioneSection_title(); 

  @Key("soggetti_detail_cittaNascitaIstituzioneItem_title")
  String soggetti_detail_cittaNascitaIstituzioneItem_title(); 

  @Key("soggetti_detail_cittadinanzaItem_title")
  String soggetti_detail_cittadinanzaItem_title(); 

  @Key("soggetti_detail_codiceAmmInIpaItem_title")
  String soggetti_detail_codiceAmmInIpaItem_title(); 

  @Key("soggetti_detail_codiceAooInIpaItem_title")
  String soggetti_detail_codiceAooInIpaItem_title(); 

  @Key("soggetti_detail_codiceFiscaleItem_title")
  String soggetti_detail_codiceFiscaleItem_title(); 

  @Key("soggetti_detail_codiceRapidoItem_title")
  String soggetti_detail_codiceRapidoItem_title(); 

  @Key("soggetti_detail_codiceUoInIpaItem_title")
  String soggetti_detail_codiceUoInIpaItem_title(); 

  @Key("soggetti_detail_cognomeItem_title")
  String soggetti_detail_cognomeItem_title(); 

  @Key("soggetti_detail_comuneNascitaIstituzioneItem_title")
  String soggetti_detail_comuneNascitaIstituzioneItem_title(); 

  @Key("soggetti_detail_condizioneGiuridicaItem_title")
  String soggetti_detail_condizioneGiuridicaItem_title(); 

  @Key("soggetti_detail_contattiSection_title")
  String soggetti_detail_contattiSection_title(); 

  @Key("soggetti_detail_contatti_flgCasellaIstituzItem_title")
  String soggetti_detail_contatti_flgCasellaIstituzItem_title(); 

  @Key("soggetti_detail_contatti_flgDichIpaItem_title")
  String soggetti_detail_contatti_flgDichIpaItem_title(); 

  @Key("soggetti_detail_contatti_flgPecItem_title")
  String soggetti_detail_contatti_flgPecItem_title(); 

  @Key("soggetti_detail_contatti_flgDomicilioDigitaleItem_title")
  String soggetti_detail_contatti_flgDomicilioDigitaleItem_title(); 

  
  @Key("soggetti_detail_contatti_tipoTelItem_title")
  String soggetti_detail_contatti_tipoTelItem_title(); 

  @Key("soggetti_detail_dataCessazioneItem_title")
  String soggetti_detail_dataCessazioneItem_title(); 

  @Key("soggetti_detail_dataNascitaIstituzioneItem_title")
  String soggetti_detail_dataNascitaIstituzioneItem_title(); 

  @Key("soggetti_detail_denominazioneItem_title")
  String soggetti_detail_denominazioneItem_title(); 

  @Key("soggetti_detail_edit_title")
  String soggetti_detail_edit_title(String attribute0);

  @Key("soggetti_detail_estremiSuIpaSection_title")
  String soggetti_detail_estremiSuIpaSection_title(); 

  @Key("soggetti_detail_flgModificabileSottoUo_title")
  String soggetti_detail_flgModificabileSottoUo_title(); 

  @Key("soggetti_detail_flgPersFisicaItem_title")
  String soggetti_detail_flgPersFisicaItem_title(); 

  @Key("soggetti_detail_flgUnitaDiPersonaleItem_title")
  String soggetti_detail_flgUnitaDiPersonaleItem_title(); 

  @Key("soggetti_detail_flgVisibileSottoUo_title")
  String soggetti_detail_flgVisibileSottoUo_title(); 

  @Key("soggetti_detail_inRubricaCondivisa_message")
  String soggetti_detail_inRubricaCondivisa_message(); 

  @Key("soggetti_detail_inRubricaDi_message")
  String soggetti_detail_inRubricaDi_message(); 

  @Key("soggetti_detail_indirizziSection_title")
  String soggetti_detail_indirizziSection_title(); 

  @Key("soggetti_detail_indirizzi_capItem_title")
  String soggetti_detail_indirizzi_capItem_title(); 

  @Key("soggetti_detail_indirizzi_cittaItem_title")
  String soggetti_detail_indirizzi_cittaItem_title(); 

  @Key("soggetti_detail_indirizzi_civicoItem_title")
  String soggetti_detail_indirizzi_civicoItem_title(); 

  @Key("soggetti_detail_indirizzi_complementoIndirizzoItem_title")
  String soggetti_detail_indirizzi_complementoIndirizzoItem_title(); 

  @Key("soggetti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage")
  String soggetti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage(); 

  @Key("soggetti_detail_indirizzi_comuneItem_title")
  String soggetti_detail_indirizzi_comuneItem_title(); 

  @Key("soggetti_detail_indirizzi_dataValidoDalItem_title")
  String soggetti_detail_indirizzi_dataValidoDalItem_title(); 

  @Key("soggetti_detail_indirizzi_dataValidoFinoAlItem_title")
  String soggetti_detail_indirizzi_dataValidoFinoAlItem_title(); 

  @Key("soggetti_detail_indirizzi_frazioneItem_title")
  String soggetti_detail_indirizzi_frazioneItem_title(); 

  @Key("soggetti_detail_indirizzi_indirizzoItem_title")
  String soggetti_detail_indirizzi_indirizzoItem_title(); 

  @Key("soggetti_detail_indirizzi_provinciaItem_title")
  String soggetti_detail_indirizzi_provinciaItem_title(); 

  @Key("soggetti_detail_indirizzi_statoItem_title")
  String soggetti_detail_indirizzi_statoItem_title(); 

  @Key("soggetti_detail_indirizzi_zonaItem_title")
  String soggetti_detail_indirizzi_zonaItem_title(); 

  @Key("soggetti_detail_istituzioneSection_title")
  String soggetti_detail_istituzioneSection_title(); 

  @Key("soggetti_detail_nascitaSection_title")
  String soggetti_detail_nascitaSection_title(); 

  @Key("soggetti_detail_new_title")
  String soggetti_detail_new_title(); 

  @Key("soggetti_detail_nomeItem_title")
  String soggetti_detail_nomeItem_title(); 

  @Key("soggetti_detail_partitaIvaItem_title")
  String soggetti_detail_partitaIvaItem_title(); 

  @Key("soggetti_detail_provNascitaIstituzioneItem_title")
  String soggetti_detail_provNascitaIstituzioneItem_title(); 

  @Key("soggetti_detail_sessoItem_title")
  String soggetti_detail_sessoItem_title(); 

  @Key("soggetti_detail_soggettiSection_title")
  String soggetti_detail_soggettiSection_title(); 

  @Key("soggetti_detail_sottotipoItem_title")
  String soggetti_detail_sottotipoItem_title(); 

  @Key("soggetti_detail_statoNascitaIstituzioneItem_title")
  String soggetti_detail_statoNascitaIstituzioneItem_title(); 

  @Key("soggetti_detail_tipoItem_title")
  String soggetti_detail_tipoItem_title(); 

  @Key("soggetti_detail_tipologiaItem_title")
  String soggetti_detail_tipologiaItem_title(); 

  @Key("soggetti_detail_titoloItem_title")
  String soggetti_detail_titoloItem_title(); 

  @Key("soggetti_detail_view_title")
  String soggetti_detail_view_title(String attribute0);

  @Key("soggetti_eliminazioneFisicaAsk_message")
  String soggetti_eliminazioneFisicaAsk_message(); 

  @Key("soggetti_esitoValidazione_KO_value")
  String soggetti_esitoValidazione_KO_value(); 

  @Key("soggetti_esitoValidazione_OK_value")
  String soggetti_esitoValidazione_OK_value(); 

  @Key("soggetti_flgAnn_1_value")
  String soggetti_flgAnn_1_value(); 

  @Key("soggetti_flgCertificato_1_value")
  String soggetti_flgCertificato_1_value(); 

  @Key("soggetti_flgDiSistema_1_value")
  String soggetti_flgDiSistema_1_value(); 

  @Key("soggetti_flgEmailPecPeo_PEC_value")
  String soggetti_flgEmailPecPeo_PEC_value(); 

  @Key("soggetti_flgEmailPecPeo_PEO_value")
  String soggetti_flgEmailPecPeo_PEO_value(); 

  @Key("soggetti_flgPersFisica_0_value")
  String soggetti_flgPersFisica_0_value(); 

  @Key("soggetti_flgPersFisica_1_value")
  String soggetti_flgPersFisica_1_value(); 

  @Key("soggetti_flgPersFisica_NULL_value")
  String soggetti_flgPersFisica_NULL_value(); 

  @Key("soggetti_flgValido_1_value")
  String soggetti_flgValido_1_value(); 


  //#SoggettiList
  @Key("soggetti_list_acronimoField_title")
  String soggetti_list_acronimoField_title(); 

  @Key("soggetti_list_altreDenominazioniField_title")
  String soggetti_list_altreDenominazioniField_title(); 

  @Key("soggetti_list_causaleCessazioneField_title")
  String soggetti_list_causaleCessazioneField_title(); 

  @Key("soggetti_list_cittaField_title")
  String soggetti_list_cittaField_title(); 

  @Key("soggetti_list_cittadinanzaField_title")
  String soggetti_list_cittadinanzaField_title(); 

  @Key("soggetti_list_codTipoSoggIntField_title")
  String soggetti_list_codTipoSoggIntField_title(); 

  @Key("soggetti_list_codiceAmmInIpaField_title")
  String soggetti_list_codiceAmmInIpaField_title(); 

  @Key("soggetti_list_codiceAooInIpaField_title")
  String soggetti_list_codiceAooInIpaField_title(); 

  @Key("soggetti_list_codiceFiscaleField_title")
  String soggetti_list_codiceFiscaleField_title(); 

  @Key("soggetti_list_codiceIpaField_title")
  String soggetti_list_codiceIpaField_title(); 

  @Key("soggetti_list_codiceOrigineField_title")
  String soggetti_list_codiceOrigineField_title(); 

  @Key("soggetti_list_codiceRapidoField_title")
  String soggetti_list_codiceRapidoField_title(); 

  @Key("soggetti_list_cognomeField_title")
  String soggetti_list_cognomeField_title(); 

  @Key("soggetti_list_comuneNascitaIstituzioneField_title")
  String soggetti_list_comuneNascitaIstituzioneField_title(); 

  @Key("soggetti_list_condizioneGiuridicaField_title")
  String soggetti_list_condizioneGiuridicaField_title(); 

  @Key("soggetti_list_dataCessazioneField_title")
  String soggetti_list_dataCessazioneField_title(); 

  @Key("soggetti_list_dataNascitaIstituzioneField_title")
  String soggetti_list_dataNascitaIstituzioneField_title(); 

  @Key("soggetti_list_denominazioneField_title")
  String soggetti_list_denominazioneField_title(); 

  @Key("soggetti_list_emailField_title")
  String soggetti_list_emailField_title(); 

  @Key("soggetti_list_emailPecField_title")
  String soggetti_list_emailPecField_title(); 
  
  @Key("soggetti_list_emailPeoField_title")
  String soggetti_list_emailPeoField_title(); 

  @Key("soggetti_list_estremiCertificazioneField_title")
  String soggetti_list_estremiCertificazioneField_title(); 

  @Key("soggetti_list_faxField_title")
  String soggetti_list_faxField_title(); 

  @Key("soggetti_list_flgAnnField_title")
  String soggetti_list_flgAnnField_title(); 

  @Key("soggetti_list_flgCertificatoField_title")
  String soggetti_list_flgCertificatoField_title(); 

  @Key("soggetti_list_flgDiSistemaField_title")
  String soggetti_list_flgDiSistemaField_title(); 

  @Key("soggetti_list_flgEmailPecPeoField_title")
  String soggetti_list_flgEmailPecPeoField_title(); 

  @Key("soggetti_list_flgInOrganigrammaField_title")
  String soggetti_list_flgInOrganigrammaField_title(); 

  @Key("soggetti_list_flgPersFisicaField_title")
  String soggetti_list_flgPersFisicaField_title(); 

  @Key("soggetti_list_flgValidoField_title")
  String soggetti_list_flgValidoField_title(); 

  @Key("soggetti_list_gestibSottoUoField_title")
  String soggetti_list_gestibSottoUoField_title(); 

  @Key("soggetti_list_idSoggettoField_title")
  String soggetti_list_idSoggettoField_title(); 

  @Key("soggetti_list_indirizzoCompletoField_title")
  String soggetti_list_indirizzoCompletoField_title(); 

  @Key("soggetti_list_indirizzoField_title")
  String soggetti_list_indirizzoField_title(); 

  @Key("soggetti_list_nomeField_title")
  String soggetti_list_nomeField_title(); 

  @Key("soggetti_list_partitaIvaField_title")
  String soggetti_list_partitaIvaField_title(); 

  @Key("soggetti_list_rubricaDiField_title")
  String soggetti_list_rubricaDiField_title(); 

  @Key("soggetti_list_scoreField_title")
  String soggetti_list_scoreField_title(); 

  @Key("soggetti_list_sottotipoField_title")
  String soggetti_list_sottotipoField_title(); 

  @Key("soggetti_list_statoNascitaIstituzioneField_title")
  String soggetti_list_statoNascitaIstituzioneField_title(); 

  @Key("soggetti_list_telField_title")
  String soggetti_list_telField_title(); 

  @Key("soggetti_list_tipoField_title")
  String soggetti_list_tipoField_title(); 

  @Key("soggetti_list_titoloField_title")
  String soggetti_list_titoloField_title(); 

  @Key("soggetti_list_tsInsField_title")
  String soggetti_list_tsInsField_title(); 

  @Key("soggetti_list_tsLastUpdField_title")
  String soggetti_list_tsLastUpdField_title(); 

  @Key("soggetti_list_uteInsField_title")
  String soggetti_list_uteInsField_title(); 

  @Key("soggetti_list_uteLastUpdField_title")
  String soggetti_list_uteLastUpdField_title(); 

  @Key("soggetti_list_vecchieDenominazioniField_title")
  String soggetti_list_vecchieDenominazioniField_title(); 

  @Key("soggetti_list_visibSottoUoField_title")
  String soggetti_list_visibSottoUoField_title(); 


  @Key("soggetti_lookupSoggettiPopup_title")
  String soggetti_lookupSoggettiPopup_title(); 

  @Key("soggetti_sesso_F_value")
  String soggetti_sesso_F_value(); 

  @Key("soggetti_sesso_M_value")
  String soggetti_sesso_M_value(); 

  @Key("soggetti_tipoIndirizzo_D_value")
  String soggetti_tipoIndirizzo_D_value(); 

  @Key("soggetti_tipoIndirizzo_RC_value")
  String soggetti_tipoIndirizzo_RC_value(); 

  @Key("soggetti_tipoIndirizzo_RS_value")
  String soggetti_tipoIndirizzo_RS_value(); 

  @Key("soggetti_tipoIndirizzo_SL_value")
  String soggetti_tipoIndirizzo_SL_value(); 

  @Key("soggetti_tipoIndirizzo_SO_value")
  String soggetti_tipoIndirizzo_SO_value(); 

  @Key("soggetti_tipo_AF_value")
  String soggetti_tipo_AF_value(); 

  @Key("soggetti_tipo_AG_value")
  String soggetti_tipo_AG_value(); 

  @Key("soggetti_tipo_APA_value")
  String soggetti_tipo_APA_value(); 

  @Key("soggetti_tipo_IAMM_value")
  String soggetti_tipo_IAMM_value(); 

  @Key("soggetti_tipo_PV_value")
  String soggetti_tipo_PV_value(); 

  @Key("soggetti_tipo_UOUOI_value")
  String soggetti_tipo_UOUOI_value(); 

  @Key("soggetti_tipo_UP_value")
  String soggetti_tipo_UP_value(); 
  
  @Key("soggetti_tipo_APA_PA_value")
  String soggetti_tipo_APA_PA_value(); 
  
  @Key("soggetti_tipo_APA_AOO_value")
  String soggetti_tipo_APA_AOO_value(); 
  
  @Key("soggetti_tipo_APA_UO_value")
  String soggetti_tipo_APA_UO_value(); 
  


  //###############################################################
  //#				STATISTICHE DOCUMENTI                         #
  //###############################################################

  @Key("statisticheDocumenti_window_title")
  String statisticheDocumenti_window_title(); 

  @Key("statisticheDocumenti_tipoReport_section_title")
  String statisticheDocumenti_tipoReport_section_title(); 

  @Key("statisticheDocumenti_filtri_section_title")
  String statisticheDocumenti_filtri_section_title(); 

  @Key("statisticheDocumenti_raggruppamento_section_title")
  String statisticheDocumenti_raggruppamento_section_title(); 


  @Key("statisticheDocumenti_periodoReport_section_title")
  String statisticheDocumenti_periodoReport_section_title(); 

  @Key("statisticheDocumenti_enteAooReport_section_title")
  String statisticheDocumenti_enteAooReport_section_title(); 

  @Key("statisticheDocumenti_uoReport_section_title")
  String statisticheDocumenti_uoReport_section_title(); 

  @Key("statisticheDocumenti_applicazioniEsterneReport_section_title")
  String statisticheDocumenti_applicazioniEsterneReport_section_title(); 

  @Key("statisticheDocumenti_utenteReport_section_title")
  String statisticheDocumenti_utenteReport_section_title(); 


  @Key("statistichedocumenti_combo_enteaoo_label")
  String statistichedocumenti_combo_enteaoo_label(); 

  @Key("statistichedocumenti_combo_utente_label")
  String statistichedocumenti_combo_utente_label(); 

  @Key("statistichedocumenti_combo_applicazioniEsterne_label")
  String statistichedocumenti_combo_applicazioniEsterne_label(); 

  @Key("statistichedocumenti_combo_tipoRegistrazione_label")
  String statistichedocumenti_combo_tipoRegistrazione_label(); 

  @Key("statistichedocumenti_combo_categoriaRegistrazione_label")
  String statistichedocumenti_combo_categoriaRegistrazione_label(); 

  @Key("statistichedocumenti_combo_supporto_label")
  String statistichedocumenti_combo_supporto_label(); 

  @Key("statistichedocumenti_combo_presenzaFile_label")
  String statistichedocumenti_combo_presenzaFile_label(); 

  @Key("statistichedocumenti_combo_mezzoTrasmissione_label")
  String statistichedocumenti_combo_mezzoTrasmissione_label(); 

  @Key("statistichedocumenti_combo_raggruppaPresenzaFile_label")
  String statistichedocumenti_combo_raggruppaPresenzaFile_label(); 

  @Key("statistichedocumenti_combo_livelliRiservatezza_label")
  String statistichedocumenti_combo_livelliRiservatezza_label(); 

  @Key("statistichedocumenti_combo_registroNumerazione_label")
  String statistichedocumenti_combo_registroNumerazione_label(); 

  @Key("statistichedocumenti_combo_raggruppaPeriodo_label")
  String statistichedocumenti_combo_raggruppaPeriodo_label(); 

  @Key("statistichedocumenti_combo_raggruppaUo_label")
  String statistichedocumenti_combo_raggruppaUo_label(); 

  @Key("statistichedocumenti_combo_enteaoo_descrizioneField")
  String statistichedocumenti_combo_enteaoo_descrizioneField(); 

  @Key("statistichedocumenti_combo_utenteReport_cognomeNomeField")
  String statistichedocumenti_combo_utenteReport_cognomeNomeField(); 

  @Key("statistichedocumenti_combo_utenteReport_usernameField")
  String statistichedocumenti_combo_utenteReport_usernameField(); 


  @Key("statistichedocumenti_check_applicazioniEsterne_label")
  String statistichedocumenti_check_applicazioniEsterne_label(); 

  @Key("statistichedocumenti_check_flgIncluseSottoUo_label")
  String statistichedocumenti_check_flgIncluseSottoUo_label(); 

  @Key("statistichedocumenti_check_utente_label")
  String statistichedocumenti_check_utente_label(); 

  @Key("statistichedocumenti_check_raggruppaPresenzaFile_label")
  String statistichedocumenti_check_raggruppaPresenzaFile_label(); 

  @Key("statistichedocumenti_check_raggruppaRegValideAnnullate_label")
  String statistichedocumenti_check_raggruppaRegValideAnnullate_label(); 
 

  //###############################################################
  //#				STATISTICHE COGITO                            #
  //###############################################################

  @Key("statisticheCogito_window_title")
  String statisticheCogito_window_title(); 
  
  @Key("statistichecogito_combo_utente_label")
  String statistichecogito_combo_utente_label(); 
  
  @Key("statistichecogito_combo_classificazione_suggerita_label")
  String statistichecogito_combo_classificazione_suggerita_label(); 
  
  @Key("statistichecogito_combo_classificazione_indiceField")
  String statistichecogito_combo_classificazione_indiceField(); 
 
  @Key("statistichecogito_combo_classificazione_descrizioneField")
  String statistichecogito_combo_classificazione_descrizioneField(); 
   
  @Key("statistichecogito_combo_classificazione_scelta_label")
  String statistichecogito_combo_classificazione_scelta_label(); 
 
  @Key("statistichecogito_combo_esito_label")
  String statistichecogito_combo_esito_label(); 
  
  @Key("statistichecogito_check_errore_label")
  String statistichecogito_check_errore_label(); 
  
  @Key("statistichecogito_check_esito_label")
  String statistichecogito_check_esito_label(); 
    
  @Key("statistichecogito_check_classificazione_label")
  String statistichecogito_check_classificazione_label(); 
  
  @Key("statistichecogito_check_registrazione_label")
  String statistichecogito_check_registrazione_label(); 
  
  
  // VisualizzaDatiStatisticheCogitoLogList
  @Key("datiStatisticheCogitoLog_list_nomeUDField_title")
  String datiStatisticheCogitoLog_list_nomeUDField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_codiceUOField_title")
  String datiStatisticheCogitoLog_list_codiceUOField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_nomeUOField_title")
  String datiStatisticheCogitoLog_list_nomeUOField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_usernameUtenteField_title")
  String datiStatisticheCogitoLog_list_usernameUtenteField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_denominazioneUtenteField_title")
  String datiStatisticheCogitoLog_list_denominazioneUtenteField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_nriLivelliClassificazioneField_title")
  String datiStatisticheCogitoLog_list_nriLivelliClassificazioneField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_denominazioneClassificazioneField_title")
  String datiStatisticheCogitoLog_list_denominazioneClassificazioneField_title(); 
  
  
  @Key("datiStatisticheCogitoLog_list_periodoField_title")
  String datiStatisticheCogitoLog_list_periodoField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_nrDocumentiField_title")
  String datiStatisticheCogitoLog_list_nrDocumentiField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_percField_title")
  String datiStatisticheCogitoLog_list_percField_title(); 
  
  @Key("datiStatisticheCogitoLog_list_percArrotondataField_title")
  String datiStatisticheCogitoLog_list_percArrotondataField_title(); 
  
  //###############################################################
  //#				STATISTICHE MAIL                             #
  //###############################################################
  
  @Key("statisticheMail_window_title")
  String statisticheMail_window_title(); 
  
  //###############################################################
  //#				STATISTICHE MAIL STORICIZZATE                 #
  //###############################################################
  
  @Key("statisticheMailStoricizzate_window_title")
  String statisticheMailStoricizzate_window_title();

  //###############################################################
  //#				STATO CARICAMENTO RUBRICHE                    #
  //###############################################################

  @Key("statoCaricamentoRows_detail_data_ins")
  String statoCaricamentoRows_detail_data_ins(); 

  @Key("statoCaricamentoRows_detail_data_ins_prompt")
  String statoCaricamentoRows_detail_data_ins_prompt(); 

  @Key("statoCaricamentoRows_detail_error_mess")
  String statoCaricamentoRows_detail_error_mess(); 

  @Key("statoCaricamentoRows_detail_error_mess_prompt")
  String statoCaricamentoRows_detail_error_mess_prompt(); 

  @Key("statoCaricamentoRows_detail_esito")
  String statoCaricamentoRows_detail_esito(); 

  @Key("statoCaricamentoRows_detail_tipo_contenuto")
  String statoCaricamentoRows_detail_tipo_contenuto(); 

  @Key("statoCaricamentoRows_detail_tipo_dato")
  String statoCaricamentoRows_detail_tipo_dato(); 

  @Key("statoCaricamentoRows_detail_try_num")
  String statoCaricamentoRows_detail_try_num(); 

  @Key("statoCaricamentoRows_detail_ultima_elab")
  String statoCaricamentoRows_detail_ultima_elab(); 

  @Key("statoCaricamentoRows_detail_valore")
  String statoCaricamentoRows_detail_valore(); 

  @Key("statoCaricamentoRubricheStati_daElaborare")
  String statoCaricamentoRubricheStati_daElaborare(); 

  @Key("statoCaricamentoRubricheStati_elaboratoConErrori")
  String statoCaricamentoRubricheStati_elaboratoConErrori(); 

  @Key("statoCaricamentoRubricheStati_elaboratoSenzaErrori")
  String statoCaricamentoRubricheStati_elaboratoSenzaErrori(); 

  @Key("statoCaricamentoRubricheStati_inElaborazione")
  String statoCaricamentoRubricheStati_inElaborazione(); 

  @Key("statoCaricamentoRubriche_clienti_dettaglio_section")
  String statoCaricamentoRubriche_clienti_dettaglio_section(); 

  @Key("statoCaricamentoRubriche_dettaglio_title")
  String statoCaricamentoRubriche_dettaglio_title(); 

  @Key("statoCaricamentoRubriche_layout_title")
  String statoCaricamentoRubriche_layout_title(); 

  @Key("statoCaricamentoRubriche_list_data_fine_elaborazione")
  String statoCaricamentoRubriche_list_data_fine_elaborazione(); 

  @Key("statoCaricamentoRubriche_list_data_fine_elaborazione_prompt")
  String statoCaricamentoRubriche_list_data_fine_elaborazione_prompt(); 

  @Key("statoCaricamentoRubriche_list_data_fine_nroRigheCaricate")
  String statoCaricamentoRubriche_list_data_fine_nroRigheCaricate(); 

  @Key("statoCaricamentoRubriche_list_data_fine_nroRigheCaricate_prompt")
  String statoCaricamentoRubriche_list_data_fine_nroRigheCaricate_prompt(); 

  @Key("statoCaricamentoRubriche_list_data_fine_nroRigheInErrore")
  String statoCaricamentoRubriche_list_data_fine_nroRigheInErrore(); 

  @Key("statoCaricamentoRubriche_list_data_fine_nroRigheInErrore_prompt")
  String statoCaricamentoRubriche_list_data_fine_nroRigheInErrore_prompt(); 

  @Key("statoCaricamentoRubriche_list_data_fine_nroRigheTotali")
  String statoCaricamentoRubriche_list_data_fine_nroRigheTotali(); 

  @Key("statoCaricamentoRubriche_list_data_fine_nroRigheTotali_prompt")
  String statoCaricamentoRubriche_list_data_fine_nroRigheTotali_prompt(); 

  @Key("statoCaricamentoRubriche_list_data_inizio_elaborazione")
  String statoCaricamentoRubriche_list_data_inizio_elaborazione(); 

  @Key("statoCaricamentoRubriche_list_data_inizio_elaborazione_prompt")
  String statoCaricamentoRubriche_list_data_inizio_elaborazione_prompt(); 

  @Key("statoCaricamentoRubriche_list_denominazione_societa")
  String statoCaricamentoRubriche_list_denominazione_societa(); 

  @Key("statoCaricamentoRubriche_list_id")
  String statoCaricamentoRubriche_list_id(); 

  @Key("statoCaricamentoRubriche_list_status")
  String statoCaricamentoRubriche_list_status(); 

  @Key("statoCaricamentoRubriche_list_tipo")
  String statoCaricamentoRubriche_list_tipo(); 

  @Key("statoCaricamentoRubriche_list_upload_date")
  String statoCaricamentoRubriche_list_upload_date(); 

  @Key("statoCaricamentoRubriche_list_upload_date_prompt")
  String statoCaricamentoRubriche_list_upload_date_prompt(); 



  //###############################################################
  //#				       STATO TRASMISSIONE                     #
  //###############################################################

  @Key("stato_trasmissione_mail_consegna")
  String stato_trasmissione_mail_consegna(); 

  @Key("stato_trasmissione_mail_error")
  String stato_trasmissione_mail_error(); 

  @Key("stato_trasmissione_mail_inprogress")
  String stato_trasmissione_mail_inprogress(); 

  @Key("stato_trasmissione_mail_title")
  String stato_trasmissione_mail_title(); 

  @Key("stato_trasmissione_mail_warning")
  String stato_trasmissione_mail_warning(); 



  //###############################################################
  //#				       TIPI DOCUMENTO                         #
  //###############################################################

  @Key("tipi_doc_annullamentoLogicoAsk_message")
  String tipi_doc_annullamentoLogicoAsk_message(); 

  @Key("tipi_doc_detail_codTipoDocItem_title")
  String tipi_doc_detail_codTipoDocItem_title(); 

  @Key("tipi_doc_detail_descrizioneTipoDocItem_title")
  String tipi_doc_detail_descrizioneTipoDocItem_title(); 

  @Key("tipi_doc_detail_edit_title")
  String tipi_doc_detail_edit_title(String attribute0);

  @Key("tipi_doc_detail_flgAnnItem_title")
  String tipi_doc_detail_flgAnnItem_title(); 

  @Key("tipi_doc_detail_idTipoDocItem_title")
  String tipi_doc_detail_idTipoDocItem_title(); 

  @Key("tipi_doc_detail_new_title")
  String tipi_doc_detail_new_title(); 

  @Key("tipi_doc_detail_nomeTipoDocItem_title")
  String tipi_doc_detail_nomeTipoDocItem_title(); 

  @Key("tipi_doc_detail_tipiDocSection_title")
  String tipi_doc_detail_tipiDocSection_title(); 

  @Key("tipi_doc_detail_view_title")
  String tipi_doc_detail_view_title(String attribute0);

  @Key("tipi_doc_eliminazioneFisicaAsk_message")
  String tipi_doc_eliminazioneFisicaAsk_message(); 

  @Key("tipi_doc_flgAnn_1_value")
  String tipi_doc_flgAnn_1_value(); 

  @Key("tipi_doc_flgDiSistema_1_value")
  String tipi_doc_flgDiSistema_1_value(); 

  @Key("tipi_doc_flgValido_1_value")
  String tipi_doc_flgValido_1_value(); 

  @Key("tipi_doc_list_codTipoDocField_title")
  String tipi_doc_list_codTipoDocField_title(); 

  @Key("tipi_doc_list_descrizioneTipoDocField_title")
  String tipi_doc_list_descrizioneTipoDocField_title(); 

  @Key("tipi_doc_list_flgAnnField_title")
  String tipi_doc_list_flgAnnField_title(); 

  @Key("tipi_doc_list_flgDiSistemaField_title")
  String tipi_doc_list_flgDiSistemaField_title(); 

  @Key("tipi_doc_list_flgValidoField_title")
  String tipi_doc_list_flgValidoField_title(); 

  @Key("tipi_doc_list_idTipoDocField_title")
  String tipi_doc_list_idTipoDocField_title(); 

  @Key("tipi_doc_list_nomeTipoDocField_title")
  String tipi_doc_list_nomeTipoDocField_title(); 

  @Key("tipi_doc_list_scoreField_title")
  String tipi_doc_list_scoreField_title(); 

  @Key("tipi_doc_list_tsInsField_title")
  String tipi_doc_list_tsInsField_title(); 

  @Key("tipi_doc_list_tsLastUpdField_title")
  String tipi_doc_list_tsLastUpdField_title(); 

  @Key("tipi_doc_list_uteInsField_title")
  String tipi_doc_list_uteInsField_title(); 

  @Key("tipi_doc_list_uteLastUpdField_title")
  String tipi_doc_list_uteLastUpdField_title(); 



  //#############################################################
  //#    		TIPOLOGIE FASCICOLI E ALTRI AGGREGATI  		    #
  //#############################################################

  @Key("tipo_fascicoli_aggr_title")
  String tipo_fascicoli_aggr_title(); 

  @Key("tipofascicoliaggr_new_title")
  String tipofascicoliaggr_new_title(); 

  @Key("tipofascicoliaggr_edit_title")
  String tipofascicoliaggr_edit_title(String attribute0);

  @Key("tipofascicoliaggr_view_title")
  String tipofascicoliaggr_view_title(String attribute0);

  @Key("tipofascicoliaggr_list_id")
  String tipofascicoliaggr_list_id(); 

  @Key("tipofascicoliaggr_list_nome")
  String tipofascicoliaggr_list_nome(); 

  @Key("tipofascicoliaggr_list_descrizione")
  String tipofascicoliaggr_list_descrizione(); 

  @Key("tipofascicoliaggr_list_id_gen")
  String tipofascicoliaggr_list_id_gen(); 

  @Key("tipofascicoliaggr_list_nome_gen")
  String tipofascicoliaggr_list_nome_gen(); 

  @Key("tipofascicoliaggr_list_flgScansionare")
  String tipofascicoliaggr_list_flgScansionare(); 

  @Key("tipofascicoliaggr_list_periodo_conserv")
  String tipofascicoliaggr_list_periodo_conserv(); 

  @Key("tipofascicoliaggr_list_annotazioni")
  String tipofascicoliaggr_list_annotazioni(); 

  @Key("tipofascicoliaggr_list_desc_appl")
  String tipofascicoliaggr_list_desc_appl(); 

  @Key("tipofascicoliaggr_list_flgAbVis")
  String tipofascicoliaggr_list_flgAbVis(); 

  @Key("tipofascicoliaggr_list_flgAbLav")
  String tipofascicoliaggr_list_flgAbLav(); 

  @Key("tipofascicoliaggr_list_flgAbAss")
  String tipofascicoliaggr_list_flgAbAss(); 

  @Key("tipofascicoliaggr_list_dtUltAgg")
  String tipofascicoliaggr_list_dtUltAgg(); 

  @Key("tipofascicoliaggr_list_uteUltAgg")
  String tipofascicoliaggr_list_uteUltAgg(); 

  @Key("tipofascicoliaggr_detail_nome")
  String tipofascicoliaggr_detail_nome(); 

  @Key("tipofascicoliaggr_detail_annotazioni")
  String tipofascicoliaggr_detail_annotazioni(); 

  @Key("tipofascicoliaggr_detail_idFolderTypeGen")
  String tipofascicoliaggr_detail_idFolderTypeGen(); 
  
  @Key("tipofascicoliaggr_detail_idProcessType")
  String tipofascicoliaggr_detail_idProcessType(); 

  @Key("tipofascicoliaggr_detail_template_nome")
  String tipofascicoliaggr_detail_template_nome(); 

  @Key("tipofascicoliaggr_detail_template_timbro")
  String tipofascicoliaggr_detail_template_timbro(); 

  @Key("tipofascicoliaggr_detail_template_codid")
  String tipofascicoliaggr_detail_template_codid(); 

  @Key("tipofascicoliaggr_detail_flgRichAbilXVisualizz")
  String tipofascicoliaggr_detail_flgRichAbilXVisualizz(); 

  @Key("tipofascicoliaggr_detail_flgRichAbilXGest")
  String tipofascicoliaggr_detail_flgRichAbilXGest(); 

  @Key("tipofascicoliaggr_detail_flgRichAbilXAssegn")
  String tipofascicoliaggr_detail_flgRichAbilXAssegn(); 

  @Key("tipofascicoliaggr_detail_flgConservPerm")
  String tipofascicoliaggr_detail_flgConservPerm(); 

  @Key("tipofascicoliaggr_detail_periodoConservInAnni")
  String tipofascicoliaggr_detail_periodoConservInAnni(); 

  @Key("tipofascicoliaggr_detail_livelliClassificazione")
  String tipofascicoliaggr_detail_livelliClassificazione(); 

  @Key("tipofascicoliaggr_detail_section_attr_custom")
  String tipofascicoliaggr_detail_section_attr_custom(); 

  @Key("tipofascicoliaggr_lookupTipoFascicoliAggregatiPopup_title")
  String tipofascicoliaggr_lookupTipoFascicoliAggregatiPopup_title(); 

  
  //#############################################################
  //#    				TIPOLOGIE DOCUMENTALI          		    #
  //#############################################################

  @Key("tipologie_documentali_title")
  String tipologie_documentali_title(); 

  @Key("tipologie_documentali_prompt")
  String tipologie_documentali_prompt(); 

  @Key("tipologieDocumentali_new_title")
  String tipologieDocumentali_new_title(); 

  @Key("tipologieDocumentali_edit_title")
  String tipologieDocumentali_edit_title(String attribute0);

  @Key("tipologieDocumentali_view_title")
  String tipologieDocumentali_view_title(String attribute0);

  @Key("tipologieDocumentali_idTipoDoc_list")
  String tipologieDocumentali_idTipoDoc_list(); 

  @Key("tipologieDocumentali_nome_list")
  String tipologieDocumentali_nome_list(); 

  @Key("tipologieDocumentali_descrizione_list")
  String tipologieDocumentali_descrizione_list(); 

  @Key("tipologieDocumentali_sottoTipoDi_list")
  String tipologieDocumentali_sottoTipoDi_list(); 

  @Key("tipologieDocumentali_periodoConserv_list")
  String tipologieDocumentali_periodoConserv_list(); 

  @Key("tipologieDocumentali_descrSuppConserv_list")
  String tipologieDocumentali_descrSuppConserv_list(); 

  @Key("tipologieDocumentali_specAccess_list")
  String tipologieDocumentali_specAccess_list(); 

  @Key("tipologieDocumentali_specRiprod_list")
  String tipologieDocumentali_specRiprod_list(); 

  @Key("tipologieDocumentali_annotazioni_list")
  String tipologieDocumentali_annotazioni_list(); 

  @Key("tipologieDocumentali_valido_list")
  String tipologieDocumentali_valido_list(); 

  @Key("tipologieDocumentali_creataIl_list")
  String tipologieDocumentali_creataIl_list(); 

  @Key("tipologieDocumentali_creataDa_list")
  String tipologieDocumentali_creataDa_list(); 

  @Key("tipologieDocumentali_ultAggiorn_list")
  String tipologieDocumentali_ultAggiorn_list(); 

  @Key("tipologieDocumentali_ultAggiornEffeDa_list")
  String tipologieDocumentali_ultAggiornEffeDa_list(); 
  
  @Key("tipologieDocumentali_flgIsAssociataIterWf_list")
  String tipologieDocumentali_flgIsAssociataIterWf_list();
  
  @Key("tipologieDocumentali_flgRichFirmaDigitale_list")
  String tipologieDocumentali_flgRichFirmaDigitale_list();
  
  @Key("tipologieDocumentali_flgIsAssociataIterWfAlt_list")
  String tipologieDocumentali_flgIsAssociataIterWfAlt_list(); 
  
  @Key("tipologieDocumentali_flgRichFirmaDigitaleValidaAlt_list")
  String tipologieDocumentali_flgRichFirmaDigitaleValidaAlt_list(); 
  
  @Key("tipologieDocumentali_flgRichFirmaDigitaleNonValidaAlt_list")
  String tipologieDocumentali_flgRichFirmaDigitaleNonValidaAlt_list(); 


  @Key("tipologieDocumentali_detail_section_attradd")
  String tipologieDocumentali_detail_section_attradd(); 

  @Key("tipologieDocumentali_detail_nome")
  String tipologieDocumentali_detail_nome(); 

  @Key("tipologieDocumentali_detail_descrizione")
  String tipologieDocumentali_detail_descrizione(); 

  @Key("tipologieDocumentali_detail_flgAllegato")
  String tipologieDocumentali_detail_flgAllegato(); 
  
  @Key("tipologieDocumentali_detail_flgRichFirmaDigitale")
  String tipologieDocumentali_detail_flgRichFirmaDigitale(); 
  
  @Key("tipologieDocumentali_detail_idDocTypeGen")
  String tipologieDocumentali_detail_idDocTypeGen(); 

  @Key("tipologieDocumentali_detail_idProcessType")
  String tipologieDocumentali_detail_idProcessType();

  @Key("tipologieDocumentali_detail_flgConservPermIn")
  String tipologieDocumentali_detail_flgConservPermIn(); 

  @Key("tipologieDocumentali_detail_periodoConservIn")
  String tipologieDocumentali_detail_periodoConservIn(); 

  @Key("tipologieDocumentali_detail_flgRichAbilVis")
  String tipologieDocumentali_detail_flgRichAbilVis(); 

  @Key("tipologieDocumentali_detail_flgRichAbilXGestIn")
  String tipologieDocumentali_detail_flgRichAbilXGestIn(); 

  @Key("tipologieDocumentali_detail_flgRichAbilXAssegnIn")
  String tipologieDocumentali_detail_flgRichAbilXAssegnIn(); 

  @Key("tipologieDocumentali_detail_flgAbilFirma")
  String tipologieDocumentali_detail_flgAbilFirma(); 

  @Key("tipologieDocumentali_detail_section_attr_custom")
  String tipologieDocumentali_detail_section_attr_custom(); 
  
  @Key("tipologieDocumentali_detail_section_template")
  String tipologieDocumentali_detail_section_template();

  @Key("tipologieDocumentali_detail_templateTimbroUD")
  String tipologieDocumentali_detail_templateTimbroUD ();

  @Key("tipologieDocumentali_detail_templateNome")
  String tipologieDocumentali_detail_templateNome();
  
  @Key("tipologieDocumentali_detail_flgTipoProv")
  String tipologieDocumentali_detail_flgTipoProv();
  
  
  @Key("tipologieDocumentali_lookupTipologieDocumentaliPopup_title")
  String tipologieDocumentali_lookupTipologieDocumentaliPopup_title();
  

  //#############################################################
  //#                       TITOLARIO                           #
  //#############################################################

  @Key("titolario_list_descrizioneField_title")
  String titolario_list_descrizioneField_title(); 

  @Key("titolario_list_folderUpButton_prompt")
  String titolario_list_folderUpButton_prompt(); 

  @Key("titolario_list_iconaFolderButton_prompt")
  String titolario_list_iconaFolderButton_prompt(); 

  @Key("titolario_list_indiceField_title")
  String titolario_list_indiceField_title(); 

  @Key("titolario_list_paroleChiaveField_title")
  String titolario_list_paroleChiaveField_title(); 

  @Key("titolario_list_scoreField_title")
  String titolario_list_scoreField_title(); 

  @Key("titolario_list_tsValidaDalField_title")
  String titolario_list_tsValidaDalField_title(); 

  @Key("titolario_list_tsValidaFinoAlField_title")
  String titolario_list_tsValidaFinoAlField_title(); 

  @Key("titolario_list_periodoConservAnni")
  String titolario_list_periodoConservAnni(); 

  @Key("titolario_list_flgAbilATuttiField_title")
  String titolario_list_flgAbilATuttiField_title(); 

  @Key("titolario_list_flgTipoNumerazione")
  String titolario_list_flgTipoNumerazione(); 


  @Key("titolario_detail_livello")
  String titolario_detail_livello(); 

  @Key("titolario_detail_descrizione")
  String titolario_detail_descrizione(); 

  @Key("titolario_detail_paroleChiave")
  String titolario_detail_paroleChiave(); 

  @Key("titolario_detail_tsValidaDal")
  String titolario_detail_tsValidaDal(); 

  @Key("titolario_detail_tsValidaFinoAl")
  String titolario_detail_tsValidaFinoAl(); 

  @Key("titolario_detail_flgConservPermIn")
  String titolario_detail_flgConservPermIn(); 

  @Key("titolario_detail_periodoConservIn")
  String titolario_detail_periodoConservIn(); 

  @Key("titolario_detail_flgNoRichAbil")
  String titolario_detail_flgNoRichAbil(); 

  @Key("titolario_detail_flgNumContinua")
  String titolario_detail_flgNumContinua(); 
  
  
  @Key("titolario_list_descrizioneEstesaField_title")
  String titolario_list_descrizioneEstesaField_title(); 

  
  @Key("titolario_lookupClassifichePopup_title")
  String titolario_lookupClassifichePopup_title(); 


  //#############################################################
  //#                       TOPOGRAFICO                         #
  //#############################################################

  @Key("topografico_detail_codiceRapidoItem_title")
  String topografico_detail_codiceRapidoItem_title(); 

  @Key("topografico_detail_descrizioneItem_title")
  String topografico_detail_descrizioneItem_title(); 

  @Key("topografico_detail_edit_title")
  String topografico_detail_edit_title(String attribute0);

  @Key("topografico_detail_new_title")
  String topografico_detail_new_title(); 

  @Key("topografico_detail_nomeItem_title")
  String topografico_detail_nomeItem_title(); 

  @Key("topografico_detail_noteItem_title")
  String topografico_detail_noteItem_title(); 

  @Key("topografico_detail_view_title")
  String topografico_detail_view_title(String attribute0);

  @Key("topografico_flgCreatodame_0_value")
  String topografico_flgCreatodame_0_value(); 

  @Key("topografico_flgCreatodame_1_value")
  String topografico_flgCreatodame_1_value(); 

  @Key("topografico_flgValido_0_value")
  String topografico_flgValido_0_value(); 

  @Key("topografico_flgValido_1_value")
  String topografico_flgValido_1_value(); 


  //#TopograficoList
  @Key("topografico_list_codiceOrigineField_title")
  String topografico_list_codiceOrigineField_title(); 

  @Key("topografico_list_codiceRapidoField_title")
  String topografico_list_codiceRapidoField_title(); 

  @Key("topografico_list_dataInsField_title")
  String topografico_list_dataInsField_title(); 

  @Key("topografico_list_dataUltModField_title")
  String topografico_list_dataUltModField_title(); 

  @Key("topografico_list_descUtenteInsField_title")
  String topografico_list_descUtenteInsField_title(); 

  @Key("topografico_list_descUtenteUltModField_title")
  String topografico_list_descUtenteUltModField_title(); 

  @Key("topografico_list_descrizioneField_title")
  String topografico_list_descrizioneField_title(); 

  @Key("topografico_list_flgCreatodameField_title")
  String topografico_list_flgCreatodameField_title(); 

  @Key("topografico_list_flgValidoField_title")
  String topografico_list_flgValidoField_title(); 

  @Key("topografico_list_idTopograficoField_title")
  String topografico_list_idTopograficoField_title(); 

  @Key("topografico_list_nomeField_title")
  String topografico_list_nomeField_title(); 

  @Key("topografico_list_noteField_title")
  String topografico_list_noteField_title(); 

  @Key("topografico_list_recProtettoField_title")
  String topografico_list_recProtettoField_title(); 


  @Key("topografico_lookupTopograficoPopup_title")
  String topografico_lookupTopograficoPopup_title(); 

  @Key("topografico_recProtetto_0_value")
  String topografico_recProtetto_0_value(); 

  @Key("topografico_recProtetto_1_value")
  String topografico_recProtetto_1_value(); 


  //#############################################################
  //#                       LOG OPERAZIONI                      #
  //#############################################################
  
  // Menu'
  @Key("menu_log_operazioni_title")
  String menu_log_operazioni_title(); 

  @Key("menu_log_operazioni_prompt")
  String menu_log_operazioni_prompt(); 
  
  // Filer
  @Key("log_operazioni_filter_tipoOperazione")
  String log_operazioni_filter_tipoOperazione(); 
  
  @Key("log_operazioni_filter_esitoOperazione")
  String log_operazioni_filter_esitoOperazione(); 
  
  @Key("log_operazioni_filter_tsOperazione")
  String log_operazioni_filter_tsOperazione(); 
  
  
  @Key("log_operazioni_filter_operazioneEffettuataDa")
  String log_operazioni_filter_operazioneEffettuataDa(); 
  
  // LogOperazioniLayout
  @Key("log_operazioni_detail_new_title")
  String log_operazioni_detail_new_title(); 
  
  @Key("log_operazioni_detail_edit_title")
  String log_operazioni_detail_edit_title(String attribute0);
  
  @Key("log_operazioni_detail_view_title")
  String log_operazioni_detail_view_title(String attribute0);
  
  // LogOperazioniList
  @Key("log_operazioni_list_tsOperazioneField_title")
  String log_operazioni_list_tsOperazioneField_title(); 
  
  @Key("log_operazioni_list_tipoOperazioneField_title")
  String log_operazioni_list_tipoOperazioneField_title(); 
  
  @Key("log_operazioni_list_descUtenteOperazioneField_title")
  String log_operazioni_list_descUtenteOperazioneField_title(); 
  
  @Key("log_operazioni_list_descUtenteDelegatoOperazioneField_title")
  String log_operazioni_list_descUtenteDelegatoOperazioneField_title(); 
  
  @Key("log_operazioni_list_dettagliOperazioneField_title")
  String log_operazioni_list_dettagliOperazioneField_title(); 
  
  @Key("log_operazioni_list_esitoOperazioneField_title")
  String log_operazioni_list_esitoOperazioneField_title(); 
  
  @Key("log_operazioni_list_viewOperazioniButton_prompt")
  String log_operazioni_list_viewOperazioniButton_prompt(); 
  
  // LogOperazioniDetail
  
  
  //#############################################################
  //#    				TOPONOMASTICA AURIGA         		    #
  //#############################################################

  @Key("trova_vie_list_idToponimo")
  String trova_vie_list_idToponimo(); 

  @Key("trova_vie_list_descrNomeToponimo")
  String trova_vie_list_descrNomeToponimo(); 

  @Key("trova_vie_list_tipoToponimo")
  String trova_vie_list_tipoToponimo(); 

  @Key("trova_vie_list_codiceViarioToponimo")
  String trova_vie_list_codiceViarioToponimo(); 

  @Key("trova_vie_list_cap")
  String trova_vie_list_cap(); 

  @Key("trova_vie_list_zona")
  String trova_vie_list_zona(); 


  @Key("trova_civici_list_idCivico")
  String trova_civici_list_idCivico(); 

  @Key("trova_civici_list_civico")
  String trova_civici_list_civico(); 

  @Key("trova_civici_list_nrCivico")
  String trova_civici_list_nrCivico(); 

  @Key("trova_civici_list_esponenteBarrato")
  String trova_civici_list_esponenteBarrato(); 

  @Key("trova_civici_list_cap")
  String trova_civici_list_cap(); 

  @Key("trova_civici_list_zona")
  String trova_civici_list_zona(); 



  //#############################################################
  //#    				VERSIONI FILE         		            #
  //#############################################################

  @Key("versioni_file_list_numero")
  String versioni_file_list_numero(); 

  @Key("versioni_file_list_nome")
  String versioni_file_list_nome(); 

  @Key("versioni_file_list_iconaFirmata")
  String versioni_file_list_iconaFirmata(); 

  @Key("versioni_file_list_acq_scanner")
  String versioni_file_list_acq_scanner(); 

  @Key("versioni_file_list_creataDa")
  String versioni_file_list_creataDa(); 

  @Key("versioni_file_list_creataIl")
  String versioni_file_list_creataIl(); 

  @Key("versioni_file_list_modDa")
  String versioni_file_list_modDa(); 

  @Key("versioni_file_list_modIl")
  String versioni_file_list_modIl(); 

  @Key("versioni_file_list_dim")
  String versioni_file_list_dim(); 



  //#############################################################
  //#                       VOCABOLARIO                         #
  //#############################################################

  @Key("vocabolario_new_title")
  String vocabolario_new_title(); 

  @Key("vocabolario_edit_title")
  String vocabolario_edit_title(String attribute0);

  @Key("vocabolario_view_title")
  String vocabolario_view_title(String attribute0);

  @Key("vocabolario_valore")
  String vocabolario_valore(); 

  @Key("vocabolario_codiceValore")
  String vocabolario_codiceValore(); 

  @Key("vocabolario_dictionaryEntry")
  String vocabolario_dictionaryEntry(); 

  @Key("vocabolario_dtInizioValidita")
  String vocabolario_dtInizioValidita(); 

  @Key("vocabolario_dtFineValidita")
  String vocabolario_dtFineValidita(); 

  @Key("vocabolario_titlePeriodoValidita")
  String vocabolario_titlePeriodoValidita(); 

  @Key("vocabolario_flgValiditaValore")
  String vocabolario_flgValiditaValore(); 

  @Key("vocabolario_flgValoreRiservatoAlSistema")
  String vocabolario_flgValoreRiservatoAlSistema(); 

  @Key("vocabolario_significatoValore")
  String vocabolario_significatoValore(); 

  @Key("vocabolario_valueGenVincolo")
  String vocabolario_valueGenVincolo(); 

  @Key("vocabolario_vocabolarioDi")
  String vocabolario_vocabolarioDi(); 

  @Key("vocabolario_flgVisibSottoUo")
  String vocabolario_flgVisibSottoUo(); 

  @Key("vocabolario_flgGestSottoUo")
  String vocabolario_flgGestSottoUo(); 



  //#############################################################
  //#                   SCADENZE CONFIGURATE                    #
  //#############################################################

  @Key("scadenze_configurate_descrizione")
  String scadenze_configurate_descrizione(); 

  @Key("scadenze_configurate_Durata")
  String scadenze_configurate_Durata(); 

  @Key("scadenze_configurate_Tipo")
  String scadenze_configurate_Tipo(); 

  @Key("scadenze_configurate_Valida_dal")
  String scadenze_configurate_Valida_dal(); 

  @Key("scadenze_configurate_Valida_fino_al")
  String scadenze_configurate_Valida_fino_al(); 



  //#############################
  //#		     PDV		    #
  //#############################

  @Key("menu_monitoraggioPdV_title")
  String menu_monitoraggioPdV_title(); 

  @Key("menu_monitoraggioPdV_prompt")
  String menu_monitoraggioPdV_prompt(); 


  @Key("monitoraggioPdV_new_title")
  String monitoraggioPdV_new_title(); 

  @Key("monitoraggioPdV_edit_title")
  String monitoraggioPdV_edit_title(String attribute0);

  @Key("monitoraggioPdV_view_title")
  String monitoraggioPdV_view_title(String attribute0);


  @Key("detail_errori_codiceItem_title")
  String detail_errori_codiceItem_title(); 

  @Key("detail_errori_messaggioItem_title")
  String detail_errori_messaggioItem_title(); 


  //# MonitoraggioPdVFilter
  @Key("monitoraggioPdV_filter_idPdV")
  String monitoraggioPdV_filter_idPdV(); 

  @Key("monitoraggioPdV_filter_etichetta")
  String monitoraggioPdV_filter_etichetta(); 

  @Key("monitoraggioPdV_filter_stato")
  String monitoraggioPdV_filter_stato(); 

  @Key("monitoraggioPdV_filter_tSUltimoAggStato")
  String monitoraggioPdV_filter_tSUltimoAggStato(); 

  @Key("monitoraggioPdV_filter_dataGenerazionePdV")
  String monitoraggioPdV_filter_dataGenerazionePdV(); 

  @Key("monitoraggioPdV_filter_codProcessoBancaProd")
  String monitoraggioPdV_filter_codProcessoBancaProd(); 

  @Key("monitoraggioPdV_filter_nroDocPdV")
  String monitoraggioPdV_filter_nroDocPdV(); 

  @Key("monitoraggioPdV_filter_volDocPdV")
  String monitoraggioPdV_filter_volDocPdV(); 

  @Key("monitoraggioPdV_filter_codiciErrWarnTrasm")
  String monitoraggioPdV_filter_codiciErrWarnTrasm(); 

  @Key("monitoraggioPdV_filter_msgErrWarnTrasm")
  String monitoraggioPdV_filter_msgErrWarnTrasm(); 

  @Key("monitoraggioPdV_filter_codiciErrWarnRecRdV")
  String monitoraggioPdV_filter_codiciErrWarnRecRdV(); 

  @Key("monitoraggioPdV_filter_msgErrWarnRecRdV")
  String monitoraggioPdV_filter_msgErrWarnRecRdV(); 

  @Key("monitoraggioPdV_filter_tempoRicezRicTrasm")
  String monitoraggioPdV_filter_tempoRicezRicTrasm(); 

  @Key("monitoraggioPdV_filter_tempoSenzaRicTrasm")
  String monitoraggioPdV_filter_tempoSenzaRicTrasm(); 

  @Key("monitoraggioPdV_filter_tempoRicezioneRdV")
  String monitoraggioPdV_filter_tempoRicezioneRdV(); 

  @Key("monitoraggioPdV_filter_tempoSenzaRdV")
  String monitoraggioPdV_filter_tempoSenzaRdV(); 


  //# DocumentiPdVFilter
  @Key("documentiPdV_filter_idItemInviatoCons")
  String documentiPdV_filter_idItemInviatoCons(); 

  @Key("documentiPdV_filter_esitoInvioCons")
  String documentiPdV_filter_esitoInvioCons(); 

  @Key("documentiPdV_filter_codiciErrWarn")
  String documentiPdV_filter_codiciErrWarn(); 

  @Key("documentiPdV_filter_msgErrWarn")
  String documentiPdV_filter_msgErrWarn(); 

  @Key("documentiPdV_filter_idTipiItem")
  String documentiPdV_filter_idTipiItem(); 

  @Key("documentiPdV_filter_idItemConservatore")
  String documentiPdV_filter_idItemConservatore(); 


  //# MonitoraggioPdVList
  @Key("monitoraggioPdV_list_idPdVField_title")
  String monitoraggioPdV_list_idPdVField_title(); 

  @Key("monitoraggioPdV_list_etichettaField_title")
  String monitoraggioPdV_list_etichettaField_title(); 

  @Key("monitoraggioPdV_list_statoField_title")
  String monitoraggioPdV_list_statoField_title(); 

  @Key("monitoraggioPdV_list_dataUltimoAggStatoField_title")
  String monitoraggioPdV_list_dataUltimoAggStatoField_title(); 

  @Key("monitoraggioPdV_list_dataGenerazionePdVField_title")
  String monitoraggioPdV_list_dataGenerazionePdVField_title(); 

  @Key("monitoraggioPdV_list_nomeProcessoBancaProdField_title")
  String monitoraggioPdV_list_nomeProcessoBancaProdField_title(); 

  @Key("monitoraggioPdV_list_numDocumentiPdVField_title")
  String monitoraggioPdV_list_numDocumentiPdVField_title(); 

  @Key("monitoraggioPdV_list_dimensioneField_title")
  String monitoraggioPdV_list_dimensioneField_title(); 

  @Key("monitoraggioPdV_list_codErroreTrasmissionePdVField_title")
  String monitoraggioPdV_list_codErroreTrasmissionePdVField_title(); 

  @Key("monitoraggioPdV_list_messaggioErroreTrasmissionePdVField_title")
  String monitoraggioPdV_list_messaggioErroreTrasmissionePdVField_title(); 

  @Key("monitoraggioPdV_list_codErroreRecuperoRdVField_title")
  String monitoraggioPdV_list_codErroreRecuperoRdVField_title(); 

  @Key("monitoraggioPdV_list_messaggioErroreRecuperoRdVField_title")
  String monitoraggioPdV_list_messaggioErroreRecuperoRdVField_title(); 

  @Key("monitoraggioPdV_list_tempoRitornoRicevutaAccRifTrasmField_title")
  String monitoraggioPdV_list_tempoRitornoRicevutaAccRifTrasmField_title(); 

  @Key("monitoraggioPdV_list_tempoRitornoRdVField_title")
  String monitoraggioPdV_list_tempoRitornoRdVField_title(); 

  @Key("monitoraggioPdV_list_downloadFileButtonField_title")
  String monitoraggioPdV_list_downloadFileButtonField_title(); 

  @Key("monitoraggioPdV_list_downloadFileIndiceXmlMenuItem_title")
  String monitoraggioPdV_list_downloadFileIndiceXmlMenuItem_title(); 

  @Key("monitoraggioPdV_list_downloadFileInfPdVMenuItem_title")
  String monitoraggioPdV_list_downloadFileInfPdVMenuItem_title(); 

  @Key("monitoraggioPdV_list_downloadFileRicevutaTrasmMenuItem_title")
  String monitoraggioPdV_list_downloadFileRicevutaTrasmMenuItem_title(); 

  @Key("monitoraggioPdV_list_downloadFileRdVMenuItem_title")
  String monitoraggioPdV_list_downloadFileRdVMenuItem_title(); 

  @Key("monitoraggioPdV_list_downloadFileSbustatoRdVMenuItem_title")
  String monitoraggioPdV_list_downloadFileSbustatoRdVMenuItem_title(); 

  @Key("monitoraggioPdV_list_documentiPdVButtonField_title")
  String monitoraggioPdV_list_documentiPdVButtonField_title(String attribute0);


  //# DocumentiPdVList
  @Key("documentiPdV_list_idPdVField_title")
  String documentiPdV_list_idPdVField_title(); 

  @Key("documentiPdV_list_idDocOriginaleField_title")
  String documentiPdV_list_idDocOriginaleField_title(); 

  @Key("documentiPdV_list_esitoField_title")
  String documentiPdV_list_esitoField_title(); 

  @Key("documentiPdV_list_codiceErroreField_title")
  String documentiPdV_list_codiceErroreField_title(); 

  @Key("documentiPdV_list_messaggioErroreField_title")
  String documentiPdV_list_messaggioErroreField_title(); 

  @Key("documentiPdV_list_idTipologiaDocField_title")
  String documentiPdV_list_idTipologiaDocField_title(); 

  @Key("documentiPdV_list_nomeTipologiaDocField_title")
  String documentiPdV_list_nomeTipologiaDocField_title(); 

  @Key("documentiPdV_list_idDocSdCField_title")
  String documentiPdV_list_idDocSdCField_title(); 



  //############################################
  //#	MONITORAGGIO OPERAZIONI BATCH		   #
  //############################################
  
  @Key("menu_monitoraggio_operazioni_batch_title")
  String menu_monitoraggio_operazioni_batch_title(); 

  @Key("menu_monitoraggio_operazioni_batch_prompt")
  String menu_monitoraggio_operazioni_batch_prompt(); 
  
  
  // MonitoraggioOperazioniBatchLayout
  
  @Key("monitoraggio_operazioni_batch_detail_new_title")
  String monitoraggio_operazioni_batch_detail_new_title(); 
  
  @Key("monitoraggio_operazioni_batch_detail_edit_title")
  String monitoraggio_operazioni_batch_detail_edit_title(String attribute0);
  
  @Key("monitoraggio_operazioni_batch_detail_view_title")
  String monitoraggio_operazioni_batch_detail_view_title(String attribute0);
  
  @Key("monitoraggio_operazioni_batch_mettiDaRiprocessareMultiButton_title")
  String monitoraggio_operazioni_batch_mettiDaRiprocessareMultiButton_title(); 
  
  @Key("monitoraggio_operazioni_batch_listaRecSelezionati_vuota_error")
  String monitoraggio_operazioni_batch_listaRecSelezionati_vuota_error(); 
  
  @Key("monitoraggio_operazioni_batch_metti_da_riprocessare_successo")
  String monitoraggio_operazioni_batch_metti_da_riprocessare_successo(); 
  
  @Key("monitoraggio_operazioni_batch_metti_da_riprocessare_errore_parziale")
  String monitoraggio_operazioni_batch_metti_da_riprocessare_errore_parziale(); 
  
  @Key("monitoraggio_operazioni_batch_metti_da_riprocessare_errore_totale")
  String monitoraggio_operazioni_batch_metti_da_riprocessare_errore_totale(); 
  
  @Key("monitoraggio_operazioni_batch_metti_da_riprocessare_errore")
  String monitoraggio_operazioni_batch_metti_da_riprocessare_errore(); 
  
  @Key("monitoraggio_operazioni_batch_togliDaRiprocessareMultiButton_title")
  String monitoraggio_operazioni_batch_togliDaRiprocessareMultiButton_title(); 
  
  @Key("monitoraggio_operazioni_batch_togli_da_riprocessare_successo")
  String monitoraggio_operazioni_batch_togli_da_riprocessare_successo(); 
  
  @Key("monitoraggio_operazioni_batch_togli_da_riprocessare_errore_parziale")
  String monitoraggio_operazioni_batch_togli_da_riprocessare_errore_parziale(); 
  
  @Key("monitoraggio_operazioni_batch_togli_da_riprocessare_errore_totale")
  String monitoraggio_operazioni_batch_togli_da_riprocessare_errore_totale(); 
  
  @Key("monitoraggio_operazioni_batch_togli_da_riprocessare_errore")
  String monitoraggio_operazioni_batch_togli_da_riprocessare_errore(); 
  
  
  
  // filtri MonitoraggioOperazioniBatchList
  @Key("monitoraggio_operazioni_batch_list_filter_tipoOperazione")
  String monitoraggio_operazioni_batch_list_filter_tipoOperazione(); 
  
  @Key("monitoraggio_operazioni_batch_list_filter_tipoOggettiDaProcessare")
  String monitoraggio_operazioni_batch_list_filter_tipoOggettiDaProcessare(); 
 
  @Key("monitoraggio_operazioni_batch_list_filter_dataRichiesta")
  String monitoraggio_operazioni_batch_list_filter_dataRichiesta(); 

  @Key("monitoraggio_operazioni_batch_list_filter_dataSchedulazione")
  String monitoraggio_operazioni_batch_list_filter_dataSchedulazione(); 
  
  @Key("monitoraggio_operazioni_batch_list_filter_statoRichiesta")
  String monitoraggio_operazioni_batch_list_filter_statoRichiesta(); 
  
  @Key("monitoraggio_operazioni_batch_list_filter_motivoRichiesta")
  String monitoraggio_operazioni_batch_list_filter_motivoRichiesta(); 
  
  @Key("monitoraggio_operazioni_batch_list_filter_utenteRichiestaSottomissione")
  String monitoraggio_operazioni_batch_list_filter_utenteRichiestaSottomissione(); 
  
  @Key("monitoraggio_operazioni_batch_list_filter_tipoEventoScatenante")
  String monitoraggio_operazioni_batch_list_filter_tipoEventoScatenante(); 
  
  @Key("monitoraggio_operazioni_batch_list_filter_eventoScatenanteSuTipoOggetto")
  String monitoraggio_operazioni_batch_list_filter_eventoScatenanteSuTipoOggetto(); 
  
  @Key("monitoraggio_operazioni_batch_list_filter_fineUltimaElaborazione")
  String monitoraggio_operazioni_batch_list_filter_fineUltimaElaborazione(); 
  
  // MonitoraggioOperazioniBatchList
  
  @Key("monitoraggio_operazioni_batch_list_idRichiestaField_title")
  String monitoraggio_operazioni_batch_list_idRichiestaField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_tipoOperazioneField_title")
  String monitoraggio_operazioni_batch_list_tipoOperazioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_tipoOggettiDaProcessareField_title")
  String monitoraggio_operazioni_batch_list_tipoOggettiDaProcessareField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_dataRichiestaField_title")
  String monitoraggio_operazioni_batch_list_dataRichiestaField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_dataSchedulazioneField_title")
  String monitoraggio_operazioni_batch_list_dataSchedulazioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_motivoRichiestaField_title")
  String monitoraggio_operazioni_batch_list_motivoRichiestaField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_utenteRichiestaSottomissioneField_title")
  String monitoraggio_operazioni_batch_list_utenteRichiestaSottomissioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_statoRichiestaField_title")
  String monitoraggio_operazioni_batch_list_statoRichiestaField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_livelloPrioritaRichiestaField_title")
  String monitoraggio_operazioni_batch_list_livelloPrioritaRichiestaField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_estremiOperazioneDerivanteRichiestaField_title")
  String monitoraggio_operazioni_batch_list_estremiOperazioneDerivanteRichiestaField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_dettagliOperazioneRichiestaField_title")
  String monitoraggio_operazioni_batch_list_dettagliOperazioneRichiestaField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_numeroElaborazioniField_title")
  String monitoraggio_operazioni_batch_list_numeroElaborazioniField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_inizioPrimaElaborazioneField_title")
  String monitoraggio_operazioni_batch_list_inizioPrimaElaborazioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_inizioUltimaElaborazioneField_title")
  String monitoraggio_operazioni_batch_list_inizioUltimaElaborazioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_fineUltimaElaborazioneField_title")
  String monitoraggio_operazioni_batch_list_fineUltimaElaborazioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_dataCompletataElaborazioneField_title")
  String monitoraggio_operazioni_batch_list_dataCompletataElaborazioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_noteField_title")
  String monitoraggio_operazioni_batch_list_noteField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_tipoEventoScatenanteField_title")
  String monitoraggio_operazioni_batch_list_tipoEventoScatenanteField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_eventoScatenanteSuTipoOggettoField_title")
  String monitoraggio_operazioni_batch_list_eventoScatenanteSuTipoOggettoField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_estremiOggettoSuOperazioneField_title")
  String monitoraggio_operazioni_batch_list_estremiOggettoSuOperazioneField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_numeroOggettiDaElaborareField_title")
  String monitoraggio_operazioni_batch_list_numeroOggettiDaElaborareField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_numeroOggettiElaboratiConSuccessoField_title")
  String monitoraggio_operazioni_batch_list_numeroOggettiElaboratiConSuccessoField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_numeroOggettiElaboratiConErroreField_title")
  String monitoraggio_operazioni_batch_list_numeroOggettiElaboratiConErroreField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_flgValidoField_title")
  String monitoraggio_operazioni_batch_list_flgValidoField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_flgCreatodameField_title")
  String monitoraggio_operazioni_batch_list_flgCreatodameField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_recProtettoField_title")
  String monitoraggio_operazioni_batch_list_recProtettoField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_flgDettagliField_title")
  String monitoraggio_operazioni_batch_list_flgDettagliField_title(); 
  
  @Key("monitoraggio_operazioni_batch_flgValido_1_value")
  String monitoraggio_operazioni_batch_flgValido_1_value(); 
  
  @Key("monitoraggio_operazioni_batch_flgCreatodame_1_value")
  String monitoraggio_operazioni_batch_flgCreatodame_1_value(); 
  
  @Key("monitoraggio_operazioni_batch_recProtetto_1_value")
  String monitoraggio_operazioni_batch_recProtetto_1_value(); 
  
  @Key("monitoraggio_operazioni_batch_flgDettagli_1_value")
  String monitoraggio_operazioni_batch_flgDettagli_1_value(); 
  
  @Key("monitoraggio_operazioni_batch_list_listaOggettiElaboratiConSuccessoButtonField_title")
  String monitoraggio_operazioni_batch_list_listaOggettiElaboratiConSuccessoButtonField_title(); 
  
  @Key("monitoraggio_operazioni_batch_list_listaOggettiElaboratiConErroreButtonField_title")
  String monitoraggio_operazioni_batch_list_listaOggettiElaboratiConErroreButtonField_title(); 
  
    
  // OggettiElaboratiList

  @Key("oggetti_elaborati_list_title")
  String oggetti_elaborati_list_title(String attribute0, String attribute1, String attribute2); 
  
  
  @Key("oggetti_elaborati_list_tipoOggettoElaboratoField_title")
  String oggetti_elaborati_list_tipoOggettoElaboratoField_title(); 
  
  @Key("oggetti_elaborati_list_idOggettoElaboratoField_title")
  String oggetti_elaborati_list_idOggettoElaboratoField_title(); 
  
  @Key("oggetti_elaborati_list_dataUltimaElaborazioneField_title")
  String oggetti_elaborati_list_dataUltimaElaborazioneField_title(); 
  
  @Key("oggetti_elaborati_list_esitoElaborazioneField_title")
  String oggetti_elaborati_list_esitoElaborazioneField_title(); 
  
  @Key("oggetti_elaborati_list_codiceErroreElaborazioneField_title")
  String oggetti_elaborati_list_codiceErroreElaborazioneField_title(); 
  
  @Key("oggetti_elaborati_list_descErroreElaborazioneField_title")
  String oggetti_elaborati_list_descErroreElaborazioneField_title(); 
  
  @Key("oggetti_elaborati_list_numeroElaborazioniField_title")
  String oggetti_elaborati_list_numeroElaborazioniField_title(); 
  
  @Key("oggetti_elaborati_list_estremiOggettoElaboratoField_title")
  String oggetti_elaborati_list_estremiOggettoElaboratoField_title(); 
  
  @Key("oggetti_elaborati_list_flgDettagliField_title")
  String oggetti_elaborati_list_flgDettagliField_title(); 
  
  
  
  @Key("oggetti_elaborati_list_esitoElaborazioneField_esito_OK_value")
  String oggetti_elaborati_list_esitoElaborazioneField_esito_OK_value(); 
  
  @Key("oggetti_elaborati_list_esitoElaborazioneField_esito_KO_value")
  String oggetti_elaborati_list_esitoElaborazioneField_esito_KO_value(); 
  
  // filtri OggettiElaboratiList

  @Key("oggetti_elaborati_list_filter_esitoElaborazione")
  String oggetti_elaborati_list_filter_esitoElaborazione(); 
  
  @Key("oggetti_elaborati_list_filter_tipoOggetti")
  String oggetti_elaborati_list_filter_tipoOggetti(); 
  
  @Key("oggetti_elaborati_list_filter_ctxMsgErrore")
  String oggetti_elaborati_list_filter_ctxMsgErrore(); 
  
  @Key("oggetti_elaborati_list_filter_dtFineUltimaElaborazione")
  String oggetti_elaborati_list_filter_dtFineUltimaElaborazione(); 
  
  
  // MonitoraggioOperazioniBatchDetail
  
  
//#############################################################
  //#                       PROFILI                             #
  //#############################################################
  
  // Menu'
  @Key("menu_profili_title")
  String menu_profili_title(); 
  
  @Key("menu_profili_prompt")
  String menu_profili_prompt(); 
  
  // Filter
  @Key("profili_filter_nomeProfilo")
  String profili_filter_nomeProfilo(); 
  
  // ProfiliLayout
  @Key("profili_detail_new_title")
  String profili_detail_new_title(); 
  
  
  @Key("profili_detail_edit_title")
  String profili_detail_edit_title(String attribute0);

  @Key("profili_detail_view_title")
  String profili_detail_view_title(String attribute0);

  
  // ProfiliList
  @Key("profili_list_nomeProfiloField_title")
  String profili_list_nomeProfiloField_title(); 
  
  // ProfiliDetail
  @Key("profili_detail_nomeProfiloItem_title")
  String profili_detail_nomeProfiloItem_title(); 
  
  //#############################################################
  //#                       SUB PROFILI                         #
  //#############################################################
  
  // Menu'
  @Key("menu_sub_profili_title")
  String menu_sub_profili_title(); 
  
  @Key("menu_sub_profili_prompt")
  String menu_sub_profili_prompt(); 
  
  // Filter
  @Key("sub_profili_filter_nomeSubProfilo")
  String sub_profili_filter_nomeSubProfilo(); 
  
  // SubProfiliLayout
  @Key("sub_profili_detail_new_title")
  String sub_profili_detail_new_title(); 
  
  
  @Key("sub_profili_detail_edit_title")
  String sub_profili_detail_edit_title(String attribute0);

  @Key("sub_profili_detail_view_title")
  String sub_profili_detail_view_title(String attribute0);

  
  // SubProfiliList
  @Key("sub_profili_list_nomeGruppoPrivField_title")
  String sub_profili_list_nomeGruppoPrivField_title(); 
  
  @Key("sub_profili_list_noteGruppoPrivField_title")
  String sub_profili_list_noteGruppoPrivField_title(); 
  
  // SubProfiliDetail
  @Key("sub_profili_detail_nomeGruppoPrivItem_title")
  String sub_profili_detail_nomeGruppoPrivItem_title(); 
  
  @Key("sub_profili_detail_noteGruppoPrivItem_title")
  String sub_profili_detail_noteGruppoPrivItem_title(); 
  
  @Key("sub_profili_detail_tabDatiPrincipali_title")
  String sub_profili_detail_tabDatiPrincipali_title(); 
  
  @Key("sub_profili_detail_tabFunzionalitaSistema_title")
  String sub_profili_detail_tabFunzionalitaSistema_title(); 
  
  @Key("sub_profili_detail_tabTipoProcessoProcedimento_title")
  String sub_profili_detail_tabTipoProcessoProcedimento_title(); 
  
  @Key("sub_profili_detail_tabTipoDocumento_title")
  String sub_profili_detail_tabTipoDocumento_title(); 
  
  @Key("sub_profili_detail_tabTipoFolder_title")
  String sub_profili_detail_tabTipoFolder_title(); 
  
  @Key("sub_profili_detail_tabClassificazione_title")
  String sub_profili_detail_tabClassificazione_title(); 
  
  @Key("sub_profili_detail_tabTipoRegistrazione_title")
  String sub_profili_detail_tabTipoRegistrazione_title(); 
  
  @Key("sub_profili_detail_tabModelloRegistrazione_title")
  String sub_profili_detail_tabModelloRegistrazione_title(); 
  
  // Abilitazioni a funzioni di sistema 
  @Key("sub_profili_abilitazioni_F_FC_value")
  String sub_profili_abilitazioni_F_FC_value(); 
  
  @Key("sub_profili_abilitazioni_F_M_value")
  String sub_profili_abilitazioni_F_M_value(); 
  
  @Key("sub_profili_abilitazioni_F_I_value")
  String sub_profili_abilitazioni_F_I_value(); 
   
  // Abilitazioni a tipi processi/iter atti
  @Key("sub_profili_abilitazioni_TP_A_value")
  String sub_profili_abilitazioni_TP_A_value(); 

  // Abilitazioni a tipologie documentali
  @Key("sub_profili_abilitazioni_TD_A_value")
  String sub_profili_abilitazioni_TD_A_value(); 

  @Key("sub_profili_abilitazioni_TD_F_value")
  String sub_profili_abilitazioni_TD_F_value(); 

  @Key("sub_profili_abilitazioni_TD_P_value")
  String sub_profili_abilitazioni_TD_P_value(); 
		   
  // Abilitazioni a tipi fascicoli/aggregati
  @Key("sub_profili_abilitazioni_TF_A_value")
  String sub_profili_abilitazioni_TF_A_value(); 
	
  // Abilitazioni alle classifiche
  @Key("sub_profili_abilitazioni_C_A_value")
  String sub_profili_abilitazioni_C_A_value(); 
	
  @Key("sub_profili_abilitazioni_C_AF_value")
  String sub_profili_abilitazioni_C_AF_value(); 
	
  // Abilitazioni a repertori
  @Key("sub_profili_abilitazioni_TR_A")
  String sub_profili_abilitazioni_TR_A(); 
		   
		  

  //#############################################################
  //#                       PUBBLICAZIONE ALBO                  #
  //#############################################################
  
  // Menu'
  @Key("menu_pubblicazione_albo_title")
  String menu_pubblicazione_albo_title(); 
  
  @Key("menu_pubblicazione_albo_prompt")
  String menu_pubblicazione_albo_prompt(); 
  
  @Key("menu_pubblicazione_albo_nuova_richiesta_title")
  String menu_pubblicazione_albo_nuova_richiesta_title(); 

  @Key("menu_pubblicazione_albo_nuova_richiesta_prompt")
  String menu_pubblicazione_albo_nuova_richiesta_prompt(); 
  
  @Key("menu_pubblicazione_albo_consultazione_richieste_title")
  String menu_pubblicazione_albo_consultazione_richieste_title(); 
  
  @Key("menu_pubblicazione_albo_consultazione_richieste_prompt")
  String menu_pubblicazione_albo_consultazione_richieste_prompt(); 
  
  @Key("menu_pubblicazione_albo_ricerca_pubblicazioni_title")
  String menu_pubblicazione_albo_ricerca_pubblicazioni_title(); 
  
  @Key("menu_pubblicazione_albo_ricerca_pubblicazioni_prompt")
  String menu_pubblicazione_albo_ricerca_pubblicazioni_prompt(); 
  
  // PubblicazioneAlboRicercaPubblicazioniLayout
  @Key("pubblicazione_albo_ricerca_pubblicazioni_detail_new_title")
  String pubblicazione_albo_ricerca_pubblicazioni_detail_new_title(); 
  
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_detail_view_title")
  String pubblicazione_albo_ricerca_pubblicazioni_detail_view_title(String estremi, String dataInizioPubblicazione);
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_detail_edit_title")
  String pubblicazione_albo_ricerca_pubblicazioni_detail_edit_title(String estremi, String dataInizioPubblicazione);
  
  // PubblicazioneAlboRicercaPubblicazioniList
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_segnaturaField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_segnaturaField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_dataAttoField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_dataAttoField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_nroPubblicazioneField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_nroPubblicazioneField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_dataInizioPubblicazioneField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_dataInizioPubblicazioneField_title(); 
  
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_dataFinePubblicazioneField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_dataFinePubblicazioneField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_richiedentePubblicazioneField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_richiedentePubblicazioneField_title(); 
   
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_statoPubblicazioneField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_statoPubblicazioneField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_tsPubblicazioneField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_tsPubblicazioneField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_formaPubblicazioneField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_formaPubblicazioneField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_motivoAnnullamentoField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_motivoAnnullamentoField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_fascicoliAppField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_fascicoliAppField_title(); 
  
  @Key("pubblicazione_albo_ricerca_pubblicazioni_list_uoProponenteField_title")
  String pubblicazione_albo_ricerca_pubblicazioni_list_uoProponenteField_title(); 
  
  @Key("pubblicazione_albo_proroga_pubblicazione_title")
  String pubblicazione_albo_proroga_pubblicazione_title(); 
  
  @Key("pubblicazione_albo_annulla_pubblicazione_title")
  String pubblicazione_albo_annulla_pubblicazione_title(); 
  
  @Key("pubblicazione_albo_rettifica_pubblicazione_title")
  String pubblicazione_albo_rettifica_pubblicazione_title(); 
		  
  // PubblicazioneAlboConsultazioneRichiesteLayout
  @Key("pubblicazione_albo_consultazione_richieste_detail_new_title")
  String pubblicazione_albo_consultazione_richieste_detail_new_title(); 

  @Key("pubblicazione_albo_consultazione_richieste_detail_view_title")
  String pubblicazione_albo_consultazione_richieste_detail_view_title(String estremi, String dataInizioPubblicazione);
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_edit_title")
  String pubblicazione_albo_consultazione_richieste_detail_edit_title(String estremi, String dataInizioPubblicazione);
  
  // PubblicazioneAlboConsultazioneRichiesteList
  @Key("pubblicazione_albo_consultazione_richieste_list_idRichiestaField_title")
  String pubblicazione_albo_consultazione_richieste_list_idRichiestaField_title(); 

  @Key("pubblicazione_albo_consultazione_richieste_list_dataRichiestaField_title")
  String pubblicazione_albo_consultazione_richieste_list_dataRichiestaField_title(); 

  @Key("pubblicazione_albo_consultazione_richieste_list_descUtentePubblicazioneField_title")
  String pubblicazione_albo_consultazione_richieste_list_descUtentePubblicazioneField_title(); 

  @Key("pubblicazione_albo_consultazione_richieste_list_tipoAttoField_title")
  String pubblicazione_albo_consultazione_richieste_list_tipoAttoField_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_list_numeroAttoField_title")
  String pubblicazione_albo_consultazione_richieste_list_numeroAttoField_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_list_dataAttoField_title")
  String pubblicazione_albo_consultazione_richieste_list_dataAttoField_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_list_mittenteRichiestaField_title")
  String pubblicazione_albo_consultazione_richieste_list_mittenteRichiestaField_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_list_oggettoField_title")
  String pubblicazione_albo_consultazione_richieste_list_oggettoField_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_list_dataInizioPubblicazioneField_title")
  String pubblicazione_albo_consultazione_richieste_list_dataInizioPubblicazioneField_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_list_giorniPubblicazioneField_title")
  String pubblicazione_albo_consultazione_richieste_list_giorniPubblicazioneField_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_list_dataFinePubblicazioneField_title")
  String pubblicazione_albo_consultazione_richieste_list_dataFinePubblicazioneField_title(); 
  
  // PubblicazioneAlboConsultazioneRichiesteDetail
  @Key("pubblicazione_albo_consultazione_richieste_detail_statoAttoItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_statoAttoItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_formaPubblicazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_formaPubblicazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_tipoRegistrazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_tipoRegistrazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_siglaRegistrazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_siglaRegistrazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_annoRegistrazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_annoRegistrazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_numeroRegistrazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_numeroRegistrazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_dataAttoItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_dataAttoItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_dataAdozioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_dataAdozioneItem_title();
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_dataEsecutivitaItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_dataEsecutivitaItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_tipoAttoItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_tipoAttoItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_tipoMittenteItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_tipoMittenteItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_dataInizioPubblicazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_dataInizioPubblicazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_giorniPubblicazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_giorniPubblicazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_dataFinePubblicazioneItem_title")
  String pubblicazione_albo_consultazione_richieste_detail_dataFinePubblicazioneItem_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_detailSectionAtto_title")
  String pubblicazione_albo_consultazione_richieste_detail_detailSectionAtto_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_detailSectionMittenteRichiedente_title")
  String pubblicazione_albo_consultazione_richieste_detail_detailSectionMittenteRichiedente_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_detailSectionOggetto_title")
  String pubblicazione_albo_consultazione_richieste_detail_detailSectionOggetto_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_detailSectionPeriodoPubblicazione_title")
  String pubblicazione_albo_consultazione_richieste_detail_detailSectionPeriodoPubblicazione_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_detailSectionFormaPubblicazione_title")
  String pubblicazione_albo_consultazione_richieste_detail_detailSectionFormaPubblicazione_title(); 
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_detailSectionNotePubblicazione_title")
  String pubblicazione_albo_consultazione_richieste_detail_detailSectionNotePubblicazione_title();
  
  @Key("pubblicazione_albo_consultazione_richieste_detail_detailSectionDefinizioneColonne_title")
  String pubblicazione_albo_consultazione_richieste_detail_detailSectionDefinizioneColonne_title(); 
  
  
  //#############################################################
  //#                       TITOLARIO COGITO                    #
  //#############################################################
  
  
  // LookupTitolarioCogitoPopup
  @Key("titolarioCogito_lookupTitolarioCogitoPopup_title")
  String titolarioCogito_lookupTitolarioCogitoPopup_title(); 
  

  //#######################################################
  //#### 			APPOSIZIONE VISTO POPUP				###
  //#######################################################

  @Key("apposizioneVisto_popup_title")
  String apposizioneVisto_popup_title(); 

  @Key("rifiutoApposizioneVisto_popup_title")
  String rifiutoApposizioneVisto_popup_title(); 

  @Key("apposizioneFirma_popup_title")
  String apposizioneFirma_popup_title(); 

  @Key("rifiutoApposizioneFirma_popup_title")
  String rifiutoApposizioneFirma_popup_title(); 



  //#######################################################
  //####     SELEZIONA AZIONE SUCCESSIVA POPUP		    ###
  //#######################################################

  @Key("azioneSuccessivaPopup_title")
  String azioneSuccessivaPopup_title(); 



  //#######################################################
  //####     GESTIONE MODELLI POPUP					    ###
  //#######################################################

  @Key("gestioneModelli_title")
  String gestioneModelli_title(); 

  @Key("gestioneModelli_salvaModelloButton_title")
  String gestioneModelli_salvaModelloButton_title(); 

  @Key("saveModelloAction_salvataggioCorretto_message")
  String saveModelloAction_salvataggioCorretto_message(); 

  @Key("gestioneModelli_cancellazione_ask")
  String gestioneModelli_cancellazione_ask(); 

  //#############################################################
  //#              AGGANCIA UTENTI APPLICAZIONE ESTERNA POPUP   #
  //#############################################################

  @Key("agganciautenteapplicazioneestpopup_combo_applicazioniEsterne_label")
  String agganciautenteapplicazioneestpopup_combo_applicazioniEsterne_label(); 

  @Key("agganciautenteapplicazioneestpopup_combo_uoCollegateUtente_label")
  String agganciautenteapplicazioneestpopup_combo_uoCollegateUtente_label(); 

  @Key("agganciautenteapplicazioneestpopup_utenteItem_title")
  String agganciautenteapplicazioneestpopup_utenteItem_title(); 

  @Key("agganciautenteapplicazioneestpopup_usernameItem_title")
  String agganciautenteapplicazioneestpopup_usernameItem_title(); 

  @Key("agganciautenteapplicazioneestpopup_passwordItem_title")
  String agganciautenteapplicazioneestpopup_passwordItem_title(); 

  @Key("agganciautenteapplicazioneestpopup_confermaPasswordItem_title")
  String agganciautenteapplicazioneestpopup_confermaPasswordItem_title(); 



  //#############################################################
  //#              PERSONALIZZAZIONI UTENTE MENU                #
  //#############################################################

  @Key("configUtenteMenuFirmaEmail_title")
  String configUtenteMenuFirmaEmail_title(); 

  @Key("configUtenteMenuFirmeEmail_title")
  String configUtenteMenuFirmeEmail_title(); 

  @Key("configUtenteMenuCodRapidoOrganigramma_title")
  String configUtenteMenuCodRapidoOrganigramma_title(); 

  @Key("configUtenteMenuUoLavoro_title")
  String configUtenteMenuUoLavoro_title();
  
  @Key("configUtenteMenuPreferenzaScrivania_title")
  String configUtenteMenuPreferenzaScrivania_title();
  
  @Key("configUtenteMenuPreferenzaNotificheMail_title")
  String configUtenteMenuPreferenzaNotificheMail_title();
  
  @Key("configUtenteMenuPreferenzaIterAtti_title")
  String configUtenteMenuPreferenzaIterAtti_title();
  
  @Key("configUtenteMenuPreferenzaScrivania_mostradoc_title")
  String configUtenteMenuPreferenzaScrivania_mostradoc_title();

  @Key("configUtenteMenuImpostazioniStampa_title")
  String configUtenteMenuImpostazioniStampa_title();
  
  @Key("configUtenteMenuImpostazioniFirma_title")
  String configUtenteMenuImpostazioniFirma_title();
  
  @Key("configUtenteMenuImpostazioniCredenzialiFirmaAutomatica_title")
  String configUtenteMenuImpostazioniCredenzialiFirmaAutomatica_title();
  
  @Key("configUtenteMenuImpostaModalitaFirma_title")
  String configUtenteMenuImpostaModalitaFirma_title();
  
  @Key("configUtenteMenuImpostaCredenzialiFirmaAutomatica_title")
  String configUtenteMenuImpostaCredenzialiFirmaAutomatica_title();

  @Key("configUtenteMenuRimuoviCredenzialiFirmaAutomatica_title")
  String configUtenteMenuRimuoviCredenzialiFirmaAutomatica_title();
  
  @Key("configUtenteMenuErroreCredenzialiFirmaAutomatica_title")
  String configUtenteMenuErroreCredenzialiFirmaAutomatica_title();
  
  @Key("configUtenteMenuErroreImpostazioneFirmaAutomatica_title")
  String configUtenteMenuErroreImpostazioneFirmaAutomatica_title();
  
  @Key("configUtenteMenuImpostazioniFirma_metodoFirma_title")
  String configUtenteMenuImpostazioniFirma_metodoFirma_title();
  
  @Key("configUtenteMenuImpostazioniFirma_client_value")
  String configUtenteMenuImpostazioniFirma_client_value();
  
  @Key("configUtenteMenuImpostazioniFirma_remotaAutomatica_value")
  String configUtenteMenuImpostazioniFirma_remotaAutomatica_value();
  
  @Key("configUtenteMenuImpostazioniFirma_remota_value")
  String configUtenteMenuImpostazioniFirma_remota_value();
  
  @Key("configUtenteMenuImpostazioniFirma_remota_ws_value")
  String configUtenteMenuImpostazioniFirma_remota_ws_value();
  
  @Key("configUtenteMenuImpostazioniFirma_remota_solo_pin_value")
  String configUtenteMenuImpostazioniFirma_remota_solo_pin_value();
  
  @Key("configUtenteMenuImpostazioniFirma_providerFirmaHsmSelect")
  String configUtenteMenuImpostazioniFirma_providerFirmaHsmSelect();
  
  @Key("configUtenteMenuImpostazioniFirma_userId")
  String configUtenteMenuImpostazioniFirma_userId();
  
  @Key("configUtenteMenuImpostazioniFirma_userIdCF")
  String configUtenteMenuImpostazioniFirma_userIdCF();
  
  @Key("configUtenteMenuImpostazioniFirma_firmaInDelega")
  String configUtenteMenuImpostazioniFirma_firmaInDelega();
  
  @Key("configUtenteMenuImpostazioniFirma_firmaInDelegaCF")
  String configUtenteMenuImpostazioniFirma_firmaInDelegaCF();
  
  @Key("configUtenteMenuImpostazioniFirma_password")
  String configUtenteMenuImpostazioniFirma_password();
  
  @Key("configUtenteMenuImpostazioniFirma_confermaPassword")
  String configUtenteMenuImpostazioniFirma_confermaPassword();
  
  @Key("configUtenteMenuImpostazioniFirma_cambiaPassword")
  String configUtenteMenuImpostazioniFirma_cambiaPassword();
  
  
  @Key("configUtenteMenuImpostazioniFirma_usernameRichOtp")
  String configUtenteMenuImpostazioniFirma_usernameRichOtp();
  
  @Key("configUtenteMenuImpostazioniFirma_passwordRichOtp")
  String configUtenteMenuImpostazioniFirma_passwordRichOtp();
  
  @Key("configUtenteMenuImpostazioniFirma_confermaPasswordRichOtp")
  String configUtenteMenuImpostazioniFirma_confermaPasswordRichOtp();
  
  @Key("configUtenteMenuImpostazioniFirma_cambiaPasswordRichOtp")
  String configUtenteMenuImpostazioniFirma_cambiaPasswordRichOtp();
  
  @Key("configUtenteMenuImpostazioniFirma_showGeneraOtpViaSms")
  String configUtenteMenuImpostazioniFirma_showGeneraOtpViaSms();
  
  @Key("configUtenteMenuImpostazioniFirma_showGeneraOtpViaCall")
  String configUtenteMenuImpostazioniFirma_showGeneraOtpViaCall();
  
  @Key("configUtenteMenuStampaEtichettaAutoReg_title")
  String configUtenteMenuStampaEtichettaAutoReg_title(); 
  
  @Key("configUtenteMenuSkipSceltaNrCopieEtichette_title")
  String configUtenteMenuSkipSceltaNrCopieEtichette_title();

  @Key("configUtenteMenuImpostazioniCopertinaTimbro_title")
  String configUtenteMenuImpostazioniCopertinaTimbro_title(); 

  @Key("configUtenteMenuImpostazioniDocumento_title")
  String configUtenteMenuImpostazioniDocumento_title(); 

  @Key("configUtenteMenuImpostazioniFascicolo_title")
  String configUtenteMenuImpostazioniFascicolo_title(); 

  @Key("configUtenteMenuSceltaOrganigramma_title")
  String configUtenteMenuSceltaOrganigramma_title();

  @Key("saveImpostazioniCopertinaTimbroWindow_skipSceltaOpzioniTimbroSegnatura")
  String saveImpostazioniCopertinaTimbroWindow_skipSceltaOpzioniTimbroSegnatura(); 

  @Key("saveImpostazioniCopertinaTimbroWindow_skipSceltaOpzioniCopertina")
  String saveImpostazioniCopertinaTimbroWindow_skipSceltaOpzioniCopertina(); 
  
  @Key("saveImpostazioniCopertinaTimbroWindow_skipSceltaApponiTimbro")
  String saveImpostazioniCopertinaTimbroWindow_skipSceltaApponiTimbro();
  
  @Key("saveImpostazioniCopertinaTimbroWindow_skipSceltaTimbroDocZip")
  String saveImpostazioniCopertinaTimbroWindow_skipSceltaTimbroDocZip();

  @Key("configUtenteMenuSceltaOrganigramma_nascondiUtentiSection")
  String configUtenteMenuSceltaOrganigramma_nascondiUtentiSection();
  
  @Key("configUtenteMenuSceltaOrganigramma_destinatariInterniRegistrazioniCheckBox")
  String configUtenteMenuSceltaOrganigramma_destinatariInterniRegistrazioniCheckBox();
  
  @Key("configUtenteMenuSceltaOrganigramma_mittentiInterniRegistrazioniCheckBox")
  String configUtenteMenuSceltaOrganigramma_mittentiInterniRegistrazioniCheckBox();
  
  @Key("configUtenteMenuSceltaOrganigramma_assegnatariCheckBox")
  String configUtenteMenuSceltaOrganigramma_assegnatariCheckBox();
  
  @Key("configUtenteMenuSceltaOrganigramma_destinatariInviiPerConoscenzaCheckBox")
  String configUtenteMenuSceltaOrganigramma_destinatariInviiPerConoscenzaCheckBox();
  
  @Key("archivio_list_downloadDocsZip")
  String archivio_list_downloadDocsZip(); 
  
  @Key("archivio_list_downloadDocsZip_originali")
  String archivio_list_downloadDocsZip_originali(); 
  
  @Key("archivio_list_downloadDocsZip_timbrati_segnatura")
  String archivio_list_downloadDocsZip_timbrati_segnatura(); 
  
  @Key("archivio_list_downloadDocsZip_timbrati_conforme_stampa")
  String archivio_list_downloadDocsZip_timbrati_conforme_stampa(); 
  
  @Key("archivio_list_downloadDocsZip_timbrati_conforme_digitale")
  String archivio_list_downloadDocsZip_timbrati_conforme_digitale(); 
  
  @Key("archivio_list_downloadDocsZip_timbrati_conforme_cartaceo")
  String archivio_list_downloadDocsZip_timbrati_conforme_cartaceo(); 
  
  @Key("archivio_list_downloadDocsZip_sbustati")
  String archivio_list_downloadDocsZip_sbustati(); 

  @Key("alert_archivio_list_downloadDocsZip")
  String alert_archivio_list_downloadDocsZip(); 

  @Key("alert_archivio_massivo_downloadDocsZip")
  String alert_archivio_massivo_downloadDocsZip(); 

  @Key("alert_archivio_massivo_alldoc_downloadDocsZip")
  String alert_archivio_massivo_alldoc_downloadDocsZip(); 

  @Key("alert_archivio_list_limiteMaxZipError")
  String alert_archivio_list_limiteMaxZipError(); 
  
  @Key("alert_archivio_list_downloadDocsZip_wait")
  String alert_archivio_list_downloadDocsZip_wait(); 

  @Key("alert_archiviazione_azione_da_fare")
  String alert_archiviazione_azione_da_fare(); 

  @Key("alert_archiviazione_azione_da_fare_massiva")
  String alert_archiviazione_azione_da_fare_massiva(); 

  @Key("funzProtButton_title")
  String funzProtButton_title();
  
  @Key("funzProtButton_alert_attivata")
  String funzProtButton_alert_attivata(String desUo);
  
  @Key("funzProtButton_alert_disattivata")
  String funzProtButton_alert_disattivata();
  
  @Key("configUtenteImpostazioniFascicolo_detailSectionFascicolo_title")
  String configUtenteImpostazioniFascicolo_detailSectionFascicolo_title();
  
  @Key("configUtenteImpostazioniFascicolo_detailSectionCartella_title")
  String configUtenteImpostazioniFascicolo_detailSectionCartella_title();
  
  @Key("configUtenteImpostazioniFascicolo_detailSectionPraticaPregressa_title")
  String configUtenteImpostazioniFascicolo_detailSectionPraticaPregressa_title();
  
  @Key("configUtenteImpostazioniFascicolo_idFolderTypeFascicolo_title")
  String configUtenteImpostazioniFascicolo_idFolderTypeFascicolo_title();
  
  @Key("configUtenteImpostazioniFascicolo_skipSceltaTipologiaFascicolo_title")
  String configUtenteImpostazioniFascicolo_skipSceltaTipologiaFascicolo_title();
  
  @Key("configUtenteImpostazioniFascicolo_idFolderTypeCartella_title")
  String configUtenteImpostazioniFascicolo_idFolderTypeCartella_title();
  
  @Key("configUtenteImpostazioniFascicolo_skipSceltaTipologiaCartella_title")
  String configUtenteImpostazioniFascicolo_skipSceltaTipologiaCartella_title();
  
  @Key("configUtenteImpostazioniFascicolo_idFolderTypePregresso_title")
  String configUtenteImpostazioniFascicolo_idFolderTypePregresso_title();
  
  @Key("configUtenteImpostazioniFascicolo_skipSceltaTipologiaPregresso_title")
  String configUtenteImpostazioniFascicolo_skipSceltaTipologiaPregresso_title();
  
  @Key("configUtenteMenuPreferenzeAccessibilita_title")
  String configUtenteMenuPreferenzeAccessibilita_title();
  
  //##################################################################
  //#                             Preview                            #
  //##################################################################
  
  @Key("previewWindow_alertDimensioneFilePreviewPdf_message")
  String previewWindow_alertDimensioneFilePreviewPdf_message();
  
  @Key("previewWindow_alertDimensioneFilePreviewGenerica_message")
  String previewWindow_alertDimensioneFilePreviewGenerica_message();
  
  //#############################################################################################
  //#                          Preview - Popup dopo selezione pagine                            #
  //#############################################################################################
    
  @Key("previewWindowAfterPageSelectionPopup_title")
  String previewWindowAfterPageSelectionPopup_title();
  
  @Key("previewWindowAfterPageSelectionPopup_azioneDaFare_title")
  String previewWindowAfterPageSelectionPopup_azioneDaFare_title();					  
						  
  @Key("previewWindowAfterPageSelectionPopup_azioneDaFare_salva")
  String previewWindowAfterPageSelectionPopup_azioneDaFare_salva();
  
  @Key("previewWindowAfterPageSelectionPopup_azioneDaFare_salvaComeVersioneOmissis")
  String previewWindowAfterPageSelectionPopup_azioneDaFare_salvaComeVersioneOmissis();
  
  @Key("previewWindowAfterPageSelectionPopup_azioneDaFare_scarica")
  String previewWindowAfterPageSelectionPopup_azioneDaFare_scarica();
  
  @Key("previewWindowAfterPageSelectionPopup_azioneDaFare_nessunaAzione")
  String previewWindowAfterPageSelectionPopup_azioneDaFare_nessunaAzione();
  
  @Key("previewWindowAfterPageSelectionPopup_peccette_non_applicabile")
  String previewWindowAfterPageSelectionPopup_peccette_non_applicabile();
  
  //#############################################################
  //#                       CHIUSURA MASSIVA EMAIL              #
  //#############################################################
  
  // Menu'
  @Key("menu_chiusura_massiva_email_title")
  String menu_chiusura_massiva_email_title(); 
  
  @Key("menu_chiusura_massiva_email_prompt")
  String menu_chiusura_massiva_email_prompt(); 

  //#############################################################
  //#                     PERIZIE CANVAS                        #
  //#############################################################

  @Key("perizia_codiceRapitoItem_title")
  String perizia_codiceRapitoItem_title(); 
  
  @Key("perizia_combo_codiceField_title")
  String perizia_combo_codiceField_title(); 
  
  @Key("perizia_combo_descrizioneField_title")
  String perizia_combo_descrizioneField_title(); 

  
  //#############################################################
  //#                     DATI CONTABILI                        #
  //#############################################################

  @Key("dati_contabili_combo_capitoloField_title")
  String dati_contabili_combo_capitoloField_title(); 

  @Key("dati_contabili_combo_descrizioneField_title")
  String dati_contabili_combo_descrizioneField_title(); 
  
  
  
  
  
  
  
  
  //#############################################################
  //#            CONTENUTI FOGLIO CSV IMPORTATO                 #
  //#############################################################
  
  @Key("contenuto_foglio_importato_list_codApplicazione_title")
  String contenuto_foglio_importato_list_codApplicazione_title();
  
  @Key("contenuto_foglio_importato_list_codIstanzaApplicazione_title")
  String contenuto_foglio_importato_list_codIstanzaApplicazione_title();
  
  @Key("contenuto_foglio_importato_list_denominazioneApplicazione_title")
  String contenuto_foglio_importato_list_denominazioneApplicazione_title();
  
  @Key("contenuto_foglio_importato_list_tipologiaContenuto_title")
  String contenuto_foglio_importato_list_tipologiaContenuto_title();
  
  @Key("contenuto_foglio_importato_list_tsUpload_title")
  String contenuto_foglio_importato_list_tsUpload_title();
  
  @Key("contenuto_foglio_importato_list_denominazioneUtenteUpload_title")
  String contenuto_foglio_importato_list_denominazioneUtenteUpload_title();
  
//  @Key("contenuto_foglio_importato_list_uri_title")
//  String contenuto_foglio_importato_list_uri_title();
  
  @Key("contenuto_foglio_importato_list_stato_title")
  String contenuto_foglio_importato_list_stato_title();
  
  @Key("contenuto_foglio_importato_list_tsInizioElab_title")
  String contenuto_foglio_importato_list_tsInizioElab_title();
  
  @Key("contenuto_foglio_importato_list_tsFineElab_title")
  String contenuto_foglio_importato_list_tsFineElab_title();
  
  @Key("contenuto_foglio_importato_list_nrTotaleRighe_title")
  String contenuto_foglio_importato_list_nrTotaleRighe_title();
  
  @Key("contenuto_foglio_importato_list_nrRigheElabSuccesso_title")
  String contenuto_foglio_importato_list_nrRigheElabSuccesso_title();
  
  @Key("contenuto_foglio_importato_list_nrRigheInErrore_title")
  String contenuto_foglio_importato_list_nrRigheInErrore_title();
  
  @Key("contenuto_foglio_importato_list_idFoglio_title")
  String contenuto_foglio_importato_list_idFoglio_title();
  
  @Key("contenuto_foglio_importato_list_nomeFile_title")
  String contenuto_foglio_importato_list_nomeFile_title();
  
  @Key("contenuto_foglio_importato_list_erroreElabFoglio_title")
  String contenuto_foglio_importato_list_erroreElabFoglio_title();
  
  @Key("contenuto_foglio_importato_list_nrRiga_title")
  String contenuto_foglio_importato_list_nrRiga_title();
  
  @Key("contenuto_foglio_importato_list_tsInsRiga_title")
  String contenuto_foglio_importato_list_tsInsRiga_title();
  
  @Key("contenuto_foglio_importato_list_esitoElabRiga_title")
  String contenuto_foglio_importato_list_esitoElabRiga_title();
  
  @Key("contenuto_foglio_importato_list_tsUltimaElabRiga_title")
  String contenuto_foglio_importato_list_tsUltimaElabRiga_title();
  
  @Key("contenuto_foglio_importato_list_codErroreElabRiga_title")
  String contenuto_foglio_importato_list_codErroreElabRiga_title();
  
  @Key("contenuto_foglio_importato_list_msgErroreElabRiga_title")
  String contenuto_foglio_importato_list_msgErroreElabRiga_title();
  
  @Key("contenuto_foglio_importato_list_valoriRiga_title")
  String contenuto_foglio_importato_list_valoriRiga_title();

  @Key("contenuto_foglio_importato_edit_title")
  String contenuto_foglio_importato_edit_title(String attribute0, String attribute1);
  
  @Key("contenuto_foglio_importato_view_title")
  String contenuto_foglio_importato_view_title(String attribute0, String attribute1);
  
  //#############################################################
  //#                      FOGLIO CSV IMPORTATO                 #
  //#############################################################
  
  @Key("foglio_importato_list_codApplicazione_title")
  String foglio_importato_list_codApplicazione_title();
  
  @Key("foglio_importato_list_codIstanzaApplicazione_title")
  String foglio_importato_list_codIstanzaApplicazione_title();
  
  @Key("foglio_importato_list_denominazioneApplicazione_title")
  String foglio_importato_list_denominazioneApplicazione_title();
  
  @Key("foglio_importato_list_tipologiaContenuto_title")
  String foglio_importato_list_tipologiaContenuto_title();
  
  @Key("foglio_importato_list_tsUpload_title")
  String foglio_importato_list_tsUpload_title();

  @Key("foglio_importato_list_denominazioneUtenteUpload_title")
  String foglio_importato_list_denominazioneUtenteUpload_title();

//  @Key("foglio_importato_list_uri_title")
//  String foglio_importato_list_uri_title();

  @Key("foglio_importato_list_stato_title")
  String foglio_importato_list_stato_title();

  @Key("foglio_importato_list_tsInizioElab_title")
  String foglio_importato_list_tsInizioElab_title();

  @Key("foglio_importato_list_tsFineElab_title")
  String foglio_importato_list_tsFineElab_title();

  @Key("foglio_importato_list_nrTotaleRighe_title")
  String foglio_importato_list_nrTotaleRighe_title();

  @Key("foglio_importato_list_nrRigheElabSuccesso_title")
  String foglio_importato_list_nrRigheElabSuccesso_title();

  @Key("foglio_importato_list_nrRigheInErrore_title")
  String foglio_importato_list_nrRigheInErrore_title();

  @Key("foglio_importato_list_idFoglio_title")
  String foglio_importato_list_idFoglio_title();

  @Key("foglio_importato_list_nomeFile_title")
  String foglio_importato_list_nomeFile_title();
  
  @Key("foglio_importato_list_erroreElabFoglio_title")
  String foglio_importato_list_erroreElabFoglio_title();
  
//  @Key("foglio_importato_list_uriExcelRielaborato_title")
//  String foglio_importato_list_uriExcelRielaborato_title();
  
//  @Key("foglio_importato_list_uriFileProdotto_title")
//  String foglio_importato_list_uriFileProdotto_title();

//#############################################################
//#          ATTIVA/DISATTIVA SESSIONE IN DELEGA              #
//#############################################################
  
  @Key("attiva_disattiva_sessione_in_delega_form_selectDelega_noSearchOrEmptyMessage")
  String attiva_disattiva_sessione_in_delega_form_selectDelega_noSearchOrEmptyMessage(); 

  //#############################################################
  //#               RUOLI AMMINISTRATIVI                        #
  //#############################################################
  
  // Menu'
  @Key("menu_ruoli_amministrativi_title")
  String menu_ruoli_amministrativi_title(); 

  @Key("menu_ruoli_amministrativi_prompt")
  String menu_ruoli_amministrativi_prompt(); 
  
  // RuoliAmministrativiLayout
  @Key("ruoli_amministrativi_detail_new_title")
  String ruoli_amministrativi_detail_new_title(); 
 
  @Key("ruoli_amministrativi_detail_edit_title")
  String ruoli_amministrativi_detail_edit_title(String attribute0);
 
  @Key("ruoli_amministrativi_detail_view_title")
  String ruoli_amministrativi_detail_view_title(String attribute0);
 
  // RuoliAmministrativiList
  @Key("ruoli_amministrativi_list_idRuoloField_title")
  String ruoli_amministrativi_list_idRuoloField_title(); 
 
  @Key("ruoli_amministrativi_list_descrizioneRuoloField_title")
  String ruoli_amministrativi_list_descrizioneRuoloField_title(); 
 
  @Key("ruoli_amministrativi_list_flgValidoField_title")
  String ruoli_amministrativi_list_flgValidoField_title();  
  
  @Key("ruoli_amministrativi_list_descUtenteInsField_title")
  String ruoli_amministrativi_list_descUtenteInsField_title();  
  
  @Key("ruoli_amministrativi_list_dataInsField_title")
  String ruoli_amministrativi_list_dataInsField_title();   
 
  @Key("ruoli_amministrativi_list_descUtenteUltModField_title")
  String ruoli_amministrativi_list_descUtenteUltModField_title();  
  
  @Key("ruoli_amministrativi_list_dataUltModField_title")
  String ruoli_amministrativi_list_dataUltModField_title();  

  @Key("ruoli_amministrativi_list_recProtettoField_title")
  String ruoli_amministrativi_list_recProtettoField_title();  
  
  @Key("ruoli_amministrativi_flgValido_0_value")
  String ruoli_amministrativi_flgValido_0_value();
  
  @Key("ruoli_amministrativi_flgValido_1_value")
  String ruoli_amministrativi_flgValido_1_value();
  
  @Key("ruoli_amministrativi_flgAnnullato_1_value")
  String ruoli_amministrativi_flgAnnullato_1_value();  

  @Key("ruoli_amministrativi_recProtetto_1_value")
  String ruoli_amministrativi_recProtetto_1_value();  
  
  // RuoliAmministrativiDetail
  @Key("ruoli_amministrativi_detail_idRuoloItem_title")
  String ruoli_amministrativi_detail_idRuoloItem_title();  
  
  @Key("ruoli_amministrativi_detail_descrizioneRuoloItem_title")
  String ruoli_amministrativi_detail_descrizioneRuoloItem_title();  
    
  @Key("ruoli_amministrativi_detail_ciProvRuoloItem_title")
  String ruoli_amministrativi_detail_ciProvRuoloItem_title();  
  
  @Key("ruoli_amministrativi_detail_flgEspletaSoloAlleUOItem_title")
  String ruoli_amministrativi_detail_flgEspletaSoloAlleUOItem_title();  
  
  @Key("ruoli_amministrativi_detail_listaRuoliInclusiItem_title")
  String ruoli_amministrativi_detail_listaRuoliInclusiItem_title();  
  
  //#############################################################
  //#               CONTENUTI AMMINISTRAZIONE TRASPARENTE       #
  //#############################################################
  
  // Menu'
  @Key("menu_contenuti_amministrazione_trasparente_title")
  String menu_contenuti_amministrazione_trasparente_title(); 

  @Key("menu_contenuti_amministrazione_trasparente_prompt")
  String menu_contenuti_amministrazione_trasparente_prompt(); 
  
  // Layout
  @Key("contenuti_amministrazione_trasparente_detail_new_title")
  String contenuti_amministrazione_trasparente_detail_new_title(); 
 
  @Key("contenuti_amministrazione_trasparente_detail_edit_title")
  String contenuti_amministrazione_trasparente_detail_edit_title(String attribute0);
 
  @Key("contenuti_amministrazione_trasparente_detail_view_title")
  String contenuti_amministrazione_trasparente_detail_view_title(String attribute0);
 
  
  @Key("contenuti_amministrazione_trasparente_tree_detail_new_title")
  String contenuti_amministrazione_trasparente_tree_detail_new_title(); 
 
  @Key("contenuti_amministrazione_trasparente_tree_detail_edit_title")
  String contenuti_amministrazione_trasparente_tree_detail_edit_title();
 
  @Key("contenuti_amministrazione_trasparente_tree_detail_view_title")
  String contenuti_amministrazione_trasparente_tree_detail_view_title();
 
  // List
  @Key("contenuti_amministrazione_trasparente_list_tipoContenutoField_title")
  String contenuti_amministrazione_trasparente_list_tipoContenutoField_title(); 
  
 
  @Key("contenuti_amministrazione_trasparente_list_statoRichiestaPubblicazioneField_title")
  String contenuti_amministrazione_trasparente_list_statoRichiestaPubblicazioneField_title(); 
  
  
  @Key("contenuti_amministrazione_trasparente_list_titoloContenutoField_title")
  String contenuti_amministrazione_trasparente_list_titoloContenutoField_title(); 
  
  // Context-menu
  @Key("contenuti_amministrazione_trasparente_aggiungiContenutoSuccessivoMenuItem_title")
  String contenuti_amministrazione_trasparente_aggiungiContenutoSuccessivoMenuItem_title(); 
  
  
  @Key("contenuti_amministrazione_trasparente_autorizzaRichiestaPubblicazioneMenuItem_title")
  String contenuti_amministrazione_trasparente_autorizzaRichiestaPubblicazioneMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_respingiRichiestaPubblicazioneMenuItem_title")
  String contenuti_amministrazione_trasparente_respingiRichiestaPubblicazioneMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_titoloSezioneMenuItem_title")
  String contenuti_amministrazione_trasparente_titoloSezioneMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_fineSezioneMenuItem_title")
  String contenuti_amministrazione_trasparente_fineSezioneMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_paragrafoMenuItem_title")
  String contenuti_amministrazione_trasparente_paragrafoMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_documentoSempliceMenuItem_title")
  String contenuti_amministrazione_trasparente_documentoSempliceMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_documentoConAllegatiMenuItem_title")
  String contenuti_amministrazione_trasparente_documentoConAllegatiMenuItem_title(); 

  @Key("contenuti_amministrazione_trasparente_tabellaMenuItem_title")
  String contenuti_amministrazione_trasparente_tabellaMenuItem_title(); 
   
  @Key("contenuti_amministrazione_trasparente_aggiungiSottoSezioneMenuItem_title")
  String contenuti_amministrazione_trasparente_aggiungiSottoSezioneMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_aggiungiSezioneSuccessivaMenuItem_title")
  String contenuti_amministrazione_trasparente_aggiungiSezioneSuccessivaMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_modificaNomeSezioneMenuItem_title")
  String contenuti_amministrazione_trasparente_modificaNomeSezioneMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_eliminaSezioneMenuItem_title")
  String contenuti_amministrazione_trasparente_eliminaSezioneMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_uoAbilitateMenuItem_title")
  String contenuti_amministrazione_trasparente_uoAbilitateMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_utentiAbilitatiMenuItem_title")
  String contenuti_amministrazione_trasparente_utentiAbilitatiMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneDaAutorizzareIcon_title")
  String contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneDaAutorizzareIcon_title(); 
  
  @Key("contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneAutorizzataIcon_title")
  String contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneAutorizzataIcon_title(); 
 
  @Key("contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneRespintaIcon_title")
  String contenuti_amministrazione_trasparente_statoRichiestaPubblicazioneRespintaIcon_title(); 
  
  @Key("contenuti_amministrazione_trasparente_headerMenuItem_title")
  String contenuti_amministrazione_trasparente_headerMenuItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_rifNormativiMenuItem_title")
  String contenuti_amministrazione_trasparente_rifNormativiMenuItem_title(); 
 
  

  @Key("contenuti_amministrazione_trasparente_tagliaContenutoMenuItem_title")
  String contenuti_amministrazione_trasparente_tagliaContenutoMenuItem_title(); 
  
  

  @Key("contenuti_amministrazione_trasparente_incollaContenutoMenuItem_title")
  String contenuti_amministrazione_trasparente_incollaContenutoMenuItem_title(); 
  
  // Detail  
  @Key("contenuti_amministrazione_trasparente_rifNormativiNewButtonItem_title")
  String contenuti_amministrazione_trasparente_rifNormativiNewButtonItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_rifNormativiUpdButtonItem_title")
  String contenuti_amministrazione_trasparente_rifNormativiUpdButtonItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_headerNewButtonItem_title")
  String contenuti_amministrazione_trasparente_headerNewButtonItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_headerUpdButtonItem_title")
  String contenuti_amministrazione_trasparente_headerUpdButtonItem_title(); 
    
  @Key("contenuti_amministrazione_trasparente_detail_tipoContenutoItem_title")
  String contenuti_amministrazione_trasparente_detail_tipoContenutoItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_detail_titoloContenutoItem_title")
  String contenuti_amministrazione_trasparente_detail_titoloContenutoItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_detail_htmlContenutoItem_title")
  String contenuti_amministrazione_trasparente_detail_htmlContenutoItem_title(); 
  
  @Key("contenuti_amministrazione_trasparente_detailSectionParagrafo_title")
  String contenuti_amministrazione_trasparente_detailSectionParagrafo_title(); 
  
  @Key("contenuti_amministrazione_trasparente_detail_flgMostraDatiAggiornamentoItem_title")
  String contenuti_amministrazione_trasparente_detail_flgMostraDatiAggiornamentoItem_title(); 
 
  @Key("contenuti_amministrazione_trasparente_detail_nroAllegato_title")
  String contenuti_amministrazione_trasparente_detail_nroAllegato_title(); 
   
  // Privilegi Entita Sezione
  @Key("contenuti_amministrazione_trasparente_privilegi_entita_sezione_P_value")
  String contenuti_amministrazione_trasparente_privilegi_entita_sezione_P_value(); 
  
  @Key("contenuti_amministrazione_trasparente_privilegi_entita_sezione_G_value")
  String contenuti_amministrazione_trasparente_privilegi_entita_sezione_G_value(); 
  
  //#############################################################
  //#                 MODIFICA DATI EXTRA ITER                  #
  //#############################################################
  
  @Key("modificaDatiExtraIter_menu_apri_title")
  String modificaDatiExtraIter_menu_apri_title();
 
  //#############################################################
  //#                 MODIFICA OPERE ATTO                       #
  //#############################################################
  
  @Key("modificaOpereAtto_menu_apri_title")
  String modificaOpereAtto_menu_apri_title(); 

  @Key("modificaOpereAtto_Popup_Ud_title")
  String modificaOpereAtto_Popup_Ud_title(String attribute0);
  
  //#############################################################
  //#                 MODIFICA DATI PUBBLICAZIONE ATTO          #
  //#############################################################
  
  @Key("modificaDatiPubblAtto_menu_apri_title")
  String modificaDatiPubblAtto_menu_apri_title(); 

  @Key("modificaDatiPubblAtto_Popup_Ud_title")
  String modificaDatiPubblAtto_Popup_Ud_title(String attribute0);
  
  //#############################################################
  //#                 MODIFICA  DIR. RESP. REG. TECNICA         #
  //#############################################################
  
  @Key("modificaSostDirRegTecnicaPropAtto_menu_apri_title")
  String modificaSostDirRegTecnicaPropAtto_menu_apri_title(); 

  @Key("modificaSostDirRegTecnicaPropAtto_Popup_Ud_title")
  String modificaSostDirRegTecnicaPropAtto_Popup_Ud_title(String attribute0);
  
  @Key("modificaTermineRaccoltaSottoscrizioniPropAtto_menu_apri_title")
  String modificaTermineRaccoltaSottoscrizioniPropAtto_menu_apri_title(); 

  @Key("modificaTermineRaccoltaSottoscrizioniPropAtto_Popup_Ud_title")
  String modificaTermineRaccoltaSottoscrizioniPropAtto_Popup_Ud_title(String attribute0);
  
  //###############################################################################
  //#     REGOLE PROTOCOLLAZIONE AUTOMATICA SULLE CASELLE PEC/PEO                 #
  //###############################################################################
  
  @Key("menu_regole_protocollazione_automatica_caselle_pec_peo_title")
  String menu_regole_protocollazione_automatica_caselle_pec_peo_title(); 

  @Key("menu_regole_protocollazione_automatica_caselle_pec_peo_prompt")
  String menu_regole_protocollazione_automatica_caselle_pec_peo_prompt(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_statoRegolaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_statoRegolaItem_title();
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_nomeRegolaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_nomeRegolaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_descrizioneRegolaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_descrizioneRegolaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_dataAttivazioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_dataAttivazioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_dataCessazioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_dataCessazioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_dataInizioSospensioneItem")
  String regole_protocollazione_automatica_caselle_pec_peo_dataInizioSospensioneItem(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_dataFineSospensioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_dataFineSospensioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_caselleItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_caselleItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_detailSectionCaratteristiche_title")
  String regole_protocollazione_automatica_caselle_pec_peo_detailSectionCaratteristiche_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgTipoEmailRicezioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgTipoEmailRicezioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgTipoEmailItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgTipoEmailItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_indirizziMittentiItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_indirizziMittentiItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_codAmmMittenteItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_codAmmMittenteItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_codAooMittenteItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_codAooMittenteItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_codRegistroMittenteItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_codRegistroMittenteItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_datiSegnaturaRegoleRegAutoCaselleItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_datiSegnaturaRegoleRegAutoCaselleItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_paroleInOggettoMailItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_paroleInOggettoMailItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_paroleInTestoMailItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_paroleInTestoMailItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_emailDestinatariItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_emailDestinatariItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_tipoDestinatarioItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_tipoDestinatarioItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_indirizzoDestinatarioItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_indirizzoDestinatarioItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_detailSectionParametriProtocollazione_title")
  String regole_protocollazione_automatica_caselle_pec_peo_detailSectionParametriProtocollazione_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgTipoRegistrazioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgTipoRegistrazioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_repertorioItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_repertorioItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_uoRegistrazioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_uoRegistrazioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_utenteRegistrazioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_utenteRegistrazioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_mittenteRegistrazioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_mittenteRegistrazioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_uoDestinatariaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_uoDestinatariaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgAssegnaPerCompetenzaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgAssegnaPerCompetenzaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgAssegnaPerConoscenzaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgAssegnaPerConoscenzaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_uoAssegnatariaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_uoAssegnatariaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_uoPerConoscenzaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_uoPerConoscenzaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgRiservatezzaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgRiservatezzaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_classificazioneFascicolazioneItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_classificazioneFascicolazioneItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_folderCustomItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_folderCustomItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgTimbraturaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgTimbraturaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgNotificaConfermaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgNotificaConfermaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_indirizzoDestinatarioRispostaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_indirizzoDestinatarioRispostaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_oggettoRispostaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_oggettoRispostaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_testoRispostaItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_testoRispostaItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_indirizzoMittenteItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_indirizzoMittenteItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_codAmmMittenteCanvas_title")
  String regole_protocollazione_automatica_caselle_pec_peo_codAmmMittenteCanvas_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_codAooMittenteCanvas_title")
  String regole_protocollazione_automatica_caselle_pec_peo_codAooMittenteCanvas_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_codRegistroMittenteCanvas_title")
  String regole_protocollazione_automatica_caselle_pec_peo_codRegistroMittenteCanvas_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_parolaInOggettoMailItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_parolaInOggettoMailItem_title(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_parolaInTestoMailItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_parolaInTestoMailItem_title(); 
  
//  @Key("regole_protocollazione_automatica_caselle_pec_peo_parolaInOggettoMailItem_prompt")
//  String regole_protocollazione_automatica_caselle_pec_peo_parolaInOggettoMailItem_prompt(); 
//  
//  @Key("regole_protocollazione_automatica_caselle_pec_peo_parolaInTestoMailItem_prompt")
//  String regole_protocollazione_automatica_caselle_pec_peo_parolaInTestoMailItem_prompt(); 
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_detail_new_title")
  String regole_protocollazione_automatica_caselle_pec_peo_detail_new_title(); 
 
  @Key("regole_protocollazione_automatica_caselle_pec_peo_detail_edit_title")
  String regole_protocollazione_automatica_caselle_pec_peo_detail_edit_title(String attribute0);
 
  @Key("regole_protocollazione_automatica_caselle_pec_peo_detail_view_title")
  String regole_protocollazione_automatica_caselle_pec_peo_detail_view_title(String attribute0);
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgValidoItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_flgValidoItem_title();
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_descUtenteInsItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_descUtenteInsItem_title();
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_dataInsItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_dataInsItem_title();
    
  @Key("regole_protocollazione_automatica_caselle_pec_peo_descUtenteUltModItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_descUtenteUltModItem_title();
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_dataUltModItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_dataUltModItem_title();
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_recProtettoItem_title")
  String regole_protocollazione_automatica_caselle_pec_peo_recProtettoItem_title();
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgValido_0_value")
  String regole_protocollazione_automatica_caselle_pec_peo_flgValido_0_value();
  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgValido_1_value")
  String regole_protocollazione_automatica_caselle_pec_peo_flgValido_1_value();
  
		  
  @Key("regole_protocollazione_automatica_caselle_pec_peo_flgAnnullato_1_value")
  String regole_protocollazione_automatica_caselle_pec_peo_flgAnnullato_1_value();
		 
  @Key("regole_protocollazione_automatica_caselle_pec_peo_recProtetto_1_value")
  String regole_protocollazione_automatica_caselle_pec_peo_recProtetto_1_value();
  
  //###############################################################
  //#				STATISTICHE TRASPARENZA AMMINISTRATIVA        #
  //###############################################################
  
  @Key("statisticheTrasparenzaAmministrativa_window_title")
  String statisticheTrasparenzaAmministrativa_window_title();

  //#############################################################
  //#                      Invio raccomandate                   #
  //#############################################################
  
  @Key("invio_raccomandate_idPoste_title")
  String invio_raccomandate_idPoste_title();

  @Key("invio_raccomandate_tipo_title")
  String invio_raccomandate_tipo_title();

  @Key("invio_raccomandate_numeroProtocollo_title")
  String invio_raccomandate_numeroProtocollo_title();

  @Key("invio_raccomandate_dataProtocollo_title")
  String invio_raccomandate_dataProtocollo_title();

  @Key("invio_raccomandate_anno_title")
  String invio_raccomandate_anno_title();

  @Key("invio_raccomandate_dataInvio_title")
  String invio_raccomandate_dataInvio_title();

  @Key("invio_raccomandate_statoLavorazioneLotto_title")
  String invio_raccomandate_statoLavorazioneRacc_title();

  @Key("invio_raccomandate_dataAggiornamentoStato_title")
  String invio_raccomandate_dataAggiornamentoStato_title();

  @Key("invio_raccomandate_datiMittente_title")
  String invio_raccomandate_datiMittente_title();

  @Key("invio_raccomandate_destinatario_title")
  String invio_raccomandate_destinatario_title();

  @Key("invio_raccomandate_importoTotale_title")
  String invio_raccomandate_importoTotale_title();

  @Key("invio_raccomandate_imponibile_title")
  String invio_raccomandate_imponibile_title();

  @Key("invio_raccomandate_iva_title")
  String invio_raccomandate_iva_title();

  @Key("invio_raccomandate_nroRaccomandata_title")
  String invio_raccomandate_nroRaccomandata_title();
  
  @Key("invio_raccomandate_nroLettera_title")
  String invio_raccomandate_nroLettera_title();

  @Key("invio_raccomandate_dataLotto_title")
  String invio_raccomandate_dataLotto_title();

  @Key("invio_raccomandate_idRicevuta_title")
  String invio_raccomandate_idRicevuta_title();

  @Key("invio_raccomandate_epm_ts_title")
  String invio_raccomandate_epm_ts_title();

  @Key("invio_raccomandate_epm_key_title")
  String invio_raccomandate_epm_key_title();

  //#############################################################
  //#                          Altro                            #
  //#############################################################

  @Key("ckEditorHtmlCorrection_message")
  String ckEditorHtmlCorrection_message();
  
  @Key("pannelloLeggendaDinamica_title")
  String pannelloLeggendaDinamica_title(); 

  @Key("annulla_archiviazione_label_button")
  String annulla_archiviazione_label_button(); 

  @Key("associa_invio_email_indirizzo_destinatario")
  String associa_invio_email_indirizzo_destinatario(); 

  @Key("bozze_creanuovabozza_salva")
  String bozze_creanuovabozza_salva(); 

  @Key("cancella_firma_button_prompt")
  String cancella_firma_button_prompt(); 

  @Key("combo_attivitaFlusso_title")
  String combo_attivitaFlusso_title(); 

  @Key("gestioneutenti_clienti_list_codaccountbillingItem_title")
  String gestioneutenti_clienti_list_codaccountbillingItem_title(); 

  @Key("language")
  String language(); 

  @Key("recordNonSelezionabileXFinalita_message")
  String recordNonSelezionabileXFinalita_message(); 

  @Key("rilascia_label_button")
  String rilascia_label_button(); 

  @Key("variazionidatireg_window_title")
  String variazionidatireg_window_title(String attribute0);
  
  @Key("salvataggioWaitPopup_message")
  String salvataggioWaitPopup_message();
  
  @Key("generazioneDocumentoWaitPopup_message")
  String generazioneDocumentoWaitPopup_message();
  
  @Key("avvioIterAttiWaitPopup_message")
  String avvioIterAttiWaitPopup_message();
  
  @Key("backButton_prompt")
  String backButton_prompt();
  
  @Key("messaggioRichiestaLogin")
  String messaggioRichiestaLogin();

}