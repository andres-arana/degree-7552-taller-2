package fiuba.mda.mer.control;


import org.eclipse.draw2d.Figure;

import fiuba.mda.mer.modelo.base.Componente;
import fiuba.mda.mer.ui.figuras.Figura;

public interface Control<T extends Componente> {

	/**
	 * Figura asociada con este control correspondiente al diagrama del id
	 * especificado.
	 * 
	 * @param idDiagrama
	 * @return
	 */
	public Figura<T> getFigura(String idDiagrama);

	/**
	 * Agrega la figura de este control y las figuras de los hijos al
	 * contenedor.
	 * 
	 * @param contenedor
	 * @param idDiagrama
	 */
	public void dibujar(Figure contenedor, String idDiagrama);
	
	/**
	 * Devuelve el icono que corresponde a este control.
	 * @return
	 */
	public String getNombreIcono();
}
