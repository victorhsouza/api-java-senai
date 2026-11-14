package com.victor.segundaapi.controller;

import com.victor.segundaapi.model.ProfessorModel;
import com.victor.segundaapi.repository.ProfessorRepository;

import com.victor.segundaapi.service.ProfessorService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/professor")
@CrossOrigin(value = "*")

public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login( @RequestBody ProfessorModel professor){
        boolean sucesso = professorService.autenticar(professor.getEmail(), professor.getSenha());

        if(sucesso){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Professor autenticado com sucesso"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem","Email ou senha invalido"));

    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<ProfessorModel>> listarProfessores() {
        List<ProfessorModel> lista = professorService.listarProfessores();
        return ResponseEntity.ok().body(lista);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<?> cadastrarProfessor(@RequestBody ProfessorModel professor) {
        ProfessorModel professorSalvo = professorService.cadastrarProfessor(professor);
        if(professorSalvo == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", "Email ja cadastrado ou sem senha ou senha vazia"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(professorSalvo);
    }
//
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletarPorId( @PathVariable Long id) {
        boolean professorExiste = professorService.deletarProfessor(id);

        if (professorExiste) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Professor excluido com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Professor nao existe"));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> listarPorId( @PathVariable Long id){
        Optional<ProfessorModel> professor = professorService.listarPorId(id);

        if(professor.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(professor);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Professor nao encontrado"));
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizarProfessor( @PathVariable Long id,   @RequestBody ProfessorModel professorModel){
        ProfessorModel professor = professorService.atualizarProfessor(id,professorModel);

        if(professor != null){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Professor atualizado com sucesso!"));
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Falha ao atualizar professor. Verifique os dados da requisicao"));
        }


    }




//        @GetMapping(value= "/{id}")
//        public ResponseEntity<?> buscarPorId( @PathVariable Long id){
//            Optional<ProfessorModel> professor = professorRepository.findById(id);
//
//            if(professor.isPresent()){
//                return ResponseEntity.ok().body(professor.get());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Professor nao encontrado"));
//            }
//
//        }
//
//        @PutMapping(value = "/{id}")
//        public ResponseEntity<?> alterarPorId( @PathVariable Long id, @RequestBody ProfessorModel professorModel){
//            boolean existe = professorRepository.existsById(id);
//
//            if(!existe){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Professor nao encontrado"));
//            } else{
//                ProfessorModel professor = new ProfessorModel();
//                professor.setNome(professorModel.getNome());
//                professor.setEmail(professorModel.getEmail());
//                professor.setCpf(professorModel.getCpf());
//                professor.setIdProfessor(id);
//
//                professorRepository.save(professor);
//
//                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Professor alterado com sucesso"));
//
//            }
//        }



}


