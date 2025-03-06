package it.eng.dm.sira.service.insert;

import it.eng.dm.sira.dao.DaoDmtRelazioneOst;
import it.eng.dm.sira.entity.DmtRelazioneOst;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.bean.GenericOSTInsertInputBean;
import it.eng.dm.sira.service.bean.GenericOSTInsertOutputBean;
import it.eng.dm.sira.service.bean.SiraRelOstCaratterizzazione;
import it.eng.spring.utility.SpringAppContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hyperborea.sira.ws.CaratterizzazioniOst;
import com.hyperborea.sira.ws.CostNostId;
import com.hyperborea.sira.ws.FontiDati;
import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
import com.hyperborea.sira.ws.RelazioniOst;
import com.hyperborea.sira.ws.RelazioniOstId;
import com.hyperborea.sira.ws.SearchIntertematiciWSProxy;
import com.hyperborea.sira.ws.TipologieFontiDati;
import com.hyperborea.sira.ws.TipologieRost;
import com.hyperborea.sira.ws.WsBeginTransactionRequest;
import com.hyperborea.sira.ws.WsCommitTransactionRequest;
import com.hyperborea.sira.ws.WsFonteRef;
import com.hyperborea.sira.ws.WsFontiDatiRef;
import com.hyperborea.sira.ws.WsFontiDatiResponse;
import com.hyperborea.sira.ws.WsNewCcostRequest;
import com.hyperborea.sira.ws.WsNewCcostResponse;
import com.hyperborea.sira.ws.WsNewFontiDatiRequest;
import com.hyperborea.sira.ws.WsNewOstRequest;
import com.hyperborea.sira.ws.WsNewOstResponse;
import com.hyperborea.sira.ws.WsNewRostRequest;
import com.hyperborea.sira.ws.WsOstRef;
import com.hyperborea.sira.ws.WsViewOstRequest;
import com.hyperborea.sira.ws.WsViewOstResponse;

public class GenericInsertService {

	private static Logger log = Logger.getLogger(GenericInsertService.class);

	private static final String packageCcost = "com.hyperborea.sira.ws.";

