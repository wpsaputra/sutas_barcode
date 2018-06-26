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
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Torchick on 27/03/2017.
 */

public interface ApiInterface {
    @POST("hp")
    Call<String> saveHp(@Body String body);

    @POST("batch")
    Call<String> saveBatch(@Body String body);

//    https://sultradata.com/project/sutas_web_api/api.php/batch?filter=id_barcode,eq,74_01_050_016_006B&order=date_terima,desc
//    return json array
    @GET("batch")
    Call<JsonObject> getBatchByBarcode(@Query("filter") String barcode, @Query("order") String order);

//    https://sultradata.com/project/sutas_web_api/api.php/hp/{no_hp}
//    get json object
    @GET("hp/{no_hp}")
    Call<JsonObject> getHpByPhoneNumber(@Path("no_hp") String phoneNumber);

}
