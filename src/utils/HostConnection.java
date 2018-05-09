package utils;

import model.Member;
import model.Request;
import model.Response;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostConnection {

    private static final String HOST_IP = "localhost";
    private static final int PORT = 8000;

    private Socket socket;

    private Request request;

    private static HostConnection hostConnection;


    private HostConnection(){ }


    public static HostConnection getInstance() {
        if(hostConnection == null){
            hostConnection = new HostConnection();
        }
        return hostConnection;
    }

    public Response connect(Request request) throws IOException {

        //List<Member> members = null;

        Response response = new Response();
        try {
            socket = new Socket(HOST_IP, PORT);
            if(getRequest() != null){
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                out.writeObject(request);

                response = (Response) in.readObject();

                out.flush();
                socket.shutdownInput();
                socket.shutdownOutput();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Unable to connect to server!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }


/*    public void closeConnection() throws IOException {
        if(!socket.isClosed()){
            socket.close();
        }
    }*/
}
