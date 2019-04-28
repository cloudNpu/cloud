import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider, Pagination } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import { Pie } from "@/components/Charts";

@connect(({ pieChart, loading }) => ({
  pieChart,
  loading: loading.effects[" pieChart/fetchStatus"]
}))
class pieChart extends Component {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "pieChart/fetchStatus"
    });
  }
  render() {
    const { pieChart, loading } = this.props;
    const { perData } = pieChart;
    return (
      <Card bordered={false}>
        <Pie
          percent={perData}
          subTitle="current-memory-usage"
          total={`${perData}`}
          height={280}
        />
      </Card>
    );
  }
}

export default pieChart;
