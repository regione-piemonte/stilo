package it.eng.dm.sira.service.insert;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.bean.SoggettoGiuridicoInputBean;
import it.eng.spring.utility.SpringAppContext;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyperborea.sira.ws.CaratterizzazioniOst;
import com.hyperborea.sira.ws.WsBeginTransactionRequest;
import com.hyperborea.sira.ws.WsCommitTransactionRequest;
import com.hyperborea.sira.ws.WsFontiDatiRef;
import com.hyperborea.sira.ws.WsFontiDatiResponse;
import com.hyperborea.sira.ws.WsNewCcostRequest;
import com.hyperborea.sira.ws.WsNewFontiDatiRequest;
import com.hyperborea.sira.ws.WsNewOstRequest;
import com.hyperborea.sira.ws.WsNewOstResponse;
import com.hyperborea.sira.ws.WsOstRef;

/**
 * classe per l'inserimento del soggetto giuridico
 * 
 * @author jravagnan
 * 
 */
public class InserimentoSoggettoGiuridico {

	private static Logger log = Logger.getLogger(InserimentoSoggettoGiuridico.class);

	/**
	 * inserisce un soggetto giuridico
	 * 
	 * @param id
	 * @param input
	 * @return
	 * @throws SiraBusinessException
	 * @throws Exception
	 */
	public Integer insert(SoggettoGiuridicoInputBean input) throws SiraBusinessException {
		WsOstRef ref = null;
		//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		try {
			WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
			service.getCatastiProxy().beginTransaction(btreq);
			WsNewOstRequest ostReq = new WsNewOstRequest();
			ostReq.setCostNostId(input.getId());
			WsNewOstResponse ostResp = service.getCatastiProxy().newOst(ostReq);
			ref = ostResp.getWsOstRef();
			WsNewFontiDatiRequest fonteRequest = new WsNewFontiDatiRequest();
			fonteRequest.setFontiDati(input.getFonti());
			WsFontiDatiResponse fontiResponse = service.getCatastiProxy().newFontiDati(fonteRequest);
			WsFontiDatiRef fonteRef = fontiResponse.getWsFontiDatiRef();
			WsNewCcostRequest costRequest = new WsNewCcostRequest();
			CaratterizzazioniOst caratterizzazioni = new CaratterizzazioniOst();
			caratterizzazioni.setCcostSoggettiGiuridici(input.getCaratterizzazione());
			caratterizzazioni.setDataInizio(Calendar.getInstance());
			caratterizzazioni.setDenominazione(input.getCaratterizzazione().getCcostSediLegali().getRagioneSociale());
			caratterizzazioni.setUbicazioniOst(input.getUbicazione());
			caratterizzazioni.setTipologieFontiDati(input.getTipologiaFonte());
			costRequest.setCaratterizzazioniOst(caratterizzazioni);
			costRequest.setWsOstRef(ref);
			costRequest.setWsFontiDatiRef(fonteRef);
			service.getCatastiProxy().newCcost(costRequest);
			WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
			service.getCatastiProxy().closeTransaction(creq);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Impossibile inserire il nuovo soggetto");
			throw new SiraBusinessException("Impossibile inserire il nuovo soggetto", e);
		}
		return ref.getIdOst();
	}

}
