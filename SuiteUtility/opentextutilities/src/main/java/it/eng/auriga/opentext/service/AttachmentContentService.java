package it.eng.auriga.opentext.service;

import java.util.List;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.bean.OTAttachment;
import it.eng.auriga.opentext.service.cs.bean.OTEsitoOperazione;
import it.eng.auriga.opentext.service.cs.bean.OTMetadataAttachment;

public interface AttachmentContentService {
	public List<OTMetadataAttachment> removeAttachment(String otToken, String plant, Long idAttachment)
			throws ContentServerException;

	public OTAttachment getAttachment(String otToken, Long idAttachment) throws ContentServerException;

	public List<OTMetadataAttachment> classificaAttachment(String otToken, String plant, long idAttachment,
			String nameAttachment, String descriptionAttachmentType) throws ContentServerException;

	public OTEsitoOperazione versioningAttachment(String otToken, Long idAttachmentt, byte[] content);
}
