/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller handling pet-related operations for a specific owner.
 * This controller manages the creation, retrieval, and update of pets.
 *
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
class PetController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	private final OwnerRepository owners;

	/**
	 * Constructs a new PetController with the given OwnerRepository.
	 *
	 * @param owners The repository for owner-related operations
	 */
	public PetController(OwnerRepository owners) {
		this.owners = owners;
	}

	/**
	 * Provides a collection of pet types for form population.
	 *
	 * @return A collection of PetType objects
	 */
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.owners.findPetTypes();
	}

	/**
	 * Finds and provides the owner based on the given owner ID.
	 *
	 * @param ownerId The ID of the owner to find
	 * @return The found Owner object
	 * @throws IllegalArgumentException if the owner is not found
	 */
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		Owner owner = this.owners.findById(ownerId);
		if (owner == null) {
			throw new IllegalArgumentException("Owner ID not found: " + ownerId);
		}
		return owner;
	}

	/**
	 * Finds and provides the pet based on the given owner ID and pet ID.
	 *
	 * @param ownerId The ID of the owner
	 * @param petId The ID of the pet (optional)
	 * @return The found Pet object or a new Pet if petId is null
	 * @throws IllegalArgumentException if the owner is not found
	 */
	@ModelAttribute("pet")
	public Pet findPet(@PathVariable("ownerId") int ownerId,
			@PathVariable(name = "petId", required = false) Integer petId) {
		if (petId == null) {
			return new Pet();
		}

		Owner owner = this.owners.findById(ownerId);
		if (owner == null) {
			throw new IllegalArgumentException("Owner ID not found: " + ownerId);
		}
		return owner.getPet(petId);
	}

	/**
	 * Initializes the binder for the owner object.
	 *
	 * @param dataBinder The WebDataBinder to be initialized
	 */
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Initializes the binder for the pet object and sets the validator.
	 *
	 * @param dataBinder The WebDataBinder to be initialized
	 */
	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	/**
	 * Handles GET requests for creating a new pet.
	 *
	 * @param owner The owner object
	 * @param model The model map for adding attributes
	 * @return The view name for creating or updating a pet
	 */
	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 * Handles POST requests for creating a new pet.
	 *
	 * @param owner The owner object
	 * @param pet The pet object to be created
	 * @param result The BindingResult for validation errors
	 * @param model The model map for adding attributes
	 * @param redirectAttributes RedirectAttributes for flash attributes
	 * @return The view name or redirect URL
	 */
	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		if (StringUtils.hasText(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}

		owner.addPet(pet);
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		this.owners.save(owner);
		redirectAttributes.addFlashAttribute("message", "New Pet has been Added");
		return "redirect:/owners/{ownerId}";
	}

	/**
	 * Handles GET requests for editing an existing pet.
	 *
	 * @param owner The owner object
	 * @param petId The ID of the pet to be edited
	 * @param model The model map for adding attributes
	 * @param redirectAttributes RedirectAttributes for flash attributes
	 * @return The view name for creating or updating a pet
	 */
	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(Owner owner, @PathVariable("petId") int petId, ModelMap model,
			RedirectAttributes redirectAttributes) {
		Pet pet = owner.getPet(petId);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 * Handles POST requests for updating an existing pet.
	 *
	 * @param pet The pet object to be updated
	 * @param result The BindingResult for validation errors
	 * @param owner The owner object
	 * @param model The model map for adding attributes
	 * @param redirectAttributes RedirectAttributes for flash attributes
	 * @return The view name or redirect URL
	 */
	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model,
			RedirectAttributes redirectAttributes) {

		String petName = pet.getName();

		// checking if the pet name already exist for the owner
		if (StringUtils.hasText(petName)) {
			Pet existingPet = owner.getPet(petName.toLowerCase(), false);
			if (existingPet != null && existingPet.getId() != pet.getId()) {
				result.rejectValue("name", "duplicate", "already exists");
			}
		}

		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}

		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		owner.addPet(pet);
		this.owners.save(owner);
		redirectAttributes.addFlashAttribute("message", "Pet details has been edited");
		return "redirect:/owners/{ownerId}";
	}

}
