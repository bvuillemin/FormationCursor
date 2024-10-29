package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PetValidatorTests {

    private PetValidator petValidator;
    private Pet pet;
    private Errors errors;

    @BeforeEach
    void setUp() {
        petValidator = new PetValidator();
        pet = new Pet();
        errors = new BeanPropertyBindingResult(pet, "pet");
    }

    @Test
    void testValidatePetWithValidData() {
        pet.setName("Fluffy");
        pet.setType(new PetType());
        pet.setBirthDate(LocalDate.now().minusYears(1));

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    void testValidatePetWithNoName() {
        pet.setType(new PetType());
        pet.setBirthDate(LocalDate.now().minusYears(1));

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("name")).isNotNull();
        assertThat(errors.getFieldError("name").getCode()).isEqualTo("required");
    }

    @Test
    void testValidateNewPetWithNoType() {
        pet.setName("Fluffy");
        pet.setBirthDate(LocalDate.now().minusYears(1));

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("type")).isNotNull();
        assertThat(errors.getFieldError("type").getCode()).isEqualTo("required");
    }

    @Test
    void testValidateExistingPetWithNoType() {
        pet.setId(1);
        pet.setName("Fluffy");
        pet.setBirthDate(LocalDate.now().minusYears(1));

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    void testValidatePetWithNoBirthDate() {
        pet.setName("Fluffy");
        pet.setType(new PetType());

        petValidator.validate(pet, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("birthDate")).isNotNull();
        assertThat(errors.getFieldError("birthDate").getCode()).isEqualTo("required");
    }

    @Test
    void testSupportsClass() {
        assertThat(petValidator.supports(Pet.class)).isTrue();
        assertThat(petValidator.supports(Object.class)).isFalse();
    }
}
