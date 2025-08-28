package antoine.raspberry_led;

import com.pi4j.Pi4J;
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

        var pi = Pi4J.newAutoContext();
        context.getBean(Main.class).run(pi);
        pi.shutdown();
    }
}
