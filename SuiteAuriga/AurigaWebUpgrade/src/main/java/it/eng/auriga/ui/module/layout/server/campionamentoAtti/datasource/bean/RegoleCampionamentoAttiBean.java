/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * 
 * @author matzanin
 *
 */

public class RegoleCampionamentoAttiBean extends GruppoAttiCampionamentoBean {
	
	private List<GruppoAttiCampionamentoBean> listaGruppiAttiCampionamento;
	
	public List<GruppoAttiCampionamentoBean> getListaGruppiAttiCampionamento() {
		return listaGruppiAttiCampionamento;
	}
	public void setListaGruppiAttiCampionamento(List<GruppoAttiCampionamentoBean> listaGruppiAttiCampionamento) {
		this.listaGruppiAttiCampionamento = listaGruppiAttiCampionamento;
	}
	
}