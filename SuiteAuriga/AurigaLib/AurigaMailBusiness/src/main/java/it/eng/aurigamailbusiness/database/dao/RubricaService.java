/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.MailingListBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.RubricaOutBean;
import it.eng.aurigamailbusiness.bean.RubricaSearchBean;
import it.eng.aurigamailbusiness.bean.TMembriMailingListBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.bean.VoceRubricaBeanIn;
import it.eng.aurigamailbusiness.converters.TRubricaEmailToTRubricaEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.converter.UtilPopulate;

/**
 * servizio di gestione rubrica
 * 
 * @author jravagnan
 * 
 */
@Service(name = "RubricaService")
public class RubricaService {

	private Logger log = LogManager.getLogger(RubricaService.class);

	private final static String WILDCARD_PARAMETER_NAME = "WILDCARD";

	private final static String WILDCARD_CHARACTER = "%";

	/**
	 * cerca una voce rubrica per descrizione e/o per account
	 * 
	 * @param beanIn
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	@Operation(name = "search")
	public ResultBean<RubricaOutBean> search(RubricaSearchBean beanIn) {
		ResultBean<RubricaOutBean> result = new ResultBean<RubricaOutBean>();
		List<String> wildcards = getParametersValue();
		RubricaOutBean out = new RubricaOutBean();
		Session lSession = null;
		try {
			// dato il non elevato numero di record non poniamo nessun limite alla ricerca
			// if (StringUtils.isBlank(beanIn.getDescrizioneVoce()) && StringUtils.isBlank(beanIn.getAccountEmail())) {
			// log.error("E' necessario valorizzare almeno la descrizione o l'account");
			// throw new AurigaMailBusinessException("E' necessario valorizzare almeno la descrizione o l'account");
			// }
			// Apro la sessione
			lSession = HibernateUtil.begin();
			// devo cercare sulla tabella TRubricaEmail con la ricerca che desidero
			Criteria lCriteriaRubrica = lSession.createCriteria(TRubricaEmail.class);
			String descrizioneVoce = beanIn.getDescrizioneVoce();
			if (!StringUtils.isBlank(descrizioneVoce)) {
				if (wildcards.size() > 0) {
					for (String wc : wildcards) {
						descrizioneVoce = descrizioneVoce.replace(wc, WILDCARD_CHARACTER);
					}
				}
				lCriteriaRubrica.add(Restrictions.ilike("descrizioneVoce", descrizioneVoce));
			}
			String accountEmail = beanIn.getAccountEmail();
			if (!StringUtils.isBlank(accountEmail)) {
				if (wildcards.size() > 0) {
					for (String wc : wildcards) {
						accountEmail = accountEmail.replace(wc, WILDCARD_CHARACTER);
					}
				}
				lCriteriaRubrica.add(Restrictions.ilike("accountEmail", accountEmail));
			}
			// Flag annullato false
			if (beanIn.getAncheAnnullate() != null && !beanIn.getAncheAnnullate()) {
				lCriteriaRubrica.add(Restrictions.eq("flgAnn", false));
			}
			if (beanIn.getSoloPec() != null && beanIn.getSoloPec()) {
				lCriteriaRubrica.add(Restrictions.eq("flgPecVerificata", true));
			}
			if (beanIn.getAncheMailingList() != null && !beanIn.getAncheMailingList()) {
				lCriteriaRubrica.add(Restrictions.eq("flgMailingList", false));
			}
			if (!StringUtils.isBlank(beanIn.getIdFruitore())) {
				Disjunction disCond = Restrictions.disjunction();
				disCond.add(Restrictions.isNull("TAnagFruitoriCaselle.idFruitoreCasella"));
				disCond.add(Restrictions.eq("TAnagFruitoriCaselle.idFruitoreCasella", beanIn.getIdFruitore()));
				lCriteriaRubrica.add(disCond);
			} else {
				lCriteriaRubrica.add(Restrictions.isNull("TAnagFruitoriCaselle.idFruitoreCasella"));
			}
			List<TRubricaEmail> vociRubrica = lCriteriaRubrica.list();
			List<TRubricaEmailBean> listaVociRubrica = new ArrayList<TRubricaEmailBean>();
			for (TRubricaEmail voce : vociRubrica) {
				TRubricaEmailBean bean = (TRubricaEmailBean) UtilPopulate.populate((TRubricaEmail) voce, TRubricaEmailBean.class,
						new TRubricaEmailToTRubricaEmailBean());
				listaVociRubrica.add(bean);
			}
			out.setVociRubrica(listaVociRubrica);
		} catch (Exception exception) {
			log.error("Errore: " + exception.getMessage(), exception);
			result.setInError(true);
			result.setDefaultMessage(exception.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(exception));
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception e) {
				log.error("Errore: " + e.getMessage(), e);
			}
		}
		result.setInError(false);
		result.setResultBean(out);
		return result;
	}

	/**
	 * 'annulla' una voce rubrica
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "eliminaVoceRubrica")
	public ResultBean<TRubricaEmailBean> eliminaVoceRubrica(TRubricaEmailBean in) {
		ResultBean<TRubricaEmailBean> result = new ResultBean<TRubricaEmailBean>();
		try {
			DaoTRubricaEmail daoRubrica = (DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class);
			in.setFlgAnn(true);
			TRubricaEmailBean voceRubricaAggiornata = daoRubrica.update(in);
			result.setResultBean(voceRubricaAggiornata);
			result.setInError(false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setInError(true);
			result.setDefaultMessage(e.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	@Operation(name = "saveVoceRubrica")
	public ResultBean<TRubricaEmailBean> saveVoceRubrica(VoceRubricaBeanIn in) {
		ResultBean<TRubricaEmailBean> result = new ResultBean<TRubricaEmailBean>();
		try {
			// una voce non può essere già annullata
			if (in.getVoceRubrica().getFlgAnn()) {
				log.error("Non è possibile salvare una voce già cancellata");
				throw new AurigaMailBusinessException("Non è possibile salvare una voce già cancellata");
			}
			// una voce non può essere ML, c'è un metodo apposito
			if (in.getVoceRubrica().getFlgMailingList()) {
				log.error("Per inserire una ML bisogna utilizzare il metodo idoneo");
				throw new AurigaMailBusinessException("Per inserire una ML bisogna utilizzare il metodo idoneo");
			}
			// la verifica della PEC viene fatta in automatico, non in manuale
			if (in.getVoceRubrica().getFlgPecVerificata()) {
				log.error("Non si può inserire una voce come PEC verificata, la procedura di verifica  è automatica");
				throw new AurigaMailBusinessException("Non si può inserire una voce come PEC verificata, la procedura di verifica  è automatica");
			}
			// il flag viene settato da una migrazione e non da un inserimento manuale
			if (in.getVoceRubrica().getFlgPresenteInIpa()) {
				log.error("Non si può inserire una voce come presente in IPA, procedura solo automatica");
				throw new AurigaMailBusinessException("Non si può inserire una voce come presente in IPA, procedura solo automatica");
			}
			if ((in.getIdFruitore() != null && !in.getIdFruitore().equals(in.getVoceRubrica().getIdFruitoreCasella()))
					|| (in.getIdFruitore() == null && in.getVoceRubrica().getIdFruitoreCasella() != null)) {
				log.error("Non si può inserire una voce relativa ad un fruitore differente da quello che fa l'inserimento");
				throw new AurigaMailBusinessException("Non si può inserire una voce relativa ad un fruitore differente da quello che fa l'inserimento");
			}

			DaoTRubricaEmail daoRubrica = (DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class);
			TRubricaEmailBean toSave = in.getVoceRubrica();
			toSave.setIdVoceRubricaEmail(KeyGenerator.gen());
			TRubricaEmailBean voceRubricaAggiornata = daoRubrica.save(toSave);
			result.setResultBean(voceRubricaAggiornata);
			result.setInError(false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setInError(true);
			result.setDefaultMessage(e.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	@Operation(name = "updateVoceRubrica")
	public ResultBean<TRubricaEmailBean> updateVoceRubrica(VoceRubricaBeanIn in) {
		ResultBean<TRubricaEmailBean> result = new ResultBean<TRubricaEmailBean>();
		try {
			DaoTRubricaEmail daoRubrica = (DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class);
			TRubricaEmailBean voceOriginale = daoRubrica.get(in.getVoceRubrica().getIdVoceRubricaEmail());
			if ((in.getIdFruitore() != null && !in.getIdFruitore().equals(in.getVoceRubrica().getIdFruitoreCasella()))
					|| (in.getIdFruitore() == null && in.getVoceRubrica().getIdFruitoreCasella() != null)) {
				log.error("Non si può aggiornare una voce relativa ad un fruitore differente da quello che fa l'inserimento");
				throw new AurigaMailBusinessException("Non si può aggiornare una voce relativa ad un fruitore differente da quello che fa l'inserimento");
			}
			// se pec_verificata = true non può modificare tipo_account
			if (voceOriginale.getFlgPecVerificata() && !voceOriginale.getTipoAccount().equals(in.getVoceRubrica().getTipoAccount())) {
				log.error("Non si può modificare il tipo account di una pec verificata");
				throw new AurigaMailBusinessException("Non si può modificare il tipo account di una pec verificata");
			}
			// se id_prov_sogg_rif valorizzato non può modificare tipo_sogg_rif
			if (voceOriginale.getIdProvSoggRif() != null && !voceOriginale.getTipoSoggRif().equals(in.getVoceRubrica().getTipoSoggRif())) {
				log.error("Non si può modificare il tipo soggetto di riferimento");
				throw new AurigaMailBusinessException("Non si può modificare il tipo soggetto di riferimento");
			}
			TRubricaEmailBean voceRubricaAggiornata = daoRubrica.update(in.getVoceRubrica());
			result.setResultBean(voceRubricaAggiornata);
			result.setInError(false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setInError(true);
			result.setDefaultMessage(e.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	/**
	 * inserisce una ML o la aggiorna se già presente
	 * 
	 * @param voceMailingList
	 * @param idMembriRubrica
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "inserisciAggiornaMailingList")
	public ResultBean<TRubricaEmailBean> inserisciAggiornaMailingList(MailingListBean beanIn) {
		ResultBean<TRubricaEmailBean> result = new ResultBean<TRubricaEmailBean>();
		Session lSession = null;
		TRubricaEmailBean voceML = null;
		try {
			// Apro la sessione
			lSession = HibernateUtil.begin();
			Transaction lTransaction = lSession.beginTransaction();
			DaoTRubricaEmail daoRubrica = (DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class);
			DaoTMembriMailingList daoTMembriMailingList = (DaoTMembriMailingList) DaoFactory.getDao(DaoTMembriMailingList.class);
			// Se la ML non esiste già ne inserisco una di nuova
			if (StringUtils.isBlank(beanIn.getVoceMailingList().getIdVoceRubricaEmail())) {
				TRubricaEmailBean toSave = beanIn.getVoceMailingList();
				toSave.setIdVoceRubricaEmail(KeyGenerator.gen());
				voceML = daoRubrica.saveInSession(toSave, lSession);
				// inserisco anche i membri della ML come relazione
				for (String idMember : beanIn.getIdVoceRubricaMembri()) {
					TRubricaEmail lTRubricaEmail = (TRubricaEmail) lSession.get(TRubricaEmail.class, idMember);
					TRubricaEmailBean lTRubricaEmailBean = (TRubricaEmailBean) UtilPopulate.populate(lTRubricaEmail, TRubricaEmailBean.class,
							new TRubricaEmailToTRubricaEmailBean());
					if (lTRubricaEmailBean.getFlgMailingList()) {
						if (daoTMembriMailingList.isPresenteInMailingListRicorsiva(lSession, idMember, voceML.getIdVoceRubricaEmail())) {
							log.error("Non si può inserire '" + lTRubricaEmailBean.getDescrizioneVoce()
									+ "' come membro della mailing-list se a sua volta contiene la stessa");
							throw new AurigaMailBusinessException("Non si può inserire '" + lTRubricaEmailBean.getDescrizioneVoce()
									+ "' come membro della mailing-list se a sua volta contiene la stessa");
						}
					}
					TMembriMailingListBean membro = new TMembriMailingListBean();
					membro.setIdVoceRubricaMailingList(voceML.getIdVoceRubricaEmail());
					membro.setIdVoceRubricaMembro(idMember);
					daoTMembriMailingList.saveInSession(membro, lSession);
				}
			} else {
				// visto che la ML esiste già
				// prima cancello tutti i membri poi li ricreo con quelli nuovi
				voceML = beanIn.getVoceMailingList();
				TFilterFetch<TMembriMailingListBean> filterFetch = new TFilterFetch<TMembriMailingListBean>();
				TMembriMailingListBean filtro = new TMembriMailingListBean();
				filtro.setIdVoceRubricaMailingList(voceML.getIdVoceRubricaEmail());
				filterFetch.setFilter(filtro);
				List<TMembriMailingListBean> listaMembriML = daoTMembriMailingList.search(filterFetch).getData();
				for (TMembriMailingListBean membro : listaMembriML) {
					daoTMembriMailingList.deleteInSession(membro, lSession);
				}
				for (String idNewMembro : beanIn.getIdVoceRubricaMembri()) {
					TRubricaEmail lTRubricaEmail = (TRubricaEmail) lSession.get(TRubricaEmail.class, idNewMembro);
					TRubricaEmailBean lTRubricaEmailBean = (TRubricaEmailBean) UtilPopulate.populate(lTRubricaEmail, TRubricaEmailBean.class,
							new TRubricaEmailToTRubricaEmailBean());
					if (lTRubricaEmailBean.getFlgMailingList()) {
						if (daoTMembriMailingList.isPresenteInMailingListRicorsiva(lSession, idNewMembro, voceML.getIdVoceRubricaEmail())) {
							log.error("Non si può inserire '" + lTRubricaEmailBean.getDescrizioneVoce()
									+ "' come membro della mailing-list se a sua volta contiene la stessa");
							throw new AurigaMailBusinessException("Non si può inserire '" + lTRubricaEmailBean.getDescrizioneVoce()
									+ "' come membro della mailing-list se a sua volta contiene la stessa");
						}
					}
					TMembriMailingListBean membro = new TMembriMailingListBean();
					membro.setIdVoceRubricaMailingList(voceML.getIdVoceRubricaEmail());
					membro.setIdVoceRubricaMembro(idNewMembro);
					daoTMembriMailingList.saveInSession(membro, lSession);
				}
			}
			lSession.flush();
			lTransaction.commit();
			// HibernateUtil.commit(lSession);
			result.setResultBean(voceML);
			result.setInError(false);
		} catch (Exception exception) {
			log.error("Errore: " + exception.getMessage(), exception);
			result.setInError(true);
			result.setDefaultMessage(exception.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(exception));
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception e) {
				log.error("Errore: " + e.getMessage(), e);
				result.setInError(true);
				result.setDefaultMessage(e.getMessage());
				result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			}
		}
		return result;
	}

	/**
	 * permette di aggiungere una o più voci ad una ML preesistente
	 * 
	 * @param beanIn
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	@Operation(name = "aggiungiMembriMailingList")
	public ResultBean<TRubricaEmailBean> aggiungiMembroMailingList(MailingListBean beanIn) {
		ResultBean<TRubricaEmailBean> result = new ResultBean<TRubricaEmailBean>();
		Session lSession = null;
		try {
			// Apro la sessione
			lSession = HibernateUtil.begin();
			Transaction lTransaction = lSession.beginTransaction();
			DaoTMembriMailingList daoTMembriMailingList = (DaoTMembriMailingList) DaoFactory.getDao(DaoTMembriMailingList.class);
			for (String idMembro : beanIn.getIdVoceRubricaMembri()) {
				TMembriMailingListBean membro = new TMembriMailingListBean();
				membro.setIdVoceRubricaMailingList(beanIn.getVoceMailingList().getIdVoceRubricaEmail());
				membro.setIdVoceRubricaMembro(idMembro);
				daoTMembriMailingList.saveInSession(membro, lSession);
			}
			lSession.flush();
			lTransaction.commit();
			// HibernateUtil.commit(lSession);
			result.setResultBean(beanIn.getVoceMailingList());
			result.setInError(false);
		} catch (Exception e) {
			log.error("Errore: " + e.getMessage(), e);
			result.setInError(true);
			result.setDefaultMessage(e.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception e) {
				log.error("Errore: " + e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * 'esplode' una ML nei vari membri
	 * 
	 * @param voceML
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "getMembriML")
	public ResultBean<RubricaOutBean> getMembriML(TRubricaEmailBean voceML) {
		ResultBean<RubricaOutBean> result = new ResultBean<RubricaOutBean>();
		try {
			RubricaOutBean out = new RubricaOutBean();
			DaoTMembriMailingList daoTMembriMailingList = (DaoTMembriMailingList) DaoFactory.getDao(DaoTMembriMailingList.class);
			TFilterFetch<TMembriMailingListBean> filterFetch = new TFilterFetch<TMembriMailingListBean>();
			TMembriMailingListBean filtro = new TMembriMailingListBean();
			filtro.setIdVoceRubricaMailingList(voceML.getIdVoceRubricaEmail());
			filterFetch.setFilter(filtro);
			List<TMembriMailingListBean> listaMembriML = daoTMembriMailingList.search(filterFetch).getData();
			List<TRubricaEmailBean> listaVoci = new ArrayList<TRubricaEmailBean>();
			DaoTRubricaEmail daoRubrica = (DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class);
			for (TMembriMailingListBean membro : listaMembriML) {
				TRubricaEmailBean voce = daoRubrica.get(membro.getIdVoceRubricaMembro());
				listaVoci.add(voce);
			}
			out.setVociRubrica(listaVoci);
			result.setInError(false);
			result.setResultBean(out);
		} catch (Exception e) {
			log.error("Errore: " + e.getMessage(), e);
			result.setInError(true);
			result.setDefaultMessage(e.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	/**
	 * metodo che legge tutti i parametri di configurazione
	 */
	private List<String> getParametersValue() {
		TFilterFetch<TParametersBean> filter = new TFilterFetch<TParametersBean>();
		List<String> wildcards = new ArrayList<String>();
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			List<TParametersBean> listaRis = daoParametri.getAllParameters(filter).getData();
			for (TParametersBean parametro : listaRis) {
				if (parametro.getParKey().equalsIgnoreCase(WILDCARD_PARAMETER_NAME)) {
					StringTokenizer tokenizer = new StringTokenizer(parametro.getStrValue(), ",");
					while (tokenizer.hasMoreTokens()) {
						wildcards.add(tokenizer.nextToken());
					}
				}
			}
		} catch (Exception e) {
			log.error("Impossibile caricare i parametri, si useranno le impostazioni di default: ", e);
		}
		return wildcards;
	}

}
