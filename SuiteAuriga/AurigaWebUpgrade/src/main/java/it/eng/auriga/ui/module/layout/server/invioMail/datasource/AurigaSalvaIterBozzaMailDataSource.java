/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.ItemLavorazioneMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SalvaInBozzaMailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.AurigaLoadDettaglioEmailDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.CorpoMailDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailAllegatoBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailBean;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.aurigamailbusiness.bean.AllegatiInvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.DestinatariInvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.DraftAndWorkItemsBean;
import it.eng.aurigamailbusiness.bean.DraftAndWorkItemsSavedBean;
import it.eng.aurigamailbusiness.bean.IdMailInoltrataMailXmlBean;
import it.eng.aurigamailbusiness.bean.InvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.ItemLavorazioneMailXmlBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.RispostaInoltro;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.client.MailSenderService;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@Datasource(id = "AurigaSalvaIterBozzaMailDataSource")
public class AurigaSalvaIterBozzaMailDataSource extends AbstractServiceDataSource<SalvaInBozzaMailBean, SalvaInBozzaMailBean> {

	private static Logger mLogger = Logger.getLogger(AurigaSalvaIterBozzaMailDataSource.class);
	
	private String updateBozza;

	@Override
	public SalvaInBozzaMailBean call(SalvaInBozzaMailBean pInBean) throws Exception {

		return new SalvaInBozzaMailBean();
	}

	public SalvaInBozzaMailBean saveBozza(SalvaInBozzaMailBean pInBean) throws Exception {

		mLogger.debug("start saveDraftAndWorkItems");

		pInBean = checkBodyHtml(pInBean);

		String tipoRel = getExtraparams().get("tipoRel") != null ? getExtraparams().get("tipoRel") : "";
		String operazione = getExtraparams().get("operazione") != null ? getExtraparams().get("operazione") : "N";
		String tipoRelCopia = pInBean.getTipoRelCopia() != null ? pInBean.getTipoRelCopia() : "";
		String invio_finale = getExtraparams().get("invio_finale") != null ? getExtraparams().get("invio_finale") : "";
		setUpdateBozza(getExtraparams().get("updateBozza") != null ? getExtraparams().get("updateBozza") : "");

		DraftAndWorkItemsBean input = getDatiBozza(pInBean, tipoRel, operazione, tipoRelCopia, updateBozza);
		/**
		 * Se stò creando una nuova bozza da ( NIC - Risposta - Inoltro ) non devo passare l'idMailPrincipale della mail di provenienza.
		 * In update invece devo passare l'idEmail della bozza
		 */
		if(isUpdateBozza()){
			input.setIdemailio(pInBean.getIdEmailPrincipale());
		} else {
			input.setIdemailio(null);
		}

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(loginBean.getSchema());
		lMailLoginBean.setToken(loginBean.getToken());
		lMailLoginBean.setUserId(loginBean.getIdUserLavoro());
		lMailLoginBean.setIdUtente(loginBean.getIdUtente());

		MailSenderService senderService = new MailSenderService();
		ResultBean<DraftAndWorkItemsSavedBean> result = senderService.savedraftandworkitems(getLocale(), input,
				invio_finale != null && "invio_finale".equals(invio_finale) ? DraftAndWorkItemsBean.MODE_SAVE_DRAFT_PRE_SEND
						: DraftAndWorkItemsBean.MODE_SAVE_DRAFT,
				lMailLoginBean);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		pInBean.setIdEmail(result.getResultBean().getIdEmail());
		pInBean.setIdEmailPrincipale(result.getResultBean().getIdEmail());

		return pInBean;
	}

	public SalvaInBozzaMailBean saveItemLavorazione(SalvaInBozzaMailBean bean) throws Exception {

		mLogger.debug("start saveItemLavorazione");

		DraftAndWorkItemsBean input = getItemLavorazione(bean);
		input.setIdemailio(bean.getIdEmailPrincipale());

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(loginBean.getSchema());
		lMailLoginBean.setToken(loginBean.getToken());
		lMailLoginBean.setUserId(loginBean.getIdUserLavoro());

		MailSenderService senderService = new MailSenderService();
		ResultBean<DraftAndWorkItemsSavedBean> result = senderService.savedraftandworkitems(getLocale(), input, DraftAndWorkItemsBean.MODE_SAVE_ONLY_WORK_ITEMS,
				lMailLoginBean);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result.getDefaultMessage());
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		bean.setIdEmailPrincipale(result.getResultBean().getIdEmail());

