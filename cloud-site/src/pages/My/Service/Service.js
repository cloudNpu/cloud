import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import styles from "./Service.less";

const Columns = [
  {
    title: "App",
    dataIndex: "app",
    key: "app"
  },
  {
    title: "InstanceID",
    dataIndex: "instanceId",
    key: "instanceId"
  },
  {
    title: "  IpAddr",
    dataIndex: "ipAddr",
    key: "ipAddr"
  },
  {
    title: "Port",
    dataIndex: "port",
    key: "port"
  },
  {
    title: "AppGroupName",
    dataIndex: "appGroupName",
    key: "appGroupName"
  }
];

@connect(({ service, loading }) => ({
  service,
  loading: loading.effects["service/fetch"]
}))
class Service extends Component {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "service/fetch"
    });
  }
  render() {
    const { service, loading } = this.props;
    const { ServiceData } = service;

    return (
      <PageHeaderWrapper title="我的服务">
        <Card bordered={false}>
          <Table
            style={{ marginBottom: 16 }}
            pagination={false}
            loading={loading}
            dataSource={ServiceData}
            columns={Columns}
          />
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default Service;
