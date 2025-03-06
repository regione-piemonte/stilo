package it.eng.dm.sira.service.procedimento;

import it.eng.core.business.TFilterFetch;
import it.eng.dm.sira.dao.DaoVMguOrganigramma;
import it.eng.dm.sira.entity.VMguOrganigramma;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.LivelloEnte;
import it.eng.dm.sira.service.bean.StatoEnte;

import java.util.ArrayList;
import java.util.List;

public class RicercaOrganigrammaService {

	public List<OggettoEnte> search(Long idPadre) throws SiraBusinessException {
		List<OggettoEnte> ret = new ArrayList<OggettoEnte>();
		DaoVMguOrganigramma daoOrganigramma = new DaoVMguOrganigramma();
		try {
			TFilterFetch<VMguOrganigramma> filterFetch = new TFilterFetch<VMguOrganigramma>();
			VMguOrganigramma filter = new VMguOrganigramma();
			filter.setIdPadre(idPadre);
			filter.setStato(StatoEnte.ATTIVO.getValue());
			filterFetch.setFilter(filter);
			List<VMguOrganigramma> listaRisultati = daoOrganigramma.search(filterFetch).getData();
			for (VMguOrganigramma oggetto : listaRisultati) {
				OggettoEnte ente = new OggettoEnte();
				ente.setEnte(oggetto);
				if (oggetto.getLivello().equals(LivelloEnte.FOGLIA.getValue())) {
					ente.setFoglia(true);
				} else {
					ente.setFoglia(false);
				}
				ret.add(ente);
			}
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile caricare il sottoalbero: ",e);
		}
		return ret;
	}
	
	public List<VMguOrganigramma> getAlberoOrganigramma() throws SiraBusinessException{
		try {
		DaoVMguOrganigramma daoOrganigramma = new DaoVMguOrganigramma();
		TFilterFetch<VMguOrganigramma> filterFetch = new TFilterFetch<VMguOrganigramma>();
		VMguOrganigramma filter = new VMguOrganigramma();
		filter.setStato(StatoEnte.ATTIVO.getValue());
		filterFetch.setFilter(filter);
		List<VMguOrganigramma> listaRisultati;
		listaRisultati = daoOrganigramma.search(filterFetch).getData();
		return listaRisultati;
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile caricare l'organigramma: ",e);
		}
	}

}
