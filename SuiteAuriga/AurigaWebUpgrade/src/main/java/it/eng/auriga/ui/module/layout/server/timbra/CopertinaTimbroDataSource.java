/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetbarcodedacapofilapraticaBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrospecxtipoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkRegistrazionedocGetbarcodedacapofilapratica;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.client.DmpkRegistrazionedocGettimbrospecxtipo;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="CopertinaTimbroDataSource")
public class CopertinaTimbroDataSource extends AbstractServiceDataSource<OpzioniCopertinaBean, CopertinaResultBean> {
	
	private static final Logger log = Logger.getLogger(CopertinaTimbroDataSource.class);
	
	@Override
	public CopertinaResultBean call(OpzioniCopertinaBean bean) throws Exception {
		
		Boolean isMultiplo = getExtraparams() != null && getExtraparams().get("isMultiplo") != null && "true".equals(getExtraparams().get("isMultiplo")) ?
				true : false;
		
		String provenienza = getExtraparams() != null && getExtraparams().get("provenienza") != null && "A".equals(getExtraparams().get("provenienza")) ?
				"A" : "";
		
		String tipologia = getExtraparams() != null && getExtraparams().get("tipologia") != null && "T".equals(getExtraparams().get("tipologia")) ?
				"T" : "";
		
		String posizionale = getExtraparams() != null && getExtraparams().get("posizionale") != null && "P".equals(getExtraparams().get("posizionale")) ?
				"P" : "";

		bean.setIsMultiplo(isMultiplo);
		bean.setProvenienza(provenienza);
		bean.setTipoTimbroCopertina(tipologia);
		bean.setPosizionale(posizionale);

		TimbraCopertinaUtility lCopertinaUtility = new TimbraCopertinaUtility();
		return lCopertinaUtility.apponiCopertina(bean, getSession(), getLocale());
	}
	
	public OpzioniCopertinaBean getDatiSegnatura(OpzioniCopertinaBean bean) throws Exception {
		
		OpzioniCopertinaBean lOpzioniCopertinaBean = new OpzioniCopertinaBean();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		if(bean.getBarcodePraticaPregressa() != null && "true".equals(bean.getBarcodePraticaPregressa())){
			
			lOpzioniCopertinaBean.setIdUd(bean.getIdFolder());
			lOpzioniCopertinaBean.setNumeroAllegato(bean.getNumeroAllegato());
			
			//PRATICA PREGRESSA
			DmpkRegistrazionedocGetbarcodedacapofilapraticaBean input = new DmpkRegistrazionedocGetbarcodedacapofilapraticaBean();
			input.setIdfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
			input.setSezionepraticain(bean.getSezionePratica());
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean
					.getIdUserLavoro()) : null);
			input.setNroposizionein(bean.getNumeroAllegato() != null && !"".equals(bean.getNumeroAllegato()) ? new Integer(bean.getNumeroAllegato()) : null);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			DmpkRegistrazionedocGetbarcodedacapofilapratica lDmpkRegistrazionedocGetbarcodedacapofilapratica = new
					DmpkRegistrazionedocGetbarcodedacapofilapratica();
		
