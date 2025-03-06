package it.eng.dm.sira.service.insert;

import it.eng.dm.sira.service.SiraService;
import it.eng.spring.utility.SpringAppContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyperborea.sira.ws.SoggettiFisici;
import com.hyperborea.sira.ws.WsNewSoggettiFisiciRequest;
import com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse;
import com.hyperborea.sira.ws.WsSoggettiFisiciRef;

public class InserimentoSoggettoFisico {

	public Integer insert(SoggettiFisici input) throws Exception {
		//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		WsNewSoggettiFisiciRequest request = new WsNewSoggettiFisiciRequest();
		request.setSoggettiFisici(input);
		WsNewSoggettiFisiciResponse response = service.getCatastiProxy().newSoggettiFisici(request);
		WsSoggettiFisiciRef ref = response.getWsSoggettiFisiciRef();
		return ref.getIdSoggetto();
	}

}
