package it.eng.dm.sira.dao;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.dm.sira.entity.DmtDettAttivitaWf;
import it.eng.dm.sira.entity.DmtDettAttivitaWfId;
import it.eng.dm.sira.service.bean.AclBean;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.dm.sira.service.util.ConnectionUtil;
import it.eng.spring.utility.SpringAppContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoDmtDettAttivitaWF {

	public DmtDettAttivitaWf get(DmtDettAttivitaWfId id) throws Exception {
		SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			DmtDettAttivitaWf att = (DmtDettAttivitaWf) session.get(DmtDettAttivitaWf.class, id);
			session.flush();
			return att;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

//	public DmtDettAttivitaWf save(DmtDettAttivitaWf bean) throws Exception {
//		Connection connection = ConnectionUtil.getConnection();
//
//		/**
//		 * Insert into AURI_OWNER_1.DMT_DETT_ATTIVITA_WF (PROV_CI_TY_FLUSSO_WF, PROCESS_NAME, ACL, DETT_X_ESITO, DEST_TRASMISSIONE,
//		 * NRO_ORDINE_VIS, FLG_PROCESS_END) Values ('CALENDARIO_VENATORIO', 'Pre-istruttoria', AURI_MASTER.DMTO_ACL_ATTIVITA(
//		 * "AURI_MASTER".DMO_ACE_ATTIVITA('RP','RESPONSABILE',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),
//		 * "AURI_MASTER".DMO_ACE_ATTIVITA('RP','REFERENTE TECNICO',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,NULL,NULL),
//		 * "AURI_MASTER".DMO_ACE_ATTIVITA('RP','REFERENTE AMMINISTRATIVO',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL),
//		 * "AURI_MASTER".DMO_ACE_ATTIVITA('UT','1034',NULL,NULL,NULL,NULL,NULL,NULL,4,NULL,NULL,1,1),
//		 * "AURI_MASTER".DMO_ACE_ATTIVITA('UT','109',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,NULL,1,1)), NULL, NULL, 0, 1);
//		 */
//		Statement statement = null;
//		String insertTableSQL = null;
//		
//		String permessiStr = "";
//		for (AclBean permesso : bean.dammiPermessi()) {
//			permessiStr += "\"AURI_MASTER\".DMO_ACE_ATTIVITA('" + permesso.getFlgTipo() + "','" + permesso.getCiInAnagProv() + "','"
//					+ permesso.getFlgInclSottouo() + "',NULL,NULL,NULL,NULL,NULL,'" + permesso.getNroPosInAcl() + "',NULL,NULL,'"
//					+ permesso.getFlgVisibilita() + "','" + permesso.getFlgEsecuzione() + "'),";
//		}
//		
//			try {
//				statement = connection.createStatement();
//				System.out.println(insertTableSQL);
//				insertTableSQL = "Insert into AURI_OWNER_1.DMT_DETT_ATTIVITA_WF (PROV_CI_TY_FLUSSO_WF, PROCESS_NAME, ACL, DETT_X_ESITO, DEST_TRASMISSIONE,NRO_ORDINE_VIS, FLG_PROCESS_END) "+
//				"Values ('CALENDARIO_VENATORIO', 'Pre-istruttoria', AURI_MASTER.DMTO_ACL_ATTIVITA(";
//								 "AURI_MASTER".DMO_ACE_ATTIVITA('RP','RESPONSABILE',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),
//								 "AURI_MASTER".DMO_ACE_ATTIVITA('RP','REFERENTE TECNICO',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,NULL,NULL,NULL),
//								 "AURI_MASTER".DMO_ACE_ATTIVITA('RP','REFERENTE AMMINISTRATIVO',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL),
//								 "AURI_MASTER".DMO_ACE_ATTIVITA('UT','1034',NULL,NULL,NULL,NULL,NULL,NULL,4,NULL,NULL,1,1),
//								 "AURI_MASTER".DMO_ACE_ATTIVITA('UT','109',NULL,NULL,NULL,NULL,NULL,NULL,5,NULL,NULL,1,1)), NULL, NULL, 0, 1);"
//				// execute insert SQL stetement
//				statement.executeQuery(insertTableSQL);
//			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//			} finally {
//				if (statement != null) {
//					statement.close();
//				}
//				connection.commit();
//			}
//		}
//		return bean;
//
//	}

	private void aggiornaPermessi(DmtDettAttivitaWf bean) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Statement statement = null;
		String selectTableSQL = null;
		for (AclBean permesso : bean.dammiPermessi()) {
			selectTableSQL = "insert into(select a.acl from DMT_DETT_ATTIVITA_WF a where a.prov_ci_ty_flusso_wf='"
					+ bean.getId().getProvCiTyFlussoWf() + "' and nro_ordine_vis='" + bean.getId().getNroOrdineVis() + "') nt values('"
					+ permesso.getFlgTipo() + "','" + permesso.getCiInAnagProv() + "','" + permesso.getFlgInclSottouo() + "'," + null + ","
					+ null + "," + null + "," + null + "," + null + ",'" + permesso.getNroPosInAcl() + "'," + null + "," + null + ",'"
					+ permesso.getFlgVisibilita() + "','" + permesso.getFlgEsecuzione() + "')";
			try {
				statement = connection.createStatement();
				System.out.println(selectTableSQL);
				// execute insert SQL stetement
				statement.executeQuery(selectTableSQL);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (statement != null) {
					statement.close();
				}
				connection.commit();
			}
		}
		connection.close();
	}

	public TPagingList<DmtDettAttivitaWf> search(TFilterFetch<DmtDettAttivitaWf> filter) throws Exception {
		SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// Creo i criteri di ricerca
			Criteria criteria = this.buildHibernateCriteriaByFilter(session, filter);
			// Conto i record totali
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			Integer startRow = (filter != null && filter.getStartRow() != null) ? filter.getStartRow() : 0;
			Integer endRow = (filter != null && filter.getEndRow() != null) ? filter.getEndRow() : startRow + count.intValue() - 1;
			// Creo l'oggetto paginatore
			TPagingList<DmtDettAttivitaWf> paginglist = new TPagingList<DmtDettAttivitaWf>();
			paginglist.setTotalRows(count.intValue());
			paginglist.setStartRow(startRow);
			paginglist.setEndRow(endRow);
			// Recupero i record
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			for (Object obj : HibernateUtil.pagingByCriteria(criteria, filter.getStartRow(), filter.getEndRow())) {
				paginglist.addData((DmtDettAttivitaWf) obj);
			}
			return paginglist;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}

	private Criteria buildHibernateCriteriaByFilter(Session session, TFilterFetch<DmtDettAttivitaWf> filter) {
		Criteria lCriteria = session.createCriteria(DmtDettAttivitaWf.class);
		if (filter.getFilter().getActivityName() != null) {
			lCriteria.add(Restrictions.eq("activityName", filter.getFilter().getActivityName()).ignoreCase());
		}
		if (filter.getFilter().getProcessName() != null) {
			lCriteria.add(Restrictions.eq("processName", filter.getFilter().getProcessName()).ignoreCase());
		}
		if (filter.getFilter().getId().getProvCiTyFlussoWf() != null) {
			lCriteria.add(Restrictions.eq("id.provCiTyFlussoWf", filter.getFilter().getId().getProvCiTyFlussoWf()).ignoreCase());
		}
		return lCriteria;
	}

}