package com.softtek.service;

import com.softtek.modelo.Mascota;

public interface ExternalService {
    boolean validarVacunas(Mascota mascota);
    boolean verificarRegistroMunicipal(Mascota mascota);

}
