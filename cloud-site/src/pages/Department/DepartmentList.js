import React, { PureComponent, Fragment } from "react";
import { Table, Button, Input, message, Popconfirm, Divider } from "antd";
import isEqual from "lodash/isEqual";
import styles from "./style.less";
import { connect } from "dva";

@connect(({ loading }) => ({
  loading: loading.models.department
}))
class DepartmentList extends PureComponent {
  index = 0;
  cacheOriginData = {};
  constructor(props) {
    super(props);
    this.state = {
      data: props.value,
      loading: false,
      value: props.value
    };
  }

  static getDerivedStateDepartmentProps(nextProps, preState) {
    if (isEqual(nextProps.value, preState.value)) {
      return null;
    }
    return {
      data: nextProps.value,
      value: nextProps.value
    };
  }

  getRowByKey(key, newData) {
    const { data } = this.state;
    return (newData || data).filter(item => item.key === key)[0];
  }

  toggleEditable = (e, key) => {
    e.preventDefault();
    const { data } = this.state;
    const newData = data.map(item => ({ ...item }));
    const target = this.getRowByKey(key, newData);
    if (target) {
      // 进入编辑状态时保存原始数据
      if (!target.editable) {
        this.cacheOriginData[key] = { ...target };
      }
      target.editable = !target.editable;
      this.setState({ data: newData });
    }
  };

  //添加
  newMember = () => {
    const { data } = this.state;
    const newData = data.map(item => ({ ...item }));
    newData.push({
      key: `NEW_TEMP_ID_${this.index}`,
      workId: "",
      name: "",
      department: "",
      editable: true,
      isNew: true
    });
    this.index += 1;
    this.setState({ data: newData });
  };

  //删除
  remove(key) {
    const { data } = this.state;
    const { onChange } = this.props;
    const newData = data.filter(item => item.key !== key);
    const { dispatch } = this.props;
    dispatch({
      type: "department/delete",
      payload: {
        dept: data.filter(item => item.key == key)
      }
    });
    this.setState({ data: newData });
    onChange(newData);
  }

  handleKeyPress(e, key) {
    if (e.key === "Enter") {
      this.saveRow(e, key);
    }
  }

  handleFieldChange(e, fieldName, key) {
    const { data } = this.state;
    const newData = data.map(item => ({ ...item }));
    const target = this.getRowByKey(key, newData);
    if (target) {
      target[fieldName] = e.target.value;
      this.setState({ data: newData });
    }
  }

  //保存
  saveRow(e, key) {
    e.persist();
    this.setState({
      loading: true
    });
    setTimeout(() => {
      if (this.clickedCancel) {
        this.clickedCancel = false;
        return;
      }
      const target = this.getRowByKey(key) || {};
      if (
        !target.deptName ||
       /* !target.departmentId ||*/
        !target.description
      ) {
        message.error("请填写完整部门信息。");
        e.target.focus();
        this.setState({
          loading: false
        });
        return;
      }

      // console.log(target);
      const { dispatch } = this.props;
      dispatch({
        type: "department/save",
        payload: {
          dept: target
        }
      });

      delete target.isNew;
      this.toggleEditable(e, key);
      const { data } = this.state;
      const { onChange } = this.props;
      onChange(data);
      this.setState({
        loading: false
      });
    }, 500);
  }

  //取消
  cancel(e, key) {
    this.clickedCancel = true;
    e.preventDefault();
    const { data } = this.state;
    const newData = data.map(item => ({ ...item }));
    const target = this.getRowByKey(key, newData);
    if (this.cacheOriginData[key]) {
      Object.assign(target, this.cacheOriginData[key]);
      delete this.cacheOriginData[key];
    }
    target.editable = false;
    this.setState({ data: newData });
    this.clickedCancel = false;
  }
  render() {
    const columns = [
      {
        title: "部门名称",
        dataIndex: "deptName",
        key: "deptName",
        width: "35%",
        render: (text, record) => {
          if (record.editable) {
            return (
              <Input
                value={text}
                autoFocus
                onChange={e =>
                  this.handleFieldChange(e, "deptName", record.key)
                }
                onKeyPress={e => this.handleKeyPress(e, record.key)}
                placeholder="部门名称"
              />
            );
          }
          return text;
        }
      },
   /*   {
        title: "部门编号",
        dataIndex: "departmentId",
        key: "departmentId",
        width: "20%",
        render: (text, record) => {
          if (record.editable) {
            return (
              <Input
                value={text}
                onChange={e =>
                  this.handleFieldChange(e, "departmentId", record.key)
                }
                onKeyPress={e => this.handleKeyPress(e, record.key)}
                placeholder="部门编号"
              />
            );
          }
          return text;
        }
      },*/
      {
        title: "部门描述",
        dataIndex: "description",
        key: "description",
        width: "35%",
        render: (text, record) => {
          if (record.editable) {
            return (
              <Input
                value={text}
                onChange={e =>
                  this.handleFieldChange(e, "description", record.key)
                }
                onKeyPress={e => this.handleKeyPress(e, record.key)}
                placeholder="部门描述"
              />
            );
          }
          return text;
        }
      },
      {
        title: "操作",
        key: "action",
        render: (text, record) => {
          const { loading } = this.state;
          if (!!record.editable && loading) {
            return null;
          }
          if (record.editable) {
            if (record.isNew) {
              return (
                <span>
                  <a onClick={e => this.saveRow(e, record.key)}>添加</a>
                  <Divider type="vertical" />
                  <Popconfirm
                    title="是否要删除此行？"
                    onConfirm={() => this.remove(record.key)}
                  >
                    <a>删除</a>
                  </Popconfirm>
                </span>
              );
            }
            return (
              <span>
                <a onClick={e => this.saveRow(e, record.key)}>保存</a>
                <Divider type="vertical" />
                <a onClick={e => this.cancel(e, record.key)}>取消</a>
              </span>
            );
          }
          return (
            <span>
              <a onClick={e => this.toggleEditable(e, record.key)}>编辑</a>
              <Divider type="vertical" />
              <Popconfirm
                title="是否要删除此行？"
                onConfirm={() => this.remove(record.key)}
              >
                <a>删除</a>
              </Popconfirm>
            </span>
          );
        }
      }
    ];

    const { loading, data } = this.state;

    return (
      <Fragment>
        <Table
          loading={loading}
          columns={columns}
          dataSource={data}
          pagination={false}
          rowClassName={record => (record.editable ? styles.editable : "")}
        />
        <Button
          style={{ width: "100%", marginTop: 16, marginBottom: 8 }}
          type="dashed"
          onClick={this.newMember}
          icon="plus"
        >
          新增部门
        </Button>
      </Fragment>
    );
  }
}

export default DepartmentList;
