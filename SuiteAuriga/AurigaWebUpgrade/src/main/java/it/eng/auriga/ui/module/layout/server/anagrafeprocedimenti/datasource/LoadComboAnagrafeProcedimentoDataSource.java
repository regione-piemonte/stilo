/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesTrovaprocesstypesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkProcessTypesTrovaprocesstypes;
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
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



@Datasource(id = "LoadComboAnagrafeProcedimentoDataSource")
public class LoadComboAnagrafeProcedimentoDataSource extends AbstractFetchDataSource<SimpleKeyValueBean>{

	private static final Logger log = Logger.getLogger(LoadComboAnagrafeProcedimentoDataSource.class);
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		List<SimpleKeyValueBean> data = new ArrayList<SimpleKeyValueBean>();
		
		String idProcessTypeGenKey = StringUtils.isNotBlank(getExtraparams().get("idProcessTypeGen")) ? getExtraparams().get("idProcessTypeGen") : "";
		String nomeProcessTypeGenValue = StringUtils.isNotBlank(getExtraparams().get("nomeProcessTypeGen")) ? getExtraparams().get("nomeProcessTypeGen") : "";
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
		DmpkProcessTypesTrovaprocesstypesBean input = new DmpkProcessTypesTrovaprocesstypesBean();
		
		String nomeProcedimento= "";
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("value")) {
					nomeProcedimento = (String) criterion.getValue();
				}
			}
		}
		
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setNomeio(nomeProcedimento);
		
		DmpkProcessTypesTrovaprocesstypes dmpkProcessTypesTrovaprocesstypes = new DmpkProcessTypesTrovaprocesstypes();
		StoreResultBean<DmpkProcessTypesTrovaprocesstypesBean> output = dmpkProcessTypesTrovaprocesstypes.execute(locale, loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if(output.getResultBean().getListaxmlout() != null)
		{
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if(lista != null)
			{
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

			 		SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
			 		lSimpleKeyValueBean.setKey(v.get(0) != null ? v.get(0) : null);
			 		lSimpleKeyValueBean.setValue(v.get(1));
			 		data.add(lSimpleKeyValueBean);
				}
			}
		}
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		
		SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
		lSimpleKeyValueBean.setKey(idProcessTypeGenKey);
		lSimpleKeyValueBean.setValue(nomeProcessTypeGenValue);
		data.add(lSimpleKeyValueBean);
		
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(data.size());
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;
	}

}
