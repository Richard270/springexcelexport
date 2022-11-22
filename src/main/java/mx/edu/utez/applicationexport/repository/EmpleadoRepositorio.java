package mx.edu.utez.applicationexport.repository;

import mx.edu.utez.applicationexport.entidades.Empleado;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmpleadoRepositorio extends PagingAndSortingRepository<Empleado, Long> {
}
