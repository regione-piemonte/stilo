/*
 * Copyright 2005 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
/*
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 */
/*
 * $Id: ApacheData.java 375655 2006-02-07 18:35:54Z mullan $
 */
package it.eng.utility.cryptosigner.provider.dom;

import javax.xml.crypto.Data;

import org.apache.xml.security.signature.XMLSignatureInput;

/**
 * XMLSignatureInput Data wrapper. 
 *
 * @author Sean Mullan
 */
public interface ApacheData extends Data {

 
    public XMLSignatureInput getXMLSignatureInput();
}
