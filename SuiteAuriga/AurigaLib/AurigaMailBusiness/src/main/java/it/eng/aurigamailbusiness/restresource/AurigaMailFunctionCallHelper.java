/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsbatchBean;
import it.eng.auriga.database.store.dmpk_dizionario.bean.DmpkDizionarioTrovadictvaluesfordictentryBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAnnullaarchiviazioneemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAnnullaassegnazioneemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailArchiviaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAssegnaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCollegaregtoemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCtrlutenzaabilitatainvioBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLoaddettemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLockemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailSetazionedafaresuemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTagemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailUnlockemailBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginapplicazioneBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginconcredenzialiesterneBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailMetadata;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailMetadataOrdering;
import it.eng.aurigamailbusiness.bean.restrepresentation.SezioneCacheFiltriTrovaEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.AssignRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CancelAssignmentRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CancelClosingRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CloseRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CollegaRegToEmailRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.DictionaryLookupRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.ExternalApplicationLoginRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.GetEmailBoxesRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.LockRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.LookupRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.SetActionToDoRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.TagRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.UnlockRequest;
import it.eng.client.DmpkBmanagerInsbatch;
import it.eng.client.DmpkDizionarioTrovadictvaluesfordictentry;
import it.eng.client.DmpkIntMgoEmailAnnullaarchiviazioneemail;
import it.eng.client.DmpkIntMgoEmailAnnullaassegnazioneemail;
import it.eng.client.DmpkIntMgoEmailArchiviaemail;
import it.eng.client.DmpkIntMgoEmailAssegnaemail;
import it.eng.client.DmpkIntMgoEmailCollegaregtoemail;
import it.eng.client.DmpkIntMgoEmailCtrlutenzaabilitatainvio;
import it.eng.client.DmpkIntMgoEmailLoaddettemail;
import it.eng.client.DmpkIntMgoEmailLockemail;
import it.eng.client.DmpkIntMgoEmailSetazionedafaresuemail;
import it.eng.client.DmpkIntMgoEmailTagemail;
import it.eng.client.DmpkIntMgoEmailTrovaemail;
import it.eng.client.DmpkIntMgoEmailUnlockemail;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkLoginLogin;
import it.eng.client.DmpkLoginLoginapplicazione;
import it.eng.client.DmpkLoginLoginconcredenzialiesterne;
import it.eng.xml.XmlUtilitySerializer;

public class AurigaMailFunctionCallHelper {

	private static final Logger logger = Logger.getLogger(AurigaMailFunctionCallHelper.class);

	private final DmpkLoginLoginconcredenzialiesterne client_LOGIN_CON_CREDENZIALI_ESTERNE;
	private final DmpkLoginLoginapplicazione client_LOGIN_APPLICAZIONE;
	private final DmpkLoginLogin client_LOGIN;

	private final DmpkIntMgoEmailCtrlutenzaabilitatainvio client_CTRL_UTENZA_ABILITATA_INVIO;
	private final DmpkIntMgoEmailTrovaemail client_TROVA_EMAIL;// ricerca
	private final DmpkIntMgoEmailLoaddettemail client_LOAD_DETT_EMAIL;// dettaglio
	private final DmpkIntMgoEmailSetazionedafaresuemail client_SET_AZIONE_DA_FARE_SU_EMAIL;

	private final DmpkIntMgoEmailLockemail client_LOCK_EMAIL;// presa in carico
	private final DmpkIntMgoEmailUnlockemail client_UNLOCK_EMAIL;// rilascio

	private final DmpkIntMgoEmailArchiviaemail client_ARCHIVIA_EMAIL;// chiusura
	private final DmpkIntMgoEmailAnnullaarchiviazioneemail client_ANNULLA_ARCHIVIAZIONE_EMAIL;// riapertura

	private final DmpkIntMgoEmailAssegnaemail client_ASSEGNA_EMAIL;
	private final DmpkIntMgoEmailAnnullaassegnazioneemail client_ANNULLA_ASSEGNAZIONE_EMAIL;

	private final DmpkIntMgoEmailTagemail client_TAG_EMAIL;

	private final DmpkDizionarioTrovadictvaluesfordictentry client_TROVA_DICT_VALUES_FOR_DICT_ENTRY;

	private final DmpkIntMgoEmailCollegaregtoemail client_COLLEGA_REG_EMAIL;
	
	private final DmpkBmanagerInsbatch client_BMANAGER_INSBATCH;
//	private final DaoBmtJobs client_JOBS;
//	private final DaoBmtJobParameters client_JOB_PARAMETERS;
	
	private final DmpkLoadComboDmfn_load_combo client_DMFN_LOAD_COMBO;

