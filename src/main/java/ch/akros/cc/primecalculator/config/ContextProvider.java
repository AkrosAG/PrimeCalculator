/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextProvider implements ApplicationContextAware {

   private static ApplicationContext ctx;

   /*
    * (non-Javadoc)
    * 
    * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
    * ApplicationContext)
    */
   @Override
   public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {

      ctx = applicationContext;
   }

   /**
    * Get a Spring bean by type.
    **/
   public static <T> T getBean(final Class<T> beanClass) {

      return ctx.getBean(beanClass);
   }

   /**
    * Get a Spring bean by name.
    **/
   public static Object getBean(final String beanName) {

      return ctx.getBean(beanName);
   }
}
