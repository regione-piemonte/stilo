package it.eng.server;

public class AutenticationProxy {

	private AutenticationProxy(){}
	
	private static AutenticationProxy singleton=null;
		
	public static synchronized AutenticationProxy getInstance(){
		if(singleton==null){
			singleton= new AutenticationProxy();
		}
		return singleton;
	}
	
	public boolean isProxy(){
		if(proxyName==null){
			return false;
		}else{
			return true;
		}
	}
	
	private String user = null;
	private String password = null;
	private String proxyName = null;
	private Integer proxyPort = null;

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

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	
	
	
}