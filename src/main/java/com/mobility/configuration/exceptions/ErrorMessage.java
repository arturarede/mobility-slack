package com.mobility.configuration.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
  private final Date timestamp;
  private final int status;
  private final String error;
  private final String message;
  private final String description;
}