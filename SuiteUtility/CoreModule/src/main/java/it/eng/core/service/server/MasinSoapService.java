package it.eng.core.service.server;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

public class MasinSoapService {

	public static void main(String[] args) {
		Endpoint ep = Endpoint.publish("http://localhost:9999/business/soap/ServiceSoap", new SoapService());
		SOAPBinding binding = (SOAPBinding) ep.getBinding();
		binding.setMTOMEnabled(true);
	}
}
