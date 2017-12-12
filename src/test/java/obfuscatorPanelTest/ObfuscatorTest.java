package obfuscatorPanelTest;

import vista.ui.Panels.ObfuscatorPanel;
import vista.ui.Profiles.ProfileControl.TipoSistema;

public class ObfuscatorTest {

	public static void main(String[] args) throws Exception{
		SimpleMainFrame frame = new SimpleMainFrame();
		ObfuscatorPanel pn = new ObfuscatorPanel(frame.getData(), frame.getProfile(), TipoSistema.MINORISTA);
		frame.setSubPanel(pn);
	}
	
}
