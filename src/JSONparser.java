import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JSONparser {
    public static HashMap<String, String> collectRecords(String file, String object) throws IOException {
        HashMap<String, String> records = new HashMap<>();
        FileReader fileReader = new FileReader("src\\"+file);
        StringBuilder jsonString = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line=bufferedReader.readLine())!=null){
                   jsonString.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject json = new JSONObject(jsonString.toString());
        JSONArray record = json.getJSONObject("spr").getJSONObject(object).getJSONArray("record");

        for(int i=0;i<record.length(); i++) {
            String code;
            try {
                Integer temp = record.getJSONObject(i).getInt("code");
                code = temp.toString();
            } catch (org.json.JSONException e){
                code = record.getJSONObject(i).getString("code");
            }
            String name = record.getJSONObject(i).getString("name");
            records.put(code, name);
        }
        return records;
    }
}
