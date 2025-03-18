/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface OutputProvider {

	public void saveOutputParameter() throws Exception;
	public boolean getAutoClosePostPrint();
	public String getCallBackAskForClose();
}
