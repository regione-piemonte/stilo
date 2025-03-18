/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public final class ClientDataThreadLocal {

	private static final ThreadLocal<Map<String, String>> tl = new ThreadLocal<Map<String, String>>();

	public static void set(Map<String, String> clientData) {
		tl.set(clientData);
	}

	public static Map<String, String> get() {
		return (Map<String, String>) tl.get();
	}

	public static void clear() {
		tl.remove();
	}
}
