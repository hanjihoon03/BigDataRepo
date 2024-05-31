package com.example.bigdataboost.batch.writer;

import com.example.bigdataboost.model.Member;
import com.example.bigdataboost.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;


@RequiredArgsConstructor
public class MemberItemWriter implements ItemWriter<Member> {

    private final MemberRepository memberRepository;
    @Override
    public void write(Chunk<? extends Member> items) throws Exception {
        for (Member member : items) {
            memberRepository.save(member);
        }
    }
}
