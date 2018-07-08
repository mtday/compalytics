package bdp.compalytics.app.di;

import bdp.compalytics.service.Runner;
import bdp.compalytics.service.impl.DefaultRunner;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RunnerBinder extends AbstractBinder {
    private final Runner runner;

    public RunnerBinder() {
        runner = new DefaultRunner();
    }

    public Runner getRunner() {
        return runner;
    }

    protected void configure() {
        bind(runner).to(Runner.class);
    }
}
