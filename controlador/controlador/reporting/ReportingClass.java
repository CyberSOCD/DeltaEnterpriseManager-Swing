package controlador.reporting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import vista.ui.Dialog.MessageCenterDialog;
import conexion.fileAccess.LoadFile;
import conexion.xlsxFileAccess.XlsxAccessFile;
import controlador.common.UserConnectionData;
import controlador.controlResult.GenericStatus;
import controlador.logger.LogLineObject;
import controlador.tools.LogTools;

public interface ReportingClass {
	public static enum Period {
		WEEKLY, DAILY
	}

	public void generateReport();
	
}
