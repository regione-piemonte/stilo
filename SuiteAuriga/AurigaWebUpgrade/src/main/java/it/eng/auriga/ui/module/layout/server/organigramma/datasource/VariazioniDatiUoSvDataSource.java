/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGetvariazionidatiuosvBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.VariazioneDatiUoSvBean;
import it.eng.client.DmpkDefSecurityGetvariazionidatiuosv;
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

@Datasource(id = "VariazioniDatiUoSvDataSource")
public class VariazioniDatiUoSvDataSource extends AbstractFetchDataSource<VariazioneDatiUoSvBean> {

	@Override
	public PaginatorBean<VariazioneDatiUoSvBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(loginBean.getSchema());
		
		String idUoSv = getExtraparams().get("idUoSv");
		String flgUoSv = getExtraparams().get("flgUoSv");
				
		DmpkDefSecurityGetvariazionidatiuosvBean input = new DmpkDefSecurityGetvariazionidatiuosvBean();
	    input.setFlguosvin(flgUoSv != null ? flgUoSv : "UO");
		input.setIduosvin(new BigDecimal(idUoSv));
		input.setVariazionidalin(null);
		input.setVariazionialin(null);
			
		DmpkDefSecurityGetvariazionidatiuosv dmpkDefSecurityGetvariazionidatiuosv = new DmpkDefSecurityGetvariazionidatiuosv();
		
		StoreResultBean<DmpkDefSecurityGetvariazionidatiuosvBean> output = dmpkDefSecurityGetvariazionidatiuosv.execute(getLocale(), schemaBean, input);
			
		List<VariazioneDatiUoSvBean> listaVariazioni = new ArrayList<VariazioneDatiUoSvBean>();					
		if(output.getResultBean().getVariazionimlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getVariazionimlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		
			       		VariazioneDatiUoSvBean variazioneDatiUoSv = new VariazioneDatiUoSvBean();
			       		
			       		variazioneDatiUoSv.setIdUoSv(idUoSv);
			       		variazioneDatiUoSv.setFlgUoSv(flgUoSv);
			       		variazioneDatiUoSv.setDataVariazione(v.get(0) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(0)) : null); //colonna 1 dell'xml
			       		variazioneDatiUoSv.setCodiceUo(v.get(4)); //colonna 5 dell'xml	
			       		variazioneDatiUoSv.setDenominazioneUo(v.get(5)); //colonna 6 dell'xml	
			       		
			       		// Solo per la postazione
			       		if(flgUoSv != null && "SV".equals(flgUoSv)) {
				       		variazioneDatiUoSv.setUtente(v.get(6)); //colonna 7 dell'xml	
				       		variazioneDatiUoSv.setNomePostazione(v.get(9)); //colonna 10 dell'xml	
				       		variazioneDatiUoSv.setRuoloUtenteInUo(v.get(10)); //colonna 11 dell'xml	
				       		variazioneDatiUoSv.setFlgIncludiUoSubordinate(v.get(11)); //colonna 12 dell'xml	
				       		variazioneDatiUoSv.setFlgIncludiPostazioniSubordinate(v.get(12)); //colonna 13 dell'xml	
			       		}			      
			       					       		       		
		        		listaVariazioni.add(variazioneDatiUoSv);		        		
			   		}							
			}								
		}
		
		PaginatorBean<VariazioneDatiUoSvBean> lPaginatorBean = new PaginatorBean<VariazioneDatiUoSvBean>();
		lPaginatorBean.setData(listaVariazioni);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? listaVariazioni.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(listaVariazioni.size());
		
		return lPaginatorBean;		
	}	
	
	@Override
	public VariazioneDatiUoSvBean get(VariazioneDatiUoSvBean bean) throws Exception {		
		return null;
	}
	
	@Override
	public VariazioneDatiUoSvBean remove(VariazioneDatiUoSvBean bean)
	throws Exception {
		return null;
	}
	
	@Override
	public VariazioneDatiUoSvBean add(VariazioneDatiUoSvBean bean) 
	throws Exception {
		return bean;
	}

	@Override
	public VariazioneDatiUoSvBean update(VariazioneDatiUoSvBean bean,
			VariazioneDatiUoSvBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(VariazioneDatiUoSvBean bean)
	throws Exception {
		return null;
	}

}