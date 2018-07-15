package id.web.proditipolines.kurir.network;

import id.web.proditipolines.kurir.models.LoginResponse;
import id.web.proditipolines.kurir.models.PengirimanResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ASUS on 5/24/2018.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password);

    @GET("api/pesanan/kurir/{id}")
    Call<PengirimanResponse> getPengiriman(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/pesanan/status/{kode}")
    Call<String> updateStatusPesanan(@Path("kode") int kode,
                                     @Field("status") String status);

}
