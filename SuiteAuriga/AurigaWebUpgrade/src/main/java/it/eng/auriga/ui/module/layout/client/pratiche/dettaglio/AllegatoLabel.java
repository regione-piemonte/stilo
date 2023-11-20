/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Label;

public class AllegatoLabel extends Label {

	public AllegatoLabel(String nomeTipoAllegato, String descrizioneTipoAllegato, String flgContenuto, String nroFile) {		
		this(nomeTipoAllegato, descrizioneTipoAllegato, null, flgContenuto, nroFile);	
	}
	
	public AllegatoLabel(String nomeTipoAllegato, String descrizioneTipoAllegato, String prescrizioneAllegato, String flgContenuto, String nroFile) {		
		if(prescrizioneAllegato != null && !"".equals(prescrizioneAllegato)) {
			setContents(getAllegatoLabelContentsWithPrescrizione(nomeTipoAllegato,prescrizioneAllegato, descrizioneTipoAllegato, flgContenuto, nroFile));
		} else {
			setContents(getAllegatoLabelContents(nomeTipoAllegato, descrizioneTipoAllegato, flgContenuto, nroFile));
		}		
		setHeight(30);
		setIcon(getIconaFlgContenuto(flgContenuto));
		setIconSize(22);	
		setIconOrientation("right");
		setIconAlign("right");
		setBaseStyle(it.eng.utility.Styles.taskLabel);
		setPadding(8);
		
	}
	
	public String getAllegatoLabelContents(String nomeTipoAllegato, String descrizioneTipoAllegato, String flgContenuto, String nroFile) {		
		return "<div class='" + it.eng.utility.Styles.allegatoLabelNome + "'>" + nomeTipoAllegato + "</div>" +
			   "<div class='" + it.eng.utility.Styles.allegatoLabelDescrizione + "'>&nbsp;&nbsp;&nbsp;<i>" + descrizioneTipoAllegato + "</i></div>";		 				
	}
	
	public String getAllegatoLabelContentsWithPrescrizione(String nomeTipoAllegato,String prescrizioneAllegato, String descrizioneTipoAllegato, String flgContenuto, String nroFile) {
		return getAllegatoLabelContents(nomeTipoAllegato, descrizioneTipoAllegato, flgContenuto, nroFile) + 
			   "<div class='" + it.eng.utility.Styles.allegatoLabelPrescrizione + "'>&nbsp;&nbsp;&nbsp;" + prescrizioneAllegato + "</div>";
	}
	
	public String getIconaFlgContenuto(String flgContenuto) {
		if(flgContenuto != null) { 
			if("1".equals(flgContenuto)) {
				return "pratiche/task/flgFatta/fatta.png";
			} else if("-1".equals(flgContenuto)) {
				return "pratiche/task/flgFatta/non_fatta.png";
			}
		}
		return "blank.png";
	}
	
}
