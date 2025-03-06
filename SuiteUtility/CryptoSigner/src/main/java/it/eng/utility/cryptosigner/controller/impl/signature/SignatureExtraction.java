package it.eng.utility.cryptosigner.controller.impl.signature;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.cms.CMSException;

import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.signature.ISignature;

/**
 * Recupera il contenuto della busta tramite la chiamata al metodo {@link it.eng.utility.cryptosigner.data.AbstractSigner#getContentAsFile getContentAsFile} del
 * signer ad esso associato.
 * 
 * @author Stefano Zennaro
 *
 */
public class SignatureExtraction extends AbstractSignerController {

	private static Logger log = LogManager.getLogger(SignatureExtraction.class);

	private ASN1Primitive getSingleValuedSignedAttribute(AttributeTable unsignedAttrTable, AttributeTable signedAttrTable, ASN1ObjectIdentifier attrOID,
			String printableName) throws CMSException {
		if (unsignedAttrTable != null && unsignedAttrTable.getAll(attrOID).size() > 0) {
			throw new CMSException("The " + printableName + " attribute MUST NOT be an unsigned attribute");
		}

		if (signedAttrTable == null) {
			return null;
		}

		ASN1EncodableVector v = signedAttrTable.getAll(attrOID);
		switch (v.size()) {
		case 0:
			return null;
		case 1: {
			Attribute t = (Attribute) v.get(0);
			ASN1Set attrValues = t.getAttrValues();
			if (attrValues.size() != 1) {
				throw new CMSException("A " + printableName + " attribute MUST have a single attribute value");
			}

			return attrValues.getObjectAt(0).toASN1Primitive();
		}
		default:
			throw new CMSException("The SignedAttributes in a signerInfo MUST NOT include multiple instances of the " + printableName + " attribute");
		}
	}

	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {

		AbstractSigner signer = input.getSigner();
		List<ISignature> signatures = signer.getSignatures( input.getEnvelope() );
		output.setProperty(OutputSignerBean.SIGNATURE_PROPERTY, signatures);

		return signatures != null && !signatures.isEmpty();
	}

}
