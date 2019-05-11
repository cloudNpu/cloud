import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider, Pagination } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";

const Columns = [
  {
    title: "环境",
    dataIndex: "environment"
  },
  {
    title: "cpu个数",
    dataIndex: "num-of-cpus"
  },
  {
    title: "总共可用内存",
    dataIndex: "total-avail-memory"
  },
  {
    title: "已使用内存",
    dataIndex: "current-memory-usage"
  },
  {
    title: "服务启动时间",
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
