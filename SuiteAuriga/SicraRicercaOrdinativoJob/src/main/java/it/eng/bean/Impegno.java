/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class Impegno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Testata testata;

	public Testata getTestata() {
		return testata;
	}

	public void setTestata(Testata testata) {
		this.testata = testata;
	}

	@Override
	public String toString() {
		return "Impegno [testata=" + testata + "]";
	}
	
}
