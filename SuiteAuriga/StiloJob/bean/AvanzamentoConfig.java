/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.UUID;

import it.eng.mail.bean.MailAddress;

public class AvanzamentoConfig implements Serializable {

	private static final long serialVersionUID = 1975150304771642459L;

	private String jobName;
	private String schema;

	private String reportPath;
	private String connToken;
	private int numTryMax;
	private boolean inviaSegnalazioni;
	private MailAddress indirizziMailSegnalazioni = new MailAddress();

	public AvanzamentoConfig() {
		this.jobName = UUID.randomUUID().toString().replace("-", "");
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getNumTryMax() {
		return numTryMax;
	}

	public void setNumTryMax(int numTryMax) {
		this.numTryMax = numTryMax;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public boolean isInviaSegnalazioni() {
		return inviaSegnalazioni;
	}

	public void setInviaSegnalazioni(boolean inviaSegnalazioni) {
		this.inviaSegnalazioni = inviaSegnalazioni;
	}

	public MailAddress getIndirizziMailSegnalazioni() {
		return indirizziMailSegnalazioni;
	}

	public void setIndirizziMailSegnalazioni(MailAddress indirizziMailSegnalazioni) {
		this.indirizziMailSegnalazioni = indirizziMailSegnalazioni;
	}

	public String getConnToken() {
		return connToken;
	}

	public void setConnToken(String connToken) {
		this.connToken = connToken;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@Override
	public String toString() {
		return String.format("AvanzamentoConfig [jobName=%s,  reportPath=%s, inviaSegnalazioni=%s ]", jobName,
				reportPath, inviaSegnalazioni, indirizziMailSegnalazioni);
	}

}
