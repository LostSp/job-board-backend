package org.jobboard.model;


import jakarta.persistence.*;


@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    private String title;
    private String company;
    private String location;
    private String experience;
    @Column(length = 500)
    private String applyLink;

    public Job() {}
    public Job(String title, String company, String location, String experience, String applyLink) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.experience = experience;
        this.applyLink = applyLink;
    }
    public Job(String title, String company, String location, String applyLink) {
        this.title = title;
        this.company = company;
        this.location = location;

        this.applyLink = applyLink;
    }
    public Job(String title, String company, String location) {
        this.title = title;
        this.company = company;
        this.location = location;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }




}
