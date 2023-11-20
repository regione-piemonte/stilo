/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_policy.bean.DmpkPolicyTestprivsuoggdefcontesto_lBean;
import it.eng.auriga.database.store.dmpk_policy.store.impl.Testprivsuoggdefcontesto_lImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.business.HibernateUtil;
import it.eng.storeutil.AnalyzeResult;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import it.eng.auriga.database.store.bean.SchemaBean;
/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */
@Service(name="DmpkPolicyTestprivsuoggdefcontesto_l")
public class Testprivsuoggdefcontesto_l {
		
	private DmpkPolicyTestprivsuoggdefcontesto_lBean bean;
		  
	public void setBean(DmpkPolicyTestprivsuoggdefcontesto_lBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkPolicyTestprivsuoggdefcontesto_lBean> execute(SchemaBean lSchemaBean, DmpkPolicyTestprivsuoggdefcontesto_lBean pBean) throws Exception{
	   final Testprivsuoggdefcontesto_lImpl lTestprivsuoggdefcontesto_l = new Testprivsuoggdefcontesto_lImpl();
	   setBean(pBean);
	   lTestprivsuoggdefcontesto_l.setBean(bean);
       setCommit();
	   SubjectBean subject =  SubjectUtil.subject.get();
	   subject.setIdDominio(lSchemaBean.getSchema());	
	   SubjectUtil.subject.set(subject);    
	   Session session = null;
		try {
			session = HibernateUtil.begin();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lTestprivsuoggdefcontesto_l.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkPolicyTestprivsuoggdefcontesto_lBean> result = new StoreResultBean<DmpkPolicyTestprivsuoggdefcontesto_lBean>();
			AnalyzeResult.analyze(bean, result);
			result.setResultBean(bean);
			return result;
		}catch(Exception e){
			if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().equals("Chiusura forzata")) throw new Exception("Chiusura forzata");
			else throw e;
		}finally{
			HibernateUtil.release(session);
		}
		
	}
	
	protected void setCommit() {
	}
}