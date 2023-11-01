package hmm.itam.mapper;

import hmm.itam.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> getUserList();
    UserVo getUserByHmm_id(String hmm_id); // 로그인 화면(ID, pw 체크)
    UserVo getUserById(Long id); //



    void insertUser(UserVo userVo); // 로그인 사용자 등록
    void updateUser(UserVo userVo); // 회원 정보 수정
    void deleteUser(Long id); // 회원 정보 삭제


}
