package bank;

import bank.protocol.BankFacade;
import bank.protocol.InMemoryBank;
import bank.protocol.Request;
import bank.protocol.Response;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/bank")
public class Server {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 5003;
    private static final String DEFAULT_CONTEXT = "/";

    private static final BankFacade BANK_FACADE = new BankFacade(new InMemoryBank());

    public static void main(String[] args) throws Exception {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.valueOf(args[1]);
        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server(host, port, DEFAULT_CONTEXT, null, Server.class);
        server.start();
        System.out.println(String.format("Server started on port %s:%s", host, port));
        // prevent process exit
        while (true) {
            System.in.read();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.printf("New session %s\n", session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.printf("Session %s closed because of %s\n", session.getId(), closeReason);
    }

    @OnMessage
    public String onMessage(String message, Session session) throws IOException {
        Request request = Request.fromString(message);
        switch (request.actionId) {
            case Request.ACTION_GET_ACCOUNT_NUMBERS:
                return BANK_FACADE.getAccountNumbers(request).toString();
            case Request.ACTION_GET_ACCOUNT:
                return BANK_FACADE.getAccount(request).toString();
            case Request.ACTION_CREATE_ACCOUNT:
                return BANK_FACADE.createAccount(request).toString();
            case Request.ACTION_CLOSE_ACCOUNT:
                return BANK_FACADE.closeAccount(request).toString();
            case Request.ACTION_TRANSFER:
                return BANK_FACADE.transfer(request).toString();
            case Request.ACTION_DEPOSIT:
            case Request.ACTION_WITHDRAW:
                return BANK_FACADE.transaction(request).toString();
            default:
                return Response.ERROR_BAD_REQUEST.toString();
        }
    }

    @OnError
    public void onError(Throwable exception, Session session) throws IOException {
        System.out.println("an error occurred on connection " + session.getId() + ":" + exception);
        exception.printStackTrace();
        session.getBasicRemote().sendText(Response.ERROR_INTERNAL_ERROR.toString());
    }
}