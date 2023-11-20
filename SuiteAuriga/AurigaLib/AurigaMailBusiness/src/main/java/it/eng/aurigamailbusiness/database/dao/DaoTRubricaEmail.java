/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.converters.TRubricaEmailBeanToTRubricaEmail;
import it.eng.aurigamailbusiness.converters.TRubricaEmailToTRubricaEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.util.ListUtil;

@Service(name = "DaoTRubricaEmail")
public class DaoTRubricaEmail extends DaoGenericOperations<TRubricaEmailBean> {

	public DaoTRubricaEmail() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le voci della rubrica";

	@Override
	@Operation(name = "save")
	public TRubricaEmailBean save(TRubricaEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			bean = saveInSession(bean, session);
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Operation(name = "get")
	public TRubricaEmailBean get(String idVoceRubrica) throws Exception {
		Session session = null;
		TRubricaEmailBean result = null;
		try {
			session = HibernateUtil.begin();
			TRubricaEmail trub = (TRubricaEmail) session.load(TRubricaEmail.class, idVoceRubrica);
			result = (TRubricaEmailBean) UtilPopulate.populate(trub, TRubricaEmailBean.class, new TRubricaEmailToTRubricaEmailBean());
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public TRubricaEmailBean saveInSession(TRubricaEmailBean bean, Session session) throws Exception {
		TRubricaEmail toInsert = (TRubricaEmail) UtilPopulate.populate(bean, TRubricaEmail.class, new TRubricaEmailBeanToTRubricaEmail());
		session.save(toInsert);
		session.flush();
		bean = (TRubricaEmailBean) UtilPopulate.populate(toInsert, TRubricaEmailBean.class, new TRubricaEmailToTRubricaEmailBean());
		return bean;
	}

	public TPagingList<TRubricaEmailBean> searchInSession(TFilterFetch<TRubricaEmailBean> filter, Session session) throws Exception {
		if (filter == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
		// Conto i record totali
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
		Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
		// Creo l'oggetto paginatore
		TPagingList<TRubricaEmailBean> paginglist = new TPagingList<TRubricaEmailBean>();
		paginglist.setTotalRows(count.intValue());
		paginglist.setStartRow(startRow);
		paginglist.setEndRow(endRow);
		// Recupero i record
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
			TRubricaEmailBean bean = (TRubricaEmailBean) UtilPopulate.populate((TRubricaEmail) obj, TRubricaEmailBean.class,
					new TRubricaEmailToTRubricaEmailBean());
			paginglist.addData(bean);
			// rimuovo l'oggetto TRubricaEmai altrimenti un successivo update fallirà, ho già salvato il nuovo bean nella lista restituita dal metodo
			session.evict(obj);
		}
		return paginglist;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TRubricaEmailBean> search(TFilterFetch<TRubricaEmailBean> filter) throws Exception {
		Session session = null;
		try {
			if (filter == null) {
				throw new AurigaMailBusinessException(errorSearch);
			}
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TRubricaEmailBean> paginglist = new TPagingList<TRubricaEmailBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TRubricaEmailBean bean = (TRubricaEmailBean) UtilPopulate.populate((TRubricaEmail) obj, TRubricaEmailBean.class,
						new TRubricaEmailToTRubricaEmailBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "update")
	public TRubricaEmailBean update(TRubricaEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			bean = updateInSession(bean, session);
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TRubricaEmailBean updateInSession(TRubricaEmailBean bean, Session session) throws Exception {
		TRubricaEmail toUpdate = (TRubricaEmail) UtilPopulate.populate(bean, TRubricaEmail.class, new TRubricaEmailBeanToTRubricaEmail());
		session.update(toUpdate);
		session.flush();
		bean = (TRubricaEmailBean) UtilPopulate.populate(toUpdate, TRubricaEmailBean.class, new TRubricaEmailToTRubricaEmailBean());
		return bean;
	}

	@Override
	@Operation(name = "delete")
	public void delete(TRubricaEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRubricaEmail toInsert = (TRubricaEmail) UtilPopulate.populate(bean, TRubricaEmail.class, new TRubricaEmailBeanToTRubricaEmail());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(TRubricaEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRubricaEmail toInsert = (TRubricaEmail) UtilPopulate.populate(bean, TRubricaEmail.class, new TRubricaEmailBeanToTRubricaEmail());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TRubricaEmailBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null
				|| (filter.getFilter().getIdFruitoreCasella() == null && filter.getFilter().getAccountEmail() == null
						&& filter.getFilter().getIdVoceRubricaEmail() == null && filter.getFilter().getCodAmministrazione() == null
						&& filter.getFilter().getCodAoo() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TRubricaEmail.class);
		if (filter.getFilter().getIdFruitoreCasella() != null) {
			lCriteria.add(Restrictions.eq("TAnagFruitoriCaselle.idFruitoreCasella", filter.getFilter().getIdFruitoreCasella()));
		}
		if (filter.getFilter().getAccountEmail() != null) {
			lCriteria.add(Restrictions.eq("accountEmail", filter.getFilter().getAccountEmail()).ignoreCase());
		}
		if (filter.getFilter().getIdVoceRubricaEmail() != null) {
			lCriteria.add(Restrictions.eq("idVoceRubricaEmail", filter.getFilter().getIdVoceRubricaEmail()));
		}
		if (filter.getFilter().getCodAmministrazione() != null) {
			lCriteria.add(Restrictions.eq("codAmministrazione", filter.getFilter().getCodAmministrazione().toLowerCase()).ignoreCase());
		}
		if (filter.getFilter().getCodAoo() != null) {
			lCriteria.add(Restrictions.eq("codAoo", filter.getFilter().getCodAoo().toLowerCase()).ignoreCase());
		}
		if (ListUtil.isNotEmpty(filter.getOrders())) {
			for (TOrderBy tOrderBy : filter.getOrders()) {
				if (tOrderBy.getType().equals(OrderByType.ASCENDING)) {
					lCriteria.addOrder(Order.asc(tOrderBy.getPropname()));
				} else {
					lCriteria.addOrder(Order.desc(tOrderBy.getPropname()));
				}
			}
		}
		return lCriteria;
	}
}