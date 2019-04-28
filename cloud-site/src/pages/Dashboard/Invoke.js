import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider, Pagination } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";

const Columns = [
  {
    title: "App",
    dataIndex: "appName"
  },
  {
    title: "InstanceID",
    dataIndex: "instanceId"
  },
  {
    title: "invokeCount",
    dataIndex: "invokeCount"
  }
];

@connect(({ invokeTable, loading }) => ({
  invokeTable,
  loading: loading.effects["invokeTable/fetch"]
}))
class InvokeList extends Component {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "invokeTable/fetch"
    });
  }
  render() {
    const { invokeTable, loading } = this.props;
    const { InvokeData } = invokeTable;
    return (
      <Card bordered={false}>
        <Table
          style={{ marginBottom: 16 }}
          pagination={{ pageSize: 4 }}
          loading={loading}
          dataSource={InvokeData}
          columns={Columns}
        />
      </Card>
    );
  }
}

export default InvokeList;
