/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetlogcontenutosezBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneContenutiAmministrazioneTrasparente.datasource.bean.CronologiaOperazioniBean;
import it.eng.client.DmpkAmmTraspGetlogcontenutosez;
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

/**
 * 
 * @author dbe4235
 *
 */
@Datasource(id = "CronologiaOperazioniDataSource")
public class CronologiaOperazioniDataSource extends AbstractFetchDataSource<CronologiaOperazioniBean> {

	@Override
	public PaginatorBean<CronologiaOperazioniBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
				
		List<CronologiaOperazioniBean> listaOperazioni = new ArrayList<CronologiaOperazioniBean>();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkAmmTraspGetlogcontenutosezBean input = new DmpkAmmTraspGetlogcontenutosezBean();
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodidconnectiontokenin(token);
		input.setIdcontenutosezin(new BigDecimal(getExtraparams().get("idContenutoSezione")));
		
		DmpkAmmTraspGetlogcontenutosez dmpkAmmTraspGetlogcontenutosez = new DmpkAmmTraspGetlogcontenutosez();
		StoreResultBean<DmpkAmmTraspGetlogcontenutosezBean> output = dmpkAmmTraspGetlogcontenutosez.execute(getLocale(), loginBean, input);
		
		if(output.getResultBean().getListalogout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListalogout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		
			       		CronologiaOperazioniBean operazione = new CronologiaOperazioniBean();
			       		
			       		operazione.setTsOperazione(v.get(0) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(0)) : null);
			       		operazione.setProgressivoOperazioneXOrd(v.get(1));
			       		operazione.setIdUtenteOperazione(v.get(2));
			       		operazione.setDescrizioneUtenteOperazione(v.get(3));
			       		operazione.setIdUtenteOperazioneDelega(v.get(4));
			       		operazione.setDescrizioneUtenteOperazioneDelega(v.get(5));
			       		operazione.setCodTipoOperazione(v.get(6));
			       		operazione.setDescrizioneTipoOperazione(v.get(7));
			       		operazione.setDescrizioneDettagliOperazione(v.get(8));
			       		operazione.setEsitoOperazione(v.get(9));
			       		operazione.setMessaggioErroreOperazione(v.get(10));
			       		
			       		listaOperazioni.add(operazione);		        		
			   	}							
			}				
		}
		
		PaginatorBean<CronologiaOperazioniBean> lPaginatorBean = new PaginatorBean<CronologiaOperazioniBean>();
		lPaginatorBean.setData(listaOperazioni);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? listaOperazioni.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(listaOperazioni.size());
		
		return lPaginatorBean;		
	}	
	
	@Override
	public CronologiaOperazioniBean get(CronologiaOperazioniBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public CronologiaOperazioniBean remove(CronologiaOperazioniBean bean)
	throws Exception {
		
		return null;
	}
	
	@Override
	public CronologiaOperazioniBean add(CronologiaOperazioniBean bean) 
	throws Exception {
		
		return bean;
	}

	@Override
	public CronologiaOperazioniBean update(CronologiaOperazioniBean bean,
			CronologiaOperazioniBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(CronologiaOperazioniBean bean)
	throws Exception {
		
		return null;
	}

}