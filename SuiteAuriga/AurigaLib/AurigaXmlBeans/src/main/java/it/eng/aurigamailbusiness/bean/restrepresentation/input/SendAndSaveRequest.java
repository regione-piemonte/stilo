/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.RispostaInoltro;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;

@XmlRootElement(name="richiestaSpedisciESalvaEmail")
public class SendAndSaveRequest extends MutualInput {
	
	@XmlElement(name="accountDiPosta", required=true)
	protected String account;

	@XmlElement(name="mittente")
	private String addressFrom;
	
	@XmlElement(name="aliasMittente")
	private String aliasAddressFrom;
	
	@XmlElement(name="soggetto")
	private String subject;
	
	@XmlElement(name="corpo")
	private String body;
	
	@XmlElement(name="formatoHTML", defaultValue="false")
	private Boolean isHtml = Boolean.FALSE;
	
	@XmlElement(name="richiestaConfermaDiLettura", defaultValue="false")
	private Boolean returnReceipt = Boolean.FALSE;
	
//	@XmlElementWrapper
	@XmlElement(name="allegato")
	private List<SenderAttachmentsBean> attachments = new ArrayList<SenderAttachmentsBean>(0);
		
//	@XmlElementWrapper
	@XmlElement(name="destinatario")
	private List<String> addressesTo = new ArrayList<String>(0);
	
//	@XmlElementWrapper
	@XmlElement(name="destinatarioInConoscenza")
	private List<String> addressesCc = new ArrayList<String>(0);
	
//	@XmlElementWrapper
	@XmlElement(name="destinatarioInConoscenzaNascosta")
	private List<String> addressesBcc = new ArrayList<String>(0);
	
	@XmlElement(name="idEmail")
	private String idEmail;
	 
	@XmlElement(name="tipologia")
	private RispostaInoltro rispostaInoltro;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public Boolean getIsHtml() {
		return isHtml;
	}
	public void setIsHtml(Boolean isHtml) {
		this.isHtml = isHtml;
	}
	
	public List<String> getAddressesTo() {
		return addressesTo;
	}
	public void setAddressesTo(List<String> addressesTo) {
		this.addressesTo = addressesTo;
	}
	
	public List<String> getAddressesCc() {
		return addressesCc;
	}
	public void setAddressesCc(List<String> addressesCc) {
		this.addressesCc = addressesCc;
	}
	
	public List<String> getAddressesBcc() {
		return addressesBcc;
	}
	public void setAddressBcc(List<String> addressesBcc) {
		this.addressesBcc = addressesBcc;
	}
	
	public String getAddressFrom() {
		return addressFrom;
	}
	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}
	
	public String getAliasAddressFrom() {
		return aliasAddressFrom;
	}
	public void setAliasAddressFrom(String aliasAddressFrom) {
		this.aliasAddressFrom = aliasAddressFrom;
	}

	public Boolean getReturnReceipt() {
		return returnReceipt;
	}
	public void setReturnReceipt(Boolean returnReceipt) {
		this.returnReceipt = returnReceipt;
	}
	
	public List<SenderAttachmentsBean> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<SenderAttachmentsBean> attachments) {
		this.attachments = attachments;
	}
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
	public RispostaInoltro getRispostaInoltro() {
		return rispostaInoltro;
	}
	public void setRispostaInoltro(RispostaInoltro rispostaInoltro) {
		this.rispostaInoltro = rispostaInoltro;
	}
	
}
