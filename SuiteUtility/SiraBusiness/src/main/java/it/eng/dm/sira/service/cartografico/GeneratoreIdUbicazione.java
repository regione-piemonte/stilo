package it.eng.dm.sira.service.cartografico;

import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.util.ComuneUtil;
import it.eng.spring.utility.SpringAppContext;

import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyperborea.sira.ws.ComuniItalia;
import com.hyperborea.sira.ws.ComuniItaliaId;
import com.hyperborea.sira.ws.SearchIntertematiciWSProxy;
import com.hyperborea.sira.ws.UbicazioniOst;
import com.hyperborea.sira.ws.WsNewUbicazioniOstRequest;
import com.hyperborea.sira.ws.WsNewUbicazioniOstResponse;

public class GeneratoreIdUbicazione {

	public Integer generaId(String comune) throws Exception {
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		SearchIntertematiciWSProxy proxy = service.getCatastiProxy();
		WsNewUbicazioniOstRequest request = new WsNewUbicazioniOstRequest();
		UbicazioniOst ubicazione = new UbicazioniOst();
		Map<String, String> mappa = ComuneUtil.getCodiciComuneProvinciaRegione(comune);
		ComuniItalia oggettoComune = new ComuniItalia();
		ComuniItaliaId id = new ComuniItaliaId();
		id.setIstatCom(mappa.get("C"));
		id.setIstatProv(mappa.get("P"));
		id.setIstatReg(mappa.get("R"));
		oggettoComune.setComuniItaliaId(id);
		ubicazione.setComuneItalia(oggettoComune);
		request.setUbicazioniOst(ubicazione);
		WsNewUbicazioniOstResponse response = proxy.newUbicazioniOst(request);
		return response.getWsUbicazioniOstRef().getIdUbicazioniOst();
	}

}
