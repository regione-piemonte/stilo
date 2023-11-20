/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetdatidaacsorBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.RigaDatiDaACSOR;
import it.eng.client.DmpkRepositoryGuiGetdatidaacsor;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id="FindDatiPrincipaliContribuenteDataSource")
public class FindDatiPrincipaliContribuenteDataSource extends AbstractServiceDataSource<RigaDatiDaACSOR, RigaDatiDaACSOR> {

	@SuppressWarnings("unused")
	private static final String BUSINESS_CLIENT = DmpkRepositoryGuiGetdatidaacsor.class.getSimpleName();
	private static Logger logger = Logger.getLogger(FindDatiPrincipaliContribuenteDataSource.class);
	
	@Override
	public RigaDatiDaACSOR call(RigaDatiDaACSOR bean) throws Exception {
//		logger.debug("inizio call() per eseguire "+BUSINESS_CLIENT);
		final String codiceACSOR = bean.getCodiceACSOR();

		RigaDatiDaACSOR result = new RigaDatiDaACSOR();
		
//		logger.info("CodACSOR: "+String.valueOf(codiceACSOR));
		if (StringUtils.isBlank(codiceACSOR)) {
			return result;
		}
		
		final AurigaLoginBean aurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		final String idUserLavoroStr = aurigaLoginBean.getIdUserLavoro();
		final String token = aurigaLoginBean.getToken();
		
		final DmpkRepositoryGuiGetdatidaacsorBean inputParams = new DmpkRepositoryGuiGetdatidaacsorBean();
		inputParams.setCodidconnectiontokenin(token);
		inputParams.setIduserlavoroin(idUserLavoroStr!=null?new BigDecimal(idUserLavoroStr):null);
		inputParams.setCodacsorin(codiceACSOR);
		
		final DmpkRepositoryGuiGetdatidaacsor client_get_dati_da_ACSOR = new DmpkRepositoryGuiGetdatidaacsor();

//		if (logger.isDebugEnabled()) {
//			logger.debug("CodIdConnectionToken: "+String.valueOf(token));
//			logger.debug("IdUserLavoro: "+String.valueOf(idUserLavoroStr));
//			logger.debug("Avvio esecuzione di "+BUSINESS_CLIENT);
//		}

		final StoreResultBean<DmpkRepositoryGuiGetdatidaacsorBean> output = client_get_dati_da_ACSOR.execute(getLocale(), aurigaLoginBean, inputParams);
		
		if (output.isInError()) {
			final String msg = String.valueOf(output.getDefaultMessage());
			logger.error("L'esecuzione della function 'DMPK_REPOSITORY_GUI.GetDatiDaACSOR' si è conclusa con errore:\n"+msg);
			addMessage("Non è stato possibile recuperare il Contribuente con 'Codice ACS' "+codiceACSOR, msg, MessageType.ERROR);
			return result;
		} 
		
		final DmpkRepositoryGuiGetdatidaacsorBean outputParams = output.getResultBean();
		final String xmlLista = outputParams.getListaout();
	
		if (logger.isDebugEnabled()) {
		   logger.debug("ListaOut:\n"+String.valueOf(xmlLista));
		}

		List<RigaDatiDaACSOR> items = new ArrayList<RigaDatiDaACSOR>(0);
		if ( StringUtils.containsIgnoreCase(xmlLista, "Riga") ) {
			try {
			   items = XmlListaUtility.recuperaLista(xmlLista, RigaDatiDaACSOR.class);
			} catch (Exception e) {
			   logger.error("La deserializzazione del parametro 'ListaOut' è fallita:\n"+String.valueOf(xmlLista));
			   addMessage("Non è stato possibile recuperare il Contribuente con 'Codice ACS' "+codiceACSOR, String.valueOf(e.getLocalizedMessage()), MessageType.ERROR);
			   return result;
			}
		}

		switch (items.size()) {
		case 0:
//			addMessage("Nessun Contribuente trovato con 'Codice ACS' "+codiceACSOR, "", MessageType.INFO);
			break;
		case 1:
			result = items.get(0);
			check(codiceACSOR, result);
			break;
		default:
			result = items.get(0);
			check(codiceACSOR, result);
			logger.error("Il parametro 'ListaOut' contiene più di una riga: "+items.size());
			break;
		}
		
//		logger.debug("fine call() per eseguire "+BUSINESS_CLIENT);
		return result;
	}

	private void check(final String codiceACSOR, RigaDatiDaACSOR result) {
		if ( isValid(result) ) {
		   result.setCodiceACSOR(codiceACSOR);
		} else {
//		   addMessage("Nessun Contribuente trovato con 'Codice ACS' "+codiceACSOR, "", MessageType.INFO);
		}
	}

	private boolean isValid(RigaDatiDaACSOR result) {
		return StringUtils.isNotBlank(result.getCognome()) 
			   || StringUtils.isNotBlank(result.getNome())
			   || StringUtils.isNotBlank(result.getCf());
	}

}
