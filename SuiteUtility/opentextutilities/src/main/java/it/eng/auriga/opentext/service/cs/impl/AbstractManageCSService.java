package it.eng.auriga.opentext.service.cs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opentext.livelink.service.documentmanagement.Node;

import it.eng.auriga.opentext.config.ContentServerSettings;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.CSMemberService;
import it.eng.auriga.opentext.service.cs.ContentServiceIface;
import it.eng.auriga.opentext.service.cs.DocumentService;
import it.eng.auriga.opentext.service.cs.ManagementCSClient;
import it.eng.auriga.opentext.service.cs.SearchServiceIface;
import it.eng.auriga.opentext.service.cs.SoapHeaderService;
import it.eng.auriga.opentext.service.cs.util.ContentServerBooleanValueCathegory;
import it.eng.auriga.opentext.service.cs.util.ContentServerDateValueCathegory;
import it.eng.auriga.opentext.service.cs.util.ContentServerIntegerValueCathegory;
import it.eng.auriga.opentext.service.cs.util.ContentServerStringValueCathegory;
import it.eng.auriga.opentext.service.cs.util.IContentServerCathegoryType;
import it.eng.auriga.opentext.utils.FeatureUtility;

public abstract class AbstractManageCSService {

	protected ContentServerSettings settingcs;

	protected SoapHeaderService soapHeaderService;

	protected ManagementCSClient managementCSClient;

	protected DocumentService csDocumentService;

	protected ContentServiceIface contentService;

	protected SearchServiceIface searchServiceClient;

	protected CSMemberService memberServiceClient;


	protected Map<String, IContentServerCathegoryType> buildMapCathegoryType() {
		Map<String, IContentServerCathegoryType> mapTypeCathegory = new HashMap<String, IContentServerCathegoryType>();
		mapTypeCathegory.put("com.opentext.livelink.service.documentmanagement.StringValue", new ContentServerStringValueCathegory());
		mapTypeCathegory.put("com.opentext.livelink.service.documentmanagement.IntegerValue",
				new ContentServerIntegerValueCathegory());
		mapTypeCathegory.put("com.opentext.livelink.service.documentmanagement.DateValue", new ContentServerDateValueCathegory());
		mapTypeCathegory.put("com.opentext.livelink.service.documentmanagement.BooleanValue",
				new ContentServerBooleanValueCathegory());

		return mapTypeCathegory;

	}

	protected Node getPlantNode(String otToken, String plant) throws ContentServerException {
		List<String> plantPath = FeatureUtility.buildPlantPath(settingcs.getFolderContainerName(), plant);
		return csDocumentService.getFolderByPath(otToken, plantPath);

	}

	public ContentServerSettings getSettingcs() {
		return settingcs;
	}

	public void setSettingcs(ContentServerSettings settingcs) {
		this.settingcs = settingcs;
	}

	public SoapHeaderService getSoapHeaderService() {
		return soapHeaderService;
	}

	public void setSoapHeaderService(SoapHeaderService soapHeaderService) {
		this.soapHeaderService = soapHeaderService;
	}

	public ManagementCSClient getManagementCSClient(String endpointCS, String endpointDM) {
		if(managementCSClient==null)
			managementCSClient = new ManagementCSClientImpl(endpointCS, endpointDM);
		return managementCSClient;
	}

	public void setManagementCSClient(ManagementCSClient managementCSClient) {
		this.managementCSClient = managementCSClient;
	}

	public DocumentService getCsDocumentService() {
		return csDocumentService;
	}

	public void setCsDocumentService(DocumentService csDocumentService) {
		this.csDocumentService = csDocumentService;
	}

	public ContentServiceIface getContentService() {
		return contentService;
	}

	public void setContentService(ContentServiceIface contentService) {
		this.contentService = contentService;
	}

}
