package it.eng.hybrid.module.firmaCertificato.thread;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceKeys;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceManager;
import it.eng.hybrid.module.firmaCertificato.tool.VirtualCard;
import it.eng.hybrid.module.firmaCertificato.ui.PanelSign;

public class CardPresentThread extends Thread{

	public final static Logger logger = Logger.getLogger(CardPresentThread.class);
	
	public CardPresentThread(PanelSign panelsign, CardTerminals cardterminals){
		this.sign = panelsign;
		this.cardterminals = cardterminals;
	}
	
	private PanelSign sign;
	private CardTerminals cardterminals = null;	
	private boolean loop = true;
	private String cardname = null;
	private  boolean emulation = PreferenceManager.enabled(PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE );
	
	public void stopThread(){
		this.loop = false;
	}

	public void setCardName(String cardname){
		this.cardname = cardname;
	}
	
	@Override
	public void run(){
		while(loop){
			// logger.debug("CardPresentThread attivo");
			try{
				List<CardTerminal> listcardterminal = new ArrayList<CardTerminal>();
				if (emulation){
					//emulazione terminale sw per test 
					//sarebbe piu' corretto fare un provider 
					listcardterminal.add(new VirtualCard());
					sign.settingReader(listcardterminal);
					sign.setCardPresent(true, true);
				} else {
					//Recupero le carte
//					for (int i=0; i<cardterminals.list().size();i++){
//						if( cardterminals.list().get(i).isCardPresent() )
//							listcardterminal.add( cardterminals.list().get(i) );
//					}
					listcardterminal = cardterminals.list();
					//Setto le carte presenti
					sign.settingReader(listcardterminal);
					//controllo se ne ho una settata
					if(cardname != null){
						CardTerminal terminal = cardterminals.getTerminal(cardname);
						if (terminal.isCardPresent()){
							sign.setCardPresent(true, false);
						} else {
							sign.setCardPresent(false, false);
						}
					} else {
						sign.setCardPresent(false, false);
					}
				}
			} catch (CardException e) {
				try {
					cardterminals = ricaricaSmartCardReader();
				} catch (Exception e1) {
					logger.error("Errore nella reinizializzazione del lettore");
				}
				sign.clearReader();
				sign.setCardPresent(false, false);
			} catch (Exception e) {
				sign.clearReader();
				sign.setCardPresent(false, false);
			} finally {
				try{
					Thread.sleep(1000);
				} catch (Exception e){}
			}
		}
	}
	
	public CardTerminals ricaricaSmartCardReader() throws Exception{
		Class pcscterminal = Class.forName("sun.security.smartcardio.PCSCTerminals");
        Field contextId = pcscterminal.getDeclaredField("contextId");
        contextId.setAccessible(true);

        CardTerminals terminals = null;
        if (contextId.getLong(pcscterminal) != 0L) {
            // First get a new context value
            Class pcsc = Class.forName("sun.security.smartcardio.PCSC");
            Method SCardEstablishContext = pcsc.getDeclaredMethod("SCardEstablishContext", 
            		new Class[] { Integer.TYPE }
            );
            SCardEstablishContext.setAccessible(true);

            Field SCARD_SCOPE_USER = pcsc.getDeclaredField("SCARD_SCOPE_USER");
            SCARD_SCOPE_USER.setAccessible(true);

            long newId = ((Long)SCardEstablishContext.invoke(pcsc, 
                    new Object[] { SCARD_SCOPE_USER.getInt(pcsc) }
            ));
            contextId.setLong(pcscterminal, newId);

            // Then clear the terminals in cache
            TerminalFactory factory = TerminalFactory.getDefault();
            terminals = factory.terminals();
            Field fieldTerminals = pcscterminal.getDeclaredField("terminals");
            fieldTerminals.setAccessible(true);
            Class classMap = Class.forName("java.util.Map");
            Method clearMap = classMap.getDeclaredMethod("clear");

            clearMap.invoke(fieldTerminals.get(terminals));
        }
        
        return terminals;
	}

}
