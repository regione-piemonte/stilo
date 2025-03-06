package it.eng.dm.sira.service.search;

import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.bean.SoggettoFisicoBeanIn;
import it.eng.dm.sira.service.util.ComuneUtil;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyperborea.sira.ws.SoggettiFisici;
import com.hyperborea.sira.ws.SoggettiFisiciSearchCommand;
import com.hyperborea.sira.ws.WsCountSoggettiFisiciRequest;
import com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse;
import com.hyperborea.sira.ws.WsPage;
import com.hyperborea.sira.ws.WsSearchSoggettiFisiciRequest;
import com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse;
import com.hyperborea.sira.ws.WsSoggettiFisiciRef;
import com.hyperborea.sira.ws.WsViewSoggettiFisiciRequest;
import com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse;

public class CercaSoggettoFisicoService {

	private static Logger log = Logger.getLogger(CercaSoggettoFisicoService.class);

	public List<SoggettiFisici> search(SoggettoFisicoBeanIn input) throws Exception {
		//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		List<SoggettiFisici> result = new ArrayList<SoggettiFisici>();
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		SoggettiFisiciSearchCommand command = new SoggettiFisiciSearchCommand();
		String comune = input.getComune();
		String provincia = input.getProvincia();
		String nome = input.getNome();
		String cognome = input.getCognome();
		String codiceFiscale = input.getCodiceFiscale();
		if (StringUtils.isEmpty(nome) && StringUtils.isEmpty(cognome) && StringUtils.isEmpty(comune) && StringUtils.isEmpty(provincia)
				&& StringUtils.isEmpty(codiceFiscale)) {
			throw new Exception("Nessun filtro inserito, ricerca troppo generica");
		}
		if (!StringUtils.isEmpty(nome)) {
			command.setNome(nome);
		}
		if (!StringUtils.isEmpty(cognome)) {
			command.setCognome(cognome);
		}
		if (!StringUtils.isEmpty(comune)) {
			command.setComune(ComuneUtil.getCode(comune));
		}
		if (!StringUtils.isEmpty(provincia)) {
			command.setProvincia(provincia);
		}
		if (!StringUtils.isEmpty(codiceFiscale)) {
			command.setCodiceFiscale(codiceFiscale);
		}
		WsCountSoggettiFisiciRequest requestCount = new WsCountSoggettiFisiciRequest();
		requestCount.setCommand(command);
		WsCountSoggettiFisiciResponse responseCount = service.getCatastiProxy().countSoggettiFisici(requestCount);
		Long count = responseCount.getNumeroSoggettiFisici();
		log.debug("Numero soggetti trovati: " + count);
		if (count > 0) {
			WsSearchSoggettiFisiciRequest soggettorequest = new WsSearchSoggettiFisiciRequest();
			soggettorequest.setCommand(command);
			soggettorequest.setPageSize(count.intValue());
			soggettorequest.setPageNumber(0);
			WsSearchSoggettiFisiciResponse responseSoggetti = service.getCatastiProxy().searchSoggettiFisici(soggettorequest);
			WsPage page = responseSoggetti.getWsPage();
			Object[] pageElements = page.getPageElements();
			for (int i = 0; i < pageElements.length; i++) {
				WsViewSoggettiFisiciRequest viewRew = new WsViewSoggettiFisiciRequest();
				viewRew.setWsSoggettiFisiciRef((WsSoggettiFisiciRef) pageElements[i]);
				WsViewSoggettiFisiciResponse resView = service.getCatastiProxy().viewSoggettiFisici(viewRew);
				result.add(resView.getSoggettiFisici());
			}
		}
		return result;
	}

	public Long count(SoggettoFisicoBeanIn input) throws Exception {
		//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		SoggettiFisiciSearchCommand command = new SoggettiFisiciSearchCommand();
		String comune = input.getComune();
		String provincia = input.getProvincia();
		String nome = input.getNome();
		String cognome = input.getCognome();
		String codiceFiscale = input.getCodiceFiscale();
		if (StringUtils.isEmpty(nome) && StringUtils.isEmpty(cognome) && StringUtils.isEmpty(comune) && StringUtils.isEmpty(provincia)
				&& StringUtils.isEmpty(codiceFiscale)) {
			throw new Exception("Nessun filtro inserito, ricerca troppo generica");
		}
		if (!StringUtils.isEmpty(nome)) {
			command.setNome(comune);
		}
		if (!StringUtils.isEmpty(cognome)) {
			command.setCognome(cognome);
		}
		if (!StringUtils.isEmpty(comune)) {
			command.setComune(ComuneUtil.getCode(comune));
		}
		if (!StringUtils.isEmpty(provincia)) {
			command.setProvincia(provincia);
		}
		if (!StringUtils.isEmpty(codiceFiscale)) {
			command.setCodiceFiscale(codiceFiscale);
		}
		WsCountSoggettiFisiciRequest requestCount = new WsCountSoggettiFisiciRequest();
		requestCount.setCommand(command);
		WsCountSoggettiFisiciResponse responseCount = service.getCatastiProxy().countSoggettiFisici(requestCount);
		Long count = responseCount.getNumeroSoggettiFisici();
		log.debug("Numero soggetti trovati: " + count);
		return count;
	}

}
