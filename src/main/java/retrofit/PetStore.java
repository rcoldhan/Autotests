package retrofit;

import pet.Pet;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface PetStore {
    @POST("/v2/pet")
    Call<Pet> createPet(@Body Pet pet);

    @GET("/v2/pet/{id}")
    Call<Pet> getPet(@Path("id") int id);

    @PUT("/v2/pet")
    Call<Pet> putPet(@Body Pet pet);

    @DELETE("/v2/pet/{id}")
    Call<Pet> deletePet(@Path("id") int id);
}