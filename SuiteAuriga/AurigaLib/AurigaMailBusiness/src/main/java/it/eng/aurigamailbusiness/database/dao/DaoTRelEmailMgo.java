/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.converters.TRelEmailMgoBeanToTRelEmailMgo;
import it.eng.aurigamailbusiness.converters.TRelEmailMgoToTRelEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TRelEmailMgo;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.util.ListUtil;

@Service(name = "DaoTRelEmailMgo")
public class DaoTRelEmailMgo extends DaoGenericOperations<TRelEmailMgoBean> {

	public DaoTRelEmailMgo() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le relazioni fra le email";

	@Override
	@Operation(name = "save")
	public TRelEmailMgoBean save(TRelEmailMgoBean bean) throws Exception {
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

	public TRelEmailMgoBean saveInSession(TRelEmailMgoBean bean, Session session) throws Exception {
		TRelEmailMgo toInsert = (TRelEmailMgo) UtilPopulate.populate(bean, TRelEmailMgo.class, new TRelEmailMgoBeanToTRelEmailMgo());
		toInsert.setIdRelEmail(KeyGenerator.gen());
		session.save(toInsert);
		session.flush();
		bean = (TRelEmailMgoBean) UtilPopulate.populate(toInsert, TRelEmailMgoBean.class, new TRelEmailMgoToTRelEmailMgoBean());
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TRelEmailMgoBean> search(TFilterFetch<TRelEmailMgoBean> filter) throws Exception {
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
			TPagingList<TRelEmailMgoBean> paginglist = new TPagingList<TRelEmailMgoBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TRelEmailMgoBean bean = (TRelEmailMgoBean) UtilPopulate.populate((TRelEmailMgo) obj, TRelEmailMgoBean.class,
						new TRelEmailMgoToTRelEmailMgoBean());
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
	public TRelEmailMgoBean update(TRelEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelEmailMgo toInsert = (TRelEmailMgo) UtilPopulate.populate(bean, TRelEmailMgo.class, new TRelEmailMgoBeanToTRelEmailMgo());
			session.update(toInsert);
			session.flush();
			bean = (TRelEmailMgoBean) UtilPopulate.populate(toInsert, TRelEmailMgoBean.class, new TRelEmailMgoToTRelEmailMgoBean());
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
	public void delete(TRelEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelEmailMgo toInsert = (TRelEmailMgo) UtilPopulate.populate(bean, TRelEmailMgo.class, new TRelEmailMgoBeanToTRelEmailMgo());
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
	public void forcedelete(TRelEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelEmailMgo toInsert = (TRelEmailMgo) UtilPopulate.populate(bean, TRelEmailMgo.class, new TRelEmailMgoBeanToTRelEmailMgo());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TRelEmailMgoBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || (filter.getFilter().getIdEmail1() == null && filter.getFilter().getIdEmail2() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TRelEmailMgo.class);
		if (filter.getFilter().getIdEmail1() != null) {
			lCriteria.add(Restrictions.eq("TEmailMgoByIdEmail1.idEmail", filter.getFilter().getIdEmail1()));
		}
		if (filter.getFilter().getIdEmail2() != null) {
			lCriteria.add(Restrictions.eq("TEmailMgoByIdEmail2.idEmail", filter.getFilter().getIdEmail2()));
		}
		if (filter.getFilter().getIdRecDizCategRel() != null) {
			lCriteria.add(Restrictions.eq("TValDizionarioByIdRecDizCategRel.idRecDiz", filter.getFilter().getIdRecDizCategRel()));
		}
		if (filter.getFilter().getIdRecDizRuolo1Vs2() != null) {
			lCriteria.add(Restrictions.eq("TValDizionarioByIdRecDizRuolo1Vs2.idRecDiz", filter.getFilter().getIdRecDizRuolo1Vs2()));
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