package modelos;

public class DireccionesPOJO {
	String token;
    String cp;
    String pais;
    String localidad;
    String ciudad;
    String calle;
    String numero;
    String bloque;
    String piso;
    String puerta;
    String observaciones;
	public DireccionesPOJO() {
		super();
	}
	public DireccionesPOJO(String token, String cp, String pais, String localidad, String ciudad, String calle,
			String numero, String bloque, String piso, String puerta, String observaciones) {
		super();
		this.token = token;
		this.cp = cp;
		this.pais = pais;
		this.localidad = localidad;
		this.ciudad = ciudad;
		this.calle = calle;
		this.numero = numero;
		this.bloque = bloque;
		this.piso = piso;
		this.puerta = puerta;
		this.observaciones = observaciones;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
    
    
}
