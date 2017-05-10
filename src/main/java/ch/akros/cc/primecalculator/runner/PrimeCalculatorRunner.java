/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.runner;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import ch.akros.cc.primecalculator.worker.PrimeCalculatorWorker;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

/**
 * Runner Thread to execute a calculation thread each 1000 numbers.
 */
@Component
@Scope("prototype")
public class PrimeCalculatorRunner implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(PrimeCalculatorRunner.class);

	private final Map<Long, Long> numbersToCalculatePrime = new HashMap<>();

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		final SystemInfo si = new SystemInfo();
		final HardwareAbstractionLayer hal = si.getHardware();

		for (final Entry<Long, Long> numberToCalculatePrime : numbersToCalculatePrime.entrySet()) {
			final PrimeCalculatorWorker worker = applicationContext.getBean(PrimeCalculatorWorker.class);
			worker.setNumberRange(numberToCalculatePrime.getKey(), numberToCalculatePrime.getValue());
			taskExecutor.execute(worker);
		}

		while (taskExecutor.getActiveCount() != 0) {
			printMemory(hal.getMemory());
			printCpu(hal.getProcessor());
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
		}
		taskExecutor.shutdown();
	}

	private void printMemory(final GlobalMemory memory) {

		final long usedMemory = memory.getTotal() - memory.getAvailable();
		LOG.debug("UsedMemory={}/{}", FormatUtil.formatBytes(usedMemory).replaceAll(" ", ""),
				FormatUtil.formatBytes(memory.getTotal()).replaceAll(" ", ""));
	}

	private void printCpu(final CentralProcessor processor) {

		final DecimalFormat df = new DecimalFormat("#.00");
		LOG.debug("CpuLoad={}", df.format(processor.getSystemCpuLoad() * 100));
	}

	public void setNumberRange(final long begin, final long end) {

		long indexBegin = begin;
		do {
			long indexEnd = indexBegin + 1000;
			if (indexEnd > end) {
				indexEnd = end;
			}
			numbersToCalculatePrime.put(indexBegin, indexEnd);
			indexBegin += 1000;
		} while (indexBegin <= end);
	}

}
