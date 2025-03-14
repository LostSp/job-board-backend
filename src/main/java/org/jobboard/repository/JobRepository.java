
package org.jobboard.repository;

import org.jobboard.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    //  Deletes all jobs for a specific title
    @Transactional
    void deleteByTitle(String title);

    //  Counts jobs for a specific title
    long countByTitle(String title);

    //  Custom query for searching jobs (case-insensitive)
    @Query("SELECT j FROM Job j WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Job> searchJobsByTitle(String title, Pageable pageable);
}

