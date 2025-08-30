package antoine.raspberry_led;

import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmProviderBase;

public class PulseWidthModulationProvider extends PwmProviderBase {

    @Override
    public Pwm create(PwmConfig config) {
        PulseWidthModulation pulseWidthModulation = new PulseWidthModulation(
            this,
            config
        );
        this.context.registry().add(pulseWidthModulation);
        return pulseWidthModulation;
    }
}
