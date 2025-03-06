package it.eng.utility.client.toponomastica;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import com.sun.jersey.api.client.ClientResponse;

import it.eng.utility.restClient.RestRequestBean;
import it.eng.utility.restClient.RestService;

public class ClientToponomastica {
	
	public static final String BEAN_ID = "clientToponomastica";
	private static final Logger logger = Logger.getLogger(ClientToponomastica.class);
	private String formatoRisposta;
	private String esitoNumCivicoSi;
	private String esitoNumCivicoNo;
	
	/*
	 * Chiamata a servizio rest per ricercare strade passando parola chiave come parametro
	 * Input: bean StradaRequest
	 * Return: bean StradaResponse
	 */
	public StradaResponse getStrada(StradaRequest input) {
		logger.info("Servizio getStrada");
		
		// set request param da passare a servizio rest
		RestRequestBean requestBean = setRequestParamStrada(input);
		
		// chiamata rest /rstGetStradaDescr
		RestService restService = new RestService();
		ClientResponse response = restService.callService(requestBean);
		
		// acquisizione response del servizio rest
		StradaResponse result = setResponseStrada(response);
		
		return result;
	}
	
	/*
	 * Chiamata a servizio rest per controllare esistenza di un civico passato come parametro
	 * Input: bean NumeroCivicoRequest
	 * Return: bean NumeroCivicoResponse
	 */
	public NumeroCivicoResponse getEsistenzaNumeroCivico(NumeroCivicoRequest input) {
		logger.info("Servizio getEsistenzaNumeroCivico");
		
		// set request param da passare a servizio rest
		RestRequestBean requestBean = setRequestParamNumeroCivico(input, "r");
		
		// chiamata rest /rstGetEsistenzaCivico
		RestService restService = new RestService();
		ClientResponse response = restService.callService(requestBean);
		logger.info("Chiamata a getEsistenzaNumeroCivico passando colore_civico = r");
		
		// acquisizione response del servizio rest
		NumeroCivicoResponse result = setResponseNumeroCivico(response);
		
		if (!result.isNumeroCivicoEsiste()) {
			logger.info("Chiamata a getEsistenzaNumeroCivico con response false, secondo tentativo");
			
			// effettuo una seconda chiamata
			RestRequestBean requestBean2 = setRequestParamNumeroCivico(input, "n");
			
			ClientResponse response2 = restService.callService(requestBean2);
			logger.info("Chiamata a getEsistenzaNumeroCivico passando colore_civico = s");
			
			NumeroCivicoResponse result2 = setResponseNumeroCivico(response2);
			
			return result2;
		}
		else {
			return result;
		}
	}
	
