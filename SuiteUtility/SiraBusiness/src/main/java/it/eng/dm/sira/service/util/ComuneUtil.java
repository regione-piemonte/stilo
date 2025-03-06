package it.eng.dm.sira.service.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.ReflectionUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.config.ConfigUtil;
import it.eng.dm.sira.dao.DaoComuniItalia;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.SiraComuniItaliaBean;
import it.eng.spring.utility.SpringAppContext;

/**
 * classe di utilita' per i codici istat
 * 
 * @author jravagnan
 * 
 */
public class ComuneUtil {

	private static Logger log = Logger.getLogger(ComuneUtil.class);

	public static String getCode(String comune) throws Exception {
		comune = comune.toUpperCase();
		DaoComuniItalia daoComuni = new DaoComuniItalia();
		TFilterFetch<SiraComuniItaliaBean> filterFetch = new TFilterFetch<SiraComuniItaliaBean>();
		SiraComuniItaliaBean bean = new SiraComuniItaliaBean();
		bean.setComune(comune);
		filterFetch.setFilter(bean);
		List<SiraComuniItaliaBean> risultati = (List<SiraComuniItaliaBean>) daoComuni.search(filterFetch).getData();
		if (risultati == null || risultati.size() == 0) {
			throw new SiraBusinessException("Nessun risultato per il comune inserito");
		}
		if (risultati.size() > 1) {
			throw new SiraBusinessException("Risultato non univoco");
		}
		String istat = risultati.get(0).getIstat();
		String reg = istat.substring(0, 2);
		String prov = istat.substring(2, 5);
		String com = istat.substring(5, istat.length());
		log.debug("codice comune: " + reg + "-" + prov + "-" + com);
		return reg + "-" + prov + "-" + com;
	}

	public static Map<String, String> getCodiciComuneProvinciaRegione(String comune) throws Exception {
		Map<String, String> mappaValori = new HashMap<String, String>();
		comune = comune.toUpperCase();
		DaoComuniItalia daoComuni = new DaoComuniItalia();
		TFilterFetch<SiraComuniItaliaBean> filterFetch = new TFilterFetch<SiraComuniItaliaBean>();
		SiraComuniItaliaBean bean = new SiraComuniItaliaBean();
		bean.setComune(comune);
		filterFetch.setFilter(bean);
		List<SiraComuniItaliaBean> risultati = (List<SiraComuniItaliaBean>) daoComuni.search(filterFetch).getData();
		if (risultati == null || risultati.size() == 0) {
			throw new SiraBusinessException("Nessun risultato per il comune inserito");
		}
		if (risultati.size() > 1) {
			throw new SiraBusinessException("Risultato non univoco");
		}
		String istat = risultati.get(0).getIstat();
		String reg = istat.substring(0, 2);
		String prov = istat.substring(2, 5);
		String com = istat.substring(5, istat.length());
		log.debug("codice comune: " + reg + "-" + prov + "-" + com);
		mappaValori.put("C", com);
		mappaValori.put("P", prov);
		mappaValori.put("R", reg);
		return mappaValori;
	}

	public static String getCodeProvincia(String provincia) throws Exception {
		provincia = provincia.toUpperCase();
		DaoComuniItalia daoComuni = new DaoComuniItalia();
		TFilterFetch<SiraComuniItaliaBean> filterFetch = new TFilterFetch<SiraComuniItaliaBean>();
		SiraComuniItaliaBean bean = new SiraComuniItaliaBean();
		bean.setProvincia(provincia);
		filterFetch.setFilter(bean);
		List<SiraComuniItaliaBean> risultati = (List<SiraComuniItaliaBean>) daoComuni.search(filterFetch).getData();
		if (risultati == null || risultati.size() == 0) {
			throw new SiraBusinessException("Nessun risultato perla provincia inserita");
		}
		String prov = risultati.get(0).getIstatProv();
		String reg = risultati.get(0).getIstatReg();
		log.debug("codice provincia: " + reg + "-" + prov);
		return reg + "-" + prov;
	}

	public static String getCodeRegione(String regione) throws Exception {
		regione = regione.toUpperCase();
		DaoComuniItalia daoComuni = new DaoComuniItalia();
		TFilterFetch<SiraComuniItaliaBean> filterFetch = new TFilterFetch<SiraComuniItaliaBean>();
		SiraComuniItaliaBean bean = new SiraComuniItaliaBean();
		bean.setRegione(regione);
		filterFetch.setFilter(bean);
		List<SiraComuniItaliaBean> risultati = (List<SiraComuniItaliaBean>) daoComuni.search(filterFetch).getData();
		if (risultati == null || risultati.size() == 0) {
			throw new SiraBusinessException("Nessun risultato per la regione inserita");
		}

		String reg = risultati.get(0).getIstatReg();
		log.debug("codice regione: " + reg);
		return reg;
	}

	public static String getNomeComune(String istat) throws Exception {
		DaoComuniItalia daoComuni = new DaoComuniItalia();
		TFilterFetch<SiraComuniItaliaBean> filterFetch = new TFilterFetch<SiraComuniItaliaBean>();
		SiraComuniItaliaBean bean = new SiraComuniItaliaBean();
		String reg = istat.substring(0, 2);
		String prov = istat.substring(2, 5);
		String com = istat.substring(5, istat.length());		
		bean.setIstatReg(reg);
		bean.setIstatProv(prov);
		bean.setIstatCom(com);
		filterFetch.setFilter(bean);
		List<SiraComuniItaliaBean> risultati = (List<SiraComuniItaliaBean>) daoComuni.search(filterFetch).getData();
		if (risultati == null || risultati.size() == 0) {
			throw new SiraBusinessException("Nessun risultato per la regione inserita");
		}

		String comune = risultati.get(0).getComune();
		log.debug("comune: " + comune);
		return comune;
	}
	
}
