/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatiprocxatt_jBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesInsprocessfoBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesLogchiusurataskdettprocBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesTrovaprocessiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CriteriAvanzatiPratiche;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ModelloAttivitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.PraticaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.PraticaXMLBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.XmlDatiProcOutBean;
import it.eng.client.DmpkProcessesGetdatiprocxatt_j;
import it.eng.client.DmpkProcessesInsprocessfo;
import it.eng.client.DmpkProcessesLogchiusurataskdettproc;
import it.eng.client.DmpkProcessesTrovaprocessi;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="PraticheDataSource")
public class PraticheDataSource extends AbstractFetchDataSource<PraticaBean>{
	
	private static final Logger log = Logger.getLogger(PraticheDataSource.class);	
	
	@Override
	public PaginatorBean<PraticaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		List<PraticaBean> data = new ArrayList<PraticaBean>();
				
		String nomeLista = getExtraparams().get("nomeLista");
		String idPratica = getExtraparams().get("idPratica");
		
		DmpkProcessesTrovaprocessiBean trovaProcessiInput = new DmpkProcessesTrovaprocessiBean();
		trovaProcessiInput.setCodidconnectiontokenin(token);
		trovaProcessiInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		trovaProcessiInput.setColorderbyio(null);
		trovaProcessiInput.setFlgdescorderbyio(null);  
		trovaProcessiInput.setFlgsenzapaginazionein(1);
		trovaProcessiInput.setNropaginaio(null);
		trovaProcessiInput.setBachsizeio(null);
		trovaProcessiInput.setOverflowlimitin(null);
		trovaProcessiInput.setFlgsenzatotin(null);
		
		CriteriAvanzatiPratiche criteriAvanzati = new CriteriAvanzatiPratiche();
		if(!isBackOffice()) {
			criteriAvanzati.setUseridProponente(loginBean.getUserid());
			criteriAvanzati.setProfiloProponente("AZIENDA-ENTE");
		}
		trovaProcessiInput.setColtoreturnin("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,51,52,59,60,61,62");
		
		if(StringUtils.isNotBlank(idPratica)) {
			
			criteriAvanzati.setIdProcess(idPratica);
			
		} else if(StringUtils.isNotBlank(nomeLista)) {
			
			criteriAvanzati.setSezioneHomeProponente(nomeLista);
			
		} else {		
			
			trovaProcessiInput.setColtoreturnin(null);
			
			if(criteria!=null && criteria.getCriteria()!=null){		
				for(Criterion crit : criteria.getCriteria()){
					if("cerca".equals(crit.getFieldName())) 
					{
						String filtroCerca = (String) crit.getValue();	
						if(StringUtils.isNotBlank(filtroCerca)) {
							
						}
					} 
					else if("stato".equals(crit.getFieldName())) 
					{
						String filtroStato = (String) crit.getValue();	
						if(StringUtils.isNotBlank(filtroStato) && !filtroStato.equals("Qualsiasi stato")) {
							if(filtroStato.equals("Da inviare / in corso")) {
								
							} else if(filtroStato.equals("Da inviare")) {
								
							} else if(filtroStato.equals("In corso")) {
								
							} else if(filtroStato.equals("Concluse")) {
								
							}
						}
					} 
					else if("tipo".equals(crit.getFieldName())) 
					{
						String filtroTipo = (String) crit.getValue();
						if(StringUtils.isNotBlank(filtroTipo) && !filtroTipo.equals("Qualsiasi tipo")) {
							
						}
					} 
					else if("aNomeDi".equals(crit.getFieldName())) 
					{
						String filtroANomeDi = (String) crit.getValue();
						if(StringUtils.isNotBlank(filtroANomeDi) && !filtroANomeDi.equals("A mio nome")) {
							
						}
					} 
					else if("data".equals(crit.getFieldName())) 
					{
						String filtroData = (String) crit.getValue();
						if(StringUtils.isNotBlank(filtroData) && !filtroData.equals("Qualsiasi data")) {
							if(filtroData.equals("Oggi")) {
								
							} else if(filtroData.equals("Ieri")) {
								
							} else if(filtroData.equals("Ultimi 7 giorni")) {
								
							} else if(filtroData.equals("Settimana corrente")) {
								
							} else if(filtroData.equals("Settimana scorsa")) {
								
							} else if(filtroData.equals("Mese corrente")) {
								
							} else if(filtroData.equals("Mese scorso")) {
								
							} else if(filtroData.equals("Anno corrente")) {
								
							} else {
								
							}
						}
					} 
					else if("ordinamento".equals(crit.getFieldName())) 
					{
						String ordinamento = (String) crit.getValue();
						if(StringUtils.isNotBlank(ordinamento)) {
							if(ordinamento.equals("Ordina per data inserimento")) {
								trovaProcessiInput.setColorderbyio(null);	
								trovaProcessiInput.setFlgdescorderbyio(null);	
							} else if(ordinamento.equals("Ordina per data invio")) {
								trovaProcessiInput.setColorderbyio(null);
								trovaProcessiInput.setFlgdescorderbyio(null);
							} else if(ordinamento.equals("Ordina per richiedente")) {
								trovaProcessiInput.setColorderbyio(null);	
								trovaProcessiInput.setFlgdescorderbyio(null);
							} else if(ordinamento.equals("Ordina per tipo")) {
								trovaProcessiInput.setColorderbyio(null);	
								trovaProcessiInput.setFlgdescorderbyio(null);
							}				
						}
					}		
				}
			}
		}
				
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		trovaProcessiInput.setCriteriavanzatiio(lXmlUtilitySerializer.bindXml(criteriAvanzati));		
		
