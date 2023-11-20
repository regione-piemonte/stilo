/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.client.ConsultazioneAmcoImpl;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.function.bean.AmcoTipiDocumentoRequest;
import it.eng.document.function.bean.AmcoTipiDocumentoResponse;
import it.eng.document.function.bean.AmcoTipoDocumento;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

@Datasource(id = "LoadComboTipoMovimentoGSADataSource")
public class LoadComboTipoMovimentoGSADataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	private static final Logger logger = Logger.getLogger(LoadComboTipoMovimentoGSADataSource.class);
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		List<SimpleKeyValueBean> lista = new ArrayList<SimpleKeyValueBean>();
		
		ConsultazioneAmcoImpl lConsultazioneAmcoImpl = new ConsultazioneAmcoImpl(); 
		AmcoTipiDocumentoRequest input = new AmcoTipiDocumentoRequest();
		input.setNome(null);
		input.setDescrizione(null);
		input.setFinanziaria("N");	   
		AmcoTipiDocumentoResponse output = null;
		
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("AmcoRicercaTipiDocumento");
			lPerformanceLogger.start();		
			output = lConsultazioneAmcoImpl.ricercaTipiDocumento(getLocale(), input);
			lPerformanceLogger.end();		
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di AMCO (RicercaTipiDocumento)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
//			throw new StoreException(errorMessage);
		}
		
		if(output != null) {
			if(output.getDescrizioneErrore() != null && !"".equals(output.getDescrizioneErrore())) {			
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di AMCO (RicercaTipiDocumento): " + output.getDescrizioneErrore();
				logger.error(errorMessage);			
//				throw new StoreException(errorMessage);	
			} else if(output.getTipiDocumento() != null && output.getTipiDocumento().size() > 0) {
				for(AmcoTipoDocumento lAmcoTipoDocumento : output.getTipiDocumento()) {
					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
					lSimpleKeyValueBean.setKey(lAmcoTipoDocumento.getNome());
					lSimpleKeyValueBean.setValue(lAmcoTipoDocumento.getDescrizione());
					lSimpleKeyValueBean.setDisplayValue(lAmcoTipoDocumento.getNome() + " - " + lAmcoTipoDocumento.getDescrizione());
					lista.add(lSimpleKeyValueBean);
				}
			}		
		}
		
//		lista = getTestData();	
		
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lista);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lista.size());
		lPaginatorBean.setTotalRows(lista.size());		
		return lPaginatorBean;
	}
	
	public List<SimpleKeyValueBean> getTestData() {
		List<SimpleKeyValueBean> lista = new ArrayList<SimpleKeyValueBean>();
		SimpleKeyValueBean lSimpleKeyValueBean1 = new SimpleKeyValueBean();
		lSimpleKeyValueBean1.setKey("Valore 1");
		lSimpleKeyValueBean1.setValue("Valore 1 descrizione");
		lista.add(lSimpleKeyValueBean1);
		SimpleKeyValueBean lSimpleKeyValueBean2 = new SimpleKeyValueBean();
		lSimpleKeyValueBean2.setKey("Valore 2");
		lSimpleKeyValueBean2.setValue("Valore 2 descrizione");
		lista.add(lSimpleKeyValueBean2);
		return lista;
	}
	
}