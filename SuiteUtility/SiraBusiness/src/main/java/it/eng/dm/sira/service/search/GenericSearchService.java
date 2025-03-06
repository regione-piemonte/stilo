package it.eng.dm.sira.service.search;

import it.eng.dm.sira.service.bean.NaturaOST;
import it.eng.dm.sira.service.bean.SoggettoFisicoBeanIn;

import java.util.ArrayList;
import java.util.List;

import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
import com.hyperborea.sira.ws.ReferencedBean;
import com.hyperborea.sira.ws.SoggettiFisici;

public class GenericSearchService {


	public List<ReferencedBean> search(GenericSearchServiceBeanIn input) throws Exception {
		List<ReferencedBean> ret = new ArrayList<ReferencedBean>();
		Integer nost = input.getNatura();
		if (nost == Integer.parseInt(NaturaOST.Soggetto_Fisico.getValue())) {
			CercaSoggettoFisicoService serviceFisico = new CercaSoggettoFisicoService();
			if (input instanceof SoggettoFisicoBeanIn) {
				SoggettoFisicoBeanIn in = (SoggettoFisicoBeanIn) input;
				List<SoggettiFisici> result = serviceFisico.search(in);
				ret.addAll(result);
			}

		} else {
			CercaOstService ostService = new CercaOstService();
			List<OggettiStruttureTerritoriali> osts = ostService.searchOST(input);
			ret.addAll(osts);
		}
		return ret;
	}

	public Long count(GenericSearchServiceBeanIn input) throws Exception {
		Long count = new Long(0);
		Integer nost = input.getNatura();
		if (nost == Integer.parseInt(NaturaOST.Soggetto_Fisico.getValue())) {
			CercaSoggettoFisicoService serviceFisico = new CercaSoggettoFisicoService();
			if (input instanceof SoggettoFisicoBeanIn) {
				SoggettoFisicoBeanIn in = (SoggettoFisicoBeanIn) input;
				count = serviceFisico.count(in);
			}

		} else {
			CercaOstService ostService = new CercaOstService();
			count = ostService.countOST(input);
		}
		return count;
	}

}
