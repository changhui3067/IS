//package service;
//
//import Dao.dao.UserMapper;
//import Dao.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Required;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
//import org.springframework.stereotype.Service;
//
///**
// * Created by Administrator on 2015/3/17.
// */
//@Service
//public class LoginService {
//    @Autowired(required = false)
//    private UserMapper userMapper;
//    @Autowired(required =  false)
//    User user;
//    @Autowired(required = false)
//    public int getLoginInfo(String userName,String password){
//        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
//        try {
//            user = userMapper.selectByUserName(userName);
//            String md5Pwd=md5.encodePassword(password, userName);
//            // System.out.println(md5Pwd);
//            if(user.getPassword().equals(md5Pwd)){
//                return user.getUserid();
//            }
//            else {
//                return -1;
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            return -2;
//        }
//
//
//
//
//    }
//
//
//}
