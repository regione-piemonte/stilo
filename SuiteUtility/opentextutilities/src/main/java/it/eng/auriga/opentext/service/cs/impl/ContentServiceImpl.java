package it.eng.auriga.opentext.service.cs.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.opentext.livelink.service.contentservice.ContentService;
import com.sun.xml.ws.fault.ServerSOAPFaultException;

import it.eng.auriga.opentext.enumeration.EsitoEnum;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.exception.DuplicateDocumentNameException;
import it.eng.auriga.opentext.service.cs.ContentServiceIface;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;
import it.eng.auriga.opentext.service.cs.bean.OTEsitoOperazione;
import it.eng.auriga.opentext.utils.ByteArrayDataSourceUtil;

@Service("contentService")
public class ContentServiceImpl extends AbstractManageCSService implements ContentServiceIface {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

	private String endpointCS;
	private String endpointDM;
	
	public ContentServiceImpl(String endpointCS, String endpointDM) {
		super();
		this.endpointCS = endpointCS;
		this.endpointDM = endpointDM;
	}

	public ContentServiceImpl() {
		super();
	}

	public OTEsitoOperazione uploadContent(String otToken, String contextId, OTDocumentContent otDocumentContent)
			throws ContentServerException, DuplicateDocumentNameException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		OTEsitoOperazione otEsitoOperazione = new OTEsitoOperazione();
		try {
			ContentService contentService = getContentServiceClient(endpointCS);
			managementCSClient.updateContentServiceClientToUpload(otToken, contextId, otDocumentContent);
			contentService.uploadContent(new DataHandler(
					new ByteArrayDataSourceUtil(otDocumentContent.getContent(), otDocumentContent.getMimeType())));

		}

		catch (Exception e) {
			logger.error("Eccezione durante la trasformazione da byte[] in file " + e.getMessage(), e);
			otEsitoOperazione.setCodiceEsito(EsitoEnum.ESITO_KO_2.getCodiceEsito());
			otEsitoOperazione.setDescrizioneEsito(EsitoEnum.ESITO_KO_2.getDescrizioneEsito());
			if (e instanceof ServerSOAPFaultException && e.getLocalizedMessage().contains("DocMan.DuplicateName"))
				throw new DuplicateDocumentNameException();
			else
				throw new ContentServerException(e.getMessage());

		}

		return otEsitoOperazione;
	}

	public byte[] downloadContent(String otToken, String documentContextId) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		byte[] documentContent = null;
		try {
			ContentService contentService = getContentServiceClient(endpointCS);
			managementCSClient.updateContentServiceClientToDownload(otToken, documentContextId);

			InputStream iStream = contentService.downloadContent(documentContextId).getInputStream();
			documentContent = IOUtils.toByteArray(iStream);

		} catch (IOException exception) {
			logger.error("Errore nella conversione del contenuto del documento in ByteArray " + exception.getMessage(),
					exception);
			throw new ContentServerException(settingcs.getAuthException());
		}

		return documentContent;
	}

	private ContentService getContentServiceClient(String endpointCS) throws ContentServerException {
		if( managementCSClient==null)
			managementCSClient = new ManagementCSClientImpl(endpointCS, endpointDM);
		return managementCSClient.getContentServiceClient(endpointCS);

	}

	public String getEndpointCS() {
		return endpointCS;
	}

	public void setEndpointCS(String endpointCS) {
		this.endpointCS = endpointCS;
	}

	public String getEndpointDM() {
		return endpointDM;
	}

	public void setEndpointDM(String endpointDM) {
		this.endpointDM = endpointDM;
	}

}
