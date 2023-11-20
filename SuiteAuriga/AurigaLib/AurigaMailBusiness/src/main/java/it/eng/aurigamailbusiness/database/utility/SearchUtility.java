/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.google.common.collect.Lists;

import it.eng.aurigamailbusiness.bean.EmailGroupBean;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TCommentiEmail;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailVsUtenti;
import it.eng.aurigamailbusiness.database.mail.TFolderCaselle;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolder;
import it.eng.aurigamailbusiness.database.mail.TRelUtentiVsFruitori;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleInput;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleOutput;
import it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteIn;
import it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaIn;
import it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderIn;
import it.eng.aurigamailbusiness.database.utility.bean.lucene.LuceneBeanResult;
import it.eng.aurigamailbusiness.database.utility.bean.search.AssegnatarioType;
import it.eng.aurigamailbusiness.database.utility.bean.search.EmailResultBean;
import it.eng.aurigamailbusiness.database.utility.bean.search.FilterSearch;
import it.eng.aurigamailbusiness.database.utility.bean.search.PresenzaCommenti;
import it.eng.aurigamailbusiness.database.utility.bean.search.SearchIn;
import it.eng.aurigamailbusiness.database.utility.bean.search.SearchOut;
import it.eng.aurigamailbusiness.database.utility.bean.search.TipiNofificaInteropRicevute;
import it.eng.aurigamailbusiness.database.utility.converters.TEmailMgoToEmailResultBean;
import it.eng.aurigamailbusiness.database.utility.send.InputOutput;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.exception.ConstraintException;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.CheckCostraint;
import it.eng.util.ListUtil;

/**
 * 
 * Classe di utility per la ricerca sulla email
 * 
 * @author Rametta
 * 
 */
@Service(name = "SearchUtility")
public class SearchUtility {

	private static Logger mLogger = LogManager.getLogger(SearchUtility.class);

	private final static int MINIMUM_LENGTH_TERM = 4;

	private final static int MAXIMUM_EXPRESSION_NUMBER = 1000;

