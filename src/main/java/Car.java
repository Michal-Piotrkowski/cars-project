import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class Car {
    public UUID uuid;
    public String model;
    public Integer year;
    public Airbag driver;
    public Airbag passenger;
    public Airbag rear;
    public Airbag side;
    public String color;
    public Boolean isGenerated = false;

    public Car(String model, Integer year, Airbag driver, Airbag passenger, Airbag rear, Airbag side, String color, UUID uuid) {
        this.model = model;
        this.year = year;
        this.driver = driver;
        this.passenger = passenger;
        this.rear = rear;
        this.side = side;
        this.color = color;
        this.uuid = generateUUID();
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
                ", color='" + color + '\'' +
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
