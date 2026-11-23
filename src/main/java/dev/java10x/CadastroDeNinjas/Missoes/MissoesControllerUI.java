package dev.java10x.CadastroDeNinjas.Missoes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("missoes/ui")
public class MissoesControllerUI {

    private final MissoesService missoesService;

    public MissoesControllerUI(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @GetMapping("/listar")
    public String listarMissao(Model model){
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        model.addAttribute("missoes", missoes);
        return "missoes/listarMissoes";
    }

    @GetMapping("/listar/{id}")
    public String listarMissoesPorId(@PathVariable Long id, Model model){
        MissoesDTO missao = missoesService.listarMissoesPorId(id);
        if (missao != null) {
            model.addAttribute("missao", missao);
            return "missoes/detalhesMissoes";
        }
            return "redirect:/missoes/ui/listar";
    }

    @PostMapping("/salvar")
    public String criarMissao(@ModelAttribute MissoesDTO missoesDTO, RedirectAttributes redirectAttributes){
        missoesService.criarMissao(missoesDTO);
        redirectAttributes.addFlashAttribute("mensagem", "Missão criada com sucesso!");
        return "redirect:/missoes/ui/listar";
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionarMissao(Model model){
        model.addAttribute("missao", new MissoesDTO());
        return "missoes/adicionarMissoes";
    }

    @GetMapping("/alterar/{id}")
    public String mostrarFormularioAlterar(@PathVariable Long id, Model model) {
        MissoesDTO missao = missoesService.listarMissoesPorId(id);
        if (missao == null) {
            return "redirect:/missoes/ui/listar";
        }
        model.addAttribute("missao", missao);
        return "missoes/alterarMissoes";
    }

    @PostMapping("/alterar/{id}")
    public String alterarMissao(@PathVariable Long id, @ModelAttribute MissoesDTO missoesDTO, RedirectAttributes redirectAttributes) {
        missoesService.alterarMissaoPorId(id, missoesDTO);
        redirectAttributes.addFlashAttribute("mensagem", "Missão alterada com sucesso!");
        return "redirect:/missoes/ui/listar";
    }

    @GetMapping("/deletar/{id}")
    public String deletarMissao(@PathVariable Long id){
        missoesService.deletarMissaoPorId(id);
        return "redirect:/missoes/ui/listar";
    }
}