	/*
	 * Metodo insertOST per il salvataggio sui catasti mediante servizi CBDA
	 * 
	 * @param	input	I dati di caratterizzazione OST da salvare
	 * @return	Il bean relativo alla caratterizzazione salvata sui Catasti
	 * @see GenericOSTInsertOutputBean
	 */
	@Deprecated
	public GenericOSTInsertOutputBean insertOST(GenericOSTInsertInputBean input) throws SiraBusinessException {
		GenericOSTInsertOutputBean output = new GenericOSTInsertOutputBean();
		try {
			// disabilito per evitare problemi di controlli su OST nuovi
			// check(input);
			// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			SearchIntertematiciWSProxy proxy = service.getCatastiProxy();
			// se l'id da storicizzare è nullo effettuo un semplice inserimento, in caso contrario effettuo una storicizzazione
			if (input.getIdOstToUpdate() == null) {
				WsOstRef ref = null;
				// INIZIO TRANSAZIONE
				WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
				proxy.beginTransaction(btreq);
				// INSERIMENTO NUOVO OST
				WsNewOstRequest ostReq = new WsNewOstRequest();
				ostReq.setCostNostId(input.getId());
				WsNewOstResponse ostResp = proxy.newOst(ostReq);
				ref = ostResp.getWsOstRef();

				// INSERIMENTO NUOVA FONTE
				WsNewFontiDatiRequest fonteRequest = new WsNewFontiDatiRequest();
				fonteRequest.setFontiDati(input.getFonti());

				// SET FONTE - OST
				fonteRequest.setWsOstRefs(0, ref);

				WsFontiDatiResponse fontiResponse = proxy.newFontiDati(fonteRequest);
				WsFontiDatiRef fonteRef = fontiResponse.getWsFontiDatiRef();

				// INSERIMENTO CARATTERIZZAZIONI
				WsNewCcostRequest costRequest = new WsNewCcostRequest();
				if (input.getCaratterizzazioni().getTipologieFontiDati() == null) {
					TipologieFontiDati tipologia = new TipologieFontiDati();
					tipologia.setCodice(7);
					tipologia.setDescrizione("tipologia GPA");
					input.getCaratterizzazioni().setTipologieFontiDati(tipologia);
				}
				if (input.getCaratterizzazioni().getDataInizio() == null) {
					input.getCaratterizzazioni().setDataInizio(Calendar.getInstance());
				}
				if (input.getCaratterizzazioni().getDenominazione() == null) {
					throw new SiraBusinessException("Impossibile inserire il nuovo OST: denominazione obbligatoria");
				}
				input.getCaratterizzazioni().setUbicazioniOst(input.getUbicazione());
				costRequest.setCaratterizzazioniOst(input.getCaratterizzazioni());
				costRequest.setWsOstRef(ref);
				costRequest.setWsFontiDatiRef(fonteRef);
				WsNewCcostResponse resp = proxy.newCcost(costRequest);
				// INSERIMENTO RELAZIONI
				if(input.getRelazioni() != null && input.getRelazioni().size() > 0) {	
					for(int i = 0; i < input.getRelazioni().size(); i++) {
						RelazioniOst rel = input.getRelazioni().get(i);						
						rel.getId().setChildOstRef(ref);																	
						WsNewRostRequest rostRequest = new WsNewRostRequest();						
						rostRequest.setRelazioniOst(rel);
						rostRequest.setWsOstRefChild(ref);						
						rostRequest.setWsOstRefParent(rel.getId().getParentOstRef());
						rostRequest.setIdTipologieRost(rel.getId().getTipologieRost().getCodice());
						rostRequest.setWsFontiDatiRef(fonteRef);
						rostRequest.setDataInizio(Calendar.getInstance());
						proxy.newRelazioniOst(rostRequest);
					}					
				}
				// INSERIMENTO STATI
				// TODO: da inserire la gestione
				// FINE TRANSAZIONE
				WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
				proxy.closeTransaction(creq);
				output.setIdOST(ref.getIdOst());
				output.setIdCCost(resp.getWsCcostRef().getIdCcost());
			}
			// effettuo una storicizzazione
			else {

			}
		} catch (Exception e) {
			log.error("Impossibile inserire il nuovo OST", e);
			throw new SiraBusinessException("Impossibile inserire il nuovo OST", e);
		}

		return output;
	}

	public WsFontiDatiRef insertNewFonte(FontiDati lFonteDati) throws SiraBusinessException {
		try {
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			SearchIntertematiciWSProxy proxy = service.getCatastiProxy();

			WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
			proxy.beginTransaction(btreq);

			WsNewFontiDatiRequest fonteRequest = new WsNewFontiDatiRequest();
			fonteRequest.setFontiDati(lFonteDati);

			WsFontiDatiResponse fontiResponse = proxy.newFontiDati(fonteRequest);

			WsFontiDatiRef fonteRef = fontiResponse.getWsFontiDatiRef();

			WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
			proxy.closeTransaction(creq);

			return fonteRef;

		} catch (Exception e) {
			log.error("Impossibile inserire la nuova fonte", e);
			throw new SiraBusinessException("Impossibile inserire la nuova fonte", e);
		}
	}

	public void updateFonte(FontiDati lFonteDati, List<WsOstRef> ostRefs) throws SiraBusinessException {
		try {
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			SearchIntertematiciWSProxy proxy = service.getCatastiProxy();

			WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
			proxy.beginTransaction(btreq);

			WsNewFontiDatiRequest fonteRequest = new WsNewFontiDatiRequest();
			fonteRequest.setFontiDati(lFonteDati);

			WsOstRef[] refs = new WsOstRef[ostRefs.size()];
			for (int i = 0; i < ostRefs.size(); i++) {
				refs[i] = ostRefs.get(i);
			}
			fonteRequest.setWsOstRefs(refs);

			proxy.newFontiDati(fonteRequest);

			WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
			proxy.closeTransaction(creq);

		} catch (Exception e) {
			log.error("Impossibile aggiornare la fonte", e);
			throw new SiraBusinessException("Impossibile aggiornare la fonte", e);
		}
	}
	
