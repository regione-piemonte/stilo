/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.config.AurigaBusinessClientConfig;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class EtichetteThread extends Thread {

	public PingCallback mPingCallback;
	private boolean businessReady;
	private File mFile;
	private static Logger mLogger = Logger.getLogger(EtichetteThread.class);

	public EtichetteThread(PingCallback pPingCallback) throws IOException{
		mPingCallback = pPingCallback;
		mFile = File.createTempFile("tmp", "");

	}
	@Override
	public void run() {

		mLogger.debug("Comincio a pingare la business");
		while (!businessReady){
			mLogger.debug("Ancora non pronta, faccio uno sleep");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				mLogger.warn(e);
			}
			businessReady = pingBusiness();

		}
		try {
			mLogger.debug("Servizio attivo");
			boolean servizioInErrore = true;
			while (servizioInErrore){
				servizioInErrore = callService();
				if (servizioInErrore) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						mLogger.warn(e);
					}
				}
			}

		} catch (Exception e) {
			mLogger.warn(e);
		}
	}
	private boolean callService() {
		try {
			mPingCallback.initAfterBusinessLoad();
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	private boolean pingBusiness() {
		mLogger.debug("Start ping verso " + AurigaBusinessClientConfig.getInstance().getUrl());
		try {
			FileUtils.copyURLToFile(new URL(AurigaBusinessClientConfig.getInstance().getUrl()), mFile, 200, 500);
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			mLogger.debug("Impossibile pingare");
			return false;
		}
		return true;

	}
}
