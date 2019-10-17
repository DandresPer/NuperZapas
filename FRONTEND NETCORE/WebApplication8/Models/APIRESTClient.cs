using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;
using WebApplication8.ModelosVistas;
using System.Text;
using Newtonsoft.Json;
using Microsoft.AspNetCore.Mvc;

namespace WebApplication8.Models
{
    public class APIRESTClient
    {
        private string BASE_URL = "http://localhost:8080/PRUEBAJO3/servicio/api/";
        [HttpPost]
        public async Task<String> registrar(VistaRegistroModelo modelo)
        {
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                BASE_URL = BASE_URL + "registro";
                var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL);
                string modeloJson = JsonConvert.SerializeObject(modelo);
                var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.PostAsync(BASE_URL, content);
                return await response.Content.ReadAsStringAsync();
            }
            catch
            {
                return null;
            }
        }

        [HttpPost]
        public async Task<String> login(VistaLoginModelo modelo)
        {
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                BASE_URL = BASE_URL + "login";
                var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL);
                string modeloJson = JsonConvert.SerializeObject(modelo);
                var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.PostAsync(BASE_URL, content);
                return await response.Content.ReadAsStringAsync();
            }
            catch
            {
                return null;
            }
        }

        [HttpGet]
        public async Task<List<Productos>> getProductos()
        {
            List<Productos> productos = new List<Productos>();
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                string BASE_URL_TEMP = "";
                BASE_URL_TEMP = BASE_URL + "getProductos";
                var request = new HttpRequestMessage(HttpMethod.Get, BASE_URL_TEMP);
                //string modeloJson = JsonConvert.SerializeObject(modelo);
                //var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.GetAsync(BASE_URL_TEMP);
                var result = await response.Content.ReadAsAsync<object>();
                productos = JsonConvert.DeserializeObject<List<Productos>>(result.ToString());
                return productos;
            }
            catch (Exception e)
            {
                return null;
            }
        }
        [HttpGet]
        public async Task<Productos> getProducto(String sku, String token)
        {
            Productos producto = new Productos();
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

                string BASE_URL_TEMP = "";
                BASE_URL_TEMP = BASE_URL + "getProducto/" + sku;
                var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL_TEMP);
                //string modeloJson = JsonConvert.SerializeObject(modelo);
                //var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.GetAsync(BASE_URL_TEMP);
                var result = await response.Content.ReadAsAsync<object>();
                producto = JsonConvert.DeserializeObject<Productos>(result.ToString());
                return producto;
            }
            catch (Exception e)
            {
                return null;
            }
        }
        [HttpPost]
        public async Task<HttpResponseMessage> comprarCarro(CarroToken modelo)
        {
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", modelo.token);
                BASE_URL = BASE_URL + "comprarCarro";
                var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL);
                string modeloJson = JsonConvert.SerializeObject(modelo);
                var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.PostAsync(BASE_URL, content);
                return response;
            }
            catch
            {
                return null;
            }
        }
        [HttpGet]
        public async Task<List<Categorias>> getCategorias() {
            List<Categorias> categorias = new List<Categorias>();
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                string BASE_URL_TEMP = "";
                BASE_URL_TEMP = BASE_URL + "getCategorias";
                var request = new HttpRequestMessage(HttpMethod.Get, BASE_URL_TEMP);
                //string modeloJson = JsonConvert.SerializeObject(modelo);
                //var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.GetAsync(BASE_URL_TEMP);
                var result = await response.Content.ReadAsAsync<object>();
                categorias = JsonConvert.DeserializeObject<List<Categorias>>(result.ToString());
                return categorias;
            }
            catch (Exception e)
            {
                return null;
            }


        }
        [HttpGet]
        public async Task<List<Productos>> getProductosCat(int cat_cod_1, int cat_cod_2)
        {
            List<Productos> productos = new List<Productos>();
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                //client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

                string BASE_URL_TEMP = "";
                BASE_URL_TEMP = BASE_URL + "getProductosCat/" + cat_cod_1 + "/" + cat_cod_2;
                var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL_TEMP);
                //string modeloJson = JsonConvert.SerializeObject(modelo);
                //var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.GetAsync(BASE_URL_TEMP);
                var result = await response.Content.ReadAsAsync<object>();
                productos = JsonConvert.DeserializeObject<List<Productos>>(result.ToString());
                return productos;
            }
            catch (Exception e)
            {
                return null;
            }
        }
        [HttpGet]
        public async Task<List<Productos>> getProductosFiltro(String filtro)
        {
            List<Productos> productos = new List<Productos>();
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                //client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);

                string BASE_URL_TEMP = "";
                BASE_URL_TEMP = BASE_URL + "getProductosFiltro/" + filtro;
                var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL_TEMP);
                //string modeloJson = JsonConvert.SerializeObject(modelo);
                //var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.GetAsync(BASE_URL_TEMP);
                var result = await response.Content.ReadAsAsync<object>();
                productos = JsonConvert.DeserializeObject<List<Productos>>(result.ToString());
                return productos;
            }
            catch (Exception e)
            {
                return null;
            }
        }
        [HttpPost]
        public async Task<String> segundoRegistrar(VistaSegundoRegistroModelo modelo)
        {
            try
            {
                HttpClient client = new HttpClient();
                client.BaseAddress = new Uri(BASE_URL);
                client.DefaultRequestHeaders.Accept.Add(
                    new MediaTypeWithQualityHeaderValue("application/json"));
                BASE_URL = BASE_URL + "segundoRegistro";
                var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL);
                string modeloJson = JsonConvert.SerializeObject(modelo);
                var content = new StringContent(modeloJson, Encoding.UTF8, "application/json");
                var response = await client.PostAsync(BASE_URL, content);
                return await response.Content.ReadAsStringAsync();
            }
            catch
            {
                return null;
            }
        }
    }
}
