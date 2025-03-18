/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.nio.charset.Charset;

public interface HTMLCleaner {

	String convertToString(String htmlContent, Charset charset);

	String convertToString(String htmlContent);

}
