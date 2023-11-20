/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public abstract class FilterBase implements Filter {
	
	protected abstract Logger getLogger();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {	
		@SuppressWarnings("unchecked")
		final Enumeration<String> paramNames = filterConfig.getInitParameterNames();
		while (paramNames.hasMoreElements()) {
			final String paramName = (String) paramNames.nextElement();
			final String paramValue = filterConfig.getInitParameter(paramName);
//			getLogger().debug("Parametro filtro nome: "+paramName);
//			getLogger().debug("Parametro filtro valore: "+paramValue);
			try {

				final Class<?> propertyClass = PropertyUtils.getPropertyDescriptor(this, paramName).getPropertyType();			
				//TODO: implementare gli altri casi
				if ( propertyClass.getName().equalsIgnoreCase("boolean") ) {
					PropertyUtils.setSimpleProperty(this, paramName, Boolean.parseBoolean(paramValue));
				} else if ( propertyClass.getName().equalsIgnoreCase("int") ) {
					PropertyUtils.setSimpleProperty(this, paramName, Integer.parseInt(paramValue));
				} else if ( propertyClass.getName().equalsIgnoreCase("java.lang.String") ) {
					PropertyUtils.setSimpleProperty(this, paramName, paramValue);
				} else if ( propertyClass.getName().equalsIgnoreCase("java.net.URL") ) {
					PropertyUtils.setSimpleProperty(this, paramName, ConvertUtils.convert(paramValue, URL.class));
				} else {
					getLogger().debug("Tipo di valore del parametro del filtro attinente ad una casistica non implementata.");
				}
	
			} catch (ReflectiveOperationException e) {
				if (isConfigProblemFatal()) {
					throw new ServletException(e.getCause());
				}
				getLogger().warn(e.toString());
			} 
		}//while
	}//init
	
	@Override
	public void destroy() {
	}

	protected boolean isConfigProblemFatal() {
		return false;
	}
	
	protected final boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

}
