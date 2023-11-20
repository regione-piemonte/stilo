/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

/**
 * 
 * @author DANCRIST
 *
 */

public class DocPdVBean extends DocPdVXmlBean {
	
	private Map<String, Object> attributiChiaveDoc;
	private Map<String, String> tipiAttributiChiaveDoc;
	private List<ErroriRdVBean> erroriRdV;
	
	public Map<String, Object> getAttributiChiaveDoc() {
		return attributiChiaveDoc;
	}
	public void setAttributiChiaveDoc(Map<String, Object> attributiChiaveDoc) {
		this.attributiChiaveDoc = attributiChiaveDoc;
	}
	public Map<String, String> getTipiAttributiChiaveDoc() {
		return tipiAttributiChiaveDoc;
	}
	public void setTipiAttributiChiaveDoc(Map<String, String> tipiAttributiChiaveDoc) {
		this.tipiAttributiChiaveDoc = tipiAttributiChiaveDoc;
	}
	public List<ErroriRdVBean> getErroriRdV() {
		return erroriRdV;
	}
	public void setErroriRdV(List<ErroriRdVBean> erroriRdV) {
		this.erroriRdV = erroriRdV;
	}
}
