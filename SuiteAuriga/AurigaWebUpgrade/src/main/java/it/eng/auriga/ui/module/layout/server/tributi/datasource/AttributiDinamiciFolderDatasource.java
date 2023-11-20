/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciSetattrdinamiciBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.tributi.datasource.bean.AttributiDinamiciFolderBean;
import it.eng.client.DmpkAttributiDinamiciSetattrdinamici;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringWriter;
import java.math.BigDecimal;

import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="AttributiDinamiciFolderDatasource")
public class AttributiDinamiciFolderDatasource extends AbstractServiceDataSource<AttributiDinamiciFolderBean, AttributiDinamiciFolderBean>{

	@Override
	public AttributiDinamiciFolderBean call(AttributiDinamiciFolderBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		String flgIgnoreObblig = getExtraparams().get("flgIgnoreObblig");
		
		DmpkAttributiDinamiciSetattrdinamiciBean lBean = new DmpkAttributiDinamiciSetattrdinamiciBean();
		lBean.setCodidconnectiontokenin(token);
		lBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		lBean.setNometabellain("DMT_FOLDER");
		lBean.setRowidin(pInBean.getRowId());
		lBean.setActivitynamewfin(pInBean.getActivityNameWF());
		lBean.setEsitoactivitywfin(pInBean.getEsitoActivityWF());
		
		if(StringUtils.isNotBlank(flgIgnoreObblig)) {
			lBean.setFlgignoraobbligin(new Integer(flgIgnoreObblig));
		}
		
		if(pInBean.getValori() != null && pInBean.getValori().size() > 0 && pInBean.getTipiValori() != null && pInBean.getTipiValori().size() > 0) {
			SezioneCache lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, pInBean.getValori(), pInBean.getTipiValori(), getSession());			
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			lBean.setAttrvaluesxmlin(lStringWriter.toString());
		}
		
		DmpkAttributiDinamiciSetattrdinamici store = new DmpkAttributiDinamiciSetattrdinamici();
		StoreResultBean<DmpkAttributiDinamiciSetattrdinamiciBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
		if (result.isInError()){
			throw new StoreException(result);
		}
		
		return pInBean;
	}

}
