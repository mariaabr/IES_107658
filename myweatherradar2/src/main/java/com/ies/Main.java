package com.ies;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {

    //todo: should generalize for a city passed as argument
    private static final int CITY_ID_AVEIRO = 1010500;

    public static void  main(String[] args ) {

        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        // prepare the call to remote endpoint
        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID_AVEIRO);

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var firstDay = forecast.getData().listIterator().next();

                System.out.printf( "Temperatura máxima para dia %s é %4.1f ºC %n", firstDay.getForecastDate(), Double.parseDouble(firstDay.getTMax()));
                System.out.printf( "Temperatura mínima para dia %s é %4.1f ºC %n", firstDay.getForecastDate(), Double.parseDouble(firstDay.getTMin()));
                System.out.printf( "A probabilidade de chuva %s é de %4.1f%% %n", firstDay.getForecastDate(), Double.parseDouble(firstDay.getPrecipitaProb()));
                System.out.printf( "O vento no dia %s vem da direção %s %n", firstDay.getForecastDate(), firstDay.getPredWindDir());

            } else {
                System.out.println( "No results for this request!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
