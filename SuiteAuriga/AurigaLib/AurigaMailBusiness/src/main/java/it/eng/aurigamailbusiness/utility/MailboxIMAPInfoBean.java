/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.mail.Quota;

/**
 * Classe che contiene le informazioni restituite da IMAP, quindi folder presenti e quote associate
 * 
 * @author mzanetti
 *
 */

public class MailboxIMAPInfoBean {

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("MailboxIMAPInfoBean [folders=" + folders);
		if (quotas != null) {
			result.append("Quota= [");
			try {
				for (Quota quota : quotas) {
					if (quota != null) {
						for (Quota.Resource resource : quota.resources) {
							result.append(String.format("Nome:'%s', Limite:'%s', Utilizzo:'%s'", resource.name, resource.limit, resource.usage));
							result.append(System.getProperty("line.separator"));
							result.append(String.format("Percentuale usata:'%f'", MailboxIMAPInfoBean.getPercentageQuota(resource)));
						}
					}
				}
			} catch (Exception e) {
			}
		}
		result.append(" ]]");
		return result.toString();
	}

	private static final long serialVersionUID = 4306874148478205343L;

	private List<MailboxFolderInfoBean> folders;

	private List<Quota> quotas; // quote associate alla mailbox, potrebbero esserci una quota per ogni folder, o un'unica folder condivisa per tutte le
	// folder

	public List<MailboxFolderInfoBean> getFolders() {
		return folders;
	}

	public void setFolders(List<MailboxFolderInfoBean> folders) {
		this.folders = folders;
	}

	public List<Quota> getQuotas() {
		return quotas;
	}

	public void setQuotas(List<Quota> quotas) {
		this.quotas = quotas;
	}

	public static Float getPercentageQuota(Quota.Resource resource) throws Exception {

		if (resource != null) {

			return (float) resource.usage * 100 / resource.limit;

		}

		return null;

	}

}
