/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.module.business.entity.TParameters;
import it.eng.core.business.HibernateUtil;

public class DaoTParametri {

	private static final Logger log = Logger.getLogger(DaoTParametri.class);

	public String getValore(String parKey) throws Exception {
		String result = null;
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Criteria criteria = session.createCriteria(TParameters.class);

			criteria.add(Restrictions.eq("parKey", parKey));
			criteria.setProjection(Projections.projectionList().add(Projections.property("strValue")));

			result = (String) criteria.uniqueResult();

		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public TParameters getParametro(String parKey) throws Exception {
		TParameters result = null;
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Criteria criteria = session.createCriteria(TParameters.class);

			criteria.add(Restrictions.eq("parKey", parKey));

			List<TParameters> listaParam = criteria.list();

			if (listaParam.size() == 1) {
				result = listaParam.get(0);
				log.debug(result);
			}

		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

}
