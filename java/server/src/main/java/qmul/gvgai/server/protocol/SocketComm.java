package qmul.gvgai.server.protocol;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketComm extends Comm {

    private int port;
    private Socket socket;
    private boolean end;
    private static int THRESHOLD = 60000;

    /**
     * Public constructor of the player.
     */
    public SocketComm(int port) {
        super();
        end = false;
        this.port = port;
        initBuffers();
    }

    /**
     * Creates the buffers for pipe communication.
     */
    @Override
    public void initBuffers() {
        try {
            //Accepting the socket connection.
            while (socket == null) {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.setPerformancePreferences(0, 2, 1);

                socket = serverSocket.accept();
                socket.setTcpNoDelay(true);
                socket.setSendBufferSize(65536 * 2);
            }


            //Initialize input and output through socket.
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

        } catch (java.net.BindException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start() {
        try {
            initBuffers();

            while (!end) {

            }
            //out.format("Sending back: " + received);

            // may want to close this client side instead
            socket.close();
            System.out.println("Closed socket");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
