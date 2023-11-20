/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class RequestFieldIndiceFascicoloClearoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String uri;
	private List<String> options;
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public List<String> getOptions() {
		return options;
	}
	
	public void setOptions(List<String> options) {
		this.options = options;
	}
	
	@Override
	public String toString() {
		return "RequestFieldIndiceFascicoloClearoBean [uri=" + uri + ", options=" + options + "]";
	}
	
}
