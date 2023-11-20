/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.client.ConsultazioneAmcoImpl;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.function.bean.AmcoContiCreditoDebitoRequest;
import it.eng.document.function.bean.AmcoContiCreditoDebitoResponse;
import it.eng.document.function.bean.AmcoContiImputazioneRequest;
import it.eng.document.function.bean.AmcoContiImputazioneResponse;
import it.eng.document.function.bean.AmcoContoCreditoDebito;
import it.eng.document.function.bean.AmcoContoImputazione;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;

@Datasource(id = "LoadComboContoPrimaNotaGSADataSource")
public class LoadComboContoPrimaNotaGSADataSource extends AbstractFetchDataSource<SimpleKeyValueBean> {

	private static final Logger logger = Logger.getLogger(LoadComboContoPrimaNotaGSADataSource.class);
	
	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		List<SimpleKeyValueBean> lista = new ArrayList<SimpleKeyValueBean>();
		
		String flgContoDebitoCredito = StringUtils.isNotBlank(getExtraparams().get("flgContoDebitoCredito")) ? getExtraparams().get("flgContoDebitoCredito") : "";
		String nomeTipoDoc = StringUtils.isNotBlank(getExtraparams().get("nomeTipoDoc")) ? getExtraparams().get("nomeTipoDoc") : "";
		String codiceBP = StringUtils.isNotBlank(getExtraparams().get("codiceBP")) ? getExtraparams().get("codiceBP") : "";
		String codiceCapitolo = StringUtils.isNotBlank(getExtraparams().get("codiceCapitolo")) ? getExtraparams().get("codiceCapitolo") : "";
		String entrataUscita = StringUtils.isNotBlank(getExtraparams().get("entrataUscita")) ? getExtraparams().get("entrataUscita") : "";
		
		String codiceConto = null;
		String descConto = null;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("key")) {
					codiceConto = (String) criterion.getValue();
				} else if (criterion.getFieldName().equals("value")) {
					descConto = (String) criterion.getValue();
				}
			}
		}
		
		if("S".equalsIgnoreCase(flgContoDebitoCredito)) {
			
			ConsultazioneAmcoImpl lConsultazioneAmcoImpl = new ConsultazioneAmcoImpl(); 
			AmcoContiCreditoDebitoRequest input = new AmcoContiCreditoDebitoRequest();
			input.setNomeTipoDoc(nomeTipoDoc);
			input.setCodiceBP(codiceBP);
			input.setCodiceCapitolo(codiceCapitolo);
			input.setEntrataUscita(entrataUscita);
			AmcoContiCreditoDebitoResponse output = null;
			
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("AmcoRicercaContiCreditoDebito");
				lPerformanceLogger.start();		
				output = lConsultazioneAmcoImpl.ricercaContiCreditoDebito(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di AMCO (RicercaContiCreditoDebito)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
//				throw new StoreException(errorMessage);
			}
			
			if(output != null) {
				if(output.getDescrizioneErrore() != null && !"".equals(output.getDescrizioneErrore())) {			
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di AMCO (RicercaContiCreditoDebito): " + output.getDescrizioneErrore();
					logger.error(errorMessage);			
//					throw new StoreException(errorMessage);	
				} else if(output.getContoCreditoDebito() != null && output.getContoCreditoDebito().size() > 0) {
					for(AmcoContoCreditoDebito lAmcoContoCreditoDebito : output.getContoCreditoDebito()) {
						SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
						lSimpleKeyValueBean.setKey(lAmcoContoCreditoDebito.getCodice());
						lSimpleKeyValueBean.setValue(lAmcoContoCreditoDebito.getDescrizione());
						lSimpleKeyValueBean.setDisplayValue(lAmcoContoCreditoDebito.getCodice() + " - " + lAmcoContoCreditoDebito.getDescrizione());
						lista.add(lSimpleKeyValueBean);
					}
				}
			}
			
		} else {
			
			ConsultazioneAmcoImpl lConsultazioneAmcoImpl = new ConsultazioneAmcoImpl(); 
			AmcoContiImputazioneRequest input = new AmcoContiImputazioneRequest();
			input.setNomeTipoDoc(nomeTipoDoc);
			input.setCodiceCapitolo(codiceCapitolo);
			input.setCodiceConto(codiceConto);
			input.setDescConto(descConto);
			input.setEntrataUscita(entrataUscita);			
			AmcoContiImputazioneResponse output = null;
			
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("AmcoRicercaContiImputazione");
				lPerformanceLogger.start();		
				output = lConsultazioneAmcoImpl.ricercaContiImputazione(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di AMCO (RicercaContiImputazione)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
//				throw new StoreException(errorMessage);
			}
			
			if(output != null) { 
				if(output.getDescrizioneErrore() != null && !"".equals(output.getDescrizioneErrore())) {			
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di AMCO (RicercaContiImputazione): " + output.getDescrizioneErrore();
					logger.error(errorMessage);			
//					throw new StoreException(errorMessage);	
				} else if(output.getContiImputazione() != null && output.getContiImputazione().size() > 0) {
					for(AmcoContoImputazione lAmcoContoImputazione : output.getContiImputazione()) {
						SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
						lSimpleKeyValueBean.setKey(lAmcoContoImputazione.getCodice());
						lSimpleKeyValueBean.setValue(lAmcoContoImputazione.getDescrizione());
						lSimpleKeyValueBean.setDisplayValue(lAmcoContoImputazione.getCodice() + " - " + lAmcoContoImputazione.getDescrizione());
						lista.add(lSimpleKeyValueBean);
					}
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
		lSimpleKeyValueBean1.setKey("1");
		lSimpleKeyValueBean1.setValue("Valore 1");
		lSimpleKeyValueBean1.setDisplayValue("1 - Valore 1");
		lista.add(lSimpleKeyValueBean1);
		SimpleKeyValueBean lSimpleKeyValueBean2 = new SimpleKeyValueBean();
		lSimpleKeyValueBean2.setKey("2");
		lSimpleKeyValueBean2.setValue("Valore 2");
		lSimpleKeyValueBean2.setDisplayValue("2 - Valore 2");
		lista.add(lSimpleKeyValueBean2);
		return lista;
	}
	
}