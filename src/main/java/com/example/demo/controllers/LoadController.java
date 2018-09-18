package com.example.demo.controllers;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/load")
public class LoadController {
// ------------------------------ FIELDS ------------------------------

    private JobLauncher jobLauncherAsync;

    private Job job;

    @Value("${input}")
    private String inputFilePath;

// --------------------------- CONSTRUCTORS ---------------------------

    @Autowired
    public LoadController(JobLauncher jobLauncherAsync, Job job) {
        this.jobLauncherAsync = jobLauncherAsync;
        this.job = job;
    }

// -------------------------- OTHER METHODS --------------------------

    @GetMapping(value = {"/", "{fileName}"})
    public BatchStatus load(@PathVariable Optional<String> fileName) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        String fileNameToLoad = "users";
        if (fileName.isPresent())
            fileNameToLoad = fileName.get();

        String fullFileName = inputFilePath + fileNameToLoad + ".csv";
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        maps.put("fileName", new JobParameter(fullFileName));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncherAsync.run(job, parameters);

        return jobExecution.getStatus();
    }
}
