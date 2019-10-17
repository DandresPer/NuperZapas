using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
namespace WebApplication8.Models
{
    public class Productos
    {
        public string sku { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public string marca { get; set; }
        public string categoria { get; set; }
        public string categoriaPadre { get; set; }
        public string tallas { get; set; }
        public double precio { get; set; }
        public int stock { get; set; }
        public bool esOferta { get; set; }
        public bool esRebajas { get; set; }
        public string imgSmall { get; set; }
        public string imgBig { get; set; }
    }
}
