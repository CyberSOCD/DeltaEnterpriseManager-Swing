package uiProfiles;

import java.util.ArrayList;


@SuppressWarnings("serial")
public abstract class ProfileConstants {

	public final static String certi = "Certificacion";
	public final static String gestion = "Gestion";
	public final static String desa = "Desarrollo";

	///////////////////////////////////////////////////////////////////////////
	//		ENVIRONMENT PROFILE CONSTANS
	///////////////////////////////////////////////////////////////////////////
	public final static ArrayList<String> CERTIFICACION_ENVIRONMENT_KEY = new ArrayList<String>() {
		{ add("ACEPTACION_ID50_L1");add("TEST_ID50_L1");}
	};
	public final static ArrayList<String> GESTION_ENVIRONMENT_KEY = new ArrayList<String>() {
		{ add("TEST_CORRECTIVO_AM_LX");add("DESARROLLO_EVOLUTIVO_INDRA_AM_LX");add("DESARROLLO_EVOLUTIVO_EVERIS_AM_LX");
		add("DESARROLLO_CORRECTIVO_AM_LX");add("TEST_EVOLUTIVO_AM_LX");add("ACEPTACION_EVOLUTIVO_AM_LX");
		add("PARALELO_AM_LX");add("ACEPTACION_ID50_L1");add("ACEPTACION_ID50_L2");add("TEST_ID50_L1");
		add("TEST_ID50_L2");add("DELTA_DES_EVOLUTIVO_01_MIN");add("DELTA_DES_EVOLUTIVO_02_MIN");add("SOPORTE_CORRECTIVO");
		add("TEST_CORRECTIVO");add("PARALELO_MIN");add("DESARROLLO_CORRECTIVO");add("DESARROLLO_CONVERSION");
		add("EJECUCION_CONVERSION");add("RENDIMIENTO_ONLINE");}
	};
	
	public final static ArrayList<String> DESARROLLO_ENVIRONMENT_KEY = new ArrayList<String>(){
		{add("DESARROLLO_EVOLUTIVO_INDRA_AM_LX");add("DESARROLLO_EVOLUTIVO_EVERIS_AM_LX");add("DESARROLLO_CORRECTIVO_AM_LX");
		add("DELTA_DES_EVOLUTIVO_01_MIN");add("DELTA_DES_EVOLUTIVO_02_MIN");add("DESARROLLO_CORRECTIVO");add("DESARROLLO_CONVERSION");}
	};
	
	///////////////////////////////////////////////////////////////////////////
	//		TIMEOUT HEALTH VALIDATION CONSTANTS
	///////////////////////////////////////////////////////////////////////////
	public final static long CERTIFICACION_HEALTH_TMEOUT = 40000;
	public final static long GESTION_HEALTH_TMEOUT = 10000;
	public final static long DESARROLLO_HEALTH_TMEOUT = 120000;
	
}
