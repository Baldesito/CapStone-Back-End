package com.example.Proggetto_final_f_stack.repository;


import com.example.Proggetto_final_f_stack.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}

