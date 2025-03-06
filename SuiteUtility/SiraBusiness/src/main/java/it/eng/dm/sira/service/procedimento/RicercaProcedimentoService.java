package it.eng.dm.sira.service.procedimento;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.dm.sira.dao.DaoTRelProcessTipoAreaEnte;
import it.eng.dm.sira.entity.DmtProcessTypes;
import it.eng.dm.sira.service.bean.RelProcessTipoAreaEnteBean;
import it.eng.dm.sira.service.bean.TipoRicerca;
import it.eng.dm.sira.service.hibernate.SiraHibernate;
import it.eng.spring.utility.SpringAppContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

public class RicercaProcedimentoService {

	public DaoTRelProcessTipoAreaEnte daoRelazioni;

	public static Map<Long, DmtProcessTypes> mappaProcedimenti = new HashMap<Long, DmtProcessTypes>();

	public DaoTRelProcessTipoAreaEnte getDaoRelazioni() {
		return daoRelazioni;
	}

	public void setDaoRelazioni(DaoTRelProcessTipoAreaEnte daoRelazioni) {
		this.daoRelazioni = daoRelazioni;
	}

	public List<DmtProcessTypes> searchProcedimento(RicercaProcedimentoBeanIn in) throws Exception {
		DaoTRelProcessTipoAreaEnte daoRelazioni = new DaoTRelProcessTipoAreaEnte();
		List<DmtProcessTypes> procedimenti = new ArrayList<DmtProcessTypes>();
		Session session = null;
		try {
			SpringAppContext.getContext().getBean(SiraHibernate.class).initSubject();
			session = HibernateUtil.begin();
			if (mappaProcedimenti.keySet() == null || mappaProcedimenti.keySet().size() == 0) {
				String hql = "select t.idProcessType, t.nomeProcessType, t.desProcessType, t.flgAnn from DmtProcessTypes t where (t.flgAnn is null OR t.flgAnn <>1) and t.desProcessType is not null";
				Query query = session.createQuery(hql);
				List<Object[]> results = query.list();
				for (Object[] proc : results) {
					DmtProcessTypes dest = new DmtProcessTypes();
					dest.setIdProcessType((Long) proc[0]);
					dest.setNomeProcessType((String) proc[1]);
					dest.setDesProcessType((String) proc[2]);
					mappaProcedimenti.put(dest.getIdProcessType(), dest);
				}
			}
			TFilterFetch<RelProcessTipoAreaEnteBean> filterFetch = new TFilterFetch<RelProcessTipoAreaEnteBean>();
			RelProcessTipoAreaEnteBean filter = new RelProcessTipoAreaEnteBean();
			List<RelProcessTipoAreaEnteBean> listaRelazioni = null;
			if (in.getTipoRicerca() != null) {
				switch (in.getTipoRicerca()) {
				case PAROLA_CHIAVE:
					String parola = in.getParolaChiave();
					for (DmtProcessTypes toSearch : mappaProcedimenti.values()) {
						if ((toSearch.getNomeProcessType() != null && StringUtils.containsIgnoreCase(toSearch.getNomeProcessType(), parola))
								|| (toSearch.getDesProcessType() != null && StringUtils.containsIgnoreCase(toSearch.getDesProcessType(),
										parola))) {
							procedimenti.add(toSearch);
						}
					}
					break;
				case AREA_TEMATICA:
					BigDecimal idArea = in.getIdArea();
					filter.setIdArea(idArea);
					filterFetch.setFilter(filter);
					listaRelazioni = daoRelazioni.search(filterFetch).getData();
					break;
				case SETTORE_AMMINISTRATIVO:
					BigDecimal idDominio = in.getDominio();
					filter.setIdDominio(idDominio.toString());
					filterFetch.setFilter(filter);
					listaRelazioni = daoRelazioni.search(filterFetch).getData();
					break;
				case TIPOLOGIA:
					BigDecimal idTipologia = in.getIdTipologia();
					filter.setIdTipologia(idTipologia);
					filterFetch.setFilter(filter);
					listaRelazioni = daoRelazioni.search(filterFetch).getData();
					break;
				}
				if (in.getTipoRicerca() != TipoRicerca.PAROLA_CHIAVE && listaRelazioni != null) {
					for (RelProcessTipoAreaEnteBean relazione : listaRelazioni) {
						// aggiungo solo i procedimenti che non sono ancora censiti
						if (!(procedimenti.contains(mappaProcedimenti.get(relazione.getIdProcess().longValue())))) {
							procedimenti.add(mappaProcedimenti.get(relazione.getIdProcess().longValue()));
						}
						;
					}
				}
			} else {
				for (DmtProcessTypes proc : mappaProcedimenti.values()) {
					procedimenti.add(proc);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
		return procedimenti;
	}
}
