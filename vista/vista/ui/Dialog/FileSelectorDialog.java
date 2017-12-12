package vista.ui.Dialog;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Ventana para seleccionar un fichero
 * 
 */
@SuppressWarnings("serial")
public class FileSelectorDialog extends JFileChooser {

	public FileSelectorDialog() {
		super();

	}
	
	public FileSelectorDialog(String defaultPath) {
		super(defaultPath);

	}

	/**
	 * Se le indica la extensión valida que filtrará el FileChooser con formato
	 * .xxx
	 * 
	 * @param extension
	 */
	public void setExtension(String extension) {
		final String ext = extension;
		final String description = "*" + extension;
		FileFilter f = new FileFilter() {
			
			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public boolean accept(File f) {
				if(!f.isDirectory()){
					String name = f.getName();
					//Se valida que los últimos caracteres coinciden 
					//con la extension pasada
					if(name.substring(name.length()-ext.length(), name.length()).equals(ext)){
						return true;
					}
				}else
					return true;
				return false;
			}
		};
		setFileFilter(f);
	}

	public File getSelectedFile() {
		return super.getSelectedFile();
	}
}
