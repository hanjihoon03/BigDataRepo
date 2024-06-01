package com.example.bigdataboost.batch;

import com.example.bigdataboost.model.NaverShoppingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NaverShoppingDataBatchConfig {

    @Bean
    public Job naverShoppingDataGet(JobRepository jobRepository, Step shoppingDataStep) throws Exception {
        return new JobBuilder("getData", jobRepository)
                .incrementer(new RunIdIncrementer()) // Job 실행마다 고유한 ID를 생성하여 식별
                .start(shoppingDataStep) // 해당 스텝부터 배치 작업 시작
                .build();
    }

    @Bean
    @JobScope
    public Step shoppingDataStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("getDataStep",jobRepository)
                .<NaverShoppingResponse,NaverShoppingResponse>chunk(1,transactionManager)
                .reader(readNaverData())
                .writer(writeNaverData())
                .build();
    }
    @Bean
    public ItemReader<NaverShoppingResponse> readNaverData() {
        return null;
    }
    @Bean
    public ItemWriter<NaverShoppingResponse> writeNaverData() {
        return null;
    }



}
