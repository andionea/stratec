import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadFile {
    static ArrayList<Machine> machines = new ArrayList<>();
    static ArrayList<Part> parts = new ArrayList<>();
    static ArrayList<PartOperation> partsOp = new ArrayList<>();

    public static int findMachine(String machinename) {
        for (int i = 0; i < machines.size(); i++) {
            if (machines.get(i) != null && machines.get(i).getName().equals(machinename)) {
                return i;
            }
        }
        return -1;
    }

    public static String similarWord(String name) {
        int[] lengths = new int[machines.size()];
        for (int j = 0; j < machines.size(); j++) {
            String shorter = "";
            int ok = 0;
            if (machines.get(j).getName().length() <= name.length()) {
                shorter = machines.get(j).getName();
            } else shorter = name;
            for (int i = 0; i < shorter.length(); i++) {
                if (machines.get(j).getName().charAt(i) == name.charAt(i)) {
                    ok++;
                }
            }
            lengths[j] = ok;
        }

        int index = 0;
        int maxim = 0;
        for (int j = 0; j < lengths.length; j++) {
            if (lengths[j] > maxim) {
                maxim = lengths[j];
                index = j;
            }
        }
        return machines.get(index).getName();
    }

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert the path to the file: ");
        String filePath = scanner.nextLine();
        File file = new File(filePath);
        Scanner scan = null;
        boolean fileFound = false;
        do {
            try {
                scan = new Scanner(file);
                fileFound = true;
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
                System.out.println("Enter another file path or type 'stop' to quit the program: ");
                filePath = scanner.nextLine();
                if (filePath.equals("stop")) {
                    System.exit(0);
                }
                file = new File(filePath);
            }
        } while (!fileFound);
        String currentLine = "";
        int ok = 0;
        int aproved = 1;
        while (scan.hasNextLine()) {
            aproved = 1;
            if (currentLine.equals("Machine features:")) {
                aproved = 0;
            }
            if (currentLine.equals("Part list:")) {
                aproved = 0;
            }
            if (currentLine.equals("Part operations:")) {
                aproved = 0;
            }
            if (aproved == 1) {
                currentLine = scan.nextLine();
            }
            if (currentLine.equals("Available machines:")) {
                while (scan.hasNextLine()) {
                    currentLine = scan.nextLine();
                    if (currentLine.equals("Machine features:")) {
                        break;
                    } else if (!currentLine.isEmpty() && !currentLine.contains("#")) {
                        Machine machine = new Machine();
                        machine.setName(currentLine.substring(currentLine.indexOf(".") + 2));
                        machines.add(machine);
                    }
                }
            } else if (currentLine.equals("Machine features:")) {
                while (scan.hasNextLine()) {
                    currentLine = scan.nextLine();
                    if (currentLine.equals("Part list:")) {
                        break;
                    } else if (!currentLine.isEmpty() && !currentLine.contains("#")) {
                        if (currentLine.contains("Capacity")) {
                            String capacityString = currentLine.substring(currentLine.indexOf(":") + 2).trim();
                            String capacity = "";
                            if (capacityString.toLowerCase().contains("no limit")) {
                                capacity = "no limit";
                            } else {
                                String[] stringParts = capacityString.split(" ");
                                capacity = stringParts[2];
                            }
                            machines.get(ok).setCapacity(capacity);
                        } else if (currentLine.contains("Cooldown")) {
                            String cooldownString = currentLine.substring(currentLine.indexOf(":") + 2).trim();
                            int cooldown = 0;
                            if (!cooldownString.toLowerCase().equals("none")) {
                                try {
                                    cooldown = Integer.parseInt(cooldownString.split(" ")[0]);
                                } catch (NumberFormatException ignored) {
                                }
                            }
                            machines.get(ok).setCooldownInSeconds(cooldown);
                            ok++;
                        }
                    }
                }
            } else if (currentLine.equals("Part list:")) {
                while (scan.hasNextLine()) {
                    currentLine = scan.nextLine();
                    if (currentLine.equals("Part operations:")) {
                        break;
                    } else if (!currentLine.isEmpty() && !currentLine.contains("#")) {
                        Part part = new Part();
                        String partName = currentLine.substring(0, currentLine.indexOf('-') - 1);
                        partName = partName.substring(partName.indexOf(". ") + 2);
                        part.setName(partName);
                        int item = 0;
                        int index = currentLine.indexOf('-') + 2;
                        StringBuilder builder = new StringBuilder();
                        while (index < currentLine.length() && Character.isDigit(currentLine.charAt(index))) {
                            builder.append(currentLine.charAt(index));
                            index++;
                        }
                        if (builder.length() > 0) {
                            item = Integer.parseInt(builder.toString());
                        }
                        part.setItems(item);
                        parts.add(part);
                    }
                }
            } else if (currentLine.equals("Part operations:")) {
                ok = 0;
                int contor = 0;
                while (scan.hasNextLine()) {
                    currentLine = scan.nextLine();
                    if (currentLine.contains("-")) {
                        Pattern patternName = Pattern.compile("-\\s+(.*?):");
                        Matcher matcherName = patternName.matcher(currentLine);
                        String name = "";
                        if (matcherName.find()) {
                            name = matcherName.group(1);
                        }

                        Pattern patternTime = Pattern.compile(":\\s+(\\d+)\\s+seconds");
                        Matcher matcherTime = patternTime.matcher(currentLine);
                        int time = 0;
                        if (matcherTime.find()) {
                            time = Integer.parseInt(matcherTime.group(1));
                        }
                        PartOperation partoperation = new PartOperation();
                        partsOp.add(partoperation);
                        partsOp.get(contor).setTime(time);
                        int machineIndex = findMachine(name);
                        if (machineIndex == -1) {
                            name = similarWord(name);
                            machineIndex = findMachine(name);
                        }
                        partsOp.get(contor).setMachineUsed(machines.get(machineIndex));
                        parts.get(ok).addOperations(partsOp.get(contor));
                        contor++;
                    } else if (currentLine.isEmpty()) {
                        ok++;
                    }
                }
            }
        }

        System.out.println("\n");
        for (Machine machine : machines) {
            if (machine != null) {
                System.out.println(machine.toString());
            }
        }
        System.out.println("\n");
        for (Part part : parts) {
            if (part != null) {
                System.out.println(part.toString());
            }
        }
    }
}