/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.database.store.bean.StorageStoreBean;
import it.eng.database.store.result.bean.StorageStoreResultBean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

public class StorageAnalyzeResult {

	public static void analyze(StorageStoreBean bean, StorageStoreResultBean<?> pStoreResultBean) throws NumberFormatException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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
