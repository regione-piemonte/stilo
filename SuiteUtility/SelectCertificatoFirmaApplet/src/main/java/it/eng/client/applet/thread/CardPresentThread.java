package it.eng.client.applet.thread;

import it.eng.client.applet.panel.PanelCertificati;

import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;

public class CardPresentThread extends Thread{

	public CardPresentThread(PanelCertificati panelCert,CardTerminals cardterminals){
		this.panelCert = panelCert;
		this.cardterminals = cardterminals;
	}
	
	private PanelCertificati panelCert;
	private CardTerminals cardterminals = null;	
	private boolean loop = true;
	private String cardname = null;
	private  boolean emulation=PreferenceManager.enabled(PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE );
	
	public void stopThread(){
		this.loop = false;
	}

	public void setCardName(String cardname){
		this.cardname = cardname;
	}
	
	@Override
	public void run(){
		while(loop){
			try{
				List<CardTerminal> listcardterminal = new ArrayList<CardTerminal>();
				if(emulation){
					//emulazione terminale sw per test 
					//sarebbe piu' corretto fare un provider 
					listcardterminal.add(new VirtualCard());
					panelCert.settingReader(listcardterminal);
					panelCert.setCardPresent(true, true);
				}else{
					//Recupero le carte
//					for (int i=0; i<cardterminals.list().size();i++){
//						if( cardterminals.list().get(i).isCardPresent() )
//							listcardterminal.add( cardterminals.list().get(i) );
//					}
					
					listcardterminal =cardterminals.list();

					//Setto le carte presenti
					panelCert.settingReader(listcardterminal);

					//controllo se ne ho una settata
					if(cardname != null){
						CardTerminal terminal = cardterminals.getTerminal(cardname);
						if(terminal.isCardPresent()){
							panelCert.setCardPresent(true, false);
						} else{
							panelCert.setCardPresent(false, false);
						}
					}else 
						panelCert.setCardPresent(false, false);
				}
			} catch (Exception e) {
				panelCert.clearReader();
				panelCert.setCardPresent(false, false);
			} finally {
				try{
					Thread.sleep(1000);
				} catch(Exception e){}
			}
		}
	}

}
