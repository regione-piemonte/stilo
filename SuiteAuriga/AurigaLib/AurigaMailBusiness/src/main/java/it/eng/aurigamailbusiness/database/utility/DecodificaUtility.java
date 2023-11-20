/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TUtentiModPec;
import it.eng.core.business.HibernateUtil;

public class DecodificaUtility {

	private static Logger mLogger = LogManager.getLogger(DecodificaUtility.class);

	public static String getDecodificaUtente(String idUtente) {
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			Criteria lCriteria = lSession.createCriteria(TUtentiModPec.class);
			lCriteria.add(Restrictions.idEq(idUtente));
			return getDecodificaUtente((TUtentiModPec) lCriteria.uniqueResult());
		} catch (Exception lException) {
			mLogger.error("Errore: " + lException.getMessage(), lException);
			return null;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception e) {
				mLogger.error("Errore: " + e.getMessage(), e);
			}
		}
	}

	public static String getDecodificaUtente(String idUtente, Session lSession) {
		try {
			Criteria lCriteria = lSession.createCriteria(TUtentiModPec.class);
			lCriteria.add(Restrictions.idEq(idUtente));
			return getDecodificaUtente((TUtentiModPec) lCriteria.uniqueResult());
		} catch (Exception lException) {
			mLogger.error("Errore: " + lException.getMessage(), lException);
			return null;
		}
	}

	public static String getDecodificaUtente(TUtentiModPec lTUtentiModPec) {
		StringBuffer lStringBuffer = new StringBuffer();
		lStringBuffer.append(lTUtentiModPec.getCognome());
		lStringBuffer.append(" " + lTUtentiModPec.getNome());
		if (StringUtils.isNotEmpty(lTUtentiModPec.getCodFiscale())) {
			lStringBuffer.append(" C.F. " + lTUtentiModPec.getCodFiscale());
		}
		lStringBuffer.append(" [" + lTUtentiModPec.getUsername() + "]");
		return lStringBuffer.toString();
	}

	public static String getDecodificaUo(TAnagFruitoriCaselle pTAnagFruitoriCaselle) {
		// si decodifica con [<CODICE_COMPLETO> se valorizzato + spazio] + DENOMINAZIONE_COMPLETA
		StringBuffer lStringBuffer = new StringBuffer();
		if (StringUtils.isNotEmpty(pTAnagFruitoriCaselle.getCodiceCompleto())) {
			lStringBuffer.append(pTAnagFruitoriCaselle.getCodiceCompleto() + " ");
		}
		lStringBuffer.append(pTAnagFruitoriCaselle.getDenominazioneCompleta());
		return lStringBuffer.toString();
	}

	public static String getDecodificaUo(String idUo) {
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			Criteria lCriteria = lSession.createCriteria(TAnagFruitoriCaselle.class);
			lCriteria.add(Restrictions.idEq(idUo));
			return getDecodificaUo((TAnagFruitoriCaselle) lCriteria.uniqueResult());
		} catch (Exception lException) {
			mLogger.error("Errore: " + lException.getMessage(), lException);
			return null;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception e) {
				mLogger.error("Errore: " + e.getMessage(), e);
			}
		}
	}

	public static String getDecodificaUo(String idUo, Session pSession) {
		try {
			Criteria lCriteria = pSession.createCriteria(TAnagFruitoriCaselle.class);
			lCriteria.add(Restrictions.idEq(idUo));
			return getDecodificaUo((TAnagFruitoriCaselle) lCriteria.uniqueResult());
		} catch (Exception lException) {
			mLogger.error("Errore: " + lException.getMessage(), lException);
			return null;
		}
	}

	public static String getDate(Date lDate) {
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return lSimpleDateFormat.format(lDate);

	}
}