	/*
	 * Metodo per settare parametri request per chiamata rest /rstGetStradaDescr
	 */
	private RestRequestBean setRequestParamStrada(StradaRequest input) {
		RestRequestBean requestBean = new RestRequestBean();
		
		try {
			String formato = formatoRisposta;
			if (input.getFormatoRisposta() != null) {
				formato = input.getFormatoRisposta();
			}
			
			String nomeVia = input.getNomeVia();
			if (input.getNomeVia() != null && input.getNomeVia().length() > 50) {
				nomeVia = input.getNomeVia().substring(0, 50);
			}
			
			String param = "?NOME_VIA=" + URLEncoder.encode(nomeVia.toUpperCase(),"UTF-8") + 
					   	   "&FORMATO_RISPOSTA=" + URLEncoder.encode(formato,"UTF-8");
		
			requestBean.setEndpoint(input.getEndpoint());
			requestBean.setMetodo(input.getMetodo());
			requestBean.setToken("Bearer " + input.getToken());
			requestBean.setRequestParam(param);
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		
		return requestBean;
	}
	
	/*
	 * Metodo per generare bean di response da passare alla GUI per servizio rest /rstGetStradaDescr
	 */
	private StradaResponse setResponseStrada(ClientResponse clientResponse) {
		Integer status;
		String response = null;
		
		if (clientResponse != null) {
			status = clientResponse.getStatus();
			
			if (clientResponse.getStatus() == 200) {
				// chiamata rest eseguita - acquisizione response
				response = clientResponse.getEntity(String.class);
			}
		}
		else {
			// errore applicativo
			status = 500;
		}
		
		StradaResponse result = new StradaResponse();
		result.setStatus(status);
		result.setResponse(response);
		
		return result;
	}
	
	/*
	 * Metodo per settare parametri request per chiamata rest /rstGetEsistenzaCivico
	 */
	private RestRequestBean setRequestParamNumeroCivico(NumeroCivicoRequest input, String coloreCivico) {
		RestRequestBean requestBean = new RestRequestBean();
		
		try {
			String formato = formatoRisposta;
			if (input.getFormatoRisposta() != null) {
				formato = input.getFormatoRisposta();
			}
			
			String letteraCivico = "";
			if (input.getLetteraCivico() != null) {
				letteraCivico = input.getLetteraCivico();
			}
			
			String paramColoreCivico = coloreCivico;
			if (input.getColoreCivico() != null && !input.getColoreCivico().isEmpty()) {
				paramColoreCivico = input.getColoreCivico();
			}
			
			String param = "?CODICE_STRADA=" + URLEncoder.encode(input.getCodiceStrada(),"UTF-8") + 
					   	   "&NUMERO_CIVICO=" + URLEncoder.encode(input.getNumeroCivico(),"UTF-8") +
					   	   "&LETTERA_CIVICO=" + URLEncoder.encode(letteraCivico,"UTF-8") +
					   	   "&COLORE_CIVICO=" + URLEncoder.encode(paramColoreCivico,"UTF-8") +
					   	   "&FORMATO_RISPOSTA=" + URLEncoder.encode(formato,"UTF-8");
			
			requestBean.setEndpoint(input.getEndpoint());
			requestBean.setMetodo(input.getMetodo());
			requestBean.setToken("Bearer " + input.getToken());
			requestBean.setRequestParam(param);
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
			
		return requestBean;
	}
	
	/*
	 * Metodo per generare bean di response da passare alla GUI per servizio rest /rstGetEsistenzaCivico
	 */
	private NumeroCivicoResponse setResponseNumeroCivico(ClientResponse clientResponse) {
		Integer status;
		String response = null;
		boolean numeroCivicoEsiste = false;
		
		if (clientResponse != null) {
			status = clientResponse.getStatus();
				
			if (clientResponse.getStatus() == 200) {
				// chiamata rest eseguita - acquisizione response
				response = clientResponse.getEntity(String.class);
				
				logger.info("Esiste numero civico: " + response);
				
				if (StringUtils.containsIgnoreCase(response, esitoNumCivicoSi)) {
					numeroCivicoEsiste = true;
				}
			}
		}
		else {
			// errore applicativo
			status = 500;
		}
		
		NumeroCivicoResponse result = new NumeroCivicoResponse();
		result.setStatus(status);
		result.setResponse(response);
		result.setNumeroCivicoEsiste(numeroCivicoEsiste);
		
		return result;
	}

	public String getFormatoRisposta() {
		return formatoRisposta;
	}
	
	public void setFormatoRisposta(String formatoRisposta) {
		this.formatoRisposta = formatoRisposta;
	}

	public String getEsitoNumCivicoSi() {
		return esitoNumCivicoSi;
	}

	public void setEsitoNumCivicoSi(String esitoNumCivicoSi) {
		this.esitoNumCivicoSi = esitoNumCivicoSi;
	}

	public String getEsitoNumCivicoNo() {
		return esitoNumCivicoNo;
	}

	public void setEsitoNumCivicoNo(String esitoNumCivicoNo) {
		this.esitoNumCivicoNo = esitoNumCivicoNo;
	}
	
}
