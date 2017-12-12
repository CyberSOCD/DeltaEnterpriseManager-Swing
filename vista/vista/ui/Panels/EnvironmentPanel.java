package vista.ui.Panels;

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

import vista.ui.Dialog.MessageCenterDialog;
import vista.ui.Frames.InitialFrame;
import vista.ui.Panels.Status.StatusPanel;
import vista.ui.Profiles.ProfileControl;
import vista.ui.Profiles.ProfileControl.TipoSistema;
import controlador.common.UserConnectionData;
import controlador.common.UserConnectionDataComparator;

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
	private ArrayList<UserConnectionData> partialData;
	private boolean isTesting = true;
	private TipoSistema sistema;
	private InitialFrame parent;
	
	
	public EnvironmentPanel(ArrayList<UserConnectionData> ListData, TipoSistema tipo, ProfileControl profile, InitialFrame parent){
		// Inicializa la lista de paneles de estado
		this.parent = parent; 
		this.profile = profile;
		this.sistema = tipo;
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
		partialData = profile.getProfileList(data);
		Collections.sort(partialData,comparator);
		//Mantiene unicamente los entornos que corresponden al perfil en cuestion
		for(UserConnectionData info:partialData){
			StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
			envList.add(pan);
		}
		statusPanelContainer = new JPanel();
		statusPanelContainer.setLayout(new GridLayout(envList.size(),1));
		Minoristas = new JCheckBox();
		Minoristas.setText("Minoristas");
		Minoristas.setSelected(sistema.equals(TipoSistema.MINORISTA));
		Minoristas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isTesting){
					MessageCenterDialog.showInformationDialog(parent, "Actualmente se estan validando entornos se debe para antes de cambiar el sistema seleccionado");
					Minoristas.setSelected(!Minoristas.isSelected());
				}else
					updateEnvironment();
			}
		});
		Minoristas.setEnabled(false);
		Mayoristas = new JCheckBox();
		Mayoristas.setText("Mayoristas");
		Mayoristas.setSelected(sistema.equals(TipoSistema.MAYORISTA)||profile.isAdmin());
		Mayoristas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isTesting){
					MessageCenterDialog.showInformationDialog(parent, "Actualmente se estan validando entornos se debe para antes de cambiar el sistema seleccionado");
					Mayoristas.setSelected(!Mayoristas.isSelected());
				}else
					updateEnvironment();
			}
		});
		Mayoristas.setEnabled(false);
		if(profile.isAdmin()){
			Mayoristas.setEnabled(true);
			Minoristas.setEnabled(true);
		}else{
			Mayoristas.setVisible(sistema.equals(TipoSistema.MAYORISTA));
			Minoristas.setVisible(sistema.equals(TipoSistema.MINORISTA));
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
		//Si se está validando se impide el cambio de sistema
		if(Minoristas.isSelected() && Mayoristas.isSelected()){
			sistema = TipoSistema.AMBOS;
		}else{
			if(Minoristas.isSelected())
				sistema = TipoSistema.MINORISTA;
			else if(Mayoristas.isSelected())
				sistema = TipoSistema.MAYORISTA;
		}
		
		UserConnectionDataComparator comparator = new UserConnectionDataComparator(profile);
		for(StatusPanel panel:envList){
//			panel.stopValidation();
			statusPanelContainer.remove(panel);
		}
		envList.clear();
		
		partialData = profile.getSystemProfileList(data, sistema);
		Collections.sort(partialData,comparator);
		//Mantiene unicamente los entornos que corresponden al perfil en cuestion
		for(UserConnectionData info:partialData){
			StatusPanel pan = new StatusPanel(info,info.getEnvName(), profile);
			envList.add(pan);
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
			if(panel.isTesting())
				panel.stopValidation();
		}
		parent.stopValidationInf();
	}
	public void resumeValidation(){
		UpdateStatus.setText("Parar validación");
		for(StatusPanel panel:envList){
//			if(!panel.isTesting())
				panel.resumeValidation();
		}
		parent.resumeValidationInf();
	}
}