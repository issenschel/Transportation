package com.example.transportation.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProposalDto {
    @NotBlank(message = "Не указано поле senderAddress")
    @Size(min = 10, max = 80, message = "Адрес не может быть меньше 10 или больше 80")
    private String senderAddress;
    @NotBlank(message = "Не указано поле recipientAddress")
    @Size(min = 10, max = 80, message = "Адрес не может быть меньше 10 или больше 80")
    private String recipientAddress;
    @NotNull(message = "Не указано поле dateDispatch")
    @Future(message = "Неправильно указана дата")
    private LocalDate dateDispatch;
    @NotNull(message = "Не указано поле dateDispatch")
    @Future(message = "Неправильно указана дата")
    private LocalDate dateReceipt;
    @NotNull(message = "Не указано поле transport")
    @Max(value = 4, message = "максимальное число 4")
    @Min(value = 1, message = "минимально число 1")
    private Byte transport;
    @NotNull(message = "Не указано поле budget")
    @Max(value = 1000000, message = "максимальный бюджет 1000000")
    @Min(value = 1000, message = "минимальный бюджет 1000")
    private Integer budget;
    private String description;
}
