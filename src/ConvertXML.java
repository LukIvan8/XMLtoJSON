import org.json.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertXML {

    public static void convertXMLtoJSON(String file) throws IOException {

        FileReader fileReader = new FileReader("src\\"+file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String inputString;

        while ((inputString = bufferedReader.readLine()) != null){

            stringBuilder.append(inputString);
        }
        try(FileWriter fileWriter = new FileWriter("src\\"+file.substring(0,file.length()-4)+".json")) {
            JSONObject json = XML.toJSONObject(stringBuilder.toString());
            fileWriter.write(String.valueOf(json));
        }catch (JSONException e) {
            System.out.println(e);
        }finally {
            fileReader.close();
        }

    }


}