		DmpkProcessesTrovaprocessi trovaProcessi = new DmpkProcessesTrovaprocessi();
		
		StoreResultBean<DmpkProcessesTrovaprocessiBean> trovaProcessiOutput = trovaProcessi.execute(getLocale(), loginBean, trovaProcessiInput);
		
		if(trovaProcessiOutput.isInError()) {
			if(StringUtils.isNotBlank(trovaProcessiOutput.getDefaultMessage())) {
				throw new StoreException(trovaProcessiOutput.getDefaultMessage());		
			} else {
				throw new StoreException("Errore generico");	
			}		
		} else if(StringUtils.isNotBlank(trovaProcessiOutput.getDefaultMessage())) {
			addMessage(trovaProcessiOutput.getDefaultMessage(), "", MessageType.WARNING);
		}

		if(trovaProcessiOutput.getResultBean() != null && trovaProcessiOutput.getResultBean().getResultout()!=null) {
			StringReader sr = new StringReader(trovaProcessiOutput.getResultBean().getResultout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));	
					PraticaBean pratica = new PraticaBean();
					pratica.setIdPratica(v.get(0)); // colonna 1
					pratica.setTipoProcedimento(v.get(46)); // colonna 47
					pratica.setDescrTipoProcedimento(v.get(2)); // colonna 3
					pratica.setIdSoggEsterno(v.get(58)); //colonna 59					
					pratica.setIdUd(v.get(59)); //colonna 60		
					pratica.setIdSoggGiuridico(v.get(60)); //colonna 61
					pratica.setStato(v.get(9)); // colonna 10					
					pratica.setDescrizioneSintetica(v.get(3)); //colonna 4
					pratica.setIntestazione(v.get(1));
					pratica.setNomeProgetto(v.get(48)); // colonna 49	
					pratica.setIdDocAttestatoAvvioProc(v.get(61)); // colonna 62			
					data.add(pratica);												 
				}
			}
		}

		PaginatorBean<PraticaBean> lPaginatorBean = new PaginatorBean<PraticaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}
	
	private boolean isBackOffice() {
		String isBackOffice = getExtraparams().get("isBackOffice");
		return StringUtils.isNotBlank(isBackOffice) ? new Boolean(isBackOffice) : false;		
	}	
	
	@Override
	public PraticaBean get(PraticaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(loginBean.getSchema());
		
		DmpkProcessesGetdatiprocxatt_jBean input = new DmpkProcessesGetdatiprocxatt_jBean();
		input.setIdprocessin(StringUtils.isNotBlank(bean.getIdPratica()) ? new BigDecimal(bean.getIdPratica()) : null);
		input.setIduserin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : loginBean.getIdUser());
		
		DmpkProcessesGetdatiprocxatt_j dmpkProcessesGetdatiprocxatt_j = new DmpkProcessesGetdatiprocxatt_j();
		StoreResultBean<DmpkProcessesGetdatiprocxatt_jBean> output = dmpkProcessesGetdatiprocxatt_j.execute(getLocale(), schemaBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);		
		} 
		
		if(StringUtils.isNotBlank(output.getResultBean().getXmldatiprocout())) {
			XmlDatiProcOutBean scXmlDatiProc = new XmlDatiProcOutBean();
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			scXmlDatiProc = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getXmldatiprocout(), XmlDatiProcOutBean.class);      	       
			if(scXmlDatiProc != null) {	
				bean.setTipoProcedimento(scXmlDatiProc.getTipoProcedimento());
				bean.setDescrTipoProcedimento(scXmlDatiProc.getDescrTipoProcedimento());
				bean.setIdSoggEsterno(scXmlDatiProc.getIdSoggEsterno());					
				bean.setIdUd(scXmlDatiProc.getIdUd());		
				bean.setIdSoggGiuridico(scXmlDatiProc.getIdSoggGiuridico());
				bean.setStato(scXmlDatiProc.getStato());					
				bean.setDescrizioneSintetica(scXmlDatiProc.getDescrizioneSintetica());
				bean.setIntestazione(scXmlDatiProc.getIntestazione());
				bean.setNomeProgetto(scXmlDatiProc.getNomeProgetto());	
				bean.setIdDocAttestatoAvvioProc(scXmlDatiProc.getIdDocAttestatoAvvioProc());	
				bean.setMessaggioUltimaAttivita(scXmlDatiProc.getMessaggioUltimaAttivita());
				bean.setListaModelliAttivita(scXmlDatiProc.getListaModelliAttivita());
				bean.setWarningConcorrenza(scXmlDatiProc.getWarningConcorrenza());
				bean.setIsAtto(scXmlDatiProc.getIsAtto());
			}
				
		}
		
