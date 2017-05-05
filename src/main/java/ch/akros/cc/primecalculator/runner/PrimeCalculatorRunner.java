/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.runner;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.akros.cc.primecalculator.worker.PrimeCalculatorWorker;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

public class PrimeCalculatorRunner implements Runnable {

   private static final Logger   LOG                     = LoggerFactory.getLogger(PrimeCalculatorRunner.class);

   private final Map<Long, Long> numbersToCalculatePrime = new HashMap<>();

   public PrimeCalculatorRunner(final long begin, final long end) {
      long indexBegin = begin;
      do {
         long indexEnd = indexBegin + 1000;
         if (indexEnd > end) {
            indexEnd = end;
         }
         numbersToCalculatePrime.put(indexBegin, indexEnd);
         indexBegin += 1000;
      } while (indexBegin <= end);
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run() {

      final SystemInfo si = new SystemInfo();
      final HardwareAbstractionLayer hal = si.getHardware();

      final int procCount = hal.getProcessor().getPhysicalProcessorCount();

      final ExecutorService executor = Executors.newFixedThreadPool(procCount);
      for (final Entry<Long, Long> numberToCalculatePrime : numbersToCalculatePrime.entrySet()) {
         final Runnable worker = new PrimeCalculatorWorker(numberToCalculatePrime.getKey(),
               numberToCalculatePrime.getValue());
         executor.execute(worker);
      }

      executor.shutdown();
      while (!executor.isTerminated()) {
         printMemory(hal.getMemory());
         printCpu(hal.getProcessor());
         try {
            TimeUnit.SECONDS.sleep(5);
         } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
         }
         if (Thread.currentThread().isInterrupted()) {
            break;
         }
      }
   }

   private void printMemory(final GlobalMemory memory) {

      LOG.debug("Memory: {}/{}", FormatUtil.formatBytes(memory.getAvailable()),
            FormatUtil.formatBytes(memory.getTotal()));
   }

   private void printCpu(final CentralProcessor processor) {

      LOG.debug("CPU load: {} (OS MXBean)", processor.getSystemCpuLoad() * 100);
   }

}
