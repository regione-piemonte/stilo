/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TAssegnEmailBean;
import it.eng.aurigamailbusiness.converters.TAssegnEmailBeanToTAssegnEmail;
import it.eng.aurigamailbusiness.converters.TAssegnEmailToTAssegnEmailBean;
import it.eng.aurigamailbusiness.database.mail.TAssegnEmail;
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

@Service(name = "DaoTAssegnEmail")
public class DaoTAssegnEmail extends DaoGenericOperations<TAssegnEmailBean> {

	public DaoTAssegnEmail() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituiti tutte le assegnazioni delle mail";

	@Override
	@Operation(name = "save")
	public TAssegnEmailBean save(TAssegnEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAssegnEmail toInsert = (TAssegnEmail) UtilPopulate.populate(bean, TAssegnEmail.class, new TAssegnEmailBeanToTAssegnEmail());
			session.save(toInsert);
			session.flush();
			bean = (TAssegnEmailBean) UtilPopulate.populate(toInsert, TAssegnEmailBean.class, new TAssegnEmailToTAssegnEmailBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TAssegnEmailBean> search(TFilterFetch<TAssegnEmailBean> filter) throws Exception {
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
			TPagingList<TAssegnEmailBean> paginglist = new TPagingList<TAssegnEmailBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TAssegnEmailBean bean = (TAssegnEmailBean) UtilPopulate.populate((TAssegnEmail) obj, TAssegnEmailBean.class,
						new TAssegnEmailToTAssegnEmailBean());
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
	public TAssegnEmailBean update(TAssegnEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAssegnEmail toUpdate = (TAssegnEmail) UtilPopulate.populate(bean, TAssegnEmail.class, new TAssegnEmailBeanToTAssegnEmail());
			session.update(toUpdate);
			session.flush();
			bean = (TAssegnEmailBean) UtilPopulate.populate(toUpdate, TAssegnEmailBean.class, new TAssegnEmailToTAssegnEmailBean());
			transaction.commit();
			return bean;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	@Operation(name = "delete")
	public void delete(TAssegnEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAssegnEmail toDelete = (TAssegnEmail) UtilPopulate.populate(bean, TAssegnEmail.class, new TAssegnEmailBeanToTAssegnEmail());
			session.delete(toDelete);
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
	public void forcedelete(TAssegnEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAssegnEmail toDelete = (TAssegnEmail) UtilPopulate.populate(bean, TAssegnEmail.class, new TAssegnEmailBeanToTAssegnEmail());
			session.delete(toDelete);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TAssegnEmailBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || (filter.getFilter().getIdEmail() == null && filter.getFilter().getIdUtenteDest() == null
				&& filter.getFilter().getIdUtenteMitt() == null && filter.getFilter().getIdFruitoreDest() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TAssegnEmail.class);
		if (filter.getFilter().getIdEmail() != null) {
			lCriteria.add(Restrictions.eq("TEmailMgo.idEmail", filter.getFilter().getIdEmail()));
		}
		if (filter.getFilter().getIdUtenteDest() != null) {
			lCriteria.add(Restrictions.eq("TUtentiModPecByIdUtenteDest.idUtente", filter.getFilter().getIdUtenteDest()));
		}
		if (filter.getFilter().getIdUtenteMitt() != null) {
			lCriteria.add(Restrictions.eq("TUtentiModPecByIdUtenteMitt.idUtente", filter.getFilter().getIdUtenteMitt()));
		}
		if (filter.getFilter().getIdFruitoreDest() != null) {
			lCriteria.add(Restrictions.eq("TAnagFruitoriCaselle.idFruitoreCasella", filter.getFilter().getIdFruitoreDest()));
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