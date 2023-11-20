/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringWriter;
import java.util.Date;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.soap.MTOM;

import org.apache.log4j.Logger;

import it.eng.auriga.module.business.dao.DaoDmtCodaRequestSistHr;
import it.eng.auriga.module.business.entity.DmtCodaRequestSistHr;
import it.eng.auriga.repository2.jaxws.webservices.A2A.bean.ResponseWSA2A;
import it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista;


/**
 * @author Antonio Peluso
 */

@WebService(targetNamespace = "http://A2A.webservices.repository2.auriga.eng.it", endpointInterface = "it.eng.auriga.repository2.jaxws.webservices.A2A.WSIA2A", name = "WSA2A")
@MTOM(enabled = true, threshold = 0)
//@HandlerChain(file = "../common/handlerAuthentication.xml")

public class WSA2A implements WSIA2A {

	static Logger aLogger = Logger.getLogger(WSA2A.class.getName());

	public WSA2A() {
		super();

	}

	
	@Override
	public ResponseWSA2A sendUtenti(it.eng.auriga.repository2.jaxws.webservices.A2A.utente.bean.Lista listaUtenti) {
		
		ResponseWSA2A response = new ResponseWSA2A();
		
		try {
			JAXBContext lJAXBContextMarshaller = JAXBContext.newInstance(it.eng.auriga.repository2.jaxws.webservices.A2A.utente.bean.Lista.class);
			Marshaller marshaller = lJAXBContextMarshaller.createMarshaller();

			StringWriter listaUtentiXmlWriter = new StringWriter();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(listaUtenti, listaUtentiXmlWriter);
			
			aLogger.debug("REQUEST: " + listaUtentiXmlWriter.toString());
			
			DmtCodaRequestSistHr lDmtCodaRequestSistHr = new DmtCodaRequestSistHr();
			lDmtCodaRequestSistHr.setTipoRequest("utenti");
			lDmtCodaRequestSistHr.setXmlRequest( listaUtentiXmlWriter.toString());
			lDmtCodaRequestSistHr.setStato("da_elaborare");
			lDmtCodaRequestSistHr.setTsRequest(new Date());
			
			DaoDmtCodaRequestSistHr dao = new DaoDmtCodaRequestSistHr();
			dao.save(lDmtCodaRequestSistHr);
			
			response.setOk(true);
			response.setMsg("Utenti inseriti correttamente");
			
		} catch (Exception e) {
			response.setOk(false);
			response.setMsg("Errore durante l'esecuzione del WS: " + e.getMessage());
		}
			
		return response;
	}

	@Override
	public ResponseWSA2A sendNodiOrganigramma(it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista listaNodiOrganigramma) {
		
		ResponseWSA2A response = new ResponseWSA2A();
		
		try {
			JAXBContext lJAXBContextMarshaller = JAXBContext.newInstance(it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista.class);
			Marshaller marshaller = lJAXBContextMarshaller.createMarshaller();

			StringWriter listaOrganigrammaXmlWriter = new StringWriter();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(listaNodiOrganigramma, listaOrganigrammaXmlWriter);
			
			aLogger.debug("REQUEST: " + listaOrganigrammaXmlWriter.toString());
			
			DmtCodaRequestSistHr lDmtCodaRequestSistHr = new DmtCodaRequestSistHr();
			lDmtCodaRequestSistHr.setTipoRequest("organigramma");
			lDmtCodaRequestSistHr.setXmlRequest( listaOrganigrammaXmlWriter.toString());
			lDmtCodaRequestSistHr.setStato("da_elaborare");
			lDmtCodaRequestSistHr.setTsRequest(new Date());
			
			DaoDmtCodaRequestSistHr dao = new DaoDmtCodaRequestSistHr();
			dao.save(lDmtCodaRequestSistHr);
			
			response.setOk(true);
			response.setMsg("Organigramma inserito correttamente");
			
		} catch (Exception e) {
			response.setOk(false);
			response.setMsg("Errore durante l'esecuzione del WS: " + e.getMessage());
		}
			
		return response;
	}
	
