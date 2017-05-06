/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.model;

import java.util.UUID;

public class Calculation {

   private Long   id;
   private UUID   traceNumber;
   private long   primeNumber;
   private long   calcTime;
   private String ip;

   public Calculation() {
   }

   public Calculation(final UUID traceNumber, final long primeNumber, final long calcTime, final String ip) {
      this.traceNumber = traceNumber;
      this.primeNumber = primeNumber;
      this.calcTime = calcTime;
      this.ip = ip;
   }

   public Long getId() {

      return id;
   }

   public void setId(final Long id) {

      this.id = id;
   }

   public UUID getTraceNumber() {

      return traceNumber;
   }

   public void setTraceNumber(final UUID traceNumber) {

      this.traceNumber = traceNumber;
   }

   public long getPrimeNumber() {

      return primeNumber;
   }

   public void setPrimeNumber(final long primeNumber) {

      this.primeNumber = primeNumber;
   }

   public long getCalcTime() {

      return calcTime;
   }

   public void setCalcTime(final long calcTime) {

      this.calcTime = calcTime;
   }

   public String getIp() {

      return ip;
   }

   public void setIp(final String ip) {

      this.ip = ip;
   }
}
