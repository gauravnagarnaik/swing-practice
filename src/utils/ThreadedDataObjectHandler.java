package utils;

import model.Login_Info;
import model.Member;
import model.Request;
import model.Response;
import service.LoginService;
import service.MemberService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;


class ThreadedDataObjectHandler extends Thread {
    private Socket incoming;
    private static LoginService loginService;
    private static Member loggedInUser;
    private MemberService memberService;


    public ThreadedDataObjectHandler(Socket incoming) {
        this.incoming = incoming;
        loginService = LoginService.getLoginInstance();
        memberService = new MemberService();
        loggedInUser = LoginService.getLoginUser();
    }

    public void run() {


        try {

            ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());

            Request myObject = (Request) in.readObject();

            System.out.println("Message read: " + myObject.getMessage());


            Response response = null;

            try {
                switch (myObject.getMessage()) {
                    case "REGISTER":
                        createAccount(myObject);
                        break;
                    case "LOGIN":
                        response = new Response();
                        boolean checkLogin = fetchAccount(myObject);
                        if (!checkLogin) {
                            loggedInUser = LoginService.getLoginUser();
                            response.setMember(loggedInUser);
                        }
                        response.setLoginCheck(checkLogin);
                        break;
                    case "FETCH_USERS":
                        List<Member> members = fetchUsers();
                        response = new Response();
                        response.setMembers(members);
                        out.writeObject(response);
                        break;
                    case "DELETE":
                        deleteAccount(myObject);
                        break;
                    case "UPDATE":
                        updateAccount(myObject);
                        break;

                }
            } catch (Exception er) {
                er.printStackTrace();
                out.writeObject(response);
                out.flush();
                incoming.shutdownOutput();
            }
            out.writeObject(response);
            out.flush();
            incoming.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean fetchAccount(Request request) throws SQLException {
        if(request == null){
            throw new IllegalArgumentException("Empty Request");
        }

        Login_Info login_info = new Login_Info();
        login_info.setUserName(request.getUserName());
        login_info.setPassword(request.getPassword());
        return loginService.checkLogin(login_info);
    }

    private void updateAccount(Request request) throws SQLException, IllegalAccessException {
        // TODO Auto-generated method stub

        if(request == null) {
            throw new IllegalArgumentException("Empty User Body");
        }


        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());

        Login_Info login_info = new Login_Info();
        login_info.setUserName(request.getUserName());
        login_info.setPassword(request.getPassword());


        if(loggedInUser.getPhoneNumber().equals(request.getPhoneNumber())){
            member.setMemberId(loggedInUser.getMemberId());
            login_info.setMemberId(loggedInUser.getMemberId());
        }

        memberService.addMember(member, login_info);

    }

    private void deleteAccount(Request request) throws IllegalAccessException, SQLException {
        // TODO Auto-generated method stub
        if(request == null) {
            throw new IllegalArgumentException("Empty User Body");
        }

        memberService.deleteMember(request.getMemberId());
    }

    private List<Member> fetchUsers() throws SQLException {
        // TODO Auto-generated method stub
        return memberService.getAllMembers();
    }


    private void createAccount(Request request) throws IllegalAccessException, SQLException {
        // TODO Auto-generated method stub
        if(request == null) {
            throw new IllegalArgumentException("Empty User Body");
        }


        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());

        Login_Info login_info = new Login_Info();
        login_info.setUserName(request.getUserName());
        login_info.setPassword(request.getPassword());


        if(loggedInUser.getPhoneNumber().equals(request.getPhoneNumber())){
            member.setMemberId(loggedInUser.getMemberId());
            login_info.setMemberId(loggedInUser.getMemberId());
        }

        memberService.addMember(member, login_info);
    }
}