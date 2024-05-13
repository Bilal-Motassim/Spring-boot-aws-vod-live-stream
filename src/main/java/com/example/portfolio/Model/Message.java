package com.example.portfolio.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Message {
    private String from;
    private String text;
}
