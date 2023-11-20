/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.DraftAndWorkItemsBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;

@XmlRootElement(name="mailLoginDraftAndWorkItemsBean")
public class MailLoginDraftAndWorkItemsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DraftAndWorkItemsBean draftAndWorkItemsBean;
	private MailLoginBean mailLoginBean;
	
	public DraftAndWorkItemsBean getDraftAndWorkItemsBean() {
		return draftAndWorkItemsBean;
	}
	public void setDraftAndWorkItemsBean(DraftAndWorkItemsBean draftAndWorkItemsBean) {
		this.draftAndWorkItemsBean = draftAndWorkItemsBean;
	}
	public MailLoginBean getMailLoginBean() {
		return mailLoginBean;
	}
	public void setMailLoginBean(MailLoginBean mailLoginBean) {
		this.mailLoginBean = mailLoginBean;
	}
	
}
