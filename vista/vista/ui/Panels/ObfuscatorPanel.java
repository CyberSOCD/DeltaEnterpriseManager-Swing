package vista.ui.Panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import vista.ui.tableModel.TableModelObfuscator;

/**
 * 
 * Panel con interfaz para realizar la validacion del ofuscado en un entorno
 * 
 */
@SuppressWarnings("serial")
public class ObfuscatorPanel extends JPanel {
	private JPanel mainPanel;
	private JTable ofusTable;
	private TableModelObfuscator ofusModel;
	private JScrollPane scrollPanel;
	
	public ObfuscatorPanel(){
		loadData();
		initialize();
	}
	
	private void loadData(){
		
	}
	
	private void initialize(){
		mainPanel = new JPanel();
		
		ofusModel = new TableModelObfuscator(null);
		
		ofusTable = new JTable(ofusModel);
		
		scrollPanel = new JScrollPane(ofusTable);
		ofusTable.setFillsViewportHeight(true);
		
		
		add(mainPanel);
	}
	
	/**
	 * Carga con datos, si hay, la tabla
	 */
	private void loadTable(){
		
	}
}
