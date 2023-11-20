/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.fileOperation.clientws package. 
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

    private final static QName _FileOperationRequest_QNAME = new QName("it.eng.fileoperation.ws", "FileOperationRequest");
    private final static QName _FileOperationResponse_QNAME = new QName("it.eng.fileoperation.ws", "FileOperationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.fileOperation.clientws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link AbstractResponseOperationType }
     * 
     */
    public AbstractResponseOperationType createAbstractResponseOperationType() {
        return new AbstractResponseOperationType();
    }

    /**
     * Create an instance of {@link ResponseFormatRecognitionType }
     * 
     */
    public ResponseFormatRecognitionType createResponseFormatRecognitionType() {
        return new ResponseFormatRecognitionType();
    }

    /**
     * Create an instance of {@link SigVerifyResultType }
     * 
     */
    public SigVerifyResultType createSigVerifyResultType() {
        return new SigVerifyResultType();
    }

    /**
     * Create an instance of {@link SignerInformationType }
     * 
     */
    public SignerInformationType createSignerInformationType() {
        return new SignerInformationType();
    }

    /**
     * Create an instance of {@link SigVerifyResultType.SigVerifyResult }
     * 
     */
    public SigVerifyResultType.SigVerifyResult createSigVerifyResultTypeSigVerifyResult() {
        return new SigVerifyResultType.SigVerifyResult();
    }

    /**
     * Create an instance of {@link ResponseUnpackMultipartType }
     * 
     */
    public ResponseUnpackMultipartType createResponseUnpackMultipartType() {
        return new ResponseUnpackMultipartType();
    }

    /**
     * Create an instance of {@link TimeStampInfotype }
     * 
     */
    public TimeStampInfotype createTimeStampInfotype() {
        return new TimeStampInfotype();
    }

    /**
     * Create an instance of {@link InputRapportoVerificaType }
     * 
     */
    public InputRapportoVerificaType createInputRapportoVerificaType() {
        return new InputRapportoVerificaType();
    }

    /**
     * Create an instance of {@link InputSigVerifyType }
     * 
     */
    public InputSigVerifyType createInputSigVerifyType() {
        return new InputSigVerifyType();
    }

    /**
     * Create an instance of {@link Response.FileoperationResponse }
     * 
     */
    public Response.FileoperationResponse createResponseFileoperationResponse() {
        return new Response.FileoperationResponse();
    }

    /**
     * Create an instance of {@link FileOperationResponse }
     * 
     */
    public FileOperationResponse createFileOperationResponse() {
        return new FileOperationResponse();
    }

    /**
     * Create an instance of {@link FileOperationResponse.FileoperationResponse }
     * 
     */
    public FileOperationResponse.FileoperationResponse createFileOperationResponseFileoperationResponse() {
        return new FileOperationResponse.FileoperationResponse();
    }

    /**
     * Create an instance of {@link MappaParametri }
     * 
     */
    public MappaParametri createMappaParametri() {
        return new MappaParametri();
    }

    /**
     * Create an instance of {@link PaginaTimbro }
     * 
     */
    public PaginaTimbro createPaginaTimbro() {
        return new PaginaTimbro();
    }

    /**
     * Create an instance of {@link InputTimbroType }
     * 
     */
    public InputTimbroType createInputTimbroType() {
        return new InputTimbroType();
    }

    /**
     * Create an instance of {@link TestoTimbro }
     * 
     */
    public TestoTimbro createTestoTimbro() {
        return new TestoTimbro();
    }

    /**
     * Create an instance of {@link InputCopiaConformeType }
     * 
     */
    public InputCopiaConformeType createInputCopiaConformeType() {
        return new InputCopiaConformeType();
    }

    /**
     * Create an instance of {@link ImageFile }
     * 
     */
    public ImageFile createImageFile() {
        return new ImageFile();
    }

    /**
     * Create an instance of {@link InputPreparaFirmaPadesType }
     * 
     */
    public InputPreparaFirmaPadesType createInputPreparaFirmaPadesType() {
        return new InputPreparaFirmaPadesType();
    }

    /**
     * Create an instance of {@link InformazioniFirmaGraficaType }
     * 
     */
    public InformazioniFirmaGraficaType createInformazioniFirmaGraficaType() {
        return new InformazioniFirmaGraficaType();
    }

    /**
     * Create an instance of {@link InputCompletaFirmaPadesType }
     * 
     */
    public InputCompletaFirmaPadesType createInputCompletaFirmaPadesType() {
        return new InputCompletaFirmaPadesType();
    }

    /**
     * Create an instance of {@link ResponseTimbroType }
     * 
     */
    public ResponseTimbroType createResponseTimbroType() {
        return new ResponseTimbroType();
    }

    /**
     * Create an instance of {@link ResponseCompletaFirmaPadesType }
     * 
     */
    public ResponseCompletaFirmaPadesType createResponseCompletaFirmaPadesType() {
        return new ResponseCompletaFirmaPadesType();
    }

    /**
     * Create an instance of {@link ResponseCopiaConformeType }
     * 
     */
    public ResponseCopiaConformeType createResponseCopiaConformeType() {
        return new ResponseCopiaConformeType();
    }

    /**
     * Create an instance of {@link ResponsePreparaFirmaPadesType }
     * 
     */
    public ResponsePreparaFirmaPadesType createResponsePreparaFirmaPadesType() {
        return new ResponsePreparaFirmaPadesType();
    }

    /**
     * Create an instance of {@link AbstractInputOperationType }
     * 
     */
    public AbstractInputOperationType createAbstractInputOperationType() {
        return new AbstractInputOperationType();
    }

    /**
     * Create an instance of {@link MessageType }
     * 
     */
    public MessageType createMessageType() {
        return new MessageType();
    }

    /**
     * Create an instance of {@link Base64Binary }
     * 
     */
    public Base64Binary createBase64Binary() {
        return new Base64Binary();
    }

    /**
     * Create an instance of {@link HexBinary }
     * 
     */
    public HexBinary createHexBinary() {
        return new HexBinary();
    }

    /**
     * Create an instance of {@link FileOperationRequest }
     * 
     */
    public FileOperationRequest createFileOperationRequest() {
        return new FileOperationRequest();
    }

    /**
     * Create an instance of {@link FileOperation }
     * 
     */
    public FileOperation createFileOperation() {
        return new FileOperation();
    }

    /**
     * Create an instance of {@link InputFileOperationType }
     * 
     */
    public InputFileOperationType createInputFileOperationType() {
        return new InputFileOperationType();
    }

    /**
     * Create an instance of {@link Operations }
     * 
     */
    public Operations createOperations() {
        return new Operations();
    }

    /**
     * Create an instance of {@link Response.GenericError }
     * 
     */
    public Response.GenericError createResponseGenericError() {
        return new Response.GenericError();
    }

    /**
     * Create an instance of {@link InputFile }
     * 
     */
    public InputFile createInputFile() {
        return new InputFile();
    }

    /**
     * Create an instance of {@link InputFormatRecognitionType }
     * 
     */
    public InputFormatRecognitionType createInputFormatRecognitionType() {
        return new InputFormatRecognitionType();
    }

    /**
     * Create an instance of {@link InputConversionType }
     * 
     */
    public InputConversionType createInputConversionType() {
        return new InputConversionType();
    }

    /**
     * Create an instance of {@link InputUnpackMultipartType }
     * 
     */
    public InputUnpackMultipartType createInputUnpackMultipartType() {
        return new InputUnpackMultipartType();
    }

    /**
     * Create an instance of {@link InputFileCompressType }
     * 
     */
    public InputFileCompressType createInputFileCompressType() {
        return new InputFileCompressType();
    }

    /**
     * Create an instance of {@link InputCodeDetectorType }
     * 
     */
    public InputCodeDetectorType createInputCodeDetectorType() {
        return new InputCodeDetectorType();
    }

    /**
     * Create an instance of {@link InputUnpackType }
     * 
     */
    public InputUnpackType createInputUnpackType() {
        return new InputUnpackType();
    }

    /**
     * Create an instance of {@link InputDigestType }
     * 
     */
    public InputDigestType createInputDigestType() {
        return new InputDigestType();
    }

    /**
     * Create an instance of {@link InputUnpackDigestType }
     * 
     */
    public InputUnpackDigestType createInputUnpackDigestType() {
        return new InputUnpackDigestType();
    }

    /**
     * Create an instance of {@link ResponseUnpackType }
     * 
     */
    public ResponseUnpackType createResponseUnpackType() {
        return new ResponseUnpackType();
    }

    /**
     * Create an instance of {@link ResponseFileCompressType }
     * 
     */
    public ResponseFileCompressType createResponseFileCompressType() {
        return new ResponseFileCompressType();
    }

    /**
     * Create an instance of {@link MultipartContentType }
     * 
     */
    public MultipartContentType createMultipartContentType() {
        return new MultipartContentType();
    }

    /**
     * Create an instance of {@link ResponseSigVerify }
     * 
     */
    public ResponseSigVerify createResponseSigVerify() {
        return new ResponseSigVerify();
    }

    /**
     * Create an instance of {@link DnType }
     * 
     */
    public DnType createDnType() {
        return new DnType();
    }

    /**
     * Create an instance of {@link QcStatements }
     * 
     */
    public QcStatements createQcStatements() {
        return new QcStatements();
    }

    /**
     * Create an instance of {@link KeyUsages }
     * 
     */
    public KeyUsages createKeyUsages() {
        return new KeyUsages();
    }

    /**
     * Create an instance of {@link ResponsePdfConvResultType }
     * 
     */
    public ResponsePdfConvResultType createResponsePdfConvResultType() {
        return new ResponsePdfConvResultType();
    }

    /**
     * Create an instance of {@link ResponseFileDigestType }
     * 
     */
    public ResponseFileDigestType createResponseFileDigestType() {
        return new ResponseFileDigestType();
    }

    /**
     * Create an instance of {@link ResponseFileDigestUnpackType }
     * 
     */
    public ResponseFileDigestUnpackType createResponseFileDigestUnpackType() {
        return new ResponseFileDigestUnpackType();
    }

    /**
     * Create an instance of {@link ResponseCodeDetector }
     * 
     */
    public ResponseCodeDetector createResponseCodeDetector() {
        return new ResponseCodeDetector();
    }

    /**
     * Create an instance of {@link ResponseRapportoVerificaType }
     * 
     */
    public ResponseRapportoVerificaType createResponseRapportoVerificaType() {
        return new ResponseRapportoVerificaType();
    }

    /**
     * Create an instance of {@link AbstractResponseOperationType.ErrorsMessage }
     * 
     */
    public AbstractResponseOperationType.ErrorsMessage createAbstractResponseOperationTypeErrorsMessage() {
        return new AbstractResponseOperationType.ErrorsMessage();
    }

    /**
     * Create an instance of {@link AbstractResponseOperationType.Warnings }
     * 
     */
    public AbstractResponseOperationType.Warnings createAbstractResponseOperationTypeWarnings() {
        return new AbstractResponseOperationType.Warnings();
    }

    /**
     * Create an instance of {@link ResponseFormatRecognitionType.DatiFormato }
     * 
     */
    public ResponseFormatRecognitionType.DatiFormato createResponseFormatRecognitionTypeDatiFormato() {
        return new ResponseFormatRecognitionType.DatiFormato();
    }

    /**
     * Create an instance of {@link ResponseFormatRecognitionType.DatiFormatiInterni }
     * 
     */
    public ResponseFormatRecognitionType.DatiFormatiInterni createResponseFormatRecognitionTypeDatiFormatiInterni() {
        return new ResponseFormatRecognitionType.DatiFormatiInterni();
    }

    /**
     * Create an instance of {@link SignerInformationType.Certificato }
     * 
     */
    public SignerInformationType.Certificato createSignerInformationTypeCertificato() {
        return new SignerInformationType.Certificato();
    }

    /**
     * Create an instance of {@link SignerInformationType.Marca }
     * 
     */
    public SignerInformationType.Marca createSignerInformationTypeMarca() {
        return new SignerInformationType.Marca();
    }

    /**
     * Create an instance of {@link SigVerifyResultType.SigVerifyResult.FormatResult }
     * 
     */
    public SigVerifyResultType.SigVerifyResult.FormatResult createSigVerifyResultTypeSigVerifyResultFormatResult() {
        return new SigVerifyResultType.SigVerifyResult.FormatResult();
    }

    /**
     * Create an instance of {@link SigVerifyResultType.SigVerifyResult.TimestampVerificationResult }
     * 
     */
    public SigVerifyResultType.SigVerifyResult.TimestampVerificationResult createSigVerifyResultTypeSigVerifyResultTimestampVerificationResult() {
        return new SigVerifyResultType.SigVerifyResult.TimestampVerificationResult();
    }

    /**
     * Create an instance of {@link SigVerifyResultType.SigVerifyResult.SignerInformations }
     * 
     */
    public SigVerifyResultType.SigVerifyResult.SignerInformations createSigVerifyResultTypeSigVerifyResultSignerInformations() {
        return new SigVerifyResultType.SigVerifyResult.SignerInformations();
    }

    /**
     * Create an instance of {@link ResponseUnpackMultipartType.MultipartContents }
     * 
     */
    public ResponseUnpackMultipartType.MultipartContents createResponseUnpackMultipartTypeMultipartContents() {
        return new ResponseUnpackMultipartType.MultipartContents();
    }

    /**
     * Create an instance of {@link TimeStampInfotype.TsaInfo }
     * 
     */
    public TimeStampInfotype.TsaInfo createTimeStampInfotypeTsaInfo() {
        return new TimeStampInfotype.TsaInfo();
    }

    /**
     * Create an instance of {@link InputRapportoVerificaType.SignatureVerify }
     * 
     */
    public InputRapportoVerificaType.SignatureVerify createInputRapportoVerificaTypeSignatureVerify() {
        return new InputRapportoVerificaType.SignatureVerify();
    }

    /**
     * Create an instance of {@link InputRapportoVerificaType.TimestampVerifiy }
     * 
     */
    public InputRapportoVerificaType.TimestampVerifiy createInputRapportoVerificaTypeTimestampVerifiy() {
        return new InputRapportoVerificaType.TimestampVerifiy();
    }

    /**
     * Create an instance of {@link InputSigVerifyType.SignatureVerify }
     * 
     */
    public InputSigVerifyType.SignatureVerify createInputSigVerifyTypeSignatureVerify() {
        return new InputSigVerifyType.SignatureVerify();
    }

    /**
     * Create an instance of {@link InputSigVerifyType.TimestampVerifiy }
     * 
     */
    public InputSigVerifyType.TimestampVerifiy createInputSigVerifyTypeTimestampVerifiy() {
        return new InputSigVerifyType.TimestampVerifiy();
    }

    /**
     * Create an instance of {@link Response.FileoperationResponse.FileOperationResults }
     * 
     */
    public Response.FileoperationResponse.FileOperationResults createResponseFileoperationResponseFileOperationResults() {
        return new Response.FileoperationResponse.FileOperationResults();
    }

    /**
     * Create an instance of {@link FileOperationResponse.GenericError }
     * 
     */
    public FileOperationResponse.GenericError createFileOperationResponseGenericError() {
        return new FileOperationResponse.GenericError();
    }

    /**
     * Create an instance of {@link FileOperationResponse.FileoperationResponse.FileOperationResults }
     * 
     */
    public FileOperationResponse.FileoperationResponse.FileOperationResults createFileOperationResponseFileoperationResponseFileOperationResults() {
        return new FileOperationResponse.FileoperationResponse.FileOperationResults();
    }

    /**
     * Create an instance of {@link MappaParametri.Parametro }
     * 
     */
    public MappaParametri.Parametro createMappaParametriParametro() {
        return new MappaParametri.Parametro();
    }

    /**
     * Create an instance of {@link PaginaTimbro.Pagine }
     * 
     */
    public PaginaTimbro.Pagine createPaginaTimbroPagine() {
        return new PaginaTimbro.Pagine();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileOperationRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FileOperationRequest }{@code >}
     */
    @XmlElementDecl(namespace = "it.eng.fileoperation.ws", name = "FileOperationRequest")
    public JAXBElement<FileOperationRequest> createFileOperationRequest(FileOperationRequest value) {
        return new JAXBElement<FileOperationRequest>(_FileOperationRequest_QNAME, FileOperationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileOperationResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FileOperationResponse }{@code >}
     */
    @XmlElementDecl(namespace = "it.eng.fileoperation.ws", name = "FileOperationResponse")
    public JAXBElement<FileOperationResponse> createFileOperationResponse(FileOperationResponse value) {
        return new JAXBElement<FileOperationResponse>(_FileOperationResponse_QNAME, FileOperationResponse.class, null, value);
    }

}
