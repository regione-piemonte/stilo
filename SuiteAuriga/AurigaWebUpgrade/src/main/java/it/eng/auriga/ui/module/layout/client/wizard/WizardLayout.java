/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class WizardLayout extends VLayout {
	HLayout lHLayout;
	VLayout lVLayoutDocuments;
	WizardDocumentTree lWizardDocumentTree;
	TreeViewer lVLayoutViewer;
	
	public WizardLayout(String idProcess,String idUd) {
		lHLayout = new HLayout();
		
		lVLayoutDocuments = new VLayout();
		lWizardDocumentTree = new WizardDocumentTree("", this,idProcess,idUd);
		lVLayoutDocuments.addMember(lWizardDocumentTree);
		lVLayoutDocuments.setWidth("180");
		lVLayoutViewer = new TreeViewer();
		
		VLayout lVLayoutContainerViewer = new VLayout();
		lVLayoutContainerViewer.setMembers(lVLayoutViewer);
		lVLayoutContainerViewer.setAlign(VerticalAlignment.CENTER);
		lVLayoutContainerViewer.setAlign(Alignment.CENTER);
		lVLayoutViewer.setAlign(Alignment.CENTER);
		lVLayoutViewer.setAlign(VerticalAlignment.CENTER);
		lVLayoutViewer.setWidth("150");
		lVLayoutViewer.setHeight100();
		
		lHLayout.setMembers(lVLayoutDocuments, lVLayoutContainerViewer);
		addMember(lHLayout);
	}

	public void showNoFile() {
		lVLayoutViewer.setMembers(new VLayout());
	}
	
	public Layout getViewer(){
		return lVLayoutViewer;
	}
}