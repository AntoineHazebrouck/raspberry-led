package antoine.raspberry_led;

import com.pi4j.io.pwm.Pwm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RgbLed {

    private final Pwm red;
    private final Pwm green;
    private final Pwm blue;

    public RgbLed frequency(int frequency) {
        red.frequency(frequency);
        green.frequency(frequency);
        blue.frequency(frequency);
        return this;
    }

    /**
     * Amounts in percentages
     * @param red
     * @param green
     * @param blue
     */
    public RgbLed on(int red, int green, int blue) {
        this.red.on(invert(red));
        this.green.on(invert(green));
        this.blue.on(invert(blue));
        return this;
    }

    /**
     * 100 becomes 0, and 0 becomes 100
     * @param percent
     * @return
     */
    private int invert(int percent) {
        return 100 - percent;
    }
}
