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
    @POST("hp")
    Call<String> saveHp2(@Body String body);

    @POST("batch")
    Call<String> saveBatch(@Body String body);

}
