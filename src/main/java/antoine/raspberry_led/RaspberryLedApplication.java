package antoine.raspberry_led;

import com.pi4j.Pi4J;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalInputProviderImpl;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalOutputProviderImpl;

import antoine.raspberry_led.pwm.PulseWidthModulationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RaspberryLedApplication {

    public static void main(String[] args) throws Exception {
        var context = SpringApplication.run(
            RaspberryLedApplication.class,
            args
        );
        log.info("Starting raspberry pi program");

        var pi = Pi4J.newContextBuilder()
            .add(new GpioDDigitalOutputProviderImpl())
            .add(new GpioDDigitalInputProviderImpl())
            .add(new PulseWidthModulationProvider())
            .build();

        pi
            .providers()
            .all()
            .forEach((key, provider) -> {
                log.info(key);
                log.info(provider.toString());
            });

        context.getBean(Main.class).run(pi);
        pi.shutdown();
    }
}
