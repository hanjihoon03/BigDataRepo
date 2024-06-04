package com.example.bigdataboost.batch.writer;

import com.example.bigdataboost.model.Member;
import com.example.bigdataboost.model.PlayerYears;
import com.example.bigdataboost.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class PlayerItemWriter implements ItemWriter<PlayerYears> {
    private final PlayerRepository playerRepository;

    @Override
    public void write(Chunk<? extends PlayerYears> chunk) throws Exception {
        for (PlayerYears playerYears : chunk) {
            playerRepository.save(playerYears);
        }
    }
}

