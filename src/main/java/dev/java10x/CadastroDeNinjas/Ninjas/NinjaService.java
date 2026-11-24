package dev.java10x.CadastroDeNinjas.Ninjas;

import dev.java10x.CadastroDeNinjas.Missoes.MissoesModel;
import dev.java10x.CadastroDeNinjas.Missoes.MissoesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;
    private final NinjaMapper ninjaMapper;
    private final MissoesRepository missoesRepository;

    public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper, MissoesRepository missoesRepository) {
        this.ninjaRepository = ninjaRepository;
        this.ninjaMapper = ninjaMapper;
        this.missoesRepository = missoesRepository;
    }

    // Listar todos os meus ninjas
    public List<NinjaDTO> listarNinjas() {
        List<NinjaModel> ninjaModels = ninjaRepository.findAll();

        return ninjaModels.stream()
                .map(ninjaMapper::map)
                .collect(Collectors.toList());
    }

    // Listar todos os meus ninjas por ID
    public NinjaDTO listarNinjasPorId(Long id) {
        Optional<NinjaModel> ninjaPorId = ninjaRepository.findById(id);
        return  ninjaPorId.map(ninjaMapper::map).orElse(null);
    }

    // Criar ninja
    public NinjaDTO criarNinja(NinjaDTO ninjaDTO){
        NinjaModel ninjaModel = ninjaMapper.map(ninjaDTO);
        ninjaModel = ninjaRepository.save(ninjaModel);
        return ninjaMapper.map(ninjaModel);
    }

    // Delete ninja por ID - tem que ser um metodo void
    public void deletarNinjasPorId(Long id){
        ninjaRepository.deleteById(id);
    }

    // Atualizar ninja por ID
    public NinjaDTO atualizarNinjaPorid(Long id, NinjaDTO ninjaDTO){
       Optional<NinjaModel> ninjaExistente = ninjaRepository.findById(id);
       if (ninjaExistente.isPresent()){
           NinjaModel ninjaAtualizado = ninjaMapper.map(ninjaDTO);
           ninjaAtualizado.setId(id);
           NinjaModel ninjaSalvo = ninjaRepository.save(ninjaAtualizado);
           return  ninjaMapper.map(ninjaSalvo);
       }
       return null;
    }

    // Atribuir Missao para o ninja
    public void atribuirMissao(Long ninjaId, Long missaoId){
        NinjaModel ninja = ninjaRepository.findById(ninjaId)
                .orElseThrow(() -> new RuntimeException("Missão não encontrado"));

        if (missaoId == null){
            ninja.setMissoes(null);
        } else {
            MissoesModel missao = missoesRepository.findById(missaoId)
                    .orElseThrow(() -> new RuntimeException("Missão não encontrado"));
            ninja.setMissoes(missao);
        }
        ninjaRepository.save(ninja);
    }

}
