/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Interfaccia che espone i le costanti delle label da inserire nelle gui
 * 
 * @author michele
 *
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {

	/************************************************************
	 * GENERALI *
	 ************************************************************/
	
	@Key("operators_recursivelyTitle")
	String operators_recursivelyTitle();

	@Key("operators_tipoTitle")
	String operators_tipoTitle();

	@Key("operators_allTheWordsTitle")
	String operators_allTheWordsTitle();

	@Key("operators_oneOrMoreWordsTitle")
	String operators_oneOrMoreWordsTitle();

	@Key("operators_wordsStartWithTitle")
	String operators_wordsStartWithTitle();

	@Key("operators_likeTitle")
	String operators_likeTitle();
	
	@Key("operators_lastNDaysTitle")
	String operators_lastNDaysTitle();

	@Key("list_noSearchMessage")
	String list_noSearchMessage();

	@Key("list_emptyMessage")
	String list_emptyMessage();
	
	@Key("list_emptyMessage_ricercaMailArchiviate_corrente_FilterCMMI")
	String list_emptyMessage_ricercaMailArchiviate_corrente_FilterCMMI();
	
	@Key("list_emptyMessage_ricercaMailArchiviate_corrente_Filter")
	String list_emptyMessage_ricercaMailArchiviate_corrente_Filter();
	
	@Key("list_emptyMessage_ricercaMailArchiviateFilter_CMMI")
	String list_emptyMessage_ricercaMailArchiviateFilter_CMMI();
	
	@Key("list_emptyMessage_ricercaMailArchiviateFilter")
	String list_emptyMessage_ricercaMailArchiviateFilter();	

	@Key("tree_nodoNonEsplodibile_message")
	String tree_nodoNonEsplodibile_message();

	@Key("filtriTipologiaButton_title")
	String filtriTipologiaButton_title();

	
	@Key("searchButton_title")
	String searchButton_title();

	@Key("clearButton_title")
	String clearButton_title();

	@Key("removeValoreButton_Title")
	String removeValoreButton_Title();

	@Key("saveLayoutFiltroButton_prompt")
	String saveLayoutFiltroButton_prompt();

	@Key("saveFilterButton_title")
	String saveFilterButton_title();

	@Key("saveLayoutListaButton_prompt")
	String saveLayoutListaButton_prompt();

	@Key("newButton_prompt")
	String newButton_prompt();

	@Key("refreshListButton_prompt")
	String refreshListButton_prompt();

	@Key("multiselectOnButton_prompt")
	String multiselectOnButton_prompt();

	@Key("multiselectOffButton_prompt")
	String multiselectOffButton_prompt();

	@Key("exportButton_prompt")
	String exportButton_prompt();

	@Key("filtroAttivoImg_prompt")
	String filtroAttivoImg_prompt();

	@Key("detailAutoOnButton_prompt")
	String detailAutoOnButton_prompt();

	@Key("detailAutoOffButton_prompt")
	String detailAutoOffButton_prompt();

	@Key("detailButton_prompt")
	String detailButton_prompt();

	@Key("modifyButton_prompt")
	String modifyButton_prompt();

	@Key("deleteButton_prompt")
	String deleteButton_prompt();

	@Key("altreOpButton_prompt")
	String altreOpButton_prompt();

	@Key("selezionaButton_prompt")
	String selezionaButton_prompt();

	@Key("deleteMultiButton_prompt")
	String deleteMultiButton_prompt();

	@Key("saveLayoutButton_title")
	String saveLayoutButton_title();

	@Key("saveButton_prompt")
	String saveButton_prompt();

	@Key("reloadDetailButton_prompt")
	String reloadDetailButton_prompt();

	@Key("undoButton_prompt")
	String undoButton_prompt();

	@Key("backToListButton_prompt")
	String backToListButton_prompt();

	@Key("savePreferenceButton_title")
	String savePreferenceButton_title();
	
	@Key("askDeletePreference")
	String askDeletePreference();
	
	@Key("askDeleteRicercaPreferita")
	String askDeleteRicercaPreferita();

	@Key("newDetail_titlePrefix")
	String newDetail_titlePrefix();

	@Key("editDetail_titlePrefix")
	String editDetail_titlePrefix();

	@Key("viewDetail_titlePrefix")
	String viewDetail_titlePrefix();

	@Key("colonneMenuItem_title")
	String colonneMenuItem_title();

	@Key("raggruppaPerMenuItem_titlePrefix")
	String raggruppaPerMenuItem_titlePrefix();

	@Key("groupingModePerGiorno_title")
	String groupingModePerGiorno_title();

	@Key("groupingModePerMese_title")
	String groupingModePerMese_title();

	@Key("groupingModePerAnno_title")
	String groupingModePerAnno_title();

	@Key("decodificaMese_1")
	String decodificaMese_1();

	@Key("decodificaMese_2")
	String decodificaMese_2();

	@Key("decodificaMese_3")
	String decodificaMese_3();

	@Key("decodificaMese_4")
	String decodificaMese_4();

	@Key("decodificaMese_5")
	String decodificaMese_5();

	@Key("decodificaMese_6")
	String decodificaMese_6();

	@Key("decodificaMese_7")
	String decodificaMese_7();

	@Key("decodificaMese_8")
	String decodificaMese_8();

	@Key("decodificaMese_9")
	String decodificaMese_9();

	@Key("decodificaMese_10")
	String decodificaMese_10();

	@Key("decodificaMese_11")
	String decodificaMese_11();

	@Key("decodificaMese_12")
	String decodificaMese_12();

	@Key("linkResetPasswordItem_value")
	String linkResetPasswordItem_value();

	// Messaggi per resettare la password
	@Key("resetPasswordAsk_message")
	String resetPasswordAsk_message();

	@Key("resetPasswordShowWaitPopup_message")
	String resetPasswordShowWaitPopup_message();

	@Key("resetPasswordEsitoOK_message")
	String resetPasswordEsitoOK_message();

	@Key("resetPasswordEsitoOK_WithPassword_message")
	String resetPasswordEsitoOK_WithPassword_message(String newPassword);

	// Messaggi per creare una nuova password
	@Key("creaPasswordAsk_message")
	String creaPasswordAsk_message();

	@Key("creaPasswordShowWaitPopup_message")
	String creaPasswordShowWaitPopup_message();

	@Key("creaPasswordEsitoOK_message")
	String creaPasswordEsitoOK_message();

	@Key("usernameRequired_message")
	String usernameRequired_message();

	@Key("emailRequired_message")
	String emailRequired_message();
	
	@Key("usernameItem_title")
	String usernameItem_title();

	@Key("passwordItem_title")
	String passwordItem_title();

	@Key("loginButton_title")
	String loginButton_title();

	@Key("loginWindow_title")
	String loginWindow_title();

	@Key("loginError_message")
	String loginError_message();

	@Key("loginErrorNessunProfiloAssociato_message")
	String loginErrorNessunProfiloAssociato_message();

	@Key("passwordScadutaError_message")
	String passwordScadutaError_message();

	@Key("requiredFieldError_message")
	String requiredFieldError_message();

	@Key("validateError_message")
	String validateError_message();

	@Key("afterSave_message")
	String afterSave_message(String estremi);

	@Key("afterDelete_message")
	String afterDelete_message(String estremi);

	@Key("saveError_message")
	String saveError_message(String estremi);

	@Key("deleteError_message")
	String deleteError_message(String estremi);

	@Key("duplicateFilter_message")
	String duplicateFilter_message();

	@Key("noSelectedRecords_message")
	String noSelectedRecords_message();

	@Key("ricercaSelectItem_title")
	String ricercaSelectItem_title();

	@Key("maxRecordVisualizzabiliItem_title")
	String maxRecordVisualizzabiliItem_title();
	
	@Key("nroRecordXPagina_title")
	String nroRecordXPagina_title();

	@Key("nroPagina_title")
	String nroPagina_title();
	
	@Key("layoutSelectItem_title")
	String layoutSelectItem_title();

	@Key("addToPreferiti_prompt")
	String addToPreferiti_prompt();

	@Key("removeFromPreferiti_prompt")
	String removeFromPreferiti_prompt();

	@Key("setHomepageMenuItem_title")
	String setHomepageMenuItem_title();

	@Key("saveDimPosMenuItem_title")
	String saveDimPosMenuItem_title();

	@Key("autoSearchMenuItem_title")
	String autoSearchMenuItem_title();

	@Key("afterSetHomepage_message")
	String afterSetHomepage_message();

	@Key("afterSaveDimPos_message")
	String afterSaveDimPos_message();

	@Key("afterAttivaAutoSearch_message")
	String afterAttivaAutoSearch_message();

	@Key("afterDisattivaAutoSearch_message")
	String afterDisattivaAutoSearch_message();

	@Key("nroRecordLabel_prefix")
	String nroRecordLabel_prefix();

	@Key("windowDeleteAsk_title")
	String windowDeleteAsk_title();

	@Key("menu_prompt")
	String menu_prompt();

	@Key("welcome")
	String welcome(String userid);

	@Key("welcomeDelega")
	String welcomeDelega(String userid, String delega);

	@Key("logoutButton_prompt")
	String logoutButton_prompt();

	@Key("deleteButtonAsk_message")
	String deleteButtonAsk_message();

	@Key("saveLayoutButtonAskForValue_message")
	String saveLayoutButtonAskForValue_message();

	@Key("addButton_prompt")
	String addButton_prompt();

	@Key("removeButton_prompt")
	String removeButton_prompt();

	@Key("filteredSelectItem_filterButton_prompt")
	String filteredSelectItem_filterButton_prompt();

	@Key("emptyPickListDimNonStdMessage")
	String emptyPickListDimNonStdMessage();

	@Key("emptyPickListDimOrganigrammaNonStdMessage")
	String emptyPickListDimOrganigrammaNonStdMessage();

	@Key("emptyPickListComuniNonStdMessage")
	String emptyPickListComuniNonStdMessage();

	// Zona form date chooser e windowDatechooser
	@Key("formDateChooser_start")
	String formDateChooser_start();

	@Key("formDateChooser_end")
	String formDateChooser_end();

	@Key("formChooser_ok")
	String formChooser_ok();

	@Key("formChooser_clear")
	String formChooser_clear();

	@Key("formChooser_cancel")
	String formChooser_cancel();

	@Key("formDateChooser_dateError")
	String formDateChooser_dateError();

	@Key("windowDateChooser_title")
	String windowDateChooser_title();

	@Key("formNumberChooser_start")
	String formNumberChooser_start();

	@Key("formNumberChooser_end")
	String formNumberChooser_end();

	@Key("formNumberChooser_numberError")
	String formNumberChooser_numberError();

	@Key("windowNumbereChooser_title")
	String windowNumbereChooser_title();

	@Key("selectItemFiltrableEditorNumber_minoreDi")
	String selectItemFiltrableEditorNumber_minoreDi(String numer);

	@Key("selectItemFiltrableEditorNumber_maggioreDi")
	String selectItemFiltrableEditorNumber_maggioreDi(String numer);

	@Key("selectItemFiltrableEditorNumber_intervallo")
	String selectItemFiltrableEditorNumber_intervallo(String start, String end);

	@Key("selectItemFiltrableEditorData_minoreDi")
	String selectItemFiltrableEditorData_minoreDi(String numer);

	@Key("selectItemFiltrableEditorData_maggioreDi")
	String selectItemFiltrableEditorData_maggioreDi(String numer);

	@Key("selectItemFiltrableEditorData_intervallo")
	String selectItemFiltrableEditorData_intervallo(String start, String end);

	@Key("estremiRegistrazioneItem_sigla_title")
	String estremiRegistrazioneItem_sigla_title();

	@Key("estremiRegistrazioneItem_anno_title")
	String estremiRegistrazioneItem_anno_title();

	@Key("estremiRegistrazioneItem_numero_title")
	String estremiRegistrazioneItem_numero_title();

	@Key("progressBarWindow_title")
	String progressBarWindow_title();

	@Key("progressBarWindow_interrupt_message")
	String progressBarWindow_interrupt_message();
	
	@Key("uploadProgressBarWindow_title")
	String uploadProgressBarWindow_title();

	@Key("prettyFileUploadInput_title")
	String prettyFileUploadInput_title();

	// ottavio
	@Key("afterOperazioneOK_message")
	String afterOperazioneOK_message();

	@Key("afterOperazioneKO_message")
	String afterOperazioneKO_message();
	
	@Key("navigationContextMenu_ricaricaAlberoMenuItem_title")
	String navigationContextMenu_ricaricaAlberoMenuItem_title();

	@Key("navigationContextMenu_cercaMenuItem_title")
	String navigationContextMenu_cercaMenuItem_title();

	@Key("navigationContextMenu_impostaComeRadiceDefaultMenuItem_title")
	String navigationContextMenu_impostaComeRadiceDefaultMenuItem_title();

	@Key("afterImpostaComeRadiceDefault_message")
	String afterImpostaComeRadiceDefault_message();

	@Key("navigatorPercorsoItem_title")
	String navigatorPercorsoItem_title();

	@Key("navigatorVaiAlLivSupItem_prompt")
	String navigatorVaiAlLivSupItem_prompt();

	@Key("filterFullTextItem_parole_prompt")
	String filterFullTextItem_parole_prompt();

	@Key("filterFullTextItem_attributi_title")
	String filterFullTextItem_attributi_title();

	@Key("filterFullTextItem_flgRicorsiva_title")
	String filterFullTextItem_flgRicorsiva_title();

	@Key("protocollazione_message_registrazione_generale_esito_OK_value")
	String protocollazione_message_registrazione_generale_esito_OK_value(String numeroRegistrazione, String annoRegistrazione);

	@Key("protocollazione_message_registrazione_interna_esito_OK_value")
	String protocollazione_message_registrazione_interna_esito_OK_value(String numeroRegistrazione, String annoRegistrazione);

	@Key("protocollazione_message_modifica_registrazione_esito_OK_value")
	String protocollazione_message_modifica_registrazione_esito_OK_value(String numeroRegistrazione, String annoRegistrazione);

	@Key("selezione_formato_file_esportazione")
	String selezione_formato_file_esportazione();

	@Key("operazioneInCorso_message")
	String operazioneInCorso_message();
	  
	@Key("backButton_prompt")
	String backButton_prompt();
	
	//################################################################
	//#                          Donwload                            #
	//################################################################
	  	  
	@Key("download_alertDimensioneFile_message")
	String download_alertDimensioneFile_message(String desUo);
	 
	@Key("download_alertDimensioneFile_procedi")
	String download_alertDimensioneFile_procedi();
	
	@Key("download_alertDimensioneFile_annulla")
	String download_alertDimensioneFile_annulla();
	
	//##############################################################
	//#                          Messaggi                          #
	//##############################################################
	
	@Key("beforeSearch_postaElettronica_dataInvio_meseMax")
	String beforeSearch_postaElettronica_dataInvio_meseMax();
	
	@Key("beforeSearch_postaElettronica_ricercaTestualeContains")
	String beforeSearch_postaElettronica_ricercaTestualeContains(String nomeFiltro);
	
	@Key("beforeSearch_postaElettronica_ricercaTestualeMinore5caratteri")
	String beforeSearch_postaElettronica_ricercaTestualeMinore5caratteri();
	
	@Key("beforeSearch_postaElettronica_ricercaMailArchiviate")
	String beforeSearch_postaElettronica_ricercaMailArchiviate(String cliente);
	
	@Key("beforeSearch_archivio_data_meseMax")
	String beforeSearch_archivio_data_meseMax();
	
	/***********************************************************/

	@Key("language")
	String language();


	@Key("json_invalidCharacters")
	String json_invalidCharacters();
	
	  
    @Key("gestioneModelli_cancellazione_ask")
    String gestioneModelli_cancellazione_ask(); 
}