/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator.AttributiOperatori;
import it.eng.document.NumeroColonna;

public class AttributiProcBean {

	@NumeroColonna(numero = "1")
	private String nome;
	@NumeroColonna(numero = "2")
	private AttributiOperatori operatoreLogico;
	@NumeroColonna(numero = "3")
	private String confrontoDa;
	@NumeroColonna(numero = "4")
	private String confrontoA;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public AttributiOperatori getOperatoreLogico() {
		return operatoreLogico;
	}
	public void setOperatoreLogico(AttributiOperatori operatoreLogico) {
		this.operatoreLogico = operatoreLogico;
	}
	public String getConfrontoDa() {
		return confrontoDa;
	}
	public void setConfrontoDa(String confrontoDa) {
		this.confrontoDa = confrontoDa;
	}
	public String getConfrontoA() {
		return confrontoA;
	}
	public void setConfrontoA(String confrontoA) {
		this.confrontoA = confrontoA;
	}
	
}
