package io.choerodon.test.manager.infra.dto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import io.choerodon.mybatis.entity.BaseDTO;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Table(name = "test_cycle_case")
public class TestCycleCaseDTO extends BaseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long executeId;

    private Long cycleId;

    private Long issueId;

    private String rank;

    private Long executionStatus;

    private Long assignedTo;

    private Long projectId;

    @Column(name = "description")
    private String comment;

    @Transient
    private String executionStatusName;

    @Transient
    private String cycleName;

    @Transient
    private String folderName;

    @Transient
    private Long versionId;

    @Transient
    private Long lastExecuteId;

    @Transient
    private Long nextExecuteId;

    private Long objectVersionNumber;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Date creationDate;

    private Date lastUpdateDate;

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Transient
    List<TestCycleCaseAttachmentRelDTO> caseAttachment;

    @Transient
    List<TestCycleCaseDefectRelDTO> caseDefect;

    @Transient
    List<TestCycleCaseDefectRelDTO> subStepDefects;

    @Transient
    List<TestCycleCaseStepDTO> cycleCaseStep;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setId(Long id) {
        executeId = id;
    }

    public Long getExecuteId() {
        return executeId;
    }

    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getExecutionStatusName() {
        return executionStatusName;
    }

    public void setExecutionStatusName(String executionStatusName) {
        this.executionStatusName = executionStatusName;
    }

    public Long getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(Long executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getComment() {
        return comment;
    }

    public String getDescription() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<TestCycleCaseAttachmentRelDTO> getCaseAttachment() {
        return caseAttachment;
    }

    public void setCaseAttachment(List<TestCycleCaseAttachmentRelDTO> caseAttachment) {
        this.caseAttachment = caseAttachment;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<TestCycleCaseDefectRelDTO> getCaseDefect() {
        return caseDefect;
    }

    public void setCaseDefect(List<TestCycleCaseDefectRelDTO> caseDefect) {
        this.caseDefect = caseDefect;
    }

    public List<TestCycleCaseDefectRelDTO> getSubStepDefects() {
        return subStepDefects;
    }

    public void setSubStepDefects(List<TestCycleCaseDefectRelDTO> subStepDefects) {
        this.subStepDefects = subStepDefects;
    }

    public List<TestCycleCaseStepDTO> getCycleCaseStep() {
        return cycleCaseStep;
    }

    public void setCycleCaseStep(List<TestCycleCaseStepDTO> cycleCaseStep) {
        this.cycleCaseStep = cycleCaseStep;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getLastExecuteId() {
        return lastExecuteId;
    }

    public void setLastExecuteId(Long lastExecuteId) {
        this.lastExecuteId = lastExecuteId;
    }

    public Long getNextExecuteId() {
        return nextExecuteId;
    }

    public void setNextExecuteId(Long nextExecuteId) {
        this.nextExecuteId = nextExecuteId;
    }

    @Override
    public Long getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    @Override
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    @Override
    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }
}