/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.module.business.dao.beans.DmvConsumerWSBean;
import it.eng.auriga.module.business.dao.exception.DmvConsumerEmptyCredentialException;
import it.eng.auriga.module.business.dao.exception.DmvConsumerInvalidCredentialException;
import it.eng.auriga.module.business.entity.DmvConsumerWs;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;

@Service(name = "DaoDMVConsumerWS")
public class DaoDMVConsumerWS {

	private static final Logger logger = Logger.getLogger(DaoDMVConsumerWS.class);

	public String getTokenDmvConsumerWs(SchemaBean pSchemaBean, String userid, String password) throws Exception {

		logger.debug("Userid DmvConsumerWs: " + userid);

		if (StringUtils.isBlank(userid)) {
			throw new DmvConsumerEmptyCredentialException("Userid non valorizzato");
		}

		Session session = null;
		try {

			session = HibernateUtil.openSession(pSchemaBean.getSchema());
			Criteria criteria = session.createCriteria(DmvConsumerWs.class);
			criteria.setProjection(Projections.projectionList().add(Projections.property("id.connToken"), "connToken"));

			criteria.add(Restrictions.eq("id.userid", userid));
			criteria.add(Restrictions.eq("id.password", password));

			String connToken = (String) criteria.uniqueResult();
			if (connToken == null) {
				throw new DmvConsumerInvalidCredentialException("Nessun conumer associato alle credenziali fornite");
			}

			return connToken;

		} finally {
			HibernateUtil.release(session);
		}

	}

	public DmvConsumerWSBean getDmvConsumerWs(SchemaBean pSchemaBean, String userid, String password) throws Exception {

		logger.debug("Userid DmvConsumerWs: " + userid);

		if (StringUtils.isBlank(userid)) {
			throw new DmvConsumerEmptyCredentialException("Userid non valorizzato");
		}

		Session session = null;
		try {

			session = HibernateUtil.openSession(pSchemaBean.getSchema());
			Criteria criteria = session.createCriteria(DmvConsumerWs.class);
			criteria.setProjection(Projections.projectionList().add(Projections.property("id.idSpAoo"), "idSpAoo")
					.add(Projections.property("id.userid"), "userid").add(Projections.property("id.password"), "password")
					.add(Projections.property("id.descrizione"), "descrizione").add(Projections.property("id.ciApplicazione"), "ciApplicazione")
					.add(Projections.property("id.ciIstanzaApplicazione"), "ciIstanzaApplicazione").add(Projections.property("id.cifratura"), "cifratura")
					.add(Projections.property("id.chiaveCifratura"), "chiaveCifratura").add(Projections.property("id.connToken"), "connToken")
					.add(Projections.property("id.idUser"), "idUser").add(Projections.property("id.idUtenteMail"), "idUtenteMail"));

			criteria.add(Restrictions.eq("id.userid", userid));
			criteria.add(Restrictions.eq("id.password", password));

			DmvConsumerWSBean dmvConsumerWSBean = (DmvConsumerWSBean) criteria.setResultTransformer(Transformers.aliasToBean(DmvConsumerWSBean.class))
					.uniqueResult();
			if (dmvConsumerWSBean == null) {
				throw new DmvConsumerInvalidCredentialException("Nessun conumer associato alle credenziali fornite");
			}

			return dmvConsumerWSBean;

		} finally {
			HibernateUtil.release(session);
		}

	}

}
