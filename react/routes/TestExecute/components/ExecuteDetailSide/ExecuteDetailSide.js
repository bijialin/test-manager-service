import React, {
  Component, useRef, useState, useEffect, useContext,
} from 'react';
import { Choerodon } from '@choerodon/boot';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { observer, inject } from 'mobx-react-lite';
import { throttle } from 'lodash';
import { FormattedMessage } from 'react-intl';
import {
  Button, Tooltip, Icon, Upload, Select,
} from 'choerodon-ui';
import { stores } from '@choerodon/boot';
import { find } from 'lodash';
import { delta2Html, issueLink, text2Delta } from '../../../../common/utils';
import {
  WYSIWYGEditor, Upload as UploadButton, StatusTags, DateTimeAgo, User, RichTextShow, openFullEditor as FullEditor,
  TextEditToggle,
  ResizeAble,
} from '../../../../components';
import { addDefects, removeDefect } from '../../../../api/ExecuteDetailApi';
import Store from '../../stores';
import TypeTag from '../../../IssueManage/components/TypeTag';
import DefectList from './DefectList';
import './ExecuteDetailSide.less';
import UploadButtonExcuteDetail from './UploadButtonExcuteDetail';

const { HeaderStore } = stores;

const { Edit, Text } = TextEditToggle;
const { Option } = Select;
const navs = [
  { code: 'detail', tooltip: '详情', icon: 'error_outline' },
  { code: 'des', tooltip: '描述', icon: 'subject' },
  { code: 'attachment', tooltip: '附件', icon: 'attach_file' },
  { code: 'bug', tooltip: '缺陷', icon: 'bug_report' },
];
let sign = true;
const Section = ({
  id,
  icon,
  title,
  action,
  children,
  style,
}) => (
  <section id={id}>
    <div className="c7ntest-side-item-header">
      <div className="c7ntest-side-item-header-left">
        {/* <Icon type={icon} /> */}
        <span>{title}</span>
      </div>
      <div className="c7ntest-side-item-header-right">
        {action}
      </div>
    </div>
    <div className="c7ntest-side-item-content" style={style}>
      {children}
    </div>
    <div className="c7ntest-side-item-header-line" />

  </section>
);
const defaultProps = {
  issueInfosVO: { issueTypeVO: {} },
};
const propTypes = {
  issueInfosVO: PropTypes.shape({}),
  cycleData: PropTypes.shape({}).isRequired,
  fileList: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  onFileRemove: PropTypes.func.isRequired,
  status: PropTypes.shape({}).isRequired,
  onClose: PropTypes.func.isRequired,
  onUpload: PropTypes.func.isRequired,
  onCommentSave: PropTypes.func.isRequired,
  onRemoveDefect: PropTypes.func.isRequired,
  onCreateBugShow: PropTypes.func.isRequired,
};

