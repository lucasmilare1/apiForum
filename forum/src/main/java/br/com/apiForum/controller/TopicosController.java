package br.com.apiForum.controller;

import br.com.apiForum.Dto.DetalhesDoTopicoDto;
import br.com.apiForum.Dto.TopicoDto;

import br.com.apiForum.controller.form.AttTopicoForm;
import br.com.apiForum.controller.form.TopicoForm;
import br.com.apiForum.modelo.Curso;
import br.com.apiForum.modelo.Topico;
import br.com.apiForum.repository.CursoRepository;
import br.com.apiForum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
//Controller responde as requisições que começam com "/topicos"
@RequestMapping("/topicos")
public class TopicosController {

    //injeção de dependencias
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos;
            topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        } else{List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }

   @PostMapping  //@RequestBody indica ao Spring que os parametros enviados no corpo da requisição devem ser atribuidos ao parâmetro do método.
    public ResponseEntity<TopicoDto> cadastar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
    Topico topico = form.converter(cursoRepository); //@valid indica ao Spring para executar as validações do bean Validation no parametro do metodo.
    topicoRepository.save(topico);
    //é passado o caminho do recurso, {id} é interpretado como um parametro dinamico
    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
    return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")     //@PathVariable Diz que é uma variavel do path, da URL.
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id){
    Optional <Topico> topico = topicoRepository.findById(id);
    if(topico.isPresent()){
        return ResponseEntity.ok( new DetalhesDoTopicoDto(topico.get()));
       }
      return ResponseEntity.notFound().build();
    }
    //atualizar um topico
    @PutMapping("/{id}")
    @Transactional// avisa o Spring para comitar a transação no final deste metodo. efetua o commit automatico da transação, caso nao ocorra uma exception
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,@RequestBody @Valid AttTopicoForm form){ Optional <Topico> topico = topicoRepository.findById(id);
        Optional <Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()) {Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));

        }
    return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?>remvoer(@PathVariable Long id){
        Optional <Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()) {
         topicoRepository.deleteById(id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();

    }
}
