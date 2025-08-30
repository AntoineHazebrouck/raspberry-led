package antoine.raspberry_led;

import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Main {

    public void run(Context pi) throws Exception {
        Pwm pwm = pi.pwm().create(17);
        pwm.setFrequency(1000);

        for (int i = 0; i < 10; i++) {
            double dutyCycle = 0;
            while (dutyCycle < 100) {
                pwm.on(dutyCycle);
                // pwm.setDutyCycle(dutyCycle);
                dutyCycle += 1;
                Thread.sleep(10);
            }
            while (dutyCycle > 0) {
                pwm.on(dutyCycle);
                // pwm.setDutyCycle(dutyCycle);
                dutyCycle -= 1;
                Thread.sleep(10);
            }
        }
    }
}
