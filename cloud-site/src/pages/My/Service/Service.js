import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import MyRegistry from "./MyRegistry";
const Columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id"
  },
  {
    title: "AppName",
    dataIndex: "appname",
    key: "appname"
  },
  {
    title: "userId",
    dataIndex: "userId",
    key: "userId"
  },
  {
    title: "operatorId",
    dataIndex: "operatorId",
    key: "operatorId"
  }
];

@connect(({ service, loading }) => ({
  service,
  loading: loading.effects["service/fetch"],
}))
class Service extends Component {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "service/fetch"
    });
    dispatch({
     type: "myregistry/fetch"
    });
  }
  render() {
    const { service, loading } = this.props;
    const { ServiceData } = service;
    return (
      <PageHeaderWrapper title="我的服务">
        <Card bordered={false}>
          <h3>我可用的服务</h3>
          <Table
            style={{ marginBottom: 16 }}
            pagination={{ pageSize: 4 }}
            loading={loading}
            dataSource={ServiceData}
            columns={Columns}
          />
            <Divider />
            <h3>我注册的服务</h3>
            <MyRegistry />
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default Service;
