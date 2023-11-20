/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.converters.TEmailMgoBeanToTEmailMgo;
import it.eng.aurigamailbusiness.converters.TEmailMgoToTEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.ThEmailMgo;
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

@Service(name = "DaoTEmailMgo")
public class DaoTEmailMgo extends DaoGenericOperations<TEmailMgoBean> {
	
	private static final Logger log = Logger.getLogger(DaoTEmailMgo.class);

	public DaoTEmailMgo() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le mail";

	@Override
	@Operation(name = "save")
	public TEmailMgoBean save(TEmailMgoBean bean) throws Exception {
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

	public TEmailMgoBean saveInSession(TEmailMgoBean bean, Session session) throws Exception {
		try {
		TEmailMgo toInsert = (TEmailMgo) UtilPopulate.populate(bean, TEmailMgo.class, new TEmailMgoBeanToTEmailMgo());
		log.info("TEmailMgoBean: "+bean.toString());
		log.info("TEmailMgo: "+toInsert.toString());
		session.save(toInsert);
		session.flush();
		bean = (TEmailMgoBean) UtilPopulate.populate(toInsert, TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean(session));
		return bean;
		} catch (Exception e) {
			log.error("Exception TEmailMgoBean saveInSession: "+e.getMessage());
			throw e;
		}
	}

	@Operation(name = "get")
	public TEmailMgoBean get(String idEmail) throws Exception {
		Session session = null;
		TEmailMgoBean result = null;
		try {
			session = HibernateUtil.begin();
			result = getInSession(idEmail, session);
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	public TEmailMgoBean getInSession(String idEmail, Session session) throws Exception {
		try {
			log.info("####### recupero mail con id: " + idEmail + " ########");
			TEmailMgo mgo = (TEmailMgo) session.load(TEmailMgo.class, idEmail);
			log.info("mgo: " + mgo);
			return (TEmailMgoBean) UtilPopulate.populate(mgo, TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean(session));
		} catch (ObjectNotFoundException e) {
			//non ho trovato la mail all'interno della T_EMAIL_MGO. Provo a cercarlo all'interno della TH_EMAIL_MGO
			log.info("non ho trovato la mail all'interno della T_EMAIL_MGO");
			ThEmailMgo mgoH = (ThEmailMgo) session.load(ThEmailMgo.class, idEmail);
			log.info("mgoH: " + mgoH);
			return (TEmailMgoBean) UtilPopulate.populate(mgoH, TEmailMgoBean.class, null);
		}		
	}

	public TEmailMgo getTEmailMgoInSession(String idEmail, Session session) throws Exception {
		TEmailMgo mgo = (TEmailMgo) session.load(TEmailMgo.class, idEmail);
		return mgo;
	}

	@Override
	@Operation(name = "update")
	public TEmailMgoBean update(TEmailMgoBean bean) throws Exception {
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

	public TEmailMgoBean updateInSession(TEmailMgoBean bean, Session session) throws Exception {
		TEmailMgo toUpdate = (TEmailMgo) UtilPopulate.populate(bean, TEmailMgo.class, new TEmailMgoBeanToTEmailMgo());
		session.update(toUpdate);
		session.flush();
		bean = (TEmailMgoBean) UtilPopulate.populate(toUpdate, TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean(session));
		return bean;
	}

	public TEmailMgo updateInSession(TEmailMgo bean, Session session) throws Exception {
		session.update(bean);
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TEmailMgoBean> search(TFilterFetch<TEmailMgoBean> filter) throws Exception {
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
			TPagingList<TEmailMgoBean> paginglist = new TPagingList<TEmailMgoBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TEmailMgoBean bean = (TEmailMgoBean) UtilPopulate.populate((TEmailMgo) obj, TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	public TPagingList<TEmailMgoBean> searchInSession(TFilterFetch<TEmailMgoBean> filter, Session session) throws Exception {
		try {
			if (filter == null) {
				throw new AurigaMailBusinessException(errorSearch);
			}
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = filter.getStartRow() != null ? filter.getStartRow() : 0;
			Integer endRow = filter != null && filter.getEndRow() != null ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<TEmailMgoBean> paginglist = new TPagingList<TEmailMgoBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TEmailMgoBean bean = (TEmailMgoBean) UtilPopulate.populate((TEmailMgo) obj, TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean());
				paginglist.addData(bean);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Operation(name = "delete")
	public void delete(TEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TEmailMgo toInsert = (TEmailMgo) UtilPopulate.populate(bean, TEmailMgo.class, new TEmailMgoBeanToTEmailMgo());
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
	public void forcedelete(TEmailMgoBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TEmailMgo toInsert = (TEmailMgo) UtilPopulate.populate(bean, TEmailMgo.class, new TEmailMgoBeanToTEmailMgo());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TEmailMgoBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null || (filter.getFilter().getMessageId() == null && filter.getFilter().getIdEmail() == null
				&& filter.getFilter().getMessageIdTrasporto() == null && filter.getFilter().getIdCasella() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TEmailMgo.class);
		if (filter.getFilter().getIdCasella() != null) {
			lCriteria.add(Restrictions.eq("mailboxAccount.idAccount", filter.getFilter().getIdCasella()));
		}
		if (filter.getFilter().getMessageId() != null) {
			lCriteria.add(Restrictions.eq("messageId", filter.getFilter().getMessageId()));
		}
		if (filter.getFilter().getMessageIdTrasporto() != null) {
			lCriteria.add(Restrictions.eq("messageIdTrasporto", filter.getFilter().getMessageIdTrasporto()));
		}
		if (filter.getFilter().getIdEmail() != null) {
			lCriteria.add(Restrictions.eq("idEmail", filter.getFilter().getIdEmail()));
		}
		if (filter.getFilter().getFlgIo() != null) {
			lCriteria.add(Restrictions.eq("flgIo", filter.getFilter().getFlgIo()));
		}
		if (filter.getFilter().getIdUtenteAssegn() != null) {
			lCriteria.add(Restrictions.eq("TUtentiModPecByIdUtenteAssegn.idUtente", filter.getFilter().getIdUtenteAssegn()));
		}
		if (filter.getFilter().getIdUoAssegn() != null) {
			lCriteria.add(Restrictions.eq("TAnagFruitoriCaselle.idFruitoreCasella", filter.getFilter().getIdUoAssegn()));
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