	public void insertRelazioniProcedimento(Long idProcedimento, Map<String, String> mappaObjOST, Map<String, String> mappaObjCCOST, WsFontiDatiRef fonteRef) throws SiraBusinessException {
		try {
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			
			log.error("\n######## SALVATAGGIO RELAZIONI ########\n");
			
			SearchIntertematiciWSProxy proxy = service.getCatastiProxy();
			WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
			proxy.beginTransaction(btreq);
			
			DaoDmtRelazioneOst daoDmtRelazioneOst = new DaoDmtRelazioneOst();
			List<DmtRelazioneOst> lista = daoDmtRelazioneOst.searchRelazioniByProcedimento(idProcedimento);
			
			TipologieFontiDati lTipologieFontiDati = new TipologieFontiDati();
			lTipologieFontiDati.setCodice(new Integer(1));
			lTipologieFontiDati.setDescrizione("Procedimento GPA " + idProcedimento.toString());
			
			WsFonteRef fonteRel = new WsFonteRef();
			fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
			fonteRel.setTipoFonte(1);
			
			log.error("\n-------> Procedimento: " + idProcedimento + " relazioni: " + lista.size() + "\n");
			
			for(int i = 0; i < lista.size(); i++) {
				DmtRelazioneOst relazioneItem = lista.get(i);
				log.error("-------> Rel " + (i+1) + "/" + lista.size() + ": " + relazioneItem.getIdRelazione() + " - " + relazioneItem.getEtichetta());
				
				//Convert idProcObj in IDCCOST e IDOST 
				WsOstRef ostRefParent = new WsOstRef();
				//Set Parent OST ref
				if(relazioneItem.getIdOstPadre() != null) {
					ostRefParent.setIdOst(relazioneItem.getIdOstPadre().intValue());
				} else {
					if( mappaObjOST.containsKey("" + relazioneItem.getIdObjPadre()) ) {
						ostRefParent.setIdOst(Integer.parseInt(mappaObjOST.get("" + relazioneItem.getIdObjPadre())));
						relazioneItem.setIdOstPadre(Long.parseLong(mappaObjOST.get("" + relazioneItem.getIdObjPadre())));
					} else {
						throw new SiraBusinessException("Relazione OST: Impossibile trovare uno ID OST associato all'oggetto padre " + 
								relazioneItem.getIdObjPadre() + " nel procedimento " + idProcedimento);
					}
				}
				
				log.error("------------> ostRefParent: " + ostRefParent.getIdOst());
				
				WsOstRef ostRefChild = new WsOstRef();
				if(relazioneItem.getIdOstFiglio() != null) {
					ostRefChild.setIdOst(relazioneItem.getIdOstFiglio().intValue());
				} else {
					if( mappaObjOST.containsKey("" + relazioneItem.getIdObjFiglio()) ) {
						ostRefChild.setIdOst(Integer.parseInt(mappaObjOST.get("" + relazioneItem.getIdObjFiglio())));
						relazioneItem.setIdOstFiglio(Long.parseLong(mappaObjOST.get("" + relazioneItem.getIdObjFiglio())));
					} else {
						throw new SiraBusinessException("Relazione OST: Impossibile trovare uno ID OST associato all'oggetto figlio " + 
								relazioneItem.getIdObjFiglio() + " nel procedimento " + idProcedimento);
					}
				}
				
				log.error("------------> ostRefChild: " + ostRefChild.getIdOst());
				
				Calendar calDataInizio = Calendar.getInstance();
				calDataInizio.setTime(relazioneItem.getDataInizio());
				
				log.error("------------> calDataInizio: " + calDataInizio.toString());
				
				Calendar calDataFine = null;
				if(relazioneItem.getDataFine() != null) {
					calDataFine = Calendar.getInstance();
					calDataFine.setTime(relazioneItem.getDataFine());
					
					log.error("------------> calDataInizio: " + calDataInizio.toString());
				}
				
				TipologieRost tipologiaRost = new TipologieRost();
				tipologiaRost.setCodice(relazioneItem.getTipoRelazione().intValue());
				
				log.error("------------> tipologiaRost: " + tipologiaRost.getCodice());
				
				RelazioniOstId relOstId = new RelazioniOstId(ostRefChild, calDataInizio, ostRefParent, tipologiaRost);
				
			    RelazioniOst relazioneToSave = new RelazioniOst(
		    		calDataFine, 
		    		relOstId, 
		    		relazioneItem.getUsername(), 
		    		relazioneItem.getNote(), 
		    		lTipologieFontiDati, 
		    		fonteRel
			    );
				
				if(relazioneToSave != null) { 	
		
					WsNewRostRequest rostRequest = new WsNewRostRequest();						

					rostRequest.setRelazioniOst(relazioneToSave);
					log.error("----------------> setRelazioniOst");

					rostRequest.setWsOstRefParent(ostRefParent);
					log.error("----------------> setWsOstRefParent");
					
					rostRequest.setWsOstRefChild(ostRefChild);		
					log.error("----------------> setWsOstRefChild");
	
					rostRequest.setIdTipologieRost(relazioneItem.getTipoRelazione().intValue());
					log.error("----------------> setIdTipologieRost");

					rostRequest.setWsFontiDatiRef(fonteRef);
					log.error("----------------> setWsFontiDatiRef");
					
					rostRequest.setNote(relazioneItem.getNote());
					log.error("----------------> setNote");
					
					rostRequest.setDataInizio(calDataInizio);
					log.error("----------------> setDataInizio");
					
					rostRequest.setDataFine(calDataFine);
					log.error("----------------> setDataFine");

					proxy.newRelazioniOst(rostRequest);		
					log.error("------------> newRelazioniOst");
					
					// Update riferimenti sulla tabella DMT_RELAZIONE_OST
//					daoDmtRelazioneOst.update(relazioneItem);
//					log.error("------------> daoDmtRelazioneOst.update");
					
				}
			}
			
			WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
			proxy.closeTransaction(creq);
		} catch (Exception e) {
			log.error("Impossibile inserire le relazioni", e);
			throw new SiraBusinessException("Impossibile inserire le relazioni", e);
		}
	}
	
