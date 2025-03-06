package it.eng.auriga.opentext.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opentext.livelink.service.documentmanagement.Node;

import it.eng.auriga.opentext.enumeration.CSAttachmentMetadataEnum;
import it.eng.auriga.opentext.enumeration.EsitoEnum;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.AttachmentContentService;
import it.eng.auriga.opentext.service.cs.bean.OTAttachment;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;
import it.eng.auriga.opentext.service.cs.bean.OTEsitoOperazione;
import it.eng.auriga.opentext.service.cs.bean.OTMetadataAttachment;
import it.eng.auriga.opentext.service.cs.bean.SearchServiceBean;
import it.eng.auriga.opentext.service.cs.impl.AbstractManageCSService;

@Component("attachmentContentService")
public class AttachmentContentServiceImpl extends AbstractManageCSService implements AttachmentContentService {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AttachmentContentServiceImpl.class);

	public List<OTMetadataAttachment> removeAttachment(String otToken, String plant, Long idAttachment)
			throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		List<String> elencoMetadati = new ArrayList<String>();
		elencoMetadati.add(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue());

		Map<String, Object> mapMetadata = csDocumentService.retrieveMetadataFromCathegory(otToken, idAttachment,
				settingcs.getIdAttachmentCathegory(), elencoMetadati);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> REcupero dei metadati da Content Server " + idAttachment);

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> REcupero attachment da Content Server " + idAttachment);
		csDocumentService.removeDocument(otToken, idAttachment);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Cancellazione attachment da Content Server " + idAttachment);

		Node plantNode = this.getPlantNode(otToken, plant);

		SearchServiceBean searchServiceBean = new SearchServiceBean();
		String whereConditionRiservati = "where1=(\"" + CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue()
				+ "\" : \"" + mapMetadata.get(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue())
				+ "\")&boolean2=and&where2=(\"" + CSAttachmentMetadataEnum.OTLocation.getValue() + "\" : \""
				+ plantNode.getID() + "\")";
		searchServiceBean.setWhereCondition(whereConditionRiservati);

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> Esecuzione ricerca attachment attualmente associati al DWO "
				+ mapMetadata.get(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue()));

		List<OTMetadataAttachment> elencoMetadatiAttachment = searchServiceClient.executeSearchAttachmentDWO(otToken,
				searchServiceBean);
		try {
			elencoMetadatiAttachment.remove(new OTMetadataAttachment(idAttachment));
		} catch (Exception e) {
			logger.error("Errore nella rimozione manuale dall'elenco dei metadati associati al workorder "
					+ mapMetadata.get(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue()));
		}

		return elencoMetadatiAttachment;
	}

	public OTAttachment getAttachment(String otToken, Long idAttachment) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		OTDocumentContent documentContent = csDocumentService.getDocumentContent(otToken, idAttachment);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> REcupero contenuto da Content Server " + idAttachment);
		List<String> elencoMetadati = new ArrayList<String>();
		elencoMetadati.add(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue());
		elencoMetadati.add(CSAttachmentMetadataEnum.TIPO_ATTACHMENT.getValue());
		Map<String, Object> mapMetadata = csDocumentService.retrieveMetadataFromCathegory(otToken, idAttachment,
				settingcs.getIdAttachmentCathegory(), elencoMetadati);
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " >> REcupero dei metadati da Content Server " + idAttachment);
		OTAttachment attachmentDWO = new OTAttachment();
		attachmentDWO
				.setAttachmentType((String) mapMetadata.get(CSAttachmentMetadataEnum.TIPO_ATTACHMENT.getValue()));
		attachmentDWO.setIdDWO(
				Long.valueOf((String) mapMetadata.get(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue())));
		attachmentDWO.setDocumentContent(documentContent);
		attachmentDWO.setIdAttachment(idAttachment);

		return attachmentDWO;
	}

	public List<OTMetadataAttachment> classificaAttachment(String otToken, String plant, long idAttachment,
			String nameAttachment, String descriptionAttachmentType) throws ContentServerException {
		List<OTMetadataAttachment> elencoMetadatiAttachment = new ArrayList<OTMetadataAttachment>();
		try {
			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

			OTMetadataAttachment otMetadataAttachmentDWO = new OTMetadataAttachment(idAttachment);

			if (descriptionAttachmentType != null) {
				Map<String, Object> metadataToUpdate = new HashMap<String, Object>();
				metadataToUpdate.put(CSAttachmentMetadataEnum.TIPO_ATTACHMENT.getValue(), descriptionAttachmentType);
				csDocumentService.updateMetadataNode(otToken, settingcs.getIdAttachmentCathegory(), idAttachment,
						metadataToUpdate);
				otMetadataAttachmentDWO.setOtTipoAttachment(descriptionAttachmentType);
			}

			if (nameAttachment != null && !nameAttachment.equals("")) {
				csDocumentService.renameDocument(otToken, idAttachment, nameAttachment);

				otMetadataAttachmentDWO.setoTFileName(nameAttachment);
			}

			List<String> elencoMetadati = new ArrayList<String>();
			elencoMetadati.add(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue());

			Map<String, Object> mapMetadata = csDocumentService.retrieveMetadataFromCathegory(otToken, idAttachment,
					settingcs.getIdAttachmentCathegory(), elencoMetadati);

			Node plantNode = this.getPlantNode(otToken, plant);

			SearchServiceBean searchServiceBean = new SearchServiceBean();
			String whereConditionRiservati = "where1=(\"" + CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue()
					+ "\" : \"" + mapMetadata.get(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue())
					+ "\")&boolean2=and&where2=(\"" + CSAttachmentMetadataEnum.OTLocation.getValue() + "\" : \""
					+ plantNode.getID() + "\")";
			searchServiceBean.setWhereCondition(whereConditionRiservati);

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " >> Esecuzione ricerca attachment attualmente associati al DWO "
					+ mapMetadata.get(CSAttachmentMetadataEnum.PARENT_WORKORDER.getValue()));

			elencoMetadatiAttachment = searchServiceClient.executeSearchAttachmentDWO(otToken, searchServiceBean);
			// forzo il refresh dell'elemento nella lista restituito dalla search a causa
			// dei 20 secondi dell'indicizzazione
			elencoMetadatiAttachment.set(elencoMetadatiAttachment.indexOf(otMetadataAttachmentDWO),
					otMetadataAttachmentDWO);
		} catch (Exception e) {
			logger.error("Errore nella classificazione dell'allegato " + idAttachment + " " + e.getMessage(), e);
			throw new ContentServerException("Errore nella classificazione dell'allegato " + idAttachment);
		}

		return elencoMetadatiAttachment;

	}

	public OTEsitoOperazione versioningAttachment(String otToken, Long idAttachment, byte[] content) {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		OTEsitoOperazione otEsitoOperazione = new OTEsitoOperazione();
		try {

			// creo l'allegato nella folder del DWO
			csDocumentService.versionNode(otToken, idAttachment, content);

		} catch (ContentServerException e) {
			otEsitoOperazione.setCodiceEsito(EsitoEnum.ESITO_KO_2.getCodiceEsito());
			otEsitoOperazione.setDescrizioneEsito(EsitoEnum.ESITO_KO_2.getDescrizioneEsito());
			logger.error("Errore verso il CS per il versioning dell'attachment" + e.getMessage(), e);
		}
		return otEsitoOperazione;

	}

}
