package com.ies;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.awt.Toolkit;
import java.util.*;

import java.util.logging.Logger;

public class AnyCityForecast {

    private static final Logger logger = Logger.getLogger(AnyCityForecast.class.getName());
    private Toolkit toolkit;
    private Timer timer;

    public AnyCityForecast() {
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 20 * 1000); // random city at 20 seconds
        timer.schedule(new StopTask(), 3 * 60 * 1000); // ends the program after 3 minutes
    }

    class RemindTask extends TimerTask {
        public void run() {
        
            requestRandomCityForecast();
            toolkit.beep();
        }

        private void requestRandomCityForecast() {

            ArrayList<Integer> cities = new ArrayList<>(); // cities' id list
            ArrayList<String> cities_name = new ArrayList<>(); // cities' name list
            int randomGlobalIdLocal; // random city id
            String local = ""; // city name

            // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.ipma.pt/open-data/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // int cityCode = 0;
            // if (args.length == 0) {
            //     logger.info( "Código de cidade inválido!");
            //     System.exit(0);
            // } else{
            //     cityCode = Integer.parseInt(args[0]);
            // }
            
            // tem acesso a api das cidades
            IpmaService service = retrofit.create(IpmaService.class);
            Call<IpmaCityForecast> callSync = service.getCityID();

            try{

                Response<IpmaCityForecast> apiResponse = callSync.execute();
                IpmaCityForecast forecast = apiResponse.body();

                // percorrer os dados do forecast para termos acesso aos ids das cidades
                for(CityForecast city : forecast.getData()){
                    cities.add(city.getGlobalIdLocal());
                    cities_name.add(city.getLocal());
                    // logger.info("" + city.getGlobalIdLocal());
                }

                // choose a random city id
                Random random = new Random();

                randomGlobalIdLocal = 0;

                if (!cities.isEmpty()) {
                    int randomIndex = random.nextInt(cities.size());
                    randomGlobalIdLocal = cities.get(randomIndex);
                    local = cities_name.get(randomIndex);
                    // logger.info("Random Id: " + randomGlobalIdLocal);
                } else {
                    logger.info("A lista de cidades está vazia.");
                }

                // create a typed interface to use the remote API (a client)
                service = retrofit.create(IpmaService.class);
                // prepare the call to remote endpoint
                callSync = service.getForecastForACity(randomGlobalIdLocal);

                try {
                    apiResponse = callSync.execute();
                    forecast = apiResponse.body();

                    if (forecast != null) {
                        logger.info( "--------------------------------------------------------------------------------------");
                        logger.info( "Os dados serão relativos à cidade " + local + "\nId: " + randomGlobalIdLocal);
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

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class StopTask extends TimerTask {
        public void run() {
            logger.info("Já passaram 3 minutos!");
            System.exit(0);
        }
    }

    // private static final int CITY_ID_AVEIRO = 1010500
    public static void  main(String[] args ) {

        new AnyCityForecast();

    }
}