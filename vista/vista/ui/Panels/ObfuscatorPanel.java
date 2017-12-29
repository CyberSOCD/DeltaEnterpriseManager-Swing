package vista.ui.Panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controlador.common.UserConnectionData;
import controlador.common.UserConnectionDataComparator;
import controlador.objects.obfuscator.ObfuscatorObject;
import controlador.tools.ObfuscatorTools;
import vista.ui.Dialog.FileSelectorDialog;
import vista.ui.Dialog.MessageCenterDialog;
import vista.ui.Dialog.ObfuscatorDetailsDialog;
import vista.ui.Profiles.ProfileControl;
import vista.ui.Profiles.ProfileControl.TipoSistema;
import vista.ui.ProgressBar.Validation.ObfuscatorProgressBar;
import vista.ui.Runnables.JProgressBarTask;
import vista.ui.tableModel.TableModelObfuscator;

/**
 * 
 * Panel con interfaz para realizar la validacion del ofuscado en un entorno
 * y mostrar un historico de las validaciones de ofuscado realizadas
 * 
 */
@SuppressWarnings("serial")
public class ObfuscatorPanel extends JPanel {
	private ProfileControl profile;
	private JPanel mainPanel;
	private JPanel validationPanel;
	private JPanel obfuscatorValidationPanel;
	private JPanel tableOfusPanel;
	private JPanel filePanel;
	private JPanel buttonsBDPanel;
	private JPanel scriptDataPanel;
	private JPanel scriptDataLabelPanel;
	private JLabel scriptNameLabel;
	private JLabel scriptSystemLabel;
	private JLabel scriptVersionLabel;
	private JLabel progressVersionLabel;
	
	private JPanel scriptDataTextPanel;
	private JTextField scriptNameText;
	private JTextField scriptSystemText;
	private JTextField scriptVersionText;
	
	private JPanel environmentPanel;
	private ArrayList<JRadioButton> rButtonsList;
	private JTable ofusTable;
	private TableModelObfuscator ofusModel;
	private JScrollPane scrollPanel;
	private ArrayList<ObfuscatorObject> list;
	private ArrayList<UserConnectionData> data;
	private ArrayList<UserConnectionData> filterData;
	
	private JProgressBarTask barTask;
	private ObfuscatorProgressBar progressBar;
	
	private File obfuscatorFile;
	
	private JButton loadScriptBtn;
	private JButton clearScriptBtn;
	private JButton validateScriptBtn;
	private JButton detailsBtn;
	
	/**
	 * Button content constants
	 */
	private static final String loadButtonValue = "Cargar Script de ofuscado";
	private static final String clearButtonValue = "Limpiar Script de ofuscado cargado";
	private static final String validateButtonValue = "Validar Ofuscado";
	private static final String detailsButtonValue = "Detalles";
	
	private static final String borderName = "Entornos Ofuscado";
	
	private static final String scriptNameLabelValue = "Nombre de script";
	private static final String scriptSystemLabelValue = "Sistema";
	private static final String scriptVersionLabelValue = "Version del script";
	private static final String progressVersionLabelValue = "Progresion de la validacion";
	
	private static final String scriptNameTextDefaultvalue = "No hay ningun script cargado";
	private static final String scriptSystemTextDefaultvalue = "No hay ningun script cargado";
	private static final String scriptVersionTextDefaultvalue = "No hay ningun script cargado";
	private HashMap <UserConnectionData,JRadioButton> relation;
	private ButtonGroup btnGroup;
	private Thread thread;
	private boolean validating = false;
	private TipoSistema sistema;
	private ObfuscatorObject validationResult = null;
	
	public ObfuscatorPanel(ArrayList<UserConnectionData> ListData, ProfileControl profile, TipoSistema tipo){
		data = ListData;
		sistema = tipo;
		relation = new HashMap<UserConnectionData, JRadioButton>();
		rButtonsList = new ArrayList<JRadioButton>();
		this.profile = profile;
		loadData();
		initialize();
	}
	
	public boolean isValidating(){
		return validating;
	}
	
