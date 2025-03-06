package it.eng.core.business.export;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;

public class XmlExporter extends AbstractExportListener {

	Logger log = Logger.getLogger(XmlExporter.class);

	private XMLStreamWriter staxWriter;
	/* nome del file da creare */
	private String fileName;
	// private Class<?> entityClass;
	/* mapping delle property da inserire nell'export all prop se vuoto o null */
	private HashMap<String, String> propertyMapping;

	public XmlExporter(String fileName) throws Exception {
		this.fileName = fileName;

	}

	public void onInit(ExportEvent ee) throws Exception {
		File file = new File(fileName);
		FileOutputStream out = new FileOutputStream(file);
		String now = new SimpleDateFormat().format(new Date(System.currentTimeMillis()));
		// TODO test staxapip2 google code
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		staxWriter = factory.createXMLStreamWriter(out);
		// staxWriter.writeStartDocument("UTF-8", "1.0");
		staxWriter.writeStartElement("dataset");
	}

	@Override
	public void onData(ExportEvent ee) throws Exception {
		// record da scrivere nel file
		Object record = ee.getRecord();
		// SerializationHelper.serialize((Serializable)record );
		Map<String, Object> map = BeanUtilsBean2.getInstance().getPropertyUtils().describe(record);
		staxWriter.writeStartElement(record.getClass().getSimpleName());
		for (String key : map.keySet()) {
			String value = Stringifier.stringify(map.get(key));
			if (value != null)// stampa soo quelli stringabili
				staxWriter.writeAttribute(key, value);
			// staxWriter.flush();
		}
		staxWriter.writeEndElement();
	}

	public void onFinish(ExportEvent ee) throws Exception {
		// TODO completa l'elaborazione
		staxWriter.writeEndElement();
		staxWriter.writeEndDocument();
		staxWriter.flush();
		staxWriter.close();
	}

	@Override
	public void onRunning(ExportEvent ee) throws Exception {
		log.info("elaborato record " + ee.getNumRec());

	}

}
