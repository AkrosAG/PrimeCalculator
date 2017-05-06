/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ch.akros.cc.primecalculator.config.PrimeCalculatorConfigurationProperties;
import ch.akros.cc.primecalculator.model.Calculation;

@Service
public class CalculationPublisher {

   private static final Logger                    LOG = LoggerFactory.getLogger(CalculationPublisher.class);

   @Autowired
   private PrimeCalculatorConfigurationProperties properties;

   @Autowired
   private RestTemplate                           restTemplate;

   public void publishCalculation(final UUID traceNumber, final long primeNumber, final long calcTime) {

      String host;
      try {
         host = InetAddress.getLocalHost().getHostAddress();
      } catch (final UnknownHostException e) {
         LOG.error("Keine Hostadresse gefunden", e);
         host = "localhost";
      }

      final Calculation calc = new Calculation(traceNumber, primeNumber, calcTime, host);

      try {
         final ResponseEntity<Boolean> resp = restTemplate.postForEntity(properties.getEndpoint(), calc, Boolean.class);

         if (HttpStatus.OK.equals(resp.getStatusCode()) && resp.getBody()) {
            LOG.info("Primzahl {} wurde im Repository aufgenommen. uuid={} ip={} prime={} calctime={}",
                  calc.getPrimeNumber(), calc.getTraceNumber(), calc.getIp(), calc.getPrimeNumber(),
                  calc.getCalcTime());
         } else {
            LOG.warn("Primzahl {} wurde nicht im Repository aufgenommen. uuid={} ip={} prime={} calctime={}",
                  calc.getPrimeNumber(), calc.getTraceNumber(), calc.getIp(), calc.getPrimeNumber(),
                  calc.getCalcTime());
         }
      } catch (final RestClientException e) {
         LOG.error("Fehler beim senden der Primzahl ans Repository", e);
      }
   }
}
