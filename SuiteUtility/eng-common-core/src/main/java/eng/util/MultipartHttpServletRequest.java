// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.RequestDispatcher;
import java.util.Locale;
import java.io.BufferedReader;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import java.io.IOException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import java.io.File;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;

public class MultipartHttpServletRequest implements HttpServletRequest
{
    private boolean isMultiPart;
    private Hashtable values;
    private Hashtable files;
    private HttpServletRequest l_req;
    
    public MultipartHttpServletRequest(final HttpServletRequest req, final String pathUpload) throws FileUploadException, IOException {
        this.values = null;
        this.files = null;
        this.l_req = null;
        this.isMultiPart = ServletFileUpload.isMultipartContent(req);
        this.l_req = req;
        final File temp_dir = new File(pathUpload);
        String temp_file_name = "";
        String fileName = "";
        String pathFileName = "";
        String extFile = "";
        String tmpFileNameNoExt = "";
        int idx = 0;
        if (this.isMultiPart) {
            this.values = new Hashtable();
            this.files = new Hashtable();
            final DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(0);
            final ServletFileUpload upload = new ServletFileUpload((FileItemFactory)factory);
            final List items = upload.parseRequest(req);
            final Iterator i = items.iterator();
            DiskFileItem item = null;
            File temp_f = null;
            while (i.hasNext()) {
                item = i.next();
                if (item.isFormField()) {
                    this.values.put(item.getFieldName(), item.getString());
                }
                else {
                    try {
                        if ((temp_f = item.getStoreLocation()).length() > 0L) {
                            extFile = "";
                            pathFileName = ((item.getName() == null) ? "" : item.getName());
                            idx = pathFileName.lastIndexOf(".");
                            if (idx != -1) {
                                extFile = pathFileName.substring(idx);
                            }
                            tmpFileNameNoExt = temp_f.getName();
                            idx = temp_f.getName().lastIndexOf(".");
                            if (idx != -1) {
                                tmpFileNameNoExt = temp_f.getName().substring(0, idx);
                            }
                            fileName = tmpFileNameNoExt + extFile;
                            final File newFile = new File(temp_dir, fileName);
                            FileUtils.copyFile(temp_f, newFile, true);
                            temp_file_name = fileName;
                        }
                        else {
                            temp_file_name = "";
                        }
                    }
                    catch (IOException e) {
                        throw new FileUploadException("MultipartHttpServletRequest: impossibile salvare allegato in cartella temporanea. Errore:" + e.getMessage());
                    }
                    this.files.put(item.getFieldName(), new UploadedFile(FilenameUtils.getName(item.getName()), temp_file_name));
                }
                item.delete();
            }
        }
    }
    
    public Object get(final String name) {
        Object result = null;
        if (this.values != null) {
            result = this.values.get(name);
            if (result instanceof Vector) {
                if (((Vector)result).size() == 1) {
                    return ((Vector)result).elementAt(0);
                }
                return result;
            }
        }
        else {
            final String[] array = this.l_req.getParameterValues(name);
            final Vector vec = new Vector();
            if (array != null) {
                for (int i = 0; i < array.length; ++i) {
                    vec.addElement(array[i]);
                }
                if (vec.size() == 1) {
                    result = vec.elementAt(0);
                }
                else {
                    result = vec;
                }
            }
        }
        return result;
    }
    
    public String getParameter(final String name) {
        if (this.values != null) {
            Object value = this.get(name);
            String result = null;
            if (value != null) {
                if (value instanceof Vector) {
                    value = ((Vector)value).elementAt(0);
                }
                result = value.toString();
            }
            return result;
        }
        return this.l_req.getParameter(name);
    }
    
    public String[] getParameterValues(final String name) {
        if (this.values == null) {
            return this.l_req.getParameterValues(name);
        }
        final Object value = this.get(name);
        if (value == null) {
            return null;
        }
        if (value instanceof Vector) {
            final String[] results = new String[((Vector)value).size()];
            for (int i = 0; i < ((Vector)value).size(); ++i) {
                results[i] = ((Vector)value).elementAt(i).toString();
            }
            return results;
        }
        return new String[] { value.toString() };
    }
    
    public Enumeration getFilesEnumeration() {
        return this.files.elements();
    }
    
    public Map getParameterMap() {
        return this.values;
    }
    
    public Enumeration getParameterNames() {
        if (this.values != null) {
            return this.values.keys();
        }
        return this.l_req.getParameterNames();
    }
    
    public int countFiles() {
        if (this.files == null) {
            return 0;
        }
        boolean allEmptyFiles = true;
        final Enumeration e = this.files.elements();
        ByteArrayInputStream l_b_stream = null;
        while (e.hasMoreElements()) {
            l_b_stream = (ByteArrayInputStream)e.nextElement().getInputStream();
            allEmptyFiles = (allEmptyFiles && l_b_stream.available() == 0);
        }
        if (allEmptyFiles) {
            return 0;
        }
        return this.files.size();
    }
    
    public HttpServletRequest getUnderlyingRequest() {
        return this.l_req;
    }
    
    public Object getAttribute(final String name) {
        return this.l_req.getAttribute(name);
    }
    
