package it.eng.hybrid.module.wordOpener.file;

import it.eng.hybrid.module.wordOpener.config.WordOpenerConfigManager;
import it.eng.hybrid.module.wordOpener.config.WordOpenerConfigManager.TIPO_ENCODING;
import it.eng.hybrid.module.wordOpener.exception.InitException;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class ComparatorFileUtils {

	private static Logger mLogger = Logger.getLogger(ComparatorFileUtils.class);
	
	public static boolean isChangedFile(InputStream lInputStrem) throws InitException, IOException{
		mLogger.debug("Verifico l'impronta attuale con quella originale");
		byte[] ret= calcolaImpronta(lInputStrem);
		String lStrigResult = null;
		TIPO_ENCODING lEncoding = WordOpenerConfigManager.getTipoEncoding();
		if (lEncoding == TIPO_ENCODING.HEX) lStrigResult=Hex.encodeHexString(ret);
		else if (lEncoding == TIPO_ENCODING.BASE64) lStrigResult = Base64.encodeBase64String(ret);
		else throw new IOException("Codec non supportato " + lEncoding);
		mLogger.debug("L'impronta attuale vale " + lStrigResult);
		mLogger.debug("L'impronta originale vale " + WordOpenerConfigManager.getImpronta());
		boolean uguale = lStrigResult.equals(WordOpenerConfigManager.getImpronta());
		mLogger.debug("Sono uguali " + uguale);
		return !uguale;
	}

	private static byte[] calcolaImpronta(InputStream lInputStrem) throws InitException, IOException {
		mLogger.debug("Tipo Impronta vale " + WordOpenerConfigManager.getTipoImpronta().getRealValue());
		switch (WordOpenerConfigManager.getTipoImpronta()) {
		case SHA1:
			return DigestUtils.sha(lInputStrem);
		case SHA256:
			return DigestUtils.sha256(lInputStrem);
		default:
			return null;
		}
		
	}
}
