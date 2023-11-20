/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.jdbc.Work;

import com.sun.xml.ws.developer.SchemaValidation;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsbatchBean;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.InsbatchImpl;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginMarktokenusageBean;
import it.eng.auriga.database.store.dmpk_login.store.impl.MarktokenusageImpl;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFinddoctype_jBean;
import it.eng.auriga.database.store.dmpk_utility.store.Finddoctype_j;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.DaoBmtJobParameters;
import it.eng.auriga.module.business.dao.DaoDMVConsumerWS;
import it.eng.auriga.module.business.dao.DaoDmtLottiDocElaborazioniMassive;
import it.eng.auriga.module.business.dao.DaoTParametri;
import it.eng.auriga.module.business.dao.beans.DmtLottiDocElabMassiveBean;
import it.eng.auriga.module.business.dao.beans.DmvConsumerWSBean;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.auriga.module.business.entity.DmtLottiDocElabMassive;
import it.eng.auriga.module.business.entity.TParameters;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.auriga.module.business.operazioniaurigalottidoc.exception.OperazioniAurigaLottiDocException;
import it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc.FaultActionsOnDocList;
import it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc.OperationType;
import it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc.RequestActionsOnDocList;
import it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc.RequestActionsOnDocList.Operations.Operation;
import it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc.ResponseActionsOnDocList;
import it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc.ResultType;
import it.eng.enums.ProvenienzaRequestEnum;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.OperazioniAurigaLottiDocConfigBean;
import it.eng.document.function.StoreException;
import it.eng.document.storage.DocumentStorage;
import it.eng.enums.FormatoLottoEnum;
import it.eng.enums.StatoLottoEnum;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.filemanager.FileBean;
import it.eng.utility.filemanager.FileManager;
import it.eng.utility.filemanager.FileManagerFactory;
import it.eng.utility.filemanager.FileManagerUtil;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;

@WebService(targetNamespace = "http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.operazioniaurigalottidoc.WSIOperazioniAurigaLottiDoc", name = "WSOperazioniAurigaLottiDoc")
@SchemaValidation
@MTOM(enabled = true, threshold = 0)

public class WSOperazioniAurigaLottiDoc implements WSIOperazioniAurigaLottiDoc {

	@Resource
	private WebServiceContext context;

	static Logger aLogger = Logger.getLogger(WSOperazioniAurigaLottiDoc.class.getName());

	public static final String PAR_KEY_ROOT_PATH_SERVIZIO_OPER_LOTTI_DOC = "ROOT_PATH_SERVIZIO_OPER_LOTTI_DOC";
	public static final String JOB_AURIGA_LOTTI_DOC = "OP_AURIGA_LOTTI_DOC";
	public static final String JOB_PARAMETER_ID_LOTTO = "ID_LOTTO";
	public static final String JOB_PARAMETER_REQUEST_XML = "REQUEST_XML";
	public static final String JOB_PARAMETER_TOKEN = "CONN_TOKEN";
	public static final String JOB_PARAMETER_ID_USER = "ID_USER";
	public static final String JOB_PARAMETER_ID_UTENTE_MAIL = "ID_UTENTE_MAIL";
	public static final String JOB_PARAMETER_URI = "URI_ATTACHMENT";
	public static final String JOB_PARAMETER_ORIGINE_REQUEST = "ORIGINE_REQUEST";
	private static final String PAR_TYPE_VARCHAR2 = "VARCHAR2";
	private static final String PAR_TYPE_INTEGER = "INTEGER";
	private static final String PAR_TYPE_CLOB = "CLOB";
	private static final String PAR_DIR_IN = "IN";
	

