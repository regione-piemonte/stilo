/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGetidorganigrammaspaooBean;
import it.eng.auriga.database.store.dmpk_def_security.store.impl.GetidorganigrammaspaooImpl;
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
@Service(name="DmpkDefSecurityGetidorganigrammaspaoo")
public class Getidorganigrammaspaoo {
		
	private DmpkDefSecurityGetidorganigrammaspaooBean bean;
		  
	public void setBean(DmpkDefSecurityGetidorganigrammaspaooBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkDefSecurityGetidorganigrammaspaooBean> execute(SchemaBean lSchemaBean, DmpkDefSecurityGetidorganigrammaspaooBean pBean) throws Exception{
	   final GetidorganigrammaspaooImpl lGetidorganigrammaspaoo = new GetidorganigrammaspaooImpl();
	   setBean(pBean);
	   lGetidorganigrammaspaoo.setBean(bean);
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
					lGetidorganigrammaspaoo.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkDefSecurityGetidorganigrammaspaooBean> result = new StoreResultBean<DmpkDefSecurityGetidorganigrammaspaooBean>();
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