package antoine.raspberry_led;

import com.pi4j.context.Context;
import java.time.Duration;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Main {

    public void run(Context pi) throws Exception {
        RgbLed rgb = new RgbLed(
            pi.pwm().create(17),
            pi.pwm().create(27),
            pi.pwm().create(26)
        ).frequency(1000);

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            // rgb.on(
            //     random.nextInt(101),
            //     random.nextInt(101),
            //     random.nextInt(101)
            // );
            rgb.on(100, 0, 0);
            Thread.sleep(Duration.ofSeconds(1));
        }
    }
}
