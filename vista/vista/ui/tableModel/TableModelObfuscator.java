package vista.ui.tableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import controlador.objects.obfuscator.ObfuscatorObject;

@SuppressWarnings("serial")
public class TableModelObfuscator extends AbstractTableModel {
	// Por defecto se ordena por fecha de forma descendente
	private boolean desc = true;
	private HashMap<Date, ObfuscatorObject> data;
	private ObfuscatorObject[] sortData;
	private final static int maxColumnNum = 5;
	private final static String FECHA_VALIDACION = "Fecha Validacion";
	private final static String FECHA_OFUSCADO = "Fecha Ofuscado";
	private final static String NUM_TABLAS_A_OFUSCAR = "Total tablas";
	private final static String NUM_TABLAS_OFUSCADAS = "Num. Tablas Ofuscadas";
	private final static String NUM_TABLAS_SIN_OFUSCAR = "Num. Tablas sin Ofuscar";
	
	public TableModelObfuscator(ArrayList<ObfuscatorObject> list){
		super();
		initializeData(list);
	}
	
	public void updateTableData(ArrayList<ObfuscatorObject> list){
		initializeData(list);
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return maxColumnNum;
	}
	
	public String getColumnName(int column){
		String returnValue;
		switch (column) {
		case 0:
			returnValue = FECHA_VALIDACION;
			break;
		case 1:
			returnValue = FECHA_OFUSCADO;
			break;
		case 2:
			returnValue = NUM_TABLAS_A_OFUSCAR;
			break;
		case 3:
			returnValue = NUM_TABLAS_OFUSCADAS;
			break;
		case 4:
			returnValue = NUM_TABLAS_SIN_OFUSCAR;
			break;
		default:
			returnValue = null;
		}
		return returnValue;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		sortData = sortListObfuscator();
		// Obtiene el objeto que se corresponde con el rowIndex pasado
		ObfuscatorObject rowObject = sortData[rowIndex];
		// Recupera el objeto que corresponde con la celda indicada
		// (rowIndex+columnIndex)
		return parseIndexColumn(rowObject, columnIndex);
	}
	
	public ObfuscatorObject getObjectAtRow(int rowIndex){
		sortData = sortListObfuscator();
		ObfuscatorObject rowObject = sortData[rowIndex];
		return rowObject;
	}
	
	/**
	 * A�ade una fila nueva a la lista
	 * @param obj
	 */
	public void addRow(ObfuscatorObject obj){
		data.put(obj.getFechaOfuscado(), obj);
	}
	
	/**
	 * Inicializa la lista de objetos
	 * @param obj
	 */
	private void initializeData(ArrayList<ObfuscatorObject> list){
		data = new HashMap<Date, ObfuscatorObject>();
		for(ObfuscatorObject obj:list){
			data.put(obj.getFechaValidacion(), obj);
		}
	}

	/**
	 * Devuelve el Object que corresponde a la columna pasada
	 * 
	 * @param obfuscatorObject
	 * @param ColumnIndex
	 * @return
	 */
	private Object parseIndexColumn(ObfuscatorObject obfuscatorObject,
			int ColumnIndex) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Object returnValue;
		switch (ColumnIndex) {
		case 0:
			returnValue = formatter.format(obfuscatorObject.getFechaValidacion());
			break;
		case 1:
			returnValue = formatter.format(obfuscatorObject.getFechaOfuscado());
			break;
		case 2:
			returnValue = obfuscatorObject.getNumTablasAOfuscar();
			break;
		case 3:
			returnValue = obfuscatorObject.getNumTablasOfuscadas();
			break;
		case 4:
			returnValue = obfuscatorObject.getNumTablasSinOfuscar();
			break;
		default:
			returnValue = null;
		}
		return returnValue;
	}

	/**
	 * Devuelve un Array con los datos ordenados con el criterio de la fecha
	 */
	private ObfuscatorObject[] sortListObfuscator() {
		ObfuscatorObject[] arrayResult = new ObfuscatorObject[data.size()];
		Date[] dates = new Date[data.size()];
		data.keySet().toArray(dates);
		for (int i = 1; i < data.size(); i++) {
			if (desc) {
				if (dates[i - 1].after(dates[i])) {
					Date aux = dates[i - 1];
					dates[i - 1] = dates[i];
					dates[i] = aux;
				}
			} else {
				if (dates[i - 1].before(dates[i])) {
					Date aux = dates[i - 1];
					dates[i - 1] = dates[i];
					dates[i] = aux;
				}
			}
		}
		for (int i = 0; i < data.size(); i++) {
			arrayResult[i] = data.get(dates[i]);
		}
		return arrayResult;
	}
}
