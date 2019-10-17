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
    public class ClienteController : Controller
    {
        APIRESTClient _apiClient = new APIRESTClient();
        #region --METODOS PUBLICOS--
        [HttpGet]
        public IActionResult RegistroUsuario()
        {
            ViewData["Message"] = "Your contact page.";
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> RegistroUsuario(VistaRegistroModelo model)
        {
            APIRESTClient apiClient = new APIRESTClient();
            await apiClient.registrar(model);
            ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
            return RedirectToAction("Index", "Home");
        }
        [HttpPost]
        public async Task<IActionResult> Login(VistaLoginModelo model)
        {
            APIRESTClient apiClient = new APIRESTClient();
            var token = await apiClient.login(model);
            if (token.Length  < 400)
            {
                HttpContext.Session.SetString("token", token);
                ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
            }
            else
            {
                HttpContext.Session.Remove("token");
                ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
            }
            return RedirectToAction("Index", "Home");
        }
        [HttpGet]
        public IActionResult Logout()
        {
            HttpContext.Session.Remove("token");
            ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
            return RedirectToAction("Index", "Home");
        }
        [HttpGet]
        public IActionResult SegundoRegistroUsuario()
        {
            ViewData["Message"] = "Your contact page.";
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> SegundoRegistroUsuario(VistaSegundoRegistroModelo model)
        {
            APIRESTClient apiClient = new APIRESTClient();
            var token = HttpContext.Session.GetString("token");
            model.token = token;
            await apiClient.segundoRegistrar(model);
            ViewBag.Productos = JsonConvert.DeserializeObject<List<Productos>>(HttpContext.Session.GetString("productos"));
            return RedirectToAction("ComprarCarro", "Pedido");
        }



        #endregion
    }

}