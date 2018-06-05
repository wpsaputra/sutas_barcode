package bps.sultra.sutasbarcode.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Torchick on 27/03/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "https://sultradata.com/project/sutas_web_api/api.php/";
    private static Retrofit retrofit = null;
    static Gson gson = new GsonBuilder().setLenient().create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
