package hmm.itam.mapper;

import hmm.itam.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> getUserList();

    void insertUser(UserVo userVo); // 로그인 사용자 등록

    UserVo getUserByHmm_id(String hmm_id); // 로그인 화면(ID 체크)

    UserVo getUserById(Long id); //

}
