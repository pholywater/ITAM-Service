package hmm.itam.mapper;

import hmm.itam.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberVo> getMemberList();

    void insertMember(MemberVo memberVo); // 신규직원 등록

}
