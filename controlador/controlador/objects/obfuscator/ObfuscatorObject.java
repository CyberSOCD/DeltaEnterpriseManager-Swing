package controlador.objects.obfuscator;

import java.util.ArrayList;
import java.util.Date;

public class ObfuscatorObject {

	private Date fechaOfuscado;

	private ArrayList<String> tablasOfuscadas;
	private ArrayList<String> tablasAOfuscar;
	private ArrayList<String> tablasSinOfuscar;
	
	private int numTablasOfuscadas;
	private int numTablasSinOfuscar;
	private int numTablasAOfuscar;

	public int getNumTablasAOfuscar() {
		return numTablasAOfuscar;
	}

	public void setNumTablasAOfuscar(int numTablasAOfuscar) {
		this.numTablasAOfuscar = numTablasAOfuscar;
	}

	public int getNumTablasOfuscadas() {
		return numTablasOfuscadas;
	}

	public void setNumTablasOfuscadas(int numTablasOfuscadas) {
		this.numTablasOfuscadas = numTablasOfuscadas;
	}

	public int getNumTablasSinOfuscar() {
		return numTablasSinOfuscar;
	}

	public void setNumTablasSinOfuscar(int numTablasSinOfuscar) {
		this.numTablasSinOfuscar = numTablasSinOfuscar;
	}

	public Date getFechaOfuscado() {
		return fechaOfuscado;
	}

	public void setFechaOfuscado(Date fechaOfuscado) {
		this.fechaOfuscado = fechaOfuscado;
	}

	public ArrayList<String> getTablasOfuscadas() {
		return tablasOfuscadas;
	}

	public void setTablasOfuscadas(ArrayList<String> tablasOfuscadas) {
		this.tablasOfuscadas = tablasOfuscadas;
	}

	public ArrayList<String> getTablasAOfuscadas() {
		return tablasAOfuscar;
	}

	public void setTablasAOfuscadas(ArrayList<String> tablasAOfuscadas) {
		this.tablasAOfuscar = tablasAOfuscadas;
	}

	public ArrayList<String> getTablasSinOfuscadas() {
		return tablasSinOfuscar;
	}

	public void setTablasSinOfuscadas(ArrayList<String> tablasSinOfuscadas) {
		this.tablasSinOfuscar = tablasSinOfuscadas;
	}

}
