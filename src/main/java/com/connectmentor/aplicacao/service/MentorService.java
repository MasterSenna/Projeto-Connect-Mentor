package com.connectmentor.aplicacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.connectmentor.aplicacao.entity.Mentor;
import com.connectmentor.aplicacao.repository.MentorRepository;

@Service
public class MentorService {
	
	@Autowired
	private MentorRepository mentorRepository;
	
	public void insHabMentor(Mentor mentor) {
		this.mentorRepository.save(mentor);
		// Você pode redirecionar no controlador, não aqui no serviço
	}

	public void salvarMentor(Mentor mentor) {
		mentorRepository.save(mentor);
	}

	public Mentor findById(Long idMentor) {
		Optional<Mentor> mentorOptional = mentorRepository.findById(idMentor);
		return mentorOptional.orElse(null);
	}

	@Transactional
	public void atualizarFoto(Long idMentor, byte[] fotoBytes) {
		Optional<Mentor> mentorOptional = mentorRepository.findById(idMentor);
           
		if (mentorOptional.isPresent()) {
			Mentor mentor = mentorOptional.get();
			mentor.setFoto(fotoBytes); // Supondo que você tenha um atributo 'foto' no Mentor

			mentorRepository.save(mentor);
		} else {
			// Tratar o caso em que o mentor com o ID fornecido não foi encontrado
			throw new IllegalArgumentException("Mentor com ID " + idMentor + " não encontrado.");
		}
	}
}
