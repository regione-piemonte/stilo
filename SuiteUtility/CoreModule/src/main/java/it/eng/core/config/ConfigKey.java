package it.eng.core.config;

public interface ConfigKey {
	//elenco dei database configurati
	String DATABASE="database";
	//db di default per un versatore quando non ha un mapping
	String DATABASE_DEFAULT="database.default";
	//db trasversale per gli amministratori o per domini non specificati
	String DATABASE_TRASVERSALE="database.trasversale";
	//mapping versatori db
	String DOMINI="dominio";
	// linguaggio di default
	String LANGUAGE_CODE="language.code";
	String LANGUAGE_COUNTRY="language.country";
	String LANGUAGE_VARIANT="language.variant";
	//audit
	String AUDIT_AUDITER="audit.auditer";
	String AUDIT_ENABLED="audit.enabled";
	//reflection package in cui cercare i servizi
	String SERVICE_PACKAGE="service.package";
	
	String INTERNAL_AUTH_ENABLED="authentication.internal.enabled";
	String INTERNAL_SECURITY_SERVICE="authentication.servicename";
	String INTERNAL_SECURITY_BEAN="authentication.bean";
	String APPLICAZIONE_ENTE_PREFIX="applicazione-ente";

	
}
