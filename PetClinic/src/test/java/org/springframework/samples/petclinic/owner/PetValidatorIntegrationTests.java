package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PetValidatorIntegrationTests {

    @Autowired
    private PetValidator petValidator;

    private Pet pet;
    private Errors errors;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        errors = new BeanPropertyBindingResult(pet, "pet");
    }

    @Test
    void testValidPet() {
        pet.setName("Fluffy");
        pet.setType(new PetType());
        pet.setBirthDate(LocalDate.now().minusYears(1));

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    void testInvalidName() {
        pet.setName("");
        pet.setType(new PetType());
        pet.setBirthDate(LocalDate.now().minusYears(1));

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("name")).isNotNull();
        assertThat(errors.getFieldError("name").getCode()).isEqualTo("required");
    }

    @Test
    void testInvalidType() {
        pet.setName("Fluffy");
        pet.setBirthDate(LocalDate.now().minusYears(1));

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("type")).isNotNull();
        assertThat(errors.getFieldError("type").getCode()).isEqualTo("required");
    }

    @Test
    void testInvalidBirthDate() {
        pet.setName("Fluffy");
        pet.setType(new PetType());

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("birthDate")).isNotNull();
        assertThat(errors.getFieldError("birthDate").getCode()).isEqualTo("required");
    }

    @Test
    void testAllFieldsInvalid() {
        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldErrorCount()).isEqualTo(3);
        assertThat(errors.getFieldError("name")).isNotNull();
        assertThat(errors.getFieldError("type")).isNotNull();
        assertThat(errors.getFieldError("birthDate")).isNotNull();
    }
}
