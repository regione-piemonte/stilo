/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.utility.ui.module.core.server.bean.Criterion;

public class CriteriPersonalizzatiUtil {
	
	public enum TipoFiltro {
		DATA_SENZA_ORA, DATA, STRINGA, NUMERO, CHECK
	}
	
	public static final String  FMT_STD_DATA = "dd/MM/yyyy";
	public static final String  FMT_STD_TIMESTAMP = "dd/MM/yyyy HH:mm";
	
	public static TipoFiltro getTipoFiltroFromTipoAttributoCustom(String tipo) throws Exception {
		TipoFiltro tipoFiltro = TipoFiltro.STRINGA;
		if(tipo != null) {
			if("DATE".equals(tipo)) {
				tipoFiltro = TipoFiltro.DATA_SENZA_ORA;	
			} else if("DATETIME".equals(tipo)) {				
				tipoFiltro = TipoFiltro.DATA;
			} else if("NUMBER".equals(tipo) || "EURO".equals(tipo)) {
				tipoFiltro = TipoFiltro.NUMERO;
			} else if("CHECK".equals(tipo)) {
				tipoFiltro = TipoFiltro.CHECK;
			}
		}
		return tipoFiltro;
	}
	
	public static CriteriPersonalizzati getCriterioPersonalizzato(String attrName, String operator, String value, TipoFiltro tipo) throws Exception {
		Criterion criterion = new Criterion();
		criterion.setFieldName(attrName);
		criterion.setOperator(operator);
		criterion.setValue(value);		
		return getCriterioPersonalizzato(attrName, criterion, tipo);
	}
	
	public static CriteriPersonalizzati getCriterioPersonalizzato(String attrName, Criterion criterion) throws Exception {
		return getCriterioPersonalizzato(attrName, criterion, TipoFiltro.STRINGA);
	}
	
	public static CriteriPersonalizzati getCriterioPersonalizzato(String attrName, Criterion criterion, TipoFiltro tipo) throws Exception {
		CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
		criteriPersonalizzati.setAttrName(attrName);
		criteriPersonalizzati.setOperator(getDecodeCritOperator(criterion.getOperator()));
		String valore = (criterion.getValue() != null) ? getStringValue(criterion.getValue(), tipo) : null;
		if(StringUtils.isNotBlank(valore)) {
			if (criterion.getOperator().equalsIgnoreCase("iStartsWith") || criterion.getOperator().equalsIgnoreCase("startsWith")) {
				criteriPersonalizzati.setValue1(valore + "%");						
			} else if(criterion.getOperator().equalsIgnoreCase("iEndsWith") || criterion.getOperator().equalsIgnoreCase("endsWith")) {
				criteriPersonalizzati.setValue1("%" + valore);					
			} else if(criterion.getOperator().equalsIgnoreCase("iContains") || criterion.getOperator().equalsIgnoreCase("contains") || criterion.getOperator().equalsIgnoreCase("like")) {
				criteriPersonalizzati.setValue1("%" + valore + "%");
			} else {
				criteriPersonalizzati.setValue1(valore);
			}			
			// per i tipi check non devo passare il valore ma solo l'operatore ("spuntato" o "non spuntato")
			if(tipo == TipoFiltro.CHECK) {
//				boolean checked = criteriPersonalizzati.getValue1() != null && "1".equals(criteriPersonalizzati.getValue1());
//				if(checked) {
//					criteriPersonalizzati.setOperator("spuntato");				
//					criteriPersonalizzati.setValue1(null);
//					return criteriPersonalizzati;
//				}
//				return null;
				if("1".equals(criteriPersonalizzati.getValue1())) {
					criteriPersonalizzati.setOperator("spuntato");
					criteriPersonalizzati.setValue1(null);
					return criteriPersonalizzati;
				} else if("0".equals(criteriPersonalizzati.getValue1())) {
					criteriPersonalizzati.setOperator("non spuntato");
					criteriPersonalizzati.setValue1(null);
					return criteriPersonalizzati;
				}
				return null;
			}			
		} else {
			String valore1 = (criterion.getStart() != null) ? getStringValue(criterion.getStart(), tipo) : null;
			String valore2 = (criterion.getEnd() != null) ? getStringValue(criterion.getEnd(), tipo) : null;
			if(StringUtils.isNotBlank(valore1) || StringUtils.isNotBlank(valore2)) {
				if (criterion.getOperator().equalsIgnoreCase("betweenInclusive")) {
					criteriPersonalizzati.setValue1(valore1);					
					criteriPersonalizzati.setValue2(valore2);							
				}					
			}			
		}
		if(StringUtils.isNotBlank(criteriPersonalizzati.getValue1()) || StringUtils.isNotBlank(criteriPersonalizzati.getValue2())) {
			return criteriPersonalizzati;
		}
		return null;		
	}
	
	private static String getStringValue(Object value, TipoFiltro tipo) throws Exception {
		if(value == null) return null;
		if(tipo == TipoFiltro.DATA_SENZA_ORA) {
			Date dateValue = parseDate((String) value);
			return new SimpleDateFormat(FMT_STD_DATA).format(dateValue);
		} else if(tipo == TipoFiltro.DATA) {
			Date dateTimeValue = parseDateTime((String) value);
			return new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dateTimeValue);
		} else if(tipo == TipoFiltro.CHECK) {
			if(value instanceof Boolean) {
				Boolean boolValue = (Boolean) value; 
				return (boolValue != null && boolValue) ? "1" : "0";
			} else if(value instanceof String) {
				String strValue = (String) value;
				return (strValue.equalsIgnoreCase("true") || strValue.equals("1") || strValue.equalsIgnoreCase("si")) ? "1" : "0";
			}
		} else if(value instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) value;
			return (String) map.get("parole");			
		}
		return (String) value;
	}
	
	public static String getDecodeCritOperator(String operator){
		if(StringUtils.isNotBlank(operator)) 
		{
			if(operator.equalsIgnoreCase("iContains")){
				return "simile a (case-insensitive)";
			}
			else if (operator.equalsIgnoreCase("iEquals")){
				return "simile a (case-insensitive)";
			}
			else if (operator.equalsIgnoreCase("iStartsWith")){
				return "simile a (case-insensitive)";
			}
			else if (operator.equalsIgnoreCase("iEndsWith")){
				return "simile a (case-insensitive)";
			}
			else if (operator.equalsIgnoreCase("equals")){
				return "uguale";
			}
			else if (operator.equalsIgnoreCase("greaterThan")){
				return "maggiore";
			}
			else if (operator.equalsIgnoreCase("greaterOrEqual")){
				return "maggiore o uguale";
			}
			else if (operator.equalsIgnoreCase("lessThan")){
				return "minore";
			}
			else if (operator.equalsIgnoreCase("lessOrEqual")){
				return "minore o uguale";
			}
			else if (operator.equalsIgnoreCase("betweenInclusive")){
				return "tra";
			}
			else if (operator.equalsIgnoreCase("wordsStartWith")){
				return "contains";
			}
			else if (operator.equalsIgnoreCase("like")){
				return "simile a (case-insensitive)";
			}
		}
		return null;
	}
	
	private static Date parseDate(String date) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");	
		try {
			return dateFormat.parse(date);
		} catch (Exception e) {
			return displayDateFormat.parse(date);
		}		
	}
	
	private static Date parseDateTime(String date) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
		SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		try {
			return dateFormat.parse(date);
		} catch (Exception e) {
			return displayDateFormat.parse(date);
		}				
	}
	
}
