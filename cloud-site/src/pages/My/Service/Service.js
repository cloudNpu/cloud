import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import styles from "./Service.less";

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
  loading: loading.effects["service/fetch"]
}))
class Service extends Component {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "service/fetch"
      //   payload:localStorageStorage.getItem("antd-pro-authority")
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
