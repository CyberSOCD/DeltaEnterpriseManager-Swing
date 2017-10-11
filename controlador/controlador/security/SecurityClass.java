package controlador.security;

import org.apache.commons.codec.binary.Base64;

import vista.ui.Profiles.ProfileConstants;

/**
 * 
 */
public class SecurityClass {
	private String profile_code;
	private char[] input;

	public SecurityClass(String sys, String profile, char[] baseCode) {
		profile_code = profile;
		input = baseCode;
		validateProfile(input);
	}

	public boolean validProfile(char[] baseCode) {
		String var1 = parseCode(baseCode);
		String var2 = parseCode(input);
		return compareData(var1, var2);
	}

	private void validateProfile(char[] data) {
		if (data == null)
			return;
		switch (profile_code) {
		case ProfileConstants.ADMINISTRADOR:
			input = ProfileConstants.cadena_gc;
			break;
		case ProfileConstants.CERTIFICACION_MIN:
			input = ProfileConstants.cadena_min_ce;
			break;
		case ProfileConstants.GESTION_MAY:
			input = ProfileConstants.cadena_AM;
			break;
		case ProfileConstants.GESTION_MIN:
			input = ProfileConstants.cadena_min_ge;
			break;
		default:
			break;
		}
	}

	private boolean compareData(String data1, String data2) {
		return data1.equals(new String(Base64.decodeBase64(data2.getBytes())));
	}
	private String parseCode(char[] values){
		String var = "";
		for(char c:values){
			var = var + c;
		}
		return var;
	}
}
