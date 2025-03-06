package it.eng.dm.sira.service.ost;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.bean.FiglioAlbero;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.hyperborea.sira.ws.WsVisitOstRequest;
import com.hyperborea.sira.ws.WsVisitedOst;

/**
 * ricava le informazioni su un OST partendo dall'idOggetto
 * 
 * @author jravagnan
 * 
 */
public class VisitOSTService {

	Logger log = Logger.getLogger(VisitOSTService.class);

	public List<FiglioAlbero> getAllFigliNow(Integer idOst) throws SiraBusinessException {
		return getFigli(idOst, null, null);
	}

	public List<FiglioAlbero> getFigliVarDepthNow(Integer idOst, Integer maxDepth) throws SiraBusinessException {
		return getFigli(idOst, maxDepth, null);
	}

	public List<FiglioAlbero> getFigliDiretti(Integer idOst) throws SiraBusinessException {
		return getFigliVarDepthNow(idOst, 1);
	}

	public List<FiglioAlbero> getFigliDirettiNoUnitaLocale(Integer idOst) throws SiraBusinessException {
		List<FiglioAlbero> figliDirettiNoUnitaLocale = new ArrayList<FiglioAlbero>();
		List<FiglioAlbero> figliDiretti = getFigliVarDepthNow(idOst, 1);
		for (FiglioAlbero figlio : figliDiretti) {
			if (figlio.getCategoria() != 1) {
				figliDirettiNoUnitaLocale.add(figlio);
			}
		}
		return figliDirettiNoUnitaLocale;
	}

	public List<FiglioAlbero> getFigli(Integer idOst, Integer maxDepth, Calendar date) throws SiraBusinessException {
		List<FiglioAlbero> figli = new ArrayList<FiglioAlbero>();
		try {
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			WsVisitOstRequest request = new WsVisitOstRequest();
			request.setIdOst(idOst);
			if (maxDepth != null)
				request.setMaxDepth(maxDepth);
			if (date != null)
				request.setDate(date);
			request.setDate(Calendar.getInstance());
			WsVisitedOst[] visitati = service.getCatastiProxy().visitOst(request);
			if (visitati != null) {
				for (WsVisitedOst ost : visitati) {
					FiglioAlbero figlio = new FiglioAlbero();
					figlio.setCategoria(ost.getWsOstRef().getCostId());
					figlio.setDenominazione(ost.getWsCcostRef().getLabel());
					figlio.setIdCcost(ost.getWsCcostRef().getIdCcost());
					figlio.setIdOst(ost.getWsOstRef().getIdOst());
					figlio.setIdUbicazione(ost.getWsCcostRef().getIdUbicazione());
					figlio.setNatura(ost.getWsOstRef().getNostId());
					figli.add(figlio);
				}
			}
		} catch (Exception e) {
			log.debug("impossibile caricare i figli ", e);
			throw new SiraBusinessException("impossibile caricare i figli ", e);
		}
		return figli;
	}

}
