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

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

public class MascotaService03AssertJTest {

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

        //Assert(Afirmar) : ¿Jtest?

        //Verificar las propiedades de la mascota registrada

        assertThat(registrada).isNotNull();
        assertThat(registrada.getNombre()).isEqualTo("Garfield");
        assertThat(registrada.getPropietario()).isNotNull();
        assertThat(registrada.getPropietario().getNombre()).isEqualTo("Dany");
        assertThat(registrada.getPropietario().getCiudad()).isEqualTo("Lima");
        assertThat(registrada.getPropietario().getTelefono()).isEqualTo("987654321");
        assertThat(registrada).isSameAs(mascota);
        assertThat(registrada.getId()).isPositive();
    }

    @Test
    @DisplayName("El nombre de la mascota no puede estar vacío")
    void testNombreMascotaNoPuedeEstarVacio() {
        // Arrange

        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);

        Mascota mascota = new Mascota();
        mascota.setNombre(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("El nombre de la mascota no puede estar vacío.");
    }

    @Test
    @DisplayName("El nombre de la mascota no puede estar vacío")
    void testNombreMascotaNoPuedeEstarEmpty() {
        // Arrange

        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);

        Mascota mascota = new Mascota();
        mascota.setNombre("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("El nombre de la mascota no puede estar vacío.");
    }

    @Test
    @DisplayName("La mascota debe tener un propietario")
    void testMascotaDebeTenerPropietario() {
        // Arrange

        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("La mascota debe tener un propietario.");
    }

    @Test
    @DisplayName("El propietario debe tener un teléfono registrado")
    void testPropietarioDebeTenerTelefono() {
        // Arrange
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);
        Propietario propietario = new Propietario("Dany", "Lima", null);
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("El propietario debe tener un teléfono registrado.");
    }

    @Test
    @DisplayName("El propietario debe tener un teléfono registrado")
    void testPropietarioDebeTenerTelefonoNoVacio() {
        // Arrange
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);
        Propietario propietario = new Propietario("Dany", "Lima", "");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("El propietario debe tener un teléfono registrado.");
    }

    @Test
    @DisplayName("La mascota debe tener todas las vacunas al día")
    void testMascotaDebeTenerVacunasAlDia() {
        // Arrange
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService;
        MascotasService mascotaService;
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        externalService = new ExternalService() {
            @Override
            public boolean validarVacunas(Mascota mascota) {
                return false;
            }

            @Override
            public boolean verificarRegistroMunicipal(Mascota mascota) {
                return true;
            }
        };
        mascotaService = new MascotasService(mascotaRepository, externalService);

        // Act & Assert

        MascotasService finalMascotaService = mascotaService;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> finalMascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("La mascota no tiene todas las vacunas al día.");
    }

    @Test
    @DisplayName("La mascota debe estar registrada en el municipio")
    void testMascotaDebeEstarRegistradaEnMunicipio() {
        // Arrange
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService;
        MascotasService mascotaService;
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        externalService = new ExternalService() {
            @Override
            public boolean validarVacunas(Mascota mascota) {
                return true;
            }

            @Override
            public boolean verificarRegistroMunicipal(Mascota mascota) {
                return false;
            }
        };
        mascotaService = new MascotasService(mascotaRepository, externalService);

        // Act & Assert
        MascotasService finalMascotaService = mascotaService;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> finalMascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("La mascota no está registrada en el municipio.");
    }

    @Test
    @DisplayName("La mascota no debe estar ya registrada")
    void testMascotaNoDebeEstarRegistrada() {
        // Arrange
        MascotaRepository mascotaRepository;
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService;
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        mascotaRepository = new MascotaRepositoryImpl() {
            @Override
            public Optional<Mascota> findById(Integer id) {
                return Optional.of(mascota);
            }
        };
        mascotaService = new MascotasService(mascotaRepository, externalService);

        // Act & Assert
        MascotasService finalMascotaService = mascotaService;
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> finalMascotaService.registrarMascota(mascota));
        assertThat(exception).hasMessage("Esta mascota ya está registrada.");
    }

    @Test
    @DisplayName("Buscar mascota por ID existente")
    void testBuscarMascotaPorIdExistente() {
        // Arrange

        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);
        Mascota mascota = new Mascota();
        mascota.setId(1);
        mascotaRepository.guardar(mascota);

        // Act
        Optional<Mascota> resultado = mascotaService.buscarMascotaPorId(1);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Eliminar mascota por ID existente")
    void testEliminarMascotaPorIdExistente() {
        // Arrange
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotasService mascotaService = new MascotasService(mascotaRepository, externalService);
        Mascota mascota = new Mascota();
        mascota.setId(1);
        mascotaRepository.guardar(mascota);

        // Act
        mascotaService.eliminarMascotaPorId(1);

        // Assert
        assertThat(mascotaRepository.findById(1)).isNotPresent();
    }


}
