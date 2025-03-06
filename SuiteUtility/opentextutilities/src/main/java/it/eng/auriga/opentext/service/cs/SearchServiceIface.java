package it.eng.auriga.opentext.service.cs;

import java.util.List;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.bean.OTProtocolloMetadata;
import it.eng.auriga.opentext.service.cs.bean.OTMetadataAttachment;
import it.eng.auriga.opentext.service.cs.bean.SearchServiceBean;
import it.eng.auriga.opentext.service.cs.bean.SearchServiceResultBean;

public interface SearchServiceIface {
	public List<SearchServiceResultBean> executeSearch(String otToken, SearchServiceBean searchServiceBean, String nomeCategoria,  List<String> fieldshints) throws ContentServerException;
	public List<OTMetadataAttachment> executeSearchAttachmentDWO(String otToken, SearchServiceBean searchServiceBean) throws ContentServerException;
	public List<OTProtocolloMetadata> executeSearchDWO(String otToken, SearchServiceBean searchServiceBean,  boolean notSearchAnnullati, boolean notSearchArchiviati)
			throws ContentServerException;
}
