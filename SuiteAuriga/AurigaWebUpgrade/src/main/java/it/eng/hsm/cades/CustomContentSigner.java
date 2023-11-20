/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorStreamException;

import it.eng.hsm.HsmSignOptionFactory;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientRuntimeOperatorException;
import it.eng.hsm.client.exception.HsmClientSignatureException;

public class CustomContentSigner implements ContentSigner {

	private static final Logger log = Logger.getLogger(CustomContentSigner.class);

	private AlgorithmIdentifier i;
	private SignatureOutputStream j;
	String alg;

	byte[] certEncoded;

	private Hsm clientHsm;

	public CustomContentSigner(byte[] certEncoded, String alg, Hsm clientHsm) throws NoSuchAlgorithmException {
		this.certEncoded = certEncoded;
		this.i = new DefaultSignatureAlgorithmIdentifierFinder().find(alg);
		this.alg = alg;
		this.clientHsm = clientHsm;

		String digestAlg = "SHA1";
		if (this.alg.startsWith("SHA256")) {
			digestAlg = "SHA256";
		}
		this.j = new SignatureOutputStream(digestAlg);
	}

	// public CustomContentSigner( byte[] certEncoded, String alg, Hsm clientHsm, String otp, String certificateSerialNumber) throws NoSuchAlgorithmException {
	// this.certEncoded = certEncoded;
	// this.i = new DefaultSignatureAlgorithmIdentifierFinder().find(alg);
	// this.alg = alg;
	// this.clientHsm = clientHsm;
	// this.otp = otp;
	// this.certificateSerialNumber = certificateSerialNumber;
	//
	// String digestAlg = "SHA1";
	// if (this.alg.startsWith("SHA256")) {
	// digestAlg = "SHA256";
	// }
	// this.j = new SignatureOutputStream(digestAlg);
	// }

	public AlgorithmIdentifier getAlgorithmIdentifier() {
		return this.i;
	}

	public OutputStream getOutputStream() {
		return this.j;
	}

	public byte[] getSignature() {
		String msg;
		try {
			return this.j.getSignature();

		} catch (UnsupportedOperationException e) {
			msg = e.getMessage();
			Throwable t = e.getCause();
			if (t != null)
				msg = t.getMessage();
			throw new HsmClientRuntimeOperatorException("Errore di firma: " + msg, e);

		} catch (HsmClientSignatureException e) {
			msg = e.getMessage();
			Throwable t = e.getCause();
			if (t != null)
				msg = t.getMessage();
			throw new HsmClientRuntimeOperatorException("Errore di firma: " + msg, e);

		} catch (HsmClientConfigException e) {
			msg = e.getMessage();
			Throwable t = e.getCause();
			if (t != null)
				msg = t.getMessage();
			throw new HsmClientRuntimeOperatorException("Errore di firma: " + msg, e);
		}
	}

	private class SignatureOutputStream extends OutputStream {

		private MessageDigest _md;
		private String _digestAlg;

		SignatureOutputStream(String digestAlg) throws NoSuchAlgorithmException {
			this._md = MessageDigest.getInstance(digestAlg);
			this._digestAlg = digestAlg;
		}

		@Override
		public void write(byte[] bytes, int off, int len) throws IOException {
			try {
				this._md.update(bytes, off, len);
			} catch (Exception e) {
				throw new OperatorStreamException("exception in content signer: " + e.getMessage(), e);
			}
		}

		@Override
		public void write(byte[] bytes) throws IOException {
			try {
				this._md.update(bytes);
			} catch (Exception e) {
				throw new OperatorStreamException("exception in content signer: " + e.getMessage(), e);
			}
		}

		@Override
		public void write(int b) throws IOException {
			try {
				this._md.update((byte) b);
			} catch (Exception e) {
				throw new OperatorStreamException("exception in content signer: " + e.getMessage(), e);
			}
		}

		byte[] getSignature() throws UnsupportedOperationException, HsmClientConfigException, HsmClientSignatureException {

			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			byte[] digest = this._md.digest();

			List<HashRequestBean> listaHashDaFirmare = new ArrayList<HashRequestBean>();
			HashRequestBean hashRequestBean = new HashRequestBean();
			hashRequestBean.setHash(Base64.encodeBase64String(digest));
			hashRequestBean.setSignOption(HsmSignOptionFactory.getSignOption(clientHsm.getHsmConfig().getHsmType()));
			listaHashDaFirmare.add(hashRequestBean);

			// clientHsm.getHsmConfig().getClientConfig().getSession()

			SignResponseBean response = null;

			// Verifico se devo effettuare le firme in sessione
			if (clientHsm.getHsmConfig().getClientConfig().isRequireSignatureInSession()) {
				// Eseguo la firma multipla hash in sessione. La sessione è già stata aperta al momento della richiesta dei certificati
				String idSession = clientHsm.getHsmConfig().getClientConfig().getIdSession();
				response = clientHsm.firmaMultiplaHashInSession(listaHashDaFirmare, idSession);
			} else {
				// Eseguo la firma multipla hash normale
				response = clientHsm.firmaMultiplaHash(listaHashDaFirmare);
			}

			MessageBean message = response.getMessage();
			if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
				log.error("Errore: - " + message.getCode() + " " + message.getDescription());
				throw new HsmClientSignatureException("Errore nella firma: " + message.getDescription());
			}
			List<HashResponseBean> listHashResponseBean = response.getHashResponseBean();
			if ((listHashResponseBean != null) && (listHashResponseBean.size() >= 1) && (listHashResponseBean.get(0).getHashFirmata() != null)) {
				return listHashResponseBean.get(0).getHashFirmata();
			} else {
				String errorMessage = "Errore nella firma remota";
				if ((message != null) && (message.getDescription() != null)) {
					errorMessage += ". " + message.getDescription();
				}
				if ((listHashResponseBean != null) && (listHashResponseBean.size() >= 1)) {
					for (HashResponseBean bean : response.getHashResponseBean()){
						if (bean.getMessage() != null){
							MessageBean messageElemento = bean.getMessage();
							errorMessage += "\n" + "Codice errore: " + messageElemento.getCode() + " Descrizione errore: " +  messageElemento.getDescription();
						}
					}	
				}
				throw new HsmClientSignatureException(errorMessage);
			}
		}
	}
}