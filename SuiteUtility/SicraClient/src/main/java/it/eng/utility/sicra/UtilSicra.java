package it.eng.utility.sicra;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import it.eng.utility.NoNamespaceContext;
import it.eng.utility.data.Outcome;
import it.eng.utility.sicra.contabilita.ConstantSicra;

public class UtilSicra {

	public static final NamespaceContext noNamespaceContext = new NoNamespaceContext();
	public static final XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();

	public static final void aggiornaEsito(final Outcome output, Exception e) {
		final Throwable cause = e.getCause();
		output.setRispostaNonRicevuta(true);
		output.setTimeout(cause instanceof SocketTimeoutException);
		output.setMessaggio(cause != null ? cause.getLocalizedMessage() : e.getLocalizedMessage());
		output.setOk(false);
	}

	// public static final String toXML(org.springframework.oxm.Marshaller marshaller, Object obj)
	// throws IOException, XMLStreamException, FactoryConfigurationError {
	// final Writer sw = new StringWriter();
	// final XMLStreamWriter streamWriter = xmlOutputFactory.createXMLStreamWriter(sw);
	// streamWriter.setNamespaceContext(noNamespaceContext);
	// final Result result = StaxUtils.createStaxResult(streamWriter);
	// marshaller.marshal(obj, result);
	// String xml = sw.toString().trim();
	// // final int k = xml.indexOf(" ");
	// // if (k > 0) {
	// // final int h = xml.indexOf(">");
	// // if (h > k) {
	// // final String s1 = xml.substring(0, k);
	// // final String s2 = xml.substring(h);
	// // xml = s1 + s2;
	// // }
	// // }
	// return xml;
	// }

	public static final String toXML(org.springframework.oxm.Marshaller marshaller, Object obj)
			throws IOException, XMLStreamException, FactoryConfigurationError {
		final Writer sw = new StringWriter();
		final Result result = new StreamResult(sw);
		marshaller.marshal(obj, result);
		return sw.toString();
	}
	
	public static final String toXMLNoNameSpace(org.springframework.oxm.Marshaller marshaller, Object obj, String namespace)
			throws IOException, XMLStreamException, FactoryConfigurationError {
		/*
		final Writer sw = new StringWriter();
		final XMLStreamWriter streamWriter = xmlOutputFactory.createXMLStreamWriter(sw);
		streamWriter.setNamespaceContext(noNamespaceContext);
		final Result result = StaxUtils.createStaxResult(streamWriter);
		marshaller.marshal(obj, result);
		return sw.toString();
		*/
		final Writer sw = new StringWriter();
		final Result result = new StreamResult(sw);
		marshaller.marshal(obj, result);
		String xml = sw.toString();
		
		if (!namespace.equals("") && namespace != null) {
			if (xml.contains("<richiesta xmlns=\"" + namespace +  "\">")) {
				xml = xml.replace("<richiesta xmlns=\"" + namespace +  "\">", "<richiesta>");
			}
		}
		
		return xml;
	}

	public static final String toXMLCDATA(org.springframework.oxm.Marshaller marshaller, Object obj) throws Exception {
		return addToCDATA(toXML(marshaller, obj));
	}

	public static final Object toObject(org.springframework.oxm.Unmarshaller unmarshaller, String xml, Charset xmlEncoding) throws Exception {
		final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(xmlEncoding));
		// final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		final Source source = new StreamSource(inputStream);
		return unmarshaller.unmarshal(source);
	}
	
	public static final Object toObjectNamespace(org.springframework.oxm.Unmarshaller unmarshaller, String xml, Charset xmlEncoding, String namespace) throws Exception {
		if (!namespace.equals("") && namespace != null) {
			if (xml.contains("<risultato>")) {
				xml = xml.replace("<risultato>", "<risultato xmlns=\"" + namespace +  "\">");
			}
		}
		
		final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(xmlEncoding));
		// final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		final Source source = new StreamSource(inputStream);
		return unmarshaller.unmarshal(source);
	}

	public static final String addToCDATA(String xml) {
		return "<![CDATA[" + xml + "]]>";
	}

	public static boolean isCancellazione(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta.Impegno.Testata testata) {
		return ConstantSicra.AZIONE_CANCELLA_MOVIMENTO.equalsIgnoreCase(testata.getAzione());
	}

	public static boolean isInserimento(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta.Impegno.Testata testata) {
		return ConstantSicra.AZIONE_CREA_MOVIMENTO.equalsIgnoreCase(testata.getAzione());
	}

	// public static boolean isOK(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato.Messaggi messaggi) {
	// return ConstantSicra.TIPO_MESSAGGI_OK.equalsIgnoreCase(messaggi.getTipo());
	// }
	//
	// public static boolean isErrore(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato.Messaggi messaggi) {
	// return ConstantSicra.TIPO_MESSAGGI_ERRORE.equalsIgnoreCase(messaggi.getTipo());
	// }
	//
	// public static boolean isAvviso(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato.Messaggi messaggi) {
	// return ConstantSicra.TIPO_MESSAGGI_AVVISO.equalsIgnoreCase(messaggi.getTipo());
	// }

}
