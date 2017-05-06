/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ch.akros.cc.primecalculator.config.PrimeCalculatorConfigurationProperties;
import ch.akros.cc.primecalculator.runner.PrimeCalculatorRunner;

@SpringBootApplication
public class PrimeCalculatorMain implements CommandLineRunner {

   @Autowired
   private PrimeCalculatorConfigurationProperties properties;

   @Autowired
   private ApplicationContext                     applicationContext;

   /*
    * (non-Javadoc)
    * 
    * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
    */
   @Override
   public void run(final String... arg0) throws Exception {

      final PrimeCalculatorRunner runner = applicationContext.getBean(PrimeCalculatorRunner.class);
      runner.setNumberRange(properties.getBegin(), properties.getEnd());

      final Thread t = new Thread(runner);
      t.start();
      t.join();
   }

   public static void main(final String[] args) {

      SpringApplication.run(PrimeCalculatorMain.class, args);
   }

}
