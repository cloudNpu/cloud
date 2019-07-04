import React, { Component } from "react";
import { connect } from "dva";
import { Card, Badge, Table, Divider } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";


const Columns = [
    {
        title: "ID",
        dataIndex: "id",
    },
    {
        title: "AppName",
        dataIndex: "appName",
    },
    {
        title: "InstanceID",
        dataIndex: "instanceId",
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
        title: "InvokeCount",
        dataIndex: "invokeCount",
        align: "right",
        needTotal: true
    },
    {
        title: "Visible",
        dataIndex: "visible",
        render: visible => <div>{visible ? "已发布" : "已撤回"}</div>
    }
];

@connect(({ myregistry, loading }) => ({
    myregistry,
    loading: loading.effects["myregistry/fetch"],
}))

class MyRegistry extends Component {
    componentDidMount() {
        const { dispatch } = this.props;
        dispatch({
            type: "myregistry/fetch"
        });
    }
    render() {
        const { myregistry, loading } = this.props;
        const { myRegistryApp } = myregistry;

        return (
                <Card bordered={false}>
                    <Table
                        style={{ marginBottom: 16 }}
                        pagination={{ pageSize: 4 }}
                        loading={loading}
                        dataSource={myRegistryApp}
                        columns={Columns}
                    />
                </Card>
        );
    }
}

export default MyRegistry;
