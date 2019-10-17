using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication8.Models;

namespace WebApplication8.ModelosVistas
{
    public class navbarPartialModelCat
    {
        public Productos producto { get; set; }
        public VistaLoginModelo login { get; set; }
        public List<Productos> productosList { get; set; }

    }
}