function ExecuteDetailSide(props) {
  const context = useContext(Store);
  const { ExecuteDetailStore } = context;
  const container = useRef();
  const [currentNav, setCurrentNav] = useState('detail');
  const [editing, setEditing] = useState(false);

  function isInLook(ele) {
    const a = ele.offsetTop;
    const target = document.getElementById('scroll-area');
    return a + ele.offsetHeight > target.scrollTop;
  }

  function getCurrentNav(e) {
    return find(navs.map(nav => nav.code), i => isInLook(document.getElementById(i)));
  }

  const handleScroll = (e) => {
    if (sign) {
      const newCurrentNav = getCurrentNav(e);
      if (currentNav !== newCurrentNav && newCurrentNav) {
        setCurrentNav(newCurrentNav);
      }
    }
  };

  const setQuery = (width = container.current.clientWidth) => {
    if (width <= 600) {
      container.current.setAttribute('max-width', '600px');
    } else {
      container.current.removeAttribute('max-width');
    }
  };

  // componentDidMount() {
  //   document.getElementById('scroll-area').addEventListener('scroll', handleScroll);
  //   setQuery();
  // }
  useEffect(() => {
    if (document.getElementById('scroll-area')) {
      document.getElementById('scroll-area').removeEventListener('scroll', handleScroll);
    }
    document.getElementById('scroll-area').addEventListener('scroll', handleScroll);
    setQuery();
  }, [handleScroll]);

  // componentWillUnmount() {
  //   if (document.getElementById('scroll-area')) {
  //     document.getElementById('scroll-area').removeEventListener('scroll', handleScroll);
  //   }
  // }

  const scrollToAnchor = (anchorName) => {
    if (anchorName) {
      const anchorElement = document.getElementById(anchorName);
      if (anchorElement) {
        sign = false;
        anchorElement.scrollIntoView({
          behavior: 'smooth',
          block: 'start',
          // inline: "nearest",
        });
        setTimeout(() => {
          sign = true;
        }, 2000);
      }
    }
  };


  const renderNavs = () => navs.map(nav => (
    <Tooltip placement="right" title={nav.tooltip} key={nav.code}>
      <li className={`c7ntest-li ${currentNav === nav.code ? 'c7ntest-li-active' : ''}`}>
        <Icon
          type={`${nav.icon} c7ntest-icon-li`}
          role="none"
          onClick={() => {
            setCurrentNav(nav.code);
            scrollToAnchor(nav.code);
          }}
        />
      </li>
    </Tooltip>
  ));


  const handleResizeEnd = ({ width }) => {
    localStorage.setItem('agile.ExecuteDetail.width', `${width}px`);
  };

  const handleResize = throttle(({ width }) => {
    setQuery(width);
    // console.log(width, parseInt(width / 100) * 100);
  }, 150);


  function render() {
    const {
      fileList, detailData, status, onClose, 
    } = props;
    // console.log('render', props);
    const { statusColor, statusName } = status;
    const {
      executor, description, executorDate, executionStatus, summary, 
    } = detailData;
    // 默认18个字启动省略
    const renderIssueSummary = (text) => {
      const ellipsis = '...';
      const textArr = [...text];
      return (
        <Tooltip title={text}>
          {textArr.length > 20 ? textArr.splice(0, 20).join('') + ellipsis : text}
        </Tooltip>
      );
    };


    return (
      <div style={{
        position: 'fixed',
        right: 0,
        top: HeaderStore.announcementClosed ? 50 : 100,
        bottom: 0,
        zIndex: 101,
        overflowY: 'hidden',
        overflowX: 'visible',
      }}
      >

        <ResizeAble
          modes={['left']}
          size={{
            maxWidth: window.innerWidth * 0.6,
            minWidth: 440,
          }}
          defaultSize={{
            width: localStorage.getItem('agile.ExecuteDetail.width') || 600,
            height: '100%',
          }}
          onResizeEnd={handleResizeEnd}
          onResize={handleResize}
        >

          <div className="c7ntest-ExecuteDetailSide" ref={container}>
            <div className="c7ntest-ExecuteDetailSide-divider" />

            <div className="c7ntest-content">
              <div className="c7ntest-content-top">
                <div className="c7ntest-between-center">
                  <div style={{ fontSize: '16px', fontWeight: 500 }}>
                    <div style={{
                      height: 44, display: 'flex', alignItems: 'center', justifyContent: 'center',
                    }}
                    >
                      <TypeTag data={{ colour: '#4D90FE', icon: 'test-case' }} />
                      <span style={{ marginLeft: 5 }}>相关用例:</span>
                      {/* <Link className="primary c7ntest-text-dot" style={{ marginLeft: 5 }} to={issueLink(issueId, typeCode, issueNum)}>{issueNum}</Link> */}
                    </div>

                  </div>
                  <Button funcType="flat" icon="last_page" onClick={onClose}>
                    <span>隐藏详情</span>
                  </Button>
                </div>
                <div style={{ fontSize: '20px', marginRight: '5px', marginBottom: '15px' }}>
                  {summary}
                </div>
              </div>
              <div className="c7ntest-content-bottom" id="scroll-area" style={{ position: 'relative' }}>
                {/* 详情 */}
                <Section
                  id="detail"
                  icon="error_outline"
                  title="详情"
                >
                  {/* 状态 */}
                  <div className="c7ntest-item-one-line">
                    <div className="c7ntest-item-one-line-left">状态：</div>
                    <div className="c7ntest-item-one-line-right">
                      {statusColor && (
                        <StatusTags
                          style={{
                            height: 20, fontSize: '12px', lineHeight: '20px', marginRight: 15,
                          }}
                          color={statusColor}
                          name={statusName}
                        />
                      )}
                    </div>
                  </div>
                  {/* 执行人 */}
                  <div className="c7ntest-item-one-line">
                    <div className="c7ntest-item-one-line-left">执行人：</div>
                    <div className="c7ntest-item-one-line-right">
                      <User user={executor} />
                    </div>
                  </div>

                  {/* 执行日期 */}
                  <div className="c7ntest-item-one-line">
                    <div className="c7ntest-item-one-line-left">执行日期：</div>
                    <div className="c7ntest-item-one-line-right">
                      <DateTimeAgo date={executorDate} />
                    </div>
                  </div>
                </Section>
                {/* 描述 */}
                <Section
                  id="des"
                  icon="subject"
                  title="描述"
                  style={{ padding: '0 15px 0 0' }}
                >
                  <RichTextShow data={delta2Html(description)} />

                </Section>
                {/* 附件 */}
                <Section
                  id="attachment"
                  icon="attach_file"
                  title="附件"
                >
                  {/* <Upload
                    fileList={fileList}
                    className="upload-button"
                  /> */}
                  <UploadButtonExcuteDetail
                    // onRemove={handleRemove}
                    // updateNow={onChangeFileList}
                    fileList={fileList}
                  />
                </Section>

              </div>
            </div>
          </div>
        </ResizeAble>
      </div>

    );
  }
  return render();
}

ExecuteDetailSide.propTypes = propTypes;
ExecuteDetailSide.defaultProps = defaultProps;
export default observer(ExecuteDetailSide);
