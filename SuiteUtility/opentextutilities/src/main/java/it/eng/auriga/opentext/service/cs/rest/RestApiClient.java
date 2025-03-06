package it.eng.auriga.opentext.service.cs.rest;

//import java.time.Duration;
//import java.util.Collections;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;

//import com.fasterxml.jackson.databind.JsonNode;

//import io.netty.channel.ChannelOption;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import io.netty.handler.timeout.WriteTimeoutHandler;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.client.HttpClient;

public class RestApiClient {
	
	
//	
//	
//	
//	
//	public static void main(String[] args) {
////		HttpClient httpClient = HttpClient.create()
////				  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
////				  .responseTimeout(Duration.ofMillis(5000))
////				  .doOnConnected(conn -> 
////				    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
////				      .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
//
//
//		WebClient client = WebClient.builder()
//				  .baseUrl("http://otcs.esl.eng.it:9080/OTCS/cs/api")
//				  .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
//				  .clientConnector(new ReactorClientHttpConnector())
//				  .defaultCookie("cookieKey", "cookieValue")
//				  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				   .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
//				  .defaultUriVariables(Collections.singletonMap("url", "http://otcs.esl.eng.it:9080/OTCS/cs/api"))
//				  .build();
//		
//	
//		
//		JsonNode response = getAuthCS(client);
//		client = client.mutate().defaultHeader("OTCSTicket", response.get("ticket").asText()).build();
//				
//		response = searchDocuments(client);
//		
//		System.out.println("Searche result: " + response);
//	//	System.out.println("Barcode: " + );
//		
//	}
//	
//	private static JsonNode searchDocuments(WebClient client) {
//		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//		map.add("where", "Attr_26085_2:70975737");
//		map.add("slice", "2859");
//		map.add("select", "{'OTReserved','OTReservedBy','OTReservedByFullName','OTReservedByName','OTReservedDate','OTReservedTime','Attr_29707_2', 'Attr_26085_2','Attr_26085_4', 'Attr_26085_9'}");
//		return client.post()
//			      .uri("/v2/search")
//			      .body(BodyInserters.fromMultipartData(map))
//			      .retrieve()
//			      .onStatus(HttpStatus::is4xxClientError, response -> {
//			          System.out.println("4xx error");
//			          return Mono.error(new RuntimeException("4xx"));
//			        })
//			       .onStatus(HttpStatus::is5xxServerError, response -> {
//			          System.out.println("5xx error");
//			          return Mono.error(new RuntimeException("5xx"));
//			        })
//			      .bodyToMono(JsonNode.class)
//			      .block();
//	}
//
//	private static JsonNode getAuthCS(WebClient client) {
//		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//		map.add("username", "Admin");
//		map.add("password", "Eng20212021");
//		return client.post()
//			      .uri("/v1/auth")
//			      .body(BodyInserters.fromMultipartData(map))
//			      .retrieve()
//			      .onStatus(HttpStatus::is4xxClientError, response -> {
//			          System.out.println("4xx error");
//			          return Mono.error(new RuntimeException("4xx"));
//			        })
//			       .onStatus(HttpStatus::is5xxServerError, response -> {
//			          System.out.println("5xx error");
//			          return Mono.error(new RuntimeException("5xx"));
//			        })
//			      .bodyToMono(JsonNode.class)
//			      .block();
//	}
}
