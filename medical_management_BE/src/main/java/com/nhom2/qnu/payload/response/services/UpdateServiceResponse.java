package com.nhom2.qnu.payload.response.services;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateServiceResponse {
  private String status;
  private String massage;
}
