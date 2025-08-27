package antoine.raspberry_led;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RaspberryLedApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(
            RaspberryLedApplication.class,
            args
        );
        log.info("Starting raspberry pi program");
        context.getBean(Main.class).run();
    }
}
