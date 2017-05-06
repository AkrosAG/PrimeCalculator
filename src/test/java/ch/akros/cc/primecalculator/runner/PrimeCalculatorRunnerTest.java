package ch.akros.cc.primecalculator.runner;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import ch.akros.cc.primecalculator.config.AppConfig;
import ch.akros.cc.primecalculator.config.PrimeCalculatorConfigurationProperties;
import ch.akros.cc.primecalculator.service.CalculationPublisher;
import ch.akros.cc.primecalculator.worker.PrimeCalculatorWorker;

@RestClientTest()
@ContextConfiguration(classes = { AppConfig.class, PrimeCalculatorConfigurationProperties.class,
      CalculationPublisher.class, PrimeCalculatorRunner.class, PrimeCalculatorWorker.class })
@RunWith(SpringRunner.class)
public class PrimeCalculatorRunnerTest {

   @Autowired
   private MockRestServiceServer                  server;

   @Autowired
   private ApplicationContext                     applicationContext;

   @Autowired
   private PrimeCalculatorConfigurationProperties properties;

   @Test
   public void PrimeCalculatorRunner_calculateZeroToTen_shouldProduceLog() throws InterruptedException {

      server.expect(ExpectedCount.times(10), requestTo("http://localhost:666/calc"))
            .andRespond(withSuccess("true", MediaType.APPLICATION_JSON));

      final PrimeCalculatorRunner runner = applicationContext.getBean(PrimeCalculatorRunner.class);
      runner.setNumberRange(properties.getBegin(), properties.getEnd());

      final Thread t = new Thread(runner);
      t.start();
      t.join();
   }
}
