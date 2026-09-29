package dev.java10x.CadastroDeNinjas.Ninjas;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class NinjaController {

    @GetMapping("/boasVindas")
    public String boasVindas(){
        return "Essa é minha primeira mensagem nessa rota";
    }

    // Adicionar ninja (CREATE)
    @PostMapping("/criar")
    public String criarNinjas(){
        return "Ninja criado";
    }

    // Procurar Ninja por ID (CREATE)
    @GetMapping("/todos")
    public String mostrarTodosNinjas(){
        return "Todos os Ninjas";
    }

    // Mostrar ninja por ID (READ)
    @GetMapping("/todosID")
    public String mostrarTodosNinjasID(){
        return "Todos os Ninjas por ID";
    }

    // Alterar dados do Ninja (UPDATE)
    @PutMapping("/alterar")
    public String alterarNinjaID(){
        return "Alterar Ninja por ID";
    }

    // Deletar Ninja (DELETE)
    @DeleteMapping("/deletarID")
    public String deletarNinjaID(){
        return "Deletar Ninja por ID";
    }


}
