//package service;
//
//import Dao.dao.UserMapper;
//import Dao.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
//import org.springframework.stereotype.Service;
//
///**
// * Created by Administrator on 2015/3/17.
// */
//@Service
//public class RegisterService {
//    @Autowired(required = false)
//    private UserMapper userMapper;
//    @Autowired(required = false)
//    private User user;
//    public String getRegInfo(String userName,String email,String password,boolean sex){
//        user=new User();
//        user.setUsername(userName);
//        user.setEmailaddress(email);
//        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
//        String md5Pwd=md5PasswordEncoder.encodePassword(password,userName);
//        user.setPassword(md5Pwd);
//        user.setSex(sex);
//        try {
//            userMapper.insert(user);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return "insert failed in insert into db";
//        }
//        return "success";
//
//    }
//
//}
