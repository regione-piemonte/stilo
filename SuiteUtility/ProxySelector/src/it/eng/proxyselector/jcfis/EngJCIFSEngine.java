package it.eng.proxyselector.jcfis;
import java.io.IOException;

import jcifs.ntlmssp.NtlmFlags;
import jcifs.ntlmssp.Type1Message;

import jcifs.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMEngineException;

public final class EngJCIFSEngine implements NTLMEngine {
  private static final Log log = LogFactory.getLog(EngJCIFSEngine.class);
    private static final int TYPE_1_FLAGS = 
            NtlmFlags.NTLMSSP_NEGOTIATE_56 | 
            NtlmFlags.NTLMSSP_NEGOTIATE_128 | 
            NtlmFlags.NTLMSSP_NEGOTIATE_NTLM2 | 
            NtlmFlags.NTLMSSP_NEGOTIATE_ALWAYS_SIGN | 
            NtlmFlags.NTLMSSP_REQUEST_TARGET;

    public String generateType1Msg(final String domain, final String workstation)
            throws NTLMEngineException {
      log.debug("entering  generateType1Msg (domain:"+domain+" workstation:"+workstation);
        final Type1Message type1Message = new Type1Message(TYPE_1_FLAGS, domain, workstation);
        log.debug("type1Message :"+type1Message );
        return Base64.encode(type1Message.toByteArray());
    }

    public String generateType3Msg(final String username, final String password,
            final String domain, final String workstation, final String challenge)
            throws NTLMEngineException {
      log.debug("entering  generateType3Msg ( username:"+username+", domain:"+domain+", workstation:"+workstation+", challenge:"+challenge);
        EngType2Message type2Message;
        try {
            type2Message = new EngType2Message(Base64.decode(challenge));
            log.debug("type2Message:"+type2Message);
        } catch (final IOException exception) {
            throw new NTLMEngineException("Invalid NTLM type 2 message", exception);
        }
        final int type2Flags = type2Message.getFlags();
        final int type3Flags = type2Flags
                & (0xffffffff ^ (NtlmFlags.NTLMSSP_TARGET_TYPE_DOMAIN | NtlmFlags.NTLMSSP_TARGET_TYPE_SERVER));
        final EngType3Message type3Message = new EngType3Message(type2Message, password, domain,
                username, workstation, type3Flags);
        log.debug("type3Message:"+type3Message);
        return Base64.encode(type3Message.toByteArray());
    }

}

