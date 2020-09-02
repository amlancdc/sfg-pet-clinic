package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;
    Set<Owner> ownersData = new HashSet<>();

    String lastName = "Chowdhury";
    Owner owner1;
    Owner owner2;


    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetServiceMap(new PetTypeMapService()));

        owner1 = Owner.builder().id(1L).lastName(lastName).build();
        ownersData.add(owner1);
        owner2 = Owner.builder().id(2L).build();
        ownersData.add(owner2);

        ownerServiceMap.save(owner1);
        ownerServiceMap.save(owner2);
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(ownersData, owners);
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(1L);

        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(1, owners.size());

        ownerServiceMap.deleteById(2L);

        owners = ownerServiceMap.findAll();
        assertEquals(0, owners.size());

    }

    @Test
    void delete() {
        ownerServiceMap.delete(owner1);

        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(1, owners.size());

        ownerServiceMap.delete(owner2);

        owners = ownerServiceMap.findAll();
        assertEquals(0, owners.size());
    }

    @Test
    void saveOwnerNull() {
        Owner owner = ownerServiceMap.save(null);
        assertNull(owner);
    }

    @Test
    void saveOwnerNullId() {
        Owner ownerData = Owner.builder().id(null).build();
        Owner ownerSaved = ownerServiceMap.save(ownerData);

        assertNotNull(ownerSaved.getId());
        assertNotNull(ownerSaved.getPets());
    }

    @Test
    void saveOwnerNullPets() {
        Owner ownerData = Owner.builder().id(1L).build();
        ownerData.setPets(null);
        Owner ownerSaved = ownerServiceMap.save(ownerData);

        assertNotNull(ownerSaved.getId());
        assertNotNull(ownerSaved.getPets());
    }

    @Test
    void saveOwnerNullPetType() {
        Owner ownerData = Owner.builder().id(3L).build();
        Pet petData = Pet.builder().id(1L).petType(null).build();
        Set<Pet> petsData = new HashSet<>();
        petsData.add(petData);
        ownerData.setPets(petsData);

        assertThrows(RuntimeException.class, () -> {ownerServiceMap.save(ownerData);});
    }

    @Test
    void saveOwnerNotNullPetType() {
        Owner ownerData = Owner.builder().id(3L).build();
        Pet petData = Pet.builder().id(1L).petType(PetType.builder().id(1L).build()).build();
        Set<Pet> petsData = new HashSet<>();
        petsData.add(petData);
        ownerData.setPets(petsData);

        Owner ownerSaved = ownerServiceMap.save(ownerData);

        assertNotNull(ownerSaved.getId());
        assertNotNull(ownerSaved.getPets());
        assertNotNull(ownerSaved.getPets().stream().findFirst());
        assertNotNull(ownerSaved.getPets().stream().findFirst().get().getId());
    }


    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(1L);
        assertEquals(owner1, owner);
    }

    @Test
    void findByLastName() {
        Owner owner = ownerServiceMap.findByLastName(lastName);
        assertNotNull(owner);
    }

    @Test
    void findByLastNameNull() {
        Owner owner = ownerServiceMap.findByLastName("Amlan");
        assertNull(owner);
    }
}