package com.example.bigdataboost.batch;

import com.example.bigdataboost.batch.reader.MemberItemReader;
import com.example.bigdataboost.batch.writer.MemberItemWriter;
import com.example.bigdataboost.model.Member;
import com.example.bigdataboost.repository.MemberRepository;
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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class TestBatchConfig {

    private final RedisConnectionFactory redisConnectionFactory;
    private final MemberRepository memberRepository;
    // 배치 작업을 정의하는 메소드
    @Bean
    public Job shoppingDataJob(JobRepository jobRepository, Step shoppingDataStep) throws Exception {
        return new JobBuilder("addMember", jobRepository)
                .incrementer(new RunIdIncrementer()) // Job 실행마다 고유한 ID를 생성하여 식별
                .start(shoppingDataStep) // 해당 스텝부터 배치 작업 시작
                .build();
    }

    // 배치 스텝을 정의하는 메소드
    @Bean
    @JobScope
    public Step shoppingDataStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("addStep", jobRepository)
                .<Member, Member>chunk(10, transactionManager) // 10개씩 묶어서 처리하는 청크 기반의 스텝
                .reader(memberItemReader()) // 아이템을 읽어오는 리더 설정
                .writer(memberItemWriter(memberRepository)) // 아이템을 처리하여 쓰는 라이터 설정
                .build();
    }

    // 아이템을 읽어오는 리더 빈을 생성하는 메소드
    @Bean
    public ItemReader<Member> memberItemReader() {
        return new MemberItemReader(); // MemberItemReader 인스턴스를 리턴
    }

    // 아이템을 처리하여 쓰는 라이터 빈을 생성하는 메소드
    @Bean
    public ItemWriter<Member> memberItemWriter(MemberRepository memberRepository) {
        return new MemberItemWriter(memberRepository); // MemberItemWriter 인스턴스를 리턴
    }
}