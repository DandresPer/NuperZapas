package persistencia;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.security.Keys;
import modelos.Carros;
import modelos.CarrosId;
import modelos.Categorias;
import modelos.Clientes;
import modelos.Direcciones;
import modelos.DireccionesId;
import modelos.DireccionesPOJO;
import modelos.Pedidos;
import modelos.PedidosId;
import modelos.PedidosPOJO;
import modelos.PedidosTotal;
import modelos.PedidosTotalId;
import modelos.Productos;
import modelos.ProductosPOJO;
import servicio.APIRESTService;

import javax.persistence.criteria.*;

public class Operaciones {
	// VARIABLES PARA HIBERNATE
	private Session ses;
	private Transaction tx;
	private final static Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private List<String> listaTokens = new ArrayList<>();
	
    public final static SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
	public Operaciones(SessionFactory sf) {
		ses = sf.openSession();

	}

	// METODOS DEL SERVICIO RESTFUL - TODAS LAS RESPUESTAS SON JSON
	public int registrarUsuario(Clientes cli)
	// Introduce un nuevo usuario en la BD si no hay otro registro con ese email
	// exito '0' error '1'
	{
		int flag = 1;
		Clientes cliente = null;
		try {
			System.out.println("INTENTA");
		cliente = buscarClientePorEmail(cli.getEmail());
		}catch(Exception e) {
			System.out.println("EXCEPDICE " + e.getMessage());
		}
		if (cliente == null) {
			try {
				System.out.println("CREANDO");
				tx = ses.beginTransaction();
				ses.save(cli);
				tx.commit();
				flag = 0;
			} catch (PersistenceException e) {
				tx.rollback();
				System.out.println("Error HIBERNATE");
				System.out.printf("MENSAJE: %s%n", e.getMessage());
				System.out.println("EMAIL: " + cli.getEmail());
				flag = 1;
			}
		} else {
			System.out.println("EMAIL DUPLICADO");
			flag = 1;
		}
		//ses.close();
		return flag;

	}

	public int segundoRegistrarUsuario(DireccionesPOJO direccionPOJO)
	// Introduce un nuevo usuario en la BD si no hay otro registro con ese email
	// exito '0' error '1'
	{
		int flag = 1;
		DireccionesId direccionId = new DireccionesId();
		Direcciones direccion = new Direcciones();
		Clientes cliente = new Clientes();
		String email = "";
		
		try {
			System.out.println("INTENTA");
			email = checkToken2(direccionPOJO.getToken().replace("\"",""));
			System.out.println("EMAIL: "  + email);
		cliente = buscarClientePorEmail(email);
		System.out.println("CLIENTE: "  + cliente);
		}catch(Exception e) {
			System.out.println("EXCEPDICE " + e.getMessage());
		}
		if (cliente == null) {
			flag = 1;
		} else {
			System.out.println("CLIENTE ENCONTRADO");
			
			direccionId.setBloque(direccionPOJO.getBloque());
			direccionId.setCalle(direccionPOJO.getCalle());
			direccionId.setClienteEmail(email);
			direccionId.setCp(direccionPOJO.getCp());
			direccionId.setLocalidad(direccionPOJO.getLocalidad());
			direccionId.setNumero(direccionPOJO.getNumero());
			direccionId.setPais(direccionPOJO.getPais());
			direccionId.setPiso(direccionPOJO.getPiso());
			direccionId.setPuerta(direccionPOJO.getPuerta());
			
			direccion.setCiudad(direccionPOJO.getCiudad());
			direccion.setClientes(cliente);
			direccion.setId(direccionId);
			direccion.setObservaciones(direccionPOJO.getObservaciones());
			
			try {
				System.out.println("GUARDANDO");
				tx = ses.beginTransaction();
			ses.save(direccion);
			tx.commit();
			flag=0;
			System.out.println("GUARDADO");
			} catch(Exception e) {
				tx.rollback();
				System.out.println("FALLO AL INSERTAR DIRECCION: " + e.getMessage());
				flag=1;
			}
		}
		return flag;

	}

	
	public String login(String email, String pass) {
		Clientes cliente = null;
		cliente = buscarClientePorEmail(email);
		if (cliente != null) {
			String jws = Jwts.builder().setIssuer("NuperAuth").claim("email", email).signWith(KEY).compact();

			return jws.toString();
		}
		return null;
	}
	public Boolean checkToken(String jws) throws Exception {
    	try {
    		//Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    		System.out.println("LLLLLL: " + jws);
    		Boolean isValidToken= Jwts.parser().setSigningKey(KEY).parseClaimsJws(jws)
    				.getBody().getIssuer().equals("NuperAuth");

    		System.out.println("IS_VALID: " + isValidToken);
			return true;

    	}catch(Exception e) {
    		System.out.println("TOKEN ERRONEO : " + e.getMessage() );
    		return false;
    	}
	}
	
