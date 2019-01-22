import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { formatMessage, FormattedMessage } from 'umi/locale';
import {Form, Input,  Button, Card} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
//import styles from './style.less';

const FormItem = Form.Item;

@connect(({ loading }) => ({
    loading: loading.models.menusAdd
}))
@Form.create()
class Add extends PureComponent {
    handleSubmit = e => {
        const { dispatch, form } = this.props;
        e.preventDefault();
        form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                dispatch({
                    type: 'menusAdd/submitRegularForm',
                    payload: values,
                });
            }
        });
    };

    render() {
        const { submitting } = this.props;
        const {
            form: { getFieldDecorator, getFieldValue },
        } = this.props;

        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 7 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 12 },
                md: { span: 10 },
            },
        };

        const submitFormLayout = {
            wrapperCol: {
                xs: { span: 24, offset: 0 },
                sm: { span: 10, offset: 7 },
            },
        };

        return (
            <PageHeaderWrapper
                title={<FormattedMessage id="app.forms.basic.title" />}
                content={<FormattedMessage id="app.forms.basic.description" />}
            >
                <Card bordered={false}>
                    <Form onSubmit={this.handleSubmit} hideRequiredMark style={{ marginTop: 8 }}>
                        <FormItem {...formItemLayout} label={<FormattedMessage id="name" />}>
                            {getFieldDecorator('name', {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage({ id: 'validation.name.required' }),
                                    },
                                ],
                            })(<Input placeholder={formatMessage({ id: 'form.name.placeholder' })} />)}
                        </FormItem>

                        <FormItem {...formItemLayout} label={<FormattedMessage id="icon" />}>
                            {getFieldDecorator('icon', {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage({ id: 'validation.icon.required' }),
                                    },
                                ],
                            })(<Input placeholder={formatMessage({ id: 'form.icon.placeholder' })} />)}
                        </FormItem>

                        <FormItem {...formItemLayout} label={<FormattedMessage id="path" />}>
                            {getFieldDecorator('path', {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage({ id: 'validation.path.required' }),
                                    },
                                ],
                            })(<Input placeholder={formatMessage({ id: 'form.path.placeholder' })} />)}
                        </FormItem>

                        <FormItem {...formItemLayout} label={<FormattedMessage id="compath" />}>
                            {getFieldDecorator('compath', {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage({ id: 'validation.compath.required' }),
                                    },
                                ],
                            })(<Input placeholder={formatMessage({ id: 'form.compath.placeholder' })} />)}
                        </FormItem>

                        <FormItem {...formItemLayout} label={<FormattedMessage id="router" />}>
                            {getFieldDecorator('router', {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage({ id: 'validation.router.required' }),
                                    },
                                ],
                            })(<Input placeholder={formatMessage({ id: 'form.router.placeholder' })} />)}
                        </FormItem>

                        <FormItem {...submitFormLayout} style={{ marginTop: 32 }}>
                            <Button type="primary" htmlType="submit" loading={submitting}>
                                <FormattedMessage id="submit" />
                            </Button>
                            <Button style={{ marginLeft: 100 }}>
                                <FormattedMessage id="save" />
                            </Button>
                        </FormItem>
                    </Form>
                </Card>
            </PageHeaderWrapper>
        );
    }
}

export default Add;
