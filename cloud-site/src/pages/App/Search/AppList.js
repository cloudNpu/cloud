import React, { PureComponent, Fragment } from "react";
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

import styles from "./AppList.less";
const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const RadioGroup = Radio.Group;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(",");
//新建弹出页面
const CreateForm = Form.create()(props => {
  const {
    modalVisible,
    form,
    handleAdd,
    handleSubmit,
    handleModalVisible
  } = props;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      if (fieldsValue.app) {
        console.log(fieldsValue);
        handleAdd(fieldsValue);
      } else {
        var instanceInfo = JSON.parse(fieldsValue.txt);
        console.log(instanceInfo);
        console.log(instanceInfo.instance.app);
        handleSubmit(instanceInfo);
      }
    });
  };
  const TabPane = Tabs.TabPane;
  function callback(key) {
    // console.log(key);
  }
  return (
    <Modal
      destroyOnClose
      title="新建服务"
      visible={modalVisible}
      onOk={okHandle}
      onCancel={() => handleModalVisible()}
    >
      <Tabs defaultActiveKey="1" onChange={callback}>
        <TabPane tab="Tab 1" key="1">
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="App"
          >
            {form.getFieldDecorator("app", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="InstanceId"
          >
            {form.getFieldDecorator("instanceId", {
              // rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="IpAddr"
          >
            {form.getFieldDecorator("ipAddr", {
              // rules: [{required: true, message: '请输入至少三个字符的规则描述！', min: 3}],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="Port"
          >
            {form.getFieldDecorator("port", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="Status"
          >
            {form.getFieldDecorator("status", {
              //  rules: [{ required: true, message: '请选择一个！', min: 3 }],
            })(
              <Select style={{ width: "100%" }} /* mode={'multiple'}*/>
                <Option value={1}>UP</Option>
                <Option value={0}>DOWN</Option>
              </Select>
            )}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="overriddenstatus"
          >
            {form.getFieldDecorator("overriddenstatus", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="homePageUrl"
          >
            {form.getFieldDecorator("homePageUrl", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="hostName"
          >
            {form.getFieldDecorator("hostName", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="securePort"
          >
            {form.getFieldDecorator("securePort", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="countryId"
          >
            {form.getFieldDecorator("countryId", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="dataCenterInfo"
          >
            {form.getFieldDecorator("dataCenterInfo", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="metadata"
          >
            {form.getFieldDecorator("metadata", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="statusPageUrl"
          >
            {form.getFieldDecorator("statusPageUrl", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="healthCheckUrl"
          >
            {form.getFieldDecorator("healthCheckUrl", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="vipAddress"
          >
            {form.getFieldDecorator("vipAddress", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="secureVipAddress"
          >
            {form.getFieldDecorator("secureVipAddress", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="lastDirtyTimestamp"
          >
            {form.getFieldDecorator("lastDirtyTimestamp", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="inputParams"
          >
            {form.getFieldDecorator("inputParams", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
          <FormItem
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 15 }}
            label="outputParams"
          >
            {form.getFieldDecorator("outputParams", {
              //rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
        </TabPane>

        <TabPane tab="Tab 2" key="2">
          <FormItem
            labelCol={{ span: 6 }}
            wrapperCol={{ span: 18 }}
            label="请输入Json格式"
          >
            {form.getFieldDecorator("txt", {})(
              <TextArea
                style={{ minHeight: 32 }}
                //placeholder={"请输入"}
                rows={10}
              />
            )}
          </FormItem>
        </TabPane>
      </Tabs>
    </Modal>
  );
});

//配置
@Form.create()
class UpdateForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      formVals: {
        instance: {
          key: props.values.key,
          app: props.values.app,
          instanceId: props.values.instanceId,
          ipAddr: props.values.ipAddr,
          status: props.values.status
          // visible: props.values.visible,
        }
      },
      currentStep: 0
    };

    this.formLayout = {
      labelCol: { span: 7 },
      wrapperCol: { span: 13 }
    };
  }

  handleNext = currentStep => {
    const { form, handleUpdate } = this.props;
    const { formVals: oldValue } = this.state;
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      const formVals = { ...oldValue, ...fieldsValue };
      this.setState(
        {
          formVals
        },
        () => {
          if (currentStep === 0) {
            var service = JSON.parse(formVals.sdl);
            console.log(service);
            handleUpdate(service);
          }
        }
      );
    });
  };

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    return [
      <FormItem key="sdl">
        {form.getFieldDecorator("sdl", {
          initialValue: JSON.stringify(formVals)
        })(<TextArea rows={10} />)}
      </FormItem>
    ];
  };
  renderFooter = currentStep => {
    const { handleUpdateModalVisible, values } = this.props;
    return [
      <Button
        key="cancel"
        onClick={() => handleUpdateModalVisible(false, values)}
      >
        取消
      </Button>,
      <Button
        key="submit"
        type="primary"
        onClick={() => this.handleNext(currentStep)}
      >
        完成
      </Button>
    ];
  };

  render() {
    const { updateModalVisible, handleUpdateModalVisible } = this.props;
    const { currentStep, formVals } = this.state;

    return (
      <Modal
        width={640}
        bodyStyle={{ padding: "32px 40px 48px" }}
        destroyOnClose
        title="编辑服务"
        visible={updateModalVisible}
        footer={this.renderFooter(currentStep)}
        onCancel={() => handleUpdateModalVisible()}
        afterClose={() => handleUpdateModalVisible()}
      >
        {this.renderContent(currentStep, formVals)}
      </Modal>
    );
  }
}
//
@Form.create()
class ChangeForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      formVals: {
        appName: props.values.appName,
        visible: props.values.visible
      },
      currentStep: 0
    };

    this.formLayout = {
      labelCol: { span: 7 },
      wrapperCol: { span: 13 }
    };
  }

  handleNext = currentStep => {
    const { form, handleChange } = this.props;
    const { formVals: oldValue } = this.state;
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      const formVals = { ...oldValue, ...fieldsValue };
      this.setState(
        {
          formVals
        },
        () => {
          if (currentStep === 0) {
            handleChange(formVals); //点击确定，调用handleChange
            // console.log("successful call");
          }
        }
      );
    });
  };

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    return [
      <FormItem key="appName" {...this.formLayout} label="app">
        {form.getFieldDecorator("appName", {
          initialValue: formVals.appName,
          rules: [
            {
              required: true,
              message: "用于确定是否为该服务发布信息，不能更改",
              min: 0
            }
          ]
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="visible" {...this.formLayout} label="visible">
        {form.getFieldDecorator("visible", {
          initialValue: formVals.visible
        })(
          <Select style={{ width: "100%" }} /* mode={'multiple'}*/>
            <Option value={true}>true</Option>
            <Option value={false}>false</Option>
          </Select>
        )}
      </FormItem>
    ];
  };
  renderFooter = currentStep => {
    const { handleChangeModalVisible, values } = this.props;
    return [
      <Button
        key="cancel"
        onClick={() => handleChangeModalVisible(false, values)}
      >
        取消
      </Button>,
      <Button
        key="submit"
        type="primary"
        onClick={() => this.handleNext(currentStep)}
      >
        完成
      </Button>
    ];
  };

  render() {
    const { changeModalVisible, handleChangeModalVisible } = this.props;
    const { currentStep, formVals } = this.state;

    return (
      <Modal
        width={640}
        bodyStyle={{ padding: "32px 40px 48px" }}
        destroyOnClose
        title="发布/撤回服务"
        visible={changeModalVisible}
        footer={this.renderFooter(currentStep)}
        onCancel={() => handleChangeModalVisible()}
        afterClose={() => handleChangeModalVisible()}
      >
        {this.renderContent(currentStep, formVals)}
      </Modal>
    );
  }
}
//

/* eslint react/no-multi-comp:0 */
@connect(({ applist, loading }) => ({
  applist,
  loading: loading.models.applist
}))
@Form.create()
class AppList extends PureComponent {
  state = {
    modalVisible: false,
    updateModalVisible: false,
    changeModalVisible: false,
    expandForm: false,
    selectedRows: [],
    formValues: {},
    stepFormValues: {},
    stepFormValues1: {}
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
      width: 70
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
      sorter: true,
      align: "right",
      render: val => `${val} 万`,
      // mark to display a total number
      needTotal: true
    },
    {
      title: "Visible",
      dataIndex: "visible"
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
    /*{
      title: "LeaseInfoid",
      dataIndex: "leaseinfoid"
    },行数太多*/
    {
      title: "Sid",
      dataIndex: "sid"
    },
    /* {
      title: "StatusUrl",
      dataIndex: "statusurl"
    },*/
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
    /* {
      title: "IsSecurePortEnabled",
      dataIndex: "issecureportenabled"
    },*/
    /* {
      title: "IsUnsecurePortEnable",
      dataIndex: "isunsecureportenable"
    },*/
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
    },
    {
      title: "Action",
      fixed: "right",
      render: (text, record) => (
        <Fragment>
          <a onClick={() => this.handleUpdateModalVisible(true, record)}>
            编辑
          </a>
          <Divider type="vertical" />
          <a onClick={() => this.handleChangeModalVisible(true, record)}>
            切换状态
          </a>
        </Fragment>
      )
    }
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "applist/fetch"
    });
  }
  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = { ...obj };
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});
    const params = {
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      ...formValues,
      ...filters
    };
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: "applist/fetch",
      payload: params
    });
  };

  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({
      formValues: {}
    });
    // const str="hello";
    // if(str.equals("a")) {
    dispatch({
      type: "applist/fetch",
      payload: {}
    });
  };

  toggleForm = () => {
    const { expandForm } = this.state;
    this.setState({
      expandForm: !expandForm
    });
  };

  handleMenuClick = e => {
    const { dispatch } = this.props;
    const { selectedRows } = this.state;
    var selectRow = selectedRows.map(row => row.id);
    //const str=selectRow.map((item)=>item).join(',');
    if (!selectedRows) return;
    switch (e.key) {
      case "remove":
        dispatch({
          type: "applist/remove",
          payload: selectRow,
          callback: () => {
            this.setState({
              selectedRows: []
            });
          }
        });
        break;
      default:
        break;
    }
  };

  handleSelectRows = rows => {
    this.setState({
      selectedRows: rows
    });
  };

  handleSearch = e => {
    e.preventDefault(); // 阻止提交

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      const values = {
        ...fieldsValue,
        updatedAt: fieldsValue.updatedAt && fieldsValue.updatedAt.valueOf()
      };
      console.log(values);
      console.log(values.id);
      this.setState({
        formValues: values
      });
      if (values.appName) {
        dispatch({
          type: "applist/app",
          payload: values
        });
      }
      if (values.id) {
        dispatch({
          type: "applist/id",
          payload: values
        });
      }
      if (values.visible) {
        dispatch({
          type: "applist/visible",
          payload: values
        });
      }
      if (values.port) {
        dispatch({
          type: "applist/port",
          payload: values
        });
      }
      if (values.ipAddr) {
        dispatch({
          type: "applist/ipAddr",
          payload: values
        });
      }
    });
  };

  handleModalVisible = flag => {
    this.setState({
      modalVisible: !!flag
    });
  };

  handleUpdateModalVisible = (flag, record) => {
    this.setState({
      updateModalVisible: !!flag,
      stepFormValues: record || {}
    });
  };
  handleChangeModalVisible = (flag, record) => {
    this.setState({
      changeModalVisible: !!flag,
      stepFormValues1: record || {}
    });
  };

  handleAdd = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "applist/add",
      payload: {
        instance: {
          instanceId: fields.instanceId,
          app: fields.app,
          ipAddr: fields.ipAddr,
          status: fields.status,
          // visible: fields.visible,
          hostName: fields.hostName,
          overriddenstatus: fields.overriddenstatus,
          countryId: fields.countryId,
          homePageUrl: fields.homePageUrl,
          statusPageUrl: fields.statusPageUrl,
          healthCheckUrl: fields.healthCheckUrl,
          vipAddress: fields.vipAddress,
          secureVipAddress: fields.secureVipAddress,
          lastDirtyTimestamp: fields.lastDirtyTimestamp,
          inputParams: fields.inputParams,
          outputParams: fields.outputParams
        }
      }
    });
    message.success("添加成功");
    this.handleModalVisible();
  };
  handleSubmit = instanceInfo => {
    const { dispatch } = this.props;
    dispatch({
      type: "applist/add",
      payload: instanceInfo
    });
    message.success("添加成功");
    this.handleModalVisible();
  };
  //
  handleUpdate = service => {
    const { dispatch } = this.props;
    dispatch({
      type: "applist/update",
      payload: service
    });
    message.success("配置成功");
    this.handleUpdateModalVisible();
  };
  //
  handleChange = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "applist/change",
      payload: {
        appName: fields.appName,
        visible: fields.visible
      }
    });
    message.success("切换成功");
    this.handleChangeModalVisible();
  };
  //

  renderSimpleForm() {
    const {
      form: { getFieldDecorator }
    } = this.props;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="App">
              {getFieldDecorator("appName")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="ID">
              {getFieldDecorator("id")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <span className={styles.submitButtons}>
              <Button type="primary" htmlType="submit">
                查询
              </Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                重置
              </Button>
              <a style={{ marginLeft: 8 }} onClick={this.toggleForm}>
                添加筛选 <Icon type="down" />
              </a>
            </span>
          </Col>
        </Row>
      </Form>
    );
  }

  renderAdvancedForm() {
    const {
      form: { getFieldDecorator }
    } = this.props;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="App">
              {getFieldDecorator("appName")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="ID">
              {getFieldDecorator("id")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="IpAddr">
              {getFieldDecorator("ipAddr")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="Visible">
              {getFieldDecorator("visible")(
                <Select placeholder="请选择" style={{ width: "100%" }}>
                  <Option value="1">0</Option>
                  <Option value="0">1</Option>
                </Select>
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="Port">
              {getFieldDecorator("port")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
        <div style={{ overflow: "hidden" }}>
          <div style={{ float: "right", marginBottom: 24 }}>
            <Button type="primary" htmlType="submit">
              查询
            </Button>
            <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
              重置
            </Button>
            <a style={{ marginLeft: 8 }} onClick={this.toggleForm}>
              收起 <Icon type="up" />
            </a>
          </div>
        </div>
      </Form>
    );
  }

  renderForm() {
    const { expandForm } = this.state;
    return expandForm ? this.renderAdvancedForm() : this.renderSimpleForm();
  }

  render() {
    const {
      applist: { data },
      loading
    } = this.props;
    const {
      selectedRows,
      modalVisible,
      updateModalVisible,
      changeModalVisible,
      stepFormValues,
      stepFormValues1
    } = this.state;
    const menu = (
      <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        <Menu.Item key="remove">删除</Menu.Item>
        {/*  <Menu.Item key="approval">批量审批</Menu.Item>*/}
      </Menu>
    );

    const parentMethods = {
      handleAdd: this.handleAdd,
      handleSubmit: this.handleSubmit,
      handleModalVisible: this.handleModalVisible
    };
    const updateMethods = {
      handleUpdateModalVisible: this.handleUpdateModalVisible,
      handleUpdate: this.handleUpdate
    };
    //
    const changeMethods = {
      handleChangeModalVisible: this.handleChangeModalVisible,
      handleChange: this.handleChange
      //
    };
    return (
      <PageHeaderWrapper title="查询表格">
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderForm()}</div>
            {/*
                        <div className={styles.scrolls}>{this.renderForm()}</div>
*/}
            <div className={styles.tableListOperator}>
              <Button
                icon="plus"
                type="primary"
                onClick={() => this.handleModalVisible(true)}
              >
                新建
              </Button>
              {selectedRows.length > 0 && (
                <span>
                  <Button>批量操作</Button>
                  <Dropdown overlay={menu}>
                    <Button>
                      更多操作 <Icon type="down" />
                    </Button>
                  </Dropdown>
                </span>
              )}
            </div>
            <StandardTable
              selectedRows={selectedRows}
              loading={loading}
              data={data}
              columns={this.columns}
              onSelectRow={this.handleSelectRows}
              onChange={this.handleStandardTableChange}
            />
          </div>
        </Card>
        <CreateForm {...parentMethods} modalVisible={modalVisible} />
        {stepFormValues && Object.keys(stepFormValues).length ? (
          <UpdateForm
            {...updateMethods}
            updateModalVisible={updateModalVisible}
            values={stepFormValues}
          />
        ) : null}
        {stepFormValues1 && Object.keys(stepFormValues1).length ? (
          <ChangeForm
            {...changeMethods}
            changeModalVisible={changeModalVisible}
            values={stepFormValues1}
          />
        ) : null}
      </PageHeaderWrapper>
    );
  }
}
export default AppList;
