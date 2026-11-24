package dev.java10x.CadastroDeNinjas.Ninjas;

import dev.java10x.CadastroDeNinjas.Missoes.MissoesDTO;
import dev.java10x.CadastroDeNinjas.Missoes.MissoesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ninjas/ui")
public class NinjaControllerUi {

    private final NinjaService ninjaService;
    private final MissoesService missoesService;

    public NinjaControllerUi(NinjaService ninjaService, MissoesService missoesService) {
        this.ninjaService = ninjaService;
        this.missoesService = missoesService;
    }


    @GetMapping("/listar")
    public String listarNinjas(Model model){
        List<NinjaDTO> ninjas = ninjaService.listarNinjas();
        model.addAttribute("ninjas", ninjas);
        return "ninjas/listarNinjas"; // tem que retornar o nome da pagina que renderiza
    }

    @GetMapping("/deletar/{id}")
    public String deletarNinjasPorId(@PathVariable Long id) {
        ninjaService.deletarNinjasPorId(id);
        return "redirect:/ninjas/ui/listar";
    }

    @GetMapping("/alterar/{id}")
    public String mostrarFormularioAlterar(@PathVariable Long id, Model model) {
        NinjaDTO ninja = ninjaService.listarNinjasPorId(id);
        if (ninja == null) {
            return "redirect:/ninjas/ui/listar";
        }
        model.addAttribute("ninja", ninja);
        return "ninjas/alterarNinja";
    }

    @PostMapping("/alterar/{id}")
    public String alterarNinja(@PathVariable Long id, @ModelAttribute NinjaDTO ninja,
                               RedirectAttributes redirectAttributes) {
        ninjaService.atualizarNinjaPorid(id, ninja);
        redirectAttributes.addFlashAttribute("mensagem", "Ninja alterado com sucesso!");
        return "redirect:/ninjas/ui/listar";
    }

    @GetMapping("/listar/{id}")
    public String listarNinjasPorId(@PathVariable Long id, Model model){
        NinjaDTO ninja = ninjaService.listarNinjasPorId(id);
        if (ninja != null) {
            model.addAttribute("ninja", ninja);
            return "ninjas/detalhesNinja";
        }
        return "redirect:/ninjas/ui/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionarNinja(Model model){
        model.addAttribute("ninja", new NinjaDTO());
        return "ninjas/adicionarNinja";
    }

    @PostMapping("/salvar")
    public String salvarNinja(@ModelAttribute NinjaDTO ninja, RedirectAttributes redirectAttributes){
        ninjaService.criarNinja(ninja);
        redirectAttributes.addFlashAttribute("mensagem", "Ninja cadastrado com sucesso!");
        return "redirect:/ninjas/ui/listar";
    }

    @GetMapping("/atribuir-missao/{id}")
    public String atribuirMissao(@PathVariable Long id, Model model){
        NinjaDTO ninja = ninjaService.listarNinjasPorId(id);
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        model.addAttribute("ninja", ninjaService.listarNinjasPorId(id));
        model.addAttribute("missoes", missoesService.listarMissoes());
        return "ninjas/atribuirMissao";
    }

    @PostMapping("/atribuir-missao")
    public String atribuirMissao(@RequestParam Long ninjaId, @RequestParam(required = false)  Long missaoId){
        ninjaService.atribuirMissao(ninjaId, missaoId);
        return "redirect:/ninjas/ui/listar";
    }


}
