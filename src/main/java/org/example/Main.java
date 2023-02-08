package org.example;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;


public final class Main {

    public static void main(String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder()
                .include(BenchMain.class.getSimpleName())
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
