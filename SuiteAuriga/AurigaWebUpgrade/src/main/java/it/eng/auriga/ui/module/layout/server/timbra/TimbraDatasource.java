/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrospecxtipoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.AddToRecentDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AddToRecentBean;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.client.DmpkRegistrazionedocGettimbrospecxtipo;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "TimbraDatasource")
public class TimbraDatasource extends AbstractServiceDataSource<OpzioniTimbroBean, TimbraResultBean> {

	@Override
	public TimbraResultBean call(OpzioniTimbroBean bean) throws Exception {
		
		TimbraUtility timbraUtility = new TimbraUtility();
		TimbraResultBean lTimbraResultBean = timbraUtility.timbra(bean, getSession(), getLocale());
		// Verifico se la timbratura è andata a buon fine
		if (!lTimbraResultBean.isResult()) {
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		} else {
			if(bean!=null && StringUtils.isNotBlank(bean.getFinalita()) && "COPIA_CONFORME_CUSTOM".equalsIgnoreCase(bean.getFinalita())){
				lTimbraResultBean.setNomeFile(bean.getNomeFile());	
			}else {
				lTimbraResultBean.setNomeFile(FilenameUtils.getBaseName(getNomeFileSbustato(bean.getNomeFile())) + "_timbrato.pdf");	
			}
					
			if(StringUtils.isNotBlank(bean.getIdUd()) && StringUtils.isNotBlank(bean.getIdDoc())) {
			
				AddToRecentBean lAddToRecentBeanIn = new AddToRecentBean();
				lAddToRecentBeanIn.setIdUd(bean.getIdUd());
				lAddToRecentBeanIn.setIdDoc(bean.getIdDoc());
				
				AddToRecentDataSource lDataSource = new AddToRecentDataSource();
				try {			
					lDataSource.setSession(getSession());
					lDataSource.add(lAddToRecentBeanIn);
				}
				catch (Exception e) {
					String errorMessage = "Si è verificato un errore durante la timbratura del file";
					if (StringUtils.isNotBlank(e.getMessage())) {
						errorMessage += ": " + e.getMessage();
					}
					throw new Exception(errorMessage);
				}
			}
		}
			
		return lTimbraResultBean;
	}
	
	public OpzioniTimbroBean getTipologia(OpzioniTimbroBean bean) throws Exception{
		
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		
		if (StringUtils.isNotBlank(bean.getIdDoc())) {
			
			lOpzioniTimbroBean.setNomeFile(bean.getNomeFile());
			lOpzioniTimbroBean.setUri(bean.getUri());
			lOpzioniTimbroBean.setRemote(bean.isRemote());
			lOpzioniTimbroBean.setMimetype(bean.getMimetype());
			lOpzioniTimbroBean.setIdUd(bean.getIdUd());
			lOpzioniTimbroBean.setIdDoc(bean.getIdDoc());
			
			DmpkRegistrazionedocGettimbrospecxtipo store = new DmpkRegistrazionedocGettimbrospecxtipo();
			DmpkRegistrazionedocGettimbrospecxtipoBean input = new DmpkRegistrazionedocGettimbrospecxtipoBean();

			input.setIddocfolderin(new BigDecimal(bean.getIdDoc()));
			input.setFlgdocfolderin("D");

			StoreResultBean<DmpkRegistrazionedocGettimbrospecxtipoBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

			if (result.isInError()) {
				throw new StoreException(result);
			}
			
			lOpzioniTimbroBean.setTesto(result.getResultBean().getContenutobarcodeout());
			lOpzioniTimbroBean.setTestoIntestazione(result.getResultBean().getTestoinchiaroout());
		}

		String posTimbroConfig = "";
		String rotaTimbroConfig = "";
		String paginaTimbroConfig = "";
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");
		if(lOpzioniTimbroAttachEmail != null){
			 posTimbroConfig = lOpzioniTimbroAttachEmail.getPosizioneTimbro() != null ? lOpzioniTimbroAttachEmail.getPosizioneTimbro().value() : null;
			 rotaTimbroConfig = lOpzioniTimbroAttachEmail.getRotazioneTimbro() != null ? lOpzioniTimbroAttachEmail.getRotazioneTimbro().value() : null;
			 paginaTimbroConfig = lOpzioniTimbroAttachEmail.getPaginaTimbro()!= null && lOpzioniTimbroAttachEmail.getPaginaTimbro().getTipoPagina()!= null ? lOpzioniTimbroAttachEmail.getPaginaTimbro().getTipoPagina().value() : null;
		}
			
		String posTimbroPref = bean.getPosizioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getPosizioneTimbroPref()) ?
				bean.getPosizioneTimbroPref() : null;
		String rotaTimbroPref = bean.getRotazioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ?
				bean.getRotazioneTimbroPref() : null;
		String tipoPaginaPref = bean.getTipoPagina() != null && !"".equalsIgnoreCase(bean.getTipoPagina()) ? 
				bean.getTipoPagina() : null;
									
