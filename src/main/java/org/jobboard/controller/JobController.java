
package org.jobboard.controller;

import org.jobboard.model.Job;
import org.jobboard.repository.JobRepository;
import org.jobboard.service.JobScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobScraperService jobScraperService;

    @Autowired
    private JobRepository jobRepository;

    //  Fetch jobs from DB with pagination (infinite scrolling)
    @GetMapping
    public Page<Job> getJobs(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Job> jobPage = jobRepository.searchJobsByTitle(title, PageRequest.of(page, size));

        //  If the job title doesn't exist, scrape and add it to the list
        if (jobPage.isEmpty()) {
            jobScraperService.scrapeJobs(title);
        }

        return jobPage;
    }

    //  Manually trigger job scraping for a specific title
    @GetMapping("/scrape")
    public String scrapeJobs(@RequestParam String title) {
        jobScraperService.scrapeJobs(title);
        return " Job scraping started for: " + title;
    }
}





