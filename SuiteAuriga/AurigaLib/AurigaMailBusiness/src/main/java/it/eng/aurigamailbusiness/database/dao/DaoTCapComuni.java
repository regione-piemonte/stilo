/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import it.eng.aurigamailbusiness.bean.TCapComuniBean;
import it.eng.aurigamailbusiness.converters.TCapComuniBeanToTCapComuni;
import it.eng.aurigamailbusiness.converters.TCapComuniToTCapComuniBean;
import it.eng.aurigamailbusiness.database.mail.TCapComuni;
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

@Service(name = "DaoTCapComuni")
public class DaoTCapComuni extends DaoGenericOperations<TCapComuniBean> {

	public DaoTCapComuni() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituiti tutti i CAP dei comuni";

	@Override
	@Operation(name = "save")
	public TCapComuniBean save(TCapComuniBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCapComuni toInsert = (TCapComuni) UtilPopulate.populate(bean, TCapComuni.class, new TCapComuniBeanToTCapComuni());
			session.save(toInsert);
			session.flush();
			bean = (TCapComuniBean) UtilPopulate.populate(toInsert, TCapComuniBean.class, new TCapComuniToTCapComuniBean());
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
	public TPagingList<TCapComuniBean> search(TFilterFetch<TCapComuniBean> filter) throws Exception {
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
			TPagingList<TCapComuniBean> paginglist = new TPagingList<TCapComuniBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TCapComuniBean bean = (TCapComuniBean) UtilPopulate.populate((TCapComuni) obj, TCapComuniBean.class, new TCapComuniToTCapComuniBean());
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
	public TCapComuniBean update(TCapComuniBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCapComuni toInsert = (TCapComuni) UtilPopulate.populate(bean, TCapComuni.class, new TCapComuniBeanToTCapComuni());
			session.update(toInsert);
			session.flush();
			bean = (TCapComuniBean) UtilPopulate.populate(toInsert, TCapComuniBean.class, new TCapComuniToTCapComuniBean());
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
	public void delete(TCapComuniBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCapComuni toInsert = (TCapComuni) UtilPopulate.populate(bean, TCapComuni.class, new TCapComuniBeanToTCapComuni());
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
	public void forcedelete(TCapComuniBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCapComuni toInsert = (TCapComuni) UtilPopulate.populate(bean, TCapComuni.class, new TCapComuniBeanToTCapComuni());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TCapComuniBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TCapComuni.class);
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