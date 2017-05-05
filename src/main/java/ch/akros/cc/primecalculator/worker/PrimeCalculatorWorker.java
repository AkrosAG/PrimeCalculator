/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.akros.cc.primecalculator.calculator.PrimeCalculator;

/**
 * Worker-Thread to calculate all prime number in the given range.
 */
public class PrimeCalculatorWorker implements Runnable {

   private static final Logger LOG = LoggerFactory.getLogger(PrimeCalculatorWorker.class);

   private long                begin;
   private long                end;

   public PrimeCalculatorWorker(long begin, long end) {
      this.begin = begin;
      this.end = end;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run() {

      long numberToCheck = begin;
      while (!Thread.currentThread().isInterrupted()) {
         boolean isPrime = PrimeCalculator.isPrime(numberToCheck);
         if (isPrime) {
            LOG.info("{} is eine Primzahl", numberToCheck);
         } else {
            LOG.info("{} is eine keine Primzahl", numberToCheck);
         }
         numberToCheck++;
         if (numberToCheck > end) {
            break;
         }
      }
   }

}
