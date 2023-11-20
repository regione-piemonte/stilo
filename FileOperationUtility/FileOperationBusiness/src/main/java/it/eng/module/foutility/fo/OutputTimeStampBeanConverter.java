/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.EnvelopeType;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult.TimestampVerificationResult;
import it.eng.module.foutility.beans.generated.TimeStampInfotype;
import it.eng.module.foutility.beans.generated.TimeStampInfotype.TsaInfo;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.data.type.SignerType;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/**
 * converte il bean OutputTimeStampBean nella struttura jaxb dedicata
 *
 */
public class OutputTimeStampBeanConverter {
	
	private static Logger log = LogManager.getLogger(OutputTimeStampBeanConverter.class);
	
	/*public static void populateTimeStampVerResult(TimestampVerificationResult timeStampVerificationResult,OutputTimeStampBean  bean){
		
		List<DocumentAndTimeStampInfoBean> infos=bean.getDocumentAndTimeStampInfos();
		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfoBean : infos) {
			TimeStampInfotype tst = new TimeStampInfotype();
			populateTimeStampInfo(tst, documentAndTimeStampInfoBean);
			timeStampVerificationResult.getTimeStampInfo().add(tst);
		}
	}*/
	
	public static void populateTimeStampInfo(TimeStampInfotype infoType,DocumentAndTimeStampInfoBean timeStampInfo, boolean tsaReliability ){
		HashMap validityInfo=timeStampInfo.getValidityInfo();
		if(validityInfo.containsKey(DocumentAndTimeStampInfoBean.PROP_HASH_ALGORITHM)){
			infoType.setHashAlgOID(((ASN1ObjectIdentifier)validityInfo.get(DocumentAndTimeStampInfoBean.PROP_HASH_ALGORITHM)).getId());
		}
		if(validityInfo.containsKey(DocumentAndTimeStampInfoBean.PROP_MILLISECS)){
			infoType.setDateMillisec((String)validityInfo.get(DocumentAndTimeStampInfoBean.PROP_MILLISECS));
		}
		if(validityInfo.containsKey(DocumentAndTimeStampInfoBean.PROP_DATE)){
			Date date=(Date)validityInfo.get(DocumentAndTimeStampInfoBean.PROP_DATE);
			if(date!=null){
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(date);
				XMLGregorianCalendar date2;
				try {
					date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
					infoType.setDate(date2);
				} catch (DatatypeConfigurationException e) {
					log.fatal("fatal converting to xmlcalendar",e);
				}
			}
		}
		if(validityInfo.containsKey(DocumentAndTimeStampInfoBean.PROP_TIMESTAMP_FORMAT)){
			SignerType type=(SignerType)validityInfo.get(DocumentAndTimeStampInfoBean.PROP_TIMESTAMP_FORMAT);
			if(type!=null)
				infoType.setFormat(EnvelopeType.fromValue(type.name()));
		}
		
		
		//aggiungo errori e warning 
		//TODO determianre lo stato tra ERROR SKIPPED e KO
		//al momento il cryptosigner fornisce un risultato complessivo 
		//della validazione del timestamp non delle singole verifiche
		//
		ValidationInfos vinfos=timeStampInfo.getValidationInfos();
		if(vinfos!=null){
			//if( tsaReliability ){
				if(vinfos.isValid()) {
					infoType.setVerificationStatus(VerificationStatusType.OK);
				} else {
					OutputOperations.addErrors(infoType, vinfos.getErrorsBean(), VerificationStatusType.KO );
					OutputOperations.addWarnings(infoType, FileOpMessage.SIGN_OP_WARNING,vinfos.getWarnings() );
				}
			/*} else {
				infoType.setVerificationStatus(VerificationStatusType.SKIPPED);
			}*/
		}
		
		//if(validityInfo.containsKey(DocumentAndTimeStampInfoBean.PROP_RECOGNIZED_CERTIFICATE)){
		//	String  reliable=(String)validityInfo.get(DocumentAndTimeStampInfoBean.PROP_RECOGNIZED_CERTIFICATE);
			//if(reliable!=null){
				TsaInfo tsaInfo= new TsaInfo();
				//tsaInfo.setReliable(new Boolean(reliable));
				String  tsaName=(String)validityInfo.get(DocumentAndTimeStampInfoBean.PROP_SID);
				if(tsaName!=null){
					tsaInfo.setTsaName(tsaName);
				}
				infoType.setTsaInfo(tsaInfo);
				//TODO volendo c'è anche il certificato!?
			//}	 
		//}
		
	}
}
