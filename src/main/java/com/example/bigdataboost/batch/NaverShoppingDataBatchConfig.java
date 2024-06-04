package com.example.bigdataboost.batch;

import com.example.bigdataboost.model.submodel.Category;
import com.example.bigdataboost.service.NaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NaverShoppingDataBatchConfig {
    private final NaverService naverService;

    @Bean
    public Job naverShoppingDataGet(JobRepository jobRepository, Step shoppingDataStep) throws Exception {
        return new JobBuilder("shoppingDataJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // Job 실행마다 고유한 ID를 생성하여 식별
                .start(shoppingDataStep) // 해당 스텝부터 배치 작업 시작
                .build();
    }

    @Bean
    @JobScope
    public Step shoppingDataStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("shoppingDataStep",jobRepository)
                .<Category,String>chunk(1,transactionManager)
                .reader(readNaverData())
                .processor(categoryItemProcessor())
                .writer(writeNaverData())
                .build();
    }
    @Bean
    public ItemReader<Category> readNaverData() {
        return new ListItemReader<>(Arrays.asList(Category.values()));
    }
    @Bean
    public ItemProcessor<Category, String> categoryItemProcessor() {
        return category -> {
            log.info("category={}", category.getCode());
            naverService.getShoppingDataSet(category);
            return category.getCode();
        };
    }
    @Bean
    public ItemWriter<String> writeNaverData() {
        return items -> items.forEach(item -> log.info("save data: {}", item));
    }



}
