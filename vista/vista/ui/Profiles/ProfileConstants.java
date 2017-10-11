package vista.ui.Profiles;

import java.util.ArrayList;

@SuppressWarnings("serial")
public abstract class ProfileConstants {

	public final static String CERTIFICACION_MIN = "Certificacion";
	public final static String GESTION_MIN = "Gestion";
	public final static String GESTION_MAY = "Gestion AM";
	public final static String ADMINISTRADOR = "Administrador";
	public final static String DESA = "Desa";

	// public final static String desa = "Desarrollo";

	// /////////////////////////////////////////////////////////////////////////
	// ENVIRONMENT PROFILE CONSTANS
	// Se asocian los entornos propios de cada perfil a modo de filtro
	// si un perfil no tiene el entorno no aparecera en la lista
	// /////////////////////////////////////////////////////////////////////////
	public final static ArrayList<String> CERTIFICACION_MIN_ENVIRONMENT_KEY = new ArrayList<String>() {
		{
			add("ACEPTACION_ID50_L1");
			add("TEST_ID50_L1");
		}
	};

	public final static ArrayList<String> GESTION_MIN_ENVIRONMENT_KEY = new ArrayList<String>() {
		{
			add("ACEPTACION_ID50_L1");
			add("ACEPTACION_ID50_L2");
			add("TEST_ID50_L1");
			add("TEST_ID50_L2");
			add("DELTA_DES_EVOLUTIVO_01_MIN");
			add("DELTA_DES_EVOLUTIVO_02_MIN");
			add("SOPORTE_CORRECTIVO");
			add("TEST_CORRECTIVO");
			add("PARALELO_MIN");
			add("DESARROLLO_CORRECTIVO");
			add("DESARROLLO_CONVERSION");
			add("EJECUCION_CONVERSION");
			add("RENDIMIENTO_ONLINE");
		}
	};

	public final static ArrayList<String> GESTION_MAY_ENVIRONMENT_KEY = new ArrayList<String>() {
		{
			add("TEST_CORRECTIVO_AM_LX");
			add("DESARROLLO_EVOLUTIVO_INDRA_AM_LX");
			add("DESARROLLO_EVOLUTIVO_EVERIS_AM_LX");
			add("DESARROLLO_CORRECTIVO_AM_LX");
			add("TEST_EVOLUTIVO_AM_LX");
			add("ACEPTACION_EVOLUTIVO_AM_LX");
			add("PARALELO_AM_LX");
		}
	};

	public final static ArrayList<String> ADMIN_ENVIRONMENT_KEY = new ArrayList<String>() {
		{
			add("TEST_CORRECTIVO_AM_LX");
			add("DESARROLLO_EVOLUTIVO_INDRA_AM_LX");
			add("DESARROLLO_EVOLUTIVO_EVERIS_AM_LX");
			add("DESARROLLO_CORRECTIVO_AM_LX");
			add("TEST_EVOLUTIVO_AM_LX");
			add("ACEPTACION_EVOLUTIVO_AM_LX");
			add("PARALELO_AM_LX");
			add("ACEPTACION_ID50_L1");
			add("ACEPTACION_ID50_L2");
			add("TEST_ID50_L1");
			add("TEST_ID50_L2");
			add("DELTA_DES_EVOLUTIVO_01_MIN");
			add("DELTA_DES_EVOLUTIVO_02_MIN");
			add("SOPORTE_CORRECTIVO");
			add("TEST_CORRECTIVO");
			add("PARALELO_MIN");
			add("DESARROLLO_CORRECTIVO");
			add("DESARROLLO_CONVERSION");
			add("EJECUCION_CONVERSION");
			add("RENDIMIENTO_ONLINE");
		}
	};
	
	public final static ArrayList<String> DESA_ENVIRONMENT_KEY = new ArrayList<String>() {
		{
			add("DELTA_DES_EVOLUTIVO_01_MIN");
			add("DELTA_DES_EVOLUTIVO_02_MIN");
			add("DESARROLLO_EVOLUTIVO_INDRA_AM_LX");
			add("DESARROLLO_EVOLUTIVO_EVERIS_AM_LX");
		}
	};

	// public final static ArrayList<String> DESARROLLO_ENVIRONMENT_KEY = new
	// ArrayList<String>(){
	// {add("DESARROLLO_EVOLUTIVO_INDRA_AM_LX");add("DESARROLLO_EVOLUTIVO_EVERIS_AM_LX");add("DESARROLLO_CORRECTIVO_AM_LX");
	// add("DELTA_DES_EVOLUTIVO_01_MIN");add("DELTA_DES_EVOLUTIVO_02_MIN");add("DESARROLLO_CORRECTIVO");add("DESARROLLO_CONVERSION");}
	// };

	// /////////////////////////////////////////////////////////////////////////
	// TIMEOUT HEALTH VALIDATION CONSTANTS
	// Determina el tiempo de espera hasta poder validar de nuevo
	// para evitar saturar el sistema con validaciones
	// /////////////////////////////////////////////////////////////////////////
	public final static long CERTIFICACION_MIN_HEALTH_TMEOUT = 60000;
	public final static long GESTION_MIN_HEALTH_TMEOUT = 10000;
	public final static long GESTION_MAY_HEALTH_TMEOUT = 10000;
	public final static long ADMIN_HEALTH_TMEOUT = 1000;
	// public final static long DESARROLLO_HEALTH_TMEOUT = 120000;

	public final static int CERTIFICACION_MIN_FREQ_HEALTH = 5;
	public final static int GESTION_MIN_FREQ_HEALTH = 5;
	public final static int GESTION_MAY_FREQ_HEALTH = 5;
	public final static int ADMIN_MIN_FREQ_HEALTH = 1;

	// Codigo de la contraseña admin
	public final static char[] cadena_gc = { (char) 82, (char) 48,
			(char) 78, (char) 66, (char) 90, (char) 71, (char) 49, (char) 112,
			(char) 98, (char) 107, (char) 82, (char) 70, (char) 84, (char) 81,
			(char) 61, (char) 61 };
	// Codigo de la contraseña mayoristas gestion saturno
	public final static char[] cadena_AM = { (char) 99, (char) 50,
			(char) 70, (char) 48, (char) 100, (char) 88, (char) 74, (char) 117,
			(char) 98, (char) 119, (char) 61, (char) 61 };
	// Codigo de la contraseña minoristas gestion pacifico
	public final static char[] cadena_min_ge = { (char) 99, (char) 71,
			(char) 70, (char) 106, (char) 97, (char) 87, (char) 90, (char) 112,
			(char) 89, (char) 50, (char) 56, (char) 61, (char) 61 };
	// Codigo de la contraseña minoristas certificacion canada
	public final static char[] cadena_min_ce = { (char) 89, (char) 50,
			(char) 70, (char) 117, (char) 89, (char) 87, (char) 82, (char) 104 };
}
