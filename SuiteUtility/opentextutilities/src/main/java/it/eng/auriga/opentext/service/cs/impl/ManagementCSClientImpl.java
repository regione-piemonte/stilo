package it.eng.auriga.opentext.service.cs.impl;

import java.net.URL;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;

import org.slf4j.LoggerFactory;

import com.opentext.livelink.service.contentservice.ContentService;
import com.opentext.livelink.service.contentservice.ContentService_Service;
import com.opentext.livelink.service.documentmanagement.DocumentManagement;
import com.opentext.livelink.service.documentmanagement.DocumentManagement_Service;
import com.opentext.livelink.service.memberservice.MemberService;
import com.opentext.livelink.service.memberservice.MemberService_Service;
import com.opentext.livelink.service.searchservice.SearchService;
import com.opentext.livelink.service.searchservice.SearchService_Service;
import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.xml.ws.developer.WSBindingProvider;

import it.eng.auriga.opentext.config.ContentServerSettings;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.ManagementCSClient;
import it.eng.auriga.opentext.service.cs.bean.OTDocumentContent;

public class ManagementCSClientImpl extends AbstractManageCSService implements ManagementCSClient {

	private ContentService contentServiceClient = null;

	private DocumentManagement docManClient = null;

	private SearchService searchServiceClient = null;

	private MemberService memberServiceClient = null;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ManagementCSClientImpl.class);

	public ManagementCSClientImpl(String endpointCS, String endpointDM) {
		settingcs = new ContentServerSettings();
		soapHeaderService = new SoapHeaderServiceImpl();
		csDocumentService = new DocumentServiceImpl(endpointCS, endpointDM);
		contentService = new ContentServiceImpl(endpointCS, endpointDM);
	}

	public ManagementCSClientImpl() {
		settingcs = new ContentServerSettings();
		soapHeaderService = new SoapHeaderServiceImpl();
		csDocumentService = new DocumentServiceImpl();
		contentService = new ContentServiceImpl();
	}

	public DocumentManagement getDocumentManagement(String endpointDM) throws ContentServerException {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		if (docManClient == null) {
			try {

				// Create the DocumentManagement service client
				// DocumentManagement_Service docManService = new DocumentManagement_Service(
				// new URL(settingcs.getDocManagementWsdl()));

				DocumentManagement_Service docManService = null;
				if (endpointDM != null) {
					docManService = new DocumentManagement_Service(new URL(endpointDM));
				}
				// else
				// docManService = new DocumentManagement_Service(endpointDM);
				docManClient = docManService.getBasicHttpBindingDocumentManagement(endpointDM);

				logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName()
						+ " >> DocumentManagement service client creato correttamente");
			} catch (Exception e) {
				logger.error("Eccezione nella creazione del DocumentManagement service client " + e.getMessage(), e);
				throw new ContentServerException(settingcs.getAuthException());
			}
		}
		return docManClient;
	}

	public ContentService getContentServiceClient(String endpointCS) throws ContentServerException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		// ContentService contentServiceClient = null;
		if (contentServiceClient == null) {
			try {

				ContentService_Service contentServiceEndpoint = null;
				if (endpointCS != null)
					contentServiceEndpoint = new ContentService_Service(new URL(endpointCS));
//				else
//					contentServiceEndpoint = new ContentService_Service();
				contentServiceClient = contentServiceEndpoint.getBasicHttpBindingContentService(endpointCS,new MTOMFeature());
				((BindingProvider) contentServiceClient).getRequestContext()
						.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 10240);

				// soapHeaderService.setAuthSOAPHeader(otToken,
				// (WSBindingProvider)contentServiceClient);

				logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName()
						+ " >> ContentService Management service client creato correttamente");
			} catch (Exception e) {
				logger.error("Eccezione nella creazione del ContentService service client " + e.getMessage(), e);
				throw new ContentServerException(settingcs.getAuthException());
			}
		}
		return contentServiceClient;
	}

	public void updateContentServiceClientToDownload(String otToken, String contextId) throws ContentServerException {
		soapHeaderService.setAuthAndContextSoapHeaderElementToDownload(otToken, contextId,
				(WSBindingProvider) this.contentServiceClient);
	}

	public void updateContentServiceClientToUpload(String otToken, String contextId,
			OTDocumentContent otDocumentContent) throws ContentServerException {
		soapHeaderService.setAuthAndContextSoapHeaderElementToUpload(otToken, contextId, otDocumentContent,
				(WSBindingProvider) this.contentServiceClient);
	}

	public MemberService getMemberServiceClient() throws ContentServerException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");

		if (memberServiceClient == null) {

			try {

				MemberService_Service memberServiceEndpoint = new MemberService_Service(
						new URL(settingcs.getMemberServiceWsdl()));
				memberServiceClient = memberServiceEndpoint.getBasicHttpBindingMemberService(settingcs.getMemberServiceWsdl(),new MTOMFeature());
				((BindingProvider) memberServiceClient).getRequestContext()
						.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, settingcs.getCwsConfigMTOMchunkSize());
				logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName()
						+ " >> MemberService client creato correttamente");

			} catch (Exception e) {
				logger.error("Eccezione nella creazione del MemberService service client " + e.getMessage(), e);
				throw new ContentServerException(settingcs.getAuthException());
			}
		}
		return memberServiceClient;
	}

	public void updateClientWithAuthorization(String otToken, WSBindingProvider providerInput)
			throws ContentServerException {
		soapHeaderService.setAuthSOAPHeader(otToken, providerInput);

	}

	public SearchService getSearchServiceClient() throws ContentServerException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		if (searchServiceClient == null) {
			try {

				SearchService_Service MemberServiceEndpoint = new SearchService_Service(
						new URL(settingcs.getSearchServiceWsdl()));
				searchServiceClient = MemberServiceEndpoint.getBasicHttpBindingSearchService(settingcs.getSearchServiceWsdl(),new MTOMFeature());
				((BindingProvider) searchServiceClient).getRequestContext()
						.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, settingcs.getCwsConfigMTOMchunkSize());
			} catch (Exception e) {
				logger.error("Eccezione nella creazione del MemberService service client " + e.getMessage(), e);
				throw new ContentServerException(settingcs.getAuthException());
			}
		}
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		return searchServiceClient;
	}

}
