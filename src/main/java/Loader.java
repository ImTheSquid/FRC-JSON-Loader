

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Set;

public class Loader {
    //Temporary run method
    public static void main(String[] args) {
        loadJSON("/controllerProfiles.json");
        String[] driver=JSONMap.getDriverProfileNames();
        System.out.println(driver[0]+"/"+driver[1]);
        System.out.println(JSONMap.getDriverProfile("default2").getXbox().toString());
    }

    static void loadJSON(String src){
        Object obj;
        try{
            File f=new File(Loader.class.getResource(src).toURI());
            obj=new JSONParser().parse(new FileReader(f));
        } catch (IOException | ParseException | URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        JSONObject topObj=(JSONObject) obj;
        JSONObject driver=(JSONObject) topObj.get("driver");
        String[] driverProfs= setToString(driver.keySet());
        ControllerProfile[] drivers=new ControllerProfile[driverProfs.length];
        for(int i=0;i<drivers.length;i++){
            drivers[i]=loadProfile((JSONObject)driver.get(driverProfs[i]),driverProfs[i]);
        }
        JSONObject gunner=(JSONObject) topObj.get("gunner");
        String[] gunnerProfs=setToString(gunner.keySet());
        ControllerProfile[] gunners=new ControllerProfile[gunnerProfs.length];
        for(int i=0;i<gunners.length;i++){
            gunners[i]=loadProfile((JSONObject)gunner.get(gunnerProfs[i]),gunnerProfs[i]);
        }
        JSONMap.loadProfiles(drivers,gunners);
    }

    private static String[] setToString(Set source){
        String[] dest = new String[source.size()];
        int iter=0;
        for (Object o : source) {
            dest[iter]=(String)o;
            iter++;
        }
        return dest;
    }

    private static ControllerProfile loadProfile(JSONObject profile,String name){
        HashMap<String, Object> xboxMaps=new HashMap<>();
        fillMap(profile,xboxMaps,"xbox");
        HashMap<String,Object> joystickMaps=new HashMap<>();
        fillMap(profile,joystickMaps,"joystick");
        return new ControllerProfile(name,xboxMaps,joystickMaps);
    }

    private static void fillMap(JSONObject source, HashMap<String, Object> dest, String key){
        JSONObject object=(JSONObject)source.get(key);
        Set keys=object.keySet();
        for(Object o:keys){
            JSONArray arr=(JSONArray)object.get(o);
            dest.put((String)arr.get(0),arr.get(1));
        }
    }
}
