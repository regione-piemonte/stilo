/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.swing.JApplet;

public interface CommonNameProvider {
	public boolean sendCommonName(String commonName);
	public void saveOutputParameter(JApplet applet) throws Exception;
}
