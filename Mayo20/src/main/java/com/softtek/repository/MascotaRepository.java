package com.softtek.repository;

import com.softtek.modelo.Mascota;

import java.util.Optional;

public interface MascotaRepository {

    Mascota guardar(Mascota mascota);
    Optional<Mascota> findById(Integer id);
    void deleteById(Integer id);
}
