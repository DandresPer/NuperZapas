using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication8.Models
{
    public class Pedidos
    {
  //      public int PedidosId { get; set; }
  //      public Clientes Clientes { get; set; }
        public Productos ProductoPOJO { get; set; }
        public int Unidades { get; set; }
        public double PrecionUnitario { get; set; }
        public double PrecioTotal { get; set; }
    }
}
