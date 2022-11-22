package mx.edu.utez.applicationexport.controller;

import mx.edu.utez.applicationexport.entidades.Empleado;
import mx.edu.utez.applicationexport.service.EmpleadoServicio;
import mx.edu.utez.applicationexport.util.paginacion.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmpleadoController {

    @Autowired
    private EmpleadoServicio empleadoServicio;

    @GetMapping({"/","/listar",""})
    public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page,5);
        Page<Empleado> empleados = empleadoServicio.findAll(pageRequest);
        PageRender<Empleado> pageRender = new PageRender<>("/lista", empleados);

        model.addAttribute("titulo", "Listado de empleados");
        model.addAttribute("empleados", empleados);
        model.addAttribute("page", pageRender);

        return "listar";
    }
}
