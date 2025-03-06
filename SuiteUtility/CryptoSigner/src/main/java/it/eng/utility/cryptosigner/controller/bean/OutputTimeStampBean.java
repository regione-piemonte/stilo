package it.eng.utility.cryptosigner.controller.bean;

import it.eng.utility.cryptosigner.data.AbstractSigner;

import java.util.List;
import java.util.Map;

import org.bouncycastle.tsp.TimeStampToken;

public class OutputTimeStampBean extends OutputBean{

	Map<byte[],TimeStampToken> mapSignatureTimeStampTokens;
	List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos;
	
	AbstractSigner signer;
	
	/**
	 * @return the documentAndTimeStampInfos
	 */
	public List<DocumentAndTimeStampInfoBean> getDocumentAndTimeStampInfos() {
		return documentAndTimeStampInfos;
	}

	/**
	 * @param documentAndTimeStampInfos the documentAndTimeStampInfos to set
	 */
	public void setDocumentAndTimeStampInfos(
			List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos) {
		this.documentAndTimeStampInfos = documentAndTimeStampInfos;
	}

	/**
	 * @return the signer
	 */
	public AbstractSigner getSigner() {
		return signer;
	}

	/**
	 * @param signer the signer to set
	 */
	public void setSigner(AbstractSigner signer) {
		this.signer = signer;
	}

	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens() {
		return mapSignatureTimeStampTokens;
	}

	public void setMapSignatureTimeStampTokens(
			Map<byte[], TimeStampToken> mapSignatureTimeStampTokens) {
		this.mapSignatureTimeStampTokens = mapSignatureTimeStampTokens;
	}
	
}
