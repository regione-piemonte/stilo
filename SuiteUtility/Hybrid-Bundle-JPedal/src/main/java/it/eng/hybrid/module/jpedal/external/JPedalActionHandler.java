/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.viewer.Commands;

public interface JPedalActionHandler {
	public void actionPerformed(SwingGUI currentGUI, Commands commands);
}
