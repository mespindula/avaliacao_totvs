package br.com.avaliacao.contas.pagar;

import br.com.avaliacao.contas.pagar.infrastructure.secutity.AccountCredentials;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ContasPagarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContasPagarApplication.class, args);
	}

	@PostMapping("/sign-up")
	@Operation(description = "Recupera o token (header da resposta) através das credenciais enviadas")
	public void signUp(@RequestBody AccountCredentials credentials) { 	}
}
