package uiPanels;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * 
 * Engloba los checklist para filtrar entornos por Mayoristas/Minoristas
 *
 */
@SuppressWarnings("serial")
public class FilterEnvironmentPanel extends JPanel{
	private JCheckBox Minoristas;
	private JCheckBox Mayoristas;
	public FilterEnvironmentPanel(JCheckBox Minoristas, JCheckBox Mayoristas){
		this.Minoristas = Minoristas;
		this.Mayoristas = Mayoristas;
		initialize();
	}
	private void initialize(){
		add(Minoristas);
		add(Mayoristas);
	}
}
