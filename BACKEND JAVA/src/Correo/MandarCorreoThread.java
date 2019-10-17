package Correo;

public class MandarCorreoThread implements Runnable{
	public Correo correoDentro;
	public String destino;
	public String asunto;
	public String cuerpo;
	public MandarCorreoThread (Correo correo, String destino, String asunto, String cuerpo) {
		
	this.correoDentro = correo;
	this.destino = destino;
	this.asunto = asunto;
	this.cuerpo=cuerpo;
		
	}
	
	@Override
	public void run() {

		this.correoDentro.enviar(this.destino, this.asunto, this.cuerpo);
		
	}

}
