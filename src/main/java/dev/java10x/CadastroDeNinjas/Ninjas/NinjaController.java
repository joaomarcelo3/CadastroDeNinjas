package dev.java10x.CadastroDeNinjas.Ninjas;

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
    public ResponseEntity<String> criarNinjas(@RequestBody NinjaDTO ninjaDTO){
        NinjaDTO novoNinja = ninjaService.criarNinja(ninjaDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ninja criado com sucesso: " + novoNinja.getNome() + " (ID): " + novoNinja.getId());
    }

    // Listar todos os ninjas (CREATE)
    @GetMapping("/listar")
    public ResponseEntity<List<NinjaDTO>> listarNinjas(){
        List<NinjaDTO> ninjasListados = ninjaService.listarNinjas();
        return ResponseEntity.ok(ninjasListados);
    }

    // Listar um ninja por id (READ)
    @GetMapping("/listar/{id}")
    public ResponseEntity<NinjaDTO> listarNinjasPorId(@PathVariable Long id){
        NinjaDTO ninjaListado = ninjaService.listarNinjasPorId(id);
        return ResponseEntity.ok(ninjaListado);
    }

    // Alterar dados do Ninja (UPDATE)
    @PutMapping("/alterar/{id}")
    public ResponseEntity<String> alterarNinjaID(@PathVariable Long id, @RequestBody NinjaDTO ninjaDTO){
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
    public ResponseEntity<String> deletarNinjasPorId(@PathVariable Long id) {
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
