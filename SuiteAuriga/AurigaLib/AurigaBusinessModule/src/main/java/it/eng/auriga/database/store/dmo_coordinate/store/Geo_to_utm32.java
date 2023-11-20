/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmo_coordinate.bean.DmoCoordinateGeo_to_utm32Bean;
import it.eng.auriga.database.store.dmo_coordinate.store.impl.Geo_to_utm32Impl;
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
@Service(name="DmoCoordinateGeo_to_utm32")
public class Geo_to_utm32 {
		
	private DmoCoordinateGeo_to_utm32Bean bean;
		  
	public void setBean(DmoCoordinateGeo_to_utm32Bean bean){
		this.bean = bean;
	}
	
	@Operation(name="execute")
	public StoreResultBean<DmoCoordinateGeo_to_utm32Bean> execute(SchemaBean lSchemaBean, DmoCoordinateGeo_to_utm32Bean pBean) throws Exception{
	   final Geo_to_utm32Impl lGeo_to_utm32 = new Geo_to_utm32Impl();
	   setBean(pBean);
	   lGeo_to_utm32.setBean(bean);
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
					lGeo_to_utm32.execute(paramConnection);
				}
			});
			StoreResultBean<DmoCoordinateGeo_to_utm32Bean> result = new StoreResultBean<DmoCoordinateGeo_to_utm32Bean>();
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