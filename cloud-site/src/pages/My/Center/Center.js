import React, { Component, Fragment } from "react";
import { formatMessage, FormattedMessage } from "umi/locale";
import { Form, Input, Upload, Select, Button, message } from "antd";
import { connect } from "dva";
import styles from "./Center.less";
import PhoneView from "./PhoneView";
import user from "../../../models/user";
// import { getTimeDistance } from '@/utils/utils';

const FormItem = Form.Item;
const { Option } = Select;

// 头像组件 方便以后独立，增加裁剪之类的功能
const AvatarView = ({ avatar }) => (
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
);

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

@connect(({ center }) => ({
  currentUser: center.currentUser
}))
@Form.create()
class Center extends Component {
  componentDidMount() {
    this.setBaseInfo();
  }
  setBaseInfo = () => {
    const { currentUser, form } = this.props;
    Object.keys(form.getFieldsValue()).forEach(key => {
      const obj = {};
      obj[key] = currentUser[key] || null;
      form.setFieldsValue(obj); //将表中数据根据key值放入表中
    });
  };
  getAvatarURL() {
    const { currentUser } = this.props;
    if (currentUser.avatar) {
      return currentUser.avatar;
    }
    const url =
      "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";
    return url;
  }
  getViewDom = ref => {
    this.view = ref;
  };
  //currentUser不是对象，未找到对象！
  handleUpdateForm = currentUser => {
    const { dispatch } = this.props;
    console.log("111111111111");
    dispatch({
      type: "center/update",
      payload: {
        id: currentUser.id,
        username: currentUser.username,
        dept: currentUser.dept,
        mobile: currentUser.mobile,
        officeTel: currentUser.officeTel,
        avatar: currentUser.avatar,
        new_passward: currentUser.new_passward,
        new_passward_again: currentUser.new_passward_again
      }
    });
    message.success("更新成功");
  };

  render() {
    const {
      form: { getFieldDecorator }
    } = this.props;
    // console.log(sessionStorage.getItem(user.id))
    // console.log(sessionStorage.getItem(user.dept)
    return (
      <div className={styles.baseView} ref={this.getViewDom}>
        <div className={styles.left}>
          <Form
            layout="vertical"
            onSubmit={this.handleUpdateForm}
            hideRequiredMark
          >
            <FormItem
              label={formatMessage({ id: "app.settings.basic.nickname" })}
            >
              {getFieldDecorator("username", {
                rules: [
                  {
                    required: true,
                    message: formatMessage(
                      { id: "app.settings.basic.nickname-message" },
                      {}
                    )
                  }
                ]
              })(<Input />)}
            </FormItem>
            <FormItem label={formatMessage({ id: "app.settings.basic.dept" })}>
              {getFieldDecorator("dept", {
                rules: [
                  {
                    required: true,
                    message: formatMessage(
                      { id: "app.settings.basic.dept-message" },
                      {}
                    )
                  }
                ]
              })(<Input />)}
            </FormItem>
            <FormItem label={formatMessage({ id: "app.settings.basic.phone" })}>
              {getFieldDecorator("officeTel", {
                rules: [
                  {
                    required: true,
                    message: formatMessage(
                      { id: "app.settings.basic.phone-message" },
                      {}
                    )
                  },
                  { validator: validatorPhone }
                ]
              })(<PhoneView />)}
            </FormItem>
            <FormItem label={formatMessage({ id: "app.settings.basic.phone" })}>
              {getFieldDecorator("mobile", {})(<Input />)}
            </FormItem>
            {/* <FormItem label={formatMessage({ id: "app.settings.basic.old" })}>
                            {getFieldDecorator("passward", {
                                rules: [
                                    {
                                        required: true,
                                        message: formatMessage(
                                            { id: "app.settings.basic.old-message" },
                                            {}
                                        )
                                    }
                                ]
                            })(<Input />)}
                        </FormItem>*/}
            <FormItem label={formatMessage({ id: "app.settings.basic.new" })}>
              {getFieldDecorator("new_passward", {})(<Input />)}
            </FormItem>
            <FormItem label={formatMessage({ id: "app.settings.basic.again" })}>
              {getFieldDecorator("new_passward_again", {})(<Input />)}
            </FormItem>
          </Form>
          <Button type="primary" onClick={this.handleUpdateForm}>
            <FormattedMessage
              id="app.settings.basic.update"
              defaultMessage="Update Information"
            />
          </Button>
        </div>
        <div className={styles.right}>
          <AvatarView avatar={this.getAvatarURL()} />
        </div>
      </div>
    );
  }
}

export default Center;
