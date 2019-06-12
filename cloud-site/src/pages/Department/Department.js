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
import styles from "./style.less";
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
      title="新建部门"
      visible={modalVisible}
      onOk={okHandle}
      onCancel={() => handleModalVisible()}
    >
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="部门名称"
      >
        {form.getFieldDecorator("deptName", {
          rules: [{ required: true, message: "部门名不能为空！" }]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="部门描述"
      >
        {form.getFieldDecorator("description", {
          rules: [{ required: false }]
        })(<Input placeholder="请输入" />)}
      </FormItem>
    </Modal>
  );
});

@Form.create()
class UpdateForm extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      formVals: {
        id: props.values.id,
        deptName: props.values.deptName,
        description: props.values.description
      },
      currentStep: 1
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
          if (currentStep < 1) {
            this.forward();
          } else {
            handleUpdate(formVals);
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
      <FormItem key="deptName" {...this.formLayout} label="部门名称">
        {form.getFieldDecorator("deptName", {
          rules: [{ required: true, message: "请输入部门名称！" }],
          initialValue: formVals.deptName
        })(<Input placeholder="请输入" />)}
      </FormItem>,
      <FormItem key="description" {...this.formLayout} label="部门描述">
        {form.getFieldDecorator("description", {
          rules: [{ required: false }],
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
  };

  render() {
    const { updateModalVisible, handleUpdateModalVisible } = this.props;
    const { currentStep, formVals } = this.state;

    return (
      <Modal
        width={640}
        bodyStyle={{ padding: "32px 40px 48px" }}
        destroyOnClose
        title="编辑部门"
        visible={updateModalVisible}
        footer={this.renderFooter(currentStep)}
        onCancel={() => handleUpdateModalVisible()}
      >
        <Steps style={{ marginBottom: 28 }} size="small" current={currentStep}>
          <Step title="基本信息" />
        </Steps>
        {this.renderContent(currentStep, formVals)}
      </Modal>
    );
  }
}

@connect(({ department, loading }) => ({
  department,
  loading: loading.models.department
}))
@Form.create()
class Department extends PureComponent {
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
      title: "部门名称",
      dataIndex: "deptName"
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
        </Fragment>
      )
    }
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    //console.log(this.props);
    dispatch({
      type: "department/fetch",
      payload: {}
    });
  }

  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;
    //console.log();
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
      type: "department/fetch",
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
      type: "department/fetch",
      payload: {}
    });
  };

  // toggleForm = () => {
  //     const { expandForm } = this.state;
  //     this.setState({
  //         expandForm: !expandForm
  //     });
  // };

  handleMenuClick = e => {
    const { dispatch } = this.props;
    const { selectedRows } = this.state;
    if (!selectedRows) return;
    switch (e.key) {
      case "delete":
        dispatch({
          type: "department/delete",
          payload: {
            ids: selectedRows.map(row => row.id)
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
      type: "department/add",
      payload: {
        deptName: fields.deptName,
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
      type: "department/update",
      payload: {
        id: fields.id,
        deptName: fields.deptName,
        description: fields.description
      }
    });
    message.success("编辑成功");
    this.handleUpdateModalVisible();
  };

  renderSimpleForm() {
    const {
      form: { getFieldDecorator }
    } = this.props;
  }

  renderForm() {
    const { expandForm } = this.state;
    return expandForm ? this.renderAdvancedForm() : this.renderSimpleForm();
  }

  render() {
    const {
      department: { data },
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
      <PageHeaderWrapper title="部门信息">
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

export default Department;
