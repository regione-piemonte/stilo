package it.eng.hybrid.module.jpedal.ui.popup.firma;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

public class VirtualCard extends CardTerminal{

	@Override
	public Card connect(String arg0) throws CardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// 
		return "Virtual Card";
	}

	@Override
	public boolean isCardPresent() throws CardException {
		//  
		return true;
	}

	@Override
	public boolean waitForCardAbsent(long arg0) throws CardException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean waitForCardPresent(long arg0) throws CardException {
		// TODO Auto-generated method stub
		return false;
	}

}
