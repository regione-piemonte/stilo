/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.gui.SwingGUI;

public interface JPedalActionHandler {
	public void actionPerformed(SwingGUI currentGUI, Commands commands);
}
