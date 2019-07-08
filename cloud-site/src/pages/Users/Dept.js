import React, { PureComponent } from "react";
import { connect } from "dva";
import { Select, Spin } from "antd";
import styles from "./RoleModal.less";

const { Option } = Select;

const nullSlectItem = {
  label: "",
  key: ""
};

@connect(({ dept }) => {
  const { depts, isLoading } = dept;
  return {
    depts,
    isLoading
  };
})
class Dept extends PureComponent {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "dept/fetch"
    });
  }

  // componentDidUpdate(props){
  //     const {dispatch, value} = this.props;
  //
  //     if (!props.value && !!value && !!value.menus) {
  //         dispatch({
  //             type:'menu/fetchMenus',
  //             payload: value.menus.key,
  //         });
  //     }
  // }
  getDeptsOption() {
    const { depts } = this.props;
    return this.getOption(depts);
  }

  getOption = list => {
    if (!list || list.length < 1) {
      return (
        <Option key={0} value={0}>
          {/*<Option value={0}>*/}
          没找到选项
        </Option>
      );
    }
    return list.map(item => (
      <Option key={item.id} value={item.id}>
        {/*<Option value={item.id}>*/}
        {item.deptName}
      </Option>
    ));
    //console.log(item.id);
  };

  selectDeptsItem = item => {
    const { dispatch, onChange } = this.props;
    dispatch({
      type: "dept/fetch",
      payload: item.key
    });
    onChange({
      depts: item
    });
    console.log(item);
  };

  conversionObject() {
    const { value } = this.props;
    if (!value) {
      return {
        depts: nullSlectItem
      };
    }
    const { depts } = value;
    return {
      depts: depts || nullSlectItem
    };
  }
  render() {
    const { depts } = this.conversionObject();
    const { isLoading } = this.props;
    return (
      <Spin spinning={isLoading} wrapperClassName={styles.row}>
        <Select
          // mode={"multiple"}
          className={styles.item}
          value={depts}
          labelInValue
          showSearch
          onSelect={this.selectDeptsItem}
        >
          {this.getDeptsOption()}
        </Select>
      </Spin>
    );
  }
}
export default Dept;
