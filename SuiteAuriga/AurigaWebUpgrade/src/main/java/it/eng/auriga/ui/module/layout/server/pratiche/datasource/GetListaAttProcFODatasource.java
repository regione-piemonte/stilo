/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetlistaattprocfoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DatiProcXAtt;
import it.eng.client.DmpkProcessesGetlistaattprocfo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id="GetListaAttProcFODatasource")
public class GetListaAttProcFODatasource extends AbstractDataSource<AttProcBean, AttProcBean>{

	private static Logger mLogger = Logger.getLogger(GetListaAttProcFODatasource.class);
	@Override
	public PaginatorBean<AttProcBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String idProcess = (String) getExtraparams().get("idProcess");
		String codGruppoAtt = (String) getExtraparams().get("codGruppoAtt");
		String idTipoEventoApp = (String) getExtraparams().get("idTipoEventoApp");
		
		DmpkProcessesGetlistaattprocfoBean input = new DmpkProcessesGetlistaattprocfoBean();
		input.setCodidconnectiontokenin(token);		
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprocessin(new BigDecimal(idProcess));
		input.setCidgruppoattin(codGruppoAtt);
		input.setIdtipoeventoappin(StringUtils.isNotBlank(idTipoEventoApp) ? new BigDecimal(idTipoEventoApp) : null);
		
		DmpkProcessesGetlistaattprocfo store = new DmpkProcessesGetlistaattprocfo();
		StoreResultBean<DmpkProcessesGetlistaattprocfoBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		if (result.isInError()){
			throw new StoreException(result);
		}	
		
		DatiProcXAtt scDatiProcXAtt = null;
		if (StringUtils.isNotBlank(result.getResultBean().getDatiprocxattout())){
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			scDatiProcXAtt = lXmlUtilityDeserializer.unbindXml(result.getResultBean().getDatiprocxattout(), DatiProcXAtt.class);	
		}   
		
		List<AttProcBean> listaAttivita = XmlListaUtility.recuperaLista(result.getResultBean().getListaattivitaout(), AttProcBean.class);
		if(listaAttivita != null) {		
			for(AttProcBean attProc : listaAttivita) {
				attProc.setIdProcess(idProcess);
				if(StringUtils.isNotBlank(attProc.getActivityName())) {
					attProc.setNome(attProc.getActivityName() + "|*|" + attProc.getNome());
				}
//				attProc.setFlgDatiVisibili("1");	
				if (scDatiProcXAtt != null){
					attProc.setIdUnitaLocale(scDatiProcXAtt.getIdUnitaLocale());
				    attProc.setNomeUnitaLocale(scDatiProcXAtt.getNomeUnitaLocale());
				    attProc.setIdUbicazioneAreaIntervento(scDatiProcXAtt.getIdUbicazioneAreaIntervento());
				    attProc.setIdUdIstanza(scDatiProcXAtt.getIdUdIstanza());
				    attProc.setUriRicAvvio(scDatiProcXAtt.getUriRicAvvio());
				    attProc.setEmailProponente(scDatiProcXAtt.getEmailProponente());					    
				    attProc.setIdDefProcFlow(scDatiProcXAtt.getIdDefProcFlow());	
				    attProc.setIdInstProcFlow(scDatiProcXAtt.getIdInstProcFlow());						
		        }		 
			}			
		}		
		
		return new PaginatorBean<AttProcBean>(listaAttivita);	
	}
	
	@Override
	public AttProcBean get(AttProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AttProcBean add(AttProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AttProcBean remove(AttProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AttProcBean update(AttProcBean bean, AttProcBean oldvalue)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AttProcBean bean)
			throws Exception {
		
		return null;
	}

}
