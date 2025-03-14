package org.jobboard.controller;



import org.jobboard.service.JobScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scrape")
public class JobScraperController {

    @Autowired
    private JobScraperService jobScraperService;

   /* @GetMapping
    public String scrapeJobs() {
        jobScraperService.scrapeJobs();
        return "Job scraping completed!";
    }*/
}
