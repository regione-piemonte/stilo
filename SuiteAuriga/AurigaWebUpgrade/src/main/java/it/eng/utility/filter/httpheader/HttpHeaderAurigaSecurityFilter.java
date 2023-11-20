/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import it.eng.utility.filter.FilterBase;

public class HttpHeaderAurigaSecurityFilter extends FilterBase {
	
	private static final Logger logger = Logger.getLogger(HttpHeaderAurigaSecurityFilter.class);
	
	private static final String PARAM_NAME_ANTI_CLICK_JACKING_OPTION = "antiClickJackingOption";
	private static final String PARAM_NAME_ANTI_CLICK_JACKING_URI = "antiClickJackingUri";
	
	private static final String CSP_HEADER_NAME = "Content-Security-Policy";
	private static final String HSTS_HEADER_NAME = "Strict-Transport-Security";
	private static final String ANTI_CLICK_JACKING_HEADER_NAME = "X-Frame-Options";
	private static final String BLOCK_CONTENT_TYPE_SNIFFING_HEADER_NAME = "X-Content-Type-Options";
	private static final String XSS_PROTECTION_HEADER_NAME = "X-XSS-Protection";
	//---------------------------------------------------------------------------------------------------------------
	private static final String BLOCK_CONTENT_TYPE_SNIFFING_HEADER_VALUE = "nosniff";
	private static final String XSS_PROTECTION_HEADER_VALUE = "1; mode=block";
	//---------------------------------------------------------------------------------------------------------------
	private static final String X_PERMITTED_CROSS_DOMAIN_POLICIES_HEADER_NAME = "X-Permitted-Cross-Domain-Policies";
	//---------------------------------------------------------------------------------------------------------------
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	
    private static enum XFrameOption {
	    DENY("DENY"),  
	    SAME_ORIGIN("SAMEORIGIN"),
	    ALLOW_FROM("ALLOW-FROM");
	    
	    private final String headerValue;
	    
	    private XFrameOption(String headerValue) {
	       this.headerValue = headerValue;
	    }
	    
	    public String getHeaderValue() {
	       return this.headerValue;
	    }
    }
    
    private static enum PermittedCrosDomainPoliciesOption {
    	MASTER_ONLY("master-only");
	    
	    private final String headerValue;
	    
	    private PermittedCrosDomainPoliciesOption(String headerValue) {
	       this.headerValue = headerValue;
	    }
	    
	    public String getHeaderValue() {
	       return this.headerValue;
	    }
    }
	
	//-----------------------------------------------
	private boolean hstsEnabled;
	private int hstsMaxAgeSeconds;
	private boolean hstsIncludeSubDomains;
	private String hstsHeaderValue;
	  //-----------------------------------------------
	private boolean antiClickJackingEnabled;
	private XFrameOption antiClickJackingOption;
	private URI antiClickJackingUri;
	private String antiClickJackingHeaderValue;
	  //-------------------------------------------------
	private boolean blockContentTypeSniffingEnabled;
	//------------
	private boolean xssProtectionEnabled;
	//------------
	private boolean cspEnabled;
	private String cspHeaderValue;
	//------------	
	private boolean permittedCrossDomainPoliciesEnabled;
	private String permittedCrossDomainPoliciesValue;
	//------------	
	private boolean accessControlAllowOriginEnabled;
	private String accessControlAllowOriginValue;
	
	private boolean filterEnabled;
	
