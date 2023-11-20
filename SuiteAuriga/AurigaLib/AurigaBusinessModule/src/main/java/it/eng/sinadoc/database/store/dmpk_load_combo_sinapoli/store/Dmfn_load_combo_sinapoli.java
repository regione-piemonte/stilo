/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.sinadoc.database.store.dmpk_load_combo_sinapoli.bean.DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean;
import it.eng.sinadoc.database.store.dmpk_load_combo_sinapoli.store.impl.Dmfn_load_combo_sinapoliImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.business.HibernateUtil;
import it.eng.storeutil.AnalyzeResult;

import java.sql.Connection;
import java.sql.SQLException;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.apache.commons.beanutils.BeanUtilsBean2;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginMarktokenusageBean;
import it.eng.auriga.database.store.dmpk_login.store.Marktokenusage;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
@Service(name="DmpkLoadComboSinapoliDmfn_load_combo_sinapoli")
public class Dmfn_load_combo_sinapoli {
		
	private DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean bean;
		  
	public void setBean(DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")	
	public StoreResultBean<DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean> execute(AurigaLoginBean lLoginBean, DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean pBean) throws Exception{
	   final Dmfn_load_combo_sinapoliImpl lDmfn_load_combo_sinapoli = new Dmfn_load_combo_sinapoliImpl();
	   lDmfn_load_combo_sinapoli.setBean(pBean);
	   setBean(pBean);
       setCommit();
       setToken(lLoginBean);
	   SubjectBean subject =  SubjectUtil.subject.get();
	   subject.setIdDominio(lLoginBean.getSchema());
	   SubjectUtil.subject.set(subject);    
	   Session session = null;
		try {
			LoginService lLoginService = new LoginService();
			lLoginService.login(lLoginBean);
			session = HibernateUtil.begin();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lDmfn_load_combo_sinapoli.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean> result = new StoreResultBean<DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean>();
			AnalyzeResult.analyze(pBean, result);
			result.setResultBean(pBean);
			Marktokenusage lMarktokenusage = new Marktokenusage();
			DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
			if (lLoginBean.getToken()!=null)
				lMarktokenusageBean.setCodidconnectiontokenin(lLoginBean.getToken());
			lMarktokenusageBean.setFlgautocommitin(1);
			lMarktokenusage.execute(lLoginBean, lMarktokenusageBean);
			return result;
		}catch(Exception e){
			throw e;
		}finally{
			HibernateUtil.release(session);
		}
		
	}
	
	protected void setCommit() {
	}

	protected void setToken(AurigaLoginBean pLoginBean) {
		bean.setCodidconnectiontokenin(pLoginBean.getToken());
	}	  
	  
}