	private final XmlUtilitySerializer xmlUtilitySerializer;

	public AurigaMailFunctionCallHelper() {
		client_LOGIN_APPLICAZIONE = new DmpkLoginLoginapplicazione();
		client_LOGIN_CON_CREDENZIALI_ESTERNE = new DmpkLoginLoginconcredenzialiesterne();
		client_LOGIN = new DmpkLoginLogin();
		client_CTRL_UTENZA_ABILITATA_INVIO = new DmpkIntMgoEmailCtrlutenzaabilitatainvio();
		client_TROVA_EMAIL = new DmpkIntMgoEmailTrovaemail();
		client_LOAD_DETT_EMAIL = new DmpkIntMgoEmailLoaddettemail();
		client_SET_AZIONE_DA_FARE_SU_EMAIL = new DmpkIntMgoEmailSetazionedafaresuemail();
		client_LOCK_EMAIL = new DmpkIntMgoEmailLockemail();
		client_UNLOCK_EMAIL = new DmpkIntMgoEmailUnlockemail();
		client_ARCHIVIA_EMAIL = new DmpkIntMgoEmailArchiviaemail();
		client_ANNULLA_ARCHIVIAZIONE_EMAIL = new DmpkIntMgoEmailAnnullaarchiviazioneemail();
		client_ASSEGNA_EMAIL = new DmpkIntMgoEmailAssegnaemail();
		client_ANNULLA_ASSEGNAZIONE_EMAIL = new DmpkIntMgoEmailAnnullaassegnazioneemail();
		client_TAG_EMAIL = new DmpkIntMgoEmailTagemail();
		client_TROVA_DICT_VALUES_FOR_DICT_ENTRY = new DmpkDizionarioTrovadictvaluesfordictentry();
		client_COLLEGA_REG_EMAIL = new DmpkIntMgoEmailCollegaregtoemail();
//		client_JOBS = new DaoBmtJobs();
//		client_JOB_PARAMETERS = new DaoBmtJobParameters();
		client_BMANAGER_INSBATCH = new DmpkBmanagerInsbatch();
		client_DMFN_LOAD_COMBO = new DmpkLoadComboDmfn_load_combo();
		xmlUtilitySerializer = new XmlUtilitySerializer();
	}