	public void changeSystem(TipoSistema tipo){
		sistema = tipo;
		//Se elimina la lista de botonoes del grupo
		relation.clear();
		btnGroup = new ButtonGroup();
		filterData = profile.getSystemProfileList(data, sistema);
		environmentPanel.removeAll();
		UserConnectionDataComparator comparator = new UserConnectionDataComparator(profile);
		Collections.sort(filterData,comparator);
		JPanel aux = new JPanel();
		for(UserConnectionData usr:filterData){
			JRadioButton rButton = new JRadioButton(usr.getEnvName());
			rButton.setAlignmentX(LEFT_ALIGNMENT);
			rButton.setSelected(false);
			relation.put(usr, rButton);
			btnGroup.add(rButton);
			aux = new JPanel();
			aux.setSize(new Dimension(100,5));
			aux.setAlignmentX(LEFT_ALIGNMENT);
			environmentPanel.add(aux);
			environmentPanel.add(rButton);
			rButton.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						processChange((JRadioButton)e.getSource());
				    }
				}
			});
			rButtonsList.add(rButton);
		}
		validate();
		repaint();
	}
	
	private void loadData(){
		filterData = profile.getSystemProfileList(data, sistema);
		//Recupera la lista de ObfuscatorObject que corresponde
		list = new ArrayList<ObfuscatorObject>();
		
	}
	
	private void initialize(){
		setLayout(new GridLayout(1,1));
		
		/***************************************************************************
		 * Se inicializa el panel principal Layout de dos columnas para diferenciar 
		 * seleccion de entornos de validacion contra la BBDD y consulta de historico
		 */
		
		mainPanel = new JPanel();
//		mainPanel.setLayout(new GridLayout(1,2));
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
//		mainPanel.setBorder(BorderFactory.createTitledBorder("Main Panel"));
		
		/***************************************************************************
		 * Se inicializa el panel de seleccion de entorno a validar
		 */
		
		environmentPanel = new JPanel();
		environmentPanel.setLayout(new BoxLayout(environmentPanel,BoxLayout.Y_AXIS));
		environmentPanel.setAlignmentY(TOP_ALIGNMENT);
//		environmentPanel.setLayout(new GridLayout(data.size()+7, 1,5,20));
		environmentPanel.setBorder(BorderFactory.createTitledBorder(borderName));
		
		/***************************************************************************
		 * Se cargan los componentes internos para la seleccion de entorno
		 */
		
		btnGroup = new ButtonGroup();
		//Se crea la opcion por defecto sin seleccionar ningun entorno
//		rButtonDefault = new JRadioButton(defaultValue);
//		rButtonDefault.setSelected(true);
//		btnGroup.add(rButtonDefault);
		JPanel aux = new JPanel();
		aux.setSize(new Dimension(100,10));
		aux.setAlignmentX(LEFT_ALIGNMENT);
		environmentPanel.add(aux);
//		rButtonDefault.setAlignmentX(LEFT_ALIGNMENT);
//		environmentPanel.add(rButtonDefault);
		UserConnectionDataComparator comparator = new UserConnectionDataComparator(profile);
		Collections.sort(filterData,comparator);
		for(UserConnectionData usr:filterData){
			if(profile.checkEnvironment(usr)){
				JRadioButton rButton = new JRadioButton(usr.getEnvName());
				rButton.setAlignmentX(LEFT_ALIGNMENT);
				rButton.setSelected(false);
				relation.put(usr, rButton);
				btnGroup.add(rButton);
				aux = new JPanel();
				aux.setSize(new Dimension(100,5));
				aux.setAlignmentX(LEFT_ALIGNMENT);
				environmentPanel.add(aux);
				environmentPanel.add(rButton);
				rButton.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							processChange((JRadioButton)e.getSource());
					    }
					}
				});
				rButtonsList.add(rButton);
			}
			aux = new JPanel();
			aux.setSize(new Dimension(100,10));
			aux.setAlignmentX(LEFT_ALIGNMENT);
			environmentPanel.add(aux);
		}
		
		/***************************************************************************
		 * Se inicializa el panel para la validacion contra la BBDD y 
		 * el historico de validaciones realizadas
		 */
		
		validationPanel = new JPanel();
		validationPanel.setAlignmentY(TOP_ALIGNMENT);
		validationPanel.setLayout(new GridLayout(2,1));
		validationPanel.setBorder(BorderFactory.createTitledBorder("ValidationPanel"));
		
		/***************************************************************************
		 * Se inicializa el panel para la validacion contra la BBDD
		 * incluyendo la carga de un script de ofuscado
		 */
		
		obfuscatorValidationPanel = new JPanel();
