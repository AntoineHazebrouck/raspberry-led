package antoine.raspberry_led;

import com.pi4j.Pi4J;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Main {

    public void run() {
        var pi4j = Pi4J.newAutoContext();

        var led = pi4j.digitalOutput().create(17);

        led.blink(1, 100, TimeUnit.SECONDS);

        pi4j.shutdown();
    }
}
