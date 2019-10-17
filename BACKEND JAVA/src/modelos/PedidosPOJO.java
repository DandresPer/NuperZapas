package modelos;

public class PedidosPOJO {

	private ProductosPOJO ProductoPOJO;
	private int Unidades;
	private double PrecionUnitario;
	public double PrecioTotal;
	public PedidosPOJO() {
		super();
	}
	public PedidosPOJO(ProductosPOJO productoPOJO, int unidades, double precionUnitario, double precioTotal) {
		super();
		ProductoPOJO = productoPOJO;
		Unidades = unidades;
		PrecionUnitario = precionUnitario;
		PrecioTotal = precioTotal;
	}
	public ProductosPOJO getProductoPOJO() {
		return ProductoPOJO;
	}
	public void setProductoPOJO(ProductosPOJO productoPOJO) {
		ProductoPOJO = productoPOJO;
	}
	public int getUnidades() {
		return Unidades;
	}
	public void setUnidades(int unidades) {
		Unidades = unidades;
	}
	public double getPrecionUnitario() {
		return PrecionUnitario;
	}
	public void setPrecionUnitario(double precionUnitario) {
		PrecionUnitario = precionUnitario;
	}
	public double getPrecioTotal() {
		return PrecioTotal;
	}
	public void setPrecioTotal(double precioTotal) {
		PrecioTotal = precioTotal;
	}
	
	

}
