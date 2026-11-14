package com.victor.segundaapi.controller;

import com.victor.segundaapi.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/aluno")
public class AlunoController {

    @Autowired
    AlunoService alunoService;


}
