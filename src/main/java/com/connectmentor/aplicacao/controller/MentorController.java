package com.connectmentor.aplicacao.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.connectmentor.aplicacao.service.MentorService;

@Controller
@RequestMapping("/")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    // Outros métodos do controlador aqui...

    // Endpoint para alterar a foto do mentor
    @PostMapping("/alterarFoto")
    public ResponseEntity<String> alterarFoto(@RequestParam Long idMentor, @RequestParam("foto") MultipartFile foto) {
        try {
            // Lógica para salvar a foto no banco de dados
            byte[] fotoBytes = foto.getBytes();
            
            // Chame o serviço para atualizar a foto do mentor
            mentorService.atualizarFoto(idMentor, fotoBytes);

            return ResponseEntity.ok("Foto alterada com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar a foto");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Mentor não encontrado ou foto não enviada");
        }
    }
}
