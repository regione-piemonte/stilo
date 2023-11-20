/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.ui.module.layout.server.postainuscita.datasource.bean.RicevutePostaInUscitaBean;
import it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean;
import it.eng.aurigamailbusiness.bean.InterrogazioneRelazioneEmailBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TipoRelazione;
import it.eng.client.AurigaMailService;
import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "RicevutePostaInUscitaDataSource")
public class RicevutePostaInUscitaDataSource extends AbstractFetchDataSource<RicevutePostaInUscitaBean> {

	@Override
	public String getNomeEntita() {
		return "ricevutepostainuscita";
	}	
	
	@Override
	public PaginatorBean<RicevutePostaInUscitaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {	
	
		// leggo il paramatro di input
		String idEmailRif = StringUtils.isNotBlank(getExtraparams().get("idEmailRif")) ? getExtraparams().get("idEmailRif") : null;			
		String idDestinatario = StringUtils.isNotBlank(getExtraparams().get("idDestinatario")) ? getExtraparams().get("idDestinatario") : null;

		// Inizializzo l'INPUT
		InterrogazioneRelazioneEmailBean lInterrogazioneRelazioneEmailBean = new InterrogazioneRelazioneEmailBean();
		lInterrogazioneRelazioneEmailBean.setIdEmail(idEmailRif);
		TipoRelazione tipoRelazione = TipoRelazione.DIRETTA;
		lInterrogazioneRelazioneEmailBean.setTipoRelazione(tipoRelazione);
		
		EmailInfoRelazioniBean lEmailInfoRelazioniBean = AurigaMailService.getMailProcessorService().getrelazionimail(getLocale(), lInterrogazioneRelazioneEmailBean);		
		
		// Inizializzo l'OUTPUT
		List<RicevutePostaInUscitaBean> data = new ArrayList<RicevutePostaInUscitaBean>();
		
		if(lEmailInfoRelazioniBean.getRelazioni() != null) {

			for (TRelEmailMgoBean listTRelEmailMgoBean : lEmailInfoRelazioniBean.getRelazioni()) {					
				
				String idEmail1 = listTRelEmailMgoBean.getIdEmail1();				
				String idDestinatario2 = listTRelEmailMgoBean.getIdDestinatario2();			
				
				TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), idEmail1);
				
				if(idDestinatario == null || idDestinatario.equals(idDestinatario2)) {
					// filtro solo le mail che sono ricevute PEC e delivery status notification
					if (lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEO_RIC_CONF") ||							
						lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_ACC") ||
						lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_NO_ACC") ||
						lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_PREAS_C") ||
						lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_CONS") ||
						lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_PREAVV_NO_CONS") ||
						lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_NO_CONS") ||
						lTEmailMgoBean.getCategoria().equalsIgnoreCase("DELIVERY_STATUS_NOT"))
					{
								RicevutePostaInUscitaBean node = new RicevutePostaInUscitaBean();
								
								// Setto i valori dell'XML nel bean 
								node.setIdEmail(idEmail1);	
								
								String userLanguage = getLocale().getLanguage();
								
								if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEO_RIC_CONF"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_PEO_RIC_CONF_value"));														
								else if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_ACC"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_PEC_RIC_ACC_value"));														
								else if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_NO_ACC"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_PEC_RIC_NO_ACC_value"));								
								else if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_PREAS_C"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_PEC_RIC_PREAS_C_value"));								
								else if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_CONS"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_PEC_RIC_CONS_value"));								
								else if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_PREAVV_NO_CONS"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_PEC_RIC_PREAVV_NO_CONS_value"));								
								else if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("PEC_RIC_NO_CONS"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_PEC_RIC_NO_CONS_value"));								
								else if(lTEmailMgoBean.getCategoria().equalsIgnoreCase("DELIVERY_STATUS_NOT"))
									lTEmailMgoBean.setCategoria(MessageUtil.getValue(userLanguage, getSession(), "email_categoria_DELIVERY_STATUS_NOT_value"));						
								node.setCategoria(lTEmailMgoBean.getCategoria()); //colonna 2  : CATEGORIA	
																
					       		node.setDataRicezione(lTEmailMgoBean.getTsRicezione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(lTEmailMgoBean.getTsRicezione()) : null); //colonna 3  : DATA RICEZIONE
					       		
					       		node.setMittente(lTEmailMgoBean.getAccountMittente()); //colonna 4 : MITTENTE
					       		
					       		data.add(node);
					}					
				}
			}
			
		}
		
		PaginatorBean<RicevutePostaInUscitaBean> lPaginatorBean = new PaginatorBean<RicevutePostaInUscitaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;
	}	
}
