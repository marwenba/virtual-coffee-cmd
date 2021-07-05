package asklepios.thinkit.services;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

import asklepios.thinkit.models.Request;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.json.JSONObject;

public class DataExchangeService {
    public static final String ANSI_RED = "\u001B[31m";

    // this Function will convert the Hour (string) Input into 24 hours format
    // integer
    static int timeConverter(String time) {
        int t = 0;
        int inputTime = Integer.parseInt(time.replaceAll("[^0-9]", ""));
        if (time.indexOf("pm") != -1) {
            t = inputTime + 12;

        } else {
            t = inputTime;
        }

        return t;
    }

    // Main Function that will communicate with the server
    public static JSONObject sendData(String startTime, String endTime, String offset, int participants,
            XmlRpcClient client) throws IOException {
        Request request = new Request();
        JSONObject obj = new JSONObject();
        List<Integer> range = new ArrayList<Integer>();

        int startAv = timeConverter(startTime);
        int endAv = timeConverter(endTime);
        range.add(startAv);
        range.add(endAv);
        request.setRange(range);
        request.setOffset(offset.toUpperCase());
        request.setParticipants(participants);
        Object[] objects = new Object[] { request.toString() };
        String result = null;
        try {
            // Sending the request and receiving the result using XML RPC
            Object resulty = (String) client.execute("virtualcoffee.responsefromRequest", objects);
            result = resulty.toString();

            // Result to Json
            obj = new JSONObject(result.toString());

        } catch (XmlRpcException e) {
            System.out.println(ANSI_RED + "Something went wrong with your request ! Would you try later !" + ANSI_RED);
        }

        return obj;
    }

}
