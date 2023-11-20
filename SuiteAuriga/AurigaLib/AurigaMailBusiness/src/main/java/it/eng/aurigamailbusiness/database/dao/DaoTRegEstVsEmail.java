/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TRegEstVsEmailBean;
import it.eng.aurigamailbusiness.converters.TRegEstVsEmailBeanToTRegEstVsEmail;
import it.eng.aurigamailbusiness.converters.TRegEstVsEmailToTRegEstVsEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRegEstVsEmail;
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

@Service(name = "DaoTRegEstVsEmail")
public class DaoTRegEstVsEmail extends DaoGenericOperations<TRegEstVsEmailBean> {

	public static final String NULL_CONVENTION = "FILTER_IS_NULL"; // costante da utilizzare per forzare l'impostazione del filtro di ricerca a NULL

	public DaoTRegEstVsEmail() {
	}

	private static String errorSearch = "Nessun filtro impostato. Interrotta la ricerca altrimenti sarebbero restituite tutte le registrazioni di protocollo assegnate dalle amministrazioni riceventi agli enti";

	@Override
	@Operation(name = "save")
	public TRegEstVsEmailBean save(TRegEstVsEmailBean bean) throws Exception {
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

	public TRegEstVsEmailBean saveInSession(TRegEstVsEmailBean bean, Session session) throws Exception {
		TRegEstVsEmail toInsert = (TRegEstVsEmail) UtilPopulate.populate(bean, TRegEstVsEmail.class, new TRegEstVsEmailBeanToTRegEstVsEmail());
		session.save(toInsert);
		session.flush();
		bean = (TRegEstVsEmailBean) UtilPopulate.populate(toInsert, TRegEstVsEmailBean.class, new TRegEstVsEmailToTRegEstVsEmailBean());
		return bean;
	}

	@Override
	@Operation(name = "search")
	public TPagingList<TRegEstVsEmailBean> search(TFilterFetch<TRegEstVsEmailBean> filter) throws Exception {
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
			TPagingList<TRegEstVsEmailBean> paginglist = new TPagingList<TRegEstVsEmailBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, startRow, endRow)) {
				TRegEstVsEmailBean bean = (TRegEstVsEmailBean) UtilPopulate.populate((TRegEstVsEmail) obj, TRegEstVsEmailBean.class,
						new TRegEstVsEmailToTRegEstVsEmailBean());
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
	public TRegEstVsEmailBean update(TRegEstVsEmailBean bean) throws Exception {
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

	public TRegEstVsEmailBean updateInSession(TRegEstVsEmailBean bean, Session session) throws Exception {

		TRegEstVsEmail toInsert = (TRegEstVsEmail) UtilPopulate.populate(bean, TRegEstVsEmail.class, new TRegEstVsEmailBeanToTRegEstVsEmail());
		session.update(toInsert);
		session.flush();
		bean = (TRegEstVsEmailBean) UtilPopulate.populate(toInsert, TRegEstVsEmailBean.class, new TRegEstVsEmailToTRegEstVsEmailBean());
		return bean;
	}

	@Override
	@Operation(name = "delete")
	public void delete(TRegEstVsEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRegEstVsEmail toInsert = (TRegEstVsEmail) UtilPopulate.populate(bean, TRegEstVsEmail.class, new TRegEstVsEmailBeanToTRegEstVsEmail());
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
	public void forcedelete(TRegEstVsEmailBean bean) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TRegEstVsEmail toInsert = (TRegEstVsEmail) UtilPopulate.populate(bean, TRegEstVsEmail.class, new TRegEstVsEmailBeanToTRegEstVsEmail());
			session.delete(toInsert);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<TRegEstVsEmailBean> filter) throws AurigaMailBusinessException {
		if (filter == null || filter.getFilter() == null
				|| (filter.getFilter().getIdDestinatarioEmail() == null && filter.getFilter().getAnnoReg() == null && filter.getFilter().getNumReg() == null
						&& filter.getFilter().getCodAmministrazione() == null && filter.getFilter().getCodAoo() == null
						&& filter.getFilter().getSiglaRegistro() == null && filter.getFilter().getTsReg() == null)) {
			throw new AurigaMailBusinessException(errorSearch);
		}
		Criteria lCriteria = session.createCriteria(TRegEstVsEmail.class);
		if (filter.getFilter().getAnnoReg() != null) {
			lCriteria.add(Restrictions.eq("annoReg", filter.getFilter().getAnnoReg()));
		}
		if (filter.getFilter().getNumReg() != null) {
			lCriteria.add(Restrictions.eq("numReg", filter.getFilter().getNumReg()));
		}
		if (filter.getFilter().getCodAmministrazione() != null) {
			lCriteria.add(Restrictions.eq("codAmministrazione", filter.getFilter().getCodAmministrazione()));
		}
		if (filter.getFilter().getCodAoo() != null) {
			lCriteria.add(Restrictions.eq("codAoo", filter.getFilter().getCodAoo()));
		}
		if (filter.getFilter().getSiglaRegistro() != null) {
			// imposto la condizione per forzare che la sigla registro sia NULL
			if (filter.getFilter().getSiglaRegistro().equals(NULL_CONVENTION)) {
				lCriteria.add(Restrictions.isNull("siglaRegistro"));
			} else {
				lCriteria.add(Restrictions.eq("siglaRegistro", filter.getFilter().getSiglaRegistro()));
			}
		}
		if (filter.getFilter().getTsReg() != null) {
			lCriteria.add(Restrictions.eq("tsReg", filter.getFilter().getTsReg()));
		}
		if (filter.getFilter().getIdDestinatarioEmail() != null) {
			lCriteria.add(Restrictions.eq("TDestinatariEmailMgo.idDestinatarioEmail", filter.getFilter().getIdDestinatarioEmail()));
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