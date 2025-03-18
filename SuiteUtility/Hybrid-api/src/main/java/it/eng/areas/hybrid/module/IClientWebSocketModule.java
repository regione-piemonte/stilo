/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public interface IClientWebSocketModule extends IClientModule {
	
    void onMessage(String message) throws Exception;

    void onClose(int code, String reason, boolean initiatedByRemote);

    void onException(Exception e);

}