	public String checkToken2(String jws) throws Exception {
    	try {
    		//Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    		System.out.println("CHECKTOKEN2: " + jws);
    		Boolean isValidToken= Jwts.parser().setSigningKey(KEY).parseClaimsJws(jws)
    				.getBody().getIssuer().equals("NuperAuth");

    		System.out.println("IS_VALID: " + isValidToken);
    		System.out.println("ESTAAAA" + Jwts.parser().setSigningKey(KEY).parseClaimsJws(jws).getBody().get("email").toString());
			return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jws).getBody().get("email").toString();
    		//return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jws).getBody().get("email").toString();

    	}catch(Exception e) {
    		System.out.println("TOKEN ERRONEO : " + e.getMessage() );
    		return "nope";
    	}
	}
	
	public List<Productos> getProductos() {

		String hql = "FROM Productos";
		Query<Productos> query = ses.createQuery(hql);
		List<Productos> listaTodosProductos = query.list();

		return listaTodosProductos;
	}

	public Productos getProducto(String sku) {

		String hql = "FROM Productos WHERE sku = :sku";
		Query<Productos> query = ses.createQuery(hql);
		query.setParameter("sku", sku);
		Productos producto = query.getSingleResult();

		return producto;
	}

	private Clientes buscarClientePorEmail(String email)
	// Busca un email en la BD
	// '0' No existe '1' Existe
	{
		System.out.println("MAILENTRADA " + email);
		Clientes cliente = null;
		String hql = "FROM Clientes WHERE email = :email";
		Query<Clientes> query = ses.createQuery(hql);
		query.setParameter("email", email);
		cliente = query.getSingleResult();
		System.out.println("CLIENTE: " + cliente.getEmail());

		return cliente;
	}
	public void addCarro(List<PedidosPOJO> pedidos,String token) {
		CarrosId carroId = new CarrosId();
		PedidosPOJO pedidoPOJO = new PedidosPOJO();
		PedidosId pedidoId = new PedidosId();
		ProductosPOJO productoPOJO = new ProductosPOJO();
		Productos producto = new Productos();
		
		Clientes cliente = new Clientes();
		/*ID CARRO CLIENTES PEDIDOS*/

		/*BUSCAR CLIENTE*/
		String emailCliente = "";
		try {
		emailCliente = checkToken2(token);
		} catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		System.out.println("EMAIL CLIENTE: " + emailCliente);
		cliente = buscarClientePorEmail(emailCliente);
		System.out.println("PEDIDOSSS " + pedidos.size());

		/*BUSCAR EL ULTIMO PEDIDO*/
		String hql = "SELECT MAX(ped.id.idPedido) FROM Pedidos ped";
		Query<Integer> query = ses.createQuery(hql);
		int idPedidoMax = query.getSingleResult() +1;
		
		/*BUSCAR EL ULTIMO CARRO*/
		hql = "SELECT MAX(car.id.idCarro) FROM Carros car";
		query = ses.createQuery(hql);
		int idCarroMax = query.getSingleResult() +1;
		
		PedidosTotal pedidoTotal = new PedidosTotal();
		PedidosTotalId pedidoTotalId = new PedidosTotalId();
		
		double precioTotalPedidos = 0;
		for (PedidosPOJO ped : pedidos) {
			Pedidos pedido = new Pedidos();
			Carros carro = new Carros();
			/*GUARDAMOS PRECIO TOTAL DE TODOS*/
			precioTotalPedidos += ped.getPrecioTotal();
			System.out.println("PED SKU ENTRO") ;				
			System.out.println("PED SKU " + ped.getProductoPOJO().getSku());		
			/*SET PEDIDO ID*/
			
			
				pedidoId.setIdPedido(idPedidoMax);
				pedidoId.setProductoSku(ped.getProductoPOJO().getSku());
				
				/*construir producto from POJO*/
				producto.setCategoria(ped.getProductoPOJO().getCategoria());
				producto.setCategoriaPadre(ped.getProductoPOJO().getCategoriaPadre());
				producto.setDescripcion(ped.getProductoPOJO().getDescripcion());
				producto.setEsOferta(ped.getProductoPOJO().isEsOferta());
				producto.setEsRebajas(ped.getProductoPOJO().isEsRebajas());
				producto.setImgBig(ped.getProductoPOJO().getImgBig());
				producto.setImgSmall(ped.getProductoPOJO().getImgSmall());
				producto.setMarca(ped.getProductoPOJO().getMarca());
				producto.setNombre(ped.getProductoPOJO().getNombre());
				producto.setPrecio(ped.getProductoPOJO().getPrecio());
				producto.setSku(ped.getProductoPOJO().getSku());
				producto.setStock(ped.getProductoPOJO().getStock());
				producto.setTallas(ped.getProductoPOJO().getTallas());
				
				//producto.setValoracioneses(valoracioneses);
				//producto.setPedidoses(pedidoses);
				/*construir pedido from POJO*/
				pedido.setId(pedidoId);
				pedido.setPrecioTotal(ped.getPrecioTotal());
				pedido.setPrecioUnitario(ped.getPrecionUnitario());
				pedido.setProductos(producto);
				pedido.setUnidades(ped.getUnidades());				
				
				//pedido.setCarroses(carroses);

				/*GUARDAR PEDIDOS*/
				try {
					System.out.println("HE ENTRADO!!!");
					pedido.setId(pedidoId);
					tx = ses.beginTransaction();
					ses.save(pedido);
					tx.commit();
					System.out.println("HE PASADO!!!");

				} catch (Exception e) {
					tx.rollback();
					System.out.println("Error HIBERNATE PEDIDO");
					System.out.printf("MENSAJE: %s%n", e.getMessage());
	
				}
				
				/*GUARDAR TOTAL_PEDIDO*/

				
				
				
				/*ACTUALIZA CARROID*/
			try {
			carroId.setClienteEmail(emailCliente);
			carroId.setIdCarro(idCarroMax);
			carroId.setIdPedido(idPedidoMax);
			carroId.setProductoSku(ped.getProductoPOJO().getSku());
			}catch(Exception e) {
				System.out.println("ERROR CARROID: " + e);
			}
		
			/*GUARDAR CARRO*/
			try {
			carro.setId(carroId);
			carro.setClientes(cliente);
			carro.setPedidos(pedido);
			}catch(Exception e) {
				System.out.println("ERROR CARROID: " +e);
			}
			try {
				System.out.println("ENTRO METEER CARRO");
				tx = ses.beginTransaction();
				ses.save(carro);
				tx.commit();
				System.out.println("SALGO METEER CARRO");

			} catch (PersistenceException e) {
				tx.rollback();
				System.out.println("Error HIBERNATE CARRO");
				System.out.printf("MENSAJE: %s%n", e.getMessage());

			}


		}
		try {
			System.out.println("ENTRO TOTAL_PEDIDOID");
			
			pedidoTotalId.setClienteEmail(emailCliente);
			pedidoTotalId.setIdPedido(idPedidoMax);
			
			System.out.println("SALGO TOTAL_PEDIDOID");					
		}catch(Exception e) {
			System.out.println("TOTAL_PEDIDO: " + e);
		}
		
		try {
			System.out.println("ENTRO TOTAL_PEDIDO");
			
			pedidoTotal.setId(pedidoTotalId);
			pedidoTotal.setClientes(cliente);
			pedidoTotal.setTotalPedido(precioTotalPedidos);
			
			System.out.println("SALGO TOTAL_PEDIDO");					
		}catch(Exception e) {
			System.out.println("TOTAL_PEDIDO: " + e);
		}
		
		try {
			System.out.println("ENTRO METEER TOTAL_CARRO");
			tx = ses.beginTransaction();
			ses.save(pedidoTotal);
			tx.commit();
			System.out.println("SALGO METEER TOTAL_CARRO");

		} catch (PersistenceException e) {
			System.out.println("Error HIBERNATE TOTAL_CARRO");
			System.out.printf("MENSAJE: %s%n", e.getMessage());

		}
		return;
	}

	public List<Categorias> getCategorias(){
		String hql = "FROM Categorias";
		Query<Categorias> query = ses.createQuery(hql);
		List<Categorias> listaCategorias = query.list();

		return listaCategorias;
		
	}

	public List<Productos> getProductosCat(int cat_cod_1, int cat_cod_2){
		String hql="";
		Query<Productos> query;
		if(cat_cod_1 > 3 && cat_cod_2 != 0) {
			hql = "FROM Productos prod WHERE prod.categoria = :cat_cod_1 AND prod.categoriaPadre = :cat_cod_2";
			System.out.print("QUERY");
			query = ses.createQuery(hql);
			query.setParameter("cat_cod_1", Integer.toString(cat_cod_1));
			query.setParameter("cat_cod_2", Integer.toString(cat_cod_2));
		}
		else {
			hql = "FROM Productos prod WHERE prod.categoria = :cat_cod_1";
			System.out.print("QUERY");
			query = ses.createQuery(hql);
			query.setParameter("cat_cod_1", Integer.toString(cat_cod_1));
		}
		

		List<Productos> listaProductos = query.list();
		
		return listaProductos;
	}
	public List<Productos> getProductosFiltro(String filtro){
		String hql = "FROM Productos prod WHERE prod.nombre LIKE :filtro OR prod.marca LIKE :filtro";
		System.out.print("QUERY");
		Query<Productos> query = ses.createQuery(hql);
		query.setParameter("filtro", "%" +filtro + "%");
		List<Productos> listaProductos = query.list();
		
		return listaProductos;
	}


}