	/**
	 * Chiama la function ORACLE DMPK_LOGIN.LOGINCONCREDENZIALIESTERNE()
	 * 
	 * @param applicazione
	 * @param istanzaApplicazione
	 * @param username
	 * @param password
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkLoginLoginconcredenzialiesterneBean> callLoginConCredenzialiEsterneFunc(ExternalApplicationLoginRequest bean, String schema)
			throws Exception {
		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setSchema(schema);

		final DmpkLoginLoginconcredenzialiesterneBean params = new DmpkLoginLoginconcredenzialiesterneBean();
		params.setCodapplicazionein(bean.getApplication());
		params.setCodistanzaapplin(bean.getApplicationInstance());
		params.setUsernamein(bean.getUsername());
		params.setPasswordin(bean.getPassword());

		StoreResultBean<DmpkLoginLoginconcredenzialiesterneBean> storeResultBean = null;
		storeResultBean = client_LOGIN_CON_CREDENZIALI_ESTERNE.execute(Locale.ITALIAN, aurigaLoginBean, params);

		return storeResultBean;
	}// callLoginConCredenzialiEsterneFunc

	/**
	 * Chiama la function ORACLE DMPK_LOGIN.LOGINAPPLICAZIONE()
	 * 
	 * @param userId
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkLoginLoginapplicazioneBean> callLoginApplicazioneFunc(String userId, String schema) throws Exception {
		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setSchema(schema);

		final DmpkLoginLoginapplicazioneBean params = new DmpkLoginLoginapplicazioneBean();
		params.setUseridapplicazionein(userId);
		params.setFlgnoctrlpasswordin(1);

		StoreResultBean<DmpkLoginLoginapplicazioneBean> storeResultBean = null;
		storeResultBean = client_LOGIN_APPLICAZIONE.execute(Locale.ITALIAN, aurigaLoginBean, params);

		return storeResultBean;
	}// callLoginApplicazioneFunc
	
	/**
	 * Chiama la function ORACLE DMPK_LOGIN.LOGIN()
	 * 
	 * @param userId
	 * @param password
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkLoginLoginBean> callLoginFunc(String userId, String password, String schema)
			throws Exception {
		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setSchema(schema);

		final DmpkLoginLoginBean params = new DmpkLoginLoginBean();
		params.setUsernamein(userId);
		params.setPasswordin(password);

		StoreResultBean<DmpkLoginLoginBean> storeResultBean = null;
		storeResultBean = client_LOGIN.execute(Locale.ITALIAN, aurigaLoginBean, params);

		return storeResultBean;
	}// callLoginFunc


	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.CTRLUTENZAABILITATAINVIO()
	 * 
	 * @param token
	 * @param account
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailCtrlutenzaabilitatainvioBean> callCtrlUtenzaAbilitataInvioFunc(String token, String account, String schema)
			throws Exception {
		logger.debug("callCtrlUtenzaAbilitataInvioFunc inizio");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(token);
		aurigaLoginBean.setSchema(schema);
		// aurigaLoginBean.setIdUserLavoro("113");//ho visto che da un'altra parte viene valorizzato

		final DmpkIntMgoEmailCtrlutenzaabilitatainvioBean params = new DmpkIntMgoEmailCtrlutenzaabilitatainvioBean();
		params.setCodidconnectiontokenin(token);
		params.setIduserlavoroin(null);
		params.setAccountmittentein(account);

		StoreResultBean<DmpkIntMgoEmailCtrlutenzaabilitatainvioBean> storeResultBean = null;
		storeResultBean = client_CTRL_UTENZA_ABILITATA_INVIO.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callCtrlUtenzaAbilitataInvioFunc fine");
		return storeResultBean;
	}// callCtrlUtenzaAbilitataInvioFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.SETAZIONEDAFARESUEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailSetazionedafaresuemailBean> callSetAzioneDaFareSuEmailFunc(SetActionToDoRequest bean, String schema)
			throws Exception {
		logger.debug("callSetAzioneDaFareSuEmailFunc inizio");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailSetazionedafaresuemailBean params = new DmpkIntMgoEmailSetazionedafaresuemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setIdemailin(bean.getIdEmail());
		params.setCodazionedafarein(bean.getCodiceAzione());
		params.setDettazionedafarein(bean.getDettaglioAzione());
		params.setFlgrilasciolockin(BooleanUtils.toIntegerObject(bean.getFlagRilascioLock()));

		// parametri di input non valorizzati
		params.setFlgazionecompletatain(null);// Integer - FlgAzioneCompletataIn
		params.setIduolavoroin(null);// BigDecimal - IdUOLavoroIn
		params.setFlgrollbckfullin(null);// Integer - FlgRollBckFullIn

		StoreResultBean<DmpkIntMgoEmailSetazionedafaresuemailBean> storeResultBean = null;
		storeResultBean = client_SET_AZIONE_DA_FARE_SU_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callSetAzioneDaFareSuEmailFunc fine");
		return storeResultBean;
	}// callSetAzioneDaFareSuEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.ASSEGNAEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailAssegnaemailBean> callAssegnaEmailFunc(AssignRequest bean, String schema) throws Exception {
		logger.debug("callAssegnaEmailFunc inizio");

		final String assegnatarixml = xmlUtilitySerializer.bindXmlList(bean.getListaAssegnatariXML().getItems());

		// logger.debug("=======================================================================");
		// logger.debug("AssegnatariXMLIn:\n"+ String.valueOf(assegnatarixml) );
		// logger.debug("=======================================================================");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailAssegnaemailBean params = new DmpkIntMgoEmailAssegnaemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setAssegnatarixmlin(assegnatarixml);
		params.setIdemailin(bean.getIdEmail());

		// parametri di input non valorizzati
		params.setFlgrollbckfullin(null);// Integer - FlgRollBckFullIn

		StoreResultBean<DmpkIntMgoEmailAssegnaemailBean> storeResultBean = null;
		storeResultBean = client_ASSEGNA_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callAssegnaEmailFunc fine");
		return storeResultBean;
	}// callAssegnaEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.ANNULLAASSEGNAZIONEEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailAnnullaassegnazioneemailBean> callAnnullaAssegnazioneEmailFunc(CancelAssignmentRequest bean, String schema)
			throws Exception {
		logger.debug("callAnnullaAssegnazioneEmailFunc inizio");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailAnnullaassegnazioneemailBean params = new DmpkIntMgoEmailAnnullaassegnazioneemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setIdemailin(bean.getIdEmail());
		params.setMotiviin(bean.getMotivi());

		// parametri di input non valorizzati
		params.setIdassemailtoannin(null);// String - IdAssEmailToAnnIn
		params.setFlgrollbckfullin(null);// Integer - FlgRollBckFullIn

		StoreResultBean<DmpkIntMgoEmailAnnullaassegnazioneemailBean> storeResultBean = null;
		storeResultBean = client_ANNULLA_ASSEGNAZIONE_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callAnnullaAssegnazioneEmailFunc fine");
		return storeResultBean;
	}// callAnnullaAssegnazioneEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.LOCKEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailLockemailBean> callLockEmailFunc(LockRequest bean, String schema) throws Exception {
		logger.debug("callLockEmailFunc inizio");

		final String listaidemail = xmlUtilitySerializer.bindXmlList(bean.getListaIdEmail().getItems());

		// logger.debug("=======================================================================");
		// logger.debug("ListaIdEmailIn:\n"+ String.valueOf(listaidemail) );
		// logger.debug("=======================================================================");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailLockemailBean params = new DmpkIntMgoEmailLockemailBean();
		params.setFlgautocommitin(1);
		// FIXME
		// Domande: Va bene valorizzato in questo modo ?
		params.setFlglockimplin(0);// Integer

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setListaidemailin(listaidemail);
		params.setMotiviin(bean.getMotivi());
		params.setIduserlockforin(bean.getIdUtenteDestinatarioLock());

		// parametri di input non valorizzati
		params.setIduolavoroin(null);// BigDecimal
		params.setIdrecdizoperlockin(null);// String
		params.setDesoperlockin(null);// String
		params.setFlgrollbckfullin(null);// Integer

		StoreResultBean<DmpkIntMgoEmailLockemailBean> storeResultBean = null;
		storeResultBean = client_LOCK_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callLockEmailFunc fine");
		return storeResultBean;
	}// callLockEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.UNLOCKEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailUnlockemailBean> callUnlockEmailFunc(UnlockRequest bean, String schema) throws Exception {
		logger.debug("callUnlockEmailFunc inizio");

		final String listaidemail = xmlUtilitySerializer.bindXmlList(bean.getListaIdEmail().getItems());

		// logger.debug("=======================================================================");
		// logger.debug("ListaIdEmailIn:\n"+ String.valueOf(listaidemail) );
		// logger.debug("=======================================================================");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailUnlockemailBean params = new DmpkIntMgoEmailUnlockemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setListaidemailin(listaidemail);
		params.setMotiviin(bean.getMotivi());

		// parametri di input non valorizzati
		params.setFlgrollbckfullin(null);// Integer - FlgRollBckFullIn

		StoreResultBean<DmpkIntMgoEmailUnlockemailBean> storeResultBean = null;
		storeResultBean = client_UNLOCK_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callUnlockEmailFunc fine");
		return storeResultBean;
	}// callUnlockEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.ARCHIVIAEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailArchiviaemailBean> callArchiviaEmailFunc(CloseRequest bean, String schema) throws Exception {
		logger.debug("callArchiviaEmailFunc inizio");

		final String listaidemail = xmlUtilitySerializer.bindXmlList(bean.getListaIdEmail().getItems());

		// logger.debug("=======================================================================");
		// logger.debug("ListaIdEmailIn:\n"+ String.valueOf(listaidemail) );
		// logger.debug("=======================================================================");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailArchiviaemailBean params = new DmpkIntMgoEmailArchiviaemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setListaidemailin(listaidemail);
		params.setMotiviin(bean.getMotivi());

		// parametri di input non valorizzati
		params.setFlgrollbckfullin(null);// Integer - FlgRollBckFullIn
		params.setFlggestazionedafarein(null);// String - FlgGestAzioneDaFareIn

		StoreResultBean<DmpkIntMgoEmailArchiviaemailBean> storeResultBean = null;
		storeResultBean = client_ARCHIVIA_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callArchiviaEmailFunc fine");
		return storeResultBean;
	}// callArchiviaEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.ANNULLAARCHIVIAZIONEEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailAnnullaarchiviazioneemailBean> callAnnullaArchiviazioneEmailFunc(CancelClosingRequest bean, String schema)
			throws Exception {
		logger.debug("callAnnullaArchiviazioneEmailFunc inizio");

		final String listaidemail = xmlUtilitySerializer.bindXmlList(bean.getListaIdEmail().getItems());

		// logger.debug("=======================================================================");
		// logger.debug("ListaIdEmailIn:\n"+ String.valueOf(listaidemail) );
		// logger.debug("=======================================================================");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailAnnullaarchiviazioneemailBean params = new DmpkIntMgoEmailAnnullaarchiviazioneemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setListaidemailin(listaidemail);
		params.setMotiviin(bean.getMotivi());
		params.setFlgrilasciolockin(BooleanUtils.toIntegerObject(bean.getFlagRilascioLock()));

		// parametri di input non valorizzati
		params.setIduolavoroin(null);// BigDecimal - IdUOLavoroIn
		params.setFlgrollbckfullin(null);// Integer - FlgRollBckFullIn

		StoreResultBean<DmpkIntMgoEmailAnnullaarchiviazioneemailBean> storeResultBean = null;
		storeResultBean = client_ANNULLA_ARCHIVIAZIONE_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callAnnullaArchiviazioneEmailFunc fine");
		return storeResultBean;
	}// callAnnullaArchiviazioneEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.LOADDETTEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailLoaddettemailBean> callLoadDettEmailFunc(String token, String schema, String idEmail, Integer idUtente)
			throws Exception {
		logger.debug("callLoadDettEmailFunc inizio");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(token);
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailLoaddettemailBean params = new DmpkIntMgoEmailLoaddettemailBean();
		params.setFlgsoloabilitazioniin(0);

		params.setCodidconnectiontokenin(token);
		params.setIdemailin(idEmail);
		params.setIduserlavoroin(getIdUserLavoro(idUtente));

		// parametri di input non valorizzati
		params.setIdemailprecin(null);// String - IdEmailPrecIn

		StoreResultBean<DmpkIntMgoEmailLoaddettemailBean> storeResultBean = null;
		storeResultBean = client_LOAD_DETT_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callLoadDettEmailFunc fine");
		return storeResultBean;
	}// callLoadDettEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.TROVAEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailTrovaemailBean> callTrovaEmailFunc(LookupRequest bean, String schema) throws Exception {
		logger.debug("callTrovaEmailFunc inizio");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final SezioneCacheFiltriTrovaEmail filterBean = bean.getFiltro();

		// //range Anno email
		// filterBean.setAnnoEmailDa(annoEmailDa);
		// filterBean.setAnnoEmailA(annoEmailA);
		//
		// //range Anno Reg
		// filterBean.setAnnoRegDa(annoRegDa);
		// filterBean.setAnnoRegA(annoRegA);
		//
		// //range Assegnazione
		// filterBean.setAssegnazioneDal(assegnazioneDal);
		// filterBean.setAssegnazioneAl(assegnazioneAl);
		//
		// //range Data di Movimentazione
		// filterBean.setDataDiMovimentazioneDal(dataDiMovimentazioneDal);
		// filterBean.setDataDiMovimentazioneAl(dataDiMovimentazioneAl);
		//
		// //range Dimensione
		// filterBean.setDimensioneDa(dimensioneDa);
		// filterBean.setDimensioneA(dimensioneA);
		//
		// //range Data Archiviazione
		// filterBean.setDtArchiviazioneDal(dtArchiviazioneDal);
		// filterBean.setDtArchiviazioneAl(dtArchiviazioneAl);
		//
		// //range Data Reg
		// filterBean.setDtRegDal(dtRegDal);
		// filterBean.setDtRegAl(dtRegAl);
		//
		// //range Inserita
		// filterBean.setInseritaDal(inseritaDal);
		// filterBean.setInseritaAl(inseritaAl);
		//
		// //range Invio
		// filterBean.setInvioDal(invioDal);
		// filterBean.setInvioAl(invioAl);
		//
		// //range Lavorati
		// filterBean.setLavoratiDal(lavoratiDal);
		// filterBean.setLavoratiAl(lavoratiAl);
		//
		// //range Nro Allegati
		// filterBean.setNroAllegatiDa(nroAllegatiDa);
		// filterBean.setNroAllegatiA(nroAllegatiA);
		//
		// //range Nro Destinatari
		// filterBean.setNroDestinatariDa(nroDestinatariDa);
		// filterBean.setNroDestinatariA(nroDestinatariA);
		//
		// //range Nro Giorni Stato Lavorazione APERTO
		// filterBean.setNroGiorniStatoLavorazioneApertoDa(nroGiorniStatoLavorazioneApertoDa);
		// filterBean.setNroGiorniStatoLavorazioneApertoAl(nroGiorniStatoLavorazioneApertoAl);
		//
		// //range Nro Reg
		// filterBean.setNroRegDa(nroRegDa);
		// filterBean.setNroRegA(nroRegA);
		//
		// //range Progr Email
		// filterBean.setProgrEmailDa(progrEmailDa);
		// filterBean.setProgrEmailA(progrEmailA);
		//
		// //range Ricezione
		// filterBean.setRicezioneDal(ricezioneDal);
		// filterBean.setRicezioneAl(ricezioneAl);
		//
		// filterBean.setAssegnazioneEffettuata(assegnazioneEffettuata);
		//
		// filterBean.setCategoria(categoria);
		// filterBean.setClassificaFolder(classificaFolder);
		// filterBean.setCodAzioneDaFare(codAzioneDaFare);
		// filterBean.setCodCategoriaReg(codCategoriaReg);
		// filterBean.setCorpo(corpo);
		// filterBean.setCruscottoMail(cruscottoMail);
		// filterBean.setFlgIO(flgIO);
		// filterBean.setIdCasella(idCasella);
		// filterBean.setIdEmailInoltroDi(idEmailInoltroDi);
		// filterBean.setIdEmailRif(idEmailRif);
		// filterBean.setIdEmailRisposteA(idEmailRisposteA);
		// filterBean.setIdMessaggio(idMessaggio);
		// filterBean.setIdUOLavoro(idUOLavoro);
		// filterBean.setIdUserAssegnatario(idUserAssegnatario);
		// filterBean.setInCaricoA(inCaricoA);
		// filterBean.setIncludiAssegnateAdAltri(includiAssegnateAdAltri);
		// filterBean.setIndirizzoCasella(indirizzoCasella);
		// filterBean.setIndirizzoDestinatario(indirizzoDestinatario);
		// filterBean.setIndirizzoMittente(indirizzoMittente);
		// filterBean.setInoltrata(inoltrata);
		// filterBean.setInviataRisposta(inviataRisposta);
		// filterBean.setLavoratoDa(lavoratoDa);
		// filterBean.setMessageId(messageId);
		// filterBean.setMovimentatoDa(movimentatoDa);
		// filterBean.setNomeFileAssociato(nomeFileAssociato);
		// filterBean.setNoteApposte(noteApposte);
		// filterBean.setOggetto(oggetto);
		// filterBean.setOperDestinatario(operDestinatario);
		// filterBean.setOperFileAssociato(operFileAssociato);
		// filterBean.setOperMittente(operMittente);
		// filterBean.setPresenzaAllegati(presenzaAllegati);
		// filterBean.setPresenzaAvvertimenti(presenzaAvvertimenti);
		// filterBean.setRegistroReg(registroReg);
		// filterBean.setRicercaNonRicorsiva(ricercaNonRicorsiva);
		// filterBean.setRicevuteConferme(ricevuteConferme);
		// filterBean.setRicevuteEccezioni(ricevuteEccezioni);
		// filterBean.setRicevutiAggiornamenti(ricevutiAggiornamenti);
		// filterBean.setRicevutiAnnullamenti(ricevutiAnnullamenti);
		// filterBean.setRicevutiErroriTrasmConsegna(ricevutiErroriTrasmConsegna);
		// filterBean.setStatoConsolidamento(statoConsolidamento);
		// filterBean.setStatoIAC(statoIAC);
		// filterBean.setStatoLavorazioneUrgenza(statoLavorazioneUrgenza);
		// filterBean.setStatoProtocollazione(statoProtocollazione);
		// filterBean.setTagApposto(tagApposto);
		// filterBean.setTipoEmail(tipoEmail);
		// filterBean.setTipoProgrEmail(tipoProgrEmail);
		final String filter = xmlUtilitySerializer.bindXml(filterBean);
		logger.debug("=======================================================================");
		logger.debug("FiltriIO:\n" + filter);
		logger.debug("=======================================================================");

		final DmpkIntMgoEmailTrovaemailBean params = new DmpkIntMgoEmailTrovaemailBean();

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setFiltriio(filter);

		params.setFlgsenzapaginazionein(BooleanUtils.toIntegerObject(!bean.isFlagPaginazione()));// Integer
		params.setOverflowlimitin(1000);
		params.setFlgsenzatotin(0);

		if (bean.getColonne() != null) {
			final List<EmailMetadata> set = bean.getColonne().getIntestazione();
			if (set != null) {
				final String string = set.toString();
				params.setColtoreturnin(StringUtils.mid(string, 1, string.length() - 2));
				logger.debug("ColToReturnIn: " + params.getColtoreturnin());
			}
		}

		if (bean.getOrdinamento() != null) {
			final List<EmailMetadataOrdering> set = bean.getOrdinamento().getMetadato();
			if (set != null) {
				String orderBy = "";
				String flgDescOrderBy = "";
				for (EmailMetadataOrdering metadato : set) {
					if (metadato.getIntestazioneColonna() == null)
						continue;
					orderBy += "," + metadato.getIntestazioneColonna().toString();
					flgDescOrderBy += "," + BooleanUtils.toIntegerObject(metadato.isDiscendente());
				}
				if (!orderBy.isEmpty()) {
					params.setColorderbyio(orderBy.substring(1));
					params.setFlgdescorderbyio(flgDescOrderBy.substring(1));
				}

				logger.debug("ColOrderByIO: " + String.valueOf(params.getColorderbyio()));
				logger.debug("FlgDescOrderByIO: " + String.valueOf(params.getFlgdescorderbyio()));
			}
		}

		if (bean.isFlagPaginazione()) {
			final boolean flagPos = bean.getPagina() != null && bean.getPagina() > 0;
			params.setNropaginaio(flagPos ? bean.getPagina() : 1);
			params.setBachsizeio(bean.getElementiPerPagina());
		}

		// parametri di input non valorizzati
		params.setFlgbatchsearchin(null);// Integer - FlgBatchSearchIn

		StoreResultBean<DmpkIntMgoEmailTrovaemailBean> storeResultBean = null;
		storeResultBean = client_TROVA_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callTrovaEmailFunc fine");
		return storeResultBean;
	}// callTrovaEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.TAGEMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailTagemailBean> callTagEmailFunc(TagRequest bean, String schema) throws Exception {
		logger.debug("callTagEmailFunc inizio");

		final String listaidemail = xmlUtilitySerializer.bindXmlList(bean.getListaIdEmail().getItems());
		final String xmltag = xmlUtilitySerializer.bindXmlList(bean.getListaXMLTag().getItems());

		// logger.debug("=======================================================================");
		// logger.debug("ListaIdEmailIn:\n"+ String.valueOf(listaidemail) );
		// logger.debug("XMLTagIn:\n"+ String.valueOf(xmltag) );
		// logger.debug("=======================================================================");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailTagemailBean params = new DmpkIntMgoEmailTagemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setListaidemailin(listaidemail);
		params.setXmltagin(xmltag);

		// parametri di input non valorizzati
		// params.setFlgrollbckfullin(null);//Integer - FlgRollBckFullIn

		StoreResultBean<DmpkIntMgoEmailTagemailBean> storeResultBean = null;
		storeResultBean = client_TAG_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callTagEmailFunc fine");
		return storeResultBean;
	}// callTagEmailFunc

	/**
	 * Chiama la function ORACLE DMPK_DIZIONARIO.TROVADICTVALUESFORDICTENTRY()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkDizionarioTrovadictvaluesfordictentryBean> callTrovadictvaluesfordictentryFunc(DictionaryLookupRequest bean, String schema)
			throws Exception {
		logger.debug("callTrovadictvaluesfordictentryFunc inizio");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkDizionarioTrovadictvaluesfordictentryBean params = new DmpkDizionarioTrovadictvaluesfordictentryBean();
		params.setOverflowlimitin(1000);
		params.setFlgsenzatotin(0);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));
		params.setDictionaryentryin(bean.getVoceDizionario());
		params.setStrinvaluein(bean.getValoreContenuto());
		params.setStrincodvaluein(bean.getCodiceContenuto());
		// ----------------------------------------------------------------
		params.setFlgsenzapaginazionein(BooleanUtils.toIntegerObject(!bean.isFlagPaginazione()));
		if (bean.isFlagPaginazione()) {
			final boolean flagPos = bean.getPagina() != null && bean.getPagina() > 0;
			params.setNropaginaio(flagPos ? bean.getPagina() : 1);
			params.setBachsizeio(bean.getElementiPerPagina());
		}

		// parametri di input non valorizzati
		// params.setTsrifin(null);
		// params.setValuegenvincoloin("????");
		// params.setRestringiadizdiuoin("????");
		// params.setColorderbyio("????");
		// params.setFlgdescorderbyio("????");

		logger.debug("DictionaryEntryIn: " + String.valueOf(params.getDictionaryentryin()));

		StoreResultBean<DmpkDizionarioTrovadictvaluesfordictentryBean> storeResultBean = null;
		storeResultBean = client_TROVA_DICT_VALUES_FOR_DICT_ENTRY.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callTrovadictvaluesfordictentryFunc fine");
		return storeResultBean;
	}// callTrovadictvaluesfordictentryFunc

	/**
	 * Chiama la function ORACLE DMPK_INT_MGO_EMAIL.COLLEGA_REG_TO_EMAIL()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkIntMgoEmailCollegaregtoemailBean> callCollegaRegToEmailFunc(CollegaRegToEmailRequest bean, String schema) throws Exception {
		logger.debug("callCollegaRegToEmailFunc inizio");

		final String listaimpronte = xmlUtilitySerializer.bindXmlList(bean.getImpronteAllegati());

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(bean.getToken());
		aurigaLoginBean.setSchema(schema);

		final DmpkIntMgoEmailCollegaregtoemailBean params = new DmpkIntMgoEmailCollegaregtoemailBean();
		params.setFlgautocommitin(1);

		params.setCodidconnectiontokenin(bean.getToken());
		params.setIduserlavoroin(getIdUserLavoro(bean.getIdUtente()));

		// TODO: capire se conviene mettere nella Request direttamente i campi in BigDecimal
		params.setIdemailin(bean.getIdEmail());
		params.setIdudin(new BigDecimal(bean.getIdUd()));
		params.setCategoriaregin(bean.getCategoriaReg());
		params.setSiglaregistroin(bean.getSiglaRegistro());
		params.setAnnoregin(new BigDecimal(bean.getAnnoReg()));
		params.setNumeroregin(new BigDecimal(bean.getNumeroReg()));
		params.setImpronteallegatiin(listaimpronte);

		StoreResultBean<DmpkIntMgoEmailCollegaregtoemailBean> storeResultBean = null;
		storeResultBean = client_COLLEGA_REG_EMAIL.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callCollegaRegToEmailFunc fine");
		return storeResultBean;
	}
	
	/**
	 * Chiama la function ORACLE DMPK_BMANAGER.INSBATCH()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws ExceptionBmanager
	 */
	public StoreResultBean<DmpkBmanagerInsbatchBean> callInsBatchFunc(JobBean bean, String jobParamsXml, String schema) throws Exception {
		logger.debug("callInsBatchFunc inizio");
		
		final SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(schema);
		
		final DmpkBmanagerInsbatchBean params = new DmpkBmanagerInsbatchBean();
		params.setFlgautocommitin(1);
		
		params.setCodapplicazionein(bean.getCodApplOwner());
		params.setCodistapplicazionein(bean.getCodIstApplOwner());
		params.setConndriverin(bean.getConnDriver());
		params.setConnstringin(bean.getConnString());
		params.setFlgsoloxbassaoperin(BooleanUtils.toIntegerObject(bean.getFlgXBassaOper()));
		params.setFormatofileoutputin(bean.getFormato());
		params.setIddominioin(bean.getIdSpAoo());
		params.setNomefileoutputin(bean.getExportFilename());
		params.setParametrixmlin(jobParamsXml);
		params.setPrioritain(BooleanUtils.toIntegerObject(bean.getPriorita()));
//		params.setScheduletimein(bean.getScheduleTime());
		params.setTipojobin(bean.getTipo());
		params.setUseridin(bean.getIdUser());
		
		StoreResultBean<DmpkBmanagerInsbatchBean> storeResultBean = null;
		storeResultBean = client_BMANAGER_INSBATCH.execute(Locale.ITALIAN, schemaBean, params);
		
		logger.debug("callInsBatchFunc fine");
		return storeResultBean;
	}
	
