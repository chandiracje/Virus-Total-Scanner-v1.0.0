import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//ip scanner
public class VTScanner {
    private String apiKey;

    public VTScanner(String apiKey) {
        this.apiKey = apiKey;
    }

    public int scan(String ip) throws Exception {
        String urlString = "https://www.virustotal.com/api/v3/ip_addresses/" + ip;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("x-apikey", this.apiKey);
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == 401)
            throw new Exception("Invalid API Key");
        if (responseCode == 404)
            throw new Exception("IP not found in VirusTotal database");
        if (responseCode != 200)
            throw new Exception("HTTP Error Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        Pattern p = Pattern.compile("\"malicious\":\\s*(\\d+)");
        Matcher m = p.matcher(response.toString());

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return -1;
    }
}

// file hash scanner
class VTScannerhash {
    private String apiKey;

    public VTScannerhash(String apiKey) {
        this.apiKey = apiKey;
    }

    public int scan(String hash) throws Exception {
        String urlString = "https://www.virustotal.com/api/v3/files/" + hash;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("x-apikey", this.apiKey);
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == 401)
            throw new Exception("Invalid API Key");
        if (responseCode == 404)
            throw new Exception("Hash not found in VirusTotal database");
        if (responseCode != 200)
            throw new Exception("HTTP Error Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        Pattern p = Pattern.compile("\"malicious\":\\s*(\\d+)");
        Matcher m = p.matcher(response.toString());

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return -1;
    }
}

// domain scanner
class VTScannerDomain {
    private String apiKey;

    public VTScannerDomain(String apiKey) {
        this.apiKey = apiKey;
    }

    public int scan(String domain) throws Exception {
        String urlString = "https://www.virustotal.com/api/v3/domains/" + domain;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("x-apikey", this.apiKey);
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == 401)
            throw new Exception("Invalid API Key");
        if (responseCode == 404)
            throw new Exception("Domain not found in VirusTotal database");
        if (responseCode != 200)
            throw new Exception("HTTP Error Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        Pattern p = Pattern.compile("\"malicious\":\\s*(\\d+)");
        Matcher m = p.matcher(response.toString());

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return -1;
    }
}