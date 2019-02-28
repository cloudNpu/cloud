import React, { PureComponent } from 'react';
import { connect } from 'dva';
import {Form, Select, Input, Button, Card} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

@connect(({ addRole }) => ({
    addRole,
}))

class AddRole extends PureComponent {
    state = {
        loading: false
    };

    componentDidMount() {
        const { dispatch } = this.props;
        dispatch({
            type: 'addRole/menu',
        });
    }

    render() {

        const {addRole: { menu }} = this.props;

        const Option = Select.Option;
        const FormItem = Form.Item;
        const { getFieldProps } = this.props.form;
        //表单项栅格设置
        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 },
        };
        //下拉列表项
        const children = [];
        for (let i = 0; i < menu.length; i++) {
            children.push(<Option key={i} value={ menu[i].roleMenu }>{ menu[i].roleMenu }</Option>);
        }
        return (
            <PageHeaderWrapper>
                <Card bordered={false}>
                <FormItem {...formItemLayout} label="角色名称" >
                    <Input {...getFieldProps('name')}  style={{ width:"300px" }} />
                </FormItem>
                <FormItem {...formItemLayout} label="VALUE" >
                    <Input {...getFieldProps('value')} style={{ width:"300px" }} />
                </FormItem>
                <FormItem {...formItemLayout} label="角色描述" >
                    <Input {...getFieldProps('description')} style={{ width:"300px" }} />
                </FormItem>
                <FormItem {...formItemLayout} label="角色授权" >
                    <Select  mode={"multiple"} {...getFieldProps('roleMenu')} style={{ width:"300px" }} >
                        {children}
                    </Select>
                </FormItem>

                <FormItem wrapperCol={{ span: 12, offset: 6 }} >
                    <Button type="primary" htmlType="submit">提交</Button>
                </FormItem>
                </Card>
            </PageHeaderWrapper>
        );
    }
}
const addRole = Form.create()(AddRole);
export default addRole;
