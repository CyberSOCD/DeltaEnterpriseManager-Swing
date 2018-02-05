package vista.ui.Tabs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import controlador.common.UserConnectionData;
import vista.ui.Panels.ReportingPanel;
import vista.ui.Profiles.ProfileControl;
import vista.ui.Profiles.ProfileControl.TipoSistema;

@SuppressWarnings("serial")
public class ReportingControlTab extends GenericControlTab{
	private ArrayList<UserConnectionData> data;
	private TipoSistema sistema;
	private ProfileControl profile;
	private JPanel mainPanel;
	private JPanel panelActive;
	private JPanel buttonsPanel;
	private ReportingPanel reportingPanel;

	public ReportingControlTab(ArrayList<UserConnectionData> ListData, TipoSistema tipo, ProfileControl profile) {
		super(tipo, profile);
		data = ListData;
		sistema = tipo;
		this.profile = profile;
		initialize();
	}

	@Override
	public void loadComponent() {
		
	}

	@Override
	protected void changeSelected(JPanel panel) {
		if(!panel.equals(panelActive)){
			panelActive = panel;
			if(panelActive.equals(panelMayorista)){
				unSelected(panelMinorista);
				sistema = TipoSistema.MAYORISTA;
			}
			else{
				unSelected(panelMayorista);
				sistema = TipoSistema.MINORISTA;
			}
			setSelected(panelActive);
			reportingPanel.changeSystem(sistema);
			validate();
			repaint();
		}
	}
	
	private void initialize(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		setLayout(new GridLayout(1,1));
		add(mainPanel);
		reportingPanel = new ReportingPanel(data, profile, sistema);
		panelActive = panelMinorista;
		buttonsPanel = new JPanel();
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.ipady = 70;
		gridBag.ipadx = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		mainPanel.add(profilePanel, BorderLayout.NORTH);
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.ipady = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		mainPanel.add(reportingPanel, BorderLayout.WEST);
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.ipady = 70;
		gridBag.ipadx = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 2;
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
	}

}
