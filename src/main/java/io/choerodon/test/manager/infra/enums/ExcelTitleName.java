package io.choerodon.test.manager.infra.enums;

/**
 * @author shinan.chen
 * @date 2019/7/16
 */
public class ExcelTitleName {
    private ExcelTitleName() {
    }

    public static final String FOLDER = "文件夹*";
    public static final String CASE_SUMMARY = "用例概要*";
    public static final String CASE_DESCRIPTION = "用例描述";
    public static final String CASE_CODE = "用例编号";
    public static final String PRIORITY = "优先级";
    public static final String ASSIGNER = "被指定人";
    public static final String COMPONENT = "模块";
    public static final String LINK_ISSUE = "关联的issue";
    public static final String TEST_STEP = "测试步骤";
    public static final String TEST_DATA = "测试数据";
    public static final String EXPECT_RESULT = "预期结果";
}