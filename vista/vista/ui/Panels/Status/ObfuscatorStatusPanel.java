package vista.ui.Panels.Status;

import javax.swing.JPanel;

import vista.ui.Profiles.ProfileControl;
import controlador.common.UserConnectionData;

@SuppressWarnings("serial")
public class ObfuscatorStatusPanel extends JPanel implements GenericStatusPanel {

	public ObfuscatorStatusPanel(UserConnectionData data, String name,
			ProfileControl profile) {

	}

	@Override
	public void validateStatus() {

	}

	@Override
	public boolean isTesting() {
		return false;
	}

}
