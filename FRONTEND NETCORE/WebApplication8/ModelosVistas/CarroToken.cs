using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication8.Models;

namespace WebApplication8.ModelosVistas
{
    public class CarroToken
    {
        public List<Pedidos> carro { get; set; }
        public string token { get; set; }
    }
}