		lOpzioniTimbroBean.setPosizioneTimbroPref(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
		lOpzioniTimbroBean.setRotazioneTimbroPref(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);
		lOpzioniTimbroBean.setTipoPagina(tipoPaginaPref != null ? tipoPaginaPref : paginaTimbroConfig);
		
		return lOpzioniTimbroBean;
	}
	
	public OpzioniTimbroBean getSegnatura(OpzioniTimbroBean bean) throws Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
			
		lOpzioniTimbroBean.setNomeFile(bean.getNomeFile());
		lOpzioniTimbroBean.setUri(bean.getUri());
		lOpzioniTimbroBean.setRemote(bean.isRemote());
		lOpzioniTimbroBean.setMimetype(bean.getMimetype());
		lOpzioniTimbroBean.setIdUd(bean.getIdUd());
		lOpzioniTimbroBean.setIdDoc(bean.getIdDoc());
		lOpzioniTimbroBean.setNroProgAllegato(bean.getNroProgAllegato());
		lOpzioniTimbroBean.setFinalita(bean.getFinalita());
		lOpzioniTimbroBean.setFlgVersionePubblicabile(bean.getFlgVersionePubblicabile());
		
		DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
		DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
		input.setIdudio(new BigDecimal(bean.getIdUd()));
		input.setFinalitain(bean.getFinalita());
		input.setIddocin(bean.getIdDoc() != null && !"".equals(bean.getIdDoc()) ? new BigDecimal(bean.getIdDoc()) : null);
		input.setNroallegatoin(bean.getNroProgAllegato());

		StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

		if (result.isInError()) {
			throw new StoreException(result);
		}
		lOpzioniTimbroBean.setTesto(result.getResultBean().getContenutobarcodeout());
		if(input.getNroallegatoin()!=null && isClienteArpaLazio(getSession())) {
			lOpzioniTimbroBean.setTestoIntestazione("Allegato n.".concat(result.getResultBean().getTestoinchiaroout()));
		} else {
			lOpzioniTimbroBean.setTestoIntestazione(result.getResultBean().getTestoinchiaroout());
		}
		
		OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");
		
		lOpzioniTimbroBean.setPosizioneIntestazione(lOpzioniTimbroAttachEmail.getPosizioneIntestazione() != null
				? lOpzioniTimbroAttachEmail.getPosizioneIntestazione().value()
				: null);
		lOpzioniTimbroBean.setPosizioneTestoInChiaro(lOpzioniTimbroAttachEmail.getPosizioneTestoInChiaro() != null
				? lOpzioniTimbroAttachEmail.getPosizioneTestoInChiaro().value()
				: null);
		
