package fiuba.mda.mer.modelo.base;

public interface ComponenteCardinal {
	public String getCardinalidadMaxima();

	public void setCardinalidadMaxima(String cardinalidad);

	public String getCardinalidadMinima();

	public void setCardinalidadMinima(String cardinalidad);
}