	public void insertRelazione(RelazioniOst relazione, CostNostId id, WsFontiDatiRef fonteRef) throws SiraBusinessException {
		try {
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			SearchIntertematiciWSProxy proxy = service.getCatastiProxy();
			WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
			proxy.beginTransaction(btreq);
			WsOstRef ref = null;
			WsNewOstRequest ostReq = new WsNewOstRequest();
			ostReq.setCostNostId(id);
			WsNewOstResponse ostResp = proxy.newOst(ostReq);
			ref = ostResp.getWsOstRef();
			
			// INSERIMENTO RELAZIONI
			//if(input.getRelazioni() != null && input.getRelazioni().size() > 0) {	
			if(relazione !=null) { 	
				//for(int i = 0; i < input.getRelazioni().size(); i++) {
					//RelazioniOst rel = input.getRelazioni().get(i);						
					//rel.getId().setChildOstRef(ref);	
					relazione.getId().setChildOstRef(ref);
					WsNewRostRequest rostRequest = new WsNewRostRequest();						
					//rostRequest.setRelazioniOst(rel);
					rostRequest.setRelazioniOst(relazione);
					rostRequest.setWsOstRefChild(ref);						
					//rostRequest.setWsOstRefParent(rel.getId().getParentOstRef());
					//rostRequest.setIdTipologieRost(rel.getId().getTipologieRost().getCodice());
					
					rostRequest.setWsOstRefParent(relazione.getId().getParentOstRef());
//					rostRequest.setIdTipologieRost(relazione.getId().getTipologieRost().getCodice()); //-->giusto?
					rostRequest.setIdTipologieRost(2);
					WsFontiDatiRef wsFontiDatiRef = new WsFontiDatiRef();
					//wsFontiDatiRef.setIdFontiDati(fonteRef.getIdFontiDati()); //-->giusto??
					wsFontiDatiRef.setIdFontiDati(420026918);
					//rostRequest.setWsFontiDatiRef(fonteRef);
					rostRequest.setWsFontiDatiRef(wsFontiDatiRef);
					//rostRequest.setDataInizio(Calendar.getInstance());
					String string = "2015-05-09";
					String pattern = "yyyy-MM-dd";
					Date date = new SimpleDateFormat(pattern).parse(string);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					rostRequest.setDataInizio(calendar);
					proxy.newRelazioniOst(rostRequest);
				//}					
			}
			WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
			proxy.closeTransaction(creq);
		} catch (Exception e) {
			log.error("Impossibile inserire la relazione", e);
			throw new SiraBusinessException("Impossibile inserire la relazione", e);
		}
	}

