package antoine.raspberry_led;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Main {

    public void run() {
        var pi = Pi4J.newAutoContext();

        List<DigitalOutput> leds = Stream.of(
            17,
            18,
            27
            // 22,
            // 23,
            // 24,
            // 25,
            // 2,
            // 3,
            // 8
        )
            .map(pin -> pi.digitalOutput().<DigitalOutput>create(pin))
            .toList();

        for (int i = 0; i < 10; i++) {
            leds.forEach(led -> {
                turnOff(leds);

                led.blink(500, 1, TimeUnit.MILLISECONDS, DigitalState.LOW);
            });
        }

        pi.shutdown();
    }

    private void turnOff(List<DigitalOutput> leds) {
        leds.forEach(led -> led.high());
    }
}