		return bean;
	}

	protected DraftAndWorkItemsBean getDatiBozza(SalvaInBozzaMailBean bean, String tipoRel, String operazione, String tipoRelCopia, String updateBozza) throws Exception {

		DraftAndWorkItemsBean input = new DraftAndWorkItemsBean();
		InvioMailXmlBean xmlItem = new InvioMailXmlBean();

		// Salvo delle eventuali mail inoltrate massivamente
		// Queste sono presenti quando salvo la bozza di un inoltro
		if (bean.getListaIdEmailInoltrate() != null && bean.getListaIdEmailInoltrate().size() > 0) {
			List<IdMailInoltrataMailXmlBean> listaIdEmailInoltrate = new ArrayList<IdMailInoltrataMailXmlBean>();
			for (InvioMailBean item : bean.getListaIdEmailInoltrate()) {
				IdMailInoltrataMailXmlBean lIdMailInoltrataMailXmlBean = new IdMailInoltrataMailXmlBean();
				lIdMailInoltrataMailXmlBean.setIdMailInoltrata(item.getIdEmail());

				listaIdEmailInoltrate.add(lIdMailInoltrataMailXmlBean);
			}
			xmlItem.setListaIdEmailInoltrate(listaIdEmailInoltrate);
		}

		if (StringUtils.isNotBlank(bean.getIdEmailPrincipale()) && isUpdateBozza()) {
			xmlItem.setDimensione(bean.getDimensioneMail());
			xmlItem.setUri(bean.getUriMail());
		}

		if ("I".equals(operazione)) {
			// Sto facendo la bozza di un inoltro
			xmlItem.setEmailPredTipoRel(RispostaInoltro.INOLTRO.getValue());
			xmlItem.setEmailPredIdEmail(bean.getIdEmail());
		} else if ("R".equals(operazione)) {
			// Sto facendo la bozza di una risposta
			xmlItem.setEmailPredTipoRel(RispostaInoltro.RISPOSTA.getValue());
			xmlItem.setEmailPredIdEmail(bean.getIdEmail());
		} else if ("N".equals(operazione)) {
			// Sto facendo la bozza di un nuovo messaggio o di invio come copia
			// In questo ultimo caso a me interessa la relazione che c'è tra il messaggio da cui faccio la copia e il suo precedente.
			// Questa informazione la trovo in tipoRelCopia.
			// Se è la bozza di un nuovo messaggio normale (non come copia), allora idEmail e listaIdEmailPredecessore sono null
			if (tipoRelCopia.equals("risposta")) {
				xmlItem.setEmailPredTipoRel(RispostaInoltro.RISPOSTA.getValue());
			} else if (tipoRelCopia.equals("inoltro") || tipoRel.equals("inoltroAllegaMailOrig")) {
				xmlItem.setEmailPredTipoRel(RispostaInoltro.INOLTRO.getValue());
			} else if (tipoRelCopia.equals("notifica_eccezione")) {
				xmlItem.setEmailPredTipoRel(RispostaInoltro.NOTIFICA_ECCEZIONE.getValue());
			} else if (tipoRelCopia.equals("notifica_conferma")) {
				xmlItem.setEmailPredTipoRel(RispostaInoltro.NOTIFICA_CONFERMA.getValue());
			}
			// Salvando la bozza di un nuovo invio come copia, l'id del predecessore lo posso trovare nella lista
			// listaIdMailPredecessore anche se è singolo
			// Se invece ho una lista di predecessori (possibile solamente se il messaggio da cui ho fatto la copia era un inoltro),
			// i predecessori li trovo nella lista listaIdEmailPredecessore. La lista dei predecessori la salvo in listaIdEmailInoltrate
			String idPredecessoreSingolo = bean.getIdEmail();
			if ((bean.getListaIdEmailPredecessore() != null) && (bean.getListaIdEmailPredecessore().size() == 1)) {
				// Ho un solo predecessore nella lista
				idPredecessoreSingolo = bean.getListaIdEmailPredecessore().get(0).getIdMailInoltrata();
			} else if ((bean.getListaIdEmailPredecessore() != null) && (bean.getListaIdEmailPredecessore().size() > 1)) {
				List<IdMailInoltrataMailXmlBean> listaIdEmailInoltrate = new ArrayList<IdMailInoltrataMailXmlBean>();
				for (IdMailInoltrataMailXmlBean item : bean.getListaIdEmailPredecessore()) {
					IdMailInoltrataMailXmlBean lIdMailInoltrataMailXmlBean = new IdMailInoltrataMailXmlBean();
					lIdMailInoltrataMailXmlBean.setIdMailInoltrata(item.getIdMailInoltrata());
					listaIdEmailInoltrate.add(lIdMailInoltrataMailXmlBean);
				}
				xmlItem.setListaIdEmailInoltrate(listaIdEmailInoltrate);
				// Non uso più il predecessore singolo
				idPredecessoreSingolo = null;
			}
			xmlItem.setEmailPredIdEmail(idPredecessoreSingolo);
		}

		xmlItem.setAccountMittente(
				bean.getAliasAccountMittente() != null && !"".equals(bean.getAliasAccountMittente()) ? bean.getAliasAccountMittente() : bean.getMittente());
		xmlItem.setSubject(bean.getOggetto());
		xmlItem.setFlgEmailFirmata(0);

		if (bean.getTextHtml().equals("text")) {
			xmlItem.setBody(bean.getBodyText());
		} else {
			xmlItem.setBody("<html>" + bean.getBodyHtml() + "</html>");
		}

		List<DestinatariInvioMailXmlBean> xmlDestinatariInvioMail = new ArrayList<DestinatariInvioMailXmlBean>();
		
		List<String> lDestinatariPrincipaliList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(bean.getDestinatari())) {
			String[] lStringDestinatari = IndirizziEmailSplitter.split(bean.getDestinatari());
			lDestinatariPrincipaliList = Arrays.asList(lStringDestinatari);
			for (String destinatarioItem : lDestinatariPrincipaliList) {
				DestinatariInvioMailXmlBean lDestinatariInvioMailXmlBean = new DestinatariInvioMailXmlBean();
				lDestinatariInvioMailXmlBean.setIndirizzo(destinatarioItem.trim());
				lDestinatariInvioMailXmlBean.setTipo("P");
				xmlDestinatariInvioMail.add(lDestinatariInvioMailXmlBean);
			}
		}

		List<String> lDestinatariCCList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(bean.getDestinatariCC())) {
			String[] lStringDestinatariCC = IndirizziEmailSplitter.split(bean.getDestinatariCC());
			lDestinatariCCList = Arrays.asList(lStringDestinatariCC);
			for (String destinatarioItem : lDestinatariCCList) {
				DestinatariInvioMailXmlBean lDestinatariInvioMailXmlBean = new DestinatariInvioMailXmlBean();
				lDestinatariInvioMailXmlBean.setIndirizzo(destinatarioItem.trim());
				lDestinatariInvioMailXmlBean.setTipo("CC");
				xmlDestinatariInvioMail.add(lDestinatariInvioMailXmlBean);
			}
		}
		
		if(bean.getCasellaIsPec() == null){
			List<String> lDestinatariCCNList = new ArrayList<String>();
			if (StringUtils.isNotEmpty(bean.getDestinatariCCN())) {
				String[] lStringDestinatariCCN = IndirizziEmailSplitter.split(bean.getDestinatariCCN());
				lDestinatariCCNList = Arrays.asList(lStringDestinatariCCN);
				for (String destinatarioItem : lDestinatariCCNList) {
					DestinatariInvioMailXmlBean lDestinatariInvioMailXmlBean = new DestinatariInvioMailXmlBean();
					lDestinatariInvioMailXmlBean.setIndirizzo(destinatarioItem.trim());
					lDestinatariInvioMailXmlBean.setTipo("CCN");
					xmlDestinatariInvioMail.add(lDestinatariInvioMailXmlBean);
				}
			}
		}else if(bean.getCasellaIsPec() != null && !"true".equals(bean.getCasellaIsPec())){
			List<String> lDestinatariCCNList = new ArrayList<String>();
			if (StringUtils.isNotEmpty(bean.getDestinatariCCN())) {
				String[] lStringDestinatariCCN = IndirizziEmailSplitter.split(bean.getDestinatariCCN());
				lDestinatariCCNList = Arrays.asList(lStringDestinatariCCN);
				for (String destinatarioItem : lDestinatariCCNList) {
					DestinatariInvioMailXmlBean lDestinatariInvioMailXmlBean = new DestinatariInvioMailXmlBean();
					lDestinatariInvioMailXmlBean.setIndirizzo(destinatarioItem.trim());
					lDestinatariInvioMailXmlBean.setTipo("CCN");
					xmlDestinatariInvioMail.add(lDestinatariInvioMailXmlBean);
				}
			}
		}

		xmlItem.setListaDestinatari(xmlDestinatariInvioMail);

		List<AllegatiInvioMailXmlBean> xmlAllegatiInvioMail = new ArrayList<AllegatiInvioMailXmlBean>();
		List<SenderAttachmentsBean> lSenderAttachmentsBean = new ArrayList<SenderAttachmentsBean>();
		if (bean.getAttach() != null && !bean.getAttach().isEmpty()) {
			/**
			 * Recupero allegati di una bozza già esistente
			 */
			if (bean.getIdEmailPrincipale() != null && !"".equalsIgnoreCase(bean.getIdEmailPrincipale())) {

				DettaglioEmailBean lDettaglioEmailBean = getAttachInDettaglioEmailBean(bean.getIdEmailPrincipale());
				for (AttachmentBean lAttachmentBean : bean.getAttach()) {
					AllegatiInvioMailXmlBean lAllegatiInvioMailXmlBean = new AllegatiInvioMailXmlBean();
					String uri = lAttachmentBean.getUriAttach();
					if (uri.equals("_noUri")) {
						uri = recuperaAttachments(lDettaglioEmailBean, lAttachmentBean.getFileNameAttach());
					}

					InfoFileUtility lFileUtility = new InfoFileUtility();
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
					
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					if(lAttachmentBean.getInfoFileAttach()!=null) {
						lMimeTypeFirmaBean = lAttachmentBean.getInfoFileAttach();
					}else {
						lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
								lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);
					}

					lAllegatiInvioMailXmlBean.setNomeFile(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName())
							? lMimeTypeFirmaBean.getCorrectFileName() : lAttachmentBean.getFileNameAttach());
					lAllegatiInvioMailXmlBean.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? 1 : 0);
					lAllegatiInvioMailXmlBean.setMimetype(lMimeTypeFirmaBean.getMimetype());

					lAllegatiInvioMailXmlBean.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));

					lAllegatiInvioMailXmlBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
					lAllegatiInvioMailXmlBean.setEncoding(lMimeTypeFirmaBean.getEncoding());
					lAllegatiInvioMailXmlBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
					xmlAllegatiInvioMail.add(lAllegatiInvioMailXmlBean);

					SenderAttachmentsBean atta = new SenderAttachmentsBean();
					atta.setFile(StorageImplementation.getStorage().extractFile(uri));
					atta.setFilename(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName()) ? lMimeTypeFirmaBean.getCorrectFileName()
							: lAttachmentBean.getFileNameAttach());
					atta.setFirmato(lMimeTypeFirmaBean.isFirmato());
					atta.setMimetype(lMimeTypeFirmaBean.getMimetype());
					atta.setOriginalName(lAttachmentBean.getFileNameAttach());
					atta.setPdfConCommenti(lMimeTypeFirmaBean.isPdfConCommenti());
					atta.setPdfEditabile(lMimeTypeFirmaBean.isPdfEditabile());
					atta.setFirmaValida(lMimeTypeFirmaBean.isFirmaValida());
					lSenderAttachmentsBean.add(atta);
				}
			} else {
				/**
				 * Recupero allegati di una nuova bozza
				 */
				for (AttachmentBean lAttachmentBean : bean.getAttach()) {
					AllegatiInvioMailXmlBean lAllegatiInvioMailXmlBean = new AllegatiInvioMailXmlBean();
					String uri = lAttachmentBean.getUriAttach();

					InfoFileUtility lFileUtility = new InfoFileUtility();
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
					
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					if(lAttachmentBean.getInfoFileAttach()!=null) {
						lMimeTypeFirmaBean = lAttachmentBean.getInfoFileAttach();
					}else {
						lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
								lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);
					}
					
					lAllegatiInvioMailXmlBean.setNomeFile(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName())
							? lMimeTypeFirmaBean.getCorrectFileName() : lAttachmentBean.getFileNameAttach());
					lAllegatiInvioMailXmlBean.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? 1 : 0);
					lAllegatiInvioMailXmlBean.setMimetype(lMimeTypeFirmaBean.getMimetype());

					lAllegatiInvioMailXmlBean.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));

					lAllegatiInvioMailXmlBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
					lAllegatiInvioMailXmlBean.setEncoding(lMimeTypeFirmaBean.getEncoding());
					lAllegatiInvioMailXmlBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
					xmlAllegatiInvioMail.add(lAllegatiInvioMailXmlBean);

					SenderAttachmentsBean atta = new SenderAttachmentsBean();
					atta.setFile(StorageImplementation.getStorage().extractFile(uri));
					atta.setFilename(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName()) ? lMimeTypeFirmaBean.getCorrectFileName()
							: lAttachmentBean.getFileNameAttach());
					atta.setFirmato(lMimeTypeFirmaBean.isFirmato());
					atta.setMimetype(lMimeTypeFirmaBean.getMimetype());
					atta.setOriginalName(lAttachmentBean.getFileNameAttach());
					atta.setPdfConCommenti(lMimeTypeFirmaBean.isPdfConCommenti());
					atta.setPdfEditabile(lMimeTypeFirmaBean.isPdfEditabile());
					atta.setFirmaValida(lMimeTypeFirmaBean.isFirmaValida());
					lSenderAttachmentsBean.add(atta);
				}
			}
			/*
			 * Se ci sono allegati vengono inseriti all'interno di queste variabili; se non ci sono allegati queste variabili sono settate a null e vengono
			 * passate in questo modo. Nella chiamata alla business questo valore verrà controllato e passato nel formato adeguato alla stored
			 */
			xmlItem.setlSenderAttachmentsBean(lSenderAttachmentsBean);
			xmlItem.setListaAllegati(xmlAllegatiInvioMail);
		}

		xmlItem.setFlgRichConfermaLettura(bean.getConfermaLettura() != null && bean.getConfermaLettura() ? 1 : 0);

		List<File> listaFileItemLavorazione = salvaListaItemLavorazione(bean, xmlItem);

		input.setDatiemailin(xmlItem);
		input.setListaFileItemLavorazione(listaFileItemLavorazione);
		return input;
	}

	public DraftAndWorkItemsBean getItemLavorazione(SalvaInBozzaMailBean bean)
			throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, StorageException {

		DraftAndWorkItemsBean input = new DraftAndWorkItemsBean();
		InvioMailXmlBean xmlItem = new InvioMailXmlBean();

		if (StringUtils.isNotBlank(bean.getIdEmailPrincipale())) {
			xmlItem.setDimensione(bean.getDimensioneMail());
			xmlItem.setUri(bean.getUriMail());
		}

		xmlItem.setAccountMittente(bean.getAliasAccountMittente());

		List<File> listaFileItemLavorazione = salvaListaItemLavorazione(bean, xmlItem);

		input.setDatiemailin(xmlItem);
		input.setListaFileItemLavorazione(listaFileItemLavorazione);

		return input;
	}

	private String recuperaAttachments(DettaglioEmailBean lDettaglioEmailBean, String fileNameAttach) throws Exception {
		if (lDettaglioEmailBean != null) {
			for (DettaglioEmailAllegatoBean lDettaglioEmailAllegatoBean : lDettaglioEmailBean.getAllegati()) {
				if (lDettaglioEmailAllegatoBean.getNomeFileAllegato().equals(fileNameAttach)) {
					return lDettaglioEmailAllegatoBean.getUriFileAllegato();
				}
			}
		}
		throw new StoreException("Impossibile recuperare l'allegato " + fileNameAttach);
	}

	private DettaglioEmailBean getAttachInDettaglioEmailBean(String idEmail) throws StoreException {
		DettaglioEmailBean lDettaglioEmailBean = new DettaglioEmailBean();
		lDettaglioEmailBean.setIdEmail(idEmail);
		AurigaLoadDettaglioEmailDataSource lAurigaLoadDettaglioEmailDataSource = new AurigaLoadDettaglioEmailDataSource();
		try {
			lAurigaLoadDettaglioEmailDataSource.setSession(getSession());
			lDettaglioEmailBean = lAurigaLoadDettaglioEmailDataSource.retrieveAttach(lDettaglioEmailBean);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return lDettaglioEmailBean;
	}

	private boolean isFileCambiato(String uri2, String uri1) {
		if ((StringUtils.isNotBlank(uri1)) && (StringUtils.isNotBlank(uri2)) && (uri1.equalsIgnoreCase(uri2))) {
			return false;
		} else {
			return true;
		}
	}

	private List<File> salvaListaItemLavorazione(SalvaInBozzaMailBean bean, InvioMailXmlBean xmlItem) throws StorageException {
		List<ItemLavorazioneMailXmlBean> listaItemLavorazione = new ArrayList<ItemLavorazioneMailXmlBean>();
		List<File> listaFileAllegati = new ArrayList<File>();
		if (bean.getListaItemInLavorazione() != null && bean.getListaItemInLavorazione().size() > 0) {
			for (ItemLavorazioneMailBean item : bean.getListaItemInLavorazione()) {
				// Mi serve per scartare gli item vuoti
				boolean flagItemValido = false;
				ItemLavorazioneMailXmlBean lItemLavorazioneMailXmlBean = new ItemLavorazioneMailXmlBean();
				lItemLavorazioneMailXmlBean.setSceltaTipo(item.getItemLavTipo() != null && !"".equals(item.getItemLavTipo()) ? item.getItemLavTipo() : "F");
				lItemLavorazioneMailXmlBean.setNumProgressivo(item.getItemLavNrItem() != null ? String.valueOf(item.getItemLavNrItem()) : null);
				lItemLavorazioneMailXmlBean.setCaricatoDa(item.getItemLavCaricatoDa());
				lItemLavorazioneMailXmlBean.setDataOraCaricamento(item.getItemLavDataOraCaricamento() != null ? item.getItemLavDataOraCaricamento() : null);
				lItemLavorazioneMailXmlBean.setModificatoDa(item.getItemLavModificatoDa());
				lItemLavorazioneMailXmlBean.setDataOraModifica(item.getItemLavDataOraModifica() != null ? item.getItemLavDataOraModifica() : null);

				/**
				 * Gestione item non modificabili e/o cancellabili
				 */
				lItemLavorazioneMailXmlBean
						.setFlgNonModCancLocked(item.getFlgNonModCancLocked() != null && item.getFlgNonModCancLocked() ? new Integer(1) : new Integer(0));
				lItemLavorazioneMailXmlBean.setFlgNonModCanc(item.getFlgNonModCanc());
				lItemLavorazioneMailXmlBean.setMotivoNonModCanc(item.getMotivoNonModCanc());

				if (lItemLavorazioneMailXmlBean.getSceltaTipo().equals("F")) {
					if (StringUtils.isNotBlank(item.getItemLavUriFile())) {
						flagItemValido = true;
						lItemLavorazioneMailXmlBean.setUriFile(item.getItemLavUriFile());
						// uri del file originale, per l'eventuale cancellazione
						lItemLavorazioneMailXmlBean.setUriFileSaved(item.getItemLavUriFileSaved());
						lItemLavorazioneMailXmlBean.setNomeFile(item.getItemLavNomeFile());
						lItemLavorazioneMailXmlBean
								.setDimensioneFile(item.getItemLavInfoFile() != null ? String.valueOf(item.getItemLavInfoFile().getBytes()) : null);
						lItemLavorazioneMailXmlBean.setMimeType(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getMimetype() : null);
						lItemLavorazioneMailXmlBean.setFlgFileFirmato(item.getItemLavFlgFileFirmato() != null && !"".equals(item.getItemLavFlgFileFirmato())
								&& "true".equals(item.getItemLavFlgFileFirmato()) ? 1 : 0);
						lItemLavorazioneMailXmlBean.setImpronta(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getImpronta() : null);

						/*
						 * Nel caso in cui si scansioni un documento l'encoding e l'impronta si trovano in itemLavAlgoritmoImprontaFile e
						 * itemLavAlgoritmoEncodingImprontaFile mentre nel caso in cui si tratti dell'upload di un file si trovano all'interno di LavInfoFile in
						 * Algoritmo e in Encoding
						 */
						String algoritmoImprontaFile = "";
						String algoritmoEncodingImprontaFile = "";
						if (item.getItemLavInfoFile() != null) {
							if (StringUtils.isBlank(item.getItemLavInfoFile().getAlgoritmo())) {
								// Il caso in cui si trovano all'interno di itemLavAlgoritmoImprontaFile
								algoritmoImprontaFile = item.getItemLavAlgoritmoImprontaFile();
							} else {
								// Il caso in cui si trovano all'interno di LavInfoFile
								algoritmoImprontaFile = item.getItemLavInfoFile().getAlgoritmo();
							}

							if (StringUtils.isBlank(item.getItemLavInfoFile().getEncoding())) {
								// Il caso in cui si trovano all'interno di itemLavAlgoritmoEncodingImprontaFile
								algoritmoEncodingImprontaFile = item.getItemLavAlgoritmoEncodingImprontaFile();
							} else {
								// Il caso in cui si trovano all'interno di LavInfoFile
								algoritmoEncodingImprontaFile = item.getItemLavInfoFile().getEncoding();
							}
						}

						lItemLavorazioneMailXmlBean.setAgoritmoImpronta(algoritmoImprontaFile);
						lItemLavorazioneMailXmlBean.setAlgoritmoEncoding(algoritmoEncodingImprontaFile);
						lItemLavorazioneMailXmlBean.setFlgConvertibilePdf(item.getItemLavFlgConvertibilePdf() != null
								&& !"".equals(item.getItemLavFlgConvertibilePdf()) && "true".equals(item.getItemLavFlgConvertibilePdf()) ? 1 : 0);
						if (isFileCambiato(item.getItemLavUriFile(), item.getItemLavUriFileSaved())) {
							listaFileAllegati.add(StorageImplementation.getStorage().extractFile(item.getItemLavUriFile()));
							lItemLavorazioneMailXmlBean.setPosFileInLista(listaFileAllegati.size() - 1);
						}
					}
				} else if (lItemLavorazioneMailXmlBean.getSceltaTipo().equals("AT")) {
					if ((StringUtils.isNotBlank(item.getItemLavCommento())) || (StringUtils.isNotBlank(item.getItemLavTag()))) {
						flagItemValido = true;
						// uri dell'eventuale file da cancellare
						lItemLavorazioneMailXmlBean.setUriFileSaved(item.getItemLavUriFileSaved());
						lItemLavorazioneMailXmlBean.setNota(item.getItemLavCommento());
						lItemLavorazioneMailXmlBean.setTag(item.getItemLavTag());
					}
					/**
					 * Gestione obbligatorietà compilazione campo note per tag scelto
					 */
					lItemLavorazioneMailXmlBean.setFlgCommentoObbligatorioTag(item.getFlgCommentoObbligatorioTag());
				}
				if (flagItemValido) {
					listaItemLavorazione.add(lItemLavorazioneMailXmlBean);
				}
			}
			xmlItem.setListaItemLavorazione(listaItemLavorazione);
		}
		return listaFileAllegati;
	}

	private SalvaInBozzaMailBean checkBodyHtml(SalvaInBozzaMailBean mailBean) {
		/*
		 * Richiamo il datasource in cui è definito il metodo che controlla se effettivamente
		 * il body deve essere vuoto o meno.
		 * Se ad esempio il body è formato solamente da <!-- commenti --> o <div>..... allora deve
		 * essere vuoto 
		 */		
		
		CorpoMailDataSource corpoMailDataSource = new CorpoMailDataSource();
		
		String newBodyHtml = "";
		try {
			/*
			 * In salvataggio, al contrario dell'invio, non bisogna rimuovere i marker
			 * ma solamente controllare il testo se è vuoto o meno.
			 */
			newBodyHtml = corpoMailDataSource.checkBodyHtml(mailBean.getBodyHtml());
			
			//Espressione regolare utilizzata per rimuovere i caratteri speciali non decodificabili nei vari charset
			newBodyHtml = newBodyHtml.replaceAll("\\p{C}", " ");
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		mailBean.setBodyHtml(newBodyHtml);
		mailBean.setBodyText(newBodyHtml);
		
		return mailBean;
	}
	
	private void setUpdateBozza(String updateBozza){
		this.updateBozza = updateBozza;
	}
	
	private Boolean isUpdateBozza(){
		return updateBozza != null && "true".equalsIgnoreCase(updateBozza);
	}
}