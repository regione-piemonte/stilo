/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.DominioBean;
import it.eng.aurigamailbusiness.bean.EmailGroupBean;
import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TProfiliFruitoriMgoBean;
import it.eng.aurigamailbusiness.bean.TProfiliUtentiMgoBean;
import it.eng.aurigamailbusiness.converters.TAnagFruitoriCaselleToTAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.converters.TProfiliFruitoriMgoToTProfiliFruitoriMgoBean;
import it.eng.aurigamailbusiness.converters.TProfiliUtentiMgoToTProfiliUtentiMgoBean;
import it.eng.aurigamailbusiness.database.dao.DaoMailboxAccount;
import it.eng.aurigamailbusiness.database.dao.DaoTAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TFolderCaselle;
import it.eng.aurigamailbusiness.database.mail.TProfiliFruitoriMgo;
import it.eng.aurigamailbusiness.database.mail.TProfiliUtentiMgo;
import it.eng.aurigamailbusiness.database.mail.TRelUtentiVsFruitori;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaCaselleUtenteWithAccountOutput;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleInput;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleInput.FlagArrivoInvio;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleOutput;
import it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteIn;
import it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteOut;
import it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaIn;
import it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaOut;
import it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.Profilo;
import it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderIn;
import it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderOut;
import it.eng.aurigamailbusiness.database.utility.send.InputOutput;
import it.eng.aurigamailbusiness.database.utility.send.SendUtils;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.exception.ConstraintException;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.util.CheckCostraint;
import it.eng.util.DaoUtil;
import it.eng.util.ListUtil;

@Service(name = "CasellaUtility")
public class CasellaUtility {

	private static Logger mLogger = LogManager.getLogger(CasellaUtility.class);

	private static Map<String, List<String>> cacheFruitori = new ConcurrentHashMap<String, List<String>>();

