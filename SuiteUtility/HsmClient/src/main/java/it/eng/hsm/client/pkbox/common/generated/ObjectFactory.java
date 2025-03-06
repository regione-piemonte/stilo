
package it.eng.hsm.client.pkbox.common.generated;

import javax.xml.bind.JAXBElement;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;



/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.hsm.client.pkbox.env.generated package. 
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

    private final static QName _TimeStampExSigner_QNAME = new QName("http://server.pkbox.it/xsd", "signer");
    private final static QName _SignerExCertificateID_QNAME = new QName("http://server.pkbox.it/xsd", "certificateID");
    private final static QName _SignerTimeStamp_QNAME = new QName("http://server.pkbox.it/xsd", "timeStamp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.hsm.client.pkbox.envelope.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Signer }
     * 
     */
    public Signer createSigner() {
        return new Signer();
    }

    /**
     * Create an instance of {@link CertificateValue }
     * 
     */
    public CertificateValue createCertificateValue() {
        return new CertificateValue();
    }


    /**
     * Create an instance of {@link CertificateID }
     * 
     */
    public CertificateID createCertificateID() {
        return new CertificateID();
    }

    /**
     * Create an instance of {@link CertificatePolicy }
     * 
     */
    public CertificatePolicy createCertificatePolicy() {
        return new CertificatePolicy();
    }

    /**
     * Create an instance of {@link PolicyInfo }
     * 
     */
    public PolicyInfo createPolicyInfo() {
        return new PolicyInfo();
    }



    /**
     * Create an instance of {@link TimeStampEx }
     * 
     */
    public TimeStampEx createTimeStampEx() {
        return new TimeStampEx();
    }

    /**
     * Create an instance of {@link SignerEx }
     * 
     */
    public SignerEx createSignerEx() {
        return new SignerEx();
    }


    /**
     * Create an instance of {@link TimeStamp }
     * 
     */
    public TimeStamp createTimeStamp() {
        return new TimeStamp();
    }



    /**
     * Create an instance of {@link PKBoxException2 }
     * 
     */
    public PKBoxException2 createPKBoxException2() {
        return new PKBoxException2();
    }

    
   

    /**
     * Create an instance of {@link ByteArray }
     * 
     */
    public ByteArray createByteArray() {
        return new ByteArray();
    }

   

    

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignerEx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.pkbox.it/xsd", name = "signer", scope = TimeStampEx.class)
    public JAXBElement<SignerEx> createTimeStampExSigner(SignerEx value) {
        return new JAXBElement<SignerEx>(_TimeStampExSigner_QNAME, SignerEx.class, TimeStampEx.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.pkbox.it/xsd", name = "certificateID", scope = SignerEx.class)
    public JAXBElement<CertificateID> createSignerExCertificateID(CertificateID value) {
        return new JAXBElement<CertificateID>(_SignerExCertificateID_QNAME, CertificateID.class, SignerEx.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Signer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.pkbox.it/xsd", name = "signer", scope = TimeStamp.class)
    public JAXBElement<Signer> createTimeStampSigner(Signer value) {
        return new JAXBElement<Signer>(_TimeStampExSigner_QNAME, Signer.class, TimeStamp.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.pkbox.it/xsd", name = "certificateID", scope = Signer.class)
    public JAXBElement<CertificateID> createSignerCertificateID(CertificateID value) {
        return new JAXBElement<CertificateID>(_SignerExCertificateID_QNAME, CertificateID.class, Signer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeStamp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.pkbox.it/xsd", name = "timeStamp", scope = Signer.class)
    public JAXBElement<TimeStamp> createSignerTimeStamp(TimeStamp value) {
        return new JAXBElement<TimeStamp>(_SignerTimeStamp_QNAME, TimeStamp.class, Signer.class, value);
    }

}
