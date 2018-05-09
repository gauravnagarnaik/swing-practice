package model.dao;

import model.Login_Info;
import model.Member;

import java.sql.SQLException;
import java.util.List;

public interface Repository {


    List<Member> getAllUsersForAdmin() throws SQLException;

    void upsertMember(Member member) throws SQLException;

    void deleteMember(long memberId) throws SQLException;

    List<Member> getAllUsersForUser() throws SQLException;

    Login_Info checkLogin(Login_Info login) throws SQLException;

    Member getMember(long memberId) throws SQLException;

    long getMemberIdFromPhoneNum(String phoneNumber) throws SQLException;

    void saveLoginInfo(Login_Info login_info) throws SQLException;

}
