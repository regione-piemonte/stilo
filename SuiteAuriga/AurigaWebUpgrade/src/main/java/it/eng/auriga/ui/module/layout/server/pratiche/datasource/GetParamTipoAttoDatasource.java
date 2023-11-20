/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesGetparametritipoattoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.UfficioProponenteAttoDatasource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.auriga.ui.module.layout.server.task.bean.AttributiCustomCablatiAttoXmlBean;
import it.eng.auriga.ui.module.layout.server.task.bean.ParametriTipoAttoBean;
import it.eng.auriga.ui.module.layout.server.task.bean.XmlDatiEventoOutBean;
import it.eng.client.DmpkDocTypesGetparametritipoatto;
import it.eng.document.function.bean.Flag;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "GetParamTipoAttoDatasource")
public class GetParamTipoAttoDatasource extends AbstractServiceDataSource<AttProcBean, AttProcBean> {

	private static final Logger log = Logger.getLogger(GetParamTipoAttoDatasource.class);

	@Override
	public AttProcBean call(AttProcBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkDocTypesGetparametritipoattoBean input = new DmpkDocTypesGetparametritipoattoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIddoctypein(new BigDecimal(bean.getIdTipoDoc()));
		DmpkDocTypesGetparametritipoatto dmpkDocTypesGetparametritipoatto = new DmpkDocTypesGetparametritipoatto();
		StoreResultBean<DmpkDocTypesGetparametritipoattoBean> output = dmpkDocTypesGetparametritipoatto.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		if (StringUtils.isNotBlank(output.getResultBean().getParametritipoattoout())) {
			XmlDatiEventoOutBean scXmlDatiEvento = new XmlDatiEventoOutBean();
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			log.debug("taskInfoXml: " + output.getResultBean().getParametritipoattoout());
			scXmlDatiEvento = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getParametritipoattoout(), XmlDatiEventoOutBean.class);
			if (scXmlDatiEvento != null) {
				bean.setAttributiAddDocTabs(scXmlDatiEvento.getAttributiAddDocTabs());
				bean.setFlgPubblicazioneAllegatiUguale(scXmlDatiEvento.getFlgPubblicazioneAllegatiUguale() != null && scXmlDatiEvento.getFlgPubblicazioneAllegatiUguale().equalsIgnoreCase("true"));
				bean.setFlgAvvioLiquidazioneContabilia(scXmlDatiEvento.getFlgAvvioLiquidazioneContabilia() == Flag.SETTED);
				bean.setFlgNumPropostaDiffXStruttura(scXmlDatiEvento.getFlgNumPropostaDiffXStruttura() != null && scXmlDatiEvento.getFlgNumPropostaDiffXStruttura().equalsIgnoreCase("true"));
				ParametriTipoAttoBean lParametriTipoAttoBean = new ParametriTipoAttoBean();
				lParametriTipoAttoBean.setAttributiCustomCablati(scXmlDatiEvento.getParametriTipoAttoAttributiCustomCablati()); 
//				lParametriTipoAttoBean.setAttributiCustomCablati(new java.util.ArrayList<it.eng.auriga.ui.module.layout.server.task.bean.AttributiCustomCablatiAttoXmlBean>());
				// va' fatto anche qui?
				String altriParamLoadComboUfficioProponente = null;
				if(lParametriTipoAttoBean.getAttributiCustomCablati() != null) {
					for(AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBean : lParametriTipoAttoBean.getAttributiCustomCablati()) {
						if(lAttributiCustomCablatiAttoXmlBean.getAttrName() != null && lAttributiCustomCablatiAttoXmlBean.getAttrName().equalsIgnoreCase("ID_UO_PROPONENTE")) {
							altriParamLoadComboUfficioProponente = lAttributiCustomCablatiAttoXmlBean.getAltriParametriLoadCombo();
							break;
						}
					}
				}					
				UfficioProponenteAttoDatasource lUfficioProponenteAttoDatasource = new UfficioProponenteAttoDatasource();		
				lUfficioProponenteAttoDatasource.setSession(getSession());
				Map<String, String> extraparams = new LinkedHashMap<String, String>();
				extraparams.put("altriParamLoadCombo", altriParamLoadComboUfficioProponente);			
				extraparams.put("idTipoDoc", bean.getIdTipoDoc());			
				lUfficioProponenteAttoDatasource.setExtraparams(extraparams);
				PaginatorBean<UoProtocollanteBean> valoriUfficioProponente = lUfficioProponenteAttoDatasource.fetch(null, null, null, null);
				lParametriTipoAttoBean.setValoriUfficioProponente(valoriUfficioProponente != null ? valoriUfficioProponente.getData() : new ArrayList<UoProtocollanteBean>());
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultXTipoAtto(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegrante() == Flag.SETTED);
				} else {
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultXTipoAtto(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FLG_ALLEG_ATTO_PARTE_INT_DEFAULT"));
				}
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultOrdPermanente(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() == Flag.SETTED);
				}
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoParteIntDefaultOrdTemporanea(scXmlDatiEvento.getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() == Flag.SETTED);
				}				
				if(scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() != null && scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAllegAttoPubblSepDefaultXTipoAtto(scXmlDatiEvento.getParametriTipoAttoDefaultAllegPubblSeparata() == Flag.SETTED);
				} else {
					lParametriTipoAttoBean.setFlgAllegAttoPubblSepDefaultXTipoAtto(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FLG_ALLEG_ATTO_PUBBL_SEPARATA_DEFAULT"));
				}				
				if(scXmlDatiEvento.getParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti() != null && scXmlDatiEvento.getParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti() != Flag.NULL) {
					// se è null non va settato
					lParametriTipoAttoBean.setFlgAttivaSceltaPosizioneAllegatiUniti(scXmlDatiEvento.getParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti() == Flag.SETTED);
				}
				bean.setParametriTipoAtto(lParametriTipoAttoBean);
			}
		}
