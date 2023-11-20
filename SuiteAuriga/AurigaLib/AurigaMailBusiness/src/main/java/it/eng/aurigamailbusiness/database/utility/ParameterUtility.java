/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.database.mail.TParameters;
import it.eng.core.business.HibernateUtil;

public class ParameterUtility {

	private static final String NUM_VALUE = "NUM_VALUE";
	private static final String MAX_MINUTI_PROCESSO_MAIL = "MAX_MINUTI_PROCESSO_MAIL";
	private static final String CONCATENATE_SENDER = "CONCATENATE_SENDER";
	private static final BigDecimal DEFAULT_MAX_MINUTI_PROCESSO_MAIL = new BigDecimal(10).setScale(0);
	private static final Boolean DEFAULT_CONCATENATE_SENDER = false;

	static Logger log = LogManager.getLogger(ParameterUtility.class);

	private ParameterUtility() {
		throw new IllegalStateException("Classe di utilità");
	}

	public static BigDecimal getMaxNumForRicerca() throws Exception {
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			TParameters lTParameters = (TParameters) lSession.createCriteria(TParameters.class).add(Restrictions.eq("parKey", NUM_VALUE)).uniqueResult();
			if (lTParameters == null) {
				return null;
			} else {
				return lTParameters.getNumValue();
			}
		} catch (Exception e) {
			log.error("Errore recupero parametro NUM_VALUE", e);
			throw e;
		} finally {
			HibernateUtil.release(lSession);
		}
	}

	/**
	 * Recupera dalla T_PARAMETERS la durata massima in minuti per l'elaborazione di un singolo messaggio
	 * 
	 * @return
	 * @throws Exception
	 */

	public static BigDecimal getMaxMinutiPerProcessoMail() throws Exception {
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			TParameters lTParameters = (TParameters) lSession.createCriteria(TParameters.class).add(Restrictions.eq("parKey", MAX_MINUTI_PROCESSO_MAIL))
					.uniqueResult();
			if (lTParameters == null) {
				return DEFAULT_MAX_MINUTI_PROCESSO_MAIL; // in mancanza di un parametro in database utilizzo il valore di default
			} else {
				return lTParameters.getNumValue();
			}
		} catch (Exception e) {
			log.error("Errore recupero parametro MAX_MINUTI_PROCESSO_MAIL", e);
			throw e;
		} finally {
			HibernateUtil.release(lSession);
		}
	}

	public static Boolean isConcatenateSender() throws Exception {
		Boolean result = false;
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			TParameters lTParameters = (TParameters) lSession.createCriteria(TParameters.class).add(Restrictions.eq("parKey", CONCATENATE_SENDER))
					.uniqueResult();
			if (lTParameters == null) {
				result = DEFAULT_CONCATENATE_SENDER; // in mancanza di un parametro in database utilizzo il valore di default
			} else {
				result = lTParameters.getNumValue() != null && lTParameters.getNumValue().equals(BigDecimal.ONE);
			}
		} catch (Exception e) {
			log.error("Errore recupero parametro CONCATENATE_SENDER", e);
			throw e;
		} finally {
			HibernateUtil.release(lSession);
		}
		return result;
	}

}
