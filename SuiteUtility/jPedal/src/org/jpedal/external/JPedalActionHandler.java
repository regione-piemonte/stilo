package org.jpedal.external;

import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.gui.SwingGUI;

public interface JPedalActionHandler {
	public void actionPerformed(SwingGUI currentGUI, Commands commands);
}
