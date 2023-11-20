/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import com.google.gson.Gson;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OperatoreStringaFullTextMistaFilter;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.module.core.shared.bean.ScadenzaBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterType;
import it.eng.xml.ReflectionUtility;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource da
 * usabile da un DynamicForm
 * @author michele
 * @param <T>
 */
public abstract class AbstractFetchDataSource<T> extends AbstractDataSource<T, T> {

    @Override
    public T get(T bean) throws Exception {
        return null;
    }

    @Override
    public T update(T bean, T oldvalue) throws Exception {
        return null;
    }

    @Override
    public java.util.Map<String, ErrorBean> validate(T bean) throws Exception {
        return null;
    }

    @Override
    public T add(T bean) throws Exception {
        return null;
    }

    @Override
    public T remove(T bean) throws Exception {
        return null;
    }

    @Override
    public abstract PaginatorBean<T> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception;

    public String getNomeEntita() {
        return null;
    }

    public void buildFilterBeanFromCriteria(T bean, AdvancedCriteria criteria) throws Exception {
    	BeanWrapperImpl wrappperBean = BeanPropertyUtility.getBeanWrapper(bean);
        if (criteria != null && criteria.getCriteria() != null) {
            for (Criterion lCriterion : criteria.getCriteria()) {
                buildFilterBeanFromCriterion(bean, lCriterion, wrappperBean);
            }
        }
    }

    protected void buildFilterBeanFromCriterion(T bean, Criterion lCriterion, BeanWrapperImpl wrappperBean) throws Exception {
		// Federico Cacco 14-10-2015
		// Carico filtro tradotto
		FilterConfigurator lFilterConfigurator = getXmlFiltriTradotto();
		//FilterConfigurator lFilterConfigurator = (FilterConfigurator) SpringAppContext.getContext().getBean("FilterConfigurator");
        FilterBean filterBean = (getNomeEntita() != null) ? lFilterConfigurator.getListe().get(getNomeEntita()) : null;
        if (filterBean != null) {
            FilterFieldBean filterFieldBean = null;
            for (FilterFieldBean lFilterFieldBean : filterBean.getFields()) {
                if (lFilterFieldBean.getName().equals(lCriterion.getFieldName())) {
                    filterFieldBean = lFilterFieldBean;
                    break;
                }
            }
            if (filterFieldBean != null) {
                FilterType type = filterFieldBean.getType();
                switch (type) {
                case check:
                	buildCheckFilter(bean, lCriterion, wrappperBean);
                	break;
                case data_senza_ora:
                    buildDateFilter(bean, lCriterion, wrappperBean);
                    break;
                case data_e_ora:
                    buildDateTimeFilter(bean, lCriterion, wrappperBean);
                    break;
                case anno:
                case numero:
                    buildNumberFilter(bean, lCriterion, wrappperBean);
                    break;
                case stringa_full_text:
                case stringa_full_text_restricted:
                case lista_scelta:
                case lista_scelta_estesa:
                case stringa_ricerca_ristretta:
                case stringa_ricerca_estesa_case_insensitive_1:
                case stringa_ricerca_estesa_case_insensitive_2:
                case stringa_ricerca_complessa_1:
                case combo_box:
                    buildTextFilter(bean, lCriterion, wrappperBean);
                    break;
                case stringa_full_text_mista:
                	buildStringaFullTextMistaFilter(bean, lCriterion, wrappperBean);                	
                    break;
                case scadenza:
                    buildScadenza(bean, lCriterion, wrappperBean);
                    break;
                case custom:
                    buildCustomFilter(bean, lCriterion, wrappperBean);
                    break;
                default:
                    buildDefaultFilter(bean, lCriterion, wrappperBean);
                    break;
                }
                if (bean instanceof AbstractBean) {
                    ((AbstractBean) bean).addOperator(lCriterion.getFieldName(), lCriterion.getOperator());
                }
            }
        }
    }
    
    protected void buildCheckFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
    	
    	boolean value;
    	
