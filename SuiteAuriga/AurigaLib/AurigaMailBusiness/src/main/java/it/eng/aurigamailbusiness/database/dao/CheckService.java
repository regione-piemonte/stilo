/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.CheckLockOutBean;
import it.eng.aurigamailbusiness.bean.LockBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.utility.DateUtils;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;

/**
 * servizio di controllo di lock/unlock
 * 
 * @author jravagnan
 * 
 */
@Service(name = "CheckService")
public class CheckService {

	private Logger log = LogManager.getLogger(CheckService.class);

	private static final String NOME_PARAMETRO_TIMEOUT = "DURATA_LOCK";

	private static final Integer DEFAULT_TIMEOUT = 10;

	private static final String ERRORERECORDNONTROVATO = "Impossibile trovare il record associato all'id: ";

	@Operation(name = "lock")
	public TEmailMgoBean lock(LockBean lockIn) throws AurigaMailBusinessException {
		TEmailMgoBean ret = null;
		try {
			DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			TEmailMgoBean email = daoMail.get(lockIn.getMailId());
			if (email == null) {
				log.error(ERRORERECORDNONTROVATO + lockIn.getMailId());
				throw new AurigaMailBusinessException(ERRORERECORDNONTROVATO + lockIn.getMailId());
			}
			// lock non permesso se email già lockata, utente diverso da quello che ha già lockato e nel caso sia passato l'intervallo
			// temporale
			if ((!StringUtils.isBlank(email.getIdUtenteLock()) && !email.getIdUtenteLock().equals(lockIn.getUserId()))
					&& DateUtils.getDifferenceInMinute(new Date(), email.getTsLock()) < getIntervalloLock()) {
				log.error("Operazione non consentita: sull’e-mail " + lockIn.getMailId() + " sta lavorando un altro utente");
				throw new AurigaMailBusinessException("Operazione non consentita: sull’e-mail " + lockIn.getMailId() + " sta lavorando un altro utente");
			}
			email.setIdUtenteLock(lockIn.getUserId());
			email.setTsLock(new Date());
			ret = daoMail.update(email);
		} catch (Exception e) {
			log.error("Impossibile eseguire il lock sulla email con id: " + lockIn.getMailId());
			throw new AurigaMailBusinessException("Impossibile eseguire il lock sulla email con id: " + lockIn.getMailId(), e);
		}
		return ret;
	}

	@Operation(name = "unlock")
	public TEmailMgoBean unlock(LockBean lockIn) throws Exception {
		TEmailMgoBean ret = null;
		try {
			DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			TEmailMgoBean email = daoMail.get(lockIn.getMailId());
			if (email == null) {
				log.error(ERRORERECORDNONTROVATO + lockIn.getMailId());
				throw new AurigaMailBusinessException(ERRORERECORDNONTROVATO + lockIn.getMailId());
			}
			if (StringUtils.isBlank(email.getIdUtenteLock())) {
				log.warn("Tentativo di eseguire un'operazione di unlock su una risorsa già sbloccata: " + lockIn.getMailId());
				ret = new TEmailMgoBean();
			} else {
				if (!lockIn.getUserId().equals(email.getIdUtenteLock())) {
					log.error("Impossibile sbloccare una risorsa già bloccata da un altro utente: " + lockIn.getMailId());
					throw new AurigaMailBusinessException("Impossibile sbloccare una risorsa già bloccata da un altro utente: " + lockIn.getMailId());
				} else {
					email.setIdUtenteLock(null);
					email.setTsLock(null);
					ret = daoMail.update(email);
				}
			}
		} catch (Exception e) {
			log.error("Impossibile eseguire l'operazione di unlock sulla email con id: " + lockIn.getMailId());
			throw e;
		}
		return ret;
	}

	@Operation(name = "forceUnlock")
	public TEmailMgoBean forceUnlock(LockBean lockIn) throws Exception {
		TEmailMgoBean ret = null;
		try {
			DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			TEmailMgoBean email = daoMail.get(lockIn.getMailId());
			if (email == null) {
				log.error("Impossibile trovare il record associato all'id: " + lockIn.getMailId());
				throw new AurigaMailBusinessException("Impossibile trovare il record associato all'id: " + lockIn.getMailId());
			}
			if (StringUtils.isBlank(email.getIdUtenteLock())) {
				log.warn("Tentativo di eseguire un'operazione di unlock su una risorsa già sbloccata: " + lockIn.getMailId());
			} else {
				email.setIdUtenteLock(null);
				email.setTsLock(null);
				ret = daoMail.update(email);
			}
		} catch (Exception e) {
			log.error("Impossibile eseguire l'operazione di unlock sulla email con id: " + lockIn.getMailId());
			throw e;
		}
		return ret;
	}

	@Operation(name = "checkLock")
	public CheckLockOutBean checkLock(LockBean lockIn) throws Exception {
		CheckLockOutBean out = new CheckLockOutBean();
		try {
			DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			TEmailMgoBean email = daoMail.get(lockIn.getMailId());
			out.setMail(email);
			if (email == null) {
				log.error("Impossibile trovare il record associato all'id: " + lockIn.getMailId());
				throw new AurigaMailBusinessException("Impossibile trovare il record associato all'id: " + lockIn.getMailId());
			}
			if (StringUtils.isBlank(email.getIdUtenteLock())
					|| (StringUtils.isNotBlank(email.getIdUtenteLock()) && email.getIdUtenteLock().equals(lockIn.getUserId()))
					|| DateUtils.getDifferenceInMinute(new Date(), email.getTsLock()) > getIntervalloLock()) {
				out.setIsLocked(false);
			} else {
				out.setIsLocked(true);
			}
		} catch (Exception e) {
			log.error("Impossibile eseguire l'operazione di verifica lock sulla email con id: " + lockIn.getMailId());
			throw e;
		}
		return out;
	}

	/**
	 * calcola il valore dell'intervallo di lock
	 * 
	 * @return
	 * @throws Exception
	 */
	private Integer getIntervalloLock() throws Exception {
		DaoTParameters daoParameters = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
		TParametersBean parameter = daoParameters.get(NOME_PARAMETRO_TIMEOUT);
		if (parameter.getNumValue() != null)
			return parameter.getNumValue().intValue();
		else
			return DEFAULT_TIMEOUT;

	}

}
