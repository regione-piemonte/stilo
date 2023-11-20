/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesCtrldispcapcontabilerda_adspBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGeneraprogressivoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CapitoloImportoDaVerificareBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CapitoliADSPBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ErroreRigaExcelBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ImportoCWOLNonDisponibileException;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ListaCapitoliADSPBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ListaDatiContabiliADSPBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResultVerificaImportoADSPBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PeriziaBean;
import it.eng.auriga.ui.module.layout.shared.bean.CelleExcelDatiContabiliADSPEnum;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.client.ContabilitaAttiImpl;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkProcessesCtrldispcapcontabilerda_adsp;
import it.eng.client.DmpkUtilityGeneraprogressivo;
import it.eng.document.function.bean.ContabilitaAdspDatiAttoResponse;
import it.eng.document.function.bean.ContabilitaAdspDatiCapitoloResponse;
import it.eng.document.function.bean.ContabilitaAdspDeleteAttoRequest;
import it.eng.document.function.bean.ContabilitaAdspDeleteAttoResponse;
import it.eng.document.function.bean.ContabilitaAdspDeleteRicRequest;
import it.eng.document.function.bean.ContabilitaAdspDeleteRicResponse;
import it.eng.document.function.bean.ContabilitaAdspGetAttoRequest;
import it.eng.document.function.bean.ContabilitaAdspGetAttoResponse;
import it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPRequest;
import it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPResponse;
import it.eng.document.function.bean.ContabilitaAdspGetRichiestaResponse;
import it.eng.document.function.bean.ContabilitaAdspInsertAttoRequest;
import it.eng.document.function.bean.ContabilitaAdspInsertAttoResponse;
import it.eng.document.function.bean.ContabilitaAdspInsertRicRequest;
import it.eng.document.function.bean.ContabilitaAdspInsertRicResponse;
import it.eng.document.function.bean.ContabilitaAdspResponse;
import it.eng.document.function.bean.ContabilitaAdspRichiesteContabiliRequest;
import it.eng.document.function.bean.ContabilitaAdspRichiesteContabiliResponse;
import it.eng.document.function.bean.ContabilitaAdspUpdateAttoRequest;
import it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse;
import it.eng.document.function.bean.ContabilitaAdspUpdateRicRequest;
import it.eng.document.function.bean.ContabilitaAdspUpdateRicResponse;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DatiContabiliADSPXmlBean;
import it.eng.document.function.bean.ProposteConcorrenti;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ContabilitaADSPDataSource")
public class ContabilitaADSPDataSource extends AbstractFetchDataSource<DatiContabiliBean> {

	private static final Logger logger = Logger.getLogger(ContabilitaADSPDataSource.class);
	
	
	private static final String STATO_SISTEMA_CONTABILE_ALLINEATO = "allineato";
	private static final String STATO_SISTEMA_CONTABILE_NON_ALLINEATO = "non_allineato";
	private static final String STATO_SISTEMA_CONTABILE_DA_ALLINEARE = "da_allineare";
	private static final String STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING = "da_allineare_warning";
	
	private static final String OPERAZIONE_SISTEMA_CONTABILE_INSERT = "insert";
	private static final String OPERAZIONE_SISTEMA_CONTABILE_UPDATE = "update";
	private static final String OPERAZIONE_SISTEMA_CONTABILE_DELETE = "delete";
	
//	/**RICORDARSI DI TENERE GLI INDICI ALLINEATI ANCHE NELL*EXPORT (ListaDatiContabiliADSPItem)  **/
//	private static final int CELLA_MOVIMENTO_E_U = 0;
//	private static final int CELLA_ANNO_ESERCIZIO = 1;
//	private static final int CELLA_CIG = 2;
//	private static final int CELLA_CUP = 3;
//	private static final int CELLA_CAPITOLO = 4;
//	private static final int CELLA_CONTO = 5;
//	private static final int CELLA_OPERA = 6;
//	private static final int CELLA_IMPORTO = 7;
//	private static final int CELLA_NOTE = 8;
//	private static final int CELLA_IMPONIBILE = 9;
//	
//	/** AGGIORNARE NUMERO CAMPI TOTALI SE SI AGGIUNGE UNA COLONNA**/
//	private static final int NUMERO_CAMPI = 10;


	private double valueImporto;	
	
