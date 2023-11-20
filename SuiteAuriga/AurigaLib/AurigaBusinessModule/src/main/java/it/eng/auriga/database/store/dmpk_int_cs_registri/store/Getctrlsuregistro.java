/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_cs_registri.bean.DmpkIntCsRegistriGetctrlsuregistroBean;
import it.eng.auriga.database.store.dmpk_int_cs_registri.store.impl.GetctrlsuregistroImpl;
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
@Service(name="DmpkIntCsRegistriGetctrlsuregistro")
public class Getctrlsuregistro {
		
	private DmpkIntCsRegistriGetctrlsuregistroBean bean;
		  
	public void setBean(DmpkIntCsRegistriGetctrlsuregistroBean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmpkIntCsRegistriGetctrlsuregistroBean> execute(SchemaBean lSchemaBean, DmpkIntCsRegistriGetctrlsuregistroBean pBean) throws Exception{
	   final GetctrlsuregistroImpl lGetctrlsuregistro = new GetctrlsuregistroImpl();
	   setBean(pBean);
	   lGetctrlsuregistro.setBean(bean);
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
					lGetctrlsuregistro.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkIntCsRegistriGetctrlsuregistroBean> result = new StoreResultBean<DmpkIntCsRegistriGetctrlsuregistroBean>();
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