    public Enumeration getAttributeNames() {
        return this.l_req.getAttributeNames();
    }
    
    public String getCharacterEncoding() {
        return this.l_req.getCharacterEncoding();
    }
    
    public int getContentLength() {
        return this.l_req.getContentLength();
    }
    
    public String getContentType() {
        return this.l_req.getContentType();
    }
    
    public ServletInputStream getInputStream() throws IOException {
        return this.l_req.getInputStream();
    }
    
    public String getProtocol() {
        return this.l_req.getProtocol();
    }
    
    public String getScheme() {
        return this.l_req.getScheme();
    }
    
    public String getServerName() {
        return this.l_req.getServerName();
    }
    
    public int getServerPort() {
        return this.l_req.getServerPort();
    }
    
    public BufferedReader getReader() throws IOException {
        return this.l_req.getReader();
    }
    
    public String getRemoteAddr() {
        return this.l_req.getRemoteAddr();
    }
    
    public String getRemoteHost() {
        return this.l_req.getRemoteHost();
    }
    
    public void setAttribute(final String name, final Object o) {
        this.l_req.setAttribute(name, o);
    }
    
    public void removeAttribute(final String name) {
        this.l_req.removeAttribute(name);
    }
    
    public Locale getLocale() {
        return this.l_req.getLocale();
    }
    
    public Enumeration getLocales() {
        return this.l_req.getLocales();
    }
    
    public boolean isSecure() {
        return this.l_req.isSecure();
    }
    
    public RequestDispatcher getRequestDispatcher(final String path) {
        return this.l_req.getRequestDispatcher(path);
    }
    
    public String getRealPath(final String path) {
        return this.l_req.getRealPath(path);
    }
    
    public String getAuthType() {
        return this.l_req.getAuthType();
    }
    
    public Cookie[] getCookies() {
        return this.l_req.getCookies();
    }
    
    public long getDateHeader(final String name) {
        return this.l_req.getDateHeader(name);
    }
    
    public String getHeader(final String name) {
        return this.l_req.getHeader(name);
    }
    
    public Enumeration getHeaders(final String name) {
        return this.l_req.getHeaders(name);
    }
    
    public Enumeration getHeaderNames() {
        return this.l_req.getHeaderNames();
    }
    
    public int getIntHeader(final String name) {
        return this.l_req.getIntHeader(name);
    }
    
    public String getMethod() {
        return this.l_req.getMethod();
    }
    
    public String getPathInfo() {
        return this.l_req.getPathInfo();
    }
    
    public String getPathTranslated() {
        return this.l_req.getPathTranslated();
    }
    
    public String getContextPath() {
        return this.l_req.getContextPath();
    }
    
    public String getQueryString() {
        return this.l_req.getQueryString();
    }
    
    public String getRemoteUser() {
        return this.l_req.getRemoteUser();
    }
    
    public boolean isUserInRole(final String role) {
        return this.l_req.isUserInRole(role);
    }
    
    public Principal getUserPrincipal() {
        return this.l_req.getUserPrincipal();
    }
    
    public String getRequestedSessionId() {
        return this.l_req.getRequestedSessionId();
    }
    
    public String getRequestURI() {
        return this.l_req.getRequestURI();
    }
    
    public String getServletPath() {
        return this.l_req.getServletPath();
    }
    
    public HttpSession getSession(final boolean create) {
        return this.l_req.getSession(create);
    }
    
    public HttpSession getSession() {
        return this.l_req.getSession();
    }
    
    public boolean isRequestedSessionIdValid() {
        return this.l_req.isRequestedSessionIdValid();
    }
    
    public boolean isRequestedSessionIdFromCookie() {
        return this.l_req.isRequestedSessionIdFromCookie();
    }
    
    public boolean isRequestedSessionIdFromURL() {
        return this.l_req.isRequestedSessionIdFromURL();
    }
    
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return this.l_req.isRequestedSessionIdFromURL();
    }
    
    public StringBuffer getRequestURL() {
        return null;
    }
    
    public void setCharacterEncoding(final String arg0) throws UnsupportedEncodingException {
    }
    
    public String getLocalAddr() {
        return null;
    }
    
    public String getLocalName() {
        return null;
    }
    
    public int getLocalPort() {
        return 0;
    }
    
    public int getRemotePort() {
        return 0;
    }
    
    public class UploadedFile
    {
        private String l_name;
        private String l_tempName;
        private InputStream l_file;
        
        public UploadedFile(final InputStream stream, final String name, final String tempName) {
            this.l_file = null;
            this.l_name = name;
            this.l_tempName = tempName;
            this.l_file = stream;
        }
        
        public UploadedFile(final String name, final String tempName) {
            this.l_file = null;
            this.l_name = name;
            this.l_tempName = tempName;
            this.l_file = null;
        }
        
        public void setName(final String name) {
            this.l_name = name;
        }
        
        public void setTempName(final String name) {
            this.l_tempName = name;
        }
        
        public void setInputStream(final InputStream stream) {
            this.l_file = stream;
        }
        
        public String getName() {
            return this.l_name;
        }
        
        public String getTempName() {
            return this.l_tempName;
        }
        
        public InputStream getInputStream() {
            return this.l_file;
        }
    }
}
