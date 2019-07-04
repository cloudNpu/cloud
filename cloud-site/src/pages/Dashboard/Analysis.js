import React, { PureComponent, Fragment, Suspense } from "react";
import { connect } from "dva";
import moment from "moment";
import {
  Row,
  Col,
  Card,
  Form,
  Table,
  Input,
  Select,
  Icon,
  Button,
  Dropdown,
  Menu,
  // InputNumber,
  DatePicker,
  Modal,
  message,
  Badge,
  Divider,
  Steps,
  Radio,
  Tabs
} from "antd";
import StandardTable from "@/components/StandardTable1";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import SystemIntroduce from "./SystemIntroduce";
import PageLoading from "@/components/PageLoading";
import styles from "./Analysis.less";
const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const RadioGroup = Radio.Group;
import Status from "./Status";
//const SystemIntroduce=React.lazy(()=>import('./SystemIntroduce'));

/* eslint react/no-multi-comp:0 */
@connect(({ analysis, loading }) => ({
  analysis,
  loading: loading.models.analysis
}))
@Form.create()
class App extends PureComponent {
  state = {
    selectedRows: []
  };
  columns = [
    {
      title: "ID",
      dataIndex: "id",
      fixed: "left",
      width: 70
    },
    {
      title: "App",
      dataIndex: "appName",
      fixed: "left",
      width: 70
    },
    {
      title: "InstanceID",
      dataIndex: "instanceId",
      fixed: "left",
      width: 40
    },
    {
      title: "IpAddr",
      dataIndex: "ipAddr"
    },
    {
      title: "port",
      dataIndex: "port"
    },
    {
      title: "status",
      dataIndex: "status"
    },
    {
      title: "AppGroupName",
      dataIndex: "appGroupName"
    },
    {
      title: "InvokeCount",
      dataIndex: "invokeCount",
      // sorter: true,
      align: "right",
      // render: val => `${val} 万`,
      // mark to display a total number
      needTotal: true
    },
    {
      title: "Visible",
      dataIndex: "visible",
      render: visible => <div>{visible ? "已发布" : "未发布"}</div>
    },
    {
      title: "securePortEnabled",
      dataIndex: "securePortEnabled"
    },
    {
      title: "unsecurePortEnabled",
      dataIndex: "unsecurePortEnabled"
    },
    {
      title: "Overriddenstatus",
      dataIndex: "overriddenStatus"
    },
    {
      title: "Inputparm",
      dataIndex: "inputparm"
    },
    {
      title: "Outputparm",
      dataIndex: "outputparm"
    },
    {
      title: "complexTypes",
      dataIndex: "complexType"
    },
    {
      title: "countryID",
      dataIndex: "countryId"
    },
    {
      title: "homePageUrl",
      dataIndex: "homePageUrl"
    },
    {
      title: "statusPageUrl",
      dataIndex: "statusPageUrl"
    },
    {
      title: "healthCheckUrl",
      dataIndex: "healthCheckUrl"
    },
    {
      title: "AsgName",
      dataIndex: "asgName"
    },
    {
      title: "ActionType",
      dataIndex: "actionType"
    },
    {
      title: "LastDirtyTimeStamp",
      dataIndex: "lastDirtyTimestamp"
    },
    {
      title: "LastUpdateTimeStamp",
      dataIndex: "lastUpdateTimestamp"
    },

    {
      title: "MetaData",
      dataIndex: "metadata"
    },
    {
      title: "Sid",
      dataIndex: "sid"
    },
    {
      title: "VipAddress",
      dataIndex: "vipAddress"
    },
    {
      title: "SecureVipAddress",
      dataIndex: "secureVipAddress"
    },
    {
      title: "StatusPageRelativeUrl",
      dataIndex: "StatusPageRelativeUrl"
    },
    {
      title: "StatusPageExplicitUrl",
      dataIndex: "StatusPageExplicitUrl"
    },
    {
      title: "HealthCheckSecureExplicitUrl",
      dataIndex: "healthCheckSecureExplicitUrl"
    },
    {
      title: "lastUpdatedTimestamp",
      dataIndex: "lastUpdatedTimestamp"
    },
    {
      title: "vipAddressUnresolved",
      dataIndex: "vipAddressUnresolved"
    },
    {
      title: "secureVipAddressUnresolved",
      dataIndex: "secureVipAddressUnresolved"
    },
    {
      title: "secureHealthCheckUrl",
      dataIndex: "secureHealthCheckUrl"
    },
    {
      title: "inputParams",
      dataIndex: "inputParams"
    },
    {
      title: "outputParams",
      dataIndex: "outputParams"
    },
    {
      title: "method",
      dataIndex: "method"
    },
    {
      title: "healthCheckRelativeUrl",
      dataIndex: "healthCheckRelativeUrl"
    },
    {
      title: "HealthCheckExplicitUrl",
      dataIndex: "healthCheckExplicitUrl"
    },
    {
      title: "DataCenterInfo",
      dataIndex: "dataCenterInfo"
    },
    {
      title: "HostName",
      dataIndex: "hostName"
    },
    {
      title: "instanceInfoDirty",
      dataIndex: "instanceInfoDirty"
    },
    {
      title: "IsCoordinatingDiscoveryServer",
      dataIndex: "isCoordinatingDiscoveryServer"
    }
  ];

  componentDidMount() {
     /* this.timer = setInterval(
          function () {*/
              const {dispatch} = this.props;
              dispatch({
                  type: "analysis/fetch"
              });
              dispatch({
                  type: "analysis/fetchStatus"
              });
         /* }.bind(this),
          5000
      );*/
  }
  render() {
    const { analysis, loading } = this.props;
    const { data, visitData } = analysis;
    //console.log(visitData);

    return (
      /* <PageHeaderWrapper title="查询表格">*/
      <Card bordered={false}>
        <h2>System Status</h2>
        <Suspense fallback={<PageLoading />}>
          <SystemIntroduce loading={loading} visitData={visitData} />
        </Suspense>
        <Divider />
        <h2>generalStats</h2>
        <Status />
        <Divider />
        <h2>Instances Info</h2>
        <StandardTable
          selectedRows={false}
          loading={loading}
          data={data}
          columns={this.columns}
          onSelectRow={this.handleSelectRows}
          onChange={this.handleStandardTableChange}
        />
      </Card>
      /*</PageHeaderWrapper>*/
    );
  }
}
export default App;
