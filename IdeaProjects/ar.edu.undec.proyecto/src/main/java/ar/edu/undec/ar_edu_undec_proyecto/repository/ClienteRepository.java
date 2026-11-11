package ar.edu.undec.ar_edu_undec_proyecto.repository;

import ar.edu.undec.ar_edu_undec_proyecto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
}
