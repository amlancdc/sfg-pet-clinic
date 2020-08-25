package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeSevice;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long>
        implements OwnerService {

    private final PetTypeSevice petTypeSevice;
    private final PetService petService;

    public OwnerServiceMap(PetTypeSevice petTypeSevice, PetService petService) {
        this.petTypeSevice = petTypeSevice;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public Owner save(Owner object) {

        if(object != null){
            if(object.getPets() != null){
                object.getPets().forEach(pet-> {
                    if(pet.getPetType() != null) {
                        if (pet.getPetType().getId() == null) {
                            pet.setPetType(petTypeSevice.save(pet.getPetType()));
                        }
                    }
                    else {
                        throw new RuntimeException("PetType is required");
                    }

                   if(pet.getId() == null){
                       Pet savedPet = petService.save(pet);
                       pet.setId(savedPet.getId());
                   }
                });
            }
        }
        else {
            return null;
        }


        return super.save(object);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
