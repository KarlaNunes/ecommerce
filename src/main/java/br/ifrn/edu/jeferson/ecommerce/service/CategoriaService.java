package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO salvar(CategoriaRequestDTO categoriaDto) {

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDto.getNome());
        categoria.setDescricao(categoriaDto.getDescricao());
        categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    public List<CategoriaResponseDTO> lista(){
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaResponseDTO> response = new ArrayList<>();

        for (Categoria categoria : categorias) {
            CategoriaResponseDTO categoriaDto = new CategoriaResponseDTO();
            categoriaDto.setId(categoria.getId());
            categoriaDto.setNome(categoria.getNome());
            categoriaDto.setDescricao(categoria.getDescricao());
            response.add(categoriaDto);
        }

        return response;
    }

    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaDto) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if(categoria != null) {
            categoria.setNome(categoriaDto.getNome());
            categoria.setDescricao(categoriaDto.getDescricao());
            categoriaRepository.save(categoria);
            return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
        }
        return null;
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if(categoria != null) {
            return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
        }
        return null;
    }

}
