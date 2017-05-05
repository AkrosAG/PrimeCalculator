/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.calculator;

/**
 * Calculator for prime numbers
 */
public final class PrimeCalculator {

   /**
    * Check if the given number is a prime number.
    * 
    * @param number The number to check.
    * @return true if the number is a prime number, lese return false.
    */
   public static boolean isPrime(final long number) {

      int n = 2;

      // Schleife ueber alle moeglichen Teiler n des Primzahlkandidaten i:
      while (number % n != 0 && n <= number / 2) {
         // Erhoehe n solange, wie i nicht nurch n teilbar ist und die
         // Obergrenze noch nicht erreicht ist
         n = n + 1;
      }

      // Falls die Schleife bis zur Obergrenze i/2 durchlaufen wurde ist es eine Primzahl, andernfalls wurde die
      // Schleife vorher abgebrochen, weil i durch n teilbar war.
      return (n >= number / 2 + 1 && number != 1);
   }
}
