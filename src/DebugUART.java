import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.io.IOException;

public class DebugUART {
    public static void main(String[] args) throws InterruptedException, IOException {
        int[] measure = {0x52, 0xFF, 0x6B, 0x22};
        String[] portName = SerialPortList.getPortNames();
//        for (int i=0; i<portName.length; i++){
//            System.out.println(portName[i]);
//        }

        SerialPort serialPort = new SerialPort("COM3");
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_230400,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean flag = true;
        while(flag) {
            try {
                int i =6;
                String sAnswer = serialPort.readHexString(6);
                System.out.println();
                System.out.println(sAnswer);
                serialPort.writeIntArray(measure);
                flag = false;
            } catch (SerialPortException spEx) {
                System.out.print(".");
            }
        }
    }
}
