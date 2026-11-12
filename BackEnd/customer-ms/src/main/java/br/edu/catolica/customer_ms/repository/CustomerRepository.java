package br.edu.catolica.customer_ms.repository;

import br.edu.catolica.customer_ms.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCpf(String cpf);
}
