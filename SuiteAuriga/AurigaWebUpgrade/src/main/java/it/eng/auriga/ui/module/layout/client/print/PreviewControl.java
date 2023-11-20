/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewImageWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowPageSelectionCallback;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/*
 * Classe generale per la gestione delle preview dei file
 */

public class PreviewControl {
	
	/**
	 * Metodo statico per lo switch della visualizzazione del tipo di file: 1) File di tipo Image -> PreviewImageWindow 2) File non di tipo Image ->
	 * PreviewWindow
	 * 
	 * @param uri
	 * @param remoteUri
	 * @param info
	 * @param recordType
	 * @param filename
	 * @param previewWindowPageSelectionCallback
	 * @param isReadOnly
	 * @param isFromOmissis
	 */
	public static void switchPreview(String uri, Boolean remoteUri, InfoFileRecord info, String recordType, String filename, PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback, boolean isReadOnly, boolean enableOptionToSaveInOmissisForm) {

		switchPreview(uri, remoteUri, info, recordType, filename, previewWindowPageSelectionCallback, isReadOnly, enableOptionToSaveInOmissisForm, true);
	}
	
	/**
	 * Metodo statico per lo switch della visualizzazione del tipo di file: 1) File di tipo Image -> PreviewImageWindow 2) File non di tipo Image ->
	 * PreviewWindow
	 * 
	 * @param uri
	 * @param remoteUri
	 * @param info
	 * @param recordType
	 * @param filename
	 * @param previewWindowPageSelectionCallback
	 * @param isReadOnly
	 * @param isFromOmissis
	 * @param isShowModal 
	 */
	public static void switchPreview(String uri, Boolean remoteUri, InfoFileRecord info, String recordType, String filename, PreviewWindowPageSelectionCallback previewWindowPageSelectionCallback, boolean isReadOnly, boolean enableOptionToSaveInOmissisForm, final boolean isShowModal) {
		
		if (isFileImage(info) && !enableOptionToSaveInOmissisForm) {
			new PreviewImageWindow(uri, remoteUri, info);
		} else {
			new PreviewWindow(uri, remoteUri, info, recordType, filename, previewWindowPageSelectionCallback, isReadOnly, enableOptionToSaveInOmissisForm) {
				@Override
				public boolean isModal() {
					// TODO Auto-generated method stub
					return isShowModal;
				}
			};
		}
	}

	/**
	 * Metodo statico per lo switch della visualizzazione del tipo di file: 1) File di tipo Image -> PreviewImageWindow 2) File non di tipo Image ->
	 * PreviewWindow
	 * 
	 * @param uri
	 * @param remoteUri
	 * @param info
	 * @param recordType
	 * @param filename
	 */
	public static void switchPreview(String uri, Boolean remoteUri, InfoFileRecord info, String recordType, String filename) {

		switchPreview(uri, remoteUri, info, recordType, filename, true);
	}
	
	/**
	 * Metodo statico per lo switch della visualizzazione del tipo di file: 1) File di tipo Image -> PreviewImageWindow 2) File non di tipo Image ->
	 * PreviewWindow
	 * 
	 * @param uri
	 * @param remoteUri
	 * @param info
	 * @param recordType
	 * @param filename
	 * @param isShowModal 
	 */
	public static void switchPreview(String uri, Boolean remoteUri, InfoFileRecord info, String recordType, String filename, final boolean isShowModal) {
		
		if (isFileImage(info)) {
			new PreviewImageWindow(uri, remoteUri, info);
		} else {
			new PreviewWindow(uri, remoteUri, info, recordType, filename){
				@Override
				public boolean isModal() {
					// TODO Auto-generated method stub
					return isShowModal;
				}
			};
		}
	}

	/**
	 * Metodo di utilita' che restituisce un boolean se il file è di tipo image
	 * 
	 * @param info
	 * @return
	 */
	private static boolean isFileImage(InfoFileRecord info) {
		return info != null && info.getMimetype() != null && info.getMimetype().startsWith("image") && !info.getMimetype().equals("image/tiff");
	}

}
