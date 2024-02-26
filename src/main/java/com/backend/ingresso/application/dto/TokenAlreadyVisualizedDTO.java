package com.backend.ingresso.application.dto;

public class TokenAlreadyVisualizedDTO {
    private Integer TokenAlreadyVisualized;
    private String ErroMessage;

    public TokenAlreadyVisualizedDTO(Integer tokenAlreadyVisualized, String erroMessage) {
        TokenAlreadyVisualized = tokenAlreadyVisualized;
        ErroMessage = erroMessage;
    }

    public Integer getTokenAlreadyVisualized() {
        return TokenAlreadyVisualized;
    }

    public String getErroMessage() {
        return ErroMessage;
    }
}
