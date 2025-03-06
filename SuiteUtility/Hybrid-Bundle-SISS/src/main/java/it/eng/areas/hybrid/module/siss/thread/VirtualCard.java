package it.eng.areas.hybrid.module.siss.thread;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

public class VirtualCard extends CardTerminal{

	@Override
	public Card connect(String arg0) throws CardException {
		return null;
	}

	@Override
	public String getName() {
		return "Virtual Card";
	}

	@Override
	public boolean isCardPresent() throws CardException {
		return true;
	}

	@Override
	public boolean waitForCardAbsent(long arg0) throws CardException {
		return false;
	}

	@Override
	public boolean waitForCardPresent(long arg0) throws CardException {
		return false;
	}

}
