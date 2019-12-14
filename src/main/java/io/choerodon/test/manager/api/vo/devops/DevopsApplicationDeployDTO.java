package io.choerodon.test.manager.api.vo.devops;

import io.choerodon.test.manager.api.vo.ApplicationDeployVO;

public class DevopsApplicationDeployDTO {
    private Long appVersionId;
    private Long environmentId;
    private String values;
    private Long appId;
    private String type;
    private Long appInstanceId;
    private Long commandId;
    private String instanceName;
    private boolean isNotChange;

    public Long getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(Long appVersionId) {
        this.appVersionId = appVersionId;
    }

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAppInstanceId() {
        return appInstanceId;
    }

    public void setAppInstanceId(Long appInstanceId) {
        this.appInstanceId = appInstanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public boolean getIsNotChange() {
        return isNotChange;
    }

    public void setIsNotChange(boolean isNotChange) {
        this.isNotChange = isNotChange;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public DevopsApplicationDeployDTO(ApplicationDeployVO deployDTO, Long appInstanceId, String values) {
        this.appVersionId = deployDTO.getAppVersionId();
        this.environmentId = deployDTO.getEnvironmentId();
        this.values = values;
        this.appId = deployDTO.getAppId();
        this.instanceName = "att-" + deployDTO.getAppId()+ "-" + deployDTO.getAppVersionId() + "-" + appInstanceId;
    }
}