package io.choerodon.test.manager.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.choerodon.agile.api.dto.ProductVersionDTO;
import io.choerodon.core.exception.CommonException;
import io.choerodon.test.manager.api.dto.TestCycleDTO;
import io.choerodon.test.manager.app.service.TestCycleService;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleE;
import io.choerodon.test.manager.domain.service.ITestCycleService;
import io.choerodon.test.manager.domain.test.manager.factory.TestCycleEFactory;
import io.choerodon.test.manager.infra.feign.ProductionVersionClient;
import io.choerodon.agile.api.dto.ProductVersionPageDTO;
import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.core.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Component
public class TestCycleServiceImpl implements TestCycleService {
	@Autowired
	ITestCycleService iTestCycleService;

	@Autowired
	ProductionVersionClient productionVersionClient;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TestCycleDTO insert(TestCycleDTO testCycleDTO) {
		return ConvertHelper.convert(iTestCycleService.insert(ConvertHelper.convert(testCycleDTO, TestCycleE.class)), TestCycleDTO.class);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(TestCycleDTO testCycleDTO) {
		iTestCycleService.delete(ConvertHelper.convert(testCycleDTO, TestCycleE.class));

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TestCycleDTO update(TestCycleDTO testCycleDTO) {
		TestCycleE temp = TestCycleEFactory.create();
		temp.setCycleId(testCycleDTO.getCycleId());
		TestCycleE temp1 = temp.queryOne();
		if (testCycleDTO.getType().equals(TestCycleE.FOLDER)) {
			temp1.setCycleName(testCycleDTO.getCycleName());
			temp1.setObjectVersionNumber(testCycleDTO.getObjectVersionNumber());
		} else if (testCycleDTO.getType().equals(TestCycleE.CYCLE)) {
			Optional.ofNullable(testCycleDTO.getBuild()).ifPresent(v -> temp1.setBuild(v));
			Optional.ofNullable(testCycleDTO.getCycleName()).ifPresent(v -> temp1.setCycleName(v));
			Optional.ofNullable(testCycleDTO.getDescription()).ifPresent(v -> temp1.setDescription(v));
			Optional.ofNullable(testCycleDTO.getEnvironment()).ifPresent(v -> temp1.setEnvironment(v));
			Optional.ofNullable(testCycleDTO.getFromDate()).ifPresent(v -> temp1.setFromDate(v));
			Optional.ofNullable(testCycleDTO.getToDate()).ifPresent(v -> temp1.setToDate(v));
		}
		return ConvertHelper.convert(iTestCycleService.update(temp1), TestCycleDTO.class);

	}

	public TestCycleDTO getOneCycle(Long cycleId) {
		TestCycleE testCycleE = TestCycleEFactory.create();
		testCycleE.setCycleId(cycleId);
		testCycleE.querySelf();
		return ConvertHelper.convert(testCycleE.queryOne(), TestCycleDTO.class);
	}

	@Override
	public List<TestCycleDTO> getTestCycle(Long versionId) {
		return ConvertHelper.convertList(iTestCycleService.queryCycleWithBar(versionId), TestCycleDTO.class);
	}

	@Override
	public List<TestCycleDTO> filterCycleWithBar(String filter) {

		JSONObject object = JSON.parseObject(filter);
		ResponseEntity<List<ProductVersionDTO>> dto = productionVersionClient.listByProjectId(object.getLong("projectId"));
		List<ProductVersionDTO> versions = dto.getBody();

		if (versions.size() == 0) {
			return new ArrayList<>();
		}
		List<TestCycleDTO> cycles = ConvertHelper.convertList(iTestCycleService.filterCycleWithBar(object.getString("parameter"),
				versions.stream().map(v -> v.getVersionId()).toArray(Long[]::new)), TestCycleDTO.class);
		cycles.forEach(v -> {
			for (ProductVersionDTO u : versions) {
				if (v.getVersionId().equals(u.getVersionId())) {
					v.setVersionName(u.getName());
					v.setVersionStatusName(u.getStatusName());
					break;
				}
			}
		});
		return cycles;
	}

	@Override
	public ResponseEntity<Page<ProductVersionPageDTO>> getTestCycleVersion(Long projectId, Map<String, Object> searchParamMap) {
		return productionVersionClient.listByOptions(projectId, searchParamMap);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TestCycleDTO cloneCycle(Long cycleId, String cycleName, Long projectId) {
		TestCycleE testCycleE = TestCycleEFactory.create();
		testCycleE.setCycleId(cycleId);
		List<TestCycleE> list = iTestCycleService.querySubCycle(testCycleE);
		if (!(list.size() == 1 && list.get(0).getCycleName() != cycleName)) {
			throw new CommonException("error.test.cycle.clone.duplicate.name");
		}
		TestCycleE newTestCycleE = TestCycleEFactory.create();
		newTestCycleE.setCycleName(cycleName);
		newTestCycleE.setType(TestCycleE.CYCLE);
		return ConvertHelper.convert(iTestCycleService.cloneCycle(list.get(0), newTestCycleE, projectId), TestCycleDTO.class);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TestCycleDTO cloneFolder(Long cycleId, TestCycleDTO testCycleDTO, Long projectId) {
		TestCycleE testCycleE = TestCycleEFactory.create();
		testCycleE.setCycleId(cycleId);
		List<TestCycleE> list = iTestCycleService.querySubCycle(testCycleE);
		if (list.size() != 1) {
			throw new CommonException("error.test.cycle.clone.");
		}

		return ConvertHelper.convert(iTestCycleService.cloneFolder(list.get(0), ConvertHelper.convert(testCycleDTO, TestCycleE.class), projectId), TestCycleDTO.class);
	}
}
