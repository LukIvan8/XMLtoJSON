import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
        JSONArray recordArray = new JSONArray();
        HashSet<String> keys = new HashSet<>();
        for (String key : ru_records.keySet()) {
            keys.add(key);
            JSONObject record = new JSONObject();
            record.put("code", key);
            record.put("rus", ru_records.get(key));
            record.put("kaz", makeKazGreat(key));
            record.put("eng", en_records.get(key));
            recordArray.put(record);
        }
        for (String key : kz_records.keySet()) {
            if (!keys.contains(key)) {
                keys.add(key);
                JSONObject record = new JSONObject();
                record.put("code", key);
                record.put("rus", ru_records.get(key));
                record.put("kaz", makeKazGreat(key));
                record.put("eng", en_records.get(key));
                recordArray.put(record);
            }
        }
        for (String key : en_records.keySet()) {
            if (!keys.contains(key)) {
                keys.add(key);
                JSONObject record = new JSONObject();
                record.put("code", key);
                record.put("rus", ru_records.get(key));
                record.put("kaz", makeKazGreat(key));
                record.put("eng", en_records.get(key));
                recordArray.put(record);
            }
        }
        try (FileWriter file = new FileWriter("src\\Results\\" + object + ".json")) {
            file.write(recordArray.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String makeKazGreat(String key){
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
        if (record_kaz == null){
            return null;
        }
        char[] char_record = record_kaz.toCharArray();
        for (int i=0; i< record_kaz.length() ; ++i){
            if (map.containsKey(char_record[i])) {
                char_record[i] = map.get(char_record[i]);
            }
        }
        String record_end = new String(char_record);
        return record_end;

    }
}
