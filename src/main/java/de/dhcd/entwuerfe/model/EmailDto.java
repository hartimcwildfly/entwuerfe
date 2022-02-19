package de.dhcd.entwuerfe.model;


import lombok.Data;


@Data
public class EmailDto {
    private final String from;
    private final String to;
    private final String subject;
    private final String text;
}
