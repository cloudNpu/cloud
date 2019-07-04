import { queryMyRegistry } from "@/services/myregistry";

export default {
    namespace: "myregistry",
    state: {
        myRegistryApp :[]
    },

    effects: {
        *fetch(_, { call, put }) {
            const response = yield (yield call(queryMyRegistry)).json();
            yield put({
                type: "show",
                payload: {
                    myRegistryApp: response
                }
            });

        }
    },

    reducers: {
        show(state, { payload }) {
            return {
                ...state,
                ...payload
            };
        }
    }
};
