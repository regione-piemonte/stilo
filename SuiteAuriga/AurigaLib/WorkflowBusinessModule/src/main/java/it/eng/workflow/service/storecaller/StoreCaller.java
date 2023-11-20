/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.StoreBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

public class StoreCaller<T extends StoreBean> {

	public T setTokenAndUserId(AurigaLoginBean pAurigaLoginBean, T bean) throws IllegalAccessException, InvocationTargetException{
		BeanUtilsBean2.getInstance().setProperty(bean, "codidconnectiontokenin", pAurigaLoginBean.getToken());
		if (StringUtils.isNotEmpty(pAurigaLoginBean.getIdUserLavoro())){
			BigDecimal lBigDecimal = new BigDecimal(pAurigaLoginBean.getIdUserLavoro());
			BeanUtilsBean2.getInstance().setProperty(bean, "iduserlavoroin", lBigDecimal);
		}
		return bean;
	}
}
