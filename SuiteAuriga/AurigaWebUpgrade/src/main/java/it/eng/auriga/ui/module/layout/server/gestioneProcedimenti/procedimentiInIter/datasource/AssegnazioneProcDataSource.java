/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesSetsogginterniBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.AssegnazioneProcBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.IstruttoreProcBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.ProcedimentiInIterBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.SoggettoAssegnazioneProcBean;
import it.eng.client.DmpkProcessesSetsogginterni;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AssegnazioneProcDataSource")
public class AssegnazioneProcDataSource extends AbstractDataSource<AssegnazioneProcBean, AssegnazioneProcBean>{	

	@Override
	public AssegnazioneProcBean add(AssegnazioneProcBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ProcedimentiInIterBean proc : bean.getListaRecord()) {						
						
			DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			if(proc.getIdProcedimento()!=null) {
				input.setIdprocessin(new BigDecimal(proc.getIdProcedimento()));
			}
			
			input.setRuolosoggettiin("ISTRUTTORE");
				
			input.setListaxmlin(getXmlAssegnazione(bean));
					
			DmpkProcessesSetsogginterni dmpkProcessesSetsogginterni = new DmpkProcessesSetsogginterni();
			StoreResultBean<DmpkProcessesSetsogginterniBean> output = dmpkProcessesSetsogginterni.execute(getLocale(),loginBean, input);
					
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(proc.getIdProcedimento(), output.getDefaultMessage());
			}						
						
		}
		
		if(errorMessages != null) {
			bean.setErrorMessages(errorMessages);			
		}
		
		return bean;
	}
	
	public AssegnazioneProcBean presaInCarico(AssegnazioneProcBean bean) throws Exception {	
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// IdUserLavoro valorizzato = Utente delegante ( se si lavora in delega ).
		// IdUser valorizzato = Utente loggato( se non si lavora in delega )
		String idUser = loginBean.getIdUser() != null && !"".equals(loginBean.getIdUser()) ? loginBean.getIdUser().toString() : null;
		
		HashMap<String, String> errorMessages = null;
		
		for(ProcedimentiInIterBean proc : bean.getListaRecord()) {						
						
			DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			
			if(proc.getIdProcedimento()!=null) {
				input.setIdprocessin(new BigDecimal(proc.getIdProcedimento()));
			}
			
			input.setRuolosoggettiin("ISTRUTTORE");
				
			input.setListaxmlin(getXmlAssegnazionePresaInCarico(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? loginBean.getIdUserLavoro(): idUser));
					
			DmpkProcessesSetsogginterni dmpkProcessesSetsogginterni = new DmpkProcessesSetsogginterni();
			StoreResultBean<DmpkProcessesSetsogginterniBean> output = dmpkProcessesSetsogginterni.execute(getLocale(),loginBean, input);
					
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(proc.getIdProcedimento(), output.getDefaultMessage());
			}						
						
		}
		
		if(errorMessages != null) {
			bean.setErrorMessages(errorMessages);			
		}
		
		return bean;
		
	}
	
	public String getXmlAssegnazione(AssegnazioneProcBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaAssegnazione(bean));
	}
	
	public String getXmlAssegnazionePresaInCarico(String idUoSv) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaAssegnazionePresaInCarico(idUoSv));
	}
	
	private List<SoggettoAssegnazioneProcBean> getListaAssegnazione(AssegnazioneProcBean bean) throws Exception {
		List<SoggettoAssegnazioneProcBean> listaAssegnazione = new ArrayList<SoggettoAssegnazioneProcBean>();
		if(bean.getListaAssegnazione()!=null){
			for(IstruttoreProcBean assegnatarioBean : bean.getListaAssegnazione()){
				SoggettoAssegnazioneProcBean lSoggettoAssegnazioneProcBean = new SoggettoAssegnazioneProcBean();
				lSoggettoAssegnazioneProcBean.setFlgUoSv("UT");
				lSoggettoAssegnazioneProcBean.setIdUoSv(assegnatarioBean.getIdUtenteIstruttoreProc());
				
				listaAssegnazione.add(lSoggettoAssegnazioneProcBean);
			}
		}
		return listaAssegnazione;
	}
	
	private List<SoggettoAssegnazioneProcBean> getListaAssegnazionePresaInCarico(String idUoSv) throws Exception {
		
		List<SoggettoAssegnazioneProcBean> listaAssegnazione = new ArrayList<SoggettoAssegnazioneProcBean>();
		SoggettoAssegnazioneProcBean lSoggettoAssegnazioneProcBean = new SoggettoAssegnazioneProcBean();
		lSoggettoAssegnazioneProcBean.setFlgUoSv("UT");
		lSoggettoAssegnazioneProcBean.setIdUoSv(idUoSv);
		
		listaAssegnazione.add(lSoggettoAssegnazioneProcBean);
		
		return listaAssegnazione;
	}

	@Override
	public AssegnazioneProcBean get(AssegnazioneProcBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public AssegnazioneProcBean remove(AssegnazioneProcBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public AssegnazioneProcBean update(AssegnazioneProcBean bean,
			AssegnazioneProcBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<AssegnazioneProcBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AssegnazioneProcBean bean)
	throws Exception {
		
		return null;
	}

}