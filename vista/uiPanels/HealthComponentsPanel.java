package uiPanels;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import common.UserConnectionData;
import common.UserConnectionDataComparator;
import controlResult.BatchStatus;
import controlResult.EnvironmentStatus;
import controlResult.OfflineStatus;
import controlResult.OnlineStatus;
import uiCheckBox.CooldownCheckBox;
import uiPanels.Status.DumbStatusPanel;
import uiProfiles.ProfileControl;
import validationPackage.BatchValidation;
import validationPackage.EnvStatusValidation;
import validationPackage.OfflineValidation;
import validationPackage.OnlineValidation;
/**
 * 
 * Panel que maneja el estado de varias validaciones
 * login,online,offline... 
 *
 */
@SuppressWarnings("serial")
public class HealthComponentsPanel extends JPanel{
	private ProfileControl profile;
	
	private final int loginPos = 0;
	private final int onlinePos = 1;
	private final int offlinePos = 2;
	private final int batchPos = 3;
	
	private JPanel mainPanel;
	private boolean isMayoristas;
//	private boolean isMinoristas;
	private boolean globalChange = false;
	private HashMap<CooldownCheckBox,DumbStatusPanel[]> relation;
	
	private JPanel checkBoxPanel;
	private JCheckBox checkBoxHeader;
	private ValidationPanel loginPanel;
	private ValidationPanel onlinePanel;
	private ValidationPanel offlinePanel;
	private ValidationPanel batchPanel;
	//Lista de entornos
	private ArrayList<UserConnectionData> data;
	private ArrayList<CooldownCheckBox> validationList;
	//Lista de Validadores
	private ArrayList<DumbStatusPanel> loginList;
	//Lista de Validadores
	private ArrayList<DumbStatusPanel> onlineList;
	//Lista de Validadores
	private ArrayList<DumbStatusPanel> offlineList;
	//Lista de Validadores
	private ArrayList<DumbStatusPanel> batchList;
	
	public final long loginFreq = 500;
	public final long onlineFreq = 500;
	public final long offlineFreq = 5000;
	public final long batchFreq = 500;
	
	public HealthComponentsPanel(ArrayList<UserConnectionData> envList, boolean isAM, boolean isMin, ProfileControl profile){
		this.setOpaque(false);
		this.profile = profile;
		isMayoristas = isAM;
//		isMinoristas = isMin;
		relation = new HashMap<CooldownCheckBox, DumbStatusPanel[]>();
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
		offlineList.clear();
		batchList.clear();
		loginList.clear();
		data = envList;
		isMayoristas = isAM;
//		isMinoristas = isMin;
		mainPanel.removeAll();
		loginPanel.removeAll();
		onlinePanel.removeAll();
		offlinePanel.removeAll();
		batchPanel.removeAll();
		initialize();
		validate();
		repaint();
	}
	
	public void validateAllSelected(){
		loading();
		for(CooldownCheckBox c:validationList){
			if(c.isSelected()){
				if(!c.isVisible())
					continue;
				relation.get(c)[loginPos].validateStatus();
				relation.get(c)[onlinePos].validateStatus();
				relation.get(c)[offlinePos].validateStatus();
//				relation.get(c)[batchPos].validateStatus();
				c.activate(relation);
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
		for(DumbStatusPanel d:offlineList){
			d.resetState();
		}for(DumbStatusPanel d:batchList){
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
		offlineList = new ArrayList<DumbStatusPanel>();
		batchList = new ArrayList<DumbStatusPanel>();
		validationList = new ArrayList<CooldownCheckBox>();
		//Recorre entornos correspondientes y crea los CheckBox
		checkBoxPanel = new JPanel();
//		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		checkBoxPanel.setLayout(new GridLayout());
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(data.size()+1,1,1, 30));
		container.setOpaque(false);
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		setLayout(new GridLayout(1,1));
		add(mainPanel);
		mainPanel.setLayout(new GridLayout(1, 3, 5, 5));
		checkBoxHeader = new JCheckBox();
//		checkBoxHeader.setBorder(BorderFactory.createEmptyBorder(1, 20, 1, 1));
		container.add(checkBoxHeader);
		UserConnectionDataComparator comparator = new UserConnectionDataComparator(profile);
		Collections.sort(data, comparator);
		for(UserConnectionData d:data){
			CooldownCheckBox c = new CooldownCheckBox(profile.getHealthValidationTimeout());
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
			//Panel Offline
			DumbStatusPanel offlinePanel = new DumbStatusPanel(new OfflineValidation(d), new OfflineStatus(), d);
			offlineList.add(offlinePanel);
			//Panel Batch
			DumbStatusPanel batchPanel = new DumbStatusPanel(new BatchValidation(d), new BatchStatus(), d);
			batchList.add(batchPanel);
			DumbStatusPanel[] arr = new DumbStatusPanel[4];
			arr[loginPos] = loginPanel;
			arr[onlinePos] = onlinePanel;
			arr[offlinePos] = offlinePanel;
			arr[batchPos] = batchPanel;
			relation.put(c, arr);
			c.setVisible(d.isProfile());
		}
		checkBoxHeader.setOpaque(false);
		checkBoxHeader.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(checkBoxHeader.isSelected()==globalChange)
					return;
				for(JCheckBox c:validationList){
					if(c.isEnabled())
						c.setSelected(checkBoxHeader.isSelected());
				}
				globalChange = checkBoxHeader.isSelected();
			}
		});
		checkBoxHeader.setAlignmentX( Component.LEFT_ALIGNMENT );
//		checkBoxPanel.add(checkBoxHeader);
		checkBoxPanel.add(container);
		loginPanel = new ValidationPanel(loginList, "Login");
		onlinePanel = new ValidationPanel(onlineList, "Online");
		offlinePanel = new ValidationPanel(offlineList, "Offline");
		batchPanel = new ValidationPanel(batchList, "Batch");
		checkBoxPanel.setOpaque(false);
		loginPanel.setOpaque(false);
		onlinePanel.setOpaque(false);
		offlinePanel.setOpaque(false);
		batchPanel.setOpaque(false);
		mainPanel.add(checkBoxPanel);
		mainPanel.add(loginPanel);
		mainPanel.add(onlinePanel);
		mainPanel.add(offlinePanel);
//		mainPanel.add(batchPanel);
	}
}
