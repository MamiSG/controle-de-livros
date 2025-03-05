package com.example.demo.models;

import jakarta.persistence.*;
import com.example.demo.enums.Reserva;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private Reserva reserva;

    public Livro() {}

    public Livro(String titulo, Reserva reserva) {
        this.titulo = titulo;
        this.reserva = reserva;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
