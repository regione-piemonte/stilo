/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSAttributeTableGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;

public class CustomJcaSignerInfoGeneratorBuilder {

	private SignerInfoGeneratorBuilder builder;

	public CustomJcaSignerInfoGeneratorBuilder(DigestCalculatorProvider digestProvider) {
		builder = new SignerInfoGeneratorBuilder(digestProvider);
	}

	/**
	 * If the passed in flag is true, the signer signature will be based on the data, not a collection of signed attributes, and no signed attributes will be
	 * included.
	 *
	 * @return the builder object
	 */
	public CustomJcaSignerInfoGeneratorBuilder setDirectSignature(boolean hasNoSignedAttributes) {
		builder.setDirectSignature(hasNoSignedAttributes);
		return this;
	}

	public CustomJcaSignerInfoGeneratorBuilder setSignedAttributeGenerator(CMSAttributeTableGenerator signedGen) {
		builder.setSignedAttributeGenerator(signedGen);
		return this;
	}

	public CustomJcaSignerInfoGeneratorBuilder setUnsignedAttributeGenerator(CMSAttributeTableGenerator unsignedGen) {
		builder.setUnsignedAttributeGenerator(unsignedGen);
		return this;
	}

	public SignerInfoGenerator build(ContentSigner contentSigner, X509CertificateHolder certHolder) throws OperatorCreationException {
		return builder.build(contentSigner, certHolder);
	}

	public SignerInfoGenerator build(ContentSigner contentSigner, byte[] keyIdentifier) throws OperatorCreationException {
		return builder.build(contentSigner, keyIdentifier);
	}

	public SignerInfoGenerator build(ContentSigner contentSigner, X509Certificate certificate) throws OperatorCreationException, CertificateEncodingException {
		return this.build(contentSigner, new JcaX509CertificateHolder(certificate));
	}
}