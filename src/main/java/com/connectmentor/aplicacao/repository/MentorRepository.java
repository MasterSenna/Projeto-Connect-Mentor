package com.connectmentor.aplicacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.connectmentor.aplicacao.entity.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {

    Optional<Mentor> findByEmail(String email);
    Optional<Mentor> findBySenha(String senha);

    @Modifying
    @Query("UPDATE Mentor SET certificado = :certificado WHERE id = :idMentor")
    void updateCertificado(Long idMentor, byte[] certificado);

    @Modifying
    @Query("UPDATE Mentor SET foto = :foto WHERE id = :idMentor")
    void updateFoto(Long idMentor, byte[] foto);
}
