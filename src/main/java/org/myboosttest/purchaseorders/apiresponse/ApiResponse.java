package org.myboosttest.purchaseorders.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private String status;
    private String message;
}