	/*
	 * Overload metodo insertOST per il salvataggio della caratterizzazione mediante servizi CBDA e 
	 * il set della fonte dati (popolamento lista riferimenti OST)
	 * 
	 * @param	input	I dati di caratterizzazione OST da salvare
	 * @param	ostRefs La lista di riferimenti OST da utilizzarsi per l'update della Fonte
	 * @return	Il bean relativo alla caratterizzazione salvata sui Catasti
	 * @see GenericOSTInsertOutputBean
	 */
	public GenericOSTInsertOutputBean insertOST(GenericOSTInsertInputBean input, List<WsOstRef> ostRefs, WsFontiDatiRef fonteRef)
			throws SiraBusinessException {
		GenericOSTInsertOutputBean output = new GenericOSTInsertOutputBean();
		try {
			// disabilito per evitare problemi di controlli su OST nuovi
			// check(input);
			// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			SearchIntertematiciWSProxy proxy = service.getCatastiProxy();
			// se l'id da storicizzare è nullo effettuo un semplice inserimento, in caso contrario effettuo una storicizzazione
			if (input.getIdOstToUpdate() == null) {
				// INIZIO TRANSAZIONE
				WsOstRef ref = null;
				WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
				proxy.beginTransaction(btreq);
				// INSERIMENTO NUOVO OST
				WsNewOstRequest ostReq = new WsNewOstRequest();
				ostReq.setCostNostId(input.getId());
				WsNewOstResponse ostResp = proxy.newOst(ostReq);
				ref = ostResp.getWsOstRef();

				// AGGIUNGI RIFERIMENTO OST ALLA LISTA
				ostRefs.add(ref);

				// INSERIMENTO CARATTERIZZAZIONI
				WsNewCcostRequest costRequest = new WsNewCcostRequest();
				if (input.getCaratterizzazioni().getTipologieFontiDati() == null) {
					TipologieFontiDati tipologia = new TipologieFontiDati();
					tipologia.setCodice(7);
					tipologia.setDescrizione("tipologia GPA");
					input.getCaratterizzazioni().setTipologieFontiDati(tipologia);
				}
				if (input.getCaratterizzazioni().getDataInizio() == null) {
					input.getCaratterizzazioni().setDataInizio(Calendar.getInstance());
				}
				if (input.getCaratterizzazioni().getDenominazione() == null) {
					throw new SiraBusinessException("Impossibile inserire il nuovo OST: denominazione obbligatoria");
				}
				input.getCaratterizzazioni().setUbicazioniOst(input.getUbicazione());
				costRequest.setCaratterizzazioniOst(input.getCaratterizzazioni());
				costRequest.setWsOstRef(ref);
				costRequest.setWsFontiDatiRef(fonteRef);
				WsNewCcostResponse resp = proxy.newCcost(costRequest);
				// INSERIMENTO RELAZIONI
				if(input.getRelazioni() != null && input.getRelazioni().size() > 0) {	
					for(int i = 0; i < input.getRelazioni().size(); i++) {
						RelazioniOst rel = input.getRelazioni().get(i);						
						rel.getId().setChildOstRef(ref);						
						WsNewRostRequest rostRequest = new WsNewRostRequest();						
						rostRequest.setRelazioniOst(rel);
						rostRequest.setWsOstRefChild(ref);						
						rostRequest.setWsOstRefParent(rel.getId().getParentOstRef());
						rostRequest.setIdTipologieRost(rel.getId().getTipologieRost().getCodice());
						rostRequest.setWsFontiDatiRef(fonteRef);
						rostRequest.setDataInizio(Calendar.getInstance());
						proxy.newRelazioniOst(rostRequest);
					}					
				}
				// INSERIMENTO STATI
				// TODO: da inserire la gestione
				// FINE TRANSAZIONE
				WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
				proxy.closeTransaction(creq);
				output.setIdOST(ref.getIdOst());
				output.setIdCCost(resp.getWsCcostRef().getIdCcost());
			}
			// storicizzo
			else {
				// INIZIO TRANSAZIONE
				WsBeginTransactionRequest btreq = new WsBeginTransactionRequest();
				proxy.beginTransaction(btreq);
				// carico l'ost di riferimento
				WsViewOstRequest requestView = new WsViewOstRequest();
				WsOstRef ref = new WsOstRef();
				ref.setCostId(input.getId().getCodCost());
				ref.setNostId(input.getId().getCodNost());
				ref.setIdOst(input.getIdOstToUpdate());
				requestView.setWsOstRef(ref);
				WsViewOstResponse viewResponse = proxy.viewOst(requestView);
				OggettiStruttureTerritoriali ost = viewResponse.getOst();
				CaratterizzazioniOst[] caratterizzazioni = ost.getCaratterizzazioniOsts();
				// chiudo tutte le caratterizzazioni attive
				for (CaratterizzazioniOst caratterizzazione : caratterizzazioni) {
					if (caratterizzazione.getDataFine() == null) {
						caratterizzazione.setDataFine(Calendar.getInstance());
					}
					// INSERIMENTO NUOVA CARATTERIZZAZIONE
					WsNewCcostRequest costRequest = new WsNewCcostRequest();
					if (input.getCaratterizzazioni().getTipologieFontiDati() == null) {
						TipologieFontiDati tipologia = new TipologieFontiDati();
						tipologia.setCodice(7);
						tipologia.setDescrizione("tipologia GPA");
						input.getCaratterizzazioni().setTipologieFontiDati(tipologia);
					}
					if (input.getCaratterizzazioni().getDataInizio() == null) {
						input.getCaratterizzazioni().setDataInizio(Calendar.getInstance());
					}
					if (input.getCaratterizzazioni().getDenominazione() == null) {
						throw new SiraBusinessException("Impossibile inserire il nuovo OST: denominazione obbligatoria");
					}
					input.getCaratterizzazioni().setUbicazioniOst(input.getUbicazione());
					costRequest.setCaratterizzazioniOst(input.getCaratterizzazioni());
					costRequest.setWsOstRef(ref);
					costRequest.setWsFontiDatiRef(fonteRef);
					WsNewCcostResponse resp = proxy.newCcost(costRequest);
					WsCommitTransactionRequest creq = new WsCommitTransactionRequest();
					proxy.closeTransaction(creq);
					output.setIdOST(ref.getIdOst());
					output.setIdCCost(resp.getWsCcostRef().getIdCcost());
				}
			}
		} catch (Exception e) {
			log.error("Impossibile inserire il nuovo OST", e);
			throw new SiraBusinessException("Impossibile inserire il nuovo OST", e);
		}
		return output;
	}

