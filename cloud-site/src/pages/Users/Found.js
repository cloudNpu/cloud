import React, { PureComponent, Fragment } from "react";
import { connect } from "dva";
import moment from "moment";
import {
  Row,
  Col,
  Card,
  Form,
  Input,
  Select,
  Icon,
  Button,
  Dropdown,
  Menu,
  InputNumber,
  DatePicker,
  Modal,
  message,
  Badge,
  Divider,
  Steps,
  Radio,
  Table
} from "antd";
import StandardTable from "@/components/StandardTable";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";

import styles from "./Found.less";

const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const RadioGroup = Radio.Group;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(",");
const sexMap = ["default", "success"];
const sex = ["男", "女"];
const CreateForm = Form.create()(props => {
  const { modalVisible, form, handleAdd, handleModalVisible } = props;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAdd(fieldsValue);
    });
  };
  return (
    <Modal
      destroyOnClose
      title="添加用户"
      visible={modalVisible}
      onOk={okHandle}
      onCancel={() => handleModalVisible()}
    >
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="用户名">
        {form.getFieldDecorator("username", {
          rules: [{ required: true, message: "用户名不能为空！", min: 0 }]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="性别">
        {form.getFieldDecorator("sex", {})(
          <Select style={{ width: "100%" }} /* mode={'multiple'}*/>
            <Option value={0}>男</Option>
            <Option value={1}>女</Option>
          </Select>
        )}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="部门">
        {form.getFieldDecorator("dept", {
          //  rules: [{ required: true, message: '请选择一个！', min: 3 }],
        })(
          <Select style={{ width: "100%" }}>
            <Option value={5}>5</Option>
            <Option value={4}>4</Option>
            <Option value={3}>3</Option>
            <Option value={2}>2</Option>
            <Option value={1}>1</Option>
            <Option value={0}>0</Option>
          </Select>
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="出生日期"
      >
        {form.getFieldDecorator("birthday", {
          //  rules: [{ required: true, message: '请选择时间' }],
        })(
          <Input placeholder="请输入" /> /*<DatePicker
                        style={{ width: '100%' }}
                        showTime
                        format="YYYY-MM-DD HH:mm:ss"
                        placeholder="选择出生年月日"
                    />*/
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="移动电话"
      >
        {form.getFieldDecorator("mobile", {
          //  rules: [{ required: true, message: '电话不能为空', min: 0 }],
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="办公电话"
      >
        {form.getFieldDecorator("officeTel", {
          //     rules: [{ required: true, message: '电话不能为空', min: 0 }],
        })(<Input placeholder="请输入" />)}
      </FormItem>
      {
        <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="角色">
          {form.getFieldDecorator("role", {})(
            <Select style={{ width: "100%" }} mode={"multiple"}>
              <Option value={"用户管理员"}>用户管理员</Option>
              <Option value={"角色管理员"}>角色管理员</Option>
              <Option value={"服务管理员"}>服务管理员</Option>
              <Option value={"管理员"}>管理员</Option>
            </Select>
          )}
        </FormItem>
      }
    </Modal>
  );
});

@Form.create()
class UpdateForm extends PureComponent {
  static defaultProps = {
    handleUpdate: () => {},
    handleUpdateModalVisible: () => {},
    values: {}
  };
  constructor(props) {
    super(props);

    this.state = {
      formVals: {
        key: props.values.key,
        username: props.values.username,
        sex: props.values.sex,
        dept: props.values.dept,
        birthday: props.values.birthday,
        mobile: props.values.mobile,
        officeTel: props.values.officeTel,
        role: props.values.role
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
            handleUpdate(formVals);
          }
        }
      );
    });
  };

  //  const { currentStep } = this.state;

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    return [
      <FormItem key="username" {...this.formLayout} label="用户名">
        {form.getFieldDecorator("username", {
          //rules: [{ required: true, message: '请输入规则名称！' }],
          initialValue: formVals.username
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="dept" {...this.formLayout} label="部门">
        {form.getFieldDecorator("dept", {
          // rules: [{ required: true, message: '请输入至少五个字符的规则描述！', min: 5 }],
          initialValue: formVals.dept
        })(
          <Select style={{ width: "100%" }}>
            <Option value={5}>5</Option>
            <Option value={4}>4</Option>
            <Option value={3}>3</Option>
            <Option value={2}>2</Option>
            <Option value={1}>1</Option>
            <Option value={0}>0</Option>
          </Select>
        )}
      </FormItem>,
      <FormItem key="sex" {...this.formLayout} label="性别">
        {form.getFieldDecorator("sex", {
          initialValue: formVals.sex
        })(
          <Select style={{ width: "100%" }} /* mode={'multiple'}*/>
            <Option value={0}>男</Option>
            <Option value={1}>女</Option>
          </Select>
        )}
      </FormItem>,
      <FormItem key="birthday" {...this.formLayout} label="出生日期">
        {form.getFieldDecorator("birthday", {
          initialValue: formVals.birthday
          //rules: [{ required: true, message: '请选择时间！' }],
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="mobile" {...this.formLayout} label="移动电话">
        {form.getFieldDecorator("mobile", {
          //  rules: [{ required: true, message: '电话不能为空', min: 0 }],
          initialValue: formVals.mobile
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="officeTel" {...this.formLayout} label="办公电话">
        {form.getFieldDecorator("officeTel", {
          initialValue: formVals.officeTel
          //     rules: [{ required: true, message: '电话不能为空', min: 0 }],
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="role" {...this.formLayout} label="角色">
        {form.getFieldDecorator("role", {
          initialValue: formVals.role
        })(
          <Select style={{ width: "100%" }} mode={"multiple"}>
            <Option value={"用户管理员"}>用户管理员</Option>
            <Option value={"角色管理员"}>角色管理员</Option>
            <Option value={"服务管理员"}>服务管理员</Option>
            <Option value={"管理员"}>管理员</Option>
          </Select>
        )}
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
    const { updateModalVisible, handleUpdateModalVisible, values } = this.props;
    const { currentStep, formVals } = this.state;
    return (
      <Modal
        width={640}
        bodyStyle={{ padding: "32px 40px 48px" }}
        destroyOnClose
        title="编辑用户"
        visible={updateModalVisible}
        footer={this.renderFooter(currentStep)}
        onCancel={() => handleUpdateModalVisible(false, values)}
        afterClose={() => handleUpdateModalVisible()}
      >
        {this.renderContent(currentStep, formVals)}
      </Modal>
    );
  }
}
//服务授权部分，导入服务列表
const AuthorizeForm = Form.create()(props => {
  const {
    authorizemodalVisible,
    form,
    handleAuthorize,
    handleAuthorizeModalVisible,
    selectedApps
  } = props;
  const okAHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleAuthorize(fieldsValue);
    });
  };
  var columns = [
    {
      title: "App",
      dataIndex: "app"
    },
    {
      title: "InstanceID",
      dataIndex: "instanceId"
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
    }
  ];
  var data = [
    {
      key: "1",
      app: "App1",
      instanceId: "S1",
      ipAddr: "10.0.0.0",
      port: "8080",
      userid: "张三"
    },
    {
      key: "2",
      app: "App2",
      instanceId: "S2",
      ipAddr: "10.0.0.0",
      port: "8080",
      userid: "李四"
    },
    {
      key: "3",
      app: "App3",
      instanceId: "S3",
      ipAddr: "10.0.0.0",
      port: "8080",
      userid: "王五"
    },
    {
      key: "4",
      app: "App4",
      instanceId: "S4",
      ipAddr: "10.0.0.0",
      port: "8080",
      userid: "赵六"
    }
  ];
  var rowSelection = {
    onSelect: function(record, selected, selectedRows) {
      selectedApps.push(record.key);
      //console.log(record, selected, selectedRows);
    },
    onSelectAll: function(selected, selectedRows) {
      //console.log(selected, selectedRows);
    }
  };
  return (
    <Modal
      destroyOnClose
      title="服务授权"
      visible={authorizemodalVisible}
      onOk={okAHandle}
      onCancel={() => handleAuthorizeModalVisible()}
    >
      <Table
        columns={columns}
        rowSelection={rowSelection}
        rowKey={record => record.key} //特别注意，需要设置表格主键唯一id的名称，以优化react显示
        dataSource={data}
      />
    </Modal>
  );
});
//角色批量授权
const RoleForm = Form.create()(props => {
  const {
    rolemodalVisible,
    form,
    handleRole,
    handleRoleModalVisible,
    selectedRoles
  } = props;
  const okRHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleRole(fieldsValue);
    });
  };
  var columns = [
    {
      title: "角色",
      dataIndex: "role"
    }
  ];
  var data = [
    {
      key: "1",
      role: "角色一"
    },
    {
      key: "2",
      role: "角色二"
    },
    {
      key: "3",
      role: "角色三"
    },
    {
      key: "4",
      role: "角色四"
    }
  ];
  var rowSelection = {
    onSelect: function(record, selected, selectedRows) {
      selectedRoles.push(record.key);
      //console.log(record, selected, selectedRows);
    },
    onSelectAll: function(selected, selectedRows) {
      //console.log(selected, selectedRows);
    }
  };
  return (
    <Modal
      destroyOnClose
      title="角色授权"
      visible={rolemodalVisible}
      onOk={okRHandle}
      onCancel={() => handleRoleModalVisible()}
    >
      <Table
        columns={columns}
        rowSelection={rowSelection}
        rowKey={record => record.key} //特别注意，需要设置表格主键唯一id的名称，以优化react显示
        dataSource={data}
      />
    </Modal>
  );
});
//

/* eslint react/no-multi-comp:0 */
@connect(({ found, loading }) => ({
  found,
  loading: loading.models.found
}))
@Form.create()
class Found extends PureComponent {
  state = {
    modalVisible: false,
    updateModalVisible: false,
    expandForm: false,
    selectedRows: [],
    selectedApps: [],
    selectedRoles: [],
    formValues: {},
    stepFormValues: {}
  };

  columns = [
    {
      title: "用户名",
      dataIndex: "username"
      // width:100,
    },
    {
      title: "部门",
      dataIndex: "dept"
    },
    {
      title: "性别",
      dataIndex: "sex",
      //width:100,
      filters: [
        {
          text: sex[0],
          value: 0
        },
        {
          text: sex[1],
          value: 1
        }
      ],
      render(val) {
        return <Badge status={sexMap[val]} text={sex[val]} />;
      }
    },
    {
      title: "出生日期",
      dataIndex: "birthday"
      /*sorter: false,
                  render: val => <span>{moment(val).format('YYYY-MM-DD')}</span>,*/
    },
    {
      title: "移动电话",
      dataIndex: "mobile"
    },
    {
      title: "办公电话",
      dataIndex: "officeTel"
    },
    {
      title: "角色",
      dataIndex: "role"
    },
    {
      title: "操作",
      //fixed: 'right',
      render: (text, record) => (
        <Fragment>
          <a onClick={() => this.handleUpdateModalVisible(true, record)}>
            编辑
          </a>
          {/* <Divider type="vertical" />
          <a href="">授权</a>*/}
        </Fragment>
      )
    }
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "found/fetch"
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
      type: "found/fetch",
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
      type: "found/fetch",
      payload: {}
    });
    /* dispatch({
             type: "found/fetch",
             payload: {
                 users: row,
                 apps : row
             }
         });*/
  };

  toggleForm = () => {
    const { expandForm } = this.state;
    this.setState({
      expandForm: !expandForm
    });
  };

  handleMenuClick = e => {
    const { dispatch, modalVisible } = this.props;
    const { selectedRows } = this.state;
    const okHandle = () => {
      form.validateFields((err, fieldsValue) => {
        if (err) return;
        form.resetFields();
        handleAdd(fieldsValue);
      });
    };

    if (!selectedRows) return;
    switch (e.key) {
      case "remove":
        dispatch({
          type: "found/remove",
          payload: {
            key: selectedRows.map(row => row.key)
          },
          callback: () => {
            this.setState({
              selectedRows: []
            });
          }
        });
        //console.log(selectedRows.map(row => row.key));能输出要删除行的key
        break;
      //
      case "roleAuthorize":
        {
          this.handleRoleModalVisible(true);
          //console.log("the test is so difficult");
        }
        break;
      case "appAuthorize":
        {
          this.handleAuthorizeModalVisible(true);
          //console.log(selectedRows.map(row => row.key));//为选中用户的ID值
          //console.log("give me a app");
        }
        break;
      //
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
    e.preventDefault();

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
        type: "found/fetch",
        payload: values
      });
    });
  };

  handleModalVisible = flag => {
    this.setState({
      modalVisible: !!flag
    });
  };
  //
  handleAuthorizeModalVisible = flag => {
    this.setState({
      authorizemodalVisible: !!flag
    });
  };
  handleRoleModalVisible = flag => {
    this.setState({
      rolemodalVisible: !!flag
    });
  };
  //
  handleUpdateModalVisible = (flag, record) => {
    this.setState({
      updateModalVisible: !!flag,
      stepFormValues: record || {}
    });
  };

  handleAdd = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "found/add",
      payload: {
        username: fields.username,
        sex: fields.sex,
        dept: fields.dept,
        birthday: fields.birthday,
        mobile: fields.mobile,
        officeTel: fields.officeTel,
        role: fields.role
      }
    });
    message.success("添加成功");
    this.handleModalVisible();
  };
  //
  handleAuthorize = fields => {
    const { dispatch } = this.props;
    const { selectedRows, selectedApps } = this.state;
    let selectedUsers = [];
    selectedRows.map(item => {
      selectedUsers.push(item.key);
    });
    dispatch({
      type: "found/add_user_app",
      payload: {
        users: selectedUsers,
        apps: selectedApps
      }
    });
    message.success("授权成功");
    this.handleAuthorizeModalVisible();
  };
  handleRole = fields => {
    const { dispatch } = this.props;
    const { selectedRows, selectedRoles } = this.state;
    let selectedUsers = [];
    selectedRows.map(item => {
      selectedUsers.push(item.key);
    });
    dispatch({
      type: "found/add_user_role",
      payload: {
        users: selectedUsers,
        roles: selectedRoles
      }
    });
    message.success("授权成功");
    this.handleRoleModalVisible();
  };

  //
  handleUpdate = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "found/update",
      payload: {
        key: fields.key,
        username: fields.username,
        sex: fields.sex,
        dept: fields.dept,
        birthday: fields.birthday,
        mobile: fields.mobile,
        officeTel: fields.officeTel,
        role: fields.role
      }
    });

    message.success("配置成功");
    this.handleUpdateModalVisible();
  };

  renderSimpleForm() {
    const {
      form: { getFieldDecorator }
    } = this.props;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="用户名">
              {getFieldDecorator("username")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="部门">
              {getFieldDecorator("dept")(
                <Select placeholder="请选择" style={{ width: "100%" }}>
                  <Option value={5}>5</Option>
                  <Option value={4}>4</Option>
                  <Option value={3}>3</Option>
                  <Option value={2}>2</Option>
                  <Option value={1}>1</Option>
                  <Option value={0}>0</Option>
                </Select>
              )}
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
                展开 <Icon type="down" />
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
            <FormItem label="用户名">
              {getFieldDecorator("username")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="部门">
              {getFieldDecorator("dept")(
                <Select placeholder="请选择" style={{ width: "100%" }}>
                  <Option value={5}>5</Option>
                  <Option value={4}>4</Option>
                  <Option value={3}>3</Option>
                  <Option value={2}>2</Option>
                  <Option value={1}>1</Option>
                  <Option value={0}>0</Option>
                </Select>
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="移动电话">
              {getFieldDecorator("mobile")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="办公电话">
              {getFieldDecorator("officeTel")(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="性别">
              {getFieldDecorator("sex")(
                <Select placeholder="请选择" style={{ width: "100%" }}>
                  <Option value={0}>男</Option>
                  <Option value={1}>女</Option>
                </Select>
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="出生日期">
              {getFieldDecorator("birthday")(
                <Input placeholder="请输入" />
                /*<DatePicker style={{ width: '100%' }} placeholder="请输入更新日期" />*/
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="角色">
              {getFieldDecorator("role")(<Input placeholder="请输入" />)}
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
      found: { data },
      loading
    } = this.props;
    const {
      selectedRows,
      selectedApps,
      selectedRoles,
      modalVisible,
      authorizemodalVisible,
      rolemodalVisible,
      updateModalVisible,
      stepFormValues
    } = this.state;
    const menu = (
      <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        <Menu.Item key="remove">删除</Menu.Item>
        <Menu.Item key="roleAuthorize">角色授权</Menu.Item>
        <Menu.Item key="appAuthorize">服务授权</Menu.Item>
      </Menu>
    );

    const parentMethods = {
      handleAdd: this.handleAdd,
      handleModalVisible: this.handleModalVisible
    };
    const authorizeMethods = {
      handleAuthorize: this.handleAuthorize,
      handleAuthorizeModalVisible: this.handleAuthorizeModalVisible
    };
    const roleMethods = {
      handleRole: this.handleRole,
      handleRoleModalVisible: this.handleRoleModalVisible
    };
    const updateMethods = {
      handleUpdateModalVisible: this.handleUpdateModalVisible,
      handleUpdate: this.handleUpdate
    };
    return (
      <PageHeaderWrapper title="查询表格">
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderForm()}</div>
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
        <AuthorizeForm
          {...authorizeMethods}
          authorizemodalVisible={authorizemodalVisible}
          selectedApps={selectedApps}
        />
        <RoleForm
          {...roleMethods}
          rolemodalVisible={rolemodalVisible}
          selectedRoles={selectedRoles}
        />
        {stepFormValues && Object.keys(stepFormValues).length ? (
          <UpdateForm
            {...updateMethods}
            updateModalVisible={updateModalVisible}
            values={stepFormValues}
          />
        ) : null}
      </PageHeaderWrapper>
    );
  }
}

export default Found;
