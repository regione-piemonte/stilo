package it.eng.dm.sira.service.ost;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.FiglioAlbero;

import java.util.List;

import org.apache.log4j.Logger;

public class InfoSedeLegaleService {

	private Logger log = Logger.getLogger(InfoSedeLegaleService.class);

	public List<RiferimentiOst> getUnitaLocali(String idOst) throws SiraBusinessException {
		List<RiferimentiOst> unitalocali = null;
		OstCollegatiBeanIn in = new OstCollegatiBeanIn();
		in.setCategoria("1"); // fisso per unit locali
		in.setNatura("1"); // fisso per unit locali
		in.setIdOst(idOst);
		try {
			RelazioniSoggettoService rss = new RelazioniSoggettoService();
			unitalocali = rss.getOstCollegati(in);
			return unitalocali;
		} catch (Exception e) {
			log.error("impossibile ricavare le unit locali ", e);
			throw new SiraBusinessException("impossibile ricavare le unit locali ", e);
		}
	}

	public InfoAlberoOst getOstDirettamenteCollegati(String idOst) throws SiraBusinessException {
		InfoOSTService infoService = new InfoOSTService();
		InfoAlberoOst albero = null;
		try {
			InfoBeanIn in = new InfoBeanIn();
			in.setCategoria("2");
			in.setNatura("1");
			in.setIdOggetto(idOst);
			albero = infoService.getInfoOstCollegatiCardinalitaNoUnitaLocaleDeepMethod(in);
		} catch (Exception e) {
			log.error("impossibile ricavare gli ost collegati direttamente ", e);
			throw new SiraBusinessException("impossibile ricavare gli ost collegati direttamente ", e);
		}
		return albero;
	}
 
	public List<FiglioAlbero> getAllOstCollegatiNow(String idOst) throws SiraBusinessException {
		VisitOSTService service = new VisitOSTService();
		List<FiglioAlbero> listaRis = null;
		try {
		listaRis = service.getAllFigliNow(Integer.parseInt(idOst));
		} catch (Exception e) {
			log.error("impossibile ricavare tutti gli ost collegati ", e);
			throw new SiraBusinessException("impossibile ricavare tutti gli ost collegati ", e);
		}
		return listaRis;
	}

}
