/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.log;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import org.mockito.ArgumentMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

public final class LoggingTestUtil {

   public static void setupLoggingMock(final Logger logger, final Appender<ILoggingEvent> appender) {

      final ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
      root.addAppender(appender);
   }

   public static void verifyLogAppended(final Appender<ILoggingEvent> appender, final String loggedString) {

      verify(appender).doAppend(argThat(new ArgumentMatcher<ILoggingEvent>() {

         @Override
         public boolean matches(final ILoggingEvent event) {

            return event.getFormattedMessage().contains(loggedString);
         }
      }));
   }

   public static void verifyLogAppendedAtLevel(final Appender<ILoggingEvent> appender, final Level level) {

      verify(appender).doAppend(argThat(new ArgumentMatcher<ILoggingEvent>() {

         @Override
         public boolean matches(final ILoggingEvent event) {

            return event.getLevel().equals(level);
         }
      }));
   }

   public static void verifyLogAppendedAtLevel(final Appender<ILoggingEvent> appender, final Level level,
         final String loggedString) {

      verify(appender).doAppend(argThat(new ArgumentMatcher<ILoggingEvent>() {

         @Override
         public boolean matches(final ILoggingEvent event) {

            return event.getLevel().equals(level) && event.getFormattedMessage().contains(loggedString);
         }
      }));
   }
}
