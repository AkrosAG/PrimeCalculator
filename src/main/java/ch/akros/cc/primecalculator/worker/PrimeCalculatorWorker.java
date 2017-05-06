/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.worker;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import ch.akros.cc.primecalculator.calculator.PrimeCalculator;
import ch.akros.cc.primecalculator.service.CalculationPublisher;

/**
 * Worker-Thread to calculate all prime number in the given range.
 */
@Component
@Scope("prototype")
public class PrimeCalculatorWorker implements Runnable {

   private static final Logger  LOG = LoggerFactory.getLogger(PrimeCalculatorWorker.class);

   private long                 begin;
   private long                 end;

   @Autowired
   private CalculationPublisher publisher;

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run() {

      long numberToCheck = begin;
      while (!Thread.currentThread().isInterrupted()) {
         final UUID traceNumber = UUID.randomUUID();
         final StopWatch watch = new StopWatch();
         watch.start();
         final boolean isPrime = PrimeCalculator.isPrime(numberToCheck);
         watch.stop();
         final long calcTime = watch.getTotalTimeMillis();
         if (isPrime) {
            LOG.info("{} ist eine Primzahl. uuid={} calctime={}", numberToCheck, traceNumber, calcTime);
            publisher.publishCalculation(traceNumber, numberToCheck, calcTime);
         } else {
            LOG.info("{} ist keine Primzahl. uuid={} calctime={}", numberToCheck, traceNumber, calcTime);
         }
         numberToCheck++;
         if (numberToCheck > end) {
            break;
         }
      }
   }

   public void setNumberRange(final long begin, final long end) {

      this.begin = begin;
      this.end = end;
   }
}
