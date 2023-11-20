/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 0.0.4-SNAPSHOT
 */
public class DmpkLoadComboSinapoliDmfn_load_combo_sinapoli {
		
	private static final String SERVICE_NAME = "DmpkLoadComboSinapoliDmfn_load_combo_sinapoli";	
	private static Logger mLogger = Logger.getLogger(DmpkLoadComboSinapoliDmfn_load_combo_sinapoli.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.auriga.database.store.result.bean.StoreResultBean 
  	 public it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.sinadoc.database.store.dmpk_load_combo_sinapoli.bean.DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean>  execute(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.sinadoc.database.store.dmpk_load_combo_sinapoli.bean.DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.sinadoc.database.store.dmpk_load_combo_sinapoli.bean.DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean>>() {}.getType();
  	  	return (it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.sinadoc.database.store.dmpk_load_combo_sinapoli.bean.DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.database.store.result.bean.StoreResultBean.class,outputType, SERVICE_NAME, "execute", var2,var3);
	 } 
}    