package com.coronavirus.coronavirustracker.services;

import com.coronavirus.coronavirustracker.models.LocationsStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

//adding the service annotation to run during the spring application starts
@Service
public class CoronaVirusDataService {
    private static String CORONA_VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    //list of all the LocationsStats
    private List<LocationsStats> allStats = new ArrayList<>();

    public List<LocationsStats> getAllStats() {
        return allStats;
    }

    //run the method when the spring application starts
    @PostConstruct
    //scheduler using cron
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationsStats> newStats = new ArrayList<>();

        //create a http client
        HttpClient client = HttpClient.newHttpClient();

        //create or bind the http request in the builder with uri
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CORONA_VIRUS_DATA_URL))
                .build();

        // pass the request to the created http client
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        //converting the string response to a reader object
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        //reading the csv file using apache commons csv
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);


        //reading the csv file from the url and setting it to the locationStats arraylist
        for (CSVRecord record : records) {
            LocationsStats locationStat = new LocationsStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCounty(record.get("Country/Region"));
            locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
            int latestDay=Integer.parseInt(record.get(record.size()-1));
            int previousDay=Integer.parseInt(record.get(record.size()-2));
            locationStat.setLatestTotalCases(latestDay);
            locationStat.setDiffFromPreviousDay(latestDay-previousDay);
            newStats.add(locationStat);
        }
        this.allStats=newStats;
    }


}
