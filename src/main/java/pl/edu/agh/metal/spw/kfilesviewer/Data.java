package pl.edu.agh.metal.spw.kfilesviewer;

import javafx.geometry.Point3D;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Data {
    private List<Point3D> nodesCoordinates;
    private List<SolidElementDef> solidElementDefList;
    private BufferedReader bufferedReader;


    public Data(File file) throws CustomException {
        nodesCoordinates = new ArrayList<>();
        solidElementDefList = new ArrayList<>();
        loadDataFromFile(file);
    }


    private void loadDataFromFile(File file) throws CustomException {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            validateFirstLine();
            readNodesCoordinates();
            readSolidElementsDefinitions();
            bufferedReader.close();
        } catch (FileNotFoundException ignored) {
            throw new CustomException("File '" + file.getName() + "' does not exist.");
        } catch (IOException ignored) {
            throw new CustomException("Error while reading file: '" + file.getName() + "'.");
        } catch (Exception ignored) {
            throw new CustomException("Can not read file '" + file.getName() + "' - File format corrupted.");
        }
    }


    private void validateFirstLine() throws Exception {
        String line = bufferedReader.readLine();
        if (!"*NODE".equals(line)) {
            throw new Exception();
        }
    }


    private void readNodesCoordinates() throws IOException {
        String currentLine;
        while (!(currentLine = bufferedReader.readLine()).equals("*ELEMENT_SOLID")) {
            String[] numbers = currentLine.split(", ");
            nodesCoordinates.add(new Point3D(
                    Double.parseDouble(numbers[1]),
                    Double.parseDouble(numbers[2]),
                    Double.parseDouble(numbers[3])
            ));
        }
    }


    private void readSolidElementsDefinitions() throws IOException {
        String currentLine;
        while (!(currentLine = bufferedReader.readLine()).equals("*END")) {
            String[] numbers = currentLine.split(", ");
            solidElementDefList.add(new SolidElementDef(new int[]{
                    Integer.parseInt(numbers[2]),
                    Integer.parseInt(numbers[3]),
                    Integer.parseInt(numbers[4]),
                    Integer.parseInt(numbers[5]),
                    Integer.parseInt(numbers[6]),
                    Integer.parseInt(numbers[7]),
                    Integer.parseInt(numbers[8]),
                    Integer.parseInt(numbers[9])
            }));
        }
    }


    public List<Point3D> getNodesCoordinates() {
        return nodesCoordinates;
    }


    public List<SolidElementDef> getSolidElementDefList() {
        return solidElementDefList;
    }

}
