package it.eng.core.service.server;

import it.eng.core.business.exception.CoreExceptionBean;
import it.eng.core.business.exception.ServiceException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
// implies a singleton-scope
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException exception) {
        // FIXME multipart mode
        FormDataMultiPart multi = new FormDataMultiPart();
        multi.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        multi.bodyPart(new FormDataBodyPart("error", new CoreExceptionBean(exception), MediaType.APPLICATION_XML_TYPE));

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(multi).build();
    }

}
