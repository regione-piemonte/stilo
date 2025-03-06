package it.eng.dm.sira.service.ost;

import it.eng.core.business.TFilterFetch;
import it.eng.dm.sira.dao.DaoVCategoriaNatura;
import it.eng.dm.sira.entity.VCategoriaNatura;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.bean.FiglioAlbero;
import it.eng.dm.sira.service.ost.util.OstUtils;
import it.eng.spring.utility.SpringAppContext;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hyperborea.sira.ws.CaratterizzazioniOst;
import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
import com.hyperborea.sira.ws.ReferencedBean;
import com.hyperborea.sira.ws.RelazioniOst;
import com.hyperborea.sira.ws.WsOstRef;
import com.hyperborea.sira.ws.WsSoggettiFisiciRef;
import com.hyperborea.sira.ws.WsViewOstRequest;
import com.hyperborea.sira.ws.WsViewOstResponse;
import com.hyperborea.sira.ws.WsViewSoggettiFisiciRequest;
import com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse;

/**
 * ricava le informazioni su un OST partendo dall'idOggetto
 * 
 * @author jravagnan
 * 
 */
public class InfoOSTService {

	private static Map<String, VCategoriaNatura> categorie = null;

	private Logger log = Logger.getLogger(InfoOSTService.class);

	private void init() {
		if (categorie == null) {
			try {
				DaoVCategoriaNatura daoCategoriaNatura = new DaoVCategoriaNatura();
				TFilterFetch<VCategoriaNatura> filterFetch = new TFilterFetch<VCategoriaNatura>();
				VCategoriaNatura filter = new VCategoriaNatura();
				filterFetch.setFilter(filter);
				List<VCategoriaNatura> listaRis = daoCategoriaNatura.search(filterFetch).getData();
				if (listaRis != null && listaRis.size() > 0) {
					categorie = new HashMap<String, VCategoriaNatura>();
					for (VCategoriaNatura record : listaRis) {
						categorie.put(record.getCodCost().toString(), record);
					}
				}
			} catch (Exception e) {
				log.error("Impossibile caricare le categorie: ", e);
			}
		}
	}