	public HttpHeaderAurigaSecurityFilter() {
		
		this.hstsEnabled = true;
		this.hstsMaxAgeSeconds = 0;
		this.hstsIncludeSubDomains = false;
		    
		this.antiClickJackingEnabled = true;
		this.antiClickJackingOption = XFrameOption.DENY;
		    
		this.blockContentTypeSniffingEnabled = true;
		    
		this.xssProtectionEnabled = true;
	
		this.cspEnabled = true;
		
		this.filterEnabled = true;
		
		this.permittedCrossDomainPoliciesEnabled = true;
		this.permittedCrossDomainPoliciesValue = PermittedCrosDomainPoliciesOption.MASTER_ONLY.getHeaderValue();
		
		this.accessControlAllowOriginEnabled = true;
		this.permittedCrossDomainPoliciesValue = "*";
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
//		final HttpSession session = httpRequest.getSession(false);
		
	    if (!filterEnabled) {
	    	chain.doFilter(request, response);
	    	return;
	    }

		if (!(response instanceof HttpServletResponse)) {
			chain.doFilter(request, response);
			return;
		}
		
	    if (response.isCommitted()) {
	        throw new ServletException("Risposta http già compiuta.");
	    }
	    
	    final String query = httpRequest.getQueryString();
	    if (!isBlank(query) && query.contains("script")) {
	    	httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "E' stato individuato uno script nella query string.");
	    	return;
	    }
	      
