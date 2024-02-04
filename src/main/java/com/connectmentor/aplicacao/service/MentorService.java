package com.connectmentor.aplicacao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.connectmentor.aplicacao.entity.Mentor;
import com.connectmentor.aplicacao.repository.MentorRepository;

@Service
public class MentorService {
	
	@Autowired
	private MentorRepository mentorRepository;
	

    public String salvarMentor(Mentor mentor) {
        mentorRepository.save(mentor);
        return "redirect:index";
    }

	
	public Mentor buscarIdMentor(Long idMentor) {
		Optional<Mentor> mentor = mentorRepository.findById(idMentor);
		return mentor.orElse(null);
	}
	
	public Mentor buscarPorEmail(String email) {
	    Optional<Mentor> mentor = mentorRepository.findByEmail(email);
	    return mentor.orElse(null);
	}
	
	public Mentor buscarPorSenha(String senha) {
	    Optional<Mentor> mentor = mentorRepository.findBySenha(senha);
	    return mentor.orElse(null);
	}

	@Transactional
    public void atualizarFoto(Long idMentor, byte[] fotoBytes) {
        // Recupera o mentor do banco de dados
        Mentor mentor = mentorRepository.findById(idMentor)
                .orElseThrow(() -> new IllegalArgumentException("Mentor não encontrado"));

        // Atualiza a foto do mentor
        mentor.setFoto(fotoBytes);

        // Salva as alterações no banco de dados
        mentorRepository.save(mentor);
    }
	
	public void salvarCertificado(Long idMentor, byte[] certificado) {
        mentorRepository.updateCertificado(idMentor, certificado);
    }
	

    public byte[] obterCertificado(Long idMentor) {
        Mentor mentor = mentorRepository.findById(idMentor)
                .orElseThrow(() -> new IllegalArgumentException("Mentor não encontrado"));

        return mentor.getCertificado();
    }
	
}