//		setTestData(bean);
		
		return bean;
	}
	
	public void setTestData(PraticaBean bean) {
		
		List<ModelloAttivitaBean> listaModelliAttivita = new ArrayList<ModelloAttivitaBean>();
		
//		ModelloAttivitaBean lModelloAttivitaBean = new ModelloAttivitaBean();	
//		lModelloAttivitaBean.setActivityName("redazione_testo");
//		lModelloAttivitaBean.setIdTipoDoc(null);
//		lModelloAttivitaBean.setNomeTipoDoc(null);
//		lModelloAttivitaBean.setIdModello("111");
//		lModelloAttivitaBean.setNomeModello("Nuovo modello proposta decreto");
//		lModelloAttivitaBean.setFlgDaFirmare(true);
//		lModelloAttivitaBean.setNomeFile("test_modello");
//		lModelloAttivitaBean.setFlgFirmaAuto(true);
//		lModelloAttivitaBean.setProviderFirmaAuto("ARUBA_FA");
//		lModelloAttivitaBean.setFirmaInDelegaFirmaAuto("mferrign");
//		lModelloAttivitaBean.setUserIdFirmaAuto("cosnigro");
//		lModelloAttivitaBean.setPasswordFirmaAuto("kVGDxMzlLVyY");
//		lModelloAttivitaBean.setFlgDelegaFirmaAuto(true);
//		lModelloAttivitaBean.setFlgPostAvanzamentoFlusso(true);
//		listaModelliAttivita.add(lModelloAttivitaBean);
		
		
//		ModelloAttivitaBean lModelloAttivitaBean1 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean1.setActivityName("firma_adottante");
//		lModelloAttivitaBean1.setIdTipoDoc("9999");
//		lModelloAttivitaBean1.setNomeTipoDoc("Proposta di decreto");
//		lModelloAttivitaBean1.setIdModello("175");
//		lModelloAttivitaBean1.setNomeModello("Parere di regolarità tecnica delibera di Giunta");
//		lModelloAttivitaBean1.setFlgDaFirmare(true);
//		lModelloAttivitaBean1.setNomeFile("modello_firma_adottante_1");
//		lModelloAttivitaBean1.setEsitiXGenModello("esito1|*|esito2");
//		listaModelliAttivita.add(lModelloAttivitaBean1);
//		ModelloAttivitaBean lModelloAttivitaBean2 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean2.setActivityName("firma_adottante");
//		lModelloAttivitaBean2.setIdTipoDoc("24002");
//		lModelloAttivitaBean2.setNomeTipoDoc("TARI - Rimborso Generale");
//		lModelloAttivitaBean2.setIdModello("176");
//		lModelloAttivitaBean2.setNomeModello("Parere di regolarità contabile delibera di Giunta");
//		lModelloAttivitaBean2.setFlgDaFirmare(false);
//		lModelloAttivitaBean2.setNomeFile("modello_firma_adottante_2");
//		lModelloAttivitaBean1.setEsitiXGenModello("esito1|*|esito2");
//		listaModelliAttivita.add(lModelloAttivitaBean2);
//		ModelloAttivitaBean lModelloAttivitaBean3 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean3.setActivityName("controllo_ragioneria");
//		lModelloAttivitaBean3.setIdTipoDoc("566");
//		lModelloAttivitaBean3.setNomeTipoDoc("Richiesta Pagamento");
//		lModelloAttivitaBean3.setIdModello("118");
//		lModelloAttivitaBean3.setNomeModello("Mod_Istanza_Autotutela");
//		lModelloAttivitaBean3.setFlgDaFirmare(false);
//		lModelloAttivitaBean3.setNomeFile("modello_controllo_ragioneria_1");
//		lModelloAttivitaBean3.setEsitiXGenModello("esito1|*|esito2");
//		listaModelliAttivita.add(lModelloAttivitaBean3);
//		ModelloAttivitaBean lModelloAttivitaBean4 = new ModelloAttivitaBean();		
//		lModelloAttivitaBean4.setActivityName("stesura_testo_ord");
//		lModelloAttivitaBean4.setIdTipoDoc("626");
//		lModelloAttivitaBean4.setNomeTipoDoc("Liquidazione tecnica");
//		lModelloAttivitaBean4.setIdModello("134");
//		lModelloAttivitaBean4.setNomeModello("DISPOSITIVO_DELIBERA");
//		lModelloAttivitaBean4.setFlgDaFirmare(true);
//		lModelloAttivitaBean4.setNomeFile("stesura_testo_1");
//		lModelloAttivitaBean4.setEsitiXGenModello("esito1|*|esito2");
//		lModelloAttivitaBean4.setFlgFirmaAuto(true);
//		lModelloAttivitaBean4.setProviderFirmaAuto("ARUBA_FA");
//		lModelloAttivitaBean4.setFirmaInDelegaFirmaAuto("mferrign");
//		lModelloAttivitaBean4.setUserIdFirmaAuto("cosnigro");
//		lModelloAttivitaBean4.setPasswordFirmaAuto("kVGDxMzlLVyY");
//		lModelloAttivitaBean4.setFlgDelegaFirmaAuto(true);
//		listaModelliAttivita.add(lModelloAttivitaBean4);
//		ModelloAttivitaBean lModelloAttivitaBean5 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean5.setActivityName("stesura_testo_ord");
//		lModelloAttivitaBean5.setIdTipoDoc("50001");
//		lModelloAttivitaBean5.setNomeTipoDoc("Parere dei revisori contabili");
//		lModelloAttivitaBean5.setIdModello("180");
//		lModelloAttivitaBean5.setNomeModello("Modello per Decreto ADSP");
//		lModelloAttivitaBean5.setFlgDaFirmare(true);
//		lModelloAttivitaBean5.setNomeFile("stesura_testo_2");
//		lModelloAttivitaBean5.setEsitiXGenModello("esito1|*|esito2");
//		lModelloAttivitaBean5.setFlgFirmaAuto(true);
//		lModelloAttivitaBean5.setProviderFirmaAuto("ARUBA_FA");
//		lModelloAttivitaBean5.setFirmaInDelegaFirmaAuto("mferrign");
//		lModelloAttivitaBean5.setUserIdFirmaAuto("cosnigro");
//		lModelloAttivitaBean5.setPasswordFirmaAuto("kVGDxMzlLVyY");
//		lModelloAttivitaBean5.setFlgDelegaFirmaAuto(true);
//		listaModelliAttivita.add(lModelloAttivitaBean5);
//		ModelloAttivitaBean lModelloAttivitaBean6 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean6.setActivityName("visto_rdp");
//		lModelloAttivitaBean6.setIdTipoDoc("24020");
//		lModelloAttivitaBean6.setNomeTipoDoc("Parere di regolarità contabile");
//		lModelloAttivitaBean6.setIdModello("188");
//		lModelloAttivitaBean6.setNomeModello("Odg di consiglio");
//		lModelloAttivitaBean6.setFlgDaFirmare(true);
//		lModelloAttivitaBean6.setNomeFile("visto_rdp_1");
//		lModelloAttivitaBean6.setEsitiXGenModello("esito1|*|esito2");
//		listaModelliAttivita.add(lModelloAttivitaBean6);
//		ModelloAttivitaBean lModelloAttivitaBean7 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean7.setActivityName("visto_rdp");
//		lModelloAttivitaBean7.setIdTipoDoc("24019");
//		lModelloAttivitaBean7.setNomeTipoDoc("Parere di regolarità tecnica");
//		lModelloAttivitaBean7.setIdModello("169");
//		lModelloAttivitaBean7.setNomeModello("Certificato di pubblicazione");
//		lModelloAttivitaBean7.setFlgDaFirmare(false);
//		lModelloAttivitaBean7.setNomeFile("visto_rdp_2");
//		lModelloAttivitaBean7.setEsitiXGenModello("esito1|*|esito2");
//		listaModelliAttivita.add(lModelloAttivitaBean7);		
//		bean.setListaModelliAttivita(listaModelliAttivita);
		
//		ModelloAttivitaBean lModelloAttivitaBean1 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean1.setActivityName("redazione_testo_proposta");
//		lModelloAttivitaBean1.setIdTipoDoc("1153");
//		lModelloAttivitaBean1.setNomeTipoDoc("Ordinanza sindacale");
//		lModelloAttivitaBean1.setIdModello("221");
//		lModelloAttivitaBean1.setNomeModello("Test ckeditor");
//		lModelloAttivitaBean1.setFlgDaFirmare(true);
//		lModelloAttivitaBean1.setNomeFile("lModelloAttivitaBean1");
//		lModelloAttivitaBean1.setEsitiXGenModello("pronta, prosegui iter");
//		lModelloAttivitaBean1.setFlgFirmaGrafica(true);
//		lModelloAttivitaBean1.setNroPaginaFirmaGrafica("1");
//		lModelloAttivitaBean1.setNroRigaFirmaGrafica("1");
//		lModelloAttivitaBean1.setNroColonnaFirmaGrafica("1");
//		lModelloAttivitaBean1.setTestoFirmaGrafica("Firmato dal contabile $intestatarioCertificato$ in data $dataCorrente$");
//		listaModelliAttivita.add(lModelloAttivitaBean1);
		
//		ModelloAttivitaBean lModelloAttivitaBean2 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean2.setActivityName("redazione_testo_proposta");
//		lModelloAttivitaBean2.setIdTipoDoc("2025");
//		lModelloAttivitaBean2.setNomeTipoDoc("Comunicazione");
//		lModelloAttivitaBean2.setIdModello("221");
//		lModelloAttivitaBean2.setNomeModello("Test ckeditor");
//		lModelloAttivitaBean2.setFlgDaFirmare(true);
//		lModelloAttivitaBean2.setNomeFile("lModelloAttivitaBean2");
//		lModelloAttivitaBean2.setEsitiXGenModello("pronta, prosegui iter");
//		lModelloAttivitaBean2.setFlgFirmaGrafica(false);
//		lModelloAttivitaBean2.setNroPaginaFirmaGrafica("1");
//		lModelloAttivitaBean2.setNroRigaFirmaGrafica("1");
//		lModelloAttivitaBean2.setNroColonnaFirmaGrafica("1");
//		lModelloAttivitaBean2.setTestoFirmaGrafica("Firmato dal contabile $intestatarioCertificato$ in data $dataCorrente$");
//		listaModelliAttivita.add(lModelloAttivitaBean2);
		
//		ModelloAttivitaBean lModelloAttivitaBean3 = new ModelloAttivitaBean();	
//		lModelloAttivitaBean3.setActivityName("redazione_testo_proposta");
//		lModelloAttivitaBean3.setIdTipoDoc("50002");
//		lModelloAttivitaBean3.setNomeTipoDoc("Allegato contabile");
//		lModelloAttivitaBean3.setIdModello("221");
//		lModelloAttivitaBean3.setNomeModello("Test ckeditor");
//		lModelloAttivitaBean3.setFlgDaFirmare(false);
//		lModelloAttivitaBean3.setNomeFile("lModelloAttivitaBean3");
//		lModelloAttivitaBean3.setEsitiXGenModello("pronta, prosegui iter");
//		lModelloAttivitaBean3.setFlgFirmaGrafica(true);
//		lModelloAttivitaBean3.setNroPaginaFirmaGrafica("1");
//		lModelloAttivitaBean3.setNroRigaFirmaGrafica("1");
//		lModelloAttivitaBean3.setNroColonnaFirmaGrafica("1");
//		lModelloAttivitaBean3.setTestoFirmaGrafica("Firmato dal contabile $intestatarioCertificato$ in data $dataCorrente$");
//		listaModelliAttivita.add(lModelloAttivitaBean3);
		
//		bean.setListaModelliAttivita(listaModelliAttivita);
	}

	@Override
	public PraticaBean add(PraticaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		
		DmpkProcessesInsprocessfoBean input = new DmpkProcessesInsprocessfoBean();		
		input.setCodidconnectiontokenin(token);		
		input.setIdprocesstypein(bean.getTipoProcedimento()!=null ? new BigDecimal(bean.getTipoProcedimento()) : null);
		input.setEtichettaproponentein(bean.getNomeProgetto());
//		input.setTipoprogettoin(bean.getTipoProgetto());
		String tipoImpianto = "";
		if(bean.getTipoAllegato() != null) {
			if(bean.getTipoAllegato().equals("A1")) {
				tipoImpianto = bean.getTipoOstA1() != null ? bean.getTipoOstA1() : "";
			} else if(bean.getTipoAllegato().equals("B1")) {
				tipoImpianto = bean.getTipoOstB1() != null ? bean.getTipoOstB1() : "";		
			}
		}
		input.setTipoprogettoin(bean.getTipoAllegato()+"|*|"+bean.getFlgNuovoModifica()+"|*|"+tipoImpianto);
		input.setOggettoin(bean.getDescrizioneSintetica());		
		input.setUseridproponentein(loginBean.getUserid());
		input.setProfiloproponentein("AZIENDA-ENTE");		
		input.setRichiedentexmlin(getXmlPraticheBean(bean));

		DmpkProcessesInsprocessfo dmpkProcessesInsprocessfo = new DmpkProcessesInsprocessfo();
		StoreResultBean<DmpkProcessesInsprocessfoBean> output = dmpkProcessesInsprocessfo.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdPratica(output.getResultBean().getIdprocessout()!=null ? output.getResultBean().getIdprocessout().toString() : null);
		
		return bean;
	}	
	
	private String getXmlPraticheBean(PraticaBean bean)throws JAXBException, IllegalAccessException,InvocationTargetException, NoSuchMethodException {
		String xmlPratiche;
		List<PraticaXMLBean> lList = new ArrayList<PraticaXMLBean>();
		
		PraticaXMLBean praticaXMLBean = new PraticaXMLBean();		
		praticaXMLBean.setDenominazione(bean.getDenominazione());
		praticaXMLBean.setCodFiscale(bean.getCodFiscale());
		praticaXMLBean.setPartitaIva(bean.getPartitaIva());
		praticaXMLBean.setIdSoggGiuridico(bean.getIdSoggGiuridico());
		
		if(bean.getIndirizzoSedeLegale() != null && bean.getIndirizzoSedeLegale().size() > 0) {			
			IndirizzoSoggettoBean indirizzoSedeLegale = bean.getIndirizzoSedeLegale().get(0);			
			praticaXMLBean.setNomeStatoSedeLegale(indirizzoSedeLegale.getStato());
			if(StringUtils.isNotBlank(praticaXMLBean.getNomeStatoSedeLegale()) && "ITALIA".equals(praticaXMLBean.getNomeStatoSedeLegale())) {
				praticaXMLBean.setTipoToponimoSedeLegale(indirizzoSedeLegale.getTipoToponimo());
				praticaXMLBean.setToponimoSedeLegale(indirizzoSedeLegale.getToponimo());								
				praticaXMLBean.setComuneSedeLegale(indirizzoSedeLegale.getComune());
				praticaXMLBean.setNomeComuneSedeLegale(indirizzoSedeLegale.getNomeComune());			
				praticaXMLBean.setCapSedeLegale(indirizzoSedeLegale.getCap());				
			} else {
				praticaXMLBean.setToponimoSedeLegale(indirizzoSedeLegale.getIndirizzo());								
				praticaXMLBean.setNomeComuneSedeLegale(indirizzoSedeLegale.getCitta());				
			}
			praticaXMLBean.setNroCivicoSedeLegale(indirizzoSedeLegale.getCivico());
			praticaXMLBean.setLocalitaFrazioneSedeLegale(indirizzoSedeLegale.getFrazione());
			praticaXMLBean.setZonaSedeLegale(indirizzoSedeLegale.getZona());
			praticaXMLBean.setComplementoIndirizzoSedeLegale(indirizzoSedeLegale.getComplementoIndirizzo());				
		}
		
		if(bean.getIndirizzoRecapito() != null && bean.getIndirizzoRecapito().size() > 0) {
			if(StringUtils.isBlank(praticaXMLBean.getToponimoRecapito()) &&
			   StringUtils.isBlank(praticaXMLBean.getComuneRecapito()) &&
			   StringUtils.isBlank(praticaXMLBean.getNomeComuneRecapito()) &&
			   StringUtils.isBlank(praticaXMLBean.getCapRecapito())) {		
				//in questo caso non considero l'indirizzo recapito
			} else {
				IndirizzoSoggettoBean indirizzoRecapito = bean.getIndirizzoRecapito().get(0);
				praticaXMLBean.setNomeStatoRecapito(indirizzoRecapito.getStato());
				if(StringUtils.isNotBlank(praticaXMLBean.getNomeStatoRecapito()) && "ITALIA".equals(praticaXMLBean.getNomeStatoRecapito())) {				
					praticaXMLBean.setTipoToponimoRecapito(indirizzoRecapito.getTipoToponimo());
					praticaXMLBean.setToponimoRecapito(indirizzoRecapito.getToponimo());								
					praticaXMLBean.setComuneRecapito(indirizzoRecapito.getComune());
					praticaXMLBean.setNomeComuneRecapito(indirizzoRecapito.getNomeComune());			
					praticaXMLBean.setCapRecapito(indirizzoRecapito.getCap());				
				} else {
					praticaXMLBean.setToponimoRecapito(indirizzoRecapito.getIndirizzo());								
					praticaXMLBean.setNomeComuneRecapito(indirizzoRecapito.getCitta());				
				}
				praticaXMLBean.setNroCivicoRecapito(indirizzoRecapito.getCivico());
				praticaXMLBean.setLocalitaFrazioneRecapito(indirizzoRecapito.getFrazione());
				praticaXMLBean.setZonaRecapito(indirizzoRecapito.getZona());
				praticaXMLBean.setComplementoIndirizzoRecapito(indirizzoRecapito.getComplementoIndirizzo());
			}
		}
		
		praticaXMLBean.setEmail(bean.getEmail());
		praticaXMLBean.setTelefono(bean.getTelefono());	
		
		lList.add(praticaXMLBean);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlPratiche = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlPratiche;
	}
	
	// Per la gestione dei warning di concorrenza task	
	public PraticaBean chiusuraDettaglioAtto(PraticaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		
		DmpkProcessesLogchiusurataskdettprocBean input = new DmpkProcessesLogchiusurataskdettprocBean();		
		input.setCodidconnectiontokenin(token);		
		input.setIdprocessin(new BigDecimal(bean.getIdPratica()));		

		DmpkProcessesLogchiusurataskdettproc store = new DmpkProcessesLogchiusurataskdettproc();
		StoreResultBean<DmpkProcessesLogchiusurataskdettprocBean> output = store.execute(getLocale(), loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);		
		} else if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}
		
		return bean;
	}	
	
}
