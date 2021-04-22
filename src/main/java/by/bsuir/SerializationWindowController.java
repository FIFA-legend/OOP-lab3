package by.bsuir;

import by.bsuir.entities.*;
import by.bsuir.service.Load;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SerializationWindowController {

    @FXML
    private RadioButton binaryRadio;

    @FXML
    private ToggleGroup serializationType;

    @FXML
    private RadioButton xmlRadio;

    @FXML
    private RadioButton textRadio;

    @FXML
    private TextField pathInput;

    @FXML
    private Button fileButton;

    @FXML
    private Button serializeButton;

    @FXML
    private Button deserializeButton;

    @FXML
    private Button backButton;

    private File file;

    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> Load.getInstance().loadWindow("main_window.fxml"));

        fileButton.setOnAction(actionEvent -> {
            FileChooser chooser = new FileChooser();
            file = chooser.showOpenDialog(App.getStage());
            if (file == null || !file.getPath().endsWith(".txt")) {
                printInvalidFileError();
            } else {
                pathInput.setText(file.getAbsolutePath());
            }
        });

        serializeButton.setOnAction(actionEvent -> {
            if (binaryRadio.isSelected()) {
                binarySerialization();
            } else if (xmlRadio.isSelected()) {
                xmlSerialization();
            } else if (textRadio.isSelected()) {
                textSerialization();
            }
        });

        deserializeButton.setOnAction(actionEvent -> {
            if (binaryRadio.isSelected()) {
                binaryDeserialization();
            } else if (xmlRadio.isSelected()) {
                xmlDeserialization();
            } else if (textRadio.isSelected()) {
                textDeserialization();
            }
        });
    }

    private void xmlSerialization() {
        if (file != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                XmlMapper xmlMapper = new XmlMapper();
                for (Vehicle vehicle : App.getVehicles()) {
                    String xmlString = xmlMapper.writeValueAsString(vehicle);
                    bw.write(xmlString);
                }
                App.getVehicles().clear();
                printOK();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            printInvalidFileError();
        }
    }

    private void xmlDeserialization() {
        if (file != null) {
            XmlMapper xmlMapper = new XmlMapper();
            try {
                String str = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                while (!str.trim().isEmpty()) {
                    Vehicle vehicle = deserializeOneObject(str, xmlMapper);
                    App.getVehicles().add(vehicle);
                    String closeTag = "</" + vehicle.getClass().getSimpleName() + ">";
                    str = str.substring(str.indexOf(closeTag) + closeTag.length());
                }
                printOK();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            printInvalidFileError();
        }
    }

    private Vehicle deserializeOneObject(String str, XmlMapper xmlMapper) throws JsonProcessingException {
        String className = str.substring(1, str.indexOf(">"));
        String closeTag = "</" + className + ">";
        String xml = str.substring(0, str.indexOf(closeTag) + closeTag.length());
        if (Bicycle.class.getSimpleName().equals(className)) {
            return xmlMapper.readValue(xml, Bicycle.class);
        } else if (Motorcycle.class.getSimpleName().equals(className)) {
            return xmlMapper.readValue(xml, Motorcycle.class);
        } else if (Car.class.getSimpleName().equals(className)) {
            return xmlMapper.readValue(xml, Car.class);
        } else if (Truck.class.getSimpleName().equals(className)) {
            return xmlMapper.readValue(xml, Truck.class);
        } else {
            return null;
        }
    }

    private void textSerialization() {
        if (file != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                StringBuilder sb = new StringBuilder();
                sb.append(App.getVehicles().size()).append("\n");
                for (Vehicle vehicle : App.getVehicles()) {
                    serializeObject(vehicle, sb);
                }
                bw.write(sb.toString());
                App.getVehicles().clear();
                printOK();
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            printInvalidFileError();
        }
    }

    private void textDeserialization() {
        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                int count = Integer.parseInt(br.readLine());
                for (int i = 0; i < count; i++) {
                    Object o = deserializeObject(br);
                    if (o instanceof Vehicle) App.getVehicles().add((Vehicle) o);
                }
                printOK();
            } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            printInvalidFileError();
        }
    }

    private Object deserializeObject(BufferedReader br) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String line = br.readLine();
        Class<?> cl = Class.forName(line.split(" = ")[1]);
        Object o = cl.newInstance();
        while (true) {
            for (Field field : cl.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType() != String.class && isSerializable(field.getType().getInterfaces())) {
                    field.set(o, deserializeObject(br));
                } else {
                    String value = br.readLine().split(" = ")[1];
                    if (field.getType().isEnum()) {
                        field.set(o, Type.valueOf(value));
                    } else if (field.getType().getSimpleName().equals("int")) {
                        field.set(o, Integer.parseInt(value));
                    } else {
                        field.set(o, value);
                    }
                }
                field.setAccessible(false);
            }
            if (cl.getSuperclass() == Object.class) {
                break;
            } else {
                cl = cl.getSuperclass();
            }
        }
        return o;
    }

    private void serializeObject(Object object, StringBuilder sb) throws IllegalAccessException {
        sb.append("class = ")
                .append(object.getClass().getName())
                .append("\n");
        Class<?> cl = object.getClass();
        while (true) {
            for (Field field : cl.getDeclaredFields()) {
                serializeField(field, object, sb);
            }
            if (cl.getSuperclass() == Object.class) {
                break;
            } else {
                cl = cl.getSuperclass();
            }
        }
    }

    private void serializeField(Field field, Object object, StringBuilder sb) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == String.class || !isSerializable(field.getType().getInterfaces())) {
            sb.append(field.getName())
                    .append(" = ")
                    .append(field.get(object))
                    .append("\n");
        } else {
            serializeObject(field.get(object), sb);
        }
        field.setAccessible(false);
    }

    private boolean isSerializable(Class<?>[] array) {
        for (Class<?> cl : array) {
            if (cl == Serializable.class) return true;
        }
        return false;
    }

    private void binaryDeserialization() {
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                int count = ois.readInt();
                for (int i = 0; i < count; i++) {
                    App.getVehicles().add((Vehicle) ois.readObject());
                }
                printOK();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            printInvalidFileError();
        }
    }

    private void binarySerialization() {
        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeInt(App.getVehicles().size());
                for (Vehicle vehicle : App.getVehicles()) {
                    oos.writeObject(vehicle);
                }
                App.getVehicles().clear();
                printOK();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            printInvalidFileError();
        }
    }

    private void printOK() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Entities successfully serialized/deserialized.");
        alert.show();
    }

    private void printInvalidFileError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("You must open a file with the extension .txt");
        alert.show();
    }

}
