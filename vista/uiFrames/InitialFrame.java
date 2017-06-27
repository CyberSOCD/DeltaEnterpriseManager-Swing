package uiFrames;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import common.UserConnectionData;
import uiPanels.EnvironmentPanel;
import uiPanels.TestingPanel;
import uiProfiles.ProfileControl;

/**
 * 
 * Frame principal de la aplicación da acceso a todas las funcionalidades
 * 
 */
@SuppressWarnings("serial")
public class InitialFrame extends JFrame {
	private EnvironmentPanel validationPanel;
	private TestingPanel testPanel;
	private boolean isMinoristas;
	private boolean isMayoristas;
	private ProfileControl prof;
	private JMenuBar menuBar;
	private JMenu menuEntorno;
	private JMenu menuAcciones;
	private JMenu subMenuEntornoEstado;
	private JMenuItem exitMenuItem;
	private JMenuItem validacionMenuItem;
	private JMenuItem stopValidationMenuItem;
	private JMenuItem resumeValidationMenuItem;

	/**
	 * Constructor inicializa el objeto que maneja los datos
	 */
	public InitialFrame(ArrayList<UserConnectionData> data, boolean isAm, boolean isMin, ProfileControl profile) {
		// Inicializa el objeto Webclass para evitar falsear tiempos de
		// respuesta
		prof = profile;
		isMayoristas = isAm;
		isMinoristas = isMin;
		validationPanel = new EnvironmentPanel(data,isMayoristas, isMinoristas, profile,this);
		testPanel = new TestingPanel(data, isMayoristas, isMinoristas, profile);
		setLayout(new GridLayout(1, 2));
		initialize();
	}

	/**
	 * Inicializa los elementos de la UI
	 */
	private void initialize() {
		setTitle("Delta Enterprise Manager - " + prof.getSelectedProfile());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		this.setVisible(true);
		// this.add(validationPanel);
		// this.add(testPanel);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				validationPanel, testPanel);
		split.setDividerLocation(300);
		createMenu();
		setJMenuBar(menuBar);
		this.add(split);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(WindowEvent winEvt) {
	        	if(validationPanel.isTesting()){
	        		validationPanel.stopValidation();
	        	}
	        }
		});
	}
	
	/**
	 * Se añaden todas las opciones de menu necesarias el JMenuBar
	 */
	private void createMenu(){
		//-------------------------------------------------------------
		//Barra de menus
		menuBar = new JMenuBar();
		
		//-------------------------------------------------------------
		//Opciones de Menu
		menuEntorno = new JMenu("Entornos", true);
		
		menuAcciones = new JMenu("Acciones", true);
		
		//Opciones de SubMenu
		subMenuEntornoEstado = new JMenu("Estado",true);
		
		//-------------------------------------------------------------
		//Valores de Menu
		validacionMenuItem = new JMenuItem(new AbstractAction("Validación entornos") {
			@Override
			public void actionPerformed(ActionEvent e) {
				testPanel.selectValidation();
			}
		});
		
		stopValidationMenuItem = new JMenuItem(new AbstractAction("Parar validacion") {
			@Override
			public void actionPerformed(ActionEvent e) {
				validationPanel.stopValidation();
			}
		});
		
		resumeValidationMenuItem = new JMenuItem(new AbstractAction("Reanudar validacion") {
			@Override
			public void actionPerformed(ActionEvent e) {
				validationPanel.resumeValidation();
				resumeValidationMenuItem.setEnabled(false);
				stopValidationMenuItem.setEnabled(true);
			}
		});
		resumeValidationMenuItem.setEnabled(false);
		exitMenuItem = new JMenuItem(new AbstractAction("Salir") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitMenuItem.setMargin(new Insets(30, 30, 30, 30));
		
		//-------------------------------------------------------------
		menuAcciones.add(validacionMenuItem);
		
		menuEntorno.add(subMenuEntornoEstado);
		subMenuEntornoEstado.add(stopValidationMenuItem);
		subMenuEntornoEstado.add(resumeValidationMenuItem);
		menuEntorno.addSeparator();
		menuEntorno.add(exitMenuItem);
		menuBar.add(menuEntorno);
		menuBar.add(menuAcciones);
	}
	public void resumeValidationInf(){
		resumeValidationMenuItem.setEnabled(false);
		stopValidationMenuItem.setEnabled(true);
	}
	public void stopValidationInf(){
		resumeValidationMenuItem.setEnabled(true);
		stopValidationMenuItem.setEnabled(false);
	}
}
