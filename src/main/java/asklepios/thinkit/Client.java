package asklepios.thinkit;

import asklepios.thinkit.services.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.xmlrpc.client.XmlRpcClient;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.*;

@Command(name = "client", description = "this command will allow a Thinkiteer (a user) to specify a start and an end of the availability slot, offset to GMT (for timezone awareness) and a number of participants.", mixinStandardHelpOptions = true)
public class Client implements Runnable {

    @Option(names = { "-s", "--start" }, description = "Start of Availability slot")
    String startTime;
    @Option(names = { "-e", "--end" }, description = "End of Availability slot")
    String endTime;
    @Option(names = { "-o",
            "--offset" }, description = "Offset to GMT following the format gmt+X or gmt-Y where X and Y are the offset")
    String offset;
    @Option(names = { "-n",
            "--number" }, description = "Desired number of participants in the meeting, with a maximum of 3")
    Integer numberOfParticipants;

    // Command Line Colors to give it some vibe
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Client.class, args);
    }

    public void run() {
        Socket s;
        System.out.println(ANSI_GREEN + "Welcome to Think-it's Virtual Coffee Command Tool !" + ANSI_GREEN);
        if (numberOfParticipants < 4) {
            try {
                // Connect XML RPC Client to the Server
                XmlRpcClient client = new XmlRpcClient();
                XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
                config.setServerURL(new URL("http://localhost:9000/RPC2"));
                config.setEnabledForExtensions(true);
                client.setConfig(config);

                System.out.println(ANSI_WHITE + "Sending Request to Think-it Server !" + ANSI_WHITE);
                System.out.println();
                System.out.println();

                // Sending Request to the Server
                JSONObject virtualSlots = DataExchangeService.sendData(startTime, endTime, offset, numberOfParticipants,
                        client);

                System.out.println("Looking for available virtual coffee slots ...");
                System.out.println();
                System.out.println();

                // Showing Results if there is available slots
                if (!virtualSlots.toString().equals("{}")) {
                    final JSONArray names = virtualSlots.getJSONArray("names");
                    final JSONArray times = virtualSlots.getJSONArray("times");
                    if (names.length() > 0 && times.length() > 0) {

                        System.out.println(ANSI_GREEN + "We found some slots available for you !" + ANSI_GREEN);
                        System.out.println();
                        System.out.println();
                        System.out.println(ANSI_WHITE + "At these range of time :" + ANSI_WHITE);
                        System.out.println(times.get(0));
                        System.out.println();
                        System.out.println();
                        System.out.println("With the Thinkitsters :");
                        for (int i = 0; i < names.length(); i++) {
                            System.out.println();
                            System.out.println(ANSI_CYAN + "-" + names.get(i) + ANSI_CYAN);
                            System.out.println();
                        }

                    }
                } else {
                    System.out.println();
                    System.out.println();
                    // Showing This Message if there is no slots
                    System.out.println(ANSI_RED
                            + "We are Sorry ! Thinkiters are pretty busy at that time range ! Probably try again later ?"
                            + ANSI_RED);

                }

            } catch (IOException e1) {
                System.out.println();
                System.out.println();
                // Showing This Message if there is an error
                System.out.println(
                        ANSI_RED + "Something went wrong with your request ! Would you try later !" + ANSI_RED);

            }

        } else {
            // Showing This Message if the user put more than 4 guests
            System.out.println(ANSI_RED + "Wpuld you choose a number of guests less than 4 members ?" + ANSI_RED);
        }
    }
}
