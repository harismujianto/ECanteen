package com.jungkatjungkit.ecanteen.config.riwayat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String BASE_URL = "http://101.50.2.14:13000/";

    public static Retrofit retrofit = null;

    public static OrderApiService getOrderApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(OrderApiService.class);
    }
}