    	if(criterion.getValue() != null && (criterion.getValue() instanceof Boolean)) {
    		value = (Boolean)criterion.getValue();
    	} else {
        	String valueStr = (String) criterion.getValue();
        	value = valueStr != null && ("TRUE".equals(valueStr) || "1".equals(valueStr) || "SI".equals(valueStr));	
    	}
    	
    	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName(), value);
    	// BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName(), value);
    }
    
    protected Date[] getDateEstesaFilterValue (Criterion criterion) throws Exception {

    	Date[] result = new Date[2];

    	if (criterion.getOperator().equals("lessThan") || criterion.getOperator().equals("greaterThan")
    		|| criterion.getOperator().equals("lessOrEqual") || criterion.getOperator().equals("greaterOrEqual")
    		|| criterion.getOperator().equals("equals")){
    		String data = null;
    		if (criterion.getValue() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getValue();
    			data = lMapValues.get("data");
    		}
    		if (data == null || data.trim().equals("") || parseDate(data) == null) {
    			return result;
    		} else {
    			if (criterion.getOperator().equals("lessThan")) {
    				GregorianCalendar end = new GregorianCalendar();
    				end.setTime(parseDate(data));
    				end.set(Calendar.HOUR_OF_DAY, 0);
    				end.set(Calendar.MINUTE, 0);
    				end.set(Calendar.SECOND, 0);
    				end.set(Calendar.MILLISECOND, 0);
    				end.add(Calendar.DAY_OF_YEAR, -1);
    				result[1] = end.getTime();
    			} else if (criterion.getOperator().equals("greaterThan")) {
    				GregorianCalendar start = new GregorianCalendar();
    				start.setTime(parseDate(data));
    				start.set(Calendar.HOUR_OF_DAY, 0);
    				start.set(Calendar.MINUTE, 0);
    				start.set(Calendar.SECOND, 0);
    				start.set(Calendar.MILLISECOND, 0);
    				start.add(Calendar.DAY_OF_YEAR, 1);
    				result[0] = start.getTime();
    			}else if (criterion.getOperator().equals("equals")) {
        			GregorianCalendar start = new GregorianCalendar();
        			start.setTime(parseDate(data));
        			start.set(Calendar.HOUR_OF_DAY, 0);
        			start.set(Calendar.MINUTE, 0);
        			start.set(Calendar.SECOND, 0);
        			start.set(Calendar.MILLISECOND, 0);
        			result[0] = start.getTime();
        			GregorianCalendar end = new GregorianCalendar();
        			end.setTime(parseDate(data));
        			end.set(Calendar.HOUR_OF_DAY, 23);
        			end.set(Calendar.MINUTE, 59);
        			end.set(Calendar.SECOND, 59);
        			end.set(Calendar.MILLISECOND, 999);
        			result[1] = end.getTime();
        		} else if (criterion.getOperator().equals("lessOrEqual")) {
        			GregorianCalendar end = new GregorianCalendar();
        			end.setTime(parseDate(data));
        			end.set(Calendar.HOUR_OF_DAY, 23);
        			end.set(Calendar.MINUTE, 59);
        			end.set(Calendar.SECOND, 59);
        			end.set(Calendar.MILLISECOND, 999);
        			result[1] = end.getTime();
        		} else if (criterion.getOperator().equals("greaterOrEqual")) {
        			GregorianCalendar start = new GregorianCalendar();
        			start.setTime(parseDate(data));
        			start.set(Calendar.HOUR_OF_DAY, 0);
        			start.set(Calendar.MINUTE, 0);
        			start.set(Calendar.SECOND, 0);
        			start.set(Calendar.MILLISECOND, 0);
        			result[0] = start.getTime();
        		}
    		}
    	} else if (criterion.getOperator().equals("betweenInclusive")) {
    		String dataDa = null;
    		if (criterion.getStart() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getStart();
    			dataDa = lMapValues.get("data");
    		}
    		if (dataDa == null || dataDa.trim().equals("") || parseDate(dataDa) == null) {
    			result[0] = null;
    		} else {
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate(dataDa));
    			start.set(Calendar.HOUR_OF_DAY, 0);
				start.set(Calendar.MINUTE, 0);
				start.set(Calendar.SECOND, 0);
				start.set(Calendar.MILLISECOND, 0);
    			result[0] = start.getTime();
    		}
    		String dataA = null;
    		if (criterion.getEnd() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getEnd();
    			dataA = lMapValues.get("data");
    		}
    		if (dataA == null || dataA.trim().equals("") || parseDate(dataA) == null) {
    			result[1] = null;
    		} else {
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate(dataA));
    			end.set(Calendar.HOUR_OF_DAY, 23);
    			end.set(Calendar.MINUTE, 59);
    			end.set(Calendar.SECOND, 59);
    			end.set(Calendar.MILLISECOND, 999);
    			result[1] = end.getTime();
    		}
    	} else if (criterion.getOperator().equals("lastNDays")) {
    		long nGiorni = 0;
    		if (criterion.getValue() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getValue();
    			if (lMapValues.get("nGiorni") == null) {
        			result[0] = null;
        		} else {
        			nGiorni = Long.parseLong((String) lMapValues.get("nGiorni"));
    				GregorianCalendar start = new GregorianCalendar();
    				Date today = new Date();
    				long diffInMilli = today.getTime() - (nGiorni*24*60*60*1000);

    				start.setTime(new Date(diffInMilli));
    				start.set(Calendar.HOUR_OF_DAY, 0);
    				start.set(Calendar.MINUTE, 0);
    				start.set(Calendar.SECOND, 0);
    				start.set(Calendar.MILLISECOND, 0);
    				result[0] = start.getTime();
        		}
    		} else {
    			result[0] = null;
    		}
    	}
    	return result;
    }
    
    protected Date[] getDateFilterValue(Criterion criterion) throws Exception {
    	Date[] result = new Date[2];
    	if (criterion.getValue() != null && parseDate((String) criterion.getValue()) != null) {
    		if (criterion.getOperator().equals("equals")) {
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate((String) criterion.getValue()));
    			start.set(Calendar.HOUR_OF_DAY, 0);
    			start.set(Calendar.MINUTE, 0);
    			start.set(Calendar.SECOND, 0);
    			start.set(Calendar.MILLISECOND, 0);
    			result[0] = start.getTime();
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate((String) criterion.getValue()));
    			end.set(Calendar.HOUR_OF_DAY, 23);
    			end.set(Calendar.MINUTE, 59);
    			end.set(Calendar.SECOND, 59);
    			end.set(Calendar.MILLISECOND, 999);
    			result[1] = end.getTime();
    		} else if (criterion.getOperator().equals("lessOrEqual")) {
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate((String) criterion.getValue()));
    			end.set(Calendar.HOUR_OF_DAY, 23);
    			end.set(Calendar.MINUTE, 59);
    			end.set(Calendar.SECOND, 59);
    			end.set(Calendar.MILLISECOND, 999);
    			result[1] = end.getTime();
    		} else if (criterion.getOperator().equals("greaterOrEqual")) {
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate((String) criterion.getValue()));
    			start.set(Calendar.HOUR_OF_DAY, 0);
    			start.set(Calendar.MINUTE, 0);
    			start.set(Calendar.SECOND, 0);
    			start.set(Calendar.MILLISECOND, 0);
    			result[0] = start.getTime();
    		} else if (criterion.getOperator().equals("lessThan")) {
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate((String) criterion.getValue()));
    			end.set(Calendar.HOUR_OF_DAY, 23);
    			end.set(Calendar.MINUTE, 59);
    			end.set(Calendar.SECOND, 59);
    			end.set(Calendar.MILLISECOND, 999);
    			end.add(Calendar.DAY_OF_YEAR, -1);
    			result[1] = end.getTime();
    		} else if (criterion.getOperator().equals("greaterThan")) {
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate((String) criterion.getValue()));
    			start.set(Calendar.HOUR_OF_DAY, 0);
    			start.set(Calendar.MINUTE, 0);
    			start.set(Calendar.SECOND, 0);
    			start.set(Calendar.MILLISECOND, 0);
    			start.add(Calendar.DAY_OF_YEAR, 1);
    			result[0] = start.getTime();
    		}
    	} else if ((criterion.getStart() != null && parseDate((String) criterion.getStart()) != null) || (criterion.getEnd() != null  && parseDate((String) criterion.getEnd()) != null)) {
    		if (criterion.getOperator().equals("betweenInclusive")) {
    			if (criterion.getStart() != null && parseDate((String) criterion.getStart()) != null) {
    				String[] startStr = ((String) criterion.getStart()).split("T");
    				GregorianCalendar start = new GregorianCalendar();
    				start.setTime(parseDate(startStr[0]));
    				start.set(Calendar.HOUR_OF_DAY, 0);
    				start.set(Calendar.MINUTE, 0);
    				start.set(Calendar.SECOND, 0);
    				start.set(Calendar.MILLISECOND, 0);
    				result[0] = start.getTime();
    			}
    			if (criterion.getEnd() != null  && parseDate((String) criterion.getEnd()) != null) {
    				String[] endStr = ((String) criterion.getEnd()).split("T");
    				GregorianCalendar end = new GregorianCalendar();
    				end.setTime(parseDate(endStr[0]));
    				end.set(Calendar.HOUR_OF_DAY, 23);
    				end.set(Calendar.MINUTE, 59);
    				end.set(Calendar.SECOND, 59);
    				end.set(Calendar.MILLISECOND, 999);
    				result[1] = end.getTime();
    			}
    		}
    	}
    	return result;
    }

    protected void buildDateFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
        Date[] estremiData = getDateFilterValue(criterion);
        BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
        BeanPropertyUtility.setPropertyValue(bean, wrapperBean, criterion.getFieldName() + "Start", estremiData[0]);
        // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "Start", estremiData[0]);
        BeanPropertyUtility.setPropertyValue(bean, wrapperBean, criterion.getFieldName() + "End", estremiData[1]);
        // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "End", estremiData[1]);
    }

    public Date[] getDateTimeFilterValue(Criterion criterion) throws Exception {
        Date[] result = new Date[2];
        
        if (criterion.getValue() != null && parseDateTime((String) criterion.getValue()) != null) {
            if (criterion.getOperator().equals("equals")) {
                GregorianCalendar start = new GregorianCalendar();
                start.setTime(parseDateTime((String) criterion.getValue()));
                start.set(Calendar.HOUR_OF_DAY, 0);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);
                start.set(Calendar.MILLISECOND, 0);
                result[0] = start.getTime();
                GregorianCalendar end = new GregorianCalendar();
                end.setTime(parseDateTime((String) criterion.getValue()));
                end.set(Calendar.HOUR_OF_DAY, 23);
                end.set(Calendar.MINUTE, 59);
                end.set(Calendar.SECOND, 59);
                end.set(Calendar.MILLISECOND, 999);
                result[1] = end.getTime();
            } else if (criterion.getOperator().equals("lessOrEqual")) {
                GregorianCalendar end = new GregorianCalendar();
                end.setTime(parseDateTime((String) criterion.getValue()));
                end.set(Calendar.HOUR_OF_DAY, 23);
                end.set(Calendar.MINUTE, 59);
                end.set(Calendar.SECOND, 59);
                end.set(Calendar.MILLISECOND, 999);
                result[1] = end.getTime();
            } else if (criterion.getOperator().equals("greaterOrEqual")) {
                GregorianCalendar start = new GregorianCalendar();
                start.setTime(parseDateTime((String) criterion.getValue()));
                start.set(Calendar.HOUR_OF_DAY, 0);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);
                start.set(Calendar.MILLISECOND, 0);
                result[0] = start.getTime();
            } else if (criterion.getOperator().equals("lessThan")) {
                GregorianCalendar end = new GregorianCalendar();
                end.setTime(parseDateTime((String) criterion.getValue()));
                end.set(Calendar.HOUR_OF_DAY, 23);
                end.set(Calendar.MINUTE, 59);
                end.set(Calendar.SECOND, 59);
                end.set(Calendar.MILLISECOND, 999);
                end.add(Calendar.DAY_OF_YEAR, -1);
                result[1] = end.getTime();
            } else if (criterion.getOperator().equals("greaterThan")) {
                GregorianCalendar start = new GregorianCalendar();
                start.setTime(parseDateTime((String) criterion.getValue()));
                start.set(Calendar.HOUR_OF_DAY, 0);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);
                start.set(Calendar.MILLISECOND, 0);
                start.add(Calendar.DAY_OF_YEAR, 1);
                result[0] = start.getTime();
            }
        } else if ((criterion.getStart() != null && parseDateTime((String) criterion.getStart()) != null) || (criterion.getEnd() != null && parseDateTime((String) criterion.getEnd()) != null)) {
            if (criterion.getOperator().equals("betweenInclusive")) {
                if (criterion.getStart() != null && parseDateTime((String) criterion.getStart()) != null) {
                    String[] startStr = ((String) criterion.getStart()).split("T");
                    GregorianCalendar start = new GregorianCalendar();
                    start.setTime(parseDateTime(startStr[0]));
                    start.set(Calendar.HOUR_OF_DAY, 0);
                    start.set(Calendar.MINUTE, 0);
                    start.set(Calendar.SECOND, 0);
                    start.set(Calendar.MILLISECOND, 0);
                    result[0] = start.getTime();
                }
                if (criterion.getEnd() != null && parseDateTime((String) criterion.getEnd()) != null) {
                    String[] endStr = ((String) criterion.getEnd()).split("T");
                    GregorianCalendar end = new GregorianCalendar();
                    end.setTime(parseDateTime(endStr[0]));
                    end.set(Calendar.HOUR_OF_DAY, 23);
                    end.set(Calendar.MINUTE, 59);
                    end.set(Calendar.SECOND, 59);
                    end.set(Calendar.MILLISECOND, 999);
                    result[1] = end.getTime();
                }
            }
        }
        return result;
    }

    protected void buildDateTimeFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
        Date[] estremiData = getDateTimeFilterValue(criterion);
        BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "Start", estremiData[0]);
        // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "Start", estremiData[0]);
        BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "End", estremiData[1]);
        // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "End", estremiData[1]);
    }

    protected Date parseDate(String date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(date);
        } catch (Exception e1) {
        	try { 
        		return displayDateFormat.parse(date);
        	} catch (Exception e2) {}
        }
        return null;
    }

    protected Date parseDateTime(String date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            return dateFormat.parse(date);
        } catch (Exception e1) {
        	try {
            	return displayDateFormat.parse(date);
        	} catch (Exception e2) {}
        }
        return null;
    }

    protected void buildDefaultFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
        if (criterion.getValue() != null) {
            if (criterion.getOperator().equals("equals")) {
            	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName(), criterion.getValue());
                // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName(), criterion.getValue());
            }
        }
    }
    
    protected void buildScadenza(T bean, Criterion lCriterion, BeanWrapperImpl wrappperBean) throws Exception {
        ScadenzaBean lScadenzaBean = new ScadenzaBean();
        BeanWrapperImpl wrapperScadenzaBean = BeanPropertyUtility.getBeanWrapper(lScadenzaBean);
        LinkedHashMap<String, Object> lLinkedHashMap = (LinkedHashMap<String, Object>) lCriterion.getValue();
        for (String lString : lLinkedHashMap.keySet()) {
        	BeanPropertyUtility.setPropertyValue(lScadenzaBean, wrapperScadenzaBean, lString, lLinkedHashMap.get(lString));
            // BeanUtilsBean2.getInstance().setProperty(lScadenzaBean, lString, lLinkedHashMap.get(lString));
        }
        BeanPropertyUtility.setPropertyValue(bean, wrappperBean, "scadenza", lScadenzaBean);
        // BeanUtilsBean2.getInstance().setProperty(bean, "scadenza", lScadenzaBean);
    }

    protected String getTextFilterValue(Criterion criterion) {
        String value = null;
        if (criterion.getValue() != null) {
            if (criterion.getValue() instanceof String) {
                if (criterion.getOperator().equals("iEquals") || criterion.getOperator().equals("equals")
                        || criterion.getOperator().equals("wordsStartWith")) {
                    value = (String) criterion.getValue();
                } else if (criterion.getOperator().equals("iStartsWith") || criterion.getOperator().equals("startsWith")) {
                    value = criterion.getValue() + "%";
                } else if (criterion.getOperator().equals("iEndsWith") || criterion.getOperator().equals("endsWith")) {
                    value = "%" + criterion.getValue();
                } else if (criterion.getOperator().equals("iContains") || criterion.getOperator().equals("contains")
                        || criterion.getOperator().equals("like")) {
                    value = "%" + criterion.getValue() + "%";
                }
                return value;
            } else if (criterion.getValue() instanceof ArrayList) {
                ArrayList<String> values = (ArrayList<String>) criterion.getValue();
                if (criterion.getOperator().equals("equals")) {
                    for (String val : values) {
                        if (value == null) {
                            value = "" + val;
                        } else
                            value += ";" + val;
                    }
                } else if (criterion.getOperator().equals("notEqual")) {
                    for (String val : values) {
                        if (value == null) {
                            value = "!" + val;
                        } else
                            value += ";" + val;
                    }
                }
            }
        }
        return value;
    }

    protected void buildTextFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
    	String value = getTextFilterValue(criterion);
    	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName(), value);
        // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName(), value);
    }
    
    protected void buildStringaFullTextMistaFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
        String value = getValueStringaFullTextMista(criterion);
        BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName(), value);   
        // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName(), value);        
        String operatorValue = getOperatorStringaFullTextMista(criterion);
        String operatorFieldName = "oper" + criterion.getFieldName().substring(0, 1).toUpperCase() + criterion.getFieldName().substring(1);
        BeanPropertyUtility.setPropertyValue(bean, wrappperBean, operatorFieldName, operatorValue);   
        // BeanUtilsBean2.getInstance().setProperty(bean, operatorFieldName, operatorValue);         
    }

    protected void buildNumberFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
        if (criterion.getValue() != null) {
            if (criterion.getOperator().equals("equals")) {
            	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName(), criterion.getValue());
                // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName(), criterion.getValue());
            } else if (criterion.getOperator().equals("lessOrEqual")) {
            	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "End", criterion.getValue());
                // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "End", criterion.getValue());
            } else if (criterion.getOperator().equals("greaterOrEqual")) {
            	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "Start", criterion.getValue());
                // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "Start", criterion.getValue());
            } else if (criterion.getOperator().equals("lessThan")) {
                BigDecimal value = new BigDecimal(String.valueOf(criterion.getValue()));
                value = value.add(new BigDecimal(-1));
                BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "End", value);
                // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "End", value);
            } else if (criterion.getOperator().equals("greaterThan")) {
                BigDecimal value = new BigDecimal(String.valueOf(criterion.getValue()));
                value = value.add(new BigDecimal(1));
                BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "Start", value);
                // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "Start", value);
            }
        } else if (criterion.getStart() != null || criterion.getEnd() != null) {
            if (criterion.getOperator().equals("betweenInclusive")) {
                if (criterion.getStart() != null) {
                	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "Start", criterion.getStart());
                    // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "Start", criterion.getStart());
                }
                if (criterion.getEnd() != null) {
                	BeanPropertyUtility.setPropertyValue(bean, wrappperBean, criterion.getFieldName() + "End", criterion.getEnd());
                    // BeanUtilsBean2.getInstance().setProperty(bean, criterion.getFieldName() + "End", criterion.getEnd());
                }
            }
        }
    }

    protected String[] getNumberFilterValue(Criterion criterion) throws Exception {
        String[] result = new String[2];
        result[0] = null;
        result[1] = null;
        if (criterion.getValue() != null) {
            if (criterion.getOperator().equals("equals")) {
                result[0] = (String) criterion.getValue();
                result[1] = (String) criterion.getValue();
            } else if (criterion.getOperator().equals("lessOrEqual")) {
                result[1] = (String) criterion.getValue();
            } else if (criterion.getOperator().equals("greaterOrEqual")) {
                result[0] = (String) criterion.getValue();
            } else if (criterion.getOperator().equals("lessThan")) {
                int lValue = Integer.valueOf((String) criterion.getValue());
                result[1] = Integer.toString(lValue - 1);
            } else if (criterion.getOperator().equals("greaterThan")) {
                int lValue = Integer.valueOf((String) criterion.getValue());
                result[0] = Integer.toString(lValue + 1);
            }
        } else if (criterion.getStart() != null || criterion.getEnd() != null) {
            if (criterion.getOperator().equals("betweenInclusive")) {
                if (criterion.getStart() != null) {
                    result[0] = (String) criterion.getStart();
                }
                if (criterion.getEnd() != null) {
                    result[1] = (String) criterion.getEnd();
                }
            }
        }
        return result;
    }

    protected String calculateOreMinuti(String oraMinuti) {
        try {
            if (oraMinuti == null)
                return null;
            if (oraMinuti.trim().equals(""))
                return null;
            if (oraMinuti.length() != 4)
                return null;
            String ora = oraMinuti.substring(0, 2);
            String minuti = oraMinuti.substring(2, 4);
            if (ora.startsWith("0"))
                ora = ora.substring(1);
            if (minuti.startsWith("0"))
                minuti = minuti.substring(1);
            int oraInt = Integer.valueOf(ora);
            int minutiInt = Integer.valueOf(minuti);
            if (oraInt >= 0 && oraInt <= 23 && minutiInt >= 0 && minutiInt <= 59)
                return oraMinuti;
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    protected Date[] getDateConOraFilterValue(Criterion criterion) throws Exception {
    	Date[] result = new Date[2];

    	if (criterion.getOperator().equals("lessThan") || criterion.getOperator().equals("greaterThan")) {
    		String oraMinuti = null;
    		String data = null;
    		if (criterion.getValue() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getValue();
    			data = lMapValues.get("data");
    			oraMinuti = lMapValues.get("oraMinuti");
    			oraMinuti = calculateOreMinuti(oraMinuti);
    		}
    		if (data == null || data.trim().equals("") || parseDate(data) == null) {
    			return result;
    		} else {
    			if (criterion.getOperator().equals("lessThan")) {
    				if (oraMinuti == null)
    					oraMinuti = "2359";
    				String ora = oraMinuti.substring(0, 2);
    				String minuti = oraMinuti.substring(2, 4);
    				if (ora.startsWith("0"))
    					ora = ora.substring(1);
    				if (minuti.startsWith("0"))
    					minuti = minuti.substring(1);
    				int oraInt = Integer.valueOf(ora);
    				int minutiInt = Integer.valueOf(minuti);
    				GregorianCalendar end = new GregorianCalendar();
    				end.setTime(parseDate(data));
    				end.set(Calendar.HOUR_OF_DAY, oraInt);
    				end.set(Calendar.MINUTE, minutiInt);
    				result[1] = end.getTime();
    			} else if (criterion.getOperator().equals("greaterThan")) {
    				if (oraMinuti == null)
    					oraMinuti = "0000";
    				String ora = oraMinuti.substring(0, 2);
    				String minuti = oraMinuti.substring(2, 4);
    				if (ora.startsWith("0"))
    					ora = ora.substring(1);
    				if (minuti.startsWith("0"))
    					minuti = minuti.substring(1);
    				int oraInt = Integer.valueOf(ora);
    				int minutiInt = Integer.valueOf(minuti);
    				GregorianCalendar start = new GregorianCalendar();
    				start.setTime(parseDate(data));
    				start.set(Calendar.HOUR_OF_DAY, oraInt);
    				start.set(Calendar.MINUTE, minutiInt);
    				result[0] = start.getTime();
    			}
    		}
    	} else if (criterion.getOperator().equals("betweenInclusive")) {
    		String oraMinutiDa = null;
    		String dataDa = null;
    		if (criterion.getStart() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getStart();
    			dataDa = lMapValues.get("data");
    			oraMinutiDa = lMapValues.get("oraMinuti");
    			oraMinutiDa = calculateOreMinuti(oraMinutiDa);
    		}
    		if (dataDa == null || dataDa.trim().equals("") || parseDate(dataDa) == null) {
    			result[0] = null;
    		} else {
    			if (oraMinutiDa == null)
    				oraMinutiDa = "0000";
    			String ora = oraMinutiDa.substring(0, 2);
    			String minuti = oraMinutiDa.substring(2, 4);
    			if (ora.startsWith("0"))
    				ora = ora.substring(1);
    			if (minuti.startsWith("0"))
    				minuti = minuti.substring(1);
    			int oraInt = Integer.valueOf(ora);
    			int minutiInt = Integer.valueOf(minuti);
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate(dataDa));
    			start.set(Calendar.HOUR_OF_DAY, oraInt);
    			start.set(Calendar.MINUTE, minutiInt);
    			result[0] = start.getTime();
    		}
    		String oraMinutiA = null;
    		String dataA = null;
    		if (criterion.getEnd() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getEnd();
    			dataA = lMapValues.get("data");
    			oraMinutiA = lMapValues.get("oraMinuti");
    			oraMinutiA = calculateOreMinuti(oraMinutiA);
    		}
    		if (dataA == null || dataA.trim().equals("") || parseDate(dataA) == null) {
    			result[1] = null;
    		} else {
    			if (oraMinutiA == null)
    				oraMinutiA = "2359";
    			String ora = oraMinutiA.substring(0, 2);
    			String minuti = oraMinutiA.substring(2, 4);
    			if (ora.startsWith("0"))
    				ora = ora.substring(1);
    			if (minuti.startsWith("0"))
    				minuti = minuti.substring(1);
    			int oraInt = Integer.valueOf(ora);
    			int minutiInt = Integer.valueOf(minuti);
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate(dataA));
    			end.set(Calendar.HOUR_OF_DAY, oraInt);
    			end.set(Calendar.MINUTE, minutiInt);
    			result[1] = end.getTime();
    		}
    	} else if (criterion.getOperator().equals("lastNDays")) {
    		long nGiorni = 0;
    		if (criterion.getValue() != null) {
    			Map<String, String> lMapValues = (Map<String, String>) criterion.getValue();
    			if (lMapValues.get("nGiorni") == null) {
        			result[0] = null;
        		} else {
        			nGiorni = Long.parseLong((String) lMapValues.get("nGiorni"));
    				String oraMinuti = "0000";
    	    		String ora = oraMinuti.substring(0, 2);
    	    		String minuti = oraMinuti.substring(2, 4);
    	    		if (ora.startsWith("0"))
    	    			ora = ora.substring(1);
    	    		if (minuti.startsWith("0"))
    	    			minuti = minuti.substring(1);
    	    		int oraInt = Integer.valueOf(ora);
    	    		int minutiInt = Integer.valueOf(minuti);

    	    		GregorianCalendar start = new GregorianCalendar();
    	    		Date today = new Date();
    	    		long diffInMilli = today.getTime() - (nGiorni*24*60*60*1000);
    	    		start.setTime(new Date(diffInMilli));
    	    		start.set(Calendar.HOUR_OF_DAY, oraInt);
    	    		start.set(Calendar.MINUTE, minutiInt);
    	    		result[0] = start.getTime();
        		}
    		} else {
    			result[0] = null;
    		}
    		
    	}
    	return result;
    }
    
    protected void buildCustomFilter(T bean, Criterion criterion, BeanWrapperImpl wrappperBean) throws Exception {
    	
    }

    protected String getValueStringaFullTextMista(Criterion crit) {
        if (crit != null && crit.getValue() != null) {
            Map map = (Map) crit.getValue();
            String parole = (String) map.get("parole");
            if(parole != null && !"".equals(parole.trim())) {
	            if (crit.getOperator() != null && "like".equals(crit.getOperator())) {
	                parole = "%" + parole + "%";
	            }
	            return parole;
            }            
        }
        return null;
    }

    protected String getOperatorStringaFullTextMista(Criterion crit) {
        if (crit != null && StringUtils.isNotBlank(crit.getOperator())) {
        	OperatoreStringaFullTextMistaFilter operator = OperatoreStringaFullTextMistaFilter.getFromOperator(crit.getOperator());
        	if(operator != null) {
        		return operator.getDbValue();
        	}
        }
        return null;
    }
    
    protected String getTipoAttributiCustomDelTipoFilter(Criterion crit) {
        if (crit != null && crit.getValue() != null) {
    		Map map = (Map) crit.getValue();
            return (String) map.get("tipo");                      
        }
        return null;
    }
    
    protected AdvancedCriteria getCriteriaAttributiCustomDelTipoFilter(Criterion crit) {
        if (crit != null && crit.getValue() != null) {
    		Map map = (Map) crit.getValue();
    		if(map.get(("filtriCustom")) != null) {
    			Gson gson = GsonBuilderFactory.getIstance().create();
        		return gson.fromJson((String) map.get("filtriCustom"), AdvancedCriteria.class);	
    		}    		
        }
        return null;
    }
    
    protected Map<String,String> getMappaTipiAttributiCustomDelTipoFilter(Criterion crit) {
        if (crit != null && crit.getValue() != null) {
    		Map map = (Map) crit.getValue();
    		if(map.get(("tipiAttributiCustom")) != null) {
        		return (HashMap<String, String>) map.get("tipiAttributiCustom");
    		}
        }
        return new HashMap<String, String>();
    }
    
    protected <E> String[] getColAndFlgDescOrderBy(Class<E> xmlBean, HashSet<String> setNumColonneOrdinabili) throws Exception {
		 
		String orderBy = getExtraparams().get("orderBy");
		
		String colOrderBy = null;
		String flgDescOrderBy = null;
		
		// Cerco tutti i nomi delle colonne ordinate nel bean di riferimento e individuo il rispettovo numero di colonna
		if (StringUtils.isNotBlank(orderBy)) {
			// Leggo i campi del bean di riferimento
			Field[] xmlBeanFields = ReflectionUtility.getFields(xmlBean);	    			  			
			StringTokenizer st = new StringTokenizer(orderBy, ";");
		    while (st.hasMoreTokens()) {
		    	String fieldOrd = st.nextToken();
		    	if (StringUtils.isNotBlank(fieldOrd)) {
		    		// Cerco il nome della colonna ordinata nel bean di riferimento
					for (Field field : xmlBeanFields) {				        
						NumeroColonna lNumeroColonna = (NumeroColonna)field.getAnnotation(NumeroColonna.class);
				        if (lNumeroColonna != null) {		
				        	String fieldName = fieldOrd.substring(1);
				        	// Se il nome della colonna corrisponde con quella della colonna ordinata prendo il numero della colonna
							if (fieldName.equalsIgnoreCase(field.getName())){
								String flgDesc = fieldOrd.startsWith("-") ? "1" : "0";
					        	// Se il numero colonna e' nella lista dei numeri colonne ordinabili della store	
								if (setNumColonneOrdinabili == null || setNumColonneOrdinabili.contains(lNumeroColonna.numero())) {
									if (colOrderBy == null) {
										colOrderBy = lNumeroColonna.numero();
										flgDescOrderBy = flgDesc;
									} else {
										colOrderBy += ";" + lNumeroColonna.numero();
										flgDescOrderBy += ";" + flgDesc;
									}
							    }								   
							}
						}
					}
				}	    	
		    }
		}
		
		String[] colAndFlgDescOrderBy = new String[2];
		colAndFlgDescOrderBy[0] = colOrderBy;
		colAndFlgDescOrderBy[1] = flgDescOrderBy;
		
		return colAndFlgDescOrderBy;
	}	

}