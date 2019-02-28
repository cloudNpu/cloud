import { addMenu } from "@/services/menu";
import { message } from "antd";

export default {
    namespace: 'AddMenu',

    state: {
        step: {
            payAccount: 'ant-design@alipay.com',
            receiverAccount: 'test@example.com',
            receiverName: 'Alex',
            amount: '500',
        },
    },

    effects: {
        *save({ payload }, { call, put }) {
           // yield call(addMenu, payload)
            const response = yield call(addMenu, payload);
            if (response.status===200) {
                message.success("提交成功");
            } else message.success("提交失败");
        }
    }
};
