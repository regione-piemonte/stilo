package it.eng.proxyselector.jcfis;

import java.io.IOException;

import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jcifs.netbios.NbtAddress;

import jcifs.Config;

/**
 * Represents an NTLMSSP Type-1 message.
 */
public class EngType1Message extends EngNtlmMessage {
  private static final Log log = LogFactory.getLog(EngType1Message.class);
    private static final int DEFAULT_FLAGS;

    private static final String DEFAULT_DOMAIN;

    private static final String DEFAULT_WORKSTATION;

    private String suppliedDomain;

    private String suppliedWorkstation;

    static {
        DEFAULT_FLAGS = NTLMSSP_NEGOTIATE_NTLM |
                (Config.getBoolean("jcifs.smb.client.useUnicode", true) ?
                        NTLMSSP_NEGOTIATE_UNICODE : NTLMSSP_NEGOTIATE_OEM);
        DEFAULT_DOMAIN = Config.getProperty("jcifs.smb.client.domain", null);
        String defaultWorkstation = null;
        try {
            defaultWorkstation = NbtAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) { }
        DEFAULT_WORKSTATION = defaultWorkstation;
    }

    /**
     * Creates a Type-1 message using default values from the current
     * environment.
     */
    public EngType1Message() {
        this(getDefaultFlags(), getDefaultDomain(), getDefaultWorkstation());
    }

    /**
     * Creates a Type-1 message with the specified parameters.
     *
     * @param flags The flags to apply to this message.
     * @param suppliedDomain The supplied authentication domain.
     * @param suppliedWorkstation The supplied workstation name.
     */
    public EngType1Message(int flags, String suppliedDomain,
            String suppliedWorkstation) {
        setFlags(getDefaultFlags() | flags);
        setSuppliedDomain(suppliedDomain);
        if (suppliedWorkstation == null)
            suppliedWorkstation = getDefaultWorkstation();
        setSuppliedWorkstation(suppliedWorkstation);
    }

    /**
     * Creates a Type-1 message using the given raw Type-1 material.
     *
     * @param material The raw Type-1 material used to construct this message.
     * @throws IOException If an error occurs while parsing the material.
     */
    public EngType1Message(byte[] material) throws IOException {
        parse(material);
    }

    /**
     * Returns the supplied authentication domain.
     *
     * @return A <code>String</code> containing the supplied domain.
     */
    public String getSuppliedDomain() {
        return suppliedDomain;
    }

    /**
     * Sets the supplied authentication domain for this message.
     *
     * @param suppliedDomain The supplied domain for this message.
     */
    public void setSuppliedDomain(String suppliedDomain) {
        this.suppliedDomain = suppliedDomain;
    }

    /**
     * Returns the supplied workstation name.
     * 
     * @return A <code>String</code> containing the supplied workstation name.
     */
    public String getSuppliedWorkstation() {
        return suppliedWorkstation;
    }

    /**
     * Sets the supplied workstation name for this message.
     * 
     * @param suppliedWorkstation The supplied workstation for this message.
     */
    public void setSuppliedWorkstation(String suppliedWorkstation) {
        this.suppliedWorkstation = suppliedWorkstation;
    }

    public byte[] toByteArray() {
        try {
            String suppliedDomain = getSuppliedDomain();
            String suppliedWorkstation = getSuppliedWorkstation();
            int flags = getFlags();
            boolean hostInfo = false;
            byte[] domain = new byte[0];
            if (suppliedDomain != null && suppliedDomain.length() != 0) {
                hostInfo = true;
                flags |= NTLMSSP_NEGOTIATE_OEM_DOMAIN_SUPPLIED;
                domain = suppliedDomain.toUpperCase().getBytes(
                        getOEMEncoding());
            } else {
                flags &= (NTLMSSP_NEGOTIATE_OEM_DOMAIN_SUPPLIED ^ 0xffffffff);
            }
            byte[] workstation = new byte[0];
            if (suppliedWorkstation != null &&
                    suppliedWorkstation.length() != 0) {
                hostInfo = true;
                flags |= NTLMSSP_NEGOTIATE_OEM_WORKSTATION_SUPPLIED;
                workstation =
                        suppliedWorkstation.toUpperCase().getBytes(
                                getOEMEncoding());
            } else {
                flags &= (NTLMSSP_NEGOTIATE_OEM_WORKSTATION_SUPPLIED ^
                        0xffffffff);
            }
            byte[] type1 = new byte[hostInfo ?
                    (32 + domain.length + workstation.length) : 16];
            System.arraycopy(NTLMSSP_SIGNATURE, 0, type1, 0, 8);
            writeULong(type1, 8, 1);
            writeULong(type1, 12, flags);
            if (hostInfo) {
                writeSecurityBuffer(type1, 16, 32, domain);
                writeSecurityBuffer(type1, 24, 32 + domain.length, workstation);
            }
            return type1;
        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public String toString() {
        String suppliedDomain = getSuppliedDomain();
        String suppliedWorkstation = getSuppliedWorkstation();
        return "Type1Message[suppliedDomain=" + (suppliedDomain == null ? "null" : suppliedDomain) +
                ",suppliedWorkstation=" + (suppliedWorkstation == null ? "null" : suppliedWorkstation) +
                ",flags=0x" + jcifs.util.Hexdump.toHexString(getFlags(), 8) + "]";
    }

    /**
     * Returns the default flags for a generic Type-1 message in the
     * current environment.
     * 
     * @return An <code>int</code> containing the default flags.
     */
    public static int getDefaultFlags() {
        return DEFAULT_FLAGS;
    }

    /**
     * Returns the default domain from the current environment.
     *
     * @return A <code>String</code> containing the default domain.
     */
    public static String getDefaultDomain() {
        return DEFAULT_DOMAIN;
    }

    /**
     * Returns the default workstation from the current environment.
     *
     * @return A <code>String</code> containing the default workstation.
     */
    public static String getDefaultWorkstation() {
        return DEFAULT_WORKSTATION;
    }

    private void parse(byte[] material) throws IOException {
      if (log.isDebugEnabled()){
        StringBuilder sb= new StringBuilder();  
        if (material!= null) for(byte c : material) {
          sb.append(String.format("%03d ", c));
        }
        log.debug("entering parse (" + sb.toString());
      }  
      
        for (int i = 0; i < 8; i++) {
            if (material[i] != NTLMSSP_SIGNATURE[i]) {
                throw new IOException("Not an NTLMSSP message.");
            }
        }
        if (readULong(material, 8) != 1) {
            throw new IOException("Not a Type 1 message.");
        }
        int flags = readULong(material, 12);
        String suppliedDomain = null;
        if ((flags & NTLMSSP_NEGOTIATE_OEM_DOMAIN_SUPPLIED) != 0) {
            byte[] domain = readSecurityBuffer(material, 16);
            suppliedDomain = new String(domain, getOEMEncoding());
        }
        String suppliedWorkstation = null;
        if ((flags & NTLMSSP_NEGOTIATE_OEM_WORKSTATION_SUPPLIED) != 0) {
            byte[] workstation = readSecurityBuffer(material, 24);
            suppliedWorkstation = new String(workstation, getOEMEncoding());
        }
        setFlags(flags);
        setSuppliedDomain(suppliedDomain);
        setSuppliedWorkstation(suppliedWorkstation);
        log.debug("message:"+this.toString());
    }

}
