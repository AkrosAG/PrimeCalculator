package ch.akros.cc.primecalculator.runner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.akros.cc.primecalculator.log.LoggingTestUtil;
import ch.akros.cc.primecalculator.worker.PrimeCalculatorWorkerTest;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

@RunWith(MockitoJUnitRunner.class)
public class PrimeCalculatorRunnerTest {

   private static final Logger     LOG = LoggerFactory.getLogger(PrimeCalculatorWorkerTest.class);

   @Mock
   private Appender<ILoggingEvent> mockAppender;

   @Before
   public void setUp() {

      LoggingTestUtil.setupLoggingMock(LOG, mockAppender);
   }

   @Test
   public void PrimeCalculatorRunner_calculateZeroToTen_shouldProduceLog() throws InterruptedException {

      final Thread t = new Thread(new PrimeCalculatorRunner(0, 10));
      t.start();
      t.join();

      checkLog();
   }

   private void checkLog() {

      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "0 ist eine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "1 ist keine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "2 ist eine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "3 ist eine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "4 ist keine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "5 ist eine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "6 ist keine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "7 ist eine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "8 ist keine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "9 ist keine Primzahl");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.INFO, "10 ist keine Primzahl");

      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.DEBUG, "Memory:");
      LoggingTestUtil.verifyLogAppendedAtLevel(mockAppender, Level.DEBUG, "CPU load:");
   }
}
