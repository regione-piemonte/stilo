/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public interface FileInputProvider {

	public FileInputResponse getFileInputResponse() throws Exception;
	public String getPin() throws Exception;
	public String getAlias() throws Exception;
	public String getMetodoFirma() throws Exception;
}
