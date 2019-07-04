import React, { PureComponent, Fragment } from "react";
import { connect } from "dva";
import { Form } from "antd";
import StandardTable from "@/components/StandardTable";

const FormItem = Form.Item;
/* eslint react/no-multi-comp:0 */
@connect(({ applist, loading }) => ({
  applist,
  loading: loading.models.applist
}))
@Form.create()
class AppList extends PureComponent {
  state = {
    selectedAppRows: []
  };
  columns = [
    {
      title: "App",
      dataIndex: "appName",
      width: 70
    },
    {
      title: "InstanceID",
      dataIndex: "instanceId",
      width: 40
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
      title: "Visible",
      dataIndex: "visible",
      render: visible => <div>{visible ? "可见" : "不可见"}</div>
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

  handleSelectRows = rows => {
    this.setState({
      selectedAppRows: rows
    });
  };
  render() {
    const {
      applist: { data },
      loading
    } = this.props;
    const { selectedAppRows } = this.state;
    console.log(selectedAppRows);
    sessionStorage.setItem("selectedAppRows", JSON.stringify(selectedAppRows));
    return (
      <StandardTable
        selectedRows={selectedAppRows}
        loading={loading}
        data={data}
        rowKey={record => record.id}
        columns={this.columns}
        onSelectRow={this.handleSelectRows}
        onChange={this.handleStandardTableChange}
      />
    );
  }
}
export default AppList;
