import { routerRedux } from 'dva/router';
import { message } from 'antd';
import { fakeSubmitForm } from '@/services/api';

export default {
    namespace: 'menusAdd',

    state: {
        step: {
            payAccount: 'ant-design@alipay.com',
            receiverAccount: 'test@example.com',
            receiverName: 'Alex',
            amount: '500',
        },
    },

    effects: {
        *submitRegularForm({ payload }, { call }) {
            yield call(fakeSubmitForm, payload);
            message.success('提交成功');
        },
    },

   /* reducers: {
        saveStepFormData(state, { payload }) {
            return {
                ...state,
                step: {
                    ...state.step,
                    ...payload,
                },
            };
        },
    },*/
};
