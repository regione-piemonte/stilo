package it.eng.auriga.opentext.service.cs;

import com.opentext.livelink.service.contentservice.ContentService;
import com.opentext.livelink.service.documentmanagement.DocumentManagement;
import com.opentext.livelink.service.memberservice.MemberService;
import com.opentext.livelink.service.searchservice.SearchService;
import com.sun.xml.ws.developer.WSBindingProvider;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;

public interface ManagementCSClient {

	public DocumentManagement getDocumentManagement(String endpointDM) throws ContentServerException;

	public void updateContentServiceClientToDownload(String otToken, String contextId) throws ContentServerException;

	public MemberService getMemberServiceClient() throws ContentServerException;

	

	public void updateContentServiceClientToUpload(String otToken, String contextId,OTDocumentContent otDocumentContent)
			throws ContentServerException ;
	public ContentService getContentServiceClient(String endpointCS) throws ContentServerException;
	
	public  SearchService getSearchServiceClient() throws ContentServerException ;
	
	public void updateClientWithAuthorization(String otToken, WSBindingProvider providerInput) throws ContentServerException;
	
	

}
