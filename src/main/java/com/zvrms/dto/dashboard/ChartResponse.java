package com.zvrms.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartResponse {

    private String label;

    private Long value;

}