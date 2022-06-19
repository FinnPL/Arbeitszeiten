import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class jsonEditor {
    @SuppressWarnings("unchecked")
    public void create(File file, String brakeTime, String workTime) {
        // File Configs Details
        JSONObject fileconfig = new JSONObject();
        fileconfig.put("location", file.getAbsolutePath());

        // File Configs Object
        JSONObject fileconfigs = new JSONObject();
        fileconfigs.put("fileconfig", fileconfig);

        // Work Details
        JSONObject workDetails = new JSONObject();
        workDetails.put("brakeTime", brakeTime);
        workDetails.put("workTime", workTime);

        // Work Configs Object
        JSONObject workObject = new JSONObject();
        workObject.put("workconfig", workDetails);

        // Add config to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(fileconfigs);
        employeeList.add(workObject);

        // Write JSON file
        try (FileWriter cFile = new FileWriter("config.json")) {
            // We can write any JSONArray or JSONObject instance to the file
            cFile.write(employeeList.toJSONString());
            cFile.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("config.json"));
            JSONObject b = (JSONObject) a.get(0);
            JSONObject c = (JSONObject) b.get("fileconfig");
            String path = (String) c.get("location");
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBreaktime() {
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("config.json"));
            JSONObject b = (JSONObject) a.get(1);
            JSONObject c = (JSONObject) b.get("workconfig");
            String breaktime = (String) c.get("brakeTime");
            return breaktime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getWorktime() {
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("config.json"));
            JSONObject b = (JSONObject) a.get(1);
            JSONObject c = (JSONObject) b.get("workconfig");
            String worktime = (String) c.get("workTime");
            return worktime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}