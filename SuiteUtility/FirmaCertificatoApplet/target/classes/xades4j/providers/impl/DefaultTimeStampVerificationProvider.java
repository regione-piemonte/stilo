/*
 * XAdES4j - A Java library for generation and verification of XAdES signatures.
 * Copyright (C) 2010 Luis Goncalves.
 *
 * XAdES4j is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or any later version.
 *
 * XAdES4j is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with XAdES4j. If not, see <http://www.gnu.org/licenses/>.
 */
package xades4j.providers.impl;

import java.util.Date;

import xades4j.providers.TimeStampTokenVerificationException;
import xades4j.providers.TimeStampVerificationProvider;

/**
 * Default implementation of {@code TimeStampVerificationProvider}. It verifies
 * the token signature, including the TSA certificate, and the digest imprint.
 * <p>
 * The implementation is based on Bouncy Castle and <b>only supports DER-encoded tokens</b>.
 * @author Luís
 */
public class DefaultTimeStampVerificationProvider implements TimeStampVerificationProvider
{

  @Override
  public Date verifyToken(byte[] timeStampToken, byte[] tsDigestInput) throws TimeStampTokenVerificationException {
    throw new IllegalAccessError("TODO");
  }

}
