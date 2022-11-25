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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class EmpleadoController {

    @Autowired
    private EmpleadoServicio empleadoServicio;

    @GetMapping("/ver/{id}")
    public String verDetallesEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash){
        Empleado empleado = empleadoServicio.findOne(id);
        if (empleado == null) {
            flash.addFlashAttribute("error", "El empleado no existe en la base de datos");
            return "redirect:/listar";
        }
        modelo.put("empleado", empleado);
        modelo.put("titulo", "Detalles del empleado: " + empleado.getNombre());
        return "Ver";
    }

    @GetMapping("/form")
    public String mostrarFormularioRegistro(Map<String,Object> model) {
        Empleado empleado = new Empleado();
        model.put("empleado", empleado);
        model.put("titulo", "Registro de empleados");
        return "form";
    }

    @PostMapping("/form")
    public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registro de empleados");
            return "form";
        }
        String mensaje = (empleado.getId() != null) ? "El empleado ha sido modificado correctamente" : "El empleado ha sido registrado correctamente";
        empleadoServicio.save(empleado);
        status.setComplete();
        flash.addFlashAttribute("succes", mensaje);
        return "redirect:listar";
    }

    @GetMapping("/form/{id}")
    public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Empleado empleado = null;
        if (id > 0) {
            empleado = empleadoServicio.findOne(id);
            if (empleado == null) {
                flash.addFlashAttribute("error", "El empleado no existe en la base de datos");
                return "redirect:/listar";
            }
        }
        else {
            flash.addFlashAttribute("error", "El ID del empleado no puede ser cero");
            return "redirect:/listar";
        }
        model.put("empleado", empleado);
        model.put("titulo", "Edición de empleado");
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String elimanrCliente(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            empleadoServicio.delete(id);
            flash.addFlashAttribute("success", "El empleado ha sido eliminado correctamente");
        }
        return "redirect:/listar";
    }



    @GetMapping({"/","/listar",""})
    public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page,5);
        Page<Empleado> empleados = empleadoServicio.findAll(pageRequest);
        PageRender<Empleado> pageRender = new PageRender<>("/listar", empleados);

        model.addAttribute("titulo", "Listado de empleados");
        model.addAttribute("empleados", empleados);
        model.addAttribute("page", pageRender);

        return "listar";
    }
}
