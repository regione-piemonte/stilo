/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.ColMapping;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.MassSubmissionRequest;
import it.eng.aurigamailbusiness.utility.JobParameterRiga;
import it.eng.jaxb.variabili.Lista;

public class InvioMassivoUtil {

	public static final void check(final MassSubmissionRequest data) {
		final int status400 = Response.Status.BAD_REQUEST.getStatusCode();
		if (data == null) {
			throw new AurigaMailException(status400, "E' necessario valorizzare la parte del corpo 'data'.");
		}
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isBlank(data.getUserId())) {
			sb.append("L'identificativo dell'utente è un dato obbligatorio.\n");
		}
		if (StringUtils.isBlank(data.getPassword())) {
//			sb.append("La password dell'utente è un dato obbligatorio.\n");
		}
		if (StringUtils.isBlank(data.getAddressFrom())) {
			sb.append("L'indirizzo invio è un dato obbligatorio.\n");
		}
		if (StringUtils.isBlank(data.getSubject())) {
			sb.append("L'oggetto mail è un dato obbligatorio.\n");
		}
		if (StringUtils.isBlank(data.getBody())) {
			sb.append("Il testo mail è un dato obbligatorio.\n");
		}
		boolean flagOK = false;
        for (ColMapping colMapping : data.getColumns()) {
        	final String content = colMapping.getColContent();
        	if (StringUtils.equalsIgnoreCase(content, "IndirizziEmailTo")) {
        		final String name = colMapping.getColName();
        		flagOK = StringUtils.isAlpha(name);
        		if (flagOK) {
        			break;
        		}
        	}
		}
        if (!flagOK) {
        	sb.append("Manca indicazione della colonna del file xls che contiene il/i destinatari principali della mail.");
        }
        if (sb.length()>0) {
        	throw new AurigaMailException(status400, sb.toString());
        }
	}
	
	public static final Lista getLista(final List<ColMapping> cols) {
		final Lista lista = new Lista();
		for (ColMapping colMapping : cols) {
        	final String content = colMapping.getColContent();
        	final String name = colMapping.getColName();
        	
			final Lista.Riga.Colonna cel00 = new Lista.Riga.Colonna();
			cel00.setNro(BigInteger.ONE);
			cel00.setContent(content);
			
			final Lista.Riga.Colonna cel01 = new Lista.Riga.Colonna();
			cel01.setNro(BigInteger.valueOf(2));
			cel01.setContent(name);

        	final Lista.Riga riga = new Lista.Riga();
			riga.getColonna().add(cel00);
			riga.getColonna().add(cel01);
			lista.getRiga().add(riga);
		}//for
		return lista;
	}
	
	public static final Lista getListaAllegati(final List<SenderAttachmentsBean> attachments) {
		final Lista lista = new Lista();

		for (SenderAttachmentsBean attach : attachments) {
			int k = 0;
			final Lista.Riga.Colonna cel00 = new Lista.Riga.Colonna();
			cel00.setNro(BigInteger.valueOf(++k));
			cel00.setContent(attach.getFilename());
			
			final Lista.Riga.Colonna cel01 = new Lista.Riga.Colonna();
			cel01.setNro(BigInteger.valueOf(++k));
			cel01.setContent(attach.getOriginalName());
			
			final Lista.Riga.Colonna cel02 = new Lista.Riga.Colonna();
			cel02.setNro(BigInteger.valueOf(++k));
			cel02.setContent(""+attach.getDimensione().longValue());
			
			final Lista.Riga.Colonna cel03 = new Lista.Riga.Colonna();
			cel03.setNro(BigInteger.valueOf(++k));
			cel03.setContent(attach.getMimetype());
			
			final Lista.Riga.Colonna cel04 = new Lista.Riga.Colonna();
			cel04.setNro(BigInteger.valueOf(++k));
			cel04.setContent("false");
			
			final Lista.Riga.Colonna cel05 = new Lista.Riga.Colonna();
			cel05.setNro(BigInteger.valueOf(++k));
			cel05.setContent("false");
			
			final Lista.Riga.Colonna cel06 = new Lista.Riga.Colonna();
			cel06.setNro(BigInteger.valueOf(++k));
			cel06.setContent(attach.getImpronta());
			
			final Lista.Riga.Colonna cel07 = new Lista.Riga.Colonna();
			cel07.setNro(BigInteger.valueOf(++k));
			cel07.setContent(attach.getAlgoritmo());
			
			final Lista.Riga.Colonna cel08 = new Lista.Riga.Colonna();
			cel08.setNro(BigInteger.valueOf(++k));
			cel08.setContent(attach.getEncoding());

        	final Lista.Riga riga = new Lista.Riga();
			riga.getColonna().add(cel00);
			riga.getColonna().add(cel01);
			riga.getColonna().add(cel02);
			riga.getColonna().add(cel03);
			riga.getColonna().add(cel04);
			riga.getColonna().add(cel05);
			riga.getColonna().add(cel06);
			riga.getColonna().add(cel07);
			riga.getColonna().add(cel08);
			lista.getRiga().add(riga);

		}//for
		return lista;
	}

	

	public static final List<JobParameterBean> getJobParameters(final String idAccount, final String xmlLista, final String subject, final String body, final int attachmentsSize,
			final String uriExcel, final String xmlListaAllegati, final BigDecimal idJob) {
		final List<JobParameterBean> jobParams = new ArrayList<>();
		int i = 0;
		final JobParameterBean jobParamBean_urifile = new JobParameterBean();
		jobParamBean_urifile.setIdJob(idJob);
		jobParamBean_urifile.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_urifile.setParameterDir("OUT");
		jobParamBean_urifile.setParameterType("VARCHAR2");
		jobParamBean_urifile.setParameterSubtype("URI_FILE");
		jobParamBean_urifile.setParameterValue(uriExcel);
		jobParams.add(jobParamBean_urifile);
		//===
		final JobParameterBean jobParamBean_idAccountMittenteMail = new JobParameterBean();
		jobParamBean_idAccountMittenteMail.setIdJob(idJob);
		jobParamBean_idAccountMittenteMail.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_idAccountMittenteMail.setParameterDir("OUT");
		jobParamBean_idAccountMittenteMail.setParameterType("VARCHAR2");
		jobParamBean_idAccountMittenteMail.setParameterSubtype("ID_ACCOUNT_MITTENTE_MAIL");
		jobParamBean_idAccountMittenteMail.setParameterValue(idAccount);
		jobParams.add(jobParamBean_idAccountMittenteMail);
		//===
		final JobParameterBean jobParamBean_oggettoMail = new JobParameterBean();
		jobParamBean_oggettoMail.setIdJob(idJob);
		jobParamBean_oggettoMail.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_oggettoMail.setParameterDir("OUT");
		jobParamBean_oggettoMail.setParameterType("VARCHAR2");
		jobParamBean_oggettoMail.setParameterSubtype("OGGETTO_MAIL");
		jobParamBean_oggettoMail.setParameterValue(subject);
		jobParams.add(jobParamBean_oggettoMail);
		//===
		final JobParameterBean jobParamBean_corpoMail = new JobParameterBean();
		jobParamBean_corpoMail.setIdJob(idJob);
		jobParamBean_corpoMail.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_corpoMail.setParameterDir("OUT");
		jobParamBean_corpoMail.setParameterType("CLOB");
		jobParamBean_corpoMail.setParameterSubtype("CORPO_MAIL");
		jobParamBean_corpoMail.setParameterValue(body);
		jobParams.add(jobParamBean_corpoMail);
		//===
		final JobParameterBean jobParamBean_rigaInizioFile = new JobParameterBean();
		jobParamBean_rigaInizioFile.setIdJob(idJob);
		jobParamBean_rigaInizioFile.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_rigaInizioFile.setParameterDir("OUT");
		jobParamBean_rigaInizioFile.setParameterType("NUMERIC");
		jobParamBean_rigaInizioFile.setParameterSubtype("RIGA_INIZIO_FILE");
		jobParamBean_rigaInizioFile.setParameterValue("0");
		jobParams.add(jobParamBean_rigaInizioFile);
		//===
		final JobParameterBean jobParamBean_rigaFineFile = new JobParameterBean();
		jobParamBean_rigaFineFile.setIdJob(idJob);
		jobParamBean_rigaFineFile.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_rigaFineFile.setParameterDir("OUT");
		jobParamBean_rigaFineFile.setParameterType("NUMERIC");
		jobParamBean_rigaFineFile.setParameterSubtype("RIGA_FINE_FILE");
		jobParamBean_rigaFineFile.setParameterValue(null);
		jobParams.add(jobParamBean_rigaFineFile);
		//===
		final JobParameterBean jobParamBean_xmlContenutiFile = new JobParameterBean();
		jobParamBean_xmlContenutiFile.setIdJob(idJob);
		jobParamBean_xmlContenutiFile.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_xmlContenutiFile.setParameterDir("OUT");
		jobParamBean_xmlContenutiFile.setParameterType("CLOB");
		jobParamBean_xmlContenutiFile.setParameterSubtype("XML_CONTENUTI_FILE");
		jobParamBean_xmlContenutiFile.setParameterValue(xmlLista);
		jobParams.add(jobParamBean_xmlContenutiFile);
		//===
		final JobParameterBean jobParamBean_nroAllegatiMail = new JobParameterBean();
		jobParamBean_nroAllegatiMail.setIdJob(idJob);
		jobParamBean_nroAllegatiMail.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_nroAllegatiMail.setParameterDir("OUT");
		jobParamBean_nroAllegatiMail.setParameterType("INTEGER");
		jobParamBean_nroAllegatiMail.setParameterSubtype("NRO_ALLEGATI_MAIL");
		jobParamBean_nroAllegatiMail.setParameterValue(""+attachmentsSize);
		jobParams.add(jobParamBean_nroAllegatiMail);
		//===
		final JobParameterBean jobParamBean_uriAllegatiMail = new JobParameterBean();
		jobParamBean_uriAllegatiMail.setIdJob(idJob);
		jobParamBean_uriAllegatiMail.setParameterId(BigDecimal.valueOf(++i));		
		jobParamBean_uriAllegatiMail.setParameterDir("OUT");
		jobParamBean_uriAllegatiMail.setParameterType("CLOB");
		jobParamBean_uriAllegatiMail.setParameterSubtype("URI_ALLEGATI_MAIL");
		jobParamBean_uriAllegatiMail.setParameterValue(xmlListaAllegati);
		jobParams.add(jobParamBean_uriAllegatiMail);
		
		return jobParams;
	}

	public static final List<JobParameterRiga> getJobParameterRighe(final String idAccount, final String xmlLista, final String subject, final String body, final int attachmentsSize,
			final String uriExcel, final String xmlListaAllegati, final BigDecimal idJob) {
		final List<JobParameterBean> jobParams = InvioMassivoUtil.getJobParameters(idAccount, xmlLista, subject, body, attachmentsSize, uriExcel, xmlListaAllegati, idJob );
		final List<JobParameterRiga> righe = new ArrayList<>(jobParams.size());
		for (JobParameterBean jobParameterBean : jobParams) {
			JobParameterRiga riga = new JobParameterRiga();
			riga.setNomeParametro(jobParameterBean.getParameterSubtype());
			riga.setTipoParametro(jobParameterBean.getParameterType());
			riga.setValoreParametro(jobParameterBean.getParameterValue());
			riga.setVerso(jobParameterBean.getParameterDir());
			righe.add(riga);
		}
		return righe;
	}

	

}
