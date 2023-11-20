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
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.SelezionaRicercatoreVisureBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.SoggettoAssegnazioneVisureBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.VisureBean;
import it.eng.client.DmpkProcessesSetsogginterni;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="SelezionaRicercatoreVisureDataSource")
public class SelezionaRicercatoreVisureDataSource extends AbstractDataSource<SelezionaRicercatoreVisureBean, SelezionaRicercatoreVisureBean> {

	@Override
	public SelezionaRicercatoreVisureBean add(SelezionaRicercatoreVisureBean bean)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = null;
		
		if(StringUtils.isNotBlank(bean.getIdUtenteRicercatore())) {
			
			for (VisureBean visura : bean.getListaRecord()) { 
			
				DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
				if (visura.getIdProcedimento() != null) {
					input.setIdprocessin(new BigDecimal(visura.getIdProcedimento()));
				}	
				input.setRuolosoggettiin("RICERCATORE INCARICATO");
				input.setFlgappendrecin(null);
												
				List<SoggettoAssegnazioneVisureBean> listaAssegnazione = new ArrayList<SoggettoAssegnazioneVisureBean>();
				SoggettoAssegnazioneVisureBean lSoggettoAssegnazioneVisureBean = new SoggettoAssegnazioneVisureBean();
				lSoggettoAssegnazioneVisureBean.setFlgUoSvUt("UT");
				lSoggettoAssegnazioneVisureBean.setIdUoSvUt(bean.getIdUtenteRicercatore());				
				listaAssegnazione.add(lSoggettoAssegnazioneVisureBean);		
				input.setListaxmlin(new XmlUtilitySerializer().bindXmlList(listaAssegnazione));
	
				DmpkProcessesSetsogginterni store = new DmpkProcessesSetsogginterni();
				StoreResultBean<DmpkProcessesSetsogginterniBean> output = store.execute(getLocale(), loginBean, input);
	
				if(StringUtils.isNotBlank(output.getDefaultMessage())) {
					if(errorMessages == null) errorMessages = new HashMap<String, String>();
					errorMessages.put(visura.getIdUd(), output.getDefaultMessage());
				}
			}
			
			if (errorMessages != null) {
				bean.setErrorMessages(errorMessages);
			}
		}

		return bean;
	}
	
	@Override
	public PaginatorBean<SelezionaRicercatoreVisureBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;		
	}	
	
	@Override
	public SelezionaRicercatoreVisureBean get(SelezionaRicercatoreVisureBean bean)
			throws Exception {
		return null;
	}

	@Override
	public SelezionaRicercatoreVisureBean remove(SelezionaRicercatoreVisureBean bean)
			throws Exception {
		return null;
	}

	@Override
	public SelezionaRicercatoreVisureBean update(SelezionaRicercatoreVisureBean bean,
			SelezionaRicercatoreVisureBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(SelezionaRicercatoreVisureBean bean) throws Exception {
		return null;
	}

}