package sk.myProject.school.service;

import com.google.gson.Gson;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.service.devicetwin.*;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.*;

@NoArgsConstructor
public class DeviceTwinService {

//    public static String connString = "HostName=stuFeiTcupela.azure-devices.net;SharedAccessKeyName=Diplomovka;SharedAccessKey=xS/K1mR4A2U3wzX91MrAQ4DMT1uS/78NPAIoTAe8BTY=";
    public String connString;
    public static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
//    private static String deviceId = "Nav2Device";
    private String deviceId;


    public DeviceTwinService(String connString, String deviceId) {
        this.connString = connString;
        this.deviceId = deviceId;
    }

    public static class DeviceTwinStatusCallBack implements IotHubEventCallback {
        @Override
        public void execute(IotHubStatusCode status, Object context) {
            System.out.println("IoT Hub responded to device twin operation with status " + status.name());
        }
    }

    public String getData() {
        try {
            DeviceTwin twinClient = DeviceTwin.createFromConnectionString(connString);
            DeviceTwinDevice device = new DeviceTwinDevice(deviceId);
            System.out.println("Device twin before update:");
            twinClient.getTwin(device);
            Map<String, Object> jsonMap = new HashMap<>();
            Set<Pair> reportedProperties = device.getReportedProperties();
            for (Pair property : reportedProperties) {
                jsonMap.put(property.getKey(), property.getValue());
            }
            return new Gson().toJson(jsonMap);
        } catch (IotHubException | IOException e) {
            return e.getMessage();
        }
//        try {
//            String jsonMap = "{    \"MessageIndex\": 4.0,    \"Message\": [        {            \"index\": 1.0,            \"Position\": {                \"x\": 0.4738,                \"y\": -1.9466,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.1323,                \"w\": 0.9912            }        },        {            \"index\": 2.0,            \"Position\": {                \"x\": 0.4632,                \"y\": -1.8955,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.2715,                \"w\": 0.9624            }        },        {            \"index\": 3.0,            \"Position\": {                \"x\": 0.461,                \"y\": -1.8744,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.3617,                \"w\": 0.9323            }        },        {            \"index\": 4.0,            \"Position\": {                \"x\": 0.4677,                \"y\": -1.8636,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.5316,                \"w\": 0.847            }        },        {            \"index\": 5.0,            \"Position\": {                \"x\": 0.4552,                \"y\": -1.8763,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.6887,                \"w\": 0.725            }        },        {            \"index\": 6.0,            \"Position\": {                \"x\": 0.4409,                \"y\": -1.9255,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.8264,                \"w\": 0.5631            }        },        {            \"index\": 7.0,            \"Position\": {                \"x\": 0.3875,                \"y\": -2.0149,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.9184,                \"w\": 0.3956            }        },        {            \"index\": 8.0,            \"Position\": {                \"x\": 0.3091,                \"y\": -2.0688,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.9777,                \"w\": 0.2102            }        },        {            \"index\": 9.0,            \"Position\": {                \"x\": 0.2376,                \"y\": -2.1006,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": -0.9979,                \"w\": 0.0652            }        },        {            \"index\": 10.0,            \"Position\": {                \"x\": 0.1795,                \"y\": -2.0958,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": 0.9985,                \"w\": 0.0553            }        },        {            \"index\": 11.0,            \"Position\": {                \"x\": 0.1125,                \"y\": -2.0787,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": 0.9821,                \"w\": 0.1884            }        },        {            \"index\": 12.0,            \"Position\": {                \"x\": 0.0746,                \"y\": -2.0625,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": 0.9525,                \"w\": 0.3045            }        },        {            \"index\": 13.0,            \"Position\": {                \"x\": 0.0395,                \"y\": -2.0341,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": 0.9123,                \"w\": 0.4095            }        },        {            \"index\": 14.0,            \"Position\": {                \"x\": -0.0086,                \"y\": -1.956,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": 0.9542,                \"w\": 0.2992            }        },        {            \"index\": 15.0,            \"Position\": {                \"x\": -0.0069,                \"y\": -1.9503,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": 0.9814,                \"w\": 0.1918            }        },        {            \"index\": 16.0,            \"Position\": {                \"x\": -0.0101,                \"y\": -1.9536,                \"z\": 0.0            },            \"Orientation\": {                \"x\": 0.0,                \"y\": 0.0,                \"z\": 0.9963,                \"w\": 0.0861            }        }    ]}";
//            Gson gson = new Gson();
//            Object jsonObject = gson.fromJson(jsonMap, Object.class);
//            return new Gson().toJson(jsonObject);
//        } catch (Exception e) {
//            return e.getMessage();
//        }
    }
//public String getData() {
//    try {
//        Random random = new Random();
//        int numPoints = 10; // Number of points to generate
//
//        Map<String, Object> jsonData = new HashMap<>();
//        jsonData.put("MessageIndex", numPoints);
//
//        Map<String, Object>[] messageArray = new Map[numPoints];
//        for (int i = 0; i < numPoints; i++) {
//            Map<String, Object> pointData = new HashMap<>();
//            pointData.put("index", (double) (i + 1)); // 1-indexed
//            pointData.put("Position", generateRandomPosition(random));
//            pointData.put("Orientation", generateRandomOrientation(random));
//            messageArray[i] = pointData;
//        }
//
//        jsonData.put("Message", messageArray);
//
//        return new Gson().toJson(jsonData);
//    } catch (Exception e) {
//        return e.getMessage();
//    }
//}

    private Map<String, Double> generateRandomPosition(Random random) {
        Map<String, Double> position = new HashMap<>();
        position.put("x", random.nextDouble() * 2 - 1); // Range: -1 to 1
        position.put("y", random.nextDouble() * 2 - 1); // Range: -1 to 1
        position.put("z", random.nextDouble() * 2 - 1); // Range: -1 to 1
        return position;
    }

    private Map<String, Double> generateRandomOrientation(Random random) {
        Map<String, Double> orientation = new HashMap<>();
        orientation.put("x", random.nextDouble()); // Example range: 0 to 1
        orientation.put("y", random.nextDouble()); // Example range: 0 to 1
        orientation.put("z", random.nextDouble()); // Example range: 0 to 1
        orientation.put("w", random.nextDouble()); // Example range: 0 to 1
        return orientation;
    }
}
