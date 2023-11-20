/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DmtLottiDocElabMassiveBean;
import it.eng.auriga.module.business.entity.DmtLottiDocElabMassive;
import it.eng.core.annotation.Operation;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

public class DaoDmtLottiDocElaborazioniMassive extends DaoGenericOperations<DmtLottiDocElabMassiveBean> {

	private static final String ERROR_SEARCH = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le oeprazioni massive";

	@Override
	public DmtLottiDocElabMassiveBean save(DmtLottiDocElabMassiveBean bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			saveInSession(bean, session);
			ltransaction.commit();
			return bean;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public DmtLottiDocElabMassiveBean saveInSession(DmtLottiDocElabMassiveBean bean, Session session) throws Exception {
		if (bean != null) {
			DmtLottiDocElabMassive lDmtLottiDocElabMassive = (DmtLottiDocElabMassive) UtilPopulate.populate(bean, DmtLottiDocElabMassive.class, null);
			session.save(lDmtLottiDocElabMassive);
			session.flush();
		}
		return bean;
	}

	@Operation(name = "save")
	public DmtLottiDocElabMassiveBean saveWithLogin(AurigaLoginBean pAurigaLoginBean, DmtLottiDocElabMassiveBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.save(bean);
	}

	@Override
	public TPagingList<DmtLottiDocElabMassiveBean> search(TFilterFetch<DmtLottiDocElabMassiveBean> filter) throws Exception {
		throw new UnsupportedOperationException("Metodo non implementato");
	}

	@Operation(name = "search")
	public TPagingList<DmtLottiDocElabMassiveBean> searchWithLogin(AurigaLoginBean pAurigaLoginBean, TFilterFetch<DmtLottiDocElabMassiveBean> filter)
			throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.search(filter);
	}

	@Override
	public DmtLottiDocElabMassiveBean update(DmtLottiDocElabMassiveBean bean) throws Exception {
		throw new UnsupportedOperationException("Metodo non implementato");
	}

	@Operation(name = "update")
	public DmtLottiDocElabMassiveBean updateWithLogin(AurigaLoginBean pAurigaLoginBean, DmtLottiDocElabMassiveBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return this.update(bean);
	}

	@Override
	public void delete(DmtLottiDocElabMassiveBean bean) throws Exception {
		throw new UnsupportedOperationException("Metodo non implementato");
	}

	@Operation(name = "delete")
	public void deleteWithLogin(AurigaLoginBean pAurigaLoginBean, DmtLottiDocElabMassiveBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		this.delete(bean);
	}

	@Override
	public void forcedelete(DmtLottiDocElabMassiveBean bean) throws Exception {
		throw new UnsupportedOperationException("Metodo non implementato");
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<DmtLottiDocElabMassiveBean> filter) throws Exception {

		if (filter == null || filter.getFilter() == null
				|| (filter.getFilter().getIdJob() == null && filter.getFilter().getIdLotto() == null && filter.getFilter().getUseridApplicazione() == null)) {
			throw new Exception(ERROR_SEARCH);
		}
		Criteria criteria = session.createCriteria(DmtLottiDocElabMassiveBean.class);
		if (filter.getFilter().getIdLotto() != null) {
			criteria.add(Restrictions.eq("idJob", filter.getFilter().getIdJob()));
		}
		if (StringUtils.isNotBlank(filter.getFilter().getIdLotto())) {
			criteria.add(Restrictions.eq("idLotto", filter.getFilter().getIdLotto()));
		}
		if (StringUtils.isNotBlank(filter.getFilter().getUseridApplicazione())) {
			criteria.add(Restrictions.eq("useridApplicazione", filter.getFilter().getUseridApplicazione()));
		}

		HibernateUtil.addOrderCriteria(criteria, filter.getOrders());
		return criteria;
	}

}
