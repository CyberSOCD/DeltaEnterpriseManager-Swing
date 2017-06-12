package uiPanels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import uiPanels.Status.DumbStatusPanel;

@SuppressWarnings("serial")
public class ValidationPanel extends JPanel{

	private ArrayList<DumbStatusPanel> statusList;
	private String name;
	private JLabel valHeader;
	private JPanel panelHeader;
	
	public ValidationPanel(ArrayList<DumbStatusPanel> list, String validationName){
		statusList = list;
		name = validationName;
		initialize();
	}
	
	public void validateActive(){
		
	}
	
	private void initialize(){
//		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(statusList.size()+1,1));
//		setBorder(BorderFactory.createTitledBorder(name));
		panelHeader = new JPanel();
		valHeader = new JLabel(name);
		panelHeader.add(valHeader);
		panelHeader.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		panelHeader.setMaximumSize(new Dimension(200,100));
		add(panelHeader);
		for(DumbStatusPanel dumb:statusList){
			dumb.setBorder(BorderFactory.createLineBorder(this.getBackground()));
			add(dumb);
		}
	}
}
