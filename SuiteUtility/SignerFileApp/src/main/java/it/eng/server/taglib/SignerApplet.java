package it.eng.server.taglib;

import it.eng.server.SignerSessionBean;
import it.eng.server.SignerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SignerApplet extends TagSupport{
	
	private String codebase;
	private String archiveroot = null;
	private String callSignerBefore = null;
	private boolean smartClient = true;
	private String version = "2.0";
	
	
	
	
	private String wsAuth = null;
	
	@Override
	public int doStartTag() throws JspException {
		try{
			JspWriter out = pageContext.getOut();
			
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			String archive = null;
			if(archiveroot!=null){
				if(archiveroot.equals("")){
					archive = codebase+"/SmartCardApplet.jar";
				}else{
					archive = archiveroot+"/"+codebase+"/SmartCardApplet.jar";
				}
			}else{
				archive = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/"+codebase+"/SmartCardApplet.jar";
			}
			
			
			SignerSessionBean signersessionbean = (SignerSessionBean)request.getSession().getAttribute(SignerUtil.SESSION_SIGNER);
			
			StringBuffer strb= new StringBuffer();
			strb.append("<APPLET id=\"SignerApplet\" CODE=\"it.eng.client.applet.SmartCard\" NAME=\"Applet di firma\" HEIGHT=200 WIDTH=300  mayscript=\"mayscript\" archive=\""+archive+"?"+version+"\">");
			if (wsAuth!=null && !"".equals(wsAuth.trim())){
				strb.append("<PARAM name=\"wsAuth\"  value=\" " + wsAuth + "\" >");	
			}
			strb.append("</APPLET>");
			
			if(smartClient){
				out.print("<script>");		
				out.println("function loadSigner(){");
				out.println("	isc.Window.create({");
				out.println("title: \"Applet di Firma\",");
				out.println("ID:'signer',");
				out.println("autoSize: true,");
				out.println("canDragReposition: false,");
				out.println("canDragResize: false,");
				out.println("autoCenter:true,");
				out.println("items: [");
				out.println("   isc.HTMLFlow.create({");
				out.println("	top:100,");
				out.println("	contents:'"+strb.toString()+"',");
				out.println("	    		dynamicContents:true");
				out.println("			})");
				out.println("	    ]");
				out.println("	});");
				out.println("}");
				out.print("</script>");			
			}else{
				
				out.print(strb.toString());
//				out.print("<APPLET id=\"SignerApplet\" CODE=\"it.eng.client.applet.SmartCard\" NAME=\"Applet di firma\" HEIGHT=200 WIDTH=300  mayscript=\"mayscript\" archive=\""+archive+"\"></APPLET>");
			}
			if(callSignerBefore==null){
				out.print("<script>avvioFirma();</script>");
			}else{
				out.print("<script>"+callSignerBefore+"();</script>");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
		
	public String getCodebase(){
		return codebase;
	}

	public void setCodebase(String codebase) {
		this.codebase = codebase;
	}

	public boolean isSmartClient() {
		return smartClient;
	}

	public void setSmartClient(boolean smartClient) {
		this.smartClient = smartClient;
	}

	public String getCallSignerBefore() {
		return callSignerBefore;
	}

	public void setCallSignerBefore(String callSignerBefore) {
		this.callSignerBefore = callSignerBefore;
	}
	
	

	public String getArchiveroot() {
		return archiveroot;
	}

	public void setArchiveroot(String archiveroot) {
		this.archiveroot = archiveroot;
	}

	public String getWsAuth() {
		return wsAuth;
	}

	public void setWsAuth(String wsAuth) {
		this.wsAuth = wsAuth;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}