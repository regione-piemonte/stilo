package it.eng.areas.hybrid.module;


public interface IClientWebSocketModule extends IClientModule {
	
    void onMessage(String message) throws Exception;

    void onClose(int code, String reason, boolean initiatedByRemote);

    void onException(Exception e);

}
