import React, { PureComponent } from "react";
import { connect } from "dva";
import { Form, Select, Input, Button } from "antd";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";

@connect(({ rolesearch }) => ({
  rolesearch
}))
class AddRole extends PureComponent {
  state = {
    loading: false
  };

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: "rolesearch/menu"
    });
  }

  handleSubmit = e => {
    let apirul = "/api/roles";
    //接口地址
    let data = {
      name: this.props.form.getFieldsValue().name,
      value: this.props.form.getFieldsValue().value,
      description: this.props.form.getFieldsValue().description,
      roleMenu: this.props.form.getFieldsValue().roleMenu
    };
    fetch(apirul, {
      method: "post",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })
      .then(function(res) {
        return res.json();
      })
      .then(res => {
        //console.log(res);
      });
  };

  render() {
    const {
      rolesearch: { menu }
    } = this.props;

    const Option = Select.Option;
    const FormItem = Form.Item;
    //获取表单输入值的方法
    const { getFieldProps } = this.props.form;
    //表单项栅格设置
    const formItemLayout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 14 }
    };
    //下拉列表项
    const children = [];
    for (let i = 0; i < menu.length; i++) {
      children.push(
        <Option key={i} value={menu[i].roleMenu}>
          {menu[i].roleMenu}
        </Option>
      );
    }

    return (
      <PageHeaderWrapper>
        <Form onSubmit={this.handleSubmit}>
          <FormItem {...formItemLayout} label="角色名称">
            <Input {...getFieldProps("name")} style={{ width: "300px" }} />
          </FormItem>
          <FormItem {...formItemLayout} label="VALUE">
            <Input {...getFieldProps("value")} style={{ width: "300px" }} />
          </FormItem>
          <FormItem {...formItemLayout} label="角色描述">
            <Input
              {...getFieldProps("description")}
              style={{ width: "300px" }}
            />
          </FormItem>
          <FormItem {...formItemLayout} label="角色授权">
            <Select
              mode={"multiple"}
              {...getFieldProps("roleMenu")}
              style={{ width: "300px" }}
            >
              {children}
            </Select>
          </FormItem>

          <FormItem wrapperCol={{ span: 12, offset: 6 }}>
            {/*<a href={'#role/search'+role.id}>*/}
            <Button
              type="primary"
              htmlType="submit"
              style={{ marginLeft: 100 }}
            >
              提交
            </Button>
            {/* </a>*/}
          </FormItem>
        </Form>
      </PageHeaderWrapper>
    );
  }
}
const addRole = Form.create()(AddRole);
export default addRole;
