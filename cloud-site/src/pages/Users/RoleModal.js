import React, { PureComponent, Fragment } from "react";
import { connect } from "dva";
import { Row, Form, Input, Select, Steps } from "antd";
import StandardTable from "@/components/StandardTable";

const FormItem = Form.Item;
const { Step } = Steps;
const { TextArea } = Input;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(",");

@connect(({ rolesearch, loading }) => ({
  rolesearch,
  loading: loading.models.rolesearch
}))
@Form.create()
class Search extends PureComponent {
  state = {
    modalVisible: false,
    selectedRoleRows: [],
    stepFormValues: {}
  };

  columns = [
    {
      title: "角色名称",
      dataIndex: "name"
    },
    {
      title: "描述",
      dataIndex: "description"
    }
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "rolesearch/fetch",
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
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: "rolesearch/fetch",
      payload: params
    });
  };

  handleSelectRoleRows = rows => {
    this.setState({
      selectedRoleRows: rows
    });
  };
  render() {
    const {
      rolesearch: { data },
      loading
    } = this.props;
    const { selectedRoleRows } = this.state;
    sessionStorage.setItem(
      "selectedRoleRows",
      JSON.stringify(selectedRoleRows)
    );
    return (
      <StandardTable
        selectedRows={selectedRoleRows}
        loading={loading}
        data={data}
        columns={this.columns}
        onSelectRow={this.handleSelectRoleRows}
        onChange={this.handleStandardTableChange}
      />
    );
  }
}

export default Search;
