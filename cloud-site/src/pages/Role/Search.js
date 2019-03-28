import React, { PureComponent, Fragment } from "react";
import { connect } from "dva";
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
  Modal,
  message,
  Steps,
  Divider
} from "antd";
import StandardTable from "@/components/StandardTable";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import styles from "./Search.less";
const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(",");

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
      title="新建角色"
      visible={modalVisible}
      onOk={okHandle}
      onCancel={() => handleModalVisible()}
    >
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="角色名称"
      >
        {form.getFieldDecorator("name", {
          rules: [{ required: true, message: "角色名不能为空！" }]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="VALUE">
        {form.getFieldDecorator("value", {
          rules: [{ required: true, message: "value值不能为空！" }]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="角色描述"
      >
        {form.getFieldDecorator("description", {
          rules: [
            {
              required: false
            }
          ]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      {/*<FormItem*/}
      {/*labelCol={{ span: 5 }}*/}
      {/*wrapperCol={{ span: 15 }}*/}
      {/*label="角色授权"*/}
      {/*>*/}
      {/*{form.getFieldDecorator("roleAuth", {*/}
      {/*})(*/}
      {/*<Select*/}
      {/*style={{ width: "100%" }}*/}
      {/*mode={"multiple"}*/}
      {/*placeholder={"请选择"}*/}
      {/*>*/}
      {/*<Option value="1">用户管理</Option>*/}
      {/*<Option value="2">角色管理</Option>*/}
      {/*<Option value="3">菜单管理</Option>*/}
      {/*<Option value="4">部门管理</Option>*/}
      {/*</Select>*/}
      {/*)}*/}
      {/*</FormItem>*/}
    </Modal>
  );
});

@Form.create()
class UpdateForm extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      formVals: {
        key: props.values.key,
        name: props.values.name,
        value: props.values.value,
        description: props.values.description
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
    //const { form1, handleMenu } = this.props;
    const { formVals: oldValue } = this.state;
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      const formVals = { ...oldValue, ...fieldsValue };
      this.setState(
        {
          formVals
        },
        () => {
          if (currentStep < 1) {
            this.forward();
          } else {
            handleUpdate(formVals);
          }
        }
      );
    });
    // form1.validateFields((err, fieldsValue) => {
    //     if (err) return;
    //     const formVals = { ...oldValue, ...fieldsValue };
    //     this.setState(
    //         {
    //             formVals
    //         },
    //         () => {
    //             if (currentStep < 1) {
    //                 this.forward();
    //             } else {
    //                 handleMenu(formVals);
    //             }
    //         }
    //     );
    // });
  };

  backward = () => {
    const { currentStep } = this.state;
    this.setState({
      currentStep: currentStep - 1
    });
  };

  forward = () => {
    const { currentStep } = this.state;
    this.setState({
      currentStep: currentStep + 1
    });
  };

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    // if (currentStep === 1) {
    //     return [
    //         <FormItem key="roleAuth" {...this.formLayout} label="角色授权">
    //             {form.getFieldDecorator("roleAuth", {
    //                 initialValue: formVals.roleAuth
    //             })(
    //                 <Select mode={"multiple"} style={{ width: "100%" }}>
    //                     <Option value="1">用户管理</Option>
    //                     <Option value="2">角色管理</Option>
    //                     <Option value="3">菜单管理</Option>
    //                     <Option value="4">部门管理</Option>
    //                 </Select>
    //             )}
    //         </FormItem>
    //     ];
    // }
    return [
      <FormItem key="name" {...this.formLayout} label="角色名称">
        {form.getFieldDecorator("name", {
          rules: [{ required: true, message: "请输入角色名称！" }],
          initialValue: formVals.name
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="value" {...this.formLayout} label="VALUE">
        {form.getFieldDecorator("value", {
          rules: [{ required: true, message: "请输入value值！" }],
          initialValue: formVals.value
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="description" {...this.formLayout} label="角色描述">
        {form.getFieldDecorator("description", {
          rules: [
            {
              required: false
            }
          ],
          initialValue: formVals.description
        })(<TextArea rows={4} />)}
      </FormItem>
    ];
  };

  renderFooter = currentStep => {
    const { handleUpdateModalVisible } = this.props;
    // if (currentStep === 1) {
    return [
      //<Button key="back" style={{ float: "left" }} onClick={this.backward}>
      //  上一步
      //</Button>,
      <Button key="cancel" onClick={() => handleUpdateModalVisible()}>
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
    // }
    //  return [
    //      <Button key="cancel" onClick={() => handleUpdateModalVisible()}>
    //          取消
    //      </Button>,
    //      <Button
    //          key="forward"
    //          type="primary"
    //          onClick={() => this.handleNext(currentStep)}
    //      >
    //          下一步
    //      </Button>
    //  ];
  };

  render() {
    const { updateModalVisible, handleUpdateModalVisible } = this.props;
    const { currentStep, formVals } = this.state;

    return (
      <Modal
        width={640}
        bodyStyle={{ padding: "32px 40px 48px" }}
        destroyOnClose
        title="编辑角色"
        visible={updateModalVisible}
        footer={this.renderFooter(currentStep)}
        onCancel={() => handleUpdateModalVisible()}
      >
        <Steps style={{ marginBottom: 28 }} size="small" current={currentStep}>
          <Step title="基本信息" />
          {/*<Step title="编辑角色属性" />*/}
        </Steps>
        {this.renderContent(currentStep, formVals)}
      </Modal>
    );
  }
}

@Form.create()
class MenuForm extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      formVals: {
        roleMenu: props.values.roleMenu,
        key: props.values.key
      },
      currentStep: 0
    };

    this.formLayout = {
      labelCol: { span: 7 },
      wrapperCol: { span: 13 }
    };
  }

  handleNext = currentStep => {
    const { form, handleMenu } = this.props;
    const { formVals: oldValue } = this.state;
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      const formVals = { ...oldValue, ...fieldsValue };
      this.setState(
        {
          formVals
        },
        () => {
          if (currentStep < 1) {
            this.forward();
          } else {
            handlemenu(formVals);
          }
        }
      );
    });
  };

  forward = () => {
    const { currentStep } = this.state;
    this.setState({
      currentStep: currentStep + 1
    });
  };

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    return [
      <FormItem key="roleMenu" {...this.formLayout} label="角色授权">
        {form.getFieldDecorator("roleMenu", {
          initialValue: formVals.roleMenu
        })(
          // <select name="menuVOs" id="menuVOs">
          //     <c:forEach items="${menuVOs}" var="type">
          //         <option value="${roleMenu_Id}">${roleMenu_Name}</option>
          //     </c:forEach>
          // </select>
          <Select mode={"multiple"} style={{ width: "100%" }}>
            <Option value="1">用户管理</Option>
            <Option value="2">角色管理</Option>
            <Option value="3">菜单管理</Option>
            <Option value="4">部门管理</Option>
          </Select>
        )}
      </FormItem>
    ];
  };

  renderFooter = currentStep => {
    const { handleMenuModalVisible } = this.props;
    return [
      <Button key="cancel" onClick={() => handleMenuModalVisible()}>
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
    const { menuModalVisible, handleMenuModalVisible } = this.props;
    const { currentStep, formVals } = this.state;

    return (
      <Modal
        width={640}
        bodyStyle={{ padding: "32px 40px 48px" }}
        destroyOnClose
        title="角色授权"
        visible={menuModalVisible}
        footer={this.renderFooter(currentStep)}
        onCancel={() => handleMenuModalVisible()}
      >
        <Steps style={{ marginBottom: 28 }} size="small" current={currentStep}>
          <Step title="角色授权" />
        </Steps>
        {this.renderContent(currentStep, formVals)}
      </Modal>
    );
  }
}

@connect(({ rolesearch, loading }) => ({
  rolesearch,
  loading: loading.models.rolesearch
}))
@Form.create()
class Search extends PureComponent {
  state = {
    modalVisible: false,
    updateModalVisible: false,
    menuModalVisible: false,
    expandForm: false,
    selectedRows: [],
    formValues: {},
    stepFormValues: {}
  };

  columns = [
    {
      title: "角色名称",
      dataIndex: "name"
    },
    {
      title: "VALUE",
      dataIndex: "value"
    },
    {
      title: "角色权限",
      dataIndex: "roleMenu"
    },
    {
      title: "描述",
      dataIndex: "description"
    },
    {
      title: "操作",
      render: (text, record) => (
        <Fragment>
          <a onClick={() => this.handleUpdateModalVisible(true, record)}>
            编辑
          </a>
          <Divider type="vertical" />
          <a onClick={() => this.handleMenuModalVisible(true, record)}>授权</a>
        </Fragment>
      )
    }
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    //console.log(dispatch);
    dispatch({
      type: "rolesearch/fetch",
      payload: {}
    });
  }

  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;
    console.log();
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
    //console.log(params);
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: "rolesearch/fetch",
      payload: params
    });
  };

  //重置
  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({
      formValues: {}
    });
    dispatch({
      type: "rolesearch/fetch",
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
      case "delete":
        dispatch({
          type: "rolesearch/delete",
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

  //查询
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
        type: "rolesearch/fetch",
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
  handleMenuModalVisible = (flag, record) => {
    this.setState({
      menuModalVisible: !!flag,
      stepFormValues: record || {}
    });
  };

  //添加
  handleAdd = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "rolesearch/add",
      payload: {
        name: fields.name,
        value: fields.value,
        description: fields.description
      }
    });
    //  console.log(dispatch);
    message.success("添加成功");

    this.handleModalVisible();
  };

  //编辑
  handleUpdate = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "rolesearch/update",
      payload: {
        key: fields.key,
        name: fields.name,
        description: fields.description,
        value: fields.value
      }
    });
    message.success("编辑成功");
    this.handleUpdateModalVisible();
  };

  //授权
  handleMenu = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "rolesearch/menu",
      payload: {
        roleMenu: fields.roleMenu,
        key: fields.key
      }
    });

    message.success("授权成功");
    this.handleMenuModalVisible();
  };

  renderSimpleForm() {
    const {
      form: { getFieldDecorator }
    } = this.props;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="角色名称">
              {getFieldDecorator("name")(<Input placeholder="请输入" />)}
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
            </span>
          </Col>
        </Row>
      </Form>
    );
  }

  renderForm() {
    const { expandForm } = this.state;
    return expandForm ? this.renderAdvancedForm() : this.renderSimpleForm();
  }

  render() {
    const {
      rolesearch: { data },
      loading
    } = this.props;
    //console.log(this.props);
    const {
      selectedRows,
      modalVisible,
      updateModalVisible,
      stepFormValues
    } = this.state;
    const menu = (
      <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        <Menu.Item key="delete">删除</Menu.Item>
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
      <PageHeaderWrapper title="检索角色">
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

export default Search;
