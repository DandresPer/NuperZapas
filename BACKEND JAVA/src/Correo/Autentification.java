package Correo;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Autentification extends Authenticator {

	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication("nuperzapas@gmail.com", "R2d2c3P0&#");
	}

}
