package Api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static spark.Spark.*;

public class ApiHandler {


    public static void main (String [] args) {

        String jsonData = getJsonData();

        // Create an account
        post("/api/createAccount", (req, res) -> true);

        // Login to an existing account
        get("/api/login", (req, res) -> true);


        // Get list of volunteering opportunities
        get("/api/getOpportunities", (req, res) -> jsonData);

    }

    private static String getJsonData() {
        Scanner sc = null;
        String data = "";
        try {
            sc = new Scanner(new File("/home/cunniemm/Desktop/Hackathon/data.json"));

            while (sc.hasNext()) {
                data += sc.next();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }

}
