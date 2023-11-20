/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.export.IExport;
import it.eng.utility.export.type.xml.DataExport;
import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringEscapeUtils;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ExportXML implements IExport {

	@Override
	public void export(File file, ExportBean bean) throws Exception {
		DataExport root = new DataExport();
		AdvancedCriteria criteria = bean.getCriteria();
		if (criteria != null && criteria.getCriteria() != null) {
			FilterConfigurator config = ((FilterConfigurator) SpringAppContext.getContext().getBean("FilterConfigurator"));
			FilterBean filterConfig = config.getListe().get(bean.getNomeEntita());
			DataExport.Filters filters = new DataExport.Filters();
			for (Criterion criterion : criteria.getCriteria()) {
				FilterFieldBean filterFieldConfig = null;
				for (FilterFieldBean filterField : filterConfig.getFields()) {
					if (filterField.getName().equals(criterion.getFieldName())) {
						filterFieldConfig = filterField;
					}
				}
				String fieldName = filterFieldConfig != null ? filterFieldConfig.getTitle() : criterion.getFieldName();
				String value = null;
				if (criterion.getValue() != null) {
					if (criterion.getValue() instanceof String) {
						value = (String) criterion.getValue();
						// if (filterFieldConfig != null && filterFieldConfig.getSelect() != null) {
						// if (filterFieldConfig.getSelect().getValueMap() != null) {
						// value = filterFieldConfig.getSelect().getValueMap().get(value);
						// }
						// else if (filterFieldConfig.getSelect().getDatasourceName() != null) {
						// for (ItemFilterBean item : filterFieldConfig.getSelect().getLayout().getFields()) {
						// if (item.isValue()) {
						// AbstractDataSource datasource = (AbstractDataSource) SingletonDataSource.getInstance().getDatasources()
						// .get(filterFieldConfig.getSelect().getDatasourceName()).newInstance();
						// value = (String) datasource.getDisplayValue(item.getName(), value);
						// }
						// }
						// }
						// }
					} else if (criterion.getValue() instanceof ArrayList) {
						ArrayList<String> values = (ArrayList<String>) criterion.getValue();
						for (String val : values) {
							// if (filterFieldConfig != null && filterFieldConfig.getSelect() != null) {
							// if (filterFieldConfig.getSelect().getValueMap() != null) {
							// val = filterFieldConfig.getSelect().getValueMap().get(val);
							// }
							// else if (filterFieldConfig.getSelect().getDatasourceName() != null) {
							// for (ItemFilterBean item : filterFieldConfig.getSelect().getLayout().getFields()) {
							// if (item.isValue()) {
							// AbstractDataSource datasource =
							// (AbstractDataSource)SingletonDataSource.getInstance().getDatasources().get(filterFieldConfig.getSelect().getDatasourceName()).newInstance();
							// val = (String) datasource.getDisplayValue(item.getName(), val);
							// }
							// }
							// }
							// }
							if (value == null) {
								value = "" + val;
							} else
								value += ";" + val;
						}
					} else {
						value = criterion.getValue().toString();
					}
				}
				String from = null;
				if (criterion.getStart() != null) {
					if (criterion.getStart() instanceof String) {
						from = (String) criterion.getStart();
						// if (filterFieldConfig != null && filterFieldConfig.getSelect() != null) {
						// if (filterFieldConfig.getSelect().getValueMap() != null) {
						// from = filterFieldConfig.getSelect().getValueMap().get(from);
						// }
						// else if (filterFieldConfig.getSelect().getDatasourceName() != null) {
						// for (ItemFilterBean item : filterFieldConfig.getSelect().getLayout().getFields()) {
						// if (item.isValue()) {
						// AbstractDataSource datasource = (AbstractDataSource) SingletonDataSource.getInstance().getDatasources()
						// .get(filterFieldConfig.getSelect().getDatasourceName()).newInstance();
						// from = (String) datasource.getDisplayValue(item.getName(), value);
						// }
						// }
						// }
						// }
					} else if (criterion.getStart() instanceof ArrayList) {
						ArrayList<String> values = (ArrayList<String>) criterion.getStart();
						for (String val : values) {
							// if (filterFieldConfig != null && filterFieldConfig.getSelect() != null) {
							// if (filterFieldConfig.getSelect().getValueMap() != null) {
							// val = filterFieldConfig.getSelect().getValueMap().get(val);
							// }
							// else if (filterFieldConfig.getSelect().getDatasourceName() != null) {
							// for (ItemFilterBean item : filterFieldConfig.getSelect().getLayout().getFields()) {
							// if (item.isValue()) {
							// AbstractDataSource datasource =
							// (AbstractDataSource)SingletonDataSource.getInstance().getDatasources().get(filterFieldConfig.getSelect().getDatasourceName()).newInstance();
							// val = (String) datasource.getDisplayValue(item.getName(), val);
							// }
							// }
							// }
							// }
							if (from == null) {
								from = "" + val;
							} else
								from += ";" + val;
						}
					} else {
						from = criterion.getStart().toString();
					}
				}
				String to = null;
				if (criterion.getEnd() != null) {
					if (criterion.getEnd() instanceof String) {
						to = (String) criterion.getEnd();
						// if (filterFieldConfig != null && filterFieldConfig.getSelect() != null) {
						// if (filterFieldConfig.getSelect().getValueMap() != null) {
						// to = filterFieldConfig.getSelect().getValueMap().get(to);
						// }
						// else if (filterFieldConfig.getSelect().getDatasourceName() != null) {
						// for (ItemFilterBean item : filterFieldConfig.getSelect().getLayout().getFields()) {
						// if (item.isValue()) {
						// AbstractDataSource datasource = (AbstractDataSource) SingletonDataSource.getInstance().getDatasources()
						// .get(filterFieldConfig.getSelect().getDatasourceName()).newInstance();
						// to = (String) datasource.getDisplayValue(item.getName(), value);
						// }
						// }
						// }
						// }
					} else if (criterion.getEnd() instanceof ArrayList) {
						ArrayList<String> values = (ArrayList<String>) criterion.getEnd();
						for (String val : values) {
							// if (filterFieldConfig != null && filterFieldConfig.getSelect() != null) {
							// if (filterFieldConfig.getSelect().getValueMap() != null) {
							// val = filterFieldConfig.getSelect().getValueMap().get(val);
							// }
							// else if (filterFieldConfig.getSelect().getDatasourceName() != null) {
							// for (ItemFilterBean item : filterFieldConfig.getSelect().getLayout().getFields()) {
							// if (item.isValue()) {
							// AbstractDataSource datasource =
							// (AbstractDataSource)SingletonDataSource.getInstance().getDatasources().get(filterFieldConfig.getSelect().getDatasourceName()).newInstance();
							// val = (String) datasource.getDisplayValue(item.getName(), val);
							// }
							// }
							// }
							// }
							if (to == null) {
								to = "" + val;
							} else
								to += ";" + val;
						}
					} else {
						to = criterion.getEnd().toString();
					}
				}
				DataExport.Filters.Filter filter = new DataExport.Filters.Filter();
				filter.setOn(fieldName);
				filter.setOperator(criterion.getOperator());
				filter.setValue(value);
				filter.setFrom(from);
				filter.setTo(to);
				filters.getFilter().add(filter);
			}
			root.setFilters(filters);
		}
		DataExport.Resultset resultSet = new DataExport.Resultset();
		for (int r = 0; r < bean.getRecords().length; r++) {
			Map record = bean.getRecords()[r];
			DataExport.Resultset.Rec rec = new DataExport.Resultset.Rec();
			for (int c = 0; c < bean.getFields().length; c++) {
				DataExport.Resultset.Rec.Col col = new DataExport.Resultset.Rec.Col();

				if (bean.showHeaders()) {
					col.setName(StringEscapeUtils.unescapeHtml(bean.getHeaders()[c]));
				}
				col.setValue(StringEscapeUtils.unescapeHtml((String) record.get(bean.getFields()[c])));
				rec.getCol().add(col);
			}
			resultSet.getRec().add(rec);
		}
		resultSet.setRecNum(BigInteger.valueOf(bean.getRecords().length));
		root.setResultset(resultSet);

		// effettuo il marshalling direttamente su file, l'implementazione
		// precedente generava l'xml su un'unica linea, questo mi dava problemi
		// poi nel merging delle esportazioni delle liste, olatre al fatto che
		// il file generato non era indentato
		marshal(root, file);
	}

	/**
	 * Effettua il marshalling generando una stringa contenente l'xml di interesse
	 * 
	 * @param root
	 * @return
	 * @throws Exception
	 */
	public String marshal(DataExport root) throws Exception {
		JAXBContext cont = JAXBContext.newInstance(DataExport.class);
		Marshaller marsh = cont.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		XMLSerializer serializer = this.getXMLSerializer(output);
		marsh.marshal(root, serializer.asContentHandler());
		String ret = new String(output.toByteArray());
		return ret;
	}

	/**
	 * Effettua il marshalling direttamente su file
	 * 
	 * @param root
	 * @param destFile
	 * @throws Exception
	 */
	public void marshal(DataExport root, File destFile) throws Exception {
		JAXBContext cont = JAXBContext.newInstance(DataExport.class);
		Marshaller marsh = cont.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marsh.marshal(root, destFile);
	}

	private XMLSerializer getXMLSerializer(ByteArrayOutputStream output) {
		// configure an OutputFormat to handle CDATA
		OutputFormat of = new OutputFormat();
		// specify which of your elements you want to be handled as CDATA.
		// The use of the '^' between the namespaceURI and the localname
		// seems to be an implementation detail of the xerces code.
		// When processing xml that doesn't use namespaces, simply omit the
		// namespace prefix as shown in the third CDataElement below.
		of.setCDataElements(new String[] { "^page", "^title" });
		// set any other options you'd like
		of.setPreserveSpace(true);
		of.setIndenting(true);
		// create the serializer
		XMLSerializer serializer = new XMLSerializer(of);
		serializer.setOutputByteStream(output);
		return serializer;
	}
}