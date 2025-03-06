package it.eng.hsm.client.option;

public class SignOption {

	private boolean sigillo=false;
	private boolean enveloping=false;
	private boolean detached=false;
	
	private ClientOption clientOption;

	public ClientOption getClientOption() {
		return clientOption;
	}

	public void setClientOption(ClientOption clientOption) {
		this.clientOption = clientOption;
	}

	public boolean isSigillo() {
		return sigillo;
	}

	public void setSigillo(boolean sigillo) {
		this.sigillo = sigillo;
	}

	public boolean isEnveloping() {
		return enveloping;
	}

	public void setEnveloping(boolean enveloping) {
		this.enveloping = enveloping;
	}

	public boolean isDetached() {
		return detached;
	}

	public void setDetached(boolean detached) {
		this.detached = detached;
	}
	
	

}
