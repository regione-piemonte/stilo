/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.aurigamailbusiness.converters.TRegProtVsEmailBeanToTRegProtVsEmail;
import it.eng.aurigamailbusiness.converters.TRegProtVsEmailToTRegProtVsEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRegProtVsEmail;
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

@Service(name = "DaoTRegProtVsEmail")
public class DaoTRegProtVsEmail extends DaoGenericOperations<TRegProtVsEmailBean> {

	public static final String NULL_CONVENTION = "FILTER_IS_NULL"; // costante da utilizzare per forzare l'impostazione del filtro di ricerca a NULL

	public DaoTRegProtVsEmail() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le registrazioni di protocollo associate alle mail";

	@Override
	@Operation(name = "save")
	public TRegProtVsEmailBean save(TRegProtVsEmailBean bean) throws Exception {
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

	public TRegProtVsEmailBean saveInSession(TRegProtVsEmailBean bean, Session session) throws Exception {
		TRegProtVsEmail toInsert = (TRegProtVsEmail) UtilPopulate.populate(bean, TRegProtVsEmail.class, new TRegProtVsEmailBeanToTRegProtVsEmail());
		session.save(toInsert);
		session.flush();
		bean = (TRegProtVsEmailBean) UtilPopulate.populate(toInsert, TRegProtVsEmailBean.class, new TRegProtVsEmailToTRegProtVsEmailBean());
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TRegProtVsEmailBean> search(TFilterFetch<TRegProtVsEmailBean> filter) throws Exception {
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
			TPagingList<TRegProtVsEmailBean> paginglist = new TPagingList<TRegProtVsEmailBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TRegProtVsEmailBean bean = (TRegProtVsEmailBean) UtilPopulate.populate((TRegProtVsEmail) obj, TRegProtVsEmailBean.class,
						new TRegProtVsEmailToTRegProtVsEmailBean());
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
	public TRegProtVsEmailBean update(TRegProtVsEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRegProtVsEmail toInsert = (TRegProtVsEmail) UtilPopulate.populate(bean, TRegProtVsEmail.class, new TRegProtVsEmailBeanToTRegProtVsEmail());
			session.update(toInsert);
			session.flush();
			bean = (TRegProtVsEmailBean) UtilPopulate.populate(toInsert, TRegProtVsEmailBean.class, new TRegProtVsEmailToTRegProtVsEmailBean());
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
	public void delete(TRegProtVsEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRegProtVsEmail toInsert = (TRegProtVsEmail) UtilPopulate.populate(bean, TRegProtVsEmail.class, new TRegProtVsEmailBeanToTRegProtVsEmail());
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
	public void forcedelete(TRegProtVsEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRegProtVsEmail toInsert = (TRegProtVsEmail) UtilPopulate.populate(bean, TRegProtVsEmail.class, new TRegProtVsEmailBeanToTRegProtVsEmail());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TRegProtVsEmailBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null
				|| (filter.getFilter().getIdRegProtEmail() == null && filter.getFilter().getSiglaRegistro() == null && filter.getFilter().getAnnoReg() == null
						&& filter.getFilter().getNumReg() == null && filter.getFilter().getCategoriaReg() == null && filter.getFilter().getIdProvReg() == null
						&& filter.getFilter().getIdEmail() == null && filter.getFilter().getTsReg() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TRegProtVsEmail.class);
		if (filter.getFilter().getIdRegProtEmail() != null) {
			lCriteria.add(Restrictions.eq("idRegProtEmail", filter.getFilter().getIdRegProtEmail()));
		}

		if (filter.getFilter().getSiglaRegistro() != null) {
			// imposto la condizione per forzare che la sigla registro sia NULL
			if (filter.getFilter().getSiglaRegistro().equals(NULL_CONVENTION)) {
				lCriteria.add(Restrictions.isNull("siglaRegistro"));
			} else {
				lCriteria.add(Restrictions.eq("siglaRegistro", filter.getFilter().getSiglaRegistro()));
			}
		}
		if (filter.getFilter().getAnnoReg() != null) {
			lCriteria.add(Restrictions.eq("annoReg", filter.getFilter().getAnnoReg()));
		}
		if (filter.getFilter().getNumReg() != null) {
			lCriteria.add(Restrictions.eq("numReg", filter.getFilter().getNumReg()));
		}
		if (filter.getFilter().getCategoriaReg() != null) {
			lCriteria.add(Restrictions.eq("categoriaReg", filter.getFilter().getCategoriaReg()));
		}
		if (filter.getFilter().getTsReg() != null) {
			lCriteria.add(Restrictions.eq("tsReg", filter.getFilter().getTsReg()));
		}
		if (filter.getFilter().getIdProvReg() != null) {
			lCriteria.add(Restrictions.eq("idProvReg", filter.getFilter().getIdProvReg()));
		}
		if (filter.getFilter().getIdEmail() != null) {
			lCriteria.add(Restrictions.eq("TEmailMgo.idEmail", filter.getFilter().getIdEmail()));
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