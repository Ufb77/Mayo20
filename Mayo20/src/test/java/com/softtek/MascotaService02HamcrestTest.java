package com.softtek;

import com.softtek.modelo.Mascota;
import com.softtek.modelo.Propietario;
import com.softtek.repository.MascotaRepository;
import com.softtek.repository.MascotaRepositoryImpl;
import com.softtek.service.ExternalService;
import com.softtek.service.ExternalServiceImpl;
import com.softtek.service.MascotasService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import  static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MascotaService02HamcrestTest {

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente(){
        // Arrange(Preparar)
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        //Act(Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        //Assert(Afirmar) : Hamcrest

        //Verificar las propiedades de la mascota registrada

        assertThat(registrada, is(notNullValue()));
        assertThat(registrada.getNombre(), is(equalTo("Garfield")));
        assertThat(registrada.getPropietario(), is(notNullValue()));
        assertThat(registrada.getPropietario().getNombre(), is(equalTo("Dany")));
        assertThat(registrada.getPropietario().getCiudad(), is(equalTo("Lima")));
        assertThat(registrada.getPropietario().getTelefono(), is(equalTo("987654321")));
        assertThat(registrada, is(sameInstance(mascota)));
        assertThat(registrada, is(notNullValue()));
    }


}
