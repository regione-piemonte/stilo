/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.ExtractverdocImpl;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindud_jBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetiddocprimarioallegatofromudBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Findud_jImpl;
import it.eng.auriga.database.store.dmpk_utility.store.impl.GetiddocprimarioallegatofromudImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.RigaEstrazioneDocumento;
import it.eng.document.storage.DocumentStorage;
import it.eng.storeutil.AnalyzeResult;

/**
 * Contiene la logica applicativa per l'estrazione di documenti da una singola riga<br/>
 * Necessario per il multithreading
 * 
 * @author D.Bragato
 *
 */
public class DDTExporter implements Callable<ExecutionResultBean<File>> {

	protected Logger log = Logger.getLogger(DDTExporter.class);
	
	public static final String INFO_RIGA_INDICE = "INFO_RIGA_INDICE";
	private static final String REG_EMERG_FISSO_A = "A";

	private AurigaLoginBean aurigaLoginBean;
	//private Session session;
	private JobBean currentJob;
	private RigaEstrazioneDocumento rigaIndice;
	private File exportDir;

	public DDTExporter(AurigaLoginBean aurigaLoginBean, JobBean currentJob, RigaEstrazioneDocumento rigaIndice, File exportDir) {
		this.aurigaLoginBean = aurigaLoginBean;
		//this.session = session;
		this.currentJob = currentJob;
		this.rigaIndice = rigaIndice;
		this.exportDir = exportDir;
	}

	@Override
	public ExecutionResultBean<File> call() throws Exception {

		ExecutionResultBean<File> retValue = new ExecutionResultBean<File>();
		retValue.addAdditionalInformation(INFO_RIGA_INDICE, rigaIndice);
		
		ExecutionResultBean<?> validateResult = validaDatiRiga();
		
		if (validateResult.isSuccessful()) {

			log.debug("Riga: " + rigaIndice.getNumeroRiga());
			
			String sezionale = rigaIndice.getNumeroDocumento().substring(0, 4);
			
			String codAppOwner = null;
			if (StringUtils.isNotBlank(rigaIndice.getSocieta())) {
				if (new BigDecimal("74").compareTo(currentJob.getIdSpAoo()) == 0) {
					codAppOwner = "FATTAIRLIQUIDE_" + rigaIndice.getSocieta();
				} else {
					codAppOwner = rigaIndice.getSocieta();
				}
			}
			log.debug(String.format("Parametri di ricerca: sezionale[%s], IdSpAoo[%s], codAppOwner[%s]", 
					sezionale, currentJob.getIdSpAoo(), codAppOwner));
			
			Session session = null;
			try {
				session = HibernateUtil.begin();
				Transaction lTransaction = session.beginTransaction();

				// cerco l'unità doc
				DmpkUtilityFindud_jBean findUdBean = new DmpkUtilityFindud_jBean();
				findUdBean.setIdspaooin(currentJob.getIdSpAoo());
				findUdBean.setCodapplownerin(codAppOwner);
				findUdBean.setCodcategoriaregin(REG_EMERG_FISSO_A);
				findUdBean.setSiglaregin(sezionale);
				findUdBean.setAnnoregin(new Integer(rigaIndice.getAnnoDocumento()));
				findUdBean.setNumregin(new BigDecimal(rigaIndice.getNumeroDocumento()));

				final Findud_jImpl findUd = new Findud_jImpl();
				findUd.setBean(findUdBean);
				
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						findUd.execute(paramConnection);
					}
				});
				
				StoreResultBean<DmpkUtilityFindud_jBean> findUdResult = new StoreResultBean<DmpkUtilityFindud_jBean>();
				AnalyzeResult.analyze(findUdBean, findUdResult);
				findUdResult.setResultBean(findUdBean);

				BigDecimal idUd = null;
				if (findUdResult.isInError()) {
					throw new StoreException(findUdResult);
				} else {
					log.debug("idUd vale " + findUdResult.getResultBean().getIdudout());
					idUd = findUdResult.getResultBean().getIdudout();
					if (idUd == null) throw new Exception("Unità doc non trovata");
				}

				//cerco il doc primario
				DmpkUtilityGetiddocprimarioallegatofromudBean getDocPrimarioBean = new DmpkUtilityGetiddocprimarioallegatofromudBean();
				getDocPrimarioBean.setIdspaooin(currentJob.getIdSpAoo());
				getDocPrimarioBean.setIdudin(idUd);
				getDocPrimarioBean.setNrodocvsudin(0);
				