//		obfuscatorValidationPanel.setBorder(BorderFactory.createTitledBorder("BD"));
		obfuscatorValidationPanel.setLayout(new BoxLayout(obfuscatorValidationPanel,BoxLayout.Y_AXIS));
		
		/***************************************************************************
		 * Se cargan todos los componentes interiores para la validacion
		 * contra la BBDD
		 */
		
		/**
		 * Panel donde se muestran datos sobre el script de ofuscado cargado
		 */
		filePanel = new JPanel();
//		filePanel.setLayout(new GridLayout(1,1));
		filePanel.setLayout(new BoxLayout(filePanel,BoxLayout.Y_AXIS));
//		filePanel.setBorder(BorderFactory.createTitledBorder("FilePanel"));
		
		scriptDataPanel = new JPanel();
//		scriptDataPanel.setLayout(new GridLayout(1,2));
		scriptDataPanel.setLayout(new BoxLayout(scriptDataPanel,BoxLayout.X_AXIS));
		scriptDataPanel.setAlignmentX(LEFT_ALIGNMENT);
//		scriptDataPanel.setBorder(BorderFactory.createTitledBorder("ScriptsDataPanel"));
		
		scriptDataLabelPanel = new JPanel();
//		scriptDataLabelPanel.setLayout(new GridLayout(3,1,5,5));
		
		scriptDataLabelPanel.setLayout(new BoxLayout(scriptDataLabelPanel, BoxLayout.PAGE_AXIS));
		
		
//		scriptDataLabelPanel.setBorder(BorderFactory.createTitledBorder("LabelsPanel"));
		scriptDataLabelPanel.setAlignmentY(TOP_ALIGNMENT);
		scriptNameLabel = new JLabel(scriptNameLabelValue);
		scriptSystemLabel = new JLabel(scriptSystemLabelValue);
		scriptVersionLabel = new JLabel(scriptVersionLabelValue);
		progressVersionLabel = new JLabel(progressVersionLabelValue);
		scriptDataLabelPanel.add(Box.createRigidArea(new Dimension(10, 13)));
		scriptDataLabelPanel.add(scriptNameLabel);
		scriptDataLabelPanel.add(Box.createRigidArea(new Dimension(10, 13)));
		scriptDataLabelPanel.add(scriptSystemLabel);
		scriptDataLabelPanel.add(Box.createRigidArea(new Dimension(10, 13)));
		scriptDataLabelPanel.add(scriptVersionLabel);
		scriptDataLabelPanel.add(Box.createRigidArea(new Dimension(10, 13)));
		scriptDataLabelPanel.add(progressVersionLabel);
		
		scriptDataTextPanel = new JPanel();
//		scriptDataTextPanel.setLayout(new GridLayout(3,1,5,5));
		scriptDataTextPanel.setLayout(new BoxLayout(scriptDataTextPanel, BoxLayout.PAGE_AXIS));
//		scriptDataTextPanel.setBorder(BorderFactory.createTitledBorder("TextsPanel"));
		scriptDataTextPanel.setAlignmentY(TOP_ALIGNMENT);
		scriptNameText = new JTextField(scriptNameTextDefaultvalue);
		scriptNameText.setMaximumSize(new Dimension(
				scriptNameText.getMaximumSize().width, 
				scriptNameText.getMinimumSize().height));
		scriptNameText.setEditable(false);
		scriptSystemText = new JTextField(scriptSystemTextDefaultvalue);
		scriptSystemText.setMaximumSize(new Dimension(
				scriptSystemText.getMaximumSize().width, 
				scriptSystemText.getMinimumSize().height));
		scriptSystemText.setEditable(false);
		scriptVersionText = new JTextField(scriptVersionTextDefaultvalue);
		scriptVersionText.setMaximumSize(new Dimension(
				scriptVersionText.getMaximumSize().width, 
				scriptVersionText.getMinimumSize().height));
		scriptVersionText.setEditable(false);
		
		progressBar = new ObfuscatorProgressBar();
