package br.com.apiForum.repository;

import br.com.apiForum.modelo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository <Topico, Long> {


    List<Topico> findByCursoNome(String nomeCurso);

}


