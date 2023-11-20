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
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.CondivisioneAttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.SoggettoSmistamentoAttiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;
import it.eng.client.DmpkProcessesSetsogginterni;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "CondivisioneAttiDataSource")
public class CondivisioneAttiDataSource extends AbstractDataSource<CondivisioneAttiBean, CondivisioneAttiBean>{	

	@Override
	public CondivisioneAttiBean add(CondivisioneAttiBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(AttiBean atto : bean.getListaRecord()) {		
			
			String errorMessage = "";
			
			DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdprocessin(atto.getIdProcedimento() != null ? new BigDecimal(atto.getIdProcedimento()) : null);
			input.setRuolosoggettiin("DEST_CONDIVISIONE_ATTO");
			if(bean.getListaRecord().size() > 1) {
				// se è massiva passo il flgAppendRecIn a 1 perchè saranno solo in aggiunta
				input.setFlgappendrecin(new Integer("1"));
			}
			input.setNroposfirstrecin(null);
			input.setListaxmlin(getXmlListaCondivisione(bean));
				
			DmpkProcessesSetsogginterni dmpkProcessesSetsogginterni = new DmpkProcessesSetsogginterni();
			StoreResultBean<DmpkProcessesSetsogginterniBean> output = dmpkProcessesSetsogginterni.execute(getLocale(),loginBean, input);
				
			if(output.isInError()) {
				errorMessage += "Condivisione atto con ruolo DEST_CONDIVISIONE_ATTO fallito";
				if(StringUtils.isNotBlank(output.getDefaultMessage())) {
					errorMessage += ": " + output.getDefaultMessage();
				} 
				errorMessage += ".";
			}				
			
			if(StringUtils.isNotBlank(errorMessage)) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(atto.getIdProcedimento(), errorMessage);
			}					
		}
		
		if(errorMessages != null) {
			bean.setErrorMessages(errorMessages);			
		}
		
		return bean;
	}
	
	public String getXmlListaCondivisione(CondivisioneAttiBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<SoggettoSmistamentoAttiBean> listaCondivisione = new ArrayList<SoggettoSmistamentoAttiBean>();
		listaCondivisione.addAll(getListaCondivisione(bean));
		return lXmlUtilitySerializer.bindXmlList(listaCondivisione);
	}
	
	private List<SoggettoSmistamentoAttiBean> getListaCondivisione(CondivisioneAttiBean bean) throws Exception {
		List<SoggettoSmistamentoAttiBean> listaCondivisione = new ArrayList<SoggettoSmistamentoAttiBean>();
		if(bean.getListaCondivisione() != null){
			for(DestInvioCCBean condivisioneBean : bean.getListaCondivisione()) {
				if(condivisioneBean != null) {					
					if(condivisioneBean.getTipo() != null && "LD".equals(condivisioneBean.getTipo())) {
						if(StringUtils.isNotBlank(condivisioneBean.getGruppo())) {
							SoggettoSmistamentoAttiBean lSoggettoSmistamentoAttiBean = new SoggettoSmistamentoAttiBean();
							lSoggettoSmistamentoAttiBean.setFlgUoSv("G");	
							lSoggettoSmistamentoAttiBean.setIdUoSv(condivisioneBean.getGruppo());
							listaCondivisione.add(lSoggettoSmistamentoAttiBean);
						}
					} else if(StringUtils.isNotBlank(condivisioneBean.getIdUo())) {
						SoggettoSmistamentoAttiBean lSoggettoSmistamentoAttiBean = new SoggettoSmistamentoAttiBean();
						lSoggettoSmistamentoAttiBean.setFlgUoSv(condivisioneBean.getTypeNodo());		
						lSoggettoSmistamentoAttiBean.setIdUoSv(condivisioneBean.getIdUo());
						listaCondivisione.add(lSoggettoSmistamentoAttiBean);					
					} 
				}
			}
		}
		return listaCondivisione;
	}

	@Override 
	public CondivisioneAttiBean get(CondivisioneAttiBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public CondivisioneAttiBean remove(CondivisioneAttiBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public CondivisioneAttiBean update(CondivisioneAttiBean bean,
			CondivisioneAttiBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<CondivisioneAttiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(CondivisioneAttiBean bean)
	throws Exception {
		
		return null;
	}

}
