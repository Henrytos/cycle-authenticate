package com.stefanini.cycle_authenticate.infra.configs.http;

import io.swagger.v3.oas.annotations.media.Schema;

public record ResponseMessageDTO(
        @Schema(description = "message", defaultValue = "message...")
        String message,
        @Schema(description = "status code http", defaultValue = "200")
        int statusCode){}