/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGridField;

public class AttributiDinamiciList extends CustomList {

	public AttributiDinamiciList(String nomeEntita, RecordList attributiAdd) {
		
		super(nomeEntita);
		
		ListGridField[] otherHiddenFields = getOtherHiddenFields();
		final ListGridField[] fields = new ListGridField[attributiAdd.getLength() + otherHiddenFields.length + 1];
		
		Map<Integer, String> mappaLabelRiquadri = new HashMap<Integer, String>();
		Map<Integer, RecordList> mappaAttributiRiquadri = new HashMap<Integer, RecordList>();
		
		if(attributiAdd != null && attributiAdd.getLength() > 0) {
						
			for (int i = 0; i < attributiAdd.getLength(); i++) {				
				
				final Record attr = attributiAdd.get(i);		
				
				if(mappaLabelRiquadri.get(new Integer(attr.getAttribute("numero"))) == null) {
					if(attr.getAttribute("labelRiquadro") != null && !"".equals(attr.getAttribute("labelRiquadro"))) {
						mappaLabelRiquadri.put(new Integer(attr.getAttribute("numero")), attr.getAttribute("labelRiquadro"));
					} 										
				}
				
				if(mappaLabelRiquadri.get(new Integer(attr.getAttribute("numero"))) != null) {
					RecordList recordList = mappaAttributiRiquadri.get(new Integer(attr.getAttribute("numero")));
					if(recordList == null) {
						recordList = new RecordList();					
					}				
					recordList.add(attr);
					mappaAttributiRiquadri.put(new Integer(attr.getAttribute("numero")), recordList);
				}
				
				if("DATE".equals(attr.getAttribute("tipo"))) {
					fields[i] = buildDateField(attr);								
				} else if("DATETIME".equals(attr.getAttribute("tipo"))) {				
					fields[i] = buildDateTimeField(attr);	
				} else if("TEXT".equals(attr.getAttribute("tipo"))) {
					fields[i] = buildTextField(attr);	
				} else if("TEXT-AREA".equals(attr.getAttribute("tipo"))) {
					fields[i] = buildTextAreaField(attr);						
				} else if("CHECK".equals(attr.getAttribute("tipo"))) {
					fields[i] = buildCheckField(attr);	
				} else if("INTEGER".equals(attr.getAttribute("tipo"))) {
					fields[i] = buildIntegerField(attr);	
				} else if("EURO".equals(attr.getAttribute("tipo"))) {
					fields[i] = buildEuroField(attr);	
				} else if("DECIMAL".equals(attr.getAttribute("tipo"))) {
					fields[i] = buildDecimalField(attr);	
				} else if("LISTA".equals(attr.getAttribute("tipo"))) {					
					fields[i] = buildListaField(attr);
				} else if("COMBO-BOX".equals(attr.getAttribute("tipo"))) {						
					fields[i] = buildComboBoxField(attr);	
				}													
				
			}
			
		}
		
		ListGridField rowId = new ListGridField("rowId");
		rowId.setHidden(true);						
		rowId.setCanHide(false);
		
		fields[attributiAdd.getLength()] = rowId;
		
		for(int i = 0; i < otherHiddenFields.length; i++) {
			
			fields[attributiAdd.getLength() + i + 1] = otherHiddenFields[i];
			
		}
		
		setFields(fields);
		
		List<Integer> riquadri = new ArrayList<Integer>(mappaAttributiRiquadri.keySet());
		Collections.sort(riquadri);
		
		List<HeaderSpan> headerSpansList = new ArrayList<HeaderSpan>();
			
		for(Integer nroRiquadro : riquadri) {
				
			if(mappaLabelRiquadri.get(nroRiquadro) != null) {
				
				String labelRiquadro = mappaLabelRiquadri.get(nroRiquadro);
							
				if(labelRiquadro.startsWith("*")) {
					labelRiquadro = labelRiquadro.substring(1);
				}
				
				String[] attributiRiquadro = new String[mappaAttributiRiquadri.get(nroRiquadro).getLength()];
				
				for (int i = 0; i < mappaAttributiRiquadri.get(nroRiquadro).getLength(); i++) {				
						
					Record attr = mappaAttributiRiquadri.get(nroRiquadro).get(i);
					attributiRiquadro[i] = attr.getAttribute("nome"); 	
					
				}	
				
				headerSpansList.add(new HeaderSpan(labelRiquadro, attributiRiquadro));
				
			}
			
		}
		
		if(headerSpansList.size() > 0) {
			setHeaderSpans(headerSpansList.toArray(new HeaderSpan[headerSpansList.size()]));
		}	
	
	}
	
	private ListGridField buildDateField(Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));
		field.setType(ListGridFieldType.DATE);
		field.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		return field;
	}
	
	private ListGridField buildDateTimeField(Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));
		field.setType(ListGridFieldType.DATE);
		field.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		return field;
	}
	
	private ListGridField buildTextField(final Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));	
		return field;
	}
	
	private ListGridField buildTextAreaField(Record attr) {
		return new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));		
	}
	
	private ListGridField buildCheckField(Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));
		field.setType(ListGridFieldType.BOOLEAN);		
		return field;
	}
	
	private ListGridField buildIntegerField(Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));
		field.setType(ListGridFieldType.INTEGER);
		field.setAlign(Alignment.CENTER);
		return field;
	}
	
	private ListGridField buildEuroField(Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));
		field.setType(ListGridFieldType.FLOAT);
		field.setAlign(Alignment.CENTER);
		return field;
	}
	
	private ListGridField buildDecimalField(Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));
		field.setType(ListGridFieldType.FLOAT);
		field.setAlign(Alignment.CENTER);
		return field;
	}
	
	private ListGridField buildListaField(Record attr) {
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));
		field.setHidden(true);						
		field.setCanHide(false);
		return field;
	}
	
	private ListGridField buildComboBoxField(Record attr) {
		GWTRestDataSource datasource = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
		datasource.addParam("nomeCombo", attr.getAttribute("nome"));
		ListGridField field = new ListGridField(attr.getAttribute("nome"),  attr.getAttribute("label"));		
		field.setOptionDataSource(datasource);		
		return field;
	}
	
	public ListGridField[] getOtherHiddenFields() {
		
		return new ListGridField[] {};
	}
	
}

