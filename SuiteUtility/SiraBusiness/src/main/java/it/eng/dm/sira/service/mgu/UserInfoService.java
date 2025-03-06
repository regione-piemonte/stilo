package it.eng.dm.sira.service.mgu;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.SiraService;
import it.eng.sira.mgu.ws.MguDelegaDTO;
import it.eng.sira.mgu.ws.MguDominioDTO;
import it.eng.sira.mgu.ws.MguPermessoRisorsaDTO;
import it.eng.sira.mgu.ws.MguSgiuridicoOstDTO;
import it.eng.sira.mgu.ws.MguUtenteDTO;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.AxisFault;

import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
import com.hyperborea.sira.ws.SoggettiFisici;
import com.hyperborea.sira.ws.WsOstRef;
import com.hyperborea.sira.ws.WsSoggettiFisiciRef;
import com.hyperborea.sira.ws.WsViewOstRequest;
import com.hyperborea.sira.ws.WsViewOstResponse;
import com.hyperborea.sira.ws.WsViewSoggettiFisiciRequest;
import com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse;

/**
 * servizio per ottenere le informazioni su soggetti censiti in MGU
 * 
 * @author jravagnan
 * 
 */
public class UserInfoService {

	private static final String GIURIDICO = "GIURIDICO";

	private static final String ENTE = "ENTE";

	/**
	 * ottiene i dati del soggetto fisico collegato allo username di login
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public SoggettiFisici getInfoUserFisico(String username) throws Exception {
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		SoggettiFisici result = null;
		MguUtenteDTO utente = service.getMguProxy().getUtente(username.toLowerCase());
		Long idSoggettoFisico = utente.getIdSoggettoFisico();
		if (idSoggettoFisico != null) {
			WsViewSoggettiFisiciRequest request = new WsViewSoggettiFisiciRequest();
			WsSoggettiFisiciRef riferimento = new WsSoggettiFisiciRef();
			riferimento.setIdSoggetto(idSoggettoFisico.intValue());
			request.setWsSoggettiFisiciRef(riferimento);
			WsViewSoggettiFisiciResponse response = service.getCatastiProxy().getSearchIntertematiciWS().viewSoggettiFisici(request);
			result = response.getSoggettiFisici();
		}
		return result;
	}

	/**
	 * ottiene i dati relativi ai soggetti giuridici collegati allo username di login
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public List<OggettiStruttureTerritoriali> getInfoSoggettiGiuridiciCollegati(String username) throws Exception {
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		List<OggettiStruttureTerritoriali> listaOst = new ArrayList<OggettiStruttureTerritoriali>();
		try {
			MguDominioDTO[] domini = service.getMguProxy().getDomini(username.toLowerCase());
			if (domini != null) {
				for (MguDominioDTO dominio : domini) {
					if (dominio.getTipologia().equalsIgnoreCase(GIURIDICO) || dominio.getTipologia().equalsIgnoreCase(ENTE)) {
						Long idOst = dominio.getIdOst();
						WsViewOstRequest request = new WsViewOstRequest();
						WsOstRef riferimento = new WsOstRef();
						riferimento.setCostId(2);
						riferimento.setNostId(1);
						riferimento.setIdOst(idOst.intValue());
						request.setWsOstRef(riferimento);
						WsViewOstResponse response = service.getCatastiProxy().getSearchIntertematiciWS().viewOst(request);
						listaOst.add(response.getOst());
					}
				}
			}
		} catch (Exception e) {
			if (e instanceof AxisFault && ((AxisFault) e).getFaultString().equalsIgnoreCase("utente sconosciuto")) {
				throw new SiraBusinessException("Utente sconosciuto, impossibile conoscerne i domini associati");
			} else {
				throw e;
			}
		}
		return listaOst;
	}

	public UserInfoOutPutBean getInfoDelegheCollegate(String username) throws Exception {
		UserInfoOutPutBean out = new UserInfoOutPutBean();
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		Map<Long, List<MguSgiuridicoOstDTO>> mappaUl = new HashMap<Long, List<MguSgiuridicoOstDTO>>();
		List<UserPermessi> userPermessi = new ArrayList<UserPermessi>();
		MguDelegaDTO[] deleghe = service.getMguProxy().getDelegheRicevute(username.toLowerCase());
		if (deleghe != null) {
			for (MguDelegaDTO delega : deleghe) {
				MguPermessoRisorsaDTO[] permessi = delega.getPermessi();
				for (MguPermessoRisorsaDTO permesso : permessi) {
					MguSgiuridicoOstDTO[] ulocali = permesso.getSgiuridicoost();
					for (MguSgiuridicoOstDTO ul : ulocali) {
						List<MguSgiuridicoOstDTO> listaUl = new ArrayList<MguSgiuridicoOstDTO>();
						Long idDominio = ul.getIdDominio();
						Long idOst = service.getMguProxy().getDominioFromId(idDominio).getIdOst();
						if (mappaUl.get(idOst) == null) {
							listaUl.add(ul);
							mappaUl.put(idOst, listaUl);
						} else {
							List<MguSgiuridicoOstDTO> listaEsistente = mappaUl.get(idOst);
							if (!(listaEsistente.contains(ul))) {
								listaEsistente.add(ul);
								mappaUl.put(idOst, listaEsistente);
							}
						}
					}
				}
				MguDominioDTO[] domini = service.getMguProxy().getDomini(delega.getDelegante().getUsername());
				if (domini != null) {
					for (MguDominioDTO dominio : domini) {
						if (dominio.getTipologia().equalsIgnoreCase(GIURIDICO) || dominio.getTipologia().equalsIgnoreCase(ENTE)) {
							Long idOst = dominio.getIdOst();
							if (mappaUl.get(idOst) != null) {
								UserPermessi permUser = new UserPermessi();
								WsViewOstRequest request = new WsViewOstRequest();
								WsOstRef riferimento = new WsOstRef();
								riferimento.setCostId(2);
								riferimento.setNostId(1);
								riferimento.setIdOst(idOst.intValue());
								request.setWsOstRef(riferimento);
								WsViewOstResponse response = service.getCatastiProxy().getSearchIntertematiciWS().viewOst(request);
								permUser.setSoggettoGiuridico(response.getOst());
								permUser.setUnitaLocali(mappaUl.get(idOst));
								userPermessi.add(permUser);
							}
						}
					}
				}
			}
			out.setPermessi(userPermessi);
		}
		return out;
	}
}
