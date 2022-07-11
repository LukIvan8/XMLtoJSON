import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static HashMap<String, String> ru_records;
    public static HashMap<String, String> kz_records;
    public static HashMap<String, String> en_records;

    public static void main(String[] args) throws IOException {
        ConvertXML.convertXMLtoJSON("SPRZ.EN.xml");
        ConvertXML.convertXMLtoJSON("SPRZ.KZ.xml");
        ConvertXML.convertXMLtoJSON("SPRZ.RU.xml");
        String[] objects = {"is_", "va_", "kv_", "gr_", "ir_", "kr_", "in_", "rd_", "or_", "mp_"};
        for (String s : objects) {
            ru_records = JSONparser.collectRecords("SPRZ.RU.json", s);
            kz_records = JSONparser.collectRecords("SPRZ.KZ.json", s);
            en_records = JSONparser.collectRecords("SPRZ.EN.json", s);
            makeJSON(s);
        }
    }

    public static void makeJSON(String object) {
        JsonArray recordArray = new JsonArray();
        HashSet<String> keys = new HashSet<>();
        CheckRecords(recordArray, keys, ru_records);
        CheckRecords(recordArray, keys, kz_records);
        CheckRecords(recordArray, keys, en_records);
        try (FileWriter file = new FileWriter("src\\Results\\" + object + ".json")) {
            file.write(recordArray.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CheckRecords(JsonArray recordArray, HashSet<String> keys, HashMap<String, String> kz_records) {
        for (String key : kz_records.keySet()) {
            if (!keys.contains(key)) {
                keys.add(key);
                JsonObject record = new JsonObject();
                adder("code", key, record);
                adder("rus", ru_records.get(key), record);
                adder("kaz", makeKazGreat(key), record);
                adder("eng", en_records.get(key), record);
                recordArray.add(record);
            }
        }
    }

    public static String makeKazGreat(String key) {
        String record_kaz = kz_records.get(key);
        Map<Character, Character> map = new HashMap<>();
        map.put('є', 'қ');
        map.put('ј', 'Қ');
        map.put('µ', 'ұ');
        map.put('¤', 'ө');
        map.put('¦', 'ң');
        map.put('Ї', 'ү');
        map.put('ў', 'ә');
        map.put('°', 'Ү');
        map.put('¬', 'ғ');
        map.put('J', 'ө');
        map.put('Ў', 'ә');
        map.put('і', 'і');
        map.put('­', 'Ғ');
        System.out.println(record_kaz);
        if (record_kaz == null) {
            return null;
        }
        char[] char_record = record_kaz.toCharArray();
        for (int i = 0; i < record_kaz.length(); ++i) {
            if (map.containsKey(char_record[i])) {
                char_record[i] = map.get(char_record[i]);
            }
        }
        return new String(char_record);

    }
    public static void adder(String property, String value, JsonObject record){
        if (value!=null){
            record.addProperty(property, value);
        }
    }
}