	@Override
	public ResponseActionsOnDocList operazioniAurigaLottiDoc(RequestActionsOnDocList parameter) throws OperazioniAurigaLottiDocFault {

		aLogger.trace(">> operazioniAurigaLottiDoc()");
		aLogger.trace(">> operazioniAurigaLottiDoc() -- " + parameter);

		return elaborateRequest(parameter,ProvenienzaRequestEnum.REQ_ORIGIN_LOTTO.getProvenienzaRequest());

	}
	
	
	@Override
	public ResponseActionsOnDocList operazioniAurigaElencoDoc(RequestActionsOnDocList parameter)
			throws OperazioniAurigaLottiDocFault {
		aLogger.trace(">> operazioniAurigaElencoDoc()");
		aLogger.trace(">> operazioniAurigaElencoDoc() -- " + parameter);

		return elaborateRequest(parameter,ProvenienzaRequestEnum.REQ_ORIGIN_ELENCO.getProvenienzaRequest());
	}
	
	/**
	 * 
	 * @param parameter parametri ricevuti dalla request
	 * @param reqOrigin tipo di request ricevuta, serve per identificare il tipo di operazione richiamata
	 * @return
	 * @throws OperazioniAurigaLottiDocFault
	 */
	private ResponseActionsOnDocList elaborateRequest(RequestActionsOnDocList parameter, String reqOrigin)
			throws OperazioniAurigaLottiDocFault {
		ResponseActionsOnDocList response = new ResponseActionsOnDocList();
		response.setEsito(ResultType.KO);

		try {

			// la validazione dei campi non è necessaria perchè viene già effettuata da XMlValidation, tuttavia controlliamo almeno la presenza del campo
			// principale idLotto
			if (parameter == null || StringUtils.isBlank(parameter.getIdLottoDoc())) {
				FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
				faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getCode()));
				faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getMsg());
				throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getMsg(), faultInfo);
			}

			// recupero configurazione dal bean spring
			OperazioniAurigaLottiDocConfigBean lOperazioniAurigaLottiDocConfigBean = config();
			// configurazioni directory lotto
			FileManager fileManager = FileManagerFactory.getFileManager(lOperazioniAurigaLottiDocConfigBean.getFileManagerConfig());

			// creo bean connessione
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(lOperazioniAurigaLottiDocConfigBean.getDefaultSchema());

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(lSchemaBean.getSchema());
			SubjectUtil.subject.set(subject);

			// recupero credenziali per effettuare le chiamata alla store
			DmvConsumerWSBean consumerWS = getConsumerWS(lSchemaBean);

			// verifico tipologia documentale
			if (StringUtils.isBlank(parameter.getTipoDoc()) || !verificaTipologiaDocumentale(lSchemaBean, consumerWS.getIdSpAoo(), parameter.getTipoDoc())) {
				aLogger.debug("Tipologia documentale non valida: " + parameter.getTipoDoc());
				FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
				faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_DOC_TYPE.getCode()));
				faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_DOC_TYPE.getMsg());
				throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_DOC_TYPE.getMsg(), faultInfo);
			}
			aLogger.debug("Tipologia documentale " + parameter.getTipoDoc() + " valida");

			Long dimensione = null;  // puo essere calcolata direttamente dal file in attach alla chiamata SOAP o recuperato dall'area di destinazione
			String uriAttach = null;  // indica l'uri dell'attachment conversato.
			
			if("ATTACHMENT".equals(parameter.getFormatoLottoDoc()) &&  ProvenienzaRequestEnum.REQ_ORIGIN_ELENCO == ProvenienzaRequestEnum.fromString(reqOrigin)) {
				// Attualmente l'operazione REQ_ORIGIN_ELENCO prevede solo il FormatoLottoDoc di tipo ATTACHMENT
				if(parameter.getIndexAttach() != null) {
					ByteArrayInputStream bis = null;
					try {
						byte[] decoded = Base64.decodeBase64(parameter.getIndexAttach());
						dimensione = Long.valueOf(decoded.length);
						bis = new ByteArrayInputStream(decoded);
						uriAttach = DocumentStorage.storeInput(bis,consumerWS.getIdSpAoo());
						aLogger.debug("Lotto ricevuto tramite attach: " + parameter.getIdLottoDoc() + ". Dimensione in byte: " + dimensione);
					} catch (Exception e) {
						throw e;
					} finally {
						try { bis.close(); } catch (Exception e) {}
					}
				} else {
					FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
					faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_ATTACH.getCode()));
					faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_ATTACH.getMsg());
					throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_ATTACH.getMsg(), faultInfo); 
				}
			} else if(!"ATTACHMENT".equals(parameter.getFormatoLottoDoc()) &&  ProvenienzaRequestEnum.REQ_ORIGIN_LOTTO == ProvenienzaRequestEnum.fromString(reqOrigin)) {
				// REQ_ORIGIN_LOTTO prevede tutti i tipi di Formati tranne quello di tipo ATTACHMENT
				// verifico se esiste il lotto nell'area di destinazione e ne calcolo la dimensione in byte
				dimensione = calcolaDimensioneLotto(fileManager, parameter.getIdLottoDoc(), parameter.getFormatoLottoDoc(), consumerWS.getCiApplicazione());
				aLogger.debug("Trovato lotto " + parameter.getIdLottoDoc() + ". Dimensione in byte: " + dimensione);
			} else {
				// Viene generata un eccezione di tipo INVALID_REQUEST nel caso non siano state rispettate le condizioni precedenti
				FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
				faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getCode()));
				faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getMsg());
				throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_REQUEST.getMsg(), faultInfo); 
			}

			// verifico se esiste in database un lotto con lo stesso id
			if (verificaIdLottoDuplicato(parameter.getIdLottoDoc(), consumerWS)) {
				FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
				faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.DUPLICATED_ID.getCode()));
				faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.DUPLICATED_ID.getMsg());
				throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.DUPLICATED_ID.getMsg(), faultInfo);
			}

			// Operazioni devono essere valorizzate
			List<Operation> listaOperazioni = parameter.getOperations().getOperation();
			List<OperationType> listaTipiOperazione = new ArrayList<OperationType>();
			for (Operation operation : listaOperazioni) {
				// compongo lista operazioni per successiva validazione
				listaTipiOperazione.add(operation.getType());
			}
			// validazione operazioni
			if (!validaDipendenzeOperazioni(listaTipiOperazione)) {
				FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
				faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_OPERATIONS.getCode()));
				faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_OPERATIONS.getMsg());
				throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_OPERATIONS.getMsg(), faultInfo);
			}

			// procedo con l'inserimento in BMT_JOBS
			insertBMTJob(lSchemaBean, consumerWS, parameter, dimensione, reqOrigin, uriAttach);

			response.setEsito(ResultType.OK);

		} catch (OperazioniAurigaLottiDocFault e) {
			aLogger.error("OperazioniAurigaLottiDocFault", e);
			throw e;
		} catch (Exception e) {
			aLogger.error("Eccezione generica", e);
			// eccezione generica: setto l'eccezione generica e inserisco lo stack trace e il messaggio di errore
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			if (StringUtils.isNotBlank(e.getMessage())) {
				faultInfo.setErrMsg(e.getMessage());
			} else if (StringUtils.isNotBlank(e.getLocalizedMessage())) {
				faultInfo.setErrMsg(e.getLocalizedMessage());
			}
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo, e);
		} finally {
			aLogger.trace(">> operazioniAurigaLottiDoc() -- " + response);
			aLogger.trace("<< operazioniAurigaLottiDoc()");
		}

		return response;
	}

	/**
	 * Recupero del bean di configurazione del servizio
	 * 
	 * @return
	 * @throws OperazioniAurigaLottiDocFault
	 */

	private OperazioniAurigaLottiDocConfigBean config() throws OperazioniAurigaLottiDocFault {

		OperazioniAurigaLottiDocConfigBean lOperazioniAurigaLottiDocConfigBean = null;
		try {
			// recupero il token per effettuare la chiamata alla store e il relativo schema dal bean di configurazione
			lOperazioniAurigaLottiDocConfigBean = (OperazioniAurigaLottiDocConfigBean) SpringAppContext.getContext()
					.getBean("OperazioniAurigaLottiDocConfigBean");
		} catch (Exception e) {
			aLogger.fatal("Eccezione nel recupero del bean OperazioniAurigaLottiDocConfigBean");
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo);
		}
		if (lOperazioniAurigaLottiDocConfigBean == null) {
			aLogger.fatal("Bean OperazioniAurigaLottiDocConfigBean non configurato");
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo);
		}
		if (StringUtils.isBlank(lOperazioniAurigaLottiDocConfigBean.getDefaultSchema())) {
			aLogger.fatal("Schema non valorizzato");
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo);
		}
		if (lOperazioniAurigaLottiDocConfigBean.getFileManagerConfig() == null
				|| lOperazioniAurigaLottiDocConfigBean.getFileManagerConfig().getTipoServizio() == null) {
			aLogger.fatal("Configurazione file manager non valorizzate");
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.GENERIC_ERROR.getMsg(), faultInfo);
		}

		return lOperazioniAurigaLottiDocConfigBean;
	}

	private DmvConsumerWSBean getConsumerWS(SchemaBean pSchemaBean) throws OperazioniAurigaLottiDocFault {

		aLogger.trace("Verifica delle credenziali");

		// verifico username e password in input alla richiesta SOAP
		MessageContext lMessageContext = context.getMessageContext();
		String username = (String) lMessageContext.get("username");
		String password = (String) lMessageContext.get("password");

		if (StringUtils.isBlank(username)) {
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg(), faultInfo);
		}

		DaoDMVConsumerWS dao = new DaoDMVConsumerWS();
		try {
			return dao.getDmvConsumerWs(pSchemaBean, username, password);
		} catch (Exception e) {
			aLogger.error("Errore recupero consumer WS", e);
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.INVALID_CREDENTIAL.getMsg(), faultInfo);
		}

	}

	/**
	 * Verifico se {link idLotto} è duplicato verificando se esiste nel Set {link setIdLotti}
	 * 
	 * @param idLotto
	 * @param consumerWSBean:
	 *            informazioni con le credenziali del servizio
	 * @return
	 * @throws Exception
	 */

	private Boolean verificaIdLottoDuplicato(String idLotto, DmvConsumerWSBean consumerWSBean) throws Exception {

		aLogger.debug("Verifica duplicazione lotto in DMT_LOTTI_DOC_ELAB_MASSIVE" + idLotto);

		Session session = null;
		try {
			session = HibernateUtil.begin();

			Criteria criteria = session.createCriteria(DmtLottiDocElabMassive.class);
			criteria.add(Restrictions.eq("useridApplicazione", consumerWSBean.getUserid()));
			criteria.add(Restrictions.eq("idLotto", idLotto));

			Long numeroRisultati = (Long) criteria.setProjection(Projections.count("idJob")).uniqueResult();

			if (numeroRisultati != null && numeroRisultati > 0) {
				return true;
			}
		} finally {
			HibernateUtil.release(session);
		}

		return false;

	}

	/**
	 * Verifico se {link idLotto} esiste e ne restituisce la dimensione in byte
	 * 
	 * @param idLotto
	 * @return
	 */

	private Long calcolaDimensioneLotto(FileManager fileManager, String idLotto, String formato, String ciApplicazione) throws Exception {

		aLogger.debug("Verifica esistenza lotto " + idLotto + " avente formato " + formato + "  per applicazione " + ciApplicazione);

		DaoTParametri daoParametri = new DaoTParametri();
		String rootpath = null;

		if (StringUtils.isBlank(formato)) {
			throw new OperazioniAurigaLottiDocException("Formato non valorizzato");
		}

		try {
			TParameters parametroRoot = daoParametri.getParametro(PAR_KEY_ROOT_PATH_SERVIZIO_OPER_LOTTI_DOC);
			if (parametroRoot != null) {
				rootpath = parametroRoot.getStrValue();
			}
		} catch (Exception e) {
			throw new OperazioniAurigaLottiDocException("Eccezione nella lettura del parametro " + PAR_KEY_ROOT_PATH_SERVIZIO_OPER_LOTTI_DOC, e);
		}

		if (StringUtils.isBlank(rootpath)) {
			throw new OperazioniAurigaLottiDocException("Root path servizio non definita");
		}

		rootpath = rootpath.replace("$useridApplChiamante$", ciApplicazione);
		rootpath = EnvironmentVariableConfigManager.replaceEnvironmentVariable(rootpath);

		aLogger.trace("Rootpath lotto " + rootpath);

		String filename = idLotto;

		if (formato.equalsIgnoreCase(FormatoLottoEnum.ZIP.getCodice())) {
			filename = filename.concat(FilenameUtils.EXTENSION_SEPARATOR_STR).concat(FormatoLottoEnum.ZIP.getEstensione());
		} else if (formato.equalsIgnoreCase(FormatoLottoEnum.SEVENZIP.getCodice())) {
			filename = filename.concat(FilenameUtils.EXTENSION_SEPARATOR_STR).concat(FormatoLottoEnum.SEVENZIP.getEstensione());
		}

		FileBean fileBean = FileManagerUtil.createFromString(rootpath, filename);

		aLogger.trace("Filename lotto " + fileBean.getName());

		if (!fileManager.fileExists(fileBean)) {
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.NOT_FOUND.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.NOT_FOUND.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.NOT_FOUND.getMsg(), faultInfo);
		}

		File fileLotto = FileManagerUtil.getFileFromBean(fileBean);

		return FileUtils.sizeOf(fileLotto);
	}

	/**
	 * Metodo di inserimento (in transazione) del job in BMT_JOBS, parametro id lotto e request XML
	 * 
	 * @param schemaBean
	 * @param consumerWSBean
	 *            informazioni con le credenziali del servizio
	 * @param parameter
	 *            richiesta in input
	 * @param dimensione:
	 *            dimensione del file del lotto
	 *            
	 * @param reqOrigin:
	 * 			  tipo di operazione chiamata dal client
	 * 
	 * @param uriAttach:
	 *            eventuale URI dell'attach
	 * 
	 * @throws Exception
	 */

	private void insertBMTJob(SchemaBean schemaBean, DmvConsumerWSBean consumerWSBean, RequestActionsOnDocList parameter, Long dimensione, String reqOrigin, String uriAttach ) throws Exception {

		aLogger.debug("Inserisco job in BMT_JOBS");

		AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		aurigaLoginBean.setSchema(schemaBean.getSchema());
		aurigaLoginBean.setToken(consumerWSBean.getConnToken());

		Session session = HibernateUtil.begin();
		Transaction lTransaction = session.beginTransaction();

		try {

			DmpkBmanagerInsbatchBean lDmpkBmanagerInsbatchBean = new DmpkBmanagerInsbatchBean();
			if (consumerWSBean.getCiApplicazione().equals("TRIBUTI"))
			{
			lDmpkBmanagerInsbatchBean.setTipojobin(JOB_AURIGA_LOTTI_DOC+"_"+consumerWSBean.getCiApplicazione());
			}
			else
			{
			  lDmpkBmanagerInsbatchBean.setTipojobin(JOB_AURIGA_LOTTI_DOC);	
			}
			lDmpkBmanagerInsbatchBean.setUseridin(consumerWSBean.getUserid());
			lDmpkBmanagerInsbatchBean.setIddominioin(consumerWSBean.getIdSpAoo());
			lDmpkBmanagerInsbatchBean.setCodapplicazionein(consumerWSBean.getCiApplicazione());
			lDmpkBmanagerInsbatchBean.setCodistapplicazionein(consumerWSBean.getCiIstanzaApplicazione());
			lDmpkBmanagerInsbatchBean.setFormatofileoutputin(parameter.getFormatoLottoDoc());
			lDmpkBmanagerInsbatchBean.setNomefileoutputin(parameter.getIdLottoDoc());
			lDmpkBmanagerInsbatchBean.setParametriin(reqOrigin);
			lDmpkBmanagerInsbatchBean.setFlgautocommitin(0);

			final InsbatchImpl insbatchImpl = new InsbatchImpl();
			insbatchImpl.setBean(lDmpkBmanagerInsbatchBean);

			LoginService lLoginService = new LoginService();
			lLoginService.login(aurigaLoginBean);

			// effettuo chiamata alla store
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					insbatchImpl.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkBmanagerInsbatchBean> resultStore = new StoreResultBean<DmpkBmanagerInsbatchBean>();
			AnalyzeResult.analyze(lDmpkBmanagerInsbatchBean, resultStore);
			resultStore.setResultBean(lDmpkBmanagerInsbatchBean);

			// marco il token come valido
			final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
			DmpkLoginMarktokenusageBean lMarktokenusageBeanAllegato = new DmpkLoginMarktokenusageBean();
			lMarktokenusageBeanAllegato.setCodidconnectiontokenin(aurigaLoginBean.getToken());
			lMarktokenusageBeanAllegato.setFlgautocommitin(0);
			lMarktokenusage.setBean(lMarktokenusageBeanAllegato);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					lMarktokenusage.execute(paramConnection);
				}
			});
			if (resultStore.isInError()) {
				aLogger.debug(resultStore.getDefaultMessage());
				aLogger.debug(resultStore.getErrorContext());
				aLogger.debug(resultStore.getErrorCode());
				throw new StoreException(resultStore);
			}

			session.flush();

			final BigDecimal idJob = new BigDecimal(lDmpkBmanagerInsbatchBean.getIdjobout());
			final String idLotto = parameter.getIdLottoDoc();

			aLogger.debug("id Job: " + idJob);

			aLogger.debug("Inserisco job parameter idLotto " + idLotto + " in BMT_JOBS");

			List<JobParameterBean> jobParamList = new ArrayList<JobParameterBean>();
			JobParameterBean jobParameterBean = new JobParameterBean();
			jobParameterBean.setIdJob(idJob);
			jobParameterBean.setParameterId(BigDecimal.ZERO);
			jobParameterBean.setParameterDir(PAR_DIR_IN);
			jobParameterBean.setParameterType(PAR_TYPE_VARCHAR2);
			jobParameterBean.setParameterSubtype(JOB_PARAMETER_ID_LOTTO);
			jobParameterBean.setParameterValue(idLotto);

			jobParamList.add(jobParameterBean);

			aLogger.debug("Inserisco job parameter connection token in BMT_JOBS");

			jobParameterBean = new JobParameterBean();
			jobParameterBean.setIdJob(idJob);
			jobParameterBean.setParameterId(BigDecimal.ONE);
			jobParameterBean.setParameterDir(PAR_DIR_IN);
			jobParameterBean.setParameterType(PAR_TYPE_VARCHAR2);
			jobParameterBean.setParameterSubtype(JOB_PARAMETER_TOKEN);
			jobParameterBean.setParameterValue(consumerWSBean.getConnToken());

			jobParamList.add(jobParameterBean);

			aLogger.debug("Inserisco job parameter id user Auriga token in BMT_JOBS");

			jobParameterBean = new JobParameterBean();
			jobParameterBean.setIdJob(idJob);
			jobParameterBean.setParameterId(BigDecimal.valueOf(2L));
			jobParameterBean.setParameterDir(PAR_DIR_IN);
			jobParameterBean.setParameterType(PAR_TYPE_INTEGER);
			jobParameterBean.setParameterSubtype(JOB_PARAMETER_ID_USER);
			jobParameterBean.setParameterValue(consumerWSBean.getIdUser().toPlainString());

			jobParamList.add(jobParameterBean);

			aLogger.debug("Inserisco job parameter id user Auriga token in BMT_JOBS");

			jobParameterBean = new JobParameterBean();
			jobParameterBean.setIdJob(idJob);
			jobParameterBean.setParameterId(BigDecimal.valueOf(3L));
			jobParameterBean.setParameterDir(PAR_DIR_IN);
			jobParameterBean.setParameterType(PAR_TYPE_VARCHAR2);
			jobParameterBean.setParameterSubtype(JOB_PARAMETER_ID_UTENTE_MAIL);
			jobParameterBean.setParameterValue(consumerWSBean.getIdUtenteMail());

			jobParamList.add(jobParameterBean);

			aLogger.debug("Inserisco job parameter request in BMT_JOBS");

			jobParameterBean = new JobParameterBean();
			jobParameterBean.setIdJob(idJob);
			jobParameterBean.setParameterId(BigDecimal.valueOf(4L));
			jobParameterBean.setParameterDir(PAR_DIR_IN);
			jobParameterBean.setParameterType(PAR_TYPE_CLOB);
			jobParameterBean.setParameterSubtype(JOB_PARAMETER_REQUEST_XML);

			JAXBContext lJAXBContextMarshaller = JAXBContext.newInstance(RequestActionsOnDocList.class);
			Marshaller marshaller = lJAXBContextMarshaller.createMarshaller();

			StringWriter stringWriter = new StringWriter();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(parameter, stringWriter);

			jobParameterBean.setParameterValue(stringWriter.toString());

			jobParamList.add(jobParameterBean);
			
			aLogger.debug("Inserisco job uri attachment in BMT_JOBS");

			jobParameterBean = new JobParameterBean();
			jobParameterBean.setIdJob(idJob);
			jobParameterBean.setParameterId(BigDecimal.valueOf(5L));
			jobParameterBean.setParameterDir(PAR_DIR_IN);
			jobParameterBean.setParameterType(PAR_TYPE_VARCHAR2);
			jobParameterBean.setParameterSubtype(JOB_PARAMETER_URI);
			jobParameterBean.setParameterValue(uriAttach);

			jobParamList.add(jobParameterBean);

			DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();

			for (JobParameterBean jobParam : jobParamList) {
				daoBmtJobParameters.saveInSession(jobParam, session);
			}

			aLogger.debug("Inserisco lotto " + parameter.getIdLottoDoc() + " in DMT_LOTTI_DOC_ELAB_MASSIVE");

			Date now = GregorianCalendar.getInstance().getTime();

			DmtLottiDocElabMassiveBean lDmtLottiDocElabMassiveBean = new DmtLottiDocElabMassiveBean();
			lDmtLottiDocElabMassiveBean.setIdJob(idJob);
			lDmtLottiDocElabMassiveBean.setIdLotto(idLotto);
			lDmtLottiDocElabMassiveBean.setUseridApplicazione(consumerWSBean.getUserid());
			lDmtLottiDocElabMassiveBean.setIdSpAoo(consumerWSBean.getIdSpAoo());
			lDmtLottiDocElabMassiveBean.setTsMessaDisposizione(now);
			lDmtLottiDocElabMassiveBean.setTsLastUpd(now);
			lDmtLottiDocElabMassiveBean.setFormato(parameter.getFormatoLottoDoc());
			lDmtLottiDocElabMassiveBean.setTipologiaDoc(parameter.getTipoDoc());
			lDmtLottiDocElabMassiveBean.setStato(StatoLottoEnum.INSERITO.getStato());
			lDmtLottiDocElabMassiveBean.setXmlRequest(stringWriter.toString());
			lDmtLottiDocElabMassiveBean.setNroDocDichiarati(0);
			lDmtLottiDocElabMassiveBean.setNroFileDichiarati(0);
			lDmtLottiDocElabMassiveBean.setDimensione(BigDecimal.valueOf(dimensione));

			DaoDmtLottiDocElaborazioniMassive daoDmtLottiDocElaborazioniMassive = new DaoDmtLottiDocElaborazioniMassive();
			daoDmtLottiDocElaborazioniMassive.saveInSession(lDmtLottiDocElabMassiveBean, session);

			lTransaction.commit();

		} catch (ConstraintViolationException e) {
			FaultActionsOnDocList faultInfo = new FaultActionsOnDocList();
			faultInfo.setErrCode(BigInteger.valueOf(OperazioniAurigaLottiDocFaultCode.DUPLICATED_ID.getCode()));
			faultInfo.setErrMsg(OperazioniAurigaLottiDocFaultCode.DUPLICATED_ID.getMsg());
			throw new OperazioniAurigaLottiDocFault(OperazioniAurigaLottiDocFaultCode.DUPLICATED_ID.getMsg(), faultInfo);
		} catch (Exception e) {
			throw new OperazioniAurigaLottiDocException("Eccezione nell'inserimento in BMT_JOBS", e);
		} finally {
			HibernateUtil.release(session);
		}

	}

	/**
	 * Metodo che verifica la tipologia documentale in input per il dominio specificato
	 * 
	 * @return
	 * @throws Exception
	 */

	private Boolean verificaTipologiaDocumentale(SchemaBean schemaBean, BigDecimal idSpAOO, String docType) throws Exception {

		Boolean result = false;

		final Finddoctype_j service = new Finddoctype_j();
		DmpkUtilityFinddoctype_jBean jBean = new DmpkUtilityFinddoctype_jBean();
		jBean.setIdspaooin(idSpAOO);
		jBean.setProvcidoctypein(docType);

		service.setBean(jBean);
		StoreResultBean<DmpkUtilityFinddoctype_jBean> lResultStore = service.execute(schemaBean, jBean);

		AnalyzeResult.analyze(jBean, lResultStore);
		lResultStore.setResultBean(jBean);

		// si è verificato un errore
		if (lResultStore.isInError()) {
			aLogger.debug(lResultStore.getDefaultMessage());
			aLogger.debug(lResultStore.getErrorContext());
			aLogger.debug(lResultStore.getErrorCode());
		} else {
			// tipologia documentale valida se id tipologia è valorizzata
			result = lResultStore.getResultBean().getIddoctypeout() != null;
		}

		return result;

	}

	/**
	 * Verifica se le operazioni specificate nella request sono valide o meno
	 * 
	 * @param listaTipiOperazione
	 * @return
	 */

	private Boolean validaDipendenzeOperazioni(List<OperationType> listaTipiOperazione) throws Exception {

		if (listaTipiOperazione.isEmpty()) {
			aLogger.error("Operazioni non inserite");
			return false;
		}

		if (listaTipiOperazione.contains(OperationType.ARCHIVIAZIONE_EMAIL) && !listaTipiOperazione.contains(OperationType.INVIO_PEO)
				&& !listaTipiOperazione.contains(OperationType.INVIO_PEC)) {
			aLogger.error(
					"Dipendenze operazioni non soddisfatte: richiesta l'operazione di archiviazione mail senza che sia specificata un'operazione di invio mail");
			return false;
		}
		if (listaTipiOperazione.contains(OperationType.INVIO_PEO) && listaTipiOperazione.contains(OperationType.INVIO_PEC)) {
			aLogger.error("Dipendenze operazioni non soddisfatte: richieste entrambe le operazioni di invio mail");
			return false;
		}
		aLogger.debug("Dipendenze operazioni soddisfatte");
		return true;
	}

}
