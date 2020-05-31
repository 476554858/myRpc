package protocol.http;

import framework.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public String post(String hostName, Integer port, Invocation invocation)throws Exception{
        URL url = new URL("http",hostName,port,"/");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(invocation);
        oos.flush();
        oos.close();

        InputStream inputStream = httpURLConnection.getInputStream();
        return IOUtils.toString(inputStream);
    }
}
