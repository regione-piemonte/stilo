/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class AnagraficaRubricaEmailBean extends AnagraficaRubricaEmailXmlBean{

    private boolean flgPEC;
	private String codAoo;
	private String codAmministrazione;
	private String idUtenteIns;
	private String idUtenteUltMod;
	private String idProvSoggRif;
	private String idFruitoreCasella;
	private List<SoggettoGruppoEmailBean> listaSoggettiGruppo;
	
	public boolean isFlgPEC() {
		return flgPEC;
	}
	public void setFlgPEC(boolean flgPEC) {
		this.flgPEC = flgPEC;
	}
	public String getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(String idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public String getIdUtenteUltMod() {
		return idUtenteUltMod;
	}
	public void setIdUtenteUltMod(String idUtenteUltMod) {
		this.idUtenteUltMod = idUtenteUltMod;
	}
	public String getCodAoo() {
		return codAoo;
	}
	public void setCodAoo(String codAoo) {
		this.codAoo = codAoo;
	}
	public String getCodAmministrazione() {
		return codAmministrazione;
	}
	public void setCodAmministrazione(String codAmministrazione) {
		this.codAmministrazione = codAmministrazione;
	}
	public String getIdProvSoggRif() {
		return idProvSoggRif;
	}
	public void setIdProvSoggRif(String idProvSoggRif) {
		this.idProvSoggRif = idProvSoggRif;
	}
	public String getIdFruitoreCasella() {
		return idFruitoreCasella;
	}
	public void setIdFruitoreCasella(String idFruitoreCasella) {
		this.idFruitoreCasella = idFruitoreCasella;
	}
	public List<SoggettoGruppoEmailBean> getListaSoggettiGruppo() {
		return listaSoggettiGruppo;
	}
	public void setListaSoggettiGruppo(List<SoggettoGruppoEmailBean> listaSoggettiGruppo) {
		this.listaSoggettiGruppo = listaSoggettiGruppo;
	}
							
}
