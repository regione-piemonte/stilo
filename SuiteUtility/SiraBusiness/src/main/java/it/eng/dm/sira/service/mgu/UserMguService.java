package it.eng.dm.sira.service.mgu;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.SiraService;
import it.eng.sira.mgu.ws.MguDelegaDTO;
import it.eng.sira.mgu.ws.MguDominioDTO;
import it.eng.sira.mgu.ws.MguPermessoDTO;
import it.eng.sira.mgu.ws.MguRisorsaDTO;
import it.eng.sira.mgu.ws.MguServiceEndPointProxy;
import it.eng.sira.mgu.ws.MguUtenteDTO;
import it.eng.spring.utility.SpringAppContext;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * servizio che espone i metodi per ottenere informazioni sull'utente
 * 
 * @author jravagnan
 * 
 */
public class UserMguService {

	public List<MguDominioDTO> getDominiUtente(String userId) throws SiraBusinessException {
		List<MguDominioDTO> domini = null;
		try {
			//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			MguServiceEndPointProxy proxy = service.getMguProxy();
			MguDominioDTO[] arrayDomini = proxy.getDomini(userId);
			if (arrayDomini != null) {
				domini = Arrays.asList(arrayDomini);
			}
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile ottenere i domini per l'utente " + userId, e);
		}
		return domini;
	}

	public List<MguDelegaDTO> getDelegheUtente(String userId) throws SiraBusinessException {
		List<MguDelegaDTO> deleghe = null;
		try {
			//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			MguServiceEndPointProxy proxy = service.getMguProxy();
			MguDelegaDTO[] arrayDeleghe = proxy.getDelegheRicevute(userId);
			if (arrayDeleghe != null) {
				deleghe = Arrays.asList(arrayDeleghe);
			}
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile ottenere le deleghe per l'utente " + userId, e);
		}
		return deleghe;
	}

	public List<MguPermessoDTO> getPermessiUtente(String userId) throws SiraBusinessException {
		List<MguPermessoDTO> permessi = null;
		try {
			//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			MguServiceEndPointProxy proxy = service.getMguProxy();
			MguPermessoDTO[] arrayPermessi = proxy.getPermessi(userId);
			if (arrayPermessi != null) {
				permessi = Arrays.asList(arrayPermessi);
			}
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile ottenere le deleghe per l'utente " + userId, e);
		}
		return permessi;
	}

	public List<MguRisorsaDTO> getRisorseUtente(String userId) throws SiraBusinessException {
		List<MguRisorsaDTO> risorse = null;
		try {
			//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			MguServiceEndPointProxy proxy = service.getMguProxy();
			MguRisorsaDTO[] arrayRisorse = proxy.getRisorse(userId);
			if (arrayRisorse != null) {
				risorse = Arrays.asList(arrayRisorse);
			}
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile ottenere le deleghe per l'utente " + userId, e);
		}
		return risorse;
	}

	public MguUtenteDTO getInfoUtente(String userId) throws SiraBusinessException {
		MguUtenteDTO info = null;
		try {
			//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			MguServiceEndPointProxy proxy = service.getMguProxy();
			info = proxy.getUtente(userId);
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile ottenere le deleghe per l'utente " + userId, e);
		}
		return info;
	}

}
