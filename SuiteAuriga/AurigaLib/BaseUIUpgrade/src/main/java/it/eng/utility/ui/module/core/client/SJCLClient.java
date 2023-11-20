/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class SJCLClient {

	public static native String encrypt(String password, String data)/*-{
		return $wnd.sjcl.encrypt(password, data);
	}-*/;

	public static native String decrypt(String password, String data)/*-{
		return $wnd.sjcl.decrypt(password, data);
	}-*/;

	private String password;
	private boolean enabled;
	
	public SJCLClient() {
		this.enabled = false;
		this.password = "iygwduyvw";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String encrypt(String data) {
		// System.out.println("SJCLClient.encrypt() called and enabled: "+enabled);
		if (!this.enabled) return data;
		if (data == null || data.trim().isEmpty()) return data;
		// System.out.println("ENCRYPT");
		return encrypt(this.password, data);
	};

	public String decrypt(String data) {
		// System.out.println("SJCLClient.decrypt() called and enabled: "+enabled);
		if (!this.enabled) return data;
		if (data == null || data.trim().isEmpty()) return data;
		// System.out.println("DECRYPT");
		return decrypt(this.password, data);
		
	}
	
		
	public String decryptIfNeeded(String data) {
		// System.out.println("SJCLClient.decryptIfNeeded() called and enabled: "+enabled);
		String result = data;
		if (data == null || data.trim().isEmpty()) {
			// enabled = false;
			// System.out.println("WARNING: SJCLClient.enabled setted to false");
			return result;
		}

		final boolean flagEncrypted = data.indexOf("cipher") >= 0;
		enabled = flagEncrypted;
		// System.out.println("SJCLClient.enabled setted to: "+enabled);
		if (flagEncrypted) {
			// System.out.println("DECRYPT");
			result = decrypt(this.password, data);
								
		} else {
			// System.out.println("WARNING: ALREADY DECRYPTED!!!!!!!");
		}
		return result;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("[enabled=");
		builder.append(this.enabled);
		builder.append(", password=");
		builder.append(this.password);
		builder.append("]");
		return builder.toString();
	}
	
}
