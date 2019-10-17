package servicio;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NameBinding;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import modelos.CarroTokenPOJO;
import modelos.Carros;
import modelos.Categorias;
import modelos.Clientes;
import modelos.CredencialesPOJO;
import modelos.DireccionesPOJO;
import modelos.Pedidos;
import modelos.PedidosPOJO;
import modelos.Productos;
import persistencia.HibernateUtil;
import persistencia.Operaciones;

import Correo.*;

@Path("api")

public class APIRESTService {
	
	private static final long serialVersionUID = 1L;
	//Instanciacion estatica SessionFactory
	private static SessionFactory sf = HibernateUtil.getSessionFactory();
	//Instanciacion operaciones con el SessionFactory
	public static Operaciones opera = new Operaciones(sf);

	@Path("/getProductos")
	//Recibe FormParams con los datos del cliente y lo registra
	//devuelve json '0' exito '1' error
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	
	public String getProductos() throws Exception{
		try {
			System.out.println("ENTRO GET_PRODUCTOS");
			List<Productos> listaTodosProductos = opera.getProductos();
			Gson gson = new Gson();
			
		return gson.toJson(listaTodosProductos);
		
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).toString();
		}	
	}
	
	@Path("/getCategorias")
	//Recibe FormParams con los datos del cliente y lo registra
	//devuelve json '0' exito '1' error
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	
	public String getCategorias() throws Exception{
		try {
			System.out.println("ENTRO GET_CATEGORIAS");
			List<Categorias> listaCategorias = opera.getCategorias();
			Gson gson = new Gson();
			
		return gson.toJson(listaCategorias);
		
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).toString();
		}	
	}
	
	@Path("/getProducto/{sku}")
	//Recibe FormParams con los datos del cliente y lo registra
	//devuelve json '0' exito '1' error
	@GET
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	
	public String getProducto(@PathParam("sku") String sku) throws Exception{
		try {
			System.out.println("ENTRO GET_PRODUCTO");
			Productos producto = opera.getProducto(sku);
			Gson gson = new Gson();
			
		return gson.toJson(producto);
		
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).toString();
		}	
	}
		
	@Path("/getProductosCat/{cat_cod_1}/{cat_cod_2}")
	@GET
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	
	public String getProductosPorCategoria(@PathParam("cat_cod_1") int cat_cod_1, 
			@PathParam("cat_cod_2") int cat_cod_2) {
		try {
			System.out.println("ENTRO GET_PRODUCTOCAT");
			List<Productos> productos = opera.getProductosCat(cat_cod_1, cat_cod_2);
			Gson gson = new Gson();
			
		return gson.toJson(productos);
		
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).toString();
		}	
	}
	
	@Path("/getProductosFiltro/{filtro}")
	@GET
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	
	public String getProductosFiltro(@PathParam("filtro") String filtro) {
		try {
			System.out.println("ENTRO GET_PRODUCTO_FILTRO");
			List<Productos> productos = opera.getProductosFiltro(filtro);
			Gson gson = new Gson();
			
		return gson.toJson(productos);
		
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).toString();
		}	
	}
	
	@Path("/registro")
	//Recibe FormParams con los datos del cliente y lo registra
	//devuelve json '0' exito '1' error
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response.ResponseBuilder registro(Clientes cliente) throws Exception{
		try {
			System.out.println("ENTRO REGISTRO");
			opera.registrarUsuario(cliente);
		Gson gson = new Gson();		
		return Response.status(Status.OK);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST);
		}
		
	}
	
	@Path("/segundoRegistro")
	//Recibe FormParams con los datos del cliente y lo registra
	//devuelve json '0' exito '1' error
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response.ResponseBuilder segundoRegistro(DireccionesPOJO direccion) throws Exception{
		try {
			System.out.println("ENTRO SEGUNDO REGISTRO");
			opera.segundoRegistrarUsuario(direccion);
		Gson gson = new Gson();		
		return Response.status(Status.OK);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST);
		}
		
	}
		
	@Path("/login")
	//Recibe FormParams con los datos del cliente y lo registra
	//devuelve json '0' exito '1' error
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	
	public Response login(CredencialesPOJO credenciales) throws Exception{
		try {
			
			System.out.println("ENTRO LOGIN");
		String jwt = opera.login(credenciales.getEmail(), credenciales.getPassword());
		
		Gson gson = new Gson();
		return Response.ok(gson.toJson(jwt), MediaType.APPLICATION_JSON).build();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
		
	}
	
	@Path("/comprarCarro")
	//Recibe FormParams con los datos del cliente y lo registra
	//devuelve json '0' exito '1' error
	@POST
	@Secured
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	
	public Response comprarCarro(CarroTokenPOJO carroToken){
		
		try {
			List<PedidosPOJO> pedidos = new ArrayList<PedidosPOJO>();
			System.out.println("ENTRO COMPRAR CARRO");
			
			/*SACAR EMAIL DEL TOKEN*/
			String token = carroToken.getToken();
			System.out.println("SACO TOKEN2" + token);
			/*COMPROBAR QUE EXISTE*/
			System.out.println("1111111"+token);
	        token = token.replace("\"", "");
			System.out.println("2222222"+token);
			String isValid = opera.checkToken2(token);
			/*GUARDAR LOS PEDIDOS Y EL CARRO(añadir id carro)*/			
			pedidos = carroToken.getCarro();
			opera.addCarro(pedidos,token);
			Correo correo = new Correo();
			String HeaderMail = "<h1>Gracias por comprar en NUPER-ZAPAS!</h1>\n <h2>DETALLES DE LA COMPRA:<h2>\n";
			String HtmlMail = "";
			String uno = "";
			double total=0;
			for (PedidosPOJO pedido : pedidos) {
				uno += "<tr> <td> " + pedido.getProductoPOJO().getNombre() + " </td>" 
						+ "<td>" + pedido.getPrecionUnitario() + "</td>"
						+ "<td>" + pedido.getUnidades() + "</td>"
						+ "<td>" + (double) Math.round(pedido.getPrecioTotal() * 100)/100 + "</td>"
						+" </tr>";
				total += pedido.getPrecioTotal();
			}
			total =(double) Math.round(total * 100) / 100;
			
			HtmlMail = "  <style type=\"text/css\">\r\n" + 
					"  body {\r\n" + 
					"    color: purple;\r\n" + 
					"    background-color: #d8da3d;\r\n "+
					"	 width:100%;\r\n" +
					" }\r\n" + 
					
					"  table {\r\n" + 
					"    border: 2px solid red;\r\n" + 
					"	 text-align:center;\r\n" +
					"	 width:80%;\r\n" +
					" }\r\n" + 
					
					"  th {\r\n" + 
					"    color: orangered;\r\n" + 
					"    text-transform:uppercase;\r\n "+
					"	 font-size:20px\r\n" +
					"	 padding:20px 8px\r\n" +
					"	 font-weight:bold 8px\r\n" +
					" }\r\n" + 
					
					"  td {\r\n" + 
					"    color: orange;\r\n" + 
					"    text-transform:uppercase;\r\n "+
					"	 font-size:18px\r\n" +
					"	 padding:20px 8px\r\n" +
					" }\r\n" + 
					
					"  </style>"
					+ "<table> <tbody> <tr> <th> NOMBRE </th> <th> PRECIO UNITARIO </th> <th> UNIDADES </th>"
					+ "<th> PRECIO TOTAL </th>" +"</tr>" + uno + "<tr> <td>TOTAL:</td> <td>"+ total +" </td> </tr>" +"</tbody> </table>";
			//correo.enviar(opera.checkToken2(token), "NUPERZAPAS-COMPRA", HeaderMail + HtmlMail);
			
			Runnable r = new MandarCorreoThread(correo, opera.checkToken2(token), "NUPERZAPAS-COMPRA", HeaderMail + HtmlMail);
			new Thread(r).start();
			return Response.ok().build();
		} catch(Exception e) {
			System.out.println("EXCEP: " + e.getMessage());
			System.out.println("EXCEP: " + e);
			return Response.status(Status.BAD_REQUEST).build();
		}
		
	}
	
	

//	@Path("/getPedidosCliente")
//	//Recibe FormParams con los datos del cliente y lo registra
//	//devuelve json '0' exito '1' error
//	@GET
//	@Produces (MediaType.APPLICATION_JSON)
//	
//	public String getPedidosCliente(String token) throws Exception{
//
//		
//		try {
//			System.out.println("ENTRO GET_PEDIDOS_CLIENTE");
//			token = token.replace("\"", "");
//			
//			String emailCliente = opera.checkToken2(token);
//			/*lista carros*/
//			List<Carros> listaCarrosCliente = opera.getCarrosCliente(emailCliente);
//			/*lista pedidos*/
//
//			List<List<Pedidos>> listaPedidosCliente = new ArrayList<List<Pedidos>>();
//			List<Pedidos> listaPedidos = new ArrayList<Pedidos>();
//			
//			for (Carros car : listaCarrosCliente) {
//			
//				
// 	
//			}
//			
//			
//			
//			Gson gson = new Gson();
//			
//		return gson.toJson(listaCategorias);
//		
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//			return Response.status(Status.BAD_REQUEST).toString();
//		}	
//	}
}

