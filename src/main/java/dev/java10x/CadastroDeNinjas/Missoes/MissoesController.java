package dev.java10x.CadastroDeNinjas.Missoes;


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
    public ResponseEntity<List<MissoesDTO>> listarMissao(){
        List<MissoesDTO> missoesDTOS = missoesService.listarMissoes();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(missoesDTOS);
    }

    // GET -- Mandar requisicao para MOSTRAR as missoes POR ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarMissoesPorId(@PathVariable Long id){
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
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missoesDTO){
        MissoesDTO novaMissao = missoesService.criarMissao(missoesDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Missão Criado com sucesso: " + novaMissao.getNome() + " com o (id): " + novaMissao.getId());
    }

    // PUT -- Mandar requisicao para ATUALIZAR as missoes por id
    @PutMapping("/alterar/{id}")
    public ResponseEntity<String> alterarMissao(@PathVariable Long id, @RequestBody MissoesDTO missoesDTO){
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
    public ResponseEntity<String> deletarMissao(@PathVariable Long id){
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
