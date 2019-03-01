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
//状态下拉
const statusMap = ["default", "success"];
const status = ["DOWN", "UP"];

const visibleMap = ["default", "success"];
const visible = ["0", "1"];
//新建弹出页面
const CreateForm = Form.create()(props => {
  const { modalVisible, form, handleAdd, handleModalVisible } = props;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      if (fieldsValue.app) {
        handleAdd(fieldsValue);
      } else {
        var instanceInfo = JSON.parse(fieldsValue.txt);
        console.log(instanceInfo);
        handleAdd(instanceInfo);
      }
    });
  };
  const TabPane = Tabs.TabPane;
  function callback(key) {
    console.log(key);
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
            label="User"
          >
            {form.getFieldDecorator("userid", {
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
            labelCol={{ span: 6 }}
            wrapperCol={{ span: 14 }}
            label="AppGroupName"
          >
            {form.getFieldDecorator("appGroupName", {
              //  rules: [{ required: true, message: '请输入至少三个字符的规则描述！', min: 3 }],
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

  /* const okSubmit = (e) => {
        form.validateFields((err, fieldsValue) => {
          var instanceInfo = JSON.parse(fieldsValue.txt);
          console.log(instanceInfo);
          handleAdd(instanceInfo);
        });*/
  // var instanceInfo = JSON.parse('{"app":"fdssf","port":"123423"}');
  //   var instanceInfo = JSON.parse(JSON.stringify(fieldsValue));
  // form.validateFields((err, fieldsValue) => {
  //   if (err) return;
  //   form.resetFields();
  //   handleSubmit(fieldsValue);
  // });
  // console.log("add1");
  //var instanceInfo = JSON.parse();
  // console.log(instanceInfo.app);
  // console.log(instanceInfo.port);
});

//配置
@Form.create()
class UpdateForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      formVals: {
        key: props.values.key,
        app: props.values.app,
        instanceId: props.values.instanceId,
        ipAddr: props.values.ipAddr,
        port: props.values.port,
        userid: props.values.userid,
        status: props.values.status,
        appGroupName: props.values.appGroupName
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
            handleUpdate(service);
            console.log(service);
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
        key: props.values.key,
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
            console.log("successful call");
          }
        }
      );
    });
  };

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    return [
      <FormItem key="visible" {...this.formLayout} label="visible">
        {form.getFieldDecorator("visible", {
          initialValue: formVals.visible
        })(
          <Select style={{ width: "100%" }} /* mode={'multiple'}*/>
            <Option value={1}>发布</Option>
            <Option value={0}>撤回</Option>
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
      title: "App",
      dataIndex: "app",
      fixed: "left",
      width: 70
    },
    {
      title: "InstanceID",
      dataIndex: "instanceId",
      fixed: "left",
      width: 80
    },
    {
      title: "  IpAddr",
      dataIndex: "ipAddr"
    },
    {
      title: "Port",
      dataIndex: "port"
    },
    {
      title: "UserId",
      dataIndex: "userid"
    },
    {
      title: "status",
      dataIndex: "status",
      width: 150,
      filters: [
        {
          text: status[0],
          value: 0
        },
        {
          text: status[1],
          value: 1
        }
      ],
      render(val) {
        return <Badge status={statusMap[val]} text={status[val]} />;
      }
    },
    {
      title: "AppGroupName",
      dataIndex: "appGroupName"
    },
    {
      title: "InvokeCount",
      dataIndex: "count",
      sorter: true,
      align: "right",
      render: val => `${val} 万`,
      // mark to display a total number
      needTotal: true
    },
    {
      title: "Visible",
      dataIndex: "visible",
      width: 150,
      filters: [
        {
          text: visible[0],
          value: 0
        },
        {
          text: visible[1],
          value: 1
        }
      ],
      render(val) {
        return <Badge status={visibleMap[val]} text={visible[val]} />;
      }
    },
    {
      title: "SecurePort",
      dataIndex: "secureport"
    },
    {
      title: "Overridenstatus",
      dataIndex: "overridenstatus"
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
      dataIndex: "complextypes"
    },
    {
      title: "countryID",
      dataIndex: "countryid"
    },
    {
      title: "homePageUrl",
      dataIndex: "homepageurl"
    },
    {
      title: "statusPageUrl",
      dataIndex: "statuspageurl"
    },
    {
      title: "healthCheckUrl",
      dataIndex: "healthcheckurl"
    },
    {
      title: "Version",
      dataIndex: "version"
    },
    {
      title: "AsgName",
      dataIndex: "asgname"
    },
    {
      title: "ActionType",
      dataIndex: "actiontype"
    },
    {
      title: "LastDirtyTimeStamp",
      dataIndex: "Lastdirtytimestamp",
      sorter: true,
      render: val => <span>{moment(val).format("YYYY-MM-DD HH:mm:ss")}</span>
    },
    {
      title: "LastUpdateTimeStamp",
      dataIndex: "Lastupdatetimestamp",
      sorter: true,
      render: val => <span>{moment(val).format("YYYY-MM-DD HH:mm:ss")}</span>
    },

    {
      title: "MetaData",
      dataIndex: "metadata"
    },
    {
      title: "LeaseInfoid",
      dataIndex: "leaseinfoid"
    },
    {
      title: "Sid",
      dataIndex: "sid"
    },
    {
      title: "StatusUrl",
      dataIndex: "statusurl"
    },
    {
      title: "VipAddress",
      dataIndex: "vipaddress"
    },
    {
      title: "SecureVipAddress",
      dataIndex: "securevipaddress"
    },
    {
      title: "StatusPageRelativeUrl",
      dataIndex: "StatusPageRelativeUrl"
    },
    {
      title: "StatusPageExplicitUrl",
      dataIndex: "Statuspageexpliciturl"
    },
    {
      title: "HealthCheck",
      dataIndex: "healthcheck"
    },
    {
      title: "HealthCheckSecureExplicitUrl",
      dataIndex: "healthchecksecureexpliciturl"
    },
    {
      title: "VipAddressUnreSolved",
      dataIndex: "vipaddressunresolved"
    },
    {
      title: "SecureVipAddressUnreSolved",
      dataIndex: "securevipaddressunresolved"
    },
    {
      title: "HealthCheckExplicitUrl",
      dataIndex: "healthcheckexpliciturl"
    },
    {
      title: "IsSecurePortEnabled",
      dataIndex: "issecureportenabled"
    },
    {
      title: "IsUnsecurePortEnable",
      dataIndex: "isunsecureportenable"
    },
    {
      title: "DataCenterInfo",
      dataIndex: "datacenterinfo"
    },
    {
      title: "HostName",
      dataIndex: "hostname"
    },
    {
      title: "IsInstanceInfoDirty",
      dataIndex: "isinstanceinfodirty"
    },
    {
      title: "IsCoordinatingDiscoveryServer",
      dataIndex: "iscoordinatingdiscoveryserver"
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

    if (!selectedRows) return;
    switch (e.key) {
      case "remove":
        dispatch({
          type: "applist/remove",
          payload: {
            key: selectedRows.map(row => row.key)
          },
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

      this.setState({
        formValues: values
      });

      dispatch({
        type: "applist/fetch",
        payload: values
      });
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
        app: fields.app,
        instanceId: fields.instanceId,
        ipAddr: fields.ipAddr,
        port: fields.port,
        userid: fields.userid,
        status: fields.status,
        appGroupName: fields.appGroupName
      }
    });

    message.success("添加成功");
    this.handleModalVisible();
  };
  //
  /*  handleSubmit = instanceInfo => {
  const { dispatch } = this.props;
  dispatch({
    type: "applist/add",
    payload: {
      app: instanceInfo.app,
      instanceId: instanceInfo.instanceId,
      ipAddr: instanceInfo.ipAddr,
      port: instanceInfo.port,
      userid: instanceInfo.userid,
      status: instanceInfo.status,
      visible: instanceInfo.visible,
      appGroupName: instanceInfo.appGroupName
    }
  });

  message.success("添加成功");
  this.handleModalVisible();
};*/
  //
  handleUpdate = fields => {
    /*   JSON.parse(fields.sdl);*/
    const { dispatch } = this.props;
    dispatch({
      type: "applist/update",
      payload: {
        key: fields.key,
        app: fields.app,
        instanceId: fields.instanceId,
        ipAddr: fields.ipAddr,
        port: fields.port,
        userid: fields.userid,
        status: fields.status,
        appGroupName: fields.appGroupName
      }
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
        key: fields.key,
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
              {getFieldDecorator("app")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="InstanceID">
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
              {getFieldDecorator("app")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="InstanceID">
              {getFieldDecorator("id")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="IpAddr">
              {getFieldDecorator("ip")(<Input placeholder="请输入" />)}
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
              //  scroll={{ x: 1000, y: 300}}
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
