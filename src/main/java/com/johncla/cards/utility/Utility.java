package com.johncla.cards.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Utility {

    public String dateConverter()  {
        LocalDateTime ldt = LocalDateTime.now();

        return String.valueOf(ldt);
    }
}
