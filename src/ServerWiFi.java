import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.io.PrintWriter;import java.net.Socket;public class ServerWiFi extends Thread {    private BufferedReader in_ch    = null;    private PrintWriter out         = null;    private Command command;    private int [] identification = {0x04, 0x00, 0x00, 0x00, 0x4e, 0x31};    private char [] dataRequest = {0x04, 0x02, 0x00, 0x00, 0x20, 0x51};    private char[] chBuffer;    private boolean isCyclicWork;    private Socket fromServer;    private char[] buffer = new char[30];    public ServerWiFi(Command command, char[] chBuffer, boolean isCyclicWork) {        this.command = command;        this.chBuffer = chBuffer;        this.isCyclicWork = isCyclicWork;        System.out.println("Welcome to server!");        try{            fromServer = new Socket("192.168.4.1",8108);            in_ch  = new BufferedReader(new InputStreamReader(fromServer.getInputStream()));            out = new PrintWriter(fromServer.getOutputStream());            System.out.println("Connected...");        } catch (Exception e) {            System.out.println("Can't open port!");            System.exit(1);        }    }    @Override    public void run() {        super.run();        switch (command) {            case IDENTIFICATION:                for (int ch: identification) {                    switch (ch){                        case 0:                            out.print(ch);                            out.print(ch);                            break;                        case 1:                        case 2:                        case 3:                        case 4:                        case 5:                        case 7:                        case 8:                        case 9:                            out.print(0x00);                            out.print(ch);                            break;                        default:                            out.print(ch);                            break;                    }                }                out.flush();                System.out.println("Send Identification.");                break;            case DATA_READ:                for (int ch: dataRequest) {                    switch (ch){                        case 0:                            out.print(ch);                            out.print(ch);                            break;                        case 1:                        case 2:                        case 3:                        case 4:                        case 5:                        case 7:                        case 8:                        case 9:                            out.print(0x00);                            out.print(ch);                            break;                        default:                            out.print(ch);                            break;                    }                }                out.flush();                System.out.println("Send Give me data.");                break;        }        ////        try {//            in_ch.read(buffer);//        } catch (IOException e) {//            e.printStackTrace();//        }        System.out.print("WiFi_OK. ");//        try {//            in_ch.read(chBuffer);//        } catch (IOException e) {//            e.printStackTrace();//        }////        System.out.print("The response of server: ");//        for (int i=0; i<chBuffer.length; i++){//            System.out.print(chBuffer[i]);//        }        try {            in_ch.close();            fromServer.close();        } catch (IOException e) {            e.printStackTrace();        }        out.close();//        try {//            Thread.sleep(500);//        } catch (Exception e){}        System.out.println("Close thread.");        System.out.println("Close thread.");        System.out.println();        if (isCyclicWork) {            ServerWiFi serverWiFi = new ServerWiFi(Command.DATA_READ, chBuffer, true);            serverWiFi.start();        }    }}