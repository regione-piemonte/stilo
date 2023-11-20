/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AttiModelliCustomBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FileModelloBean;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "CaricaModelloDatasource")
public class CaricaModelloDatasource extends AbstractServiceDataSource<LoginBean, FileModelloBean>{
	
	@Override
	public FileModelloBean call(LoginBean pLoginBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		AttiModelliCustomBean configBean = (AttiModelliCustomBean)SpringAppContext.getContext().getBean("attiModelliCustom");
		
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(loginBean.getSchema());
		
		//recupero il modello relativo alla pagina di testata
		DmpkModelliDocExtractvermodello retrieveVersion = new DmpkModelliDocExtractvermodello();
		DmpkModelliDocExtractvermodelloBean modelloBean = new DmpkModelliDocExtractvermodelloBean();
		modelloBean.setCodidconnectiontokenin(loginBean.getToken());
		modelloBean.setNomemodelloin(configBean.getConfigMap().get(getExtraparams().get("modello")));
		StoreResultBean<DmpkModelliDocExtractvermodelloBean>  result  = retrieveVersion.execute(getLocale(), loginBean, modelloBean);
		
		FileModelloBean lFileModelloBean = new FileModelloBean();
		lFileModelloBean.setNomeFile(result.getResultBean().getDisplayfilenameverout());
		lFileModelloBean.setUri(result.getResultBean().getUriverout());
		lFileModelloBean.setImpronta(result.getResultBean().getImprontaverout());
		
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		if (StringUtils.isNotBlank(result.getResultBean().getImprontaverout())){
			lMimeTypeFirmaBean.setImpronta(result.getResultBean().getImprontaverout());
		}
		lMimeTypeFirmaBean.setCorrectFileName(result.getResultBean().getDisplayfilenameverout());
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);		
		lMimeTypeFirmaBean.setConvertibile(true);
		lMimeTypeFirmaBean.setDaScansione(false);
		lMimeTypeFirmaBean.setMimetype(result.getResultBean().getMimetypeverout());
		lMimeTypeFirmaBean.setBytes(result.getResultBean().getDimensioneverout() != null ? result.getResultBean().getDimensioneverout().longValue() : 0);						
		
		lFileModelloBean.setInfoFile(lMimeTypeFirmaBean);
		
		return lFileModelloBean;
		
	}
	
}
