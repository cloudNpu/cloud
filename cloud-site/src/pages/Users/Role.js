import React, { PureComponent } from "react";
import { connect } from "dva";
import { Select, Spin } from "antd";
import styles from "./RoleModal.less";

const { Option } = Select;

const nullSlectItem = {
  label: "",
  key: ""
};

@connect(({ role }) => {
  const { roles, isLoading } = role;
  return {
    roles,
    isLoading
  };
})
class Role extends PureComponent {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "role/fetch"
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
  getRolesOption() {
    const { roles } = this.props;
    return this.getOption(roles);
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
    // console.log(list);
    return list.map(item => (
      <Option key={item.id} value={item.id}>
        {/*<Option value={item.id}>*/}
        {item.name}
      </Option>
    ));
  };

  selectRolesItem = item => {
    const { dispatch, onChange } = this.props;
    dispatch({
      type: "dept/fetch",
      payload: item.key
    });
    onChange({
      roles: item
    });
  };

  conversionObject() {
    const { value } = this.props;
    if (!value) {
      return {
        roles: nullSlectItem
      };
    }
    const { roles } = value;
    return {
      roles: roles || nullSlectItem
    };
  }
  render() {
    const { roles } = this.conversionObject();
    const { isLoading } = this.props;
    return (
      <Spin spinning={isLoading} wrapperClassName={styles.row}>
        <Select
          // mode={"multiple"}
          className={styles.item}
          value={roles}
          labelInValue
          showSearch
          onSelect={this.selectRolesItem}
        >
          {this.getRolesOption()}
        </Select>
      </Spin>
    );
  }
}
export default Role;
