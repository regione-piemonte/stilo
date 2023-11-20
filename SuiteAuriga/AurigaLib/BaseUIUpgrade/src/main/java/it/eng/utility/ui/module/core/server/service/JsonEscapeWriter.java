/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.Writer;

import com.google.gson.stream.JsonWriter;

public class JsonEscapeWriter extends JsonWriter {
	
	public JsonEscapeWriter(Writer out) {
		super(out);
	}
	
	@Override
	public JsonWriter value(String value) throws IOException {
		return super.value(value);
	}	
}