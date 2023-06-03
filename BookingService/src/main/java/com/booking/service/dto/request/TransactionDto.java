package com.booking.service.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {

    @NotNull(message = "amount cannot be null")
    private Double amount;

    @NotBlank(message = "type cannot be empty/null")
    private String type;

    @NotBlank(message = "currency cannot be empty/null")
    private String currency;

    private Integer parent;


}