	/**
	 * Chiama la function ORACLE DMPK_LOAD_COMBO.DMFN_LOAD_COMBO()
	 * 
	 * @param bean
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public StoreResultBean<DmpkLoadComboDmfn_load_comboBean> callDmfnLoadComboFunc(GetEmailBoxesRequest bean, String token, String schema) throws Exception {
		logger.debug("callDmfnLoadComboFunc inizio");

		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setToken(token);
		aurigaLoginBean.setSchema(schema);
		
		String altriParametri = "";
		final String p1 = getParam("FINALITA", bean.getFinalita());
		final String p2 = getParam("TIPO_CASELLA", bean.getTipoCasella());
		final String p3 = getParam("STR_IN_ACCOUNT", bean.getIndirizzoEmail());
        for (String p : new String[] {p1, p2, p3}) {
        	altriParametri += p;
		}
        logger.info("altriParametri: "+altriParametri);
		
		final DmpkLoadComboDmfn_load_comboBean params = new DmpkLoadComboDmfn_load_comboBean();
		params.setAltriparametriin(altriParametri);
		params.setCodidconnectiontokenin(token);
		params.setFlgsolovldin(null);
		params.setPkrecin(null);
		params.setTipocomboin("CASELLE_INV_RIC");
		params.setTsvldin(null);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResultBean = null;
		storeResultBean = client_DMFN_LOAD_COMBO.execute(Locale.ITALIAN, aurigaLoginBean, params);

		logger.debug("callDmfnLoadComboFunc fine");
		return storeResultBean;
	}

	private final String getParam(final String nome, final String valore) {
		String input = "";
		if (StringUtils.isNotBlank(valore)) {
			final String val = valore.trim();
			input = nome+"|*|"+val+"|*|";
		}
		return input;
	}

	
	
//	public JobBean saveBmtJobs(JobBean params, String schema) throws Exception {
//		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
//		aurigaLoginBean.setSchema(schema);
//		final JobBean jobBean = client_JOBS.save(Locale.ITALIAN, aurigaLoginBean, params);
//		return jobBean;
//	}
//	
//	public JobParameterBean saveBmtJobParameters(JobParameterBean params, String schema) throws Exception {
//		final AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
//		aurigaLoginBean.setSchema(schema);
//		final JobParameterBean jobParamBean = client_JOB_PARAMETERS.save(Locale.ITALIAN, aurigaLoginBean, params);
//		return jobParamBean;
//	}


	private BigDecimal getIdUserLavoro(Number id) {
		return id != null ? new BigDecimal(id.toString()) : null;
	}

}// AurigaMailFunctionCallHelper
