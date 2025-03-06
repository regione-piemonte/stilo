package it.eng.client.applet.thread;

import it.eng.client.applet.panel.PanelSign;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;

public class CardPresentThread extends Thread{

	public CardPresentThread(PanelSign panelsign,CardTerminals cardterminals){
		this.sign = panelsign;
		this.cardterminals = cardterminals;
	}
	
	private PanelSign sign;
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
					sign.settingReader(listcardterminal);
					sign.setCardPresent(true, true);
				}else{
					//Recupero le carte
					listcardterminal =cardterminals.list();

					//Setto le carte presenti
					sign.settingReader(listcardterminal);

					//controllo se ne ho una settata
					if(cardname != null){
						CardTerminal terminal = cardterminals.getTerminal(cardname);
						if(terminal.isCardPresent()){
							sign.setCardPresent(true, false);
						} else{
							sign.setCardPresent(false, false);
						}
					}else 
						sign.setCardPresent(false, false);
				}
			} catch (Exception e) {
				sign.clearReader();
				sign.setCardPresent(false, false);
			} finally {
				try{
					Thread.sleep(1000);
				} catch(Exception e){}
			}
		}
	}

}
