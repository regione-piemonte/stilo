package it.eng.proxyselector.configuration;

public class ProxyConfiguration {

	private String proxy;
	private int port;
	private String username;
	private char[] password;
	private String dominio;
	private boolean useNTLM;
	private boolean useScript;
	private String script;
	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public boolean isUseNTLM() {
		return useNTLM;
	}
	public void setUseNTLM(boolean useNTLM) {
		this.useNTLM = useNTLM;
	}
	public ProxyConfiguration(String proxy, int port, String dominio,
			boolean useNTLM) {
		this.proxy = proxy;
		this.port = port;
		this.dominio = dominio;
		this.useNTLM = useNTLM;
	}
	
	public ProxyConfiguration(String proxy, int port, String dominio, String username,
			char[] password,
			boolean useNTLM, boolean useScript, String script) {
		this.proxy = proxy;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dominio = dominio;
		this.useNTLM = useNTLM;
		this.useScript = useScript;
		this.script = script;
	}
	
	public ProxyConfiguration() {
		// TODO Auto-generated constructor stub
	}
	public void setUseScript(boolean useScript) {
		this.useScript = useScript;
	}
	public boolean isUseScript() {
		return useScript;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getScript() {
		return script;
	}
	
		
}
