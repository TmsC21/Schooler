package sk.myProject.school.service;

import com.google.gson.Gson;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.service.devicetwin.*;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeviceTwinService {

    public static String connString = "HostName=stuFeiTcupela.azure-devices.net;SharedAccessKeyName=Diplomovka;SharedAccessKey=xS/K1mR4A2U3wzX91MrAQ4DMT1uS/78NPAIoTAe8BTY=";
    public static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
    private static String deviceId = "Nav2Device";

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
    }
}
