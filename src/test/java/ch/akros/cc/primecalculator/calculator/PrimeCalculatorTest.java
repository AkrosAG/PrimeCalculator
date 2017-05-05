package ch.akros.cc.primecalculator.calculator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PrimeCalculatorTest {

   @Test
   public void isPrime_shoudReturnTrue() {

      final long prime = 5;
      assertTrue(PrimeCalculator.isPrime(prime));
   }

   @Test
   public void isPrime_shouldReturnFalse() {

      final long prime = 12;
      assertFalse(PrimeCalculator.isPrime(prime));
   }

   @Test
   public void isPrime_withOne_shouldReturnFalse() {

      final long prime = 1;
      assertFalse(PrimeCalculator.isPrime(prime));
   }
}
