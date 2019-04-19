import React, { PureComponent, Fragment } from "react";
import { connect } from "dva";
import {
  Card,
  Form,
  Input,
  Icon,
  Button,
  Dropdown,
  Menu,
  Modal,
  message,
  Table,
  Steps
} from "antd";
import StandardTable from "@/components/StandardTable";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import styles from "./style.less";
import MenuFView from "./MenuFView";

const FormItem = Form.Item;
const { Step } = Steps;
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
      title="添加菜单"
      visible={modalVisible}
      onOk={okHandle}
      onCancel={() => handleModalVisible()}
    >
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="菜单名称"
      >
        {form.getFieldDecorator("name", {
          rules: [{ required: true, message: "菜单名称不能为空！" }]
        })(<Input placeholder="请输入" />)}
      </FormItem>

      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="父级菜单"
      >
        {form.getFieldDecorator("menuFidName", {
          rules: [{ required: true, message: "父级菜单不能为空！" }]
        })(<MenuFView />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="ICON">
        {form.getFieldDecorator("icon", {})(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="路径">
        {form.getFieldDecorator("path", {
          // rules: [{required: true, message: "路径不能为空！"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="组件路径"
      >
        {form.getFieldDecorator("component", {
          //  rules: [{required: true, message: "组件路径不能为空！"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="路由">
        {form.getFieldDecorator("routes", {
          // rules: [{required: true, message: "路由不能为空！"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="权限">
        {form.getFieldDecorator("authority", {
          // rules: [{required: true, message: "权限不能为空！"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="隐藏菜单"
      >
        {form.getFieldDecorator("hideInMenu", {
          // rules: [{required: true, message: "隐藏菜单不能为空！"}]
        })(<Input placeholder="请输入" />)}
      </FormItem>
      <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="描述">
        {form.getFieldDecorator("description", {})(
          <Input placeholder="请输入" />
        )}
      </FormItem>
    </Modal>
  );
});

@Form.create()
class UpdateForm extends PureComponent {
  // static defaultProps = {
  //     handleUpdate: () => {},
  //     handleUpdateModalVisible: () => {},
  //     values: {}
  // };
  constructor(props) {
    super(props);

    this.state = {
      formVals: {
        name: props.values.name,
        menuFidName: props.values.menuFidName,
        icon: props.values.icon,
        path: props.values.path,
        component: props.values.component,
        routes: props.values.routes,
        authority: props.values.authority,
        hideInMenu: props.values.hideInMenu,
        description: props.values.description,
        id: props.values.id
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

  renderFooter = currentStep => {
    const { handleUpdateModalVisible } = this.props;
    return [
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
        title="编辑菜单"
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

  renderContent = (currentStep, formVals) => {
    const { form } = this.props;
    return [
      <FormItem key="name" {...this.formLayout} label="菜单名称">
        {form.getFieldDecorator("name", {
          rules: [{ required: true, message: "请输入菜单名称！" }],
          initialValue: formVals.name
        })(<Input placeholder="请输入菜单名称" />)}
      </FormItem>,
      <FormItem key="menuFidName" {...this.formLayout} label="父级菜单">
        {form.getFieldDecorator("menuFidName", {
          // rules: [{ required: true, message: "请输入父级菜单！" }],
          initialValue: formVals.menuFidName
        })(<MenuFView />)}
      </FormItem>,
      <FormItem key="icon" {...this.formLayout} label="ICON">
        {form.getFieldDecorator("icon", {
          //rules: [{ required: false, message: "请输入icon！" }],
          initialValue: formVals.icon
        })(<Input placeholder="请输入icon" />)}
      </FormItem>,
      <FormItem key="path" {...this.formLayout} label="路径">
        {form.getFieldDecorator("path", {
          //rules: [{ required: false, message: "请输入路径！" }],
          initialValue: formVals.path
        })(<Input placeholder="请输入路径" />)}
      </FormItem>,
      <FormItem key="component" {...this.formLayout} label="组件路径">
        {form.getFieldDecorator("component", {
          //rules: [{ required: false, message: "请输入组件路径！" }],
          initialValue: formVals.component
        })(<Input placeholder="请输入组件路径" />)}
      </FormItem>,
      <FormItem key="routes" {...this.formLayout} label="路由">
        {form.getFieldDecorator("routes", {
          //rules: [{ required: false, message: "请输入路由！" }],
          initialValue: formVals.routes
        })(<Input placeholder="请输入路由" />)}
      </FormItem>,
      <FormItem key="authority" {...this.formLayout} label="权限">
        {form.getFieldDecorator("authority", {
          //rules: [{ required: false, message: "权限！" }],
          initialValue: formVals.authority
        })(<Input placeholder="请输入权限" />)}
      </FormItem>,
      <FormItem key="hideInMenu" {...this.formLayout} label="隐藏菜单">
        {form.getFieldDecorator("hideInMenu", {
          initialValue: formVals.hideInMenu
        })(<Input placeholder="请输入隐藏菜单" />)}
      </FormItem>,
      <FormItem key="description" {...this.formLayout} label="描述">
        {form.getFieldDecorator("description", {
          initialValue: formVals.description
        })(<Input placeholder="请输入描述" />)}
      </FormItem>
    ];
  };
}

@connect(({ first, loading }) => ({
  first,
  loading: loading.models.first
}))
@Form.create()
class First extends PureComponent {
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
      title: "菜单名称",
      dataIndex: "name"
    },
    {
      title: "父级菜单",
      dataIndex: "menuFidName"
    },
    {
      title: "ICON",
      dataIndex: "icon"
    },
    {
      title: "路径",
      dataIndex: "path"
    },
    {
      title: "组件路径",
      dataIndex: "component"
    },
    {
      title: "路由",
      dataIndex: "routes"
    },
    {
      title: "权限",
      dataIndex: "authority"
    },
    {
      title: "隐藏菜单",
      dataIndex: "hideInMenu"
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
    dispatch({
      type: "first/fetch",
      payload: {}
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
    // console.log(params);
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: "first/fetch",
      payload: params
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
        // console.log('bhjvjvjvjvj');
        dispatch({
          type: "first/delete",
          payload: {
            id: selectedRows.map(row => row.id)
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
    //  console.log(e.key);
    //  console.log(selectedRows.map(row => row.id));
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

  //添加
  handleAdd = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "first/add",
      payload: {
        name: fields.name,
        menu: { id: fields.menuFidName.menuFs.key },
        //   menu: { id: fields.menuFs.key },
        icon: fields.icon,
        path: fields.path,
        component: fields.component,
        routes: fields.routes,
        authority: fields.authority,
        hideInMenu: fields.hideInMenu,
        description: fields.description
      }
    });
    // console.log(fields.name);
    message.success("添加成功");
    this.handleModalVisible();
  };

  //编辑
  handleUpdate = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: "first/update",
      payload: {
        name: fields.name,
        menu: { id: fields.menuFidName.menuFs.key },
        icon: fields.icon,
        path: fields.path,
        component: fields.component,
        routes: fields.routes,
        authority: fields.authority,
        hideInMenu: fields.hideInMenu,
        description: fields.description,
        id: fields.id
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
      first: { data },
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
      <PageHeaderWrapper title="菜单列表">
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderForm()}</div>
            <div className={styles.tableListOperator}>
              <Button
                icon="plus"
                type="primary"
                onClick={() => this.handleModalVisible(true)}
              >
                添加
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

export default First;
