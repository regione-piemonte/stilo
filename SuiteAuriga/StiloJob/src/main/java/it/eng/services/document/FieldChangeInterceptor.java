/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.document.XmlVariabile;

public class FieldChangeInterceptor implements MethodInterceptor {

	private static Logger log = Logger.getLogger(FieldChangeInterceptor.class);

	private List<String> fieldsChanged = new ArrayList<String>();
	
	public FieldChangeInterceptor(List<String> campiModificati) {
		this.fieldsChanged = campiModificati;
	}

	@Override
	public Object invoke(MethodInvocation methodinvocation) throws Throwable {
		String methodName = methodinvocation.getMethod().getName();
		if (methodName.startsWith("set")) {
			if (log.isTraceEnabled()) log.trace("Metodo: " + methodName);
			String fieldName = methodName.replaceAll("^set", "");
			fieldName = fieldName.replaceFirst("^.", StringUtils.lowerCase(fieldName.substring(0, 1)));
			Field field = methodinvocation.getMethod().getDeclaringClass().getDeclaredField(fieldName);
			if (log.isTraceEnabled()) log.trace("Campo: " + field.getName());
			XmlVariabile anXmlVariabile = field.getAnnotation(XmlVariabile.class);
			if (anXmlVariabile != null) {
				String nomeCampoXml = anXmlVariabile.nome();
				if (log.isTraceEnabled()) log.trace("Annotazione XmlVariabile: " + nomeCampoXml);
				if (!fieldsChanged.contains(nomeCampoXml)) fieldsChanged.add(nomeCampoXml);
			}
		}
		
		try {
			Object result = methodinvocation.proceed();

			return result;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<String> getFieldsChanged() {
		return fieldsChanged;
	}
}