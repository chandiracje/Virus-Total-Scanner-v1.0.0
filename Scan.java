import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class Scan {

    static void displaystart() {
        System.out.println("  _    _  _                    _______       _         _ ");
        System.out.println(" | |  | |(_)                  |__   __|     | |       | |");
        System.out.println(" | |  | | _  _ __  _   _  ___    | |   ___  | |_  __ _| |");
        System.out.println(" | |  | || || '__|| | | |/ __|   | |  / _ \\ | __|/ _` | |");
        System.out.println(" \\ \\__/ /| || |   | |_| |\\__ \\   | | | (_) || |_| (_| | |");
        System.out.println("  \\____/ |_||_|    \\__,_||___/   |_|  \\___/  \\__|\\__,_|_|");
        System.out.println("                                                         ");
        System.out.println("                                                         ");
        System.out.println("Virustotal Scanner Tool v1.0.0 by CJE");
        System.out.println("");
        System.out.println("-- (h) Scan for file hash");
        System.out.println("-- (i) Scan for ip");
        System.out.println("-- (d) Scan for domain");
        System.out.println("-- (e) Exit");
        System.out.println("");
        System.out.println("For bluk scan help run scan.java --help");
        System.out.println("");
        System.out.println("** Note **: for the bulk mode only .txt files are supported");
        System.out.println("** Note **: for the bulk mode only one type of scan is supported at a time");
        System.out.println(
                "** Special Note **: If you are using this tool for the first time it will ask for the API key");
        System.out.println("");
    }

    public static void fileHashScan(String hashToScan, String apiKey) {
        VTScannerhash hashScanner = new VTScannerhash(apiKey);

        try {
            System.out.println("Scanning hash " + hashToScan + "...");
            int maliciousCount = hashScanner.scan(hashToScan);

            if (maliciousCount > 0) {
                System.out.println("RESULT: [WARNING] " + maliciousCount + " security vendors flagged this file.");
            } else if (maliciousCount == 0) {
                System.out.println("RESULT: [CLEAN] No threats detected.");
            } else {
                System.out.println(
                        "RESULT: Could not retrieve data (Hash might be invalid or file never seen by VirusTotal).");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void ipScan(String ipAddr, String apiKey) {
        VTScanner vtscanner = new VTScanner(apiKey);

        try {
            System.out.println("Scanning " + ipAddr + "...");
            int maliciousCount = vtscanner.scan(ipAddr);

            if (maliciousCount > 0) {
                System.out.println(
                        "RESULT: [WARNING] " + maliciousCount + " security vendors flagged this IP.");
            } else if (maliciousCount == 0) {
                System.out.println("RESULT: [CLEAN] No threats detected.");
            } else {
                System.out.println(
                        "RESULT: Could not retrieve data (IP might be invalid or new to VirusTotal).");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void domainScan(String domain, String apiKey) {
        VTScannerDomain vtscannerdDomain = new VTScannerDomain(apiKey);

        try {
            System.out.println("Scanning " + domain + "...");
            int maliciousCount = vtscannerdDomain.scan(domain);

            if (maliciousCount > 0) {
                System.out.println(
                        "RESULT: [WARNING] " + maliciousCount + " security vendors flagged this domain.");
            } else if (maliciousCount == 0) {
                System.out.println("RESULT: [CLEAN] No threats detected.");
            } else {
                System.out.println(
                        "RESULT: Could not retrieve data (Domain might be invalid or new to VirusTotal).");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // main menu
    public static void mainMenu(String apiKey) {
        char userChoice = 'o';

        Scanner input = new Scanner(System.in);

        do {
            displaystart();
            System.out.print(">> ");
            userChoice = input.next().charAt(0);

            // file hash
            if (userChoice == 'h' || userChoice == 'H') {
                String doCntl = "c";

                do {
                    System.out.println("");
                    System.out.print("Enter the file hash to scan [e to exit]: ");
                    String fileHash = input.next();
                    doCntl = fileHash;

                    if (doCntl.equalsIgnoreCase("e")) {
                        break;
                    }
                    fileHashScan(fileHash, apiKey);
                } while (!doCntl.equalsIgnoreCase("e"));

                // ip scan
            } else if (userChoice == 'i' || userChoice == 'I') {
                String doCntl = "c";

                do {
                    System.out.println("");
                    System.out.print("Enter the ipv4 addr to scan (0:0:0:0) [e to exit]: ");
                    String ipAddr = input.next();
                    doCntl = ipAddr;

                    if (ipAddr.equalsIgnoreCase("e")) {
                        break;
                    }

                    if (apiKey.isEmpty() || ipAddr.isEmpty()) {
                        System.out.println("Error: Both API Key and IP are required.");
                        return;
                    }
                    ipScan(ipAddr, apiKey);

                } while (!doCntl.equalsIgnoreCase("e"));

                // domain scan
            } else if (userChoice == 'd' || userChoice == 'D') {
                String doCntl = "c";

                do {
                    System.out.println("");
                    System.out.print("Enter the domain to scan [e to exit]: ");
                    String domain = input.next();
                    doCntl = domain;

                    if (domain.equalsIgnoreCase("e")) {
                        break;
                    }

                    if (apiKey.isEmpty() || domain.isEmpty()) {
                        System.out.println("Error: Both API Key and Domain are required.");
                        return;
                    }
                    domainScan(domain, apiKey);

                } while (!doCntl.equalsIgnoreCase("e"));

            } else if (userChoice == 'e' || userChoice == 'E') {
                return;
            }
            System.out.println("");
        } while (userChoice != 'e' || userChoice != 'E');
    }

    public static void main(String[] args) {
        String filePath = "apikey.txt";
        String apiKey = "";
        String apikeyByUser = "";
        String scanFilePath = "";

        Scanner input = new Scanner(System.in);

        // cheking for apikey.txt file and creating it if it doesn't exist
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            apiKey = reader.readLine();
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading API key file: " + e.getMessage());
            System.out.print("Enter your virustotal api key: ");
            apikeyByUser = input.next();
            try {
                FileWriter writer = new FileWriter(filePath);
                writer.write(apikeyByUser);
                writer.close();
            } catch (IOException e1) {
                System.out.println("Error writing API key file: " + e1.getMessage());
                apiKey = apikeyByUser;
            }
        }

        // bulk scan
        if (args.length > 0) {
            System.out.println(
                    "  _    _  _                    _______       _         _    _____                                      ");
            System.out.println(
                    " | |  | |(_)                  |__   __|     | |       | |  / ____|                                     ");
            System.out.println(
                    " | |  | | _  _ __  _   _  ___    | |   ___  | |_  __ _| | | (___   ___  __ _  _ __   _ __    ___  _ __ ");
            System.out.println(
                    " | |  | || || '__|| | | |/ __|   | |  / _ \\ | __|/ _` | |  \\___ \\ / __|/ _` || '_ \\ | '_ \\  / _ \\| '__|");
            System.out.println(
                    " \\ \\__/ /| || |   | |_| |\\__ \\   | | | (_) || |_| (_| | |  ____) | (__| (_| || | | || | | ||  __/| |   ");
            System.out.println(
                    "  \\____/ |_||_|    \\__,_||___/   |_|  \\___/  \\__|\\__,_|_| |_____/ \\___|\\__,_||_| |_||_| |_| \\___||_|   ");
            System.out.println(
                    "                                                                                                       ");
            System.out.println("  _               _____  _  ______ ");
            System.out.println(" | |             / ____|| ||  ____|");
            System.out.println(" | |__   _   _  | |     | || |__   ");
            System.out.println(" | '_ \\ | | | | | |     | ||  __|  ");
            System.out.println(" | |_) || |_| | | |____ | || |____ ");
            System.out.println(" |_.__/  \\__, |  \\_____||_||______|");
            System.out.println("          __/ |                    ");
            System.out.println("         |___/                     ");

            if (args[0].equalsIgnoreCase("--help")) {
                System.out.println("Usage: java Scan [argument] [type] [your/file/path.txt]");
                System.out.println(
                        "Usage: for txt file with multiple values (java Scan --bulk -i -d -f your/file/path.txt)");
                System.out.println("Usage: use the type in order of pattern that in the txt file");
                System.out.println("");
                System.out.println("Arguments:");
                System.out.println("--help: Show this help message");
                System.out.println("--bulk: Bulk scan");
                System.out.println("--single: Single scan (eg: java Scan --single -i 127.0.0.1)");
                System.out.println("");
                System.out.println("Type:");
                System.out.println("-i: IP scan");
                System.out.println("-d: Domain scan");
                System.out.println("-f: File Hash scan");
            } else if (args[0].equalsIgnoreCase("--bulk")) {
                if (args[1].equalsIgnoreCase("-i") && args.length == 3) {
                    scanFilePath = args[2];
                    try (BufferedReader readerfile = new BufferedReader(new FileReader(scanFilePath))) {
                        String ipLine;
                        while ((ipLine = readerfile.readLine()) != null) {
                            ipScan(ipLine, apiKey);
                            System.out.println("");
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + e.getMessage());
                    }
                } else if (args[1].equalsIgnoreCase("-d") && args.length == 3) {
                    scanFilePath = args[2];
                    try (BufferedReader readerfile = new BufferedReader(new FileReader(scanFilePath))) {
                        String domainLine;
                        while ((domainLine = readerfile.readLine()) != null) {
                            domainScan(domainLine, apiKey);
                            System.out.println("");
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + e.getMessage());
                    }
                } else if (args[1].equalsIgnoreCase("-f") && args.length == 3) {
                    scanFilePath = args[2];
                    try (BufferedReader readerfile = new BufferedReader(new FileReader(scanFilePath))) {
                        String fileLine;
                        while ((fileLine = readerfile.readLine()) != null) {
                            fileHashScan(fileLine, apiKey);
                            System.out.println("");
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + e.getMessage());
                    }
                } else if (args.length == 4) {
                    if (args[0] == "--bulk" && args[1] == "-i") {
                        System.out.println("wait for update");
                    }
                }
            } else if (args[0].equalsIgnoreCase("--single")) {
                if (args[1].equalsIgnoreCase("-i") && args.length == 3) {
                    ipScan(args[2], apiKey);
                } else if (args[1].equalsIgnoreCase("-d") && args.length == 3) {
                    domainScan(args[2], apiKey);
                } else if (args[1].equalsIgnoreCase("-f") && args.length == 3) {
                    fileHashScan(args[2], apiKey);
                }
            }
        } else {
            // main menu
            mainMenu(apiKey);
        }
    }
}