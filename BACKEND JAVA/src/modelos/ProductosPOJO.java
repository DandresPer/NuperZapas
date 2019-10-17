package modelos;

public class ProductosPOJO {
	
	private String sku;
	private String nombre;
	private String descripcion;
	private String marca;
	private String categoria;
	private String categoriaPadre;
	private String tallas;
	private double precio;
	private int stock;
	private boolean esOferta;
	private boolean esRebajas;
	private String imgSmall;
	private String imgBig;
	
	public ProductosPOJO() {
		super();
	}
	public ProductosPOJO(String sku, String nombre, String descripcion, String marca, String categoria,
			String categoriaPadre, String tallas, double precio, int stock, boolean esOferta, boolean esRebajas,
			String imgSmall, String imgBig) {
		super();
		this.sku = sku;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.marca = marca;
		this.categoria = categoria;
		this.categoriaPadre = categoriaPadre;
		this.tallas = tallas;
		this.precio = precio;
		this.stock = stock;
		this.esOferta = esOferta;
		this.esRebajas = esRebajas;
		this.imgSmall = imgSmall;
		this.imgBig = imgBig;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getCategoriaPadre() {
		return categoriaPadre;
	}
	public void setCategoriaPadre(String categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}
	public String getTallas() {
		return tallas;
	}
	public void setTallas(String tallas) {
		this.tallas = tallas;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public boolean isEsOferta() {
		return esOferta;
	}
	public void setEsOferta(boolean esOferta) {
		this.esOferta = esOferta;
	}
	public boolean isEsRebajas() {
		return esRebajas;
	}
	public void setEsRebajas(boolean esRebajas) {
		this.esRebajas = esRebajas;
	}
	public String getImgSmall() {
		return imgSmall;
	}
	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}
	public String getImgBig() {
		return imgBig;
	}
	public void setImgBig(String imgBig) {
		this.imgBig = imgBig;
	}
	
	

}
