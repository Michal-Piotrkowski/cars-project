import com.fasterxml.uuid.Generators;

import java.util.Date;
import java.util.UUID;

public class CarSearch {
    public UUID uuid;
    public String model;
    public Integer year;
    public Airbag driver;
    public Airbag passenger;
    public Airbag rear;
    public Airbag side;
    public String color;
    public String img;
    public Date date;
    public Integer price;
    public Integer vat;
    public Boolean isGenerated = false;

    public CarSearch(String model, Integer year, Airbag driver, Airbag passenger, Airbag rear, Airbag side, String color, String img, Date date, Integer price, Integer vat) {
        this.model = model;
        this.year = year;
        this.driver = driver;
        this.passenger = passenger;
        this.rear = rear;
        this.side = side;
        this.color = color;
        this.img = img;
        this.date = date;
        this.price = price;
        this.vat = vat;
    }

    public CarSearch(String carBrand, Integer randomYear, Airbag driverAirbag, Airbag passengerAirbag, Airbag rearAirbag, Airbag sideAirbag, String color, String pathToImage, Date date) {
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Car{" +
                "uuid='" + uuid + '\'' +
                ", model='" + model +
                ", year=" + year +
                ", driver=" + driver +
                ", passenger=" + passenger +
                ", rear=" + rear +
                ", side=" + side +
                ", color=" + color +
                ", img=" + img +
                ", date=" + date +
                ", price=" + price +
                ", vat='" + vat + '\'' +
                '}';
    }

    public UUID generateUUID() {
        UUID uuid = Generators.randomBasedGenerator().generate();
        return uuid;
    }

    public String getModel() {
        return model;
    }
}
