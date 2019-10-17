package modelos;

import java.util.List;

public class CarroTokenPOJO {
	private List<PedidosPOJO> carro;
	private String token;
	public CarroTokenPOJO(List<PedidosPOJO> carro, String token) {
		super();
		this.carro = carro;
		this.token = token;
	}
	public List<PedidosPOJO> getCarro() {
		return carro;
	}
	public void setCarro(List<PedidosPOJO> carro) {
		this.carro = carro;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}


	
	
}
