package com.sensemore.tenant.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogoutResult<T> extends ResultDto<T> {

}
