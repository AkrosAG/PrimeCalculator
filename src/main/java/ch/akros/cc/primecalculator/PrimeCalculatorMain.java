/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.akros.cc.primecalculator.config.PrimeCalculatorConfigurationProperties;
import ch.akros.cc.primecalculator.runner.PrimeCalculatorRunner;

@SpringBootApplication
@EnableAutoConfiguration
public class PrimeCalculatorMain implements CommandLineRunner {

   private PrimeCalculatorConfigurationProperties properties;

   @Autowired
   public void setPrimeCalculatorConfigurationProperties(final PrimeCalculatorConfigurationProperties properties) {

      this.properties = properties;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
    */
   @Override
   public void run(final String... arg0) throws Exception {

      final Thread t = new Thread(new PrimeCalculatorRunner(properties.getBegin(), properties.getEnd()));
      t.start();
      t.join();
   }

   public static void main(final String[] args) {

      SpringApplication.run(PrimeCalculatorMain.class, args);
   }
}
