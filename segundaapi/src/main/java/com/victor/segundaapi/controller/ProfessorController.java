package com.victor.segundaapi.controller;

import com.victor.segundaapi.model.ProfessorModel;
import com.victor.segundaapi.repository.ProfessorRepository;

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
    ProfessorRepository professorRepository;

    @GetMapping(value = "/listar")
    public ResponseEntity<List<ProfessorModel>> listarProfessores() {
        List<ProfessorModel> lista = professorRepository.findAll();
        return ResponseEntity.ok().body(lista);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<ProfessorModel> cadastrarProfessor(@RequestBody ProfessorModel professor) {
        ProfessorModel professorSalvo = professorRepository.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorSalvo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletarPorId( @PathVariable Long id){
        Optional<ProfessorModel> professor = professorRepository.findById(id);

        if(professor.isPresent()){
            professorRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Professor excluido com sucesso"  ));
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Professor nao existe"  ));
        }
        }

        @GetMapping(value= "/{id}")
        public ResponseEntity<?> buscarPorId( @PathVariable Long id){
            Optional<ProfessorModel> professor = professorRepository.findById(id);

            if(professor.isPresent()){
                return ResponseEntity.ok().body(professor.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Professor nao encontrado"));
            }

        }

        @PutMapping(value = "/{id}")
        public ResponseEntity<?> alterarPorId( @PathVariable Long id, @RequestBody ProfessorModel professorModel){
            boolean existe = professorRepository.existsById(id);

            if(!existe){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Professor nao encontrado"));
            } else{
                ProfessorModel professor = new ProfessorModel();
                professor.setNome(professorModel.getNome());
                professor.setEmail(professorModel.getEmail());
                professor.setCpf(professorModel.getCpf());
                professor.setIdProfessor(id);

                professorRepository.save(professor);

                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Professor alterado com sucesso"));

            }
        }



    }