		//Se la scelte delle opzioni timbro e' stata saltata
		if ("true".equalsIgnoreCase(bean.getSkipScelteOpzioniCopertina())) {
			
			lOpzioniTimbroBean.setSkipScelteOpzioniCopertina("true");
			
			if("COPIA_CONFORME_CUSTOM".equalsIgnoreCase(bean.getFinalita())) {
				OpzioniTimbroAttachEmail opzioniConformitaCustom = null;
				
//				String xmlOpzioniTimbro = ParametriDBUtil.getParametroDB(getSession(), "CONF_COPIA_CONFORME_CUSTOM");
//				if(StringUtils.isNotBlank(xmlOpzioniTimbro)) {
//					lOpzioniCopertinaTimbroBean = new XmlUtilityDeserializer().unbindXml(xmlOpzioniTimbro, OpzioniCopertinaTimbroBean.class);
//				}
				
				opzioniConformitaCustom = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniConformitaCustom");
				
				
				//Se non ho trovato le configurazioni del timbrp nel parametro DB prendo quelle di default dalle config spring
				if(opzioniConformitaCustom == null) {
					opzioniConformitaCustom = (OpzioniTimbroAttachEmail) SpringAppContext
							.getContext().getBean("OpzioniTimbroAttachEmail");
				}
				
				lOpzioniTimbroBean.setPosizioneTimbroPref(opzioniConformitaCustom.getPosizioneTimbro().value());
				lOpzioniTimbroBean.setRotazioneTimbroPref(opzioniConformitaCustom.getRotazioneTimbro().value());
				lOpzioniTimbroBean.setTipoPagina(opzioniConformitaCustom.getPaginaTimbro().getTipoPagina().value());
				
			}else {								
				String posTimbroConfig = "";
				String rotaTimbroConfig = "";
				String paginaTimbroConfig = "";
				OpzioniCopertinaTimbroBean lOpzioniCopertinaTimbroBean = (OpzioniCopertinaTimbroBean) SpringAppContext
						.getContext().getBean("OpzioniCopertinaTimbroBean");
				if (lOpzioniCopertinaTimbroBean != null) {
					posTimbroConfig = lOpzioniCopertinaTimbroBean.getPosizioneTimbro() != null
							? lOpzioniCopertinaTimbroBean.getPosizioneTimbro().value()
							: null;
					rotaTimbroConfig = lOpzioniCopertinaTimbroBean.getRotazioneTimbro() != null
							? lOpzioniCopertinaTimbroBean.getRotazioneTimbro().value()
							: null;
					paginaTimbroConfig = lOpzioniCopertinaTimbroBean.getPaginaTimbro() != null
							&& lOpzioniCopertinaTimbroBean.getPaginaTimbro().getTipoPagina() != null
							? lOpzioniCopertinaTimbroBean.getPaginaTimbro().getTipoPagina().value()
							: null;
				}		
	
				String posTimbroPref = bean.getPosizioneTimbroPref() != null
						&& !"".equalsIgnoreCase(bean.getPosizioneTimbroPref()) ? bean.getPosizioneTimbroPref() : null;
				String rotaTimbroPref = bean.getRotazioneTimbroPref() != null
						&& !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ? bean.getRotazioneTimbroPref() : null;
				String tipoPaginaPref = bean.getTipoPagina() != null
								&& !"".equalsIgnoreCase(bean.getTipoPagina()) ? bean.getTipoPagina() : null;
	
				lOpzioniTimbroBean.setPosizioneTimbroPref(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
				lOpzioniTimbroBean.setRotazioneTimbroPref(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);
				lOpzioniTimbroBean.setTipoPagina(tipoPaginaPref != null ? tipoPaginaPref : paginaTimbroConfig);
			}
		} else {
			//Se le opzioni timbro sono state scelte dalla popup (TimbraWindow)
			lOpzioniTimbroBean.setSkipScelteOpzioniCopertina("false");
			
			lOpzioniTimbroBean.setPosizioneTimbro(bean.getPosizioneTimbro() != null ? bean.getPosizioneTimbro() : null );
			lOpzioniTimbroBean.setRotazioneTimbro(bean.getRotazioneTimbro() != null ? bean.getRotazioneTimbro() : null );
			lOpzioniTimbroBean.setTipoPagina(bean.getTipoPagina() != null ? bean.getTipoPagina() : null );
			
			if(bean.getTipoPagina() != null) {
				lOpzioniTimbroBean.setTipoPagina(bean.getTipoPagina());
				if("intervallo".equalsIgnoreCase(bean.getTipoPagina())){
					lOpzioniTimbroBean.setPaginaDa(bean.getPaginaDa() != null && !"".equalsIgnoreCase(bean.getPaginaDa())
							? bean.getPaginaDa() : "1");
					lOpzioniTimbroBean.setPaginaA(bean.getPaginaA() != null && !"".equalsIgnoreCase(bean.getPaginaA())
							? bean.getPaginaA() : "1");
				} 
			} else {
				lOpzioniTimbroBean.setTipoPagina(null);
			}
		}

		return lOpzioniTimbroBean;
	}
	
	public boolean isClienteArpaLazio(HttpSession session) {
		return ParametriDBUtil.getParametroDB(session, "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(session, "CLIENTE").equalsIgnoreCase("ARPA_LAZ");
	}
	
	public static String getNomeFileSbustato(String nomeFile) { 
		String ext = ".p7m.m7m.std";
		String nomeFileSbustato = nomeFile;
		while (nomeFileSbustato.length() > 3 && ext.contains(nomeFileSbustato.substring(nomeFileSbustato.length() - 4))){
				nomeFileSbustato = nomeFileSbustato.substring(0, nomeFileSbustato.length() - 4);
				}
		return nomeFileSbustato;
	}
}