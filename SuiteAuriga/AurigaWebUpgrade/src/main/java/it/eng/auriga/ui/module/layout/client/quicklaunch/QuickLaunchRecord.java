/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.tile.TileRecord;

/**
 * traccia le informazioni necessarie alla renderizzazione dei tile di lancio rapido che compaiono nell'AurigaLayout
 * @author massimo malvestio
 *
 */
public class QuickLaunchRecord extends TileRecord implements Comparable<QuickLaunchRecord> {
	
	public QuickLaunchRecord(String picture, String description, String nomeEntita) {
		
		this(null, picture, description, nomeEntita);
		
	}
	
	public QuickLaunchRecord(Integer position, String picture, String description, String nomeEntita) {
		
		setPosition(position);
		
		setPicture(picture);		
		
		setDescription(description);
		
		setNomeEntita(nomeEntita);
		
	}
	
	public Integer getPosition() {
		return getAttributeAsInt("position");
	}

	public void setPosition(Integer position) {
		setAttribute("position", position);
	}

	public String getPicture() {
		return getAttribute("picture");
	}

	public void setPicture(String picture) {
		setAttribute("picture", picture);
	}

	public String getDescription() {
		return getAttribute("description");
	}

	public void setDescription(String description) {
		setAttribute("description", description);
	}

	public String getNomeEntita() {
		return getAttribute("nomeEntita");
	}

	public void setNomeEntita(String nomeEntita) {
		setAttribute("nomeEntita", nomeEntita);
	}

	@Override
	public int compareTo(QuickLaunchRecord obj) {
		
		Integer pos1 = this.getPosition() != null ? this.getPosition() : Integer.MAX_VALUE;
		Integer pos2 = obj.getPosition() != null ? obj.getPosition() : Integer.MAX_VALUE;
		return pos1.compareTo(pos2);
	}
		
}
