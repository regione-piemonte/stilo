/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.eng.aurigamailbusiness.database.mail.DmtDizionario;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;

@Service(name = "DaoDmtDizionario")
public class DaoDmtDizionario extends DaoGenericOperations<String> {

	public DaoDmtDizionario() {
	}

	@Operation(name = "get")
	public String get(String dictionaryEntry) throws Exception {
		Session session = null;
		String result = null;
		try {
			session = HibernateUtil.begin();
			String hql = "from DmtDizionario WHERE dictionary_Entry = :dictionaryEntry";
			Query query = session.createQuery(hql);
			query.setParameter("dictionaryEntry", dictionaryEntry);

			List<DmtDizionario> dmtDizionarios = (List<DmtDizionario>) query.list();
			if (dmtDizionarios != null && dmtDizionarios.size() > 0) {
				for (Iterator<DmtDizionario> iterator = dmtDizionarios.iterator(); iterator.hasNext();) {
					DmtDizionario dmtDizionario = iterator.next();
					if (result == null) {
						result = dmtDizionario.getValue();
					} else {
						result += "," + dmtDizionario.getValue();
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return result;
	}

	@Override
	public String save(String bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TPagingList<String> search(TFilterFetch<String> filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(String bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String bean) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void forcedelete(String bean) throws Exception {
		// TODO Auto-generated method stub

	}

}
