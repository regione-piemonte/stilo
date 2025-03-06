package it.eng.server;

import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.common.type.SignerType;
import it.eng.common.type.TabType;

import java.util.List;

/**
 * Bean che contiene tutti i dati che servono per la firma 
 * @author Administrator
 *
 */
public class SignerSessionBean {

	private HashAlgorithm digest;
	private SignerType signerType;
	private SignerType[] signerTypes;
	private List<FileElaborate> files;
	private TabType[] tabsView = new TabType[]{TabType.FIRMA,TabType.DRIVER,TabType.CONFIGURAZIONE};
	private boolean debug = false;
	private boolean verifyCRL = true;
	
	public boolean isVerifyCRL() {
		return verifyCRL;
	}
	public void setVerifyCRL(boolean verifyCRL) {
		this.verifyCRL = verifyCRL;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public TabType[] getTabsView() {
		return tabsView;
	}
	public void setTabsView(TabType[] tabsView) {
		this.tabsView = tabsView;
	}
	public HashAlgorithm getDigest() {
		return digest;
	}
	public void setDigest(HashAlgorithm digest) {
		this.digest = digest;
	}
	public SignerType getSignerType() {
		return signerType;
	}
	public void setSignerType(SignerType signerType) {
		this.signerType = signerType;
	}
	public SignerType[] getSignerTypes() {
		return signerTypes;
	}
	public void setSignerTypes(SignerType[] signerTypes) {
		this.signerTypes = signerTypes;
	}
	public List<FileElaborate> getFiles() {
		return files;
	}
	public void setFiles(List<FileElaborate> files) {
		this.files = files;
	}
	
	public String getDebug(){
		if(debug){
			return "1";
		}else{
			return "0";
		}
	}
	
	
}
