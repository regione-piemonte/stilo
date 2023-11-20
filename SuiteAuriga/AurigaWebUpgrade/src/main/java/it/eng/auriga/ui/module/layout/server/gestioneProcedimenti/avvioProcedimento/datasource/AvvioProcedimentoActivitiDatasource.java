/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetlivpathexfolderBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.avvioProcedimento.bean.AvvioProcedimentoInBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.avvioProcedimento.bean.AvvioProcedimentoOutBean;
import it.eng.client.AvvioProcedimentoService;
import it.eng.client.DmpkCoreGetlivpathexfolder;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.workflow.service.bean.AvvioProcedimentoServiceInBean;
import it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean;

@Datasource(id = "AvvioProcedimentoActivitiDatasource")
public class AvvioProcedimentoActivitiDatasource extends AbstractServiceDataSource<AvvioProcedimentoInBean, AvvioProcedimentoOutBean>{

	private static Logger mLogger = Logger.getLogger(AvvioProcedimentoActivitiDatasource.class);
	@Override
	public AvvioProcedimentoOutBean call(AvvioProcedimentoInBean pInBean)
			throws Exception {
		mLogger.debug("Start call");
		AvvioProcedimentoOutBean lAvvioProcedimentoOutBean = new AvvioProcedimentoOutBean();
		AvvioProcedimentoServiceInBean lAvvioProcedimentoServiceInBean = new AvvioProcedimentoServiceInBean();
		BeanUtilsBean2.getInstance().copyProperties(lAvvioProcedimentoServiceInBean, pInBean);
		AvvioProcedimentoService lAvvioProcedimentoService = new AvvioProcedimentoService();
		AvvioProcedimentoServiceOutBean lAvvioProcedimentoServiceOutBean = 
			lAvvioProcedimentoService.avviaprocesso(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lAvvioProcedimentoServiceInBean);
		mLogger.debug(lAvvioProcedimentoServiceOutBean.getEsito());
		if (lAvvioProcedimentoServiceOutBean.getEsito()){
			mLogger.debug("Id processo " + lAvvioProcedimentoServiceOutBean.getIdProcesso());
			mLogger.debug("Process instance " + lAvvioProcedimentoServiceOutBean.getProcessInstanceId());
			mLogger.debug("Id folder " + lAvvioProcedimentoServiceOutBean.getIdFolder());
			mLogger.debug("Process definition id " + lAvvioProcedimentoServiceOutBean.getProcessDefinitionId());
			BeanUtilsBean2.getInstance().copyProperties(lAvvioProcedimentoOutBean, lAvvioProcedimentoServiceOutBean);
		} else {
			mLogger.debug(lAvvioProcedimentoServiceOutBean.getError());
			throw new StoreException(lAvvioProcedimentoServiceOutBean.getError());
		}
		mLogger.debug("Calcolo il padre");
		DmpkCoreGetlivpathexfolder lDmpkCoreGetlivpathexfolder =
			new DmpkCoreGetlivpathexfolder();
		DmpkCoreGetlivpathexfolderBean lDmpkCoreGetlivpathexfolderBean = 
			new DmpkCoreGetlivpathexfolderBean();
		lDmpkCoreGetlivpathexfolderBean.setIdnodopercorsoricercain("/");
		lDmpkCoreGetlivpathexfolderBean.setIdfolderin(lAvvioProcedimentoOutBean.getIdFolder());
		StoreResultBean<DmpkCoreGetlivpathexfolderBean> lStoreResult = 
			lDmpkCoreGetlivpathexfolder.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkCoreGetlivpathexfolderBean);
		if (lStoreResult.isInError()){
			throw new StoreException(lStoreResult);
		}
		
		String lStrXml = lStoreResult.getResultBean().getPercorsoxmlout();
		List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(lStrXml);
		String idFolderPadre = lListResult.get(lListResult.size()-2).getKey();
		lAvvioProcedimentoOutBean.setIdFolderPadre(new BigDecimal(idFolderPadre));
		mLogger.debug("End call");
		return lAvvioProcedimentoOutBean;
	}


}
