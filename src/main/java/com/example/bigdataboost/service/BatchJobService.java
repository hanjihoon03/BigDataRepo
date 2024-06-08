package com.example.bigdataboost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchJobService {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;
    private final Environment env;


    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void runNaverShoppingDataJob() {
        try {
            Job processJob = jobRegistry.getJob(env.getProperty("spring.batch.job.name"));

            // Job을 실행할 때 필요한 JobParameters 생성
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            // JobLauncher를 사용하여 Job 실행
            jobLauncher.run(processJob, jobParameters);
        } catch (Exception e) {
            log.error("배치가 스케줄로 실행에 실패했습니다.:", e);
        }
    }
}
