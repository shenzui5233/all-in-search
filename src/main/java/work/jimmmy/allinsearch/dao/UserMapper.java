package work.jimmmy.allinsearch.dao;

import org.apache.ibatis.annotations.Param;
import work.jimmmy.allinsearch.model.UserModel;

public interface UserMapper {
    UserModel selectByTelphoneAndPassword(@Param("telphone") String telphone, @Param("password") String password);

    Integer countAllUser();
}
