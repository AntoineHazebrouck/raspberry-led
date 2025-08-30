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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PulseWidthModulation extends PwmBase implements Runnable {

    private final DigitalOutput digitalOutput;

    private Thread instance;

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

    @Override
    public Pwm on() throws IOException {
        startThreadIfNeeded();
        return this;
    }

    @Override
    public Pwm off() throws IOException {
        stopThread();
        return this;
    }

    private synchronized void stopThread() {
        if (onState) {
            onState = false;
            try {
                instance.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            digitalOutput.low(); // Ensure pin is low after stopping
        }
    }

    private synchronized void startThreadIfNeeded() {
        if (!onState) {
            onState = true;
            instance = new Thread(this::run);
            instance.setDaemon(true);
            instance.start();
        }
    }

    public void run() {
        log.info("loop started");
        super.onState = true;

        while (super.onState) {
            int period = (1000000 / getFrequency());
            int highTime = (int) (period * (getDutyCycle() / 100.0));
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
    }

    private void delayUs(long us) {
        long startTime = System.nanoTime();
        long endTime = startTime + (us * 1000);
        while (System.nanoTime() < endTime) {}
    }

    @Override
    public Pwm shutdown(Context context) throws ShutdownException {
        log.info("shutting down");
        stopThread();
        return super.shutdown(context);
    }
}
