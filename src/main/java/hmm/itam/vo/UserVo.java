package hmm.itam.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long idx;
    private String hmmId;
    private String name;
    private String search;
    private String search1;
    private String comment;
    private String department;
    private String password;
    private String authority;
    private String chartType;
    private String departmentLocation;
    private String departmentRegion;
    private String departmentFloor;
}
