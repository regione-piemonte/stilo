/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

public class ProtocollazioneUtil {
	
	public static RegistrazioneMultiplaUscitaDetail buildRegistrazioneMultiplaUscitaDetail(Record recordDettaglio) {
		return new RegistrazioneMultiplaUscitaDetail("registrazione_multipla_uscita");
	}
	
	public static ProtocollazioneDetailEntrata buildProtocollazioneDetailEntrata(Record recordDettaglio) {
		return buildProtocollazioneDetailEntrata(recordDettaglio, null);
	}
	
	public static ProtocollazioneDetailEntrata buildProtocollazioneDetailEntrata(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailEntrata(recordDettaglio, afterRegistraCallback, null);
	}

	public static ProtocollazioneDetailEntrata buildProtocollazioneDetailEntrata(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		if (recordDettaglio != null && recordDettaglio.getAttributeAsBoolean("protocolloAccessoAttiSueDaEmail")) {
			return new ProtocollazioneDetailAccessoAttiSueDaEmail("protocollazione_accesso_atti_sue") {
				
				@Override
				public boolean detailSectionCanaleDataRicezioneToShowOpen() {
					return false;
				}
				
				@Override
				public boolean detailSectionSupportoOriginaleToShowOpen() {
					return false;
				}
				
				@Override
				public boolean isModalitaWizard() {
					return false;
				}
				
				@Override
				public boolean isModalitaAllegatiGrid() {
					return isAttivaModalitaAllegatiGrid(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionDelegato() {
					return isPresenteDelegato(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionFirmatari() {
					return isPresentiFirmatari(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionPubblicazione() {
					return isPresentePubblicazione(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionRipubblicazione() {
					return isPresenteRipubblicazione(recordDettaglio);
				}
				
				@Override
				protected void afterRegistra(Record recordCallback) {
					EditaProtocolloWindowFromMail lEditaProtocolloWindowFromMail = getEditaProtocolloWindowFromMail();
					if (lEditaProtocolloWindowFromMail != null) {
						lEditaProtocolloWindowFromMail.archiviaMailFromProtocollazioneAccessoAttiSue(recordDettaglio);
					}
					if (afterRegistraCallback != null) {
						afterRegistraCallback.execute(recordCallback);
					}
				}
				
				@Override
				protected void afterUpdate(Record recordCallback) {
					if (afterUpdateCallback != null) {
						afterUpdateCallback.execute(recordCallback);
					}
				}
	
//				@Override
//				protected void afterRegistra(Record recordCallback) {
//					EditaProtocolloWindowFromMail modalWindow = getEditaProtocolloWindowFromMailwindow();
//					if (getDettaglioPostaElettronica() != null) {
//						// Ho chiamato la protocollazione sue da dettaglio mail
//						if (modalWindow != null) {
//							modalWindow.markForDestroy();
//						}
//						final DettaglioPostaElettronica dettaglioPostaElettronica = getDettaglioPostaElettronica();
//						if (dettaglioPostaElettronica != null) {
//							SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
//								
//								@Override
//								public void execute(Boolean value) {
//									if (value) {
//										dettaglioPostaElettronica.actionArchiviaMail();
//									}
//								}
//							});
//						}
////						if (dettaglioPostaElettronica != null && dettaglioPostaElettronica.getLayout() != null) {
////							dettaglioPostaElettronica.getLayout().doSearch();
////							dettaglioPostaElettronica.markForDestroy();
////						}
//					} else if (modalWindow != null && modalWindow.externalLayout){
//					if (modalWindow != null) {
//						modalWindow.markForDestroy();
//						if (dettaglioPostaElettronica != null) {
//							SC.ask(I18NUtil.getMessages().postaElettronica__list_askChiusuraMailField(), new BooleanCallback() {
//								
//								@Override
//								public void execute(Boolean value) {
//									if (value) {
//										dettaglioPostaElettronica.actionArchiviaMail();
//									}
//									modalWindow.manageAfterCloseWindow();
//								}
//							});
//						}
//						modalWindow.manageAfterCloseWindow();
//					}
//				}
				
				// Metodo per testare il reload dopo la protocollazione ma senza protocollare
//				@Override
//				public void clickSalvaRegistra() {
//					EditaProtocolloWindowFromMail modalWindow = getEditaProtocolloWindowFromMailwindow();
//					if (modalWindow != null) {
//						modalWindow.archiviaMailFromProtocollazioneAccessoAttiSue(recordDettaglio);
//					}
					
					
//					EditaProtocolloWindowFromMail modalWindow = getEditaProtocolloWindowFromMailwindow();
//					if (getDettaglioPostaElettronica() != null) {
//						if (modalWindow != null) {
//							modalWindow.markForDestroy();
//						}
//						DettaglioPostaElettronica dettaglioPostaElettronica = getDettaglioPostaElettronica();
//						if (dettaglioPostaElettronica != null && dettaglioPostaElettronica.getLayout() != null) {
//							dettaglioPostaElettronica.getLayout().doSearch();
//							dettaglioPostaElettronica.markForDestroy();
//						}
//					}
//					if (modalWindow != null) {
//						modalWindow.markForDestroy();
//						modalWindow.manageAfterCloseWindow();
//					}
//				}
				
			};
		} else {
			return new ProtocollazioneDetailEntrata("protocollazione_entrata") {
				
				@Override
				public boolean isRichiestaAccessoCivico() {	
					String siglaAccessoCivico = AurigaLayout.getParametroDB("SIGLA_REGISTRO_ACCESSO_CIVICO");
					if(recordDettaglio != null) {
						String tipoNumerazionePrincipale = recordDettaglio.getAttribute("tipoProtocollo") != null
								? recordDettaglio.getAttribute("tipoProtocollo") : "";
						String siglaNumerazionePrincipale = recordDettaglio.getAttribute("siglaProtocollo") != null
								? recordDettaglio.getAttribute("siglaProtocollo") : "";
						String siglaNumerazioneSecondaria = recordDettaglio.getAttribute("siglaNumerazioneSecondaria") != null
								? recordDettaglio.getAttribute("siglaNumerazioneSecondaria") : ""; 
						if ((tipoNumerazionePrincipale.equalsIgnoreCase("PG") && !"".equals(siglaAccessoCivico) &&
								siglaNumerazioneSecondaria.equalsIgnoreCase(siglaAccessoCivico)) ||
							 (!"".equals(siglaAccessoCivico) &&
							 siglaNumerazionePrincipale.equalsIgnoreCase(siglaAccessoCivico))) {
							return true;
						}
					}
					return super.isRichiestaAccessoCivico();
				}
				
				@Override
				public boolean isRichiestaContratti() {
					String siglaContratti = AurigaLayout.getParametroDB("SIGLA_REGISTRO_CONTRATTI");
					if(recordDettaglio != null) {
						String tipoNumerazionePrincipale = recordDettaglio.getAttribute("tipoProtocollo") != null
								? recordDettaglio.getAttribute("tipoProtocollo") : "";
						String siglaNumerazionePrincipale = recordDettaglio.getAttribute("siglaProtocollo") != null
								? recordDettaglio.getAttribute("siglaProtocollo") : "";
						String siglaNumerazioneSecondaria = recordDettaglio.getAttribute("siglaNumerazioneSecondaria") != null
								? recordDettaglio.getAttribute("siglaNumerazioneSecondaria") : "";
						if ((tipoNumerazionePrincipale.equalsIgnoreCase("PG") && !"".equals(siglaContratti)
								&& siglaNumerazioneSecondaria.equalsIgnoreCase(siglaContratti)) ||
							 (!"".equals(siglaContratti) &&
							 siglaNumerazionePrincipale.equalsIgnoreCase(siglaContratti))) {
							return true;
						}
					}
					return super.isRichiestaContratti();
				}
				
				@Override
				public boolean isModalitaWizard() {
					return isAttivoProtocolloWizard(recordDettaglio);
				}
				
				@Override
				public boolean isModalitaAllegatiGrid() {
					return isAttivaModalitaAllegatiGrid(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionDelegato() {
					return isPresenteDelegato(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionFirmatari() {
					return isPresentiFirmatari(recordDettaglio);
				}

				@Override
				public boolean showDetailSectionPubblicazione() {
					return isPresentePubblicazione(recordDettaglio);
				}
				
				@Override
				public boolean showDetailSectionRipubblicazione() {
					return isPresenteRipubblicazione(recordDettaglio);
				}
	
				@Override
				protected void afterRegistra(Record recordCallback) {
					if (afterRegistraCallback != null) {
						afterRegistraCallback.execute(recordCallback);
					}
				}
				
				@Override
				protected void afterUpdate(Record recordCallback) {
					if (afterUpdateCallback != null) {
						afterUpdateCallback.execute(recordCallback);
					}
				}
			};
		}
	}
	
	public static ProtocollazioneDetailUscita buildProtocollazioneDetailUscita(Record recordDettaglio) {
		return buildProtocollazioneDetailUscita(recordDettaglio, null);
	}

	public static ProtocollazioneDetailUscita buildProtocollazioneDetailUscita(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailUscita(recordDettaglio, afterRegistraCallback, null);
	}
	
	public static ProtocollazioneDetailUscita buildProtocollazioneDetailUscita(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		
		return new ProtocollazioneDetailUscita("protocollazione_uscita") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
						
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}

			@Override
			protected void afterRegistra(Record recordCallback) {
				if (afterRegistraCallback != null) {
					afterRegistraCallback.execute(recordCallback);
				}
			}
			
			@Override
			protected void afterUpdate(Record recordCallback) {
				if (afterUpdateCallback != null) {
					afterUpdateCallback.execute(recordCallback);
				}
			}
		};
	}
	
	public static ProtocollazioneDetailInterna buildProtocollazioneDetailInterna(Record recordDettaglio) {
		return buildProtocollazioneDetailInterna(recordDettaglio, null);
	}

	public static ProtocollazioneDetailInterna buildProtocollazioneDetailInterna(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailInterna(recordDettaglio, afterRegistraCallback, null);
	}

	public static ProtocollazioneDetailInterna buildProtocollazioneDetailInterna(final Record recordDettaglio, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		
		return new ProtocollazioneDetailInterna("protocollazione_interna") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}

			@Override
			protected void afterRegistra(Record recordCallback) {
				if (afterRegistraCallback != null) {
					afterRegistraCallback.execute(recordCallback);
				}
			}
			
			@Override
			protected void afterUpdate(Record recordCallback) {
				if (afterUpdateCallback != null) {
					afterUpdateCallback.execute(recordCallback);
				}
			}
		};
	}
	
	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(Record recordDettaglio) {
		return buildProtocollazioneDetailBozze(recordDettaglio, null, null, null);
	}
	
	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(Record recordDettaglio, Map<String, Object> params) {
		return buildProtocollazioneDetailBozze(recordDettaglio, params, null, null);
	}

	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(final Record recordDettaglio, final Map<String, Object> params, final ServiceCallback<Record> afterRegistraCallback) {
		return buildProtocollazioneDetailBozze(recordDettaglio, params, afterRegistraCallback, null);
	}
			
	public static ProtocollazioneDetailBozze buildProtocollazioneDetailBozze(final Record recordDettaglio, final Map<String, Object> params, final ServiceCallback<Record> afterRegistraCallback, final ServiceCallback<Record> afterUpdateCallback) {
		
		return new ProtocollazioneDetailBozze("protocollazione_bozze") {
			
			@Override
			public boolean isDocumentoRegistrato() {
				if(params != null) {
					return params.get("isDocRegistrato") != null && "true".equalsIgnoreCase((String) params.get("isDocRegistrato"));
				}				
				return false;
			}
			
			@Override
			public String getFlgTipoProv() {
				if(params != null) {
					return (String) params.get("flgTipoProv");
				} else if(recordDettaglio != null) {
					return recordDettaglio.getAttributeAsString("flgTipoProv");
				}				
				return null;
			}
			
			public Boolean getFlgTipoDocConVie() {
				if(params != null && params.get("flgTipoDocConVie") != null) {
					return (Boolean) params.get("flgTipoDocConVie");
				} else if(recordDettaglio != null) {
					return recordDettaglio.getAttributeAsBoolean("flgTipoDocConVie");
				}				
				return null;
			}
			
			public Boolean getFlgOggettoNonObblig() {
				if(params != null && params.get("flgOggettoNonObblig") != null) {
					return (Boolean) params.get("flgOggettoNonObblig");
				} else if(recordDettaglio != null) {
					return recordDettaglio.getAttributeAsBoolean("flgOggettoNonObblig");
				}				
				return null;
			}
				
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}

			@Override
			protected void afterRegistra(Record recordCallback) {
				if (afterRegistraCallback != null) {
					afterRegistraCallback.execute(recordCallback);
				}
			}
			
			@Override
			protected void afterUpdate(Record recordCallback) {
				if (afterUpdateCallback != null) {
					afterUpdateCallback.execute(recordCallback);
				} 
			}
		};		
	}
	
	public static ProtocollazioneDetail buildRepertorioDetail(String flgTipoProv, Record recordDettaglio) {
		if("E".equals(flgTipoProv))
			return buildRepertorioDetailEntrata(recordDettaglio, null);
		else if("I".equals(flgTipoProv))
			return buildRepertorioDetailInterno(recordDettaglio, null);
		else if("U".equals(flgTipoProv))
			return buildRepertorioDetailUscita(recordDettaglio, null);
		return null;
	}
	
	public static RepertorioDetailEntrata buildRepertorioDetailEntrata(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return new RepertorioDetailEntrata("repertorio_entrata") {
			
			@Override
			public boolean isRichiestaAccessoCivico() {
				String siglaAccessoCivico = AurigaLayout.getParametroDB("SIGLA_REGISTRO_ACCESSO_CIVICO");				
				if(recordDettaglio != null) {
					String repertorio = recordDettaglio.getAttribute("repertorio") != null
							? recordDettaglio.getAttribute("repertorio") : "";
					String tipoNumerazionePrincipale = recordDettaglio.getAttribute("tipoProtocollo") != null
							? recordDettaglio.getAttribute("tipoProtocollo") : "";
					String siglaNumerazionePrincipale = recordDettaglio.getAttribute("siglaProtocollo") != null
							? recordDettaglio.getAttribute("siglaProtocollo") : "";
					String siglaNumerazioneSecondaria = recordDettaglio.getAttribute("siglaNumerazioneSecondaria") != null
							? recordDettaglio.getAttribute("siglaNumerazioneSecondaria") : "";
					if ((repertorio.equalsIgnoreCase(siglaAccessoCivico)) ||
						(tipoNumerazionePrincipale.equalsIgnoreCase("PG") && siglaNumerazioneSecondaria.equalsIgnoreCase(siglaAccessoCivico)) ||
						(siglaNumerazionePrincipale.equalsIgnoreCase(siglaAccessoCivico))) {
						return true;
					}
				}
				return super.isRichiestaAccessoCivico();
			}
			
			@Override
			public boolean isRichiestaContratti() {
				String siglaContratti = AurigaLayout.getParametroDB("SIGLA_REGISTRO_CONTRATTI");
				if(recordDettaglio != null) {
					String repertorio = recordDettaglio.getAttribute("repertorio") != null
							? recordDettaglio.getAttribute("repertorio") : "";
					String tipoNumerazionePrincipale = recordDettaglio.getAttribute("tipoProtocollo") != null
							? recordDettaglio.getAttribute("tipoProtocollo") : "";
					String siglaNumerazionePrincipale = recordDettaglio.getAttribute("siglaProtocollo") != null
							? recordDettaglio.getAttribute("siglaProtocollo") : "";
					String siglaNumerazioneSecondaria = recordDettaglio.getAttribute("siglaNumerazioneSecondaria") != null
							? recordDettaglio.getAttribute("siglaNumerazioneSecondaria") : "";
					if ((repertorio.equalsIgnoreCase(siglaContratti)) ||
					   (tipoNumerazionePrincipale.equalsIgnoreCase("PG") && siglaNumerazioneSecondaria.equalsIgnoreCase(siglaContratti)) ||
					   (siglaNumerazionePrincipale.equalsIgnoreCase(siglaContratti))) {
						return true;
					}
				}
				return super.isRichiestaContratti();
			}
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
		};	
	}
	
	public static RepertorioDetailEntrata buildRepertorioDetailAccessoCivico(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return new RepertorioDetailEntrata("repertorio_accesso_civico") {
			
			@Override
			public boolean isRichiestaAccessoCivico() {	
				return true;				
			}
			
			@Override
			public boolean isRichiestaContratti() {				
				return false;
			}
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
		};	
	}

	public static RepertorioDetailUscita buildRepertorioDetailContratti(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return new RepertorioDetailUscita("repertorio_contratti") {
			
			@Override
			public boolean isRichiestaAccessoCivico() {	
				return false;				
			}
			
			@Override
			public boolean isRichiestaContratti() {				
				return true;
			}
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
		};	
	}
	
	public static RepertorioDetailInterno buildRepertorioDetailInterno(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return buildRepertorioDetailInterno(recordDettaglio, showAnnoPassatoItemFunction, null);
	}
	public static RepertorioDetailInterno buildRepertorioDetailInterno(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction,  final ServiceCallback<Record> afterRegistraCallback) {
		return new RepertorioDetailInterno("repertorio_interno") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
			
			@Override
			protected void afterRegistra(Record recordCallback) {
				if (afterRegistraCallback != null) {
					afterRegistraCallback.execute(recordCallback);
				}
			}
		};	
	}
	
	public static RepertorioDetailUscita buildRepertorioDetailUscita(final Record recordDettaglio, final ShowItemFunction showAnnoPassatoItemFunction) {
		return new RepertorioDetailUscita("repertorio_uscita") {
			
			@Override
			public boolean isModalitaWizard() {
				return isAttivoProtocolloWizard(recordDettaglio);
			}
			
			@Override
			public boolean isModalitaAllegatiGrid() {
				return isAttivaModalitaAllegatiGrid(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionDelegato() {
				return isPresenteDelegato(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionFirmatari() {
				return isPresentiFirmatari(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionPubblicazione() {
				return isPresentePubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showDetailSectionRipubblicazione() {
				return isPresenteRipubblicazione(recordDettaglio);
			}
			
			@Override
			public boolean showAnnoPassatoItem() {
				if(showAnnoPassatoItemFunction != null) {
					return showAnnoPassatoItemFunction.showItem();
				} 
				return super.showAnnoPassatoItem();
			}
		};	
	}
		
	public static boolean isAttivoProtocolloWizard(Record recordDettaglio) {
		boolean isIdUdValorizzato = recordDettaglio != null && recordDettaglio.getAttribute("idUd") != null && !"".equals(recordDettaglio.getAttribute("idUd"));
		boolean isSupportoOriginaleValorizzato = recordDettaglio != null && recordDettaglio.getAttribute("supportoOriginale") != null && !"".equals(recordDettaglio.getAttribute("supportoOriginale"));
		// con la preference ottimizzata se è una nuova protocollazione appare la maschera normale, non wizard, e il supporto è digitale (anche se non si vede)
		if(!isIdUdValorizzato && !AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_PROT") && AurigaLayout.getAttivaProtOttimizzataAllegati()) {
			return false;
		}	
		// altrimenti se è una nuova protocollazione o il supporto originale e valorizzato apro la modalita wizard, se è attiva
		if(!isIdUdValorizzato || isSupportoOriginaleValorizzato) {
			return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PROTOCOLLO_WIZARD");
		}
		return false;
	}

	public static boolean isAttivaModalitaAllegatiGrid(Record recordDettaglio) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_PROT")) {
			return true;
		} else if(AurigaLayout.getAttivaProtOttimizzataAllegati()) {
			return true;
		}		
		if(AurigaLayout.getParametroDB("NRO_ALLEGATI_PROT_X_MODALITA_GRID") != null && !"".equals(AurigaLayout.getParametroDB("NRO_ALLEGATI_PROT_X_MODALITA_GRID"))) {	
			int nroAllegatiProtXModalitaGrid = Integer.parseInt(AurigaLayout.getParametroDB("NRO_ALLEGATI_PROT_X_MODALITA_GRID"));
			if(recordDettaglio != null && nroAllegatiProtXModalitaGrid > 0 && recordDettaglio.getAttributeAsRecordList("listaAllegati") != null && recordDettaglio.getAttributeAsRecordList("listaAllegati").getLength() > nroAllegatiProtXModalitaGrid) {
				return true;
			}
		}			
		return false;
	}
	
	public static boolean isPresenteDelegato(Record recordDettaglio) {
		RecordList listaDelegato = recordDettaglio != null ? recordDettaglio.getAttributeAsRecordList("listaDelegato") : null;
		return listaDelegato != null && listaDelegato.getLength() > 0;		
	}
	
	public static boolean isPresentiFirmatari(Record recordDettaglio) {
		RecordList listaFirmatari = recordDettaglio != null ? recordDettaglio.getAttributeAsRecordList("listaFirmatari") : null;
		return listaFirmatari != null && listaFirmatari.getLength() > 0;			
	}
	
	public static boolean isPresentePubblicazione(Record recordDettaglio) {
		if(recordDettaglio != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni") != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni")) {
			if(recordDettaglio.getAttribute("nroPubblicazione") != null && !"".equals(recordDettaglio.getAttribute("nroPubblicazione"))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isPresenteRipubblicazione(Record recordDettaglio) {
		if(recordDettaglio != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni") != null && recordDettaglio.getAttributeAsBoolean("flgPresenzaPubblicazioni")) {
			if(recordDettaglio.getAttribute("nroRipubblicazione") != null && !"".equals(recordDettaglio.getAttribute("nroRipubblicazione"))) {
				return true;
			}			
		}
		return false;
	}
	
	public static boolean isAttivoEsibenteSenzaWizard() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESIBENTE_SENZA_WIZARD");
	}
	
	public static boolean isAttivoInteressatiSenzaWizard() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_INTERESSATI_SENZA_WIZARD");
	}
	
	public static boolean isAttivoAltreVieSenzaWizard() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIE_SENZA_WIZARD");
	}
	
	public static boolean isDestinatarioInterno(String tipoDestinatario) {
		return (tipoDestinatario != null && ("UP".equals(tipoDestinatario) || "UOI".equals(tipoDestinatario) || "LD".equals(tipoDestinatario) || "PREF".equals(tipoDestinatario)));
	}
	
	public static void isPossibleToPostel(Integer idUd, String tipo, final ServiceCallback<Record> callback) {
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("InvioRaccomandateDataSource");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("tipo", tipo);
		lGwtRestDataSource.executecustom("isPossibleToPostel", lRecord, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (callback != null) {
						callback.execute(response.getData()[0]);
					}
				}
				
			}
		});
	}
	
}
