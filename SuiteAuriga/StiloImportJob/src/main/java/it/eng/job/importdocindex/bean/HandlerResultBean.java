/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang.StringUtils;

import it.eng.bean.ExecutionResultBean;
import it.eng.job.importdocindex.constants.ErrorInfoEnum;

public class HandlerResultBean<T> extends ExecutionResultBean<T> {

	private ErrorInfoEnum errorInfo;

	public ErrorInfoEnum getErrorInfo() {
		return (ErrorInfoEnum) additionalInformations.get("errorInfo");
	}

	public void setErrorInfo(ErrorInfoEnum errorInfo) {
		additionalInformations.put("errorInfo", errorInfo);
	}

	public String printErrorInfo() {
		if (errorInfo != null) {
			if (StringUtils.isNotBlank(getMessage())) {
				return String.format("[%s] %s: %s", errorInfo.getCode(), errorInfo.getDescription(), getMessage());
			} else {
				return String.format("[%s] %s", errorInfo.getCode(), errorInfo.getDescription());
			}
		}
		return "";
	}
}
