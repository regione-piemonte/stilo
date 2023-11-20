/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetestremiregnumud_jBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Getestremiregnumud_jImpl;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfStartflussowfforprocessBean;
import it.eng.auriga.database.store.dmpk_wf.store.impl.StartflussowfforprocessImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.storeutil.AnalyzeResult;
import it.eng.workflow.service.bean.AvvioIterAttiServiceInBean;
import it.eng.workflow.service.bean.AvvioProcedimentoServiceInBean;
import it.eng.workflow.service.storecaller.StoreCaller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class StartFlussoProcess extends StoreCaller<DmpkWfStartflussowfforprocessBean>{

	private static Logger mLogger = Logger.getLogger(StartFlussoProcess.class);
	
	public StoreResultBean<DmpkWfStartflussowfforprocessBean> startFlussoAvvioProcedimento(String lStrProcessInstanceId, AvvioProcedimentoServiceInBean pAvvioProcedimentoServiceInBean, AurigaLoginBean pAurigaLoginBean, Session lSession) throws NumberFormatException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		DmpkWfStartflussowfforprocessBean lDmpkWfStartflussowfforprocessBean = creaBeanAvvioProcedimento(lStrProcessInstanceId, pAvvioProcedimentoServiceInBean, pAurigaLoginBean);
		return startFlusso(lStrProcessInstanceId, lDmpkWfStartflussowfforprocessBean, pAurigaLoginBean, lSession);
	}
	
	public StoreResultBean<DmpkWfStartflussowfforprocessBean> startFlussoAvvioIterAtti(String lStrProcessInstanceId, BigDecimal pBigDecimalIdUd, String pEstremiRegNumUd, AvvioIterAttiServiceInBean pAvvioIterAttiServiceInBean, AurigaLoginBean pAurigaLoginBean, Session lSession) throws NumberFormatException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		DmpkWfStartflussowfforprocessBean lDmpkWfStartflussowfforprocessBean = creaBeanAvvioIterAtti(lStrProcessInstanceId, pBigDecimalIdUd, pEstremiRegNumUd, pAvvioIterAttiServiceInBean, pAurigaLoginBean, lSession);
		return startFlusso(lStrProcessInstanceId, lDmpkWfStartflussowfforprocessBean, pAurigaLoginBean, lSession);
	}
	
	public StoreResultBean<DmpkWfStartflussowfforprocessBean> startFlusso(String lStrProcessInstanceId,	DmpkWfStartflussowfforprocessBean pDmpkWfStartflussowfforprocessBean, AurigaLoginBean pAurigaLoginBean, Session lSession) throws NumberFormatException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		mLogger.debug("Start insertProcess");		
		final StartflussowfforprocessImpl lStartflussowfforprocessImpl = new StartflussowfforprocessImpl();
		lStartflussowfforprocessImpl.setBean(pDmpkWfStartflussowfforprocessBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lStartflussowfforprocessImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkWfStartflussowfforprocessBean> result = new StoreResultBean<DmpkWfStartflussowfforprocessBean>();
		AnalyzeResult.analyze(pDmpkWfStartflussowfforprocessBean, result);
		result.setResultBean(pDmpkWfStartflussowfforprocessBean);
		return result;
	}

	private DmpkWfStartflussowfforprocessBean creaBeanAvvioProcedimento(String lStrProcessInstanceId, AvvioProcedimentoServiceInBean pAvvioProcedimentoServiceInBean, AurigaLoginBean pAurigaLoginBean) throws IllegalAccessException, InvocationTargetException {
		DmpkWfStartflussowfforprocessBean lDmpkWfStartflussowfforprocessBean = new DmpkWfStartflussowfforprocessBean();
		lDmpkWfStartflussowfforprocessBean.setIdprocessio(pAvvioProcedimentoServiceInBean.getIdProcessIo() != null ? new BigDecimal(pAvvioProcedimentoServiceInBean.getIdProcessIo()) : null);
		if (pAvvioProcedimentoServiceInBean.getIdProcessType()!=null)
			lDmpkWfStartflussowfforprocessBean.setIdprocesstypein(new BigDecimal(pAvvioProcedimentoServiceInBean.getIdProcessType()));
		lDmpkWfStartflussowfforprocessBean.setCiflussowfin(lStrProcessInstanceId);
		lDmpkWfStartflussowfforprocessBean.setOggettoin(pAvvioProcedimentoServiceInBean.getOggettoProc());
		lDmpkWfStartflussowfforprocessBean.setNoteprocin(pAvvioProcedimentoServiceInBean.getNoteProc());
		lDmpkWfStartflussowfforprocessBean.setFlgrollbckfullin(1);
		lDmpkWfStartflussowfforprocessBean.setFlgautocommitin(null);
		setTokenAndUserId(pAurigaLoginBean, lDmpkWfStartflussowfforprocessBean);
		return lDmpkWfStartflussowfforprocessBean;
	}
	
	private DmpkWfStartflussowfforprocessBean creaBeanAvvioIterAtti(String lStrProcessInstanceId, BigDecimal pBigDecimalIdUd, String pEstremiRegNumUd, AvvioIterAttiServiceInBean pAvvioIterAttiServiceInBean, AurigaLoginBean pAurigaLoginBean, Session lSession) throws NumberFormatException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DmpkWfStartflussowfforprocessBean lDmpkWfStartflussowfforprocessBean = new DmpkWfStartflussowfforprocessBean();
		if (pAvvioIterAttiServiceInBean.getIdTipoProc()!=null)
			lDmpkWfStartflussowfforprocessBean.setIdprocesstypein(new BigDecimal(pAvvioIterAttiServiceInBean.getIdTipoProc()));
		lDmpkWfStartflussowfforprocessBean.setCiflussowfin(lStrProcessInstanceId);
		lDmpkWfStartflussowfforprocessBean.setOggettoin(pAvvioIterAttiServiceInBean.getOggetto());
		lDmpkWfStartflussowfforprocessBean.setCiprovprocessin(pEstremiRegNumUd);		
		lDmpkWfStartflussowfforprocessBean.setFlgrollbckfullin(1);
		lDmpkWfStartflussowfforprocessBean.setFlgautocommitin(null);
		setTokenAndUserId(pAurigaLoginBean, lDmpkWfStartflussowfforprocessBean);
		return lDmpkWfStartflussowfforprocessBean;
	}
	
}
