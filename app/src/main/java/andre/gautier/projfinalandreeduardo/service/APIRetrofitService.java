package andre.gautier.projfinalandreeduardo.service;

import andre.gautier.projfinalandreeduardo.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRetrofitService {

    @GET("{CEP}/json")
    Call<CEP> getCEP(@Path("CEP")String CEP);

}