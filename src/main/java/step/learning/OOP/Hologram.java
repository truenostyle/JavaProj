package step.learning.OOP;

import com.google.gson.JsonObject;

import java.text.ParseException;

@Serializable
public class Hologram extends Literature implements Multiple {
    @Override
    public int getCount() {
        return 1;
    }
    public Hologram(String title) {
        super.setTitle(title);
    }

    @Override
    public String getCard() {
        return "Hologram: '" + super.getTitle() + "'";
    }

    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        return jsonObject.has("title");
    }
    @FromJsonParser
    public static Hologram fromJson(JsonObject jsonObject) throws ParseException {
        // Пример: Создайте и верните новый объект Hologram из jsonObject
        String title = jsonObject.get("title").getAsString();
        return new Hologram(title);  // и любые другие необходимые поля
    }
}
