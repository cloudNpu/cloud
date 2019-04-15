import React, { PureComponent } from "react";
import { connect } from "dva";
import { Select, Spin } from "antd";
import styles from "./Search.less";

const { Option } = Select;

const nullSlectItem = {
  label: "",
  key: ""
};

@connect(({ menu }) => {
  const { menus, isLoading } = menu;
  return {
    menus,
    isLoading
  };
})
class MenuView extends PureComponent {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "menu/fetchMenus"
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
  getMenusOption() {
    const { menus } = this.props;
    return this.getOption(menus);
  }

  getOption = list => {
    if (!list || list.length < 1) {
      return (
        //   <Option key={0} value ={0}>
        <Option value={0}>没找到选项</Option>
      );
    }
    // console.log(list);
    return list.map(item => (
      // <Option key={item.id} value={item.id}>
      <Option value={item.id}>{item.name}</Option>
    ));
  };

  selectMenusItem = item => {
    const { dispatch, onChange } = this.props;
    dispatch({
      type: "menu/fetchMenus",
      payload: item.key
    });
    onChange({
      menus: item
    });
  };

  conversionObject() {
    const { value } = this.props;
    if (!value) {
      return {
        menus: nullSlectItem
      };
    }
    const { menus } = value;
    return {
      menus: menus || nullSlectItem
    };
  }
  render() {
    const { menus } = this.conversionObject();
    const { isLoading } = this.props;
    return (
      <Spin spinning={isLoading} wrapperClassName={styles.row}>
        <Select
          mode={"multiple"}
          className={styles.item}
          value={menus}
          labelInValue
          showSearch
          onSelect={this.selectMenusItem}
        >
          {this.getMenusOption()}
        </Select>
      </Spin>
    );
  }
}
export default MenuView;
