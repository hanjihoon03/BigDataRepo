package com.example.bigdataboost.batch.reader;

import com.example.bigdataboost.model.Member;
import org.springframework.batch.item.ItemReader;
import java.util.List;

public class MemberItemReader implements ItemReader<Member> {
    private int count = 0;
    private final int maxCount = 10;
    @Override
    public Member read()  {
        if (count < maxCount) {
            count++;
            return Member.builder()
                    .memberId("user" + count)
                    .password("password1" + count)
                    .roles(List.of("ROLE_USER"))
                    .build();
        } else {
            return null;
        }
    }
}
