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
  Radio
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
          <Divider type="vertical" />
          <a href="">授权</a>
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
      modalVisible,
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
