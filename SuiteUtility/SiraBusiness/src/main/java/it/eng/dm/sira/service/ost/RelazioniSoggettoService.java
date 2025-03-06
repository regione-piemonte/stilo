package it.eng.dm.sira.service.ost;

import it.eng.dm.sira.service.SiraService;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hyperborea.sira.ws.CcostAnySearchComand;
import com.hyperborea.sira.ws.WsOstRef;
import com.hyperborea.sira.ws.WsPage;
import com.hyperborea.sira.ws.WsSearchOstRequest;
import com.hyperborea.sira.ws.WsSearchOstResponse;

public class RelazioniSoggettoService {
	
	private Logger log = Logger.getLogger(RelazioniSoggettoService.class);

	public List<RiferimentiOst> getOstCollegati(OstCollegatiBeanIn in) throws Exception {
		//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		Long timePre = System.currentTimeMillis();
		if (in.getIdOst() != null && in.getIdSoggettoFisico() != null) {
			throw new Exception("La ricerca  ESCLUSIVA per idOst o idSoggettoFisico");
		}
		List<RiferimentiOst> listaRiferimenti = new ArrayList<RiferimentiOst>();
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		WsSearchOstRequest request = new WsSearchOstRequest();
		CcostAnySearchComand command = new CcostAnySearchComand();
		command.setNostId(Integer.parseInt(in.getNatura()));
		command.setCostId(Integer.parseInt(in.getCategoria()));
		if (in.getIdSoggettoFisico() != null) {
			command.setIdSoggettoFisico(Integer.parseInt(in.getIdSoggettoFisico()));
		}
		if (in.getIdOst() != null) {
			command.setIdOstSedeLegale(Integer.parseInt(in.getIdOst()));
		}
		request.setSearchCommand(command);
//		Long count = service.getCatastiProxy().countOst(command);
//		log.debug("Numero risultati: "+count);
//		if (count > 0) {
//			request.setPageSize(count.intValue());
		request.setPageSize(100);
			request.setPageNumber(0);
			WsSearchOstResponse response = service.getCatastiProxy().searchOst(request);
			WsPage page = response.getWsPage();
			Object[] pageElements = page.getPageElements();
			for (int i = 0; i < pageElements.length; i++) {
				RiferimentiOst riferimento = new RiferimentiOst();
				riferimento.setCategoria(((WsOstRef) pageElements[i]).getCostId());
				riferimento.setNatura(((WsOstRef) pageElements[i]).getNostId());
				riferimento.setIdOst(((WsOstRef) pageElements[i]).getIdOst());
				riferimento.setTitle(((WsOstRef) pageElements[i]).getTitle());
				listaRiferimenti.add(riferimento);
			}
//		}
		System.out.println("time search : "+(System.currentTimeMillis()-timePre)+" ms");
		return listaRiferimenti;
	}

}
