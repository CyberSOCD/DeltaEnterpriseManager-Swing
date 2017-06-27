package uiPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import uiFrames.InitialFrame;
import uiPanels.Status.StatusPanel;
import uiProfiles.ProfileControl;
import common.UserConnectionData;
import common.UserConnectionDataComparator;

/**
 * 
 * Panel que muestra todos los entornos y permite comprobar su estado
 *
 */
public class EnvironmentPanel extends JPanel{
	
	private ProfileControl profile;
	private static final long serialVersionUID = -7317025916951652718L;
	private ArrayList<StatusPanel> envList = new ArrayList<StatusPanel>();
	private JButton UpdateStatus;
	private JPanel statusPanelContainer;
	private JCheckBox Minoristas;
	private JCheckBox Mayoristas;
	private FilterEnvironmentPanel filter;
	private ArrayList<UserConnectionData> data;
	private boolean isTesting = true;
	private boolean isMinoristas;
	private boolean isMayoristas;
	private InitialFrame parent;
	
	public EnvironmentPanel(ArrayList<UserConnectionData> ListData, boolean isAm,boolean isMin, ProfileControl profile, InitialFrame parent){
		// Inicializa la lista de paneles de estado
		this.parent = parent; 
		this.profile = profile;
		isMinoristas = isMin;
		isMayoristas = isAm;
		data = ListData;
		setLayout(new BorderLayout());
		initialize();
		for(StatusPanel p:envList){
			p.startValidation();
		}
	}
	/**
	 * Inicializa todos los componentes
	 */
	private void initialize(){
		UserConnectionDataComparator comparator = new UserConnectionDataComparator(profile);
		Collections.sort(data,comparator);
		for(UserConnectionData info:data){
			if(profile.isAdmin()){
				StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
				envList.add(pan);
				if(!info.isProfile()){
					pan.desactivatePanel();
				}
			}else if(isMinoristas && !isMayoristas){
				if(!info.getEnvName().contains(" AM")){
					StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
					envList.add(pan);
					if(!info.isProfile()){
						pan.desactivatePanel();
					}
				}
			}else if (!isMinoristas && isMayoristas){
				if(info.getEnvName().contains(" AM")){
					StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
					envList.add(pan);
					if(!info.isProfile()){
						pan.desactivatePanel();
					}
				}
			}
		}
		
		statusPanelContainer = new JPanel();
		statusPanelContainer.setLayout(new GridLayout(envList.size(),1));
		Minoristas = new JCheckBox();
		Minoristas.setText("Minoristas");
		Minoristas.setSelected(isMinoristas||profile.isAdmin());
		Minoristas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateEnvironment();
			}
		});
		Minoristas.setEnabled(false);
		Mayoristas = new JCheckBox();
		Mayoristas.setText("Mayoristas");
		Mayoristas.setSelected(isMayoristas||profile.isAdmin());
		Mayoristas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateEnvironment();
			}
		});
		Mayoristas.setEnabled(false);
//		System.out.println(profile.getSelectedProfile());
		if(profile.isAdmin()){
			Mayoristas.setEnabled(true);
			Minoristas.setEnabled(true);
		}else{
			Mayoristas.setVisible(isMayoristas);
			Minoristas.setVisible(isMinoristas);
		}
		
		filter = new FilterEnvironmentPanel(Minoristas, Mayoristas);
		add(filter, BorderLayout.NORTH);
		filter.setMaximumSize(new Dimension(20, 5));
		UpdateStatus = new JButton();
		UpdateStatus.setText("Parar validación");
		UpdateStatus.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(isTesting()){
					isTesting = false;
					stopValidation();
				}else{
					resumeValidation();
					isTesting = true;
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
		});
		for(StatusPanel panel:envList){
			statusPanelContainer.add(panel);
		}
		this.setVisible(true);
		add(statusPanelContainer,BorderLayout.CENTER);
		add(UpdateStatus, BorderLayout.SOUTH);
	}
	/**
	 * Actualiza los componentes del panel para que refresque los StatusPanel filtrados
	 */
	private void updateEnvironment(){
		UserConnectionDataComparator comparator = new UserConnectionDataComparator(profile);
		for(StatusPanel panel:envList){
			panel.stopValidation();
			statusPanelContainer.remove(panel);
		}
		envList.clear();
		Collections.sort(data,comparator);
		for(UserConnectionData info:data){
			if(Minoristas.isSelected() && !Mayoristas.isSelected()){
				if(!info.getEnvName().contains(" AM")){
					StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
					envList.add(pan);
					if(!info.isProfile()){
						pan.desactivatePanel();
					}
				}
			}else if (!Minoristas.isSelected() && Mayoristas.isSelected()){
				if(info.getEnvName().contains(" AM")){
					StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
					envList.add(pan);
					if(!info.isProfile()){
						pan.desactivatePanel();
					}
				}
			}else if(Minoristas.isSelected() && Mayoristas.isSelected()){
				StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
				envList.add(pan);
				if(!info.isProfile()){
					pan.desactivatePanel();
				}
			}
		}
		statusPanelContainer.setLayout(new GridLayout(envList.size(),1));
		for(StatusPanel panel:envList){
			statusPanelContainer.add(panel);
		}
		validate();
		repaint();
	}
	/**
	 * Actualiza los paneles con el estado del entorno
	 */
	public void updateStatus(){
		for(StatusPanel panel:envList){
			panel.validateStatus();
		}
	}
	public boolean isTesting(){
		return this.isTesting;
	}
	public void stopValidation(){
		UpdateStatus.setText("Reanudar validación");
		for(StatusPanel panel:envList){
			panel.stopValidation();
		}
		parent.stopValidationInf();
	}
	public void resumeValidation(){
		UpdateStatus.setText("Parar validación");
		for(StatusPanel panel:envList){
			if(!panel.isTesting())
				panel.resumeValidation();
		}
		parent.resumeValidationInf();
	}
}