	    if (hstsEnabled && request.isSecure()) {
	        httpResponse.setHeader(HSTS_HEADER_NAME, hstsHeaderValue);
	    }
	    if (antiClickJackingEnabled) {
	        httpResponse.setHeader(ANTI_CLICK_JACKING_HEADER_NAME, antiClickJackingHeaderValue);
	    }
	    if (blockContentTypeSniffingEnabled) {
	        httpResponse.setHeader(BLOCK_CONTENT_TYPE_SNIFFING_HEADER_NAME, BLOCK_CONTENT_TYPE_SNIFFING_HEADER_VALUE);
	    }
	    if (xssProtectionEnabled) {
	        httpResponse.setHeader(XSS_PROTECTION_HEADER_NAME, XSS_PROTECTION_HEADER_VALUE);
	    }
		if (cspEnabled) {
			final String headerValue = cspHeaderValue /*+ this.createCSPRuntime(httpRequest)*/;
			httpResponse.setHeader(CSP_HEADER_NAME, headerValue);
		}
		if (permittedCrossDomainPoliciesEnabled) {
	        httpResponse.setHeader(X_PERMITTED_CROSS_DOMAIN_POLICIES_HEADER_NAME, permittedCrossDomainPoliciesValue);
	    }
		if (accessControlAllowOriginEnabled) {
	        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, accessControlAllowOriginValue);
	    }

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
	    final String paramValueAntiClickJackingOption = filterConfig.getInitParameter(PARAM_NAME_ANTI_CLICK_JACKING_OPTION);
	    if (paramValueAntiClickJackingOption != null) {
	        setAntiClickJackingOption(paramValueAntiClickJackingOption);
	    }
	    final String paramValueAntiClickJackingUri = filterConfig.getInitParameter(PARAM_NAME_ANTI_CLICK_JACKING_URI);
	    if (paramValueAntiClickJackingUri != null) {
	        setAntiClickJackingUri(paramValueAntiClickJackingUri);
	    }
	    
	    final StringBuilder hstsValue = new StringBuilder("max-age=");
	    hstsValue.append(hstsMaxAgeSeconds);
	    if (hstsIncludeSubDomains) {
	      hstsValue.append(";includeSubDomains");
	    }
	    hstsHeaderValue = hstsValue.toString();
	    
	    final StringBuilder cjValue = new StringBuilder(antiClickJackingOption.headerValue);
	    if (antiClickJackingOption == XFrameOption.ALLOW_FROM) {
	      cjValue.append(' ');
	      cjValue.append(antiClickJackingUri);
	    }
	    antiClickJackingHeaderValue = cjValue.toString();

	    cspHeaderValue = createCSP();
	}
	
	public boolean isHstsEnabled() {
		return hstsEnabled;
	}
	
	public void setHstsEnabled(boolean hstsEnabled) {
		this.hstsEnabled = hstsEnabled;
	}

	public int getHstsMaxAgeSeconds() {
		return hstsMaxAgeSeconds;
	}
	
	public void setHstsMaxAgeSeconds(int hstsMaxAgeSeconds) {
	    if (hstsMaxAgeSeconds < 0) {
	        this.hstsMaxAgeSeconds = 0;
	      } else {
	        this.hstsMaxAgeSeconds = hstsMaxAgeSeconds;
	    }
	}

	public boolean isHstsIncludeSubDomains() {
		return hstsIncludeSubDomains;
	}
	
	public void setHstsIncludeSubDomains(boolean hstsIncludeSubDomains) {
		this.hstsIncludeSubDomains = hstsIncludeSubDomains;
	}

	public boolean isAntiClickJackingEnabled() {
		return antiClickJackingEnabled;
	}
	
	public void setAntiClickJackingEnabled(boolean antiClickJackingEnabled) {
		this.antiClickJackingEnabled = antiClickJackingEnabled;
	}

	public XFrameOption getAntiClickJackingOption() {
		return antiClickJackingOption;
	}
	
	public void setAntiClickJackingOption(XFrameOption antiClickJackingOption) {
	    this.antiClickJackingOption = antiClickJackingOption;
	}
	
	public void setAntiClickJackingOption(String antiClickJackingOption) {
	    for (XFrameOption option : XFrameOption.values()) {
	        if (option.getHeaderValue().equalsIgnoreCase(antiClickJackingOption)) {
	           this.antiClickJackingOption = option;
	           return;
	        }
	    }
	    throw new IllegalArgumentException("Valore "+String.valueOf(antiClickJackingOption) + " non valido.");
	}

	public URI getAntiClickJackingUri() {
		return antiClickJackingUri;
	}
	
	public void setAntiClickJackingUri(URI antiClickJackingUri) {
	    this.antiClickJackingUri = antiClickJackingUri;
	}
	
	public void setAntiClickJackingUri(String antiClickJackingUri) {
	    URI uri;
	    try {
	       uri = new URI(antiClickJackingUri);
	    } catch (URISyntaxException e) {
	      throw new IllegalArgumentException(e);
	    }
	    this.antiClickJackingUri = uri;
	}

	public boolean isBlockContentTypeSniffingEnabled() {
		return blockContentTypeSniffingEnabled;
	}
	
	public void setBlockContentTypeSniffingEnabled(boolean blockContentTypeSniffingEnabled) {
		this.blockContentTypeSniffingEnabled = blockContentTypeSniffingEnabled;
	}

	public boolean isXssProtectionEnabled() {
		return xssProtectionEnabled;
	}
	
	public void setXssProtectionEnabled(boolean xssProtectionEnabled) {
		this.xssProtectionEnabled = xssProtectionEnabled;
	}

	public boolean isCspEnabled() {
		return cspEnabled;
	}
	
	public void setCspEnabled(boolean cspEnabled) {
		this.cspEnabled = cspEnabled;
	}
	
	public boolean isPermittedCrossDomainPoliciesEnabled() {
		return permittedCrossDomainPoliciesEnabled;
	}
	
	public void setPermittedCrossDomainPoliciesEnabled(boolean permittedCrossDomainPoliciesEnabled) {
		this.permittedCrossDomainPoliciesEnabled = permittedCrossDomainPoliciesEnabled;
	}
	
	public String getPermittedCrossDomainPoliciesValue() {
		return permittedCrossDomainPoliciesValue;
	}
	
	public void setPermittedCrossDomainPoliciesValue(String permittedCrossDomainPoliciesValue) {
		this.permittedCrossDomainPoliciesValue = permittedCrossDomainPoliciesValue;
	}
	
	public boolean isAccessControlAllowOriginEnabled() {
		return accessControlAllowOriginEnabled;
	}
	
	public void setAccessControlAllowOriginEnabled(boolean accessControlAllowOriginEnabled) {
		this.accessControlAllowOriginEnabled = accessControlAllowOriginEnabled;
	}

	public String getAccessControlAllowOriginValue() {
		return accessControlAllowOriginValue;
	}
	
	public void setAccessControlAllowOriginValue(String accessControlAllowOriginValue) {
		this.accessControlAllowOriginValue = accessControlAllowOriginValue;
	}

	public boolean isFilterEnabled() {
		return filterEnabled;
	}
	
	public void setFilterEnabled(boolean filterEnabled) {
		this.filterEnabled = filterEnabled;
	}
	
	@Override
	protected Logger getLogger() {
		return logger;
	}
	
