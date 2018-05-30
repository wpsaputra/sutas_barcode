package bps.sultra.sutasbarcode.network;

import com.google.gson.JsonObject;

import java.util.List;

import bps.sultra.sutasbarcode.model.Batch;
import bps.sultra.sutasbarcode.model.Hp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Torchick on 27/03/2017.
 */

public interface ApiInterface {
    @POST("batch")
//    @FormUrlEncoded
    Call<Batch> savePost(@Body Batch batch);

    @POST("hp")
    @FormUrlEncoded
    Call<Hp> saveHp(@Field("no_hp") String no_hp,
                      @Field("nama") String nama,
                      @Field("id_status") long id_status);

    @POST("hp")
    Call<String> saveHp2(@Body String body);

}
