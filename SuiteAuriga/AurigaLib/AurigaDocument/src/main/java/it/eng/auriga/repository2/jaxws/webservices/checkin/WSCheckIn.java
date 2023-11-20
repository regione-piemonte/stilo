/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreLockdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Lockdoc;
import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsCheckinBean;
import it.eng.auriga.database.store.dmpk_ws.store.Checkin;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.module.business.entity.WSTrace;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.auriga.repository2.jaxws.webservices.util.WSAttachBean;
import it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD;
import it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.document.function.GestioneDocumenti;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.xml.XmlUtilityDeserializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.exolab.castor.xml.Unmarshaller;
import org.w3c.dom.Document;

/**
 * @author Ottavio passalacqua
 */

@WebService(targetNamespace = "http://checkin.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.checkin.WSICheckIn", name = "WSCheckIn")
@MTOM(enabled = true, threshold = 0)
@HandlerChain(file = "../common/handler.xml")
public class WSCheckIn extends JAXWSAbstractAurigaService implements WSICheckIn {

	private final String K_SAVEPOINTNAME = "INIZIOWSCHECIN";

	static Logger aLogger = Logger.getLogger(WSCheckIn.class.getName());

	public WSCheckIn() {
		super();

	}

	/**
	 * <code>serviceImplementation</code> biz logik del webservice.
	 *
	 * @param user
	 *            a <code>String</code>
	 * @param token
	 *            a <code>String</code>
	 * @param codAppl
	 *            a <code>String</code>
	 * @param conn
	 *            a <code>Connection</code>
	 * @param xmlDomDoc
	 *            a <code>Document</code>
	 * @param xml
	 *            a <code>String</code>
	 * @param istanzaAppl
	 *            a <code>String</code>
	 * @return a <code>String</code>
	 * @exception Exception
	 */
	@WebMethod(exclude = true)
	public final String serviceImplementation(final String user, final String token, final String codiceApplicazione, final String istanzaAppl,
			final Connection conn, final Document xmlDomDoc, final String xml, final String schemaDb, final String idDominio, final String desDominio,
			final String tipoDominio,
		      final WSTrace wsTraceBean) throws Exception {

		String risposta = null;
		String outRispostaWS = null;

		String errMsg = null;
		String xmlIn = null;

		try {

			aLogger.info("Inizio WSCheckIn");

			// setto il savepoint
			DBHelperSavePoint.SetSavepoint(conn, K_SAVEPOINTNAME);

			// creo bean connessione
			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setToken(token);
			loginBean.setCodApplicazione(codiceApplicazione);
			loginBean.setIdApplicazione(istanzaAppl);
			loginBean.setSchema(schemaDb);

			SpecializzazioneBean lspecializzazioneBean = new SpecializzazioneBean();
			lspecializzazioneBean.setCodIdConnectionToken(token);
			if (idDominio != null && !idDominio.equalsIgnoreCase(""))
				lspecializzazioneBean.setIdDominio(new BigDecimal(idDominio));

			if (tipoDominio != null && !tipoDominio.equalsIgnoreCase(""))
				lspecializzazioneBean.setTipoDominio(new Integer(tipoDominio));

			loginBean.setSpecializzazioneBean(lspecializzazioneBean);

			/*************************************************************
			 * Chiamo il WS e il servizio di AurigaDocument
			 ************************************************************/
			WSCheckInBean outWS = new WSCheckInBean();

			String outServizio = null;

			WSAttachBean wsAttach = new WSAttachBean();

			String errori = "";

			try {

				// Chiamo il WS
				outWS = callWS(loginBean, xml);

				String idDoc = outWS.getIdDoc();
				String idUd = outWS.getIdUd();

				// Leggo gli attach e le info
				wsAttach = getAttachment_file_singolo(loginBean, idUd, idDoc);

				// Cpntrollo se il mimetype degli attach e' valido
				errori = checkMimeTypeAttach(wsAttach);

				// se ho trovato errori ritorno errore
				if (!errori.equals("")) {
					aLogger.error("Rilevati i seguenti errori: " + errori);
					throw new Exception(errori);
				}

				// Chiamo il servizio di AurigaDocument
				outServizio = eseguiServizio(loginBean, conn, outWS, wsAttach);
			} catch (Exception e) {
				if(e.getMessage()!=null)
		 			 errMsg = "Errore = " + e.getMessage();
		 		 else
		 			errMsg = "Errore imprevisto.";
			}

			if (errMsg == null) {
				xmlIn = outServizio;
			} else {
				xmlIn = errMsg;
			}

			/**************************************************************************
			 * Creo XML di risposta del servzio e lo metto in attach alla response
			 **************************************************************************/
			try {
				// Creo XML di risposta
				outRispostaWS = generaXMLRispostaWS(xmlIn);

				// Creo la lista di attach
				List<InputStream> lListInputStreams = new ArrayList<InputStream>();

				// Converto l'XML
				ByteArrayInputStream inputStreamXml = new ByteArrayInputStream(outRispostaWS.getBytes());

				// Aggiungo l'XML
				lListInputStreams.add(inputStreamXml);

				// Salvo gli ATTACH alla response
				attachListInputStream(lListInputStreams);
			} catch (Exception e) {
				if(e.getMessage()!=null)
		 			 errMsg = "Errore = " + e.getMessage();
		 		 else
		 			errMsg = "Errore imprevisto.";
			}

			/*************************************************************
			 * Restituisco XML di risposta del WS
			 ************************************************************/
			if (errMsg == null) {
				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.SUCCESSO, JAXWSAbstractAurigaService.SUCCESSO, "Tutto OK", "", "");
			} else {
				risposta = generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO, errMsg, "", "");
			}

