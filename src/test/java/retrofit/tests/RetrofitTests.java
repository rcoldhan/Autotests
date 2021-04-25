package retrofit.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pet.Category;
import pet.Pet;
import retrofit.PetStore;
import retrofit.PetStoreService;

import java.io.IOException;

public class RetrofitTests {
    private static PetStore petStore;

    @BeforeAll
    public static void beforeAll() {
        petStore = new PetStoreService().getPetStore();
    }

    @Test
    public void post200() throws IOException {
        Pet pet = new Pet();
        pet.setId(123);
        pet.setName("Pesik");
        pet.setStatus("available");
        Category c = new Category();
        c.setName("Dogs");
        pet.setCategory(c);
        Pet retrofitPet = petStore.createPet(pet).execute().body();
        int responseCode = petStore.createPet(pet).execute().code();
        Assertions.assertEquals(pet, retrofitPet);
        Assertions.assertEquals(200, responseCode);
    }

    @Test
    public void get200() throws IOException {
        Pet pet = new Pet();
        pet.setId(456);
        pet.setName("Kitty");
        pet.setStatus("available");
        Category c = new Category();
        c.setName("Cats");
        pet.setCategory(c);
        petStore.createPet(pet).execute();
        Pet retrofitPet = petStore.getPet(pet.getId()).execute().body();
        int responseCode = petStore.getPet(pet.getId()).execute().code();
        Assertions.assertEquals(pet, retrofitPet);
        Assertions.assertEquals(200, responseCode);
    }

    @Test
    public void get404() throws IOException {
        Pet pet = new Pet();
        pet.setId(456);
        pet.setName("Kitty");
        pet.setStatus("available");
        Category c = new Category();
        c.setName("Cats");
        pet.setCategory(c);
        petStore.createPet(pet).execute();
        String res = petStore.deletePet(pet.getId()).execute().message();
        System.out.println(res);
        //int responseCode = petStore.getPet(retrofitPet.getId()).execute().code();
        //Assertions.assertEquals(404, responseCode);
    }
}
