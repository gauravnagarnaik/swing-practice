package model.dao;

import model.Login_Info;
import model.Member;
import utils.MemberIdSeq;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {


    private DbConnection dbConnection = DbConnection.getDbInstance();

    private MemberIdSeq memberIdSeq = MemberIdSeq.getGetInstance();

    private static final String GET_ALL_USERS_FOR_ADMINS = "SELECT * FROM MEMBER";

    private static final String GET_USER_BY_FIRST_NAME = "SELECT MEMBER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUM FROM MEMBER WHERE FIRST_NAME = ?";

    private static final String GET_USER_BY_LAST_NAME = "SELECT MEMBER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUM FROM MEMBER WHERE LAST_NAME = ?";

    private static final String GET_USER_BY_PHONE_NUM = "SELECT MEMBER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUM FROM MEMBER WHERE PHONE_NUM = ?";

    private static final String GET_ALL_USERS_FOR_USER = "SELECT FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUM FROM MEMBER WHERE IS_ADMIN = FALSE";

    private static final String ADD_MEMBER = "INSERT INTO MEMBER(MEMBER_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUM, IS_ADMIN) VALUES(?,?,?,?,?,?)";

    private static final String UPDATE_USER = "UPDATE MEMBER SET FIRST_NAME = ?, LAST_NAME = ?, ADDRESS = ?, PHONE_NUM = ?, IS_ADMIN = ? WHERE MEMBER_ID = ?";

    private static final String DELETE_MEMBER = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";

    private static final String DELETE_LOGIN = "DELETE FROM LOGIN_INFO WHERE MEMBER_ID = ?";

    private static final String GET_MEMBER = "SELECT FIRST_NAME, LAST_NAME, ADDRESS, PHONE_NUM, IS_ADMIN FROM MEMBER WHERE MEMBER_ID = ?";

    private static final String CHECK_LOGIN = "SELECT MEMBER_ID, USER_NAME, PASSWORD FROM LOGIN_INFO WHERE USER_NAME = ? AND PASSWORD = ?";

    private static final String GET_MEMBER_ID_FROM_PHONE_NUM = "SELECT MEMBER_ID FROM MEMBER WHERE PHONE_NUM = ?";

    private static final String SAVE_LOGIN_INFO = "INSERT INTO LOGIN_INFO(MEMBER_ID, USER_NAME, PASSWORD) VALUES(?,?,?)";

    private static final String UPDATE_LOGIN_INFO_USER = "UPDATE LOGIN_INFO SET USER_NAME = ?, PASSWORD = ? WHERE MEMBER_ID = ?";

    private static final String GET_LOGIN_INFO = "SELECT USER_NAME, PASSWORD FROM SSS329.LOGIN_INFO WHERE MERCHANT_ID = ?";






    @Override
    public List<Member> getAllUsersForAdmin() throws SQLException {
        Connection connection = dbConnection.getConnection();

        List<Member> userList = new ArrayList<>();
        try{
            Statement getUserStmt = connection.createStatement();
            ResultSet rs = getUserStmt.executeQuery(GET_ALL_USERS_FOR_ADMINS);
            while(rs.next()) {
                Member user = new Member();
                user.setMemberId(rs.getLong("MEMBER_ID"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setPhoneNumber(rs.getString("PHONE_NUM"));
                user.setIsAdmin(rs.getBoolean("IS_ADMIN"));

                userList.add(user);
            }
        } catch (SQLException | NullPointerException  ex){
            ex.printStackTrace();
        }

        return userList;
    }


    @Override
    public List<Member> getAllUsersForUser() throws SQLException {
        Connection connection = dbConnection.getConnection();
        List<Member> userList = new ArrayList<>();
        try{
            Statement getUserStmt = connection.createStatement();
            ResultSet rs = getUserStmt.executeQuery(GET_ALL_USERS_FOR_USER);
            while(rs.next()) {
                Member user = new Member();
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setPhoneNumber(rs.getString("PHONE_NUM"));
                user.setIsAdmin(false);
                userList.add(user);
            }
        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
        }

        return userList;
    }


    @Override
    public Login_Info checkLogin(Login_Info login) throws SQLException {
        if(login == null){
            return null;
        }

        Login_Info login_info = null;

        Connection connection = dbConnection.getConnection();

        try{
            PreparedStatement statement = connection.prepareStatement(CHECK_LOGIN);
            statement.setString(1, login.getUserName());
            statement.setString(2, login.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                login_info = new Login_Info();
                login_info.setMemberId(resultSet.getLong("MEMBER_ID"));
                login_info.setUserName(resultSet.getString("USER_NAME"));
                login_info.setPassword(resultSet.getString("PASSWORD"));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }

        return login_info;
    }

    @Override
    public Member getMember(long memberId) throws SQLException {
        if(memberId == 0){
            return null;
        }
        Member loginMember = null;
        Connection connection = dbConnection.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(GET_MEMBER);
            statement.setLong(1, memberId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                loginMember = new Member();
                loginMember.setMemberId(memberId);
                loginMember.setFirstName(resultSet.getString("FIRST_NAME"));
                loginMember.setLastName(resultSet.getString("LAST_NAME"));
                loginMember.setAddress(resultSet.getString("ADDRESS"));
                loginMember.setPhoneNumber(resultSet.getString("PHONE_NUM"));
                loginMember.setIsAdmin(resultSet.getBoolean("IS_ADMIN"));
            }
        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
        }
        return loginMember;
    }


    @Override
    public long getMemberIdFromPhoneNum(String phoneNumber) throws SQLException {
        if(phoneNumber == null){
            return  -1;
        }
        long memberId = -1;
        Connection connection = dbConnection.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(GET_MEMBER_ID_FROM_PHONE_NUM);
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                memberId = resultSet.getLong("MEMBER_ID");
            }
        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
        }
        return memberId;

    }

    @Override
    public void saveLoginInfo(Login_Info login_info) throws SQLException {
        if(login_info == null){
            throw new IllegalArgumentException("Could not save login information : Invalid Login");
        }
        Connection connection = dbConnection.getConnection();
        try{
            if(getLoginInfoForMember(login_info.getMemberId()) == null){
                PreparedStatement statement = connection.prepareStatement(UPDATE_LOGIN_INFO_USER);
                statement.setString(1, login_info.getUserName());
                statement.setString(2, login_info.getPassword());
                statement.setLong(3, login_info.getMemberId());
                statement.executeUpdate();
            }
            PreparedStatement statement = connection.prepareStatement(SAVE_LOGIN_INFO);
            statement.setLong(1, login_info.getMemberId());
            statement.setString(2, login_info.getUserName());
            statement.setString(3, login_info.getPassword());
            statement.executeUpdate();
        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
        }
    }

    private Login_Info getLoginInfoForMember(long memberId) throws SQLException {
        if(memberId < 1){
            throw new IllegalArgumentException("Invalid Member ID");
        }
        Connection connection = dbConnection.getConnection();
        Login_Info login_info = null;
        try{
            PreparedStatement statement = connection.prepareStatement(GET_LOGIN_INFO);
            statement.setLong(1, memberId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                login_info = new Login_Info();
                login_info.setMemberId(memberId);
                login_info.setUserName(resultSet.getString("USER_NAME"));
                login_info.setPassword(resultSet.getString("PASSWORD"));
            }
        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
        }
        return login_info;
    }

    @Override
    public void upsertMember(Member member) throws SQLException {

        if(member == null){
            return;
        }

        Connection connection = dbConnection.getConnection();
        try{
            if(getMember(member.getMemberId()) != null) {
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
                statement.setString(1, member.getFirstName());
                statement.setString(2, member.getLastName());
                statement.setString(3, member.getAddress());
                statement.setString(4, member.getPhoneNumber());
                statement.setBoolean(5, member.getIsAdmin());
                statement.setLong(6, member.getMemberId());
                statement.executeUpdate();
            } else {
                PreparedStatement statement = connection.prepareStatement(ADD_MEMBER);
                statement.setLong(1, memberIdSeq.getMemberId());
                statement.setString(2, member.getFirstName());
                statement.setString(3, member.getLastName());
                statement.setString(4, member.getAddress());
                statement.setString(5, member.getPhoneNumber());
                statement.setBoolean(6, member.getIsAdmin());
                statement.executeUpdate();
            }
        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteMember(long memberId) throws SQLException {
        if(memberId == 0){
            return;
        }
        Connection connection = dbConnection.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(DELETE_MEMBER);

            statement.setLong(1, memberId);
            statement.executeUpdate();

            statement = connection.prepareStatement(DELETE_LOGIN);
            statement.setLong(1, memberId);
            statement.executeUpdate();

        } catch (SQLException | NullPointerException ex){
            ex.printStackTrace();
        }
    }


}
