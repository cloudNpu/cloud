import React, { PureComponent } from "react";
import { connect } from "dva";
import { Select, Spin } from "antd";
import styles from "./style.less";

const { Option } = Select;

const nullSlectItem = {
  label: "",
  key: ""
};

@connect(({ menuF }) => {
  const { menuFs, isLoading } = menuF;
  return {
    menuFs,
    isLoading
  };
})
class MenuFView extends PureComponent {
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "menuF/fetchMenuFs"
    });
  }

  // componentDidUpdate(props){
  //     const {dispatch, value} = this.props;
  //
  //     if (!props.value && !!value && !!value.menus) {
  //         dispatch({
  //             type:'menuF/fetchMenuFs',
  //             payload: value.menuFs.key,
  //         });
  //     }
  // }
  //
  getMenuFsOption() {
    const { menuFs } = this.props;
    //  console.log(menuFs.map(item=>item.id));
    //  return this.getOption(menuFs);
    return this.getOption(menuFs); //下拉列表项
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
    // console.log(list); //下拉列表项
    return list.map(item => (
      <Option key={item.id} value={item.id}>
        {/*<Option value={item.id}>*/}
        {item.name}
      </Option>
    ));
  };

  selectMenuFsItem = item => {
    const { dispatch, onChange } = this.props;
    dispatch({
      type: "menuF/fetchMenuFs",
      payload: item.key
    });
    //   console.log(item.key);   //打印结果是所选的菜单的id
    onChange({
      menuFs: item
    });
  };

  conversionObject() {
    const { value } = this.props;
    if (!value) {
      return {
        menuFs: nullSlectItem
      };
    }
    const { menuFs } = value;
    return {
      menuFs: menuFs || nullSlectItem
    };
  }
  render() {
    const { menuFs } = this.conversionObject();
    const { isLoading } = this.props;
    return (
      <Spin spinning={isLoading} wrapperClassName={styles.row}>
        <Select
          // mode={"multiple"}
          className={styles.item}
          value={menuFs} //将选中的显示在表格中
          labelInValue
          showSearch
          onSelect={this.selectMenuFsItem}
        >
          {this.getMenuFsOption()}
        </Select>
      </Spin>
    );
  }
}
export default MenuFView;
