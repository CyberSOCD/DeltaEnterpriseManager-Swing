package obfuscatorPanelTest;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;

import vista.ui.Panels.ReportingPanel;
import vista.ui.Profiles.ProfileControl.TipoSistema;

public class ReportingTest {
	
public static void main(String[] args) throws Exception{
	SimpleMainFrame frame = new SimpleMainFrame(true);
	ReportingPanel pn = new ReportingPanel(frame.getData(), frame.getProfile(), TipoSistema.MINORISTA);
	pn.setAlignmentX(ReportingPanel.RIGHT_ALIGNMENT);
	frame.setSubPanel(pn,new BoxLayout(frame.getContentPane(),BoxLayout.PAGE_AXIS));
}

}
