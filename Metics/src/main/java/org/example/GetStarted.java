package org.example;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

/**
 * @author XiaShuai on 2020/4/17.
 */
public class GetStarted {
    static final MetricRegistry metrics = new MetricRegistry();


    public static void main(String args[]) {
        startReport();
        Meter requests = metrics.meter("requests");
        requests.mark();

        wait5Seconds();
    }

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

//        JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
//        reporter.start();
    }

    static void wait5Seconds() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException ignored) {
        }
    }
}
