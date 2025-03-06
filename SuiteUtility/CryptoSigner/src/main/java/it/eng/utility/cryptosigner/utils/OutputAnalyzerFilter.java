package it.eng.utility.cryptosigner.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;

public class OutputAnalyzerFilter {

	private static List<String> defaultOutputProperties;

	public List<String> acceptedOutputs;

	private static final Logger log = Logger.getLogger(OutputAnalyzerFilter.class);

	static {
		Field[] outputFields = OutputSignerBean.class.getFields();
		if (outputFields != null) {
			OutputSignerBean tmpOutput = new OutputSignerBean();
			defaultOutputProperties = new ArrayList<String>();
			for (Field field : outputFields) {
				if (field.getType().isAssignableFrom(String.class))
					try {
						defaultOutputProperties.add((String) field.get(tmpOutput));
					} catch (IllegalArgumentException e) {
						log.warn("Eccezione OutputAnalyzerFilter", e);
					} catch (IllegalAccessException e) {
						log.warn("Eccezione OutputAnalyzerFilter", e);
					}
			}
		}

	}

	public OutputAnalyzerFilter() {
		this.acceptedOutputs = new ArrayList<String>();
		if (defaultOutputProperties != null) {
			for (String defaultOutputProperty : defaultOutputProperties) {
				acceptedOutputs.add(defaultOutputProperty);
			}
		}
	}

	public void acceptOutput(String acceptedOutput) {
		if (defaultOutputProperties.contains(acceptedOutput) && !acceptedOutputs.contains(acceptedOutput))
			acceptedOutputs.add(acceptedOutput);
	}

	public void filterOutput(String filteredOutput) {
		if (defaultOutputProperties.contains(filteredOutput) && acceptedOutputs.contains(filteredOutput))
			acceptedOutputs.remove(filteredOutput);
	}

	public void acceptsOutputs(String[] newAcceptedOutputs) {
		if (newAcceptedOutputs != null) {
			for (String newAcceptedOutput : newAcceptedOutputs) {
				acceptOutput(newAcceptedOutput);
			}
		}
	}

	public void filterOutputs(String[] filteredOutputs) {
		if (filteredOutputs != null) {
			for (String filteredOutput : filteredOutputs) {
				filterOutput(filteredOutput);
			}
		}
	}

	public boolean isAcceptedOutput(String output) {
		return acceptedOutputs.contains(output);
	}

	public boolean areAcceptedOutputs(String[] outputs) {
		if (outputs != null) {
			for (String output : outputs) {
				if (!isAcceptedOutput(output))
					return false;
			}
		}
		return true;
	}
}
