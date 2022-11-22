package mx.edu.utez.applicationexport.service;

import mx.edu.utez.applicationexport.entidades.Empleado;
import mx.edu.utez.applicationexport.repository.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoServicioImp implements EmpleadoServicio{

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        return (List<Empleado>) empleadoRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Empleado> findAll(Pageable pageable) {
        return empleadoRepositorio.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Empleado empleado) {
        empleadoRepositorio.save(empleado);

    }

    @Override
    @Transactional(readOnly = true)
    public Empleado findOne(Long id) {
        return empleadoRepositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        empleadoRepositorio.deleteById(id);
    }
}
