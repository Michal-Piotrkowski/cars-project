import com.itextpdf.text.Document;

import java.util.UUID;

public class Invoice {
    public UUID uuid;
    public Document document;

    public Invoice(UUID uuid, com.itextpdf.text.Document document) {
        this.uuid = uuid;
        this.document = document;
    }
}
