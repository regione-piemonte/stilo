/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import it.eng.core.annotation.Service;
import it.eng.gd.lucenemanager.manager.HibernateUtils;
import it.eng.gd.lucenemanager.manager.entity.TMimetypeFmtDig;

@Service(name = "TMimeTypeFmtDig")
public class DaoTMimeTypeFmtDig {

	public List<TMimetypeFmtDig> getAllRecords() throws Exception {
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(TMimetypeFmtDig.class);
			List<TMimetypeFmtDig> listRes = criteria.list();
			return listRes;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

}