	/**
	 * API di ricerca sulla mail. Vengono individate prima le folders su cui fare la ricerca, successivamente si esegue la ricerca mediante Lucene.
	 * Successivamente si filtra sulle email.
	 * 
	 * @param pSearchIn
	 *            Insieme delle condizioni con le quali fare la ricerca
	 * @return un oggetto di tipo {@link SearchOut} che contiene i risultati
	 * @throws Exception
	 */
	//
	@Operation(name = "search")
	public SearchOut search(SearchIn pSearchIn) throws Exception {
		mLogger.debug("cerco con la seguente utenza: " + pSearchIn.getIdUtente() + " e il seguente enteAoo: " + pSearchIn.getIdEnteAOO());
		CheckCostraint.check(pSearchIn);
		Session lSession = null;
		Criteria lCriteria = null;
		SearchOut lSearchOut = new SearchOut();
		try {
			lSession = HibernateUtil.begin();
			// ricavo le folder in cui io possa cercare
			List<String> folderToSearch = retrieveFolderToSearch(lSession, pSearchIn);
			// se non ci sono folders in cui cercare è inutile eseguire qualsiasi logica
			// non sarà possibile accedere ad alcuna email
			if (!ListUtil.isEmpty(folderToSearch)) {
				mLogger.debug("Eseguo la ricerca sui folders " + ListUtil.print(folderToSearch));
				// Recupero il max num
				BigDecimal lBigDecimalNumMax = ParameterUtility.getMaxNumForRicerca();
				mLogger.debug("Il numero massimo di record estraibili è " + lBigDecimalNumMax);
				// Effettuo la ricerca su Lucene o filtro per dati ulteriori
				if (pSearchIn.getFilter() != null) {
					mLogger.debug("Verifico se effettuare la ricerca su lucene");
					// Se la lista dei termini è vuota, salto la ricerca su lucene
					if (ListUtil.isEmpty(pSearchIn.getFilter().getTerms())) {
						// Se non ho specificato folder o classificazioni, vado in errore
						if (ListUtil.isEmpty(pSearchIn.getFolders()) && ListUtil.isEmpty(pSearchIn.getClassificazioniFolder())) {
							throw new Exception("Restringere i criteri di ricerca");
						}
						mLogger.debug("Ricerca solo su db");
						// filtro per i criteri che possono essere inseriti all'interno del filtro
						// rimanendo sempre nelle folder in cui posso cercare
						lCriteria = buildHibernateCriteria(pSearchIn, lSession, folderToSearch, null);
						mLogger.debug("numero email trovate :" + lCriteria.list().size());
					} else {
						for (String term : pSearchIn.getFilter().getTerms()) {
							if (term.length() < MINIMUM_LENGTH_TERM) {
								throw new AurigaMailBusinessException("Le parole da ricercare devono contenere almeno " + MINIMUM_LENGTH_TERM + " caratteri");
							}
						}
						mLogger.debug("Effettuo la ricerca su Lucene");
						FilterSearch lFilterSearch = pSearchIn.getFilter();
						LuceneSearch lLuceneSearch = (LuceneSearch) SpringAppContext.getContext().getBean("LuceneSearch");
						List<LuceneBeanResult> luceneResults = lLuceneSearch.search(lFilterSearch.getTerms(), folderToSearch, lFilterSearch.getFlag(),
								lFilterSearch.getAttributi(), lBigDecimalNumMax);
						mLogger.debug("numero risultati lucene: " + luceneResults.size());
						lCriteria = buildHibernateCriteria(pSearchIn, lSession, folderToSearch, luceneResults);
						mLogger.debug("numero email trovate: " + lCriteria.list().size());
					}

				} else {
					if (ListUtil.isEmpty(pSearchIn.getFolders()) && ListUtil.isEmpty(pSearchIn.getClassificazioniFolder())) {
						throw new Exception("Restringere i criteri di ricerca");
					}
					mLogger.debug("Ricerca solo su db");
					lCriteria = buildHibernateCriteria(pSearchIn, lSession, folderToSearch, null);
					mLogger.debug("numero email trovate :" + lCriteria.list().size());
				}
				lCriteria.setProjection(Projections.distinct(Projections.property("TEmailMgo.idEmail")));
				List<String> mailTrovate = lCriteria.list();
				if (mailTrovate == null || mailTrovate.size() == 0) {
					mLogger.debug("Nessuna mail trovata");
					lSearchOut.setEmails(new ArrayList<EmailResultBean>());
					return lSearchOut;
				}
				mLogger.debug("numero email trovate distinte :" + mailTrovate.size());
				mLogger.debug("mail trovate " + ListUtil.print(mailTrovate));
				List<TEmailMgo> lListEmails = new ArrayList<TEmailMgo>();
				if (mailTrovate.size() < 1000) {
					mLogger.debug("numero mail inferiore a 1000");
					Criteria lCriteriaResult = lSession.createCriteria(TEmailMgo.class);
					lCriteriaResult.add(Restrictions.in("idEmail", mailTrovate));
					lListEmails = lCriteriaResult.list();
				} else {
					mLogger.debug("numero mail superiore a 1000, divido in gruppi");
					List<List<String>> gruppiMail = Lists.partition(mailTrovate, MAXIMUM_EXPRESSION_NUMBER);
					for (List<String> gruppo : gruppiMail) {
						Criteria lCriteriaMail = lSession.createCriteria(TEmailMgo.class);
						lCriteriaMail.add(Restrictions.in("idEmail", gruppo));
						List<TEmailMgo> parziale = lCriteriaMail.list();
						if (parziale != null && parziale.size() > 0) {
							lListEmails.addAll(parziale);
						}
					}
				}
				TEmailMgoToEmailResultBean lTEmailMgoToEmailResultBean = new TEmailMgoToEmailResultBean();
				lTEmailMgoToEmailResultBean.setSession(lSession);
				List<TAnagFruitoriCaselle> lListFruitori = new ArrayList<TAnagFruitoriCaselle>();
				List<TRelUtentiVsFruitori> lListRelazioneFruitori = getListFruitori(pSearchIn, lSession);
				if (lListRelazioneFruitori != null && lListRelazioneFruitori.size() > 0) {
					for (TRelUtentiVsFruitori lTRelUtentiVsFruitori : lListRelazioneFruitori) {
						lListFruitori.add(lTRelUtentiVsFruitori.getTAnagFruitoriCaselle());
					}
				}
				lTEmailMgoToEmailResultBean.setlListFruitori(lListFruitori);
				List<EmailResultBean> lListToReturn = UtilPopulate.populateList(lListEmails, EmailResultBean.class, lTEmailMgoToEmailResultBean);
				List<EmailResultBean> listaFinale = null;
				if (pSearchIn.getClassificazioniFolder() != null && pSearchIn.getClassificazioniFolder().size() == 1
						&& !pSearchIn.getClassificazioniFolder().get(0).contains("invio")) {
					mLogger.debug("ricerca mail in arrivo, per cui effettuo profilatura utente");
					// provo a filtrare a valle del tutto
					CasellaUtility cu = new CasellaUtility();
					// ottengo le caselle visibili per l'utente a seconda del suo profilo
					ListaIdCaselleInput input = new ListaIdCaselleInput();
					input.setIdEnteAOO(pSearchIn.getIdEnteAOO());
					input.setIdUtente(pSearchIn.getIdUtente());
					ListaIdCaselleOutput output = cu.getListaCaselleUtente(input);
					List<String> listaCaselle = new ArrayList<String>();
					for (InfoCasella casella : output.getAccounts()) {
						listaCaselle.add(casella.getIdAccount());
					}
					// cerco le mail visibili per le caselle previste
					// ma solo se si tratta di posta in arrivo
					List<String> mailVisibili = new ArrayList<String>();
					for (String casella : listaCaselle) {
						ListaProfiliUtenteSuCasellaIn in = new ListaProfiliUtenteSuCasellaIn();
						in.setIdCasella(casella);
						in.setIdUtente(pSearchIn.getIdUtente());
						EmailGroupBean group = cu.getListaEmailUtenteSuCasella(in);
						if (group.getIdEmails() != null) {
							mailVisibili.addAll(group.getIdEmails());
						}
					}
					mLogger.debug("mail visibili " + ListUtil.print(mailVisibili));
					// inserisco solo le email che posso vedere
					listaFinale = new ArrayList<EmailResultBean>();
					for (EmailResultBean email : lListToReturn) {
						if (mailVisibili.contains(email.getIdEmail())) {
							listaFinale.add(email);
						}
					}
					mLogger.debug("numero email consultabili :" + listaFinale.size());
				} else {
					mLogger.debug("ricerca mail in uscita, per cui NON effettuo alcuna  profilatura ");
					listaFinale = lListToReturn;
				}
				List<EmailResultBean> listaFinaleFiltroUscita = new ArrayList<EmailResultBean>();
				// devo vedere solo le mail in uscita che ho inserito io, controllo che
				// l'utente di inserimento sia lo stesso dell'interrogazione
				// se l'utente di inserimento è nullo faccio comunque vedere la mail
				for (EmailResultBean mailFinale : listaFinale) {
					if (mailFinale.getFlgIo().equals(InputOutput.USCITA.getValue())) {
						if (mailFinale.getIdUteIns() != null) {
							if (mailFinale.getIdUteIns().equalsIgnoreCase(pSearchIn.getIdUtente())) {
								mLogger.debug("La mail in uscita con id: " + mailFinale.getIdEmail() + " è stata inviata dallo stesso utente: "
										+ pSearchIn.getIdUtente());
								listaFinaleFiltroUscita.add(mailFinale);
							}
						} else {
							mLogger.debug("La mail in uscita con id: " + mailFinale.getIdEmail()
									+ " ha utente di invio nullo, non verrà inserita nella lista finale");
							// listaFinaleFiltroUscita.add(mailFinale);
						}
					} else {
						mLogger.debug("La mail è una mail in entrata, nessun controllo sull'utente di invio");
						listaFinaleFiltroUscita.add(mailFinale);
					}
				}
				mLogger.debug("numero email finali con filtro per utente per le mail in uscita :" + listaFinaleFiltroUscita.size());
				lSearchOut.setEmails(listaFinaleFiltroUscita);
			}
			return lSearchOut;
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(lSession);
		}
	}

