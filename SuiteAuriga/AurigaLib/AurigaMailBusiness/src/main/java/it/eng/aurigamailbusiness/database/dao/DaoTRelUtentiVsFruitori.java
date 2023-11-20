/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import it.eng.aurigamailbusiness.bean.TRelUtentiVsFruitoriBean;
import it.eng.aurigamailbusiness.converters.TRelUtentiVsFruitoriBeanToTRelUtentiVsFruitori;
import it.eng.aurigamailbusiness.converters.TRelUtentiVsFruitoriToTRelUtentiVsFruitoriBean;
import it.eng.aurigamailbusiness.database.mail.TRelUtentiVsFruitori;
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

@Service(name = "DaoTRelUtentiVsFruitori")
public class DaoTRelUtentiVsFruitori extends DaoGenericOperations<TRelUtentiVsFruitoriBean> {

	public DaoTRelUtentiVsFruitori() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le relazioni fra utenti e fruitori";

	@Override
	@Operation(name = "save")
	public TRelUtentiVsFruitoriBean save(TRelUtentiVsFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelUtentiVsFruitori toInsert = (TRelUtentiVsFruitori) UtilPopulate.populate(bean, TRelUtentiVsFruitori.class,
					new TRelUtentiVsFruitoriBeanToTRelUtentiVsFruitori());
			session.save(toInsert);
			session.flush();
			bean = (TRelUtentiVsFruitoriBean) UtilPopulate.populate(toInsert, TRelUtentiVsFruitoriBean.class,
					new TRelUtentiVsFruitoriToTRelUtentiVsFruitoriBean());
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
	public TPagingList<TRelUtentiVsFruitoriBean> search(TFilterFetch<TRelUtentiVsFruitoriBean> filter) throws Exception {
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
			TPagingList<TRelUtentiVsFruitoriBean> paginglist = new TPagingList<TRelUtentiVsFruitoriBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TRelUtentiVsFruitoriBean bean = (TRelUtentiVsFruitoriBean) UtilPopulate.populate((TRelUtentiVsFruitori) obj, TRelUtentiVsFruitoriBean.class,
						new TRelUtentiVsFruitoriToTRelUtentiVsFruitoriBean());
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
	public TRelUtentiVsFruitoriBean update(TRelUtentiVsFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelUtentiVsFruitori toInsert = (TRelUtentiVsFruitori) UtilPopulate.populate(bean, TRelUtentiVsFruitori.class,
					new TRelUtentiVsFruitoriBeanToTRelUtentiVsFruitori());
			session.update(toInsert);
			session.flush();
			bean = (TRelUtentiVsFruitoriBean) UtilPopulate.populate(toInsert, TRelUtentiVsFruitoriBean.class,
					new TRelUtentiVsFruitoriToTRelUtentiVsFruitoriBean());
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
	public void delete(TRelUtentiVsFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelUtentiVsFruitori toInsert = (TRelUtentiVsFruitori) UtilPopulate.populate(bean, TRelUtentiVsFruitori.class,
					new TRelUtentiVsFruitoriBeanToTRelUtentiVsFruitori());
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
	public void forcedelete(TRelUtentiVsFruitoriBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRelUtentiVsFruitori toInsert = (TRelUtentiVsFruitori) UtilPopulate.populate(bean, TRelUtentiVsFruitori.class,
					new TRelUtentiVsFruitoriBeanToTRelUtentiVsFruitori());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TRelUtentiVsFruitoriBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TRelUtentiVsFruitori.class);
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