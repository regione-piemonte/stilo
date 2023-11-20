/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;

public class AppletWindow extends Window {

	protected String appletJarName;
	
	public AppletWindow(String title, String appletJarName){
		setShowTitle(true);		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setAutoSize(true);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setAlign(Alignment.CENTER);		
		//setCanDragResize(false);  
		//MARINA MODIFICA permetto di ridimensionare la finestra
		setCanDragResize(true);  
		setRedrawOnResize(true);
		setAutoDraw(true);
		this.appletJarName = appletJarName;
		setTitle(title);
	}
	
	public static String getSignerAppletMultiJarVersion() {
		String versione = AurigaLayout.getParametroDB("SIGNER_APPLET_MULTI_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getSelCertificatoFirmaAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("SEL_CERTIFICATO_FIRMA_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getFirmaCertificatoAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("FIRMA_CON_CERTIF_INPUT_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getEditAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("EDIT_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	
	public static String getScanAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("SCAN_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getStampaEtichettaJarVersion() {
		String versione = AurigaLayout.getParametroDB("STAMPA_ETICHETTA_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getStampaFileAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("STAMPA_FILE_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getJPedalAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("JPEDAL_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getStatisticheAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("STATISTICHE_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	public static String getPrinterScannerAppletJarVersion() {
		String versione = AurigaLayout.getParametroDB("SEL_STAMPANTE_APPLET_VER");
		if(versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}
	
	
}
