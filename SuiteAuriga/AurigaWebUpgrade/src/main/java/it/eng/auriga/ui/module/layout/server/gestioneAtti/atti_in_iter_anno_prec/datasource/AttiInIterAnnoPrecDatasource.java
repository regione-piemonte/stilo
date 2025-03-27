/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.gestioneAtti.atti_in_iter_anno_prec.datasource;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerAnnullaattiiniterannoprecadspBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerCtrlattiiniterannoprecadspBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.atti_in_iter_anno_prec.bean.AttiInIterAnnoPrecBean;
import it.eng.client.DmpkBmanagerAnnullaattiiniterannoprecadsp;
import it.eng.client.DmpkBmanagerCtrlattiiniterannoprecadsp;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author passalacqua
 *
 */
@Datasource(id="AttiInIterAnnoPrecDatasource")
public class AttiInIterAnnoPrecDatasource extends AurigaAbstractFetchDatasource<AttiInIterAnnoPrecBean>{
	
	@Override
	public PaginatorBean<AttiInIterAnnoPrecBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {		
		PaginatorBean<AttiInIterAnnoPrecBean> lPaginatorBean = new PaginatorBean<AttiInIterAnnoPrecBean>();
		return lPaginatorBean;
	}	
	
	@Override
	public AttiInIterAnnoPrecBean get(AttiInIterAnnoPrecBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
		
		DmpkBmanagerCtrlattiiniterannoprecadspBean input = new DmpkBmanagerCtrlattiiniterannoprecadspBean();
				
		DmpkBmanagerCtrlattiiniterannoprecadsp service = new DmpkBmanagerCtrlattiiniterannoprecadsp();
		StoreResultBean<DmpkBmanagerCtrlattiiniterannoprecadspBean> output = service.execute(getLocale(), lSchemaBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
		
		AttiInIterAnnoPrecBean result = new AttiInIterAnnoPrecBean();
		
		// Nro totale di proposte di decreti in iter dell'anno precedente
		result.setNroTotaleProposteDecreto(output.getResultBean().getNrodecretiout() != null ? output.getResultBean().getNrodecretiout() : null);
		
		// Nro di di proposte di decreti in iter dell'anno precedente che hanno dati contabili su CWOL
		result.setNroTotaleProposteDecretoConMovimentiContabili(output.getResultBean().getNrodecreticondaticwolout() != null ? output.getResultBean().getNrodecreticondaticwolout() : null);
		
		// Nro di di proposte di decreti in iter dell'anno precedente che si trovano nella fase istruttoria
		result.setNroProposteDecretoInFaseIstruttoria(output.getResultBean().getNrodecretifaseistrout() != null ? output.getResultBean().getNrodecretifaseistrout() : null);
		
		// Nro di di proposte di decreti in iter dell'anno precedente che si trovano nella fase di verifica del bilancio
		result.setNroProposteDecretoInVerificaBilancio(output.getResultBean().getNrodecretifasebilout() != null ? output.getResultBean().getNrodecretifasebilout() : null);
		
		// Nro di di proposte di decreti in iter dell'anno precedente che si trovano nella fase di perfezionamento
		result.setNroProposteDecretoInFasePerfezionamento(output.getResultBean().getNrodecretifaseperfout() != null ? output.getResultBean().getNrodecretifaseperfout() : null);
		
		// Nro totale di proposte di RdA in iter dell'anno precedente
		result.setNroProposteRda(output.getResultBean().getNrordaout() != null ? output.getResultBean().getNrordaout() : null);
		
		return result;		
	}
	
	@Override
	public AttiInIterAnnoPrecBean update(AttiInIterAnnoPrecBean bean, AttiInIterAnnoPrecBean oldvalue) throws Exception {
		
		String listaErrori = "";
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
		
		DmpkBmanagerAnnullaattiiniterannoprecadspBean input = new DmpkBmanagerAnnullaattiiniterannoprecadspBean();
		
		// Se il check flgProposteRdaDaAnnullare è settato allora lancio la store 
		if (bean.getFlgProposteRdaDaAnnullare() !=null && bean.getFlgProposteRdaDaAnnullare()) {
			input.setTipoattoin("RDA");
			input.setFasein("");
			input.setRilcontabilein("");
			
			DmpkBmanagerAnnullaattiiniterannoprecadsp store = new DmpkBmanagerAnnullaattiiniterannoprecadsp();
	        StoreResultBean<DmpkBmanagerAnnullaattiiniterannoprecadspBean> output = store.execute(getLocale(), lSchemaBean, input);
	        
	        if(output.isInError()) {
	        	if(StringUtils.isNotBlank(output.getDefaultMessage())) {
	        		listaErrori = listaErrori + output.getDefaultMessage() + "<br><br>";
	        	}
	        }
		}
		
		// Se il check "NO", delle proposte in FASE ISTRUTTORIA è settato allora chiamo la store
		if (bean.getFlgAnnullaConRilevContabFaseIstrutNoValue() !=null && bean.getFlgAnnullaConRilevContabFaseIstrutNoValue())  {
			input.setTipoattoin("DCR");
			input.setFasein("FASE ISTRUTTORIA");
			input.setRilcontabilein("NO");
				
			DmpkBmanagerAnnullaattiiniterannoprecadsp store = new DmpkBmanagerAnnullaattiiniterannoprecadsp();
	        StoreResultBean<DmpkBmanagerAnnullaattiiniterannoprecadspBean> output = store.execute(getLocale(), lSchemaBean, input);
	        
	        if(output.isInError()) {
	        	if(StringUtils.isNotBlank(output.getDefaultMessage())) {
	        		listaErrori = listaErrori + output.getDefaultMessage() + "<br><br>";
	        	}
	        }
		}

		// Se il check "SI", delle proposte in FASE ISTRUTTORIA è settato allora chiamo la store
		if (bean.getFlgAnnullaConRilevContabFaseIstrutSiValue() !=null && bean.getFlgAnnullaConRilevContabFaseIstrutSiValue())  {
			input.setTipoattoin("DCR");
			input.setFasein("FASE ISTRUTTORIA");
			input.setRilcontabilein("SI");
				
			DmpkBmanagerAnnullaattiiniterannoprecadsp store = new DmpkBmanagerAnnullaattiiniterannoprecadsp();
	        StoreResultBean<DmpkBmanagerAnnullaattiiniterannoprecadspBean> output = store.execute(getLocale(), lSchemaBean, input);
	        
	        if(output.isInError()) {
	        	if(StringUtils.isNotBlank(output.getDefaultMessage())) {
	        		listaErrori = listaErrori + output.getDefaultMessage() + "<br><br>";
	        	}
	        }
		}
		
		// Se il check "SI, ma senza movimenti contabili" delle proposte in FASE ISTRUTTORIA è settato allora chiamo la store 
		if ( bean.getFlgAnnullaConRilevContabFaseIstrutSiSenzaMovContab() !=null && bean.getFlgAnnullaConRilevContabFaseIstrutSiSenzaMovContab()){
			input.setTipoattoin("DCR");
			input.setFasein("FASE ISTRUTTORIA");
			input.setRilcontabilein("SI, ma senza movimenti contabili");
			
			DmpkBmanagerAnnullaattiiniterannoprecadsp store = new DmpkBmanagerAnnullaattiiniterannoprecadsp();
	        StoreResultBean<DmpkBmanagerAnnullaattiiniterannoprecadspBean> output = store.execute(getLocale(), lSchemaBean, input);
	        
	        if(output.isInError()) {
	        	if(StringUtils.isNotBlank(output.getDefaultMessage())) {
	        		listaErrori = listaErrori + output.getDefaultMessage() + "<br><br>";
	        	}
	        }
		}
		
		// Se il check "NO", delle proposte in FASE PERFEZIONAMENTO è settato allora chiamo la store 
		if (bean.getFlgAnnullaConRilevContabFasePerfezNoValue() !=null && bean.getFlgAnnullaConRilevContabFasePerfezNoValue()){
			 
			input.setTipoattoin("DCR");
			input.setFasein("FASE PERFEZIONAMENTO");
			input.setRilcontabilein("NO");
			
			DmpkBmanagerAnnullaattiiniterannoprecadsp store = new DmpkBmanagerAnnullaattiiniterannoprecadsp();
	        StoreResultBean<DmpkBmanagerAnnullaattiiniterannoprecadspBean> output = store.execute(getLocale(), lSchemaBean, input);
	        
	        if(output.isInError()) {
	        	if(StringUtils.isNotBlank(output.getDefaultMessage())) {
	        		listaErrori = listaErrori + output.getDefaultMessage() + "<br><br>";
	        	}
	        }
		}
		
		// Se il check "SI" delle proposte in FASE PERFEZIONAMENTO è settato allora chiamo la store 
		if (bean.getFlgAnnullaConRilevContabFasePerfezSiValue() !=null && bean.getFlgAnnullaConRilevContabFasePerfezSiValue()) {
			input.setTipoattoin("DCR");
			input.setFasein("FASE PERFEZIONAMENTO");
			input.setRilcontabilein("SI");
			
			DmpkBmanagerAnnullaattiiniterannoprecadsp store = new DmpkBmanagerAnnullaattiiniterannoprecadsp();
	        StoreResultBean<DmpkBmanagerAnnullaattiiniterannoprecadspBean> output = store.execute(getLocale(), lSchemaBean, input);
	        
	        if(output.isInError()) {
	        	if(StringUtils.isNotBlank(output.getDefaultMessage())) {
	        		listaErrori = listaErrori + output.getDefaultMessage() + "<br><br>";
	        	}
	        }
		}
				
		// Se il check "SI, ma senza movimenti contabili" delle proposte in FASE PERFEZIONAMENTO è settato allora chiamo la store 
		if (bean.getFlgAnnullaConRilevContabFasePerfezSiSenzaMovContab() !=null && bean.getFlgAnnullaConRilevContabFasePerfezSiSenzaMovContab()){
			input.setTipoattoin("DCR");
			input.setFasein("FASE PERFEZIONAMENTO");
			input.setRilcontabilein("SI, ma senza movimenti contabili");
			
			DmpkBmanagerAnnullaattiiniterannoprecadsp store = new DmpkBmanagerAnnullaattiiniterannoprecadsp();
	        StoreResultBean<DmpkBmanagerAnnullaattiiniterannoprecadspBean> output = store.execute(getLocale(), lSchemaBean, input);
	        
	        if(output.isInError()) {
	        	if(StringUtils.isNotBlank(output.getDefaultMessage())) {
	        		listaErrori = listaErrori + output.getDefaultMessage() + "<br>";
	        	}
	        }
		}
		
		// Se ci sono errori 
		if(StringUtils.isNotBlank(listaErrori)) {
			listaErrori =  "<html><div>" + listaErrori +"</div></html>";
			throw new StoreException(listaErrori);
		}
		
		return bean;		
	}

	@Override
	public AttiInIterAnnoPrecBean add(AttiInIterAnnoPrecBean bean) throws Exception {
		return bean;
	}
		
	@Override
	public AttiInIterAnnoPrecBean remove(AttiInIterAnnoPrecBean bean) throws Exception {
		return null;
	}	
	
	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean filterBean) throws Exception {
		NroRecordTotBean retValue = new NroRecordTotBean();
		return retValue;
	}
	
	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		return null;
	}
}
