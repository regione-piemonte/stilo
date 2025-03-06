package it.eng.hybrid.module.jpedal.external;

import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.viewer.Commands;

public interface JPedalActionHandler {
	public void actionPerformed(SwingGUI currentGUI, Commands commands);
}
