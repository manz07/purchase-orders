package org.myboosttest.purchaseorders.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PONotFoundException extends RuntimeException{
    private final String msg;
}
