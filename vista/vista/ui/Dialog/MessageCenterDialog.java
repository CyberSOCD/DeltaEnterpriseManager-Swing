package vista.ui.Dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MessageCenterDialog {
	private static String ERROR_TITLE_DIALOG ="Error";
	private static String WARNING_TITLE_DIALOG ="Cuidado";
	
	public static void showErrorDialog(Component parent, String errorMessage){
		JOptionPane.showMessageDialog(parent, errorMessage, ERROR_TITLE_DIALOG,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showWarningDialog(Component parent, String errorMessage){
		JOptionPane.showMessageDialog(parent, errorMessage, WARNING_TITLE_DIALOG,
			    JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showInformationDialog(Component parent, String errorMessage){
		JOptionPane.showMessageDialog(parent, errorMessage);
	}

}