	public ReferencedBean getInfo(InfoBeanIn in) throws RemoteException {
		// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		if (in.getNatura() != null && in.getNatura().equals("66")) {
			WsViewSoggettiFisiciRequest viewRew = new WsViewSoggettiFisiciRequest();
			WsSoggettiFisiciRef wsSoggettiFisiciRef = new WsSoggettiFisiciRef();
			wsSoggettiFisiciRef.setIdSoggetto(Integer.parseInt(in.getIdOggetto()));
			viewRew.setWsSoggettiFisiciRef(wsSoggettiFisiciRef);
			WsViewSoggettiFisiciResponse resView = service.getCatastiProxy().viewSoggettiFisici(viewRew);
			return resView.getSoggettiFisici();
		} else {
			WsViewOstRequest viewRew = new WsViewOstRequest();
			WsOstRef wsOstRef = new WsOstRef();
			wsOstRef.setIdOst(Integer.parseInt(in.getIdOggetto()));
			if (in.getNatura() != null) {
				wsOstRef.setNostId(Integer.parseInt(in.getNatura()));
			}
			wsOstRef.setCostId(Integer.parseInt(in.getCategoria()));
			viewRew.setWsOstRef(wsOstRef);
			WsViewOstResponse resView = service.getCatastiProxy().viewOst(viewRew);
			return resView.getOst();
		}
	}

	public List<OggettiStruttureTerritoriali> getInfoOstCollegati(InfoBeanIn in) throws RemoteException {
		// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		List<OggettiStruttureTerritoriali> listaOst = new ArrayList<OggettiStruttureTerritoriali>();
		WsViewOstRequest viewRew = new WsViewOstRequest();
		WsOstRef wsOstRef = new WsOstRef();
		wsOstRef.setIdOst(Integer.parseInt(in.getIdOggetto()));
		wsOstRef.setNostId(Integer.parseInt(in.getNatura()));
		wsOstRef.setCostId(Integer.parseInt(in.getCategoria()));
		viewRew.setWsOstRef(wsOstRef);
		WsViewOstResponse resView = service.getCatastiProxy().viewOst(viewRew);
		OggettiStruttureTerritoriali ret = resView.getOst();
		if (ret != null && ret.getRelazioniOstsForIdOst1() != null) {
			for (RelazioniOst relazioni : ret.getRelazioniOstsForIdOst1()) {
				if (relazioni.getDataFine() == null) {
					WsViewOstRequest request = new WsViewOstRequest();
					request.setWsOstRef(relazioni.getId().getChildOstRef());
					WsViewOstResponse response = service.getCatastiProxy().viewOst(request);
					if (response != null) {
						listaOst.add(response.getOst());
					}
				}
			}
		}
		return listaOst;
	}

	public List<OggettiStruttureTerritoriali> getInfoOstCollegatiNoUnitaLocale(InfoBeanIn in) throws RemoteException {
		// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		List<OggettiStruttureTerritoriali> listaOst = new ArrayList<OggettiStruttureTerritoriali>();
		WsViewOstRequest viewRew = new WsViewOstRequest();
		WsOstRef wsOstRef = new WsOstRef();
		wsOstRef.setIdOst(Integer.parseInt(in.getIdOggetto()));
		wsOstRef.setNostId(Integer.parseInt(in.getNatura()));
		wsOstRef.setCostId(Integer.parseInt(in.getCategoria()));
		viewRew.setWsOstRef(wsOstRef);
		WsViewOstResponse resView = service.getCatastiProxy().viewOst(viewRew);
		OggettiStruttureTerritoriali ret = resView.getOst();
		if (ret != null && ret.getRelazioniOstsForIdOst1() != null) {
			for (RelazioniOst relazioni : ret.getRelazioniOstsForIdOst1()) {
				WsOstRef ref = relazioni.getId().getChildOstRef();
				if (!(ref.getCostId() == 1 && ref.getNostId() == 1)) {
					if (relazioni.getDataFine() == null) {
						WsViewOstRequest request = new WsViewOstRequest();
						request.setWsOstRef(ref);
						WsViewOstResponse response = service.getCatastiProxy().viewOst(request);
						if (response != null) {
							listaOst.add(response.getOst());
						}
					}
				}
			}
		}
		return listaOst;
	}

	public InfoAlberoOst getInfoOstCollegatiCardinalita(InfoBeanIn in) throws RemoteException {
		InfoAlberoOst albero = new InfoAlberoOst();
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		List<InfoBeanOut> listaOst = new ArrayList<InfoBeanOut>();
		WsViewOstRequest viewRew = new WsViewOstRequest();
		WsOstRef wsOstRef = new WsOstRef();
		InfoBeanOut padre = new InfoBeanOut();
		wsOstRef.setIdOst(Integer.parseInt(in.getIdOggetto()));
		wsOstRef.setNostId(Integer.parseInt(in.getNatura()));
		wsOstRef.setCostId(Integer.parseInt(in.getCategoria()));
		viewRew.setWsOstRef(wsOstRef);
		long timePre = System.currentTimeMillis();
		WsViewOstResponse resView = service.getCatastiProxy().viewOst(viewRew);
		long timePost = System.currentTimeMillis();
		System.out.println("chiamata view " + (timePost - timePre) + " ms");
		OggettiStruttureTerritoriali ret = resView.getOst();
		padre.setCategoria(Integer.parseInt(in.getCategoria()));
		padre.setNatura(Integer.parseInt(in.getNatura()));
		padre.setOggetto(ret);
		padre.setDenominazione(ret.getCaratterizzazioniOsts()[0].getDenominazione());
		padre.setFigli(ret.getRelazioniOstsForIdOst1().length);
		albero.setOstPadre(padre);
		if (ret.getRelazioniOstsForIdOst1() != null) {
			long timePreRel = System.currentTimeMillis();
			for (RelazioniOst relazioni : ret.getRelazioniOstsForIdOst1()) {
				if (relazioni.getDataFine() == null) {
					InfoBeanOut out = new InfoBeanOut();
					WsViewOstRequest request = new WsViewOstRequest();
					request.setWsOstRef(relazioni.getId().getChildOstRef());
					out.setCategoria(relazioni.getId().getChildOstRef().getCostId());
					out.setCategoria(relazioni.getId().getChildOstRef().getNostId());
					out.setDenominazione(relazioni.getId().getChildOstRef().getTitle());
					WsViewOstResponse response = service.getCatastiProxy().viewOst(request);
					if (response != null) {
						out.setOggetto(response.getOst());
						CaratterizzazioniOst toProcess = OstUtils.chooseCaratterizzazione(response.getOst().getCaratterizzazioniOsts());
						out.setIdCCost(toProcess.getIdCcost());
						out.setIdUbicazione(String.valueOf(toProcess.getUbicazioniOst().getIdUbicazione()));
						if (response.getOst().getRelazioniOstsForIdOst1() != null) {
							out.setFigli(response.getOst().getRelazioniOstsForIdOst1().length);
						} else {
							out.setFigli(0);
						}
						listaOst.add(out);
					}
				}
			}
			long timePostRel = System.currentTimeMillis();
			System.out.println("chiamata rel " + (timePostRel - timePreRel) + " ms");
		}
		albero.setOstFigli(listaOst);
		return albero;
	}

	public InfoAlberoOst getInfoOstCollegatiCardinalitaNoUnitaLocale(InfoBeanIn in) throws RemoteException {
		InfoAlberoOst albero = new InfoAlberoOst();
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		List<InfoBeanOut> listaOst = new ArrayList<InfoBeanOut>();
		WsViewOstRequest viewRew = new WsViewOstRequest();
		WsOstRef wsOstRef = new WsOstRef();
		InfoBeanOut padre = new InfoBeanOut();
		wsOstRef.setIdOst(Integer.parseInt(in.getIdOggetto()));
		wsOstRef.setNostId(StringUtils.isNotEmpty(in.getNatura()) ? Integer.parseInt(in.getNatura()) : null);
		wsOstRef.setCostId(Integer.parseInt(in.getCategoria()));
		viewRew.setWsOstRef(wsOstRef);
		WsViewOstResponse resView = service.getCatastiProxy().viewOst(viewRew);
		OggettiStruttureTerritoriali ret = resView.getOst();
		padre.setCategoria(Integer.parseInt(in.getCategoria()));
		padre.setNatura(StringUtils.isNotEmpty(in.getNatura()) ? Integer.parseInt(in.getNatura()) : null);
		padre.setOggetto(ret);
		padre.setDenominazione(ret.getCaratterizzazioniOsts()[0].getDenominazione());
		int i = 0;
		if (ret.getRelazioniOstsForIdOst1() != null) {
			for (RelazioniOst relazioni : ret.getRelazioniOstsForIdOst1()) {
				if (relazioni.getDataFine() == null) {
					InfoBeanOut out = new InfoBeanOut();
					WsOstRef ref = relazioni.getId().getChildOstRef();
					if (!(ref.getCostId() == 1 && ref.getNostId() == 1)) {
						WsViewOstRequest request = new WsViewOstRequest();
						request.setWsOstRef(relazioni.getId().getChildOstRef());
						out.setCategoria(ref.getCostId());
						out.setCategoria(ref.getNostId());
						out.setDenominazione(ref.getTitle());
						WsViewOstResponse response = service.getCatastiProxy().viewOst(request);
						if (response != null) {
							out.setOggetto(response.getOst());
							CaratterizzazioniOst toProcess = OstUtils.chooseCaratterizzazione(response.getOst().getCaratterizzazioniOsts());
							out.setIdCCost(toProcess.getIdCcost());
							out.setIdUbicazione(String.valueOf(toProcess.getUbicazioniOst().getIdUbicazione()));
							if (response.getOst().getRelazioniOstsForIdOst1() != null) {
								out.setFigli(response.getOst().getRelazioniOstsForIdOst1().length);
							} else {
								out.setFigli(0);
							}
							listaOst.add(out);
							i++;
						}
					}
				}
			}
		} else {
			padre.setFigli(0);
		}
		padre.setFigli(i);
		albero.setOstPadre(padre);
		albero.setOstFigli(listaOst);
		return albero;
	}

	public InfoAlberoOst getInfoOstCollegatiCardinalitaNoUnitaLocaleDeepMethod(InfoBeanIn in) throws NumberFormatException,
			SiraBusinessException {
		init();
		InfoAlberoOst albero = new InfoAlberoOst();
		List<InfoBeanOut> listaOst = new ArrayList<InfoBeanOut>();
		InfoBeanOut padre = new InfoBeanOut();
		if (categorie != null) {
			VCategoriaNatura obj = categorie.get(in.getCategoria());
			if (obj != null) {
				padre.setDescCategoria(obj.getDescCategoriaOst());
				padre.setDescNatura(obj.getDescNaturaOst());
			}
		}
		padre.setCategoria(Integer.parseInt(in.getCategoria()));
		padre.setNatura(StringUtils.isNotEmpty(in.getNatura()) ? Integer.parseInt(in.getNatura()) : null);
		padre.setIdOst(Integer.parseInt(in.getIdOggetto()));
		VisitOSTService service = new VisitOSTService();
		List<FiglioAlbero> figli = service.getFigliDirettiNoUnitaLocale(Integer.parseInt(in.getIdOggetto()));
		for (FiglioAlbero figlio : figli) {
			InfoBeanOut out = new InfoBeanOut();
			if (categorie != null) {
				VCategoriaNatura obj = categorie.get(String.valueOf(figlio.getCategoria()));
				if (obj != null) {
					out.setDescCategoria(obj.getDescCategoriaOst());
					out.setDescNatura(obj.getDescNaturaOst());
				}
			}
			out.setCategoria(figlio.getCategoria());
			out.setDenominazione(figlio.getDenominazione());
			out.setIdCCost(figlio.getIdCcost());
			out.setIdOst(figlio.getIdOst());
			out.setIdUbicazione(String.valueOf(figlio.getIdUbicazione()));
			out.setNatura(figlio.getNatura());
			List<FiglioAlbero> figliFiglio = service.getFigliDirettiNoUnitaLocale(figlio.getIdOst());
			out.setFigli(figliFiglio == null ? 0 : figliFiglio.size());
			listaOst.add(out);

		}
		padre.setFigli(figli.size());
		albero.setOstPadre(padre);
		albero.setOstFigli(listaOst);
		return albero;
	}

	/**
	 * il metodo restituisce la caratterizzazione cercata per l'oggetto Se idCaratterizzazione � valorizzato cerca quella, se non �
	 * valorizzato cerca la pi� recente
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public CaratterizzazioniOst getCaratterizzazione(InfoCaratterizzazioneIn in) throws Exception {
		// non � applicabile per soggetti fisici
		if (in.getNatura() != null && in.getNatura().equals("66")) {
			throw new Exception("Metodo non compatibile con i soggetti fisici");
		}
		CaratterizzazioniOst ret = null;
		Integer idcCost = in.getIdCaratterizzazione();
		ReferencedBean risultato = getInfo(in);
		// voglio una caratterizzazione specifica
		if (idcCost != null) {
			for (CaratterizzazioniOst caratterizzazione : ((OggettiStruttureTerritoriali) risultato).getCaratterizzazioniOsts()) {
				if (caratterizzazione.getIdCcost().intValue() == idcCost.intValue()) {
					ret = caratterizzazione;
					break;
				}
			}
		} else {
			// devo ordinare le caratterizzazioni per data nel caso siano pi� di una
			List<CaratterizzazioniOst> listToOrder = Arrays.asList(((OggettiStruttureTerritoriali) risultato).getCaratterizzazioniOsts());
			if (listToOrder.size() > 1) {
				Collections.sort(listToOrder, new Comparator<CaratterizzazioniOst>() {
					@Override
					public int compare(CaratterizzazioniOst o1, CaratterizzazioniOst o2) {
						long time1 = o1.getDataInizio().getTimeInMillis();
						long time2 = o2.getDataInizio().getTimeInMillis();
						if (time1 > time2) {
							return -1;
						} else {
							if (time1 < time2) {
								return 1;
							}
						}
						return 0;
					}
				});
			}
			ret = listToOrder.get(0);
		}
		return ret;
	}
}
