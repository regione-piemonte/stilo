/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.StoreBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

public class AnalyzeResult {

	public static void analyze(StoreBean bean, StoreResultBean<?> pStoreResultBean) throws NumberFormatException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Integer paramtro_1 = Integer.valueOf(BeanUtilsBean2.getInstance().getProperty(bean, "parametro_1"));
		if (paramtro_1 == 0){
			pStoreResultBean.setDefaultMessage(bean.getErrmsgout());
			pStoreResultBean.setErrorCode(bean.getErrcodeout());
			pStoreResultBean.setErrorContext(bean.getErrcontextout());
			pStoreResultBean.setStoreName(bean.getStoreName());
			pStoreResultBean.setInError(true);
		} else {
			if(StringUtils.isNotBlank(bean.getErrmsgout())) {
				pStoreResultBean.setDefaultMessage(bean.getErrmsgout());
			}
			pStoreResultBean.setInError(false);
		}
	}
}
