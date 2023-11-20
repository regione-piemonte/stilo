/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetinfoallegistrichintpraticaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AllegatoProcBean;
import it.eng.client.DmpkProcessesGetinfoallegistrichintpratica;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id="GetListaAllegatiProcDatasource")
public class GetListaAllegatiProcDatasource extends AbstractDataSource<AllegatoProcBean, AllegatoProcBean>{

	private static Logger mLogger = Logger.getLogger(GetListaAllegatiProcDatasource.class);
	@Override
	public PaginatorBean<AllegatoProcBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String idProcess = (String) getExtraparams().get("idProcess");
				
		DmpkProcessesGetinfoallegistrichintpraticaBean input = new DmpkProcessesGetinfoallegistrichintpraticaBean(); 
		input.setCodidconnectiontokenin(token);		
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprocessin(new BigDecimal(idProcess));
		input.setAllegatidiin(getExtraparams().get("allegatiDi"));
		
		DmpkProcessesGetinfoallegistrichintpratica store = new DmpkProcessesGetinfoallegistrichintpratica();
		StoreResultBean<DmpkProcessesGetinfoallegistrichintpraticaBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		if (result.isInError()){
			throw new StoreException(result);
		}
	
		List<AllegatoProcBean> listaAllegati = XmlListaUtility.recuperaLista(result.getResultBean().getDatiallegatixmlout(), AllegatoProcBean.class);
		
		if(listaAllegati != null) {
			for(AllegatoProcBean allegato : listaAllegati) {
				MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
				lMimeTypeFirmaBean.setImpronta(allegato.getImpronta());
				lMimeTypeFirmaBean.setCorrectFileName(allegato.getDisplayName());
				lMimeTypeFirmaBean.setFirmato(allegato.getFlgFirmato() != null && "1".equals(allegato.getFlgFirmato()));
				lMimeTypeFirmaBean.setFirmaValida(allegato.getFlgFirmato() != null && "1".equals(allegato.getFlgFirmato()));			
				lMimeTypeFirmaBean.setConvertibile(allegato.getFlgConvertibile() != null && "1".equals(allegato.getFlgConvertibile()));
				lMimeTypeFirmaBean.setDaScansione(false);
				lMimeTypeFirmaBean.setMimetype(allegato.getMimetype());
				if(lMimeTypeFirmaBean.isFirmato()){
					lMimeTypeFirmaBean.setTipoFirma(allegato.getDisplayName().toUpperCase().endsWith("P7M")||allegato.getDisplayName().toUpperCase().endsWith("TSD")?"CAdES_BES":"PDF");
//					lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(allegato.getFirmatari())?allegato.getFirmatari().split(","):null);
				}
				allegato.setInfoFile(lMimeTypeFirmaBean);
				allegato.setRemoteUri(true);
				allegato.setIsChanged(false);				
			}
		}
		
		return new PaginatorBean<AllegatoProcBean>(listaAllegati);	
	}
	
	@Override
	public AllegatoProcBean get(AllegatoProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AllegatoProcBean add(AllegatoProcBean bean) throws Exception {
				
		return null;
	}

	@Override
	public AllegatoProcBean remove(AllegatoProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AllegatoProcBean update(AllegatoProcBean bean, AllegatoProcBean oldvalue)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AllegatoProcBean bean)
			throws Exception {
		
		return null;
	}

}