//	private String createCSPRuntime(HttpServletRequest httpRequest) {
//		String csp = "";
//		final String server = httpRequest.getServerName();
//		csp += this.getConnectSrcV1("'self'", server+":*");
//		return csp;
//	}

	private String createCSP() {
		String csp = "";
		/** Direttiva default-src */
		// csp += this.getDefaultSrcV1("'none'");
		/** Direttiva script-src */
		// csp += this.getScriptSrcV1("'self'", "'unsafe-inline'", "'unsafe-eval'", "https://cdn.ckeditor.com/");
		csp += this.getScriptSrcV1("'self'", "'unsafe-inline'", "'unsafe-eval'");
		/** Direttiva connect-src */
		// csp += this.getConnectSrcV1("'self'", "localhost:*", "127.0.0.1:*", "https://cdn.ckeditor.com/");
		csp += this.getConnectSrcV1("'self'", "localhost:*", "127.0.0.1:*");
		/** Direttiva img-src */
//		csp += this.getImgSrcV1("'self'");
		/** Direttiva style-src */
//		csp += this.getStyleSrcV1("'self'", "'unsafe-inline'");
		/** Direttiva font-src */
//		csp += this.getFontSrcV1("'self'");
		/** Direttiva frame-src */
		csp += this.getFrameSrcV1("'self'");
		/** Direttiva child-src */
//		csp += this.getChildSrcV2("'self'");
		/** Direttiva media-src */
//		csp += this.getMediaSrcV1("");
		/** Direttiva worker-src */
//		csp += this.getWorkerSrcV3("");
		/** Direttiva manifest-src */
//		csp += this.getManifestSrcV3("");
		/** Direttiva object-src */
//		csp += this.getObjectSrcV1("'self'");
		/** Direttiva plugin-types */
//		csp += this.getPluginTypesV2(
//					"application/x-java-applet"
//					,"application/octet-stream"
//					,"application/pdf"
//					,"application/json"
//					,"application/xml"
//					,"application/javascript"
//					,"application/zip"
//					,"application/rtf"
//					,"application/xhtml+xml"
//					,"image/png"
//					,"image/svg+xml"
//					,"image/gif"
//					,"image/jpeg"
//					,"text/css"
//					,"text/csv"
//					,"text/html"
//			  );
		return csp;
	}

	private String getDefaultSrcV1(String... sources) {
		return this.getPolicyDirective("default-src", sources);
	}

	private String getScriptSrcV1(String... sources) {
		return this.getPolicyDirective("script-src", sources);
	}

	private String getStyleSrcV1(String... sources) {
		return this.getPolicyDirective("style-src", sources);
	}

	private String getImgSrcV1(String... sources) {
		return this.getPolicyDirective("img-src", sources);
	}

	private String getConnectSrcV1(String... sources) {
		return this.getPolicyDirective("connect-src", sources);
	}

	private String getFontSrcV1(String... sources) {
		return this.getPolicyDirective("font-src", sources);
	}

	private String getObjectSrcV1(String... sources) {
		return this.getPolicyDirective("object-src", sources);
	}

	@SuppressWarnings("unused")
	private String getMediaSrcV1(String... sources) {
		return this.getPolicyDirective("media-src", sources);
	}

	@SuppressWarnings("unused")
	private String getWorkerSrcV3(String... sources) {
		return this.getPolicyDirective("worker-src", sources);
	}
	
	@SuppressWarnings("unused")
	private String getManifestSrcV3(String... sources) {
		return this.getPolicyDirective("manifest-src", sources);
	}
	
	//deprecated
	private String getFrameSrcV1(String... sources) {
		return this.getPolicyDirective("frame-src", sources);
	}

	private String getChildSrcV2(String... sources) {
		return this.getPolicyDirective("child-src", sources);
	}

	private String getPluginTypesV2(String... sources) {
		return this.getPolicyDirective("plugin-types", sources);
	}

	private String getPolicyDirective(String directive, String... sources) {
		String value = "";
		for (String source : sources) {
			if (source.trim().isEmpty())
				continue;
			value += " " + source;
		}
		return value.isEmpty() ? value : directive + value + "; ";
	}

}