			StoreResultBean<DmpkRegistrazionedocGetbarcodedacapofilapraticaBean> output = lDmpkRegistrazionedocGetbarcodedacapofilapratica.execute(getLocale(), loginBean, input);
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
			}
		
			lOpzioniCopertinaBean.setTesto(output.getResultBean().getContenutobarcodeout());
			lOpzioniCopertinaBean.setTestoIntestazione(output.getResultBean().getTestoinchiaroout());
			
		} else {
						
			lOpzioniCopertinaBean.setIdUd(bean.getIdUd());
			lOpzioniCopertinaBean.setNumeroAllegato(bean.getNumeroAllegato());
			
			DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
			input.setIdudio(bean.getIdUd() != null && !"".equals(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean
					.getIdUserLavoro()) : null);
			input.setNroallegatoin(bean.getNumeroAllegato() != null && !"".equals(bean.getNumeroAllegato()) ? new Integer(bean.getNumeroAllegato()) : null);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			DmpkRegistrazionedocGettimbrodigreg lDmpkRegistrazionedocGettimbrodigreg = new
					DmpkRegistrazionedocGettimbrodigreg();
		
			StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> output = lDmpkRegistrazionedocGettimbrodigreg.execute(getLocale(), loginBean, input);
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				}
			}
		
			lOpzioniCopertinaBean.setTesto(output.getResultBean().getContenutobarcodeout());
			lOpzioniCopertinaBean.setTestoIntestazione(output.getResultBean().getTestoinchiaroout());	
			
		}
		
		String posTimbroConfig = "";
		String rotaTimbroConfig = "";
		OpzioniCopertinaTimbroBean lOpzioniCopertinaTimbroBean = (OpzioniCopertinaTimbroBean) SpringAppContext.getContext().getBean("OpzioniCopertinaTimbroBean");
		if(lOpzioniCopertinaTimbroBean != null){
			 posTimbroConfig = lOpzioniCopertinaTimbroBean.getPosizioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getPosizioneTimbro().value() : null;
			 rotaTimbroConfig = lOpzioniCopertinaTimbroBean.getRotazioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getRotazioneTimbro().value() : null;
		}
			
		String posTimbroPref = bean.getPosizioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getPosizioneTimbroPref()) ?
				bean.getPosizioneTimbroPref() : null;
		String rotaTimbroPref = bean.getRotazioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ?
				bean.getRotazioneTimbroPref() : null;
									
		lOpzioniCopertinaBean.setPosizioneTimbroPref(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
		lOpzioniCopertinaBean.setRotazioneTimbroPref(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);
		
		return lOpzioniCopertinaBean;
	}
	
	public OpzioniCopertinaBean getDatiTipologia(OpzioniCopertinaBean bean) throws Exception{
		
		OpzioniCopertinaBean lOpzioniCopertinaBean = new OpzioniCopertinaBean();
			
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
			
		DmpkRegistrazionedocGettimbrospecxtipoBean input = new DmpkRegistrazionedocGettimbrospecxtipoBean();
		
		if(StringUtils.isNotBlank(bean.getIdDoc())) {
			input.setIddocfolderin(bean.getIdDoc() != null && !"".equals(bean.getIdDoc()) ? new BigDecimal(bean.getIdDoc()) : null);
			input.setFlgdocfolderin("D");
			lOpzioniCopertinaBean.setIdDoc(bean.getIdDoc());
		} else {
			input.setIddocfolderin(bean.getIdFolder() != null && !"".equals(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null);
			input.setFlgdocfolderin("F");
			lOpzioniCopertinaBean.setIdDoc(bean.getIdFolder());
		}
		
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
		DmpkRegistrazionedocGettimbrospecxtipo lDmpkRegistrazionedocGettimbrospecxtipo = new DmpkRegistrazionedocGettimbrospecxtipo();
			
		StoreResultBean<DmpkRegistrazionedocGettimbrospecxtipoBean> output = lDmpkRegistrazionedocGettimbrospecxtipo.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			}
		}
			
		lOpzioniCopertinaBean.setTesto(output.getResultBean().getContenutobarcodeout());
		lOpzioniCopertinaBean.setTestoIntestazione(output.getResultBean().getTestoinchiaroout());
		
		
		String posTimbroConfig = "";
		String rotaTimbroConfig = "";
		OpzioniCopertinaTimbroBean lOpzioniCopertinaTimbroBean = (OpzioniCopertinaTimbroBean) SpringAppContext.getContext().getBean("OpzioniCopertinaTimbroBean");
		if(lOpzioniCopertinaTimbroBean != null){
			 posTimbroConfig = lOpzioniCopertinaTimbroBean.getPosizioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getPosizioneTimbro().value() : null;
			 rotaTimbroConfig = lOpzioniCopertinaTimbroBean.getRotazioneTimbro() != null ? lOpzioniCopertinaTimbroBean.getRotazioneTimbro().value() : null;
		}
			
		String posTimbroPref = bean.getPosizioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getPosizioneTimbroPref()) ?
				bean.getPosizioneTimbroPref() : null;
		String rotaTimbroPref = bean.getRotazioneTimbroPref() != null && !"".equalsIgnoreCase(bean.getRotazioneTimbroPref()) ?
				bean.getRotazioneTimbroPref() : null;
									
		lOpzioniCopertinaBean.setPosizioneTimbroPref(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
		lOpzioniCopertinaBean.setRotazioneTimbroPref(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);
		
		return lOpzioniCopertinaBean;
	}

}