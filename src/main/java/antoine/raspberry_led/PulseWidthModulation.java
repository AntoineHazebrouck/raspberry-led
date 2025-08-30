package antoine.raspberry_led;

import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmBase;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.library.gpiod.internal.GpioDContext;
import com.pi4j.library.gpiod.internal.GpioLine;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalOutput;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PulseWidthModulation extends PwmBase {

    private final DigitalOutput digitalOutput;

    private final Thread instance = new Thread(() -> {
        this.loop();
    });
    private boolean loopRunning = false;

    public PulseWidthModulation(PwmProvider provider, PwmConfig config) {
        super(provider, config);
        GpioLine line = GpioDContext.getInstance()
            .getOrOpenLine(config.address());
        digitalOutput = new GpioDDigitalOutput(
            line,
            provider.context().getDigitalOutputProvider(),
            DigitalOutputConfigBuilder.newInstance(provider.context())
                .address(config.address())
                .build()
        );
        digitalOutput.initialize(provider.context());

        Optional.ofNullable(config.frequency()).ifPresent(this::setFrequency);
    }

    private final AtomicInteger currentFrequency = new AtomicInteger(
        getFrequency()
    );
    private final AtomicReference<Float> currentDutyCycle =
        new AtomicReference<>(getDutyCycle());

    @Override
    public Pwm on() throws IOException {
        currentFrequency.set(getFrequency());
        currentDutyCycle.set(getDutyCycle());

        if (loopRunning == false) {
            // init infinite loop
            loopRunning = true;
            instance.start();
        }
        return this;
    }

    @Override
    public Pwm off() throws IOException {
        // setDutyCycle(0);
        // setFrequency(0);
        return this;
    }

    private void loop() {
        log.info("loop started");

        while (loopRunning) {
            int period = (1000000 / currentFrequency.get());
            int highTime = (int) (period * (currentDutyCycle.get() / 100.0));
            int lowTime = (period - highTime);

            if (highTime != 0) {
                digitalOutput.high();
                delayUs(highTime);
            }
            if (lowTime != 0) {
                digitalOutput.low();
                delayUs(lowTime);
            }
        }

        log.info("loop ended");
    }

    private void delayUs(long us) {
        long startTime = System.nanoTime();
        long endTime = startTime + (us * 1000);
        while (System.nanoTime() < endTime) {}
    }

    @Override
    public Pwm shutdown(Context context) throws ShutdownException {
        loopRunning = false; // stop infinite loop
        try {
            instance.join();
        } catch (InterruptedException e) {
            instance.interrupt();
        }
        return super.shutdown(context);
    }
}
