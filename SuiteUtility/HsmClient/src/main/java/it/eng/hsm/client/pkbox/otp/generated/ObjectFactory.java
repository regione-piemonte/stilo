
package it.eng.hsm.client.pkbox.otp.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.hsm.client.pkbox.otp.generated package. 
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

    private final static QName _IsCorrectOTPByOTPResponse_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "isCorrectOTPByOTPResponse");
    private final static QName _Version_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "version");
    private final static QName _SendOtpByAlias_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "sendOtpByAlias");
    private final static QName _SendOtpResponse_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "sendOtpResponse");
    private final static QName _NCFRWSException_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "NCFRWSException");
    private final static QName _SendOtpByAliasResponse_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "sendOtpByAliasResponse");
    private final static QName _IsCorrectOTPByOTPByAlias_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "isCorrectOTPByOTPByAlias");
    private final static QName _IsCorrectOTPByOTP_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "isCorrectOTPByOTP");
    private final static QName _SendOtp_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "sendOtp");
    private final static QName _VersionResponse_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "versionResponse");
    private final static QName _IsCorrectOTPByOTPByAliasResponse_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "isCorrectOTPByOTPByAliasResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.hsm.client.pkbox.otp.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IsCorrectOTPByOTPByAliasResponse }
     * 
     */
    public IsCorrectOTPByOTPByAliasResponse createIsCorrectOTPByOTPByAliasResponse() {
        return new IsCorrectOTPByOTPByAliasResponse();
    }

    /**
     * Create an instance of {@link IsCorrectOTPByOTP }
     * 
     */
    public IsCorrectOTPByOTP createIsCorrectOTPByOTP() {
        return new IsCorrectOTPByOTP();
    }

    /**
     * Create an instance of {@link SendOtp }
     * 
     */
    public SendOtp createSendOtp() {
        return new SendOtp();
    }

    /**
     * Create an instance of {@link VersionResponse }
     * 
     */
    public VersionResponse createVersionResponse() {
        return new VersionResponse();
    }

    /**
     * Create an instance of {@link IsCorrectOTPByOTPByAlias }
     * 
     */
    public IsCorrectOTPByOTPByAlias createIsCorrectOTPByOTPByAlias() {
        return new IsCorrectOTPByOTPByAlias();
    }

    /**
     * Create an instance of {@link SendOtpByAliasResponse }
     * 
     */
    public SendOtpByAliasResponse createSendOtpByAliasResponse() {
        return new SendOtpByAliasResponse();
    }

    /**
     * Create an instance of {@link NCFRWSException }
     * 
     */
    public NCFRWSException createNCFRWSException() {
        return new NCFRWSException();
    }

    /**
     * Create an instance of {@link SendOtpResponse }
     * 
     */
    public SendOtpResponse createSendOtpResponse() {
        return new SendOtpResponse();
    }

    /**
     * Create an instance of {@link SendOtpByAlias }
     * 
     */
    public SendOtpByAlias createSendOtpByAlias() {
        return new SendOtpByAlias();
    }

    /**
     * Create an instance of {@link Version }
     * 
     */
    public Version createVersion() {
        return new Version();
    }

    /**
     * Create an instance of {@link IsCorrectOTPByOTPResponse }
     * 
     */
    public IsCorrectOTPByOTPResponse createIsCorrectOTPByOTPResponse() {
        return new IsCorrectOTPByOTPResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsCorrectOTPByOTPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "isCorrectOTPByOTPResponse")
    public JAXBElement<IsCorrectOTPByOTPResponse> createIsCorrectOTPByOTPResponse(IsCorrectOTPByOTPResponse value) {
        return new JAXBElement<IsCorrectOTPByOTPResponse>(_IsCorrectOTPByOTPResponse_QNAME, IsCorrectOTPByOTPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Version }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "version")
    public JAXBElement<Version> createVersion(Version value) {
        return new JAXBElement<Version>(_Version_QNAME, Version.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOtpByAlias }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "sendOtpByAlias")
    public JAXBElement<SendOtpByAlias> createSendOtpByAlias(SendOtpByAlias value) {
        return new JAXBElement<SendOtpByAlias>(_SendOtpByAlias_QNAME, SendOtpByAlias.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "sendOtpResponse")
    public JAXBElement<SendOtpResponse> createSendOtpResponse(SendOtpResponse value) {
        return new JAXBElement<SendOtpResponse>(_SendOtpResponse_QNAME, SendOtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NCFRWSException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "NCFRWSException")
    public JAXBElement<NCFRWSException> createNCFRWSException(NCFRWSException value) {
        return new JAXBElement<NCFRWSException>(_NCFRWSException_QNAME, NCFRWSException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOtpByAliasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "sendOtpByAliasResponse")
    public JAXBElement<SendOtpByAliasResponse> createSendOtpByAliasResponse(SendOtpByAliasResponse value) {
        return new JAXBElement<SendOtpByAliasResponse>(_SendOtpByAliasResponse_QNAME, SendOtpByAliasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsCorrectOTPByOTPByAlias }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "isCorrectOTPByOTPByAlias")
    public JAXBElement<IsCorrectOTPByOTPByAlias> createIsCorrectOTPByOTPByAlias(IsCorrectOTPByOTPByAlias value) {
        return new JAXBElement<IsCorrectOTPByOTPByAlias>(_IsCorrectOTPByOTPByAlias_QNAME, IsCorrectOTPByOTPByAlias.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsCorrectOTPByOTP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "isCorrectOTPByOTP")
    public JAXBElement<IsCorrectOTPByOTP> createIsCorrectOTPByOTP(IsCorrectOTPByOTP value) {
        return new JAXBElement<IsCorrectOTPByOTP>(_IsCorrectOTPByOTP_QNAME, IsCorrectOTPByOTP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendOtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "sendOtp")
    public JAXBElement<SendOtp> createSendOtp(SendOtp value) {
        return new JAXBElement<SendOtp>(_SendOtp_QNAME, SendOtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "versionResponse")
    public JAXBElement<VersionResponse> createVersionResponse(VersionResponse value) {
        return new JAXBElement<VersionResponse>(_VersionResponse_QNAME, VersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsCorrectOTPByOTPByAliasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://otp.webservice.ncfr.infocert.it/", name = "isCorrectOTPByOTPByAliasResponse")
    public JAXBElement<IsCorrectOTPByOTPByAliasResponse> createIsCorrectOTPByOTPByAliasResponse(IsCorrectOTPByOTPByAliasResponse value) {
        return new JAXBElement<IsCorrectOTPByOTPByAliasResponse>(_IsCorrectOTPByOTPByAliasResponse_QNAME, IsCorrectOTPByOTPByAliasResponse.class, null, value);
    }

}
