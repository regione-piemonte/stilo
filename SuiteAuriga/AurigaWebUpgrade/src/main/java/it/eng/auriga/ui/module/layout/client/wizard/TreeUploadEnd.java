/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewLayout;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;

public class TreeUploadEnd implements UploadItemCallBackHandler, ManageInfoCallbackHandler{

	private String idTipoDocumento;
	private WizardDocumentTree wizardDocumentTree;
	private String uri;
	private Record record;
	
	public TreeUploadEnd(WizardDocumentTree pWizardDocumentTree,Record record){
		//this.idTipoDocumento = idTipoDocumento;
		this.wizardDocumentTree = pWizardDocumentTree;
		this.record=record;
	}
	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	@Override
	public void manageInfo(InfoFileRecord info) {
		PreviewLayout lPreviewLayout = new PreviewLayout(uri, false, 
				info, "FileToExtractBean", wizardDocumentTree.getWizardLayout().getViewer(),
				wizardDocumentTree.getWizardLayout(), false);
		record.setAttribute("info", info);
		wizardDocumentTree.updateBean(record);
		
	}
	@Override
	public void uploadEnd(String displayFileName, String uri) {
		this.setUri(uri);
		record.setAttribute("uri", uri);
		wizardDocumentTree.updateBean(record);
	}
	@Override
	public void manageError(String error) {
		
		
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUri() {
		return uri;
	}
}
