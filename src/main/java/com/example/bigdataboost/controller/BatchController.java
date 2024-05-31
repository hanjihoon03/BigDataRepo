package com.example.bigdataboost.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchController {
    private final JobLauncher jobLauncher;  // Job 실행을 위한 JobLauncher 주입
    private final JobRegistry jobRegistry;  // Job 레지스트리 주입
    @RequestMapping("/api/batch")
    public void test(@RequestParam String jobName) throws Exception {
        // 주어진 jobName을 사용하여 JobRegistry에서 Job을 검색
        Job processJob = jobRegistry.getJob(jobName);

        // Job을 실행할 때 필요한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        // JobLauncher를 사용하여 Job 실행
        jobLauncher.run(processJob, jobParameters);
    }
}
