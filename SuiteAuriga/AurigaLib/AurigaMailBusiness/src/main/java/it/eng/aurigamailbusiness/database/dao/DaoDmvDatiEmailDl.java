/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Query;
import org.hibernate.Session;

import it.eng.aurigamailbusiness.bean.DmvDatiEmailDlBean;
import it.eng.aurigamailbusiness.database.mail.DmvDatiEmailXDl;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;

@Service(name = "DaoDmvDatiEmailDl")
public class DaoDmvDatiEmailDl extends DaoGenericOperations<DmvDatiEmailDlBean> {

	public DaoDmvDatiEmailDl() {
	}

	@Operation(name = "get")
	public DmvDatiEmailDlBean get(String idEmail) throws Exception {
		Session session = null;
		DmvDatiEmailDlBean result = null;
		try {
			session = HibernateUtil.begin();
			String hql = "from DmvDatiEmailXDl where idEmail= :idEmail";
			Query query = session.createQuery(hql);
			query.setParameter("idEmail", idEmail);

			DmvDatiEmailXDl dmvDatiEmailXDl = (DmvDatiEmailXDl) query.uniqueResult();
			if (dmvDatiEmailXDl != null) {
				result = new DmvDatiEmailDlBean();
				result.setIdEmail(dmvDatiEmailXDl.getIdEmail());
				result.setMessageId(dmvDatiEmailXDl.getMessageId());
				result.setOggettoEmail(dmvDatiEmailXDl.getOggettoEmail());
				result.setCasellaRicezione(dmvDatiEmailXDl.getCasellaRicezione());
				result.setMittente(dmvDatiEmailXDl.getMittente());
				result.setTestoEmail(dmvDatiEmailXDl.getTestoEmail());
				result.setNomiAllegatiEmail(dmvDatiEmailXDl.getNomiAllegatiEmail());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	@Override
	@Operation(name = "save")
	public DmvDatiEmailDlBean save(DmvDatiEmailDlBean bean) throws Exception {
		return null;

	}

	@Override
	@Operation(name = "search")
	public TPagingList<DmvDatiEmailDlBean> search(TFilterFetch<DmvDatiEmailDlBean> filter) throws Exception {
		return null;
	}

	public TPagingList<DmvDatiEmailDlBean> searchInSession(TFilterFetch<DmvDatiEmailDlBean> filter, Session session)
			throws Exception {
		return null;
	}

	@Override
	@Operation(name = "update")
	public DmvDatiEmailDlBean update(DmvDatiEmailDlBean bean) throws Exception {
		return null;
	}

	@Override
	@Operation(name = "delete")
	public void delete(DmvDatiEmailDlBean bean) throws Exception {
	}

	@Override
	@Operation(name = "forcedelete")
	public void forcedelete(DmvDatiEmailDlBean bean) throws Exception {
	}
}
