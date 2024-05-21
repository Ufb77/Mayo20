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

import static org.junit.jupiter.api.Assertions.*;

public class MascotaService01JUnitTest {

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

        //Assert(Afirmar) : JUnit

        //Verifica que el objeto no es null.
        assertNotNull(registrada, "La mascota no debe ser null");
        assertNotNull(registrada.getNombre(), "El nombre de la mascota no debe ser null");
        assertNotNull(registrada.getPropietario(), "El propietario no deber ser null");

        //Verifica que el valor esperado coincida con el actual.

        assertEquals("Garfield", registrada.getNombre(), "El nombre debe ser Garfield");

        //Confirma que el propietario de la mascota registrada es el mismo que se proporcionó

        assertSame(propietario, registrada.getPropietario(), "El propietario debe ser el mismo que el ingresado");

        //Comprueba los detalles del propietario para garantizar la precisión de los datos.

        assertEquals("Dany", registrada.getPropietario().getNombre(), "El nombre del propietario debería ser Dany");
        assertEquals("Lima", registrada.getPropietario().getCiudad(), "La ciudad del propietario debería ser Lima");
        assertEquals("987654321", registrada.getPropietario().getTelefono(), "El teléfono del propietario debería ser 987654321");


        //Comprobar con más aserciones de Junit 5

        assertTrue(registrada.getId()>0, "Id debe ser positivo");

        //Verificación adificional agrupando varias aserciones. Todas deben pasar o el test fallará

        assertAll( "Propiedades de la mascota",
                () -> assertEquals( "Garfield", registrada.getNombre(), "El nombre debería coincidir."),
                () -> assertNotNull(registrada.getPropietario(), "El propietario no debe ser null."),
                () -> assertEquals( "Dany", registrada.getPropietario().getNombre(),  "El nombre del propietario debe ser 'Dany'"),
                () -> assertEquals("Lima", registrada.getPropietario().getCiudad(),  "La ciudad del propietario debe ser 'Lima'"),
                () -> assertEquals( "987654321", registrada.getPropietario().getTelefono(),  "El teléfono del propietario debe ser '987654321'")
        );

        //Comprueba que las siguientes operaciones no lanzan excepciones, lo cual es útil para confirmar qué propiedades esenciales están presentes y son accesibles

        assertDoesNotThrow(()-> registrada.getNombre(), "Obtener el nombre de la mascota no debe generar excepción");
        assertDoesNotThrow(()-> registrada.getPropietario().getCiudad(), "Obtener la ciudad del propietario no debe generar excepción");
        assertDoesNotThrow(()-> registrada.getPropietario().getTelefono(), "Obtener el  telefono no debe generar excepción");


    }


}
