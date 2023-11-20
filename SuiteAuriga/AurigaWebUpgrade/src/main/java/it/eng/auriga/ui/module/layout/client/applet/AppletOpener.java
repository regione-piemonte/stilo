/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AppletOpener {
	
	public static native void open(String url, String features) /*-{
		$wnd.appletWindow = $wnd.showModalDialog(url, window.$wnd, features);
	}-*/;
	
	public static native void close() /*-{
	$wnd.appletWindow.close();
}-*/;
	
	public static native void reinitAppletFirma(String nuoviParametri)/*-{
		$wnd.appletFirma.reinitToSign(nuoviParametri);
	}-*/;
}
