package commons;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonData {

    private static final Gson gson = new Gson();

    private static String getDataFromFile(String filePath, String key) {
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> data = gson.fromJson(reader, type);
            String value = data.get(key);
            if (value == null) {
                return "Erro: Chave '" + key + "' não encontrada no arquivo " + filePath;
            }
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao ler arquivo de dados: " + e.getMessage();
        }
    }

    public static String getData(String key) {
        return getDataFromFile("src/test/java/resources/data.json", key);
    }

    public static String getEnvironmentData(String key) {
        return getDataFromFile("src/test/java/resources/environment.json", key);
    }
}