	/**
	 * Restituisce come output la lista di ID delle caselle da cui l'utente in input può ricevere e/o inviare e-mail (ID_ACCOUNT della MAILBOX_ACCOUNT)
	 * 
	 * @param pListaIdCaselleInput
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "getListaCaselleUtente")
	public ListaIdCaselleOutput getListaCaselleUtente(ListaIdCaselleInput pListaIdCaselleInput) throws Exception {
		CheckCostraint.check(pListaIdCaselleInput);
		if (pListaIdCaselleInput == null)
			throw new ConstraintException(ListaIdCaselleInput.class);
		String idFruitoreEnteAoo = null;
		// Creo la sessione
		Session lSession = null;
		try {
			// Apro la sessione
			lSession = HibernateUtil.begin();
			if (!StringUtils.isEmpty(pListaIdCaselleInput.getIdEnteAOO())) {
				idFruitoreEnteAoo = decodificaIdCorrettoEnteAoo(pListaIdCaselleInput.getIdEnteAOO(), lSession);
				mLogger.debug("Id Ente AOO vale " + pListaIdCaselleInput.getIdEnteAOO());
				mLogger.debug("Id fruitore collegato ad idEnteAoo vale: " + idFruitoreEnteAoo);
			}
			// In tabella T_profili_utenti_mgo si trovano i profili. Filtro sul campo profilo in base al
			// Tipo in ingresso e flag_ann = false
			Criteria lCriteriaProfili = lSession.createCriteria(TProfiliUtentiMgo.class);
			// Flag annullato false
			lCriteriaProfili.add(Restrictions.eq("flgAnn", false));
			// Filtro i profili
			String[] lStrProfili = DaoUtil.getProfiliToSearch(pListaIdCaselleInput);
			mLogger.debug("I profili sono: ");
			for (String profilo : lStrProfili) {
				mLogger.debug(profilo);
			}
			if (lStrProfili.length > 0) {
				lCriteriaProfili.add(Restrictions.in("id.profilo", lStrProfili));
			}
			// Recupero il relativo record della TRelCaselleFruitori in join
			lCriteriaProfili.createAlias("TRelCaselleFruitori", "TRelCaselleFruitori");
			lCriteriaProfili.createAlias("TRelCaselleFruitori.TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
			if (StringUtils.isNotEmpty(pListaIdCaselleInput.getIdEnteAOO())) {
				mLogger.debug("Aggiungo la restrizione " + "TAnagFruitoriCaselle.TAnagFruitoriCaselleByIdEnteAoo.idFruitoreCasella");
				lCriteriaProfili.add(Restrictions.eq("TAnagFruitoriCaselle.TAnagFruitoriCaselleByIdEnteAoo.idFruitoreCasella", idFruitoreEnteAoo));
			}
			mLogger.debug("Creo l'alias TRelUtentiVsFruitorii.TUtentiModPec.TUtentiModPec.idUtente e valorizzo con " + pListaIdCaselleInput.getIdUtente());
			lCriteriaProfili.createAlias("TRelUtentiVsFruitori", "TRelUtentiVsFruitori");
			lCriteriaProfili.createAlias("TRelUtentiVsFruitori.TUtentiModPec", "TUtentiModPec");
			lCriteriaProfili.add(Restrictions.eq("TUtentiModPec.idUtente", pListaIdCaselleInput.getIdUtente()));
			// Se Cerco solo in invio
			List<FlagArrivoInvio> lList = pListaIdCaselleInput.getFlag();
			// Se la lista dei flag non è nulla o vuota
			if (lList != null && lList.size() > 0) {
				// Valorizzati entrambi
				if (lList.size() == 2) {
					mLogger.debug("Chiedi sia quelle per ricezione che per invio");
					lCriteriaProfili.add(Restrictions.or(
							// Metto le condizioni in or
							Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXRicezione", true),
							Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXInvio", true)));
				} else {
					if (lList.get(0).equals(FlagArrivoInvio.IN)) {
						mLogger.debug("Chiedi solo quelle in ricezione");
						// Prendo quelle per la ricezione
						lCriteriaProfili.add(Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXRicezione", true));
					} else if (lList.get(0).equals(FlagArrivoInvio.OUT)) {
						mLogger.debug("Chiedi solo quelle in invio");
						// Prendo quelle per l'invio
						lCriteriaProfili.add(Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXInvio", true));
					}
				}
			}
			// Non annullati
			lCriteriaProfili.add(Restrictions.eq("TRelCaselleFruitori.flgAnn", false));
			List<TProfiliUtentiMgo> lListProfili = lCriteriaProfili.list();
			ListaIdCaselleOutput lListaIdCaselleOutput = new ListaIdCaselleOutput();
			List<String> lListId = new ArrayList<String>();
			mLogger.debug("Le caselle collegate direttamente all'utente(tramite profilo) sono le seguenti(dimensione lista=" + lListProfili.size() + "): ");
			for (TProfiliUtentiMgo lTProfiliUtentiMgo : lListProfili) {
				mLogger.debug("Id account vale " + lTProfiliUtentiMgo.getTRelCaselleFruitori().getMailboxAccount().getIdAccount());
				lListId.add(lTProfiliUtentiMgo.getTRelCaselleFruitori().getMailboxAccount().getIdAccount());
			}
			mLogger.debug("Recupero le altre caselle");
			List<String> altreCaselle = getAltreCaselle(lSession, pListaIdCaselleInput, idFruitoreEnteAoo);
			mLogger.debug("Le altre caselle valgono");
			lListId.addAll(altreCaselle);
			for (String lStrCasella : altreCaselle) {
				mLogger.debug(lStrCasella);
			}
			lListId = (List<String>) ListUtil.distinct(lListId);
			List<InfoCasella> infos = new ArrayList<InfoCasella>();
			for (String idAccount : lListId) {
				InfoCasella infoCasella = new InfoCasella();
				infoCasella.setIdAccount(idAccount);
				infoCasella.setIsPec(verificaSeCasellaPec(idAccount));
				infos.add(infoCasella);
			}
			lListaIdCaselleOutput.setAccounts(infos);
			return lListaIdCaselleOutput;
		} catch (Exception exception) {
			mLogger.error("Errore: " + exception.getMessage(), exception);
			throw exception;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception e) {
				mLogger.error("Errore: " + e.getMessage(), e);
			}
		}
	}

	private String decodificaIdCorrettoEnteAoo(String idEnteAoo, Session session) throws AurigaMailBusinessException {
		Criteria criteriaFruitori = session.createCriteria(TAnagFruitoriCaselle.class);
		criteriaFruitori.add(Restrictions.eq("idProvFruitore", idEnteAoo));
		List<String> valoriTipo = new ArrayList<String>();
		valoriTipo.add("AOO");
		valoriTipo.add("ENTE");
		criteriaFruitori.add(Restrictions.in("tipo", valoriTipo));
		criteriaFruitori.add(Restrictions.eq("flgAttivo", true));
		List<TAnagFruitoriCaselle> fruitori = criteriaFruitori.list();
		if (fruitori.size() > 1) {
			throw new AurigaMailBusinessException("Esiste più di un fruitore di tipo ENTE o AOO per idProvFruitore=" + idEnteAoo);
		}
		if (fruitori.size() == 0) {
			throw new AurigaMailBusinessException("Non esiste nessun fruitore di tipo ENTE o AOO per idProvFruitore=" + idEnteAoo);
		}
		return fruitori.get(0).getIdFruitoreCasella();
	}

	/**
	 * verifica se una casella è PEC
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */
	private Boolean verificaSeCasellaPec(String idAccount) throws Exception {
		Properties propertiesaccount = SendUtils.getAccountProperties(idAccount);
		return (propertiesaccount.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true"));
	}

	/**
	 * ricava il fruitore casella dal dominio
	 * 
	 * @param beanIn
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "getFruitoreCasellaFromDominio")
	public TAnagFruitoriCaselleBean getFruitoreCaselleFromDominio(DominioBean beanIn) throws Exception {
		Session lSession = null;
		try {
			String idProvFruitore = beanIn.getIdDominio();
			lSession = HibernateUtil.begin();
			Criteria lCriteriaFruitori = lSession.createCriteria(TAnagFruitoriCaselle.class);
			lCriteriaFruitori.add(Restrictions.eq("idProvFruitore", idProvFruitore));
			lCriteriaFruitori.add(Restrictions.disjunction().add(Restrictions.eq("tipo", "AOO")).add(Restrictions.eq("tipo", "ENTE")));
			List<TAnagFruitoriCaselle> listaFruitori = lCriteriaFruitori.list();
			if (ListUtil.isEmpty(listaFruitori)) {
				mLogger.error("Nessuna anagrafica fruitore per l'idProv inserito");
				throw new AurigaMailBusinessException("Nessuna anagrafica fruitore per l'idProv inserito");
			}
			return (TAnagFruitoriCaselleBean) UtilPopulate.populate((TAnagFruitoriCaselle) listaFruitori.get(0), TAnagFruitoriCaselleBean.class,
					new TAnagFruitoriCaselleToTAnagFruitoriCaselleBean());
		} catch (Exception exception) {
			mLogger.error("Errore: " + exception.getMessage(), exception);
			throw exception;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception exception) {
				mLogger.error("Errore: " + exception.getMessage(), exception);
			}
		}
	}

	@Operation(name = "getListaCaselleUtenteWithAccount")
	public ListaCaselleUtenteWithAccountOutput getListaCaselleUtenteWithAccount(ListaIdCaselleInput pListaIdCaselleInput) throws Exception {
		CheckCostraint.check(pListaIdCaselleInput);
		ListaIdCaselleOutput lListaIdCaselleOutput = getListaCaselleUtente(pListaIdCaselleInput);
		DaoMailboxAccount lDaoMailboxAccount = ((DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class));
		Map<String, String> lMapAccountPeo = new LinkedHashMap<String, String>();
		Map<String, String> lMapAccountPec = new LinkedHashMap<String, String>();
		for (InfoCasella info : lListaIdCaselleOutput.getAccounts()) {
			String account = lDaoMailboxAccount.getRelatedAccount(info.getIdAccount());
			if (info.getIsPec()) {
				lMapAccountPec.put(info.getIdAccount(), account);
			} else {
				lMapAccountPeo.put(info.getIdAccount(), account);
			}
		}
		Map<String, String> lMapAccount = new LinkedHashMap<String, String>();
		lMapAccount.putAll(lMapAccountPeo);
		lMapAccount.putAll(lMapAccountPec);
		ListaCaselleUtenteWithAccountOutput listaCaselleUtenteWithAccountOutput = new ListaCaselleUtenteWithAccountOutput();
		listaCaselleUtenteWithAccountOutput.setAccounts(lMapAccount);
		return listaCaselleUtenteWithAccountOutput;
	}

	@Operation(name = "getAccountCasella")
	public InfoCasella getAccountCasella(InfoCasella casella) throws Exception {
		DaoMailboxAccount lDaoMailboxAccount = ((DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class));
		casella.setAccount(lDaoMailboxAccount.getRelatedAccount(casella.getIdAccount()));
		return casella;
	}

	/**
	 * Recupero le altre caselle in questo modo: - Recupero i fruitori dell'utente - Recupero i fruitori gerarchicamente superiori - Recupero i profili di ogni
	 * fruitore ricavato e si filtrano in base al profilo se valorizzato, diversificando i diretti da i non diretti - Recupero le caselle filtrando per utilizzo
	 * in base al flag
	 * 
	 * @param lSession
	 * @param pListaIdCaselleInput
	 * @return
	 * @throws Exception
	 */
	private List<String> getAltreCaselle(Session lSession, ListaIdCaselleInput pListaIdCaselleInput, String idFruitoreEnteAoo) throws Exception {
		// Vado sulla tabella TRelUtentiVsFruitori
		mLogger.debug("Recupero le altre caselle, cerco su TRelUtentiVsFruitori");
		Criteria lCriteriaFruitori = lSession.createCriteria(TRelUtentiVsFruitori.class);
		// Prendo quelle dell'utente non annullate
		lCriteriaFruitori.createAlias("TUtentiModPec", "TUtentiModPec");
		lCriteriaFruitori.createAlias("TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
		mLogger.debug("Restringo per TRelUtentiVsFruitori.TUtentiModPec.idUtente = " + pListaIdCaselleInput.getIdUtente());
		lCriteriaFruitori.add(Restrictions.eq("TUtentiModPec.idUtente", pListaIdCaselleInput.getIdUtente()));
		if (StringUtils.isNotEmpty(idFruitoreEnteAoo)) {
			mLogger.debug("Restringo per TRelUtentiVsFruitori.TAnagFruitoriCaselle.TAnagFruitoriCaselleByIdEnteAoo.idFruitoreCasella = " + idFruitoreEnteAoo);
			lCriteriaFruitori.add(Restrictions.eq("TAnagFruitoriCaselle.TAnagFruitoriCaselleByIdEnteAoo.idFruitoreCasella", idFruitoreEnteAoo));
		}
		lCriteriaFruitori.add(Restrictions.eq("flgAnn", false));
		List<TRelUtentiVsFruitori> lListRelFruitoriUtenti = lCriteriaFruitori.list();
		// Recupero i fruitori diretti
		mLogger.debug("I fruitori diretti sono(dimensione lista=" + lListRelFruitoriUtenti.size() + "):");
		List<String> lTAnagFruitoriCaselleDiretti = new ArrayList<String>(lListRelFruitoriUtenti.size());
		for (TRelUtentiVsFruitori lTRelUtentiVsFruitori : lListRelFruitoriUtenti) {
			mLogger.debug("Id fruitore casella: " + lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getIdFruitoreCasella());
			lTAnagFruitoriCaselleDiretti.add(lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
		// Recupero i fruitori superiori
		mLogger.debug("Recupero i fruitori superiori");
		List<String> lTAnagFruitoriCaselleSuperiori = new ArrayList<String>();
		if (lListRelFruitoriUtenti.size() > 0) {
			mLogger.debug("Ciclo i fruitori trovati prima");
			for (TRelUtentiVsFruitori lTRelUtentiVsFruitori : lListRelFruitoriUtenti) {
				mLogger.debug("*#@cerco il fruitore superiore del fruitore con id: " + lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getIdFruitoreCasella());
				String idFruitoreSup = null;
				if (lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdFruitoreSup() != null) {
					idFruitoreSup = lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdFruitoreSup().getIdFruitoreCasella();
				}
				if (idFruitoreSup != null) {
					DaoTAnagFruitoriCaselle daoFruitori = (DaoTAnagFruitoriCaselle) DaoFactory.getDao(DaoTAnagFruitoriCaselle.class);
					TAnagFruitoriCaselleBean fruitoreSupRic = daoFruitori.get(idFruitoreSup);
					while (fruitoreSupRic.getIdFruitoreSup() != null) {
						idFruitoreSup = fruitoreSupRic.getIdFruitoreCasella();
						if (lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdEnteAoo() != null) {
							mLogger.debug("Questo TRelUtentiVsFruitori ha TAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdEnteAoo() diverso da null");
							if (StringUtils.isNotEmpty(idFruitoreEnteAoo)) {
								mLogger.debug("Dovevo filtrare per idEnteAoo pari a " + idFruitoreEnteAoo);
								if (lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdFruitoreSup()
										.getTAnagFruitoriCaselleByIdEnteAoo().getIdFruitoreCasella().equals(idFruitoreEnteAoo)) {
									mLogger.debug("Questo TRelUtentiVsFruitori ha TAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdFruitoreSup()."
											+ "getTAnagFruitoriCaselleByIdEnteAoo().getIdFruitoreCasella() pari a " + idFruitoreEnteAoo
											+ " e lo aggiungo (idFruitore=" + idFruitoreSup + ")");
									lTAnagFruitoriCaselleSuperiori.add(idFruitoreSup);
								}
							} else {
								mLogger.debug("Non Dovevo filtrare per idEnteAoo e aggiungo lTRelUtentiVsFruitori.getTAnagFruitoriCaselle()"
										+ ".getTAnagFruitoriCaselleByIdFruitoreSup().getIdFruitoreCasella()(idFruitore=" + idFruitoreSup + ")");
								lTAnagFruitoriCaselleSuperiori.add(idFruitoreSup);
							}
						}
						fruitoreSupRic = daoFruitori.get(fruitoreSupRic.getIdFruitoreSup());
					}
					mLogger.debug("Aggiungo il padre di tutto (che non ha fruitori superiori) con idFruitore: " + fruitoreSupRic.getIdFruitoreCasella());
					lTAnagFruitoriCaselleSuperiori.add(fruitoreSupRic.getIdFruitoreCasella());
				}
			}
			mLogger.debug("I fruitori superiori sono");
			for (String lStrFruitore : lTAnagFruitoriCaselleSuperiori) {
				mLogger.debug(lStrFruitore);
			}
			mLogger.debug("I fruitori diretti sono");
			for (String lStrFruitore : lTAnagFruitoriCaselleDiretti) {
				mLogger.debug(lStrFruitore);
			}
		}
		List<TProfiliFruitoriMgo> lListProfili = null;
		if (lTAnagFruitoriCaselleDiretti.size() > 0 || lTAnagFruitoriCaselleSuperiori.size() > 0) {
			Criteria lCriteriaPRofiliFruitori = lSession.createCriteria(TProfiliFruitoriMgo.class);
			mLogger.debug("Creo query su TProfiliFruitoriMgo");
			lCriteriaPRofiliFruitori.createAlias("TRelCaselleFruitori", "TRelCaselleFruitori");
			lCriteriaPRofiliFruitori.createAlias("TRelCaselleFruitori.TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
			if (!lTAnagFruitoriCaselleDiretti.isEmpty() && !lTAnagFruitoriCaselleSuperiori.isEmpty()) {
				mLogger.debug("Ho fruitori diretti e superiori e quindi filtro in or su (TAnagFruitoriCaselle.idFruitoreCasella per i diretti) "
						+ " e (TAnagFruitoriCaselle.idFruitoreCasella con flgInclFruitoriSub = true per i superiori)");
				lCriteriaPRofiliFruitori.add(Restrictions.or(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleDiretti),
						Restrictions.and(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleSuperiori),
								Restrictions.eq("flgInclFruitoriSub", true))));
			} else if (!lTAnagFruitoriCaselleDiretti.isEmpty() && lTAnagFruitoriCaselleSuperiori.isEmpty()) {
				mLogger.debug("Ho solo i diretti e filtro per TAnagFruitoriCaselle.idFruitoreCasella con i diretti");
				lCriteriaPRofiliFruitori.add(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleDiretti));
			} else if (lTAnagFruitoriCaselleDiretti.isEmpty() && !lTAnagFruitoriCaselleSuperiori.isEmpty()) {
				mLogger.debug("Ho solo i superiori e filtro per TAnagFruitoriCaselle.idFruitoreCasella con i diretti");
				lCriteriaPRofiliFruitori.add(Restrictions.and(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleSuperiori),
						Restrictions.eq("flgInclFruitoriSub", true)));
			}
			mLogger.debug("flgAnn deve essere false");
			lCriteriaPRofiliFruitori.add(Restrictions.eq("flgAnn", false));
			String[] lStrProfili = DaoUtil.getProfiliToSearch(pListaIdCaselleInput);
			mLogger.debug("I profili su cui cercare sono:");
			for (String profilo : lStrProfili) {
				mLogger.debug(profilo);
			}
			if (lStrProfili.length > 0) {
				mLogger.debug("Filtro per i profili");
				lCriteriaPRofiliFruitori.add(Restrictions.in("id.profilo", lStrProfili));
			}
			// Se Cerco solo in invio
			List<FlagArrivoInvio> lList = pListaIdCaselleInput.getFlag();
			// Se la lista dei flag non è nulla o vuota
			if (lList != null && !lList.isEmpty()) {
				mLogger.debug("Filtro per utilizzo");
				// Valorizzati entrambi
				if (lList.size() == 2) {
					mLogger.debug("Filtro sia per ricezione che per invio");
					lCriteriaPRofiliFruitori.add(Restrictions.or(
							// Metto le condizioni in or
							Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXRicezione", true),
							Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXInvio", true)));
				} else {
					if (lList.get(0).equals(FlagArrivoInvio.IN)) {
						mLogger.debug("Filtro per ricezione");
						// Prendo quelle per la ricezione
						lCriteriaPRofiliFruitori.add(Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXRicezione", true));
					} else if (lList.get(0).equals(FlagArrivoInvio.OUT)) {
						mLogger.debug("Filtro per invio");
						// Prendo quelle per l'invio
						lCriteriaPRofiliFruitori.add(Restrictions.eq("TRelCaselleFruitori.flgUtilizzoXInvio", true));
					}
				}
			}
			lListProfili = lCriteriaPRofiliFruitori.list();
		} else {
			mLogger.debug("ATTENZIONE: Nessun fruitore diretto nè superiore collegato all'utente: " + pListaIdCaselleInput.getIdUtente() + " con idEnteAOO: "
					+ idFruitoreEnteAoo);
		}
		List<String> lListId = new ArrayList<String>();
		if (lListProfili != null) {
			for (TProfiliFruitoriMgo lTProfiliUtentiMgo : lListProfili) {
				if (!lTProfiliUtentiMgo.isFlgAnn()) {
					mLogger.debug("Trovato TProfiliFruitoriMgo con id " + lTProfiliUtentiMgo.getId().getIdRelFruitoreCasella() + " e con profilo "
							+ lTProfiliUtentiMgo.getId().getProfilo());
					mLogger.debug("Trovata casella:" + lTProfiliUtentiMgo.getTRelCaselleFruitori().getMailboxAccount().getIdAccount());
					if (!contains(lListId, lTProfiliUtentiMgo.getTRelCaselleFruitori().getMailboxAccount().getIdAccount())) {
						if (!lTProfiliUtentiMgo.getTRelCaselleFruitori().isFlgAnn()) {
							mLogger.debug("Aggiungo casellla alla lista");
							lListId.add(lTProfiliUtentiMgo.getTRelCaselleFruitori().getMailboxAccount().getIdAccount());
						}
					}
				}
			}
		}
		return lListId;
	}

	/**
	 * Recupero gli altri profili in questo modo : - Recupero i fruitori dell'utente - Recupero i fruitori gerarchicamente superiori - Recupero i profili di
	 * ogni fruitore ricavato e si filtrano in base al profilo se valorizzato, diversificando i diretti da i non diretti
	 * 
	 * @param lSession
	 * @param pListaIdCaselleInput
	 * @return
	 * @throws Exception
	 */
	private List<TProfiliFruitoriMgo> getAltriProfili(Session lSession, ListaProfiliUtenteSuCasellaIn pListaProfiliUtenteSuCasellaIn) throws Exception {
		// Vado sulla tabella TRelUtentiVsFruitori
		Criteria lCriteriaFruitori = lSession.createCriteria(TRelUtentiVsFruitori.class);
		// Prendo quelle dell'utente non annullate
		lCriteriaFruitori.createAlias("TUtentiModPec", "TUtentiModPec");
		lCriteriaFruitori.createAlias("TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
		lCriteriaFruitori.add(Restrictions.eq("TUtentiModPec.idUtente", pListaProfiliUtenteSuCasellaIn.getIdUtente()));
		List<TRelUtentiVsFruitori> lListRelFruitoriUtenti = lCriteriaFruitori.list();
		// Recupero i fruitori diretti
		List<String> lTAnagFruitoriCaselleDiretti = new ArrayList<String>(lListRelFruitoriUtenti.size());
		for (TRelUtentiVsFruitori lTRelUtentiVsFruitori : lListRelFruitoriUtenti) {
			lTAnagFruitoriCaselleDiretti.add(lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
		// Recupero i fruitori superiori (tutti i fruitori superiore per ereditare i profili)
		List<String> lTAnagFruitoriCaselleSuperiori = new ArrayList<String>();
		for (TRelUtentiVsFruitori lTRelUtentiVsFruitori : lListRelFruitoriUtenti) {
			if (lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdEnteAoo() != null) {
				lTAnagFruitoriCaselleSuperiori
						.add(lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdFruitoreSup().getIdFruitoreCasella());
				DaoTAnagFruitoriCaselle daoFruitori = (DaoTAnagFruitoriCaselle) DaoFactory.getDao(DaoTAnagFruitoriCaselle.class);
				if (lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdFruitoreSup() != null) {
					TAnagFruitoriCaselleBean fruitoreSup = daoFruitori
							.get(lTRelUtentiVsFruitori.getTAnagFruitoriCaselle().getTAnagFruitoriCaselleByIdFruitoreSup().getIdFruitoreCasella());
					while (fruitoreSup.getIdFruitoreSup() != null) {
						if (fruitoreSup.getIdEnteAOO() != null) {
							lTAnagFruitoriCaselleSuperiori.add(fruitoreSup.getIdFruitoreSup());
						}
						fruitoreSup = daoFruitori.get(fruitoreSup.getIdFruitoreSup());
					}
					// è il padre di tutti, ma va inserito anche se ha fruitoresup == null
					lTAnagFruitoriCaselleSuperiori.add(fruitoreSup.getIdFruitoreCasella());
				}
			}
		}
		// valorizzo la cache dei fruitori
		List<String> idFruitori = new ArrayList<String>();
		idFruitori.addAll(lTAnagFruitoriCaselleDiretti);
		idFruitori.addAll(lTAnagFruitoriCaselleSuperiori);
		cacheFruitori.put(pListaProfiliUtenteSuCasellaIn.getIdUtente(), idFruitori);
		Criteria lCriteriaPRofiliFruitori = lSession.createCriteria(TProfiliFruitoriMgo.class);
		lCriteriaPRofiliFruitori.createAlias("TRelCaselleFruitori", "TRelCaselleFruitori");
		lCriteriaPRofiliFruitori.createAlias("TRelCaselleFruitori.TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
		if (lTAnagFruitoriCaselleDiretti.size() > 0 && lTAnagFruitoriCaselleSuperiori.size() > 0) {
			lCriteriaPRofiliFruitori.add(Restrictions.or(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleDiretti),
					Restrictions.and(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleSuperiori),
							Restrictions.eq("flgInclFruitoriSub", true))));
		} else if (lTAnagFruitoriCaselleDiretti.size() > 0 && lTAnagFruitoriCaselleSuperiori.size() == 0) {
			lCriteriaPRofiliFruitori.add(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleDiretti));
		} else if (lTAnagFruitoriCaselleDiretti.size() == 0 && lTAnagFruitoriCaselleSuperiori.size() > 0) {
			lCriteriaPRofiliFruitori.add(Restrictions.and(Restrictions.in("TAnagFruitoriCaselle.idFruitoreCasella", lTAnagFruitoriCaselleSuperiori),
					Restrictions.eq("flgInclFruitoriSub", true)));
		}
		lCriteriaPRofiliFruitori.add(Restrictions.eq("flgAnn", false));
		return lCriteriaPRofiliFruitori.list();
	}

	/**
	 * Metodo che restituisce come output la lista di Id dei folder gerarchicamente stottostanti a quello in input (scendendo ricorsivamente)
	 * 
	 * @param pListaIdSubFolderIn
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "getListaIdSubFolder")
	public ListaIdSubFolderOut getListaIdSubFolder(ListaIdSubFolderIn pListaIdSubFolderIn) throws Exception {
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			CheckCostraint.check(pListaIdSubFolderIn);
			String idFolder = pListaIdSubFolderIn.getIdFolder();
			List<String> lListIdFolders = new ArrayList<String>();
			getRelatedFolder(lSession, idFolder, lListIdFolders);
			ListaIdSubFolderOut lListaIdSubFolderOut = new ListaIdSubFolderOut();
			lListaIdSubFolderOut.setFolders(lListIdFolders);
			return lListaIdSubFolderOut;
		} catch (Exception exception) {
			mLogger.error("Errore: " + exception.getMessage(), exception);
			throw exception;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception exception) {
				mLogger.error("Errore: " + exception.getMessage(), exception);
			}
		}
	}

	public void getRelatedFolder(Session lSession, String idFolder, List<String> lListFolders) {
		Criteria lCriteriaFolder = lSession.createCriteria(TFolderCaselle.class);
		lCriteriaFolder.createAlias("TFolderCaselle", "TFolderCaselle");
		lCriteriaFolder.add(Restrictions.eq("TFolderCaselle.idFolderCasella", idFolder));
		List<TFolderCaselle> lListTFolders = lCriteriaFolder.list();
		for (TFolderCaselle lTFolderCaselle : lListTFolders) {
			lListFolders.add(lTFolderCaselle.getIdFolderCasella());
			getRelatedFolder(lSession, lTFolderCaselle.getIdFolderCasella(), lListFolders);
		}
	}

	/**
	 * Restituisce come output la lista di ID dei folder di email - che corrispondono a caselle cui l'utente passato in ingresso è associato - che hanno una
	 * delle classificazioni in input o che sono folder gerarchicamente inferiori di una delle classificazioni in input
	 * 
	 * @param pListaIdFolderUtenteIn
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "getListaIdFolderUtente")
	public ListaIdFolderUtenteOut getListaIdFolderUtente(ListaIdFolderUtenteIn pListaIdFolderUtenteIn) throws Exception {
		CheckCostraint.check(pListaIdFolderUtenteIn);
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			// Recupero l'utente
			return getListaIdFolderUtenteWithSession(pListaIdFolderUtenteIn, lSession);
		} catch (Exception exception) {
			mLogger.error("Errore: " + exception.getMessage(), exception);
			throw exception;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception exception) {
				mLogger.error("Errore: " + exception.getMessage(), exception);
			}
		}

	}

	protected ListaIdFolderUtenteOut getListaIdFolderUtenteWithSession(ListaIdFolderUtenteIn pListaIdFolderUtenteIn, Session lSession) throws Exception {
		ListaIdCaselleInput lListaIdCaselleInput = new ListaIdCaselleInput();
		lListaIdCaselleInput.setIdUtente(pListaIdFolderUtenteIn.getIdUtente());
		lListaIdCaselleInput.setIdEnteAOO(pListaIdFolderUtenteIn.getIdEnteAOO());
		// Recupero la lista delle caselle collegate a quell'utente
		List<InfoCasella> caselleForUtente = getListaCaselleUtente(lListaIdCaselleInput).getAccounts();
		mLogger.debug("caselle collegate all'utente: " + pListaIdFolderUtenteIn.getIdUtente());
		List<String> classificazioniTrovate = new ArrayList<String>();
		if (caselleForUtente != null) {
			for (InfoCasella casella : caselleForUtente) {
				mLogger.debug("idCasella: " + casella.getIdAccount());
			}
			// Per ogni Casella, recupero i relativi subFolder e filtro per classificazione
			for (InfoCasella infoCasella : caselleForUtente) {
				Criteria lCriteriaFolder = lSession.createCriteria(TFolderCaselle.class);
				lCriteriaFolder.createAlias("mailboxAccount", "mailboxAccount");
				lCriteriaFolder.add(Restrictions.eq("mailboxAccount.idAccount", infoCasella.getIdAccount()));
				if (!ListUtil.isEmpty(pListaIdFolderUtenteIn.getClassificazioniFolder()))
					lCriteriaFolder.add(Restrictions.in("classificazione", pListaIdFolderUtenteIn.getClassificazioniFolder()));
				List<TFolderCaselle> lListFolderCaselle = lCriteriaFolder.list();
				for (TFolderCaselle lTFolderCaselle : lListFolderCaselle) {
					classificazioniTrovate.add(lTFolderCaselle.getIdFolderCasella());
					mLogger.debug(
							"classificazione trovata(idFoldeCasella:" + lTFolderCaselle.getIdFolderCasella() + "per la casella: " + infoCasella.getIdAccount());
				}
			}
		}
		// Per ogni folder trovata, recupero le relative subfolders
		mLogger.debug("ricavo le sottofolder");
		List<String> allFolders = new ArrayList<String>();
		for (String lStringClassificazioneTrovata : classificazioniTrovate) {
			allFolders.add(lStringClassificazioneTrovata);
			List<String> relatedSubFolders = new ArrayList<String>();
			ListaIdSubFolderIn lListaIdSubFolderIn = new ListaIdSubFolderIn();
			lListaIdSubFolderIn.setIdFolder(lStringClassificazioneTrovata);
			relatedSubFolders = getListaIdSubFolder(lListaIdSubFolderIn).getFolders();
			mLogger.debug(
					"Per la calssificazione: " + lStringClassificazioneTrovata + " ho trovato le seguenti sottofolder: " + ListUtil.print(relatedSubFolders));
			allFolders.addAll(relatedSubFolders);
		}
		// Elimino eventuali doppioni
		allFolders = (List<String>) ListUtil.distinct(allFolders);
		String[] lStrClassificazioniTrovate = allFolders.toArray(new String[] {});
		Arrays.sort(lStrClassificazioniTrovate);
		allFolders = Arrays.asList(lStrClassificazioniTrovate);
		ListaIdFolderUtenteOut lListaIdFolderUtenteOut = new ListaIdFolderUtenteOut();
		lListaIdFolderUtenteOut.setListIdFolders(allFolders);
		mLogger.debug("Per l'utente: " + pListaIdFolderUtenteIn.getIdUtente() + " ho trovato ler seguenti folder: " + ListUtil.print(allFolders));
		return lListaIdFolderUtenteOut;
	}

	@Operation(name = "getListaProfiliUtenteSuCasella")
	public ListaProfiliUtenteSuCasellaOut getListaProfiliUtenteSuCasella(ListaProfiliUtenteSuCasellaIn pListaProfiliUtenteSuCasellaIn) throws Exception {
		CheckCostraint.check(pListaProfiliUtenteSuCasellaIn);
		ListaProfiliUtenteSuCasellaOut out = new ListaProfiliUtenteSuCasellaOut();
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			// Recupero idUtente
			String idUtente = pListaProfiliUtenteSuCasellaIn.getIdUtente();
			// Recupero idCasella
			String idCasella = pListaProfiliUtenteSuCasellaIn.getIdCasella();
			Criteria lCriteria = lSession.createCriteria(TProfiliUtentiMgo.class);
			lCriteria.add(Restrictions.eq("flgAnn", false));
			lCriteria.createAlias("TRelCaselleFruitori", "TRelCaselleFruitori");
			lCriteria.createAlias("TRelCaselleFruitori.mailboxAccount", "mailboxAccount");
			lCriteria.add(Restrictions.eq("mailboxAccount.idAccount", idCasella));
			lCriteria.createAlias("TRelUtentiVsFruitori", "TRelUtentiVsFruitori");
			lCriteria.createAlias("TRelUtentiVsFruitori.TUtentiModPec", "TUtentiModPec");
			lCriteria.add(Restrictions.eq("TUtentiModPec.idUtente", idUtente));
			List<TProfiliUtentiMgo> profiliUtente = lCriteria.list();
			List<TProfiliUtentiMgoBean> listaProfiliUtente = new ArrayList<TProfiliUtentiMgoBean>();
			for (TProfiliUtentiMgo profilo : profiliUtente) {
				TProfiliUtentiMgoBean bean = (TProfiliUtentiMgoBean) UtilPopulate.populate((TProfiliUtentiMgo) profilo, TProfiliUtentiMgoBean.class,
						new TProfiliUtentiMgoToTProfiliUtentiMgoBean());
				listaProfiliUtente.add(bean);
			}
			List<TProfiliFruitoriMgo> profiliFruitori = getAltriProfili(lSession, pListaProfiliUtenteSuCasellaIn);
			List<TProfiliFruitoriMgoBean> listaProfiliFruitori = new ArrayList<TProfiliFruitoriMgoBean>();
			for (TProfiliFruitoriMgo profilo : profiliFruitori) {
				TProfiliFruitoriMgoBean bean = (TProfiliFruitoriMgoBean) UtilPopulate.populate((TProfiliFruitoriMgo) profilo, TProfiliFruitoriMgoBean.class,
						new TProfiliFruitoriMgoToTProfiliFruitoriMgoBean());
				listaProfiliFruitori.add(bean);
			}
			out.setProfiliUtente(listaProfiliUtente);
			out.setProfiliFrutiore(listaProfiliFruitori);
			return out;
		} catch (Exception exception) {
			mLogger.error("Errore: " + exception.getMessage(), exception);
			throw exception;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception exception) {
				mLogger.error("Errore: " + exception.getMessage(), exception);
			}
		}
	}

	/**
	 * metodo che partendo dall'utente verifica i profili suoi e dei fruitori a cui fa riferimento e ricava le email osservabili
	 * 
	 * @param pListaProfiliUtenteSuCasellaIn
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "getListaEmailUtenteSuCasella")
	public EmailGroupBean getListaEmailUtenteSuCasella(ListaProfiliUtenteSuCasellaIn pListaProfiliUtenteSuCasellaIn) throws Exception {
		EmailGroupBean gruppoMail = new EmailGroupBean();
		gruppoMail.setNameGroup("Email associate ad un utente");
		List<String> idEmails = new ArrayList<String>();
		ListaProfiliUtenteSuCasellaOut profili = getListaProfiliUtenteSuCasella(pListaProfiliUtenteSuCasellaIn);
		List<String> valoriProfilo = new ArrayList<String>();
		// raccolgo tutti i valori di profilo per capire se l'utente sia smistatore o destinatario per competenza
		mLogger.debug("profili utente diretti: ");
		for (TProfiliUtentiMgoBean profiloUtente : profili.getProfiliUtente()) {
			valoriProfilo.add(profiloUtente.getProfilo());
			mLogger.debug(profiloUtente.getProfilo() + "; ");
		}
		mLogger.debug("profili utente fruitore: ");
		for (TProfiliFruitoriMgoBean profiloFruitore : profili.getProfiliFrutiore()) {
			valoriProfilo.add(profiloFruitore.getProfilo());
			mLogger.debug(profiloFruitore.getProfilo() + "; ");
		}
		valoriProfilo = (List<String>) ListUtil.distinct(valoriProfilo);
		mLogger.debug("profili previsti dall'utente: " + ListUtil.print(valoriProfilo) + " sulla casella: " + pListaProfiliUtenteSuCasellaIn.getIdCasella());
		if (valoriProfilo.contains(Profilo.SMISTATORE.getValue())) {
			mLogger.debug("esiste il profilo smistatore");
			DaoTEmailMgo daoTEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<TEmailMgoBean>();
			TEmailMgoBean beanFiltro = new TEmailMgoBean();
			beanFiltro.setIdCasella(pListaProfiliUtenteSuCasellaIn.getIdCasella());
			beanFiltro.setFlgIo(InputOutput.INGRESSO.getValue());
			filterFetch.setFilter(beanFiltro);
			mLogger.debug("cerco tutte le mail in ingresso sulla casella: " + pListaProfiliUtenteSuCasellaIn.getIdCasella());
			List<TEmailMgoBean> listaEmails = daoTEmail.search(filterFetch).getData();
			mLogger.debug("mail trovate: " + (listaEmails == null ? "0" : listaEmails.size()));
			if (ListUtil.isNotEmpty(listaEmails)) {
				for (TEmailMgoBean mail : listaEmails) {
					idEmails.add(mail.getIdEmail());
				}
			}
		} else {
			if (valoriProfilo.contains(Profilo.DESTINATARIO_PER_COMPETENZA.getValue())) {
				mLogger.debug("esiste il profilo destinatario per competenza");
				DaoTEmailMgo daoTEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
				TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<TEmailMgoBean>();
				TEmailMgoBean beanFiltro = new TEmailMgoBean();
				beanFiltro.setIdUtenteAssegn(pListaProfiliUtenteSuCasellaIn.getIdUtente());
				beanFiltro.setIdCasella(pListaProfiliUtenteSuCasellaIn.getIdCasella());
				filterFetch.setFilter(beanFiltro);
				mLogger.debug("cerco tutte le mail  dell'utente sulla casella: " + pListaProfiliUtenteSuCasellaIn.getIdCasella());
				List<TEmailMgoBean> listaMailAssegnateUtente = daoTEmail.search(filterFetch).getData();
				mLogger.debug("mail trovate: " + (listaMailAssegnateUtente == null ? "0" : listaMailAssegnateUtente.size()));
				if (ListUtil.isNotEmpty(listaMailAssegnateUtente)) {
					for (TEmailMgoBean assegn : listaMailAssegnateUtente) {
						idEmails.add(assegn.getIdEmail());
					}
				}
				mLogger.debug("cerco i fruitori collegati all'utente");
				List<String> fruitoriCollegatiUtente = cacheFruitori.get(pListaProfiliUtenteSuCasellaIn.getIdUtente());
				mLogger.debug("numero fruitoriCollegatiUtente: " + fruitoriCollegatiUtente.size());
				for (String idFruitore : fruitoriCollegatiUtente) {
					TFilterFetch<TEmailMgoBean> filterFruitore = new TFilterFetch<TEmailMgoBean>();
					TEmailMgoBean filtroFruitore = new TEmailMgoBean();
					beanFiltro.setIdCasella(pListaProfiliUtenteSuCasellaIn.getIdCasella());
					filtroFruitore.setIdUoAssegn(idFruitore);
					filterFruitore.setFilter(filtroFruitore);
					List<TEmailMgoBean> listaMailAssegnateFruitore = daoTEmail.search(filterFruitore).getData();
					if (ListUtil.isNotEmpty(listaMailAssegnateFruitore)) {
						for (TEmailMgoBean assegn : listaMailAssegnateFruitore) {
							idEmails.add(assegn.getIdEmail());
						}
					}
				}
				mLogger.debug("mail trovate: " + (idEmails.isEmpty() ? "0" : idEmails.size()));
			} else {
				mLogger.debug("è solo mittente, non può vedere niente");
				// Non faccio nulla perchè non può vedere mail in arrivo essendo solamente mittente
			}
		}
		gruppoMail.setIdEmails(idEmails);
		return gruppoMail;
	}

	private boolean contains(List<String> lListIdFolders, String lStrFolderUtente) {
		for (String lString : lListIdFolders) {
			if (lString.equals(lStrFolderUtente)) {
				return true;
			}
		}
		return false;
	}
}
