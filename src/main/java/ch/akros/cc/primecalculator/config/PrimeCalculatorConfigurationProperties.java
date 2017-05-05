/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "primecalc")
public class PrimeCalculatorConfigurationProperties {

   @NotNull
   private long begin;

   @NotNull
   private long end;

   public long getBegin() {

      return begin;
   }

   public void setBegin(long begin) {

      this.begin = begin;
   }

   public long getEnd() {

      return end;
   }

   public void setEnd(long end) {

      this.end = end;
   }

}
