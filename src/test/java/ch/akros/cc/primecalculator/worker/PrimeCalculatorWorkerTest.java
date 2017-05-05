package ch.akros.cc.primecalculator.worker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.akros.cc.primecalculator.log.LoggingTestUtil;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

@RunWith(MockitoJUnitRunner.class)
public class PrimeCalculatorWorkerTest {

   @Mock
   private Appender<ILoggingEvent> mockAppender;

   private static Logger           logger = LoggerFactory.getLogger(PrimeCalculatorWorkerTest.class);

   @Before
   public void setUp() {

      LoggingTestUtil.setupLoggingMock(logger, mockAppender);
   }

   @Test
   public void PrimeCalculatorWorker_shouldLogThreePrimeNumbers() throws InterruptedException {

      Runnable r = new PrimeCalculatorWorker(0, 3);
      Thread t = new Thread(r);
      t.start();
      t.join();

      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "0 is eine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "1 is eine keine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "2 is eine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "3 is eine Primzahl");
   }
}
