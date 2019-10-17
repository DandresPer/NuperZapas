using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Linq.Expressions;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using WebApplication8.Models;
using Microsoft.AspNetCore.Http;
using System.Collections;
using X.PagedList;

namespace WebApplication8.Controllers
{
    public class HomeController : Controller
    {
        APIRESTClient _apiClient = new APIRESTClient();
        #region --METODOS PUBLICOS--
        public async Task<IActionResult> Index(int? page)
        {
            var pageNumber = page ?? 1;
            List<Productos> productos;
            List<Categorias> categorias;
            productos = await _apiClient.getProductos();
            HttpContext.Session.SetString("productos", JsonConvert.SerializeObject(productos));
            categorias = await _apiClient.getCategorias();
            HttpContext.Session.SetString("categorias", JsonConvert.SerializeObject(categorias));
            ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
            ViewBag.Categorias = JsonConvert.DeserializeObject<List<Categorias>>(HttpContext.Session.GetString("categorias"));
            return View();
        }
        public async Task<IActionResult> VistaProducto(string sku)
        {
            var token = HttpContext.Session.GetString("token");
            Productos producto;
            producto = await _apiClient.getProducto(sku,token);
            return View(producto);
        }
        public async Task<IActionResult> VistaProductosCat(int catCod1, int catCod2, int? page) {

            List<Productos> productos;
            productos = await _apiClient.getProductosCat(catCod1, catCod2);

            
            var pageNumber = page ?? 1;
            var onePageOfProducts = productos.ToPagedList(pageNumber, 12);


            ViewBag.ProductosCat = onePageOfProducts;
            ViewBag.Categorias = JsonConvert.DeserializeObject<List<Categorias>>(HttpContext.Session.GetString("categorias"));
            return View();

        }
        public async Task<IActionResult> VistaProductosFiltro(string filtro, int? page)
        {

            List<Productos> productos;
            productos = await _apiClient.getProductosFiltro(filtro);

            var pageNumber = page ?? 1;
            var onePageOfProducts = productos.ToPagedList(pageNumber, 12);


            ViewBag.ProductosCat = onePageOfProducts;
            ViewBag.Filtro = filtro;
            ViewBag.Categorias = JsonConvert.DeserializeObject<List<Categorias>>(HttpContext.Session.GetString("categorias"));
            return View();

        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
        #endregion
    }
}
