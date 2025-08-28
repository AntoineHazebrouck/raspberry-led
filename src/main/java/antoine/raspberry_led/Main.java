package antoine.raspberry_led;

import com.pi4j.context.Context;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Main {

    public void run(Context pi) throws Exception {
        var button = pi.digitalInput().create(18);
        var led = pi.digitalOutput().create(17);

        button.addListener(event -> {
            log.info("event = " + event.state());
            switch (event.state()) {
                // it is reverted since the circuit is pull down (the 3.3V goes to the ground when pressing the button, and by default it goes through)
                case HIGH -> {
                    led.low();
                }
                case LOW -> {
                    led.high();
                }
                default -> {
                    log.error("button state unknown");
                }
            }
        });

        while (true) {
            Thread.sleep(Duration.ofMillis(250));
        }
    }
}
