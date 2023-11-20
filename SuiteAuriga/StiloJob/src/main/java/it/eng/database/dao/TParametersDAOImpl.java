/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.eng.database.utility.HibernateUtil;
import it.eng.entity.TParameters;
import it.eng.utils.EntityUtils;

public class TParametersDAOImpl {

	private static final Logger log = Logger.getLogger(TParametersDAOImpl.class);
	private static final String CLASS_NAME = TParametersDAOImpl.class.getName();
	
	
	public TParameters getTParameters(String JOB_CLASS_NAME,TParameters obj)  {
		log.debug("Effettuo la query sulla tabella " + EntityUtils.getEntityName(TParameters.class));
		
		TParameters result = null;
		Session session = null;
		try {
			
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			
			Criteria criteria = session.createCriteria(TParameters.class)
					.add(Restrictions.eq("parKey", obj.getParKey()));
			
			result = (TParameters) criteria.uniqueResult();
		} catch (Exception e) {
			log.error("Errore in getTParameters(): ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
	
}