//		setTestData(bean);		
		return bean;
	}

	public void setTestData(AttProcBean bean) {		
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setAttrName("UFF_COMPETENTE_RAG");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setAttrLabel("Prova test");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG.setAltriParametriLoadCombo("TIPI_SOGG_INT|*|UO|*|ID_USER_LAVORO|*|$ID_USER_LAVORO$|*|FLG_ANCHE_CON_DELEGA|*|1|*|FLG_INCL_SOTTOUO_SV|*|1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanUFF_COMPETENTE_RAG);
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanANNO_CONTABILE_COMPETENZA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanANNO_CONTABILE_COMPETENZA.setAttrName("ANNO_CONTABILE_COMPETENZA");
//		lAttributiCustomCablatiAttoXmlBeanANNO_CONTABILE_COMPETENZA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanANNO_CONTABILE_COMPETENZA.setAttrLabel("Anno competenza");
//		lAttributiCustomCablatiAttoXmlBeanANNO_CONTABILE_COMPETENZA.setFlgObbligatorio("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanANNO_CONTABILE_COMPETENZA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO.setAttrName("TASK_RESULT_2_FONDI_PNRR_RADIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO.setAttrLabel("Fondi PNRR");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO.setValoriPossibili("PNRR 1|*|PNRR 2");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO.setValoreFisso("PNRR 1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PNRR_RADIO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI.setAttrName("TASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI.setAttrLabel("Programmazione acquisti");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI.setValoriPossibili("SI|*|NO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI.setValoreFisso("SI");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI.setFlgObbligatorio("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCUI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCUI.setAttrName("CUI");
//		lAttributiCustomCablatiAttoXmlBeanCUI.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanCUI.setAttrLabel("CUI");
//		lAttributiCustomCablatiAttoXmlBeanCUI.setMaxNumValori("");
//		lAttributiCustomCablatiAttoXmlBeanCUI.setFlgObbligatorio("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCUI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCUI_ANNO_RIF = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCUI_ANNO_RIF.setAttrName("CUI_ANNO_RIF");
//		lAttributiCustomCablatiAttoXmlBeanCUI_ANNO_RIF.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanCUI_ANNO_RIF.setAttrLabel("Anno riferimento");
//		lAttributiCustomCablatiAttoXmlBeanCUI_ANNO_RIF.setValoreFisso("2022");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCUI_ANNO_RIF);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA.setAttrName("TASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA.setAttrLabel("visto cap. sotto soglia");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA.setAttrName("TASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA.setAttrLabel("visto cap. sopra soglia");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA);

//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_LIQUIDAZIONE_AVB = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_LIQUIDAZIONE_AVB.setAttrName("DATI-CONT-LIQ-AVB");
//		lAttributiCustomCablatiAttoXmlBeanDATI_LIQUIDAZIONE_AVB.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanDATI_LIQUIDAZIONE_AVB.setAttrLabel("Dati liquidazione_da test");
//		lAttributiCustomCablatiAttoXmlBeanDATI_LIQUIDAZIONE_AVB.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_LIQUIDAZIONE_AVB);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDT_TERM_FIRME_CONSIGLIERI_COPROP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDT_TERM_FIRME_CONSIGLIERI_COPROP.setAttrName("DT_TERM_FIRME_CONSIGLIERI_COPROP");
//		lAttributiCustomCablatiAttoXmlBeanDT_TERM_FIRME_CONSIGLIERI_COPROP.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanDT_TERM_FIRME_CONSIGLIERI_COPROP.setAttrLabel("Termine raccolta sottoscrizioni co-proponenti_da test");
//		lAttributiCustomCablatiAttoXmlBeanDT_TERM_FIRME_CONSIGLIERI_COPROP.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDT_TERM_FIRME_CONSIGLIERI_COPROP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_X_COMMISSIONI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_X_COMMISSIONI.setAttrName("TASK_RESULT_2_FLG_X_COMMISSIONI");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_X_COMMISSIONI.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_X_COMMISSIONI.setAttrLabel("per commissioni_da test");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_X_COMMISSIONI.setFlgObbligatorio("0");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_X_COMMISSIONI.setValoreFisso("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_X_COMMISSIONI);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanMOTIVAZIONE_INT_RISP_SCRITTA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanMOTIVAZIONE_INT_RISP_SCRITTA.setAttrName("MOTIVAZIONE_INT_RISP_SCRITTA");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVAZIONE_INT_RISP_SCRITTA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVAZIONE_INT_RISP_SCRITTA.setAttrLabel("Motivazione_da test");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVAZIONE_INT_RISP_SCRITTA.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanMOTIVAZIONE_INT_RISP_SCRITTA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_INTERPELLANZA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_INTERPELLANZA.setAttrName("TASK_RESULT_2_TIPO_INTERPELLANZA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_INTERPELLANZA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_INTERPELLANZA.setAttrLabel("tipo interpellanza_da test");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_INTERPELLANZA.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_INTERPELLANZA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_PROPONENTE_ATTO_CONSIGLIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_PROPONENTE_ATTO_CONSIGLIO.setAttrName("ID_PROPONENTE_ATTO_CONSIGLIO");
//		lAttributiCustomCablatiAttoXmlBeanID_PROPONENTE_ATTO_CONSIGLIO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanID_PROPONENTE_ATTO_CONSIGLIO.setAttrLabel("Proponente_da test");
//		lAttributiCustomCablatiAttoXmlBeanID_PROPONENTE_ATTO_CONSIGLIO.setAltriParametriLoadCombo("ID_USER_LAVORO|*|$ID_USER_LAVORO$");
//		lAttributiCustomCablatiAttoXmlBeanID_PROPONENTE_ATTO_CONSIGLIO.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_PROPONENTE_ATTO_CONSIGLIO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_USER_RIF_ATTO_CONSIGLIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_USER_RIF_ATTO_CONSIGLIO.setAttrName("ID_USER_RIF_ATTO_CONSIGLIO");
//		lAttributiCustomCablatiAttoXmlBeanID_USER_RIF_ATTO_CONSIGLIO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanID_USER_RIF_ATTO_CONSIGLIO.setAttrLabel("Assessore/consigliere di riferimento_da test");
//		lAttributiCustomCablatiAttoXmlBeanID_USER_RIF_ATTO_CONSIGLIO.setAltriParametriLoadCombo("ID_USER_LAVORO|*|$ID_USER_LAVORO$");
//		lAttributiCustomCablatiAttoXmlBeanID_USER_RIF_ATTO_CONSIGLIO.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_USER_RIF_ATTO_CONSIGLIO);
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA.setAttrName("ID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA.setAttrLabel("Sostituto");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA.setAltriParametriLoadCombo("ID_UO|*|$ID_UO_PROPONENTE$|*|NRI_LIVELLI_UO|*|$NRI_LIVELLI_UO$|*|STR_IN_DES|*|$STR_IN_DES$");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA.setAttrName("PROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA");
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA.setAttrLabel("provvedimento sostituto");
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA.setAttrName("ID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA.setAttrLabel("Sostituto");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA.setAltriParametriLoadCombo("ID_UO|*|$ID_UO_PROPONENTE$|*|NRI_LIVELLI_UO|*|$NRI_LIVELLI_UO$|*|STR_IN_DES|*|$STR_IN_DES$");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA.setFlgObbligatorio("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA.setAttrName("PROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA");
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA.setAttrLabel("provvedimento sostituto");
//		lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA.setFlgObbligatorio("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_SV_ALTRI_DIR_REG_TECNICA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_SV_ALTRI_DIR_REG_TECNICA.setAttrName("ID_SV_ALTRI_DIR_REG_TECNICA");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_ALTRI_DIR_REG_TECNICA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_ALTRI_DIR_REG_TECNICA.setFlgObbligatorio("0");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_SV_ALTRI_DIR_REG_TECNICA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PROC_EX_COD_APPALTI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PROC_EX_COD_APPALTI.setAttrName("TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PROC_EX_COD_APPALTI.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PROC_EX_COD_APPALTI);		
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_SV_RUP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_SV_RUP.setAttrName("ID_SV_RUP");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_RUP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_SV_RUP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_RUP_ANCHE_ADOTTANTE = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_RUP_ANCHE_ADOTTANTE.setAttrName("TASK_RESULT_2_FLG_RUP_ANCHE_ADOTTANTE");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_RUP_ANCHE_ADOTTANTE.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_RUP_ANCHE_ADOTTANTE);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_UO_PROP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_UO_PROP.setAttrName("ID_SV_RESP_UO_PROP");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_UO_PROP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_UO_PROP);	
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_VISTI_PERFEZIONAMENTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_VISTI_PERFEZIONAMENTO.setAttrName("ID_SV_RESP_VISTI_PERFEZIONAMENTO");
//		lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_VISTI_PERFEZIONAMENTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanID_SV_RESP_VISTI_PERFEZIONAMENTO);	
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_SG = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_SG.setAttrName("TASK_RESULT_2_VISTO_SG");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_SG.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_SG);	
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_ESCL_CIG = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_ESCL_CIG.setAttrName("TASK_RESULT_2_FLG_ESCL_CIG");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_ESCL_CIG.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_ESCL_CIG);	
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanMOTIVO_ESCLUSIONE_CIG = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanMOTIVO_ESCLUSIONE_CIG.setAttrName("MOTIVO_ESCLUSIONE_CIG");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVO_ESCLUSIONE_CIG.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanMOTIVO_ESCLUSIONE_CIG);	
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCIG = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCIG.setAttrName("CIG");
//		lAttributiCustomCablatiAttoXmlBeanCIG.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCIG);	
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB.setAttrName("DATI_CONTABILI_AVB");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB);		
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_IMP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_IMP.setAttrName("DATI_CONTABILI_AVB_IMP");
//		lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_IMP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_IMP);		
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_ACC = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_ACC.setAttrName("DATI_CONTABILI_AVB_ACC");
//		lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_ACC.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_ACC);		
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_LIQ = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_LIQ.setAttrName("DATI_CONTABILI_AVB_LIQ");
//		lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_LIQ.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_CONTABILI_AVB_LIQ);
		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanSEZ_NOTIFICA_MESSI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanSEZ_NOTIFICA_MESSI.setAttrName("SEZ_NOTIFICA_MESSI");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanSEZ_NOTIFICA_MESSI);		
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_MESSI_NOTIFICATORI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_MESSI_NOTIFICATORI.setAttrName("TASK_RESULT_2_FLG_MESSI_NOTIFICATORI");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_MESSI_NOTIFICATORI.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_MESSI_NOTIFICATORI);		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI.setAttrName("DESTINATARI_NOTIFICA_MESSI");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_DES = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_DES.setAttrName("DESTINATARI_NOTIFICA_MESSI_DES");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_DES.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_DES);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_EMAIL = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_EMAIL.setAttrName("DESTINATARI_NOTIFICA_MESSI_EMAIL");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_EMAIL.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_EMAIL);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_INDIRIZZO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_INDIRIZZO.setAttrName("DESTINATARI_NOTIFICA_MESSI_INDIRIZZO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_INDIRIZZO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_INDIRIZZO);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_ALTRI_DATI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_ALTRI_DATI.setAttrName("DESTINATARI_NOTIFICA_MESSI_ALTRI_DATI");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_ALTRI_DATI.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_ALTRI_DATI);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_NRO_NOTIFICA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_NRO_NOTIFICA.setAttrName("DESTINATARI_NOTIFICA_MESSI_NRO_NOTIFICA");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_MESSI_NRO_NOTIFICA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanSEZ_NOTIFICA_PEC = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanSEZ_NOTIFICA_PEC.setAttrName("SEZ_NOTIFICA_PEC");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanSEZ_NOTIFICA_PEC);		
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_NOTIFICA_PEC = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_NOTIFICA_PEC.setAttrName("TASK_RESULT_2_FLG_NOTIFICA_PEC");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_NOTIFICA_PEC.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_NOTIFICA_PEC);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC.setAttrName("DESTINATARI_NOTIFICA_PEC");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_DES = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_DES.setAttrName("DESTINATARI_NOTIFICA_PEC_DES");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_DES.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_DES);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_PEC = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_PEC.setAttrName("DESTINATARI_NOTIFICA_PEC_PEC");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_PEC.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_PEC);
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_NOTA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_NOTA.setAttrName("DESTINATARI_NOTIFICA_PEC_NOTA");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_NOTA.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_NOTIFICA_PEC_NOTA);
//				
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO.setAttrName("TASK_RESULT_2_SOTTOTIPO_ATTO_RADIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO.setAttrLabel("Sotto-tipo atto");		
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO.setValoriPossibili("sottotipo1|*|sottotipo2");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO.setValoreFisso("sottotipo1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_SOTTOTIPO_ATTO_RADIO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER.setAttrName("TASK_RESULT_2_TIPO_ITER");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER.setAttrLabel("Tipo iter");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER.setValoriPossibili("iter1|*|iter2");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER.setValoreFisso("iter2");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_TIPO_ITER);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCOD_PROCEDIMENTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCOD_PROCEDIMENTO.setAttrName("COD_PROCEDIMENTO");
//		lAttributiCustomCablatiAttoXmlBeanCOD_PROCEDIMENTO.setAttrLabel("Procedimento");
//		lAttributiCustomCablatiAttoXmlBeanCOD_PROCEDIMENTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanCOD_PROCEDIMENTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCOD_PROCEDIMENTO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCATEGORIA_RISCHIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCATEGORIA_RISCHIO.setAttrName("CATEGORIA_RISCHIO");
//		lAttributiCustomCablatiAttoXmlBeanCATEGORIA_RISCHIO.setAttrLabel("Categoria di rischio");
//		lAttributiCustomCablatiAttoXmlBeanCATEGORIA_RISCHIO.setAltriParametriLoadCombo("DICTIONARY_ENTRY|*|CATEGORIA_RISCHIO");
//		lAttributiCustomCablatiAttoXmlBeanCATEGORIA_RISCHIO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanCATEGORIA_RISCHIO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCATEGORIA_RISCHIO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_TRASP_AVB = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_TRASP_AVB.setAttrName("DATI_TRASP_AVB");
//		lAttributiCustomCablatiAttoXmlBeanDATI_TRASP_AVB.setAttrLabel("Trasparenza");
//		lAttributiCustomCablatiAttoXmlBeanDATI_TRASP_AVB.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_TRASP_AVB);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanBENEFICIARI_TRASPARENZA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanBENEFICIARI_TRASPARENZA.setAttrName("BENEFICIARI_TRASPARENZA");
//		lAttributiCustomCablatiAttoXmlBeanBENEFICIARI_TRASPARENZA.setAttrLabel("Beneficiari");
//		lAttributiCustomCablatiAttoXmlBeanBENEFICIARI_TRASPARENZA.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanBENEFICIARI_TRASPARENZA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP.setAttrName("TIPO_PERSONA_BENEF_TRASP");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP.setAttrLabel("");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP.setValoriPossibili("fisica|*|giuridica");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP.setDecodificheValoriPossibili("fisica|*|giuridica");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP.setValoreFisso("fisica");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTIPO_PERSONA_BENEF_TRASP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCOGNOME_BENEF_TRASP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCOGNOME_BENEF_TRASP.setAttrName("COGNOME_BENEF_TRASP");
//		lAttributiCustomCablatiAttoXmlBeanCOGNOME_BENEF_TRASP.setAttrLabel("Cogn.");
//		lAttributiCustomCablatiAttoXmlBeanCOGNOME_BENEF_TRASP.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanCOGNOME_BENEF_TRASP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCOGNOME_BENEF_TRASP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanNOME_BENEF_TRASP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanNOME_BENEF_TRASP.setAttrName("NOME_BENEF_TRASP");
//		lAttributiCustomCablatiAttoXmlBeanNOME_BENEF_TRASP.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanNOME_BENEF_TRASP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanNOME_BENEF_TRASP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanRAG_SOC_BENEF_TRASP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanRAG_SOC_BENEF_TRASP.setAttrName("RAG_SOC_BENEF_TRASP");
//		lAttributiCustomCablatiAttoXmlBeanRAG_SOC_BENEF_TRASP.setAttrLabel("Rag. sociale");
//		lAttributiCustomCablatiAttoXmlBeanRAG_SOC_BENEF_TRASP.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanRAG_SOC_BENEF_TRASP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanRAG_SOC_BENEF_TRASP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCF_PIVA_BENEF_TRASP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCF_PIVA_BENEF_TRASP.setAttrLabel("C.F./P.I.");
//		lAttributiCustomCablatiAttoXmlBeanCF_PIVA_BENEF_TRASP.setAttrName("CF_PIVA_BENEF_TRASP");
//		lAttributiCustomCablatiAttoXmlBeanCF_PIVA_BENEF_TRASP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCF_PIVA_BENEF_TRASP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanIMPORTO_BENEF_TRASP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanIMPORTO_BENEF_TRASP.setAttrName("IMPORTO_BENEF_TRASP");
//		lAttributiCustomCablatiAttoXmlBeanIMPORTO_BENEF_TRASP.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanIMPORTO_BENEF_TRASP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanIMPORTO_BENEF_TRASP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFLG_PRIVACY_BENEF_TRASP = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFLG_PRIVACY_BENEF_TRASP.setAttrName("FLG_PRIVACY_BENEF_TRASP");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PRIVACY_BENEF_TRASP.setAttrLabel("privacy");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PRIVACY_BENEF_TRASP.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFLG_PRIVACY_BENEF_TRASP);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setAttrName("DESCRIZIONE_ORD_MOBILITA");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setAttrLabel("Descrizione");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setAltezzaInRighe("10");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESCRIZIONE_ORD_MOBILITA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA.setAttrName("DATI_ESECUTIVITA");
//		lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA.setAttrLabel("Esecutività");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_ESECUTIVITA);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setAttrName("FLG_IMMEDIATAMENTE_ESEGUIBILE");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setAttrLabel("immediatamente eseguibile");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFLG_IMMEDIATAMENTE_ESEGUIBILE);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setAttrName("MOTIVI_IE");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setAttrLabel("Motivo/i");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE.setAltezzaInRighe("10");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanMOTIVI_IE);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA.setAttrName("LUOGO_ORD_MOBILITA");
//		lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA.setAttrLabel("Ubicazione/i");
//		lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA.setAltezzaInRighe("10");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanLUOGO_ORD_MOBILITA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setAttrName("ATTIVA_SEZIONE_DESTINATARI");
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setAttrLabel("attiva sezione destinatari");
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanATTIVA_SEZIONE_DESTINATARI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setAttrName("DESTINATARI_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setAttrLabel("Destinatari");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO.setAttrName("DESTINATARI_PC_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO.setAttrLabel("Destinatari P.C.");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_PC_ATTO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setAttrName("DESTINATARI_ATTO_PREFISSO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setAttrLabel("Prefisso");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_PREFISSO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setAttrName("DESTINATARI_ATTO_NOME");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setAttrLabel("Nome");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_NOME);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setAttrName("DESTINATARI_ATTO_INDIRIZZO");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setAttrLabel("Sede/indirizzo");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_INDIRIZZO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA.setAttrName("DESTINATARI_ATTO_CA");
//		lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA.setAttrLabel("C.A.");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDESTINATARI_ATTO_CA);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2.setAttrName("DATI_TESTO_2");
//		lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2.setAttrLabel("Testo atto 2");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_TESTO_2);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setAttrName("PREMESSA_ATTO_2");
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setAttrLabel("Premessa 2");
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPREMESSA_ATTO_2);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setAttrName("DISPOSITIVO_ATTO_2");
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setAttrLabel("Dispositivo 2");
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDISPOSITIVO_ATTO_2);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI.setAttrName("VISTI_DIR_SUPERIORI");
//		lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI.setAttrLabel("Visti Dir. Mattia Zanin");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanVISTI_DIR_SUPERIORI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setAttrName("TASK_RESULT_2_VISTO_DIR_SUP_1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setAttrLabel("visto dirigente di area");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_1);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setAttrName("TASK_RESULT_2_VISTO_DIR_SUP_2");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setAttrLabel("visto direttore di divisione");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_DIR_SUP_2);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setAttrName("TASK_RESULT_2_FONDI_PRU");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setAttrLabel("fondi PRU");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FONDI_PRU);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013 = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setAttrName("TASK_RESULT_2_VISTO_PAR_117_2013");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setAttrLabel("visto parere favorevole ai sensi ai sensi della DD 117/2013 ssmm");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_VISTO_PAR_117_2013);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setAttrName("TASK_RESULT_2_NOTIFICA_DA_MESSI");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setAttrLabel("notifica tramite messi");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_NOTIFICA_DA_MESSI);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanCDC_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setAttrName("CDC_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setAttrLabel("Centro di Costo");
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanCDC_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanCDC_ATTO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setAttrName("TASK_RESULT_2_DECRETO_REGGIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setAttrLabel("decreto REGGIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_DECRETO_REGGIO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setAttrName("TASK_RESULT_2_AVVOCATURA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setAttrLabel("AVVOCATURA");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA.setValoreFisso("true");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_AVVOCATURA);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO.setAttrName("DATI_PUBBL_NOTIZIARIO");
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO.setAttrLabel("Pubblicazione sul Notiziario");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_NOTIZIARIO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setAttrName("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setAttrLabel("Da pubblicare");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO.setValoreFisso("SI");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTASK_RESULT_2_FLG_PUBBL_NOTIZIARIO);
//
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setAttrName("ATTO_RIF_A_SISTEMA");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setAttrLabel("a sistema");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanATTO_RIF_A_SISTEMA);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setAttrName("ATTO_RIFERIMENTO");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setAttrLabel("Atti di riferimento");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setFlgEditabile("1");
//		lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO.setMaxNumValori("");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanATTO_RIFERIMENTO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setAttrName("IND_EMAIL_DEST_NOTIFICA_ATTO");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setAttrLabel("Dest. notifiche");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanIND_EMAIL_DEST_NOTIFICA_ATTO);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR.setAttrName("DATI_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR.setAttrLabel("Pubblicazione al B.U.");
//		lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR.setMaxNumValori("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanDATI_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setAttrName("FLG_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setAttrLabel("Pubblicazione al B.U.");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setValoriPossibili("SI|*|NO");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setValoreFisso("NO");
//		lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFLG_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setAttrName("ANNO_TERMINE_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setAttrLabel("Anno di termine pubblicazione");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setValoreFisso("2024");
//		lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanANNO_TERMINE_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setAttrName("TIPO_DECORRENZA_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setAttrLabel("Decorrenza pubblicazione");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setValoreFisso("posticipata");
//		lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanTIPO_DECORRENZA_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setAttrName("PUBBL_BUR_DAL");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setAttrLabel("a partire dal");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_DAL);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setAttrName("FLG_URGENTE_PUBBL_BUR");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setAttrLabel("pubblicazione urgente");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanFLG_URGENTE_PUBBL_BUR);
//		
//		AttributiCustomCablatiAttoXmlBean lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO = new AttributiCustomCablatiAttoXmlBean();
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setAttrName("PUBBL_BUR_ENTRO");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setAttrLabel("entro il");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setFlgObbligatorio("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setMaxNumValori("1");
//		lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO.setFlgEditabile("1");
//		bean.getParametriTipoAtto().getAttributiCustomCablati().add(lAttributiCustomCablatiAttoXmlBeanPUBBL_BUR_ENTRO);
	}

}