	// check di consistenza per verificare se l'input contiene le dipendenze corrette
	public void check(GenericOSTInsertInputBean input) throws SiraBusinessException {
		if (input.getUbicazione() == null) {
			throw new SiraBusinessException("Impossibile effettuare l'inserimento, manca il campo obbligatorio ubicazione");
		}
		try {
			Integer natura = input.getId().getCodNost();
			Integer categoria = input.getId().getCodCost();
			SiraRelOstCaratterizzazione relazione = SiraRelOstCaratterizzazione.getValueForNaturaCategoria(natura, categoria);
			String caratterizzazioneCat = relazione.getCaratterizzazioneCategoria();
			String caratterizzazioneNat = relazione.getCaratterizzazioneNatura();
			CaratterizzazioniOst caratt = input.getCaratterizzazioni();
			Object fromStart = null;
			if (!(StringUtils.isEmpty(caratterizzazioneNat))) {
				String caratt1 = null;
				String caratt2 = null;
				if (caratterizzazioneNat.contains(".")) {
					caratt1 = caratterizzazioneNat.substring(0, caratterizzazioneNat.indexOf("."));
					caratt2 = caratterizzazioneNat.substring(caratterizzazioneNat.indexOf(".") + 1);
				} else {
					caratt1 = caratterizzazioneNat;
				}
				String name = null;
				Field[] fields = caratt.getClass().getDeclaredFields();
				for (Field field : fields) {
					String dataType = field.getType().getName();
					if (dataType.equals(packageCcost + caratt1)) {
						name = field.getName();
						break;
					}
				}
				if (name != null) {
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					Method method = caratt.getClass().getDeclaredMethod("get" + name, new Class[0]);
					fromStart = method.invoke(caratt, new Object[0]);
					if (fromStart == null) {
						throw new SiraBusinessException("Il bean di input non contiene la caratterizzazione: " + caratterizzazioneCat
								+ " obbligatoria");
					}
					log.debug(fromStart.getClass().getName() + " valorizzato");
					name = null;
					if (caratt2 != null) {
						Field[] campi = fromStart.getClass().getDeclaredFields();
						for (Field campo : campi) {
							String dataType = campo.getType().getName();
							if (dataType.equals(packageCcost + caratt2)) {
								name = campo.getName();
								break;
							}
						}
						if (name != null) {
							name = name.substring(0, 1).toUpperCase() + name.substring(1);
							Method metodo = fromStart.getClass().getDeclaredMethod("get" + name, new Class[0]);
							Object ret = metodo.invoke(fromStart, new Object[0]);
							if (ret == null) {
								throw new SiraBusinessException("Il bean di input non contiene la caratterizzazione: "
										+ caratterizzazioneCat + " obbligatoria");
							}
							fromStart = ret;
							log.debug(fromStart.getClass().getName() + " valorizzato");
						}
					}
				}
				if (!(StringUtils.isEmpty(caratterizzazioneCat) || caratterizzazioneCat.equalsIgnoreCase("CaratterizzazioniOst"))) {
					name = null;
					fields = fromStart.getClass().getDeclaredFields();
					for (Field field : fields) {
						String dataType = field.getType().getName();
						if (dataType.equals(packageCcost + caratterizzazioneCat)) {
							name = field.getName();
							break;
						}
					}
					if (name != null) {
						name = name.substring(0, 1).toUpperCase() + name.substring(1);
						Method method = fromStart.getClass().getDeclaredMethod("get" + name, new Class[0]);
						Object ris = method.invoke(fromStart, new Object[0]);
						if (ris == null) {
							throw new SiraBusinessException("Il bean di input non contiene la caratterizzazione: " + caratterizzazioneCat
									+ " obbligatoria");
						}
						log.debug(ris.getClass().getName() + " valorizzato");
					}
				}
			}
		} catch (Exception e) {
			throw new SiraBusinessException("Impossibile effettuare verifica sull'OST da inserire", e);
		}
	}

}
