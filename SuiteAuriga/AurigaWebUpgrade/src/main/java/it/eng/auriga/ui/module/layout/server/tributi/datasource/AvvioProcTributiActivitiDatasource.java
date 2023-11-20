/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetlivpathexfolderBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindproctyfromnomedoctypeiniBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.tributi.datasource.bean.AvvioProcTributiInBean;
import it.eng.auriga.ui.module.layout.server.tributi.datasource.bean.AvvioProcTributiOutBean;
import it.eng.client.AvvioProcedimentoService;
import it.eng.client.DmpkCoreGetlivpathexfolder;
import it.eng.client.DmpkUtilityFindproctyfromnomedoctypeini;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.workflow.service.bean.AvvioProcFascicoloServiceInBean;
import it.eng.workflow.service.bean.AvvioProcFascicoloServiceOutBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "AvvioProcTributiActivitiDatasource")
public class AvvioProcTributiActivitiDatasource extends AbstractServiceDataSource<AvvioProcTributiInBean, AvvioProcTributiOutBean>{

	private static Logger mLogger = Logger.getLogger(AvvioProcTributiActivitiDatasource.class);
	@Override
	public AvvioProcTributiOutBean call(AvvioProcTributiInBean pInBean)
			throws Exception {
		mLogger.debug("Start call");
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idDominio = null;
		if (lAurigaLoginBean.getDominio() != null && !"".equals(lAurigaLoginBean.getDominio())) {
			if (lAurigaLoginBean.getDominio().split(":").length == 2) {
				idDominio = lAurigaLoginBean.getDominio().split(":")[1];
			}
		}
		
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
		
		AvvioProcFascicoloServiceInBean lAvvioProcFascicoloServiceInBean = new AvvioProcFascicoloServiceInBean();
		lAvvioProcFascicoloServiceInBean.setIdProcessType(pInBean.getIdProcessType());
		lAvvioProcFascicoloServiceInBean.setFlowTypeId(pInBean.getFlowTypeId());
		lAvvioProcFascicoloServiceInBean.setOggettoProc(pInBean.getOggettoProc());
		lAvvioProcFascicoloServiceInBean.setIdTipoDoc(pInBean.getIdDocTypeFinale());
		lAvvioProcFascicoloServiceInBean.setNomeTipoDoc(pInBean.getNomeDocTypeFinale());
		if(pInBean.getListaIstanze() != null) {
			List<String> listaIdUd = new ArrayList<String>();
			for(int i = 0; i < pInBean.getListaIstanze().size(); i++) {
				listaIdUd.add(pInBean.getListaIstanze().get(i).getIdUdFolder());
			}
			lAvvioProcFascicoloServiceInBean.setListaIdUd(listaIdUd);		
		}
		
		AvvioProcedimentoService lAvvioProcedimentoService = new AvvioProcedimentoService();		
		AvvioProcFascicoloServiceOutBean lAvvioProcFascicoloServiceOutBean = lAvvioProcedimentoService.avviaprocfascicolo(getLocale(), lAurigaLoginBean, lAvvioProcFascicoloServiceInBean);
		
		mLogger.debug(lAvvioProcFascicoloServiceOutBean.getEsito());
		
		AvvioProcTributiOutBean lAvvioProcTributiOutBean = new AvvioProcTributiOutBean();		
		if (lAvvioProcFascicoloServiceOutBean.getEsito()) {
			mLogger.debug("Id processo " + lAvvioProcFascicoloServiceOutBean.getIdProcesso());
			mLogger.debug("Process instance " + lAvvioProcFascicoloServiceOutBean.getProcessInstanceId());
			mLogger.debug("Id folder " + lAvvioProcFascicoloServiceOutBean.getIdFolder());
			mLogger.debug("Process definition id " + lAvvioProcFascicoloServiceOutBean.getProcessDefinitionId());
			BeanUtilsBean2.getInstance().copyProperties(lAvvioProcTributiOutBean, lAvvioProcFascicoloServiceOutBean);
		} else {
			mLogger.debug(lAvvioProcFascicoloServiceOutBean.getError());
			throw new StoreException(lAvvioProcFascicoloServiceOutBean.getError());
		}
		
		mLogger.debug("Calcolo il padre");
		DmpkCoreGetlivpathexfolder lDmpkCoreGetlivpathexfolder = new DmpkCoreGetlivpathexfolder();
		DmpkCoreGetlivpathexfolderBean lDmpkCoreGetlivpathexfolderBean = new DmpkCoreGetlivpathexfolderBean();
		lDmpkCoreGetlivpathexfolderBean.setIdnodopercorsoricercain("/");
		lDmpkCoreGetlivpathexfolderBean.setIdfolderin(lAvvioProcTributiOutBean.getIdFolder());
		StoreResultBean<DmpkCoreGetlivpathexfolderBean> lDmpkCoreGetlivpathexfolderStoreResult = lDmpkCoreGetlivpathexfolder.execute(getLocale(), lAurigaLoginBean, lDmpkCoreGetlivpathexfolderBean);
		if (lDmpkCoreGetlivpathexfolderStoreResult.isInError()){
			throw new StoreException(lDmpkCoreGetlivpathexfolderStoreResult);
		}
		
		String lStrXml = lDmpkCoreGetlivpathexfolderStoreResult.getResultBean().getPercorsoxmlout();
		List<SimpleKeyValueBean> lListResult = XmlUtility.recuperaListaSemplice(lStrXml);
		String idFolderPadre = lListResult.get(lListResult.size()-2).getKey();
		lAvvioProcTributiOutBean.setIdFolderPadre(new BigDecimal(idFolderPadre));
		mLogger.debug("End call");
		return lAvvioProcTributiOutBean;
	}

}