//		progressBar.setSize(200, 30);
		progressBar.setMaximumSize(scriptVersionText.getMaximumSize());
		progressBar.setVisible(false);
		
		scriptDataTextPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		scriptDataTextPanel.add(scriptNameText);
		scriptDataTextPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		scriptDataTextPanel.add(scriptSystemText);
		scriptDataTextPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		scriptDataTextPanel.add(scriptVersionText);
		scriptDataTextPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		scriptDataTextPanel.add(progressBar);
		
		scriptDataPanel.add(scriptDataLabelPanel);
		scriptDataPanel.add(Box.createRigidArea(new Dimension(30,0)));
		scriptDataPanel.add(scriptDataTextPanel);
		
		/**
		 * Botones que controlan carga de script de ofuscado y validacion contra BBDD
		 */
		buttonsBDPanel = new JPanel();
//		buttonsBDPanel.setBorder(BorderFactory.createTitledBorder("ButtonsPanel"));
		buttonsBDPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		buttonsBDPanel.setLayout(new BoxLayout(buttonsBDPanel,BoxLayout.X_AXIS));
		
		loadScriptBtn = new JButton(loadButtonValue);
		
		loadScriptBtn.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//Muestra interfaz de carga de ficheros
				if(loadScriptBtn.isEnabled())
					loadObfuscatorScript();
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		clearScriptBtn = new JButton(clearButtonValue);
		
		clearScriptBtn.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//Limpia el fichero de la clase y resetea a valores por defecto
				if(clearScriptBtn.isEnabled())
					cleanObfuscatorScript();
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		detailsBtn = new JButton(detailsButtonValue);
		
		detailsBtn.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//Realiza la validacion contra la BD que corresponda
				if(validationResult != null)
					showDialog(validationResult);
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		validateScriptBtn = new JButton(validateButtonValue);
		
		validateScriptBtn.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//Realiza la validacion contra la BD que corresponda
				if(clearScriptBtn.isEnabled())
					validateProcess();
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		buttonsBDPanel.add(Box.createRigidArea(new Dimension(15,0)));
		buttonsBDPanel.add(loadScriptBtn);
		buttonsBDPanel.add(Box.createRigidArea(new Dimension(15,0)));
		buttonsBDPanel.add(clearScriptBtn);
		buttonsBDPanel.add(Box.createRigidArea(new Dimension(15,0)));
		buttonsBDPanel.add(validateScriptBtn);
		buttonsBDPanel.add(Box.createRigidArea(new Dimension(15,0)));
		buttonsBDPanel.add(detailsBtn);
		
		
		
		
//		barTask = new JProgressBarTask(val)
		
		filePanel.add(scriptDataPanel);
		filePanel.add(Box.createRigidArea(new Dimension(0,30)));
		filePanel.add(buttonsBDPanel);
		filePanel.add(Box.createRigidArea(new Dimension(0,30)));
		
		/***************************************************************************
		 * Se inicializa el panel para consultar el historico de
		 * validaciones de ofuscado
		 */
		tableOfusPanel = new JPanel();
		tableOfusPanel.setBorder(BorderFactory.createTitledBorder("Table Panel"));
		tableOfusPanel.setLayout(new GridLayout(1,1));
		
		ofusModel = new TableModelObfuscator(list);
		ofusTable = new JTable(ofusModel);
//		ofusTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				ObfuscatorObject obj = (ObfuscatorObject) ofusTable.getValueAt(ofusTable.getSelectedRow(), 0);
//				showDialog(obj);
//			}
//		});
		ofusTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2) {
		        	ObfuscatorObject obj = ofusModel.getObjectAtRow(row);
					showDialog(obj);
		        }
		    }
		});
		
		scrollPanel = new JScrollPane(ofusTable);
		ofusTable.setFillsViewportHeight(true);
		ofusTable.setAutoCreateRowSorter(true);
		tableOfusPanel.add(scrollPanel);
		scrollPanel.repaint();