	public NuovaPropostaAtto2CompletaBean allineamentoConSistemaContabileADSP(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		try {			
			if(bean.getListaDatiContabiliADSP()!=null && bean.getListaDatiContabiliADSP().size()>0) {
				if(bean.getFlgSavedAttoSuSistemaContabile()!=null && bean.getFlgSavedAttoSuSistemaContabile().equals(true)) {
				for(DatiContabiliADSPXmlBean movimentoContabile : bean.getListaDatiContabiliADSP()) {
					if(movimentoContabile.getStatoSistemaContabile().equals(STATO_SISTEMA_CONTABILE_NON_ALLINEATO)) {
						if(movimentoContabile.getOperazioneSistemaContabile().equals(OPERAZIONE_SISTEMA_CONTABILE_UPDATE)) {
							movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_DA_ALLINEARE);						
						}else {
							ContabilitaAdspGetRichiestaResponse response = getStatoMovimento(movimentoContabile, bean);
												
							if(response.isOk()) {
								//se il movimento è stato trovato sul sistema contabile ma i dati non coincidono
								if(response.getTipoMsg().equals("D")) {
									movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_NON_ALLINEATO);
									String valoriDisallineati ="";
									for(String valore : response.getDatiDisallineati()) {
										valoriDisallineati = valoriDisallineati + valore;
									}
									movimentoContabile.setErroreSistemaContabile(response.getTipoMsg() + "  " +  valoriDisallineati);
								}else {
									//se il movimento è stato trovato sul sistema contabile
									movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_ALLINEATO);
									movimentoContabile.setErroreSistemaContabile("");
									if(movimentoContabile.getOperazioneSistemaContabile().equals(OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
										bean.getListaDatiContabiliADSP().remove(movimentoContabile);
									}
								}	
							//se il movimento non è stato trovato sul sistema contabile
							}else {
								movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
							}
						}						
						aggiornaDBConDatiContabiliADSP(bean);						
					}	
				}
			}else{
				addMessage("Non sono ancora stati inviati i dati al sistema contabile.", "", MessageType.INFO);
			}
			}else {
				addMessage("Non sono presenti movimenti contabili", "", MessageType.INFO);
			}
		} catch(Exception e) {
			logger.error("Errore durante la verifica di allineamento col sistema contabile : " + e.getMessage(),e);
			throw new StoreException("Si è verificato un'errore durante l'allineamento col sistema contabile");				
		}		
		return bean;
	}
	
	private ContabilitaAdspGetRichiestaResponse getStatoMovimento(DatiContabiliADSPXmlBean movimentoContabile, NuovaPropostaAtto2CompletaBean bean)
			throws Exception {
		String progressivoAtto = getProgressivoAtto(bean);

		ContabilitaAdspInsertRicRequest input = new ContabilitaAdspInsertRicRequest();

		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setRigaAttim(Integer.parseInt(movimentoContabile.getId()));	
		
//		input.setAnnoEse(movimentoContabile.getAnnoEsercizio());
//		input.setImporto(Float.valueOf(movimentoContabile.getImporto()));
//		input.setCodCup(movimentoContabile.getCodiceCUP());
//		input.setCig(movimentoContabile.getCodiceCIG());
//		input.setEs(movimentoContabile.getFlgEntrataUscita().equals("E") ? "E" : "S");
//		input.setTipoImp(0);
//		
//
//		if (bean.getListaCIG() != null && bean.getListaCIG().size() > 0) {
//			input.setMotivoNoCig("0");
//		} else {
//			input.setMotivoNoCig("99");
//		}
//		input.setDesCig("");
//
//		input.setRagioneSociale("");
//		input.setDesCup("");
//		input.setDesImp("");
//		input.setProgSogg("");
//
//		input.setProgkeyvb("542"); // chiamare getCapitoli
		
		ContabilitaAdspGetRichiestaResponse output;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.ricercarichiesta(getLocale(), input);
		} catch (Exception e) {
			logger.error("Errore durante il recupero dello stato del movimento : " + e.getMessage(),e);
			throw new Exception(e);
		}
		
		return output;

	}

	public ContabilitaAdspInsertAttoResponse inserisciAtto (NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(loginBean.getSchema());
		
		String cf = getCodiceFiscaleUtente();
		
		String progressivoAtto = getProgressivoAtto(bean);
		
		ContabilitaAdspInsertAttoRequest input = new ContabilitaAdspInsertAttoRequest();
		input.setCodiceFiscaleOp(StringUtils.isNotBlank(cf) ? cf : "");
		
//		String codiceOpera = "";
//		
//		if(bean.getListaOpereADSP()!=null && bean.getListaOpereADSP().size()==1) {
//			codiceOpera = bean.getListaOpereADSP().get(0).getCodiceRapido();
//		}
//		if(bean.getListaCIG()!=null && bean.getListaCIG().size()==1) {
//			input.set(bean.getListaCIG().get(0).getCodiceCUP());
//		}
		
		if (bean.getListaRUPCodAppalti() != null && bean.getListaRUPCodAppalti().size() > 0) {
			input.setCodFiscaleRup(bean.getListaRUPCodAppalti().get(0).getCodFiscaleResponsabileUnicoProvvedimento());
		}
		input.setDatainser(new Date());
		input.setkStato(0);
		input.setOggettoAt(bean.getDesOgg());
		input.setTiAttoam(6);
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		
		List<ContabilitaAdspRichiesteContabiliRequest> listaMovimenti = new ArrayList<>();
		for(DatiContabiliADSPXmlBean movimentoContabile : bean.getListaDatiContabiliADSP() ) {
			ContabilitaAdspRichiesteContabiliRequest movContabSistemaContInput = new ContabilitaAdspRichiesteContabiliRequest();
			
			BigDecimal idMovimentoContabile; 
			if(StringUtils.isNotBlank(movimentoContabile.getId()) && !movimentoContabile.getId().startsWith("NEW_")) {
				idMovimentoContabile = new BigDecimal(movimentoContabile.getId());
			}else {
				idMovimentoContabile = getIdMovimentoContabile(bean.getSiglaRegProvvisoria() + progressivoAtto);
				movimentoContabile.setId(String.valueOf(idMovimentoContabile));
			}
			
			movContabSistemaContInput.setRigaAttim(idMovimentoContabile.intValue());
			movContabSistemaContInput.setAnnoEse(Integer.parseInt(movimentoContabile.getAnnoEsercizio()));
//			String str = movimentoContabile.getImporto();
//			NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN); // Looks like a US format
//			float f = nf.parse(str).floatValue();
//			String imp = Float.toString(f);
			movContabSistemaContInput.setImporto(movimentoContabile.getImporto() != null ? Double.valueOf(movimentoContabile.getImporto().replace(".", "").replace(",", ".")): null);
			movContabSistemaContInput.setCodCup(movimentoContabile.getCodiceCUP()!=null ? movimentoContabile.getCodiceCUP() : "");
			movContabSistemaContInput.setCig(movimentoContabile.getCodiceCIG()!=null ? movimentoContabile.getCodiceCIG() : "");
			movContabSistemaContInput.setEs(movimentoContabile.getFlgEntrataUscita().equals("E") ? "E" : "S");
			movContabSistemaContInput.setTipoImp(0);
			movContabSistemaContInput.setCodiceOpera(movimentoContabile.getOpera()!=null ? movimentoContabile.getOpera() : "");
			
//			if(bean.getListaCIG()!=null && bean.getListaCIG().size()>0) {
			if(StringUtils.isNotBlank(movimentoContabile.getCodiceCIG())) {
				movContabSistemaContInput.setMotivoNoCig("0");
			}else {
				movContabSistemaContInput.setMotivoNoCig("99");
			}
			movContabSistemaContInput.setDesCig("");
			
			movContabSistemaContInput.setRagioneSociale("");					
			movContabSistemaContInput.setDesCup("");
			movContabSistemaContInput.setDesImp("");
			movContabSistemaContInput.setProgSogg("");
								
			movContabSistemaContInput.setProgkeyvb(StringUtils.isNotBlank(movimentoContabile.getKeyCapitolo()) ? Integer.parseInt(movimentoContabile.getKeyCapitolo()) : null);
			
			listaMovimenti.add(movContabSistemaContInput);
			
		}
		
		input.setRichiesteContabili(listaMovimenti);

		
		ContabilitaAdspInsertAttoResponse output;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.inserisciattoric(getLocale(), input);
		} catch (Exception e) {
			logger.error("Errore WS inserisciAtto contabilita ADSP: " + e.getMessage(),e);
			throw new Exception(e);
		}
		
		if(!output.isOk()) {
			logger.error("Errore WS inserisciAtto contabilita ADSP: " + output.getMsg());
			if(output.getMsg().contains("Parametro richiesteContabili - riga ")) {
				String indiceRiga = StringUtils.substringAfter(StringUtils.substringBefore(output.getMsg(), " :"), "Parametro richiesteContabili - riga ");
				int indiceRigaErrore = Integer.valueOf(indiceRiga);
				int indice = 1;
				for(DatiContabiliADSPXmlBean movimentoContabile : bean.getListaDatiContabiliADSP()) {
					if(indice==indiceRigaErrore) {
						movimentoContabile.setErroreSistemaContabile(StringUtils.substringAfter(output.getMsg(), ": "));
						movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING);
					}else if(indice>indiceRigaErrore) {
						movimentoContabile.setErroreSistemaContabile("Errore durante il salvataggio dei dati contabili, la seguente riga non è stata ancora validata dal sistema contabile");
					}
					
					indice++;
				}
				
			}else {
				for(DatiContabiliADSPXmlBean movimentoContabile : bean.getListaDatiContabiliADSP()) {
					movimentoContabile.setErroreSistemaContabile(output.getMsg());
					movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING);
				}
			}			
			
			return output;
		}else {
			for(DatiContabiliADSPXmlBean movimentoContabile : bean.getListaDatiContabiliADSP() ) {
				movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_ALLINEATO);
				movimentoContabile.setErroreSistemaContabile("");
			}
		}

		return output;
		
	}

	private String getProgressivoAtto(NuovaPropostaAtto2CompletaBean bean) {
		String annoRegProvvisoria = bean.getDataRegProvvisoria() != null ? new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria()) : bean.getAnnoRegProvvisoria();
		Integer numeroCifre = bean.getNumeroRegProvvisoria().length();
		String padding = "";
		for(int i=numeroCifre; i<5; i++) {
			padding = padding + "0";
		}
		String progressivoAtto = annoRegProvvisoria + padding + bean.getNumeroRegProvvisoria();
		
		return progressivoAtto;
		
	}

	private BigDecimal getIdMovimentoContabile(String progressivoAtto) throws Exception {		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(loginBean.getSchema());
		
		String idDominio = null;
		if (loginBean.getDominio() != null && !"".equals(loginBean.getDominio())) {
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
		}
		
		DmpkUtilityGeneraprogressivoBean input = new DmpkUtilityGeneraprogressivoBean();
		input.setCodscopein("NUM_RICH_MOV_CONTABILI");
		input.setCondvaluein(progressivoAtto);
		input.setIdspaooin(StringUtils.isNotBlank(idDominio) ? new BigDecimal(idDominio) : null);

		DmpkUtilityGeneraprogressivo store = new DmpkUtilityGeneraprogressivo();
		StoreResultBean<DmpkUtilityGeneraprogressivoBean> output = store.execute(getLocale(), schemaBean, input);
		
		if (output.isInError()) {
			logger.error("Errore dalla store GeneraProgressivo: " + output.getDefaultMessage());
			throw new StoreException(output.getDefaultMessage());
		} 
		
		return output.getResultBean().getProgrgeneratoout();
	}

	public void inviaMovimentiContabili(NuovaPropostaAtto2CompletaBean bean) throws Exception {

		List<DatiContabiliADSPXmlBean> listaMovimentiContabiliAggiornati = new ArrayList<DatiContabiliADSPXmlBean>();
		
		for (DatiContabiliADSPXmlBean movimentoContabile : bean.getListaDatiContabiliADSP()) {		
			
			if (movimentoContabile.getStatoSistemaContabile().equals(STATO_SISTEMA_CONTABILE_DA_ALLINEARE)) {
				
				if (movimentoContabile.getOperazioneSistemaContabile().equals(OPERAZIONE_SISTEMA_CONTABILE_INSERT)) {
					inserisciMovimentoContabile(movimentoContabile, bean);
					listaMovimentiContabiliAggiornati.add(movimentoContabile);
				} else if (movimentoContabile.getOperazioneSistemaContabile()
						.equals(OPERAZIONE_SISTEMA_CONTABILE_UPDATE)) {
					aggiornaMovimentoContabile(movimentoContabile, bean);
					listaMovimentiContabiliAggiornati.add(movimentoContabile);
				} else if (movimentoContabile.getOperazioneSistemaContabile()
						.equals(OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
					boolean cancellato = false;
					/*Se è presente un solo movimento contabile devo cancellare l'intero atto dal sistema e non solo il dato contabile*/
					if(checkCancellaTuttiMovimenti(bean.getListaDatiContabiliADSP())) {
						ContabilitaAdspDeleteAttoResponse response = cancellaAtto(bean);
						if(response.isOk()) {
							bean.setFlgSavedAttoSuSistemaContabile(false);
						}else {
							/*Se il cancella atto dà errore evidenzio l'errore su tutti i dati che bisognava cancellare*/
							for(DatiContabiliADSPXmlBean movimentoContabileInError : bean.getListaDatiContabiliADSP()) {
								movimentoContabileInError.setErroreSistemaContabile(response.getMsg());
								movimentoContabileInError.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING);
							}
						}
						
						//Esco dal ciclo visto che l'operazione di cancella atto agisce su tutti i movimenti
						break;
						
					}else {
						cancellato = cancellaMovimentoContabile(movimentoContabile, bean);
					}
					if(!cancellato) {
						listaMovimentiContabiliAggiornati.add(movimentoContabile);
					}
				}
			}else {
				listaMovimentiContabiliAggiornati.add(movimentoContabile);
			}
		}	
		
		bean.setListaDatiContabiliADSP(listaMovimentiContabiliAggiornati);
	}



	public NuovaPropostaAtto2CompletaBean verificaStatoAttoContabilita(NuovaPropostaAtto2CompletaBean bean) throws StoreException {
		ContabilitaAdspGetAttoRequest input = new ContabilitaAdspGetAttoRequest();
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();

		input.setCodiceFiscaleOp(cf);
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		
		ContabilitaAdspGetAttoResponse output = null;
		Integer statoAtto = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.ricercaatto(getLocale(), input);
			
			if (!output.isOk() || output.getDati() == null) {
				throw new Exception("Non è possibile verificare lo stato dell'atto sul sistema contabile: " + output.getMsg());
			} else {
				if(StringUtils.isNotBlank(output.getDati().getkStato())) {
					statoAtto = Integer.valueOf(output.getDati().getkStato());
					bean.setStatoAttoContabilita(statoAtto);
				}				
			}		
		} catch (Exception e) {
			logger.error("Errore WS ricercaatto contabilita ADSP: " + e.getMessage(), e);	
			
			/*In caso non sono riuscito a contattare il WS, setto uno stato di errore ***/
			statoAtto = 99;
			bean.setStatoAttoContabilita(statoAtto);
			bean.setErrMsgEventoContabilia(e.getMessage());
		}
		
		return bean;
	}

	private boolean checkCancellaTuttiMovimenti(List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP) {
		for(DatiContabiliADSPXmlBean bean : listaDatiContabiliADSP) {
			if(!bean.getOperazioneSistemaContabile().equalsIgnoreCase(OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
				return false;
			}
		}
		return true;
	}

	public void aggiornaMovimentoContabile(DatiContabiliADSPXmlBean movimentoContabile, NuovaPropostaAtto2CompletaBean bean) throws Exception {
		ContabilitaAdspUpdateRicRequest input = new ContabilitaAdspUpdateRicRequest();
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();

		input.setCodiceFiscaleOp(cf);
		input.setRigaAttim(Integer.parseInt(movimentoContabile.getId()));
		input.setAnnoEse(movimentoContabile.getAnnoEsercizio());
		input.setImporto(movimentoContabile.getImporto() != null ? Double.valueOf(movimentoContabile.getImporto().replace(".", "").replace(",", ".")): null);
//		input.setCodCup(movimentoContabile.getCodiceCUP());
		input.setCig(movimentoContabile.getCodiceCIG()!=null ? movimentoContabile.getCodiceCIG() : "");
		input.setEs(movimentoContabile.getFlgEntrataUscita().equals("E") ? "E" : "S");
		input.setTipoImp(0);
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceOpera(movimentoContabile.getOpera()!=null ? movimentoContabile.getOpera() : "");

//		if(bean.getListaCIG()!=null && bean.getListaCIG().size()>0) {
		if (StringUtils.isNotBlank(movimentoContabile.getCodiceCIG())) {
			input.setMotivoNoCig("0");
		} else {
			input.setMotivoNoCig("99");
		}
		input.setDesCig("");

		input.setRagioneSociale("");
//		input.setDesCup("");
		input.setDesImp("");
		input.setProgSogg("");
		input.setProgkeyvb(movimentoContabile.getKeyCapitolo());
		
		ContabilitaAdspUpdateRicResponse output = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.aggiornarichiesta(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS aggiornarichiesta contabilita ADSP: " + output.getMsg());
				movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_NON_ALLINEATO);
				movimentoContabile.setErroreSistemaContabile(output.getMsg());
			} else {
				movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_ALLINEATO);
				movimentoContabile.setErroreSistemaContabile("");
			}
			
		} catch (Exception e) {
			logger.error("Errore WS aggiornarichiesta contabilita ADSP: " + e.getMessage(), e);
			movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_NON_ALLINEATO);
			movimentoContabile.setErroreSistemaContabile(e.getMessage());
		}
	}
	
	public ListaCapitoliADSPBean getCapitoli(CapitoliADSPBean bean) throws StoreException {
		ListaCapitoliADSPBean listaCapitoliADSPBean = new ListaCapitoliADSPBean();
		List<CapitoliADSPBean> listaCapitoliADSP = new ArrayList<CapitoliADSPBean>();
		
		String cf = getCodiceFiscaleUtente();
		
		String cdrUOCompetente = getExtraparams().get("cdrUOCompetente");
		
		if(StringUtils.isBlank(cdrUOCompetente)) {
			throw new StoreException("cdR non specificato");
		}

		ContabilitaAdspGetCapitoliBPRequest input = new ContabilitaAdspGetCapitoliBPRequest();
		input.setCodiceFiscaleOp(cf);
		input.setAnnoEsercizio(bean.getAnnoEsercizio());
		input.setAnnoRiferimento(bean.getAnnoEsercizio());
		input.setMovimento(bean.getFlgEntrataUscita().equals("E") ? "E" : "S");
		input.setCapitolo1(bean.getCapitolo1());				
		input.setCapitolo2(StringUtils.isNotBlank(bean.getCapitolo2()) ? bean.getCapitolo2().replace(".", "") : null );
		input.setAliasCDR(cdrUOCompetente);
		input.setImportoRichiesto(Double.valueOf(1));
		
		ContabilitaAdspGetCapitoliBPResponse output = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.ricercacapitolibp(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS ricercacapitolibp contabilita ADSP: " + output.getMsg());
				throw new StoreException(output.getMsg());
			} else {
				for(ContabilitaAdspDatiCapitoloResponse capitoliOutput : output.getDati()) {
					CapitoliADSPBean capitolo = new CapitoliADSPBean();
					capitolo.setAnnoEsercizio(capitoliOutput.getAnnoEsercizio());
					capitolo.setCapitolo1(capitoliOutput.getCapitoloParte1());
					capitolo.setCapitolo2(capitoliOutput.getCapitoloParte2());
//					capitolo.setDescrizioneProgramma(capitoliOutput.getDescrizioneProgramma());
					capitolo.setDescrizioneVoce(capitoliOutput.getDescrizioneVoce());
					capitolo.setKeyCapitolo(String.valueOf(capitoliOutput.getProgkeyvb()));
					
					String pdcFinanziario = "";
					if(capitoliOutput.getLiv1pf() != null && !"".equals(capitoliOutput.getLiv1pf())) {
						pdcFinanziario = capitoliOutput.getLiv1pf();
					}
					if(capitoliOutput.getLiv2pf() != null && !"".equals(capitoliOutput.getLiv2pf())) {
						if(!"".equals(pdcFinanziario)) {
							pdcFinanziario = pdcFinanziario.concat(".").concat(capitoliOutput.getLiv2pf());
						} else {
							pdcFinanziario = capitoliOutput.getLiv2pf();
						}
					} 
					if(capitoliOutput.getLiv3pf() != null && !"".equals(capitoliOutput.getLiv3pf())) {
						if(!"".equals(pdcFinanziario)) {
							pdcFinanziario = pdcFinanziario.concat(".").concat(capitoliOutput.getLiv3pf());
						} else {
							pdcFinanziario = capitoliOutput.getLiv3pf();
						}
					} 
					if(capitoliOutput.getLiv4pf() != null && !"".equals(capitoliOutput.getLiv4pf())) {
						if(!"".equals(pdcFinanziario)) {
							pdcFinanziario = pdcFinanziario.concat(".").concat(capitoliOutput.getLiv4pf());
						} else {
							pdcFinanziario = capitoliOutput.getLiv4pf();
						}
					}
					if(capitoliOutput.getLiv5pf() != null && !"".equals(capitoliOutput.getLiv5pf())) {
						if(!"".equals(pdcFinanziario)) {
							pdcFinanziario = pdcFinanziario.concat(".").concat(capitoliOutput.getLiv5pf());
						} else {
							pdcFinanziario = capitoliOutput.getLiv5pf();
						}
					}
					if(capitoliOutput.getDescrizionePianoFin() != null && !"".equals(capitoliOutput.getDescrizionePianoFin())) {
						pdcFinanziario= pdcFinanziario.concat(" - ").concat(capitoliOutput.getDescrizionePianoFin());
					}
					capitolo.setPdcFinanziario(pdcFinanziario);
					
					String programma = "";
					if(capitoliOutput.getProgramma() != null && !"".equals(capitoliOutput.getProgramma())) {
						programma = capitoliOutput.getProgramma();
					}
					if(capitoliOutput.getDescrizioneProgramma() != null && !"".equals(capitoliOutput.getDescrizioneProgramma())) {
						programma = programma.concat(" - ").concat(capitoliOutput.getDescrizioneProgramma());
					}
					capitolo.setProgramma(programma);
					
					String missione = "";
					if(capitoliOutput.getMissione() != null && !"".equals(capitoliOutput.getMissione())) {
						missione = capitoliOutput.getMissione();
					}
					if(capitoliOutput.getDescrizioneMissione() != null && !"".equals(capitoliOutput.getDescrizioneMissione())) {
						missione = missione.concat(" - ").concat(capitoliOutput.getDescrizioneMissione());
					}
					capitolo.setMissione(missione);
					
					listaCapitoliADSP.add(capitolo);
				}
			}
			
			listaCapitoliADSPBean.setListaCapitoliADSP(listaCapitoliADSP);
			
			return listaCapitoliADSPBean;
			
		} catch (Exception e) {
			logger.error("Errore WS ricercacapitolibp contabilita ADSP: " + e.getMessage(), e);
			throw new StoreException("Errore durante il caricamento dei capitoli: " + e.getMessage());
		}
		
	}
	
	public boolean cancellaMovimentoContabile(DatiContabiliADSPXmlBean movimentoContabile, NuovaPropostaAtto2CompletaBean bean) throws StoreException {
		ContabilitaAdspDeleteRicRequest input = new ContabilitaAdspDeleteRicRequest();
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		input.setRigaAttim(Integer.parseInt(movimentoContabile.getId()));
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		
		ContabilitaAdspDeleteRicResponse output = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.cancellarichiesta(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS cancellarichiesta contabilita ADSP: " + output.getMsg());
				movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_NON_ALLINEATO);
				movimentoContabile.setErroreSistemaContabile(output.getMsg());
			} else {
				movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_ALLINEATO);
				movimentoContabile.setErroreSistemaContabile("");
				return true;
			}
			
		} catch (Exception e) {
			logger.error("Errore WS cancellarichiesta contabilita ADSP: " + e.getMessage(), e);
			movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_NON_ALLINEATO);
			movimentoContabile.setErroreSistemaContabile(e.getMessage());
		}
		
		return false;
	}

	public void inserisciMovimentoContabile(DatiContabiliADSPXmlBean movimentoContabile, NuovaPropostaAtto2CompletaBean bean) throws Exception {
		ContabilitaAdspInsertRicRequest input = new ContabilitaAdspInsertRicRequest();
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();		
		
		BigDecimal idMovimentoContabile; 
		if(StringUtils.isNotBlank(movimentoContabile.getId()) && !movimentoContabile.getId().startsWith("NEW_")) {
			idMovimentoContabile = new BigDecimal(movimentoContabile.getId());
		}else {
			idMovimentoContabile = getIdMovimentoContabile(bean.getSiglaRegProvvisoria() + progressivoAtto);
			movimentoContabile.setId(String.valueOf(idMovimentoContabile));
		}
		
		input.setCodiceFiscaleOp(cf);
		input.setRigaAttim(idMovimentoContabile.intValue());
		input.setAnnoEse(movimentoContabile.getAnnoEsercizio());
		input.setImporto(movimentoContabile.getImporto() != null ? Double.valueOf(movimentoContabile.getImporto().replace(".", "").replace(",", ".")): null);
		input.setCodCup(movimentoContabile.getCodiceCUP()!= null ? movimentoContabile.getCodiceCUP() : "");
		input.setCig(movimentoContabile.getCodiceCIG()!=null ? movimentoContabile.getCodiceCIG() : "");
		input.setEs(movimentoContabile.getFlgEntrataUscita().equals("E") ? "E" : "S");
		input.setTipoImp(0);
		input.setCodiceOpera(movimentoContabile.getOpera()!=null ? movimentoContabile.getOpera() : "");
		input.setProgAtto(Integer.valueOf(progressivoAtto));

//		if(bean.getListaCIG()!=null && bean.getListaCIG().size()>0) {
		if (StringUtils.isNotBlank(movimentoContabile.getCodiceCIG())) {
			input.setMotivoNoCig("0");
		} else {
			input.setMotivoNoCig("99");
		}
		input.setDesCig("");

		input.setRagioneSociale("");
		input.setDesCup("");
		input.setDesImp("");
		input.setProgSogg("");
		input.setProgkeyvb(movimentoContabile.getKeyCapitolo());
		
		
		ContabilitaAdspInsertRicResponse output = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.inseriscirichiesta(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS inseriscirichiesta contabilita ADSP: " + output.getMsg());
				movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_NON_ALLINEATO);
				movimentoContabile.setErroreSistemaContabile(output.getMsg());
			} else {
				movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_ALLINEATO);
				movimentoContabile.setErroreSistemaContabile("");
			}
			
		} catch (Exception e) {
			logger.error("Errore WS inseriscirichiesta contabilita ADSP: " + e.getMessage(), e);
			movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_NON_ALLINEATO);
			movimentoContabile.setErroreSistemaContabile(e.getMessage());
		}
	}
	
	public ResultBean<Object> verificaDisponibilitaImporto(DatiContabiliADSPXmlBean bean) throws Exception {
		ResultBean<Object> result = new ResultBean<Object>();
		
		if (!bean.getFlgEntrataUscita().equalsIgnoreCase("E")) {

			String cf = getCodiceFiscaleUtente();

			String cdrUOCompetente = getExtraparams().get("cdrUOCompetente");
			
			String flgSenzaImpegniCont = getExtraparams().get("flgSenzaImpegniCont");
			
			String idUd = getExtraparams().get("idUd");
			
			String importoDaVerificare = StringUtils.isNotBlank(getExtraparams().get("importoDaVerificare")) ? getExtraparams().get("importoDaVerificare") : bean.getImporto();

			boolean flgDisattivaIntegrazioneSistemaContabile = StringUtils.isNotBlank(bean.getFlgDisattivaIntegrazioneSistemaContabile()) && 
					bean.getFlgDisattivaIntegrazioneSistemaContabile().equalsIgnoreCase("true") ? true : false;
			
			if (StringUtils.isBlank(cdrUOCompetente)) {
				throw new StoreException("cdR non specificato");
			}
			
			if (flgDisattivaIntegrazioneSistemaContabile) {
				/*
				 * VERIFICO IMPORTO PER GLI RDA, PER LA LOGICA VEDERE JIRA AURIGA-605
				 */

				if (flgSenzaImpegniCont.equals("false") && !getFlgDisattivaCtrlRda()) {
					try {
						String keyCapitolo = recuperaChiaveCapitolo(bean.getAnnoEsercizio(), bean.getFlgEntrataUscita(),
								bean.getCapitolo(), bean.getConto(), cdrUOCompetente);

						AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

						DmpkProcessesCtrldispcapcontabilerda_adspBean input = new DmpkProcessesCtrldispcapcontabilerda_adspBean();
						input.setCodidconnectiontokenin(loginBean.getToken());
						input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro())
								? new BigDecimal(loginBean.getIdUserLavoro())
								: null);
						input.setKeycapitolocontoin(keyCapitolo);
						input.setIdudin(StringUtils.isNotBlank(idUd)? new BigDecimal(idUd) : null);

						DmpkProcessesCtrldispcapcontabilerda_adsp store = new DmpkProcessesCtrldispcapcontabilerda_adsp();
						StoreResultBean<DmpkProcessesCtrldispcapcontabilerda_adspBean> output = store
								.execute(getLocale(), loginBean, input);

						if (output.isInError()) {
							throw new StoreException(output);
						}
						
						List<CapitoloImportoDaVerificareBean> data = new ArrayList<CapitoloImportoDaVerificareBean>();
						
						String xml = output.getResultBean().getListakeycontimpallocout();
						data = XmlListaUtility.recuperaLista(xml, CapitoloImportoDaVerificareBean.class);
						
						Double importoStoreDaAggiungere = (double) 0;
						Double importoNellaMappa = (double) 0;
						
						if(data.size()>0) {
							importoStoreDaAggiungere = Double.valueOf(data.get(0).getImporto().replace(".", "").replace(",", "."));
						}
						
						HashMap<String, String> mappaKeyCapitoli = bean.getMappaKeyCapitoli();
						if(mappaKeyCapitoli!=null && mappaKeyCapitoli.size()>0) {
							if(StringUtils.isNotBlank(mappaKeyCapitoli.get(keyCapitolo)!=null ? String.valueOf(mappaKeyCapitoli.get(keyCapitolo)) : "")) {
								importoNellaMappa = Double.valueOf(mappaKeyCapitoli.get(keyCapitolo)!=null ? String.valueOf(mappaKeyCapitoli.get(keyCapitolo)) : "");
							}
						}
						
						Double importoInseritoGUI = Double.valueOf(importoDaVerificare.replace(".", "").replace(",", "."));
						
						String importoTotaleDaVerificare = String.valueOf(importoInseritoGUI + importoStoreDaAggiungere + importoNellaMappa);
					
						callVerificaImportoCWOL(bean, result, cf, cdrUOCompetente, importoTotaleDaVerificare);	
						
						if(!result.isInError()) {
							bean.setImporto(importoDaVerificare);
							bean.setKeyCapitolo(keyCapitolo);
							bean.setDisponibilitaImporto("disponibile");
							result.setResultBean(bean);
						}else {
							List<ProposteConcorrenti> listaProposteConcorrenti = new ArrayList<ProposteConcorrenti>();						
							String xmlListaProposteConcorrenti = output.getResultBean().getListaatticoncorrentiout();
							listaProposteConcorrenti = XmlListaUtility.recuperaLista(xmlListaProposteConcorrenti, ProposteConcorrenti.class);		
							for(ProposteConcorrenti propostaConcorrente : listaProposteConcorrenti) {
								propostaConcorrente.setCapitoloProposta(bean.getCapitolo());
								propostaConcorrente.setContoProposta(bean.getConto());
							}
							
							
							bean.setListaProposteConcorrenti(listaProposteConcorrenti);
							bean.setDisponibilitaImporto("non_disponibile");
							
							if(listaProposteConcorrenti!=null && listaProposteConcorrenti.size()>0) {
								result.setDefaultMessage("Non c’è sufficiente disponibilità. Verifica proposte in iter che impattano sullo stesso capitolo/conto");
							}else {
								result.setDefaultMessage("Non c'è sufficiente disponibilità e non risultano richieste di approvvigionamento o decreti in iter che insistano sullo stesso capitolo/conto");

							}
							
							result.setResultBean(bean);
						}

					} catch (Exception e) {
						String errorMessage = "Non è stato possibile verificare la disponibilità dell'importo inserito, ERROR: " + e.getMessage();
						logger.error(errorMessage);
						result.setInError(true);
						result.setDefaultMessage(errorMessage);
					}
				}

			}else {						
				/*VERIFICO IMPORTO CHIAMANDO IL SERVIZIO DEL SISTEMA CONTABILE*/
				
				importoDaVerificare = importoDaVerificare.replace(".", "").replace(",", ".");
				
				callVerificaImportoCWOL(bean, result, cf, cdrUOCompetente, importoDaVerificare);
			}
		}
		
		return result;
	}

	/**
	 * @return
	 */
	public boolean getFlgDisattivaCtrlRda() {
		String modCtrlDispCapitoliRda = ParametriDBUtil.getParametroDB(getSession(), "MOD_CTRL_DISP_CAPITOLI_IN_RDA");
		
		if(StringUtils.isBlank(modCtrlDispCapitoliRda) || modCtrlDispCapitoliRda.equalsIgnoreCase("ASSENTE")) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * @param bean
	 * @param result
	 * @param cf
	 * @param cdrUOCompetente
	 * @param importoDaVerificare
	 * @throws NumberFormatException
	 */
	public void callVerificaImportoCWOL(DatiContabiliADSPXmlBean bean, ResultBean<Object> result, String cf,
			String cdrUOCompetente, String importoDaVerificare) throws NumberFormatException {
		ContabilitaAdspGetCapitoliBPRequest input = new ContabilitaAdspGetCapitoliBPRequest();
		input.setCodiceFiscaleOp(cf);
		input.setAnnoEsercizio(bean.getAnnoEsercizio());
		input.setAnnoRiferimento(bean.getAnnoEsercizio());
		input.setMovimento(bean.getFlgEntrataUscita().equals("E") ? "E" : "S");
		input.setCapitolo1(bean.getCapitolo());
		input.setCapitolo2(StringUtils.isNotBlank(bean.getConto()) ? bean.getConto().replace(".", "") : null);
		input.setAliasCDR(cdrUOCompetente);
		input.setImportoRichiesto(Double.valueOf(importoDaVerificare));

		ContabilitaAdspGetCapitoliBPResponse output = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.ricercacapitolibp(getLocale(), input);

			if (output.isOk() && output.getDati() != null) {
				ContabilitaAdspDatiCapitoloResponse capitolo = output.getDati().get(0);
				if (capitolo.getSommaDisponibile().equalsIgnoreCase("NO")) {
					result.setInError(true);
					result.setDefaultMessage("L'importo inserito non è disponibile");
				}else {
					result.setInError(false);
					result.setDefaultMessage("");
				}
			} else {
				throw new Exception(output.getMsg());
			}
		} catch (Exception e) {
			logger.error("Errore WS ricercacapitolibp contabilita ADSP: " + e.getMessage(), e);
			result.setInError(true);
			result.setDefaultMessage(
					"Non è stato possibile verificare la disponibilità dell'importo inserito, riprovare più tardi o contattare l'assistenza");
		}
	}
	
	public void aggiornaDBConDatiContabiliADSP(NuovaPropostaAtto2CompletaBean bean) throws Exception {

		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP = bean.getListaDatiContabiliADSP() != null ? bean.getListaDatiContabiliADSP() : new ArrayList<DatiContabiliADSPXmlBean>();
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_ADSP_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliADSP));
			String flgSavedAttoSuSistemaContabileADSP = bean.getFlgSavedAttoSuSistemaContabile() != null && bean.getFlgSavedAttoSuSistemaContabile() ? "1" : "0";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_SAVED_ATTO_SISTEMA_CONTABILE.Ud", flgSavedAttoSuSistemaContabileADSP);	
			
			if(bean.getListaDatiContabiliDefinitiviADSP() != null && bean.getListaDatiContabiliDefinitiviADSP().size()>0) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_ADSP_DEF_Doc", new XmlUtilitySerializer().createVariabileLista(bean.getListaDatiContabiliDefinitiviADSP()));
			}
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

			input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
			input.setFlgtipotargetin("D");

			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
			lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
	
			DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
	
			if (lUpddocudOutput.isInError()) {
				throw new StoreException(lUpddocudOutput);
			}			
			
		} catch (StoreException se) {
			logger.error("Errore durante l'aggiornamento dei dati contabili ADSP" + se.getMessage(), se);
			throw se;
		} catch (Exception e) {
			logger.error("Errore durante l'aggiornamento dei dati contabili ADSP" + e.getMessage(), e);
			throw new StoreException(e.getMessage());
		}	
	}
	
	@Override
	public PaginatorBean<DatiContabiliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}
	
	@Override
	public DatiContabiliBean get(DatiContabiliBean bean) throws Exception {
		return null;
	}

	@Override
	public DatiContabiliBean add(DatiContabiliBean bean) throws Exception {
		return null;
	}

	@Override
	public DatiContabiliBean remove(DatiContabiliBean bean) throws Exception {
		return null;
	}

	@Override
	public DatiContabiliBean update(DatiContabiliBean bean, DatiContabiliBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(DatiContabiliBean bean) throws Exception {
		return null;
	}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileLista(nomeVariabile, lista));
		}
	}
	
	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {	
		if(sezioneCache != null && sezioneCache.getVariabile() != null) {
			for(int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if(var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}
	
	public ContabilitaAdspUpdateAttoResponse attoAdottato(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		ContabilitaAdspUpdateAttoRequest input = new ContabilitaAdspUpdateAttoRequest();
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		String oggettoDescrittivo = bean.getDesOgg();
		String cup = null;
		if (bean.getListaDatiContabiliADSP() != null && bean.getListaDatiContabiliADSP().size() == 1) {
			cup = bean.getListaDatiContabiliADSP().get(0).getCodiceCUP();
		}
		String codFiscaleRupCodAppalti = null;
		if (bean.getListaRUPCodAppalti() != null && bean.getListaRUPCodAppalti().size() > 0) {
			codFiscaleRupCodAppalti = bean.getListaRUPCodAppalti().get(0).getCodFiscaleResponsabileUnicoProvvedimento();
		}
		String codiceOpera = null;
		if (bean.getListaOpereADSP() != null && bean.getListaOpereADSP().size() == 1) {
			codiceOpera = bean.getListaOpereADSP().get(0).getCodiceRapido();
		}
		String numeroRegistrazione = bean.getNumeroRegistrazione();
		Date dataRegistrazione = bean.getDataRegistrazione();
		
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		input.setOggettoAt(oggettoDescrittivo);
		input.setCodCup(cup);
		input.setCodFiscaleRup(codFiscaleRupCodAppalti);
//		input.setCodiceOpera(codiceOpera);
		input.setNumAtto(Integer.valueOf(numeroRegistrazione));
		input.setDataAtto(dataRegistrazione);
		
		ContabilitaAdspUpdateAttoResponse output = new ContabilitaAdspUpdateAttoResponse();
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.attoadottato(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS attoadottato contabilita ADSP: " + output.getMsg());
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			output.setOk(false);
			output.setMsg("Errore nell'invocazione dell'operazione attoadottato. " + e.getMessage());
		}
		
		return output;
	}
		
	public ContabilitaAdspUpdateAttoResponse attoConfermato(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		ContabilitaAdspUpdateAttoRequest input = new ContabilitaAdspUpdateAttoRequest();
		
		String oggettoDescrittivo = bean.getDesOgg();
		String cup = null;
		if (bean.getListaDatiContabiliADSP() != null && bean.getListaDatiContabiliADSP().size() == 1) {
			cup = bean.getListaDatiContabiliADSP().get(0).getCodiceCUP();
		}
		String codFiscaleRupCodAppalti = null;
		if (bean.getListaRUPCodAppalti() != null && bean.getListaRUPCodAppalti().size() > 0) {
			codFiscaleRupCodAppalti = bean.getListaRUPCodAppalti().get(0).getCodFiscaleResponsabileUnicoProvvedimento();
		}
		String codiceOpera = null;
		if (bean.getListaOpereADSP() != null && bean.getListaOpereADSP().size() == 1) {
			codiceOpera = bean.getListaOpereADSP().get(0).getCodiceRapido();
		}
		
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		input.setOggettoAt(oggettoDescrittivo);
		input.setCodCup(cup);
		input.setCodFiscaleRup(codFiscaleRupCodAppalti);
//		input.setCodiceOpera(codiceOpera);
		
		ContabilitaAdspUpdateAttoResponse output = new ContabilitaAdspUpdateAttoResponse();
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.attoconfermato(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS attoconfermato contabilita ADSP: " + output.getMsg());
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			output.setOk(false);
			output.setMsg("Errore nell'invocazione dell'operazione attoconfermato. " + e.getMessage());
		}
		
		return output;
	}
	
	public ContabilitaAdspUpdateAttoResponse attoInBozza(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		ContabilitaAdspUpdateAttoRequest input = new ContabilitaAdspUpdateAttoRequest();
		
		String oggettoDescrittivo = bean.getDesOgg();
		String cup = null;
		if (bean.getListaDatiContabiliADSP() != null && bean.getListaDatiContabiliADSP().size() == 1) {
			cup = bean.getListaDatiContabiliADSP().get(0).getCodiceCUP();
		}
		String codFiscaleRupCodAppalti = null;
		if (bean.getListaRUPCodAppalti() != null && bean.getListaRUPCodAppalti().size() > 0) {
			codFiscaleRupCodAppalti = bean.getListaRUPCodAppalti().get(0).getCodFiscaleResponsabileUnicoProvvedimento();
		}
		String codiceOpera = null;
		if (bean.getListaOpereADSP() != null && bean.getListaOpereADSP().size() == 1) {
			codiceOpera = bean.getListaOpereADSP().get(0).getCodiceRapido();
		}
		
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		input.setOggettoAt(oggettoDescrittivo);
		input.setCodCup(cup);
		input.setCodFiscaleRup(codFiscaleRupCodAppalti);
//		input.setCodiceOpera(codiceOpera);
		
		ContabilitaAdspUpdateAttoResponse output = new ContabilitaAdspUpdateAttoResponse();
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.attoinbozza(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS attoinbozza contabilita ADSP: " + output.getMsg());
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			output.setOk(false);
			output.setMsg("Errore nell'invocazione dell'operazione attoinbozza. " + e.getMessage());
		}
		
		return output;
	}
	
	
	public ContabilitaAdspUpdateAttoResponse attesaConformita(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		ContabilitaAdspUpdateAttoRequest input = new ContabilitaAdspUpdateAttoRequest();
		
		String oggettoDescrittivo = bean.getDesOgg();
		String cup = null;
		if (bean.getListaDatiContabiliADSP() != null && bean.getListaDatiContabiliADSP().size() == 1) {
			cup = bean.getListaDatiContabiliADSP().get(0).getCodiceCUP();
		}
		String codFiscaleRupCodAppalti = null;
		if (bean.getListaRUPCodAppalti() != null && bean.getListaRUPCodAppalti().size() > 0) {
			codFiscaleRupCodAppalti = bean.getListaRUPCodAppalti().get(0).getCodFiscaleResponsabileUnicoProvvedimento();
		}
		String codiceOpera = null;
		if (bean.getListaOpereADSP() != null && bean.getListaOpereADSP().size() == 1) {
			codiceOpera = bean.getListaOpereADSP().get(0).getCodiceRapido();
		}
		
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		input.setOggettoAt(oggettoDescrittivo);
		input.setCodCup(cup);
		input.setCodFiscaleRup(codFiscaleRupCodAppalti);
//		input.setCodiceOpera(codiceOpera);
		
		ContabilitaAdspUpdateAttoResponse output = new ContabilitaAdspUpdateAttoResponse();
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.attesaconformita(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS attesaConformita contabilita ADSP: " + output.getMsg());
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			output.setOk(false);
			output.setMsg("Errore nell'invocazione dell'operazione attesaConformita. " + e.getMessage());
		}
		
		return output;
	}
	
	public ContabilitaAdspUpdateAttoResponse attoValidato(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		ContabilitaAdspUpdateAttoRequest input = new ContabilitaAdspUpdateAttoRequest();
		
		String oggettoDescrittivo = bean.getDesOgg();
		String cup = null;
		if (bean.getListaDatiContabiliADSP() != null && bean.getListaDatiContabiliADSP().size() == 1) {
			cup = bean.getListaDatiContabiliADSP().get(0).getCodiceCUP();
		}
		String codFiscaleRupCodAppalti = null;
		if (bean.getListaRUPCodAppalti() != null && bean.getListaRUPCodAppalti().size() > 0) {
			codFiscaleRupCodAppalti = bean.getListaRUPCodAppalti().get(0).getCodFiscaleResponsabileUnicoProvvedimento();
		}
		String codiceOpera = null;
		if (bean.getListaOpereADSP() != null && bean.getListaOpereADSP().size() == 1) {
			codiceOpera = bean.getListaOpereADSP().get(0).getCodiceRapido();
		}
		
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		input.setOggettoAt(oggettoDescrittivo);
		input.setCodCup(cup);
		input.setCodFiscaleRup(codFiscaleRupCodAppalti);
//		input.setCodiceOpera(codiceOpera);
		
		ContabilitaAdspUpdateAttoResponse output = new ContabilitaAdspUpdateAttoResponse();
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.attovalidato(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS attovalidato contabilita ADSP: " + output.getMsg());
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			output.setOk(false);
			output.setMsg("Errore nell'invocazione dell'operazione attovalidato. " + e.getMessage());
		}
		
		return output;
	}
		
	public ContabilitaAdspDeleteAttoResponse cancellaAtto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		ContabilitaAdspDeleteAttoRequest input = new ContabilitaAdspDeleteAttoRequest();
		
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		
		ContabilitaAdspDeleteAttoResponse output = new ContabilitaAdspDeleteAttoResponse();
		
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.cancellaatto(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS cancellaatto contabilita ADSP: " + output.getMsg());
				throw new Exception(output.getMsg());
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			output.setOk(false);
			output.setMsg("C'è stato un errore durante la cancellazione dell'atto: " + e.getMessage());
		}
		
		return output;
	}
	
	public NuovaPropostaAtto2CompletaBean cancellaMovimentiContabili(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(bean.getListaDatiContabiliADSP()!=null && bean.getListaDatiContabiliADSP().size()>0) {
			if(bean.getFlgSavedAttoSuSistemaContabile()!=null && bean.getFlgSavedAttoSuSistemaContabile().equals(true)) {
				String progressivoAtto = getProgressivoAtto(bean);
				String cf = getCodiceFiscaleUtente();
				
				ContabilitaAdspDeleteAttoRequest input = new ContabilitaAdspDeleteAttoRequest();
				
				input.setProgAtto(Integer.valueOf(progressivoAtto));
				input.setCodiceFiscaleOp(cf);
				
				ContabilitaAdspDeleteAttoResponse output = new ContabilitaAdspDeleteAttoResponse();
				
				try {
					ContabilitaAttiImpl service = new ContabilitaAttiImpl();
					output = service.cancellaatto(getLocale(), input);
					
					if (!output.isOk()) {
						logger.error("Errore WS cancellaatto contabilita ADSP: " + output.getMsg());
						throw new Exception(output.getMsg());
					}else {
						bean.setListaDatiContabiliADSP(new ArrayList<DatiContabiliADSPXmlBean>());
						bean.setFlgSavedAttoSuSistemaContabile(false);
						aggiornaDBConDatiContabiliADSP(bean);
					}
				
				}catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new Exception("Non è stato possibile cancellare i movimenti contabili: " + e.getMessage());
				}
			}else {
				bean.setListaDatiContabiliADSP(new ArrayList<DatiContabiliADSPXmlBean>());
				aggiornaDBConDatiContabiliADSP(bean);
			}
		}else {
			addMessage("Non sono presenti movimenti contabili", "", MessageType.INFO);
		}
		
		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean importaDatiContabiliFromExcel(NuovaPropostaAtto2CompletaBean bean)
			throws Exception {

		String uriExcel = getExtraparams().get("uriExcel");
		String mimeType = getExtraparams().get("mimetype");
		String cdrUOCompetente = getExtraparams().get("cdrUOCompetente");
		
		List<DatiContabiliADSPXmlBean> listaMovimentiContabiliExcel = new ArrayList<DatiContabiliADSPXmlBean>();

		try {

			boolean isXls = mimeType.equals("application/excel");
			boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			/**
			 * Implementazione HSSF (Horrible SpreadSheet Format): indica un'API che funziona con Excel 2003 o versioni precedenti.
			 */
			if (isXls) {
				listaMovimentiContabiliExcel = caricaDatiFromXls(uriExcel, cdrUOCompetente, bean);
			}
			
			/**
			 * Implementazione XSSF (XML SpreadSheet Format): indica un'API che funziona con Excel 2007 o versioni successive.
			 */
			else if (isXlsx) { 
				listaMovimentiContabiliExcel = caricaDatiFromXlsx(uriExcel, cdrUOCompetente, bean);
			} 
			
			else {
				String message = "Il formato del documento non è supportato, solo xls e xlsx sono ammessi come documenti validi";
				logger.error(message);

				throw new StoreException(message);
			}
			
		} catch (Exception e) {
			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause() != null ? e.getCause().getMessage() : null;

			String message = "Durante il caricamento delle righe del file, si è verificata la seguente eccezione: " + errorMessage;
			logger.error(message, e);

			throw new StoreException(message); 
		}

		if (bean.getListaDatiContabiliADSP() != null && bean.getListaDatiContabiliADSP().size() > 0) {
			bean.getListaDatiContabiliADSP().addAll(listaMovimentiContabiliExcel);
		}else {
			bean.setListaDatiContabiliADSP(listaMovimentiContabiliExcel);
		}

		return bean;
	}

	private List<DatiContabiliADSPXmlBean> caricaDatiFromXls(String uriExcel, String cdrUOCompetente,
			NuovaPropostaAtto2CompletaBean bean) throws Exception {
		BufferedInputStream documentStream = null;
		InputStream is = null;
		List<DatiContabiliADSPXmlBean> listaDatiContabiliExcel = new ArrayList<DatiContabiliADSPXmlBean>();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();

		try {
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);

			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);

			HSSFWorkbook wb = new HSSFWorkbook(documentStream);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			
			HashMap<String, String> mappaKeyCapitoli = caricaMappaKeyCapitoli(bean);

			for (int r = 0; r < rows + 1; r++) {
				row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {

					/** CONTROLLO SE C'E' L'INTESTAZIONE */
					FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
					if (row.getCell(0) != null
							&& formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType() == Cell.CELL_TYPE_STRING
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Movimento U/E")) {
						continue;
					}

					try {
						DatiContabiliADSPXmlBean movimentoContabile = new DatiContabiliADSPXmlBean();
						movimentoContabile.setMappaKeyCapitoli(mappaKeyCapitoli);
						for (int c = 0; c < CelleExcelDatiContabiliADSPEnum.values().length; c++) {
							cell = row.getCell(c);
							switch (CelleExcelDatiContabiliADSPEnum.getCellFromIndex(c)) {

							case MOVIMENTO_E_U:
								if (cell != null  && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK 
								&& ("U".equalsIgnoreCase(cell.getStringCellValue()) || "E".equalsIgnoreCase(cell.getStringCellValue()))) {
									movimentoContabile.setFlgEntrataUscita(cell.getStringCellValue());
								} else {
									throw new ValidatorException("Il dato Spesa corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio e può avere valori E (entrata) o U (Uscita)");
								}
								break;
							case ANNO_ESERCIZIO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setAnnoEsercizio(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setAnnoEsercizio(cell.getStringCellValue());
									}
								} else {
									throw new ValidatorException("Il dato Anno Esercizio corrispondente alla colonna "
											+ (c + 1) + " è obbligatorio");
								}
								break;
							case CIG:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setCodiceCIG(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setCodiceCIG(cell.getStringCellValue());
									}
								}
								break;
							case CUP:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setCodiceCUP(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setCodiceCUP(cell.getStringCellValue());
									}
								}
								break;
							case CAPITOLO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setCapitolo(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setCapitolo(cell.getStringCellValue());
									}
								} else {
									throw new ValidatorException("Il dato Capitolo corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio");
								}
								break;
							case CONTO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setConto(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setConto(cell.getStringCellValue());
									}									
								} else {
									throw new ValidatorException("Il dato Conto+CdR corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio");
								}
								break;
							case OPERA:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									boolean flgOperaPresente = false;
									
									if(!(formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType()==Cell.CELL_TYPE_STRING) ) {
										throw new ValidatorException("Il dato Opera corrispondente alla colonna "
												+ (c + 1) + " non è nel formato atteso");
									}

									String codiceOpera = cell.getStringCellValue();

									if(StringUtils.isNotBlank(codiceOpera)){
										if (bean.getListaOpereADSP() != null && bean.getListaOpereADSP().size() > 0) {
											for (PeriziaBean instance : bean.getListaOpereADSP()) {
												if (codiceOpera.equalsIgnoreCase(instance.getCodiceRapido())) {
													flgOperaPresente = true;
													movimentoContabile.setOpera(codiceOpera);
													movimentoContabile.setDesOpera(instance.getDescrizione());
													break;
												}
											}
											if (!flgOperaPresente) {
												throw new ValidatorException("Il dato Opera corrispondente alla colonna "
														+ (c + 1) + " non ha trovato nessuna corrispondenza con le opere presenti in Auriga");
											}
										} else {
											throw new ValidatorException("Il dato Opera corrispondente alla colonna " + (c + 1)
													+ " non ha trovato nessuna corrispondenza con le opere presenti in Auriga");
										}
									}
								}
								break;
							case IMPORTO:
								cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura*/
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK && StringUtils.isNotBlank(cell.getStringCellValue())) {									
									String cellValueImporto;
									
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										cellValueImporto = String.valueOf((int) cell.getNumericCellValue());
									}else {
										cellValueImporto = cell.getStringCellValue();
									}

									double doubleNumber = Double.valueOf(cellValueImporto.replace(".", "").replace(",", "."));
									valueImporto = doubleNumber;
									int intPart = (int) doubleNumber;
									double decimalPart = doubleNumber - intPart;
									String importo;
									if (decimalPart > 0) {
										importo = String.valueOf(doubleNumber);
									} else {
										importo = String.valueOf(intPart);
									}

									movimentoContabile.setImporto(importo.replace(".", ","));
								} else {
									throw new ValidatorException("Il dato Importo corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio");
								}
								break;
								
							case NOTE:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									movimentoContabile.setNote(StringUtils.isNotBlank(cell.getStringCellValue()) ? cell.getStringCellValue() : null);										
								}
								break;
								
							case IMPONIBILE:
								cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura*/
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK && 
										StringUtils.isNotBlank(cell.getStringCellValue()) && getFlgDisattivaIntegrazioneSistemaContabile(bean)) {									
									
									String cellValueImponibile;
									
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										cellValueImponibile = String.valueOf((int) cell.getNumericCellValue());
									}else {
										cellValueImponibile = cell.getStringCellValue();
									}

									double doubleNumber = Double.valueOf(cellValueImponibile.replace(".", "").replace(",", "."));
									
									if(doubleNumber<=valueImporto) {
										int intPart = (int) doubleNumber;
										double decimalPart = doubleNumber - intPart;
										String imponibile;
										if (decimalPart > 0) {
											imponibile = String.valueOf(doubleNumber);
										} else {
											imponibile = String.valueOf(intPart);
										}
										
										movimentoContabile.setImponibile(imponibile.replace(".", ","));								
									}else {
										throw new ValidatorException("Il dato Imponibile corrispondente alla colonna " + (c + 1)
												+ " deve essere minore o ugale all'importo");
									}									
								} else {
									/*Se la tipologia di atto è tra quelle di cui non si deve fare l'integrazione quindi è visibile e obbligatorio l'iimponibile*/
									if(getFlgDisattivaIntegrazioneSistemaContabile(bean)) {
										throw new ValidatorException("Il dato Imponibile corrispondente alla colonna " + (c + 1)
												+ " è obbligatorio");
									}									
								}
							}
						}

						String keyCapitolo = recuperaChiaveCapitolo(movimentoContabile.getAnnoEsercizio(),
								movimentoContabile.getFlgEntrataUscita(), movimentoContabile.getCapitolo(),
								movimentoContabile.getConto(), cdrUOCompetente);

						movimentoContabile.setKeyCapitolo(keyCapitolo);
						movimentoContabile.setOperazioneSistemaContabile(OPERAZIONE_SISTEMA_CONTABILE_INSERT);
						movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
						
						String flgDisattivaIntegrazioneSistemaContabile = getFlgDisattivaIntegrazioneSistemaContabile(bean) ? "true" : "false";   /*TODO: DISCRIMINANTE INTEGRAZIONE SISTEMA CONTABILE */
						
						movimentoContabile.setFlgDisattivaIntegrazioneSistemaContabile(flgDisattivaIntegrazioneSistemaContabile);
						
						ResultBean<Object> response = verificaDisponibilitaImporto(movimentoContabile);  
						if(response.isInError()) {
							String errorMessageDisponibilita = response.getDefaultMessage();
							/*Se è disattiva l'integrazione col sistema contabile (quindi sto verificando gli importo degli Rda) se il metodo verificaDisponibilitaImporto() mi ha
							 * restituito un errore di disponibilita insufficiente e se è attivo il parametro di WARNING nella gestione dell'inserimento con importo non disponibile
							 * devo dare evidenza di warning della popup di errore di inserimento delle righe excel.
							 * 
							 * ***Viene gestito inserendo la prola WARNING avanti al messaggio di errore che viene mostrato nella popup (vedere classe ErroreMassivoPopup)
							 * */
							if(StringUtils.isNotBlank(errorMessageDisponibilita) && errorMessageDisponibilita.contains("sufficiente disponibilit")
									&& flgDisattivaIntegrazioneSistemaContabile.equalsIgnoreCase("true") 
									&&  StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "MOD_CTRL_DISP_CAPITOLI_IN_RDA")) 
									&& ParametriDBUtil.getParametroDB(getSession(), "MOD_CTRL_DISP_CAPITOLI_IN_RDA").equalsIgnoreCase("WARNING")) {
									
								errorMessageDisponibilita = "WARNING: " + errorMessageDisponibilita;
								listaDatiContabiliExcel.add(movimentoContabile);
							}
							throw new Exception(errorMessageDisponibilita);
						}

						aggiornaMappaKeyCapitoli(movimentoContabile, flgDisattivaIntegrazioneSistemaContabile);
												
						listaDatiContabiliExcel.add(movimentoContabile);

					} catch (Exception e) {
						ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
						erroreRiga.setNumeroRiga(String.valueOf(r + 1));
						erroreRiga.setMotivo(e.getMessage().contains("WARNING: ") ? e.getMessage() : "ERRORE: " + e.getMessage());

						listaRigheInErrore.add(erroreRiga);
					}
				}
			}
		} catch (Exception e) {
			if (!(e instanceof ValidatorException)) {
				logger.error("Errore durante la lettura dei dati dal foglio Excel: " + e.getMessage(), e);
			}

			throw e;

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (documentStream != null) {
				try {
					documentStream.close();
				} catch (Exception e) {
					logger.error("Impossibile chiudere lo stream legato al documento a causa della seguente eccezione ",
							e);
				}
			}
		}

		bean.setListaExcelDatiContabiliADSPInError(listaRigheInErrore);

		return listaDatiContabiliExcel;
	}

	/**
	 * @param movimentoContabile
	 * @param flgDisattivaIntegrazioneSistemaContabile
	 * @throws NumberFormatException
	 */
	public void aggiornaMappaKeyCapitoli(DatiContabiliADSPXmlBean movimentoContabile,
			String flgDisattivaIntegrazioneSistemaContabile) throws NumberFormatException {
		if("true".equalsIgnoreCase(flgDisattivaIntegrazioneSistemaContabile) && 
				StringUtils.isNotBlank(getExtraparams().get("flgSenzaImpegniCont")) && "false".equalsIgnoreCase(getExtraparams().get("flgSenzaImpegniCont"))
				&& !getFlgDisattivaCtrlRda()) {
			HashMap<String, String> mappaKeyCapitoli = movimentoContabile.getMappaKeyCapitoli();
			
			String importoRigaExcel = movimentoContabile.getImporto().replace(".", "").replace(",", ".");
			
			if(mappaKeyCapitoli!=null) {
				if(StringUtils.isNotBlank(String.valueOf(mappaKeyCapitoli.get(movimentoContabile.getKeyCapitolo())!=null ? mappaKeyCapitoli.get(movimentoContabile.getKeyCapitolo()) : "" ))) {
					String importoPresente = String.valueOf(mappaKeyCapitoli.get(movimentoContabile.getKeyCapitolo()));
					
					String importoAggiornato = String.valueOf(Double.valueOf(importoRigaExcel) + Double.valueOf(importoPresente));
					
					mappaKeyCapitoli.put(movimentoContabile.getKeyCapitolo(), importoAggiornato);
					
				}else {
					mappaKeyCapitoli.put(movimentoContabile.getKeyCapitolo(), importoRigaExcel);

				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private HashMap<String, String> caricaMappaKeyCapitoli(NuovaPropostaAtto2CompletaBean bean) {
		HashMap<String, String> mappaKeyCapitoli = new HashMap<String, String>();
		if(getFlgDisattivaIntegrazioneSistemaContabile(bean) && 
				StringUtils.isNotBlank(getExtraparams().get("flgSenzaImpegniCont")) && "false".equalsIgnoreCase(getExtraparams().get("flgSenzaImpegniCont"))
				&& !getFlgDisattivaCtrlRda()) {
			String mappaKeyCapitoliString = getExtraparams().get("mappaKeyCapitoli");
			mappaKeyCapitoli = new Gson().fromJson(mappaKeyCapitoliString, HashMap.class);			
		}
		
		return mappaKeyCapitoli;
	}

	private List<DatiContabiliADSPXmlBean> caricaDatiFromXlsx(String uriExcel, String cdrUOCompetente, NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		FileInputStream fis = null;
		List<DatiContabiliADSPXmlBean> listaDatiContabiliExcel = new ArrayList<DatiContabiliADSPXmlBean>();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();
		
		try{
			File document = StorageImplementation.getStorage().getRealFile(uriExcel);
			fis = new FileInputStream(document); 
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0); 
			
			int rows; // No of rows
		    rows = sheet.getLastRowNum();

		    for(int r = 0; r < rows +1; r++) {
		    	Row row = sheet.getRow(r);
				if (row != null && !isRowEmpty(row)) {

					/**CONTROLLO SE C'E' L'INTESTAZIONE*/
					FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
					if(row.getCell(0)!=null && formulaEvaluator.evaluateInCell(row.getCell(0)).getCellType()==Cell.CELL_TYPE_STRING 
							&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Movimento U/E")) {
						continue;
					}
					
					try {
						DatiContabiliADSPXmlBean movimentoContabile = new DatiContabiliADSPXmlBean();
						for (int c = 0; c < CelleExcelDatiContabiliADSPEnum.values().length; c++) {
							Cell cell = row.getCell(c);

							switch (CelleExcelDatiContabiliADSPEnum.getCellFromIndex(c)) {

							case MOVIMENTO_E_U:
								if (cell != null  && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK 
								&& ("U".equalsIgnoreCase(cell.getStringCellValue()) || "E".equalsIgnoreCase(cell.getStringCellValue()))) {
									movimentoContabile.setFlgEntrataUscita(cell.getStringCellValue());
								} else {
									throw new ValidatorException("Il dato Spesa corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio e può avere valori E (entrata) o U (Uscita)");
								}
								break;
							case ANNO_ESERCIZIO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setAnnoEsercizio(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setAnnoEsercizio(cell.getStringCellValue());
									}
								} else {
									throw new ValidatorException("Il dato Anno Esercizio corrispondente alla colonna "
											+ (c + 1) + " è obbligatorio");
								}
								break;
							case CIG:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setCodiceCIG(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setCodiceCIG(cell.getStringCellValue());
									}
								}
								break;
							case CUP:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setCodiceCUP(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setCodiceCUP(cell.getStringCellValue());
									}
								}
								break;
							case CAPITOLO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setCapitolo(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setCapitolo(cell.getStringCellValue());
									}
								} else {
									throw new ValidatorException("Il dato Capitolo corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio");
								}
								break;
							case CONTO:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										movimentoContabile.setConto(String.valueOf((int) cell.getNumericCellValue()));
									}else {
										movimentoContabile.setConto(cell.getStringCellValue());
									}									
								} else {
									throw new ValidatorException("Il dato Conto+CdR corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio");
								}
								break;
							case OPERA:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									boolean flgOperaPresente = false;
									
									if(!(formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType()==Cell.CELL_TYPE_STRING) ) {
										throw new ValidatorException("Il dato Opera corrispondente alla colonna "
												+ (c + 1) + " non è nel formato atteso");
									}

									String codiceOpera = cell.getStringCellValue();

									if(StringUtils.isNotBlank(codiceOpera)){
										if (bean.getListaOpereADSP() != null && bean.getListaOpereADSP().size() > 0) {
											for (PeriziaBean instance : bean.getListaOpereADSP()) {
												if (codiceOpera.equalsIgnoreCase(instance.getCodiceRapido())) {
													flgOperaPresente = true;
													movimentoContabile.setOpera(codiceOpera);
													movimentoContabile.setDesOpera(instance.getDescrizione());
													break;
												}
											}
											if (!flgOperaPresente) {
												throw new ValidatorException("Il dato Opera corrispondente alla colonna "
														+ (c + 1) + " non ha trovato nessuna corrispondenza con le opere presenti in Auriga");
											}
										} else {
											throw new ValidatorException("Il dato Opera corrispondente alla colonna " + (c + 1)
													+ " non ha trovato nessuna corrispondenza con le opere presenti in Auriga");
										}
									}
								}
								break;
							case IMPORTO:
								cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura*/
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK && StringUtils.isNotBlank(cell.getStringCellValue())) {									
									String cellValueImporto;
									
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										cellValueImporto = String.valueOf((int) cell.getNumericCellValue());
									}else {
										cellValueImporto = cell.getStringCellValue();
									}

									double doubleNumber = Double.valueOf(cellValueImporto.replace(".", "").replace(",", "."));
									valueImporto = doubleNumber;
									int intPart = (int) doubleNumber;
									double decimalPart = doubleNumber - intPart;
									String importo;
									if (decimalPart > 0) {
										importo = String.valueOf(doubleNumber);
									} else {
										importo = String.valueOf(intPart);
									}

									movimentoContabile.setImporto(importo.replace(".", ","));
								} else {
									throw new ValidatorException("Il dato Importo corrispondente alla colonna " + (c + 1)
											+ " è obbligatorio");
								}
								break;
								
							case NOTE:
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK) {
									movimentoContabile.setNote(StringUtils.isNotBlank(cell.getStringCellValue()) ? cell.getStringCellValue() : null);										
								}
								break;
								
							case IMPONIBILE:
								cell.setCellType(Cell.CELL_TYPE_STRING); /*forzo il valore della cella a String cosi da non avere problemi di lettura*/
								if (cell != null && formulaEvaluator.evaluateInCell(row.getCell(c)).getCellType() != Cell.CELL_TYPE_BLANK && 
										StringUtils.isNotBlank(cell.getStringCellValue()) && getFlgDisattivaIntegrazioneSistemaContabile(bean)) {									
									
									String cellValueImponibile;
									
									if(formulaEvaluator.evaluateInCell(cell).getCellType()==Cell.CELL_TYPE_NUMERIC) {
										cellValueImponibile = String.valueOf((int) cell.getNumericCellValue());
									}else {
										cellValueImponibile = cell.getStringCellValue();
									}

									double doubleNumber = Double.valueOf(cellValueImponibile.replace(".", "").replace(",", "."));
									
									if(doubleNumber<=valueImporto) {
										int intPart = (int) doubleNumber;
										double decimalPart = doubleNumber - intPart;
										String imponibile;
										if (decimalPart > 0) {
											imponibile = String.valueOf(doubleNumber);
										} else {
											imponibile = String.valueOf(intPart);
										}
										
										movimentoContabile.setImponibile(imponibile.replace(".", ","));								
									}else {
										throw new ValidatorException("Il dato Imponibile corrispondente alla colonna " + (c + 1)
												+ " deve essere minore o ugale all'importo");
									}									
								} else {
									/*Se la tipologia di atto è tra quelle di cui non si deve fare l'integrazione quindi è visibile e obbligatorio l'iimponibile*/
									if(getFlgDisattivaIntegrazioneSistemaContabile(bean)) {
										throw new ValidatorException("Il dato Imponibile corrispondente alla colonna " + (c + 1)
												+ " è obbligatorio");
									}									
								}
								
								break;
								
							}
						}

						String keyCapitolo = recuperaChiaveCapitolo(movimentoContabile.getAnnoEsercizio(),
								movimentoContabile.getFlgEntrataUscita(), movimentoContabile.getCapitolo(),
								movimentoContabile.getConto(), cdrUOCompetente);
						movimentoContabile.setKeyCapitolo(keyCapitolo);

						movimentoContabile.setOperazioneSistemaContabile(OPERAZIONE_SISTEMA_CONTABILE_INSERT);
						movimentoContabile.setStatoSistemaContabile(STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
						
						String flgDisattivaIntegrazioneSistemaContabile = getFlgDisattivaIntegrazioneSistemaContabile(bean) ? "true" : "false";   /*TODO: DISCRIMINANTE INTEGRAZIONE SISTEMA CONTABILE */
						
						movimentoContabile.setFlgDisattivaIntegrazioneSistemaContabile(flgDisattivaIntegrazioneSistemaContabile);
						
						ResultBean<Object> response = verificaDisponibilitaImporto(movimentoContabile);  
						if(response.isInError()) {
							String errorMessageDisponibilita = response.getDefaultMessage();
							/*Se è disattiva l'integrazione col sistema contabile (quindi sto verificando gli importo degli Rda) se il metodo verificaDisponibilitaImporto() mi ha
							 * restituito un errore di disponibilita insufficiente e se è attivo il parametro di WARNING nella gestione dell'inserimento con importo non disponibile
							 * devo dare evidenza di warning della popup di errore di inserimento delle righe excel.
							 * 
							 * ***Viene gestito inserendo la prola WARNING avanti al messaggio di errore che viene mostrato nella popup (vedere classe ErroreMassivoPopup)
							 * */
							if(StringUtils.isNotBlank(errorMessageDisponibilita) && errorMessageDisponibilita.contains("sufficiente disponibilit")
									&& flgDisattivaIntegrazioneSistemaContabile.equalsIgnoreCase("true") 
									&&  StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "MOD_CTRL_DISP_CAPITOLI_IN_RDA")) 
									&& ParametriDBUtil.getParametroDB(getSession(), "MOD_CTRL_DISP_CAPITOLI_IN_RDA").equalsIgnoreCase("WARNING")) {
									
								errorMessageDisponibilita = "WARNING: " + errorMessageDisponibilita;
								listaDatiContabiliExcel.add(movimentoContabile);
							}else {
								errorMessageDisponibilita = "ERRORE: " + errorMessageDisponibilita;
							}
							throw new Exception(errorMessageDisponibilita);
						}

						aggiornaMappaKeyCapitoli(movimentoContabile, flgDisattivaIntegrazioneSistemaContabile);
												
						listaDatiContabiliExcel.add(movimentoContabile);
						
					} catch (Exception e) {
						ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
						erroreRiga.setNumeroRiga(String.valueOf(r+1));
						erroreRiga.setMotivo(e.getMessage());
						
						listaRigheInErrore.add(erroreRiga);
					}
				}		        
		    }
		}  
		catch(Exception e) { 
			if (!(e instanceof ValidatorException)) {
				logger.error("Errore durante la lettura dei dati dal foglio Excel: " + e.getMessage(),e);
			}
			  
			throw e;
			
		}finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (Exception e) {}
			}
		}
		
		bean.setListaExcelDatiContabiliADSPInError(listaRigheInErrore);
		
		return listaDatiContabiliExcel;
	}
	
	private String recuperaChiaveCapitolo(String annoEsercizio, String flgEntrataUscita, String capitolo1, String capitolo2, String cdrUOCompetente) throws StoreException {
		String cf = getCodiceFiscaleUtente();

		ContabilitaAdspGetCapitoliBPRequest input = new ContabilitaAdspGetCapitoliBPRequest();
		input.setCodiceFiscaleOp(cf);
		input.setAnnoEsercizio(annoEsercizio);
		input.setAnnoRiferimento(annoEsercizio);
		input.setMovimento(flgEntrataUscita.equals("E") ? "E" : "S");
		input.setCapitolo1(capitolo1);				
		input.setCapitolo2(StringUtils.isNotBlank(capitolo2) ? capitolo2.replace(".", "") : null );
		input.setAliasCDR(cdrUOCompetente);
		input.setImportoRichiesto(Double.valueOf(1));
		
		ContabilitaAdspGetCapitoliBPResponse output = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.ricercacapitolibp(getLocale(), input);
			
			if (!output.isOk()) {
				logger.error("Errore WS ricercacapitolibp contabilita ADSP: " + output.getMsg());
				throw new StoreException(output.getMsg());
			} else {
				for(ContabilitaAdspDatiCapitoloResponse capitoliOutput : output.getDati()) {
					return String.valueOf(capitoliOutput.getProgkeyvb());										
				}
			}
			
		} catch (Exception e) {
			logger.error("Errore WS ricercacapitolibp contabilita ADSP: " + e.getMessage(), e);
			throw new StoreException("Errore durante il caricamento dei capitoli: " + e.getMessage());
		}
		
		return null;
	}
				
	public static boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}

	public void getMovimentiDefinitivi(NuovaPropostaAtto2CompletaBean bean) throws StoreException {
	
		List<DatiContabiliADSPXmlBean> listaMovimentiDefinitiviADSP = new ArrayList<DatiContabiliADSPXmlBean>();
		
		String progressivoAtto = getProgressivoAtto(bean);
		String cf = getCodiceFiscaleUtente();
		
		ContabilitaAdspGetAttoRequest input = new ContabilitaAdspGetAttoRequest();		
		input.setProgAtto(Integer.valueOf(progressivoAtto));
		input.setCodiceFiscaleOp(cf);
		
		ContabilitaAdspGetAttoResponse output = new ContabilitaAdspGetAttoResponse();
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.ricercaatto(getLocale(), input);
			
			if (!output.isOk() || output.getDati() == null) {
				throw new Exception("Errore durante il recupero degli atti definitivi sul sistema contabile: " + output.getMsg());
			}else {
				ContabilitaAdspDatiAttoResponse response = output.getDati();
				List<ContabilitaAdspRichiesteContabiliResponse> listaMovimentiContabili = response.getDettaglioRichieste();
				if(listaMovimentiContabili == null || !(listaMovimentiContabili.size()>0)) {
					throw new Exception("Non sono presenti movimenti definitivi sul sistema contabile");
				}
				
				for(ContabilitaAdspRichiesteContabiliResponse movimentoContabileADSP : listaMovimentiContabili) {
					DatiContabiliADSPXmlBean movimentoDefinitivo = new DatiContabiliADSPXmlBean();
					
					movimentoDefinitivo.setAnnoEsercizio(StringUtils.isNotBlank(movimentoContabileADSP.getDatainserImp()) ? movimentoContabileADSP.getDatainserImp().substring(0, 4) : null);
					movimentoDefinitivo.setImporto(movimentoContabileADSP.getImRicOri());
					movimentoDefinitivo.setCodiceCUP(movimentoContabileADSP.getCup());
					movimentoDefinitivo.setCodiceCIG(movimentoContabileADSP.getCig());
					movimentoDefinitivo.setFlgEntrataUscita(movimentoContabileADSP.getEs().equals("E") ? "E" : "U");
					movimentoDefinitivo.setCapitolo(movimentoContabileADSP.getCapitoloParte1());
					movimentoDefinitivo.setConto(movimentoContabileADSP.getCapitoloParte2());
					movimentoDefinitivo.setKeyCapitolo(String.valueOf(movimentoContabileADSP.getProgkeyvb()));
					movimentoDefinitivo.setOpera(movimentoContabileADSP.getCodiceOpera());
						
					listaMovimentiDefinitiviADSP.add(movimentoDefinitivo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException(e.getMessage());
		}
		
		bean.setListaDatiContabiliDefinitiviADSP(listaMovimentiDefinitiviADSP);;
	
	}
	
	private String getCodiceFiscaleUtente() throws StoreException {
		String cf = null;
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		if(StringUtils.isNotBlank(lAurigaLoginBean.getIdUserLavoro())) {
			cf = lAurigaLoginBean.getDelegaCodFiscale();
		}
		else if(lAurigaLoginBean.getSpecializzazioneBean()!=null && StringUtils.isNotBlank(lAurigaLoginBean.getSpecializzazioneBean().getCodFiscaleUserOut())) {
			cf = lAurigaLoginBean.getSpecializzazioneBean().getCodFiscaleUserOut();
		}else {
//			cf = ParametriDBUtil.getParametroDB(getSession(), "CF_UTENZA_WS_AMC");
			throw new StoreException("Il tuo codice fiscale è sconosciuto: impossibile attivare integrazione con sistema contabile");
		}
		return cf;
	}
	
	public boolean getFlgDisattivaIntegrazioneSistemaContabile(NuovaPropostaAtto2CompletaBean bean) {
		String listaIdTipoAttoNoContabilita = ParametriDBUtil.getParametroDB(getSession(), "ID_DOC_TYPE_SENZA_SCRITTURA_CONTABILITA");
		if(StringUtils.isNotBlank(listaIdTipoAttoNoContabilita)) {
			String[] setIdTipoAttoNoContabilita = listaIdTipoAttoNoContabilita.split(";");			
			for(String IdTipoAttoNoContabilita: setIdTipoAttoNoContabilita) {
				if(IdTipoAttoNoContabilita.equalsIgnoreCase(bean.getTipoDocumento())) {
					return true;
				}
			}
		}		
		return false;
	}

	public void verificaDisponibilitaProposte(NuovaPropostaAtto2CompletaBean bean) throws StoreException {
		
		try {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

			DmpkProcessesCtrldispcapcontabilerda_adspBean input = new DmpkProcessesCtrldispcapcontabilerda_adspBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro())
					? new BigDecimal(loginBean.getIdUserLavoro())
					: null);
			input.setIdudin(new BigDecimal(bean.getIdUd()));

			DmpkProcessesCtrldispcapcontabilerda_adsp store = new DmpkProcessesCtrldispcapcontabilerda_adsp();
			StoreResultBean<DmpkProcessesCtrldispcapcontabilerda_adspBean> output = store
					.execute(getLocale(), loginBean, input);

			if (output.isInError()) {
				throw new StoreException(output);
			}
			
			List<CapitoloImportoDaVerificareBean> listaProposteConcorrenti = new ArrayList<CapitoloImportoDaVerificareBean>();
			
			String xml = output.getResultBean().getListakeycontimpallocout();
			listaProposteConcorrenti = XmlListaUtility.recuperaLista(xml, CapitoloImportoDaVerificareBean.class);
			
			if(listaProposteConcorrenti!=null && listaProposteConcorrenti.size()>0) {
				for(CapitoloImportoDaVerificareBean proposta : listaProposteConcorrenti) {
					ContabilitaAdspResponse result = callVerificaImportoCWOLPerPropostaConcorrente(bean, proposta);
					if(!result.isOk()) {
						if("L'importo inserito non è disponibile".equalsIgnoreCase(result.getMsg())) {
							throw new ImportoCWOLNonDisponibileException("Non c’è più sufficiente disponibilità sul/i capitoli-conti selezionati");
						}else {
							throw new Exception(result.getMsg());
						}						
					}
				}
			}
		}catch(Exception e) {
			if(e instanceof ImportoCWOLNonDisponibileException) {
				throw new StoreException(e.getMessage());
			}else {
				String errorMessage = "Errore durante la verifica della disponibilità dell'importo per le proposte in concorrenza: " + e.getMessage();
				logger.error(errorMessage, e);
				throw new StoreException(errorMessage);
			}
		}
			
	}

	/**
	 * @param bean
	 * @return
	 * @throws NumberFormatException
	 */
	public ContabilitaAdspResponse callVerificaImportoCWOLPerPropostaConcorrente(NuovaPropostaAtto2CompletaBean bean, CapitoloImportoDaVerificareBean proposta)
			throws Exception {
		ContabilitaAdspResponse result = new ContabilitaAdspResponse();
		result.setOk(true);

		String cf = getCodiceFiscaleUtente();
		
		ContabilitaAdspGetCapitoliBPRequest input = new ContabilitaAdspGetCapitoliBPRequest();
		
		input.setCodiceFiscaleOp(cf);
		input.setAnnoEsercizio(getAnnoCorrente());
		input.setAnnoRiferimento(getAnnoCorrente());
		input.setMovimento("S");
		input.setCapitolo1(proposta.getCapitolo());
		input.setCapitolo2(StringUtils.isNotBlank(proposta.getConto()) ? proposta.getConto().replace(".", "") : null);
		input.setAliasCDR(proposta.getCdr());
		
		input.setImportoRichiesto(Double.valueOf(proposta.getImporto().replace(".", "").replace(",", ".")));

		ContabilitaAdspGetCapitoliBPResponse output = null;
		try {
			ContabilitaAttiImpl service = new ContabilitaAttiImpl();
			output = service.ricercacapitolibp(getLocale(), input);

			if (output.isOk() && output.getDati() != null) {
				ContabilitaAdspDatiCapitoloResponse capitolo = output.getDati().get(0);
				if (capitolo.getSommaDisponibile().equalsIgnoreCase("NO")) {
					result.setOk(false);
					result.setMsg("L'importo inserito non è disponibile");
				}
			} else {
				throw new Exception(output.getMsg());
			}
		} catch (Exception e) {
			logger.error("Errore WS ricercacapitolibp contabilita ADSP: " + e.getMessage(), e);
			result.setOk(false);
			result.setMsg("Errore WS ricercacapitolibp contabilita ADSP: " + e.getMessage());
		}
		
		return result;
	}

	public ResultVerificaImportoADSPBean verificaDisponibilitaImportoMultiple(
			ListaDatiContabiliADSPBean listaDatiContabiliADSPBean) throws Exception {
		ResultVerificaImportoADSPBean resultVerificaImportoADSPBean = new ResultVerificaImportoADSPBean();
		List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP = listaDatiContabiliADSPBean.getListaDatiContabiliADSP();

//		boolean flgDisattivaIntegrazioneSistemaContabile = StringUtils
//				.isNotBlank(listaDatiContabiliADSP.get(0).getFlgDisattivaIntegrazioneSistemaContabile())
//				&& listaDatiContabiliADSP.get(0).getFlgDisattivaIntegrazioneSistemaContabile().equalsIgnoreCase("true")? true : false;

		String cf = getCodiceFiscaleUtente();
		String cdrUOCompetente = getExtraparams().get("cdrUOCompetente");
		String flgSenzaImpegniCont = getExtraparams().get("flgSenzaImpegniCont");
		String idUd = getExtraparams().get("idUd");
		boolean forzaCtrlDisponibilita = StringUtils.isNotBlank(getExtraparams().get("forzaCtrlDisponibilita")) 
				&& getExtraparams().get("forzaCtrlDisponibilita").equalsIgnoreCase("true") ? true : false;

		String mappaKeyCapitoliString = getExtraparams().get("mappaKeyCapitoli");
		HashMap<String, String> mappaKeyCapitoli = new Gson().fromJson(mappaKeyCapitoliString, HashMap.class);

		String listaKeyCapitoli = "";
		for (String keyCapitolo : mappaKeyCapitoli.keySet()) {
			listaKeyCapitoli = listaKeyCapitoli + keyCapitolo + ";";
		}

		if (StringUtils.isBlank(cdrUOCompetente)) {
			throw new StoreException("cdR non specificato");
		}
		/*
		 * VERIFICO IMPORTO PER GLI RDA, PER LA LOGICA VEDERE JIRA AURIGA-605
		 */

		if (flgSenzaImpegniCont.equals("false") && !getFlgDisattivaCtrlRda()) {
			try {
//						String keyCapitolo = recuperaChiaveCapitolo(bean.getAnnoEsercizio(), bean.getFlgEntrataUscita(),
//								bean.getCapitolo(), bean.getConto(), cdrUOCompetente);

				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

				DmpkProcessesCtrldispcapcontabilerda_adspBean input = new DmpkProcessesCtrldispcapcontabilerda_adspBean();
				input.setCodidconnectiontokenin(loginBean.getToken());
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro())
						? new BigDecimal(loginBean.getIdUserLavoro())
						: null);
				input.setKeycapitolocontoin(listaKeyCapitoli);
				input.setIdudin(StringUtils.isNotBlank(idUd) ? new BigDecimal(idUd) : null);

				DmpkProcessesCtrldispcapcontabilerda_adsp store = new DmpkProcessesCtrldispcapcontabilerda_adsp();
				StoreResultBean<DmpkProcessesCtrldispcapcontabilerda_adspBean> output = store.execute(getLocale(),
						loginBean, input);

				if (output.isInError()) {
					throw new StoreException(output);
				}

				List<CapitoloImportoDaVerificareBean> listaCapitoloContoDaVerificare = new ArrayList<CapitoloImportoDaVerificareBean>();

				String xml = output.getResultBean().getListakeycontimpallocout();
				listaCapitoloContoDaVerificare = XmlListaUtility.recuperaLista(xml, CapitoloImportoDaVerificareBean.class);
				
				List<ProposteConcorrenti> listaProposteConcorrentiOutStore = new ArrayList<ProposteConcorrenti>();
				String xmlListaProposteConcorrenti = output.getResultBean().getListaatticoncorrentiout();
				listaProposteConcorrentiOutStore = XmlListaUtility.recuperaLista(xmlListaProposteConcorrenti,
						ProposteConcorrenti.class);

				Double importoStoreDaAggiungere = (double) 0;
				Double importoNellaMappa = (double) 0;
				
				/*
				 * Invoco la store passandole in input tutti i keyCapitoli dei dati contabili presenti a maschera, la store mi ritornerà l'importo complessivo delle proposte
				 * che impattano su quel capitolo conto.
				 * 
				 * La variabile in output p importi per i keyCapitoli passati in input è Listakeycontimpallocout.
				 * 
				 * Agli importi ritornati dalla store devo aggiungere gli importi presenti a maschera, che sono salvati nella mappa: mappaKeyCapitoli
				 * che ha come chiave il keyCapitolo è come valore la somma degli importi dei movimenti a maschera che incidono su quel capitolo
				 * 
				 * QUINDI AGGIUNGO GLI IMPORTI DELLA STORE DIRETTAMENTE NELLA MAPPA
				 * 
				 * 
				 * **/
				
				if (listaCapitoloContoDaVerificare.size() > 0) {
					for (CapitoloImportoDaVerificareBean capitoloContoDaVerificare : listaCapitoloContoDaVerificare) {

						importoStoreDaAggiungere = Double.valueOf(capitoloContoDaVerificare.getImporto().replace(".", "").replace(",", "."));

						if (StringUtils.isNotBlank(String.valueOf(mappaKeyCapitoli.get(capitoloContoDaVerificare.getKeyCapitolo())))) {
							importoNellaMappa = Double.valueOf(mappaKeyCapitoli.get(capitoloContoDaVerificare.getKeyCapitolo()) != null
											? String.valueOf(mappaKeyCapitoli.get(capitoloContoDaVerificare.getKeyCapitolo())) : "");

							String importoTotaleDaVerificare = String.format ("%.2f", importoStoreDaAggiungere + importoNellaMappa);

							mappaKeyCapitoli.put(capitoloContoDaVerificare.getKeyCapitolo(), importoTotaleDaVerificare);

						} else {
							mappaKeyCapitoli.put(capitoloContoDaVerificare.getKeyCapitolo(), String.valueOf(importoStoreDaAggiungere));

						}
					}

				}
				
				
				/* 
				 * Scorro i keyCapitoli della mappa (che avranno gli importi si dei dati presenti a maschera che quelli ritornati dalla store) e vado
				 * a richiamare il WS di verifica importo di CWOL
				 * 
				 * se disponibile setto tutti i movimenti che hanno quel keyCapitolo come disponibili
				 * 
				 * se non disponibili setto tutti i movimenti che hanno quel keyCapitolo come non disponibili e vado ad aggiungere tra le proposte concorrenti in result, le proposte
				 * che impattano su quel keyCapitolo (presenti nella variabile di output della store Listaatticoncorrentiout)
				 * 
				 **/								
				
				Set<String> listaKeyCapitoliMappa = mappaKeyCapitoli.keySet();
				for(String keyCapitolo : listaKeyCapitoliMappa) {
					List<DatiContabiliADSPXmlBean> listaDatiContabiliDaRitornare = new ArrayList<>();
					
					DatiContabiliADSPXmlBean bean = getDatoContabileFromKeyCapitolo(keyCapitolo, listaDatiContabiliADSP);
					
					if(StringUtils.isBlank(bean.getDisponibilitaImporto()) || forzaCtrlDisponibilita) {
						String importoTotaleDaVerificare = mappaKeyCapitoli.get(keyCapitolo).replace(".", "").replace(",", ".");

						ResultBean<Object> resultCallCWOL = new ResultBean<Object>();
						callVerificaImportoCWOL(bean, resultCallCWOL, cf, cdrUOCompetente, importoTotaleDaVerificare);    

						if (!resultCallCWOL.isInError()) {
							
							settaDisponibilitaDatiContabili(listaDatiContabiliDaRitornare, listaDatiContabiliADSP, keyCapitolo, "disponibile");
						} else {
							resultVerificaImportoADSPBean.setInError(true);

							List<ProposteConcorrenti> listaProposteConcorrentiPerKeyCapitolo = new ArrayList<ProposteConcorrenti>();
							if (listaProposteConcorrentiOutStore != null && listaProposteConcorrentiOutStore.size() > 0 ) {
								for (ProposteConcorrenti propostaConcorrente : listaProposteConcorrentiOutStore) {
									if (bean.getKeyCapitolo().equalsIgnoreCase(propostaConcorrente.getKeyCapitolo())) {
										propostaConcorrente.setCapitoloProposta(bean.getCapitolo());
										propostaConcorrente.setContoProposta(bean.getConto());
										listaProposteConcorrentiPerKeyCapitolo.add(propostaConcorrente);
										
									}
								}
								
								resultVerificaImportoADSPBean.getListaProposteConcorrenti()
								.addAll(listaProposteConcorrentiPerKeyCapitolo);
							}
							settaDisponibilitaDatiContabili(listaDatiContabiliDaRitornare, listaDatiContabiliADSP, keyCapitolo, "non_disponibile");
						}
					}else {
						settaDisponibilitaDatiContabili(listaDatiContabiliDaRitornare, listaDatiContabiliADSP, keyCapitolo, bean.getDisponibilitaImporto());
					}
					
					resultVerificaImportoADSPBean.getListaDatiContabiliADSP().addAll(listaDatiContabiliDaRitornare);
				
				}

				if(resultVerificaImportoADSPBean.isInError()) {
					if (resultVerificaImportoADSPBean.getListaProposteConcorrenti().size() > 0) {
						resultVerificaImportoADSPBean.setDefaultMessage(
								"Non c’è sufficiente disponibilità. Verifica proposte in iter che impattano sullo stesso capitolo/conto");
					} else {
						resultVerificaImportoADSPBean.setDefaultMessage(
								"Non c'è sufficiente disponibilità e non risultano richieste di approvvigionamento o decreti in iter che insistano sullo stesso capitolo/conto");
					}
				}

			} catch (Exception e) {
				String errorMessage = "Non è stato possibile verificare la disponibilità dell'importo inserito, ERROR: " + e.getMessage();
				logger.error(errorMessage);
				resultVerificaImportoADSPBean.setInError(true);
				resultVerificaImportoADSPBean.getListaDatiContabiliADSP().clear();
				resultVerificaImportoADSPBean.getListaDatiContabiliADSP().addAll(listaDatiContabiliADSP);
				resultVerificaImportoADSPBean.setDefaultMessage(errorMessage);
			}
		}else {
			resultVerificaImportoADSPBean.getListaDatiContabiliADSP().addAll(listaDatiContabiliADSP);
			if(flgSenzaImpegniCont.equals("true")) {
				resultVerificaImportoADSPBean.setInError(true);
				resultVerificaImportoADSPBean.setDefaultMessage("Azione non consentita per RdA che non comporta nuovi impegni di spesa in contabilità");
			}					
		}

		return resultVerificaImportoADSPBean;
	}
	
	
	
	private void settaDisponibilitaDatiContabili(List<DatiContabiliADSPXmlBean> listaDatiContabiliDaRitornare, List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP,
			String keyCapitolo, String statoDisponibilita) {
		for (DatiContabiliADSPXmlBean bean : listaDatiContabiliADSP) {
			if(bean.getKeyCapitolo().equalsIgnoreCase(keyCapitolo)) {
				bean.setDisponibilitaImporto(statoDisponibilita);
				listaDatiContabiliDaRitornare.add(bean);
			}
		}
		
	}

	private DatiContabiliADSPXmlBean getDatoContabileFromKeyCapitolo(String keyCapitolo, List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP) {
		for (DatiContabiliADSPXmlBean bean : listaDatiContabiliADSP) {
			if(bean.getKeyCapitolo().equalsIgnoreCase(keyCapitolo)) {
				return bean;
			}
		}
		return null;
	}

	private String getAnnoCorrente() {		
		Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.YEAR));
	}

}
