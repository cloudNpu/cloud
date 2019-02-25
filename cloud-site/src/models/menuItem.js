import { addMenuItem } from "@/services/menuItem";
import { message } from "antd";

export default {
    namespace: 'AddMenuItem',

    state: {
        step: {
            payAccount: 'ant-design@alipay.com',
            receiverAccount: 'test@example.com',
            receiverName: 'Alex',
            amount: '500',
        },
    },

    effects: {
        //  *submitRegularForm({ payload }, { call }) {
        //    yield call(fakeSubmitForm, payload);
        //  message.success('提交成功');
        //},
        *save({ payload }, { call, put }) {
            // yield call(addMenu, payload)
            const response = yield call(addMenuItem, payload);
            console.log(response);
            //console.log( response);
            if (response.status===200) {
                message.success("提交成功");
            } else message.success("提交失败");
        }
    }
};
