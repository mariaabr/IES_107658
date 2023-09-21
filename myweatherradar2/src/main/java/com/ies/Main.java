package com.ies;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // private static final int CITY_ID_AVEIRO = 
    public static void  main(String[] args ) {

        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int cityCode = 0;
        if (args.length == 0) {
            logger.info( "Código de cidade inválido!");
            System.exit(0);
        } else{
            cityCode = Integer.parseInt(args[0]);
        }

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        // prepare the call to remote endpoint
        Call<IpmaCityForecast> callSync = service.getForecastForACity(cityCode);

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                logger.info( "Temperatura máxima para dia " + forecast.getData().listIterator().next().getForecastDate() + " é " + Double.parseDouble(forecast.getData().listIterator().next().getTMax()) + "ºC");
                logger.info( "Temperatura mínima para dia " + forecast.getData().listIterator().next().getForecastDate() + " é " + Double.parseDouble(forecast.getData().listIterator().next().getTMin()) + "ºC");
                logger.info( "A probabilidade de chuva " + forecast.getData().listIterator().next().getForecastDate() + " é de " + Double.parseDouble(forecast.getData().listIterator().next().getPrecipitaProb()) + "%");
                logger.info( "O vento no dia " + forecast.getData().listIterator().next().getForecastDate() + " vem da direção " + forecast.getData().listIterator().next().getPredWindDir());
            } else {
                logger.info( "Sem resultados!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
}