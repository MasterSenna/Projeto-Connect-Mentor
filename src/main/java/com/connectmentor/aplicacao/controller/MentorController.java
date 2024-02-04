package com.connectmentor.aplicacao.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.connectmentor.aplicacao.entity.Mentor;
import com.connectmentor.aplicacao.service.MentorService;


@Controller	
@RequestMapping("/")
public class MentorController {
	

	@Autowired
	private MentorService service;
	
	
	//Setando view no endpoint cadastro;
	@GetMapping("CadastroMentor")
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView();
			mv.setViewName("CadastroMentor");
			return mv;	
			}
	
	
	
	//Postando cadastro do mentor no banco; 
	@PostMapping("/inserirMentores")
	    public String salvarMentor(@ModelAttribute Mentor mentor) {
		 service.salvarMentor(mentor);
		 /*
		 String hashsenha = PasswordUtil.encoder(mentor.getSenha());
		 mentor.setSenha(hashsenha);
		 */
	        //return "sucesso";
		 System.out.println("Mentor salvo: " + mentor);

		    return "redirect:/index";
	    }
	 
	
	//Setando view no endpoint index	
	 @GetMapping("index")
		public ModelAndView index() {
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("index");
			return mv;		
	}
	 
	//Método para logar o mentor
	 @GetMapping("/logarMentor")
	 public String logar(@RequestParam("email") String email, @RequestParam("senha") String senha) {
		 
		 Mentor mentor =  service.buscarPorEmail(email);
		 
		 System.out.println("Senha passada no parâmetro: "+senha);
			System.out.println("Senha do mentor: "+mentor.getSenha());
		
			if(!mentor.getSenha().equals(senha)) {
				return "redirect:/index";
			
			
		}else {
			System.out.println(mentor.getSenha());
			System.out.println("senha param: "+senha);
			return "redirect:/perfilMentor1";
		}	 
	 }
	 
		//Setando view no endpoint login
		@GetMapping("login")
		public ModelAndView login() {
			ModelAndView mv = new ModelAndView();
				mv.setViewName("login");
				return mv;		
			}
		
		
		// Mude o mapeamento do método para "/encontrarMentor"
	    @GetMapping("encontrarMentor")
	    public ModelAndView encontrarMentor() {
	        ModelAndView mv = new ModelAndView();
	        mv.setViewName("encontrarMentor");
	        return mv;	
	    }
		
		
		//Setando view no endpoint perfil mentor 
		@GetMapping("perfilMentor1")
		public ModelAndView perfilMentor1() {
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("perfilMentor1");
			return mv;	
		
		}
		
		@GetMapping("calendario")
		public ModelAndView calendario() {
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("calendario");
			return mv;	
		
	}
		
		
		
		@GetMapping("/visualizarCertificado/{idMentor}")
	    public ResponseEntity<byte[]> visualizarCertificado(@PathVariable Long idMentor) {
	        byte[] certificadoBytes = service.obterCertificado(idMentor);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType("application/pdf"));
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=certificado.pdf");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentLength(certificadoBytes.length)
	                .body(certificadoBytes);
	    }
	    
	    @GetMapping("/visualizarCertificadoPagina/{idMentor}")
	    public ModelAndView visualizarCertificadoPagina(@PathVariable Long idMentor) {
	        ModelAndView mv = new ModelAndView();
	        mv.setViewName("certificado");
	        mv.addObject("idMentor", idMentor);
	        return mv;
	    }

		
		
		
	    @PostMapping("/alterarFoto")
	    public ModelAndView alterarFoto(@RequestParam Long idMentor, @RequestParam("foto") MultipartFile foto) {
	        ModelAndView mv = new ModelAndView();
	        try {
	            byte[] fotoBytes = foto.getBytes();
	            service.atualizarFoto(idMentor, fotoBytes);
	            mv.setViewName("redirect:/perfilMentor1");
	            mv.addObject("idMentor", idMentor);
	            mv.addObject("mensagem", "Foto alterada com sucesso");
	        } catch (IOException e) {
	            e.printStackTrace();
	            mv.setViewName("redirect:/perfilMentor1");
	            mv.addObject("idMentor", idMentor);
	            mv.addObject("erro", "Erro ao salvar a foto");
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	            mv.setViewName("redirect:/perfilMentor1");
	            mv.addObject("idMentor", idMentor);
	            mv.addObject("erro", "Mentor não encontrado ou foto não enviada");
	        }
	        return mv;
	    }

	        
	        
	        
		
	
}
