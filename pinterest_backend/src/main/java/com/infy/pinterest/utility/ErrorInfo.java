package com.infy.pinterest.utility;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
    private String errorMessage;
    private String errorCode;
    private long timestamp;
    private String path;
}

