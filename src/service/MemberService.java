package service;

import model.Login_Info;
import model.Member;
import model.dao.Repository;
import model.dao.RepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class MemberService {

    Repository repository;

    LoginService loginService;


    public MemberService(){
        repository = new RepositoryImpl();
        loginService = LoginService.getLoginInstance();
    }

    public void updateUser(Member member, Login_Info login_info) throws IllegalAccessException, SQLException {
        if(member == null || login_info == null){
            throw new IllegalArgumentException("No user information Entered");
        }

        if(isAdminLogin()){
            throw new IllegalAccessException("Add / Delete can only be performed by an Admin");
        }

        member.setIsAdmin(false);
        repository.upsertMember(member);
        long memberId = repository.getMemberIdFromPhoneNum(member.getPhoneNumber());

        if(memberId == -1){
            throw new IllegalArgumentException("Error Saving Login Information");
        }

        login_info.setMemberId(memberId);
        repository.saveLoginInfo(login_info);

    }

    public void addMember(Member member, Login_Info login_info) throws IllegalAccessException, SQLException {
        if(member == null || login_info == null){
            throw new IllegalArgumentException("No user information Entered");
        }

        if(!isAdminLogin()){
            throw new IllegalAccessException("Add / Delete can only be performed by an Admin");
        }

        repository.upsertMember(member);
        long memberId = repository.getMemberIdFromPhoneNum(member.getPhoneNumber());

        if(memberId == -1){
            throw new IllegalArgumentException("Error Saving Login Information");
        }

        login_info.setMemberId(memberId);
        repository.saveLoginInfo(login_info);
    }


    public void deleteMember(long memberId) throws IllegalAccessException, SQLException {
        if(memberId == -1 || memberId == 0){
            throw new IllegalArgumentException("No member id provided");
        }

        if(!isAdminLogin()){
            throw new IllegalAccessException("Add / Delete can only be performed by an Admin");
        }

        repository.deleteMember(memberId);
    }




    private boolean isAdminLogin() {
        return loginService.isAdminLogin();
    }


    public List<Member> getAllMembers() throws SQLException {

        if(!isAdminLogin()){
            return repository.getAllUsersForUser();
        }
        return repository.getAllUsersForAdmin();
    }


}
