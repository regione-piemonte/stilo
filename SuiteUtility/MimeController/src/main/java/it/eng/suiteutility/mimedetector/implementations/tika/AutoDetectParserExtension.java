/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.xml.sax.SAXException;

public class AutoDetectParserExtension extends AutoDetectParser{
	
	public AutoDetectParserExtension() throws TikaException, IOException, SAXException {
		super(new TikaConfig(AutoDetectParserExtension.class.getResource("/it/eng/suiteutility/mimedetector/implementations/tika/config/tika-config.xml")));
	}
}
