/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Class for Configuration of Spring-Beans
 */
@Configuration
public class AppConfig {

   @Bean
   public RestTemplate restTemplate(final RestTemplateBuilder builder) {

      return builder //
            .additionalMessageConverters(new MappingJackson2HttpMessageConverter()) //
            .additionalMessageConverters(new StringHttpMessageConverter()) //
            .build();
   }

   @Bean
   public ThreadPoolTaskExecutor taskExecutor() {

      final SystemInfo si = new SystemInfo();
      final HardwareAbstractionLayer hal = si.getHardware();
      final int procCount = hal.getProcessor().getPhysicalProcessorCount();

      final ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
      pool.setCorePoolSize(procCount);
      pool.setMaxPoolSize(procCount);
      pool.setWaitForTasksToCompleteOnShutdown(false);
      return pool;
   }
}
