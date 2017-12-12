package vista.ui.Dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import controlador.objects.obfuscator.ObfuscatorObject;

/**
 * 
 * Dialog que muestra en detalle el resultado del ofuscado 
 *
 */
@SuppressWarnings("serial")
public class ObfuscatorDetailsDialog extends JDialog{
	
	private ObfuscatorObject object;
	
	private String Title = "Detalle del ofuscado - Validado en - ";
	
	private JPanel totalPanel;
	private JLabel totalLabel;
	private String totalLabelText = "Tablas total";
	private JList<Object> totalTables;
	
	private JPanel obfuscatePanel;
	private JLabel obfuscateLabel;
	private String obfuscateLabelText = "Tablas ofuscadas";
	private JList<Object> obfuscateTables;
	
	private JPanel invalidPanel;
	private JLabel invalidLabel;
	private String invalidLabelText = "Tablas sin Ofuscar";
	private JList<Object> invalidTables;
	
	private JPanel mainPanel;

	public ObfuscatorDetailsDialog(Dialog owner, ObfuscatorObject obj) {
		super(owner);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Title += formatter.format(obj.getFechaValidacion());
		setTitle(Title);
		object = obj;
		setLayout(new GridLayout(1,1));
		initialize();
		setPreferredSize(new Dimension(800, 500));
		setMinimumSize(new Dimension(800, 500));
	}
	
	private void initialize(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,3));
		
		totalPanel = new JPanel();
		totalPanel.setLayout(new BoxLayout(totalPanel,BoxLayout.PAGE_AXIS));
		totalLabel = new JLabel(totalLabelText);
		totalTables = new JList<Object>(object.getTablasAOfuscar().toArray());
		totalTables.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		totalTables.setLayoutOrientation(JList.VERTICAL);
		totalTables.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(totalTables);
		totalPanel.add(totalLabel);
		totalPanel.add(listScroller);
		
		obfuscatePanel = new JPanel();
		obfuscatePanel.setLayout(new BoxLayout(obfuscatePanel,BoxLayout.PAGE_AXIS));
		obfuscateLabel = new JLabel(obfuscateLabelText);
		obfuscateTables = new JList<Object>(object.getTablasOfuscadas().toArray());
		obfuscateTables.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		obfuscateTables.setLayoutOrientation(JList.VERTICAL);
		obfuscateTables.setVisibleRowCount(-1);
		listScroller = new JScrollPane(obfuscateTables);
		obfuscatePanel.add(obfuscateLabel);
		obfuscatePanel.add(listScroller);
		
		invalidPanel = new JPanel();
		invalidPanel.setLayout(new BoxLayout(invalidPanel,BoxLayout.PAGE_AXIS));
		invalidLabel = new JLabel(invalidLabelText);
		invalidTables = new JList<Object>(object.getTablasSinOfuscar().toArray());
		invalidTables.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		invalidTables.setLayoutOrientation(JList.VERTICAL);
		invalidTables.setVisibleRowCount(-1);
		listScroller = new JScrollPane(invalidTables);
		invalidPanel.add(invalidLabel);
		invalidPanel.add(listScroller);
		
		mainPanel.add(totalPanel);
		mainPanel.add(obfuscatePanel);
		mainPanel.add(invalidPanel);
		add(mainPanel);
	}

}
