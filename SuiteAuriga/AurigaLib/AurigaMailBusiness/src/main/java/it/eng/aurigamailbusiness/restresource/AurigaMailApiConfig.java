/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import io.swagger.annotations.Api;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.ReaderListener;
import io.swagger.models.Swagger;

@SwaggerDefinition( 
		basePath = "/AurigaMail/rest",
        info = @Info(
                description = "Espone servizi REST per le mail",
                version = "v1",
                title = "Servizi di AurigaMail",
//                termsOfService = "http://theweatherapi.io/terms.html",
                contact = @Contact(
                   name = "API Support",
                   url = "http://www.eng.it/",
                   email = "Ugo.PiconeChiodo@eng.it"
                )
                ,license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/xml"},
        produces = {"application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP/*, SwaggerDefinition.Scheme.HTTPS*/},
        tags = {
            @Tag(name = "Contenuto", description = "Funzionalità di recupero informazioni sulla mail"),
            @Tag(name = "Controllo", description = "Funzionalità per la chiusura e presa in carico"),
            @Tag(name = "Spedizione", description = "Funzionalità per spedire una mail"),
            @Tag(name = "Invio"/*, description = "Funzionalità per l'invio di emails tramite AurigaMail"*/),
            @Tag(name = "Interazione"/*, description = "Funzionalità per interagire con AurigaMail"*/),
            @Tag(name = "Dizionario"/*, description = ""*/)
        }
//        ,externalDocs = @ExternalDocs(value = "Meteorology", url = "http://theweatherapi.io/meteorology.html")
)
@Api
public class AurigaMailApiConfig implements ReaderListener {

	@Override
	public void beforeScan(Reader reader, Swagger swagger) {
	}

	@Override
	public void afterScan(Reader reader, Swagger swagger) {
		// swagger.setHost("pippo");
		// swagger.setBasePath( System.getProperty( "swagger.basepath", swagger.getBasePath() ));
		// FormParameter formParamFile = (FormParameter) swagger.getParameter("file");
		// formParamFile.setItems(new FileProperty());
	}

}