import React, { Component, Fragment } from "react";
import { formatMessage, FormattedMessage } from "umi/locale";
import { Form, Input, Upload, Select, Button } from "antd";
import { connect } from "dva";
import styles from "./Center.less";
//import GeographicView from './GeographicView';
import PhoneView from "./PhoneView";
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

@connect(({ user }) => ({
  currentUser: user.currentUser
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
      form.setFieldsValue(obj);
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

  render() {
    const {
      form: { getFieldDecorator }
    } = this.props;
    return (
      <div className={styles.baseView} ref={this.getViewDom}>
        <div className={styles.left}>
          <Form layout="vertical" onSubmit={this.handleSubmit} hideRequiredMark>
            <FormItem
              label={formatMessage({ id: "app.settings.basic.nickname" })}
            >
              {getFieldDecorator("name", {
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
              {getFieldDecorator("phone", {
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
              {getFieldDecorator("tel", {
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
              })(<Input />)}
            </FormItem>
            <FormItem label={formatMessage({ id: "app.settings.basic.old" })}>
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
            </FormItem>
            <FormItem label={formatMessage({ id: "app.settings.basic.new" })}>
              {getFieldDecorator("name", {
                rules: [
                  {
                    required: true,
                    message: formatMessage(
                      { id: "app.settings.basic.new-message" },
                      {}
                    )
                  }
                ]
              })(<Input />)}
            </FormItem>
            <FormItem label={formatMessage({ id: "app.settings.basic.again" })}>
              {getFieldDecorator("name", {
                rules: [
                  {
                    required: true,
                    message: formatMessage(
                      { id: "app.settings.basic.again-message" },
                      {}
                    )
                  }
                ]
              })(<Input />)}
            </FormItem>
            <Button type="primary">
              <FormattedMessage
                id="app.settings.basic.update"
                defaultMessage="Update Information"
              />
            </Button>
          </Form>
        </div>
        <div className={styles.right}>
          <AvatarView avatar={this.getAvatarURL()} />
        </div>
      </div>
    );
  }
}

export default Center;
