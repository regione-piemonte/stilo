/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import it.eng.aurigamailbusiness.bean.TCommentiEmailBean;
import it.eng.aurigamailbusiness.converters.TCommentiEmailBeanToTCommentiEmail;
import it.eng.aurigamailbusiness.converters.TCommentiEmailToTCommentiEmailBean;
import it.eng.aurigamailbusiness.database.mail.TCommentiEmail;
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

@Service(name = "DaoTCommentiEmail")
public class DaoTCommentiEmail extends DaoGenericOperations<TCommentiEmailBean> {

	public DaoTCommentiEmail() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le note associate alle mail";

	@Override
	@Operation(name = "save")
	public TCommentiEmailBean save(TCommentiEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCommentiEmail toInsert = (TCommentiEmail) UtilPopulate.populate(bean, TCommentiEmail.class, new TCommentiEmailBeanToTCommentiEmail());
			session.save(toInsert);
			session.flush();
			bean = (TCommentiEmailBean) UtilPopulate.populate(toInsert, TCommentiEmailBean.class, new TCommentiEmailToTCommentiEmailBean());
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
	public TPagingList<TCommentiEmailBean> search(TFilterFetch<TCommentiEmailBean> filter) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TCommentiEmailBean> paginglist = new TPagingList<TCommentiEmailBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TCommentiEmailBean bean = (TCommentiEmailBean) UtilPopulate.populate((TCommentiEmail) obj, TCommentiEmailBean.class,
						new TCommentiEmailToTCommentiEmailBean());
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
	public TCommentiEmailBean update(TCommentiEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCommentiEmail toInsert = (TCommentiEmail) UtilPopulate.populate(bean, TCommentiEmail.class, new TCommentiEmailBeanToTCommentiEmail());
			session.update(toInsert);
			session.flush();
			bean = (TCommentiEmailBean) UtilPopulate.populate(toInsert, TCommentiEmailBean.class, new TCommentiEmailToTCommentiEmailBean());
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
	public void delete(TCommentiEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCommentiEmail toInsert = (TCommentiEmail) UtilPopulate.populate(bean, TCommentiEmail.class, new TCommentiEmailBeanToTCommentiEmail());
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
	public void forcedelete(TCommentiEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TCommentiEmail toInsert = (TCommentiEmail) UtilPopulate.populate(bean, TCommentiEmail.class, new TCommentiEmailBeanToTCommentiEmail());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TCommentiEmailBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TCommentiEmail.class);
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