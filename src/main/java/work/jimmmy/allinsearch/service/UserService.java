package work.jimmmy.allinsearch.service;

import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.model.UserModel;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    UserModel getUser(Integer id);

    UserModel register(UserModel registerUser) throws BusinessException, NoSuchAlgorithmException;

    UserModel login(String telphone, String password) throws NoSuchAlgorithmException, BusinessException;

    Integer countAllUser();
}
