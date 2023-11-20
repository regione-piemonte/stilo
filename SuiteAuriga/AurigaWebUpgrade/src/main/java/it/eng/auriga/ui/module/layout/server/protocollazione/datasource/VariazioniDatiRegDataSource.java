/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetlistavariazionidatiregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.VariazioneDatiRegBean;
import it.eng.client.DmpkRepositoryGuiGetlistavariazionidatireg;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;



import org.apache.commons.lang3.StringUtils;

@Datasource(id = "VariazioniDatiRegDataSource")
public class VariazioniDatiRegDataSource extends AbstractFetchDataSource<VariazioneDatiRegBean> {

	@Override
	public PaginatorBean<VariazioneDatiRegBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String idUd = getExtraparams().get("idUd");
				
		DmpkRepositoryGuiGetlistavariazionidatiregBean input = new DmpkRepositoryGuiGetlistavariazionidatiregBean();
	    input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(new BigDecimal(idUd));
			
		DmpkRepositoryGuiGetlistavariazionidatireg dmpkRepositoryGuiGetlistavariazionidatireg = new DmpkRepositoryGuiGetlistavariazionidatireg();
		StoreResultBean<DmpkRepositoryGuiGetlistavariazionidatiregBean> output = dmpkRepositoryGuiGetlistavariazionidatireg.execute(getLocale(),loginBean, input);
			
		List<VariazioneDatiRegBean> listaVariazioni = new ArrayList<VariazioneDatiRegBean>();					
		if(output.getResultBean().getListavariazioniout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListavariazioniout());
			// Lista con le registrazioni che sono possibili duplicati della registrazione che si sta per effettuare (XML conforme a schema LISTA_STD.xsd)
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		
			       		HashMap<String, String> comparators = new HashMap<String, String>();
			       		VariazioneDatiRegBean variazioneDatiReg = new VariazioneDatiRegBean();
			       		variazioneDatiReg.setIdUd(idUd);
			       		variazioneDatiReg.setTsVariazione(v.get(0) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(0)) : null); //colonna 1 dell'xml
			       		variazioneDatiReg.setTsVariazioneFisso(v.get(1)); //colonna 2 dell'xml	
			       		variazioneDatiReg.setEffettuatoDa(v.get(3)); //colonna 4 dell'xml		        		
			       		variazioneDatiReg.setMotiviVariazione(v.get(4)); //colonna 5 dell'xml				       					       		 	        		
			       		variazioneDatiReg.setDatiAnnullabiliVariati(v.get(5)); //colonna 6 dell'xml				       			       					       	
		        		listaVariazioni.add(variazioneDatiReg);		        		
			   		}							
			}								
		}
		
		PaginatorBean<VariazioneDatiRegBean> lPaginatorBean = new PaginatorBean<VariazioneDatiRegBean>();
		lPaginatorBean.setData(listaVariazioni);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? listaVariazioni.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(listaVariazioni.size());
		
		return lPaginatorBean;		
	}	
	
	@Override
	public VariazioneDatiRegBean get(VariazioneDatiRegBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public VariazioneDatiRegBean remove(VariazioneDatiRegBean bean)
	throws Exception {
		
		return null;
	}
	
	@Override
	public VariazioneDatiRegBean add(VariazioneDatiRegBean bean) 
	throws Exception {
		
		return bean;
	}

	@Override
	public VariazioneDatiRegBean update(VariazioneDatiRegBean bean,
			VariazioneDatiRegBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(VariazioneDatiRegBean bean)
	throws Exception {
		
		return null;
	}

}
