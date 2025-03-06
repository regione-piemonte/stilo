package it.eng.dm.sira.service.general;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.GenericOSTSearchInputBean;
import it.eng.dm.sira.service.bean.GenericOSTSearchOutputBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hyperborea.sira.ws.CcostAnySearchComand;
import com.hyperborea.sira.ws.WsPage;

public abstract class AbstractSiraService implements ISiraService<GenericOSTSearchInputBean, GenericOSTSearchOutputBean> {

	private static Logger log = Logger.getLogger(IntertematiciService.class);

	private String endpoint;

	private SiraProxy proxy;

	@Override
	public List<GenericOSTSearchOutputBean> search(GenericOSTSearchInputBean input) throws Exception {
		if (input.getCategoria() == null || input.getNatura() == null) {
			throw new SiraBusinessException("Impossibile invocare il servizio: categoria e natura sono campi obbligatori");
		}
		if (StringUtils.isEmpty(input.getDenominazione()) && StringUtils.isEmpty(input.getComune())
				&& StringUtils.isEmpty(input.getLocalita()) && StringUtils.isEmpty(input.getProvincia())) {
			throw new SiraBusinessException("E' obbligatorio inserire un filtro per effettuare la ricerca!");
		}
		if (proxy == null) {
			throw new SiraBusinessException("E' obbligatorio specificare un proxy per il collegamento");
		}
		List<GenericOSTSearchOutputBean> listaOggetti = new ArrayList<GenericOSTSearchOutputBean>();
		try {
			if (!StringUtils.isEmpty(endpoint)) {
				proxy.setEndpoint(endpoint);
			}
			CcostAnySearchComand ccostCommand = new CcostAnySearchComand();
			ccostCommand.setCostId(input.getCategoria());
			ccostCommand.setNostId(input.getNatura());
			ccostCommand.setDenominazione(input.getDenominazione());
			ccostCommand.setComune(input.getComune());
			ccostCommand.setComune(input.getProvincia());
			ccostCommand.setComune(input.getLocalita());
			Long numeroRisultati = proxy.countOst(ccostCommand);
			if (numeroRisultati > 0) {
				WsPage result = proxy.searchOst(ccostCommand, numeroRisultati.intValue(), 0);
				if (result != null) {
					Object[] objects = result.getPageElements();
					GenericOSTSearchOutputBean out = new GenericOSTSearchOutputBean();
					out.setElementi(objects);
					listaOggetti.add(out);
				}
			}
		} catch (Exception e) {
			log.debug("impossibile ottenere il soggetto ricercato", e);
			throw new SiraBusinessException("impossibile ottenere il soggetto ricercato", e);
		}
		return listaOggetti;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public SiraProxy getProxy() {
		return proxy;
	}

	public void setProxy(SiraProxy proxy) {
		this.proxy = proxy;
	}

}
