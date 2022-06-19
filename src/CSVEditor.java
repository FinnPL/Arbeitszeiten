import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;

public class CSVEditor {
    private ArbeitsTag[] Tage;
    private int index;

    public void load(File file, GUI gui) {
        try {

            long lines = Files.lines(file.toPath()).count();
            Tage = new ArbeitsTag[(int) lines + 10];
            Scanner scanner = new Scanner(file);
            gui.addProgressbar((int) lines);
            index = 0;
            while (scanner.hasNextLine()) {

                gui.updateProgressbar();
                String[] data = scanner.nextLine().split(";");

                if (!(data[0] == null || data[0].equals(""))) {

                    ArbeitsTag tag = new ArbeitsTag(data[0]);
                    if (data.length > 3) {
                        tag.loadFullDay(stringtoZeit(data[1]), stringtoZeit(data[2]), stringtoZeit(data[3]),
                                stringtoZeit(data[4]));
                    } else if (data.length > 2) {
                        tag.setStart(stringtoZeit(data[1]));
                        tag.setEnd(stringtoZeit(data[2]));
                    } else if (data.length > 1) {
                        tag.setStart(stringtoZeit(data[1]));
                    }
                    Tage[index] = tag;
                    index++;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Datei konnte nicht gelesen werden " + file.getAbsolutePath() + "\n" + e.getMessage());
        }
    }

    public void edit(GUI gui, String Date) {
        boolean gefunden = false;
        try {
            for (int i = 0; i < index; i++) {
                ArbeitsTag tag = Tage[i];
                if (tag != null & tag.getDate().equals(Date)) {
                    gefunden = true;
                    if (tag.getStart() == null) {
                        gui.guiMaker("Geben Sie die Startzeit ein: (HH:MM)");
                        while (gui.inputTime == null) {
                            Thread.sleep(100);
                        }
                        String x = gui.inputTime;
                        tag.setStart(stringtoZeit(x));
                    } else if (tag.getEnd() == null) {
                        gui.guiMaker("Sie haben um" + tag.getStart()
                                + " Uhr Angefangen zu Arbeiten. Geben Sie die Endzeit ein: (HH:MM)");
                        while (gui.inputTime == null) {
                            Thread.sleep(100);
                        }
                        String x = gui.inputTime;
                        tag.setEnd(stringtoZeit(x));
                        tag.calc();
                    }
                }
            }
            if (!gefunden) {
                ArbeitsTag tag = new ArbeitsTag(Date);
                gui.guiMaker("Geben Sie die Startzeit ein: (HH:MM)");
                while (gui.inputTime == null) {
                    Thread.sleep(100);
                }
                String x = gui.inputTime;
                tag.setStart(stringtoZeit(x));
                Tage[index] = tag;
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Daten konnte nicht bearbeitet werden " + "\n" + e.getMessage());
        }

    }

    public void save(File file, GUI gui) {
        try {
            PrintWriter out = new PrintWriter(file);
            for (ArbeitsTag tag : Tage) {
                if (tag != null) {
                    tag.print(out);
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Datei konnte nicht gespeichert werden " + file.getAbsolutePath() + "\n" + e.getMessage());
        }
    }

    /*
     * public void editRecord(GUI gui) {
     * 
     * java.util.Date date = new Date();
     * LocalDate localDate =
     * date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
     * boolean gefunden = false;
     * 
     * try {
     * PrintWriter out = new PrintWriter("temp.csv");// create a temp file
     * File filel = new File("arbeitszeiten.csv");
     * long lines = Files.lines(filel.toPath()).count();
     * gui.addProgressbar((int) lines);
     * Scanner s = new Scanner(new File("Arbeitszeiten.csv")); // open file
     * 
     * while (s.hasNextLine()) {
     * gui.updateProgressbar();
     * String[] y = s.nextLine().split(";");
     * if (!(y[0] == null || y[0].equals(""))) {
     * if (y[0].equals(
     * localDate.getDayOfMonth() + "." + localDate.getMonthValue() + "." +
     * localDate.getYear())) {
     * gefunden = true;
     * out.println();
     * out.print(y[0] + ";");
     * switch (y.length) {
     * case 1: // if the first time is empty
     * gui.guiMaker("Geben Sie die Startzeit ein: (H:M)");
     * while (gui.inputTime == null) {
     * Thread.sleep(100);
     * }
     * out.print(gui.inputTime + ";");
     * break;
     * case 2: // if the second time is empty
     * gui.guiMaker("Sie haben um " + y[1]
     * + " angefangen zu Arbeiten--- Geben Sie die Endzeit ein: (H:M)");
     * while (gui.inputTime == null) {
     * Thread.sleep(100);
     * }
     * String x = gui.inputTime;
     * out.print(y[1] + ";");
     * out.print(x + ";");
     * out.print(stringtoZeit(x).diff(stringtoZeit(y[1])).toString() + ";");
     * out.print(
     * stringtoZeit(x).diff(stringtoZeit(y[1])).diff(stringtoZeit("07:12")).toString
     * ()
     * + ";");
     * break;
     * default:
     * break;
     * }
     * 
     * } else {
     * for (String string : y) {
     * out.print(string + ";");
     * }
     * }
     * }
     * }
     * 
     * // if the current date is not found
     * if (!gefunden) {
     * gui.guiMaker("Geben Sie die Startzeit ein: (H:M)");
     * while (gui.inputTime == null) {
     * Thread.sleep(100);
     * }
     * String x = gui.inputTime;
     * out.println();
     * out.print(
     * localDate.getDayOfMonth() + "." + localDate.getMonthValue() + "." +
     * localDate.getYear() + ";");
     * out.print(x + ";");
     * }
     * out.close();
     * s.close();
     * File file = new File("Arbeitszeiten.csv");
     * file.delete();
     * File file2 = new File("temp.csv");
     * file2.renameTo(file);
     * 
     * } catch (
     * 
     * ParseException | InterruptedException | IOException e) {
     * System.out.println("Error " + e.getMessage());
     * e.printStackTrace();
     * throw new RuntimeException(e);
     * }
     * 
     * }
     */

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