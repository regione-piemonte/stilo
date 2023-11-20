/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailMessage;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailReferences;
import it.eng.aurigamailbusiness.bean.restrepresentation.FileInfo;
import it.eng.aurigamailbusiness.bean.restrepresentation.RecipientsAddresses;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.SendAndSaveNewRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.SendAndSaveNewResponse;
import it.eng.aurigamailbusiness.database.dao.MailSenderService;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;

public class SendAndSaveUtil {

	public static final EmailSentReferenceBean getResult(final SenderBean senderBean) {
		final MailSenderService mailSenderService = new MailSenderService();
		ResultBean<EmailSentReferenceBean> rb = null;
		try {
			rb = mailSenderService.sendAndSave(senderBean);
		} catch (AurigaMailBusinessException e) {
			throw new AurigaMailException(e);
		}
		if (rb.isInError()) {
			throw new AurigaMailException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), rb.getDefaultMessage());
		}
		final EmailSentReferenceBean result = rb.getResultBean();
		return result;
	}

	public static final void setResponseFields(final List<SenderAttachmentsBean> source, final SendAndSaveNewResponse target) {
		final List<FileInfo> attachments = new ArrayList<FileInfo>(source.size());
		final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALIAN);
		BigDecimal dim = BigDecimal.ZERO;
		for (SenderAttachmentsBean bean : source) {
			final BigDecimal d = bean.getDimensione();
			final String name = bean.getFilename();
			dim = dim.add(d);
			BigDecimal nbrKB = BigDecimal.ZERO;
			if (d.signum() > 0) {
				nbrKB = d.divide(BigDecimal.valueOf(1000));
			}
			attachments.add(new FileInfo(name, nf.format(nbrKB) + " KB"));
		}
		target.setAttachments(attachments);
		if (dim.signum() > 0) {
			final BigDecimal nbrKB = dim.divide(BigDecimal.valueOf(1000));
			target.setAttachmentsSize(nf.format(nbrKB) + " KB");
		}
	}

	public static final void setSenderBeanFields(final SendAndSaveNewRequest source, final SenderBean target) {
		setSenderBeanFields(source.getEmailMessage(), target);
		setSenderBeanFields(source.getRecipientsAddresses(), target);
		target.setIsHtml(source.getHtml());
		target.setReturnReceipt(source.getNotificationReading());
		target.setFlgInvioSeparato(source.getSeparateMailing());
	}

	public static final void setEmailReferencesFields(SenderBean source, EmailReferences target) {
		final RecipientsAddresses recipientsAddresses = new RecipientsAddresses();
		recipientsAddresses.setAddressesBcc(source.getAddressBcc());
		recipientsAddresses.setAddressesCc(source.getAddressCc());
		recipientsAddresses.setAddressesTo(source.getAddressTo());
		target.setRecipientsAddresses(recipientsAddresses);
		target.setId(source.getIdEmail());
		target.setMessageId(source.getMessageId());
		target.setReference(source.getUriSavedMimeMessage());
	}

	public static final void check(final SendAndSaveNewRequest data) {
		final int status400 = Response.Status.BAD_REQUEST.getStatusCode();
		if (data == null) {
			throw new AurigaMailException(status400, "E' necessario valorizzare la parte del corpo 'data'.");
		}
		if (data.getEmailMessage() == null) {
			throw new AurigaMailException(status400, "E' necessario valorizzare il messaggio email.");
		}
		final EmailMessage message = data.getEmailMessage();
		if (StringUtils.isBlank(message.getAddressFrom())) {
			throw new AurigaMailException(status400, "E' necessario valorizzare l'indirizzo del mittente.");
		}
		RecipientsAddresses recipients = data.getRecipientsAddresses();
		if (recipients == null) {
			recipients = new RecipientsAddresses();
		}
		final boolean emptyTo = recipients.getAddressesTo() == null || recipients.getAddressesTo().isEmpty();
		if (emptyTo) {
			final boolean emptyCc = recipients.getAddressesCc() == null || recipients.getAddressesCc().isEmpty();
			if (emptyCc) {
				throw new AurigaMailException(status400, "E' necessario valorizzare l'indirizzo di almeno un destinatario.");
			}
		}
	}

	private static final void setSenderBeanFields(final RecipientsAddresses source, final SenderBean target) {
		target.setAddressBcc(source.getAddressesBcc());
		target.setAddressCc(source.getAddressesCc());
		target.setAddressTo(source.getAddressesTo());
	}

	private static final void setSenderBeanFields(final EmailMessage source, final SenderBean target) {
		target.setAccount(source.getAddressFrom());
		target.setAddressFrom(source.getAddressFrom());
		target.setAliasAddressFrom(source.getAliasAddressFrom());
		target.setBody(source.getBody());
		target.setSubject(source.getSubject());
	}

}
