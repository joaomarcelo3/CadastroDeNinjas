package dev.java10x.CadastroDeNinjas.Ninjas;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }


    // Adicionar ninja (CREATE)
    @PostMapping("/criar")
    public NinjaModel criarNinjas(@RequestBody NinjaModel ninjaModel){
        return ninjaService.criarNinja(ninjaModel);
    }

    // Listar todos os ninjas (CREATE)
    @GetMapping("/listar")
    public List<NinjaModel> listarNinjas(){
        return ninjaService.listarNinjas();
    }

    // Listar um ninja por id (READ)
    @GetMapping("/listar/{id}")
    public NinjaModel listarNinjasPorId(@PathVariable Long id){
        return ninjaService.listarNinjasPorId(id);
    }

    // Alterar dados do Ninja (UPDATE)
    @PutMapping("/alterar")
    public String alterarNinjaID(){
        return "Alterar Ninja por ID";
    }

    // Deletar Ninja (DELETE)
    @DeleteMapping("/deletar/{id}")
    public void deletarNinjaID(@PathVariable Long id){
        ninjaService.deletarNinjasPorId(id);
    }


}
