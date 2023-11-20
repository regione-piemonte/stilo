/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesProcesshistorytreeBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.client.DmpkProcessesProcesshistorytree;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;

@Datasource(id = "AlberoProcessoDatasource")
public class AlberoProcessoDatasource extends AbstractServiceDataSource<AlberoProcessoIn, AlberoProcessoOut>{

	@Override
	public AlberoProcessoOut call(AlberoProcessoIn pInBean) throws Exception {
		DmpkProcessesProcesshistorytree store = new DmpkProcessesProcesshistorytree();
		DmpkProcessesProcesshistorytreeBean bean = new DmpkProcessesProcesshistorytreeBean();
		bean.setIdprocessin(pInBean.getIdProcesso());
		StoreResultBean<DmpkProcessesProcesshistorytreeBean> lStoreResult = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
		if (lStoreResult.isInError()){
			throw new StoreException(lStoreResult);
		}
		String xmlOut = lStoreResult.getResultBean().getTreexmlout();
		List<AlberoProcessoXmlBean> lListXmlNodi = XmlListaUtility.recuperaLista(xmlOut, AlberoProcessoXmlBean.class);
		AlberoProcessoOut lAlberoProcessoOut = new AlberoProcessoOut();
		List<AlberoProcessoBean> lListNodi = new ArrayList<AlberoProcessoBean>();
		String lastFather = null;
		for (AlberoProcessoXmlBean lAlberoProcessoXmlBean : lListXmlNodi){
			AlberoProcessoBean lAlberoProcessoBean = new AlberoProcessoBean();
			if (lAlberoProcessoXmlBean.getLivello() == 1){
				lastFather = lAlberoProcessoXmlBean.getId();
			} else {
				lAlberoProcessoBean.setParent(lastFather);
			}
			BeanUtilsBean2.getInstance().copyProperties(lAlberoProcessoBean, lAlberoProcessoXmlBean);
			lListNodi.add(lAlberoProcessoBean);
		}
		lAlberoProcessoOut.setNodi(lListNodi);
		return lAlberoProcessoOut;
	}

}
