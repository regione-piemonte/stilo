package it.eng.utility.client.contabilia;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.PropertyPlaceholderHelper;

public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private Map<String, String> resolvedProps;
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		// richiamo la logica della superclasse
		super.processProperties(beanFactoryToProcess, props);
		
		// aggiungo nella mappa le proprietà
		resolvedProps = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String valueStr = resolvePlaceholder(keyStr, props);
			
			PropertyPlaceholderHelper pph = new PropertyPlaceholderHelper(this.DEFAULT_PLACEHOLDER_PREFIX, this.DEFAULT_PLACEHOLDER_SUFFIX);
			String value = pph.replacePlaceholders(valueStr, props);
			resolvedProps.put(keyStr, value);
		}
	}
	
	@Override
	protected String resolvePlaceholder(String placeholder, Properties props) {
		return super.resolvePlaceholder(placeholder, props);
	}
	
	/**
	 * la mappa con le proprietà del file xml di configurazione
	 */
	public Map<String, String> getResolvedProps() {
		return Collections.unmodifiableMap(resolvedProps);
	}
	
}