	public static void main (String[] args) throws JAXBException {
		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<tns8:ListaNodiOrganigramma xmlns:tns4=\"http://www.example.org/Trans\" xmlns:tns9=\"http://www.example.org/ORG_Entity\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns8=\"http://a2a.webservices.repository2.auriga.eng.it\" Modalita=\"completa\">\r\n"
				+ "<NodoOrganigramma>\r\n"
				+ "<Cid>A25346</Cid>\r\n"
				+ "<CidPadre>A25219</CidPadre>\r\n"
				+ "<Nome>AUTORIZZAZIONI - A25346</Nome>\r\n"
				+ "<CodSiglario>ACS/AMD/ASA/AUT</CodSiglario>\r\n"
				+ "<FlgSocieta>false</FlgSocieta>\r\n"
				+ "<DataInizio>2017-01-01</DataInizio>\r\n"
				+ "<DataFine>2017-06-04</DataFine>\r\n"
				+ "<Societa>\r\n"
				+ "<Cid>SA</Cid>\r\n"
				+ "<Nome>A2A CALORE &amp; SERVIZI SRL</Nome>\r\n"
				+ "</Societa>\r\n"
				+ "<CidResponsabile />\r\n"
				+ "<CidResponsabileInterim />\r\n"
				+ "</NodoOrganigramma>\r\n"
				+ "<NodoOrganigramma>\r\n"
				+ "<Cid>A25346</Cid>\r\n"
				+ "<CidPadre>A25219</CidPadre>\r\n"
				+ "<Nome>AMBIENTE E AUTORIZZAZIONI - A25346</Nome>\r\n"
				+ "<CodSiglario>ACS/PAD/ASA/AMA</CodSiglario>\r\n"
				+ "<FlgSocieta>false</FlgSocieta>\r\n"
				+ "<DataInizio>2021-04-01</DataInizio>\r\n"
				+ "<DataFine>4712-12-31</DataFine>\r\n"
				+ "<Societa>\r\n"
				+ "<Cid>SA</Cid>\r\n"
				+ "<Nome>A2A CALORE &amp; SERVIZI SRL</Nome>\r\n"
				+ "</Societa>\r\n"
				+ "<CidResponsabile>8721</CidResponsabile>\r\n"
				+ "<CidResponsabileInterim />\r\n"
				+ "</NodoOrganigramma>\r\n"
				+ "<NodoOrganigramma>\r\n"
				+ "<Cid>A25346</Cid>\r\n"
				+ "<CidPadre>A25219</CidPadre>\r\n"
				+ "<Nome>AUTORIZZAZIONI - A25346</Nome>\r\n"
				+ "<CodSiglario>ACS/PAD/ASA/AUT</CodSiglario>\r\n"
				+ "<FlgSocieta>false</FlgSocieta>\r\n"
				+ "<DataInizio>2017-11-01</DataInizio>\r\n"
				+ "<DataFine>2021-03-31</DataFine>\r\n"
				+ "<Societa>\r\n"
				+ "<Cid>SA</Cid>\r\n"
				+ "<Nome>A2A CALORE &amp; SERVIZI SRL</Nome>\r\n"
				+ "</Societa>\r\n"
				+ "<CidResponsabile>8721</CidResponsabile>\r\n"
				+ "<CidResponsabileInterim />\r\n"
				+ "</NodoOrganigramma>\r\n"
				+ "<NodoOrganigramma>\r\n"
				+ "<Cid>A25346</Cid>\r\n"
				+ "<CidPadre>A25219</CidPadre>\r\n"
				+ "<Nome>AUTORIZZAZIONI - A25346</Nome>\r\n"
				+ "<CodSiglario>ACS/AMD/ASA/AUT</CodSiglario>\r\n"
				+ "<FlgSocieta>false</FlgSocieta>\r\n"
				+ "<DataInizio>2017-06-05</DataInizio>\r\n"
				+ "<DataFine>2017-10-31</DataFine>\r\n"
				+ "<Societa>\r\n"
				+ "<Cid>SA</Cid>\r\n"
				+ "<Nome>A2A CALORE &amp; SERVIZI SRL</Nome>\r\n"
				+ "</Societa>\r\n"
				+ "<CidResponsabile>8721</CidResponsabile>\r\n"
				+ "<CidResponsabileInterim />\r\n"
				+ "</NodoOrganigramma>\r\n"
				+ "</tns8:ListaNodiOrganigramma>";
		
//		StringReader requestXml = new StringReader(request);   
//		
//		it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista bean = (it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(requestXml);
	
		it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista listaNodi = new Lista();
		it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista.NodoOrganigramma nodo = new it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista.NodoOrganigramma();
		nodo.setCid("testcid");
		nodo.setCidResponsabile("testResponsabile");
		nodo.setIndirizzoEmailAziendale("testIndirizzo");
		nodo.setNome("testNome");
		it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista.NodoOrganigramma nodo2 = new it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista.NodoOrganigramma();
		nodo2.setCid("testcid2");
		nodo2.setCidResponsabile("testResponsabile2");
		nodo2.setIndirizzoEmailAziendale("testIndirizzo2");
		nodo2.setNome("testNome2");
		
		listaNodi.getNodoOrganigramma().add(nodo);
		listaNodi.getNodoOrganigramma().add(nodo2);
		
//		StringWriter sw = new StringWriter();
//		SingletonJAXBContext.getInstance().createMarshaller().marshal(listaNodi, sw);
//		String xmlString = sw.toString();
//		System.out.println(xmlString);
		
		JAXBContext lJAXBContextMarshaller = JAXBContext.newInstance(it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista.class);
		Marshaller marshaller = lJAXBContextMarshaller.createMarshaller();

		JAXBContext lJAXBContextUnmarshaller = JAXBContext.newInstance(it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista.class);
		Unmarshaller unmarshaller = lJAXBContextUnmarshaller.createUnmarshaller();

		StringWriter stringWriter = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(listaNodi, stringWriter);
		
		System.out.println(stringWriter.toString());
		
		
		String ciao ="";
	
	}
	
}
