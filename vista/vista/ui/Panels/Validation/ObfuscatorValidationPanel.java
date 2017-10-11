package vista.ui.Panels.Validation;

import java.util.ArrayList;

import javax.swing.JPanel;

import vista.ui.Profiles.ProfileControl;
import controlador.common.UserConnectionData;

@SuppressWarnings("serial")
public class ObfuscatorValidationPanel extends JPanel{
	
	ProfileControl profile;
	
	public ObfuscatorValidationPanel(UserConnectionData data, ProfileControl profile, ArrayList<UserConnectionData> ListData){
		this.profile = profile;
	}

}
