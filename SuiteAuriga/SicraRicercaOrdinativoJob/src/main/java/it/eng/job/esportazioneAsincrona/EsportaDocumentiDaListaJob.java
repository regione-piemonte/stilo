/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.client.DaoBmtJobParameters;
import it.eng.client.DaoBmtJobs;
import it.eng.client.DmpkLoginLogin;
import it.eng.client.EsportazioneDocumentiFormatoZip;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.EsportazioneDocumentiZipResultBean;
import it.eng.job.SpringHelper;
import it.eng.job.util.StorageImplementation;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

/**
 * Avvia la generazione del file di esportazione di documenti da lista in excel
 * 
 * @author D.Bragato
 *
 */
@Job(type = "EsportaDocumentiDaListaJob")
public class EsportaDocumentiDaListaJob extends AbstractJob<String> {

	private static final String JOBATTRKEY_USERNAME = "username";
	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_TIPO_DOMINIO = "tipoDominio";

	private Logger logger = Logger.getLogger(EsportaDocumentiDaListaJob.class);

	private static final String JOB_CLASS_NAME = EsportaDocumentiDaListaJob.class.getName();

	private ApplicationContext context;

	private AurigaLoginBean aurigaLoginBean;

	private String username;
	private String schema;
	private Locale locale;
	private Integer tipoDominio;

	protected DaoBmtJobs bmtJobsClient;

	protected DaoBmtJobParameters daoBmtJobParameters;

	@Override
	public List<String> load() {

		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);

