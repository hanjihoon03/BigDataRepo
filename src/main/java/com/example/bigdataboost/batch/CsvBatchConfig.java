package com.example.bigdataboost.batch;

import com.example.bigdataboost.batch.writer.PlayerItemWriter;
import com.example.bigdataboost.mapper.PlayerFieldSetMapper;
import com.example.bigdataboost.model.Player;
import com.example.bigdataboost.model.PlayerYears;
import com.example.bigdataboost.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvBatchConfig {

    private final PlayerRepository playerRepository;

    //job
    @Bean
    public Job fileReadWriteJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("fileReadWriteJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fileReadStep(jobRepository, platformTransactionManager))
                .build();
    }

    //step
    @Bean
    @JobScope
    public Step fileReadStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("fileReadWriteStep", jobRepository)
                .<Player, PlayerYears>chunk(5, platformTransactionManager)
                .reader(playerFlatFileItemReader())
                .processor(playerYearsItemProcessor())
                .writer(playerDataItemWriter(playerRepository))
                .build();
    }

    //itemWriter
    @StepScope
    @Bean
    public FlatFileItemReader<Player> playerFlatFileItemReader() {
        FlatFileItemReader<Player> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("src/main/resources/test.csv")); // CSV 파일 리소스 설정
        reader.setLinesToSkip(1); // 첫 번째 라인(헤더)을 건너뜀

        DefaultLineMapper<Player> lineMapper = new DefaultLineMapper<>(); // DefaultLineMapper를 사용하여 라인을 매핑
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer() { // DelimitedLineTokenizer 설정
            {
                setDelimiter(","); // 쉼표로 구분
                setQuoteCharacter('\"'); // 따옴표로 묶인 필드 처리
                setNames("signature", "lastName", "firstName", "position", "birthYear", "debutYear"); // 각 필드 이름 설정
            }
        });
        lineMapper.setFieldSetMapper(new PlayerFieldSetMapper()); // 필드 매핑 설정
        reader.setLineMapper(lineMapper); // LineMapper 설정

        return reader;
    }
    //itemProcessor
    @StepScope
    @Bean
    public ItemProcessor<Player, PlayerYears> playerYearsItemProcessor() {
        //Player에서 PlayerYears로 가공해 반환
        return PlayerYears::new;
    }

    //itemWriter
    @StepScope
    @Bean
    public ItemWriter<PlayerYears> playerDataItemWriter(PlayerRepository playerRepository) {
        return new PlayerItemWriter(playerRepository);
    }

}
