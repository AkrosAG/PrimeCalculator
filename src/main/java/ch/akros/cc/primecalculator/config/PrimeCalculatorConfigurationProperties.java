/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Read application specific properties.
 */
@Component
@ConfigurationProperties(prefix = "primecalc")
public class PrimeCalculatorConfigurationProperties {

   @NotNull
   private long   begin;

   @NotNull
   private long   end;

   @NotNull
   private String endpoint;

   public long getBegin() {

      return begin;
   }

   public void setBegin(final long begin) {

      this.begin = begin;
   }

   public long getEnd() {

      return end;
   }

   public void setEnd(final long end) {

      this.end = end;
   }

   public String getEndpoint() {

      return endpoint;
   }

   public void setEndpoint(final String endpoint) {

      this.endpoint = endpoint;
   }

}
