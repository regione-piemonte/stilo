package it.eng.utility.cryptosigner.controller.impl.signature;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Effettua due tipi di controlli:
 * <ul>
 * <li>Recupera il formato della busta tramite la chiamata al metodo {@link it.eng.utility.cryptosigner.data.AbstractSigner#getFormat getFormat} del signer
 * dalla busta,</li>
 * <li>Confronta l'eventuale riferimento temporale configurata nel bena di input con la data di validita configurata nel campo validityProperties</li>
 * </ul>
 * 
 * @author Administrator
 *
 */
public class FormatValidity extends AbstractSignerController {

	private static Logger log = LogManager.getLogger(FormatValidity.class);

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String FORMAT_VALIDITY_CHECK = "performFormatValidity";

	public String getCheckProperty() {
		return FORMAT_VALIDITY_CHECK;
	}

	private Properties validityProperties;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Recupera i periodi di validita associati a ciascun formato di firma
	 * 
	 * @return properties
	 */
	public Properties getValidityProperties() {
		return validityProperties;
	}

	/**
	 * Definisce i periodi di validita di ciascun formato di firma
	 * 
	 * @param validityProperties
	 */
	public void setValidityProperties(Properties validityProperties) {
		this.validityProperties = validityProperties;
	}

	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {

		String format = input.getSigner().getFormat().toString();

		// Setto il formato della busta
		output.setProperty(OutputSignerBean.ENVELOPE_FORMAT_PROPERTY, format);

		boolean result = true;
		String validity = validityProperties.getProperty(format);
		ValidationInfos validationInfos = new ValidationInfos();
		// DocumentAndTimeStampInfoBean timeStampInfo= input.getDocumentAndTimeStampInfo();
		// recupero il riferimento temporale dal timestamptoken
		Date referenceDate;
		Date timeStampDate = (referenceDate = input.getReferenceDate()) == null ? new Date() : referenceDate;

		// try {
		// timeStampDate = timeStampInfo.getTimeStampToken().getTimeStampInfo().getGenTime();
		// }catch(Exception e) {/* non fare nulla.. */}

		if (validity != null && eseguiValidazioni) {
			try {
				Date date = dateFormat.parse(validity);
				if (date.before(timeStampDate)) {
					validationInfos.addErrorWithCode(MessageConstants.FV_FORMAT_EXIPERED_BEFORE,
							MessageHelper.getMessage(MessageConstants.FV_FORMAT_EXIPERED_BEFORE, date, timeStampDate));
					validationInfos.addError(MessageHelper.getMessage(MessageConstants.FV_FORMAT_EXIPERED_BEFORE, date, timeStampDate));
					result = false;
				}
			} catch (ParseException e) {
				validationInfos.addErrorWithCode(MessageConstants.FV_FORMAT_NONVERIFIABLE,
						MessageHelper.getMessage(MessageConstants.FV_FORMAT_NONVERIFIABLE, validity));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.FV_FORMAT_NONVERIFIABLE, validity));
				result = false;
			}
		}
		output.setProperty(OutputSignerBean.FORMAT_VALIDITY_PROPERTY, validationInfos);
		return result;
	}

	/**
	 * Recupera il formato di data utilizzato per indicare i periodi di validita
	 */
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * Definisce il formato di data utilizzato per indicare i periodi di validita
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

}
