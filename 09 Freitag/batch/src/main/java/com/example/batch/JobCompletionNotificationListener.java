package com.example.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private JdbcTemplate jdbcTemplate;

    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            List<Person> results = jdbcTemplate
                    .query("SELECT first_name, last_name FROM people",
                            (rs, row) ->
                                    new Person(
                                            rs.getString(1),
                                            rs.getString(2)));
            for (Person person : results) {
                log.info("Found <" + person + "> in the database.");
            }
        }
    }
}
