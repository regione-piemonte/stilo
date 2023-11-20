/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FilterTypeBean {

	private List<String> operators;

	public void setOperators(List<String> operators) {
		this.operators = operators;
	}

	public List<String> getOperators() {
		return operators;
	}
	
}
