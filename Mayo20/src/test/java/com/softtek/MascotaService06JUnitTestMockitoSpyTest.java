package com.softtek;

import com.softtek.modelo.Mascota;
import com.softtek.modelo.Propietario;
import com.softtek.repository.MascotaRepository;
import com.softtek.service.ExternalService;
import com.softtek.service.ExternalServiceImpl;
import com.softtek.service.MascotasService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MascotaService06JUnitTestMockitoSpyTest {

    @InjectMocks
    MascotasService mascotasService;

    @Mock
    MascotaRepository mascotaRepository;

    @Mock
    ExternalService externalService;

    @Test
    @DisplayName("Registrar mascota correctamente")

    void registrarMascotaCorrectamente(){

        //Arrange

        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();

        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any())).thenReturn(Optional.empty());
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);

        //Act

        Mascota registrada = mascotasService.registrarMascota(mascota);

        //Objeto no null

        assertNotNull(registrada, "La mascota registrada no debe ser nula");

        //Verifica que dos referencias apunten al mismo objeto

        assertSame(mascota, registrada, "La mascota registrada deberñia ser la misma que la ingresada");

        //verify(mascotaService).enviarNotificacion(mascota);
    }
}
