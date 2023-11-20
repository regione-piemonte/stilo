/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.soap.SOAPElement;

import org.apache.axis.MessageContext;
import org.apache.axis.client.Call;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import it.eng.albopretorio.bean.AlboPretorioAttachBean;
import it.eng.albopretorio.bean.CaricaDocumentoBean;
import it.eng.albopretorio.ws.AlboPretorioLocator;
import it.eng.albopretorio.ws.AlboPretorioSoap12Stub;
import it.eng.albopretorio.ws.DocumentoType;


public class CaricaDocumento {

	private static Logger logger = Logger.getLogger(CaricaDocumento.class);

	public SOAPEnvelope caricaDocumento(CaricaDocumentoBean caricaDocumentoBean, DocumentoType type) throws Exception{
	
		String wsEndpoint = "";
		String serviceName = "";
		String operationName = "";
		String actionURI = "";
		SOAPEnvelope response = null;
		
		if (caricaDocumentoBean != null) {
			wsEndpoint = caricaDocumentoBean.getWsEndpoint();
			serviceName = caricaDocumentoBean.getServiceName();
			operationName = caricaDocumentoBean.getOperationName();
			actionURI = caricaDocumentoBean.getActionURI();
		}
			
		try {
			if (wsEndpoint == null || "".equalsIgnoreCase(wsEndpoint))  {
				ReadProperties rp=new ReadProperties();
				wsEndpoint = rp.getProperty("END_POINT");
				logger.debug("EndPoint(da file properties): " +wsEndpoint);
			}
			if (serviceName == null || "".equalsIgnoreCase(serviceName))  {
				ReadProperties rp=new ReadProperties();
				serviceName = rp.getProperty("SERVICE_NAME");
				logger.debug("ServiceName(da file properties): " +serviceName);
			}
			if (operationName == null || "".equalsIgnoreCase(operationName))  {
				ReadProperties rp=new ReadProperties();
				operationName = rp.getProperty("OPERATION_NAME");
				logger.debug("OperationName(da file properties): " +operationName);
			}
			if (actionURI == null || "".equalsIgnoreCase(actionURI))  {
				ReadProperties rp=new ReadProperties();
				actionURI = rp.getProperty("ACTION_URI");
				logger.debug("OperationName(da file properties): " +actionURI);
			}
		} catch (Exception e) {
			throw new Exception("Errore nel reperimento delle variabili da file di properties: " + e.getMessage());
		}
			
		AlboPretorioLocator locator = new AlboPretorioLocator();
		locator.setEndpointAddress(serviceName, wsEndpoint);
		AlboPretorioSoap12Stub servicesPort = (AlboPretorioSoap12Stub)locator.getPort(AlboPretorioSoap12Stub.class);
	
		logger.debug("Istanzio il locator per la connessione, serviceName: " + serviceName + " endpoint: " + wsEndpoint);
		
		MessageContext msgContext = MessageContext.getCurrentContext();
		HTTPConstants httpConst = new HTTPConstants();
		
		servicesPort.setMaintainSession(true);
		
		logger.debug("Setto il maintain session a true");
		
		Call call = servicesPort._createCall();
		call.setProperty(msgContext.HTTP_TRANSPORT_VERSION,httpConst.HEADER_PROTOCOL_V11);
		
		call.setTargetEndpointAddress(new java.net.URL(wsEndpoint));
		call.setOperationName(operationName);
		
		logger.debug("Setto l'operazione da eseguire a: " + operationName);
		
		call.setSOAPActionURI(actionURI);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // creo l'oggetto
	    
		SOAPEnvelope soap = new SOAPEnvelope();
		SOAPElement caricaDocumento = soap.addChildElement("CaricaDocumento","","http://it.intersail/wse");
		SOAPElement documento = caricaDocumento.addChildElement("Documento","","http://www.intersail.it/AlboPretorio/Protocollo");
		SOAPElement protocollo = documento.addChildElement("Protocollo");
		protocollo.addTextNode(type.getProtocollo());
		SOAPElement numeroDocumento = documento.addChildElement("NumeroDocumento");
		numeroDocumento.addTextNode(type.getNumeroDocumento());
		SOAPElement annoDocumento = documento.addChildElement("AnnoDocumento");
		annoDocumento.addTextNode(String.valueOf(type.getAnnoDocumento()));
		
		SOAPElement dataDocumento = documento.addChildElement("DataDocumento");
		String dataDoc = type.getDataDocumento() != null ? sdf.format(type.getDataDocumento()) : null;
	    if(dataDoc != null)	dataDocumento.addTextNode(dataDoc);
		
		SOAPElement oggetto = documento.addChildElement("Oggetto");
		oggetto.addTextNode(type.getOggetto());
		SOAPElement settore = documento.addChildElement("Settore");
		settore.addTextNode(type.getSettore());
		
		SOAPElement dataInizioEsposizione = documento.addChildElement("DataInizioEsposizione");
	    String dataInizio = type.getDataInizioEsposizione() != null ? sdf.format(type.getDataInizioEsposizione()) : null;
	    if(dataInizio != null)	dataInizioEsposizione.addTextNode(dataInizio);
		
		SOAPElement dataFineEsposizione = documento.addChildElement("DataFineEsposizione");
		String dataFine = type.getDataFineEsposizione() != null ? sdf.format(type.getDataFineEsposizione()) : null;
		if(dataFine != null)	dataFineEsposizione.addTextNode(dataFine);
		
		SOAPElement nomeFile = documento.addChildElement("NomeFile");
		nomeFile.addTextNode(type.getNomeFile());
		SOAPElement userName = documento.addChildElement("Username");
		userName.addTextNode(type.getUsername());
		SOAPElement tipoDocumento = documento.addChildElement("TipoDocumento");
		tipoDocumento.addTextNode(String.valueOf(type.getTipoDocumento()));
		SOAPElement enteProponemente = documento.addChildElement("EnteProvenienza");
		enteProponemente.addTextNode(type.getEnteProvenienza());
		SOAPElement note = documento.addChildElement("Note");
		note.addTextNode(type.getNote());
		
		if(type.getAllegati() != null && !type.getAllegati().isEmpty()) {
			SOAPElement soapElementAllegati = documento.addChildElement("Allegati");
			for(int i=0; i < type.getAllegati().size(); i++) {
				AlboPretorioAttachBean attachmentBean = type.getAllegati().get(i);
				if("A".equals(attachmentBean.getTipoFile())) {
					SOAPElement allegato = soapElementAllegati.addChildElement("NomeFile");
					allegato.addTextNode(attachmentBean.getFileName());
				}
			}
		}
	
		logger.debug("Ho creato la seguente request: " + soap.getAsString());
		
		try {
			response =	call.invoke(soap);
		} catch (Exception e) {
			throw new Exception("Errore nell'invocazione del WS: " + wsEndpoint + " con il seguente errore: " + e.getMessage());
		}
		
		return response;
	}
	
	public static void main(String[] args) {

		DocumentoType type = new DocumentoType();
		
		type.setProtocollo("DET/2015/1950");
		
		type.setNumeroDocumento("12");
		
		type.setAnnoDocumento(2015);
		
		Date data = new Date();
		data.UTC(2015, 07, 30, 0,0, 0);

		type.setDataDocumento(data);
		
		type.setOggetto("Prova millumino di immenso e poi non mi illumino pi");
		type.setSettore("Sistemi informativi");
		
		data.UTC(2015, 07, 30, 0,0, 0);
		
		type.setDataInizioEsposizione(data);
		data.UTC(2015, 07, 30, 0,0, 0);

		type.setDataFineEsposizione(data);
		
		type.setNomeFile("fileTest0.pdf");
		type.setUsername("admin");
		type.setTipoDocumento(9050);
		type.setEnteProvenienza("Mestre");
		type.setNote("Mestre");

		try {
			new CaricaDocumento().caricaDocumento(null,type);
		} catch (Exception e) {
			logger.warn(e);
		}		
	}
}