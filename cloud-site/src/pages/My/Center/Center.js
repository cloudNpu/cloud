import React, { Component, Fragment } from "react";
import { formatMessage, FormattedMessage } from "umi/locale";
import { Form, Input, Upload, Select, Button, message,Icon } from "antd";
import { connect } from "dva";
import {routerRedux} from 'dva/router';
import styles from "./Center.less";
import PhoneView from "./PhoneView";
// import { getTimeDistance } from '@/utils/utils';

const FormItem = Form.Item;
const { Option } = Select;

// 头像组件 方便以后独立，增加裁剪之类的功能
/*const AvatarView = ({ avatar }) => (
  <Fragment>
    <div className={styles.avatar_title}>
      <FormattedMessage
        id="app.settings.basic.avatar"
        defaultMessage="Avatar"
      />
    </div>
    <div className={styles.avatar}>
      <img src={avatar} alt="avatar" />
    </div>
    <Upload fileList={[]}>
      <div className={styles.button_view}>
        <Button icon="upload">
          <FormattedMessage
            id="app.settings.basic.change-avatar"
            defaultMessage="Change avatar"
          />
        </Button>
      </div>
    </Upload>
  </Fragment>
);*/

const validatorPhone = (rule, value, callback) => {
  const values = value.split("-");
  if (!values[0]) {
    callback("Please input your area code!");
  }
  if (!values[1]) {
    callback("Please input your phone number!");
  }
  callback();
};

@connect(({ user }) => ({
  currentUser: user.currentUser
}))
@Form.create()
class Center extends Component {
    componentDidMount() {
        this.setBaseInfo();
    }

    setBaseInfo = () => {
        const {currentUser, form} = this.props;
        Object.keys(form.getFieldsValue()).forEach(key => {
            const obj = {};
            obj[key] = currentUser[key] || null;
            if(key==='dept'){
                console.log('get');
                console.log(obj.dept);
                form.setFieldsValue(obj.dept.deptName);
            }
            else {
                form.setFieldsValue(obj);
            }//将表中数据根据key值放入表中;
        });
        //console.log(form.getFieldValue('id'));
    };
    handleUpdateForm = fields => {
      const {getFieldValue} = this.props.form;
      const { dispatch} = this.props;
      dispatch({
        type: "center/update",
        payload:{
            id: getFieldValue('id'),
            username: getFieldValue('username'),
            password: getFieldValue('password'),
            sex: getFieldValue('sex'),
            birthday: getFieldValue('birthday'),
            mobile: getFieldValue('mobile'),
            officeTel: getFieldValue('officeTel'),
           // dept: { id: getFieldValue('dept') }
        }
      });
      message.success("修改成功，请重新登录");
      dispatch(routerRedux.push('/user/login'));
    };
    validateToNextPassword = (rule, value, callback) => {
        const form = this.props.form;
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm'], { force: true });
        }
        callback();
    };
    render() {
        const {
            form: {getFieldDecorator}
        } = this.props;
        return (
            <div className={styles.baseView} ref={this.getViewDom}>
                <div className={styles.left}>
                    <Form
                        layout="vertical"
                        onSubmit={this.handleUpdateForm}
                        hideRequiredMark
                    >
                        <FormItem label={formatMessage({id: "app.settings.basic.id"})}>
                            {getFieldDecorator("id", {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage({id: "app.settings.basic.id"}, {})
                                    }
                                ]
                            })(<Input/>)}
                        </FormItem>
                        <FormItem
                            label={formatMessage({id: "app.settings.basic.nickname"})}
                        >
                            {getFieldDecorator("username", {
                                //initialValue: sessionStorage.getItem("user".username),
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage(
                                            {id: "app.settings.basic.nickname-message"},
                                            {}
                                        )
                                    }
                                ]
                            })(<Input/>)}
                        </FormItem>
                        <FormItem label={formatMessage({id: "app.settings.basic.sex"})}>
                            {getFieldDecorator("sex", {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage(
                                            {id: "app.settings.basic.sex-message"},
                                            {}
                                        )
                                    }
                                ]
                            })(<Input/>)}
                        </FormItem>
                        <FormItem label={formatMessage({id: "app.settings.basic.dept"})}>
                            {getFieldDecorator("dept", {
                                initialValue:JSON.parse(sessionStorage.getItem('user')).dept.deptName,
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage(
                                            {id: "app.settings.basic.dept-message"},
                                            {}
                                        )
                                    }
                                ]
                            })(<Input/>)}
                        </FormItem>
                        <FormItem label={formatMessage({id: "app.settings.basic.birthday"})}>
                            {getFieldDecorator("birthday", {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage(
                                            {id: "app.settings.basic.birthday-message"},
                                            {}
                                        )
                                    }
                                ]
                            })(<Input/>)}
                        </FormItem>
                        <FormItem label={formatMessage({id: "app.settings.basic.phone"})}>
                            {getFieldDecorator("officeTel", {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage(
                                            {id: "app.settings.basic.phone-message"},
                                            {}
                                        )
                                    },
                                    {validator: validatorPhone}
                                ]
                            })(<PhoneView/>)}
                        </FormItem>
                        <FormItem label={formatMessage({id: "app.settings.basic.phone"})}>
                            {getFieldDecorator("mobile", {})(<Input/>)}
                        </FormItem>
                       {/* <FormItem label={formatMessage({id: "app.settings.basic.new"})}>
                            {getFieldDecorator("new_password", {})(
                                <Input
                                prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                                type="password"
                                placeholder="Password"
                            />)}
                        </FormItem>
                        <FormItem label={formatMessage({id: "app.settings.basic.again"})}>
                            {getFieldDecorator("new_password_again", {})(<Input/>)}
                        </FormItem>*/}
                        <Form.Item label="Password" hasFeedback>
                            {getFieldDecorator('password', {
                                rules: [
                                    {
                                        required: true,
                                        message: '请输入密码!',
                                    },
                                    {},
                                ],
                            })(<Input.Password />)}
                        </Form.Item>
                    </Form>
                    <Button type="primary" onClick={this.handleUpdateForm}>
                        <FormattedMessage
                            id="app.settings.basic.update"
                            defaultMessage="Update Information"
                        />
                    </Button>
                </div>
            </div>
        );
    }
}

export default Center;
