package vista.ui.Panels;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import controlador.common.UserConnectionData;

@SuppressWarnings("serial")
public abstract class GenericEnvironmentButtonPanel extends JPanel {

	/**
	 * Añade un @ButtonGroup que agrupara los botones que aplican en cada
	 * momento
	 * 
	 * @param btnGroup
	 */
	protected abstract void setButtonsGroup(ButtonGroup btnGroup);

	/**
	 * Se cambia de sistema eliminando los botones con el sistema antiguo y
	 * creando nuevos para la lista del nuevo sistema
	 * 
	 * @param list
	 */
	protected abstract void changeSystem(ArrayList<UserConnectionData> list);

	/**
	 * Ordena la lista de entornos yse guarda en la lista que aplica
	 */
	protected abstract void sortList();

}
