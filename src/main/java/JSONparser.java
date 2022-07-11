import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class JSONparser {
    public static LinkedHashMap<String, String> collectRecords(String file, String object) throws IOException {
        LinkedHashMap<String, String> records = new LinkedHashMap<>();
        FileReader fileReader = new FileReader("src\\main\\java\\"+file);
        StringBuilder jsonString = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line=bufferedReader.readLine())!=null){
                   jsonString.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonObject json = new Gson().fromJson(jsonString.toString(), JsonObject.class);
        JsonArray record = json.getAsJsonObject("spr").getAsJsonObject(object).getAsJsonArray("record");

        for (JsonElement element : record){
            String code;
            JsonObject el = element.getAsJsonObject();
            try {
                Long temp = el.get("code").getAsLong();
                code = Long.toString(temp);
            } catch (Exception e){
                code = el.get("code").getAsString();
            }
            String name = el.get("name").getAsString();
            records.put(code, name);
        }
        return records;
    }
}
