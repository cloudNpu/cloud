import React, { PureComponent } from "react";
import { Card, Form, Icon, Popover } from "antd";
import { connect } from "dva";
import router from "umi/router";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import MenusTable from "./MenusTable";
import styles from "./style.less";

const fieldLabels = {
    menuName: "部门名称"
};

const tableData = [
    {
        id: "1",
        menuName: "技术部",
        menuitem: "技术部"
    },
    {
        id: "2",
        menuName: "人力资源部",
        menuItem: "人力资源部"
    },
    {
        id: "3",
        menuName: "市场部",
        menuItem: "市场部"
    }
];

const formItemLayout = {
    labelCol: {
        span: 5
    },
    wrapperCol: {
        span: 19
    }
};

@connect(({ loading }) => ({
    loading: loading.models.menu
}))
@Form.create()
class First extends PureComponent {
    state = {
        width: "100%"
    };

    componentDidMount() {
        window.addEventListener("resize", this.resizeFooterToolbar, {
            passive: true
        });
    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resizeFooterToolbar);
    }

    getErrorInfo = () => {
        const {
            form: { getFieldsError }
        } = this.props;
        const errors = getFieldsError();
        const errorCount = Object.keys(errors).filter(id => errors[id]).length;
        if (!errors || errorCount === 0) {
            return null;
        }
        const scrollToField = fieldKey => {
            const labelNode = document.querySelector(`label[for="${fieldKey}"]`);
            if (labelNode) {
                labelNode.scrollIntoView(true);
            }
        };
        const errorList = Object.keys(errors).map(id => {
            if (!errors[id]) {
                return null;
            }
            return (
                <li
                    id={id}
                    className={styles.errorListItem}
                    onClick={() => scrollToField(id)}
                >
                    <Icon type="cross-circle-o" className={styles.errorIcon} />
                    <div className={styles.errorMessage}>{errors[id][0]}</div>
                    <div className={styles.errorField}>{fieldLabels[id]}</div>
                </li>
            );
        });
        return (
            <span className={styles.errorIcon}>
        <Popover
            title="菜单校验信息"
            content={errorList}
            overlayClassName={styles.errorPopover}
            trigger="click"
            getPopupContainer={trigger => trigger.parentNode}
        >
          <Icon type="exclamation-circle" />
        </Popover>
                {errorCount}
      </span>
        );
    };

    resizeFooterToolbar = () => {
        requestAnimationFrame(() => {
            const sider = document.querySelectorAll(".ant-layout-sider")[0];
            if (sider) {
                const width = `calc(100% - ${sider.style.width})`;
                const { width: stateWidth } = this.state;
                if (stateWidth !== width) {
                    this.setState({ width });
                }
            }
        });
    };

    render() {
        const {
            form: { getFieldDecorator }
        } = this.props;
        const { width } = this.state;

        return (
            <PageHeaderWrapper>
                <Card className={styles.card} bordered={false}>
                    <Card title="菜单列表" bordered={false}>
                        {getFieldDecorator("menus", {
                            initialValue: tableData
                        })(<MenusTable />)}
                    </Card>
                </Card>
            </PageHeaderWrapper>
        );
    }
}

export default First;
