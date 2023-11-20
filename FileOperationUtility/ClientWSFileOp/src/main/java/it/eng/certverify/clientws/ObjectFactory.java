/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.certverify.clientws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.certverify.clientws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CertificateVerifierRequest_QNAME = new QName("verify.cryptoutil.eng.it", "certificateVerifierRequest");
    private final static QName _CertificateVerifierResponse_QNAME = new QName("verify.cryptoutil.eng.it", "CertificateVerifierResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.certverify.clientws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CertificateVerifierRequest }
     * 
     */
    public CertificateVerifierRequest createCertificateVerifierRequest() {
        return new CertificateVerifierRequest();
    }

    /**
     * Create an instance of {@link VerificationResultType.Warnings }
     * 
     */
    public VerificationResultType.Warnings createVerificationResultTypeWarnings() {
        return new VerificationResultType.Warnings();
    }

    /**
     * Create an instance of {@link VerificationResults }
     * 
     */
    public VerificationResults createVerificationResults() {
        return new VerificationResults();
    }

    /**
     * Create an instance of {@link VerificationResultType }
     * 
     */
    public VerificationResultType createVerificationResultType() {
        return new VerificationResultType();
    }

    /**
     * Create an instance of {@link CertificateVerifierResponse }
     * 
     */
    public CertificateVerifierResponse createCertificateVerifierResponse() {
        return new CertificateVerifierResponse();
    }

    /**
     * Create an instance of {@link VerificationRequest }
     * 
     */
    public VerificationRequest createVerificationRequest() {
        return new VerificationRequest();
    }

    /**
     * Create an instance of {@link VerificationInfo }
     * 
     */
    public VerificationInfo createVerificationInfo() {
        return new VerificationInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateVerifierRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "verify.cryptoutil.eng.it", name = "certificateVerifierRequest")
    public JAXBElement<CertificateVerifierRequest> createCertificateVerifierRequest(CertificateVerifierRequest value) {
        return new JAXBElement<CertificateVerifierRequest>(_CertificateVerifierRequest_QNAME, CertificateVerifierRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateVerifierResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "verify.cryptoutil.eng.it", name = "CertificateVerifierResponse")
    public JAXBElement<CertificateVerifierResponse> createCertificateVerifierResponse(CertificateVerifierResponse value) {
        return new JAXBElement<CertificateVerifierResponse>(_CertificateVerifierResponse_QNAME, CertificateVerifierResponse.class, null, value);
    }

}
