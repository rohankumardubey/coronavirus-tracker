package com.coronavirus.coronavirustracker.controllers;

import com.coronavirus.coronavirustracker.models.LocationsStats;
import com.coronavirus.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

//this class is used to link the html file present in the Resources/templates folder
//so when the server starts it get mapped
@Controller
public class HomeController {

    //linking the CoronaVirusDataService class to HomeController class to get data from the CoronaVirusDataService class
    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    //get mapping links to the directory and call the function present in it
    @GetMapping("/")
    public String home(Model model) throws IOException, InterruptedException {
        List<LocationsStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
        int totalIncrementFromPreviousDay = allStats.stream().mapToInt(stat->stat.getDiffFromPreviousDay()).sum();
        model.addAttribute("locationStats",allStats);
        model.addAttribute("totalReportedCases",totalReportedCases);
        model.addAttribute("totalPreviousReportedCases",totalIncrementFromPreviousDay);
        return "home";
    }
}
