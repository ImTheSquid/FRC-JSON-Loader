import java.util.HashMap;

public class ControllerProfile {
    private String name;
    private HashMap<String,Object> xbox, joystick;
    public ControllerProfile(String name, HashMap<String, Object> xbox, HashMap<String, Object> joystick){
        this.joystick=joystick;
        this.name=name;
        this.xbox=xbox;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Object> getJoystick() {
        return joystick;
    }

    public HashMap<String, Object> getXbox() {
        return xbox;
    }
}
