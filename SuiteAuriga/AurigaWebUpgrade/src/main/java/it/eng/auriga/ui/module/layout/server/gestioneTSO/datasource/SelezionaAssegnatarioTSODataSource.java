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
import it.eng.auriga.ui.module.layout.server.gestioneTSO.datasource.bean.SelezionaAssegnatarioTSOBean;
import it.eng.auriga.ui.module.layout.server.gestioneTSO.datasource.bean.SoggettoAssegnazioneTSOBean;
import it.eng.auriga.ui.module.layout.server.gestioneTSO.datasource.bean.TSOBean;
import it.eng.client.DmpkProcessesSetsogginterni;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author dbe4235
 *
 */

@Datasource(id="SelezionaAssegnatarioTSODataSource")
public class SelezionaAssegnatarioTSODataSource extends AbstractDataSource<SelezionaAssegnatarioTSOBean, SelezionaAssegnatarioTSOBean> {

	@Override
	public SelezionaAssegnatarioTSOBean add(SelezionaAssegnatarioTSOBean bean)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = null;
		
		if(StringUtils.isNotBlank(bean.getIdAssegnatario())) {
			
			for (TSOBean tso : bean.getListaRecord()) { 
			
				DmpkProcessesSetsogginterniBean input = new DmpkProcessesSetsogginterniBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
				if (tso.getIdProcedimento() != null) {
					input.setIdprocessin(new BigDecimal(tso.getIdProcedimento()));
				}	
				input.setRuolosoggettiin("ISTRUTTORE");
				input.setFlgappendrecin(null);
												
				List<SoggettoAssegnazioneTSOBean> listaAssegnazione = new ArrayList<SoggettoAssegnazioneTSOBean>();
				SoggettoAssegnazioneTSOBean lSoggettoAssegnazioneTSOBean = new SoggettoAssegnazioneTSOBean();
				lSoggettoAssegnazioneTSOBean.setFlgUoSvUt("UT");
				lSoggettoAssegnazioneTSOBean.setIdUoSvUt(bean.getIdAssegnatario());				
				listaAssegnazione.add(lSoggettoAssegnazioneTSOBean);		
				input.setListaxmlin(new XmlUtilitySerializer().bindXmlList(listaAssegnazione));
	
				DmpkProcessesSetsogginterni store = new DmpkProcessesSetsogginterni();
				StoreResultBean<DmpkProcessesSetsogginterniBean> output = store.execute(getLocale(), loginBean, input);
	
				if(StringUtils.isNotBlank(output.getDefaultMessage())) {
					if(errorMessages == null) errorMessages = new HashMap<String, String>();
					errorMessages.put(tso.getIdUdFolder(), output.getDefaultMessage());
				}
			}
			
			if (errorMessages != null) {
				bean.setErrorMessages(errorMessages);
			}
		}

		return bean;
	}
	
	@Override
	public PaginatorBean<SelezionaAssegnatarioTSOBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;		
	}	
	
	@Override
	public SelezionaAssegnatarioTSOBean get(SelezionaAssegnatarioTSOBean bean)
			throws Exception {
		return null;
	}

	@Override
	public SelezionaAssegnatarioTSOBean remove(SelezionaAssegnatarioTSOBean bean)
			throws Exception {
		return null;
	}

	@Override
	public SelezionaAssegnatarioTSOBean update(SelezionaAssegnatarioTSOBean bean,
			SelezionaAssegnatarioTSOBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(SelezionaAssegnatarioTSOBean bean) throws Exception {
		return null;
	}

}