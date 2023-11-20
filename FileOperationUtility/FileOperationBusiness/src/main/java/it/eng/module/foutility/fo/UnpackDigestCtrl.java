/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.DigestAlgID;
import it.eng.module.foutility.beans.generated.DigestEncID;
import it.eng.module.foutility.beans.generated.InputDigestType;
import it.eng.module.foutility.beans.generated.InputUnpackDigestType;
import it.eng.module.foutility.beans.generated.ResponseFileDigestType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.FileOpMessage;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnpackDigestCtrl extends DigestCtrl {
	
	private static Logger log = LogManager.getLogger(UnpackDigestCtrl.class);

	@Override
	public boolean execute(InputFileBean input,AbstractInputOperationType customInput, OutputOperations output, String requestKey) {

		//analogo al digestCtrl solo che lavora sullo sbustato
		boolean ret=false;
		ResponseFileDigestType response= new ResponseFileDigestType();

		//merge default config with provided data
		InputDigestType idt=null;
		DigestAlgID appliedAlg=getDigestAlgId();
		DigestEncID appliedEnc=getEncoding();
		if(customInput instanceof InputUnpackDigestType){
			idt=((InputDigestType) customInput);
			if(idt!=null){
				appliedAlg=idt.getDigestAlgId();
				appliedEnc=idt.getEncoding();
			}
		}
		String calculated;
		File extracted=(File)output.getProperty(UnpackCtrl.EXTRACTED_FILE);
		log.debug("Metodo execute di UnpackDigestCtrl sul file " + extracted );
		
		if(extracted!=null){
			try {
				calculated = calculate(extracted, appliedAlg, appliedEnc);
				log.debug("Digest " + calculated );
				response.setResult(calculated);
				response.setDigestAlgId(appliedAlg);
				response.setEncoding(appliedEnc);
				response.setVerificationStatus(VerificationStatusType.OK);
			} catch (Exception e) {
				log.fatal("fatal calculating digest",e);
				OutputOperations.addError(response, FileOpMessage.UNPACKDIGEST_OP_ERROR, VerificationStatusType.KO, e.getMessage());
			}
		} else{
			return super.execute(input, customInput, output, requestKey);
		}
		output.addResult(this.getClass().getName(), response);
		return ret;
	}

}
