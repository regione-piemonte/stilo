/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFinduobyliv_jBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Finduobyliv_jImpl;
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
@Service(name="DmpkUtilityFinduobyliv_j")
public class Finduobyliv_j {
		
	private DmpkUtilityFinduobyliv_jBean bean;
		  
	public void setBean(DmpkUtilityFinduobyliv_jBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkUtilityFinduobyliv_jBean> execute(SchemaBean lSchemaBean, DmpkUtilityFinduobyliv_jBean pBean) throws Exception{
	   final Finduobyliv_jImpl lFinduobyliv_j = new Finduobyliv_jImpl();
	   setBean(pBean);
	   lFinduobyliv_j.setBean(bean);
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
					lFinduobyliv_j.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkUtilityFinduobyliv_jBean> result = new StoreResultBean<DmpkUtilityFinduobyliv_jBean>();
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