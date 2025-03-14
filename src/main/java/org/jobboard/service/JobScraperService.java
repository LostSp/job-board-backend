package org.jobboard.service;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.jobboard.model.Job;
import org.jobboard.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;

import java.util.*;

@Service
public class JobScraperService {

    @Autowired
    private JobRepository jobRepository;

    private static final String LINKEDIN_BASE_URL = "https://www.linkedin.com/jobs/search/?keywords=";
    private static final Set<String> DESIGNATIONS = new HashSet<>(Arrays.asList("Software Engineer", "Data Scientist", "Backend Developer", "Product Manager"));

    //  Runs when the application starts (Initial Scraping)
    @PostConstruct
    public void initialScraping() {
        System.out.println(" Running initial job scraping...");
        checkAndScrapeJobs();
    }

    //  Runs every midnight (Updates job data)
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledJobScraping() {
        System.out.println(" Running scheduled job scraping at midnight...");
        checkAndScrapeJobs();
    }

    //  Checks and scrapes jobs if required
    @Transactional
    public void checkAndScrapeJobs() {
        for (String jobTitle : DESIGNATIONS) {
            long jobCount = jobRepository.countByTitle(jobTitle);
            if (jobCount < 100) {
                System.out.println(" " + jobTitle + " has only " + jobCount + " jobs. Scraping more...");
                scrapeJobs(jobTitle);
            }
        }
    }

    //  Scrapes jobs and stores them safely
    @Transactional
    public void scrapeJobs(String jobTitle) {
        //  Ensure the job title is permanently added
        DESIGNATIONS.add(jobTitle);

        String searchUrl = LINKEDIN_BASE_URL + jobTitle.replace(" ", "%20");

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            page.navigate(searchUrl);

            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            page.waitForSelector("div.base-card", new Page.WaitForSelectorOptions().setTimeout(10000));

            List<ElementHandle> jobElements = page.querySelectorAll("div.base-card");
            List<Job> jobList = new ArrayList<>();

            if (jobElements.isEmpty()) {
                System.err.println(" No job listings found for: " + jobTitle);
                return;
            }

            //  Scrape job details
            for (ElementHandle jobElement : jobElements) {
                try {
                    String jobTitleText = jobElement.querySelector("h3.base-search-card__title") != null ?
                            jobElement.querySelector("h3.base-search-card__title").innerText().trim() : "N/A";

                    String company = jobElement.querySelector("h4.base-search-card__subtitle") != null ?
                            jobElement.querySelector("h4.base-search-card__subtitle").innerText().trim() : "N/A";

                    String location = jobElement.querySelector("span.job-search-card__location") != null ?
                            jobElement.querySelector("span.job-search-card__location").innerText().trim() : "N/A";

                    String jobLink = jobElement.querySelector("a.base-card__full-link") != null ?
                            jobElement.querySelector("a.base-card__full-link").getAttribute("href") : "N/A";

                    if (!"N/A".equals(jobTitleText) && !"N/A".equals(company)) {
                        Job job = new Job(jobTitleText, company, location, "N/A", jobLink);
                        jobList.add(job);
                    }
                } catch (Exception e) {
                    System.err.println(" Skipped a job due to an error: " + e.getMessage());
                }
            }

            //  If jobs are successfully scraped, delete old ones before inserting
            if (!jobList.isEmpty()) {
                jobRepository.deleteByTitle(jobTitle);
                jobRepository.saveAll(jobList);
                System.out.println(" Stored " + jobList.size() + " jobs for: " + jobTitle);
            }

            browser.close();
        } catch (Exception e) {
            System.err.println(" Error scraping jobs: " + e.getMessage());
        }
    }
}
