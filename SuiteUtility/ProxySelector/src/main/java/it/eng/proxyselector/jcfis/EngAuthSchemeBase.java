package it.eng.proxyselector.jcfis;


import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.NotThreadSafe;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

/**
 * Abstract authentication scheme class that serves as a basis
 * for all authentication schemes supported by HttpClient. This class
 * defines the generic way of parsing an authentication challenge. It
 * does not make any assumptions regarding the format of the challenge
 * nor does it impose any specific way of responding to that challenge.
 *
 *
 * @since 4.0
 */
@NotThreadSafe
public abstract class EngAuthSchemeBase implements ContextAwareAuthScheme {
    private static final Log log = LogFactory.getLog(EngAuthSchemeBase.class);
    private ChallengeState challengeState;

    /**
     * Creates an instance of <tt>AuthSchemeBase</tt> with the given challenge
     * state.
     *
     * @since 4.2
     */
    public EngAuthSchemeBase(final ChallengeState challengeState) {
        super();
        this.challengeState = challengeState;
    }

    public EngAuthSchemeBase() {
        this(null);
    }

    /**
     * Processes the given challenge token. Some authentication schemes
     * may involve multiple challenge-response exchanges. Such schemes must be able
     * to maintain the state information when dealing with sequential challenges
     *
     * @param header the challenge header
     *
     * @throws MalformedChallengeException is thrown if the authentication challenge
     * is malformed
     */
    public void processChallenge(final Header header) throws MalformedChallengeException {
       log.debug("entering processChallenge"+header);
        if (header == null) {
            throw new IllegalArgumentException("Header may not be null");
        }
        String authheader = header.getName();
        if (authheader.equalsIgnoreCase(AUTH.WWW_AUTH)) {
            this.challengeState = ChallengeState.TARGET;
        } else if (authheader.equalsIgnoreCase(AUTH.PROXY_AUTH)) {
            this.challengeState = ChallengeState.PROXY;
        } else {
            throw new MalformedChallengeException("Unexpected header name: " + authheader);
        }

        CharArrayBuffer buffer;
        int pos;
        if (header instanceof FormattedHeader) {
            buffer = ((FormattedHeader) header).getBuffer();
            pos = ((FormattedHeader) header).getValuePos();
        } else {
            String s = header.getValue();
            if (s == null) {
                throw new MalformedChallengeException("Header value is null");
            }
            buffer = new CharArrayBuffer(s.length());
            buffer.append(s);
            pos = 0;
        }
        while (pos < buffer.length() && HTTP.isWhitespace(buffer.charAt(pos))) {
            pos++;
        }
        int beginIndex = pos;
        while (pos < buffer.length() && !HTTP.isWhitespace(buffer.charAt(pos))) {
            pos++;
        }
        int endIndex = pos;
        String s = buffer.substring(beginIndex, endIndex);
        if (!s.equalsIgnoreCase(getSchemeName())) {
            throw new MalformedChallengeException("Invalid scheme identifier: " + s);
        }
        log.debug("buffer :>"+buffer+"< pos:"+pos+" length:"+buffer.length());
        parseChallenge(buffer, pos, buffer.length());
    }


    @SuppressWarnings("deprecation")
    public Header authenticate(
            final Credentials credentials,
            final HttpRequest request,
            final HttpContext context) throws AuthenticationException {
      log.debug("entering  authenticate (credentials:"+credentials +",request:"+request);
        return authenticate(credentials, request);
    }

    protected abstract void parseChallenge(
            CharArrayBuffer buffer, int beginIndex, int endIndex) throws MalformedChallengeException;

    /**
     * Returns <code>true</code> if authenticating against a proxy, <code>false</code>
     * otherwise.
     */
    public boolean isProxy() {
        return this.challengeState != null && this.challengeState == ChallengeState.PROXY;
    }

    /**
     * Returns {@link ChallengeState} value or <code>null</code> if unchallenged.
     *
     * @since 4.2
     */
    public ChallengeState getChallengeState() {
        return this.challengeState;
    }

    @Override
    public String toString() {
        String name = getSchemeName();
        if (name != null) {
            return name.toUpperCase(Locale.US);
        } else {
            return super.toString();
        }
    }

}
