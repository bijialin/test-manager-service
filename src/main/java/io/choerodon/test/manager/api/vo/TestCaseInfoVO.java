package io.choerodon.test.manager.api.vo;

import java.util.Date;
import java.util.List;
import io.choerodon.agile.api.vo.IssueInfoDTO;
import io.choerodon.agile.api.vo.UserDTO;
import io.choerodon.test.manager.infra.dto.UserMessageDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhaotianxin
 * @since 2019/11/14
 */
public class TestCaseInfoVO {

    @ApiModelProperty(value = "用例Id")
    private Long caseId;

    @ApiModelProperty(value = "用例编号")
    private String caseNum;

    @ApiModelProperty(value = "概要")
    private String summary;

    @ApiModelProperty(value = "用例详情")
    private String description;

    @ApiModelProperty(value = "rank")
    private String rank;

    @ApiModelProperty(value = "文件夹")
    private String folder;

    @ApiModelProperty(value = "版本Id")
    private Long versionId;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @ApiModelProperty(value = "创建人")
    private UserMessageDTO createUser;

    @ApiModelProperty(value = "最后修改人")
    private UserMessageDTO lastUpdateUser;

    @ApiModelProperty(value = "创建时间")
    private Date creationDate;

    @ApiModelProperty(value = "修改时间")
    private Date lastUpdateDate;

    @ApiModelProperty(value = "用例关联的问题链接")
    private List<IssueInfoDTO> issuesInfos;

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public UserMessageDTO getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserMessageDTO createUser) {
        this.createUser = createUser;
    }

    public UserMessageDTO getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(UserMessageDTO lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<IssueInfoDTO> getIssuesInfos() {
        return issuesInfos;
    }

    public void setIssuesInfos(List<IssueInfoDTO> issuesInfos) {
        this.issuesInfos = issuesInfos;
    }
}