//		tableOfusPanel.add(ofusTable.getTableHeader(), BorderLayout.PAGE_START);
//		tableOfusPanel.add(ofusTable, BorderLayout.CENTER);
		
		obfuscatorValidationPanel.add(filePanel);
		validationPanel.add(obfuscatorValidationPanel);
		validationPanel.add(tableOfusPanel);
		
		mainPanel.add(environmentPanel);
		mainPanel.add(validationPanel);
		
		add(mainPanel);
	}
	
	private void showDialog(ObfuscatorObject obj){
		if(obj == null)
			return;
		ObfuscatorDetailsDialog d = new ObfuscatorDetailsDialog(null, obj);
		d.setVisible(true);
	}
	
	/**
	 * 
	 * @param rButton
	 */
	private void processChange(JRadioButton rButton){
		 if(rButton.isSelected()){
			 //Limpia la lista
			 list.clear();
			 //Busca el entorno correspondiente al boton
			 UserConnectionData data = null;;
			 for(UserConnectionData usr:relation.keySet()){
				 if(relation.get(usr).equals(rButton)){
					 data = usr;
					 break;
				 }
			 }
			 ObfuscatorTools obf = new ObfuscatorTools(data);
			 list.addAll(obf.getList());
			 ofusModel.updateTableData(list);
			 ofusModel.fireTableDataChanged();
		 }
	}
	
	/**
	 * Carga un script de ofuscado
	 */
	private void loadObfuscatorScript(){
		progressBar.resetBar();
		FileSelectorDialog fc = new FileSelectorDialog(System.getProperty("user.dir"));
		fc.setExtension(".sql");
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
			//Carga los valores del fichero cargado en los text que describen el script
			File file = fc.getSelectedFile();
			try{
				FileReader reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
				//Se descarta la primera linea al ser comentario
				br.readLine();
				String fullLine = br.readLine();
				String scriptName = fullLine.split(" ")[0].replace("-", "").replace("	", "");
				String scriptSystem = fullLine.split(" ")[0].replace("-", "").replace("	", "").split("_")[3];
				String scriptVersion = fullLine.split(" ")[3].replace("-", "").replace("	", "");
				scriptNameText.setText(scriptName);
				scriptSystemText.setText(scriptSystem);
				scriptVersionText.setText(scriptVersion);
				br.close();
				reader.close();
				this.obfuscatorFile = file;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * Limpia valores cargados
	 */
	private void cleanObfuscatorScript(){
		this.obfuscatorFile = null;
		scriptNameText.setText(scriptNameTextDefaultvalue);
		scriptSystemText.setText(scriptSystemTextDefaultvalue);
		scriptVersionText.setText(scriptVersionTextDefaultvalue);
		progressBar.resetBar();
	}
	/**
	 * Ejecuta la validación sobre el entorno seleccionado
	 */
	private void validateProcess(){
		//El proceso se debe iniciar de forma asincrona para no bloquear el trabajo mientras se ejecutan las queries
		//Valida que haya seleccionado un entorno
		progressBar.resetBar();
		if(obfuscatorFile==null){
			MessageCenterDialog.showInformationDialog(this, "Se debe seleccionar un script de ofuscado");
			return;
		}
			
		UserConnectionData selectedEnv = null;
		for(UserConnectionData usr:relation.keySet()){
			if(relation.get(usr).getModel().equals(btnGroup.getSelection())){
				selectedEnv = usr;
				break;
			}
		}
		if(selectedEnv==null){
			MessageCenterDialog.showInformationDialog(this, "Se debe seleccionar uno de los entornos");
			return;
		}
		disableButtons();
		validationResult = null;
		validating = true;
		//Se deshabilitan botones implicados en el proceso
		loadScriptBtn.setEnabled(false);
		clearScriptBtn.setEnabled(false);
		validateScriptBtn.setEnabled(false);
		
		progressBar.setFile(obfuscatorFile);
		progressBar.setUserConnectionData(selectedEnv);
		progressBar.setVisible(true);
		barTask = new JProgressBarTask(progressBar, this);
		//Se realiza la validacion de forma asincrona
		thread = new Thread(barTask);
		thread.start();
	}
	
	public void finishValidation(ObfuscatorObject obj){
		validationResult = obj;
		enableButtons();
		validating = false;
		loadScriptBtn.setEnabled(true);
		clearScriptBtn.setEnabled(true);
		validateScriptBtn.setEnabled(true);
	}
	
	/**
	 * Deshabilita los radio button
	 */
	private void disableButtons(){
		for(Enumeration<AbstractButton> e = btnGroup.getElements();e.hasMoreElements();){
			e.nextElement().setEnabled(false);
		}
	}
	
	/**
	 * Habilita los radio button
	 */
	private void enableButtons(){
		for(Enumeration<AbstractButton> e = btnGroup.getElements();e.hasMoreElements();){
			e.nextElement().setEnabled(true);
		}
	}
}
