package com.example.demo.services;

import com.example.demo.enums.Reserva;
import com.example.demo.models.Livro;
import com.example.demo.repositories.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro salvarLivro(Livro livro) {
        livro.setReserva(Reserva.PENDENTE);
        return livroRepository.save(livro);
    }

    public void deletarLivro(Long id) {
        livroRepository.deleteById(id);
    }

    public Livro atualizarLivro(Long id, Livro livroAtualizado) {
        return livroRepository.findById(id)
                .map(livro -> {
                    validarTransicaoDeEstado(livro.getReserva(), livroAtualizado.getReserva());

                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setReserva(livroAtualizado.getReserva());

                    return livroRepository.save(livro);
                })
                .orElseThrow();
    }

    private void validarTransicaoDeEstado(Reserva estadoAtual, Reserva novoEstado) {
        if (estadoAtual == Reserva.RESERVADO && novoEstado == Reserva.CANCELADO) {
            throw new IllegalStateException("Não é possível cancelar um livro já reservado.");
        }
        if (estadoAtual == Reserva.CANCELADO && novoEstado == Reserva.RESERVADO) {
            throw new IllegalStateException("Não é possível reservar um livro que já foi cancelado.");
        }
    }
}
