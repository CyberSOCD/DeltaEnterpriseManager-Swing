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

import uiPanels.Status.StatusPanel;
import common.UserConnectionData;
import common.UserConnectionDataComparator;

/**
 * 
 * Panel que muestra todos los entornos y permite comprobar su estado  
 *
 */
public class EnvironmentPanel extends JPanel{
	
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
	
	public EnvironmentPanel(ArrayList<UserConnectionData> ListData, boolean isAm,boolean isMin){
		// Inicializa la lista de paneles de estado
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
		UserConnectionDataComparator comparator = new UserConnectionDataComparator();
		if(isMinoristas){
			Collections.sort(data,comparator);
			for(UserConnectionData info:data){
				if(!info.getEnvName().contains(" AM")){
					envList.add(new StatusPanel(info,info.getEnvName()));
				}
			}
		}
		if(isMayoristas){
			Collections.sort(data,comparator);
			for(UserConnectionData info:data){
				if(info.getEnvName().contains(" AM")){
					envList.add(new StatusPanel(info,info.getEnvName()));
				}
			}
		}
		statusPanelContainer = new JPanel();
		statusPanelContainer.setLayout(new GridLayout(envList.size(),1));
		Minoristas = new JCheckBox();
		Minoristas.setText("Minoristas");
		Minoristas.setSelected(isMinoristas);
		Minoristas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateEnvironment();
			}
		});
		Minoristas.setEnabled(false);
		Mayoristas = new JCheckBox();
		Mayoristas.setText("Mayoristas");
		Mayoristas.setSelected(isMayoristas);
		Mayoristas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateEnvironment();
			}
		});
		Mayoristas.setEnabled(false);
		if(isMayoristas && isMinoristas){
			Mayoristas.setEnabled(true);
			Minoristas.setEnabled(true);
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
					UpdateStatus.setText("Reanudar validación");
				}else{
					resumeValidation();
					isTesting = true;
					UpdateStatus.setText("Parar validación");
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
			panel.setVisible(true);
		}
		this.setVisible(true);
		add(statusPanelContainer,BorderLayout.CENTER);
		add(UpdateStatus, BorderLayout.SOUTH);
	}
	/**
	 * Actualiza los componentes del panel para que refresque los StatusPanel filtrados
	 */
	private void updateEnvironment(){
		UserConnectionDataComparator comparator = new UserConnectionDataComparator();
		for(StatusPanel panel:envList){
			panel.stopValidation();
			statusPanelContainer.remove(panel);
		}
		envList.clear();
		if(Minoristas.isSelected()){
			Collections.sort(data,comparator);
			for(UserConnectionData info:data){
				if(!info.getEnvName().contains(" AM")){
					envList.add(new StatusPanel(info,info.getEnvName()));
				}
			}
		}
		if(Mayoristas.isSelected()){
			Collections.sort(data,comparator);
			for(UserConnectionData info:data){
				if(info.getEnvName().contains(" AM")){
					envList.add(new StatusPanel(info,info.getEnvName()));
				}
			}
		}
		statusPanelContainer.setLayout(new GridLayout(envList.size(),1));
		for(StatusPanel panel:envList){
			statusPanelContainer.add(panel);
			panel.setVisible(true);
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
	private void stopValidation(){
		for(StatusPanel panel:envList){
			panel.stopValidation();
		}
	}
	private void resumeValidation(){
		for(StatusPanel panel:envList){
			if(!panel.isTesting())
				panel.startValidation();
		}
	}
}