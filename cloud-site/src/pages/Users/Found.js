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
import RoleModal from "./RoleModal";
import Dept from "./Dept";
import Role from "./Role";
import AppModal from "./AppModal";
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
          rules: [{ required: true, message: "用户名长度1-10！", min: 0,max:10 }]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="性别">
        {form.getFieldDecorator("sex", {
            rules: [{ required: true, message: "请选择用户性别！" }]
        })(
          <Select style={{ width: "100%" }} /* mode={'multiple'}*/>
            <Option value={"男"}>男</Option>
            <Option value={"女"}>女</Option>
          </Select>
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="出生日期"
      >
        {form.getFieldDecorator("birthday", {
            rules: [{ required: true, message: "请输入正确格式", min: 19 ,max: 19}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="移动电话"
      >
        {form.getFieldDecorator("mobile", {
            rules: [{required: true, message: "请输入正确格式"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="办公电话"
      >
        {form.getFieldDecorator("officeTel", {
            rules: [{ required: true, message: "请输入正确格式"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="部门">
        {form.getFieldDecorator("dept", {
            rules: [{ required: true, message: "请选择部门"}]
        })(<Dept />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="角色">
        {form.getFieldDecorator("role", {
            rules: [{ required: true, message: "请给用户授权"}]
        })(<Role />)}
      </FormItem>
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
        id: props.values.id,
        username: props.values.username,
        sex: props.values.sex,
        dept: props.values.deptName,
        birthday: props.values.birthday,
        mobile: props.values.mobile,
        officeTel: props.values.officeTel,
        role: props.values.roles
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

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    return [
      <FormItem key="username" {...this.formLayout} label="用户名">
        {form.getFieldDecorator("username", {
          initialValue: formVals.username,
            rules: [{ required: true, message: "用户名长度1-10！", min: 0,max:10 }]
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="sex" {...this.formLayout} label="性别">
        {form.getFieldDecorator("sex", {
          initialValue: formVals.sex,
            rules: [{ required: true, message: "请选择用户性别！" }]
        })(
          <Select style={{ width: "100%" }} /* mode={'multiple'}*/>
            <Option value={"男"}>男</Option>
            <Option value={"女"}>女</Option>
          </Select>
        )}
      </FormItem>,
      <FormItem key="birthday" {...this.formLayout} label="出生日期">
        {form.getFieldDecorator("birthday", {
          initialValue: formVals.birthday,
            rules: [{ required: true, message: "请输入正确格式", min: 19 ,max: 19}]
          //rules: [{ required: true, message: '请选择时间！' }],
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="mobile" {...this.formLayout} label="移动电话">
        {form.getFieldDecorator("mobile", {
          //  rules: [{ required: true, message: '电话不能为空', min: 0 }],
          initialValue: formVals.mobile,
            rules: [{ required: true, message: "请输入正确格式"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="officeTel" {...this.formLayout} label="办公电话">
        {form.getFieldDecorator("officeTel", {
          initialValue: formVals.officeTel,
            rules: [{ required: true, message: "请输入正确格式"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="dept" {...this.formLayout} label="部门">
        {form.getFieldDecorator("dept", {
            rules: [{ required: true, message: "请选择部门"}],
          // initialValue: formVals.dept
        })(<Dept placeholder="请输入"/>)}
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
  return (
    <Modal
      destroyOnClose
      width={700}
      title="服务授权"
      visible={authorizemodalVisible}
      onOk={okAHandle}
      onCancel={() => handleAuthorizeModalVisible()}
    >
      <FormItem key="app1">
        {form.getFieldDecorator("app1", {})(<AppModal />)}
      </FormItem>
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
  return (
    <Modal
      destroyOnClose
      title="角色授权"
      visible={rolemodalVisible}
      onOk={okRHandle}
      onCancel={() => handleRoleModalVisible()}
    >
      <FormItem key="role1">
        {form.getFieldDecorator("role1", {})(<RoleModal />)}
      </FormItem>
      {/*<Table
        columns={columns}
        rowSelection={rowSelection}
        rowKey={record => record.key} //特别注意，需要设置表格主键唯一id的名称，以优化react显示
        dataSource={data}
      />*/}
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
      title: "ID",
      dataIndex: "id"
    },
    {
      title: "用户名",
      dataIndex: "username"
      // width:100,
    },
    {
      title: "部门",
      dataIndex: "deptName"
    },
    {
      title: "性别",
      dataIndex: "sex"
      /*filters: [
        {
          text: sex[0],
          value:"男"
        },
        {
          text: sex[1],
          value: "女"
        }
      ],
      render(val) {
        return <Badge status={sexMap[val]} text={sex[val]} />;
      }*/
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
      dataIndex: "roles"
    },
    {
      title: "操作",
      //fixed: 'right',
      render: (text, record) => (
        <Fragment>
          <a onClick={() => this.handleUpdateModalVisible(true, record)}>
            编辑
          </a>
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
    console.log(this.state);
    console.log(selectedRows);

    if (!selectedRows) return;
    switch (e.key) {
      case "remove":
        let a = selectedRows.map(row => row.id);
        console.log(a);
        dispatch({
          type: "found/remove",
          payload: {
            ids: a
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
        //console.log(values);
      if(values.deptId!==undefined) {
        //console.log('111');
          values.deptId=values.deptId.depts.key;
      };

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
    let h = JSON.parse(sessionStorage.getItem("user")).id;
    dispatch({
      type: "found/add",
      payload: {
        user: {
          username: fields.username,
          password: "888888",
          sex: fields.sex,
          birthday: fields.birthday,
          mobile: fields.mobile,
          officeTel: fields.officeTel,
          dept: { id: fields.dept.depts.key }
        },
        operatorId: h,
        roleIds: [fields.role.roles.key]
      }
    });
    message.success("添加成功");
    this.handleModalVisible();
  };
  //服务授权
  handleAuthorize = fields => {
    const { dispatch } = this.props;
    const { selectedRows, selectedApps } = this.state;
    let g = JSON.parse(sessionStorage.getItem("selectedAppRows"));
    console.log(g[0].appName);
    let h = JSON.parse(sessionStorage.getItem("user")).id;
    let selectedUsers = [];
    selectedRows.map(item => {
      selectedUsers.push(item.id);
    });
    dispatch({
      type: "found/add_user_app",
      payload: {
        appName: g[0].appName,
        user: {
          id: selectedUsers[0]
        },
        operator: {
          id: h
        }
      }
    });
    message.success("授权成功");
    this.handleAuthorizeModalVisible();
  };
  // 角色授权
  handleRole = fields => {
    const { dispatch } = this.props;
    const { selectedRows, selectedRoles } = this.state;
    let e = JSON.parse(sessionStorage.getItem("selectedRoleRows"));
    for (let i = 0; i < e.length; i++) {
      selectedRoles.push(e[i].id);
    }
    let f = [];
    f.push(JSON.parse(sessionStorage.getItem("user")).id);
    let selectedUsers = [];
    selectedRows.map(item => {
      selectedUsers.push(JSON.parse(item.id));
    });
    dispatch({
      type: "found/add_user_role",
      payload: {
        userIds: selectedUsers,
        roleIds: selectedRoles,
        operatorId: f
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
        id: fields.id,
        username: fields.username,
        sex: fields.sex,
        birthday: fields.birthday,
        mobile: fields.mobile,
        officeTel: fields.officeTel,
        dept: { id: fields.dept.depts.key }
        // createdate: "20190101"
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
              {getFieldDecorator("deptId")(
                  <Dept/>
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
              {getFieldDecorator("deptId")(
                  <Dept/>
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
                  <Option value={"男"}>男</Option>
                  <Option value={"女"}>女</Option>
                </Select>
              )}
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
      <PageHeaderWrapper title="用户管理">
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
              rowKey={record => record.id}
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
