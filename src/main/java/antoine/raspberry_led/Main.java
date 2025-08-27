package antoine.raspberry_led;

import com.pi4j.Pi4J;
import com.pi4j.util.Console;
import org.springframework.stereotype.Service;

@Service
public class Main {

    public void run() {
        final var console = new Console();

        // Print program title/header
        console.title("<-- The Pi4J Project -->", "Minimal Example project");

        var pi4j = Pi4J.newAutoContext();

        var led = pi4j.digitalOutput().create(2);

        pi4j.shutdown();
    }
}
