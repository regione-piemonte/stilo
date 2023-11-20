/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.activation.DataHandler;
import it.eng.document.function.bean.RebuildedFile;
import java.util.List;


public class WSAttachBean implements Serializable {


	DataHandler[] attachments;
 	List<RebuildedFile> listRebuildedFile;
 	
	public DataHandler[] getAttachments() {
		return attachments;
	}
	public void setAttachments(DataHandler[] attachments) {
		this.attachments = attachments;
	}
	public List<RebuildedFile> getListRebuildedFile() {
		return listRebuildedFile;
	}
	public void setListRebuildedFile(List<RebuildedFile> listRebuildedFile) {
		this.listRebuildedFile = listRebuildedFile;
	}

}
