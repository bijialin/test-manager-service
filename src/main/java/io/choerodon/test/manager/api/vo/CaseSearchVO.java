package io.choerodon.test.manager.api.vo;

import org.hzero.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * @author superlee
 * @since 2020-06-18
 */
public class CaseSearchVO {

    private List<String> contents;
    @Encrypt
    private SearchArgs searchArgs;

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public SearchArgs getSearchArgs() {
        return searchArgs;
    }

    public void setSearchArgs(SearchArgs searchArgs) {
        this.searchArgs = searchArgs;
    }

    public static class SearchArgs {

        private String summary;

        @Encrypt
        private Long executionStatus;
        @Encrypt
        private Long assignUser;

        @Encrypt
        private Long previousExecuteId;

        @Encrypt
        private Long nextExecuteId;

        public Long getPreviousExecuteId() {
            return previousExecuteId;
        }

        public void setPreviousExecuteId(Long previousExecuteId) {
            this.previousExecuteId = previousExecuteId;
        }

        public Long getNextExecuteId() {
            return nextExecuteId;
        }

        public void setNextExecuteId(Long nextExecuteId) {
            this.nextExecuteId = nextExecuteId;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public Long getExecutionStatus() {
            return executionStatus;
        }

        public void setExecutionStatus(Long executionStatus) {
            this.executionStatus = executionStatus;
        }

        public Long getAssignUser() {
            return assignUser;
        }

        public void setAssignUser(Long assignUser) {
            this.assignUser = assignUser;
        }
    }
}
