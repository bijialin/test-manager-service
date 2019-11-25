package io.choerodon.test.manager.api.vo;

import java.util.List;

/**
 * @author: 25499
 * @date: 2019/11/14 13:02
 * @description:
 */
public class TestTreeFolderVO {
    private Long id;
    private List<Long> children;
    private Boolean hasChildren;
    private Boolean isExpanded;
    private Boolean isChildrenLoading;
    private TestIssueFolderVO issueFolderVO;
    private Boolean hasCase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public Boolean getChildrenLoading() {
        return isChildrenLoading;
    }

    public void setChildrenLoading(Boolean childrenLoading) {
        isChildrenLoading = childrenLoading;
    }

    public TestIssueFolderVO getIssueFolderVO() {
        return issueFolderVO;
    }

    public void setIssueFolderVO(TestIssueFolderVO issueFolderVO) {

        this.issueFolderVO = issueFolderVO;
    }

    public Boolean getHasCase() {
        return hasCase;
    }

    public void setHasCase(Boolean hasCase) {
        this.hasCase = hasCase;
    }
}
