import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class CSVEditor {

    public void editRecord(GUI gui) {

        java.util.Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean gefunden = false;

        try {
            PrintWriter out = new PrintWriter("temp.csv");// create a temp file
            File filel = new File("arbeitszeiten.csv");
            long lines = Files.lines(filel.toPath()).count();
            gui.addProgressbar((int) lines);
            Scanner s = new Scanner(new File("Arbeitszeiten.csv")); // open file

            while (s.hasNextLine()) {
                gui.updateProgressbar();
                String[] y = s.nextLine().split(";");
                if (!(y[0] == null || y[0].equals(""))) {
                    if (y[0].equals(
                            localDate.getDayOfMonth() + "." + localDate.getMonthValue() + "." + localDate.getYear())) {
                        gefunden = true;
                        out.println();
                        out.print(y[0] + ";");
                        switch (y.length) {
                            case 1: // if the first time is empty
                                gui.GUImaker("Geben Sie die Startzeit ein: (H:M)");
                                while (gui.inputTime == null) {
                                    Thread.sleep(100);
                                }
                                out.print(gui.inputTime + ";");
                                break;
                            case 2: // if the second time is empty
                                gui.GUImaker("Sie haben um " + y[1]
                                        + " angefangen zu Arbeiten--- Geben Sie die Endzeit ein: (H:M)");
                                while (gui.inputTime == null) {
                                    Thread.sleep(100);
                                }
                                String x = gui.inputTime;
                                out.print(y[1] + ";");
                                out.print(x + ";");
                                out.print(stringtoZeit(x).diff(stringtoZeit(y[1])).toString() + ";");
                                out.print(
                                        stringtoZeit(x).diff(stringtoZeit(y[1])).diff(stringtoZeit("07:12")).toString()
                                                + ";");
                                break;
                            default:
                                break;
                        }

                    } else {
                        for (String string : y) {
                            out.print(string + ";");
                        }
                    }
                }
            }

            // if the current date is not found
            if (!gefunden) {
                gui.GUImaker("Geben Sie die Startzeit ein: (H:M)");
                while (gui.inputTime == null) {
                    Thread.sleep(100);
                }
                String x = gui.inputTime;
                out.println();
                out.print(
                        localDate.getDayOfMonth() + "." + localDate.getMonthValue() + "." + localDate.getYear() + ";");
                out.print(x + ";");
            }
            out.close();
            s.close();
            File file = new File("Arbeitszeiten.csv");
            file.delete();
            File file2 = new File("temp.csv");
            file2.renameTo(file);

        } catch (

                ParseException | InterruptedException | IOException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Zeit stringtoZeit(String zeit) {
        Zeit y;
        try {
            String[] x = zeit.split(":");
            y = new Zeit(Integer.parseInt(x[0]), Integer.parseInt(x[1]));
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return y;

    }
}