	/**
	 * Costruisce il criteria in base ai filtri
	 * 
	 * Il criteria va sulla tabella T_REL_EMAIL_FOLDER in join con T_EMAIL_MGO
	 * 
	 * @param lFilterSearch
	 * @param pSession
	 * @return
	 * @throws ConstraintException
	 */
	private Criteria buildHibernateCriteria(SearchIn pSearchIn, Session pSession, List<String> folders, List<LuceneBeanResult> luceneResults)
			throws ConstraintException {
		List<String[]> aliasesUsed = new ArrayList<String[]>();
		FilterSearch lFilterSearch = pSearchIn.getFilter();
		Criteria lCriteria = pSession.createCriteria(TRelEmailFolder.class, "TRelEmailFolder");
		if (!ListUtil.isEmpty(folders)) {
			lCriteria.add(Restrictions.in("TFolderCaselle.idFolderCasella", folders));
		}
		addAlias(lCriteria, aliasesUsed, "TEmailMgo", "TEmailMgo");
		if (luceneResults != null) {
			List<String> idEmails = new ArrayList<String>(luceneResults.size());
			for (LuceneBeanResult lLuceneBeanResult : luceneResults) {
				idEmails.add(lLuceneBeanResult.getIdEmail());
			}
			if (luceneResults.size() == 0) {
				// LA RICERCA DI LUCENE HA RISPOSTO CON ZERO RISULTATI
				// INSERISCO UNA CONDIZIONE SULL'IDEMAIL CHE NON PERMETTA
				// DI RITORNARE ALCUN RISULTATO
				// Inserisco un valore farlocco sull'idEmail in maniera tale che esso non
				// venga mai trovato e questo renda il risultato vuoto
				// TODO: patch temporanea, da pensare con più calma
				idEmails.add("dummy");
			}
			lCriteria.add(Restrictions.in("TEmailMgo.idEmail", idEmails));
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getCaselle())) {
			mLogger.debug("Filtro per idCaselle " + ListUtil.print(lFilterSearch.getCaselle()));
			addAlias(lCriteria, aliasesUsed, "TEmailMgo.mailboxAccount", "TEmailMgo.mailboxAccount");
			lCriteria.add(Restrictions.in("TEmailMgo.mailboxAccount.idAccount", lFilterSearch.getCaselle()));
		}
		if (StringUtils.isNotEmpty(lFilterSearch.getMessageId())) {
			mLogger.debug("Filtro per idMessage " + lFilterSearch.getMessageId());
			lCriteria.add(Restrictions.like("TEmailMgo.messageId", lFilterSearch.getMessageId()));
		}
		if (lFilterSearch.getTsRicezioneDa() != null) {
			mLogger.debug("Filtro per data ricezione da " + lFilterSearch.getTsRicezioneDa());
			lCriteria.add(Restrictions.ge("TEmailMgo.tsRicezione", lFilterSearch.getTsRicezioneDa()));
		}
		if (lFilterSearch.getTsRicezioneA() != null) {
			mLogger.debug("Filtro per data ricezione a " + lFilterSearch.getTsRicezioneA());
			lCriteria.add(Restrictions.le("TEmailMgo.tsRicezione", lFilterSearch.getTsRicezioneA()));
		}
		if (lFilterSearch.getInvioDa() != null) {
			mLogger.debug("Filtro per data invio da " + lFilterSearch.getInvioDa());
			lCriteria.add(Restrictions.ge("TEmailMgo.tsInvio", lFilterSearch.getInvioDa()));
		}
		if (lFilterSearch.getInvioA() != null) {
			mLogger.debug("Filtro per data invio a " + lFilterSearch.getInvioA());
			lCriteria.add(Restrictions.le("TEmailMgo.tsInvio", lFilterSearch.getInvioA()));
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getCategorie())) {
			mLogger.debug("Filtro per categorie " + ListUtil.print(lFilterSearch.getCategorie()));
			lCriteria.add(Restrictions.in("TEmailMgo.categoria", lFilterSearch.getCategorie()));
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getStatiConsolidamento())) {
			mLogger.debug("Filtro per stati consolidamento " + ListUtil.print(lFilterSearch.getStatiConsolidamento()));
			lCriteria.add(Restrictions.in("TEmailMgo.statoConsolidamento", lFilterSearch.getStatiConsolidamento()));
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getStatiSpam())) {
			mLogger.debug("Filtro per stato spam " + ListUtil.print(lFilterSearch.getStatiSpam()));
			lCriteria.add(Restrictions.in("TEmailMgo.flgStatoSpam", lFilterSearch.getStatiSpam()));
		}
		if (lFilterSearch.getPresenzaAllegati() != null) {
			mLogger.debug("Filtro per presenza allegati " + lFilterSearch.getPresenzaAllegati());
			if (lFilterSearch.getPresenzaAllegati() == 0) {
				lCriteria.add(Restrictions.eq("TEmailMgo.nroAllegati", 0));
			} else {
				lCriteria.add(Restrictions.ge("TEmailMgo.nroAllegati", 1));
			}
		}
		if (lFilterSearch.getPresenzaAllegatiFirmati() != null) {
			mLogger.debug("Filtro per presenza allegati firmati " + lFilterSearch.getPresenzaAllegatiFirmati());
			if (lFilterSearch.getPresenzaAllegatiFirmati() == 0) {
				lCriteria.add(Restrictions.eq("TEmailMgo.nroAllegatiFirmati", 0));
			} else {
				lCriteria.add(Restrictions.ge("TEmailMgo.nroAllegatiFirmati", 1));
			}
		}
		if (lFilterSearch.getDimensioneMin() != null) {
			mLogger.debug("Filtro per dimensione min " + lFilterSearch.getDimensioneMin());
			lCriteria.add(Restrictions.ge("TEmailMgo.dimensione", lFilterSearch.getDimensioneMin()));
		}
		if (lFilterSearch.getDimensioneMax() != null) {
			mLogger.debug("Filtro per dimensione max a " + lFilterSearch.getDimensioneMax());
			lCriteria.add(Restrictions.le("TEmailMgo.dimensione", lFilterSearch.getDimensioneMax()));
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getContrassegni())) {
			mLogger.debug("Filtro per contrassegni " + ListUtil.print(lFilterSearch.getContrassegni()));
			addAlias(lCriteria, aliasesUsed, "TEmailMgo.TValDizionarioByIdRecDizContrassegno", "TValDizionarioByIdRecDizContrassegno");
			// lCriteria.createAlias("TEmailMgo.TValDizionarioByIdRecDizContrassegno", "TValDizionarioByIdRecDizContrassegno");
			lCriteria.add(Restrictions.in("TValDizionarioByIdRecDizContrassegno.idRecDiz", lFilterSearch.getContrassegni()));
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getLivelliPriorita())) {
			mLogger.debug("Filtro per livelli priorita " + ListUtil.print(lFilterSearch.getLivelliPriorita()));
			lCriteria.add(Restrictions.in("TEmailMgo.livPriorita", lFilterSearch.getLivelliPriorita()));
		}
		if (lFilterSearch.getStatoLettura() != null) {
			mLogger.debug("Filtro per stato lettura pari a " + lFilterSearch.getStatoLettura());
			switch (lFilterSearch.getStatoLettura()) {
			case LETTA_DA_QUALCUNO: {
				lCriteria.add(Restrictions.isNotNull("TEmailMgo.tsLettura"));
				break;
			}
			case NON_LETTA_DA_ALCUNO: {
				lCriteria.add(Restrictions.isNull("TEmailMgo.tsLettura"));
				break;
			}
			case LETTA_DA_ME: {
				// Esiste sulla TEmailVsUtenti
				DetachedCriteria lDetachedCriteriaEmailVsUtenti = DetachedCriteria.forClass(TEmailVsUtenti.class, "TEmailVsUtenti");
				// Per il nostro idUtente
				lDetachedCriteriaEmailVsUtenti.add(Property.forName("TEmailVsUtenti.id.idUtente").eq(pSearchIn.getIdUtente()));
				// E la mail in esame
				lDetachedCriteriaEmailVsUtenti.add(Property.forName("TEmailVsUtenti.id.idEmail").eqProperty("TRelEmailFolder.TEmailMgo.idEmail"));
				// Un record sul dizionario
				lDetachedCriteriaEmailVsUtenti.createAlias("TEmailVsUtenti.TValDizionario", "TValDizionario");
				// Con valore pari a letta
				lDetachedCriteriaEmailVsUtenti.add(Restrictions.eq("TValDizionario.valore", "letta"));
				lCriteria.add(Subqueries.exists(lDetachedCriteriaEmailVsUtenti.setProjection(Projections.property("id"))));
				break;
			}
			case NON_LETTA_DA_ME: {
				// Non Esiste sulla TEmailVsUtenti
				DetachedCriteria lDetachedCriteriaEmailVsUtenti = DetachedCriteria.forClass(TEmailVsUtenti.class, "TEmailVsUtenti");
				// Per il nostro idUtente
				lDetachedCriteriaEmailVsUtenti.add(Property.forName("TEmailVsUtenti.id.idUtente").eq(pSearchIn.getIdUtente()));
				// E la mail in esame
				lDetachedCriteriaEmailVsUtenti.add(Property.forName("TEmailVsUtenti.id.idEmail").eqProperty("TRelEmailFolder.TEmailMgo.idEmail"));
				// Un record sul dizionario
				lDetachedCriteriaEmailVsUtenti.createAlias("TEmailVsUtenti.TValDizionario", "TValDizionario");
				// Con valore pari a letta
				lDetachedCriteriaEmailVsUtenti.add(Restrictions.eq("TValDizionario.valore", "letta"));
				lCriteria.add(Subqueries.notExists(lDetachedCriteriaEmailVsUtenti.setProjection(Projections.property("id"))));
				break;
			}
			}
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getStatiVsUtente())) {
			mLogger.debug("Filtro per stati " + ListUtil.print(lFilterSearch.getStatiVsUtente()));
			DetachedCriteria lDetachedCriteriaEmailVsUtenti = DetachedCriteria.forClass(TEmailVsUtenti.class, "TEmailVsUtenti");
			// Per il nostro idUtente
			lDetachedCriteriaEmailVsUtenti.add(Property.forName("TEmailVsUtenti.id.idUtente").eq(pSearchIn.getIdUtente()));
			// E la mail in esame
			lDetachedCriteriaEmailVsUtenti.add(Property.forName("TEmailVsUtenti.id.idEmail").eqProperty("TRelEmailFolder.TEmailMgo.idEmail"));
			// Un record sul dizionario
			lDetachedCriteriaEmailVsUtenti.createAlias("TEmailVsUtenti.TValDizionario", "TValDizionario");
			// Con valore pari a letta
			lDetachedCriteriaEmailVsUtenti.add(Restrictions.in("TValDizionario.valore", lFilterSearch.getStatiVsUtente()));
			lCriteria.add(Subqueries.exists(lDetachedCriteriaEmailVsUtenti.setProjection(Projections.property("id"))));
		}
		if (lFilterSearch.getRispostaInviata() != null) {
			Integer rispostaInviata = lFilterSearch.getRispostaInviata();
			mLogger.debug("Filtro per risposta Inviata " + rispostaInviata);
			if (rispostaInviata == 0) {
				lCriteria.add(Restrictions.eq("TEmailMgo.flgInviataRisposta", false));
			} else {
				lCriteria.add(Restrictions.eq("TEmailMgo.flgInviataRisposta", true));
			}
		}
		if (lFilterSearch.getEffettuatoInoltro() != null) {
			Integer effettuatoInoltro = lFilterSearch.getEffettuatoInoltro();
			mLogger.debug("Filtro per effettuato inoltro " + effettuatoInoltro);
			if (effettuatoInoltro == 0) {
				lCriteria.add(Restrictions.eq("TEmailMgo.flgInoltrata", false));
			} else {
				lCriteria.add(Restrictions.eq("TEmailMgo.flgInoltrata", true));
			}
		}
		if (lFilterSearch.getStatoAssegnazione() != null) {
			Integer statoAssegnazione = lFilterSearch.getStatoAssegnazione();
			mLogger.debug("Filtro per stati assegnazione " + statoAssegnazione);
			addAlias(lCriteria, aliasesUsed, "TEmailMgo.TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
			// lCriteria.createAlias("TEmailMgo.TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
			addAlias(lCriteria, aliasesUsed, "TEmailMgo.TUtentiModPecByIdUtenteLock", "TUtentiModPecByIdUtenteLock");
			// lCriteria.createAlias("TEmailMgo.TUtentiModPecByIdUtenteLock", "TUtentiModPecByIdUtenteLock");
			if (statoAssegnazione == 0) {
				lCriteria.add(Restrictions.isNull("TAnagFruitoriCaselle.idFruitoreCasella"));
				lCriteria.add(Restrictions.isNull("TUtentiModPecByIdUtenteLock.idUtente"));
			} else {
				lCriteria.add(Restrictions.or(Restrictions.isNotNull("TAnagFruitoriCaselle.idFruitoreCasella"),
						Restrictions.isNotNull("TUtentiModPecByIdUtenteLock.idUtente")));
			}
		}
		if (lFilterSearch.getAssegnatario() != null) {
			CheckCostraint.check(lFilterSearch.getAssegnatario());
			String assegnatario = lFilterSearch.getAssegnatario().getIdAssegnatario();
			AssegnatarioType lAssegnatarioType = lFilterSearch.getAssegnatario().getType();
			mLogger.debug("Filtro per tipo " + lAssegnatarioType + " e id " + assegnatario);
			switch (lAssegnatarioType) {
			case UTENTE: {
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
				lCriteria.add(Restrictions.eq("TAnagFruitoriCaselle.idFruitoreCasella", assegnatario));
				break;
			}
			case UO: {
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TUtentiModPecByIdUtenteLock", "TUtentiModPecByIdUtenteLock");
				lCriteria.add(Restrictions.eq("TUtentiModPecByIdUtenteLock.idUtente", assegnatario));
				break;
			}
			}
		}
		if (lFilterSearch.getStatoProtocollazione() != null) {
			mLogger.debug("Filtro per stato protocollazione " + lFilterSearch.getStatoProtocollazione());
			switch (lFilterSearch.getStatoProtocollazione()) {
			case NON_PROTOCOLLATA: {
				lCriteria.add(Restrictions.isNull("TEmailMgo.flgStatoProt"));
				break;
			}
			case PROTOCOLLATA: {
				lCriteria.add(Restrictions.isNotNull("TEmailMgo.flgStatoProt"));
				break;
			}
			case PROTOCOLLATA_CON_TUTTI_GLI_ALLEGATI: {
				lCriteria.add(Restrictions.eq("TEmailMgo.flgStatoProt", lFilterSearch.getStatoProtocollazione().getValore()));
				break;
			}
			case PROTOCOLLATI_SOLO_ALCUNI_ALLEGATI: {
				lCriteria.add(Restrictions.eq("TEmailMgo.flgStatoProt", lFilterSearch.getStatoProtocollazione().getValore()));
				break;
			}
			}
		}
		if (lFilterSearch.getEstremiRegistrazione() != null) {
			String categoriaReg = lFilterSearch.getEstremiRegistrazione().getCategoriaReg();
			String siglaReg = lFilterSearch.getEstremiRegistrazione().getSiglaReg();
			Integer annoReg = lFilterSearch.getEstremiRegistrazione().getAnnoReg();
			Integer numReg = lFilterSearch.getEstremiRegistrazione().getNumReg();
			if (StringUtils.isNotEmpty(categoriaReg)) {
				mLogger.debug("Filtro per categoria : " + categoriaReg);
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegProtVsEmails", "TRegProtVsEmail");
				lCriteria.add(Restrictions.eq("TRegProtVsEmail.categoriaReg", categoriaReg));
			}
			if (StringUtils.isNotEmpty(siglaReg)) {
				mLogger.debug("Filtro per categoria : " + siglaReg);
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegProtVsEmails", "TRegProtVsEmail");
				lCriteria.add(Restrictions.eq("TRegProtVsEmail.siglaRegistro", siglaReg));
			}
			if (annoReg != null) {
				mLogger.debug("Filtro per categoria : " + annoReg);
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegProtVsEmails", "TRegProtVsEmail");
				lCriteria.add(Restrictions.eq("TRegProtVsEmail.annoReg", annoReg.shortValue()));
			}
			if (numReg != null) {
				mLogger.debug("Filtro per categoria : " + numReg);
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegProtVsEmails", "TRegProtVsEmail");
				lCriteria.add(Restrictions.eq("TRegProtVsEmail.numReg", numReg));
			}
		}
		if (lFilterSearch.getTsRegistrazioneDa() != null) {
			mLogger.debug("Filtro per data registrazione da da " + lFilterSearch.getTsRegistrazioneDa());
			addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegProtVsEmails", "TRegProtVsEmail");
			lCriteria.add(Restrictions.ge("TRegProtVsEmail.tsReg", lFilterSearch.getTsRegistrazioneDa()));
		}
		if (lFilterSearch.getTsRegistrazioneA() != null) {
			mLogger.debug("Filtro per data registrazione a " + lFilterSearch.getTsRegistrazioneA());
			addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegProtVsEmails", "TRegProtVsEmail");
			lCriteria.add(Restrictions.le("TRegProtVsEmail.tsReg", lFilterSearch.getTsRegistrazioneA()));
		}
		if (lFilterSearch.getEstremiRegistrazioneRic() != null) {
			String siglaReg = lFilterSearch.getEstremiRegistrazioneRic().getSiglaReg();
			Integer annoReg = lFilterSearch.getEstremiRegistrazioneRic().getAnnoReg();
			Integer numReg = lFilterSearch.getEstremiRegistrazioneRic().getNumReg();
			if (StringUtils.isNotEmpty(siglaReg)) {
				mLogger.debug("Filtro per categoria : " + siglaReg);
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegEstVsEmailsForIdEmailRicevuta", "TRegEstVsEmail");
				lCriteria.add(Restrictions.eq("TRegEstVsEmail.siglaRegistro", siglaReg));
			}
			if (annoReg != null) {
				mLogger.debug("Filtro per categoria : " + annoReg);
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegEstVsEmailsForIdEmailRicevuta", "TRegEstVsEmail");
				lCriteria.add(Restrictions.eq("TRegEstVsEmail.annoReg", annoReg.shortValue()));
			}
			if (numReg != null) {
				mLogger.debug("Filtro per categoria : " + numReg);
				addAlias(lCriteria, aliasesUsed, "TEmailMgo.TRegEstVsEmailsForIdEmailRicevuta", "TRegEstVsEmail");
				lCriteria.add(Restrictions.eq("TRegEstVsEmail.numReg", numReg));
			}
		}
		if (StringUtils.isNotEmpty(lFilterSearch.getIdVoceRubricaDestinatario())) {
			mLogger.debug("Filtro per id voce rubrica destinatario " + lFilterSearch.getIdVoceRubricaDestinatario());
			addAlias(lCriteria, aliasesUsed, "TEmailMgo.TDestinatariEmailMgos", "TDestinatariEmailMgo");
			addAlias(lCriteria, aliasesUsed, "TDestinatariEmailMgo.TRubricaEmail", "TRubricaEmail");
			lCriteria.add(Restrictions.eq("TRubricaEmail.idVoceRubricaEmail", lFilterSearch.getIdVoceRubricaDestinatario()));
		}
		if (ListUtil.isNotEmpty(lFilterSearch.getTipiNotificaInteropRicevute())) {
			mLogger.debug("Filtro per i tipi notifica " + ListUtil.print(lFilterSearch.getTipiNotificaInteropRicevute()));
			List<TipiNofificaInteropRicevute> tipiNotifica = lFilterSearch.getTipiNotificaInteropRicevute();
			List<Criterion> lListCriterions = new ArrayList<Criterion>(tipiNotifica.size());
			for (TipiNofificaInteropRicevute tipo : tipiNotifica) {
				switch (tipo) {
				case AGGIORNAMENTO: {
					lListCriterions.add(Restrictions.eq("TEmailMgo.flgNotifInteropAgg", true));
					break;
				}
				case ANNULLAMENTO: {
					lListCriterions.add(Restrictions.eq("TEmailMgo.flgNotifInteropAnn", true));
					break;
				}
				case CONFERMA: {
					lListCriterions.add(Restrictions.eq("TEmailMgo.flgNotifInteropConf", true));
					break;
				}
				case ECCEZIONE: {
					lListCriterions.add(Restrictions.eq("TEmailMgo.flgNotifInteropEcc", true));
					break;
				}
				}
			}
			if (lListCriterions.size() == 1) {
				lCriteria.add(lListCriterions.get(0));
			} else {
				Disjunction disjunction = Restrictions.disjunction();
				for (Criterion lCriterion : lListCriterions) {
					disjunction.add(lCriterion);
				}
				lCriteria.add(disjunction);
			}
		}
		if (lFilterSearch.getPresenzaCommenti() != null) {
			mLogger.debug("Filtro per presenza commenti: " + lFilterSearch.getPresenzaCommenti());
			PresenzaCommenti commenti = lFilterSearch.getPresenzaCommenti();
			DetachedCriteria lDetachedCriteriaEmailVsCommenti = DetachedCriteria.forClass(TCommentiEmail.class, "TCommentiEmail");
			// Per il nostro idUtente
			Disjunction lDisjunction = Restrictions.disjunction();
			lDetachedCriteriaEmailVsCommenti.createAlias("TCommentiEmail.TUtentiModPecByIdUtenteDest", "TUtentiModPec");
			lDisjunction.add(Property.forName("TUtentiModPec.idUtente").eq(pSearchIn.getIdUtente()));
			// lDetachedCriteriaEmailVsCommenti.add(Property.forName("TUtentiModPec.idUtente").eq(idUtente));
			switch (commenti) {
			case PRESENTI_COMMENTI_DESTINATI_A_ME: {
				List<TRelUtentiVsFruitori> lList = getListFruitori(pSearchIn, pSession);
				if (lList != null && lList.size() > 0) {
					List<TAnagFruitoriCaselle> lListFruitori = new ArrayList<TAnagFruitoriCaselle>();
					for (TRelUtentiVsFruitori lTRelUtentiVsFruitori : lList) {
						lListFruitori.add(lTRelUtentiVsFruitori.getTAnagFruitoriCaselle());
					}
					lDetachedCriteriaEmailVsCommenti.createAlias("TCommentiEmail.TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
					lDisjunction.add(Property.forName("TAnagFruitoriCaselle").in(lListFruitori));

				}
				break;
			}
			case PRESENTI_COMMENTI: {
				lDisjunction.add(Property.forName("TCommentiEmail.flgPubblico").eq(true));
				break;
			}
			}
			lDetachedCriteriaEmailVsCommenti.add(lDisjunction);
			lCriteria.add(Subqueries.exists(lDetachedCriteriaEmailVsCommenti.setProjection(Projections.property("id"))));

		}
		return lCriteria;
	}

	protected List<TRelUtentiVsFruitori> getListFruitori(SearchIn pSearchIn, Session pSession) {
		Criteria lCriteriaFruitori = pSession.createCriteria(TRelUtentiVsFruitori.class);
		lCriteriaFruitori.createAlias("TUtentiModPec", "TUtentiModPec");
		lCriteriaFruitori.createAlias("TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
		lCriteriaFruitori.add(Restrictions.eq("flgAnn", false));
		lCriteriaFruitori.add(Restrictions.eq("TUtentiModPec.idUtente", pSearchIn.getIdUtente()));
		List<TRelUtentiVsFruitori> lList = lCriteriaFruitori.list();
		return lList;
	}

	private void addAlias(Criteria lCriteria, List<String[]> aliasesUsed, String stringAlias, String stringAliasFor) {
		for (String[] lStrings : aliasesUsed) {
			if (lStrings[0].equals(stringAlias) && lStrings[1].equals(stringAliasFor)) {
				return;
			}
		}
		aliasesUsed.add(new String[] { stringAlias, stringAliasFor });
		lCriteria.createAlias(stringAlias, stringAliasFor);
	}

	/**
	 * Recupera la lista dei folder in cui fare la ricerca - Se la lista non è valorizzata, la recupera in base all'utente - altrimenti è l'unione della lista
	 * in input e relative sotto folder intersecata con quelle dell'utente
	 * 
	 * @param pSession
	 * @param pSearchIn
	 * @return
	 * @throws Exception
	 */
	private List<String> retrieveFolderToSearch(Session pSession, SearchIn pSearchIn) throws Exception {
		mLogger.debug("ricerco le folder per l'utente: " + pSearchIn.getIdUtente() + " e l'ente: " + pSearchIn.getIdEnteAOO());
		List<String> result = null;
		// Se la lista non è valorizzata o è vuota
		// Recupero i folder dell'utente
		CasellaUtility lCasellaUtility = new CasellaUtility();
		ListaIdFolderUtenteIn lListaIdFolderUtenteIn = new ListaIdFolderUtenteIn();
		// Setto l'utente
		lListaIdFolderUtenteIn.setIdUtente(pSearchIn.getIdUtente());
		lListaIdFolderUtenteIn.setIdEnteAOO(pSearchIn.getIdEnteAOO());
		// Setto la classificazione
		if (!ListUtil.isEmpty(pSearchIn.getClassificazioniFolder())) {
			lListaIdFolderUtenteIn.setClassificazioniFolder(pSearchIn.getClassificazioniFolder());
			mLogger.debug("Aggiungo restrizione sulle classificazioni: " + ListUtil.print(pSearchIn.getClassificazioniFolder()));
		}
		// Recupero i folders utente
		result = lCasellaUtility.getListaIdFolderUtenteWithSession(lListaIdFolderUtenteIn, pSession).getListIdFolders();
		if (result != null) {
			mLogger.debug("risultato ricerca :" + ListUtil.print(result));
		}
		if (pSearchIn.getFolders() != null && pSearchIn.getFolders().size() > 0) {
			mLogger.debug("filtro per le classificazioni");
			// Lista degli idFolder che hanno una delle classificazioni specificate
			Criteria lCriteria = pSession.createCriteria(TFolderCaselle.class);
			lCriteria.add(Restrictions.in("idFolderCasella", pSearchIn.getFolders()));
			if (!ListUtil.isEmpty(pSearchIn.getClassificazioniFolder())) {
				lCriteria.add(Restrictions.in("classificazione", pSearchIn.getClassificazioniFolder()));
				mLogger.debug("classificazioni: " + ListUtil.print(pSearchIn.getClassificazioniFolder()));
			}
			List<String> lList = new ArrayList<String>();
			// Recupero i folder
			List<TFolderCaselle> lListFolder = lCriteria.list();
			if (lListFolder != null) {
				for (TFolderCaselle folderCasella : lListFolder) {
					mLogger.debug("folder principale(idFolderCasella): " + folderCasella.getIdFolderCasella());
				}
				lList = new ArrayList<String>(lListFolder.size());
				// Per ogni folder recupero i sotto folder
				mLogger.debug("ricavo le subfolder");
				for (TFolderCaselle lTFolderCaselle : lListFolder) {
					// Aggiungo il folder appena trovato
					lList.add(lTFolderCaselle.getIdFolderCasella());
					ListaIdSubFolderIn lListaIdSubFolderIn = new ListaIdSubFolderIn();
					// Cerco i sub folder relativi
					lListaIdSubFolderIn.setIdFolder(lTFolderCaselle.getIdFolderCasella());
					// Li aggiungo
					lList.addAll(lCasellaUtility.getListaIdSubFolder(lListaIdSubFolderIn).getFolders());
				}
			}
			mLogger.debug("lista subFolder " + ListUtil.print(lList));
			// il risultato è l'intersezione delle liste di folder
			result = ListUtil.intersect(result, lList);
		}
		if (result != null) {
			mLogger.debug("risultato ricerca finale(intersezione:" + ListUtil.print(result));
		}
		return result;
	}

}
