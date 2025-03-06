package net.sf.jsignpdf;

import java.util.ArrayList;
import java.util.List;

public class DataSigner {

	private List<AbstractSigner> signersManager = new ArrayList<AbstractSigner>();

	public List<AbstractSigner> getSignersManager() {
		CAdESSigner cadesSigner = new CAdESSigner();
		signersManager.add(cadesSigner);
		P7MSigner p7mSigner = new P7MSigner();
		signersManager.add(p7mSigner);
		M7MSigner m7mSigner = new M7MSigner();
		signersManager.add(m7mSigner);
		TsrSigner tsrSigner = new TsrSigner();
		signersManager.add(tsrSigner);
		return signersManager;
	}

	public void setSignersManager(List<AbstractSigner> signersManager) {
		this.signersManager = signersManager;
	}
	
	
	
}
