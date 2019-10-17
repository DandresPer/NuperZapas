using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using WebApplication8.ModelosVistas;
using WebApplication8.Models;



namespace WebApplication8.Controllers
{
    public class PedidoController : Controller
    {
        APIRESTClient _apiClient = new APIRESTClient();
        Carros _miCarro = new Carros();

        // Pedidos _miPedido;     

        #region --METODOS PUBLICOS--
        
        public async Task<IActionResult> AddProducto(string sku, int cantidad, string returnUrl)
        {
            Pedidos _pedido = new Pedidos();
            List<Pedidos> _pedidosCarro = new List<Pedidos>();

            try
            {  //SI EXISTE CARRO EN SESSION            
                this._miCarro = JsonConvert.DeserializeObject<Carros>(HttpContext.Session.GetString("carrito"));
                
            }
            catch (Exception e)
            {   //SI NO 
                this._miCarro.Pedidos = _pedidosCarro;
            }
          
            var token = HttpContext.Session.GetString("token");

            Productos producto = await _apiClient.getProducto(sku,token);

            if (this._miCarro.Pedidos.Count == 0) //SI ESTA VACIO
            {
                 _pedido.ProductoPOJO = producto;
                _pedido.Unidades = cantidad;
                _pedido.PrecioTotal = producto.precio * cantidad;
                _pedido.PrecionUnitario = producto.precio;

                this._miCarro.Pedidos.Add(_pedido);
            }
            else {  //SI YA HAY ITEMS
                _pedido.ProductoPOJO = producto;
                _pedido.Unidades = cantidad;
                _pedido.PrecioTotal = producto.precio * cantidad;
                _pedido.PrecionUnitario = producto.precio;

                if ((this._miCarro.Pedidos.FirstOrDefault(x => x.ProductoPOJO.sku == producto.sku)) != null )//si ya existe ese item en el carro
                {
                    var aux = this._miCarro.Pedidos.Find(x => x.ProductoPOJO.sku == sku);
                    aux.Unidades += cantidad;
                    aux.PrecioTotal = aux.Unidades * aux.PrecionUnitario;

                }
                else {
                    this._miCarro.Pedidos.Add(_pedido);
                }
            }
            ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
            ViewBag.Carrito = this._miCarro;

            HttpContext.Session.SetString("carrito", JsonConvert.SerializeObject(this._miCarro));

            return Redirect(returnUrl);

        }
        public void RemoveProducto(string sku)
        {
            //Productos producto = await _apiClient.getProducto(sku);
            //HttpContext.Session.SetString("carrito", JsonConvert.SerializeObject(producto));
            //ViewBag.Productos = JsonConvert.DeserializeObject<Productos>(HttpContext.Session.GetString("productos"));
            //ViewBag.Carrito = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("carrito"));
            //return View("~/Views/Shared/Index.cshtml");
            Pedidos _pedido = new Pedidos();
            List<Pedidos> _pedidosCarro = new List<Pedidos>();
            try
            {  //SI EXISTE CARRO EN SESSION            
                this._miCarro = JsonConvert.DeserializeObject<Carros>(HttpContext.Session.GetString("carrito"));
                _miCarro.Pedidos.RemoveAll(X => X.ProductoPOJO.sku == sku);
                if (_miCarro.Pedidos.Count() == 0) {
                    HttpContext.Session.Remove("carrito");
                }
                else
                {
                    HttpContext.Session.SetString("carrito", JsonConvert.SerializeObject(this._miCarro));
                }
                }
            catch (Exception e)
            {   //SI NO 
                this._miCarro.Pedidos = _pedidosCarro;
            }

            return;
        }
        public async Task<IActionResult> ComprarCarro() {
            var token = HttpContext.Session.GetString("token");
            Carros carro = JsonConvert.DeserializeObject<Carros>(HttpContext.Session.GetString("carrito"));
            CarroToken carroToken = new CarroToken() { carro = carro.Pedidos, token = token};

            var response =  await this._apiClient.comprarCarro(carroToken);

            if (response.IsSuccessStatusCode)
            {
                ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
                HttpContext.Session.Remove("carrito");

                return RedirectToAction("Index", "Home");
            }
            else {
                ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));

                return View("~/Views/Shared/Unauthorized.cshtml");
            }
        }
        #endregion
    }
}