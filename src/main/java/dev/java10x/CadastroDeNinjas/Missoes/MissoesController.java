package dev.java10x.CadastroDeNinjas.Missoes;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // GET -- Mandar requisicao para MOSTRAR as missoes
    @GetMapping("/listar")
    @Operation(summary = "lista todas as missoes", description = "Essa rota lista todas as missões existentes e insere no banco de dados")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Missoes encontradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na listagem das missoes")
    })
    public ResponseEntity<List<MissoesDTO>> listarMissao(){
        List<MissoesDTO> missoesDTOS = missoesService.listarMissoes();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(missoesDTOS);
    }

    // GET -- Mandar requisicao para MOSTRAR as missoes POR ID
    @GetMapping("/listar/{id}")
    @Operation(summary = "lista missão por ID", description = "Essa rota lista uma missão por ID")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Missã encontrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na listagem da missão")
    })
    public ResponseEntity<?> listarMissoesPorId(
            @Parameter(description = "Usuário manda o id no caminho da requisição")
            @PathVariable Long id){
        MissoesDTO missoesDTOS = missoesService.listarMissoesPorId(id);
        if (missoesDTOS != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(missoesDTOS);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão com o (ID): " + id + " não encontrada!");
        }
    }

    // POST -- Mandar requisicao para CRIAR as missoes
    @PostMapping("/criar")
    @Operation(summary = "Cria uma nova missão", description = "Essa rota cria uma nova missão e insere no banco de dados")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Missão criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação da missão")
    })
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missoesDTO){
        MissoesDTO novaMissao = missoesService.criarMissao(missoesDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Missão Criado com sucesso: " + novaMissao.getNome() + " com o (id): " + novaMissao.getId());
    }

    // PUT -- Mandar requisicao para ATUALIZAR as missoes por id
    @PutMapping("/alterar/{id}")
    @Operation(summary = "Altera uma missão por ID", description = "Essa rota altera uma missão pelo seu ID")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Missão alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada, não foi possível alterar")
    })
    public ResponseEntity<String> alterarMissao(
            @Parameter(description = "Usuário manda o id no caminho da requisição")
            @PathVariable Long id,
            @Parameter(description = "Usuário manda os dados da missão a ser atualizado no corpo da requisição")
            @RequestBody MissoesDTO missoesDTO){
        MissoesDTO missaoAlterada = missoesService.alterarMissaoPorId(id, missoesDTO);
        if (missaoAlterada != null){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Missão Alterada com sucesso!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão com (ID): " + id + " não encontrada!");
        }
    }

    // DELETE -- Mandar requisicao para DELETAR as missoes
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta uma missão por ID", description = "Essa rota Deleta uma missão pelo seu ID")

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Missão Deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrado, não foi possível Deletar")
    })
    public ResponseEntity<String> deletarMissao(
            @Parameter(description ="Usuário manda o id no caminho da requisição")
            @PathVariable Long id){
        MissoesDTO missaoDeletada = missoesService.listarMissoesPorId(id);
        if (id != null){
            missoesService.deletarMissaoPorId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Missão " + missaoDeletada.getNome() + " com o (ID): "+ missaoDeletada.getId() + " deletada com sucesso!");

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID: " + id + " Não encontrado!");
        }
    }

}
