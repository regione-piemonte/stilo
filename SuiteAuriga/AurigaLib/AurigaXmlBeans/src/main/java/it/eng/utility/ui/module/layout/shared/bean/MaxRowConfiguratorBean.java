/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

public class MaxRowConfiguratorBean {

	private static HashMap<String, Integer> configurazioni;
	private static Integer defaultValue;

	public static void setConfigurazioni(HashMap<String, Integer> configurazioni) {
		MaxRowConfiguratorBean.configurazioni = configurazioni;
	}

	public static HashMap<String, Integer> getConfigurazioni() {
		return configurazioni;
	}

	public static void setDefaultValue(Integer defaultValue) {
		MaxRowConfiguratorBean.defaultValue = defaultValue;
	}

	public static Integer getDefaultValue() {
		return defaultValue;
	}
}
