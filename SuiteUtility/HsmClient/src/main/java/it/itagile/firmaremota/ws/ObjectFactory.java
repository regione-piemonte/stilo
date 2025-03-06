
package it.itagile.firmaremota.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.itagile.firmaremota.ws package. 
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

    private final static QName _Fault_QNAME = new QName("http://ws.firmaremota.itagile.it", "fault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.itagile.firmaremota.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddUser }
     * 
     */
    public AddUser createAddUser() {
        return new AddUser();
    }

    /**
     * Create an instance of {@link RemoteSignatureCredentials }
     * 
     */
    public RemoteSignatureCredentials createRemoteSignatureCredentials() {
        return new RemoteSignatureCredentials();
    }

    /**
     * Create an instance of {@link AddUserResponse }
     * 
     */
    public AddUserResponse createAddUserResponse() {
        return new AddUserResponse();
    }

    /**
     * Create an instance of {@link ChangePassword }
     * 
     */
    public ChangePassword createChangePassword() {
        return new ChangePassword();
    }

    /**
     * Create an instance of {@link ChangePasswordResponse }
     * 
     */
    public ChangePasswordResponse createChangePasswordResponse() {
        return new ChangePasswordResponse();
    }

    /**
     * Create an instance of {@link ChangeUserPassword }
     * 
     */
    public ChangeUserPassword createChangeUserPassword() {
        return new ChangeUserPassword();
    }

    /**
     * Create an instance of {@link ChangeUserPasswordResponse }
     * 
     */
    public ChangeUserPasswordResponse createChangeUserPasswordResponse() {
        return new ChangeUserPasswordResponse();
    }

    /**
     * Create an instance of {@link CloseSignatureSession }
     * 
     */
    public CloseSignatureSession createCloseSignatureSession() {
        return new CloseSignatureSession();
    }

    /**
     * Create an instance of {@link CloseSignatureSessionResponse }
     * 
     */
    public CloseSignatureSessionResponse createCloseSignatureSessionResponse() {
        return new CloseSignatureSessionResponse();
    }

    /**
     * Create an instance of {@link DeleteUser }
     * 
     */
    public DeleteUser createDeleteUser() {
        return new DeleteUser();
    }

    /**
     * Create an instance of {@link DeleteUserResponse }
     * 
     */
    public DeleteUserResponse createDeleteUserResponse() {
        return new DeleteUserResponse();
    }

    /**
     * Create an instance of {@link Digest }
     * 
     */
    public Digest createDigest() {
        return new Digest();
    }

    /**
     * Create an instance of {@link DigestResponse }
     * 
     */
    public DigestResponse createDigestResponse() {
        return new DigestResponse();
    }

    /**
     * Create an instance of {@link DocumentP7MInfo }
     * 
     */
    public DocumentP7MInfo createDocumentP7MInfo() {
        return new DocumentP7MInfo();
    }

    /**
     * Create an instance of {@link DocumentP7MInfoResponse }
     * 
     */
    public DocumentP7MInfoResponse createDocumentP7MInfoResponse() {
        return new DocumentP7MInfoResponse();
    }

    /**
     * Create an instance of {@link SignatureDocumentInfo }
     * 
     */
    public SignatureDocumentInfo createSignatureDocumentInfo() {
        return new SignatureDocumentInfo();
    }

    /**
     * Create an instance of {@link DocumentPdfInfo }
     * 
     */
    public DocumentPdfInfo createDocumentPdfInfo() {
        return new DocumentPdfInfo();
    }

    /**
     * Create an instance of {@link DocumentPdfInfoResponse }
     * 
     */
    public DocumentPdfInfoResponse createDocumentPdfInfoResponse() {
        return new DocumentPdfInfoResponse();
    }

    /**
     * Create an instance of {@link DocumentXadesInfo }
     * 
     */
    public DocumentXadesInfo createDocumentXadesInfo() {
        return new DocumentXadesInfo();
    }

    /**
     * Create an instance of {@link DocumentXadesInfoResponse }
     * 
     */
    public DocumentXadesInfoResponse createDocumentXadesInfoResponse() {
        return new DocumentXadesInfoResponse();
    }

    /**
     * Create an instance of {@link RemoteSignatureException }
     * 
     */
    public RemoteSignatureException createRemoteSignatureException() {
        return new RemoteSignatureException();
    }

    /**
     * Create an instance of {@link FindUser }
     * 
     */
    public FindUser createFindUser() {
        return new FindUser();
    }

    /**
     * Create an instance of {@link FindUserResponse }
     * 
     */
    public FindUserResponse createFindUserResponse() {
        return new FindUserResponse();
    }

    /**
     * Create an instance of {@link UserInfo }
     * 
     */
    public UserInfo createUserInfo() {
        return new UserInfo();
    }

    /**
     * Create an instance of {@link GetCertificates }
     * 
     */
    public GetCertificates createGetCertificates() {
        return new GetCertificates();
    }

    /**
     * Create an instance of {@link GetCertificatesResponse }
     * 
     */
    public GetCertificatesResponse createGetCertificatesResponse() {
        return new GetCertificatesResponse();
    }

    /**
     * Create an instance of {@link GetUserImages }
     * 
     */
    public GetUserImages createGetUserImages() {
        return new GetUserImages();
    }

    /**
     * Create an instance of {@link GetUserImagesResponse }
     * 
     */
    public GetUserImagesResponse createGetUserImagesResponse() {
        return new GetUserImagesResponse();
    }

    /**
     * Create an instance of {@link SignatureImage }
     * 
     */
    public SignatureImage createSignatureImage() {
        return new SignatureImage();
    }

    /**
     * Create an instance of {@link GetUsersDirectoryType }
     * 
     */
    public GetUsersDirectoryType createGetUsersDirectoryType() {
        return new GetUsersDirectoryType();
    }

    /**
     * Create an instance of {@link GetUsersDirectoryTypeResponse }
     * 
     */
    public GetUsersDirectoryTypeResponse createGetUsersDirectoryTypeResponse() {
        return new GetUsersDirectoryTypeResponse();
    }

    /**
     * Create an instance of {@link IsModuleActive }
     * 
     */
    public IsModuleActive createIsModuleActive() {
        return new IsModuleActive();
    }

    /**
     * Create an instance of {@link IsModuleActiveResponse }
     * 
     */
    public IsModuleActiveResponse createIsModuleActiveResponse() {
        return new IsModuleActiveResponse();
    }

    /**
     * Create an instance of {@link OpenSignatureSession }
     * 
     */
    public OpenSignatureSession createOpenSignatureSession() {
        return new OpenSignatureSession();
    }

    /**
     * Create an instance of {@link OpenSignatureSessionResponse }
     * 
     */
    public OpenSignatureSessionResponse createOpenSignatureSessionResponse() {
        return new OpenSignatureSessionResponse();
    }

    /**
     * Create an instance of {@link SetUserImage }
     * 
     */
    public SetUserImage createSetUserImage() {
        return new SetUserImage();
    }

    /**
     * Create an instance of {@link SetUserImageResponse }
     * 
     */
    public SetUserImageResponse createSetUserImageResponse() {
        return new SetUserImageResponse();
    }

    /**
     * Create an instance of {@link SignCAdES }
     * 
     */
    public SignCAdES createSignCAdES() {
        return new SignCAdES();
    }

    /**
     * Create an instance of {@link SignatureFlags }
     * 
     */
    public SignatureFlags createSignatureFlags() {
        return new SignatureFlags();
    }

    /**
     * Create an instance of {@link SignCAdESResponse }
     * 
     */
    public SignCAdESResponse createSignCAdESResponse() {
        return new SignCAdESResponse();
    }

    /**
     * Create an instance of {@link SignP7M }
     * 
     */
    public SignP7M createSignP7M() {
        return new SignP7M();
    }

    /**
     * Create an instance of {@link SignP7MResponse }
     * 
     */
    public SignP7MResponse createSignP7MResponse() {
        return new SignP7MResponse();
    }

    /**
     * Create an instance of {@link SignPDF }
     * 
     */
    public SignPDF createSignPDF() {
        return new SignPDF();
    }

    /**
     * Create an instance of {@link SignPDFPath }
     * 
     */
    public SignPDFPath createSignPDFPath() {
        return new SignPDFPath();
    }

    /**
     * Create an instance of {@link SignPDFPathResponse }
     * 
     */
    public SignPDFPathResponse createSignPDFPathResponse() {
        return new SignPDFPathResponse();
    }

    /**
     * Create an instance of {@link SignPDFResponse }
     * 
     */
    public SignPDFResponse createSignPDFResponse() {
        return new SignPDFResponse();
    }

    /**
     * Create an instance of {@link SignPKCS1 }
     * 
     */
    public SignPKCS1 createSignPKCS1() {
        return new SignPKCS1();
    }

    /**
     * Create an instance of {@link SignPKCS1Array }
     * 
     */
    public SignPKCS1Array createSignPKCS1Array() {
        return new SignPKCS1Array();
    }

    /**
     * Create an instance of {@link SignPKCS1ArrayResponse }
     * 
     */
    public SignPKCS1ArrayResponse createSignPKCS1ArrayResponse() {
        return new SignPKCS1ArrayResponse();
    }

    /**
     * Create an instance of {@link SignPKCS1Response }
     * 
     */
    public SignPKCS1Response createSignPKCS1Response() {
        return new SignPKCS1Response();
    }

    /**
     * Create an instance of {@link SignXAdES }
     * 
     */
    public SignXAdES createSignXAdES() {
        return new SignXAdES();
    }

    /**
     * Create an instance of {@link RemoteSignatureXadesParams }
     * 
     */
    public RemoteSignatureXadesParams createRemoteSignatureXadesParams() {
        return new RemoteSignatureXadesParams();
    }

    /**
     * Create an instance of {@link SignXAdESResponse }
     * 
     */
    public SignXAdESResponse createSignXAdESResponse() {
        return new SignXAdESResponse();
    }

    /**
     * Create an instance of {@link TimestampAttached }
     * 
     */
    public TimestampAttached createTimestampAttached() {
        return new TimestampAttached();
    }

    /**
     * Create an instance of {@link TimestampAttachedResponse }
     * 
     */
    public TimestampAttachedResponse createTimestampAttachedResponse() {
        return new TimestampAttachedResponse();
    }

    /**
     * Create an instance of {@link TimestampDetached }
     * 
     */
    public TimestampDetached createTimestampDetached() {
        return new TimestampDetached();
    }

    /**
     * Create an instance of {@link TimestampDetachedResponse }
     * 
     */
    public TimestampDetachedResponse createTimestampDetachedResponse() {
        return new TimestampDetachedResponse();
    }

    /**
     * Create an instance of {@link TimestampResponse }
     * 
     */
    public TimestampResponse createTimestampResponse() {
        return new TimestampResponse();
    }

    /**
     * Create an instance of {@link TimestampResponseResponse }
     * 
     */
    public TimestampResponseResponse createTimestampResponseResponse() {
        return new TimestampResponseResponse();
    }

    /**
     * Create an instance of {@link TimestampToken }
     * 
     */
    public TimestampToken createTimestampToken() {
        return new TimestampToken();
    }

    /**
     * Create an instance of {@link TimestampTokenResponse }
     * 
     */
    public TimestampTokenResponse createTimestampTokenResponse() {
        return new TimestampTokenResponse();
    }

    /**
     * Create an instance of {@link VerifyCertificate }
     * 
     */
    public VerifyCertificate createVerifyCertificate() {
        return new VerifyCertificate();
    }

    /**
     * Create an instance of {@link VerifyCertificateAtTime }
     * 
     */
    public VerifyCertificateAtTime createVerifyCertificateAtTime() {
        return new VerifyCertificateAtTime();
    }

    /**
     * Create an instance of {@link VerifyCertificateAtTimeResponse }
     * 
     */
    public VerifyCertificateAtTimeResponse createVerifyCertificateAtTimeResponse() {
        return new VerifyCertificateAtTimeResponse();
    }

    /**
     * Create an instance of {@link CertificateStatus }
     * 
     */
    public CertificateStatus createCertificateStatus() {
        return new CertificateStatus();
    }

    /**
     * Create an instance of {@link VerifyCertificateResponse }
     * 
     */
    public VerifyCertificateResponse createVerifyCertificateResponse() {
        return new VerifyCertificateResponse();
    }

    /**
     * Create an instance of {@link VerifySignatures }
     * 
     */
    public VerifySignatures createVerifySignatures() {
        return new VerifySignatures();
    }

    /**
     * Create an instance of {@link VerifySignaturesResponse }
     * 
     */
    public VerifySignaturesResponse createVerifySignaturesResponse() {
        return new VerifySignaturesResponse();
    }

    /**
     * Create an instance of {@link SignatureStatus }
     * 
     */
    public SignatureStatus createSignatureStatus() {
        return new SignatureStatus();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link SignatureField }
     * 
     */
    public SignatureField createSignatureField() {
        return new SignatureField();
    }

    /**
     * Create an instance of {@link ArrayOfPosition }
     * 
     */
    public ArrayOfPosition createArrayOfPosition() {
        return new ArrayOfPosition();
    }

    /**
     * Create an instance of {@link ClientCertificateAuth }
     * 
     */
    public ClientCertificateAuth createClientCertificateAuth() {
        return new ClientCertificateAuth();
    }

    /**
     * Create an instance of {@link ArrayOfSignatureStatus }
     * 
     */
    public ArrayOfSignatureStatus createArrayOfSignatureStatus() {
        return new ArrayOfSignatureStatus();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link Signatures }
     * 
     */
    public Signatures createSignatures() {
        return new Signatures();
    }

    /**
     * Create an instance of {@link ArrayOfSignature }
     * 
     */
    public ArrayOfSignature createArrayOfSignature() {
        return new ArrayOfSignature();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteSignatureException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RemoteSignatureException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.firmaremota.itagile.it", name = "fault")
    public JAXBElement<RemoteSignatureException> createFault(RemoteSignatureException value) {
        return new JAXBElement<RemoteSignatureException>(_Fault_QNAME, RemoteSignatureException.class, null, value);
    }

}
