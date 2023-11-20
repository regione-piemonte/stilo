/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.aurigamailbusiness.bean.CheckLockOutBean;
import it.eng.aurigamailbusiness.bean.LockBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;

/* http://localhost:8080/AurigaMail/rest/check/lock */
@Singleton
@Path("/check")
@Api(tags={"Controllo"}, hidden=true)
public class CheckResource extends BaseResource {
	
	public static final String SERVICE_NAME = "CheckService";

	private static final Logger logger = Logger.getLogger(CheckResource.class);

	public CheckResource() throws Exception {
	}

	@POST @Path(Operation.OP_LOCK)
	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value="Effettua il lock", notes="xxxxxxxx", response=TEmailMgoBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public TEmailMgoBean lock(@ApiParam(value="Oggetto LockBean", required=true) LockBean bean) throws Exception {
		logger.debug("lock() inizio: "+bean);
		final Response response = invokeService(Operation.OP_LOCK, bean);
		final TEmailMgoBean result = parseResponse(response, TEmailMgoBean.class);
		logger.debug("lock() fine: "+String.valueOf(result));
		return result;
	}//lock
	
	@POST @Path(Operation.OP_UNLOCK)
	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value="Effettua l'unlock", notes="xxxxxxxx", response=TEmailMgoBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public TEmailMgoBean unLock(@ApiParam(value="Oggetto LockBean", required=true) LockBean bean) throws Exception {
		logger.debug("unLock() inizio: "+bean);
		final Response response = invokeService(Operation.OP_UNLOCK, bean);
		final TEmailMgoBean result = parseResponse(response, TEmailMgoBean.class);
		logger.debug("unLock() fine: "+String.valueOf(result));
		return result;
	}//unLock
	
	@POST @Path(Operation.OP_FORCE_UNLOCK)
	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value="Effettua forzatamente l'unlock", notes="xxxxxxxx", response=TEmailMgoBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public TEmailMgoBean forceUnLock(@ApiParam(value="Oggetto LockBean", required=true) LockBean bean) throws Exception {
		logger.debug("forceUnLock() inizio: "+bean);
		final Response response = invokeService(Operation.OP_FORCE_UNLOCK, bean);
		final TEmailMgoBean result = parseResponse(response, TEmailMgoBean.class);
		logger.debug("forceUnLock() fine: "+String.valueOf(result));
		return result;
	}//forceUnLock
	
	@POST @Path(Operation.OP_CHECK_LOCK)
	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value="Effettua il controllo del lock", notes="xxxxxxxx", response=CheckLockOutBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public CheckLockOutBean checkLock(@ApiParam(value="Oggetto LockBean", required=true) LockBean bean) throws Exception {
		logger.debug("checkLock() inizio: "+bean);
		final Response response = invokeService(Operation.OP_CHECK_LOCK, bean);
		final CheckLockOutBean result = parseResponse(response, CheckLockOutBean.class);
		logger.debug("checkLock() fine: "+String.valueOf(result));
		return result;
	}//checkLock
	
	@Override
	protected String getServiceName() {
		return SERVICE_NAME;
	}

}//CheckResource