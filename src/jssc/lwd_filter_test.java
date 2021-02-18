package jssc;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by 412 on 23.10.2018.
 */
public class lwd_filter_test {

    public static void main(String[] args) throws IOException {

        String nameDataFile   = "data.txt";
        String nameFilterFile = "filter.txt";
        String buffer         = "";

        FileInputStream stream = new FileInputStream("C://Users/412/IdeaProjects/TCP_IP_Server/" + nameDataFile);
        BufferedReader  reader = new BufferedReader(new InputStreamReader(stream));
        ArrayList<Double> listSignal = new ArrayList<>();

        /*while ((buffer = reader.readLine()) != null) {
            listSignal.add(Double.parseDouble(buffer));
        }*/
        for (int i = 0; i < 11340; i++) {
            listSignal.add(Math.random());
        }

        stream = new FileInputStream("C://Users/412/IdeaProjects/TCP_IP_Server/" + nameFilterFile);
        reader = new BufferedReader(new InputStreamReader(stream));
        ArrayList<Integer> listFilter = new ArrayList<>();

        while ((buffer = reader.readLine()) != null) {
            listFilter.add(Integer.parseInt(buffer));
        }

        reader.close();
        stream.close();

        double [] y = new double[ listSignal.size() +  listFilter.size()];

        for(int n=0; n < listSignal.size(); ++n) {
            for (int m = 0; m < listFilter.size(); ++m) {
                y[n+m] += listFilter.get(m)*listSignal.get(n);
            }
        }

        double [] x = new double[ listSignal.size()/* +  listFilter.size()*/];

        for(int n=0; n < listSignal.size(); ++n) {
            for (int m = 0; m < listFilter.size(); ++m) {
                y[n] += listFilter.get(m)*listSignal.get(n);
            }
        }

        FileWriter outStream = new FileWriter("C://Users/412/IdeaProjects/TCP_IP_Server/filteredData.txt");
        BufferedWriter  writer = new BufferedWriter(outStream);

        for (int i = 0; i < x.length; i++) {
            writer.write(String.valueOf(y[i])+ "\r\n");
        }

    }
}
