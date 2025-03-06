package it.eng.dm.sira.service.search;

import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.util.ComuneUtil;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyperborea.sira.ws.CcostAnySearchComand;
import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
import com.hyperborea.sira.ws.WsOstRef;
import com.hyperborea.sira.ws.WsPage;
import com.hyperborea.sira.ws.WsSearchOstRequest;
import com.hyperborea.sira.ws.WsSearchOstResponse;
import com.hyperborea.sira.ws.WsViewOstRequest;
import com.hyperborea.sira.ws.WsViewOstResponse;

public class CercaOstService {

	private static Logger log = Logger.getLogger(CercaOstService.class);

	public List<OggettiStruttureTerritoriali> searchOST(GenericSearchServiceBeanIn input) throws Exception {
		List<OggettiStruttureTerritoriali> result = new ArrayList<OggettiStruttureTerritoriali>();
		SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		Integer nost = input.getNatura();
		Integer cost = input.getCategoria();
		String denominazione = input.getDenominazione();
		String comune = input.getComune();
		String localita = input.getLocalita();
		String provincia = input.getProvincia();
		String partitaIva = input.getPartitaIva();
		CcostAnySearchComand command = new CcostAnySearchComand();
		command.setNostId(nost);
		command.setCostId(cost);
		if (StringUtils.isEmpty(denominazione) && StringUtils.isEmpty(comune) && StringUtils.isEmpty(provincia)
				&& StringUtils.isEmpty(localita) && StringUtils.isEmpty(partitaIva)) {
			throw new Exception("Nessun filtro inserito, ricerca troppo generica");
		}
		if (!StringUtils.isEmpty(denominazione)) {
			command.setDenominazione(denominazione);
		}
		if (!StringUtils.isEmpty(comune)) {
			command.setComune(ComuneUtil.getCode(comune));
		}
		if (!StringUtils.isEmpty(provincia)) {
			command.setProvincia(ComuneUtil.getCodeProvincia(provincia));
		}
		if (!StringUtils.isEmpty(localita)) {
			command.setLocalita(localita);
		}
		if (!StringUtils.isEmpty(partitaIva)) {
			command.setPartitaIva(partitaIva);
		}
		Long count = service.getCatastiProxy().countOst(command);
		log.debug("Numero ost trovati: " + count);
		if (count > 0) {
			WsSearchOstRequest soggettorequest = new WsSearchOstRequest();
			soggettorequest.setSearchCommand(command);
			soggettorequest.setPageSize(count.intValue());
			soggettorequest.setPageNumber(0);
			WsSearchOstResponse responseSoggetti = service.getCatastiProxy().searchOst(soggettorequest);
			WsPage page = responseSoggetti.getWsPage();
			Object[] pageElements = page.getPageElements();
			for (int i = 0; i < pageElements.length; i++) {
				WsViewOstRequest viewRew = new WsViewOstRequest();
				viewRew.setWsOstRef((WsOstRef) pageElements[i]);
				WsViewOstResponse resView = service.getCatastiProxy().viewOst(viewRew);
				result.add(resView.getOst());
			}
		}
		return result;
	}

	public Long countOST(GenericSearchServiceBeanIn input) throws Exception {
		//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		Integer nost = input.getNatura();
		Integer cost = input.getCategoria();
		String denominazione = input.getDenominazione();
		String comune = input.getComune();
		String localita = input.getLocalita();
		String provincia = input.getProvincia();
		CcostAnySearchComand command = new CcostAnySearchComand();
		command.setNostId(nost);
		command.setCostId(cost);
		if (StringUtils.isEmpty(denominazione) && StringUtils.isEmpty(comune) && StringUtils.isEmpty(provincia)
				&& StringUtils.isEmpty(localita)) {
			throw new Exception("Nessun filtro inserito, ricerca troppo generica");
		}
		if (!StringUtils.isEmpty(denominazione)) {
			command.setDenominazione(denominazione);
		}
		if (!StringUtils.isEmpty(comune)) {
			command.setComune(ComuneUtil.getCode(comune));
		}
		if (!StringUtils.isEmpty(provincia)) {
			command.setProvincia(ComuneUtil.getCodeProvincia(provincia));
		}
		if (!StringUtils.isEmpty(localita)) {
			command.setLocalita(localita);
		}
		Long count = service.getCatastiProxy().countOst(command);
		log.debug("Numero ost trovati: " + count);
		return count;
	}

}
