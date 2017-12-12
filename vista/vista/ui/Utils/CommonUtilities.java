package vista.ui.Utils;

import controlador.common.UserConnectionData;

public class CommonUtilities {

	private static final String CONTROL_STRING_MAYORISTAS = "AM";
	
	public static boolean isMinorista(UserConnectionData usr){
		if(usr.getEnvKey().contains(CONTROL_STRING_MAYORISTAS))
			return false;
		return true;
	}
	
	public static boolean isMayoristas(UserConnectionData usr){
		if(usr.getEnvKey().contains(CONTROL_STRING_MAYORISTAS))
			return false;
		return true;
	}
}
