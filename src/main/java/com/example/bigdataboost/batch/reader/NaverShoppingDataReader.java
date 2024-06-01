package com.example.bigdataboost.batch.reader;

import com.example.bigdataboost.model.NaverShoppingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

@Slf4j
public class NaverShoppingDataReader implements ItemReader<NaverShoppingResponse> {

    @Override
    public NaverShoppingResponse read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
