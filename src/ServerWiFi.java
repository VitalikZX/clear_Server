import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.io.PrintWriter;import java.net.Socket;public class ServerWiFi extends Thread {    private BufferedReader in_ch    = null;    private PrintWriter out         = null;    private Socket fromServer       = null;    private Command command;    private char[] chBuffer;    private boolean isCyclicWork;    private char[] buffer = new char[30];    public ServerWiFi(Command command, char[] chBuffer, boolean isCyclicWork) {        this.command = command;        this.chBuffer = chBuffer;        this.isCyclicWork = isCyclicWork;        System.out.println("Welcome to server!");        try{            fromServer = new Socket("192.168.4.1",8108);            System.out.println("Connected...");            in_ch  = new BufferedReader(new InputStreamReader(fromServer.getInputStream()));            out = new PrintWriter(fromServer.getOutputStream());        } catch (Exception e) {            System.out.println("Can't open port!");            System.exit(1);        }    }    @Override    public void run() {        super.run();        switch (command) {            case IDENTIFICATION:                out.println("Identification.");                System.out.println("Send Identification.");                break;            case DATA_READ:                out.println("Give me data.");                break;        }        out.flush();        try {            in_ch.read(buffer);        } catch (IOException e) {            e.printStackTrace();        }        System.out.print("WiFi_OK. ");//        try {//            in_ch.read(chBuffer);//        } catch (IOException e) {//            e.printStackTrace();//        }////        System.out.print("The response of server: ");//        for (int i=0; i<chBuffer.length; i++){//            System.out.print(chBuffer[i]);//        }        out.close();        try {            in_ch.close();            fromServer.close();        } catch (IOException e) {            e.printStackTrace();        }        System.out.println();        System.out.println();        try {            Thread.sleep(500);        } catch (Exception e){}        if (isCyclicWork) {            ServerWiFi serverWiFi = new ServerWiFi(Command.DATA_READ, chBuffer, true);            serverWiFi.start();        }    }}