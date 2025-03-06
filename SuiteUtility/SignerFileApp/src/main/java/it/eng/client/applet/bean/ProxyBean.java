package it.eng.client.applet.bean;

public class ProxyBean {

	private String host;
	private int port;
	private String user;
	private String password;
	private boolean useproxy = false;
		
	public boolean isUseproxy() {
		return useproxy;
	}
	public void setUseproxy(boolean useproxy) {
		this.useproxy = useproxy;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
