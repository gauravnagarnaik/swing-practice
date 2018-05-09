package service;


import model.Login_Info;
import model.Member;
import model.dao.Repository;
import model.dao.RepositoryImpl;

import java.sql.SQLException;

public class LoginService {

    private static LoginService loginInstance = null;
    private static Member loginUser;

    private Repository repository;

    private LoginService(){
        repository = new RepositoryImpl();
    }

    public static LoginService getLoginInstance(){
        if(loginInstance == null){
            loginInstance =  new LoginService();
        }

        return loginInstance;
    }

    public static Member getLoginUser() {
        return loginUser;
    }

    private static void setLoginUser(Member loginUser) {
        LoginService.loginUser = loginUser;
    }

    public boolean isAdminLogin(){
        if(loginUser == null){
            throw new IllegalArgumentException("No Member Logged In");
        }
        return loginUser.getIsAdmin();
    }


    public boolean checkLogin(Login_Info login) throws SQLException {
        if (login == null || login.getUserName() == null || login.getPassword() == null){
            throw new IllegalArgumentException("Empty Login");
        }

        Login_Info checkedLogin = repository.checkLogin(login);

        if(checkedLogin == null){
            return false;
        }
        setLoginUser(repository.getMember(checkedLogin.getMemberId()));
        return true;
    }

}