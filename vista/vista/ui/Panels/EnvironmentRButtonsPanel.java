package vista.ui.Panels;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import vista.ui.Profiles.ProfileControl;
import controlador.common.UserConnectionData;
import controlador.common.UserConnectionDataComparator;

@SuppressWarnings("serial")
public class EnvironmentRButtonsPanel extends GenericEnvironmentButtonPanel {

	private HashMap<UserConnectionData, JRadioButton> relation;
	private ButtonGroup btnGroup;
	private ArrayList<UserConnectionData> data;
	private ProfileControl profile;

	public EnvironmentRButtonsPanel(ArrayList<UserConnectionData> ListData, ProfileControl profile) {
		this.profile = profile;
		data = new ArrayList<UserConnectionData>();
		this.data.addAll(ListData);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setAlignmentY(TOP_ALIGNMENT);
		sortList();
		relation = new HashMap<UserConnectionData, JRadioButton>();
		initializeButtons();
	}
	
	public HashMap<UserConnectionData, JRadioButton> getButtonRelation(){
		return relation;
	}

	@Override
	public void setButtonsGroup(ButtonGroup btnGroup) {
		this.btnGroup = btnGroup;
		groupRadioButtons();
	}

	@Override
	protected void changeSystem(ArrayList<UserConnectionData> list) {
		//Se eliminan todos los componentes y datos
		this.removeAll();
		for(UserConnectionData usr:relation.keySet())
			btnGroup.remove(relation.get(usr));
		relation.clear();
		data.clear();
		//Se reinicializa todo de nuevo
		data = list;
		sortList();
		initializeButtons();
		groupRadioButtons();
		validate();
		repaint();
	}

	@Override
	protected void sortList() {
		UserConnectionDataComparator comparator = new UserConnectionDataComparator(profile);
		Collections.sort(data,comparator);
	}
	
	private void initializeButtons(){
		JPanel aux = new JPanel();
		aux.setSize(new Dimension(100,10));
		aux.setAlignmentX(LEFT_ALIGNMENT);
		add(aux);
		for(UserConnectionData usr:data){
			if(profile.checkEnvironment(usr)){
				JRadioButton rButton = new JRadioButton(usr.getEnvName());
				rButton.setAlignmentX(LEFT_ALIGNMENT);
				rButton.setSelected(false);
				relation.put(usr, rButton);
				aux = new JPanel();
				aux.setSize(new Dimension(100,5));
				aux.setAlignmentX(LEFT_ALIGNMENT);
				add(aux);
				add(rButton);
			}
			aux = new JPanel();
			aux.setSize(new Dimension(100,10));
			aux.setAlignmentX(LEFT_ALIGNMENT);
			add(aux);
		}
	}
	
	private void groupRadioButtons(){
		for(UserConnectionData usr:relation.keySet()){
			btnGroup.add(relation.get(usr));
		}
	}
}
