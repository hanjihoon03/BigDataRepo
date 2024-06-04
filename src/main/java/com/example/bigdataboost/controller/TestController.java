package com.example.bigdataboost.controller;

import com.example.bigdataboost.model.NaverShoppingResponse;
import com.example.bigdataboost.service.NaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {
    private final JobLauncher jobLauncher;  // Job 실행을 위한 JobLauncher 주입
    private final JobRegistry jobRegistry;  // Job 레지스트리 주입

    private final NaverService naverService;

//    @GetMapping("/naver")
//    public ResponseEntity<String> naverTest() {
//        String settingParam = naverService.getShoppingDataSet();
//        return ResponseEntity.ok(settingParam);
//    }
    @GetMapping("naver/data")
    public ResponseEntity<List<NaverShoppingResponse>> naverAlldata() {
        List<NaverShoppingResponse> allNaver = naverService.findAllNaver();
        return ResponseEntity.ok(allNaver);
    }

    @GetMapping("naver/shopping")
    public void naverShoppingmallGetData() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, NoSuchJobException {
        Job processJob = jobRegistry.getJob("shoppingDataJob");

        // Job을 실행할 때 필요한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        // JobLauncher를 사용하여 Job 실행
        jobLauncher.run(processJob, jobParameters);
    }
    @GetMapping("/csv")
    public String createCsvFile() throws NoSuchJobException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Job processJob = jobRegistry.getJob("fileReadWriteJob");

        // Job을 실행할 때 필요한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        // JobLauncher를 사용하여 Job 실행
        jobLauncher.run(processJob, jobParameters);
        return "OK";
    }
}
