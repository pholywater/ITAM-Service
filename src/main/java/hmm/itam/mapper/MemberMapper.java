package hmm.itam.mapper;

import hmm.itam.vo.AssetVo;
import hmm.itam.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insertMember(MemberVo memberVo); // 신규직원 등록

    List<MemberVo> getDepartmentList(); // datalist 자동완성 부서 검색 리스트

    List<MemberVo> getMemberList(); // datalist 자동완성 직원 이름 검색 리스트

    MemberVo getMemberByMemberId(String memberId); // 직원번호로 검색

    void updateMember(MemberVo memberVo); // 직원 정보 수정

    List<MemberVo> getHeaderDepartmentList();


    int countByMemberId(@Param("memberId") String memberId); // 업데이트 시 직원 있는지 체크


}
