import React, { PureComponent } from "react";
import { Card, Button, Form, Icon, Input, Popover } from "antd";
import { connect } from "dva";
import router from "umi/router";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import DepartmentList from "./DepartmentList";
import styles from "./style.less";

const fieldLabels = {
    deptName: "部门名称"
};

const tableData = [
  {
    key: "1",
   /* departmentId: "01",*/
      deptName: "技术部",
      description: "技术部"
  },
  {
    key: "2",
   /* departmentId: "02",*/
      deptName: "人力资源部",
      description: "人力资源部"
  },
  {
    key: "3",
    /*departmentId: "03",*/
      deptName: "市场部",
      description: "市场部"
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
  /*submitting: loading.effects['department/submitDepartmentEdit'],*/
  loading: loading.models.department
}))
@Form.create()
class DepartmentEdit extends PureComponent {
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
    const errorCount = Object.keys(errors).filter(key => errors[key]).length;
    if (!errors || errorCount === 0) {
      return null;
    }
    const scrollToField = fieldKey => {
      const labelNode = document.querySelector(`label[for="${fieldKey}"]`);
      if (labelNode) {
        labelNode.scrollIntoView(true);
      }
    };
    const errorList = Object.keys(errors).map(key => {
      if (!errors[key]) {
        return null;
      }
      return (
        <li
          key={key}
          className={styles.errorListItem}
          onClick={() => scrollToField(key)}
        >
          <Icon type="cross-circle-o" className={styles.errorIcon} />
          <div className={styles.errorMessage}>{errors[key][0]}</div>
          <div className={styles.errorField}>{fieldLabels[key]}</div>
        </li>
      );
    });
    return (
      <span className={styles.errorIcon}>
        <Popover
          title="部门校验信息"
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
  /* validate = () => {
        const {
            form: { validateFieldsAndScroll },
            dispatch,
        } = this.props;
        validateFieldsAndScroll((error, values) => {
            if (!error) {
                // submit the values
                dispatch({
                    type: 'department/submitDepartmentEdit',
                    payload: values,
                });
            }
        });
    };*/
  render() {
    const {
      form: { getFieldDecorator }
      /* submitting,*/
    } = this.props;
    const { width } = this.state;

    return (
      <PageHeaderWrapper>
        <Card className={styles.card} bordered={false}>
          <Card title="部门列表" bordered={false}>
            {getFieldDecorator("departments", {
              initialValue: tableData
            })(<DepartmentList />)}
          </Card>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default DepartmentEdit;
