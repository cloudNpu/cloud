import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider, Pagination } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";

const Columns = [
  {
    title: "environment",
    dataIndex: "environment"
  },
  {
    title: "num-of-cpus",
    dataIndex: "num-of-cpus"
  },
  {
    title: "total-avail-memory",
    dataIndex: "total-avail-memory"
  },
  {
    title: "current-memory-usage",
    dataIndex: "current-memory-usage"
  },
  {
    title: "server-uptime",
    dataIndex: "server-uptime"
  }
];

@connect(({ pieChart, loading }) => ({
  pieChart,
  loading: loading.effects["pieChart/fetchStatus"]
}))
class status extends Component {
  componentDidMount() {
    this.timer = setInterval(
      function() {
        const { dispatch } = this.props;
        dispatch({
          type: "pieChart/fetchStatus"
        });
      }.bind(this),
      5000
    );
  }
  render() {
    const { pieChart, loading } = this.props;
    const { statusData } = pieChart;
    return (
      <Card bordered={false}>
        <Table
          style={{ marginBottom: 16 }}
          pagination={false}
          loading={loading}
          dataSource={statusData}
          columns={Columns}
        />
      </Card>
    );
  }
}

export default status;
