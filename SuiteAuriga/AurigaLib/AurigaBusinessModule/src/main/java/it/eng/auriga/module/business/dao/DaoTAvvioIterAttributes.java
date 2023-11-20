/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.AvvioIterAttributesBean;
import it.eng.auriga.module.business.entity.TAvvioIterAttributes;
import it.eng.auriga.module.business.entity.TUserPreferences;
import it.eng.auriga.module.business.entity.TUserPreferencesId;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Gestisce l'accesso alla tabella T_AVVIO_ITER_ATTRIBUTES che contiene gli
 * attributi "temporanei" necessari a far partire il processo di avvio di un
 * nuovo iter
 * 
 * @author massimo malvestio
 * 
 */
@Service(name = "DaoTAvvioIterAttributes")
public class DaoTAvvioIterAttributes extends
		DaoGenericOperations<AvvioIterAttributesBean> {

	@Operation(name = "get")
	public AvvioIterAttributesBean get(AurigaLoginBean pAurigaLoginBean,
			AvvioIterAttributesBean bean) throws Exception {

		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);

		return retrieveBean(bean);
	}

	/**
	 * @param bean
	 * @return
	 * @throws Exception
	 * @throws HibernateException
	 */
	private AvvioIterAttributesBean retrieveBean(AvvioIterAttributesBean bean)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();

			TAvvioIterAttributes id = new TAvvioIterAttributes();
			id.setIdProcess(bean.getIdProcess());

			TAvvioIterAttributes retrievedBean = (TAvvioIterAttributes) session
					.get(TAvvioIterAttributes.class, id);
			return (AvvioIterAttributesBean) UtilPopulate.populate(
					retrievedBean, AvvioIterAttributesBean.class);

		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	public AvvioIterAttributesBean save(AvvioIterAttributesBean bean)
			throws Exception {

		AvvioIterAttributesBean retValue = null;

		Session session = null;

		try {

			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TAvvioIterAttributes attributes = (TAvvioIterAttributes) UtilPopulate
					.populate(bean, TAvvioIterAttributes.class);
			session.save(attributes);
			session.flush();

			transaction.commit();

			retValue = (AvvioIterAttributesBean) UtilPopulate.populate(
					attributes, AvvioIterAttributesBean.class);

			return retValue;

		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	public TPagingList<AvvioIterAttributesBean> search(
			TFilterFetch<AvvioIterAttributesBean> filter) throws Exception {

		Session session = null;

		try {

			session = HibernateUtil.begin();

			Criteria criteria = buildCriteriaByFilter(session, filter);

			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter
					.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter
					.getEndRow() : startRow + count.intValue() - 1;

			// Creo l'oggetto paginatore
			TPagingList<AvvioIterAttributesBean> paginglist = new TPagingList<AvvioIterAttributesBean>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);

			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria,
					filter.getStartRow(), filter.getEndRow())) {
				AvvioIterAttributesBean bean = (AvvioIterAttributesBean) UtilPopulate
						.populate((AvvioIterAttributesBean) obj,
								AvvioIterAttributesBean.class);
				paginglist.addData(bean);
			}

			return paginglist;

		} finally {
			HibernateUtil.release(session);
		}

	}

	@Operation(name = "updateProcessId")
	public void updateProcessId(AurigaLoginBean loginBean, Integer processId, Integer tempProcessid) throws Exception {

		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		
		AvvioIterAttributesBean bean = new AvvioIterAttributesBean();
		bean.setIdProcess(BigDecimal.valueOf(tempProcessid));
		AvvioIterAttributesBean oldBean = retrieveBean(bean);
		
		AvvioIterAttributesBean newBean = new AvvioIterAttributesBean();
		newBean.setAttributeName(oldBean.getAttributeName());
		newBean.setAttributeValue(oldBean.getAttributeValue());
		newBean.setIdProcess(BigDecimal.valueOf(processId));
		
		save(newBean);
		
		delete(oldBean);
	}

	@Operation(name = "search")
	public TPagingList<AvvioIterAttributesBean> search(
			AurigaLoginBean pAurigaLoginBean,
			TFilterFetch<AvvioIterAttributesBean> filter) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return search(filter);
	}

	@Operation(name = "save")
	public AvvioIterAttributesBean saveWithLogin(AurigaLoginBean loginBean,
			AvvioIterAttributesBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return save(bean);
	}

	@Operation(name = "update")
	public AvvioIterAttributesBean updateWithLogin(AurigaLoginBean loginBean,
			AvvioIterAttributesBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		return update(bean);
	}

	@Operation(name = "delete")
	public void deleteWithLogin(AurigaLoginBean loginBean,
			AvvioIterAttributesBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);
		delete(bean);
	}

	/**
	 * Crea il criterio di filtraggio per la search in base ai dati presenti
	 * nell'entity passato
	 * 
	 * @param session
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	private Criteria buildCriteriaByFilter(Session session,
			TFilterFetch<AvvioIterAttributesBean> filter) throws Exception {

		Criteria retValue = session
				.createCriteria(AvvioIterAttributesBean.class);

		if (filter != null) {

			AvvioIterAttributesBean attributes = filter.getFilter();

			if (attributes != null) {

				if (attributes.getIdProcess() != null) {
					retValue.add(Restrictions.eq("idProcess",
							attributes.getIdProcess()));
				}

			}

		}

		HibernateUtil.addOrderCriteria(retValue, filter.getOrders());

		return retValue;
	}

	@Override
	public AvvioIterAttributesBean update(AvvioIterAttributesBean bean)
			throws Exception {
		
		throw new RuntimeException("Funzionalità al momento non implementata");
		
	}

	@Override
	public void delete(AvvioIterAttributesBean bean) throws Exception {

		Session session = null;
		Transaction ltransaction = null;
		try {
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();

			session.delete(bean);

			session.flush();
			ltransaction.commit();
		} finally {
			HibernateUtil.release(session);
		}
	}

	@Override
	public void forcedelete(AvvioIterAttributesBean bean) throws Exception {

	}

}
