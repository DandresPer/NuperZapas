package Correo;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {

	private String emisor="nuperzapas@gmail.com";
	private Session sesion=null;
	
	public Correo() {
		Properties propiedades= new Properties();
		propiedades.put("mail.smtp.host", "smtp.gmail.com");
		propiedades.put("mail.smtp.starttls.enable", "true");
		propiedades.put("mail.smtp.user", "emisor");
		propiedades.put("mail.smtp.port", "587");
		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.ssl.trust", "*");
		
		Autentification visar=new Autentification();
		sesion=Session.getInstance(propiedades,visar);
		sesion.setDebug(true); //para ver el proceso de conexion en la consola
		
		
	}
	
	public void enviar(String destino, String asunto, String cuerpo){
		MimeMessage mensage=new MimeMessage(sesion);
		try{
			mensage.setFrom(new InternetAddress(emisor));
		mensage.addRecipients(Message.RecipientType.CC,destino);
		mensage.setSubject(asunto);
		mensage.setContent(cuerpo, "text/html");
		Transport.send(mensage);
		System.out.println("El correo a "+destino+" ha sido mandado");
		}
		catch(Exception e){
			System.out.println("El correo a "+destino+" NO ha sido mandado"+e.getMessage());
		}
		}
	
	

}
