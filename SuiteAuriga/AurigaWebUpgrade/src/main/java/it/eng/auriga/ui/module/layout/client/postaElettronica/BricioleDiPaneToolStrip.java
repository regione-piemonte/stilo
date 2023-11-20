/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * Briciole di pane
 * @author Mattia Zanin
 *
 */
public abstract class BricioleDiPaneToolStrip extends ToolStrip {

	private RecordList livelli;
	int NUMERO_EMAIL_VISUALIZZABILE = 3;
	
	public BricioleDiPaneToolStrip() {
		setWidth100();
		setHeight(30);
		setBackgroundColor("transparent");
		setBackgroundImage("blank.png");
		setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		setBorder("0px");
		setAlign(Alignment.LEFT);				
		setOverflow(Overflow.HIDDEN);
		setVisible(false);
	}
	
	
	public void setLivelli(RecordList pLivelli,String idEmail)
	{		
		this.livelli = pLivelli;
		setMembers();
	
		if(livelli != null && livelli.getLength() > 0) 
		{							
			setVisible(livelli.getLength() > 0);
			
			/*ToolStripButton buttontree = new ToolStripButton();
			buttontree.addClickHandler(new ClickHandler() {			
				@Override
				public void onClick(ClickEvent event) 
				{
					manageListaEmail(livelli);
				}
	
				
			});
			
			buttontree.setHeight(28);
			buttontree.setPadding(1);
			buttontree.setShowDisabled(false);
			buttontree.setWrap(false);
			buttontree.setPrompt("Visualizza tutta l’alberatura delle e-mail collegate");
			buttontree.setHoverWidth(200);
			buttontree.setShowHover(true);
			buttontree.setCanHover(true);	
			buttontree.setIcon("menu/organigramma.png");  
			
			addButton(buttontree);*/
			
			ToolStripButton home = new ToolStripButton("<b>"+livelli.get(0).getAttributeAsString("descrizione") +"</b>" );
			
			if(!idEmail.equals(livelli.get(0).getAttribute("idEmail")))
			{
				home.addClickHandler(new ClickHandler() {			
					@Override
					public void onClick(ClickEvent event) 
					{
						
						manageClickLevelButtonNew(0);
											
					}
				});

			}
			else
				home.setBaseStyle(it.eng.utility.Styles.buttonBricioleDiPane);
			
			home.setHeight(28);
			home.setPadding(1);
			
			home.setShowDisabled(false);
			home.setWrap(false);
			home.setPrompt(livelli.get(0).getAttributeAsString("descrizione"));//+" - "+ livelli.get(0).getAttributeAsString("tipo") +" - "+ livelli.get(0).getAttributeAsString("sottotipo")+ " " + livelli.get(0).getAttributeAsString("tsInvio"));
			home.setHoverWidth(200);
			home.setShowHover(true);
			home.setCanHover(true);	
			home.setIcon("postaElettronica/house.png");  
		    
		    addButton(home);
			
			if(livelli.getLength() > 1)
			{
				
				if(livelli.getLength() > NUMERO_EMAIL_VISUALIZZABILE+1)
				{
					Label separator = new Label("....");
					separator.setAutoFit(true);
					addMember(separator);	
				}
				else
				{
					Label separator = new Label("|");
					separator.setAutoFit(true);
					addMember(separator);			
				}
									
				
				int init = livelli.getLength() > NUMERO_EMAIL_VISUALIZZABILE ? (livelli.getLength() - NUMERO_EMAIL_VISUALIZZABILE) : 1;
				
				for(int i = init; i < livelli.getLength(); i++) 
				{	
					final int pos = i;
					boolean isLivelloCorrente = (pos == (livelli.getLength() - 1));
					
					String titleOrig  = livelli.get(i).getAttributeAsString(getTitleField());	
					
					if(titleOrig == null) 
						titleOrig = livelli.get(i).getAttributeAsString("descrizione");
					
					String title = "<b><i>" + ((titleOrig.length() > 30) ? titleOrig.substring(0, 30) + "..." : titleOrig) + "</i></b>";
					
					ToolStripButton button = new ToolStripButton(title);
					
					if(!idEmail.equals(livelli.get(i).getAttribute("idEmail")))
					{	
						button.addClickHandler(new ClickHandler() {			
							@Override
							public void onClick(ClickEvent event) 
							{
								manageClickLevelButtonNew(pos);					
							}
						});
					}
					else
					{
						button.disable();
						button.setBaseStyle(it.eng.utility.Styles.buttonBricioleDiPane);
					}
					
					String titleAlt  = livelli.get(i).getAttributeAsString("descrizione"); //livelli.get(i).getAttributeAsString("id") + " " + livelli.get(i).getAttributeAsString("tipo") +" - "+ livelli.get(0).getAttributeAsString("sottotipo") + " " + livelli.get(i).getAttributeAsString("tsInvio");	
					
					button.setHeight(28);
					button.setPadding(1);
					
					button.setShowDisabled(false);
					button.setWrap(false);
					button.setPrompt(titleAlt);
					button.setHoverWidth(200);
					button.setShowHover(true);
					button.setCanHover(true);	
					
					
					
					addButton(button);						
					
					
					if(!isLivelloCorrente)
						addSeparator();
				
				}
			
			}
		} else {
			setVisible(false);			
		}
	}
	
	public RecordList getLivelli(){		
		return livelli;
	}
	
	
	
	public void manageClickLevelButtonNew(int pos)
	{
		Record livello = livelli.get(pos);
		caricaLivello(livello);
		
	}
	
	private void manageListaEmail(RecordList lRecord) 
	{
		
		/*Record lRecord = new Record();
		
		lRecord.setAttribute("idEmail", idEmail);
		
		final BricioleDiPaneToolStrip bp = this;
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record record = response.getData()[0];
					
					RecordList livelliPredecessori =  record.getAttributeAsRecordList("listaEmailPrecedenti");
					ListaEmailWindow listaEmailWindow = new ListaEmailWindow(livelliPredecessori,bp);
					listaEmailWindow.show();
				} 				
			}
		}, new DSRequest());*/
		final BricioleDiPaneToolStrip bp = this;
		ListaEmailWindow listaEmailWindow = new ListaEmailWindow(lRecord,bp);
		listaEmailWindow.show();
		
	}
	
	public abstract void caricaLivello(Record record);
	public abstract String getTitleField();
	
	
	
}