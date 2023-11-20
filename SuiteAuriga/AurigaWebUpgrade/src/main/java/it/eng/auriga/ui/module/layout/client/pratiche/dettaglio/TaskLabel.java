/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;

public class TaskLabel extends Label {
	
	private DettaglioPraticaLayout dettaglioPraticaLayout;
	
	public TaskLabel(String titolo, String dettagli, final String motiviNonEseg, String icon, final String flgFatta, final String flgEsito, String flgDisponibile, final String flgToDo, DettaglioPraticaLayout pDettaglioPraticaLayout) {
		
		this.dettaglioPraticaLayout = pDettaglioPraticaLayout;
		
		setHeight(30);
		setIcon(dettaglioPraticaLayout.getIconaVoceMenu(flgFatta, flgEsito, flgToDo));
		setIconSize(24);	
		setIconOrientation("right");
		setIconAlign("right");
		setDisabledCursor(Cursor.DEFAULT);
		setDisabled(flgDisponibile == null || !"1".equals(flgDisponibile));	
		setBaseStyle(isDisabled() ? it.eng.utility.Styles.taskLabelDisabled : it.eng.utility.Styles.taskLabel);
		setPadding(8);
		setCanHover(true);
		addHoverHandler(new HoverHandler() {
			
			@Override
			public void onHover(HoverEvent event) {
				
				setPrompt(dettaglioPraticaLayout.getPromptIconaVoceMenu(flgFatta, flgEsito, flgToDo, motiviNonEseg));
			}
		});
		
		setContents(getTaskLabelContentsWithIcon(titolo, dettagli, icon, flgDisponibile));				
		
	}	

	public String getTaskLabelContentsWithIcon(String titolo, String dettagli, String icon, String flgDisponibile) {
		icon = (icon != null && !"".equals(icon)) ? icon : "point.png";		
		String iconDisabled = icon.substring(0, icon.lastIndexOf(".")) + "_Disabled" + icon.substring(icon.lastIndexOf("."));
		if(flgDisponibile != null && "1".equals(flgDisponibile)) {
			return "<div class='" + it.eng.utility.Styles.taskLabelTitolo + "'><img src=\"images/pratiche/icone/" + icon + "\" height=\"20\" width=\"20\" style=\"vertical-align:middle;padding-right:5;\"/>" + titolo + "</div>" + 
				   (dettagli != null && !"".equals(dettagli) ? "<div class='" + it.eng.utility.Styles.taskLabelDettagli + "'><img src=\"images/blank.png\" height=\"20\" width=\"20\" style=\"vertical-align:middle;padding-right:5;\"/>&nbsp;&nbsp;&nbsp;<i>" + dettagli + "</i></div>" : "");
		} 
		return "<div class='" + it.eng.utility.Styles.taskLabelTitoloDisabled + "'><img src=\"images/pratiche/icone/" + iconDisabled + "\" height=\"20\" width=\"20\" style=\"vertical-align:middle;padding-right:5;\"/>" + titolo + "</div>" + 
			   (dettagli != null && !"".equals(dettagli) ? "<div class='" + it.eng.utility.Styles.taskLabelDettagliDisabled + "'><img src=\"images/blank.png\" height=\"20\" width=\"20\" style=\"vertical-align:middle;padding-right:5;\"/>&nbsp;&nbsp;&nbsp;<i>" + dettagli + "</i></div>" : "");			
	}
	
}
