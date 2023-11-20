/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;

import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.documentservice.DocumentService;
import it.doqui.acta.acaris.documentservice.DocumentServicePort;
import it.doqui.acta.acaris.documentservice.EnumTipoOperazione;
import it.doqui.acta.acaris.documentservice.IdentificatoreDocumento;
import it.doqui.acta.acaris.documentservice.InfoRichiestaCreazione;

@Dependent
public class DocumentServiceClient extends AbstractServiceClient {

    private static final String SERVICE_NAME = "service.document";
    private DocumentServicePort documentServicePort;

    public DocumentServiceClient() {
        super(SERVICE_NAME);
    }

    @Override
    protected void initService() {
        printInfo(SERVICE_NAME, "init");
        final URL url = getServiceURL(SERVICE_NAME);
        if (documentServicePort == null) {
            try {
                final DocumentService documentService = new DocumentService(url);
                documentServicePort = documentService.getDocumentServicePort();
                setHandler(((BindingProvider) documentServicePort).getBinding());
                printInfo(SERVICE_NAME, "initialized");
            } catch (Exception e) {
                printError(SERVICE_NAME, "init", e);
                throw e;
            }
        }
    }

    public IdentificatoreDocumento createDocument(final ObjectIdType repositoryId, final PrincipalIdType principalId,
                                                  final EnumTipoOperazione operationType,
                                                  final InfoRichiestaCreazione requestInfo,
                                                  final Map<String, Object> paramMap) throws Exception{
        printInfo(SERVICE_NAME, "create-document");
        IdentificatoreDocumento documentId = null;
        try {
            initService();
            setServiceIdentifier((BindingProvider) documentServicePort, paramMap);
            SOAPBinding binding = (SOAPBinding) ((BindingProvider) documentServicePort).getBinding();
            binding.setMTOMEnabled(true);
            BindingProvider bindingP = ((BindingProvider) documentServicePort);
            Map<String, Object> map = bindingP.getRequestContext();
            System.out.println("REQUEST CONTEXT: " + map);
            System.out.println("::::::setto l'encoding ISO-8859-1");
            map.put(SOAPMessage.CHARACTER_SET_ENCODING, "ISO-8859-1");
            map.put("org.apache.cxf.message.Message.ENCODING", "ISO-8859-1");
            System.out.println("REQUEST CONTEXT: " +  bindingP.getRequestContext().get(SOAPMessage.CHARACTER_SET_ENCODING));
            //map.put(org.apache.cxf.binding.soap.SoapMessage.ENCODING, "ISO-8859-1");
            documentId = documentServicePort.creaDocumento(repositoryId, principalId, operationType, requestInfo);
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("errore : " , e);
            printError(SERVICE_NAME, "create-document", e.getMessage());
            throw e;
        }
        return documentId;
    }

}
