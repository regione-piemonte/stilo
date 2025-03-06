package it.eng.client.applet.bean;

import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.common.type.SignerType;

import java.util.List;

import org.bouncycastle.crypto.tls.DigestAlgorithm;

/**
 * non più utilizzato si usano le preference salvate nel 
 * <link>PreferenceManager</link>
 *@deprecated
 *
 */
public class SmartCardConfigBean {

	private SignerType signerType = SignerType.P7M;
	private List<SignerType> signerTypeList;
	private HashAlgorithm digest = HashAlgorithm.SHA1;
	

	public SignerType getSignerType() {
		return signerType;
	}
	public void setSignerType(SignerType signerType) {
		this.signerType = signerType;
	}
	public HashAlgorithm getDigest() {
		return digest;
	}
	public void setDigest(HashAlgorithm digest) {
		this.digest = digest;
	}
	public List<SignerType> getSignerTypeList() {
		return signerTypeList;
	}
	public void setSignerTypeList(List<SignerType> signerTypeList) {
		this.signerTypeList = signerTypeList;
	}
}