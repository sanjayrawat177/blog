package com.sr.social.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDetail {

    private LocalDateTime timestamp;
    private List<String> message;
    private String detail;
}
