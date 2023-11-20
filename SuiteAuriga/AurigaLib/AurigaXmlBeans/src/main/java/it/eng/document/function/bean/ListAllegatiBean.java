/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ListAllegatiBean implements Serializable {

	private static final long serialVersionUID = 7110795438522206629L;
	private List<AllegatiBean> allegati;

	public List<AllegatiBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatiBean> allegati) {
		this.allegati = allegati;
	}
}
