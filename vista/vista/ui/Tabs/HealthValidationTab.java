package vista.ui.Tabs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vista.ui.Panels.HealthComponentsPanel;
import vista.ui.Profiles.ProfileControl;
import vista.ui.Profiles.ProfileControl.TipoSistema;
import controlador.common.UserConnectionData;

/**
 * 
 * Controla la carga de componentes y lo datos para cargar los datos
 *
 */
@SuppressWarnings("serial")
public class HealthValidationTab extends GenericControlTab{
	private ProfileControl profile;
	private ArrayList<UserConnectionData> data;
	private ArrayList<UserConnectionData> filterData;
	private JPanel mainPanel;
	private JPanel validationsPanel;
	private JPanel buttonsPanel;
	private JPanel panelActive;
	private HealthComponentsPanel pan;
	private JButton buttonValidate;
	private JButton buttonClean;
	private TipoSistema sistema;
	
	public HealthValidationTab(ArrayList<UserConnectionData> ListData, TipoSistema tipo,ProfileControl profile){
		super(tipo, profile);
		sistema = tipo;
		this.profile = profile;
		data = ListData;
 		initialize();
	}
	
	public void loadComponent() {
		
	}
	
	private void initialize(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		setLayout(new GridLayout(1,1));
		add(mainPanel);
		panelActive = panelMinorista;
		filterData = profile.getSystemProfileList(data, sistema);
		pan = new HealthComponentsPanel(filterData, sistema, profile);
		
		validationsPanel = new JPanel();
		validationsPanel.setLayout(new GridLayout(1,1));
		validationsPanel.add(pan);
		
		buttonsPanel = new JPanel();
		
		buttonValidate = new JButton("Validar");
		buttonValidate.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				globalValidation();
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		buttonsPanel.add(buttonValidate);
		
		buttonClean = new JButton("Limpiar");
		buttonClean.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				cleanValidation();
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		buttonsPanel.add(buttonClean);
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.ipady = 70;
		gridBag.ipadx = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		mainPanel.add(profilePanel, BorderLayout.NORTH);
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.ipady = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		mainPanel.add(validationsPanel, BorderLayout.WEST);
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.ipady = 70;
		gridBag.ipadx = 70;
		gridBag.weightx = 0.0;
		gridBag.gridwidth = 3;
		gridBag.gridx = 0;
		gridBag.gridy = 2;
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Comprueba todos los validadores activos en el componenete provocando su validación
	 */
	private void globalValidation(){
		pan.validateAllSelected();
	}
	
	private void cleanValidation(){
		pan.cleanAll();
	}
	
	protected void changeSelected(JPanel panel){
		if(!panel.equals(panelActive)){
			panelActive = panel;
			if(panelActive.equals(panelMayorista)){
				sistema = TipoSistema.MAYORISTA;
				filterData = profile.getSystemProfileList(data, sistema);
				unSelected(panelMinorista);
				pan.reloadComponents(filterData, sistema);
			}
			else{
				sistema = TipoSistema.MINORISTA;
				filterData = profile.getSystemProfileList(data, sistema);
				unSelected(panelMayorista);
				pan.reloadComponents(filterData, sistema);
			}
			setSelected(panelActive);
		}
	}
}
