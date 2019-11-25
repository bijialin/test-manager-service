/* eslint-disable react/state-in-constructor */
import React, {
  Component, useState, useEffect, useMemo, useReducer,
} from 'react';
import { stores, WSHandler, Action } from '@choerodon/boot';
import {
  Modal, Progress, Button, Icon, Tooltip, Select,
} from 'choerodon-ui';
import {
  Table, Form, DataSet,
} from 'choerodon-ui/pro';
import Record from 'choerodon-ui/pro/lib/data-set/Record';
import _ from 'lodash';
import moment from 'moment';
import {
  exportIssues, exportIssuesFromVersion, exportIssuesFromFolder, getExportList, exportRetry,
} from '../../../../api/IssueManageApi';
import './ExportSide.less';
import SelectTree from '../CommonComponent/SelectTree';
import ExportSideDataSet from './store';
import { getProjectId, humanizeDuration } from '../../../../common/utils';

const { Column } = Table;
const { AppState } = stores;

function ExportSide(props) {
  const dataSet = useMemo(() => new DataSet({
    autoQuery: false,
    autoCreate: true,
    fields: [
      {
        name: 'folder',
        type: 'object',
        required: true,
        label: '文件夹',
        textField: 'fileName',
        valueField: 'folderId',
        ignore: 'always',
      },
      {
        name: 'folderId',
        type: 'number',
        bind: 'folder.folderId',
      },
    ],

    transport: {
      submit: ({ data }) => ({
        url: `/test/v1/projects/${AppState.currentMenuType.id}/case/download/excel/folder?organizationId=${AppState.currentMenuType.organizationId}&userId=${AppState.userInfo.id}`,
        method: 'get',
        data: {
          folder_id: data[0].folderId,
        },
      }),
    },
  }), []);
  const [folder, setFolder] = useState({ folderId: props.folderId });

  const exportSideDataSet = useMemo(() => ExportSideDataSet(folder.folderId), [folder.folderId]);
  useEffect(() => {

  }, []);

  async function handleCreateExport() {
    if (await dataSet.submit()) {
      // exportSideDataSet.query();
      return true;
    } else {
      return false;
    }
  }

  const handleDownload = (record) => {
    const fileUrl = record.get('fileUrl');
    const id = record.get('id');
    const status = record.get('status');
    if (status === 3) {
      exportRetry(id);
      return;
    }
    if (fileUrl) {
      const ele = document.createElement('a');
      ele.href = fileUrl;
      ele.target = '_blank';
      document.body.appendChild(ele);
      ele.click();
      document.body.removeChild(ele);
    }
  };
  /**
 * 计算耗时
 * @param {*} record 
 */
  const onHumanizeDuration = (record) => {
    const { creationDate, lastUpdateDate } = record;
    const startTime = moment(creationDate);
    const lastTime = moment(lastUpdateDate);
    let diff = lastTime.diff(startTime);
    // console.log(diff);
    if (diff <= 0) {
      diff = moment().diff(startTime);
    }
    return creationDate && lastUpdateDate
      ? humanizeDuration(diff)
      : null;
  };
  const handleMessage = (message) => {
    if (message === 'ok') {
      return;
    }
    const data = JSON.parse(message);
    const { id, rate, successfulCount } = data;
    const newData = {
      ...data,
      creationDate: moment(data.creationDate).format('YYYY-MM-DD HH:mm:ss'),
      during: onHumanizeDuration(data),
    };
    const index = exportSideDataSet.findIndex(record => record.get('id') === id);
    // 存在记录就更新，不存在则新增记录
    if (index >= 0) {
      exportSideDataSet.get(index).set('rate', rate);
      exportSideDataSet.get(index).set('during', onHumanizeDuration(data));
      if (data.status !== exportSideDataSet.get(index).get('status')) {
        exportSideDataSet.get(index).set('status', data.status);
        exportSideDataSet.get(index).set('fileUrl', data.fileUrl);
        if (successfulCount) {
          exportSideDataSet.get(index).set('successfulCount', data.successfulCount);
        }
      }
    } else {
      exportSideDataSet.unshift(new Record(newData));
    }
  };


  function renderStatus({ value, text, record }) {
    // record.get('rate') Prgoress
    return (value === 2
      ? <div>已完成</div>
      : (
        <Tooltip title={`进度：${record.get('rate') ? record.get('rate').toFixed(1) : 0}%`} getPopupContainer={ele => ele.parentNode}>
          <Progress percent={record.get('rate')} showInfo={false} />
        </Tooltip>
      ));
  }

  function renderDropDownMenu({ record }) {
    const action = [{
      service: [],
      text: record.get('status') === 3 ? '重试' : '下载文件',
      action: () => handleDownload(record),
    }];
    return record.get('status') !== 1 && <Action className="action-icon" data={action} />;
  }

  function render() {
    const { folderId } = props;
    return (

      <div className="test-export-issue">
        <div className="test-export-issue-header">
          <Form dataSet={dataSet} className="test-export-issue-form">
            <SelectTree deafultValue={folderId} name="folder" pDataSet={dataSet} onChange={setFolder} placeholder="文件夹" isForbidRoot={false} />
          </Form>
          <Button className="test-export-issue-btn" type="primary" icon="playlist_add" onClick={handleCreateExport}>新建导出</Button>
        </div>
        <WSHandler
          messageKey={`choerodon:msg:test-issue-export:${AppState.userInfo.id}`}
          onMessage={handleMessage}
        >
          <Table dataSet={exportSideDataSet}>
            <Column name="name" align="left" />
            <Column name="action" width={50} renderer={renderDropDownMenu} />
            <Column name="successfulCount" width={120} align="left" />
            <Column name="creationDate" width={200} align="left" />
            <Column name="during" width={200} align="left" />
            <Column name="status" renderer={renderStatus} align="left" />
          </Table>
        </WSHandler>
      </div>
    );
  }

  return render();
}


export default ExportSide;
