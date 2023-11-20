/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

import io.swagger.core.filter.AbstractSpecFilter;
import io.swagger.models.Model;
import io.swagger.models.properties.Property;

/**
 * Classe non utilizzata. Eventualmente è da usare in questo modo:
 *     <servlet>
 *         <servlet-name>Jersey2Config</servlet-name>
 *         <servlet-class>io.swagger.jaxrs.config.DefaultJaxrsConfig</servlet-class>
 *         <init-param>
 *             <param-name>swagger.filter</param-name>
 *             <param-value>it.eng.aurigamailbusiness.rest.resource.AurigaMailSpecFilter</param-value>
 *         </init-param>
 *        <load-on-startup>2</load-on-startup>
 *    </servlet>
 */
public class AurigaMailSpecFilter extends AbstractSpecFilter {

	@Override
	public boolean isPropertyAllowed(
			              Model model, 
			              Property property, 
			              String propertyName,
			              Map<String, List<String>> params, 
			              Map<String, String> cookies, 
			              Map<String, List<String>> headers
    ) {
//        if ( property.getAccess().equalsIgnoreCase("out") ) {
//        	return false;
//        }
        return true;
	}
	
}
