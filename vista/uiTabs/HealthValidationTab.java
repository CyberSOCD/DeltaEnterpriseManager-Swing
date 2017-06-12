package uiTabs;

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

import uiPanels.HealthComponentsPanel;
import uiProfiles.ProfileControl;
import common.UserConnectionData;

/**
 * 
 * Controla la carga de componentes y lo datos para cargar los datos
 *
 */
@SuppressWarnings("serial")
public class HealthValidationTab extends GenericControlTab{
	private ProfileControl profile;
	private boolean isMayoristas;
	private boolean isMinoristas;
	private boolean isAdmin = false;
	private ArrayList<UserConnectionData> data;
	private JPanel mainPanel;
	private JPanel validationsPanel;
	private JPanel profilePanel;
	private JPanel buttonsPanel;
	private JPanel panelMinorista;
	private JPanel panelMayorista;
	private JPanel panelActive;
	private JLabel labelMin;
	private JLabel labelAM;
	private HealthComponentsPanel pan;
	private JButton buttonValidate;
	private JButton buttonClean;
	
	public HealthValidationTab(ArrayList<UserConnectionData> ListData, boolean isAM,boolean isMIN,ProfileControl profile){
		this.profile = profile;
		isMayoristas = isAM;
		isMinoristas = isMIN;
		data = ListData;
		isAdmin = isAM && isMIN;
		if(isAdmin){
			isMayoristas = false;
		}
		initialize();
	}
	
	public void loadComponent() {
		
	}
	
	private void initialize(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		setLayout(new GridLayout(1,1));
		add(mainPanel);
		profilePanel = new JPanel();
		profilePanel.setLayout(new GridLayout(1,2));
		panelMinorista = new JPanel();
		panelMinorista.setBorder(BorderFactory.createRaisedBevelBorder());
		panelMayorista = new JPanel();
		panelMayorista.setBorder(BorderFactory.createRaisedBevelBorder());
		panelActive = panelMinorista;
		pan = new HealthComponentsPanel(getList(), isMayoristas,isMinoristas, profile);
		if(isAdmin){
		panelMayorista.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				changeSelected((JPanel) e.getSource());
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		panelMinorista.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				changeSelected((JPanel) e.getSource());
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		}
		if(isAdmin){
			//Si es administrador por defecto min
			setSelected(panelMinorista);
		}else if(isMayoristas){
			setSelected(panelMayorista);
		}else if(isMinoristas){
			setSelected(panelMinorista);
		}
		labelMin = new JLabel("MINORISTAS");
		labelAM = new JLabel("MAYORISTAS");
		panelMayorista.add(labelAM);
		panelMinorista.add(labelMin);
		
		labelMin.setAlignmentX(CENTER_ALIGNMENT);
		labelMin.setAlignmentY(CENTER_ALIGNMENT);
		labelAM.setAlignmentX(CENTER_ALIGNMENT);
		labelAM.setAlignmentY(CENTER_ALIGNMENT);
		if(isMinoristas)
			profilePanel.add(panelMinorista);
		if(isMayoristas || isAdmin)
			profilePanel.add(panelMayorista);
		
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
	
	private void changeSelected(JPanel panel){
		if(!panel.equals(panelActive)){
			panelActive = panel;
			if(panelActive.equals(panelMayorista)){
				unSelected(panelMinorista);
				pan.reloadComponents(getList(), true, false);
				isMayoristas = true;
				isMinoristas = false;
			}
			else{
				unSelected(panelMayorista);
				pan.reloadComponents(getList(), false, true);
				isMayoristas = false;
				isMinoristas = true;
			}
			setSelected(panelActive);
		}
	}
	
	private void setSelected(JPanel panel){
		panel.getBackground().darker();
		panel.setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	private void unSelected(JPanel panel){
		panel.getBackground().brighter();
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
	private ArrayList<UserConnectionData> getList(){
		ArrayList<UserConnectionData> aux = new ArrayList<UserConnectionData>();
		boolean isAM = isMayoristas;
		boolean isMIN = isMinoristas;
		if(panelActive.equals(panelMayorista)){
			isAM = true;
			isMIN = false;
		}else if(panelActive.equals(panelMinorista)){
			isMIN = true;
			isAM = false;
		}
		if(!isAdmin){
			for(UserConnectionData d:data){
				if(isMayoristas){
					if(d.getEnvName().contains("AM")){
						aux.add(d);
					}
				}else if(isMinoristas){
					if(!d.getEnvName().contains("AM")){
						aux.add(d);
					}
				}
			}
		}else{
			for(UserConnectionData d:data){
				if(isAM){
					if(d.getEnvName().contains("AM")){
						aux.add(d);
					}
				}else if(isMIN){
					if(!d.getEnvName().contains("AM")){
						aux.add(d);
					}
				}
			}
		}
		return aux;
	}
}
