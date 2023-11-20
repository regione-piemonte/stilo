/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class Richiesta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Impegno impegno;

	public Impegno getImpegno() {
		return impegno;
	}

	public void setImpegno(Impegno impegno) {
		this.impegno = impegno;
	}

	@Override
	public String toString() {
		return "Richiesta [impegno=" + impegno + "]";
	}
	
}
