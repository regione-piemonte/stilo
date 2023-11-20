/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGetprocdefidflussowfBean;
import it.eng.auriga.database.store.dmpk_wf.store.impl.GetprocdefidflussowfImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class RetrieveProcessDefinition {

	public StoreResultBean<DmpkWfGetprocdefidflussowfBean> getProcessDefinition(String pStrProcessInstance, AurigaLoginBean pAurigaLoginBean, Session pSession) throws NumberFormatException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		DmpkWfGetprocdefidflussowfBean lDmpkWfGetprocdefidflussowfBean = creaBean(pStrProcessInstance, pAurigaLoginBean);
		
		final GetprocdefidflussowfImpl lGetprocdefidflussowfImpl = new GetprocdefidflussowfImpl();
		lGetprocdefidflussowfImpl.setBean(lDmpkWfGetprocdefidflussowfBean);
		pSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lGetprocdefidflussowfImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkWfGetprocdefidflussowfBean> result = new StoreResultBean<DmpkWfGetprocdefidflussowfBean>();
		result.setResultBean(lDmpkWfGetprocdefidflussowfBean);
		return result;
	}

	private DmpkWfGetprocdefidflussowfBean creaBean(String pStrProcessInstance,
			AurigaLoginBean pAurigaLoginBean) throws IllegalAccessException, InvocationTargetException {
		DmpkWfGetprocdefidflussowfBean lDmpkWfGetprocdefidflussowfBean = 
			new DmpkWfGetprocdefidflussowfBean();
		lDmpkWfGetprocdefidflussowfBean.setCiflussowfin(pStrProcessInstance);
		return lDmpkWfGetprocdefidflussowfBean;
	}
}
