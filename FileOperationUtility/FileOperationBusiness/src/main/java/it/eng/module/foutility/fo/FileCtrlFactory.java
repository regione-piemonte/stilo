/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FileCtrlFactory {

	public static CtrlBuilder getCtrlBuilder(){
		return new SpringCtrlBuilder();
	}
}