			aLogger.info("Fine WSCheckIn");

			return risposta;
		}

		catch (Exception excptn) {
			aLogger.error("WSCheckIn: " + excptn.getMessage(), excptn);
			return generaXMLRisposta(JAXWSAbstractAurigaService.FALLIMENTO, JAXWSAbstractAurigaService.ERR_ERRORE_APPLICATIVO,
					JAXWSAbstractAurigaService.ERROR_ERRORE_APPLICATIVO, "", "");
			// throw excptn;
		} finally {
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, K_SAVEPOINTNAME);
			} catch (Exception ee) {
			}
			aLogger.info("Fine WSCheckIn serviceImplementation");
		}

	}

	private String eseguiServizio(AurigaLoginBean loginBean, Connection conn, WSCheckInBean bean, WSAttachBean wsAttachEinfo) throws Exception {

		String ret = null;
		String idDoc = null;
		String idUd = null;
		String nroVersioneEstratta = null;
		String flgVerificaFirmaFile = null;
		String attributiVerXML = null;
		String docStoreFallita = "";

		aLogger.debug("Eseguo il servizio di AurigaDocument.");

		try {
			// Leggo input
			idDoc = bean.getIdDoc();
			idUd = bean.getIdUd();
			attributiVerXML = bean.getAttributiVerXML();
			flgVerificaFirmaFile = bean.getFlgVerificaFirmaFile();
			nroVersioneEstratta = bean.getNroVersioneEstratta();

			Output_UD listaFileElettroniciNonSalvatiOut = null;
			String xmlOutput_UD = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><Output_UD><IdDOC>" + bean.getIdDoc() + "</IdDOC>" + "<IdUD>"
					+ bean.getIdUd() + "</IdUD>" + "</Output_UD>";
			StringReader str = new StringReader(xmlOutput_UD);
			listaFileElettroniciNonSalvatiOut = (Output_UD) Unmarshaller.unmarshal(Output_UD.class, str);
			listaFileElettroniciNonSalvatiOut.setIdDOC(Integer.valueOf(bean.getIdDoc()));

			// Estraggo le info degli ALLEGATI dall'XML
			AttributiVerXMLBean lAttributiVerXMLBean = null;
			if (attributiVerXML != null) {
				XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
				lAttributiVerXMLBean = lXmlUtility.unbindXml(attributiVerXML, AttributiVerXMLBean.class);
			}

			// Processo gli ALLEGATI
			if (lAttributiVerXMLBean != null) {
				String errori = "";

				// Prendo gli attach
				DataHandler[] attachments = wsAttachEinfo.getAttachments();

				// Prendo la lista delle info degli attach
				List<RebuildedFile> listRebuildedFile = wsAttachEinfo.getListRebuildedFile();

				if (attachments == null || attachments.length == 0) {
					throw new Exception("Il file elettronico non e' presente");
				}

				RebuildedFile rebuildedFile = new RebuildedFile();
				rebuildedFile.setFile(listRebuildedFile.get(0).getFile());
				rebuildedFile.setIdDocumento(listRebuildedFile.get(0).getIdDocumento());
				rebuildedFile.setInfo(listRebuildedFile.get(0).getInfo());
				rebuildedFile.setPosizione(listRebuildedFile.get(0).getPosizione());
				rebuildedFile.setAnnullaLastVer(listRebuildedFile.get(0).getAnnullaLastVer());
				rebuildedFile.setUpdateVersion(listRebuildedFile.get(0).getUpdateVersion());

				/*************************************************************
				 * Salvo i file allegati
				 *************************************************************/
				errori = salvaAllegato(loginBean, conn, nroVersioneEstratta, rebuildedFile, attachments, lAttributiVerXMLBean,
						listaFileElettroniciNonSalvatiOut);

				// Se il salvataggio e' fallito restituisco l'elenco dei file non salvati
				if (errori != null && !errori.trim().equalsIgnoreCase("")) {
					throw new Exception(errori);
				}
			}

			// Restituisco l'iddoc del documento
			ret = idDoc;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return ret;
	}

	private WSCheckInBean callWS(AurigaLoginBean loginBean, String xmlIn) throws Exception {

		aLogger.debug("Eseguo il WS DmpkWSCheckIn.");

		String idDoc = null;
		String idUd = null;
		String nroVersioneEstratta = null;
		String attributiVerXML = null;
		String flgVerificaFirmaFile = null;

		try {
			// Inizializzo l'INPUT
			DmpkWsCheckinBean input = new DmpkWsCheckinBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setXmlin(xmlIn);

			// Eseguo il servizio
			Checkin service = new Checkin();
			StoreResultBean<DmpkWsCheckinBean> output = service.execute(loginBean, input);

			if (output.isInError()) {
				throw new Exception(output.getDefaultMessage());
			}

			// restituisco l'ID DOC
			if (output.getResultBean().getIddocout() != null) {
				idDoc = output.getResultBean().getIddocout().toString();
			}
			if (idDoc == null || idDoc.equalsIgnoreCase("")) {
				throw new Exception("La store procedure CheckIn ha ritornato id doc nullo");
			}

			// restituisco l'XML ATTRIBUTI
			if (output.getResultBean().getAttributiverxmlout() != null) {
				attributiVerXML = output.getResultBean().getAttributiverxmlout();
			}

			// restituisco il NRO VERSIONE
			if (output.getResultBean().getNroversioneestrattaout() != null) {
				nroVersioneEstratta = output.getResultBean().getNroversioneestrattaout().toString();
			}

			// restituisco il FLG VERIFICA FIRM FILE
			if (output.getResultBean().getFlgverificafirmafileout() != null) {
				flgVerificaFirmaFile = output.getResultBean().getNroversioneestrattaout().toString();
			}

			// Ricavo IDUD legato all'IDDOC
			try {
				GestioneDocumenti servizio = new GestioneDocumenti();
				BigDecimal idUdDecimal = servizio.leggiIdUDOfDocWS(loginBean, idDoc);
				if (idUdDecimal != null)
					idUd = idUdDecimal.toString();
			} catch (Exception ve) {
				String mess = "------> Fallita la ricerca del idUD per idDoc= " + idDoc;
				aLogger.debug(mess + " - " + ve.getMessage(), ve);
				throw new Exception(mess);
			}

			// popolo il bean di out
			WSCheckInBean result = new WSCheckInBean();
			result.setAttributiVerXML(attributiVerXML);
			result.setIdDoc(idDoc);
			result.setNroVersioneEstratta(nroVersioneEstratta);
			result.setFlgVerificaFirmaFile(flgVerificaFirmaFile);
			result.setIdUd(idUd);

			return result;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Genera il file XML di risposta del servizio Questo file viene passato come allegato in caso di successo.
	 * 
	 * @param xmlIn
	 *            Contiene xml restutuito dal servizio
	 * @return String stringa XML secondo il formato per il ritorno dell'idud
	 */
	private String generaXMLRispostaWS(String xmlIn) throws Exception {

		StringBuffer xml = new StringBuffer();
		String xmlInEsc = null;

		try {
			// ...se il token non e' null
			if (xmlIn != null) {
				// effettuo l'escape di tutti i caratteri
				xmlInEsc = eng.util.XMLUtil.xmlEscape(xmlIn);
			}
			aLogger.debug("generaXMLToken: token = " + xmlIn);
			aLogger.debug("generaXMLToken: tokenEsc = " + xmlInEsc);
			xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
			xml.append("<idDoc>" + xmlInEsc + "</idDoc>\n");
			aLogger.debug(xml.toString());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return xml.toString();
	}

	// Salvo gli allegati
	private String salvaAllegato(AurigaLoginBean loginBean, Connection conn, String nroVersioneEstratta, RebuildedFile rebuildedFileIn,
			DataHandler[] attachments, AttributiVerXMLBean lAttributiVerXMLBean, Output_UD lsXmlOut) throws Exception {
		String returnMess = "";
		try {
			AllegatiBean lAllegatiBean = new AllegatiBean();

			// booleano per sapere se e' gia' fallita una step
			boolean unaStepGiaFallita = false;

			// Array per le Versioni elettroniche non caricate
			List versioniNonCaricate = new ArrayList();
			String idDoc = "";
			String flgTipoFile = "";

			String nome = "";
			String flgPubblicata = "";
			String flgDaScansione = "";
			String note = "";
			Long idDocInt = new Long(0);
			String idUd = null;
			Long idUdInt = new Long(0);
			BigDecimal idDocFileDecimal = null;

			String lastVersion = "";

			// Nome del file da caricare (quello con cui mostrarlo e dalla cui estensione si ricava il formato)
			nome = lAttributiVerXMLBean.getDisplayFilename();

			// Indica che la versione e' pubblicata, se 0 no, se 1 si
			flgPubblicata = lAttributiVerXMLBean.getFlgPubblicata().getDbValue();

			// Indica che la versione deriva da scansione, se 0 no, se 1 si
			flgDaScansione = lAttributiVerXMLBean.getFlgDaScansione().getDbValue();

			// Note della versione
			note = lAttributiVerXMLBean.getNote();

			idDocInt = lsXmlOut.getIdDOC();
			idUdInt = lsXmlOut.getIdUD();
			idDoc = idDocInt.toString();
			idUd = idUdInt.toString();
			idDocFileDecimal = new BigDecimal(idDoc);

			if (rebuildedFileIn.getInfo().getTipo() == TipoFile.PRIMARIO) {
				flgTipoFile = "P";
			}
			if (rebuildedFileIn.getInfo().getTipo() == TipoFile.ALLEGATO) {
				flgTipoFile = "A";
			}

			lAllegatiBean = popoloAllegatiBean(rebuildedFileIn, nome, idDoc, note);

			aLogger.debug("nroVersioneEstratta = " + nroVersioneEstratta);

			if (!nroVersioneEstratta.trim().equals("") && !nroVersioneEstratta.trim().equals("0")) {

				/**************************************************
				 * SV = sostituzione di versione
				 **************************************************/
				// provo a fare il lock del documento
				try {
					// **********************************************************
					// Eseguo il DMPK_CORE.LockDoc
					// **********************************************************
					aLogger.debug("Eseguo il servizio  DMPK_CORE.LockDoc");

					BigDecimal idDocIn = idDocFileDecimal;

					DmpkCoreLockdocBean lLockdocBean = new DmpkCoreLockdocBean();
					lLockdocBean.setCodidconnectiontokenin(loginBean.getToken());
					lLockdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
					lLockdocBean.setIddocin(idDocIn);
					lLockdocBean.setFlgtipolockin("I"); // Imposto il lock I = implicito

					Lockdoc lLockdoc = new Lockdoc();

					StoreResultBean<DmpkCoreLockdocBean> lStoreResultBean = lLockdoc.execute(loginBean, lLockdocBean);
					if (lStoreResultBean.isInError()) {
						aLogger.debug(lStoreResultBean.getDefaultMessage());
						aLogger.debug(lStoreResultBean.getErrorContext());
						aLogger.debug(lStoreResultBean.getErrorCode());
						if (!((lStoreResultBean.getErrorCode() == 1008))) {
							aLogger.debug("Errore in GENERIC LOCK.\nErrcode:" + lStoreResultBean.getErrorCode() + "\nErrMsg: "
									+ lStoreResultBean.getDefaultMessage());
							throw new Exception(lStoreResultBean.getDefaultMessage());
						} else {
							// l'errore e' dovuto ad un tentativo di lock su un
							// documento gia' bloccato dallo stesso utente o da un altro utente.
							// Prendo il codice dell'errore
							lastVersion = (lStoreResultBean.getErrorCode()) + "";
						}
					} else {
						if (lStoreResultBean.getResultBean().getNroprogrlastverout() != null)
							lastVersion = lStoreResultBean.getResultBean().getNroprogrlastverout().toString();
					}
				} catch (Exception ve) {
					String mess = "------> Fallita la lock per il doc " + idDoc;
					aLogger.debug(mess + " - " + ve.getMessage(), ve);
					throw new Exception(mess + " - " + ve.getMessage());
				}

				Integer oldVersioneDoc = new Integer(nroVersioneEstratta);
				aLogger.debug("checkIn (idDoc = " + idDoc + ", oldVersioneDoc = " + oldVersioneDoc + ")");

				// verifica che l'eventuale versione passata sia uguale a quella locked
				// in caso contrario lancia un'eccezione
				if (oldVersioneDoc != null) {
					if (!lastVersion.equals(oldVersioneDoc.toString()))// errore
					{
						// annullo il lock applicativo
						String mess = "Errore in Generic CheckIn: ultima versione diversa da quella indicata" + ": IdDoc = " + idDoc.toString()
								+ " - OldVer = " + oldVersioneDoc.toString();
						throw new Exception(mess);
					}
				}

				// da completare .......

			} else {
				/**************************************************
				 * AV = step version
				 **************************************************/
				try {
					GestioneDocumenti servizio = new GestioneDocumenti();
					CreaModDocumentoOutBean servizioOut = new CreaModDocumentoOutBean();

					servizioOut = servizio.addDocsWS(loginBean, flgTipoFile, idUd, idDocFileDecimal, lAllegatiBean, null);

					// Se il servizio e' andato in errore restituisco il messaggio di errore
					if (StringUtils.isNotBlank(servizioOut.getDefaultMessage())) {
						returnMess += (unaStepGiaFallita) ? ", " + servizioOut.getDefaultMessage() : " " + servizioOut.getDefaultMessage();
					}

					// popolo un oggetto VersioneElettronicaNonCaricata per tener conto del fallimento
					VersioneElettronicaNonCaricata venc = new VersioneElettronicaNonCaricata();

					// Se la gestione dei file e' andata in errore restituisco il messaggio di errore
					StringBuffer lStringBuffer = new StringBuffer();

					int key = 0;
					if (servizioOut.getFileInErrors() != null && servizioOut.getFileInErrors().size() > 0) {
						for (String lStrFileInError : servizioOut.getFileInErrors().values()) {

							lStringBuffer.append("; " + lStrFileInError);

							// setto l'indice
							String sIndex = servizioOut.getFileInErrors().get(key);
							if (sIndex != null && !sIndex.equalsIgnoreCase(""))
								venc.setNroAttachmentAssociato(new Integer(sIndex));

							// setto il nome file
							venc.setNomeFile(lStrFileInError);

							// aggiungo alla lista dei fallimenti
							versioniNonCaricate.add(venc);

							key++;
						}
					}
					if (lStringBuffer != null && lStringBuffer.length() > 0 && !lStringBuffer.toString().equalsIgnoreCase(""))
						returnMess += (unaStepGiaFallita) ? ", " + lStringBuffer : " " + lStringBuffer;
				} catch (Exception ve) {
					String mess = "------> Fallita la stepVersion per il doc " + idDoc;
					returnMess += (unaStepGiaFallita) ? ", " + mess : " " + mess;
					unaStepGiaFallita = true;
					aLogger.debug(mess + " - " + ve.getMessage(), ve);
				}
			}

			/*************************************************************
			 * Gestisco gli eventuali messaggi di warning
			 ************************************************************/

			// preparo la stringa per xml di risposta
			if (!returnMess.equals("")) {
				returnMess = "Fallito il caricamento per i file:" + returnMess;
			}

			// se vi sono versioni non caricate
			if (versioniNonCaricate.size() > 0) {
				// trasferisco la lista in un array per effettuare il set nell'XMLOUT
				VersioneElettronicaNonCaricata vencArray[] = new VersioneElettronicaNonCaricata[versioniNonCaricate.size()];
				// scorro la lista e trasferisco
				Iterator iter = versioniNonCaricate.iterator();
				// contatore per popolare l'array
				int i = 0;
				// popolo l'array
				while (iter.hasNext()) {
					vencArray[i] = (VersioneElettronicaNonCaricata) iter.next();
					aLogger.debug("NON inserita versione #" + i);
					i++;
				}
				// effettuo il set sull'output_UD
				lsXmlOut.setVersioneElettronicaNonCaricata(vencArray);
			}
		} catch (Throwable ee) {
			returnMess += "Errore nella funzione nella salvaAllegati() - " + ee.getMessage() + "\n";
			aLogger.error(returnMess);
			throw new Exception(returnMess);
		}

		return returnMess;
	}
}
