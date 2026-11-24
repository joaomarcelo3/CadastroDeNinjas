package dev.java10x.CadastroDeNinjas.Ninjas;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private final NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    // Adicionar ninja (CREATE)
    @PostMapping("/criar")
    @Operation(summary = "Cria um novo ninja", description = "Essa rota cria um novo ninja e insere no banco de dados")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação do ninja")
    })

    public ResponseEntity<String> criarNinjas(@RequestBody NinjaDTO ninjaDTO){
        NinjaDTO novoNinja = ninjaService.criarNinja(ninjaDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ninja criado com sucesso: " + novoNinja.getNome() + " (ID): " + novoNinja.getId());
    }

    // Listar todos os ninjas (CREATE)
    @GetMapping("/listar")
    @Operation(summary = "Lista todos os ninja", description = "Essa rota lista TODOS os ninjas")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ninja encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado")
    })
    public ResponseEntity<List<NinjaDTO>> listarNinjas(){
        List<NinjaDTO> ninjasListados = ninjaService.listarNinjas();
        return ResponseEntity.ok(ninjasListados);
    }

    // Listar um ninja por id (READ)
    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista o ninja por ID", description = "Essa rota lista um ninja pelo seu ID")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ninja encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado")
    })
    public ResponseEntity<NinjaDTO> listarNinjasPorId(@PathVariable Long id){
        NinjaDTO ninjaListado = ninjaService.listarNinjasPorId(id);
        return ResponseEntity.ok(ninjaListado);
    }

    // Alterar dados do Ninja (UPDATE)
    @PutMapping("/alterar/{id}")
    @Operation(summary = "Altera o ninja por ID", description = "Essa rota altera um ninja pelo seu ID")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ninja alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado, não foi possível alterar")
    })
    public ResponseEntity<String> alterarNinjaID(
            @Parameter(description = "Usuário manda o id no caminho da requisição")
            @PathVariable Long id,

            @Parameter(description = "Usuário manda os dados do ninja a ser atualizado no corpo da requisição")
            @RequestBody NinjaDTO ninjaDTO){

            NinjaDTO ninjaAtualizado = ninjaService.atualizarNinjaPorid(id, ninjaDTO);
        if (ninjaAtualizado != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Ninja " + ninjaAtualizado.getNome() + "Atualizado com sucesso");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ninja não encontrado");
        }
    }

    // Deletar Ninja (DELETE)
    @DeleteMapping ("/deletar/{id}")
    @Operation(summary = "Deleta o ninja por ID", description = "Essa rota Deleta um ninja pelo seu ID")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ninja Deleta com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado, não foi possível Deletar")
    })
    public ResponseEntity<String> deletarNinjasPorId(
            @Parameter(description ="Usuário manda o id no caminho da requisição" )
            @PathVariable Long id) {
        NinjaDTO ninjaDeletado = ninjaService.listarNinjasPorId(id);
        if (ninjaDeletado != null) {
            ninjaService.deletarNinjasPorId(id);
            return ResponseEntity.ok("Ninja: " + ninjaDeletado.getNome() + ", (id): " + id + ", Deletado com Sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("O Ninja com o (ID): " + id + ", não Encontrado!");
        }
    }
}