		return ret;
	}

	@Override
	public void execute(String schema) {

		boolean validConfiguration = configura();

		if (validConfiguration) {
			elabora();
		}
	}

	protected void elabora() {

		ExecutionResultBean<TPagingList<JobBean>> result = retrieveJobs();

		if (result.isSuccessful()) {

			TPagingList<JobBean> results = result.getResult();

			logger.info(String.format("Sono stati trovati %1$s job da esportare", results.getData().size()));

			EsportazioneDocumentiFormatoZip jobClient = new EsportazioneDocumentiFormatoZip();

			// per ogni job trovato effettuo la generazione del file di
			// esportazione
			for (JobBean currentJob : results.getData()) {

				logger.info("Iniziata elaborazione del job " + currentJob.getIdJob());

				// aggiorno il login bean con i dati del job corrente
				try {

					updateLoginBeanSpaoo(currentJob);

				} catch (Exception e) {

					// poichè si tratta di un errore di autenticazione, mi
					// aspetto sia successo per problemi nel server, quindi
					// non
					// pongo il job in stato di errore
					logger.error(
							String.format("Durante l'autenticazione si è verificata la seguente eccezione %1$s",
									ExceptionUtils.getFullStackTrace(e)));
					continue;
				}

				try {

					EsportazioneDocumentiZipResultBean res = jobClient.generadocumentojob(locale, aurigaLoginBean, currentJob);

					if (res.isSuccessful()) {
						logger.info(String.format("Completata con successo l'esecuzione del job %1$s",
								currentJob.getIdJob()));
					} else {
						logger.error(String.format(
								"Durante l'esecuzione del job %1$s, si è verificata la seguente eccezione %2$s",
								currentJob.getIdJob(), res.getMessage()));
					}

				} catch (Exception e) {
					logger.error(e);
				}
			}
		} else {
			logger.error(result.getMessage());
		}
	}

	/**
	 * Estrae i job di cui generare i file di esportazione, filtrati i job
	 * <ul>
	 * <li>Tipo = ELAB_XLS_DOC_X_ZIP</li>
	 * <li>Status = I</li>
	 * </ul>
	 * 
	 * @return
	 * @throws Exception
	 */
	protected ExecutionResultBean<TPagingList<JobBean>> retrieveJobs() {

		ExecutionResultBean<TPagingList<JobBean>> retValue = new ExecutionResultBean<TPagingList<JobBean>>();

		try {

			TFilterFetch<JobBean> filter = new TFilterFetch<JobBean>();

			JobBean filterBean = new JobBean();

			// recupero tutti i job di tipo esportazione, di cui è stata
			// completato il recupero dei dati
			filterBean.setTipo("ELAB_XLS_DOC_X_ZIP");
			filterBean.setStatus("I");
			filter.setFilter(filterBean);

			List<TOrderBy> orders = new ArrayList<TOrderBy>();
			TOrderBy orderBy = new TOrderBy();
			orderBy.setPropname("ID_JOB");

			// dal più vecchio al più recente
			orderBy.setType(OrderByType.ASCENDING);

			filter.setOrders(orders);

			TPagingList<JobBean> results = bmtJobsClient.search(locale, aurigaLoginBean, filter);

			if (results == null) {
				results = new TPagingList<JobBean>();
				results.setData(new ArrayList<JobBean>());
			}

			if (results.getData() == null) {
				results.setData(new ArrayList<JobBean>());
			}

			retValue.setSuccessful(true);
			retValue.setResult(results);

		} catch (Exception e) {

			String message = String.format("Durante il recupero dei jobs si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));
			logger.error(message);

			retValue.setMessage(message);
			retValue.setSuccessful(false);
		}

		return retValue;
	}

	/**
	 * @param currentJob
	 * @throws Exception
	 */
	protected void updateLoginBeanSpaoo(JobBean currentJob) throws Exception {
		DmpkLoginLoginBean loginBean = authenticate(currentJob.getIdSpAoo(), tipoDominio, username);
		aurigaLoginBean.setToken(loginBean.getCodidconnectiontokenout());
		aurigaLoginBean.getSpecializzazioneBean().setIdDominio(currentJob.getIdSpAoo());
	}

	/**
	 * Autentica senza controllo di password l'utente specificato presso il
	 * dominio fornito
	 * 
	 * @param idDominio
	 * @param tipoDominio
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private DmpkLoginLoginBean authenticate(BigDecimal idDominio, int tipoDominio, String username) throws Exception {

		DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
		lLoginInput.setUsernamein(username);
		lLoginInput.setFlgtpdominioautio(tipoDominio);
		lLoginInput.setIddominioautio(idDominio);
		lLoginInput.setCodapplicazioneestin(null);
		lLoginInput.setCodistanzaapplestin(null);
		lLoginInput.setFlgrollbckfullin(null);
		lLoginInput.setFlgautocommitin(1);
		lLoginInput.setFlgnoctrlpasswordin(1);

		DmpkLoginLogin lLogin = new DmpkLoginLogin();

		StoreResultBean<DmpkLoginLoginBean> lLoginOutput = lLogin.execute(locale, aurigaLoginBean, lLoginInput);

		if (lLoginOutput.getDefaultMessage() != null) {
			throw new Exception(lLoginOutput.getErrorContext() + " - " + lLoginOutput.getErrorCode() + " - "
					+ lLoginOutput.getDefaultMessage());
		} else {

			DmpkLoginLoginBean retValue = lLoginOutput.getResultBean();
			logger.debug(String.format("Autenticazione avvenuta con successo, token %1$s",
					retValue.getCodidconnectiontokenout()));

			return retValue;
		}

	}

	private Boolean configura() {

		Boolean valid = Boolean.TRUE;

		context = SpringHelper.getMainApplicationContext();

		username = (String) getAttribute(JOBATTRKEY_USERNAME);

		schema = (String) getAttribute(JOBATTRKEY_SCHEMA);

		valid = schema != null && !schema.isEmpty();

		if (!valid) {
			logger.error("Schema non configurato");
			return valid;
		}

		aurigaLoginBean = new AurigaLoginBean();

		String localeValue = (String) getAttribute(JOBATTRKEY_LOCALE);

		valid = localeValue != null && !localeValue.isEmpty();

		if (!valid) {
			logger.error("Locale non specificato");
			return valid;
		}

		aurigaLoginBean.setLinguaApplicazione(localeValue);
		aurigaLoginBean.setSchema(schema);

		String tipoDominioValue = (String) getAttribute(JOBATTRKEY_TIPO_DOMINIO);

		valid = tipoDominioValue != null && !tipoDominioValue.isEmpty();

		if (valid) {

			tipoDominio = Integer.valueOf(tipoDominioValue);

			SpecializzazioneBean specializzazioneBean = new SpecializzazioneBean();
			specializzazioneBean.setTipoDominio(Integer.valueOf(tipoDominio));

			aurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);

		} else {
			logger.error("Tipo dominio non specificato");
			return valid;
		}

		locale = new Locale(localeValue);

		/*try {

			// solo effettuando una qualsiasi ricerca sullo storage posso
			// ricavare qualche informazioni se è correttamente configurato o
			// meno
			String test = StorageImplementation.getStorage().getConfigurazioniStorage();
			logger.info(String.format("Configurazione in uso %1$s", test));
		} catch (Exception e) {

			logger.error("Verificare i puntamenti al file di storage nell'aurigajob.xml, lo storage è nullo", e);

			valid = Boolean.FALSE;

		}*/

		bmtJobsClient = (DaoBmtJobs) context.getBean("bmtJobsClient");

		if (bmtJobsClient == null) {

			logger.error("BmtJobs non correttamente inizializzato");

			valid = Boolean.FALSE;
		}

		daoBmtJobParameters = (DaoBmtJobParameters) context.getBean("bmtJobParametersClient");

		return valid;
	}

	@Override
	public void end(String arg0) {
	}
}
