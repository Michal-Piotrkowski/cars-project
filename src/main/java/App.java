import static spark.Spark.*;

import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import spark.Request;
import spark.Response;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class App {
    public static ArrayList<Car> cars = new ArrayList<Car>();
    public static ArrayList<CarSearch> cars2 = new ArrayList<CarSearch>();
    public static Integer i = 1;

    public static void main(String[] args) {
        port(getHerokuPort());
        staticFiles.location("static");

        post("/text", (req, res) -> showCars(req, res));
        post("/text2", (req, res) -> showCars2(req, res));
        post("/add", (req, res) -> addCar(req, res));
        post("/addCars", (req, res) -> addCars(req, res));
        post("/addCars2", (req, res) -> addCars2(req, res));
        post("/DELETE", (req, res) -> delCar(req, res));
        post("/UPDATE", (req, res) -> updCar(req, res));
        post("/invoice", (req, res) -> generateInvoice(req, res)); // generowanie faktury
        post("/generateInvoicesAllCars", (req, res) -> generateInvoicesAllCars(req, res)); // generowanie faktury
        post("/generateInvoicesByYear", (req, res) -> generateInvoicesByYearCars(req, res)); // generowanie faktury
        get("/download/:id", (req, res) -> generateInvoices(req, res));
        get("/downloadall", (req, res) -> generateInvoicesAll(req, res));
        get("/downloadByYear", (req, res) -> generateInvoicesByYear(req, res));
    }

    static int getHerokuPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    static String addCar(Request req, Response res) {
        Gson gson = new Gson();
        Car car = gson.fromJson(req.body(), Car.class);
        car.setUuid(car.generateUUID());
        cars.add(car);
        return gson.toJson(car, Car.class);
    }

    static String addCars(Request req, Response res) {
        for (int i = 10; i > 0; i--) {
            randomValues();
        }
        return "s";
    }

    static String addCars2(Request req, Response res) {
        for (int i = 10; i > 0; i--) {
            randomValues2();
        }
        return "s";
    }

    static String updCar(Request req, Response res) {
        Gson gson = new Gson();
        Car car = gson.fromJson(req.body(), Car.class);
        int i = 0;
        for (Car element : cars) {
            String uuid = element.uuid.toString();
            String req_uuid = car.uuid.toString();
            if (uuid.equals(req_uuid)) {
                cars.set(i, car);
                Type listType = new TypeToken<ArrayList<Car>>() {
                }.getType();
                System.out.println(gson.toJson(cars, listType));
                return gson.toJson(cars, listType);
            }
            i++;
        }
        return "s";
    }

    static String delCar(Request req, Response res) {
        for (Car element : cars) {
            String uuid = element.uuid.toString();
            String req_uuid = req.body().toString();
            if (uuid.equals(req_uuid)) {
                cars.remove(element);
                return uuid;
            }
        }
        return "s";
    }

    public static Object showCars(Request req, Response res) {
        Type listType = new TypeToken<ArrayList<Car>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.toJson(cars, listType);
    }

    public static Object showCars2(Request req, Response res) {
        Type listType = new TypeToken<ArrayList<CarSearch>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.toJson(cars2, listType);
    }

    public static Object randomValues() {
        String[] carBrands = {"BMW", "Mercedes-Benz", "Audi", "Opel", "Skoda", "Tesla"};
        String[] colors = {"#white", "#black", "#yellow", "#red", "#green", "#blue", "#orange"};
        int model = (int) (Math.random() * ((carBrands.length - 1) - 0 + 1)) + 0;
        Integer randomYear = (int) (Math.random() * (2022 - 1950 + 1)) + 1950;
        int driver = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int passenger = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int rear = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int side = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int randomColor = (int) (Math.random() * ((colors.length - 1) - 0 + 1)) + 0;
        boolean boolValue1;
        boolean boolValue2;
        boolean boolValue3;
        boolean boolValue4;
        if (driver >= 1) {
            boolValue1 = true;
        } else {
            boolValue1 = false;
        }

        if (passenger >= 1) {
            boolValue2 = true;
        } else {
            boolValue2 = false;
        }

        if (rear >= 1) {
            boolValue3 = true;
        } else {
            boolValue3 = false;
        }

        if (side >= 1) {
            boolValue4 = true;
        } else {
            boolValue4 = false;
        }

        Airbag driverAirbag = new Airbag("driver", boolValue1);
        Airbag passengerAirbag = new Airbag("passenger", boolValue2);
        Airbag rearAirbag = new Airbag("rear", boolValue3);
        Airbag sideAirbag = new Airbag("side", boolValue4);
        UUID uuid = Generators.randomBasedGenerator().generate();
        Car car = new Car(carBrands[model], randomYear, driverAirbag, passengerAirbag, rearAirbag, sideAirbag, colors[randomColor], uuid);
        car.setUuid(car.generateUUID());
        cars.add(car);
        return car;
    }

    public static Object randomValues2() {
        String[] carBrands = {"BMW", "Mercedes-Benz", "Audi", "Opel", "Skoda", "Tesla"};
        String[] colors = {"#white", "#black", "#yellow", "#red", "#green", "#blue", "#orange"};
        int model = (int) (Math.random() * ((carBrands.length - 1) - 0 + 1)) + 0;
        Integer randomYear = (int) (Math.random() * (2022 - 1950 + 1)) + 1950;
        int driver = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int passenger = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int rear = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int side = (int) (Math.random() * (1 - 0 + 1)) + 0;
        int randomColor = (int) (Math.random() * ((colors.length - 1) - 0 + 1)) + 0;
        int randomYearSold = (int) (Math.random() * (2022 - randomYear + 1)) + randomYear;
        int randomMonthSold = (int) (Math.random() * (12 - 1 + 1)) + 1;
        int randomDaySold = (int) (Math.random() * (31 - 1 + 1)) + 1;
        int price = (int) (Math.random() * (9999 - 5000 + 1)) + 5000;
        int vat = (int) (Math.random() * (23 - 0 + 1)) + 0;
        boolean boolValue1;
        boolean boolValue2;
        boolean boolValue3;
        boolean boolValue4;
        if (driver >= 1) {
            boolValue1 = true;
        } else {
            boolValue1 = false;
        }

        if (passenger >= 1) {
            boolValue2 = true;
        } else {
            boolValue2 = false;
        }

        if (rear >= 1) {
            boolValue3 = true;
        } else {
            boolValue3 = false;
        }

        if (side >= 1) {
            boolValue4 = true;
        } else {
            boolValue4 = false;
        }

        Airbag driverAirbag = new Airbag("driver", boolValue1);
        Airbag passengerAirbag = new Airbag("passenger", boolValue2);
        Airbag rearAirbag = new Airbag("rear", boolValue3);
        Airbag sideAirbag = new Airbag("side", boolValue4);
        UUID uuid = Generators.randomBasedGenerator().generate();
        String pathToImage = "/carsImages/";
        switch (carBrands[model]) {
            case "BMW":
                pathToImage += "BMW.png";
                break;
            case "Mercedes-Benz":
                pathToImage += "mercedes.jpg";
                break;
            case "Opel":
                pathToImage += "opel.jpg";
                break;
            case "Tesla":
                pathToImage += "tesla.jpeg";
                break;
            case "Audi":
                pathToImage += "audi.jpg";
                break;
            case "Skoda":
                pathToImage += "skoda.jpg";
                break;
        }
        Date date = new Date();
        date.setYear(randomYearSold - 1900);
        date.setMonth(randomMonthSold);
        date.setDate(randomDaySold);
        CarSearch car = new CarSearch(carBrands[model], randomYear, driverAirbag, passengerAirbag, rearAirbag, sideAirbag, colors[randomColor], pathToImage, date, price, vat);
        car.setUuid(car.generateUUID());
        cars2.add(car);
        return car;
    }

    public static Object generateInvoice(Request req, Response res) throws DocumentException, IOException {
        HashMap<String, BaseColor> map = new HashMap<>() {{
            put("red", BaseColor.RED);
            put("black", BaseColor.BLACK);
            put("orange", BaseColor.ORANGE);
            put("white", BaseColor.LIGHT_GRAY);
            put("yellow", BaseColor.YELLOW);
            put("green", BaseColor.GREEN);
            put("blue", BaseColor.BLUE);
        }};
        Gson gson = new Gson();
        Car car = gson.fromJson(req.body(), Car.class);
        Airbag air1 = car.driver;
        Airbag air2 = car.passenger;
        Airbag air3 = car.side;
        Airbag air4 = car.rear;
        Document document = new Document(); // dokument pdf
        String path = "katalog/plik.pdf"; // lokalizacja zapisu
        i++;
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        Font fontbold = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, BaseColor.BLACK);
        Font fontb = FontFactory.getFont(FontFactory.COURIER, 32, Font.BOLD, BaseColor.BLACK);
        String fontColor = car.color.substring(1);
        Font fontc = FontFactory.getFont(FontFactory.COURIER, 16, map.get(fontColor));
        Font fontsmall = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph p = new Paragraph("Faktura dla: " + car.uuid, fontbold);
        Paragraph p2 = new Paragraph("Model: " + car.model, fontb);
        Paragraph p3 = new Paragraph("kolor " + car.color, fontc);
        Paragraph p4 = new Paragraph("rok: " + car.year, fontsmall);
        Paragraph p5 = new Paragraph("poduszka: " + air1.name + "-" + air1.value, fontsmall);
        Paragraph p6 = new Paragraph("poduszka: " + air2.name + "-" + air2.value, fontsmall);
        Paragraph p7 = new Paragraph("poduszka: " + air3.name + "-" + air3.value, fontsmall);
        Paragraph p8 = new Paragraph("poduszka: " + air4.name + "-" + air4.value, fontsmall);

        String pathToImage = "src/main/resources/static/carsImages/";
        switch (car.model) {
            case "BMW":
                pathToImage += "BMW.png";
                break;
            case "Mercedes-Benz":
                pathToImage += "mercedes.jpg";
                break;
            case "Opel":
                pathToImage += "opel.jpg";
                break;
            case "Tesla":
                pathToImage += "tesla.jpeg";
                break;
            case "Audi":
                pathToImage += "audi.jpg";
                break;
            case "Skoda":
                pathToImage += "skoda.jpg";
                break;
        }
        Image img = Image.getInstance(pathToImage);
        document.add(p);
        document.add(p2);
        document.add(p3);
        document.add(p4);
        document.add(p5);
        document.add(p6);
        document.add(p7);
        document.add(p8);
        document.add(img);
        document.close();
        Invoice document1 = new Invoice(car.uuid, document);
        return document1;
    }

    public static Object generateInvoices(Request req, Response res) throws IOException {
        System.out.println(req.url());
        res.type("application/octet-stream"); //
        res.header("Content-Disposition", "attachment; filename=plik.pdf"); // nagłówek

        OutputStream outputStream = res.raw().getOutputStream();
        outputStream.write(Files.readAllBytes(Path.of("katalog/plik.pdf"))); // response pliku do przeglądarki
        return "s";
    }

    public static Object generateInvoicesAll(Request req, Response res) throws IOException {
        System.out.println(req.url());
        res.type("application/octet-stream"); //
        res.header("Content-Disposition", "attachment; filename=plikAll.pdf"); // nagłówek

        OutputStream outputStream = res.raw().getOutputStream();
        outputStream.write(Files.readAllBytes(Path.of("katalog/plikAll.pdf"))); // response pliku do przeglądarki
        return "s";
    }

    public static Object generateInvoicesByYear(Request req, Response res) throws IOException {
        System.out.println(req.url());
        res.type("application/octet-stream"); //
        res.header("Content-Disposition", "attachment; filename=plikByYear.pdf"); // nagłówek

        OutputStream outputStream = res.raw().getOutputStream();
        outputStream.write(Files.readAllBytes(Path.of("katalog/plikByYear.pdf"))); // response pliku do przeglądarki
        return "s";
    }

    private static Object generateInvoicesAllCars(Request req, Response res) throws FileNotFoundException, DocumentException {
        Document document = new Document(); // dokument pdf
        String path = "katalog/plikAll.pdf"; // lokalizacja zapisu
        i++;
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        Font fontbold = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, BaseColor.BLACK);
        Font fontb = FontFactory.getFont(FontFactory.COURIER, 32, Font.BOLD, BaseColor.BLACK);
        Font fontc = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.RED);
        Font fontsmall = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm");
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(dtf);
        Paragraph p = new Paragraph("Faktura: VAT/" + date, fontb);
        Paragraph p2 = new Paragraph("Nabywca: sprzedawca: firma sprzedająca auta", fontsmall);
        Paragraph p3 = new Paragraph("Sprzedawca: nabywca:  nabywca", fontsmall);
        Paragraph p4 = new Paragraph("Faktura za wszystkie auta", fontc);
        document.add(p);
        document.add(p2);
        document.add(p3);
        document.add(p4);
        System.out.println(cars2.size());
        PdfPTable table = new PdfPTable(4);

        PdfPCell ch = new PdfPCell(new Phrase("lp", fontsmall));
        PdfPCell c2h = new PdfPCell(new Phrase("cena", fontsmall));
        PdfPCell c3h = new PdfPCell(new Phrase("VAT", fontsmall));
        PdfPCell c4h = new PdfPCell(new Phrase("wartosc", fontsmall));
        table.addCell(ch);
        table.addCell(c2h);
        table.addCell(c3h);
        table.addCell(c4h);
        PdfPCell cell;
        double amount = 0;
        int lp = 1;
        for (CarSearch car : cars2) {
            cell = new PdfPCell(new Phrase(lp + ".", fontsmall));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(car.price + "zl", fontsmall));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(car.vat + "%", fontsmall));
            table.addCell(cell);
            double vat = car.vat / 100.00;
            cell = new PdfPCell(new Phrase((float) (car.price + (car.price * vat)) + "zl", fontsmall));
            table.addCell(cell);
            amount = amount + car.price + car.price * vat;
            lp++;
        }
        document.add(table);
        Paragraph pa = new Paragraph("DO ZAPLATY: " + amount + " PLN", fontb);
        document.add(pa);
        document.close();
        UUID uuid = Generators.randomBasedGenerator().generate();
        Invoice document1 = new Invoice(uuid, document);
        return uuid;
    }

    private static Object generateInvoicesByYearCars(Request req, Response res) throws FileNotFoundException, DocumentException {
        String year = req.body();
        Document document = new Document(); // dokument pdf
        String path = "katalog/plikByYear.pdf"; // lokalizacja zapisu
        i++;
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        Font fontbold = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, BaseColor.BLACK);
        Font fontb = FontFactory.getFont(FontFactory.COURIER, 32, Font.BOLD, BaseColor.BLACK);
        Font fontc = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.RED);
        Font fontsmall = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm");
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(dtf);
        Paragraph p = new Paragraph("Faktura: VAT/" + date, fontb);
        Paragraph p2 = new Paragraph("Nabywca: sprzedawca: firma sprzedająca auta", fontsmall);
        Paragraph p3 = new Paragraph("Sprzedawca: nabywca:  nabywca", fontsmall);
        Paragraph p4 = new Paragraph("Faktura za auta z roku: " + year, fontc);
        document.add(p);
        document.add(p2);
        document.add(p3);
        document.add(p4);
        PdfPTable table = new PdfPTable(4);

        PdfPCell ch = new PdfPCell(new Phrase("lp", fontsmall));
        PdfPCell c2h = new PdfPCell(new Phrase("cena", fontsmall));
        PdfPCell c3h = new PdfPCell(new Phrase("VAT", fontsmall));
        PdfPCell c4h = new PdfPCell(new Phrase("wartosc", fontsmall));
        table.addCell(ch);
        table.addCell(c2h);
        table.addCell(c3h);
        table.addCell(c4h);
        PdfPCell cell;
        double amount = 0;
        int lp = 1;
        for (CarSearch car : cars2) {
            System.out.println(year);
            String yearc = car.year.toString();
            if (yearc == year) {
                cell = new PdfPCell(new Phrase(lp + ".", fontsmall));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(car.price + "zl", fontsmall));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(car.vat + "%", fontsmall));
                table.addCell(cell);
                double vat = car.vat / 100.00;
                cell = new PdfPCell(new Phrase((float) (car.price + (car.price * vat)) + "zl", fontsmall));
                table.addCell(cell);
                amount = amount + car.price + car.price * vat;
                lp++;
            }
        }
        document.add(table);
        Paragraph pa = new Paragraph("DO ZAPLATY: " + amount + " PLN", fontb);
        document.add(pa);
        document.close();
        UUID uuid = Generators.randomBasedGenerator().generate();
        Invoice document1 = new Invoice(uuid, document);
        return uuid;
    }
}
