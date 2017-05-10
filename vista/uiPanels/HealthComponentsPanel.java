package uiPanels;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import common.UserConnectionData;
import common.UserConnectionDataComparator;
import controlResult.EnvironmentStatus;
import controlResult.OnlineStatus;
import uiPanels.Status.DumbStatusPanel;
import validationPackage.EnvStatusValidation;
import validationPackage.OnlineValidation;
/**
 * 
 * Panel que maneja el estado de varias validaciones
 * login,online,offline... 
 *
 */
@SuppressWarnings("serial")
public class HealthComponentsPanel extends JPanel{
	private JPanel mainPanel;
	private boolean isMayoristas;
//	private boolean isMinoristas;
	private boolean globalChange = false;
	private HashMap<JCheckBox,DumbStatusPanel[]> relation;
//	private JPanel EnvironmentPanel;
	
	private JPanel checkBoxPanel;
	private JCheckBox checkBoxHeader;
	private ValidationPanel loginPanel;
	private ValidationPanel onlinePanel;
	//Lista de entornos
	private ArrayList<UserConnectionData> data;
	private ArrayList<JCheckBox> validationList;
	//Lista de Validadores
	private ArrayList<DumbStatusPanel> loginList;
	//Lista de Validadores
	private ArrayList<DumbStatusPanel> onlineList;
	
	public HealthComponentsPanel(ArrayList<UserConnectionData> envList, boolean isAM, boolean isMin){
		this.setOpaque(false);
		isMayoristas = isAM;
//		isMinoristas = isMin;
		relation = new HashMap<JCheckBox, DumbStatusPanel[]>();
		data = envList;
		initialize();
	}
	/**
	 * Recarga los componentes de acuerdo a una nueva lista de entornos a revisar
	 * @param envList
	 * @param isAM
	 * @param isMin
	 */
	public void reloadComponents(ArrayList<UserConnectionData> envList, boolean isAM, boolean isMin){
		removeAll();
		validationList.clear();
		data.clear();
		relation.clear();
		onlineList.clear();
		loginList.clear();
		data = envList;
		isMayoristas = isAM;
//		isMinoristas = isMin;
		mainPanel.removeAll();
		loginPanel.removeAll();
		onlinePanel.removeAll();
		initialize();
		validate();
		repaint();
	}
	
	public void validateAllSelected(){
		loading();
		for(JCheckBox c:validationList){
			if(c.isSelected()){
				relation.get(c)[0].validateStatus();
				relation.get(c)[1].validateStatus();
			}
		}
		resume();
	}
	
	public void cleanAll(){
		for(DumbStatusPanel d:loginList){
			d.resetState();
		}
		for(DumbStatusPanel d:onlineList){
			d.resetState();
		}
		validate();
		repaint();
	}
	
	/**
	 * Deja el panel en espera
	 */
	private void loading(){
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		topFrame.setResizable(false);
	}
	
	/**
	 * Resume el estado del panel
	 */
	private void resume(){
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.setCursor(Cursor.getDefaultCursor());
		topFrame.setResizable(true);
	}
	
	private void initialize(){
		
		loginList = new ArrayList<DumbStatusPanel>();
		onlineList = new ArrayList<DumbStatusPanel>();
		validationList = new ArrayList<JCheckBox>();
		//Recorre entornos correspondientes y crea los CheckBox
		checkBoxPanel = new JPanel();
//		checkBoxPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(data.size(),1));
		container.setOpaque(false);
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		setLayout(new GridLayout(1,1));
		add(mainPanel);
		mainPanel.setLayout(new GridLayout(1, 3, 5, 5));
		checkBoxHeader = new JCheckBox();
		UserConnectionDataComparator comparator = new UserConnectionDataComparator();
		Collections.sort(data, comparator);
		for(UserConnectionData d:data){
			JCheckBox c = new JCheckBox();
			c.setText(d.getEnvName());
			c.setOpaque(false);
			container.add(c);
			validationList.add(c);
			//Panel Login
			DumbStatusPanel loginPanel = new DumbStatusPanel(new EnvStatusValidation(d), new EnvironmentStatus(), d);
			loginList.add(loginPanel);
			//Panel Online
			DumbStatusPanel onlinePanel = new DumbStatusPanel(new OnlineValidation(d, isMayoristas), new OnlineStatus(), d);
			onlineList.add(onlinePanel);
			DumbStatusPanel[] arr = new DumbStatusPanel[2];
			arr[0] = loginPanel;
			arr[1] = onlinePanel;
			relation.put(c, arr);
		}
		checkBoxHeader.setOpaque(false);
		checkBoxHeader.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(checkBoxHeader.isSelected()==globalChange)
					return;
				for(JCheckBox c:validationList){
					c.setSelected(checkBoxHeader.isSelected());
				}
				globalChange = checkBoxHeader.isSelected();
			}
		});
		checkBoxHeader.setAlignmentX( Component.LEFT_ALIGNMENT );
		checkBoxPanel.add(checkBoxHeader);
		checkBoxPanel.add(container);
		loginPanel = new ValidationPanel(loginList, "Login");
		onlinePanel = new ValidationPanel(onlineList, "Online");
		checkBoxPanel.setOpaque(false);
		loginPanel.setOpaque(false);
		onlinePanel.setOpaque(false);
		mainPanel.add(checkBoxPanel);
		mainPanel.add(loginPanel);
		mainPanel.add(onlinePanel);
	}
}