				final GetiddocprimarioallegatofromudImpl getIdDocPrimario = new GetiddocprimarioallegatofromudImpl();
				getIdDocPrimario.setBean(getDocPrimarioBean);
				
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						getIdDocPrimario.execute(paramConnection);
					}
				});
				
				StoreResultBean<DmpkUtilityGetiddocprimarioallegatofromudBean> getIdDocPrimarioResult = new StoreResultBean<DmpkUtilityGetiddocprimarioallegatofromudBean>();
				AnalyzeResult.analyze(getDocPrimarioBean, getIdDocPrimarioResult);
				getIdDocPrimarioResult.setResultBean(getDocPrimarioBean);

				BigDecimal idDoc = null;
				if (getIdDocPrimarioResult.isInError()) {
					throw new StoreException(getIdDocPrimarioResult);
				} else {
					log.debug("idDoc vale " + getIdDocPrimarioResult.getResultBean().getParametro_1());
					idDoc = getIdDocPrimarioResult.getResultBean().getParametro_1();
					if (idDoc == null) throw new Exception("Documento primario non trovato");
				}

				//estraggo l'uri
				DmpkCoreExtractverdocBean extractVerDocBean = new DmpkCoreExtractverdocBean();
				extractVerDocBean.setCodidconnectiontokenin(aurigaLoginBean.getToken());
				extractVerDocBean.setIddocin(idDoc);
				
				final ExtractverdocImpl extractVerDoc = new ExtractverdocImpl();
				extractVerDoc.setBean(extractVerDocBean);

				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						extractVerDoc.execute(paramConnection);
					}
				});
				
				StoreResultBean<DmpkCoreExtractverdocBean> extractVerDocResult = new StoreResultBean<DmpkCoreExtractverdocBean>();
				AnalyzeResult.analyze(getDocPrimarioBean, extractVerDocResult);
				extractVerDocResult.setResultBean(extractVerDocBean);

				String filenameDocPrimario = null;
				String uriDocPrimario = null;
				if (extractVerDocResult.isInError()) {
					throw new StoreException(extractVerDocResult);
				} else {
					filenameDocPrimario = extractVerDocResult.getResultBean().getDisplayfilenameverout();
					uriDocPrimario = extractVerDocResult.getResultBean().getUriverout();
					log.debug(String.format("Doc primario %s -> %s", filenameDocPrimario, uriDocPrimario));
					if (uriDocPrimario == null) throw new Exception("Uri file primario non trovato");
				}
				
				File fileDocPrimarioInStorage = DocumentStorage.extract(uriDocPrimario, aurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				File fileDocPrimario = new File(exportDir, filenameDocPrimario);
				FileUtils.copyFile(fileDocPrimarioInStorage, fileDocPrimario, true);
				
				session.flush();
				lTransaction.commit();

				retValue.setSuccessful(true);
				retValue.setResult(fileDocPrimario);

			} catch (Exception e) {
				String message;
				if (e instanceof StoreException) {
					StoreResultBean<?> error = ((StoreException) e).getError();
					message = String.format(
							"Durante l'estrazione del documento alla riga %1$s, si è verificata la seguente eccezione: %2$s [%3$s] %4$s",
							rigaIndice.getNumeroRiga(), 
							StringUtils.defaultString(error.getErrorContext(), "#"),
							error.getErrorCode(),
							StringUtils.defaultString(error.getDefaultMessage(), "#"));
					retValue.setMessage(message);
				} else {
					message = String.format(
						"Durante l'estrazione del documento alla riga %s, si è verificata la seguente eccezione",
						rigaIndice.getNumeroRiga());
				}
				retValue.setSuccessful(false);
				retValue.addAdditionalInformation("EXCEPTION", e);
				log.error(message, e);

			} finally {
				HibernateUtil.release(session);
			}
		} else {
			retValue.setSuccessful(false);
			retValue.setMessage(validateResult.getMessage());
		}

		return retValue;

	}

	private ExecutionResultBean<Boolean> validaDatiRiga() {
		ExecutionResultBean<Boolean> retValue = new ExecutionResultBean<Boolean>();

		// CODICE SOCIETA'
		/*if (StringUtils.isBlank(rigaIndice.getSocieta())) {
			rigaIndice.getErroriDatiObbligatori().add(rigaIndice.getNumeroRiga(), "Codice societa mancante");
			rigaIndice.setDatiObbligatoriValidi(false);;
		}*/
		
		// TIPO DOCUMENTO
		if (StringUtils.isBlank(rigaIndice.getTipoDocumento())) {
			rigaIndice.getErroriDatiObbligatori().add(rigaIndice.getNumeroRiga(), "Tipo documento mancante");
			rigaIndice.setDatiObbligatoriValidi(false);;
		}
		
		// NUMERO DOCUMENTO
		if (StringUtils.isBlank(rigaIndice.getNumeroDocumento())) {
			rigaIndice.getErroriDatiObbligatori().add(rigaIndice.getNumeroRiga(), "Numero documento mancante");
			rigaIndice.setDatiObbligatoriValidi(false);;
		}

		// ANNO DOCUMENTO
		if (StringUtils.isBlank(rigaIndice.getSocieta())) {
			rigaIndice.getErroriDatiObbligatori().add(rigaIndice.getNumeroRiga(), "Anno documento mancante");
			rigaIndice.setDatiObbligatoriValidi(false);;
		} else if (!StringUtils.isNumeric(rigaIndice.getSocieta())) {
			rigaIndice.getErroriDatiObbligatori().add(rigaIndice.getNumeroRiga(), "Anno documento errato");
			rigaIndice.setDatiObbligatoriValidi(false);;
		}

		if (rigaIndice.isDatiObbligatoriValidi()) {
			
			String errMessage = StringUtils.join(rigaIndice.getErroriDatiObbligatori(), "; ");
			retValue.setMessage(errMessage);
			retValue.setSuccessful(false);
			
		} else {
			retValue.setSuccessful(true);
		}
		
		return retValue;
	}
}
